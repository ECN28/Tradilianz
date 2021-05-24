package com.yildiz.tradilianz.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findBycustomerId(Long customerId);
	List<Order> findByretailerId(Long retailerId);
	List<Order> findByamountLessThan(Double amount);
	List<Order> findByamountGreaterThan(Double amount);
}
