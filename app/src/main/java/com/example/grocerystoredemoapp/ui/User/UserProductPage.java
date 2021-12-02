package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserProductPage extends AppCompatActivity {

    FloatingActionButton addQuantity;
    FloatingActionButton subtractQuantity;
    Button addToCart;
    Integer itemQuantity;
    TextView quantityView;
    TextView brandView;
    TextView priceView;
    TextView itemNameView;
    Integer price;
    String itemName;
    String brand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_page);

        //for test purpose
        itemQuantity = 0;
        price = 50;
        itemName = "Amazon Gift Card";
        brand = "Amazon";

        addToCart = findViewById(R.id.addToCartBtn);
        addQuantity = findViewById(R.id.addQuantityBtn);
        subtractQuantity = findViewById(R.id.subtractQuantityBtn);
        quantityView = (TextView) findViewById(R.id.quantity);
        quantityView.setText(itemQuantity.toString());
        brandView = findViewById(R.id.itemBrand);
        brandView.setText(brand);
        priceView = findViewById(R.id.cost);
        priceView.setText(price.toString());
        itemNameView = findViewById(R.id.itemName);
        itemNameView.setText(itemName);


        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemQuantity++;
                quantityView.setText(itemQuantity.toString());
            }
        });

        subtractQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemQuantity > 0){
                    itemQuantity--;
                    quantityView.setText(itemQuantity.toString());
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                UserData userData = new UserData();
                userData.productList.add(itemName);
                userData.quantityList.add(itemQuantity);
                Toast itemAdded = Toast.makeText(getApplicationContext(), "item added", Toast.LENGTH_SHORT);
                itemAdded.setMargin(50, 50);
                itemAdded.show();
                startActivity(new Intent(UserProductPage.this, UserCart.class));

            }
        });

    }
}