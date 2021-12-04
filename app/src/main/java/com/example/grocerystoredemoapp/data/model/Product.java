package com.example.grocerystoredemoapp.data.model;

public class Product {
    private String name;
    private String brand;
    private double price;
    private String id;

    public Product(){}
    public Product(String name,String brand, double price){
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public Product(String name,String brand, double price,String id){
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.id = id;
    }

    @Override
    public String toString() {
        return name + brand + price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId() {
        return id;
    }
}
