package com.ryanair.flights.repository;

import java.util.List;
import java.util.Map;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Route;

public interface RouteRepository<T extends Route> {

	public Map<String, List<T>> getRoutes() throws RetryHTTPException;
}
