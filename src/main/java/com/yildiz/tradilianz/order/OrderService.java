package com.yildiz.tradilianz.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yildiz.tradilianz.customer.Customer;
import com.yildiz.tradilianz.customer.CustomerRepository;
import com.yildiz.tradilianz.exception.CustomerBalanceToLowException;
import com.yildiz.tradilianz.exception.CustomerNotFoundException;
import com.yildiz.tradilianz.exception.OrderNotFoundException;
import com.yildiz.tradilianz.exception.ProductNotFoundException;
import com.yildiz.tradilianz.product.Product;
import com.yildiz.tradilianz.product.ProductRepository;
import com.yildiz.tradilianz.retailer.Retailer;
import com.yildiz.tradilianz.retailer.RetailerRepository;

@Service
@Transactional
public class OrderService {

	private OrderRepository orderRepo;
	private CustomerRepository customerRepo;
	private RetailerRepository retailerRepo;
	private ProductRepository productRepo;
	private final ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(OrderService.class);

	public OrderService(OrderRepository orderRepo, CustomerRepository customerRepo, RetailerRepository retailerRepo,
			ProductRepository productRepo, ModelMapper modelMapper) {
		this.orderRepo = orderRepo;
		this.customerRepo = customerRepo;
		this.retailerRepo = retailerRepo;
		this.productRepo = productRepo;
		this.modelMapper = modelMapper;
	}

	public List<OrderDTOResponse> getAllOrders() {
		List<OrderDTOResponse> orderDTOs = new ArrayList<>();
		Iterable<Order> orderList = orderRepo.findAll();
		for (Order order : orderList) {
			orderDTOs.add(convertToDTO(order));
		}
		return orderDTOs;
	}

	public OrderDTOResponse getOneOrder(Long id) {
		Order order = orderRepo.findById(id).get();
		if (order == null) {
			throw new OrderNotFoundException(id);
		} else {
			OrderDTOResponse orderDTO = convertToDTO(order);
			return orderDTO;
		}
	}

