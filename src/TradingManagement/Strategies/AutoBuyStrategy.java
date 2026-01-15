package TradingManagement.Strategies;

import TradingManagement.Tradeable;
import UserManagement.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auto Buy/Sell Strategy.
 * Automatically buys when price falls below a threshold.
 * Automatically sells when price goes above a threshold.
 */
public class AutoBuyStrategy implements Strategy {

    // Configuration for auto-trading rules
    private Map<User, List<AutoTradeRule>> autoTradeRules = new HashMap<>();

    // Track holdings bought by this strategy
    private Map<User, List<AutoTradeHolding>> autoHoldings = new HashMap<>();

    public static class AutoTradeRule {
        private Tradeable tradeable;
        private double buyThreshold;    // Buy when price falls below this
        private double sellThreshold;   // Sell when price goes above this
        private double quantity;        // Quantity to buy/sell
        private boolean enabled;

        public AutoTradeRule(Tradeable tradeable, double buyThreshold, double sellThreshold, double quantity) {
            this.tradeable = tradeable;
            this.buyThreshold = buyThreshold;
            this.sellThreshold = sellThreshold;
            this.quantity = quantity;
            this.enabled = true;
        }

        public Tradeable getTradeable() {
            return tradeable;
        }

        public double getBuyThreshold() {
            return buyThreshold;
        }

        public void setBuyThreshold(double buyThreshold) {
            this.buyThreshold = buyThreshold;
        }

        public double getSellThreshold() {
            return sellThreshold;
        }

