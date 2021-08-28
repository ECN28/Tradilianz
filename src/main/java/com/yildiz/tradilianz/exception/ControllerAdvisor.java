package com.yildiz.tradilianz.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * ControllerAdvisor ist eine Komponente, in der alle Fehlermeldungen an 
 * einem Ort definiert werden können.
 */

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request){
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "No customers found");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RetailerNotFoundException.class)
	public ResponseEntity<Object> handleRetailerNotFoundException(RetailerNotFoundException ex, WebRequest request){
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request){
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderByRetailerNotFoundException.class)
	public ResponseEntity<Object> handleOrderByRetailerNotFoundException(OrderByRetailerNotFoundException ex, WebRequest request){
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderByCustomerNotFoundException.class)
	public ResponseEntity<Object> handleOrderByCustomerNotFoundException(OrderByCustomerNotFoundException ex, WebRequest request){
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(CustomerBalanceToLowException.class)
	public ResponseEntity<Object> handleCustomerBalanceToLow(CustomerBalanceToLowException ex, WebRequest request){
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("orderDTO", ex.getOrderDTOMessage());
		return new ResponseEntity<>(body, HttpStatus.CREATED);
		
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());

		List<String> errorList = new ArrayList<String>();
		List<FieldError> errors = ex.getFieldErrors();
		for(FieldError error : errors) {
			errorList.add(error.getField()+": "+error.getDefaultMessage());
		}
		body.put("errors", errorList);

//		errors.add(ex.getFieldError().getField()+": "+ex.getBindingResult().getFieldError().getDefaultMessage());
//		errors.add((ex.getFieldError().getField()+": "+ex.getBindingResult().getFieldError().getDefaultMessage()));
//		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}
