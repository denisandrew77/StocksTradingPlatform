package TradingManagement;

public abstract class Tradeable {
    String name;
    String tickerSymbol;
    double currentPrice;
    double quantity;
    public Tradeable(String name, String tickerSymbol, double currentPrice, double quantity) {
        this.name = name;
        this.tickerSymbol = tickerSymbol;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
    public String getTickerSymbol() {
        return tickerSymbol;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
    public double getQuantity() {
        return quantity;
    }
    void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name+ " "+tickerSymbol+" "+currentPrice+" "+quantity;
    }
}
