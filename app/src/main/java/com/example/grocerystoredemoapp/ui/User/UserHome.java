package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.OrderData;
import com.example.grocerystoredemoapp.ui.Admin.Settings;

public class UserHome extends AppCompatActivity {
    Button cartBtn_homePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        OrderData userData = new OrderData();


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
        cartBtn_homePage = findViewById(R.id.cartBtn_homePage);
        cartBtn_homePage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserHome.this, UserCart.class));
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }
}