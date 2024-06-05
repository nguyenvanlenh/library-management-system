package com.watermelon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watermelon.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	boolean existsByName(String name);
}
