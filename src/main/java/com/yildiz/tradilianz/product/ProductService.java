package com.yildiz.tradilianz.product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.yildiz.tradilianz.exception.ProductNotFoundException;

@Service
public class ProductService {
	
	private ModelMapper modelMapper;
	private ProductRepository productRepo;
	private final Logger log = LoggerFactory.getLogger(ProductService.class);
	
	public ProductService(ModelMapper modelMapper, ProductRepository productRepo) {
		this.modelMapper = modelMapper;
		this.productRepo = productRepo;
	}
	
	public Set<ProductDTO> findAllProducts(){
		Iterable<Product> allProducts = productRepo.findAll();
		Set<ProductDTO> productDTOSet = new HashSet<>();
		for(Product p: allProducts) {
			ProductDTO productDTO = convertToDTO(p);
			productDTOSet.add(productDTO);
		}
		return productDTOSet;
	}
	
	public ProductDTO findOneById(Long id) {
		Optional<Product> product = productRepo.findById(id);
		ProductDTO productDTO = convertToDTO(product.get());
		return productDTO;
	}
	
	public Set<ProductDTO> findByName(String name) {
		Set<ProductDTO> productDTOs = new HashSet<>();
		Set<Product> products = productRepo.findByproductName(name);
		for(Product p: products) {
			productDTOs.add(convertToDTO(p));
		}
		return productDTOs;
	}
	
	public Set<ProductDTO> findByPriceGreaterThan(double price){
		Set<ProductDTO> productDTOSet = new HashSet<>();
		Set<Product> productSet = productRepo.findBypriceGreaterThan(price);
		for(Product p: productSet) {
			ProductDTO productDTO= convertToDTO(p);
			productDTOSet.add(productDTO);
		}
		return productDTOSet;
	}
	
	public Set<ProductDTO> findByPriceBetween(double startPrice, double endPrice){
		Set<ProductDTO> productDTOSet = new HashSet<>();
		Set<Product> productSet = productRepo.findBypriceBetween(startPrice, endPrice);
		for(Product p: productSet) {
			ProductDTO productDTO= convertToDTO(p);
			productDTOSet.add(productDTO);
		}
		return productDTOSet;
	}
	
	public Set<ProductDTO> findByPriceLessThan(double price){
		Set<ProductDTO> productDTOSet = new HashSet<>();
		Set<Product> productSet = productRepo.findBypriceLessThan(price);
		for(Product p: productSet) {
			ProductDTO productDTO= convertToDTO(p);
			productDTOSet.add(productDTO);
		}
		return productDTOSet;
	}
	
	public Set<ProductDTO> findByCategory(String category){
		Set<ProductDTO> productDTOs = new HashSet<>();
		Set<Product> products = productRepo.findBycategory(category);
		for(Product p: products) {
			productDTOs.add(convertToDTO(p));
		}
		return productDTOs;
	}
	
	public Set<ProductDTO> findByQuantity(Integer quantity){
		Set<ProductDTO> productDTOs= new HashSet<>();
		Set<Product> products = productRepo.findByquantity(quantity);
		for(Product p: products) {
			productDTOs.add(convertToDTO(p));
		}
		return productDTOs;
	}
	
	public Set<ProductDTO> findByQuantityGreaterThan(Integer quantity){
		Set<ProductDTO> productDTOs= new HashSet<>();
		Set<Product> products = productRepo.findByquantityGreaterThan(quantity);
		for(Product p: products) {
			productDTOs.add(convertToDTO(p));
		}
		return productDTOs;
	}
	
	public Set<ProductDTO> findByQuantityLessThan(Integer quantity){
		Set<ProductDTO> productDTOs= new HashSet<>();
		Set<Product> products = productRepo.findByquantityLessThan(quantity);
		for(Product p: products) {
			productDTOs.add(convertToDTO(p));
		}
		return productDTOs;
	}
	
	public ProductDTO saveProduct(ProductDTO productDTO) {
		Product saveProduct = productRepo.save(convertToEntity(productDTO));
		return modelMapper.map(saveProduct, ProductDTO.class);
	}
	
	public ProductDTO updateProduct(Long Id, ProductDTO productDTO) {
		productDTO.setId(Id);
		Product updateProduct = productRepo.save(convertToEntity(productDTO));
		return modelMapper.map(updateProduct, ProductDTO.class);
	}
	
	public void deleteProduct(Long Id) {
		
		try {
			productRepo.findById(Id);
			productRepo.deleteById(Id);			
		}catch(EmptyResultDataAccessException ex) {
			log.info(ex.getMessage());
			throw new ProductNotFoundException(Id);
		}
	}
	
	
	public ProductDTO convertToDTO(Product product) {
		ProductDTO productDTO= modelMapper.map(product, ProductDTO.class);
		return productDTO;
	}
	
	public Product convertToEntity(ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		return product;
	}

}
