package com.yildiz.tradilianz.customer;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull(message = "given name is mandatory")
	private String givenName;
    @NotNull(message = "surname is mandatory")
	private String surname;
	private String birthday;
	private String streetAddress;
	private String city;
	private String postalCode;
    @NotNull(message = "email is mandatory")
	private String email;
	private String phoneNumber;
	@CreationTimestamp
	private Timestamp timestamp;
	private Double balance;
	private Integer bonuspoints;
	
	protected Customer () {
		
	}
	
	public Customer(String givenName, String surname, String birthday, String streetAddress, 
			String city, String postalCode, String email, String phoneNumber,Double balance, Integer bonuspoints) {
		this.givenName = givenName;
		this.surname = surname;
		this.birthday = birthday;
		this.streetAddress = streetAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		this.bonuspoints = bonuspoints;
	}
	
	@Override
	public String toString() {
		return("Vorname: "+givenName+" Nachname: "+surname+" Geburtstag: "+
				birthday+" Straße: "+streetAddress+" Stadt: "+city+" Postleitzahl: "+
				postalCode+" E-Mail-Adresse: "+email+" Telefonnummer: "+phoneNumber+
				"Kontostand: "+balance+" Bonuspunkte: "+bonuspoints);
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
	
	public Double getBalance() {
		return balance;
	}
	
    public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setBonuspoints(Integer bonuspoints) {
		this.bonuspoints = bonuspoints;
	}

}
