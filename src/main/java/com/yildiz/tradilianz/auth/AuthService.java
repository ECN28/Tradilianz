package com.yildiz.tradilianz.auth;

import org.modelmapper.internal.util.Iterables;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yildiz.tradilianz.auth.request.LoginRequest;
import com.yildiz.tradilianz.auth.response.JwtResponse;
import com.yildiz.tradilianz.auth.response.MessageResponse;
import com.yildiz.tradilianz.customer.CustomerDTO;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.customer.CustomerService;
import com.yildiz.tradilianz.security.jwt.JwtUtils;
import com.yildiz.tradilianz.security.services.CustomerDetailsImpl;

@Service
public class AuthService {

	private AuthenticationManager authenticationManager;
	private CustomerRepository customerRepository;
	private PasswordEncoder encoder;
	private JwtUtils jwtUtils;
	private CustomerService customerService;

	public AuthService(AuthenticationManager authenticationManager, CustomerRepository customerRepository,
			PasswordEncoder encoder, JwtUtils jwtUtils, CustomerService customerService) {

		this.authenticationManager = authenticationManager;
		this.customerRepository = customerRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
		this.customerService = customerService;
	}

	public ResponseEntity<Object> loginUser(LoginRequest loginRequest) {
		Authentication authentication;
		if (loginRequest.getUsername() != null && loginRequest.getUsername() != "") {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		} else if (loginRequest.getEmail() != null && loginRequest.getEmail() != "") {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} else {
			if (loginRequest.getUsername() == "" && loginRequest.getEmail() == null) {
				return ResponseEntity.badRequest().body(
						new MessageResponse("Error: Username cannot be blank whitespace! Type in your username pls."));
			} else if (loginRequest.getEmail() == "" && loginRequest.getUsername() == null) {
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Error: Email cannot be blank whitespace! Type in your email pls."));
			} else {
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Error: You have to login with e-mail or username"));
			}
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		CustomerDetailsImpl userDetails = (CustomerDetailsImpl) authentication.getPrincipal();
		String role = Iterables.getElement(userDetails.getAuthorities(), 0).toString();

		return ResponseEntity
				.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), role));

	}

	public ResponseEntity<Object> signupUser(CustomerDTO customerDTO) {

		if (customerRepository.existsByUsername(customerDTO.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (customerRepository.existsByEmail(customerDTO.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		customerDTO.setPassword(encoder.encode(customerDTO.getPassword()));
		customerDTO.setRole("ROLE_CUSTOMER");
		CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
		return ResponseEntity
				.ok(new MessageResponse("User " + customerDTO.getUsername() + " registered successfully!"));

	}

}
