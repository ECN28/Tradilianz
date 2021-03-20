package com.yildiz.tradilianz.customer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	/*
	 * Here we can create our custom search queries on CustomerRepository
	 */
	Customer findByUsername(String username);

	List<Customer> findBySurname(String surname);

	Customer findById(long id);

	Customer findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}