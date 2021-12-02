package com.example.grocerystoredemoapp.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private boolean isAdmin;

    LoggedInUserView(String displayName, boolean isAdmin) {
        this.displayName = displayName;
        this.isAdmin = isAdmin;
    }

    String getDisplayName() {
        return displayName;
    }

    boolean isAdmin() { return isAdmin; }
}