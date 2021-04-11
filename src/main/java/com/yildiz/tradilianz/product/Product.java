package com.yildiz.tradilianz.product;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.yildiz.tradilianz.retailer.Retailer;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String productName;
	private String description;
	@NotNull
	private Double price;
	@NotBlank
	private String category;
	private String brand;
	private Integer quantity;
	@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private Set<Retailer> retailers = new HashSet<>();

	public Product() {

	}

	public Product(String productName, String description, Double price, String category, String brand, Integer quantity) {
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.category = category;
		this.brand = brand;
		this.quantity = quantity;
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
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Set<Retailer> getRetailers() {
		return retailers;
	}

	public void setRetailers(Set<Retailer> retailers) {
		this.retailers = retailers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Product product = (Product) o;
		return id.equals(product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return ("Produktnr: " + id + " Produktname: " + productName + " Produktbeschreibung: " + description
				+ " Kategorie: " + category + " Preis: " + price + " Marke: " + brand+" Menge:"+quantity);
	}

}
