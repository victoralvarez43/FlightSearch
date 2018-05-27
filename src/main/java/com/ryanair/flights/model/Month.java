package com.ryanair.flights.model;

import java.util.List;

import com.google.common.base.MoreObjects;

/**
 * Month model.
 * 
 * @author victor
 *
 */
public class Month {

	private int month;
	private List<Day> days;
	private int year;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Month").add("month", month).add("year", year).add("days", days).toString();
	}
}
