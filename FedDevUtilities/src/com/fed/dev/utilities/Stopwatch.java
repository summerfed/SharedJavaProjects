package com.fed.dev.utilities;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;



public class Stopwatch {

	private long start;
	private String label;
	
	/**
	 * 
	 * @param name of task to record process time
	 * @return return the class
	 */
	public Stopwatch start(String label) {
		this.start = System.currentTimeMillis();
		this.label = label;
		return this;
	}

	/**
	 * Display label and total process time
	 * @return time duration of the tracked process
	 */
	public String stop() {
		long end = System.currentTimeMillis();
		String result = getTimeDuration(end, start);
		System.out.println(label + ": " + result);
		return result;
	}
	
	public static String getTimeDuration(long endTime, long startTime) {
		
		final String PATTERN = "00";
		final String PATTERN_FOR_MILLI = "00000";
		long milliseconds = endTime - startTime;		
		
		long toHours = TimeUnit.MILLISECONDS.toHours(milliseconds);
		long toMinutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
//		long toSeconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
		
		return customFormat(PATTERN, toHours) + ":" + customFormat(PATTERN, toMinutes) + ":" + customFormat(PATTERN_FOR_MILLI, milliseconds%60000);
	}
	
	private static String customFormat(String pattern, long value ) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}
	
}
