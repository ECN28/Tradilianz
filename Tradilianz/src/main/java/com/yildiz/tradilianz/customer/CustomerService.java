package com.yildiz.tradilianz.customer;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerDTO customerDTO;

	@Autowired
	private ModelMapper modelMapper;

	private final Logger log = LoggerFactory.getLogger(CustomerService.class);

	// Gebe Liste von Kunden zurück
	public List<CustomerDTO> findAll() {

		var it = customerRepository.findAll();
		var customerList = new ArrayList<CustomerDTO>();
		for (Customer customer : it) {
			customerDTO = convertToDto(customer);
			customerList.add(customerDTO);
		}
		return customerList;
	}

	// Gebe einen bestimmten Kundne zurück
	public CustomerDTO findOneById(long id) {
		Customer customer = customerRepository.findById(id);
		CustomerDTO customerDTO = convertToDto(customer);
		return customerDTO;
	}

	// Speicher einen Kunden in der Datenbank und gebe diesen zurück
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		if (customerDTO != null) {
			Customer customer = convertToEntity(customerDTO);
			log.info("Customer Entity nach Konventierung: "+customer.toString());
			customerRepository.save(customer);

			// Abrufen der gespeicherten Entity und Umwandlung in DTO
			CustomerDTO responseCustomer = convertToDto(customer);
			return responseCustomer;
		} else {
			log.info("Kunden speichern in die Datenbank fehlgeschlagen");
			return null;
		}
	}

	//Umwandlung von Entity zu DTO Objekt
	public CustomerDTO convertToDto(Customer customer) {
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		return customerDTO;
	}

	//Umwandlung von DTO zu Entity Objekt
	private Customer convertToEntity(CustomerDTO customerDTO) {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		return customer;
	}

}
