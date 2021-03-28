package com.yildiz.tradilianz.retailer;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.yildiz.tradilianz.product.Product;

@Entity
public class Retailer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@CreationTimestamp
	private Timestamp timestamp;
	@Column(name="productList")
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Product> productList;

	protected Retailer() {

	}

	public Retailer(@NotBlank @Size(max = 30) String name, String streetAddress, String postalCode,
			String city, @Email String email, @Size(max = 60) String password, String phoneNumber, 
			List<Product> productsList) {
		this.name = name;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.productList = productsList;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<Product> getProductsList() {
		return productList;
	}

	public void setProductsList(List<Product> productsList) {
		this.productList = productsList;
	}

}
