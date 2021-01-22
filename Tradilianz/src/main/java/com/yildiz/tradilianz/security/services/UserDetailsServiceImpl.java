package com.yildiz.tradilianz.security.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yildiz.tradilianz.auth.User;
import com.yildiz.tradilianz.auth.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Check for username or email passed through username parameter
		boolean valid = EmailValidator.getInstance().isValid(username);
		if (valid == false) {
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

			return UserDetailsImpl.build(user);
			
		} else {
			User user = userRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

			return UserDetailsImpl.build(user);
			
		}
	}

}
