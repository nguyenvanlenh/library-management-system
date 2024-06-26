package com.watermelon.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.watermelon.entity.Book;
import com.watermelon.service.RedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class BookListener {
private final RedisService redisService;
	

	@PostPersist
	public void postSave(Book book) {
		redisService.addNewBook(book);
		log.info("A new Book has been saved. Book ID: {}", book.getId());
	}
	
	@PostUpdate
	public void postUpdate(Book book) throws JsonProcessingException {
		redisService.updateBook(book.getId(), book);
		log.info("Book ID {} has been updated successfully in Redis.", book.getId());
		
	}
	@PostRemove
	public void postRemove(Book book) {
		if(redisService.existsById(book.getId()))
			redisService.deleteBook(book.getId());
		log.info("Book has been removed from Redis. Book ID: {}", book.getId());
	}
}
