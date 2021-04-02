package com.yildiz.tradilianz.product;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	Product findByproductName(String productName);
	Optional<Set<Product>> findBypriceGreaterThan(double price);

	boolean existsByproductName(String productName);

	Page<Product> findAll(Pageable pageable);
	Page<Product> findByCategory(String category, Pageable pageable);
	

}
