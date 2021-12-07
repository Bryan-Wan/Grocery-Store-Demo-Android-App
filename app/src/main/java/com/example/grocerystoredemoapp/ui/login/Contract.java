package com.example.grocerystoredemoapp.ui.login;

import androidx.annotation.StringRes;

import com.example.grocerystoredemoapp.data.model.LoggedInUser;

public interface Contract {
    public interface Model{
        public void login(String username, String password);
        public void checkUserIsLoggedIn();
    }

    public interface View{
        public void startHomePage(boolean isAdmin);
        public void showToastMessage(@StringRes Integer errorString);
    }

    public interface Presenter{
        public void onUserChange(LoggedInUser user);
        public void showLoginFailed();
    }
}