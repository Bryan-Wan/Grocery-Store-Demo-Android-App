package com.example.grocerystoredemoapp.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grocerystoredemoapp.R;

public class adminFulfilledOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fulfilled_orders);


        Button currentOrdersBtn = (Button) findViewById(R.id.goToCurrent);
        currentOrdersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(adminFulfilledOrders.this, adminCurrentOrders.class));
            }
        });
    }

}