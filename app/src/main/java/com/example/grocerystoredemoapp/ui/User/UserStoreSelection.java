package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.ui.Admin.Settings;

public class UserStoreSelection extends AppCompatActivity {
    LinearLayout scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_store_selection);

        scrollView = (LinearLayout)findViewById(R.id.storeLayout);

        final View view = getLayoutInflater().inflate(R.layout.activity_user_store_selecting, null, false);
        scrollView.addView(view);

        addStore();


    }

    private void addStore(){
        String name = "stc";
        String address = "whatever";

        final View view = getLayoutInflater().inflate(R.layout.activity_user_store_selecting, null, false);
        TextView storeName = (TextView)findViewById(R.id.storeName);
        TextView storeAddress = (TextView)findViewById(R.id.storeAddress);
        Button goToStore = (Button)findViewById(R.id.goToStore);
        storeAddress.setText(address);
        storeName.setText(name);

        goToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserStoreSelection.this, UserProductPage.class));
            }
        });

        scrollView.addView(view);
    }
}