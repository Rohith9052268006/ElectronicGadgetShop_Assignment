package com.hexaware.dao;


import com.hexaware.entity.Inventory;
import com.hexaware.entity.Product;
import com.hexaware.exceptions.InvalidDataException;
import com.hexaware.exceptions.InvalidProductException;
import com.hexaware.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDao{
    public void addToInventory(int productId, int quantity) throws InvalidDataException {
        try{
            Connection connection = DBConnUtil.getMyDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO Inventory (ProductID, QuantityInStock, LastStockUpdate) VALUES (?, ?, NOW())");
            
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, quantity);
            

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Adding product to inventory failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new InvalidDataException("Error adding product to inventory.");
        }
    }
    public void updateStockQuantity(int productId, int quantity) throws SQLException {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Inventory SET QuantityInStock = ? WHERE ProductID = ?")) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows affected. Product may not exist in the inventory table.");
            }
        }
    }
    public void updateStockQuantityOrdered(int productId, int quantity) throws SQLException {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Inventory SET QuantityInStock = QuantityInStock - ? WHERE ProductID = ?")) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No rows affected. Product may not exist in the inventory table.");
            }
        }
    }
    public void updateStockQuantityCancelled(int productId, int quantity) throws SQLException {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Inventory SET QuantityInStock = QuantityInStock + ? WHERE ProductID = ?")) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows affected. Product may not exist in the inventory table.");
            }
        }
    }

    
    public void removeFromInventory(int productId) throws InvalidDataException {
        try {
        	Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Inventory WHERE ProductID=?");

            
            preparedStatement.setInt(1, productId);

           
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Removing product from inventory failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new InvalidDataException("Error removing product from inventory.");
        }
    }
    public Inventory getInventoryById(int productId) throws InvalidDataException {
        Inventory product = null;

        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Inventory WHERE ProductID = ?")) {

            preparedStatement.setInt(1, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Inventory();

                    // Instantiate a new Product object
                    Product productDetails = new Product();
                    productDetails.setProductID(resultSet.getInt("ProductID"));
                    // Set the Product object in the Inventory
                    product.setProduct(productDetails);

                    product.setQuantityInStock(resultSet.getInt("QuantityInStock"));
                }
            }

        } catch (SQLException e) {
            throw new InvalidDataException("Error fetching product details from the database.");
        }

        return product;
    }

    public void getAllInventory() throws InvalidProductException {
        List<Inventory> inventoryList = new ArrayList<>();

        try (Connection connection = DBConnUtil.getMyDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Inventory")) {

            while (resultSet.next()) {
                Inventory inventory = new Inventory();
                inventory.setInventoryID(resultSet.getInt("InventoryID"));
                
                
                int productId = resultSet.getInt("ProductID");
                ProductDao productDao = new ProductDao();
                Product product = productDao.getProductById(productId);
                inventory.setProduct(product);

                inventory.setQuantityInStock(resultSet.getInt("QuantityInStock"));
                inventory.setLastStockUpdate(resultSet.getTimestamp("LastStockUpdate").toLocalDateTime());

                inventoryList.add(inventory);
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        for(int i=0;i<inventoryList.size();i++) {
        	System.out.println(inventoryList.get(i));
        }
    }
}
