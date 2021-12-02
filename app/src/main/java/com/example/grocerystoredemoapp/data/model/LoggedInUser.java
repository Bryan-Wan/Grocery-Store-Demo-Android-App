package com.example.grocerystoredemoapp.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private boolean isAdmin;

    public LoggedInUser(String userId, String displayName, boolean isAdmin) {
        this.userId = userId;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAdmin() { return isAdmin; }
}