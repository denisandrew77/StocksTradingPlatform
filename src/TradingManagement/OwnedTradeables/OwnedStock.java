package TradingManagement.OwnedTradeables;

import TradingManagement.Crypto;
import TradingManagement.Stock;

public class OwnedStock extends OwnedTradeable {
    private Stock stock;

    public OwnedStock(Stock stock) {
        super(stock);
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }
}
