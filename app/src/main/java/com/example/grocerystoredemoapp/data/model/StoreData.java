package com.example.grocerystoredemoapp.data.model;

import java.util.ArrayList;

public class StoreData {
    private String storeName;
    private String storeAddress;
    ArrayList<String> product;
    public StoreData(){}
    public StoreData(String storeName, String storeAddress, ArrayList<String> product){
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.product = product;
    }

    public ArrayList<String> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<String> product) {
        this.product = product;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
}
