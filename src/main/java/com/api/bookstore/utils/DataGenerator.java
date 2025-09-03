package com.api.bookstore.utils;

public class DataGenerator {

	public static String randomEmail() {
		int number = (int) (Math.random() * 10000);
		return "user" + number + "@example.com";
	}
	
	public static int randomID() {
		return (int) (Math.random() * 10000);
		
	}
	
	public static String randomPwd() {
		int number = (int) (Math.random() * 10000);
		return "pwd" + number;
	}
	
	
	public static int randomYear() {
		int startYear = 1700;
	    int endYear = 2025;
	    return startYear + (int) (Math.random() * (endYear - startYear + 1));
	}

}
