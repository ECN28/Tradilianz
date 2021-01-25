package com.yildiz.tradilianz.security.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerDTO;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.exception.CustomerNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerDTO customerDTO;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Check for username or email passed through username parameter
		boolean valid = EmailValidator.getInstance().isValid(username);
		if (valid == false) {

			/*
			 * Build with UserDetailsImpl but if user for Customer class is null I want to
			 * try another repository and do something like this: Retailer user =
			 * retailerRepository.findByUsername(username); And if it is not null it should
			 * pass user Object which class is Retailer and so on.
			 * 
			 */
			Customer user = customerRepository.findByUsername(username);

			return CustomerDetailsImpl.build(user);

		} else {
			Customer user = customerRepository.findByEmail(username);

			return CustomerDetailsImpl.build(user);

		}
	}
}
