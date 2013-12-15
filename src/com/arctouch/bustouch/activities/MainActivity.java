package com.arctouch.bustouch.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.arctouch.bustouch.R;
import com.arctouch.bustouch.json.common.DialogBuilder;
import com.arctouch.bustouch.json.dto.ResponseDTO;
import com.arctouch.bustouch.json.model.Route;
import com.arctouch.bustouch.listeners.SearchClickListener;

public class MainActivity extends Activity implements SearchableActivity {


	public static final String TAG = "MainActivity";
	private ImageButton btnBuscar;
	private EditText txtBusca;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		initializeButtons();
		
		
//		final MainActivity main = this;
		// FIXME
//		ImageButton btn = (ImageButton) this.findViewById(R.id.btnTabMaps);
//		
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(main, LocationActivity.class);
//				main.startActivity(i);
//			}
//		});
	}
	
	@Override
	public void receiveListOflinhas(List<Route> routes) {
		Intent i = new Intent(this, RoutesListActivity.class);
		
		ResponseDTO dto = new ResponseDTO();
		dto.setRoutes((ArrayList<Route>) routes);
		dto.setTxtBusca(this.getSearchTextValue());
		
		i.putExtra("linhas", dto);
		
		this.startActivity(i);
		
		DialogBuilder.closeProgressDialog();
	}
	
	private void initializeButtons() {
		this.btnBuscar = (ImageButton) this.findViewById(R.id.btnBuscarMain);
		this.txtBusca = (EditText) this.findViewById(R.id.txtBuscaMain);
		
		this.btnBuscar.setOnClickListener(new SearchClickListener(this));
	}
	
	public String getSearchTextValue() {
		return this.getTxtBusca().getText().toString();
	}
	
	@Override
	protected void onPostResume() {
		super.onResume();
		DialogBuilder.closeProgressDialog();
	}
	
	public ImageButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(ImageButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public EditText getTxtBusca() {
		return txtBusca;
	}

	public void setTxtBusca(EditText txtBusca) {
		this.txtBusca = txtBusca;
	}
	

}
