package com.yildiz.tradilianz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.product.Product;
import com.yildiz.tradilianz.product.ProductDTO;
import com.yildiz.tradilianz.product.ProductRepository;
import com.yildiz.tradilianz.product.ProductService;
import com.yildiz.tradilianz.retailer.Retailer;
import com.yildiz.tradilianz.retailer.RetailerRepository;

@Component
public class MyRunner implements CommandLineRunner {

	private final Logger log = LoggerFactory.getLogger(MyRunner.class);

	private final CustomerRepository customerRepo;
	private final RetailerRepository retailerRepo;
	private final ProductRepository productRepo;
	private final ProductService productService;

	public MyRunner(CustomerRepository customerRepo, RetailerRepository retailerRepo, ProductRepository productRepo,
			ProductService productService) {
		this.customerRepo = customerRepo;
		this.retailerRepo = retailerRepo;
		this.productRepo = productRepo;
		this.productService = productService;
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
			Retailer retailer1 = new Retailer("Sarah Fashion", "Am Haferkamp", "28307", "Bremen",
					"info@sarahfashion.com", "mysupersecretPass", "01762938283xxx");
			// save retailers
			retailerRepo.save(retailer);
			retailerRepo.save(retailer1);

			// create some products
			Product hose = new Product("TT Jeans", "Slim Fit Jeans von Tom Tailor", 59.99, "Jeans", "Tom Tailor");
			Product hose1 = new Product("Nike HerrenHose", "Slim Fit Jeans von Nike", 89.99, "Jeans", "Nike");
			Product hose2 = new Product("Nike DamenHose", "Slim Fit Jeans von Nike", 89.99, "Jeans", "Nike");
			Product hose3 = new Product("HoseHose", "Slim Fit Jeans von Hose", 19.99, "Jeans", "Hose");
			Product hose4 = new Product("G&G Herrenhose", "Slim Fit Jeans von G&G", 9.99, "Jeans", "Tom Tailor");
			Product hose5 = new Product("ECN28 Jeans", "Slim Fit Jeans von ECN28", 159.99, "Jeans", "ECN28");

			// many other products created in data.sql... --> look up classpath
			// (src/main/resources/data.sql)!

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

			// fetch all products greather than 20€
			log.info("Products which cost more than 20€ \n" + productRepo.findBypriceGreaterThan(20.00).toString());

			// fetch all products between 20€ and 50€
			log.info("Products which cost between 0€ and 60€ \n" + productRepo.findBypriceBetween(0, 60.00).toString());

			// fetch all products less than 100€
			log.info("Products which cost less than 100€ \n" + productRepo.findBypriceLessThan(100.00).toString());

			/*
			 * Product Service
			 */

			// return all products as DTO
			Set<ProductDTO> productDTOs = productService.findAllProducts();
			log.info("All products as DTOs from Service \n" + productDTOs);

			// return one product by id as DTO
			ProductDTO productDTO = productService.findOneById(2L);
			log.info("One ProductDTO by Id from Service \n" + productDTO);
			ProductDTO productDTO4 = productService.findOneById(4L);
			log.info("One ProductDTO by Id from Service \n" + productDTO4);

			// return one product by name as DTO
			Set<ProductDTO> productDTOname = productService.findByName("G&G Herrenhose");
			log.info("One ProductDTO by name from Service \n" + productDTOname);

			// return products greater than param price as DTO
			Set<ProductDTO> productDTOsGreater = productService.findByPriceGreaterThan(50.00);
			log.info("Product DTOs from Service greater than 50€\n" + productDTOsGreater);

			// return products less than param price as DTO
			Set<ProductDTO> porductDTOsLess = productService.findByPriceLessThan(50.00);
			log.info("Product DTOs from Service less than 50€\n" + porductDTOsLess);

			// return products between param prices as DTO
			Set<ProductDTO> productDTOsBetween = productService.findByPriceBetween(40.00, 89.99);
			log.info("Product DTOs from Service between 40 - 89.99€\n" + productDTOsBetween);

			// return products equal to param category
			log.info("Product DTOs from Service in category Schuhe" + productService.findByCategory("Schuhe"));

		} catch (NoSuchElementException ex) {
			log.info(ex.getMessage());
		}

	}

}
