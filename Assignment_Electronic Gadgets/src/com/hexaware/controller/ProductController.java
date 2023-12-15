package com.hexaware.controller;
import com.hexaware.dao.ProductDao;
import com.hexaware.entity.Product;
import com.hexaware.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class ProductController implements ProductControllerInterface {
    private ProductDao productDao = new ProductDao();
    private InventoryController inventoryController=new InventoryController();
    public void addProduct() throws DuplicateProductException, InvalidDataException, InvalidProductException{
        Product product = new Product();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter product No:");
            int productID = Integer.parseInt(br.readLine());
            product.setProductID(productID);
            
            System.out.println("Enter product name:");
            String productName = br.readLine();
            product.setProductName(productName);

            System.out.println("Enter product description:");
            String description = br.readLine();
            product.setDescription(description);

            System.out.println("Enter product price:");
            double price = Double.parseDouble(br.readLine());
            product.setPrice(price);

            productDao.addProduct(product);
            inventoryController.addProductToInventory(productID);
            System.out.println("Product added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding product: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public void updateProduct() throws ProductNotFoundException, InvalidProductException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter product ID to update:");
            int productId = Integer.parseInt(br.readLine());

            Product existingProduct = productDao.getProductById(productId);
            if (existingProduct != null) {
                System.out.println("Enter new product name:");
                String productName = br.readLine();
                existingProduct.setProductName(productName);

                System.out.println("Enter new product description:");
                String description = br.readLine();
                existingProduct.setDescription(description);

                System.out.println("Enter new product price:");
                double price = Double.parseDouble(br.readLine());
                existingProduct.setPrice(price);

                productDao.updateProduct(existingProduct);
                System.out.println("Product updated successfully.");
            } else {
            	throw new ProductNotFoundException("Product not Found");
            }
        } catch (IOException  | SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    public void removeProduct() throws InvalidProductException, ProductNotFoundException{
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter product ID to remove:");
            int productId = Integer.parseInt(br.readLine());

            Product existingProduct = productDao.getProductById(productId);
            if (existingProduct != null) {
                productDao.removeProduct(productId);
                System.out.println("Product removed successfully.");
            } else {
            	throw new ProductNotFoundException("Product not Found");
            }
        } catch (IOException | SQLException e) {
            System.out.println("Error removing product: " + e.getMessage());
        }
    }

    public void viewProducts() {
        try {
            List<Product> products = productDao.getAllProducts();
            if (!products.isEmpty()) {
                System.out.println("Product List:");
                for (Product product : products) {
                    System.out.println(product);
                }
            } else {
                System.out.println("No products found.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
    }
}
