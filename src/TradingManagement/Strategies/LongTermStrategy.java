package TradingManagement.Strategies;

import TradingManagement.OwnedTradeables.OwnedStock;
import TradingManagement.Stock;
import TradingManagement.Tradeable;
import UserManagement.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Long-Term Investing Strategy - stocks only.
 * Stocks can stay in portfolio for more than 1 day.
 * If sold on the same day as purchase, it's considered a day trade (but no penalty in this strategy).
 */
public class LongTermStrategy implements Strategy {

    // Track holdings: User -> List of holdings with purchase date
    private Map<User, List<LongTermHolding>> holdings = new HashMap<>();

    public static class LongTermHolding {
        private Stock stock;
        private double quantity;
        private LocalDate purchaseDate;
        private double purchasePrice;

        public LongTermHolding(Stock stock, double quantity, LocalDate purchaseDate, double purchasePrice) {
            this.stock = stock;
            this.quantity = quantity;
            this.purchaseDate = purchaseDate;
            this.purchasePrice = purchasePrice;
        }

        public Stock getStock() {
            return stock;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public LocalDate getPurchaseDate() {
            return purchaseDate;
        }

        public double getPurchasePrice() {
            return purchasePrice;
        }

        public boolean isSameDayTrade() {
            return purchaseDate.equals(LocalDate.now());
        }

        public long getDaysHeld() {
            return java.time.temporal.ChronoUnit.DAYS.between(purchaseDate, LocalDate.now());
        }
    }

    @Override
    public void buy(User user, double quantity, Tradeable tradeable) {
        if (!supportsTradeable(tradeable)) {
            System.out.println("Long-term investing strategy only supports stocks.");
            return;
        }

        Stock stock = (Stock) tradeable;
        double totalCost = stock.getCurrentPrice() * quantity;

        if (user.getBalance() < totalCost) {
            System.out.println("Insufficient balance for long-term investment. Required: " + totalCost + ", Available: " + user.getBalance());
            return;
        }

        if (stock.getQuantity() < quantity) {
            System.out.println("Insufficient stock quantity available. Requested: " + quantity + ", Available: " + stock.getQuantity());
            return;
        }

        // Execute the buy
        user.setBalance(user.getBalance() - totalCost);

        // Track this holding
        LongTermHolding holding = new LongTermHolding(stock, quantity, LocalDate.now(), stock.getCurrentPrice());
        holdings.computeIfAbsent(user, k -> new ArrayList<>()).add(holding);

        System.out.println("[Long-Term] Bought " + quantity + " of " + stock.getName() + " at " + stock.getCurrentPrice() + " each. Total: " + totalCost);
        System.out.println("[Long-Term] This stock can be held for any duration.");
    }

    @Override
    public void sell(User user, double quantity, Tradeable tradeable) {
        if (!supportsTradeable(tradeable)) {
            System.out.println("Long-term investing strategy only supports stocks.");
            return;
        }

        Stock stock = (Stock) tradeable;
        List<LongTermHolding> userHoldings = holdings.get(user);

        if (userHoldings == null || userHoldings.isEmpty()) {
            System.out.println("No long-term holdings to sell for user: " + user.getName());
            return;
        }

        // Find matching holding for this stock (FIFO - First In, First Out)
        LongTermHolding matchingHolding = null;
        for (LongTermHolding holding : userHoldings) {
            if (holding.getStock().getName().equals(stock.getName()) && holding.getQuantity() >= quantity) {
                matchingHolding = holding;
                break;
            }
        }

        if (matchingHolding == null) {
            System.out.println("No matching long-term holding found for " + stock.getName() + " with quantity " + quantity);
            return;
        }

        // Check if this is a same-day trade
        boolean isSameDayTrade = matchingHolding.isSameDayTrade();

        // Execute the sell
        double saleValue = stock.getCurrentPrice() * quantity;
        user.setBalance(user.getBalance() + saleValue);

        // Calculate profit/loss
        double costBasis = matchingHolding.getPurchasePrice() * quantity;
        double profitLoss = saleValue - costBasis;

        // Update or remove the holding
        matchingHolding.setQuantity(matchingHolding.getQuantity() - quantity);
        if (matchingHolding.getQuantity() <= 0) {
            userHoldings.remove(matchingHolding);
        }

        System.out.println("[Long-Term] Sold " + quantity + " of " + stock.getName() + " at " + stock.getCurrentPrice() + " each. Total: " + saleValue);
        System.out.println("[Long-Term] Days held: " + matchingHolding.getDaysHeld());
        System.out.println("[Long-Term] Profit/Loss: " + (profitLoss >= 0 ? "+" : "") + profitLoss);

        if (isSameDayTrade) {
            System.out.println("[Long-Term] Note: This was a same-day trade (considered day trade for tax purposes).");
        }
    }

    @Override
    public void endOfDay(User user) {
        List<LongTermHolding> userHoldings = holdings.get(user);

        if (userHoldings == null || userHoldings.isEmpty()) {
            System.out.println("[Long-Term] No holdings for " + user.getName());
            return;
        }

        System.out.println("[Long-Term] End of day summary for " + user.getName() + ":");
        double totalValue = 0;
        double totalCostBasis = 0;

        for (LongTermHolding holding : userHoldings) {
            double currentValue = holding.getStock().getCurrentPrice() * holding.getQuantity();
            double costBasis = holding.getPurchasePrice() * holding.getQuantity();
            double unrealizedPL = currentValue - costBasis;
            totalValue += currentValue;
            totalCostBasis += costBasis;

            System.out.println("  - " + holding.getStock().getName() + ": " + holding.getQuantity() + " shares, " +
                    "Days held: " + holding.getDaysHeld() + ", " +
                    "Current value: " + currentValue + ", " +
                    "Unrealized P/L: " + (unrealizedPL >= 0 ? "+" : "") + unrealizedPL);
        }

        System.out.println("[Long-Term] Total portfolio value: " + totalValue);
        System.out.println("[Long-Term] Total unrealized P/L: " + (totalValue - totalCostBasis >= 0 ? "+" : "") + (totalValue - totalCostBasis));
    }

    @Override
    public String getStrategyName() {
        return "Long-Term Investing Strategy";
    }

    @Override
    public boolean supportsTradeable(Tradeable tradeable) {
        return tradeable instanceof Stock;
    }

    /**
     * Get all holdings for a user
     */
    public List<LongTermHolding> getHoldings(User user) {
        return holdings.getOrDefault(user, new ArrayList<>());
    }
}