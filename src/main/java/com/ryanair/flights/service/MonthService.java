package com.ryanair.flights.service;

import java.time.LocalDateTime;
import java.util.Set;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Month;

public interface MonthService {

	public Month getMonth(String airportFrom, String airportTo, int year, int month)  throws RetryHTTPException;
	
	public Set<Month> getMonths(String airportFrom, String airportTo, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime);
}
