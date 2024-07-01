package com.qa.opencart.util;

public class StringUtils {
	public static String getRandomEmailId() {
		String emailId = "userauto" + System.currentTimeMillis() + "@opencart.com";
		System.out.println("user email Id : " + emailId);
		return emailId;
	}
}
