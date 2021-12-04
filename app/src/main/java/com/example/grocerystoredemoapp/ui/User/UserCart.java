package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.OrderData;


public class UserCart extends AppCompatActivity {
    LinearLayout scrollingLayout;
    Button purchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        scrollingLayout = findViewById(R.id.userCartScrollLayout);
        purchase = findViewById(R.id.purchaseBtn);
        addProduct();

        purchase = findViewById(R.id.purchaseBtn);
        purchase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserCart.this, UserConfirmationPage.class));
            }
        });
    }

    private void addProduct(){
        OrderData userData = new OrderData();
        Integer i = 0;
        while(i < userData.productList.size()){
            final View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
            TextView productName = view.findViewById(R.id.item);
            TextView productQuantity = view.findViewById(R.id.productQuantity);
            TextView productBrand = view.findViewById(R.id.productBrand);
            TextView productPrice = view.findViewById(R.id.productPrice);
            productName.setText(userData.productList.get(i));
            productQuantity.setText(userData.quantityList.get(i).toString());
            productBrand.setText(userData.brandList.get(i));
            productPrice.setText(userData.priceList.get(i).toString());

            scrollingLayout.addView(view);
            i++;
        }
    }


}