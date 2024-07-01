package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.util.CSVUtil;
import com.qa.opencart.util.ExcelUtil;
import com.qa.opencart.util.StringUtils;

public class RegisterPageTest extends BaseTest{
	
	
	@BeforeClass()
	public void regSetup() {
		regPage = loginpage.navigateToRegisterPage();
	}
	
	
	@DataProvider
	public Object[][] userRegTestData() {
		return new Object[][] {
			{"Arti", "automation", "9876787656", "arti@123", "yes"},
			{"Praful", "automation", "9876787690", "praful@123", "no"},
			{"Madhu", "automation", "9876787876", "madhu@123", "yes"}
		};
	}
	
	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@DataProvider
	public Object[][] userRegTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}
//	
//	
//	@Test(dataProvider = "userRegTestDataFromSheet", groups = {"sanity"})
//	public void userRegisterationTest(String firstName, String lastName, String telephone, String password, String subscribe) {
//				
//		Assert.assertTrue
//		(regPage.userRegister("Arti", "Automatin", StringUtils.getRandomEmailId(), "123344555", "test@124", "Yes"), 
//						AppError.USER_REG_NOT_DONE);
//	}
//	
	@Test(dataProvider="userRegTestDataFromCSV")
	public void userRegisterationTest(String firstname,String lastname,String phonenum,String passwrod,String subscribe)  {
		Assert.assertTrue
		(
				regPage.userRegister(firstname,lastname,StringUtils.getRandomEmailId(),phonenum,passwrod,subscribe),
				
						AppError.USER_REG_NOT_DONE);
	}
}
