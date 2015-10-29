package com.arctouch.bustouch.activities;

import java.util.List;

import com.arctouch.bustouch.json.model.Route;

public interface SearchableActivity {
	
	/**
	 * get the text input (search) value
	 * 
	 * @return
	 * 		search text
	 */
	String getSearchTextValue();
	
	/**
	 * Receive list of route from Thread
	 */
	void receiveListOflinhas(List<Route> linhas);
}
