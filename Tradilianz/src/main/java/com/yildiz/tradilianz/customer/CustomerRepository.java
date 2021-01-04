package com.yildiz.tradilianz.customer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	/*
	 * Here we can create our custom search queries on CustomerRepository
	 */

	List<Customer> findBySurname(String surname);

	Customer findById(long id);

	Customer findByEmail(String email);

}