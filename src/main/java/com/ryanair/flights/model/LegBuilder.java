package com.ryanair.flights.model;

import java.time.LocalDateTime;

/**
 * Builder for create Legs.
 * 
 * @author victor
 *
 */
public class LegBuilder {

	private LocalDateTime departureDateTime;
	
	private LocalDateTime arrivalDateTime;
	
	private String departureAirport;
	
	private String arrivalAirport;

	public LegBuilder setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
		return this;
	}

	public LegBuilder setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
		return this;
	}

	public LegBuilder setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
		return this;
	}

	public LegBuilder setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
		return this;
	}
	
	public Leg build() {
		return new Leg(departureDateTime, arrivalDateTime, departureAirport, arrivalAirport);
	}	
	
}
