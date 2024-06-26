package com.watermelon.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.watermelon.enums.ELanguage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

	private String isbn;

	private String title;

	private String description;

	private ELanguage language;

	private int quantityAvailable;

	private BigDecimal price;

	private LocalDate publicationDate;

	private Integer publisherId;
	private Integer categoryId;

	private List<Integer> listAuthors;

}
