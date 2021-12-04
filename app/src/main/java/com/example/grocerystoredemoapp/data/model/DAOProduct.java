package com.example.grocerystoredemoapp.data.model;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOProduct {
    private DatabaseReference databaseReference;
    public DAOProduct(){
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Product");
    }
    public Task<Void> add(Product info, String id){

        return databaseReference.child(id).setValue(info);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
}