	public List<OrderDTOResponse> getOrderByCustomerId(Long id) {
		List<OrderDTOResponse> orderDTOs = new ArrayList<>();
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
	
	public List<OrderDTOResponse> getOrderByAmountLessThan(Double amount) {
		List<Order> orderList = orderRepo.findByamountLessThan(amount);
		if(orderList.isEmpty()) {
			throw new OrderNotFoundException(null);
		}
		List<OrderDTOResponse> orderDTOList = new ArrayList<>();
		
		for(Order order: orderList) {
			orderDTOList.add(convertToDTO(order));
		}
		return orderDTOList;
	}
	

	public List<OrderDTOResponse> getOrderByAmountGreaterThan(Double amount) {
		List<Order> orderList = orderRepo.findByamountGreaterThan(amount);
		if(orderList.isEmpty()) {
			throw new OrderNotFoundException(null);
		}
		List<OrderDTOResponse> orderDTOList = new ArrayList<>();
		
		for(Order order: orderList) {
			orderDTOList.add(convertToDTO(order));
		}
		return orderDTOList;
	}

	public List<OrderDTOResponse> getOrderByRetailerId(Long id) {
		List<OrderDTOResponse> orderDTOs = new ArrayList<>();
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

	public OrderDTOResponse saveOrder(OrderDTO orderDTO) {

		Retailer retailer = retailerRepo.findByid(orderDTO.getRetailerId());
		Customer customer = customerRepo.findById(orderDTO.getCustomerId()).get();

		// retrieve products and quantity of shoppingCart from retailers offered
		// products
		Map<Product, Integer> shoppingCart = new HashMap<>();
		Map<Product, Integer> offeredProducts = retailer.getOfferedProducts();
		for (Map.Entry<Long, Integer> orderShoppingCart : orderDTO.getShoppingCart().entrySet()) {
			for (Map.Entry<Product, Integer> retailerProducts : offeredProducts.entrySet()) {
				if (productRepo.findByid(orderShoppingCart.getKey()) == null) {
					throw new ProductNotFoundException(orderShoppingCart.getKey());
				}
				Product product = productRepo.findById(orderShoppingCart.getKey()).get();
				if (product == retailerProducts.getKey()) {
					shoppingCart.put(retailerProducts.getKey(), orderShoppingCart.getValue());
				}
			}
		}

		// calculate & and set amount
		double amount = 0;
		for (Map.Entry<Product, Integer> products : shoppingCart.entrySet()) {
			amount += products.getKey().getPrice() * products.getValue();
		}
		orderDTO.setAmount(amount);
		// check if customer balance is enough for the transaction
		if (customer == null) {
			throw new CustomerNotFoundException(orderDTO.getCustomerId());
		} else if (customer.getBalance() < amount) {
			orderDTO.setOrderStatus(OrderStatus.CANCELED); // wird nie gespeichert!
			Order order = orderRepo.save(convertToEntity(orderDTO, retailer));
			OrderDTOResponse orderCancelDTO = convertToDTO(order);
			throw new CustomerBalanceToLowException(orderDTO.getCustomerId(), orderCancelDTO);
		} else if (customer.getBalance() > amount) {
			// set positive order status
			orderDTO.setOrderStatus(OrderStatus.CONFIRMED);
			// set new quantity of products in retailer inventory
			for (Map.Entry<Product, Integer> retailerProducts : offeredProducts.entrySet()) {
				for (Map.Entry<Product, Integer> orderedProducts : shoppingCart.entrySet()) {
					if (retailerProducts.getKey() == orderedProducts.getKey()) {
						offeredProducts.put(retailerProducts.getKey(),
								retailerProducts.getValue() - orderedProducts.getValue());
					}
				}
			}
			retailer.setOfferedProducts(offeredProducts);
			// set new balance of customer & retailer
			customer.setBalance(customer.getBalance() - amount);
			retailer.setBalance(retailer.getBalance() + amount);
		}

		// calculate & set bonuspoints
		int bonuspoints = 0;
		if (amount <= 9.99) {
			bonuspoints = 0;
		} else {
			// 1 point for every 10€, rounded up 2.5€ -> 3 points, 2,4€ -> 2 points
			bonuspoints = (int) Math.round(amount / 10);
		}
		orderDTO.setBonuspoints(bonuspoints);
		customer.setBonuspoints(customer.getBonuspoints() + bonuspoints);

		customerRepo.save(customer);
		retailerRepo.save(retailer);
		Order order = orderRepo.saveAndFlush(convertToEntity(orderDTO, retailer));
		return convertToDTO(order);
	}
	/*
	 *  We should not allow to manipulate transactions directly! Instead I plan to make a 
	 *  seperate api in which only "proven" request could be cancelled by transfering back the money
	 *  to customer/retailer after products get delivered back and so on... 
	 * 
	 * public OrderDTOResponse updateOrder(Long id, OrderDTO orderDTO, Retailer
	 * retailer) { orderDTO.setId(id); Order order = convertToEntity(orderDTO,
	 * retailer); if (order == null) { throw new OrderNotFoundException(id); } else
	 * { orderRepo.save(order); return convertToDTO(order); } }
	 * 
	 * public void deleteOrder(Long id) { Order order =
	 * orderRepo.findById(id).get(); if (order == null) { throw new
	 * OrderNotFoundException(id); } else { orderRepo.deleteById(id); } }
	 */

	// Umwandlung von DTO zu Entity Objekt
	public Order convertToEntity(OrderDTO orderDTO, Retailer retailer) {
		TypeMap<OrderDTO, Order> typeMap = modelMapper.getTypeMap(OrderDTO.class, Order.class);
		if (typeMap == null) { // if not already added
			modelMapper.addMappings(new OrderMap());
		}
		Order order = modelMapper.map(orderDTO, Order.class);
		// retrieve products and quantity of shoppingCart from retailer
		Map<Product, Integer> shoppingCart = new HashMap<>();
		Map<Product, Integer> offeredProducts = retailer.getOfferedProducts();
		for (Map.Entry<Long, Integer> orderShoppingCart : orderDTO.getShoppingCart().entrySet()) {
			for (Map.Entry<Product, Integer> retailerProducts : offeredProducts.entrySet()) {
				if (productRepo.findById(orderShoppingCart.getKey()).get() == retailerProducts.getKey()) {
					shoppingCart.put(retailerProducts.getKey(), orderShoppingCart.getValue());
				}
			}
		}

		order.setShoppingCart(shoppingCart);
		order.setCustomer(customerRepo.findById(orderDTO.getCustomerId()).get());
		order.setRetailer(retailerRepo.findById(orderDTO.getRetailerId()).get());
		return order;
	}

	// Umwandlung von Entity zu DTO Objekt
	private OrderDTOResponse convertToDTO(Order order) {
		TypeMap<Order, OrderDTOResponse> typeMap = modelMapper.getTypeMap(Order.class, OrderDTOResponse.class);
		if (typeMap == null) { // if not already added
			modelMapper.addMappings(new OrderDTOResponseMap());
		}
		OrderDTOResponse orderDTO = modelMapper.map(order, OrderDTOResponse.class);
		orderDTO.setOrderNumber(order.getOrderNumber());
		orderDTO.setShoppingCart(order.getShoppingCart());
		return orderDTO;
	}




}
