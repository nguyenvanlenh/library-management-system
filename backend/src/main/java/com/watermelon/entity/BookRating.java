package com.watermelon.entity;

import com.watermelon.entity.manytomanypk.BookRatingPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_ratings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRating {

	@EmbeddedId
	private BookRatingPK bookRatingKey;
	@Column(length = 2)
	private Integer star;
	
	private String comment;
	
	@MapsId("bookId")
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@MapsId("userId")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
