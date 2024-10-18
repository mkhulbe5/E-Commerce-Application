package com.myStartup.exceptions;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String message) {
		super(message);
	}
}
