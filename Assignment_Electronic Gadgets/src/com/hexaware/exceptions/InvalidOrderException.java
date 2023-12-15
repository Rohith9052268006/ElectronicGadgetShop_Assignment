package com.hexaware.exceptions;

@SuppressWarnings("serial")
public class InvalidOrderException extends Exception{

	public InvalidOrderException(String message) {
		super(message);
	}

}
