package com.arctouch.bustouch.listeners;

import java.util.Map;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.arctouch.bustouch.activities.RoutesListActivity;
import com.arctouch.bustouch.json.common.DialogBuilder;
import com.arctouch.bustouch.tasks.GetDeparturesByRouteTask;

public class RouteClickListener implements OnItemClickListener {

	private RoutesListActivity activity;
	
	public RouteClickListener() {}
	
	public RouteClickListener(RoutesListActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		DialogBuilder.showProgressDialog("Aguarde um momento.\nO sistema está buscando os horários.", "Buscando", activity);
		this.activity.setSelectedRouteId(getSelectedRouteId(position));
		
		new GetDeparturesByRouteTask().execute(this.activity);
	}
	
	private Long getSelectedRouteId(int position) {
		Map<String, String> item = (Map<String, String>) activity.getListView().getItemAtPosition(position);
		return Long.parseLong(item.get("id"));
	}

}
