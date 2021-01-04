package com.yildiz.tradilianz.customer;

import java.sql.Timestamp;

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
	private String givenName;
	private String surname;
	private String birthday;
	private String streetAddress;
	private String city;
	private String postalCode;
	private String email;
	private String phoneNumber;
	private Timestamp timestamp;
	private Integer bonuspoints;
}
