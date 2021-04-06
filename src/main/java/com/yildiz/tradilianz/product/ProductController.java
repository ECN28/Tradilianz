package com.yildiz.tradilianz.product;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE )
	public Set<ProductDTO> getAllProducts() {
		return productService.findAllProducts();
	}
	
	@GetMapping(value = "/products/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDTO getOneProduct(@PathVariable("id") Long Id) {
		return productService.findOneById(Id);
	}
	
	@GetMapping(value = "/products/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductDTO> getOneProductByName(@PathVariable("name") String name){
		return productService.findByName(name);
	}
	
	@GetMapping(value = "/products/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductDTO> getAllProductsByCategory(@PathVariable("category") String category){
		return productService.findByCategory(category);
	}
	
	@GetMapping(value="/products/pricesBetween", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductDTO> getAllProductsBetweenPrice(@RequestParam(name = "startPrice", required = true) Double startPrice, @RequestParam(name ="endPrice", required = true) Double endPrice){
		return productService.findByPriceBetween(startPrice, endPrice);
	}
	
	@GetMapping(value="/products/pricesLess", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductDTO> getAllProductsLessThanPrice(@RequestParam(name="price", required = true) Double price){
		return productService.findByPriceLessThan(price);
	}
	
}
