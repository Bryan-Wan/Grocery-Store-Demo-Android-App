package com.example.grocerystoredemoapp.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.grocerystoredemoapp.R;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button viewOrdersBtn = (Button) findViewById(R.id.viewOrdersBtn);
        viewOrdersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminHome.this, AdminOrdersPage.class));
            }
        });

        Button editProductsBtn = (Button) findViewById(R.id.editProductsBtn);
        editProductsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminHome.this, AdminMyProducts.class));
            }
        });

        Button editStoreBtn = (Button) findViewById(R.id.editStoreBtn);
        editStoreBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminHome.this, AdminStoreInfo.class));
            }
        });

        ImageButton adminSettingsBtn = (ImageButton) findViewById(R.id.adminSettingsBtn);
        adminSettingsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminHome.this, Settings.class));
            }
        });
    }
}