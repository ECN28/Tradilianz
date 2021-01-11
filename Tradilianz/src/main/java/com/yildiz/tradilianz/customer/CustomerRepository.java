package com.yildiz.tradilianz.customer;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	/*
	 * Here we can create our custom search queries on CustomerRepository
	 */

	List<Customer> findBySurname(String surname);

	Customer findById(long id);

	Customer findByEmail(String email);

	/*
	 * //Update Customer workaround email field ConstraintViolationException
	 * 
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query("UPDATE Customer c SET c.givenName = :#{#customer.givenName}, c.surname = :#{#customer.surname}, c.birthday= :#{#customer.birthday},"
	 * +
	 * " c.streetAddress = :#{#customer.streetAddress}, c.city = :#{#customer.city}, c.postalCode = :#{#customer.postalCode},"
	 * +
	 * " c.phoneNumber = :#{#customer.phoneNumber}, c.balance= :#{#customer.balance}, c.bonuspoints= :#{#customer.bonuspoints}"
	 * + " WHERE c.id = :#{#id} ") void updateCustomerByDTO(@Param("customer")
	 * Customer customer, @Param("id") Long id);
	 * 
	 * Instead above code we use @Column(updatable = false) in Customer Entity and
	 * use the save() method from our Interface CustomerRepository
	 */

}