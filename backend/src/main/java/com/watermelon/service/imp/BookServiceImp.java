package com.watermelon.service.imp;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.watermelon.dto.request.BookRequest;
import com.watermelon.dto.response.PageResponse;
import com.watermelon.entity.Author;
import com.watermelon.entity.Book;
import com.watermelon.entity.Category;
import com.watermelon.entity.Publisher;
import com.watermelon.exception.BookServiceBusinessException;
import com.watermelon.mapper.BookMapper;
import com.watermelon.repository.BookRepository;
import com.watermelon.service.BookService;
import com.watermelon.service.CommonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookServiceImp implements BookService {

	private final BookRepository bookRepository;
	private final CommonService commonService;

	@Transactional(readOnly = true)
	@Override
	public Book getBookById(Long bookId) {
		return commonService.getBookById(bookId);
	}
	@Transactional(readOnly = true)
	@Override
	public PageResponse<List<Book>> getAllBooks(Pageable pageable) {
		Page<Book> pageBooks = bookRepository.findAll(pageable);
		return PageResponse.<List<Book>>builder()
				.currentPage(pageBooks.getPageable().getPageNumber())
				.size(pageBooks.getSize())
				.totalPage(pageBooks.getTotalPages())
				.totalElement(pageBooks.getTotalElements())
				.content(pageBooks.getContent())
				.build();
	}

	@Transactional()
	@Override
	public Long addNewBook(BookRequest request) {
		Book book = BookMapper.toEntity(request);
		Publisher publisher = commonService.getPublisherById(request.getPublisherId());
		Category category = commonService.getCategoryById(request.getCategoryId());

		try {
			Set<Author> listAuthors = request.getListAuthors().stream().map(commonService::getAuthorById)
					.collect(Collectors.toSet());

			book.setCategory(category);
			book.setPublisher(publisher);
			book.setListAuthors(listAuthors);
			Book bookSaved = bookRepository.save(book);

			log.info("BookService:addNewBook - Successfully added new book with ID {}", bookSaved.getId());
			return bookSaved.getId();
		} catch (Exception ex) {
			log.error("Exception occurred while persisting book to database. Exception message: {}", ex.getMessage());
			throw new BookServiceBusinessException("Exception occurred while creating a new book");
		}
	}

	@Transactional
	@Override
	public void deleteBook(Long id) {
		try {
			bookRepository.deleteById(id);
			log.info("BookService:deleteBook - Successfully deleted book with ID {}", id);
		} catch (Exception ex) {
			log.error("Exception occurred while deleting book from database. Exception message: {}", ex.getMessage());
			throw new BookServiceBusinessException("Exception occurred while deleting a book");
		}
	}

	@Transactional
	@Override
	public void updateBook(Long bookId, BookRequest request) {
		Book bookUpdated = commonService.getBookById(bookId);
		try {
			bookUpdated.setTitle(request.getTitle());
			bookUpdated.setIsbn(request.getIsbn());
			bookUpdated.setDescription(request.getDescription());
			bookUpdated.setLanguage(request.getLanguage());
			bookUpdated.setQuantityAvailable(request.getQuantityAvailable());
			bookUpdated.setPrice(request.getPrice());
			bookUpdated.setPublicationDate(request.getPublicationDate());
			bookRepository.save(bookUpdated);
			log.info("BookService:updateBook - Successfully updated book with ID {}", bookId);
		} catch (Exception ex) {
			log.error("Exception occurred while updating book in the database. Exception message: {}", ex.getMessage());
			throw new BookServiceBusinessException("Exception occurred while updating a book");
		}
	}

	@Override
	public Integer updateQuantity(Long bookId, Integer quantity) {
		Book book = commonService.getBookById(bookId);
		try {
			book.setQuantityAvailable(book.getQuantityAvailable() + quantity);
			Book bookUpdated = bookRepository.save(book);
			log.info("BookService:updateQuantity - Successfully updated book quantity for book with ID {}", bookId);
			return bookUpdated.getQuantityAvailable();
		} catch (Exception ex) {
			log.error("Exception occurred while updating book quantity in the database. Exception message: {}",
					ex.getMessage());
			throw new BookServiceBusinessException("Exception occurred while updating book quantity");
		}
	}

}
