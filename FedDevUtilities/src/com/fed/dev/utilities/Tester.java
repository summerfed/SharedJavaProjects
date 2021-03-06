package com.fed.dev.utilities;

public class Tester {
	public static void getCurrentExecution() {
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		System.out.println("Classname:Methodname = " + className +":"+ methodName);
	}
	
	public static String getCurrentExecutionClassName() {
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		return className;
	}
	
	public static String getCurrentExecutionMethodName() {
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		return methodName;
	}
}
