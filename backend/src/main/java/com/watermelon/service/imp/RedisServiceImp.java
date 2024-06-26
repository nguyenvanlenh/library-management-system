package com.watermelon.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.watermelon.entity.Book;
import com.watermelon.exception.RedisServiceBusinessException;
import com.watermelon.repository.RedisRepository;
import com.watermelon.service.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImp implements RedisService {

	private final RedisRepository redisRepository;

	public void addNewBook(Book book){
		try {
			redisRepository.addNewBook(book);
		} catch (Exception e) {
			log.error("Exception occurred persisting book to Redis. Exception message: {}", e.getMessage());
			throw new RedisServiceBusinessException("Exception occurred while persisting book to Redis");
		}
	}

	public Book getBook(Long id){
		try {
			return redisRepository.findById(id);
		} catch (Exception e) {
			log.error("Exception occurred getting book in Redis. Exception message: {}", e.getMessage());
			throw new RedisServiceBusinessException("Exception occurred while getting book in Redis");
		}
	}

	@Override
	public void deleteBook(Long id) {
		try {
			redisRepository.deleteBook(id);
		} catch (Exception e) {
			log.error("Exception occurred deleting book in Redis. Exception message: {}", e.getMessage());
			throw new RedisServiceBusinessException("Exception occurred while deleting book in Redis");
		}

	}

	@Override
	public boolean existsById(Long id) {
		return redisRepository.existsById(id);
	}

	@Override
	public void updateBook(Long bookId, Book book) {
		try {
			if (redisRepository.existsById(book.getId())) {
				redisRepository.deleteBook(book.getId());
				redisRepository.addNewBook(book);
			}
		} catch (Exception e) {
			log.error("Exception occurred updating book in Redis. Exception message: {}", e.getMessage());
			throw new RedisServiceBusinessException("Exception occurred while updating book in Redis");
		}
	}
	@Override
	public List<Book> getAllBooks() {
		try {
			return redisRepository.findAll();
		} catch (Exception e) {
			log.error("Exception occurred getting list books in Redis. Exception message: {}", e.getMessage());
			throw new RedisServiceBusinessException("Exception occurred while getting list books in Redis");
		}
	}
	@Override
	public void deleteByHashKey() {
		try {
			redisRepository.deleteByHashKey();
		} catch (Exception e) {
			log.error("Exception occurred deleting hashkey book in Redis. Exception message: {}", e.getMessage());
			throw new RedisServiceBusinessException("Exception occurred while deleting hashkey book in Redis");
		}
		
	}

}
