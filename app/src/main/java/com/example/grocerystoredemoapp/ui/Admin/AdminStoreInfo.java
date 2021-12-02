package com.example.grocerystoredemoapp.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.DAOStoreData;
import com.example.grocerystoredemoapp.data.model.StoreData;

public class AdminStoreInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_store_info);
        final EditText edit_store = findViewById(R.id.adminStoreName);
        final EditText edit_address = findViewById(R.id.adminStroreAddress);
        Button btn = findViewById(R.id.saveChangesBtn);
        DAOStoreData dao = new DAOStoreData();
        btn.setOnClickListener(v->{
            StoreData store = new StoreData(edit_store.getText().toString(),edit_address.getText().toString());
            dao.add(store).addOnSuccessListener(suc->{
                Toast.makeText(this, "store added", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->{
                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }
}