package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.grocerystoredemoapp.R;

import java.util.ArrayList;

public class UserCart extends AppCompatActivity {
    LinearLayout scrollingLayout;
    Button purchase;
    static ArrayList<String> productList;
    static ArrayList<Integer> quantityList;

    protected UserCart(){
        productList = new ArrayList<String>();
        quantityList = new ArrayList<Integer>();
    }

    protected UserCart(String product, Integer quantity){
        productList.add(product);
        quantityList.add(quantity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        scrollingLayout = findViewById(R.id.userCartScrollLayout);
        purchase = findViewById(R.id.purchaseBtn);
        View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
        scrollingLayout.addView(view);






    }


}