package com.watermelon.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.watermelon.entity.BookRating;
import com.watermelon.entity.manytomanypk.BookRatingPK;
import com.watermelon.exception.BookRatingServiceBusinessException;
import com.watermelon.exception.BookServiceBusinessException;
import com.watermelon.repository.BookRatingRepository;
import com.watermelon.service.BookRatingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class BookRatingServiceImp implements BookRatingService {

	private final BookRatingRepository bookRatingRepository;

	@Override
	public List<BookRating> getRatingByBookId(Long bookId) {
		return bookRatingRepository.findByBookRatingKey_BookId(bookId);
	}

	@Override
	public BookRatingPK addRating(BookRating request) {
		try {
		return bookRatingRepository.save(request).getBookRatingKey();
		}catch(Exception ex) {
			log.error("Exception occurred while persisting book rating to database. Exception message: {}", ex.getMessage());
			throw new BookRatingServiceBusinessException("Exception occurred while adding rating");
		}
	}

	@Override
	public void deleteRating(BookRatingPK id) {
		try {
			bookRatingRepository.deleteById(id);
			log.info("BookRatingService:deleteRating - Successfully deleted book rating with ID {}", id);
		} catch (Exception ex) {
			log.error("Exception occurred while deleting book rating from database. Exception message: {}", ex.getMessage());
			throw new BookServiceBusinessException("Exception occurred while deleting rating");
		}
	}
	
	
}
