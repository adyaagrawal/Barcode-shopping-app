package com.example.barcode_shopping_app.models;

public class Product {
    private String id;
    private int available;
    private String imageId;
    private String name;
    private Double unitPrice;
    private Double unitWeight;

    public Product() {
        this("",2,"","",0.00,1.00);
    }

    public Product(String id, int available, String imageId, String name, Double unitPrice, Double unitWeight) {
        this.id = id;
        this.available = available;
        this.imageId = imageId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.unitWeight = unitWeight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }
}
