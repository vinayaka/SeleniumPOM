package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.ExtentReportListener;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;


@Epic("Epic 1000..:Desing of opencart shopping application")
@Feature("Login features")
@Story("US 100:Design for login page applicatio")
@Owner("Vinayaka Joshi")
@Issue("Login 123")
@Listeners({ExtentReportListener.class,TestAllureListener.class})
public class LoginPageTest extends BaseTest {
	@Description("Checking Login Page Title..")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1)
	
	public void LoginPageTitleTest() {
		String accttitle = loginpage.getLoginPageTitle();
		Assert.assertEquals(accttitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	@Description("Checking Login PageURl..")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2)
	public void logingPageURl() {
		String actURL = loginpage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}
	@Description("Checking Login forgetPwdLinkExist..")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 3)
	public void forgetPwdLinkExist() {
		Assert.assertTrue(loginpage.forgetPwdLinkExist(), AppError.ELEMENT_NOT_FOUND);
	}
	@Description("Verify Sucessfull login ..")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4)
	@Step("login to the application with username:{0} and password:{1}")
	public void loginTest() {
		accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accpage.getAccountPageTitle(), AppConstants.ACCOUNT_PAGE_TILE, AppError.TITLE_NOT_FOUND);

	}
}
