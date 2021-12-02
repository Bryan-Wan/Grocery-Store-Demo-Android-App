package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;

public class UserStoreSelection extends AppCompatActivity {
    String storeNameText;
    String storeAddressText;
    Button storeNameBtn;
    TextView storeAddressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_store_selection);




        LinearLayout scrollingLayout;
        scrollingLayout = findViewById(R.id.storeScrollLayout);

        View view = getLayoutInflater().inflate(R.layout.activity_user_store_added_view, null, false);
        scrollingLayout.addView(view);

        //mock data
        storeNameText = "Tim's Shop";
        storeAddressText = "1234 Yonge St";
        storeNameBtn = findViewById(R.id.storeName);
        storeAddressView = findViewById(R.id.storeAddress);
        storeNameBtn.setText(storeNameText);
        storeAddressView.setText(storeAddressText);

        storeNameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                UserData userData = new UserData(itemName, itemQuantity);
                startActivity(new Intent(UserStoreSelection.this, UserProductList.class));
            }
        });

    }
}