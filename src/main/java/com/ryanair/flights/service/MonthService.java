package com.ryanair.flights.service;

import java.time.LocalDateTime;
import java.util.Set;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Month;

/**
 * Interface that month services must implement.
 * 
 * @author victor
 *
 */
public interface MonthService {

	/**
	 * Get Month by aiportFrom, airportTo and year and month.
	 * 
	 * @param airportFrom
	 * @param airportTo
	 * @param year
	 * @param month
	 * @return
	 * @throws RetryHTTPException
	 */
	public Month getMonth(String airportFrom, String airportTo, int year, int month) throws RetryHTTPException;

	/**
	 * Get all months between departureDateTime and arrivalDateTime and airportFrom
	 * and airportTo.
	 * 
	 * @param airportFrom
	 * @param airportTo
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return
	 */
	public Set<Month> getMonths(String airportFrom, String airportTo, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime);
}
