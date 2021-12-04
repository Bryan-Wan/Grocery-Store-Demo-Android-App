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
import com.example.grocerystoredemoapp.data.model.Product;
import com.example.grocerystoredemoapp.data.model.User;
import com.example.grocerystoredemoapp.ui.Admin.AdminHome;
import com.example.grocerystoredemoapp.ui.User.UserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
                    // TODO: Refactor to use ViewModel like LoginActivity and LoginViewModel
                    Result<LoggedInUser> result = LoginRepository.getInstance(mAuth)
                            .register(username, password, isAdminRegistration);

                    if (result instanceof Result.Success) {
                        LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                        // Go to the appropriate home page

                        String[] getName = username.split("@");
                        User user = new User("", getName[0], isAdminRegistration, "", username);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (result instanceof Result.Success){
                                    Toast.makeText(LoginRegister.this, "User registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(LoginRegister.this, "User failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        if (data.isAdmin()) {
                            startActivity(new Intent(LoginRegister.this, AdminHome.class));
                            finish();
                        } else {
                            startActivity(new Intent(LoginRegister.this, UserHome.class));
                            finish();
                        }
                    }
                }
            }
        });
    }

    // TODO: Switch to appropriate home page

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
}