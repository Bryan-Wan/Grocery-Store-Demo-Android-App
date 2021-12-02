package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;

public class UserProductList extends AppCompatActivity {
    String itemName;
    String itemBrand;
    String itemPrice;
    Button itemNameBtn;
    Button cartBtn;
    TextView itemBrandView;
    TextView itemPriceView;
    TextView productListStoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_list);

        LinearLayout scrollingLayout;
        scrollingLayout = findViewById(R.id.productListLayout);

        View view = getLayoutInflater().inflate(R.layout.activity_user_product_list_product_added_view, null, false);
        scrollingLayout.addView(view);

        // mock data
        itemName = "Amazon Gift Card";
        itemBrand = "Amazon";
        itemPrice = "$50";

        itemNameBtn = findViewById(R.id.productListProductName);
        itemBrandView = findViewById(R.id.productListProductBrand);
        itemPriceView = findViewById(R.id.productListProductPrice);
        productListStoreName = findViewById(R.id.productListStoreName);
        cartBtn = findViewById(R.id.cartBtn);

        itemNameBtn.setText(itemName);
        itemBrandView.setText(itemBrand);
        itemPriceView.setText(itemPrice);
        productListStoreName.setText("Tim's shop");

        itemNameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserProductList.this, UserProductPage.class));
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserProductList.this, UserCart.class));
            }
        });
    }



}