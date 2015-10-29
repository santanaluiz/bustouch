package com.arctouch.bustouch.json.exception;

public class LinhaNotFoundException extends Exception {
	/**
	 * Construtor default
	 */
	public LinhaNotFoundException() {
		super();
	}
	
	
	/**
	 * Construtor lan??ando exception
	 * @param message
	 */
	public LinhaNotFoundException(String message) {
		super(message);
	}
}
