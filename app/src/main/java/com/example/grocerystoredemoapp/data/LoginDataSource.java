package com.example.grocerystoredemoapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.grocerystoredemoapp.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password, FirebaseAuth mAuth) {

        try {
            // Sign in using email and password
            Task<AuthResult> authTask = mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final String loginActivityTag = "Login";
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(loginActivityTag, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(loginActivityTag, "signInWithEmail:failure", task.getException());
                                //Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            AuthResult authResult = authTask.getResult();
            // TODO: Better handling of null authResult

            FirebaseUser firebaseUser = authResult.getUser();
            LoggedInUser user = null;

            // Get user info if the user is signed in
            if (firebaseUser != null) {
                user = new LoggedInUser(
                        firebaseUser.getUid(),
                        firebaseUser.getDisplayName()
                    );
            }

            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}