package com.hexaware.entity;

import com.hexaware.controller.ProductController;
import com.hexaware.exceptions.InvalidProductException;
import com.hexaware.exceptions.ProductNotFoundException;

public class Product {
    private int productID;
	private String productName;
    private String description;
    private double price;
    ProductController productController = new ProductController();

    public Product(int productID, String productName, String description, double price) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public Product() {
    	
	}

	public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void GetProductDetails() {
        System.out.println(this);
    }

    public void UpdateProductInfo() throws ProductNotFoundException, InvalidProductException {
    	productController.updateProduct();
    }
    @Override
	public String toString() {
		return "Products [productID=" + productID + ", productName=" + productName + ", description=" + description
				+ ", price=" + price;
	}

    public boolean IsProductInStock() {
    	Inventory inventory=new Inventory();
    	return inventory.IsProductAvailable(productID);
    }
}
