package TradingManagement;

public class Stock extends  Tradeable{
    public Stock(String name, String tickerSymbol, double currentPrice, double quantity) {
        super(name, tickerSymbol, currentPrice, quantity);
    }
}
