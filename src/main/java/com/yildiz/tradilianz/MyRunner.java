package com.yildiz.tradilianz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.order.Order;
import com.yildiz.tradilianz.order.OrderDTO;
import com.yildiz.tradilianz.order.OrderDTOResponse;
import com.yildiz.tradilianz.order.OrderRepository;
import com.yildiz.tradilianz.order.OrderService;
import com.yildiz.tradilianz.order.OrderStatus;
import com.yildiz.tradilianz.product.Product;
import com.yildiz.tradilianz.product.ProductDTO;
import com.yildiz.tradilianz.product.ProductRepository;
import com.yildiz.tradilianz.product.ProductService;
import com.yildiz.tradilianz.retailer.Retailer;
import com.yildiz.tradilianz.retailer.RetailerRepository;

@Component
@Transactional
public class MyRunner implements CommandLineRunner {

	private final Logger log = LoggerFactory.getLogger(MyRunner.class);

	private CustomerRepository customerRepo;
	private RetailerRepository retailerRepo;
	private ProductRepository productRepo;
	private OrderRepository orderRepo;
	private ProductService productService;
	private OrderService orderService;
	private PasswordEncoder passEncoder;

	public MyRunner(CustomerRepository customerRepo, RetailerRepository retailerRepo, ProductRepository productRepo,
			OrderRepository orderRepo, ProductService productService, OrderService orderService,
			PasswordEncoder passEncoder) {
		this.customerRepo = customerRepo;
		this.retailerRepo = retailerRepo;
		this.productRepo = productRepo;
		this.productService = productService;
		this.orderService = orderService;
		this.passEncoder = passEncoder;
		this.orderRepo = orderRepo;
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		 * Customer section
		 */

		// save few customers
		Customer customer1 = customerRepo.save(new Customer("Jen300", passEncoder.encode("MySecretPass30!"), "Jennifer",
				"Lopez", "24.07.1969", "New York street 33", "New York City", "10100", "JenniferLopezNYC@gmail.com",
				"-", 150944.833, 2000, "ROLE_CUSTOMER"));
		Customer customer2 = customerRepo.save(
				new Customer("ECN2828", passEncoder.encode("Ercoo309"), "Ercan", "Yildiz", "20.02.19xx", "Titaniaweg x",
						"Leipzig", "04205", "ercan_xxx@hotmail.de", "0176217xxxx", 2240.50, 10, "ROLE_CUSTOMER"));
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
			Retailer retailerECN28 = new Retailer("ECN28 Store", "Leipzigstraße33", "04109", "Leipzig",
					"info@ecn28store.com", "mysupersecretPass", "01762938283xxx", 20880.99); // offers some products
			Retailer retailerSarah = new Retailer("Sarah Fashion", "Am Haferkamp", "28307", "Bremen",
					"info@sarahfashion.com", "mysupersecretPass", "01762938283xxx", 105889.93);
			// save retailers
			retailerRepo.save(retailerECN28);
			retailerRepo.save(retailerSarah);

			// fetch retailers

			Retailer savedRetailer = retailerRepo.findByname("ECN28 Store");
			log.info("Hier wird geprüft, ob findByname funktioniert!: " + savedRetailer);

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
			Map<Product, Integer> offeredProducts = new HashMap<>();
			offeredProducts.put(hose, 100);
			offeredProducts.put(hose1, 100);
			offeredProducts.put(hose2, 100);
			offeredProducts.put(hose3, 100);
			offeredProducts.put(hose4, 100);
			offeredProducts.put(hose5, 100);
			
			retailerECN28.setOfferedProducts(offeredProducts);

			var products= productRepo.findAll();
			Map<Product, Integer> offeredProducts2 = new HashMap<>();
			for(Product product: products) {
				offeredProducts2.put(product, 100);
			}

			retailerSarah.setOfferedProducts(offeredProducts2);

			retailerRepo.save(retailerSarah);

			// save retailer in repo
			retailerRepo.save(retailerECN28);

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

			/*
			 * Order repository
			 */
			/*
			 * Order order1 = new Order(OrderStatus.PENDING);
			 * order1.setRetailer(retailerECN28); order1.setCustomer(customer1);
			 * Map<Product, Integer> shoppingCart = new HashMap<>(); List<Product>
			 * productList = retailerECN28.getProducts();
			 * shoppingCart.put(productList.get(0), 4); shoppingCart.put(productList.get(1),
			 * 20); shoppingCart.put(productList.get(2), 10);
			 * shoppingCart.put(productList.get(3), 20);
			 * shoppingCart.put(productList.get(4), 5);
			 * 
			 * // calculate total amout shoppingCart double amount = 0; for
			 * (Map.Entry<Product, Integer> productMap: shoppingCart.entrySet()) { amount +=
			 * productMap.getKey().getPrice()*productMap.getValue(); }
			 * order1.setAmount(amount); order1.setShoppingCart(shoppingCart);
			 * 
			 * // check shoppingCart amount vs customer balance if (customer1.getBalance() >
			 * amount) { order1.setStatus(OrderStatus.CONFIRMED); order1.setBonuspoints(10);
			 * } else { order1.setStatus(OrderStatus.CANCELED); }
			 * orderRepo.saveAndFlush(order1);
			 * 
			 * log.info("Saved order"+orderRepo.findAll().toString());
			 * 
			 * 
			 * OrderService Section
			 * 
			 * 
			 * List<OrderDTOResponse> orderDTOs = orderService.getAllOrders();
			 * log.info("OrderDTOs: "+orderDTOs.toString());
			 * log.info("find order by customer: "+orderRepo.findBycustomerId(1L).toString()
			 * );
			 * log.info("find order by retailer: "+orderRepo.findByretailerId(7L).toString()
			 * );
			 */

		} catch (NoSuchElementException ex) {
			log.info(ex.getMessage());
		}

	}

}
