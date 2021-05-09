package com.yildiz.tradilianz.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.exception.CustomerBalanceToLowException;
import com.yildiz.tradilianz.exception.CustomerNotFoundException;
import com.yildiz.tradilianz.exception.OrderNotFoundException;
import com.yildiz.tradilianz.product.Product;
import com.yildiz.tradilianz.retailer.RetailerRepository;

@Service
@Transactional
public class OrderService {

	private OrderRepository orderRepo;
	private CustomerRepository customerRepo;
	private RetailerRepository retailerRepo;
	private ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(OrderService.class);

	public OrderService(OrderRepository orderRepo, CustomerRepository customerRepo, RetailerRepository retailerRepo,
			ModelMapper modelMapper) {
		this.orderRepo = orderRepo;
		this.customerRepo = customerRepo;
		this.retailerRepo = retailerRepo;
		this.modelMapper = modelMapper;
	}

	public List<OrderDTO> getAllOrders() {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		Iterable<Order> orderList = orderRepo.findAll();
		for (Order order : orderList) {
			orderDTOs.add(convertToDTO(order));
		}
		return orderDTOs;
	}

	public OrderDTO getOneOrder(Long id) {
		Order order = orderRepo.findById(id).get();
		if (order == null) {
			throw new OrderNotFoundException(id);
		} else {
			OrderDTO orderDTO = convertToDTO(order);
			return orderDTO;
		}
	}

	public List<OrderDTO> getOrderByCustomerId(Long id) {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		List<Order> orderList = orderRepo.findBycustomerId(id);
		if (orderList.isEmpty()) {
			throw new OrderNotFoundException(id);
		} else {
			for (Order order : orderList) {
				orderDTOs.add(convertToDTO(order));
			}
		}
		return orderDTOs;
	}
	
	public List<OrderDTO> getOrderByRetailerId(Long id) {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		List<Order> orderList = orderRepo.findByretailerId(id);
		if (orderList.isEmpty()) {
			throw new OrderNotFoundException(id);
		} else {
			for (Order order : orderList) {
				orderDTOs.add(convertToDTO(order));
			}
		}
		return orderDTOs;
	}
	
	public OrderDTO saveOrder(OrderDTO orderDTO) {
		
		//calculate & and set amount
		double amount = 0;
		for(Product p: orderDTO.getShoppingCart()) {
			amount += p.getPrice(); 
		}
		orderDTO.setAmount(amount);
		
		//check if customer balance is enough for the transaction
		Customer customer = customerRepo.findById(orderDTO.getCustomerId()).get();
		if(customer == null) {
			throw new CustomerNotFoundException(orderDTO.getCustomerId());
		}else if(customer.getBalance() < amount) {
			orderDTO.setOrderStatus(OrderStatus.CANCELED);
			throw new CustomerBalanceToLowException(orderDTO.getCustomerId());
		}else {
			orderDTO.setOrderStatus(OrderStatus.CONFIRMED);
		}
		
		//calculate & set bonuspoints
		int bonuspoints = 0;
		if(amount <= 9.99) {
			bonuspoints = 0;
		}else {
			// 1 point for every 10€, rounded up 2.5€ -> 3 points, 2,4€ -> 2 points 
			bonuspoints = Math.round(bonuspoints/10); 
		}
		orderDTO.setBonuspoints(bonuspoints);
		
		Order order = orderRepo.save(convertToEntity(orderDTO));
		return convertToDTO(order);
		
	}
	

	// Umwandlung von DTO zu Entity Objekt
	public Order convertToEntity(OrderDTO orderDTO) {
		// create a TypeMap for mapping
		TypeMap<OrderDTO, Order> typeMap = modelMapper.createTypeMap(OrderDTO.class, Order.class);
		// define the mappings on the type map
		typeMap.addMappings(mapper -> {
			mapper.map(src -> src.getId(), Order::setId);
			mapper.map(src -> src.getOrderNumber(), Order::setOrderNumber);
			mapper.map(src -> src.getAmount(), Order::setAmount);
			mapper.map(src -> src.getBonuspoints(), Order::setBonuspoints);
			mapper.map(src -> src.getOrderStatus(), Order::setStatus);
			mapper.map(src -> src.getShoppingCart(), Order::setShoppingCart);
			mapper.map(src -> src.getOrderDate(), Order::setOrderDate);
		});
		Order order = modelMapper.map(orderDTO, Order.class, typeMap.toString());
		order.setCustomer(customerRepo.findById(orderDTO.getCustomerId()).get());
		order.setRetailer(retailerRepo.findById(orderDTO.getRetailerId()).get());
		return order;
	}

	// Umwandlung von Entity zu DTO Objekt
	private OrderDTO convertToDTO(Order order) {
		// create a TypeMap for mapping
		TypeMap<Order, OrderDTO> typeMap = modelMapper.createTypeMap(Order.class, OrderDTO.class);
		// define the mappings on the type map
		typeMap.addMappings(mapper -> {
			mapper.map(src -> src.getId(), OrderDTO::setId);
			mapper.map(src -> src.getOrderNumber(), OrderDTO::setOrderNumber);
			mapper.map(src -> src.getCustomer().getId(), OrderDTO::setCustomerId);
			mapper.map(src -> src.getCustomer().getSurname() + src.getCustomer().getGivenName(),
					OrderDTO::setCustomerName);
			mapper.map(src -> src.getCustomer().getStreetAddress(), OrderDTO::setCustomerAddress);
			mapper.map(src -> src.getRetailer().getId(), OrderDTO::setRetailerId);
			mapper.map(src -> src.getRetailer().getName(), OrderDTO::setRetailerName);
			mapper.map(src -> src.getRetailer().getStreetAddress(), OrderDTO::setRetailerAddress);
			mapper.map(src -> src.getShoppingCart(), OrderDTO::setShoppingCart);
			mapper.map(src -> src.getStatus(), OrderDTO::setOrderStatus);
			mapper.map(src -> src.getAmount(), OrderDTO::setAmount);
			mapper.map(src -> src.getBonuspoints(), OrderDTO::setBonuspoints);
			mapper.map(src -> src.getOrderDate(), OrderDTO::setOrderDate);
		});
		OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class, typeMap.toString());
		return orderDTO;
	}

}
