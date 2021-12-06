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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserCart extends AppCompatActivity {
    LinearLayout scrollingLayout;
    Button purchase;
    static Integer nameID = 9000;
    static Integer brandID = 10000;
    static Integer priceID = 11000;
    static Integer quantityID = 12000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        DatabaseReference orders = FirebaseDatabase.getInstance().getReference().child("Order");
        DatabaseReference products = FirebaseDatabase.getInstance().getReference().child("Product");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        orders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sp : snapshot.getChildren()) {
                    if (sp.child("byUser").exists() && sp.child("confirmOrder").getValue().toString().equals("false")) {
                        if (currentUser.getUid().toString().equals(sp.child("byUser").getValue().toString())) {
                            for (DataSnapshot pr : sp.child("cart").getChildren()) {
                                String productKey = pr.getKey();
                                String qty = "Quantity: "+ pr.getValue().toString();
                                products.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name = "";
                                        String brand = "";
                                        String price = "";
                                        for (DataSnapshot p : snapshot.getChildren()) {
                                            if (p.getKey().equals(productKey)) {
                                                name = p.child("name").getValue().toString();
                                                brand = p.child("brand").getValue().toString();
                                                price = "$" + Double.parseDouble(p.child("price").getValue().toString());
                                            }
                                        }
                                        addProduct(name, brand, price, qty);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}

                                });
                            }
                            purchase = findViewById(R.id.purchaseBtn);
                            purchase.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View view){
                                    if (sp.child("byUser").exists() && sp.child("confirmOrder").getValue().toString().equals("false")) {
                                        for (DataSnapshot s: snapshot.getChildren()) {
                                            if (s.child("byUser").exists()) {
                                                if (currentUser.getUid().toString().equals(s.child("byUser").getValue().toString())) {
                                                    s.child("confirmOrder").getRef().setValue("true");
                                                }
                                            }
                                        }
                                    }
                                    startActivity(new Intent(UserCart.this, UserOrderThankYouPage.class));
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });
    }

    private void addProduct(String product, String brand, String price, String quantity){
        scrollingLayout = (LinearLayout)findViewById(R.id.userCartScrollLayout);
        View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
        scrollingLayout.addView(view);

        TextView itemName = findViewById(R.id.productName);
        itemName.setId(nameID);
        TextView itemBrand = findViewById(R.id.productBrand);
        itemBrand.setId(brandID);
        TextView itemPrice = findViewById(R.id.productPrice);
        itemPrice.setId(priceID);
        TextView itemQuantity = findViewById(R.id.productQuantity);
        itemQuantity.setId(quantityID);

        itemName.setText(product);
        itemBrand.setText(brand);
        itemPrice.setText(price.toString());
        itemQuantity.setText(quantity.toString());

        nameID++;
        brandID++;
        priceID++;
        quantityID++;
    }
}