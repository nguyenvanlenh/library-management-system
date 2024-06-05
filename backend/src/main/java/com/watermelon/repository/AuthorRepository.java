package com.watermelon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watermelon.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
