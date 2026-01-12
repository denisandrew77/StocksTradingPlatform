package TradingManagement.TradingProcess;

import TradingManagement.Crypto;
import TradingManagement.OwnedTradeables.OwnedCrypto;
import TradingManagement.OwnedTradeables.OwnedStock;
import TradingManagement.Stock;
import TradingManagement.TradeablesManagement;
import UserManagement.User;
import UserManagement.UserManagement;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class TradingMediator implements BaseTradingMediator{
    private UserManagement userManagement;
    private TradeablesManagement tradeablesManagement;

    public TradingMediator(UserManagement userManagement, TradeablesManagement tradeablesManagement){
        this.userManagement = userManagement;
        this.tradeablesManagement = tradeablesManagement;
    }

    private boolean checkStocksTradeTime(){
        LocalDateTime now = LocalDateTime.now();
        if(now.getHour()>9 && now.getHour()<18 && now.getDayOfWeek()!=DayOfWeek.SATURDAY && now.getDayOfWeek()!=DayOfWeek.SUNDAY){
            return true;
        }
        return false;
    }

    @Override
    public boolean trade(User user, String tradeableName, double quantity) {
        if(userManagement.verifyUser(user.getName(),user.getPassword()) && user.isLoggedIn()) {
            Stock stock = tradeablesManagement.getStockByName(tradeableName);
            Crypto crypto = tradeablesManagement.getCryptoByName(tradeableName);
            if(crypto!=null){
                double currentBuyingPrice = crypto.getCurrentPrice();
                if(quantity*currentBuyingPrice <= user.getBalance() && quantity<=crypto.getQuantity()){
                    OwnedCrypto ownedCrypto = new OwnedCrypto(crypto);
                    ownedCrypto.setOwnedQuantity(quantity);
                    user.setBalance(user.getBalance()-quantity*currentBuyingPrice);
                    userManagement.addTradeable(user,ownedCrypto);
                    tradeablesManagement.updateCryptoQuantity(crypto,quantity);
                }
                else{
                    System.out.println("User " + user.getName() + " does not have enough balance: " + quantity*currentBuyingPrice+" vs "+user.getBalance());
                    return false;
                }
            } else if(stock!=null && this.checkStocksTradeTime()){
                double currentBuyingPrice = stock.getCurrentPrice();
                if(quantity*currentBuyingPrice <= user.getBalance()){
                    OwnedStock ownedStock = new OwnedStock(stock);
                    ownedStock.setOwnedQuantity(quantity);
                    user.setBalance(user.getBalance()-quantity*currentBuyingPrice);
                    userManagement.addTradeable(user,ownedStock);
                    tradeablesManagement.updateStockQuantity(stock,quantity);
                }
                else{
                    System.out.println("User " + user.getName() + " does not have enough balance");
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public void tradeForMoney(User user, String tradeableName, double quantity){

    }

}
