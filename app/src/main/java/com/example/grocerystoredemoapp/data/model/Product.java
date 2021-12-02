package com.example.grocerystoredemoapp.data.model;

public class Product {
    public String name;
    public String brand;
    public Double price;

    protected Product(String name, String brand, Double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }
}
