package TradingManagement.OwnedTradeables;

import TradingManagement.Crypto;
import TradingManagement.Stock;
import TradingManagement.Tradeable;

public class OwnedStock extends OwnedTradeable {
    private Tradeable tradeable;

    public OwnedStock(Tradeable tradeable) {
        super(tradeable);
    }

    public Tradeable getTradeable() {
        return tradeable;
    }
}
