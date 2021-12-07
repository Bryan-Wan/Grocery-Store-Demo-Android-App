package com.example.grocerystoredemoapp.ui.login;

import androidx.annotation.StringRes;

import com.example.grocerystoredemoapp.data.model.LoggedInUser;
import com.example.grocerystoredemoapp.data.model.User;

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
        public void onUserChange(User user, String userId);
        public void showLoginFailed();
    }
}