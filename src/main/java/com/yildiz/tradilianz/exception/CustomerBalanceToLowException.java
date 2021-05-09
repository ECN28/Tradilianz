package com.yildiz.tradilianz.exception;

public class CustomerBalanceToLowException extends RuntimeException {
	
	public CustomerBalanceToLowException(Long id) {
		super(String.format("Customer with %d has lower balance then needed for buying transaction", id));
	}

}
