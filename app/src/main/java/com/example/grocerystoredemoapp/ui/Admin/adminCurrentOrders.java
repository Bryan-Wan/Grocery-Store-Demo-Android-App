package com.example.grocerystoredemoapp.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grocerystoredemoapp.R;

public class adminCurrentOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_current_orders);

        Button fulfilledOrdersBtn = (Button) findViewById(R.id.goToFulfilled);
        fulfilledOrdersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(adminCurrentOrders.this, adminFulfilledOrders.class));
            }
        });
    }
}