package com.ryanair.flights.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Month;
import com.ryanair.flights.repository.MonthRepository;

@Service()
@Qualifier("monthServiceAPIRyanair")
public class MonthServiceAPIRyanair implements MonthService {

	private static Log logger = LogFactory.getLog(MonthServiceAPIRyanair.class);

	@Autowired
	private MonthRepository<Month> repository;

	@Override
	public Month getMonth(String airportFrom, String airportTo, int year, int month) throws RetryHTTPException {

		return repository.getMonth(airportFrom, airportTo, year, month);
	}

	@Override
	public Set<Month> getMonths(String airportFrom, String airportTo, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime) {
		Set<Month> months = new HashSet<Month>();

		for (LocalDateTime initDate = departureDateTime; initDate.isBefore(arrivalDateTime);) {
			try {
				int monthSearch = initDate.getMonthValue();
				int yearMonth = initDate.getYear();
				// Add one month
				initDate = initDate.plusMonths(1);
				logger.info("Get Month: " + airportFrom + ", " + airportTo + ", " + yearMonth + ", "
						+ monthSearch);
				Month month = getMonth(airportFrom, airportTo, yearMonth, monthSearch);
				months.add(month);

			} catch (Exception e) {
				logger.error("Failed to get month.");
			}
		}

		return months;
	}

}
