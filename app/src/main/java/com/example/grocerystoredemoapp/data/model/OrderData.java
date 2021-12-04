package com.example.grocerystoredemoapp.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;


@IgnoreExtraProperties
public class OrderData {
    static public ArrayList<String> productList;
    static public ArrayList<Integer> quantityList;
    static public ArrayList<String> brandList;
    static public ArrayList<Integer> priceList;

    public OrderData(){
        if(productList == null && quantityList == null){
            productList = new ArrayList<>();
            quantityList = new ArrayList<>();
            brandList = new ArrayList<>();
            priceList = new ArrayList<>();
        }
    }

    public OrderData(String productName, Integer quantity, String brand, Integer price){
        this.productList.add(productName);
        this.quantityList.add(quantity);
        this.productList.add(brand);
        this.quantityList.add(price);

    }
}
