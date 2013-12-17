package com.arctouch.bustouch.components;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.arctouch.bustouch.json.common.CommonData;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class LongPressMapView extends MapView {

	public interface OnLongpressListener {
		public void onLongpress(MapView view, GeoPoint longpressLocation);
	}

	static final int LONGPRESS_THRESHOLD = 1000;

	private GeoPoint lastMapCenter;

	private Timer longpressTimer = new Timer();
	private LongPressMapView.OnLongpressListener longpressListener;

	public LongPressMapView(Context context, String apiKey) {
		super(context, apiKey);
	}

	public LongPressMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LongPressMapView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnLongpressListener(
			LongPressMapView.OnLongpressListener listener) {
		longpressListener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		handleLongpress(event);

		return super.onTouchEvent(event);
	}

	private void handleLongpress(final MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// Finger has touched screen.
			int teste = ((int) event.getY() - (CommonData.SEARCH_LAYOUT_HEIGHT));
			longpressTimer = new Timer();
			longpressTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					GeoPoint longpressLocation = getProjection().fromPixels(
							(int) event.getX(), ((int) event.getY() - (CommonData.SEARCH_LAYOUT_HEIGHT)));

					/*
					 * Fire the listener. We pass the map location of the
					 * longpress as well, in case it is needed by the caller.
					 */
					longpressListener.onLongpress(LongPressMapView.this,
							longpressLocation);
				}

			}, LONGPRESS_THRESHOLD);

			lastMapCenter = getMapCenter();
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			if (!getMapCenter().equals(lastMapCenter)) {
				// User is panning the map, this is no longpress
				longpressTimer.cancel();
			}

			lastMapCenter = getMapCenter();
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			// User has removed finger from map.
			longpressTimer.cancel();
		}

		if (event.getPointerCount() > 1) {
			// This is a multitouch event, probably zooming.
			longpressTimer.cancel();
		}
	}
}
