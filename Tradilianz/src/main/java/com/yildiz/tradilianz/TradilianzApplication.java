package com.yildiz.tradilianz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;

@SpringBootApplication
public class TradilianzApplication {
	
	private static final Logger log = 
			LoggerFactory.getLogger(TradilianzApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TradilianzApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) ->{
			//save few customers
			repository.save(new Customer("Jennifer", "Lopez", "24.07.1969", "unknown", "New York City","10100", "JenniferLopezNYC@gmail.com", "-", 2000));
			repository.save(new Customer("Ercan", "Yildiz", "20.02.19xx", "Titaniaweg x", "Leipzig","04205", "ercan_xxx@hotmail.de", "0176217xxxx", 10));
		};
	}

}
