package TradingManagement.ExchangeProcess;

import TradingManagement.Tradeable;
import UserManagement.User;

public abstract class ExchangeProcess {
    public boolean exchange(User user, Tradeable tradeable, double quantity) {
        double currentBuyingPrice = tradeable.getCurrentPrice();
        if(verifyExchange()){
            regulateAccountValues();
            updateAccountManagement();
            return true;
        }else{
            System.out.println("User " + user.getName() + " does not have enough balance: " + quantity*currentBuyingPrice+" vs "+user.getBalance());
            return false;
        }
    };
    public abstract boolean verifyExchange();
    public abstract void regulateAccountValues();
    public abstract void updateAccountManagement();
}
