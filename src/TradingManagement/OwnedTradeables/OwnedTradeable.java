package TradingManagement.OwnedTradeables;

import TradingManagement.Tradeable;

public abstract class OwnedTradeable extends Tradeable {
    private Tradeable tradeable;

    private double ownedQuantity;

    public OwnedTradeable(Tradeable tradeable) {
        super(tradeable.getName(), tradeable.getTickerSymbol(), tradeable.getCurrentPrice(), tradeable.getQuantity());
        this.tradeable = tradeable;
        this.ownedQuantity = 0;
    }


    public Tradeable getTradeable() {
        return tradeable;
    }

    public double getOwnedQuantity() {
        return ownedQuantity;
    }

    public void setOwnedQuantity(double ownedQuantity) {
        this.ownedQuantity = ownedQuantity;
    }
    @Override
    public String toString() {
        return tradeable + " " + "owned: " + ownedQuantity;
    }
}
