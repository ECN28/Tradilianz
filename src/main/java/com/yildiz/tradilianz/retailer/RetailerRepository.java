package com.yildiz.tradilianz.retailer;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailerRepository extends PagingAndSortingRepository<Retailer, Long> {
	
	Retailer findByName(String name);
	

}
