package com.example.grocerystoredemoapp.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grocerystoredemoapp.R;

public class AdminOrdersPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders_page);

        Button currentOrdersBtn = (Button) findViewById(R.id.currentOrders);
        currentOrdersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminOrdersPage.this, adminCurrentOrders.class));
            }
        });

        Button fulfilledOrdersBtn = (Button) findViewById(R.id.goFulfilledOrders);
        fulfilledOrdersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminOrdersPage.this, adminFulfilledOrders.class));
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,AdminHome.class);
        startActivity(intent);
    }
}