package com.arctouch.bustouch.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.arctouch.bustouch.activities.SearchableActivity;
import com.arctouch.bustouch.json.common.DialogBuilder;
import com.arctouch.bustouch.tasks.SearchRoutesTask;

public class SearchClickListener implements OnClickListener {

	private SearchableActivity activity;
	
	
	/**
	 * Constructor that initialize activity variable
	 * @param activity
	 * 	activity that dispatch the search event
	 */
	public SearchClickListener(SearchableActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		DialogBuilder.showProgressDialog("Aguarde um momento.\nO sistema está realizando a busca.", "Buscando", (Activity) this.activity);
		
		// start thread
		new SearchRoutesTask().execute(this.activity);
	}

}
