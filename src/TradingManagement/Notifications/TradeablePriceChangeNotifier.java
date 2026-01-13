package TradingManagement.Notifications;

import UserManagement.User;

public class TradeablePriceChangeNotifier extends PriceChangeNotifier {
    @Override
    public void notifyPriceChange(String name, double price) {
        for(User user : this.subscribers.keySet()) {
            user.getPriceChangeNotification(price,name);
        }
    }
}
