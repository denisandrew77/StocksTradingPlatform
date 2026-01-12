package TradingManagement.OwnedTradeables;

import TradingManagement.Crypto;
import TradingManagement.Tradeable;

public class OwnedCrypto extends OwnedTradeable {
    private Tradeable tradeable;

    public OwnedCrypto(Tradeable tradeable) {
        super(tradeable);
    }

    public Tradeable getCrypto() {
        return tradeable;
    }
}
