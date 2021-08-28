package com.yildiz.tradilianz.exception;

public class OrderByRetailerNotFoundException extends RuntimeException {
	
	public OrderByRetailerNotFoundException(Long id) {
		super(String.format("Order with retailer id %d not found", id));
	}


}
