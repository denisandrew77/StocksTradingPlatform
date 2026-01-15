package TradingManagement.Strategies;

import TradingManagement.Stock;
import TradingManagement.Tradeable;
import UserManagement.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day Trading Strategy - stocks only.
 * A buy must be sold on the same day.
 * If not sold by end of day, a 5% penalty of the stock value is charged.
 */

public class DayTradingStrategy implements Strategy {

    private static final double END_OF_DAY_PENALTY_RATE = 0.05; // 5% penalty

    // Track purchases made today: User -> List of (Stock, quantity, purchaseDate)
    private Map<User, List<DayTrade>> pendingDayTrades = new HashMap<>();

    private static class DayTrade {
        Stock stock;
        double quantity;
        LocalDate purchaseDate;
        double purchasePrice;

        DayTrade(Stock stock, double quantity, LocalDate purchaseDate, double purchasePrice) {
            this.stock = stock;
            this.quantity = quantity;
            this.purchaseDate = purchaseDate;
            this.purchasePrice = purchasePrice;
        }
    }

    @Override
    public void buy(User user, double quantity, Tradeable tradeable) {
        if (!supportsTradeable(tradeable)) {
            System.out.println("Day trading strategy only supports stocks.");
            return;
        }

        Stock stock = (Stock) tradeable;
        double totalCost = stock.getCurrentPrice() * quantity;

        if (user.getBalance() < totalCost) {
            System.out.println("Insufficient balance for day trade. Required: " + totalCost + ", Available: " + user.getBalance());
            return;
        }

        if (stock.getQuantity() < quantity) {
            System.out.println("Insufficient stock quantity available. Requested: " + quantity + ", Available: " + stock.getQuantity());
            return;
        }

        // Execute the buy
        user.setBalance(user.getBalance() - totalCost);
        
        // Track this day trade
        DayTrade dayTrade = new DayTrade(stock, quantity, LocalDate.now(), stock.getCurrentPrice());
        pendingDayTrades.computeIfAbsent(user, k -> new ArrayList<>()).add(dayTrade);

        System.out.println("[Day Trading] Bought " + quantity + " of " + stock.getName() + " at " + stock.getCurrentPrice() + " each. Total: " + totalCost);
        System.out.println("[Day Trading] WARNING: Must sell before end of day to avoid 5% penalty!");
    }

    @Override
    public void sell(User user, double quantity, Tradeable tradeable) {
        if (!supportsTradeable(tradeable)) {
            System.out.println("Day trading strategy only supports stocks.");
            return;
        }

        Stock stock = (Stock) tradeable;
        List<DayTrade> userTrades = pendingDayTrades.get(user);

        if (userTrades == null || userTrades.isEmpty()) {
            System.out.println("No day trades to sell for user: " + user.getName());
            return;
        }

        // Find matching day trade for this stock
        DayTrade matchingTrade = null;
        for (DayTrade trade : userTrades) {
            if (trade.stock.getName().equals(stock.getName()) && trade.quantity >= quantity) {
                matchingTrade = trade;
                break;
            }
        }

        if (matchingTrade == null) {
            System.out.println("No matching day trade found for " + stock.getName() + " with quantity " + quantity);
            return;
        }

        // Execute the sell
        double saleValue = stock.getCurrentPrice() * quantity;
        user.setBalance(user.getBalance() + saleValue);

        // Update or remove the day trade
        matchingTrade.quantity -= quantity;
        if (matchingTrade.quantity <= 0) {
            userTrades.remove(matchingTrade);
        }

        System.out.println("[Day Trading] Sold " + quantity + " of " + stock.getName() + " at " + stock.getCurrentPrice() + " each. Total: " + saleValue);
    }

    @Override
    public void endOfDay(User user) {
        List<DayTrade> userTrades = pendingDayTrades.get(user);

        if (userTrades == null || userTrades.isEmpty()) {
            System.out.println("[Day Trading] No pending day trades for " + user.getName());
            return;
        }

        LocalDate today = LocalDate.now();
        List<DayTrade> tradesToPenalize = new ArrayList<>();

        for (DayTrade trade : userTrades) {
            if (!trade.purchaseDate.isAfter(today)) {
                tradesToPenalize.add(trade);
            }
        }

        double totalPenalty = 0;
        for (DayTrade trade : tradesToPenalize) {
            double currentValue = trade.stock.getCurrentPrice() * trade.quantity;
            double penalty = currentValue * END_OF_DAY_PENALTY_RATE;
            totalPenalty += penalty;
            System.out.println("[Day Trading] PENALTY: " + trade.stock.getName() + " not sold today. Charging 5% penalty: " + penalty);
        }

        if (totalPenalty > 0) {
            user.setBalance(user.getBalance() - totalPenalty);
            System.out.println("[Day Trading] Total end-of-day penalty for " + user.getName() + ": " + totalPenalty);
            System.out.println("[Day Trading] New balance: " + user.getBalance());
        }

        // Clear today's trades
        userTrades.removeAll(tradesToPenalize);
    }

    @Override
    public String getStrategyName() {
        return "Day Trading Strategy";
    }

    @Override
    public boolean supportsTradeable(Tradeable tradeable) {
        return tradeable instanceof Stock;
    }

    /**
     * Get pending day trades for a user
     */
    public List<DayTrade> getPendingTrades(User user) {
        return pendingDayTrades.getOrDefault(user, new ArrayList<>());
    }
}
