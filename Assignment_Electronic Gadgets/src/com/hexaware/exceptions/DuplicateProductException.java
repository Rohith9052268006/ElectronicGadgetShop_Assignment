package com.hexaware.exceptions;

@SuppressWarnings("serial")
public class DuplicateProductException extends Exception{
	public DuplicateProductException(String message) {
        super(message);
    }
}
