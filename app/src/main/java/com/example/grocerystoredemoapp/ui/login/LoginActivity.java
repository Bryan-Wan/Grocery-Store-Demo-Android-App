package com.example.grocerystoredemoapp.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.LoginRepository;
import com.example.grocerystoredemoapp.data.Result;
import com.example.grocerystoredemoapp.data.model.LoggedInUser;

import com.example.grocerystoredemoapp.data.model.User;
import com.example.grocerystoredemoapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.grocerystoredemoapp.ui.Admin.AdminHome;
import com.example.grocerystoredemoapp.ui.User.UserHome;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.loginBtn;

        // Handle login input errors
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        // Finish login on success
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }

                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);
            }
        });

        // Check for valid login form data
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Login when user presses "done" or "enter"
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Login when user presses login button
                login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        Button registerBtn = (Button) findViewById(R.id.noAccountRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, LoginRegister.class));
            }
        });

    }

    @Override
    public void onStart() {
        String userID;
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds: snapshot.getChildren()){
                        User user = ds.getValue(User.class);
                        if(ds.getKey().equals(currentUser.getUid())){
                            startHomePage(user.isAdmin());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}

            });
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isUserAdmin = (boolean) dataSnapshot.child("admin").getValue();
                String name = (String) dataSnapshot.child("displayName").getValue();
                Toast.makeText(getApplicationContext(), "Welcome "+ name, Toast.LENGTH_SHORT).show();
                startHomePage(isUserAdmin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void startHomePage(boolean isAdmin) {

        if (isAdmin == true){
            startActivity(new Intent(LoginActivity.this, AdminHome.class));
        }
        else if(!isAdmin){
            startActivity(new Intent(LoginActivity.this, UserHome.class));
        }
        else{
            Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
        }

        finish(); // Prevent going back to the login page when pressing back
    }

    private void login(String username, String password) {
        // handle login
        ExecutorService executorService = Executors.newSingleThreadExecutor();

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
                            Toast.makeText(getApplicationContext(), "Login failed.", Toast.LENGTH_SHORT).show();
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

                // Set user data
                // TODO: Save the loaded user
                LoggedInUser loggedInUser = new LoggedInUser(
                        userId,
                        user.getDisplayName(),
                        user.isAdmin()
                );

                // Use loginViewModel to set loginResult
                loginViewModel.setLoggedInUser(loggedInUser);
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }
}