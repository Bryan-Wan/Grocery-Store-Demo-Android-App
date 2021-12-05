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
import com.example.grocerystoredemoapp.data.model.OrderData;
import com.example.grocerystoredemoapp.data.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    Double price;
    String itemName;
    String brand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_page);

        Intent intent = getIntent();
        String ref = intent.getStringExtra(UserProductList.PRODUCT_REF);
        DatabaseReference product = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);

        // product.toString() returns
        // grocery-store-demo-app-default-rtdb.firebaseio.com/Product/-Mq6oxvWQWeZ9wEbmmC2
        Log.d("asasdd", "onDataChange: ");



//        product.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for(DataSnapshot ds: snapshot.getChildren()) {
//                    Product product = ds.getValue(Product.class);
//                    itemName = product.getName();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });


//        itemName = ref;



        itemQuantity = 0;
        price = 50.0;
////        itemName = "Amazon Gift Card";
//        brand = "Amazon";

        product.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String itemName = (String)dataSnapshot.child("name").getValue();
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
//                OrderData userData = new OrderData();
//                userData.productList.add(itemName);
//                userData.quantityList.add(itemQuantity);
//                userData.brandList.add(brand);
//                userData.priceList.add(price);
//                Toast itemAdded = Toast.makeText(getApplicationContext(), "item added", Toast.LENGTH_SHORT);
//                itemAdded.setMargin(50, 50);
//                itemAdded.show();
//                startActivity(new Intent(UserProductPage.this, UserCart.class));

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }
}