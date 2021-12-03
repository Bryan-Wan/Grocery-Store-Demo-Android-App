package com.example.grocerystoredemoapp.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.DAOProduct;
import com.example.grocerystoredemoapp.data.model.DAOStoreData;
import com.example.grocerystoredemoapp.data.model.Product;
import com.example.grocerystoredemoapp.data.model.StoreData;

public class AdminAddNewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_item);

        final EditText name = findViewById(R.id.name);
        final EditText brand = findViewById(R.id.brand);
        final EditText price = findViewById(R.id.price);

        Button btn = findViewById(R.id.addItemBtn);
        DAOProduct dao = new DAOProduct();

        btn.setOnClickListener(v->{
            Product product = new Product(name.getText().toString(),brand.getText().toString(),Double.valueOf(price.getText().toString()));
            dao.add(product).addOnSuccessListener(suc->{
                Toast.makeText(this, "product added", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->{
                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
            });

            /*HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("storeName", edit_store.getText().toString());
            hashMap.put("storeAddress", edit_address.getText().toString());

            dao.update(uid,hashMap).addOnSuccessListener(suc->{
                Toast.makeText(this, "store s", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->{
                Toast.makeText(this, "no"+er.getMessage(), Toast.LENGTH_SHORT).show();
            });*/

        });
    }
}
