package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.Product;
import com.example.grocerystoredemoapp.data.model.StoreData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHistory extends AppCompatActivity {
    static Integer nameID = 2000;
    static Integer qtyID = 7000;
    static Integer bgID = 9999;
    LinearLayout scrollingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        DatabaseReference orders = FirebaseDatabase.getInstance().getReference().child("Order");
        DatabaseReference products = FirebaseDatabase.getInstance().getReference().child("Product");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        orders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.removeEventListener(this);
                for (DataSnapshot sp : snapshot.getChildren()) {
                    if (sp.child("byUser").exists() && sp.child("confirmOrder").getValue().toString().equals("true")) {
                        if (currentUser.getUid().toString().equals(sp.child("byUser").getValue().toString())) {
                            for (DataSnapshot pr : sp.child("cart").getChildren()) {
                                String productKey = pr.getKey();
                                String qty = "Quantity: "+ pr.getValue().toString();
                                products.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name = "";
                                        Boolean isReady = false;
                                        for (DataSnapshot p : snapshot.getChildren()) {
                                            if (p.getKey().equals(productKey)) {
                                                name = p.child("name").getValue().toString();
                                                isReady = sp.child("orderIsReady").getValue().toString().equals("true");
                                                addHistory(name, qty, isReady);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    private void addHistory(String name, String qty, Boolean isReady) {
        scrollingLayout = (LinearLayout)findViewById(R.id.PastOrders);
        View view = getLayoutInflater().inflate(R.layout.activity_user_history_add_order, null, false);
        scrollingLayout.addView(view);

        TextView hName = findViewById(R.id.historyName);
        hName.setId(nameID);

        TextView hQty = findViewById(R.id.historyQty);
        hQty.setId(qtyID);

        ConstraintLayout background = findViewById(R.id.historyBG);
        background.setId(bgID);

        hName.setText(name);
        hQty.setText(qty);
        if (isReady) {
            background.setBackgroundResource(R.drawable.border);
            background.setBackgroundColor(Color.GREEN);

        } else {
            background.setBackgroundResource(R.drawable.border);
            background.setBackgroundColor(Color.YELLOW);
        }

        nameID++;
        qtyID++;
        bgID++;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, UserHome.class);
        startActivity(intent);
    }
}