package com.example.grocerystoredemoapp.ui.login;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.grocerystoredemoapp.data.model.LoggedInUser;
import com.example.grocerystoredemoapp.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginPresenter implements Contract.Presenter {
    private Contract.Model model;
    private Contract.View view;

    public LoginPresenter(Contract.View view) {
        this.model = new LoginModel(this);
        this.view = view;
    }

    public void login(String username, String email) {
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

}
