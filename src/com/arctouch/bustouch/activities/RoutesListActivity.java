package com.arctouch.bustouch.activities;

import static com.arctouch.bustouch.util.ValidationUtil.isNotNull;
import static com.arctouch.bustouch.util.ValidationUtil.isNull;
import static com.arctouch.bustouch.util.ValidationUtil.not;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.arctouch.bustouch.R;
import com.arctouch.bustouch.json.common.DialogBuilder;
import com.arctouch.bustouch.json.dto.ResponseDTO;
import com.arctouch.bustouch.json.model.Route;
import com.arctouch.bustouch.listeners.RouteClickListener;
import com.arctouch.bustouch.listeners.SearchClickListener;

public class RoutesListActivity extends ListActivity implements SearchableActivity {

	private ListView routesList;
	private TextView emptyMessage;
	private EditText txtSearch;
	private Long selectedRouteId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.routes_list);
		
		initializeScreenElements();
		validateDTO();
	}
	
	private void validateDTO() {
		ResponseDTO dto = (ResponseDTO) this.getIntent().getSerializableExtra("linhas");
		
		if (isNull(dto) || isNull(dto.getRoutes()) || dto.getRoutes().isEmpty()) {
			this.hideList();
		} else {
			this.showRoutes(dto.getRoutes());
		}
		
		if (isNotNull(dto) && isNotNull(dto.getTxtBusca())) {
			txtSearch.setText(dto.getTxtBusca());
		}
	}

	@Override
	protected void onPause() {
		DialogBuilder.closeProgressDialog();
		super.onPause();
	}
	
	@Override
	protected void onPostResume() {
		DialogBuilder.closeProgressDialog();
		super.onResume();
	}
	
	@Override
	protected Dialog onCreateDialog(int errorNumber) {
		return DialogBuilder.onCreateDialogOverride(errorNumber, this);
	}
	
	private void initializeScreenElements() {
		this.txtSearch = (EditText) this.findViewById(R.id.txtBusca);
		this.routesList = this.getListView();
		this.emptyMessage = (TextView) this.findViewById(R.id.lblEmpty);
		
		ImageButton btnBuscar = (ImageButton) this.findViewById(R.id.btnBuscar);
		btnBuscar.setOnClickListener(new SearchClickListener(this));
		this.routesList.setOnItemClickListener(new RouteClickListener(this));
	}
	
	/**
	 * check if the routes were found.
	 * if did: create the adapter and show the list
	 * else: show empty message
	 * 
	 * @param routes
	 * 	routes
	 */
	public void showRoutes(List<Route> routes) {
		if (isNotNull(routes) && not(routes.isEmpty())) {
			List<Map<String, String>> dataList = parseRoutesToMap(routes);
			ListAdapter adapter = createAdapter(dataList);
			
			this.showList(adapter);
		} else {
			this.hideList();
		}
		
		
	}
	
	private ListAdapter createAdapter(List<Map<String, String>> dataList) {
		return new SimpleAdapter(this, dataList , R.layout.listrow, 
                new String[] { "nome", "empresa" }, 
                new int[] { R.id.item_title, R.id.item_subtitle });
	}
	
	private List<Map<String, String>> parseRoutesToMap(List<Route> routes) {
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		
		for (Route route : routes) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(route.getId()));
			map.put("nome", route.getDisplayName());
			// FIXME 
			map.put("empresa", "");
			
			dataList.add(map);
		}
		
		return dataList;
	}
	
	private void showList(ListAdapter adapter) {
		this.setListAdapter(adapter);
		this.emptyMessage.setVisibility(View.INVISIBLE);
		this.routesList.setVisibility(View.VISIBLE);
		
		DialogBuilder.closeProgressDialog();
	}
	
	public void hideList() {
		this.setListAdapter(null);
		this.emptyMessage.setVisibility(View.VISIBLE);
		this.routesList.setVisibility(View.INVISIBLE);
		
		DialogBuilder.closeProgressDialog();
	}

	@Override
	public void receiveListOflinhas(List<Route> routes) {
		if (routes != null && !routes.isEmpty()) {
			this.showRoutes(routes);
		} else {
			this.hideList();
		}
		
		DialogBuilder.closeProgressDialog();
	}
	
	@Override
	public String getSearchTextValue() {
		return this.getTxtSearch().getText().toString();
	}

	public EditText getTxtSearch() {
		return txtSearch;
	}

	public void setTxtSearch(EditText txtSearch) {
		this.txtSearch = txtSearch;
	}

	public Long getSelectedRouteId() {
		return selectedRouteId;
	}

	public void setSelectedRouteId(Long selectedRouteId) {
		this.selectedRouteId = selectedRouteId;
	}

}
