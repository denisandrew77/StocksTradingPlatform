package TradingManagement.MarketInitialisation;

import TradingManagement.Crypto;
import TradingManagement.Stock;
import TradingManagement.TradeablesFactory.CryptoFactory;
import TradingManagement.TradeablesFactory.StocksFactory;
import TradingManagement.TradeablesManagement;

public class MarketInitialiser {
    private CryptoFactory cryptoFactory;
    private StocksFactory stockFactory;
    public MarketInitialiser(){
        cryptoFactory = new CryptoFactory();
        stockFactory = new StocksFactory();
    }

    public TradeablesManagement initialise(){
        /// create cryptos
        Crypto bitcoin     = (Crypto) cryptoFactory.createTradeable("Bitcoin", "BTC", 44646.00, 1000);
        Crypto ethereum    = (Crypto) cryptoFactory.createTradeable("Ethereum", "ETH", 2300.00, 1000);
        Crypto bnb         = (Crypto) cryptoFactory.createTradeable("Binance Coin", "BNB", 310.00, 522.00);
        Crypto solana      = (Crypto) cryptoFactory.createTradeable("Solana", "SOL", 98.50, 12.00);
        Crypto cardano     = (Crypto) cryptoFactory.createTradeable("Cardano", "ADA", 0.52, 1000.00);
        Crypto xrp         = (Crypto) cryptoFactory.createTradeable("XRP", "XRP", 0.62, 800.00);
        Crypto polkadot    = (Crypto) cryptoFactory.createTradeable("Polkadot", "DOT", 6.80, 90.00);
        Crypto dogecoin    = (Crypto) cryptoFactory.createTradeable("Dogecoin", "DOGE", 0.08, 2500.00);
        Crypto avalanche   = (Crypto) cryptoFactory.createTradeable("Avalanche", "AVAX", 36.40, 40.00);
        Crypto chainlink   = (Crypto) cryptoFactory.createTradeable("Chainlink", "LINK", 14.20, 75.00);
        /// create stocks
        Stock apple       = (Stock) stockFactory.createTradeable("Apple Inc.", "AAPL", 185.20, 15);
        Stock microsoft   = (Stock) stockFactory.createTradeable("Microsoft Corp.", "MSFT", 412.50, 8);
        Stock amazon      = (Stock) stockFactory.createTradeable("Amazon.com Inc.", "AMZN", 155.30, 12);
        Stock alphabet    = (Stock) stockFactory.createTradeable("Alphabet Inc.", "GOOGL", 138.40, 10);
        Stock tesla       = (Stock) stockFactory.createTradeable("Tesla Inc.", "TSLA", 240.10, 6);
        Stock nvidia      = (Stock) stockFactory.createTradeable("NVIDIA Corp.", "NVDA", 560.80, 4);
        Stock meta        = (Stock) stockFactory.createTradeable("Meta Platforms Inc.", "META", 350.60, 5);
        Stock netflix     = (Stock) stockFactory.createTradeable("Netflix Inc.", "NFLX", 610.40, 3);
        Stock cocaCola    = (Stock) stockFactory.createTradeable("Coca-Cola Co.", "KO", 61.80, 20);
        Stock jnj         = (Stock) stockFactory.createTradeable("Johnson & Johnson", "JNJ", 162.30, 7);

        /// get the market (singleton)
        TradeablesManagement market = TradeablesManagement.getMarket();

        /// add tradeables to market
        market.addCrypto(bitcoin);
        market.addCrypto(ethereum);
        market.addCrypto(bnb);
        market.addCrypto(solana);
        market.addCrypto(cardano);
        market.addCrypto(xrp);
        market.addCrypto(polkadot);
        market.addCrypto(dogecoin);
        market.addCrypto(avalanche);
        market.addCrypto(chainlink);

        market.addStock(apple);
        market.addStock(microsoft);
        market.addStock(amazon);
        market.addStock(alphabet);
        market.addStock(tesla);
        market.addStock(nvidia);
        market.addStock(meta);
        market.addStock(netflix);
        market.addStock(cocaCola);
        market.addStock(jnj);

        return market;
    }

}
