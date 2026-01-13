package TradingManagement.Strategies;

import TradingManagement.Tradeable;
import UserManagement.User;

public interface Strategy {
    void trade(User user, double amount, Tradeable tradeable);
}
