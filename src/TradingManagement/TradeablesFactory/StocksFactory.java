package TradingManagement.TradeablesFactory;

import TradingManagement.Stock;
import TradingManagement.Tradeable;

public class StocksFactory implements TradeableFactory {

    @Override
    public Tradeable createTradeable(String name, String tickerSymbol, double currentPrice, double quantity) {
        return new Stock(name, tickerSymbol, currentPrice, quantity);
    }
}
