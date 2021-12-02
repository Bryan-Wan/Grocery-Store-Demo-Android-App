package com.example.grocerystoredemoapp.data.model;

import java.util.ArrayList;

public class UserData {
    static ArrayList<Product> productList;
    static ArrayList<Integer> quantityList;

    protected UserData(){
        if(productList == null && quantityList == null){
            productList = new ArrayList<Product>();
            quantityList = new ArrayList<Integer>();
        }
    }

    protected UserData(Product productName, Integer quantity){
        productList.add(productName);
        quantityList.add(quantity);
    }
}
