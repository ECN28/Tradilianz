package com.yildiz.tradilianz.order;

import org.modelmapper.PropertyMap;

public class OrderDTOResponseMap extends PropertyMap<Order, OrderDTOResponse> {
	
	@Override
	protected void configure() {
		map().setId(source.getId());
		map().setOrderDate(source.getOrderDate());
		map().setOrderNumber(source.getOrderNumber());
		map().setOrderStatus(source.getStatus());
		map().setAmount(source.getAmount());
		map().setBonuspoints(source.getBonuspoints());
		map().setRetailerId(source.getRetailer().getId());
		map().setRetailerName(source.getRetailer().getName());
		map().setRetailerAddress(source.getRetailer().getStreetAddress());
		map().setCustomerId(source.getCustomer().getId());
		map().setCustomerName(source.getCustomer().getGivenName());
		map().setCustomerAddress(source.getCustomer().getStreetAddress());
		map().setShoppingCart(source.getShoppingCart());
	}

}
