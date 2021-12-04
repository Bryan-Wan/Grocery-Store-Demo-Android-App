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

            try {
                double priceOfProduct = Double.valueOf(price.getText().toString());
                if (name.length() > 0 && brand.length() > 0 && brand.length() > 0) {
                    Product product = new Product(name.getText().toString(), brand.getText().toString(), priceOfProduct, id);
                    dao.add(product, id).addOnSuccessListener(suc -> {
                        Toast.makeText(this, "product added", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(er -> {
                        Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                    name.getText().clear();
                    brand.getText().clear();
                    price.getText().clear();

                    storeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            storeRef.removeEventListener(this);
                            storeRef.child(id).setValue(id);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }catch(NumberFormatException ex) {
                Toast.makeText(this, "Not a price value!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
