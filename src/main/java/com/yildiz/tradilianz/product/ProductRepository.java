package com.yildiz.tradilianz.product;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	Set<Product> findAll();
	
	Product findByid(Long id);
	
	Set<Product> findByproductName(String productName);
	Set<Product> findBycategory(String category);
	Set<Product> findBypriceGreaterThan(double price);
	Set<Product> findBypriceBetween(double startPrice, double endPrice);
	Set<Product> findBypriceLessThan(double price);

	boolean existsByproductName(String productName);

	Page<Product> findAll(Pageable pageable);
	Page<Product> findByCategory(String category, Pageable pageable);
	
}
