package com.ryanair.flights.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;

/**
 * Flight model.
 * 
 * @author victor
 *
 */
public class Flight {

	private Long number;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "HH:mm")
	private LocalTime departureTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "HH:mm")
	private LocalTime arrivalTime;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Flight").add("number", number).add("departureTime", departureTime)
				.add("arrivalTime", arrivalTime).toString();
	}

}
