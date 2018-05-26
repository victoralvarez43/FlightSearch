package com.ryanair.flights.repository;

import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Day;
import com.ryanair.flights.model.Month;
import com.ryanair.flights.utils.ExceptionUtils;

@Component
public class MonthRestRepository implements MonthRepository<Month> {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${rest.ryanair.endpoint.month}")
	private String endpoint;

	private static Log logger = LogFactory.getLog(MonthRestRepository.class);

	@Override
	@Cacheable(value = "monthCache")
	@Retryable(value = { RetryHTTPException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	public Month getMonth(String airportFrom, String airportTo, int year, int month) throws RetryHTTPException {
		logger.debug("Get Year-Month " + year + "-" + month);
		try {
			logger.info("Call: " + endpoint + ", " + airportFrom + ", " + airportTo + ", " + year + ", " + month);
			ResponseEntity<Month> response = restTemplate.getForEntity(endpoint, Month.class, airportFrom, airportTo,
					year, month);
			// order Days by day number
			response.getBody().getDays().sort(Comparator.comparing(Day::getDay));
			response.getBody().setYear(year);
			return response.getBody();
		} catch (HttpStatusCodeException ex) {
			logger.error("Error[" + ex.getStatusCode() + "]: " + ex.getResponseBodyAsString());
			if (ExceptionUtils.isRetry(ex)) {
				throw new RetryHTTPException(ex.getMessage());
			} else {
				throw ex;
			}
		}
	}

}
