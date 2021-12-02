package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.UserData;
import com.example.grocerystoredemoapp.ui.Admin.Settings;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        UserData userData = new UserData();

        Button newOrderBtn = (Button) findViewById(R.id.userNewOrderBtn);
        newOrderBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(UserHome.this, UserStoreSelection.class));
            }
        });

        Button orderHistoryBtn = (Button) findViewById(R.id.userOrderHistoryBtn);
        orderHistoryBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(UserHome.this, UserHistory.class));
            }
        });

        ImageButton userSettingsBtn = (ImageButton) findViewById(R.id.userSettingsBtn);
        userSettingsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(UserHome.this, Settings.class));
            }
        });
    }
}