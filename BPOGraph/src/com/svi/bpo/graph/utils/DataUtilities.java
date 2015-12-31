package com.svi.bpo.graph.utils;

public class DataUtilities {
	
	public static String toStringValue(Object object) {
		if(!(object.toString().equalsIgnoreCase("null") && object!=null)) {
			return object.toString();
		}
		return null;
	}
	
	public static long toLongValue(Object object) {
		if(!(object.toString().equalsIgnoreCase("null") && object!=null)) {
			Number tmp = (Number) object;
			return tmp.longValue();
		}
		return 0L;
	}
	
	public static double toDoubleValue(Object object) {
		if(!(object.toString().equalsIgnoreCase("null") && object!=null)) {
			Number tmp = (Number) object;
			return tmp.doubleValue();
		}
		return 0.0;
	}
	
	public static boolean toBoolean(Object object) {
		if(!(object.toString().equalsIgnoreCase("null") && object!=null)) {
			return (Boolean) object;
		} else {
			return false;
		}
	}
	
	public static int toInteger(Object object) {
		if(!(object.toString().equalsIgnoreCase("null") && object!=null)) {
			return (int) object;
		} else {
			return 0;
		}

	}
}