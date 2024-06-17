package com.watermelon.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watermelon.dto.response.ApiResponse;
import com.watermelon.dto.response.PageResponse;
import com.watermelon.entity.Book;
import com.watermelon.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/searching")
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;

//	GET /api/books/search?title=SomeTitle&language=ENGLISH&categoryName=SomeCategory&authorName=SomeAuthor&publisherName=SomePublisher
//	&page=0&size=20&sort=title,asc&sort=publicationDate,desc
	@GetMapping("/books")
	public ApiResponse<PageResponse<List<Book>>> searchBooksByCustomizeQuery(
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String language,
			@RequestParam(required = false) String categoryName,
			@RequestParam(required = false) String authorName,
			@RequestParam(required = false) String publisherName,
			@PageableDefault(page = 0, size = 20) 
			@SortDefaults(
					@SortDefault(direction = Sort.Direction.ASC, sort = {"title" }))
			Pageable pageable)
			{
		PageResponse<List<Book>> data = searchService.searchBooksByCustomizeQuery(title, categoryName, authorName,
				publisherName, language, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

		return ApiResponse.<PageResponse<List<Book>>>builder().message("Data books").data(data).build();
	}

}
