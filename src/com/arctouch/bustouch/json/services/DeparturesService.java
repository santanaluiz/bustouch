package com.arctouch.bustouch.json.services;

import static com.arctouch.bustouch.util.ValidationUtil.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arctouch.bustouch.json.common.CommonData;
import com.arctouch.bustouch.json.common.GenericHttpClient;
import com.arctouch.bustouch.json.common.GenericParser;
import com.arctouch.bustouch.json.model.Departure;
import com.arctouch.bustouch.json.model.Route;
import com.google.gson.reflect.TypeToken;

public class DeparturesService extends AbstractService {
	
	private static DeparturesService instance;
	private static final String FIND_DEPARTURES_BY_ROUTE_ID = CommonData.SERVER_URL + "/v1/queries/findDeparturesByRouteId/run";
	
	
	public static DeparturesService getInstance() {
		if (isNull(instance)) {
			instance = new DeparturesService();
		}
		
		return instance;
	}
	
	public List<Departure> findDeparturesByRouteId(Long routeId) {
		String resultJSON = getJsonDeparturesFromServer(routeId);
		GenericParser<Departure> parser = new GenericParser<Departure>();
		
		return parser.getEntityListFromJson(resultJSON, getCollectionType());
	}
	
	private String getJsonDeparturesFromServer(Long id) {
		initializeClient();
		initializeHeaders();
		
		String responseJSON = executePost(FIND_DEPARTURES_BY_ROUTE_ID, getRequestBodyToFindDepartures(id));
		
		return extractRowsFromJSON(responseJSON);
	}

	private String getRequestBodyToFindDepartures(Long id) {
		return "{\"params\": {\"routeId\" : " + id + " }}";
	}

	@Override
	public Type getCollectionType() {
		return new TypeToken<List<Departure>>(){}.getType();
	}
	
	
}
