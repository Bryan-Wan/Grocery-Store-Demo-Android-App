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

import com.example.grocerystoredemoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminCurrentOrders extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView mListView;
    ArrayList<String> idInfo = new ArrayList<>();
    ArrayList<String> stringContents = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_current_orders);

        Button fulfilledOrdersBtn = (Button) findViewById(R.id.goToFulfilled);
        fulfilledOrdersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(adminCurrentOrders.this, adminFulfilledOrders.class);
                i.putExtra("key", "hello");
                startActivity(i);
            }
        });

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mListView = (ListView) findViewById(R.id.currentListView);
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Order");

        ArrayList<String> array  = new ArrayList<>();

        orderRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(currentFirebaseUser.getUid().equals((String)ds.child("forStore").getValue())){
                        Log.d("baa", "onDataChange: " + (String) ds.child("confirmOrder").getValue());
                        String userReady = (String) ds.child("confirmOrder").getValue();
                        String adminReady = (String) ds.child("orderIsReady").getValue();

                        if(userReady.equals("true") && adminReady.equals("false")){
                            array.add(ds.getKey());
                        }
                    }

                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,array);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String word = mListView.getItemAtPosition(position).toString();
        Intent i = new Intent(adminCurrentOrders.this, adminViewOrder.class);
        i.putExtra("key", word);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,AdminOrdersPage.class);
        startActivity(intent);
    }
}