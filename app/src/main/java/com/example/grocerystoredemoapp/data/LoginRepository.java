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
    private LoggedInUser user = null;

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

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Authenticate using Firebase Auth
        try {
            // Sign in using email and password
            isAuthenticating.set(true);
            Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(executorService, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final String loginActivityTag = "Login";
                            if (task.isSuccessful()) {
                                // Sign in success, set the user with the signed-in user's information
                                Log.d(loginActivityTag, "signInWithEmail:success");
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                // Load data from Firebase synchronously
                                Log.d(loginActivityTag, "Loading user data");
                                getFirebaseUserData(firebaseUser);
                                Log.d(loginActivityTag, "User data loaded");
                            } else {
                                // On sign in failure, log it
                                Log.w(loginActivityTag, "signInWithEmail:failure", task.getException());
                                //Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                            isAuthenticating.set(false);
                        }
                    });

            // Wait until authentication is finished before returning
            while (isAuthenticating.get()) {
                // TODO: Put this in a separate thread and use waits. This infinite while loop is a kludge
            }

            return new Result.Success<LoggedInUser>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
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

    private void getFirebaseUserData(FirebaseUser firebaseUser) {
        final String userId = firebaseUser.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        // Keep the user data synced at least for login
        userRef.keepSynced(true);
        // Block the thread
        isLoadingUserData.set(true);

        Log.d("getFirebaseUserData", "Setting up listener for user data");
        // TODO: Put constants all in one file for styling and in case we want to change the names in the database
        // TODO: Use class to model database to organize and reuse database access code
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    // Successfully loaded user data
                    //Log.d("firebase", String.valueOf(snapshot.getValue()));
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    Log.d("getFirebaseUserData", "Getting user data from database");
                    //User user = snapshot.getValue(User.class);
                    User user = task.getResult().getValue(User.class);
                    Log.d("getFirebaseUserData", "Saving user data");

                    // Set user data
                    // TODO: Save the loaded user
                    setLoggedInUser(new LoggedInUser(
                            userId,
                            user.getDisplayName(),
                            user.isAdmin()
                    ));

                    // Unblock
                    isLoadingUserData.set(false);
                }
            }
        });

        Log.d("getFirebaseUserData", "Blocking until user data is loaded");
        while (isLoadingUserData.get()) {
            // TODO: Use either a new thread or thread that did authentication then signal when loading is done
        }

        return;
    }

    private void setFirebaseUserData(FirebaseUser firebaseUser, String email, boolean isAdmin) {
        final String userId = firebaseUser.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("Users").child(userId);
        User user = new User("", email, isAdmin, "", email, "");

        userRef.setValue(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Registration", "Failed to write user data to database", e);
                    }
                });
    }
}