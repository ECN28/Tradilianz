package com.yildiz.tradilianz.order;

import java.sql.Timestamp;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.yildiz.tradilianz.product.Product;

import lombok.Data;

@Component
@Data
public class OrderDTOResponse {
	private Long id;
	private String orderNumber;
	private Timestamp orderDate;
	@NotNull
	private Long customerId;
	private String customerName;
	private String customerAddress;
	@NotNull
	private Long retailerId;
	private String retailerName;
	private String retailerAddress;
	private Map<Product, Integer> shoppingCart;
	private Double amount;
	private Integer bonuspoints;
	private OrderStatus orderStatus;

}
