package com.arctouch.bustouch.tests;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

import com.arctouch.bustouch.json.model.Departure;
import com.arctouch.bustouch.json.model.Route;
import com.arctouch.bustouch.json.services.DeparturesService;
import com.arctouch.bustouch.json.services.RoutesService;

public class RoutesServiceTest {
	
	@Test
	public void findRoutesByStopName() {
		List<Route> routes = RoutesService.getInstance().findRoutesByStopName("lauro linhares");
		
		assertThat(routes, not(nullValue()));
		assertThat(routes.size(), is(greaterThan(0)));
	}
	
	@Test
	public void findDeparturesByRouteId() {
		Route routeTest = RoutesService.getInstance().findRoutesByStopName("lauro linhares").get(0);
		List<Departure> departures = DeparturesService.getInstance().findDeparturesByRouteId(routeTest.getId());
		
		assertThat(departures, not(nullValue()));
		assertThat(departures.size(), is(greaterThan(0)));
	}

}
