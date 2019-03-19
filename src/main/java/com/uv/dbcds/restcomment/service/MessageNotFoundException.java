package com.uv.dbcds.restcomment.service;

public class MessageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MessageNotFoundException() {
		this("Resource not found!");
	}

	public MessageNotFoundException(String message) {
		this(message, null);
	}

	public MessageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
