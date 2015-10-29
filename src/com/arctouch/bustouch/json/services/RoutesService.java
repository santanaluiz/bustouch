package com.arctouch.bustouch.json.services;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arctouch.bustouch.json.common.CommonData;
import com.arctouch.bustouch.json.common.GenericHttpClient;
import com.arctouch.bustouch.json.common.GenericParser;
import com.arctouch.bustouch.json.model.Departure;
import com.arctouch.bustouch.json.model.Route;
import static com.arctouch.bustouch.util.ValidationUtil.*;
import com.google.gson.reflect.TypeToken;

public class RoutesService extends AbstractService {
	
	private static RoutesService instance;
	private static final String FIND_ROUTES_BY_STOP_NAME_URL = CommonData.SERVER_URL + "/v1/queries/findRoutesByStopName/run";
	private GenericHttpClient client;
	private Map<String, String> headers;
	
	public static RoutesService getInstance() {
		if (isNull(instance)) {
			instance = new RoutesService();
		}
		
		return instance;
	}
	
	public List<Route> findRoutesByStopName(String stopName) {
		String resultJSON = getJsonRoutesByStopNameFromServer(stopName);
		GenericParser<Route> parser = new GenericParser<Route>();
		
		return parser.getEntityListFromJson(resultJSON, getCollectionType());
	}
	
	private String getJsonRoutesByStopNameFromServer(String stopName) {
		 initializeClient();
		 initializeHeaders();
		 
		 String responseJSON = executePost(FIND_ROUTES_BY_STOP_NAME_URL, getRequestBodyToFindRoutesByStopName(stopName));
		 
		 return extractRowsFromJSON(responseJSON);
	}
	
	private String getRequestBodyToFindRoutesByStopName(String stopName) {
		return "{\"params\": {\"stopName\":\"%" + stopName + "%\"}}";
	}

	@Override
	public Type getCollectionType() {
		return new TypeToken<List<Route>>(){}.getType();
	}
	
}
