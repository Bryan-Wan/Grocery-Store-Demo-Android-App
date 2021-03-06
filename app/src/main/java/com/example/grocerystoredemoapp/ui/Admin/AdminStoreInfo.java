package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.DAOStoreData;
import com.example.grocerystoredemoapp.data.model.StoreData;
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

public class AdminStoreInfo extends AppCompatActivity {

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_store_info);

        Button btn = findViewById(R.id.saveChangesBtn);
        DAOStoreData dao = new DAOStoreData();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userID = currentFirebaseUser.getUid();


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> key = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    if(ds.getKey().equals(currentFirebaseUser.getUid())){

                        if(user.getStore() == null){
                            add();
                        }
                        else{
                            update(user.getStore());
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });

    }
    public void add(){
        setContentView(R.layout.activity_admin_store_info);
        final EditText edit_store = findViewById(R.id.adminStoreName);
        final EditText edit_address = findViewById(R.id.adminStroreAddress);
        Button btn = findViewById(R.id.saveChangesBtn);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        DAOStoreData dao = new DAOStoreData();
        DatabaseReference myStore = FirebaseDatabase.getInstance().getReference("StoreData");
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        ArrayList<String> empty = new ArrayList<>();
        empty.add("");
        btn.setOnClickListener(v->{
            if(edit_address.length() > 0 && edit_store.length() > 0){
                StoreData store = new StoreData(edit_store.getText().toString(),edit_address.getText().toString());
                myStore.child(currentFirebaseUser.getUid()).setValue(store);
                myRef.child(currentFirebaseUser.getUid()).child("store").setValue(currentFirebaseUser.getUid());

                dao.add(store, currentFirebaseUser.getUid()).addOnSuccessListener(suc->{

                    Toast.makeText(this, "store added", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er->{
                    Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else{
                Toast.makeText(this, "Fields Cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update(String storeId){
        setContentView(R.layout.activity_admin_store_info);
        final EditText edit_store = findViewById(R.id.adminStoreName);
        final EditText edit_address = findViewById(R.id.adminStroreAddress);
        Button btn = findViewById(R.id.saveChangesBtn);
        DAOStoreData dao = new DAOStoreData();
        btn.setOnClickListener(v->{
            if(edit_address.length() > 0 && edit_store.length() > 0){
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("storeName", edit_store.getText().toString());
                hashMap.put("storeAddress", edit_address.getText().toString());

                dao.update(storeId,hashMap).addOnSuccessListener(suc->{
                    Toast.makeText(this, "store updated", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er->{
                    Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else{
                Toast.makeText(this, "Fields Cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}