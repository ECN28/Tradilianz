package com.yildiz.tradilianz.retailer;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailerRepository extends PagingAndSortingRepository<Retailer, Long> {
	
	Retailer findByname(String name);
	Retailer findByid(Long id);
	Set<Retailer> findBypostalCode(String postalCode);
	

}
