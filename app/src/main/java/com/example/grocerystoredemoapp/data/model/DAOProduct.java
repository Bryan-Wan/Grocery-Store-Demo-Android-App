package com.example.grocerystoredemoapp.data.model;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOProduct {
    private DatabaseReference databaseReference;
    public DAOProduct(){
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Product.class.getSimpleName());
    }
    public Task<Void> add(Product info){
        return databaseReference.push().setValue(info);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
}
