package com.hexaware.exceptions;

@SuppressWarnings("serial")
public class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
}
