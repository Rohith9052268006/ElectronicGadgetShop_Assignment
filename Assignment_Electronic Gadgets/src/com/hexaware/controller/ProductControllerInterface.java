package com.hexaware.controller;

import com.hexaware.exceptions.DuplicateProductException;
import com.hexaware.exceptions.InvalidDataException;
import com.hexaware.exceptions.InvalidProductException;
import com.hexaware.exceptions.ProductNotFoundException;

public interface ProductControllerInterface {
	public void addProduct() throws DuplicateProductException, InvalidDataException, InvalidProductException;
    public void updateProduct() throws ProductNotFoundException, InvalidProductException;
    public void removeProduct() throws InvalidProductException, ProductNotFoundException;
    public void viewProducts() ;
}
