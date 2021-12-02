package com.example.grocerystoredemoapp.data.model;

import java.util.ArrayList;

public class UserData {
    static public ArrayList<String> productList;
    static public ArrayList<Integer> quantityList;
    static public ArrayList<String> brandList;
    static public ArrayList<Integer> priceList;

    public UserData(){
        if(productList == null && quantityList == null){
            productList = new ArrayList<>();
            quantityList = new ArrayList<>();
            brandList = new ArrayList<>();
            priceList = new ArrayList<>();
        }
    }

    public UserData(String productName, Integer quantity, String brand, Integer price){
        this.productList.add(productName);
        this.quantityList.add(quantity);
        this.productList.add(brand);
        this.quantityList.add(price);

    }
}
