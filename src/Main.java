import TradingManagement.Crypto;
import TradingManagement.OwnedTradeables.OwnedCrypto;
import TradingManagement.OwnedTradeables.OwnedTradeable;
import TradingManagement.Stock;
import TradingManagement.Tradeable;
import TradingManagement.TradeablesFactory.CryptoFactory;
import TradingManagement.TradeablesFactory.StocksFactory;
import TradingManagement.TradeablesFactory.TradeableFactory;
import TradingManagement.TradeablesManagement;
import TradingManagement.TradingProcess.TradingMediator;
import UserManagement.User;
import UserManagement.UserManagement;

public class Main {
    public static void main(String[] args) {
        /* Patterns to be used and where
        * 1. Singleton - single stock management instance, keeps a list of available stocks/crypto
        *              - single user management instance
        * 2. Factory method - stocks/crypto creation
        * 3. Abstract Factory
        * 4. Builder
        * 5. Composite
        * 6. Decorator
        * 7. Command
        * 8. Facade - market initialisation
        * 9. Template method
        * 10. Strategy - trading strategy
        * 11. Mediator - between user and market
        * 12. Observer - notification for price changes
        * */

        TradeableFactory factory = new CryptoFactory();
        TradeableFactory stockFactory = new StocksFactory();
        Tradeable crypto = factory.createTradeable("Bitcoin","BTC",93.999,2100000);
        ///
        Crypto bitcoin     = (Crypto) factory.createTradeable("Bitcoin", "BTC", 44646.00, 1000);
        Crypto ethereum    = (Crypto) factory.createTradeable("Ethereum", "ETH", 2300.00, 1000);
        Crypto bnb         = (Crypto) factory.createTradeable("Binance Coin", "BNB", 310.00, 522.00);
        Crypto solana      = (Crypto) factory.createTradeable("Solana", "SOL", 98.50, 12.00);
        Crypto cardano     = (Crypto) factory.createTradeable("Cardano", "ADA", 0.52, 1000.00);
        Crypto xrp         = (Crypto) factory.createTradeable("XRP", "XRP", 0.62, 800.00);
        Crypto polkadot    = (Crypto) factory.createTradeable("Polkadot", "DOT", 6.80, 90.00);
        Crypto dogecoin    = (Crypto) factory.createTradeable("Dogecoin", "DOGE", 0.08, 2500.00);
        Crypto avalanche   = (Crypto) factory.createTradeable("Avalanche", "AVAX", 36.40, 40.00);
        Crypto chainlink   = (Crypto) factory.createTradeable("Chainlink", "LINK", 14.20, 75.00);
        ///
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

        System.out.println(crypto);

        TradeablesManagement market = TradeablesManagement.getMarket();
        System.out.println(market.getCryptos());

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

        User user1 = new User("Andrew","asfa111s");
        User user2 = new User("Joshua","gssssDaaa");
        User user3 = new User("Denis","asd2332");

        user1.setBalance(140000);
        user2.setBalance(20000);
        user3.setBalance(100000);

        UserManagement userManagement = UserManagement.initUsers();
        userManagement.registerUser(user1);
        userManagement.registerUser(user2);
        userManagement.registerUser(user3);
        userManagement.getUsers();

        userManagement.logIn(user1);

        userManagement.viewPortfolio(user1);

        TradingMediator tradingMediator = new TradingMediator(userManagement,market);
        tradingMediator.trade(user1, "Ethereum",2);

        userManagement.viewPortfolio(user1);

    }
}