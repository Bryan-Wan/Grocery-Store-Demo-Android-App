package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.grocerystoredemoapp.R;

import com.example.grocerystoredemoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMyProducts extends AppCompatActivity {
    private ListView mListView;

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

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> newId = new ArrayList<>();
                    if (snapshot.getValue() instanceof ArrayList && ((ArrayList) snapshot.getValue()).size() > 0 && ((ArrayList) snapshot.getValue()).get(0) instanceof String) {
                        newId = (ArrayList<String>) snapshot.getValue();
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,newId);
                        mListView.setAdapter(adapter);
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}