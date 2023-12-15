package com.hexaware.entity;
import java.util.*;
import java.time.LocalDateTime;

public class Inventory {
    private int inventoryID;
    

	private Product product;
    private int quantityInStock;
    private LocalDateTime lastStockUpdate;
    
    public Inventory(int inventoryID, Product product, int quantityInStock, LocalDateTime lastStockUpdate) {
        this.inventoryID = inventoryID;
        this.product = product;
        this.quantityInStock = quantityInStock;
        this.lastStockUpdate = lastStockUpdate;
    }

    public Inventory() {
		
	}

	public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public LocalDateTime getLastStockUpdate() {
        return lastStockUpdate;
    }

    public void setLastStockUpdate(LocalDateTime lastStockUpdate) {
        this.lastStockUpdate = lastStockUpdate;
    }

    public Product GetProduct() {
        return product;
    }

    public int GetQuantityInStock() {
        return quantityInStock;
    }
    @Override
	public String toString() {
		return "Inventory [inventoryID=" + inventoryID + ", product=[" + product + "], quantityInStock=" + quantityInStock
				+ ", lastStockUpdate=" + lastStockUpdate + "]";
	}
    public void UpdateStockQuantity(int newQuantity) {
    	this.quantityInStock = newQuantity;
        this.lastStockUpdate = LocalDateTime.now();
    }

    public boolean IsProductAvailable(int productId) {
        
    	return product.getProductID() == productId && quantityInStock > 0;
    }
    
    public void AddToInventory(int quantity) {
    	if (quantity > 0) {
            this.quantityInStock += quantity;
            this.lastStockUpdate = LocalDateTime.now();
            System.out.println(quantity + " units added to the inventory for product " + product.getProductID());
        } else {
            System.out.println("Invalid quantity. Please provide a positive quantity.");
        }
    }

    public void RemoveFromInventory(int quantity) {
    	if (quantity > 0 && quantity <= this.quantityInStock) {
            this.quantityInStock -= quantity;
            this.lastStockUpdate = LocalDateTime.now();
            System.out.println(quantity + " units removed from the inventory for product " + product.getProductID());
        } else {
            System.out.println("Invalid quantity. Please provide a valid quantity to remove.");
        }
    }
    public double GetInventoryValue() {
    	return product.getPrice() * quantityInStock;
    }

    public List<Product> ListLowStockProducts(int threshold) {
    	List<Product> lowStockProducts = new ArrayList<>();
        if (quantityInStock < threshold) {
            lowStockProducts.add(product);
        }
        return lowStockProducts;
    }

    public List<Product> ListOutOfStockProducts() {
    	List<Product> outOfStockProducts = new ArrayList<>();
        if (quantityInStock == 0) {
            outOfStockProducts.add(product);
        }
        return outOfStockProducts;
    }

    public List<Product> ListAllProducts() {
    	List<Product> allProducts = new ArrayList<>();
        allProducts.add(product);
        return allProducts;
    }

	
}
