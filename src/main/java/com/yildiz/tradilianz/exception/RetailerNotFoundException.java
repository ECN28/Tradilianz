package com.yildiz.tradilianz.exception;

public class RetailerNotFoundException extends RuntimeException {
	
	public RetailerNotFoundException(Long id) {
		super(String.format("Retailer with Id %d not found ", id));
	}

}
