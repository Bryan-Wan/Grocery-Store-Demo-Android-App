package com.example.grocerystoredemoapp.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.DAOProduct;
import com.example.grocerystoredemoapp.data.model.DAOStoreData;
import com.example.grocerystoredemoapp.data.model.Product;
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

public class AdminAddNewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_item);

        final EditText name = findViewById(R.id.name);
        final EditText brand = findViewById(R.id.brand);
        final EditText price = findViewById(R.id.price);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Button btn = findViewById(R.id.addItemBtn);
        DAOProduct dao = new DAOProduct();

        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("StoreData").child(currentFirebaseUser.getUid()).child("products");




        btn.setOnClickListener(v->{
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product");
            String id = productRef.push().getKey();
            Product product = new Product(name.getText().toString(),brand.getText().toString(),Double.valueOf(price.getText().toString()));
            dao.add(product, id).addOnSuccessListener(suc->{
                Toast.makeText(this, "product added", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->{
                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
            });
            name.getText().clear();
            brand.getText().clear();
            price.getText().clear();
            storeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storeRef.removeEventListener(this);
                    ArrayList<String> newId = new ArrayList<>();
                    if(snapshot.getValue() == null){
                        newId.add(id);
                    }
                    else{
                        if (snapshot.getValue() instanceof ArrayList && ((ArrayList) snapshot.getValue()).size() > 0 && ((ArrayList) snapshot.getValue()).get(0) instanceof String) {
                            newId = (ArrayList<String>) snapshot.getValue();
                            newId.add(id);
                        }
                    }
                    storeRef.setValue(newId);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}
