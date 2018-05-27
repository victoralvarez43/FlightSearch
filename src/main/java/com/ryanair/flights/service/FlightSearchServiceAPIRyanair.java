package com.ryanair.flights.service;

import static com.google.common.base.Preconditions.checkArgument;

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
import org.springframework.stereotype.Service;

import com.ryanair.flights.exceptions.RetryHTTPException;
import com.ryanair.flights.model.Day;
import com.ryanair.flights.model.Flight;
import com.ryanair.flights.model.Interconnection;
import com.ryanair.flights.model.Leg;
import com.ryanair.flights.model.LegBuilder;
import com.ryanair.flights.model.Month;

@Service()
@Qualifier("flightSearchServiceAPIRyanair")
public class FlightSearchServiceAPIRyanair implements FlightSearchService {

	private static Log logger = LogFactory.getLog(FlightSearchServiceAPIRyanair.class);

	@Autowired
	@Qualifier("routeSearchServiceAPIRyanair")
	private RouteSearchService routeSearch;

	@Autowired
	@Qualifier("monthServiceAPIRyanair")
	private MonthService monthService;

	@Value("${flightSearch.timebetweenflights}")
	private int timeBetweenFlights;

	@Override
	public List<Interconnection> flightSearch(String airportFrom, String airportTo, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime, int stops) throws RetryHTTPException {
		checkArgument(departureDateTime.isBefore(arrivalDateTime), "Departure date must be greater than arrival date.");
		logger.info("Search Flights: " + airportFrom + " to " + airportTo + ", " + departureDateTime + " - "
				+ arrivalDateTime + " with " + stops);
		List<Interconnection> result = new LinkedList<Interconnection>();

		for (String route : routeSearch.getRoutes(airportFrom, airportTo, stops)) {
			String[] codes = route.split("-");
			List<Interconnection> resultByRoute = new LinkedList<Interconnection>();
			generateRouteInterconnections(departureDateTime, arrivalDateTime, codes, 1, new ArrayList<Leg>(),
					resultByRoute);
			result.addAll(resultByRoute);
		}
		// sort by stops
		result.sort((o1, o2) -> o1.getStops() >= o2.getStops() ? 1 : -1);
		return result;
	}

	/**
	 * Save in reouteInterconnections all interconnections. First get all months
	 * between departuretime and arrivaltime and next calculate all legs.
	 * 
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @param codes
	 * @param index
	 * @param legs
	 * @param routeInterconnections
	 */
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
			// Check if exists all connections
			if ((codes.length - 1) == legs.size()) {
				routeInterconnections.add(new Interconnection(codes.length - 2, legs));
			}
		}
	}

	/**
	 * Get all the legs between the dates of departure and arrival.
	 * 
	 * @param departureAirport
	 * @param arrivalAirport
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @param months
	 * @return
	 */
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
