package com.watermelon.exception;

import com.watermelon.config.Translator;

public class RedisServiceBusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RedisServiceBusinessException(String message, Object...var2) {
		super(Translator.toLocale(message,var2));
	}
}
