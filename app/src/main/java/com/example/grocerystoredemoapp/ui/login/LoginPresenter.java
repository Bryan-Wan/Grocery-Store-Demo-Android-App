package com.example.grocerystoredemoapp.ui.login;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.LoggedInUser;

public class LoginPresenter implements Contract.Presenter {
    private Contract.Model model;
    private Contract.View view;

    public LoginPresenter(Contract.View view, LoginViewModel loginViewModel) {
        this.model = new LoginModel(this, loginViewModel);
        this.view = view;
    }

    public void login(String username, String email) {
        Integer messageString = R.string.logging_in;
        view.showToastMessage(messageString);
        model.login(username, email);
    }

    public void onUserChange(LoggedInUser user) {
        if (user != null) {
            view.startHomePage(user.isAdmin());
        } else {
            // Could not load user, report error
        }
    }

    public void checkUserIsLoggedIn() {
        model.checkUserIsLoggedIn();
    }

    public void showLoginFailed() {
        Integer errorString = R.string.login_failed;
        view.showToastMessage(errorString);
    }
}
