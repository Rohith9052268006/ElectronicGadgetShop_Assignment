package com.hexaware.controller;

import java.io.*;
import java.sql.SQLException;

import com.hexaware.dao.InventoryDao;
import com.hexaware.dao.ProductDao;
import com.hexaware.entity.Inventory;
import com.hexaware.exceptions.*;
import java.util.Scanner;

public class InventoryController implements InventoryControllerInterface {
    private InventoryDao inventoryDao=new InventoryDao();
    ProductDao productDao=new ProductDao();
    Scanner scanner=null;
    
    public InventoryController(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    public InventoryController() {
		// TODO Auto-generated constructor stub
	}

	public void addProductToInventory(int productId) throws InvalidDataException,IOException, DuplicateProductException, SQLException, InvalidProductException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter quantity");
        int quantity=Integer.parseInt(br.readLine());
        validateProductAndQuantityData(productId, quantity);
        inventoryDao.addToInventory(productId, quantity);
    }
	public void addProductToInventory() throws InvalidDataException,IOException, DuplicateProductException {
        
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter product id");
		int productId=Integer.parseInt(br.readLine());
        System.out.println("Enter quantity");
        int quantity=Integer.parseInt(br.readLine());
        validateProductAndQuantityData(productId, quantity);
        inventoryDao.addToInventory(productId, quantity);
    }

    public void updateStockQuantity(int productId, int newQuantity) throws InvalidDataException, SQLException {
        validateProductAndQuantityData(productId, newQuantity);
        inventoryDao.updateStockQuantityOrdered(productId, newQuantity);
    }

    public void removeProductFromInventory(int productId) throws InvalidDataException {
        validateProductData(productId);
        inventoryDao.removeFromInventory(productId);
    }

    public void getAllInventory() throws InvalidProductException {
        inventoryDao.getAllInventory();
    }

    private void validateProductAndQuantityData(int productId, int quantity) throws InvalidDataException {
        validateProductData(productId);

        if (quantity <= 0) {
            throw new InvalidDataException("Invalid quantity. Quantity must be a positive integer.");
        }
        return;

    }

    private void validateProductData(int productId) throws InvalidDataException {
        if (productId <= 0) {
            throw new InvalidDataException("Invalid product ID. Product ID must be a positive integer.");
        }
        return;
    }
    public void updateProductQuantity() throws SQLException {
        try {
            scanner = new Scanner(System.in);

            System.out.println("Enter Product ID:");
            int productId = scanner.nextInt();
            Inventory existingProduct = inventoryDao.getInventoryById(productId);

            if (existingProduct != null) {
                System.out.println("Current Quantity: " + existingProduct.GetQuantityInStock());
                System.out.println("Enter new Quantity:");
                int newQuantity = scanner.nextInt();
                inventoryDao.updateStockQuantity(productId, newQuantity);
                System.out.println("Product quantity updated successfully!");
            } else {
                System.out.println("Product with ID " + productId + " not found.");
            }
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
        	scanner.close();
        }
    }

	
}
