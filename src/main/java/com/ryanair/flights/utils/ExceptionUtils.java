package com.ryanair.flights.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Utils methods by exceptions.
 * 
 * @author victor
 *
 */
public class ExceptionUtils {

	/**
	 * Check if httpstatus is valid for retry or not.
	 * 
	 * @param ex
	 * @return
	 */
	public static boolean isRetry(HttpStatusCodeException ex) {
		if (HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.equals(ex.getStatusCode())
				|| HttpStatus.SERVICE_UNAVAILABLE.equals(ex.getStatusCode())) {
			return true;
		} else {
			return false;
		}
	}

}
