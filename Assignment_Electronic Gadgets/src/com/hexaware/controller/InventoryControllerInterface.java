package com.hexaware.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.hexaware.exceptions.DuplicateProductException;
import com.hexaware.exceptions.InvalidDataException;
import com.hexaware.exceptions.InvalidProductException;

public interface InventoryControllerInterface {
	public void addProductToInventory(int productId) throws InvalidDataException,IOException, DuplicateProductException, SQLException, InvalidProductException;
	public void addProductToInventory() throws InvalidDataException,IOException, DuplicateProductException ;
    public void updateStockQuantity(int productId, int newQuantity) throws InvalidDataException, SQLException ;
    public void removeProductFromInventory(int productId) throws InvalidDataException;
    public void getAllInventory() throws InvalidProductException;
    public void updateProductQuantity() throws SQLException;
    

}
