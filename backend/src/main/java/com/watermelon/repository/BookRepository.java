package com.watermelon.repository;

import com.watermelon.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByCategory_Id(Integer categoryId);
}
