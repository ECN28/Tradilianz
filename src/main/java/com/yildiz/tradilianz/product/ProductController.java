package com.yildiz.tradilianz.product;

import java.util.Set;

import org.hibernate.engine.spi.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping(value="/products/priceBetween", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductDTO> getAllProductsBetweenPrice(@RequestParam(name = "startPrice", required = true) Double startPrice, @RequestParam(name ="endPrice", required = true) Double endPrice){
		return productService.findByPriceBetween(startPrice, endPrice);
	}
	
	@GetMapping(value="/products/priceLess", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductDTO> getAllProductsLessThanPrice(@RequestParam(name="price", required = true) Double price){
		return productService.findByPriceLessThan(price);
	}
	
	@GetMapping(value="/products/priceGreater", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductDTO> getAllProductsGreaterThanPrice(@RequestParam(name="price", required = true) Double price){
		return productService.findByPriceGreaterThan(price);
	}
	
	@PostMapping(value="/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDTO saveProduct(@Validated @RequestBody ProductDTO productDTO) {
		return productService.saveProduct(productDTO);
	}
	
	@PutMapping(value="/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDTO updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
		return productService.updateProduct(id, productDTO);
	}
	
	@DeleteMapping(value="/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deleteProduct(@PathVariable("id") Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
}
