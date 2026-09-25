package com.company_registration_form.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex){
     
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(error(404, ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){
		Map<String, String> errors = new LinkedHashMap<>();
		
		ex.getBindingResult()
		.getFieldErrors()
		.forEach(error -> errors.put(
				error.getField(),
				error.getDefaultMessage()));
		
		return ResponseEntity
				.badRequest()
				.body(errors);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDatabaseError(DataIntegrityViolationException ex) {
		
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(error(409, "Duplicate or invalid database value"));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex) {
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(error(500, "Internal server error"));
	}
	
	private Map<String, Object> error(
			int status, String message) {
		
		Map<String, Object> response = new LinkedHashMap<>();
		
		response.put("timestamp", LocalDateTime.now());
		response.put("status", status);
		
		response.put("message", message);
		
		return response;
	}
	
}
