package com.ryanair.flights.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Interconnection;

public interface FlightSearchService {

	public List<Interconnection> flightSearch(String airportFrom, String airportTo, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime)  throws RetryHTTPException;
}
