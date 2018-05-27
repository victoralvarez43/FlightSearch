package com.ryanair.flights.model;

import java.util.List;

import com.google.common.base.MoreObjects;

/**
 * Day model.
 * 
 * @author victor
 *
 */
public class Day {

	private int day;
	private List<Flight> flights;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Day").add("day", day).add("flights", flights).toString();
	}

}
