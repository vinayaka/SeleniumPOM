package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	//1. Page Object: By locator
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	private By email=By.id("input-email");
	private By password=By.id("input-password");

	private By loginBtn=By.xpath("//input[@value='Login']");

	private By forgetPwdLink=By.xpath("//div[@class='form-group']//a[normalize-space()='Forgotten Password']");
	private By registerLink = By.linkText("Register");

//2 Public constructor 
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleutil=new ElementUtil(driver);
	}
	
	//3 public page action
	@Step("getLoginPageTitle")
	public String getLoginPageTitle() {
		String title =eleutil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		
		System.out.println("Login Page Title Displayed:"+title);
		return title;
	}
	@Step("Enter the URL")
	public String getLoginPageURL() {
		String URL=eleutil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		
		System.out.println("Login Page URL:"+URL);
		return URL;
	}
	@Step("Forget Password Link")
	public boolean forgetPwdLinkExist() {
		return eleutil.doIsDisplyed(forgetPwdLink);
		
	}
	@Step("User Login with username:{0} and password:{1}")
	public AccountPage doLogin(String username, String pwd) {
		eleutil.doSendKeys(email, username);
		//driver.findElement(email).sendKeys(username);
		eleutil.doSendKeys(password, pwd);
		//driver.findElement(password).sendKeys(pwd);
		System.out.println("==========="+username+"  "+pwd);
		eleutil.doClick(loginBtn);
		//driver.findElement(loginBtn).click();
		System.out.println("After Login");
		return new AccountPage(driver);
		
//		System.out.println("After Login Title"+driver.getTitle());
//		return driver.getTitle();
		
	}
	@Step("Navigate to Registatrion Page")
	public RegisterPage navigateToRegisterPage() {
		eleutil.doClickWithWait(registerLink, TimeUtil.DEFAULT_TIME);
		return new RegisterPage(driver);
	}

	
}
