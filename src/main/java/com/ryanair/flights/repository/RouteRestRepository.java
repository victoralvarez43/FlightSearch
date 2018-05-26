package com.ryanair.flights.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Route;
import com.ryanair.flights.utils.ExceptionUtils;

@Component
public class RouteRestRepository implements RouteRepository<Route> {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${rest.ryanair.endpoint.routes}")
	private String endpoint;

	private static Log logger = LogFactory.getLog(RouteRestRepository.class);

	@Override
	@Cacheable(value = "routeCache")
	@Retryable(value = { RetryHTTPException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	public Map<String, List<Route>> getRoutes() throws RetryHTTPException {
		logger.info("Get All routes");
		ParameterizedTypeReference<Set<Route>> routes = new ParameterizedTypeReference<Set<Route>>() {
		};
		try {
			ResponseEntity<Set<Route>> response = restTemplate.exchange(endpoint, HttpMethod.GET, null, routes);
			return response.getBody().stream().collect(Collectors.groupingBy(Route::getAirportFrom));
		} catch (HttpStatusCodeException ex) {
			logger.error("Error[" + ex.getStatusCode() + "]: " + ex.getMessage());
			if (ExceptionUtils.isRetry(ex)) {
				throw new RetryHTTPException(ex.getMessage());
			} else {
				throw ex;
			}
		}

	}

}
