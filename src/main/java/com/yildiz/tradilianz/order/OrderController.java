package com.yildiz.tradilianz.order;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping(value = "/orders", produces = "application/json; charset=UTF-8")
	public List<OrderDTOResponse> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping(value = "/orders/{id}", produces = "application/json; charset=UTF-8")
	public OrderDTOResponse getOneOrder(@PathVariable("id") Long id) {
		return orderService.getOneOrder(id);
	}

	@GetMapping(value = "/orders/customers/{id}", produces = "application/json; charset=UTF-8")
	public List<OrderDTOResponse> getOrdersByCustomer(@PathVariable("id") Long id) {
		return orderService.getOrderByCustomerId(id);
	}

	@GetMapping(value = "/orders/retailers/{id}", produces = "application/json; charset=UTF-8")
	public List<OrderDTOResponse> getOrdersByRetailers(@PathVariable("id") Long id) {
		return orderService.getOrderByRetailerId(id);
	}

	@PostMapping(value = "/orders", produces = "application/json; charset=UTF-8")
	public OrderDTOResponse saveOrder(@Validated @RequestBody OrderDTO orderDTO) {
		return orderService.saveOrder(orderDTO);
	}

}
