package com.yildiz.tradilianz.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yildiz.tradilianz.auth.request.LoginRequest;
import com.yildiz.tradilianz.customer.CustomerDTO;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.security.jwt.JwtUtils;;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth/customer")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		return authService.loginUser(loginRequest);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody CustomerDTO customerDTO){
		return authService.signupUser(customerDTO);
}
}