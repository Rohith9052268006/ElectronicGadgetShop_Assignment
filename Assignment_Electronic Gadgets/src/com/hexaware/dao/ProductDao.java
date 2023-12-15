package com.hexaware.dao;

import com.hexaware.entity.Product;
import com.hexaware.exceptions.*;
import com.hexaware.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private Connection connection;

    public ProductDao() {
        this.connection = DBConnUtil.getMyDBConnection();
    }

    public void addProduct(Product product) throws SQLException{
        String checkDuplicateSql = "SELECT * FROM product WHERE productname = ?";
        try (PreparedStatement checkDuplicateStatement = connection.prepareStatement(checkDuplicateSql)) {
            checkDuplicateStatement.setString(1, product.getProductName());
            ResultSet resultSet = checkDuplicateStatement.executeQuery();
            if (resultSet.next()) {
                throw new DuplicateProductException("Product with name " + product.getProductName() + " already exists.");
            }
        } catch (DuplicateProductException e) {
			e.printStackTrace();
		}

        String addProductSql = "INSERT INTO product (productid,productname, description, price) VALUES (?,?, ?, ?)";
        try (PreparedStatement addProductStatement = connection.prepareStatement(addProductSql, Statement.RETURN_GENERATED_KEYS)) {
        	addProductStatement.setInt(1, product.getProductID());
        	addProductStatement.setString(2, product.getProductName());
            addProductStatement.setString(3, product.getDescription());
            addProductStatement.setDouble(4, product.getPrice());

            int affectedRows = addProductStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding product failed, no rows affected.");
            }

            
        }
    }
    public void updateProduct(Product product) throws SQLException, InvalidProductException {
        if (product.getProductID() <= 0) {
            throw new InvalidProductException("Invalid product ID.");
        }

        String updateProductSql = "UPDATE product SET productName = ?, description = ?, price = ? WHERE productID = ?";
        try (PreparedStatement updateProductStatement = connection.prepareStatement(updateProductSql)) {
            updateProductStatement.setString(1, product.getProductName());
            updateProductStatement.setString(2, product.getDescription());
            updateProductStatement.setDouble(3, product.getPrice());
            updateProductStatement.setInt(4, product.getProductID());

            int affectedRows = updateProductStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
        }
    }

    public void removeProduct(int productId) throws SQLException, InvalidProductException {
        if (productId <= 0) {
            throw new InvalidProductException("Invalid product ID.");
        }

        // Check if the product has existing orders
        String checkOrdersSql = "SELECT * FROM orderdetail WHERE productid = ?";
        try (PreparedStatement checkOrdersStatement = connection.prepareStatement(checkOrdersSql)) {
            checkOrdersStatement.setInt(1, productId);
            ResultSet resultSet = checkOrdersStatement.executeQuery();
            if (resultSet.next()) {
                throw new InvalidProductException("Cannot remove product with existing orders.");
            }
        }

        String removeProductSql = "DELETE FROM product WHERE productid = ?";
        try (PreparedStatement removeProductStatement = connection.prepareStatement(removeProductSql)) {
            removeProductStatement.setInt(1, productId);

            int affectedRows = removeProductStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Removing product failed, no rows affected.");
            }
        }
    }

    public Product getProductById(int productId) throws SQLException, InvalidProductException {
        if (productId <= 0) {
            throw new InvalidProductException("Invalid product ID.");
        }

        String getProductSql = "SELECT * FROM product WHERE productid = ?";
        try (PreparedStatement getProductStatement = connection.prepareStatement(getProductSql)) {
            getProductStatement.setInt(1, productId);
            ResultSet resultSet = getProductStatement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt("productid"));
                product.setProductName(resultSet.getString("productname"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                return product;
            } else {
                return null; // Product not found
            }
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String getAllProductsSql = "SELECT * FROM product";
        try (Statement getAllProductsStatement = connection.createStatement();
             ResultSet resultSet = getAllProductsStatement.executeQuery(getAllProductsSql)) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                products.add(product);
            }
        }
        return products;
    }
}

    