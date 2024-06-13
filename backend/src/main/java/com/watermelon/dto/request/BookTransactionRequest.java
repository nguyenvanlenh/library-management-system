package com.watermelon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTransactionRequest {

	private Long bookId;
	private String userId;
	private int numberDayBorrow;
}
