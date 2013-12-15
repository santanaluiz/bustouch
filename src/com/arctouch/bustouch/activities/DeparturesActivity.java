package com.arctouch.bustouch.activities;

import static com.arctouch.bustouch.util.ValidationUtil.isNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.arctouch.bustouch.R;
import com.arctouch.bustouch.json.common.DialogBuilder;
import com.arctouch.bustouch.json.dto.ResponseDTO;
import com.arctouch.bustouch.json.model.Departure;

public class DeparturesActivity extends ListActivity {

	private View emptyMessage;
	private View departuresTime;
	private Spinner spinnerWeekDays;
	private ResponseDTO dto;
	private static final int WEEKDAYS_POSITION = 0;
	private static final int SATURDAY_POSITION = 1;
	private static final int SUNDAY_POSITION = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.route_departures);
		
		initializeElements();
		this.setSelectedCalendar();
		
		DialogBuilder.closeProgressDialog();
	}
	
	public void showRouteDepartures(String departureCalendar) {
		if (isNotNull(this.dto) && isNotNull(this.dto.getDepartures())) {
			List<Map<String, String>> dataList = parseDeparturesToMap(this.dto.getDeparturesByCalendar(departureCalendar));
			ListAdapter adapter = createAdapter(dataList);
			
			this.showList(adapter);
		} else {
			this.hideList();
		}
	}
	
	
	private void initializeElements() {
		this.departuresTime = this.getListView();
		this.emptyMessage = this.findViewById(R.id.txtEmptyMessage);
				
		this.spinnerWeekDays = (Spinner) this.findViewById(R.id.cmbHorarios);
		fillSpinnerContent();
		setSpinnerListener();
		
		this.dto = (ResponseDTO) this.getIntent().getSerializableExtra("departures");
	}

	private void setSpinnerListener() {
		final DeparturesActivity horActivity = this;
		spinnerWeekDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long idSpinner) {
				horActivity.setSelectedCalendar();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}

	private void fillSpinnerContent() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.horarios_combo, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerWeekDays.setAdapter(adapter);
		spinnerWeekDays.setSelection(0);
	}

	@Override
	protected Dialog onCreateDialog(int errorNumber) {
		return DialogBuilder.onCreateDialogOverride(errorNumber, this);
	}
	
	
	private ListAdapter createAdapter(List<Map<String, String>> dataList) {
		return new SimpleAdapter(this, dataList , R.layout.departure_row, 
                new String[] { "time" }, 
                new int[] { R.id.txtRouteDeparture });
	}

	private List<Map<String, String>> parseDeparturesToMap(List<Departure> departures) {
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		
		for (Departure departure: departures) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(departure.getId()));
			map.put("time", departure.getTime());
			
			dataList.add(map);
		}
		
		return dataList;
	}

	private void showList(ListAdapter adapter) {
		this.setListAdapter(adapter);
		this.emptyMessage.setVisibility(View.INVISIBLE);
		this.departuresTime.setVisibility(View.VISIBLE);
		
		DialogBuilder.closeProgressDialog();
	}
	
	public void hideList() {
		this.setListAdapter(null);
		this.emptyMessage.setVisibility(View.VISIBLE);
		this.departuresTime.setVisibility(View.INVISIBLE);
		
		DialogBuilder.closeProgressDialog();
	}
	
	/**
	 * Update the departures day
	 * 
	 * @param position
	 * 		0 - Weekdays
	 * 		1 - Saturday
	 * 		2 - Sunday
	 */
	public void setSelectedCalendar() {
		int position = 0;
		
		if (spinnerWeekDays != null) {
			position = spinnerWeekDays.getSelectedItemPosition();
		}
		
		DialogBuilder.showProgressDialog("Aguarde um momento.\nO sistema está realizando a busca.", "Buscando", this);
		
		switch (position) {
			case WEEKDAYS_POSITION:
				this.showRouteDepartures(Departure.WEEKDAYS_LABEL);
				break;
			case SATURDAY_POSITION:
				this.showRouteDepartures(Departure.SATURDAY_LABEL);
				break;
			case SUNDAY_POSITION:
				this.showRouteDepartures(Departure.SUNDAY_LABEL);
				break;
			default:
				this.showRouteDepartures(Departure.WEEKDAYS_LABEL);
				break;
		}
		
		DialogBuilder.closeProgressDialog();
	}
	


	
}
