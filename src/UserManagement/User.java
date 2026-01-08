package UserManagement;

import TradingManagement.OwnedTradeables.OwnedTradeable;
import TradingManagement.Tradeable;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private boolean loggedIn;
    private List<OwnedTradeable> portfolio = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.loggedIn = false;
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

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
