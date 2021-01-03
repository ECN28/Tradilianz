package com.yildiz.tradilianz.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> findAll(){
		
		var it = customerRepository.findAll();
		var customerList = new ArrayList<Customer>();
		for(Customer customer: it) {
			customerList.add(customer);
		}
		return customerList;
	}



}
