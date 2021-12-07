package com.example.grocerystoredemoapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.LoginRepository;
import com.example.grocerystoredemoapp.data.Result;
import com.example.grocerystoredemoapp.data.model.LoggedInUser;
import com.example.grocerystoredemoapp.data.model.User;
import com.example.grocerystoredemoapp.ui.Admin.AdminHome;
import com.example.grocerystoredemoapp.ui.User.UserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginRegister extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        mAuth = FirebaseAuth.getInstance();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText confirmPasswordEditText = findViewById(R.id.confirmPassword);
        final CheckBox adminCheckBox = findViewById(R.id.adminCheckBox);
        final Button registerBtn = findViewById(R.id.registerBtn);

        // Registration process
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                boolean isAdminRegistration = adminCheckBox.isChecked();

                // Validate username
                if (!isUserNameValid(username)) {
                    String invalidUsernameMessage = getString(R.string.invalid_username);
                    Toast.makeText(getApplicationContext(), invalidUsernameMessage, Toast.LENGTH_LONG).show();
                } else if (!isPasswordValid(password)) {
                    String invalidPasswordMessage = getString(R.string.invalid_password);
                    Toast.makeText(getApplicationContext(), invalidPasswordMessage, Toast.LENGTH_LONG).show();
                } else if (!password.equals(confirmPassword)) {
                    String passwordNotMatchingMessage = getString(R.string.password_does_not_match);
                    Toast.makeText(getApplicationContext(), passwordNotMatchingMessage, Toast.LENGTH_LONG).show();
                } else {
                    // Register the user then start the home page
                    register(username, password, isAdminRegistration);
                }
            }
        });
    }
    // TODO: Refactor to use same validation as LoginViewModel
    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return false;
        }
    }
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private void updateUIOnRegister(User user) {
        // Go to the appropriate home page
        if (user.isAdmin()) {
            startActivity(new Intent(LoginRegister.this, AdminHome.class));
        } else {
            startActivity(new Intent(LoginRegister.this, UserHome.class));
        }
        finish();
    }

    private void register(String username, String password, boolean isAdmin) {
        // Register using email and password using Firebase Auth
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(LoginRegister.this, new OnCompleteListener<AuthResult>() {
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
                            Toast.makeText(getApplicationContext(), "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setFirebaseUserData(FirebaseUser firebaseUser, String email, boolean isAdmin) {
        final String userId = firebaseUser.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("Users").child(userId);
        // TODO: Fix saving of displayName
        User user = new User(email, isAdmin, null, email);
        LoggedInUser loggedInUser = new LoggedInUser(
                userId,
                user.getDisplayName(),
                user.isAdmin()
        );

        // Save new user to database
        userRef.setValue(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "User registration failed", Toast.LENGTH_SHORT).show();
                        Log.e("Registration", "Failed to write user data to database", e);
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                        // Save user in memory then go to homepage
                        LoginRepository.getInstance().setLoggedInUser(loggedInUser);
                        updateUIOnRegister(user);
                    }
                });
    }
}