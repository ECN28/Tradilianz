package com.yildiz.tradilianz.customer;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.Data;

/*
 * Dank lombok müssen wir für die CustomerDTO Klasse keine Getter & Setter deklarieren. Außerdem werden die
 * Methoden Object::toString, Object::equals and Object::hashCode automatisch überschrieben.
 */

@Component
@Data
public class CustomerDTO {

	private Long id;
	@NotBlank
	private String givenName;
	@NotBlank
	private String surname;
	private String birthday;
	private String streetAddress;
	private String city;
	private String postalCode;
	@NotBlank
	@Email
	private String email;
	private String phoneNumber;
	private Timestamp timestamp;
	private Double balance;
	private Integer bonuspoints;
}
