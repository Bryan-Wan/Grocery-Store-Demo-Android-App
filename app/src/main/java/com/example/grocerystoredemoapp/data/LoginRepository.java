package com.example.grocerystoredemoapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.grocerystoredemoapp.data.model.LoggedInUser;
import com.example.grocerystoredemoapp.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private FirebaseAuth mAuth;

    // TODO: Maybe move these atomic flags to inside the methods they are used in
    private AtomicBoolean isAuthenticating;
    private AtomicBoolean isRegistering;
    private AtomicBoolean isLoadingUserData;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    static LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
        this.isAuthenticating = new AtomicBoolean(false);
        this.isRegistering = new AtomicBoolean(false);
        this.isLoadingUserData = new AtomicBoolean(false);
    }

    public static LoginRepository getInstance(FirebaseAuth mAuth) {
        if (instance == null) {
            instance = new LoginRepository(mAuth);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        FirebaseAuth.getInstance().signOut();
    }

    public static void setLoggedInUser(LoggedInUser user) {
        instance.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }


    public Result<LoggedInUser> register(String username, String password, boolean isAdmin) {
        // handle login
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Register using Firebase Auth
        try {
            // Register using email and password
            isRegistering.set(true);
            Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(executorService, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final String registrationContextTag = "Register";
                            if (task.isSuccessful()) {
                                // Registration success, set the user with the signed-in user's information
                                Log.d(registrationContextTag, "registerWithEmail:success");
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                // Write data from Firebase synchronously
                                setFirebaseUserData(firebaseUser, username, isAdmin);
                            } else {
                                // On registration failure, log it
                                Log.w(registrationContextTag, "registerWithEmail:failure", task.getException());
                                //Toast.makeText(LoginActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                            isRegistering.set(false);
                        }
                    });

            // Wait until authentication is finished before returning
            while (isRegistering.get()) {
                // TODO: Put this in a separate thread and use waits. This infinite while loop is a kludge
            }

            return new Result.Success<LoggedInUser>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error signing up", e));
        }
    }

    private void setFirebaseUserData(FirebaseUser firebaseUser, String email, boolean isAdmin) {
        final String userId = firebaseUser.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("Users").child(userId);
        // TODO: Fix saving of displayName
        /*
        User user = new User(email, isAdmin, "", email);

        userRef.setValue(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Registration", "Failed to write user data to database", e);
                    }
                });

         */
    }
}