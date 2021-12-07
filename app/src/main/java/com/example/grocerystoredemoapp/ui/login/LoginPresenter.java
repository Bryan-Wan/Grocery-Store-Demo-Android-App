package com.example.grocerystoredemoapp.ui.login;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.LoggedInUser;
import com.example.grocerystoredemoapp.data.model.User;

public class LoginPresenter implements Contract.Presenter {
    private Contract.Model model;
    private Contract.View view;

    // Helper class for validating the data
    private LoginViewModel loginViewModel;

    public LoginPresenter(Contract.View view, LoginViewModel loginViewModel) {
        this.model = new LoginModel(this);
        this.view = view;
        this.loginViewModel = loginViewModel;
    }

    public void login(String username, String email) {
        Integer messageString = R.string.logging_in;
        view.showToastMessage(messageString);
        model.login(username, email);
    }

    public void onUserChange(User user, String userId) {
        if (user != null) {
            // Set user data
            LoggedInUser loggedInUser = new LoggedInUser(
                    userId,
                    user.getDisplayName(),
                    user.isAdmin()
            );
            // Use loginViewModel to set loginResult
            loginViewModel.setLoggedInUser(loggedInUser);
            // Start the home page through the view
            view.startHomePage(user.isAdmin());
        } else {
            // Could not load user, report error
            // TODO: Log the error
        }
    }

    public void checkUserIsLoggedIn() {
        Integer messageString = R.string.checking_login_status;
        view.showToastMessage(messageString);
        model.checkUserIsLoggedIn();
    }

    public void showLoginFailed() {
        Integer errorString = R.string.login_failed;
        view.showToastMessage(errorString);
    }
}
