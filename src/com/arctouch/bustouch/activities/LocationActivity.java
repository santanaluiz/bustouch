package com.arctouch.bustouch.activities;

import static com.arctouch.bustouch.util.ValidationUtil.isNotNull;

import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.arctouch.bustouch.R;
import com.arctouch.bustouch.components.BusSignOverlay;
import com.arctouch.bustouch.components.LongPressMapView;
import com.arctouch.bustouch.json.common.CommonData;
import com.arctouch.bustouch.json.common.DialogBuilder;
import com.arctouch.bustouch.json.model.Route;
import com.arctouch.bustouch.listeners.SearchClickListener;
import com.arctouch.bustouch.tasks.GeocodingTask;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationActivity extends MapActivity implements LocationListener, SearchableActivity {

	private LongPressMapView map;
	private LocationManager locationManager;
	private Location currentLocation;
	private GeoPoint geoPoint;
	private Geocoder geocoder;
	private static EditText searchTextField;
	private static ImageButton searchButton;
	private List<Overlay> mapOverlays;
	private MapController mapController;
	private BusSignOverlay busSignOverlay;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_by_map);
		initializeScreenElements();
		initializeMapAndLocation();
		loadCurrentLocation();

		final LocationActivity activity = this;
		map.setOnLongpressListener(new LongPressMapView.OnLongpressListener() {
	        public void onLongpress(final MapView view, final GeoPoint longpressLocation) {
	        	activity.geoPoint = longpressLocation;
        		
        		activity.executeGeocodingTask();
	        }
		});
	}
	

	private void loadCurrentLocation() {
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (isNotNull(location)) {
			this.onLocationChanged(location);
		} else {
			try {
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				
				if (isNotNull(location)) {
					this.onLocationChanged(location);
				}				
			} catch (Exception e) {}
		}
	}

	private void initializeScreenElements() {
		this.searchTextField = (EditText) this.findViewById(R.id.txtBuscaMap);
		
		this.searchButton = (ImageButton) this.findViewById(R.id.btnBuscarMap);
		this.searchButton.setOnClickListener(new SearchClickListener(this));
		
		map = (LongPressMapView) this.findViewById(R.id.map);
	}

	private void initializeMapAndLocation() {
		map.setBuiltInZoomControls(true);
		mapOverlays = map.getOverlays();
		mapController = map.getController();
		mapController.setZoom(16);
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		this.currentLocation = location;
		
		executeGeocodingTask();
	}
	
	private void executeGeocodingTask() {
		new GeocodingTask().execute(this);
	}

	public void moveMapToLocation(String streetName, boolean byGps) {
		GeoPoint point = getMapGeopoint(byGps);
		
		if (isNotNull(point)) {
			mapController.animateTo(point);
			
			removeOldOverlays();
			
			if (isNotNull(streetName)) {
				instantiateNewOverlay();
				OverlayItem overlayitem = new OverlayItem(point, "Rua encontrada", streetName);
				
				busSignOverlay.addOverlay(overlayitem);
				mapOverlays.add(busSignOverlay);
			}
			
			setCurrentLocation(null);
			setGeoPoint(null);
		}
	}

	
	private GeoPoint getMapGeopoint(boolean byGps) {
		if (isNotNull(this.getGeoPoint())) {
			return this.getGeoPoint();
		} else if (isNotNull(this.getCurrentLocation())) {
			int latitude = (int) (this.currentLocation.getLatitude() * 1000000);
			int longitude = (int) (this.currentLocation.getLongitude() * 1000000);
			return new GeoPoint(latitude, longitude);
		}
		
		return null;
	}

	private void removeOldOverlays() {
		if (isNotNull(this.getBusSignOverlay()) || this.mapOverlays.size() > 0) {
			this.mapOverlays.clear();
			this.setBusSignOverlay(null);
		}
	}

	private void instantiateNewOverlay() {
		Drawable drawable = this.getResources().getDrawable(R.drawable.placa_onibus);
		busSignOverlay = new BusSignOverlay(drawable, this);
	}

	@Override
	public void receiveListOflinhas(List<Route> routes) {
//		Intent i = new Intent(this, LinhasEncontradasActivity.class);
//		
//		ResponseDTO dto = new ResponseDTO();
//		dto.setLinhas((ArrayList<Linha>) linhas);
//		dto.setTxtBusca(this.getSearchTextValue());
//		
//		i.putExtra("linhas", dto);
//		
//		this.startActivity(i);
//		
//		DialogBuilder.closeProgressDialog();
	}
	
	@Override
	public String getSearchTextValue() {
		return this.getTxtBusca().getText().toString();
	}

	@Override
	protected Dialog onCreateDialog(int number) {
		return DialogBuilder.onCreateDialogOverride(number, this);
	}
	
	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public EditText getTxtBusca() {
		return searchTextField;
	}

	public void setTxtBusca(EditText txtBusca) {
		this.searchTextField = txtBusca;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public BusSignOverlay getBusSignOverlay() {
		return busSignOverlay;
	}

	public void setBusSignOverlay(BusSignOverlay busSignOverlay) {
		this.busSignOverlay = busSignOverlay;
	}

	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}


	public static EditText getSearchTextField() {
		return searchTextField;
	}


	public static void setSearchTextField(EditText searchTextField) {
		LocationActivity.searchTextField = searchTextField;
	}


	public static ImageButton getSearchButton() {
		return searchButton;
	}


	public static void setSearchButton(ImageButton searchButton) {
		LocationActivity.searchButton = searchButton;
	}

}