package com.arctouch.bustouch.json.model;

import java.io.Serializable;

public class Departure extends BaseEntity implements Serializable {
	
	private String time;
	private String calendar;
	public static final String WEEKDAYS_LABEL = "WEEKDAY";
	public static final String SATURDAY_LABEL = "SATURDAY";
	public static final String SUNDAY_LABEL = "SUNDAY";
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getCalendar() {
		return calendar;
	}
	
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
}
