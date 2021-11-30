package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grocerystoredemoapp.R;

public class UserWelcomePage extends AppCompatActivity {
    Button placeNewOrder;
    Button orderHistory;
    UserCart userCart;
    static Integer userCartCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(userCartCreated == 0){
            userCart = new UserCart();
            userCartCreated = 1;
        }
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_user);

        placeNewOrder = findViewById(R.id.userNewOrderBtn);
        orderHistory = findViewById(R.id.userOrderHistoryBtn);

        placeNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserWelcomePage.this, UserNewOrderPage.class);
                startActivity(i);

            }
        }
        );

        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserWelcomePage.this, UserHistory.class);
                startActivity(i);
            }
        }
        );

    }
}