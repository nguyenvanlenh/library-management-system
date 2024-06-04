package com.watermelon.entity.manytomanypk;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BookRatingPK {

	private String userId;
	private Long bookId;
}
