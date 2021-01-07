package com.yildiz.tradilianz.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PutMapping("/customers/{id}")
	public CustomerDTO updateCustomer(@PathVariable("id") long id, @RequestBody CustomerDTO customerDTO) {
		return customerService.updateCustomer(customerDTO, id);
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable("id") long id) {
		 customerService.deleteCustomer(id);
	}

}
