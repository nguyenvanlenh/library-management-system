package com.watermelon.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.watermelon.dto.request.BookRequest;
import com.watermelon.dto.response.ApiResponse;
import com.watermelon.dto.response.PageResponse;
import com.watermelon.entity.Book;
import com.watermelon.service.BookService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ApiResponse<Book> getBook(@PathVariable Long id) throws JsonProcessingException{
    		Book data = bookService.getBookById(id);
			return ApiResponse.<Book>builder()
        		.message("Data book")
        		.data(data)
        		.build();
    }
    @GetMapping
    public ApiResponse<PageResponse<List<Book>>> getBooks(
    		@PageableDefault(page = 0, size = 20) 
    		@SortDefaults(
			@SortDefault(direction = Sort.Direction.ASC, sort = {"title"})
			) Pageable pageable) throws JsonProcessingException{
    	
    	PageResponse<List<Book>> data = bookService.getAllBooks(pageable);
			return ApiResponse.<PageResponse<List<Book>>>builder()
					.message("Data books")
					.data(data)
					.build();
    }
    @PostMapping
    public ApiResponse<Long> addNewBook(@RequestBody BookRequest request) {
    	return ApiResponse.<Long>builder()
    			.status(HttpStatus.CREATED.value())
    			.message("Book added successfully")
    			.data(bookService.addNewBook(request))
    			.build();
    }
    @DeleteMapping("/{bookId}")
    public ApiResponse<Void> deleteBook(@PathVariable Long bookId){
    	bookService.deleteBook(bookId);
    	return ApiResponse.<Void>builder()
    			.status(HttpStatus.RESET_CONTENT.value())
    			.message("Book deleted successfully")
    			.build();
    }
    
    @PutMapping("/{bookId}")
    public ApiResponse<Void> updateBook(@PathVariable Long bookId, @RequestBody BookRequest request) {
    	bookService.updateBook(bookId, request);
    	return ApiResponse.<Void>builder()
    			.status(HttpStatus.NO_CONTENT.value())
    			.message("Book updated successfully")
    			.build();
    	
    }
    
    @PatchMapping("/{bookId}")
    public ApiResponse<Integer> updateQuantity(@PathVariable Long bookId, @RequestParam Integer quantity) {
    	return ApiResponse.<Integer>builder()
    			.message("Quantity book updated successfully")
    			.data(bookService.updateQuantity(bookId, quantity))
    			.build();
    			
    }
    
   
}
