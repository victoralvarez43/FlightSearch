package com.ryanair.flights.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.retry.annotation.Retryable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Route;

@Service()
@Qualifier("routeSearchServiceRyanair")
public class RouteSearchServiceRyanair implements RouteSearchService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${rest.ryanair.endpoint.routes}")
	private String endpoint;
	
	  private static Log logger = LogFactory.getLog(RouteSearchServiceRyanair.class);


	@Override
	@Cacheable(value = "routeCache")
	@Retryable(value = { RestClientException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	public Map<String, List<Route>> getRoutes() throws RetryHTTPException {
		ParameterizedTypeReference<List<Route>> routes = new ParameterizedTypeReference<List<Route>>() {
		};
		ResponseEntity<List<Route>> response = restTemplate.exchange(endpoint, HttpMethod.GET, null, routes);
		return response.getBody().stream().collect(Collectors.groupingBy(Route::getAirportFrom));
	}

	@Override
	public Set<Route> getRoutes(String airportFrom, String airportTo, int stops) throws RetryHTTPException {
		Set<Route> result = new HashSet<Route>();
		Map<String, List<Route>> routes = getRoutes();

		return searchRoute(routes, airportFrom, airportTo, stops, result);
	}

	private Set<Route> searchRoute(Map<String, List<Route>> routes,String airportFrom, String airportTo, int stops, Set<Route> result) {
		logger.debug("Search: " + airportFrom + ", " + airportTo);
		if (stops == 0) {
			return result;
		} else {
			for (Route route : routes.get(airportFrom)) {
				if (airportTo.equals(route.getAirportTo())) {
					// Add to result set
					result.add(route);
					logger.debug(airportTo);
				}else {
					return searchRoute(routes, route.getAirportTo(), airportTo, stops -1, result);
				}
			}			
		}
		return result;
	}

}
