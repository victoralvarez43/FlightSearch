package com.ryanair.flights.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;

/**
 * Leg model.
 * 
 * @author victor
 *
 */
public class Leg {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime departureDateTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime arrivalDateTime;

	private String departureAirport;

	private String arrivalAirport;

	public Leg(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, String departureAirport,
			String arrivalAirport) {
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}

	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Leg").add("departureDateTime", departureDateTime)
				.add("arrivalDateTime", arrivalDateTime).add(departureAirport, departureAirport)
				.add("arrivalAirport", arrivalAirport).toString();
	}

}
