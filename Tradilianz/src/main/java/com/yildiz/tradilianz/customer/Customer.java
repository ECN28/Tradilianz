package com.yildiz.tradilianz.customer;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String givenName;
	private String surname;
	private String birthday;
	private String streetAddress;
	private String city;
	private String postalCode;
	private String email;
	private String phoneNumber;
	@CreationTimestamp
	private Timestamp timestamp;
	private Integer bonuspoints;
	
	protected Customer () {
		
	}
	
	public Customer(String givenName, String surname, String birthday, String streetAddress, 
			String city, String postalCode, String email, String phoneNumber, Integer bonuspoints) {
		this.givenName = givenName;
		this.surname = surname;
		this.birthday = birthday;
		this.streetAddress = streetAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.bonuspoints = bonuspoints;
	}
	
	@Override
	public String toString() {
		return("Vorname: "+givenName+" Nachname: "+surname+" Geburtstag: "+
				birthday+" Straße: "+streetAddress+" Stadt: "+city+" Postleitzahl: "+
				postalCode+" E-Mail-Adresse: "+email+" Telefonnummer: "+phoneNumber+
				" Bonuspunkte: "+bonuspoints);
	}
	
	public Long getId() {
		return id;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getSurname() {
		return surname;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public Integer getBonuspoints() {
		return bonuspoints;
	}
	

}