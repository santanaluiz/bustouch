package com.arctouch.bustouch.json.model;

import java.io.Serializable;

public class Agency extends BaseEntity implements Serializable {
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
