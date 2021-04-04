package com.yildiz.tradilianz.product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	private ModelMapper modelMapper;
	private ProductRepository productRepo;
	
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
	
	public ProductDTO findOneByName(String name) {
		Product product = productRepo.findByproductName(name);
		ProductDTO productDTO = convertToDTO(product);
		return productDTO;
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
	
	public ProductDTO convertToDTO(Product product) {
		ProductDTO productDTO= modelMapper.map(product, ProductDTO.class);
		return productDTO;
	}
	
	public Product convertToEntity(ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		return product;
	}

}
