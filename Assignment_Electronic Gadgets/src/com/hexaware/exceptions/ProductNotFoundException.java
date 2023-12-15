package com.hexaware.exceptions;

@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}