package com.ryanair.flights.model;

import java.util.LinkedList;
import java.util.List;

public class Interconnection {

	private int stops;

	private List<Leg> legs;

	public Interconnection(int stops, List<Leg> legs) {
		this.stops = stops;
		this.legs = legs;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public List<Leg> getLegs() {
		if (legs == null) {
			legs = new LinkedList<Leg>();
		}
		return legs;
	}

	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}

}
