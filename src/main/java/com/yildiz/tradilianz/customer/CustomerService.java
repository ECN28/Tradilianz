package com.yildiz.tradilianz.customer;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yildiz.tradilianz.exception.CustomerNotFoundException;
import com.yildiz.tradilianz.exception.NoDataFoundException;

@Service
@Transactional
public class CustomerService {

	private CustomerRepository customerRepository;
	private CustomerDTO customerDTO;
	private ModelMapper modelMapper;
	
	public CustomerService(CustomerRepository customerRepository, CustomerDTO customerDTO, ModelMapper modelMapper) {
		this.customerRepository = customerRepository;
		this.customerDTO = customerDTO;
		this.modelMapper = modelMapper;
	}
	

	private final Logger log = LoggerFactory.getLogger(CustomerService.class);

	// Gebe Liste von Kunden zurück
	public List<CustomerDTO> findAll() {
		var it = customerRepository.findAll();
		var customerList = new ArrayList<CustomerDTO>();
		for (Customer customer : it) {
			customerDTO = convertToDto(customer);
			customerList.add(customerDTO);
		}

		if (customerList.isEmpty()) {
			throw new NoDataFoundException();
		} else {
			return customerList;
		}

	}

	// Gebe einen bestimmten Kunden zurück
	public CustomerDTO findOneById(long id) {
		Customer customer = customerRepository.findById(id);

		if (customer == null) {
			throw new CustomerNotFoundException(id);
		} else {
			CustomerDTO customerDTO = convertToDto(customer);
			return customerDTO;
		}
	}

	// Speicher einen Kunden in der Datenbank und gebe diesen zurück
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		if (customerDTO != null) {
			customerDTO.setPassword(customerDTO.getPassword());
			customerDTO.setRole("ROLE_CUSTOMER");
			Customer savedObject = customerRepository.save(convertToEntity(customerDTO));
			CustomerDTO responseCustomer = convertToDto(customerRepository.findById(savedObject.getId()).get());
			return responseCustomer;
		} else {
			log.info("Kunden speichern in die Datenbank fehlgeschlagen");
			return null;
		}
	}

	// Kundendaten bearbeiten
	public CustomerDTO updateCustomer(long id, CustomerDTO customerDTO) {
		Customer customer = customerRepository.findById(id);
		if (customer == null) {
			throw new CustomerNotFoundException(id);
		} else {
			customer.setUsername(customerDTO.getUsername());
			customer.setPassword(customerDTO.getPassword());
			customer.setSurname(customerDTO.getSurname());
			customer.setGivenName(customerDTO.getGivenName());
			customer.setBalance(customerDTO.getBalance());
			customer.setBirthday(customerDTO.getBirthday());
			customer.setBonuspoints(customerDTO.getBonuspoints());
			customer.setCity(customerDTO.getCity());
			customer.setPhoneNumber(customerDTO.getPhoneNumber());
			customer.setPostalCode(customerDTO.getPostalCode());
			customer.setStreetAddress(customerDTO.getStreetAddress());
			customer.setPassword(customerDTO.getPassword());
			customer.setRole(customerDTO.getRole());
			Customer responseCustomer = customerRepository.save(customer);
			return convertToDto(responseCustomer);
		}
	}

	// Lösche Kunden aus der Datenbank
	public void deleteCustomer(long id) {
		Customer customer = customerRepository.findById(id);
		if (customer == null) {
			throw new CustomerNotFoundException(id);
		} else {
			customerRepository.deleteById(id);
		}

	}

	// Umwandlung von Entity zu DTO Objekt
	public CustomerDTO convertToDto(Customer customer) {
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		return customerDTO;
	}

	// Umwandlung von DTO zu Entity Objekt
	private Customer convertToEntity(CustomerDTO customerDTO) {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		return customer;
	}

}
