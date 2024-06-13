package com.watermelon.service;

import org.springframework.stereotype.Service;

import com.watermelon.entity.Author;
import com.watermelon.entity.Book;
import com.watermelon.entity.BookTransaction;
import com.watermelon.entity.Category;
import com.watermelon.entity.Publisher;
import com.watermelon.entity.User;
import com.watermelon.exception.ResourceNotFoundException;
import com.watermelon.repository.AuthorRepository;
import com.watermelon.repository.BookRepository;
import com.watermelon.repository.BookTransactionRepository;
import com.watermelon.repository.CategoryRepository;
import com.watermelon.repository.PublisherRepository;
import com.watermelon.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class CommonService {

	private final BookRepository bookRepository;
	private final BookTransactionRepository bookTransactionRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final PublisherRepository publisherRepository;
	private final AuthorRepository authorRepository;
	
	public Category getCategoryById(Integer categoryId) {
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category_not_found", categoryId));
	}
	
	public Book getBookById(Long bookId) {
		return bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("book_not_found", bookId));
	}
	public BookTransaction getBookTransactionById(Long bookTranId) {
		return bookTransactionRepository.findById(bookTranId)
				.orElseThrow(() ->  new ResourceNotFoundException("booktransaction_not_found", bookTranId));
	}
	public User getUserById(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user_not_found", userId));
	}

	public Author getAuthorById(Integer authorId) {
		return authorRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("author_not_found",authorId));
	}

	public Publisher getPublisherById(Integer publisherId) {
		return publisherRepository.findById(publisherId)
				.orElseThrow(() -> new ResourceNotFoundException("publisher_not_found", publisherId));
	}
	
}
