package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminHome extends AppCompatActivity {
    String store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button viewOrdersBtn = (Button) findViewById(R.id.viewOrdersBtn);
        viewOrdersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminHome.this, AdminOrdersPage.class));
            }
        });
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        TextView name = findViewById(R.id.welcomeAdminTitle);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> key = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    if(ds.getKey().equals(currentFirebaseUser.getUid())){
                        store = user.getStore();
                        name.setText("Welcome " + user.getDisplayName());
                    }
                }
                Button editProductsBtn = (Button) findViewById(R.id.editProductsBtn);
                editProductsBtn.setOnClickListener(v->{
                    if(store == null){
                        Toast.makeText(AdminHome.this, "Create a store First", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        startActivity(new Intent(AdminHome.this, AdminMyProducts.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });

        Button editStoreBtn = (Button) findViewById(R.id.editStoreBtn);
        editStoreBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminHome.this, AdminStoreInfo.class));
            }
        });

        ImageButton adminSettingsBtn = (ImageButton) findViewById(R.id.adminSettingsBtn);
        adminSettingsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AdminHome.this, Settings.class));
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