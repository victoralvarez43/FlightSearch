package com.ryanair.flights.repository;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Month;

public interface MonthRepository<T extends Month> {

	public T getMonth(String airportFrom, String airportTo, int year, int monh) throws RetryHTTPException;
}
