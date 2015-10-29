package com.arctouch.bustouch.tasks;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.arctouch.bustouch.activities.SearchableActivity;
import com.arctouch.bustouch.json.common.CommonData;
import com.arctouch.bustouch.json.exception.InternetConnectionException;
import com.arctouch.bustouch.json.exception.LinhaNotFoundException;
import com.arctouch.bustouch.json.model.Route;
import com.arctouch.bustouch.json.services.RoutesService;

public class SearchRoutesTask extends AsyncTask<SearchableActivity, Void, List<Route>> {
	
	private SearchableActivity activity;
	
	
	@Override
	protected List<Route> doInBackground(SearchableActivity... activities) {
		this.activity = activities[0];
		
		return RoutesService.getInstance().findRoutesByStopName(this.activity.getSearchTextValue());
	}
	
	@Override
	protected void onPostExecute(List<Route> result) {
		 this.activity.receiveListOflinhas(result);
	}
	
}
