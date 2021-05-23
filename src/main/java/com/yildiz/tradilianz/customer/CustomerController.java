package com.yildiz.tradilianz.customer;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * Dispatchservlet nimmt HTTP-Anfragen entgegen und delegiert sie an die jeweiligen Requesthandler
 * (gekennzeichnet durch @Controller oder @RestController). Der Requesthandler ruft die definierten Methoden
 * aus unserer Service Klasse auf. Genauer wird  ein Data Transfer object an die Service
 * Klasse übergeben. Dieser wird dann weiter vaildiert, verarbeitet etc. Anschließend werden die Daten, die in
 * den Data DTO's enthalten sind durch die JPA API in der Datenbank gespeichert, gelöscht, abgerufen etc.
 */

@RestController
public class CustomerController {

	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(value = "/customers", produces = "application/json; charset=UTF-8" )
	public List<CustomerDTO> allCustomer() {
		return customerService.findAll();
	}

	@GetMapping(value = "/customers/{id}", produces = "application/json; charset=UTF-8" )
	public CustomerDTO oneCustomer(@PathVariable("id") long id) {
		return customerService.findOneById(id);
	}

	@PostMapping(value = "/customers", produces = "application/json; charset=UTF-8" )
	public CustomerDTO addCustomer(@Validated @RequestBody CustomerDTO customerDTO) throws Exception {
		return customerService.saveCustomer(customerDTO);
	}

	@PutMapping(value = "/customers/{id}", produces = "application/json; charset=UTF-8" )
	public CustomerDTO updateCustomer(@PathVariable("id") long id, @Validated @RequestBody CustomerDTO customerDTO) {
		return customerService.updateCustomer(id, customerDTO);
	}

	@DeleteMapping(value = "/customers/{id}", produces = "application/json; charset=UTF-8" )
	public void deleteCustomer(@PathVariable("id") long id) {
		customerService.deleteCustomer(id);
	}

}
