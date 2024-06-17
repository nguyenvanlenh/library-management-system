package com.watermelon.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.watermelon.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({
		RuntimeException.class,
		Exception.class
	})
	public ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception e){
		ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
				.status(500)
				.message(e.getMessage())
				.build();
		return ResponseEntity.status(500).body(errorResponse);
	}
	@ExceptionHandler({
		ResourceNotFoundException.class
	})
	public ResponseEntity<ApiResponse<Void>> handlingResourceNotFoundException(ResourceNotFoundException e){
		ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
				.status(404)
				.message(e.getMessage())
				.build();
		return ResponseEntity.status(404).body(errorResponse);
	}
	
	@ExceptionHandler({
		BadCredentialsException.class,
	})
	public ResponseEntity<ApiResponse<Void>> handlingUnauthenticatedException(Exception e){
		ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
				.status(401)
				.message(e.getMessage())
				.build();
		return ResponseEntity.status(401).body(errorResponse);
	}
	@ExceptionHandler({
		BookTransactionServiceBusinessException.class,
		BookServiceBusinessException.class,
		BookRatingServiceBusinessException.class,
	})
	public ResponseEntity<ApiResponse<Void>> handlingServiceBusinessException(Exception e){
		ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
				.status(500)
				.message(e.getMessage())
				.build();
		return ResponseEntity.status(500).body(errorResponse);
	}
	
}
