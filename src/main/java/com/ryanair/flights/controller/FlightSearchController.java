package com.ryanair.flights.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Interconnection;
import com.ryanair.flights.service.FlightSearchService;

@RestController
@RequestMapping("flightSearch")
public class FlightSearchController {

	@Autowired
	@Qualifier("flightSearchServiceAPIRyanair")
	private FlightSearchService flightSearchService;

	@GetMapping("/interconnections")
	public List<Interconnection> flightSearch(@RequestParam(name = "departure") String airportFrom,
			@RequestParam(name = "arrival") String airportTo,
			@RequestParam(name = "departureDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
			@RequestParam(name = "arrivalDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime)
			throws RetryHTTPException {
		return flightSearchService.flightSearch(airportFrom, airportTo, departureDateTime, arrivalDateTime);
	}
}
