package com.ryanair.flights.exceptions;

/**
 * Retry exceptions.
 * 
 * @author victor
 *
 */
public class RetryHTTPException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7310064815462493574L;

	public RetryHTTPException(String message) {
		super(message);
	}
}
