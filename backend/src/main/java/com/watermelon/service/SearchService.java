package com.watermelon.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.watermelon.dto.response.PageResponse;
import com.watermelon.entity.Book;
import com.watermelon.exception.SearchServiceBusinessException;
import com.watermelon.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

	private final SearchRepository searchRepository;
	
	
	@Transactional
	public PageResponse<List<Book>> searchBooksByCustomizeQuery(String title,
			String categoryName,
			String authorName,
			String publisherName,
			String language,
			int pageNo,
			int pageSize,
			Sort sort
			){
		try {
		return searchRepository.findAllBooksByCustomizeQuery(title, categoryName, authorName, publisherName, language, pageNo, pageSize,sort);
		}catch (Exception e) {
			log.error("Exception occurred while searching data in the database. Exception message: {}", e.getMessage());
			throw new SearchServiceBusinessException("Exception occurred while searching data in the database");
		}
	}
}
