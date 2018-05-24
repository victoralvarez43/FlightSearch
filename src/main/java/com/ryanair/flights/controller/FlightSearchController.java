package com.ryanair.flights.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Route;
import com.ryanair.flights.service.RouteSearchService;

@RestController
public class FlightSearchController {

	@Autowired
	@Qualifier("routeSearchServiceRyanair")
	private RouteSearchService routeSearch;

	@GetMapping("/")
	public String index(Model model, Principal principal) throws RetryHTTPException {
		for(Route r: routeSearch.getRoutes("BCN", "MLA", 1)){
				System.out.println(r.getAirportFrom() + ", " + r.getAirportTo());
			
		}
		return "index";
	}
}
