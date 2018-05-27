package com.ryanair.flights.repository;

import java.util.List;
import java.util.Map;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Route;

/**
 * Repository from routes.
 * 
 * @author victor
 *
 * @param <T>
 */
public interface RouteRepository<T extends Route> {

	/**
	 * Get Map with airportFrom key and values are all possibles routes.
	 * 
	 * @return
	 * @throws RetryHTTPException
	 */
	public Map<String, List<T>> getRoutes() throws RetryHTTPException;
}
