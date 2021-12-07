package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.LoginRepository;
import com.example.grocerystoredemoapp.data.model.StoreData;
import com.example.grocerystoredemoapp.data.model.User;
import com.example.grocerystoredemoapp.ui.login.LoginView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    private String userID;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mListView = (ListView) findViewById(R.id.listView);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        userID = currentFirebaseUser.getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("StoreData");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> key = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    if(ds.getKey().equals(currentFirebaseUser.getUid())){
                        ArrayList<String> array  = new ArrayList<>();
                        array.add("Username: " + user.getEmail());
                        if(user.isAdmin()){
                            storeRef.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for(DataSnapshot ds: snapshot.getChildren()){
                                        StoreData store = ds.getValue(StoreData.class);
                                        if(ds.getKey().equals(user.getStore())){
                                            if(array.size() > 1){
                                                array.set(1,"Store Name: " + store.getStoreName());
                                                array.set(2,"Store Address: " + store.getStoreAddress());
                                            }
                                            else{
                                                array.add("Store Name: " + store.getStoreName());
                                                array.add("Store Address: " + store.getStoreAddress());
                                            }

                                            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,array);
                                            mListView.setAdapter(adapter);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}

                            });
                        }
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,array);
                        mListView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });

        Button logOutBtn = findViewById(R.id.settingsLogOutBtn);

        logOutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LoginRepository.getInstance().logout();
                startActivity(new Intent(Settings.this, LoginView.class));
            }
        });
    }
}