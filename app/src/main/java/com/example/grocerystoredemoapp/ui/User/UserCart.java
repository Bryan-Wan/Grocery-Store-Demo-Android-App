package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.UserData;


public class UserCart extends AppCompatActivity {
    LinearLayout scrollingLayout;
    Button purchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        scrollingLayout = findViewById(R.id.userCartScrollLayout);
        final View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
        scrollingLayout.addView(view);
        purchase = findViewById(R.id.purchaseBtn);
        addProduct();
    }

    private void addProduct(){
        UserData userData = new UserData();
        Integer i = 0;
        while(i < userData.productList.size()){
            String name = userData.productList.get(i);
            final View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
            TextView Name = (TextView)findViewById(R.id.item);
            Name.setText(userData.productList.get(i));
            scrollingLayout.addView(view);
            i++;
        }
    }


}