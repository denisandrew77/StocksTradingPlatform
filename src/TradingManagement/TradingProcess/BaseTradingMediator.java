package TradingManagement.TradingProcess;

import TradingManagement.Tradeable;
import UserManagement.User;

public interface BaseTradingMediator {
    boolean trade(User user, String tradeableName, double quantity);
}
