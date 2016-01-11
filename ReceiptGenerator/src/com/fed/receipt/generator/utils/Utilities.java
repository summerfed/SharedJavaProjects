package com.fed.receipt.generator.utils;

import com.fed.dev.utilities.FileUtility;

public class Utilities {
	private static Utilities instance = new Utilities();
	private FileUtility fileUtil = new FileUtility();
	
	private Utilities () {}
	
	public static Utilities getInstance() {
		return instance;
	}
	
	public FileUtility getFileUtil() {
		return fileUtil;
	}
	
	
}
