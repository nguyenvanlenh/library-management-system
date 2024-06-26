package com.watermelon.repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.entity.Book;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;
	private static final String BOOK_HASH_KEY = "Book";

	public Long addNewBook(Book book) throws JsonProcessingException {
		String json = redisObjectMapper.writeValueAsString(book);
		redisTemplate.opsForHash().put(BOOK_HASH_KEY, String.valueOf(book.getId()), json);
		
		Instant expireTime = Instant.now().plus(15, ChronoUnit.DAYS);
		redisTemplate.expireAt(BOOK_HASH_KEY, expireTime);
		return book.getId();
	}

	public Book findById(Long id) throws JsonMappingException, JsonProcessingException {
		String json = (String) redisTemplate.opsForHash().get(BOOK_HASH_KEY, String.valueOf(id));
		return json != null ? redisObjectMapper.readValue(json, new TypeReference<Book>() {
		}) : null;
	}

	public List<Book> findAll() {
        Map<Object, Object> books = redisTemplate.opsForHash().entries(BOOK_HASH_KEY);
        return books.values().stream()
                .map(json -> {
                    try {
                        return redisObjectMapper.readValue((String) json, new TypeReference<Book>() {});
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error converting JSON to Book", e);
                    }
                })
                .toList();
    }

	public boolean deleteBook(Long id) {
		redisTemplate.opsForHash().delete(BOOK_HASH_KEY, String.valueOf(id));
		return true;
	}
	public boolean deleteByHashKey() {
        redisTemplate.delete(BOOK_HASH_KEY);
        return true;
    }
	 public boolean existsById(Long id) {
	        return redisTemplate.opsForHash().hasKey(BOOK_HASH_KEY, String.valueOf(id));
	    }
}
