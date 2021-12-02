package com.example.grocerystoredemoapp.ui.User;

import java.util.ArrayList;

public class UserData {
    static ArrayList<String> productList;
    static ArrayList<Integer> quantityList;

    protected UserData(){
        if(productList == null && quantityList == null){
            productList = new ArrayList<String>();
            quantityList = new ArrayList<Integer>();
        }
    }

    protected UserData(String productName, Integer quantity){
        productList.add(productName);
        quantityList.add(quantity);
    }
}
