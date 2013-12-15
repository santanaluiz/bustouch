package com.arctouch.bustouch.threads;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;

import com.arctouch.bustouch.activities.DeparturesActivity;
import com.arctouch.bustouch.activities.RoutesListActivity;
import com.arctouch.bustouch.json.dto.ResponseDTO;
import com.arctouch.bustouch.json.model.Departure;
import com.arctouch.bustouch.json.services.DeparturesService;

public class GetDeparturesByRouteTask extends AsyncTask<RoutesListActivity, Void, List<Departure>> {
	
	private RoutesListActivity activity;
	
	@Override
	protected List<Departure> doInBackground(RoutesListActivity... activities) {
		this.activity = activities[0];
		
		return DeparturesService.getInstance().findDeparturesByRouteId(this.activity.getSelectedRouteId());
	}
	
	@Override
	protected void onPostExecute(List<Departure> result) {
		ResponseDTO dto = new ResponseDTO();
		dto.setDepartures(result);
		
		activity.startActivity(mountDeparturesIntent(dto));
	}
	
	private Intent mountDeparturesIntent(ResponseDTO dto) {
		Intent intent = new Intent(this.activity, DeparturesActivity.class);
		intent.putExtra("departures", dto);
		
		return intent;
	}
	
}
