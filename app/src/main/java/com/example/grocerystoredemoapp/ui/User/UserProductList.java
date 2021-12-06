package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProductList extends AppCompatActivity {
    LinearLayout scrollingLayout;
    String itemName;
    String itemBrand;
    Double itemPrice;
    Button cartBtn;

    static int productNameID = 2000;
    static int productBrandID = 3000;
    static int productPriceID = 4000;
    static int layoutID = 5000;

    public static final String PRODUCT_REF = "com.example.grocerystoredemoapp.PRODUCT_REF";
    public static final String STORE_REF2 = "com.example.grocerystoredemoapp.STORE_REF2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_list);

        scrollingLayout = (LinearLayout) findViewById(R.id.productListLayout);

        Intent intent = getIntent();
        String ref = intent.getStringExtra(UserStoreSelection.STORE_REF);

        DatabaseReference store = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);
        DatabaseReference products = FirebaseDatabase.getInstance().getReference().child("Product");
        DatabaseReference productsFromStore = store.child("products");

        TextView storeNameTitle = findViewById(R.id.productListStoreName);
        TextView storeAddressTitle = findViewById(R.id.productListStoreAddress);

        store.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("storeName").getValue().toString();
                String address = dataSnapshot.child("storeAddress").getValue().toString();
                storeNameTitle.setText(name);
                storeAddressTitle.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

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
                                    addProduct(itemName, itemBrand, itemPrice, dsb.getRef(), store);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });
    }

    private void addProduct(String name, String brand, Double price, DatabaseReference productRef, DatabaseReference storeRef){
        View view = getLayoutInflater().inflate(R.layout.activity_user_product_list_product_added_view, null, false);
        scrollingLayout.addView(view);

        TextView productName = (TextView)findViewById(R.id.productListProductName);
        productName.setId(productNameID);

        TextView productBrand = (TextView)findViewById(R.id.productListProductBrand);
        productBrand.setId(productBrandID);

        TextView productPrice = (TextView)findViewById(R.id.productListProductPrice);
        productPrice.setId(productPriceID);

        ConstraintLayout layout = findViewById(R.id.productListConstraint);
        layout.setId(layoutID);

        productBrand.setText(brand);
        productName.setText(name);
        productPrice.setText("$" + price.toString());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProductList.this, UserProductPage.class);
                i.putExtra(PRODUCT_REF, productRef.toString());
                i.putExtra(STORE_REF2, storeRef.toString());
                startActivity(i);
            }
        });
        cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserProductList.this, UserCart.class));
            }
        });

        productNameID++;
        productPriceID++;
        productBrandID++;
        layoutID++;
    }
}