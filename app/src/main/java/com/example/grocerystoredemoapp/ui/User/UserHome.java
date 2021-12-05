package com.example.grocerystoredemoapp.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.OrderData;
import com.example.grocerystoredemoapp.ui.Admin.Settings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHome extends AppCompatActivity {
    Button cartBtn_homePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        OrderData userData = new OrderData();


        Button newOrderBtn = (Button) findViewById(R.id.userNewOrderBtn);
        newOrderBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(UserHome.this, UserStoreSelection.class));
            }
        });

        Button orderHistoryBtn = (Button) findViewById(R.id.userOrderHistoryBtn);
        orderHistoryBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(UserHome.this, UserHistory.class));
            }
        });

        ImageButton userSettingsBtn = (ImageButton) findViewById(R.id.userSettingsBtn);
        userSettingsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(UserHome.this, Settings.class));
            }
        });
        cartBtn_homePage = findViewById(R.id.cartBtn_homePage);
        cartBtn_homePage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserHome.this, UserCart.class));
            }
        });
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        TextView name = findViewById(R.id.welcomeUserTitle);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child(currentFirebaseUser.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = (String) snapshot.child("displayName").getValue();
                name.setText("Welcome: " + userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }
}