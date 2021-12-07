package com.example.grocerystoredemoapp.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.grocerystoredemoapp.data.model.LoggedInUser;
import com.example.grocerystoredemoapp.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel implements Contract.Model {
    private Contract.Presenter presenter;

    private FirebaseAuth mAuth; // TODO: Clean up LoginRepository and LoginModel

    public LoginModel(LoginPresenter presenter) {
        mAuth = FirebaseAuth.getInstance();
        // TODO: Clean up LoginViewModel and LoginModel
        this.presenter = presenter;
    }

    public void login(String username, String password) {
        // Sign in using email and password with Firebase Auth
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // TODO: Revise these strings and move them to strings.xml
                        // TODO: Clean up debug messages
                        final String loginActivityTag = "Login";
                        if (task.isSuccessful()) {
                            // Sign in success, set the user with the signed-in user's information
                            Log.d(loginActivityTag, "signInWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            // Load data from Firebase synchronously
                            getFirebaseUserData(firebaseUser);
                        } else {
                            // On sign in failure, log it
                            Log.w(loginActivityTag, "signInWithEmail:failure", task.getException());
                            presenter.showLoginFailed();
                        }
                    }
                });
    }

    private void getFirebaseUserData(FirebaseUser firebaseUser) {
        final String userId = firebaseUser.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        // TODO: Put constants all in one file for styling and in case we want to change the names in the database
        // TODO: Use class to model database to organize and reuse database access code
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Successfully loaded user data
                Log.d("firebase", String.valueOf(dataSnapshot.getValue()));

                //User user = snapshot.getValue(User.class);
                User user = dataSnapshot.getValue(User.class);
                // Notify presenter on user change
                presenter.onUserChange(user, userId);
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void checkUserIsLoggedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            // Load user data from database to check if they are an admin
            DatabaseReference myRef = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Users")
                    .child(userId)
                    .getRef();

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    // Notify presenter on user change
                    presenter.onUserChange(user, userId);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }

    }
}
