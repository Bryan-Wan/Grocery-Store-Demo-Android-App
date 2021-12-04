package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;
import com.example.grocerystoredemoapp.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMyProducts extends AppCompatActivity{
    private ListView mListView;
    ArrayList<String> productInfo = new ArrayList<>();
    ArrayList<String> idInfo = new ArrayList<>();
    ArrayList<String> newId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_products);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mListView = (ListView) findViewById(R.id.listViewProducts);


        Button addItemBtn = (Button) findViewById(R.id.addNewItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AdminMyProducts.this, AdminAddNewItem.class));
            }
        });


        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("StoreData").child(currentFirebaseUser.getUid()).child("products");
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product");

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() instanceof ArrayList && ((ArrayList) snapshot.getValue()).size() > 0 && ((ArrayList) snapshot.getValue()).get(0) instanceof String) {
                        newId = (ArrayList<String>) snapshot.getValue();

                        ArrayList<String> finalNewId = newId;
                        productRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                productRef.removeEventListener(this);
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Product product = ds.getValue(Product.class);
                                    if(newId.contains(ds.getKey())){
                                        if(!idInfo.contains(ds.getKey())){
                                            idInfo.add(ds.getKey());
                                            productInfo.add("Name: " +product.getName() + "        Brand:" + product.getBrand() + "         Price:$" + product.getPrice());
                                        }
                                    }
                                }
                                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,productInfo);
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