package com.yildiz.tradilianz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.product.ProductRepository;
import com.yildiz.tradilianz.retailer.RetailerRepository;

@Component
public class MyRunner implements CommandLineRunner {

	private final Logger log = LoggerFactory.getLogger(MyRunner.class);

	private final CustomerRepository customerRepo;
	private final RetailerRepository retailerRepo;
	private final ProductRepository productRepo;

	public MyRunner(CustomerRepository customerRepo, RetailerRepository retailerRepo, ProductRepository productRepo) {
		this.customerRepo = customerRepo;
		this.retailerRepo = retailerRepo;
		this.productRepo = productRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * Customer section
		 */
		
		// save few customers
		customerRepo.save(new Customer("Jen300", "MySecretPass", "Jennifer", "Lopez", "24.07.1969", "unknown",
				"New York City", "10100", "JenniferLopezNYC@gmail.com", "-", 91500252.34, 2000, "ROLE_CUSTOMER"));
		customerRepo.save(new Customer("ECN2828", "Ercoo309", "Ercan", "Yildiz", "20.02.19xx", "Titaniaweg x",
				"Leipzig", "04205", "ercan_xxx@hotmail.de", "0176217xxxx", 6.50, 10, "ROLE_CUSTOMER"));
		try {
			// fetch all customers
			log.info("-------------------------------");
			log.info("Customers found with findAll():");
			for (Customer customer : customerRepo.findAll()) {
				log.info(customer.toString());
			}
			log.info("-------------------------------");
		} catch (NullPointerException ex) {
			log.info(ex.getMessage());
		}

		try {
			// fetch an individual customer by ID. if Id is not valid exception Message is
			// thrown
			log.info("--------------------------------");
			log.info("Customer found with findById(1L):");
			Customer customer = customerRepo.findById(1L);
			log.info(customer.toString());
			log.info("-------------------------------");
		} catch (NullPointerException ex) {
			log.info(ex.getMessage());
		}

		try {
			// fetch an individual customer by EMAIL
			log.info("--------------------------------");
			log.info("Customer found with findbyEmail(email)");
			Customer customer = customerRepo.findByEmail("ercan_xxx@hotmail.de");
			log.info(customer.toString());
			log.info("-------------------------------");
		} catch (NullPointerException ex) {
			log.info(ex.getMessage());
		}
		
		/*
		 * Retailer section
		 */
		
	}

}
