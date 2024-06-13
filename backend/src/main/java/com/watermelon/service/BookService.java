package com.watermelon.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.watermelon.dto.request.BookRequest;
import com.watermelon.dto.response.PageResponse;
import com.watermelon.entity.Book;

public interface BookService {

	Book getBookById(Long bookId);
	PageResponse<List<Book>> getAllBooks(Pageable pageable);
	
	Long addNewBook(BookRequest request);
	void deleteBook(Long id);
	void updateBook(Long bookId, BookRequest request);
	Integer updateQuantity(Long bookId,Integer quantity);
	
	
	
}
