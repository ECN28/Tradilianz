package com.yildiz.tradilianz.exception;

import com.yildiz.tradilianz.order.OrderDTOResponse;

public class CustomerBalanceToLowException extends RuntimeException {
	
	private OrderDTOResponse orderDTOCancel;
	
	public CustomerBalanceToLowException(Long id, OrderDTOResponse orderDTOCancel) {
		super(String.format("Customer with %d has lower balance then needed for buying transaction", id));
		this.orderDTOCancel = orderDTOCancel;
	}
	
	public String getOrderDTOMessage() {
		return orderDTOCancel.toString();
	}
}
