package com.yildiz.tradilianz.customer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	List<Customer> findBySurname(String surname);
	Customer findById(long id);
}