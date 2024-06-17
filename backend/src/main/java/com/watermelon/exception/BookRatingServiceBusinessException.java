package com.watermelon.exception;

import com.watermelon.config.Translator;

public class BookRatingServiceBusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BookRatingServiceBusinessException(String message, Object...var2) {
		super(Translator.toLocale(message,var2));
	}
}
