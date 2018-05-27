package com.ryanair.flights.service;

import java.util.Set;

import com.ryanair.flights.exceptions.RetryHTTPException;

/**
 * Interface that route search services must implement.
 * 
 * @author victor
 *
 */
public interface RouteSearchService {

	/**
	 * Search all routes between airportFrom and airortTo with stops.
	 * 
	 * @param airportFrom
	 * @param airportTo
	 * @param stops
	 * @return
	 * @throws RetryHTTPException
	 */
	public Set<String> getRoutes(String airportFrom, String airportTo, int stops) throws RetryHTTPException;

}
