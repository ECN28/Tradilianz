package com.yildiz.tradilianz.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<CustomerDTO> allCustomer(){
		return customerService.findAll();
	}
	
	@GetMapping("/customers/{id}")
	public CustomerDTO oneCustomer(@PathVariable("id") long id) {
		return customerService.findOneById(id);
	}
	
	@PostMapping("/customers")
	public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerService.saveCustomer(customerDTO);
	}

}
