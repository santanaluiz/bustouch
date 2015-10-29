package com.arctouch.bustouch.tasks;

import static com.arctouch.bustouch.util.ValidationUtil.isNotNull;

import java.util.List;

import android.location.Location;
import android.os.AsyncTask;

import com.arctouch.bustouch.activities.LocationActivity;
import com.arctouch.bustouch.activities.SearchableActivity;
import com.arctouch.bustouch.json.common.GeocodingHttpClient;
import com.arctouch.bustouch.json.model.Route;
import com.arctouch.bustouch.json.services.RoutesService;

public class GeocodingTask extends AsyncTask<LocationActivity, Void, String> {
	
	private LocationActivity activity;
	
	
	@Override
	protected String doInBackground(LocationActivity... activities) {
		this.activity = activities[0];
		
		return GeocodingHttpClient.getStreet(extractLatitudeLongitude());
	}
	
	@Override
	protected void onPostExecute(String street) {
		if (isNotNull(street) && street.length() > 0) {
			this.activity.getTxtBusca().setText(street);
		}
		 this.activity.moveMapToLocation(street, isByGps());
	}
	
	private boolean isByGps() {
		if (isNotNull(this.activity.getCurrentLocation())) {
			return true;
		}
		
		return false;
	}

	private String extractLatitudeLongitude() {
		if (isNotNull(this.activity.getGeoPoint())) {
			double latitude = ((double) this.activity.getGeoPoint().getLatitudeE6()) / 1000000;
			double longitude = ((double) this.activity.getGeoPoint().getLongitudeE6()) / 1000000;
			
			return latitude + "," + longitude;
		} else if (isNotNull(this.activity.getCurrentLocation())) {
			return this.activity.getCurrentLocation().getLatitude() + "," + this.activity.getCurrentLocation().getLongitude();
		}
		
		return "";
	}
	
}
