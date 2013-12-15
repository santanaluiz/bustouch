package com.arctouch.bustouch.util;

public class ValidationUtil {
	
	public static boolean isNull(Object object) {
		return object == null;
	}
	
	public static boolean isNotNull(Object object) {
		return object != null;
	}
	
	public static boolean not(Boolean checkValue) {
		return !checkValue;
	}
	
}
