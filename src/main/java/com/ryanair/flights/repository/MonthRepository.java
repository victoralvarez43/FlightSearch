package com.ryanair.flights.repository;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Month;

/**
 * Repository for months.
 * 
 * @author victor
 *
 * @param <T>
 */
public interface MonthRepository<T extends Month> {

	/**
	 * Get Month between airportFrom-airortTo and year and month.
	 * 
	 * @param airportFrom
	 * @param airportTo
	 * @param year
	 * @param monh
	 * @return
	 * @throws RetryHTTPException
	 */
	public T getMonth(String airportFrom, String airportTo, int year, int monh) throws RetryHTTPException;
}
