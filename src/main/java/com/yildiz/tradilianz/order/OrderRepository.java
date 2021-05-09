package com.yildiz.tradilianz.order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
	
	List<Order> findBycustomerId(Long customerId);
	List<Order> findByretailerId(Long retailerId);
	
	
}
