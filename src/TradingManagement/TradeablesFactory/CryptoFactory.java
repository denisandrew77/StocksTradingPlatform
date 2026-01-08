package TradingManagement.TradeablesFactory;

import TradingManagement.Crypto;
import TradingManagement.Tradeable;

public class CryptoFactory implements TradeableFactory {

    @Override
    public Tradeable createTradeable(String name, String tickerSymbol, double currentPrice, double quantity) {
        return new Crypto(name, tickerSymbol, currentPrice, quantity);
    }
}
