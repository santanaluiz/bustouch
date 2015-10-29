package com.arctouch.bustouch.json.exception;

public class InternetConnectionException extends Exception {
	/**
	 * Construtor default
	 */
	public InternetConnectionException() {
		super();
	}
	
	
	/**
	 * Construtor lan??ando exception
	 * @param message
	 */
	public InternetConnectionException(String message) {
		super(message);
	}
}
