package com.watermelon.service;

import java.util.List;

import com.watermelon.entity.BookRating;
import com.watermelon.entity.manytomanypk.BookRatingPK;

public interface BookRatingService{

	List<BookRating> getRatingByBookId(Long bookId);
	
	BookRatingPK addRating(BookRating request);
	void deleteRating(BookRatingPK id);
}
