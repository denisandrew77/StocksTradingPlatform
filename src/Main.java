import TradingManagement.MarketInitialisation.MarketInitialiser;
import TradingManagement.Stock;
import TradingManagement.Strategies.StrategyPicker;
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
        * 6. Decorator - owned crypto/stocks
        * 7. Command
        * 8. Facade - market initialisation
        * 9. Template method
        * 10. Strategy - trading strategy
        * 11. Mediator - between user and market
        * 12. Observer
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

        user1.trade(tradingMediator,"Ethereum",2);

        userManagement.viewPortfolio(user1);
        System.out.println(user1.getBalance());
        
        // After creating users and market...

        StrategyPicker strategyPicker = new StrategyPicker();
        strategyPicker.displayAvailableStrategies();

        strategyPicker.setUserStrategy(user1, strategyPicker.getDayTradingStrategy());
        strategyPicker.setUserStrategy(user2, strategyPicker.getLongTermStrategy());
        strategyPicker.setUserStrategy(user3, strategyPicker.getAutoBuyStrategy());

        Stock apple = market.getStockByName("Apple Inc.");
        strategyPicker.buy(user1, 5, apple);
        strategyPicker.buy(user2, 10, apple);

        strategyPicker.configureAutoTradeRule(user3, apple, 180.0, 190.0, 5);

        strategyPicker.checkAutoTrades(user3);

        strategyPicker.endOfDayForAll();
    }
}