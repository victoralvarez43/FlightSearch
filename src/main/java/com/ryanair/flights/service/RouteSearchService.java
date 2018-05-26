package com.ryanair.flights.service;

import java.util.Set;

import com.ryanair.flights.exceptions.RetryHTTPException;

public interface RouteSearchService {
	
	public Set<String> getRoutes(String airportFrom, String airportTo, int stops) throws RetryHTTPException;

}
