package UserManagement;

import TradingManagement.OwnedTradeables.OwnedTradeable;

import java.util.ArrayList;

public class UserManagement {
    private ArrayList<User> users;
    private static UserManagement userManagement;
    private UserManagement() {
        this.users = new ArrayList<>();
    }

    public static UserManagement initUsers() {
        if (userManagement == null) {
            userManagement = new UserManagement();
        }
        return userManagement;
    }

    public UserManagement getUserManagement() {
        return userManagement;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void registerUser(User user) {
        users.add(user);
        System.out.println("User " + user.getName() + " registered successfully");
    }

    public boolean verifyUser(String username, String password) {
        for(User user : users) {
            if(username.equals(user.getName()) && password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public void logIn(User user) {
        if(verifyUser(user.getName(),user.getPassword())) {
            user.setLoggedIn(true);
            System.out.println("User " + user.getName() + " logged in successfully");
        }else{
            System.out.println("Invalid username or password");
        }
    }

    public void viewPortfolio(User user) {
        System.out.println(user+"'s Portfolio:"+"\n");
        if(user.isLoggedIn()) {
            for(User u: users) {
                if(user.getName().equals(u.getName())) {
                    for(OwnedTradeable tradeable : user.getPortfolio()) {
                        System.out.println(tradeable);
                    }
                }
            }
        }else{
            System.out.println("User " + user.getName() + " is not logged in");
        }
    }

    public void addTradeable(User user,OwnedTradeable tradeable){
        if(user.isLoggedIn()) {
            for(User u: users) {
                if(user.getName().equals(u.getName())) {
                    System.out.println(tradeable.toString());
                    u.getPortfolio().add(tradeable);
                }
            }
        }
    }
}
