package com.watermelon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watermelon.entity.BookTransaction;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long>{

}
