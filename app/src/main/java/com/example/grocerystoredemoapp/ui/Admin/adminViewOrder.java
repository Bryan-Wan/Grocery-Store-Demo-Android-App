package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminViewOrder extends AppCompatActivity {
    private ListView mListView;
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            TextView orderId = findViewById(R.id.orderId);
            orderId.setText("Order " + value);
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            mListView = (ListView) findViewById(R.id.displayOrder);
            DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Order").child(value).child("cart");
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product");

            orderRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    productList = new ArrayList<>();
                    for(DataSnapshot ds: snapshot.getChildren()){
                        int amount = ds.getValue(Integer.class);
                        Log.d("please", "onDataChange: " + amount);
                        String id = ds.getKey();

                        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product").child(id);
                        productRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Product product = snapshot.getValue(Product.class);
                                Log.d("please", "onDataChange: " + product.toString());
                                productList.add(new Product(product.getName(), product.getBrand(), amount));
                                viewOrderAdapter adapter = new viewOrderAdapter(getApplicationContext(), R.layout.adapter_view_layout, productList);
                                mListView.setAdapter(adapter);
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
    }
}