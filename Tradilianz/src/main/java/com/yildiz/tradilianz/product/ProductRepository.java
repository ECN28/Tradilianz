package com.yildiz.tradilianz.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>  {
	
	Product findByproductName(String productName);
	boolean existsByproductName(String productName);

	Page<Product> findAll(Pageable pageable);
	
	

}
