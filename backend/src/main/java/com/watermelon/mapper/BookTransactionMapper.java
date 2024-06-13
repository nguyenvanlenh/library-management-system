package com.watermelon.mapper;

import java.time.LocalDate;

import com.watermelon.dto.request.BookTransactionRequest;
import com.watermelon.entity.BookTransaction;

public class BookTransactionMapper {

	public static BookTransaction toEntity(BookTransactionRequest request) {
		return BookTransaction.builder()
				.borrowDate(LocalDate.now())
				.returnDate(LocalDate.now().plusDays(request.getNumberDayBorrow()))
				.build();
	}
}
