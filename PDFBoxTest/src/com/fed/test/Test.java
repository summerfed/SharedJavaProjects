package com.fed.test;

public class Test {

	public static void main(String[] args) {
		String tmp = "Fed is Working Hard";
		System.out.println(tmp.length());
		
		int part = 3;
		int modulo = tmp.length()/part;
		int section = tmp.length()/part;
		for(int i=0; i<part; i++) {
			int start = (i*section);
			System.out.println(tmp.substring(start, (start+(section))));
		}
		
		if(modulo != 0) {
			int start = (part)*section;
			System.out.println(tmp.substring(start, tmp.length()));
		} 
		
		float range = 15;
		float value = 59;
		System.out.println(Math.floor(value/range));
	}

}
