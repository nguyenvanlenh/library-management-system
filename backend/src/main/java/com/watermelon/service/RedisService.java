package com.watermelon.service;

import java.util.List;

import com.watermelon.entity.Book;

public interface RedisService {
	
	void addNewBook(Book book);
	Book getBook(Long id);
	void deleteBook(Long id);
	boolean existsById(Long id);
	void updateBook(Long bookId, Book book);
	void deleteByHashKey();
	List<Book> getAllBooks();
	

}
