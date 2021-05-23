package com.yildiz.tradilianz.order;

import org.modelmapper.PropertyMap;

public class OrderMap extends PropertyMap<OrderDTO, Order>{
	
	@Override
	protected void configure() {
		map().setId(source.getId());
		map().setOrderDate(source.getOrderDate());
		map().setOrderNumber(source.getOrderNumber());
		map().setStatus(source.getOrderStatus());
		map().setAmount(source.getAmount());
		map().setBonuspoints(source.getBonuspoints());
		skip().setCustomer(null);
		skip().setRetailer(null);
		skip().setShoppingCart(null);
	}

}
