package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accSetup() {
		accpage=loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	@Test
	public void acctpageTitleTest() {
		Assert.assertEquals(accpage.getAccountPageTitle(),AppConstants.ACCOUNT_PAGE_TILE,AppError.TITLE_NOT_FOUND);
	}
	
	@Test
	public void acctpageURLTest() {
		Assert.assertTrue(accpage.getAccountURL().contains(AppConstants.ACCOUNT_PAGE_FRACTINAL_URL),AppError.URL_NOT_FOUND);
	}
	
	@Test
	public void acctPageHeaderTest() {
		List<String> headerlist=accpage.getAccPageHeader();
		Assert.assertEquals(headerlist, AppConstants.ACC_PAGE_HEADER_LIST,AppError.List_Not_Same);
	}
	@DataProvider
	public Object[][] GetSearchData() {
		return new Object[][] {
			{"macbook",3},
			{"imac",1},
			{"samsumg",0},
			{"Airtel",0}
			
			
		};
	}
	
	@Test(dataProvider ="GetSearchData")
	
	public void searchTest(String serachkay,int resultcnt) {
		searchResultPage=accpage.doSearch(serachkay);
		Assert.assertEquals(searchResultPage.getseachResultCount(),resultcnt,AppError.RESULT_CNT_MISSMATCH);
		
	}

}
