package com.example.grocerystoredemoapp.data.model;

import java.util.ArrayList;

public class UserData {
    static public ArrayList<String> productList;
    static public ArrayList<Integer> quantityList;

    public UserData(){
        if(productList == null && quantityList == null){
            productList = new ArrayList<>();
            quantityList = new ArrayList<>();
        }
    }

    public UserData(String productName, Integer quantity){
        this.productList.add(productName);
        this.quantityList.add(quantity);
    }
}
