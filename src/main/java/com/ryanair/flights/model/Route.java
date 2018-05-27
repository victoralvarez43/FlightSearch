package com.ryanair.flights.model;

import com.google.common.base.MoreObjects;

/**
 * Route model.
 * 
 * @author victor
 *
 */
public class Route {

	private String airportTo;
	private String airportFrom;
	private String connectingAirport;
	private boolean newRoute;
	private boolean seasonalRoute;
	private String operator;
	private String group;

	public String getAirportTo() {
		return airportTo;
	}

	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}

	public String getAirportFrom() {
		return airportFrom;
	}

	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}

	public String getConnectingAirport() {
		return connectingAirport;
	}

	public void setConnectingAirport(String connectingAirport) {
		this.connectingAirport = connectingAirport;
	}

	public boolean isNewRoute() {
		return newRoute;
	}

	public void setNewRoute(boolean newRoute) {
		this.newRoute = newRoute;
	}

	public boolean isSeasonalRoute() {
		return seasonalRoute;
	}

	public void setSeasonalRoute(boolean seasonalRoute) {
		this.seasonalRoute = seasonalRoute;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airportFrom == null) ? 0 : airportFrom.hashCode());
		result = prime * result + ((airportTo == null) ? 0 : airportTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (airportFrom == null) {
			if (other.airportFrom != null)
				return false;
		} else if (!airportFrom.equals(other.airportFrom))
			return false;
		if (airportTo == null) {
			if (other.airportTo != null)
				return false;
		} else if (!airportTo.equals(other.airportTo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Route").add("airportTo", airportTo).add("airportFrom", airportFrom)
				.add("connectingAirport", connectingAirport).add("group", group).add("newRoute", newRoute)
				.add("seasonalRoute", seasonalRoute).add("operator", operator).toString();
	}

}
