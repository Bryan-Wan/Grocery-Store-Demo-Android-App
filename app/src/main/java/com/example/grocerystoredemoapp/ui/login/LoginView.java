package com.example.grocerystoredemoapp.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;

import com.example.grocerystoredemoapp.databinding.ActivityLoginBinding;

import com.example.grocerystoredemoapp.ui.Admin.AdminHome;
import com.example.grocerystoredemoapp.ui.User.UserHome;

public class LoginView extends AppCompatActivity implements Contract.View {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        presenter = new LoginPresenter(this, loginViewModel);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                    showToastMessage(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    presenter.checkUserIsLoggedIn();
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
                    presenter.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Login when user presses login button
                presenter.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        Button registerBtn = (Button) findViewById(R.id.noAccountRegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(LoginView.this, LoginRegister.class));
            }
        });

    }

    @Override
    public void onStart() {
        String userID;
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        presenter.checkUserIsLoggedIn();
    }

    public void showToastMessage(@StringRes Integer messageString) {
        Toast.makeText(getApplicationContext(), messageString, Toast.LENGTH_SHORT).show();
    }

    public void startHomePage(boolean isAdmin) {
        if (isAdmin == true){
            startActivity(new Intent(LoginView.this, AdminHome.class));
        }
        else if(!isAdmin){
            startActivity(new Intent(LoginView.this, UserHome.class));
        }
        else{
            Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
        }

        finish(); // Prevent going back to the login page when pressing back
    }
}