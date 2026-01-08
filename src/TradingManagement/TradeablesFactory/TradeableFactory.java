package TradingManagement.TradeablesFactory;

import TradingManagement.Tradeable;

public interface TradeableFactory {
    Tradeable createTradeable(String name, String tickerSymbol, double currentPrice, double quantity);
}
