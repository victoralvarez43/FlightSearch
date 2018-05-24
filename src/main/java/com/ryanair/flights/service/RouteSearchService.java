package com.ryanair.flights.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Route;

public interface RouteSearchService {
	
	public Map<String, List<Route>> getRoutes() throws RetryHTTPException;
	
	public Set<Route> getRoutes(String airportFrom, String airportTo, int stops) throws RetryHTTPException;

}
