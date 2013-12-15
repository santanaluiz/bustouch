package com.arctouch.bustouch.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.arctouch.bustouch.util.ValidationUtil.*;

public class Route extends BaseEntity implements Serializable {
	
	private String shortName;
	private String longName;
	private Date lastModifiedDate;
	private List<Departure> departures;
	private Agency agency;
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getLongName() {
		return longName;
	}
	
	public void setLongName(String longName) {
		this.longName = longName;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<Departure> getDepartures() {
		return departures;
	}

	public void setDepartures(List<Departure> departures) {
		this.departures = departures;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public String getDisplayName() {
		if (isNotNull(this.shortName) && isNotNull(this.longName)) 
			return this.shortName + " - " + this.longName;
		else
			return null;
	}
}
