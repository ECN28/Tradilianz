package com.yildiz.tradilianz.product;

import java.util.Set;

import org.springframework.http.HttpStatus;
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
	
	@GetMapping(value = "/products", produces = "application/json; charset=UTF-8" )
	public Set<ProductDTO> getAllProducts() {
		return productService.findAllProducts();
	}
	
	@GetMapping(value = "/products/{id}", produces = "application/json; charset=UTF-8")
	public ProductDTO getOneProduct(@PathVariable("id") Long Id) {
		return productService.findOneById(Id);
	}
	
	@GetMapping(value = "/products", params = "name", produces = "application/json; charset=UTF-8")
	public Set<ProductDTO> getOneProductByName(@RequestParam(name ="name", required = true) String name){
		return productService.findByName(name);
	}
	
	@GetMapping(value = "/products", params = "category",  produces = "application/json; charset=UTF-8")
	public Set<ProductDTO> getAllProductsByCategory(@RequestParam(name = "category", required = true) String category){
		return productService.findByCategory(category);
	}
	
	@GetMapping(value="/products", params = {"startPrice", "endPrice"}, produces = "application/json; charset=UTF-8")
	public Set<ProductDTO> getAllProductsBetweenPrice(@RequestParam(name = "startPrice", required = true) Double startPrice, @RequestParam(name ="endPrice", required = true) Double endPrice){
		return productService.findByPriceBetween(startPrice, endPrice);
	}
	
	@GetMapping(value="/products", params = "priceLess", produces = "application/json; charset=UTF-8")
	public Set<ProductDTO> getAllProductsLessThanPrice(@RequestParam(name="priceLess", required = true) Double priceLess){
		return productService.findByPriceLessThan(priceLess);
	}
	
	@GetMapping(value="/products", params = "priceGreater", produces = "application/json; charset=UTF-8")
	public Set<ProductDTO> getAllProductsGreaterThanPrice(@RequestParam(name="priceGreater", required = true) Double priceGreater){
		return productService.findByPriceGreaterThan(priceGreater);
	}
	
	@PostMapping(value="/products", produces = "application/json; charset=UTF-8")
	public ProductDTO saveProduct(@Validated @RequestBody ProductDTO productDTO) {
		return productService.saveProduct(productDTO);
	}
	
	@PutMapping(value="/products/{id}", produces = "application/json; charset=UTF-8")
	public ProductDTO updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
		return productService.updateProduct(id, productDTO);
	}
	
	@DeleteMapping(value="/products/{id}", produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>("Product with id:"+id+"successfully deleted!", HttpStatus.OK);
	}
	
}
