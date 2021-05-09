package com.yildiz.tradilianz.order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.product.Product;
import com.yildiz.tradilianz.retailer.Retailer;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderNumber;
	@Column( columnDefinition="Decimal(10,2)")
	private Double amount;
	private Integer bonuspoints;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "shoppingCart_products", joinColumns = {
			@JoinColumn(name = "shoppingCart_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private List<Product> shoppingCart = new ArrayList<>();
	@CreationTimestamp
	private Timestamp orderDate;
	// Ein Kunde kann n Bestellungen aufgeben, eine Bestellung ist immer genau einem
	// Kunden zugeordnet
	@ManyToOne
	private Customer customer;
	// Einem Händler können mehrere Bestellungen zugeordenet sein, eine Bestellung
	// ist immer genau zu einem Händler zugeordnet
	@ManyToOne
	private Retailer retailer;

	protected Order() {
	}

	public Order(String orderNumber, OrderStatus status) {
		this.orderNumber = orderNumber;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getBonuspoints() {
		return bonuspoints;
	}

	public void setBonuspoints(Integer bonuspoints) {
		this.bonuspoints = bonuspoints;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<Product> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(List<Product> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp timestamp) {
		this.orderDate = timestamp;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Order order = (Order) o;
		return id.equals(order.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return ("Id: " + id + " Bestellnummer:" + orderNumber + " Händlernr: " + retailer.getId() + " Kundennr:  "
				+ customer.getId() + " Summe:" + amount + " Bonuspunkte vergeben:" + bonuspoints + " Status:" + status
				+ " ProduktListe" + shoppingCart + " Zeitstempel" + orderDate);
	}

}
