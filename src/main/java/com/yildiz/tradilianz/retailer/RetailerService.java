package com.yildiz.tradilianz.retailer;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yildiz.tradilianz.exception.RetailerNotFoundException;

@Service
@Transactional
public class RetailerService {

	private RetailerRepository retailerRepo;
	private RetailerDTO retailerDTO;
	private ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(RetailerService.class);

	public RetailerService(RetailerRepository retailerRepo, RetailerDTO retailerDTO, ModelMapper modelMapper) {
		this.retailerRepo = retailerRepo;
		this.retailerDTO = retailerDTO;
		this.modelMapper = modelMapper;
	}

	public Set<RetailerDTO> findAll() {
		Set<RetailerDTO> retailerDTOs = new HashSet<>();
		Iterable<Retailer> retailerSet = retailerRepo.findAll();
		for (Retailer retailer : retailerSet) {
			retailerDTOs.add(convertToDTO(retailer));
		}
		return retailerDTOs;
	}

	public RetailerDTO findbyId(Long id) {
		Retailer retailer = retailerRepo.findByid(id);
		if (retailer == null) {
			throw new RetailerNotFoundException(id);
		} else {
		return convertToDTO(retailer);
		}
	}

	public RetailerDTO findByName(String name) {
		Retailer retailer = retailerRepo.findByname(name);
		return convertToDTO(retailer);
	}

	public Set<RetailerDTO> findByPostalCode(String postalCode) {
		Set<RetailerDTO> retailerDTOs = new HashSet<>();
		Set<Retailer> retailerSet = retailerRepo.findBypostalCode(postalCode);
		for (Retailer retailer : retailerSet) {
			retailerDTOs.add(convertToDTO(retailer));
		}
		return retailerDTOs;
	}

	public RetailerDTO save(RetailerDTO retailerDTO) {
		if (retailerDTO != null) {
			retailerDTO.setPassword(retailerDTO.getPassword());
			retailerDTO.setRole("ROLE_RETAILER");
			Retailer savedRetailer = retailerRepo.save(convertToEntity(retailerDTO));
			return convertToDTO(savedRetailer);
		} else {
			return null;
		}

	}

	public RetailerDTO update(RetailerDTO retailerDTO, Long id) {
		if (retailerRepo.findById(id) == null) {
			throw new RetailerNotFoundException(id);
		} else {
			retailerDTO.setPassword(retailerDTO.getPassword());
			retailerDTO.setId(id);
			retailerDTO.setRole("ROLE_RETAILER");
			Retailer retailer = convertToEntity(retailerDTO);
			retailerRepo.save(retailer);
			return retailerDTO;
		}
	}

	public String delete(Long id) {
		try {
			retailerRepo.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			log.info(ex.getMessage());
		}
		return (String.format("Retailer with Id %d successfully deleted!", id));
	}

	public Retailer convertToEntity(RetailerDTO retailerDTO) {
		return modelMapper.map(retailerDTO, Retailer.class);
	}

	public RetailerDTO convertToDTO(Retailer retailer) {
		return modelMapper.map(retailer, RetailerDTO.class);
	}

}
