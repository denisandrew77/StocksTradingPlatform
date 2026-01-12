package TradingManagement;

import java.util.ArrayList;

public class TradeablesManagement {
    private ArrayList<Crypto> cryptos = new ArrayList<Crypto>();
    private ArrayList<Stock> stocks = new ArrayList<Stock>();
    private static TradeablesManagement tradeablesManagement;
    private TradeablesManagement() {

    }

    public static TradeablesManagement getMarket() {
        if(tradeablesManagement == null) {
            tradeablesManagement = new TradeablesManagement();
        }
        return tradeablesManagement;
    }

    public ArrayList<Crypto> getCryptos() {
        return cryptos;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void addCrypto(Crypto crypto) {
        cryptos.add(crypto);
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void updateCryptoQuantity(Crypto crypto, double amountBought) {
        for (Crypto c : cryptos) {
            if (c.getName().equals(crypto.getName())) {
                c.setQuantity(c.getQuantity() - amountBought);
            }
        }
    }
    public void updateStockQuantity(Stock stock, double amountBought) {
        for (Stock s : stocks) {
            if (s.getName().equals(stock.getName())) {
                s.setQuantity(s.getQuantity() - amountBought);
            }
        }
    }

    public Crypto getCryptoByName(String name){
        for(Crypto c : cryptos){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    public Stock getStockByName(String name){
        for(Stock s : stocks){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    public double getCryptoPrice(String nameOrTickerSymbol){
        for(Crypto c : cryptos){
            if(c.getName().equals(nameOrTickerSymbol) || c.getTickerSymbol().equals(nameOrTickerSymbol)){
                return c.getCurrentPrice();
            }
        }
        return -1;
    }

    public double getStockPrice(String nameOrTickerSymbol){
        for(Stock s : stocks){
            if(s.getName().equals(nameOrTickerSymbol) || s.getTickerSymbol().equals(nameOrTickerSymbol)){
                return s.getCurrentPrice();
            }
        }
        return -1;
    }

}
