package com.watermelon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watermelon.entity.BookRating;
import com.watermelon.entity.manytomanypk.BookRatingPK;

public interface BookRatingRepository extends JpaRepository<BookRating, BookRatingPK> {

	List<BookRating> findByBookRatingKey_BookId(Long bookId);
}
