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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        placeNewOrder = findViewById(R.id.placeOrder);
        orderHistory = findViewById(R.id.orderHistory);

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
                Intent i = new Intent(UserWelcomePage.this, UserOrderHistory.class);
                startActivity(i);
            }
        }
        );

}
}