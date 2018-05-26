package com.ryanair.flights.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Preconditions.checkArgument;
import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Day;
import com.ryanair.flights.model.Flight;
import com.ryanair.flights.model.Interconnection;
import com.ryanair.flights.model.Leg;
import com.ryanair.flights.model.LegBuilder;
import com.ryanair.flights.model.Month;
import com.ryanair.flights.service.MonthService;
import com.ryanair.flights.service.RouteSearchService;

@RestController
@RequestMapping("flightSearch")
public class FlightSearchController {

	private static Log logger = LogFactory.getLog(FlightSearchController.class);

	@Autowired
	@Qualifier("routeSearchServiceAPIRyanair")
	private RouteSearchService routeSearch;

	@Autowired
	@Qualifier("monthServiceAPIRyanair")
	private MonthService monthService;

	@Value("${flightSearch.timebetweenflights}")
	private int timeBetweenFlights;

	@GetMapping("/interconnections")
	public List<Interconnection> searchFlights(@RequestParam(name = "departure") String airportFrom,
			@RequestParam(name = "arrival") String airportTo,
			@RequestParam(name = "departureDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
			@RequestParam(name = "arrivalDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime)
			throws RetryHTTPException {
		checkArgument(departureDateTime.isBefore(arrivalDateTime), "Departure date must be greater than arrival date.");
		logger.info("Search Routes!!");
		List<Interconnection> result = new LinkedList<Interconnection>();

		for (String route : routeSearch.getRoutes(airportFrom, airportTo, 1)) {
			String[] codes = route.split("-");
			List<Interconnection> resultByRoute = new LinkedList<Interconnection>();
			generateRouteInterconnections(departureDateTime, arrivalDateTime, codes, 1, new ArrayList<Leg>(),
					resultByRoute);
			result.addAll(resultByRoute);
		}
		return result;
	}

	public void generateRouteInterconnections(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
			String[] codes, int index, List<Leg> legs, List<Interconnection> routeInterconnections) {

		if (index < codes.length) {
			Set<Month> months = monthService.getMonths(codes[index - 1], codes[index], departureDateTime,
					arrivalDateTime);
			for (Leg leg : getLegs(codes[index - 1], codes[index], departureDateTime, arrivalDateTime, months)) {
				List<Leg> legsByRoute = new ArrayList<Leg>();
				legsByRoute.addAll(legs);
				legsByRoute.add(leg);
				generateRouteInterconnections(leg.getArrivalDateTime().plusHours(timeBetweenFlights), arrivalDateTime,
						codes, index + 1, legsByRoute, routeInterconnections);
			}
		} else {
			if ((codes.length - 1) == legs.size()) {
				routeInterconnections.add(new Interconnection(codes.length - 2, legs));
			}
		}
	}

	private List<Leg> getLegs(String departureAirport, String arrivalAirport, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime, Collection<Month> months) {
		List<Leg> legs = new ArrayList<Leg>();
		for (Month month : months) {
			for (Day day : month.getDays()) {
				LocalDate dayOfMonth = LocalDate.of(month.getYear(), month.getMonth(), day.getDay());
				if (dayOfMonth.compareTo(departureDateTime.toLocalDate()) >= 0
						&& dayOfMonth.compareTo(arrivalDateTime.toLocalDate()) <= 0) {
					for (Flight flight : day.getFlights()) {
						if (LocalDateTime
								.of(month.getYear(), month.getMonth(), day.getDay(),
										flight.getDepartureTime().getHour(), flight.getDepartureTime().getMinute())
								.compareTo(departureDateTime) >= 0
								&& (LocalDateTime
										.of(month.getYear(), month.getMonth(), day.getDay(),
												flight.getArrivalTime().getHour(), flight.getArrivalTime().getMinute())
										.compareTo(arrivalDateTime) <= 0)) {
							Leg leg = new LegBuilder().setArrivalAirport(arrivalAirport)
									.setDepartureAirport(departureAirport)
									.setArrivalDateTime(LocalDateTime.of(dayOfMonth, flight.getArrivalTime()))
									.setDepartureDateTime(LocalDateTime.of(dayOfMonth, flight.getDepartureTime()))
									.build();
							legs.add(leg);
						}
					}
				}
			}
		}
		return legs;
	}
}
