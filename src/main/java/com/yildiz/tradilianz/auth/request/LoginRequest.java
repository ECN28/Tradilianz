package com.yildiz.tradilianz.auth.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String username;

	@NotBlank
	private String password;
	
	private String email;

}