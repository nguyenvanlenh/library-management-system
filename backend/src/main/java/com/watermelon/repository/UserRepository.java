package com.watermelon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watermelon.entity.User;


public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
