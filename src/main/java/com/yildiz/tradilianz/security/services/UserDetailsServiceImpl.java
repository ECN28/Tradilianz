package com.yildiz.tradilianz.security.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	private CustomerRepository customerRepository;

	public UserDetailsServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer user;
		// Check for username or email passed through username parameter
		boolean valid = EmailValidator.getInstance().isValid(username);
		if (valid == false) {
			user = customerRepository.findByUsername(username);
		} else {
			user = customerRepository.findByEmail(username);
		}
		return CustomerDetailsImpl.build(user);
	}
}
