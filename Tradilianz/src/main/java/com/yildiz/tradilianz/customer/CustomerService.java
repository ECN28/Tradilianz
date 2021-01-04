package com.yildiz.tradilianz.customer;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
	
	public List<CustomerDTO> findAll(){
		
		var it = customerRepository.findAll();
		var customerList = new ArrayList<CustomerDTO>();
		for(Customer customer: it) {
			customerDTO = convertToDto(customer);
			customerList.add(customerDTO);
		}
		return customerList;
	}
	
	private CustomerDTO convertToDto(Customer customer) {
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		return customerDTO;
	}


}
