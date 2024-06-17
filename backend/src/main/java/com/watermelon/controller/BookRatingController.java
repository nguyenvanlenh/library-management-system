package com.watermelon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watermelon.dto.response.ApiResponse;
import com.watermelon.entity.BookRating;
import com.watermelon.entity.manytomanypk.BookRatingPK;
import com.watermelon.service.BookRatingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ratings")
public class BookRatingController {

	private final BookRatingService bookRatingService;
	
	@GetMapping("/books/{bookId}")
	public ApiResponse<List<BookRating>> getRatingsByBookId(@PathVariable Long bookId){
		return ApiResponse.<List<BookRating>>builder()
				.status(HttpStatus.OK.value())
				.message("Data ratings")
				.data(bookRatingService.getRatingByBookId(bookId))
				.build();
	}
	@PostMapping
	public ApiResponse<BookRatingPK> addRating(@RequestBody BookRating request){
		return ApiResponse.<BookRatingPK>builder()
				.status(HttpStatus.CREATED.value())
				.message("Rating added successfully")
				.data(bookRatingService.addRating(request))
				.build();
	}
	@DeleteMapping
	public ApiResponse<Void> deleteRating(@PathVariable BookRatingPK id){
		bookRatingService.deleteRating(id);
		return ApiResponse.<Void>builder()
				.status(HttpStatus.RESET_CONTENT.value())
				.message("Rating deleted successfully")
				.build();
	}
}
