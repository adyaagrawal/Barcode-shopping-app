package com.example.barcode_shopping_app.models;

public class CartItem {
    private String productId;
    private String itemName;
    private Double itemPrice;
    private int itemQty;
    private String imageId;

    public CartItem() {
        this("", "", 0.00, 0, "");
    }

    public CartItem(String productId, String itemName, Double itemPrice, int itemQty, String imageId) {
        this.productId = productId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.imageId = imageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}