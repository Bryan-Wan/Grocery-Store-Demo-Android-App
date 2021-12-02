package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grocerystoredemoapp.R;

public class UserConfirmationPage extends AppCompatActivity {

    Button placeOrder;
    Button backToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_confirmation_page);

        placeOrder = findViewById(R.id.placeOrderBtn);
        backToCart = findViewById(R.id.backToCartBtn);

        placeOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserConfirmationPage.this, UserOrderThankYouPage.class));
            }
        });

        backToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserConfirmationPage.this, UserCart.class));
            }
        });
    }
}