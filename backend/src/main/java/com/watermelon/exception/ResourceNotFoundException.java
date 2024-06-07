package com.watermelon.exception;

import com.watermelon.config.Translator;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message, Object...var2) {
		super(Translator.toLocale(message,var2));
	}
}
