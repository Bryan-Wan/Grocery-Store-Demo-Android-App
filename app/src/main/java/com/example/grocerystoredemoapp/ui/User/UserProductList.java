package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;
import com.example.grocerystoredemoapp.data.model.StoreData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProductList extends AppCompatActivity {
    String itemName;
    String itemBrand;
    String itemPrice;
    Button cartBtn;
    TextView productListStoreName;
    LinearLayout productListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_list);

        productListStoreName = findViewById(R.id.productListStoreName);
        cartBtn = findViewById(R.id.cartBtn);
        productListStoreName.setText("");

        LinearLayout scrollingLayout;
        scrollingLayout = findViewById(R.id.productListLayout);

        View view = getLayoutInflater().inflate(R.layout.activity_user_product_list_product_added_view, null, false);
        scrollingLayout.addView(view);

        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference().child("Product");

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Log.d("testing---------", "testing----------------------------------");
                    Product product = ds.getValue(Product.class);
                    String productName = product.getName();
                    String productBrand = product.getBrand();
                    Double productPrice = product.getPrice();
                    addProduct(productName, productBrand, productPrice);
                    //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,array);
                    //mListView.setAdapter(adapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            // mock data
        itemName = "Amazon Gift Card";
        itemBrand = "Amazon";
        itemPrice = "$50";




        cartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserProductList.this, UserCart.class));
            }
        });
    }

    private void addProduct(String productName, String productBrand, Double productPrice){
        final View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
        Button itemNameBtn = view.findViewById(R.id.productListProductName);
        TextView itemBrandView = view.findViewById(R.id.productListProductBrand);
        TextView itemPriceView = view.findViewById(R.id.productListProductPrice);


        itemNameBtn.setText(productName);
        itemBrandView.setText(productBrand);
        itemPriceView.setText(productPrice.toString());

        productListLayout.addView(view);



        itemNameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserProductList.this, UserProductPage.class));
            }
        });
    }
}