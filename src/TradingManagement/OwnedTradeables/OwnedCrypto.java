package TradingManagement.OwnedTradeables;

import TradingManagement.Crypto;

public class OwnedCrypto extends OwnedTradeable {
    private Crypto crypto;

    public OwnedCrypto(Crypto crypto) {
        super(crypto);
        this.crypto = new Crypto(crypto.getName(), crypto.getTickerSymbol(), getCrypto().getCurrentPrice(), crypto.getQuantity());
    }

    public Crypto getCrypto() {
        return crypto;
    }
}