        public void setSellThreshold(double sellThreshold) {
            this.sellThreshold = sellThreshold;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    private static class AutoTradeHolding {
        Tradeable tradeable;
        double quantity;
        double purchasePrice;

        AutoTradeHolding(Tradeable tradeable, double quantity, double purchasePrice) {
            this.tradeable = tradeable;
            this.quantity = quantity;
            this.purchasePrice = purchasePrice;
        }
    }

    /**
     * Configure an auto-trade rule for a user and tradeable
     */
    public void configureRule(User user, Tradeable tradeable, double buyThreshold, double sellThreshold, double quantity) {
        AutoTradeRule rule = new AutoTradeRule(tradeable, buyThreshold, sellThreshold, quantity);
        autoTradeRules.computeIfAbsent(user, k -> new ArrayList<>()).add(rule);
        System.out.println("[Auto Trade] Rule configured for " + user.getName() + ":");
        System.out.println("  - Tradeable: " + tradeable.getName());
        System.out.println("  - Buy when price <= " + buyThreshold);
        System.out.println("  - Sell when price >= " + sellThreshold);
        System.out.println("  - Quantity: " + quantity);
    }

    /**
     * Remove an auto-trade rule
     */
    public void removeRule(User user, Tradeable tradeable) {
        List<AutoTradeRule> rules = autoTradeRules.get(user);
        if (rules != null) {
            rules.removeIf(rule -> rule.getTradeable().getName().equals(tradeable.getName()));
            System.out.println("[Auto Trade] Rule removed for " + tradeable.getName());
        }
    }

    /**
     * Check prices and execute auto trades based on configured rules
     */
    public void checkAndExecute(User user) {
        List<AutoTradeRule> rules = autoTradeRules.get(user);
        if (rules == null || rules.isEmpty()) {
            System.out.println("[Auto Trade] No rules configured for " + user.getName());
            return;
        }

        System.out.println("[Auto Trade] Checking prices for " + user.getName() + "...");

        for (AutoTradeRule rule : rules) {
            if (!rule.isEnabled()) {
                continue;
            }

            double currentPrice = rule.getTradeable().getCurrentPrice();
            System.out.println("[Auto Trade] " + rule.getTradeable().getName() + " current price: " + currentPrice);

            // Check if we should buy
            if (currentPrice <= rule.getBuyThreshold()) {
                System.out.println("[Auto Trade] Price below buy threshold (" + rule.getBuyThreshold() + "). Triggering auto-buy...");
                buy(user, rule.getQuantity(), rule.getTradeable());
            }

            // Check if we should sell
            if (currentPrice >= rule.getSellThreshold()) {
                System.out.println("[Auto Trade] Price above sell threshold (" + rule.getSellThreshold() + "). Triggering auto-sell...");
                sell(user, rule.getQuantity(), rule.getTradeable());
            }
        }
    }

    @Override
    public void buy(User user, double quantity, Tradeable tradeable) {
        double totalCost = tradeable.getCurrentPrice() * quantity;

        if (user.getBalance() < totalCost) {
            System.out.println("[Auto Trade] Insufficient balance for auto-buy. Required: " + totalCost + ", Available: " + user.getBalance());
            return;
        }

        if (tradeable.getQuantity() < quantity) {
            System.out.println("[Auto Trade] Insufficient quantity available. Requested: " + quantity + ", Available: " + tradeable.getQuantity());
            return;
        }

        // Execute the buy
        user.setBalance(user.getBalance() - totalCost);

        // Track this holding
        AutoTradeHolding holding = new AutoTradeHolding(tradeable, quantity, tradeable.getCurrentPrice());
        autoHoldings.computeIfAbsent(user, k -> new ArrayList<>()).add(holding);

        System.out.println("[Auto Trade] AUTO-BUY executed: " + quantity + " of " + tradeable.getName() + " at " + tradeable.getCurrentPrice() + " each. Total: " + totalCost);
    }

    @Override
    public void sell(User user, double quantity, Tradeable tradeable) {
        List<AutoTradeHolding> userHoldings = autoHoldings.get(user);

        if (userHoldings == null || userHoldings.isEmpty()) {
            System.out.println("[Auto Trade] No auto-trade holdings to sell for user: " + user.getName());
            return;
        }

        // Find matching holding
        AutoTradeHolding matchingHolding = null;
        for (AutoTradeHolding holding : userHoldings) {
            if (holding.tradeable.getName().equals(tradeable.getName()) && holding.quantity >= quantity) {
                matchingHolding = holding;
                break;
            }
        }

        if (matchingHolding == null) {
            System.out.println("[Auto Trade] No matching holding found for " + tradeable.getName() + " with quantity " + quantity);
            return;
        }

        // Execute the sell
        double saleValue = tradeable.getCurrentPrice() * quantity;
        user.setBalance(user.getBalance() + saleValue);

        // Calculate profit/loss
        double costBasis = matchingHolding.purchasePrice * quantity;
        double profitLoss = saleValue - costBasis;

        // Update or remove the holding
        matchingHolding.quantity -= quantity;
        if (matchingHolding.quantity <= 0) {
            userHoldings.remove(matchingHolding);
        }

        System.out.println("[Auto Trade] AUTO-SELL executed: " + quantity + " of " + tradeable.getName() + " at " + tradeable.getCurrentPrice() + " each. Total: " + saleValue);
        System.out.println("[Auto Trade] Profit/Loss: " + (profitLoss >= 0 ? "+" : "") + profitLoss);
    }

    @Override
    public void endOfDay(User user) {
        System.out.println("[Auto Trade] End of day summary for " + user.getName() + ":");

        List<AutoTradeRule> rules = autoTradeRules.get(user);
        if (rules != null && !rules.isEmpty()) {
            System.out.println("  Active rules:");
            for (AutoTradeRule rule : rules) {
                System.out.println("    - " + rule.getTradeable().getName() + ": Buy <= " + rule.getBuyThreshold() + ", Sell >= " + rule.getSellThreshold());
            }
        }

        List<AutoTradeHolding> userHoldings = autoHoldings.get(user);
        if (userHoldings != null && !userHoldings.isEmpty()) {
            System.out.println("  Current auto-trade holdings:");
            double totalValue = 0;
            for (AutoTradeHolding holding : userHoldings) {
                double currentValue = holding.tradeable.getCurrentPrice() * holding.quantity;
                totalValue += currentValue;
                System.out.println("    - " + holding.tradeable.getName() + ": " + holding.quantity + " units, Value: " + currentValue);
            }
            System.out.println("  Total holdings value: " + totalValue);
        }
    }

    @Override
    public String getStrategyName() {
        return "Auto Buy/Sell Strategy";
    }

    @Override
    public boolean supportsTradeable(Tradeable tradeable) {
        return true; // Supports all tradeables
    }

    /**
     * Get all configured rules for a user
     */
    public List<AutoTradeRule> getRules(User user) {
        return autoTradeRules.getOrDefault(user, new ArrayList<>());
    }
}