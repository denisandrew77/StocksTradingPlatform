package TradingManagement;

import java.util.ArrayList;

public class TradeablesManagement {
    private ArrayList<Crypto> cryptos = new ArrayList<Crypto>();
    private ArrayList<Stock> stocks = new ArrayList<Stock>();
    private static TradeablesManagement tradeablesManagement;
    private TradeablesManagement() {
        // Cryptocurrencies
        cryptos.add(new Crypto("Bitcoin", "BTC", 43000.00, 1000));
        cryptos.add(new Crypto("Ethereum", "ETH", 2300.00, 1000));
        cryptos.add(new Crypto("Binance Coin", "BNB", 310.00, 522.00));
        cryptos.add(new Crypto("Solana", "SOL", 98.50, 12.00));
        cryptos.add(new Crypto("Cardano", "ADA", 0.52, 1000.00));
        cryptos.add(new Crypto("XRP", "XRP", 0.62, 800.00));
        cryptos.add(new Crypto("Polkadot", "DOT", 6.80, 90.00));
        cryptos.add(new Crypto("Dogecoin", "DOGE", 0.08, 2500.00));
        cryptos.add(new Crypto("Avalanche", "AVAX", 36.40, 40.00));
        cryptos.add(new Crypto("Chainlink", "LINK", 14.20, 75.00));

        // Stocks
        stocks.add(new Stock("Apple Inc.", "AAPL", 185.20, 15));
        stocks.add(new Stock("Microsoft Corp.", "MSFT", 412.50, 8));
        stocks.add(new Stock("Amazon.com Inc.", "AMZN", 155.30, 12));
        stocks.add(new Stock("Alphabet Inc.", "GOOGL", 138.40, 10));
        stocks.add(new Stock("Tesla Inc.", "TSLA", 240.10, 6));
        stocks.add(new Stock("NVIDIA Corp.", "NVDA", 560.80, 4));
        stocks.add(new Stock("Meta Platforms Inc.", "META", 350.60, 5));
        stocks.add(new Stock("Netflix Inc.", "NFLX", 610.40, 3));
        stocks.add(new Stock("Coca-Cola Co.", "KO", 61.80, 20));
        stocks.add(new Stock("Johnson & Johnson", "JNJ", 162.30, 7));
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

}
