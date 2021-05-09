package com.yildiz.tradilianz.order;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import com.yildiz.tradilianz.product.Product;

import lombok.Data;

@Component
@Data
public class OrderDTO {
	
	private Long id;
	private String orderNumber;
	private Timestamp orderDate;
	private Long customerId;
	private String customerName;
	private String customerAddress;
	private Long retailerId;
	private String retailerName;
	private String retailerAddress;
	private List<Product> shoppingCart;
	private Double amount;
	private Integer bonuspoints;
	private Enum<OrderStatus> orderStatus;
}
