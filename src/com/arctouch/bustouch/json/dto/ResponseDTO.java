package com.arctouch.bustouch.json.dto;

import static com.arctouch.bustouch.util.ValidationUtil.isNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.arctouch.bustouch.json.model.Departure;
import com.arctouch.bustouch.json.model.Route;

public class ResponseDTO implements Serializable {
	
	private List<Route> routes;
	private List<Departure> departures;
	private Route selectedRoute;
	private String txtBusca;
	
	private List<Departure> weekdays;
	private List<Departure> saturdays;
	private List<Departure> sundays;
	
	public List<Departure> getDeparturesByCalendar(String calendar) {
		if (calendar.equals(Departure.WEEKDAYS_LABEL)) {
			if (isNull(weekdays)) {
				fillDepartures();
			}
			
			return weekdays;
		} else if (calendar.equals(Departure.SATURDAY_LABEL)) {
			if (isNull(saturdays)) {
				fillDepartures();
			}
			
			return saturdays;
		} else if (calendar.equals(Departure.SUNDAY_LABEL)) {
			if (isNull(sundays)) {
				fillDepartures();
			}
			
			return sundays;
		}
		
		return null;
	}
	
	private void fillDepartures() {
		initializeDeparturesLists();
		
		for (Departure departure : departures) {
			this.addDepartureToList(departure);
		}
	}

	private void initializeDeparturesLists() {
		this.weekdays = new ArrayList<Departure>();
		this.saturdays = new ArrayList<Departure>();
		this.sundays = new ArrayList<Departure>();
	}
	
	private void addDepartureToList(Departure departure) {
		if (departure.getCalendar().equals(Departure.WEEKDAYS_LABEL)) {
			this.weekdays.add(departure);
		} else if (departure.getCalendar().equals(Departure.SATURDAY_LABEL)) {
			this.sundays.add(departure);
		} else if (departure.getCalendar().equals(Departure.SUNDAY_LABEL)) {
			this.saturdays.add(departure);
		}
	}
	
	public List<Route> getRoutes() {
		return routes;
	}
	
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
	public Route getSelectedRoute() {
		return selectedRoute;
	}
	
	public void setSelectedRoute(Route selectedRoute) {
		this.selectedRoute = selectedRoute;
	}
	
	public String getTxtBusca() {
		return txtBusca;
	}
	
	public void setTxtBusca(String txtBusca) {
		this.txtBusca = txtBusca;
	}

	public List<Departure> getDepartures() {
		return departures;
	}

	public void setDepartures(List<Departure> departures) {
		this.departures = departures;
	}
}
