package com.yildiz.tradilianz.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import com.yildiz.tradilianz.retailer.Retailer;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String productName;
	private String description;
	@NotBlank
	private Double price;
	@NotBlank
	private String category;
	private String brand;
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "productList")
	private List<Retailer> retailerList;

	protected Product() {

	}

	public Product( String productName, String description, Double price, String category, String brand,
			List<Retailer> retailerList) {
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.category = category;
		this.brand = brand;
		this.retailerList = retailerList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<Retailer> getRetailerList() {
		return retailerList;
	}

	public void setRetailerList(List<Retailer> retailerList) {
		this.retailerList = retailerList;
	}

}
