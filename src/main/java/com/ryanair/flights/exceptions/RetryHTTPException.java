package com.ryanair.flights.exceptions;

public class RetryHTTPException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7310064815462493574L;

	public RetryHTTPException(String message) {
		super(message);
	}
}
