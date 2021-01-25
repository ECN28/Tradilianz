package com.yildiz.tradilianz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.yildiz.tradilianz.auth.ERole;
import com.yildiz.tradilianz.auth.Role;
import com.yildiz.tradilianz.auth.RoleRepository;
import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.customer.CustomerService;

@SpringBootApplication
public class TradilianzApplication {

	private static final Logger log = LoggerFactory.getLogger(TradilianzApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TradilianzApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save few customers
			repository.save(new Customer("Jen300", "MySecretPass","Jennifer", "Lopez", "24.07.1969", "unknown", "New York City", "10100",
					"JenniferLopezNYC@gmail.com", "-", 91500252.34, 2000));
			repository.save(new Customer("ECN2828", "Ercoo309","Ercan", "Yildiz", "20.02.19xx", "Titaniaweg x", "Leipzig", "04205",
					"ercan_xxx@hotmail.de", "0176217xxxx", 6.50, 10));

			try {
				// fetch all customers
				log.info("Customers found with findAll():");
				log.info("-------------------------------");
				for (Customer customer : repository.findAll()) {
					log.info(customer.toString());
				}
				log.info("");
			} catch (NullPointerException ex) {
				log.info(ex.getMessage());
			}

			try {
				// fetch an individual customer by ID. if Id is not valid exception Message is
				// thrown
				Customer customer = repository.findById(3L);
				log.info("Customer found with findById(1L):");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			} catch (NullPointerException ex) {
				log.info(ex.getMessage());
			}

			try {
				// fetch an individual customer by EMAIL
				Customer customer = repository.findByEmail("ercan_xxx@hotmail.de");
				log.info("Customer found with findbyEmail(email)");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			} catch (NullPointerException ex) {
				log.info(ex.getMessage());
			}

		};
	}

	@Bean
	public CommandLineRunner demoService(CustomerService service) {
		return (args) -> {
			// fetch all customer with service
			log.info(service.findAll().toString());
		};
	}
	
	@Bean
	public CommandLineRunner insertRoles(RoleRepository roleRepository) {
		return (args) -> {
			log.info("Insert Roles into Table");
			Role role1 = roleRepository.save(new Role(ERole.ROLE_USER));
			Role role2 = roleRepository.save(new Role(ERole.ROLE_MODERATOR));
			Role role3 = roleRepository.save(new Role(ERole.ROLE_ADMIN));
			log.info("Role"+ role1.getId()+": "+role1.getName());
			log.info("Role"+ role2.getId()+": "+role2.getName());
			log.info("Role"+ role3.getId()+": "+role3.getName());
		};
	}

}
