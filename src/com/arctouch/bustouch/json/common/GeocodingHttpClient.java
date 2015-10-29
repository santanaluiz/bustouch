package com.arctouch.bustouch.json.common;

import org.json.JSONArray;
import org.json.JSONObject;

public class GeocodingHttpClient extends GenericHttpClient {
	
	/**
	 * Search the street name by geo points
	 * 
	 * @param latLong
	 * @return
	 * 	street name
	 */
	public static String getStreet(String latLong) {
		String jsonObject = getLocationInfo(latLong);
		
		String streetName = null;
		
		try {
			streetName = jsonObject.substring(0, jsonObject.indexOf(",")).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return streetName;
	}
	
	private static String getLocationInfo(String latLong) {
		String json = GeocodingHttpClient.getInstance().sendRequestByGet("http://maps.google.com/maps/api/geocode/json?sensor=false&latlng=" + latLong);
		
		return getFormattedAddressFromJson(json);
	}
	
	private static String getFormattedAddressFromJson(String json) {
		try {
			String formattedJSON = json.substring(json.indexOf("\"formatted_address\""), json.indexOf("\"geometry\""));
			formattedJSON = formattedJSON.substring(formattedJSON.indexOf(":") +1, formattedJSON.lastIndexOf(","));
			
			return formattedJSON.replace("\"", "");
		} catch (Exception e) {
			return null;
		}
	}
}
