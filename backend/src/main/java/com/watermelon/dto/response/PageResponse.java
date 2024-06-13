package com.watermelon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T>{
		private int currentPage;
		private int size;
		private int totalPage;
		private long totalElement;
		private T content;
	
}
