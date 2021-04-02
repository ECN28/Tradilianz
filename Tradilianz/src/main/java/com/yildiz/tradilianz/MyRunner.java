package com.yildiz.tradilianz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.product.Product;
import com.yildiz.tradilianz.product.ProductRepository;
import com.yildiz.tradilianz.retailer.Retailer;
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
		 * retailer & products section
		 */

		try {

			// create some retailer
			Retailer retailer = new Retailer("ECN28 Store", "Leipzigstraße33", "04109", "Leipzig",
					"info@ecn28store.com", "mysupersecretPass", "01762938283xxx"); // offers some products
			Retailer retailer1 = new Retailer("Mueller Elektronik", "Am Haferkamp", "28307", "Bremen",
					"info@muellertronik.com", "mysupersecretPass", "01762938283xxx");
			// save the retailer
			retailerRepo.save(retailer);

			// create some products
			Product hose = new Product("TT Jeans", "Slim Fit Jeans von Tom Tailor", 59.99, "Jeans", "Tom Tailor");
			Product hose1 = new Product("Nike HerrenHose", "Slim Fit Jeans von Nike", 89.99, "Jeans", "Nike");
			Product hose2 = new Product("Nike DamenHose", "Slim Fit Jeans von Nike", 89.99, "Jeans", "Nike");
			Product hose3 = new Product("HoseHose", "Slim Fit Jeans von Hose", 19.99, "Jeans", "Hose");
			Product hose4 = new Product("G&G Herrenhose", "Slim Fit Jeans von G&G", 9.99, "Jeans", "Tom Tailor");
			Product hose5 = new Product("ECN28 Jeans", "Slim Fit Jeans von ECN28", 159.99, "Designer Jeans", "ECN28");

			// save products in repository
			productRepo.saveAll(Arrays.asList(hose, hose1, hose2, hose3, hose4, hose5));

			// add products to retailer
			Set<Product> products = new HashSet<>();
			products.addAll(Arrays.asList(hose, hose1, hose2, hose3, hose4, hose5));
			retailer.setProducts(products);

			// save retailer in repo
			retailerRepo.save(retailer);

			// fetch some data and print in console
			log.info(retailerRepo.findAll().toString());
			log.info(productRepo.findById(1L).get().toString());
			log.info(productRepo.findById(2L).get().toString());
			log.info(productRepo.findById(3L).get().toString());
			log.info(productRepo.findById(4L).get().toString());
			log.info(productRepo.findById(5L).get().toString());

		} catch (NullPointerException ex) {
			log.info(ex.getMessage());
		}

	}

}
