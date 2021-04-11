package com.yildiz.tradilianz.exception;

public class ProductNotFoundException extends RuntimeException {
	
	public ProductNotFoundException(Long id) {
		super(String.format("Product with Id %d not found", id));
	}

}
