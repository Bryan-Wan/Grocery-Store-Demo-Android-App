package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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

public class AdminMyProducts extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView mListView;
    ArrayList<String> idInfo = new ArrayList<>();
    ArrayList<String> stringContents = new ArrayList<>();
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_products);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mListView = (ListView) findViewById(R.id.listViewProducts);
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("StoreData").child(currentFirebaseUser.getUid()).child("products");
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product");

        Button addItemBtn = (Button) findViewById(R.id.addNewItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AdminMyProducts.this, AdminAddNewItem.class));
            }
        });

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {

                    String key = ds.getKey();
                    productRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            productRef.removeEventListener(this);
                            boolean exist = snapshot.hasChild(key);
                            Product product = snapshot.child(key).getValue(Product.class);
                            productList.add(product);
                            idInfo.add(key);
                            stringContents.add(product.toString());
                            ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), R.layout.adapter_view_layout, productList);
                            mListView.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String word = mListView.getItemAtPosition(position).toString();
        int index = stringContents.indexOf(word);
        String idToBeRemoved = idInfo.get(index);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("StoreData").child(currentFirebaseUser.getUid()).child("products").child(idToBeRemoved);
        storeRef.removeValue();

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product").child(idToBeRemoved);

        productRef.removeValue();

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}