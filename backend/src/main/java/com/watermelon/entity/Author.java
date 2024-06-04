package com.watermelon.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="authors")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

}
