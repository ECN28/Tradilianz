package com.yildiz.tradilianz.retailer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.yildiz.tradilianz.order.Order;
import com.yildiz.tradilianz.product.Product;

@Entity
@Table(name = "retailers")
public class Retailer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(max = 30)
	private String name;
	private String streetAddress;
	private String postalCode;
	private String city;
	@Email
	private String email;
	@Size(max = 60)
	private String password;
	private String phoneNumber;
	private Double balance;
	@CreationTimestamp
	private Timestamp timestamp;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "retailers_products", joinColumns = {
			@JoinColumn(name = "retailer_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private List<Product> products = new ArrayList<>();
	
	@OneToMany (mappedBy="retailer")
	private Set<Order> orders;

	protected Retailer() {

	}

	public Retailer(String name, String streetAddress, String postalCode, String city, String email, String password,
			String phoneNumber, Double balance) {
		this.name = name;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Retailer retailer = (Retailer) o;
		return id.equals(retailer.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return (" Händlernr: " + id + " Händlername: " + name + " Straße: " + streetAddress + " Postleitzahl: "
				+ postalCode + " Stadt: " + city + " E-Mail: " + email + " Passwort: " + password + " Telefonnummer: "
				+ phoneNumber +"Kontostand: "+balance+ " Zeitstempel+ " + timestamp);
	}

}
