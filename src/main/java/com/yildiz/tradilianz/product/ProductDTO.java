package com.yildiz.tradilianz.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ProductDTO {

	private Long id;
	@NotBlank
	private String productName;
	private String description;
	@NotNull
	private Double price;
	@NotBlank
	private String category;
	private String brand;
	private Integer quantity;

}
