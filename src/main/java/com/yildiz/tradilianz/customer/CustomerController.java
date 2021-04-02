package com.yildiz.tradilianz.customer;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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

	@GetMapping("/customers")
	@PreAuthorize("hasRole('ROLE_RERTAILER')")
	public List<CustomerDTO> allCustomer() {
		return customerService.findAll();
	}

	@GetMapping("/customers/{id}")
	public CustomerDTO oneCustomer(@PathVariable("id") long id) {
		return customerService.findOneById(id);
	}

	@PostMapping("/customers")
	public CustomerDTO addCustomer(@Validated @RequestBody CustomerDTO customerDTO) throws Exception {
		return customerService.saveCustomer(customerDTO);
	}

	@PutMapping("/customers/{id}")
	public CustomerDTO updateCustomer(@PathVariable("id") long id, @Validated @RequestBody CustomerDTO customerDTO) {
		return customerService.updateCustomer(id, customerDTO);
	}

	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable("id") long id) {
		customerService.deleteCustomer(id);
	}

}