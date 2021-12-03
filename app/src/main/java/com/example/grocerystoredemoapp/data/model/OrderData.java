package com.example.grocerystoredemoapp.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;


@IgnoreExtraProperties
public class OrderData {
    static public List<String> productList;
    static public List<Integer> quantityList;
    static public List<String> brandList;
    static public List<Integer> priceList;

    public OrderData(){
    }

    public OrderData(String productName, Integer quantity, String brand, Integer price){
        this.productList.add(productName);
        this.quantityList.add(quantity);
        this.productList.add(brand);
        this.quantityList.add(price);

    }
}
