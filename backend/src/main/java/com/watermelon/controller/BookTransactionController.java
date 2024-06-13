package com.watermelon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watermelon.dto.request.BookTransactionRequest;
import com.watermelon.dto.response.ApiResponse;
import com.watermelon.service.BookTransactionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/book-transactions")
public class BookTransactionController {

	private final BookTransactionService bookTransactionService;
	
	@PostMapping("/borrow-book")
	public ApiResponse<Long> borrowBook(@RequestBody BookTransactionRequest request){
		return ApiResponse.<Long>builder()
				.status(HttpStatus.CREATED.value())
				.message("Borrow book success")
				.data(bookTransactionService.borrowBook(request))
				.build();
	}
	@PatchMapping("/return-book/{bookTranId}")
	public ApiResponse<Void> returnBook(@PathVariable Long bookTranId){
		bookTransactionService.returnBook(bookTranId);
		return ApiResponse.<Void>builder()
				.message("Return book success")
				.build();
	}
}
