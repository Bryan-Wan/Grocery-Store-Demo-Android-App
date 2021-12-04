package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;
import com.example.grocerystoredemoapp.data.model.StoreData;
import com.example.grocerystoredemoapp.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserProductList extends AppCompatActivity {
    LinearLayout scrollingLayout;
    String itemName;
    String itemBrand;
    Double itemPrice;
    static int productNameID;
    static int productBrandID;
    static int productPriceID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_list);

        scrollingLayout = findViewById(R.id.productListLayout);

        Intent intent = getIntent();
        String ref = intent.getStringExtra(UserStoreSelection.STORE_REF);

        DatabaseReference store = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);
        DatabaseReference products = FirebaseDatabase.getInstance().getReference().child("Product");
        DatabaseReference productsFromStore = store.child("products");

        TextView storeNameTitle = findViewById(R.id.productListStoreName);

        store.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("storeName").getValue().toString();
                storeNameTitle.setText(title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        productsFromStore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()) {
                    String productRef = ds.getValue().toString();

                    products.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dsb: snapshot.getChildren()) {
                                String[] newArray = dsb.getRef().toString().split("/");
                                String toLookFor = newArray[newArray.length-1];
                                if (toLookFor.equals(productRef)) {
                                    Product product = dsb.getValue(Product.class);
                                    itemName = product.getName();
                                    itemBrand = product.getBrand();
                                    itemPrice = product.getPrice();
                                    addProduct(itemName, itemBrand, itemPrice);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void addProduct(String name, String brand, Double price){
        View view = getLayoutInflater().inflate(R.layout.activity_user_product_list_product_added_view, null, false);
        scrollingLayout.addView(view);

        TextView productName = (TextView)findViewById(R.id.productListProductName);
        productName.setId(productNameID);

        TextView productBrand = (TextView)findViewById(R.id.productListProductBrand);
        productBrand.setId(productBrandID);

        TextView productPrice = (TextView)findViewById(R.id.productListProductPrice);
        productPrice.setId(productPriceID);

        productName.setText(name);
        productBrand.setText(brand);
        productPrice.setText(price.toString());

//        goToStore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(UserStoreSelection.this, UserProductList.class);
//                i.putExtra(STORE_REF, ref.toString());
////                i.putExtra(STORE_REF, "hello world");
//                startActivity(i);
//            }
//
//            //TODO: implement an intent so product list will have the products from the selected store
//        });

        productNameID++;
        productPriceID++;
        productBrandID++;
    }


}