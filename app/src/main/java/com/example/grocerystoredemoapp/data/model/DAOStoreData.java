package com.example.grocerystoredemoapp.data.model;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOStoreData {
    private DatabaseReference databaseReference;
    public DAOStoreData(){
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("StoreData");

    }
    public Task<Void> add(StoreData info, String id){

        return databaseReference.child(id).setValue(info);
    }


    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
}
