package com.hexaware.controller;

import java.io.IOException;
import com.hexaware.exceptions.InvalidDataException;

public interface CustomerControllerInterface {
    public void registerCustomer() throws InvalidDataException, NumberFormatException, IOException ;
    public void updateCustomerDetails();
    public void getAllCustomers();

	
}
