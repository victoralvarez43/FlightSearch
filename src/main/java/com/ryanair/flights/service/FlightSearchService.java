package com.ryanair.flights.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Interconnection;

/**
 * Interface that flight search services must implement.
 * 
 * @author victor
 *
 */
public interface FlightSearchService {

	/**
	 * Get interconnections between airportFrom-airportTo and
	 * departureDateTime-arrivalDateTime, with stops.
	 * 
	 * @param airportFrom
	 * @param airportTo
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @param stops
	 * @return
	 * @throws RetryHTTPException
	 */
	public List<Interconnection> flightSearch(String airportFrom, String airportTo, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime, int stops) throws RetryHTTPException;
}
