package com.yildiz.tradilianz.auth;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yildiz.tradilianz.auth.request.LoginRequest;
import com.yildiz.tradilianz.customer.CustomerDTO;;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth/customer")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {		
		this.authService = authService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.loginUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody CustomerDTO customerDTO) {
		return authService.signupUser(customerDTO);
	}
}