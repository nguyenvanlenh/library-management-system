package com.watermelon.service;

import com.watermelon.dto.request.BookTransactionRequest;

public interface BookTransactionService {

	Long borrowBook(BookTransactionRequest request);
	Long returnBook(Long bookTranId);
}
