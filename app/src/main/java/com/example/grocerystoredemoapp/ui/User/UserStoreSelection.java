package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.StoreData;
import com.example.grocerystoredemoapp.ui.Admin.Settings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserStoreSelection extends AppCompatActivity {
    LinearLayout scrollView;
    private ListView mListView;
    static int nameID = 0;
    static int addressID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_store_selection);

//        mListView = (ListView) findViewById(R.id.storesListView);
//
//        final View view = getLayoutInflater().inflate(R.layout.activity_user_store_selecting, null, false);
//        scrollView.addView(view);

        scrollView = (LinearLayout)findViewById(R.id.storeLayout);
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference().child("StoreData");

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()) {
//                    Log.d("testing---------", "testing----------------------------------");
                    StoreData store = ds.getValue(StoreData.class);
                    ArrayList<String> array = new ArrayList<>();
                    String storeName = store.getStoreName();
                    String storeAddress = store.getStoreAddress();
                    addStore(storeName, storeAddress);

//                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,array);
//                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void addStore(String name, String address){
        String storeName = name;
        String storeAddress = address;

        View view = getLayoutInflater().inflate(R.layout.activity_user_store_selecting, null, false);
        scrollView.addView(view);

        TextView storeAddressView = (TextView)findViewById(R.id.storeAddress);
        storeAddressView.setId(addressID);
        addressID++;

        TextView storeNameView = (TextView)findViewById(R.id.storeName);
        storeNameView.setId(nameID);
        nameID++;

        Button goToStore = (Button)findViewById(R.id.goToStore);
        storeAddressView.setText(address);
        storeNameView.setText(name);

        goToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserStoreSelection.this, UserProductList.class));
            }

            //TODO: implement an intent so product list will have the products from the selected store
        });


    }
}