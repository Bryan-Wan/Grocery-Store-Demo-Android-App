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
            Integer quantity = userData.quantityList.get(i);
            final View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
            TextView productName = (TextView)findViewById(R.id.item);
            TextView productQuantity = (TextView)findViewById(R.id.productQuantity);
            TextView productBrand = (TextView)findViewById(R.id.productBrand);
            TextView productPrice = (TextView)findViewById(R.id.productPrice);
            productName.setText(userData.productList.get(i));
            productQuantity.setText(userData.quantityList.get(i).toString());
            productBrand.setText(userData.brandList.get(i));
            productPrice.setText(userData.priceList.get(i).toString());

            scrollingLayout.addView(view);
            i++;
        }
    }


}