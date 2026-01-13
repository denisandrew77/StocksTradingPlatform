package TradingManagement;

import java.time.LocalDate;
import java.util.Date;

public class TradeRecord {
    private String tradeableName;
    private double quantity;
    private double price;
    private LocalDate date;

    public TradeRecord(String tradeableName, double quantity, double price, LocalDate date) {
        this.tradeableName = tradeableName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public String getTradeableName() {
        return tradeableName;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }
}
