package com.example.grocerystoredemoapp.data.model;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOStoreData {
    private DatabaseReference databaseReference;
    public DAOStoreData(){
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(StoreData.class.getSimpleName());
    }
    public Task<Void> add(StoreData info){

        return databaseReference.push().setValue(info);
    }
}
