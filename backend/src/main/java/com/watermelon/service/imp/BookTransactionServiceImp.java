package com.watermelon.service.imp;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.watermelon.config.Translator;
import com.watermelon.dto.request.BookTransactionRequest;
import com.watermelon.entity.Book;
import com.watermelon.entity.BookTransaction;
import com.watermelon.entity.User;
import com.watermelon.exception.BookTransactionServiceBusinessException;
import com.watermelon.exception.ResourceNotFoundException;
import com.watermelon.mapper.BookTransactionMapper;
import com.watermelon.repository.BookTransactionRepository;
import com.watermelon.service.BookService;
import com.watermelon.service.BookTransactionService;
import com.watermelon.service.CommonService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookTransactionServiceImp implements BookTransactionService {

	private final BookTransactionRepository bookTransactionRepository;
	private final BookService bookService;
	private final CommonService commonService;

	@Transactional
	@Override
	public Long borrowBook(BookTransactionRequest request) {
		Book book = commonService.getBookById(request.getBookId());
		boolean bookAvailable = book.getQuantityAvailable() > 0;

		if (!bookAvailable) {
			String message = Translator.toLocale("book_available", book.getId());
			log.warn("BookTransactionService:borrowBook - Book with ID {} is not available", book.getId());
			throw new ResourceNotFoundException(message);
		}
		try {

			BookTransaction bookTransaction = BookTransactionMapper.toEntity(request);
			User user = commonService.getUserById(request.getUserId());
			bookTransaction.setBook(book);
			bookTransaction.setUser(user);
			bookService.updateQuantity(request.getBookId(), -1);
			BookTransaction bookTransactionBorrow = bookTransactionRepository.save(bookTransaction);
			log.info("BookTransactionService:borrowBook - Successfully borrowed book with ID {}", book.getId());
			return bookTransactionBorrow.getBook().getId();
		} catch (Exception e) {
			log.error("Exception occurred while borrowing book: {}",
					e.getMessage());
			throw new BookTransactionServiceBusinessException("Exception occurred while borrowing the book");
		}
	}

	@Transactional
	@Override
	public Long returnBook(Long bookTranId) {
		BookTransaction bookReturn = commonService.getBookTransactionById(bookTranId);
		bookReturn.setActualReturnDate(LocalDate.now());
		try {
			bookService.updateQuantity(bookReturn.getBook().getId(), 1);
			BookTransaction bookTransaction = bookTransactionRepository.save(bookReturn);

			log.info("BookTransactionService:returnBook - Successfully returned book with transaction ID {}",
					bookTranId);
			return bookTransaction.getId();
		} catch (Exception e) {
			log.error("Exception occurred while returning book: {}",
					e.getMessage());
			throw new BookTransactionServiceBusinessException("Exception occurred while returning the book");
		}
	}

}
