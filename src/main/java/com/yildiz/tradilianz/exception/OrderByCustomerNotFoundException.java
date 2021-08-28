package com.yildiz.tradilianz.exception;

public class OrderByCustomerNotFoundException extends RuntimeException {
	
	public OrderByCustomerNotFoundException(Long id) {
		super(String.format("Order with customer id %d not found", id));
	}


}
