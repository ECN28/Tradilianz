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
		Customer user = null;
		// Check for username or email passed through username parameter
		boolean valid = EmailValidator.getInstance().isValid(username);
		if (valid == false) {
			try {
				user = customerRepository.findByUsername(username);
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}

		} else {
			try {
				user = customerRepository.findByEmail(username);

			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}

		}
		return CustomerDetailsImpl.build(user);
	}
}
