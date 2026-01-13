package TradingManagement.Notifications;

import TradingManagement.Tradeable;
import UserManagement.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PriceChangeNotifier {
    public Map<User, List<Tradeable>> subscribers;
    public abstract void notifyPriceChange(String name, double price);

    public PriceChangeNotifier() {
        subscribers = new HashMap<User, List<Tradeable>>();
    }

    public void subscribe(User user){
        List<Tradeable> watchList = new ArrayList<Tradeable>();
        subscribers.put(user,watchList);
    }

    public void unsubscribe(User user){
        subscribers.remove(user);
    }

    public Map<User, List<Tradeable>> getSubscribers(){
        return subscribers;
    }
}
