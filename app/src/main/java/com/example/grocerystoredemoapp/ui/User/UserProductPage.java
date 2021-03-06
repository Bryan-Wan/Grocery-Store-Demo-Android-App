package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProductPage extends AppCompatActivity {

    FloatingActionButton addQuantity;
    FloatingActionButton subtractQuantity;
    Button addToCart;
    Integer itemQuantity;
    TextView quantityView;
    TextView brandView;
    TextView priceView;
    TextView itemNameView;
    static String orderId;
    int a = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_page);

        Intent intent = getIntent();
        String ref = intent.getStringExtra(UserProductList.PRODUCT_REF);
        DatabaseReference product = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);

        itemQuantity = 1;

        product.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product.removeEventListener(this);
                Product p = dataSnapshot.getValue(Product.class);
                String itemName = p.getName();
                String itemBrand = p.getBrand();
                Double itemPrice = p.getPrice();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                addToCart = findViewById(R.id.addToCartBtn);
                addQuantity = findViewById(R.id.addQuantityBtn);
                subtractQuantity = findViewById(R.id.subtractQuantityBtn);
                quantityView = (TextView) findViewById(R.id.quantity);
                quantityView.setText(itemQuantity.toString());
                brandView = findViewById(R.id.itemBrand);
                brandView.setText(itemBrand);
                priceView = findViewById(R.id.cost);
                priceView.setText(itemPrice.toString());
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
                        if(itemQuantity > 1){
                            itemQuantity--;
                            quantityView.setText(itemQuantity.toString());
                        }
                    }
                });
                DatabaseReference cart = FirebaseDatabase.getInstance().getReference().child("Order");
                String storeFromIntent = intent.getStringExtra(UserProductList.STORE_REF2);
                DatabaseReference store = FirebaseDatabase.getInstance().getReferenceFromUrl(storeFromIntent);
                cart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String storeID = (String) ds.child("forStore").getValue();
                            String orderDone = (String) ds.child("confirmOrder").getValue();
                            String userRn = (String) ds.child("byUser").getValue();

                            if(storeID.equals(store.getKey()) && (orderDone.equals("true")) && (userRn.equals(currentFirebaseUser.getUid()))){
                                a = 1;
                            }
                            else if(storeID.equals(store.getKey()) && (orderDone.equals("false")) && (userRn.equals(currentFirebaseUser.getUid()))){
                                orderId = ds.getKey();
                                a = 0;
                            }
                        }
                        if(a==1){
                            orderId = "order" + cart.push().getKey();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                addToCart.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Toast itemAdded = Toast.makeText(getApplicationContext(), p.getName() + " added", Toast.LENGTH_SHORT);
                        itemAdded.show();




                        Intent intent = getIntent();
                        DatabaseReference storeReference = FirebaseDatabase.getInstance().getReferenceFromUrl(storeFromIntent);
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                        String d = storeReference.getKey();
                        DatabaseReference newStore = FirebaseDatabase.getInstance().getReference("StoreOrders");


                        cart.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snp) {

                                DataSnapshot snapshot = snp.child(orderId);

                                if (snapshot.exists()) {
                                    if (!snapshot.child("byUser").getValue().toString().equals(currentFirebaseUser.getUid()) ||
                                            !snapshot.child("forStore").getValue().toString().equals(storeReference.getKey())) {
                                        orderId = "order" + cart.push().getKey();
                                    }
                                }

                                cart.child(orderId).child("confirmOrder").setValue("false");
                                cart.child(orderId).child("orderIsReady").setValue("false");
                                cart.child(orderId).child("byUser").setValue(currentFirebaseUser.getUid());
                                cart.child(orderId).child("forStore").setValue(storeReference.getKey());
                                cart.child(orderId).child("cart").child(product.getKey().toString()).setValue(itemQuantity);

                                startActivity(new Intent(UserProductPage.this, UserCart.class));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}