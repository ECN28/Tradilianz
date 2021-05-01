package com.yildiz.tradilianz.retailer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class RetailerDTO {
	
	private Long id;
	@NotBlank
	private String name;
	private String streetAddress;
	private String postalCode;
	private String city;
	@NotBlank
	@Email
	private String email;
	private String password;
	private String phoneNumber;
	private String role;

}
