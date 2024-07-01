package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

public class AccountPage {
	private WebDriver driver;
	private ElementUtil util;
	
	public  AccountPage(WebDriver driver) {
		this.driver=driver;
		util=new ElementUtil(driver);
	}
	
	private By logoutLink=By.linkText("Logout");
	private By header=By.cssSelector("div.container h2");
	private By Search=By.name("search");
	private By SearchIcon=By.cssSelector("div#search button");
	
	

	public String getAccountPageTitle() {
		
		String title=driver.getTitle();
		System.out.println("Account Title:"+title);
		return title;
	}
	
	public String getAccountURL() {
		String URL=driver.getCurrentUrl();
		System.out.println("Account URL "+URL);
		return URL;
	}

	public boolean isLogoutLinkExist() {
		return driver.findElement(logoutLink).isDisplayed();
	}
	
	public List<String> getAccPageHeader() {
		List<WebElement> headerlist=util.waitForVisibilityOfElemenetsLocated(header,TimeUtil.DEFAULT_TIME);
		List<String> headerarrlist=new ArrayList<String>();
		
		for(WebElement e:headerlist) {
			String text=e.getText();
			headerarrlist.add(text);
		}
		return headerarrlist;
	}
	
	public boolean isSearchExist() {
		return driver.findElement(Search).isDisplayed();
	}
	public SearchresultPage doSearch(String searchKey) {

	System.out.println("Serach key "+searchKey);
	if(isSearchExist()) {
		util.doSendKeys(Search, searchKey);
		util.doClick(SearchIcon);
	
	return new SearchresultPage(driver);
	}else {
		System.out.println("Search key not present on the page");
		return null;
	}
	}
}
