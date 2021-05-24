package com.yildiz.tradilianz.customer;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.yildiz.tradilianz.order.Order;

@Entity
@Table(name="customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(max = 20)
	private String username;
	@NotBlank
	@Size(max = 60)
	private String password;
	@Column(nullable = false)
	private String givenName;
	@Column(nullable = false)
	private String surname;
	private String birthday;
	private String streetAddress;
	private String city;
	private String postalCode;
	@Email
	@Column(updatable = false, nullable = false)
	private String email;
	private String phoneNumber;
	@CreationTimestamp
	private Timestamp timestamp;
	@Column(columnDefinition = "Decimal(10,2)")
	private Double balance;
	private Integer bonuspoints;
	private String role;

	@OneToMany(mappedBy = "customer")
	private Set<Order> orders;

	protected Customer() {

	}

	public Customer(String username, String password, String givenName, String surname, String birthday,
			String streetAddress, String city, String postalCode, String email, String phoneNumber, Double balance,
			Integer bonuspoints, String role) {
		this.username = username;
		this.password = password;
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
		this.role = role;
	}

	@Override
	public String toString() {
		return ("Benutzername: " + username + " Passwort: " + password + " Vorname: " + givenName + " Nachname: "
				+ surname + " Geburtstag: " + birthday + " Straße: " + streetAddress + " Stadt: " + city
				+ " Postleitzahl: " + postalCode + " E-Mail-Adresse: " + email + " Telefonnummer: " + phoneNumber
				+ " Kontostand: " + balance + " Bonuspunkte: " + bonuspoints + " Rolle:" + role);
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
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

	public String getRole() {
		return role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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
		if (balance < 0) {
		} else {
			this.balance = balance;
		}
	}

	public void setBonuspoints(Integer bonuspoints) {
		if(bonuspoints < 0) {
			
		}else {
			this.bonuspoints = bonuspoints;
		}
	}

	public void setRole(String role) {
		this.role = role;
	}

}
