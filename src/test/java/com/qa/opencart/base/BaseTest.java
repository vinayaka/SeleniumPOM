package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;


import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProdcutInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchresultPage;

public class BaseTest {
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginpage;
	protected AccountPage accpage;
	protected SearchresultPage searchResultPage;
	protected ProdcutInfoPage productInfoPage;
	protected RegisterPage regPage;
	protected SoftAssert softAssert;
	
	@Parameters({"browser"})
	@BeforeTest
	public void Setup(@Optional String browsername) {
		df = new DriverFactory();
		prop = df.initProp();
		if(browsername!=null) {
			prop.setProperty("browser", browsername);
		}
		driver = df.initDriver(prop);
		loginpage = new LoginPage(driver);
		softAssert=new SoftAssert();
	}

	@AfterTest
	public void TearDown() {
		  
		driver.quit();
	}
}
