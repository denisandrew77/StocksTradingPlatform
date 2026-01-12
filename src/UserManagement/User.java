package UserManagement;

import TradingManagement.Crypto;
import TradingManagement.OwnedTradeables.OwnedCrypto;
import TradingManagement.OwnedTradeables.OwnedTradeable;
import TradingManagement.Tradeable;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private double balance;
    private boolean loggedIn;
    private List<OwnedTradeable> portfolio = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.balance = 0;
        this.loggedIn = false;
    }

    void addTradeable(OwnedTradeable tradeable){
        portfolio.add(tradeable);
    }

    public List<OwnedTradeable> getPortfolio() {
        return portfolio;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString(){
        return name;
    }
}
