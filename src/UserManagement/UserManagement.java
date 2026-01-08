package UserManagement;

import TradingManagement.OwnedTradeables.OwnedTradeable;

import java.util.ArrayList;

public class UserManagement {
    private ArrayList<User> users;
    private static UserManagement userManagement = new UserManagement();
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

    public void logInUser(String username, String password) {
        for(User user : users) {
            if(username.equals(user.getName()) && password.equals(user.getPassword())) {
                user.setLoggedIn(true);
                System.out.println(user.getName() + " logged in successfully");
            }
        }
    }

    public void viewPortfolio(User user) {
        if(user.isLoggedIn()) {
            for(User u: users) {
                if(user.getName().equals(u.getName()) && user.getPassword().equals(u.getPassword())) {
                    for(OwnedTradeable tradeable : user.getPortfolio()) {
                        System.out.println(tradeable);
                    }
                }
            }
        }else{
            System.out.println("User " + user.getName() + " is not logged in");
        }
    }
}
