import TradingManagement.Crypto;
import TradingManagement.OwnedTradeables.OwnedCrypto;
import TradingManagement.OwnedTradeables.OwnedTradeable;
import TradingManagement.Tradeable;
import TradingManagement.TradeablesFactory.CryptoFactory;
import TradingManagement.TradeablesFactory.TradeableFactory;
import TradingManagement.TradeablesManagement;
import UserManagement.User;
import UserManagement.UserManagement;

public class Main {
    public static void main(String[] args) {
        /* Patterns to be used and where
        * 1. Singleton - single stock management instance, keeps a list of available stocks/crypto
        *              - single user management instance
        * 2. Factory method - user creation, stocks/crypto creation
        * 3. Abstract Factory
        * 4. Builder
        * 5. Composite
        * 6. Decorator
        * 7. Command
        * 8. Facade - market initialisation
        * 9. Template method
        * 10. Strategy - trading strategy
        * 11. Mediator
        * 12. Observer - notification for price changes
        * */

        TradeableFactory factory = new CryptoFactory();
        Tradeable crypto = factory.createTradeable("Bitcoin","BTC",93.999,2100000);
        System.out.println(crypto);

        TradeablesManagement market = TradeablesManagement.getMarket();
        System.out.println(market.getCryptos());

        User user1 = new User("Andrew","asfa111s");
        User user2 = new User("Joshua","gssssDaaa");
        User user3 = new User("Denis","asd2332");

        UserManagement userManagement = UserManagement.initUsers();
        userManagement.registerUser(user1);
        userManagement.registerUser(user2);
        userManagement.registerUser(user3);
        userManagement.getUsers();

        OwnedTradeable bitcoin = new OwnedCrypto((Crypto)crypto);
        bitcoin.setOwnedQuantity(20);
        System.out.println(bitcoin);

    }
}