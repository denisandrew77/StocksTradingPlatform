package TradingManagement.Strategies;

import TradingManagement.Tradeable;
import UserManagement.User;

public interface Strategy {
    /**
     * Execute a buy operation
     */
    void buy(User user, double quantity, Tradeable tradeable);

    /**
     * Execute a sell operation
     */
    void sell(User user, double quantity, Tradeable tradeable);

    /**
     * Called at end of day to process any pending operations (e.g., day trading penalties)
     */
    void endOfDay(User user);

    /**
     * Get the strategy name
     */
    String getStrategyName();

    /**
     * Check if this strategy supports the given tradeable type
     */
    boolean supportsTradeable(Tradeable tradeable);
}
