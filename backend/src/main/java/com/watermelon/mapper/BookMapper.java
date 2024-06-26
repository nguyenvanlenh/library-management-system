package com.watermelon.mapper;

import com.watermelon.dto.request.BookRequest;
import com.watermelon.entity.Book;

public class BookMapper {

	public static Book toEntity(BookRequest request) {
		return Book.builder()
				.title(request.getTitle())
				.isbn(request.getIsbn())
				.description(request.getDescription())
				.language(request.getLanguage())
				.quantityAvailable(request.getQuantityAvailable())
				.price(request.getPrice())
				.publicationDate(request.getPublicationDate())
				.build();
	}
}
