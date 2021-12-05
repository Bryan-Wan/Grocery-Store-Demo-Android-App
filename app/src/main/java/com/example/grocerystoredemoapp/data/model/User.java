package com.example.grocerystoredemoapp.data.model;

public class User {
    private String displayName;
    private boolean isAdmin;
    private String Store;
    private String email;

    public User(){}
    public User(String displayName, boolean isAdmin, String Store, String email) {
        this.displayName = displayName;
        this.isAdmin = isAdmin;
        this.Store = Store;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}


