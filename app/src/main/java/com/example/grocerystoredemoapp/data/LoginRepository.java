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
    private static LoggedInUser user = null;

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
}