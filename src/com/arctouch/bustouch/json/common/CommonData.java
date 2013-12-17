package com.arctouch.bustouch.json.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class CommonData {

	public static final String SERVER_URL = "https://dashboard.appglu.com";
	public static final int CONNECTION_ERROR = 1;
	public static final int HORARIOS_NOT_FOUND = 2;
	public static final int LOCATION_NOT_FOUND = 3;
	public static final int CONFIRM_CHECK_IN = 4;
	public static int SEARCH_LAYOUT_HEIGHT = 0;
	
	/**
	 * Check internet connection
	 * @param activity
	 * @return
	 * 	true if it's online
	 */
	public static boolean isOnline(Activity activity) {
	    ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

	    return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
