package com.ryanair.flights.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Route;
import com.ryanair.flights.repository.RouteRepository;

@Service()
@Qualifier("routeSearchServiceAPIRyanair")
public class RouteSearchServiceAPIRyanair implements RouteSearchService {

	@Autowired
	private RouteRepository<Route> repository;

	@Override
	public Set<String> getRoutes(String airportFrom, String airportTo, int stops) throws RetryHTTPException {
		Set<String> result = new HashSet<String>();
		Map<String, List<Route>> routes = repository.getRoutes();
		String ruta = "";
		searchRoute(routes, airportFrom, airportTo, stops, result, ruta);
		return result;
	}

	private void searchRoute(Map<String, List<Route>> routes, String airportFrom, String airportTo, int stops,
			Set<String> result, String ruta) {
		if (stops >= 0) {
			ruta += airportFrom + "-";
			if (routes.containsKey(airportFrom)) {
				for (Route route : routes.get(airportFrom)) {
					if (airportTo.equals(route.getAirportTo())) {
						// Add to result set
						result.add(ruta + airportTo);
					} else {
						searchRoute(routes, route.getAirportTo(), airportTo, stops - 1, result, ruta);
					}
				}
			}
		}
	}

}
