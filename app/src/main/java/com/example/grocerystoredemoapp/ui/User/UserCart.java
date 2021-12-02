package com.example.grocerystoredemoapp.ui.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.grocerystoredemoapp.R;
import com.example.grocerystoredemoapp.data.model.UserData;

public class UserCart extends AppCompatActivity {
    LinearLayout scrollingLayout;
    Button purchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        scrollingLayout = findViewById(R.id.userCartScrollLayout);
        purchase = findViewById(R.id.purchaseBtn);
        View view = getLayoutInflater().inflate(R.layout.activity_user_product_added_view, null, false);
        scrollingLayout.addView(view);

        purchase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(UserCart.this, UserConfirmationPage.class));
            }
        });





    }

//    private void addProduct(){
//        UserData userData = new UserData();
//        Integer i = 0
//        while(i < userData.productList.size()){
//
//        }
//    }


}