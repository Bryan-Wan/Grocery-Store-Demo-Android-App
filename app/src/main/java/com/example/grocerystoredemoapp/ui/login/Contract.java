package com.example.grocerystoredemoapp.ui.login;

import com.example.grocerystoredemoapp.data.model.LoggedInUser;

public interface Contract {
    public interface Model{
        public void login(String username, String password);
        public void checkUserIsLoggedIn();
    }

    public interface View{
        public void startHomePage(boolean isAdmin);
    }

    public interface Presenter{
        public void onUserChange(LoggedInUser user);
    }
}