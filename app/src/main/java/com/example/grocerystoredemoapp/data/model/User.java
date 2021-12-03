package com.example.grocerystoredemoapp.data.model;

public class User {
    private String cart;
    private String displayName;
    private boolean isAdmin;
    private String Store;
    private String email;
    private String password;

    public User(){};
    public User(String cart, String displayName, boolean isAdmin, String Store, String email, String password) {
        this.cart = cart;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
        this.Store = Store;
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}


