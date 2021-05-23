package com.yildiz.tradilianz.retailer;

import java.util.Set;

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
public class RetailerController {
	
	private RetailerService retailerService;
	
	public RetailerController(RetailerService retailerService) {
		this.retailerService = retailerService;
	}
	
	@GetMapping(value = "/retailers", produces = "application/json; charset=UTF-8")
	public Set<RetailerDTO> getAllRetailers (){
		return retailerService.findAll();
	}
	
	@GetMapping(value = "/retailers/{id}", produces = "application/json; charset=UTF-8")
	public RetailerDTO getOneRetailer(@PathVariable("id") Long id) {
		return retailerService.findbyId(id);
	}
	
	@GetMapping(value = "/retailers", params = "name", produces = "application/json; charset=UTF-8")
	public RetailerDTO getOneRetailerByname(@RequestParam(name = "name", required = true) String name) {
		return retailerService.findByName(name);
	}
	
	@GetMapping(value = "/retailers", params = "postalCode", produces = "application/json; charset=UTF-8" )
	public Set<RetailerDTO> getAllRetailersByPostalCode(@RequestParam(name = "postalCode", required = true) String postalCode){
		return retailerService.findByPostalCode(postalCode);
	}
	
	@PutMapping(value = "/retailers/{id}", produces = "application/json; charset=UTF-8")
	public RetailerDTO updateRetailer(@RequestBody @Validated RetailerDTO retailerDTO, @PathVariable("id") Long id) {
		return retailerService.update(retailerDTO, id);
	}
	
	@PostMapping(value = "/retailers", produces = "application/json; charset=UTF-8")
	public RetailerDTO saveRetailer(@RequestBody @Validated RetailerDTO retailerDTO) {
		return retailerService.save(retailerDTO);
	}
	
	@DeleteMapping(value = "/retailers/{id}", produces = "application/json; charset=UTF-8")
	public String deleteRetailer(@PathVariable("id") Long id) {
		return retailerService.delete(id);
	}

}
