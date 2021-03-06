package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.StoreData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class UserStoreSelection extends AppCompatActivity {
    LinearLayout scrollView;
    private ListView mListView;
    static int nameID = 0;
    static int addressID = 500;
    static int buttonID = 1000;
    public static final String STORE_REF = "com.example.grocerystoredemoapp.STORE_REF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_store_selection);

        scrollView = (LinearLayout)findViewById(R.id.storeLayout);
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference().child("StoreData");


        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeRef.removeEventListener(this);
                for(DataSnapshot ds: snapshot.getChildren()) {
//                    if (ds.hasChild("products")) {
                    StoreData store = ds.getValue(StoreData.class);
                    String storeName = store.getStoreName();
                    String storeAddress = store.getStoreAddress();
                    DatabaseReference storeRef = ds.getRef();
                    addStore(storeName, storeAddress, storeRef);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
            
        });
    }

    private void addStore(String name, String address, DatabaseReference ref){

        View view = getLayoutInflater().inflate(R.layout.activity_user_store_selecting, null, false);
        scrollView.addView(view);

        TextView storeAddressView = (TextView)findViewById(R.id.storeAddress);
        storeAddressView.setId(addressID);

        TextView storeNameView = (TextView)findViewById(R.id.storeName);
        storeNameView.setId(nameID);

        Button goToStore = (Button)findViewById(R.id.goToStore);
        goToStore.setId(buttonID);

        storeAddressView.setText(address);
        storeNameView.setText(name);

        goToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserStoreSelection.this, UserProductList.class);
                i.putExtra(STORE_REF, ref.toString());
                startActivity(i);
            }
        });

        addressID++;
        nameID++;
        buttonID++;
    }
}