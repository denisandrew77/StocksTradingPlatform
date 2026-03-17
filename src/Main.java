import TradingManagement.MarketInitialisation.MarketInitialiser;
import TradingManagement.Stock;
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
        * 3. Decorator - owned crypto/stocks
        * 4. Facade - market initialisation
        * 5. Strategy - trading strategy
        * 6. Mediator - between user and market
        * */

        MarketInitialiser marketInitialiser = new MarketInitialiser();
        TradeablesManagement market = marketInitialiser.initialise();

        UserManagement userManagement = UserManagement.initUsers();

        TradingMediator tradingMediator = new TradingMediator(userManagement,market);

        User user1 = new User("Andrew","asfa111s",tradingMediator);
        User user2 = new User("Joshua","gssssDaaa",tradingMediator);
        User user3 = new User("Denis","asd2332",tradingMediator);

        user1.setBalance(140000);
        user2.setBalance(20000);
        user3.setBalance(100000);


        userManagement.registerUser(user1);
        userManagement.registerUser(user2);
        userManagement.registerUser(user3);
        userManagement.getUsers();

        userManagement.logIn(user1);

        userManagement.viewPortfolio(user1);

        //user1.trade(tradingMediator,"Ethereum",2);
        user1.trade(tradingMediator,"Tesla",1);

        userManagement.viewPortfolio(user1);
        System.out.println(user1.getBalance());
        
    }
}