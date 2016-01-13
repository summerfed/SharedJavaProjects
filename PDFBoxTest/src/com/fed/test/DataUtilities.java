package com.fed.test;

public class DataUtilities {
	
	/**
	 * IF object is null then return empty string
	 * @param object
	 * @return
	 */
	public static String toStringValue(Object object) {
		if(object!=null) {
			if(!object.toString().equalsIgnoreCase("null")) {
				return object.toString();
			}
		}
		
		return "";
	}
	
	public static long toLongValue(Object object) {
		if(!(object.toString().equalsIgnoreCase("null") && object!=null)) {
			if(object instanceof Number) {
				Number tmp = (Number) object;
				return tmp.longValue();
			}
			
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