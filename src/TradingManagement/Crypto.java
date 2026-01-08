package TradingManagement;

public class Crypto extends Tradeable {
    public Crypto(String name, String tickerSymbol, double currentPrice, double quantity) {
        super(name, tickerSymbol, currentPrice, quantity);
    }

    @Override
    public String toString(){
        return name + " " + tickerSymbol + " " + currentPrice + " " + quantity + "\n";
    }
}
