package TradingManagement.Strategies;

import TradingManagement.Tradeable;
import UserManagement.User;
import java.util.HashMap;
import java.util.Map;

/**
 * Strategy Picker - Context class for the Strategy Design Pattern.
 * Allows easy configuration and switching between different trading strategies.
 */
public class StrategyPicker {

    // Available strategies
    private final DayTradingStrategy dayTradingStrategy;
    private final LongTermStrategy longTermStrategy;
    private final AutoBuyStrategy autoBuyStrategy;

    // Current strategy per user
    private Map<User, Strategy> userStrategies = new HashMap<>();

    // Default strategy
    private Strategy defaultStrategy;

    public StrategyPicker() {
        this.dayTradingStrategy = new DayTradingStrategy();
        this.longTermStrategy = new LongTermStrategy();
        this.autoBuyStrategy = new AutoBuyStrategy();
        this.defaultStrategy = longTermStrategy; // Default to long-term investing
    }

    /**
     * Set the default strategy for all users
     */
    public void setDefaultStrategy(Strategy strategy) {
        this.defaultStrategy = strategy;
        System.out.println("[Strategy Picker] Default strategy set to: " + strategy.getStrategyName());
    }

    /**
     * Set strategy for a specific user
     */
    public void setUserStrategy(User user, Strategy strategy) {
        userStrategies.put(user, strategy);
        System.out.println("[Strategy Picker] " + user.getName() + "'s strategy set to: " + strategy.getStrategyName());
    }

    /**
     * Get the current strategy for a user
     */
    public Strategy getUserStrategy(User user) {
        return userStrategies.getOrDefault(user, defaultStrategy);
    }

    /**
     * Execute a buy using the user's current strategy
     */
    public void buy(User user, double quantity, Tradeable tradeable) {
        Strategy strategy = getUserStrategy(user);
        
        if (!strategy.supportsTradeable(tradeable)) {
            System.out.println("[Strategy Picker] " + strategy.getStrategyName() + " does not support " + tradeable.getClass().getSimpleName());
            return;
        }

        System.out.println("[Strategy Picker] Executing buy with " + strategy.getStrategyName());
        strategy.buy(user, quantity, tradeable);
    }

    /**
     * Execute a sell using the user's current strategy
     */
    public void sell(User user, double quantity, Tradeable tradeable) {
        Strategy strategy = getUserStrategy(user);

        if (!strategy.supportsTradeable(tradeable)) {
            System.out.println("[Strategy Picker] " + strategy.getStrategyName() + " does not support " + tradeable.getClass().getSimpleName());
            return;
        }

        System.out.println("[Strategy Picker] Executing sell with " + strategy.getStrategyName());
        strategy.sell(user, quantity, tradeable);
    }

    /**
     * Process end of day for a user
     */
    public void endOfDay(User user) {
        Strategy strategy = getUserStrategy(user);
        System.out.println("[Strategy Picker] Processing end of day for " + user.getName() + " with " + strategy.getStrategyName());
        strategy.endOfDay(user);
    }

    /**
     * Process end of day for all users with registered strategies
     */
    public void endOfDayForAll() {
        System.out.println("[Strategy Picker] Processing end of day for all users...");
        for (User user : userStrategies.keySet()) {
            endOfDay(user);
        }
    }

    // Getters for individual strategies (for direct access if needed)

    public DayTradingStrategy getDayTradingStrategy() {
        return dayTradingStrategy;
    }

    public LongTermStrategy getLongTermStrategy() {
        return longTermStrategy;
    }

    public AutoBuyStrategy getAutoBuyStrategy() {
        return autoBuyStrategy;
    }

    /**
     * Configure an auto-trade rule (convenience method)
     */
    public void configureAutoTradeRule(User user, Tradeable tradeable, double buyThreshold, double sellThreshold, double quantity) {
        autoBuyStrategy.configureRule(user, tradeable, buyThreshold, sellThreshold, quantity);
    }

    /**
     * Check and execute auto trades (convenience method)
     */
    public void checkAutoTrades(User user) {
        autoBuyStrategy.checkAndExecute(user);
    }

    /**
     * Display available strategies
     */
    public void displayAvailableStrategies() {
        System.out.println("Available Trading Strategies:");
        System.out.println("1. " + dayTradingStrategy.getStrategyName() + " (stocks only, must sell same day or 5% penalty)");
        System.out.println("2. " + longTermStrategy.getStrategyName() + " (stocks only, can hold indefinitely)");
        System.out.println("3. " + autoBuyStrategy.getStrategyName() + " (auto buy/sell based on price thresholds)");
    }

    /**
     * Display user's current strategy
     */
    public void displayUserStrategy(User user) {
        Strategy strategy = getUserStrategy(user);
        System.out.println(user.getName() + "'s current strategy: " + strategy.getStrategyName());
    }
}
