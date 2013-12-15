package com.arctouch.bustouch.json.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeocodingHttpClient extends GenericHttpClient {
	
	/**
	 * Search the address by geo points
	 * 
	 * @param latLong
	 * @return
	 * 	address
	 */
	public static JSONObject getLocationInfo(String latLong) {
		String json = GeocodingHttpClient.getInstance().sendRequestByGet("http://maps.google.com/maps/api/geocode/json?sensor=false&latlng=" + latLong);
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	/**
	 * Search the street name by geo points
	 * 
	 * @param latLong
	 * @return
	 * 	street name
	 */
	public static String getStreet(String latLong) {
		JSONObject jsonObject = GeocodingHttpClient.getLocationInfo(latLong);
		
		String streetName = null;
		
		try {
			streetName = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getString("formatted_address");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return streetName;
	}
}
