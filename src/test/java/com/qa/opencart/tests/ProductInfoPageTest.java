package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.util.CSVUtil;
import com.qa.opencart.util.ExcelUtil;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void  ProductInfoPageSetup() {
		accpage=loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
			{"canon", "Canon EOS 5D"}

		};
	}
	
	@Test(dataProvider = "getProductData")

	public void prodcutHeaderTest(String prouctname,String Headername) {
		searchResultPage=accpage.doSearch(prouctname);
		productInfoPage=searchResultPage.selectProduct(Headername);
		
		Assert.assertEquals(productInfoPage.getProdcutHeader(), Headername, AppError.HEADER_NOT_FOUND);
	}
	

	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7},
			{"canon", "Canon EOS 5D", 3}

		};
	}
	@DataProvider
	public Object[][] getProductImgeSheetData(){
		
		return ExcelUtil.getTestData(AppConstants.PRODUCT_IMAGES_SHEET_NAME);
	}
	
	
	
	
	@Test(dataProvider = "getProductImgeSheetData")
	
	public void prodcutImgaeTest(String productname,String Productheader, String imgcnt) {
		searchResultPage=accpage.doSearch(productname);
		productInfoPage=searchResultPage.selectProduct(Productheader);
		
		Assert.assertEquals(productInfoPage.getImgesCount(), Integer.parseInt(imgcnt), AppError.IMAGES_NOT_MATCH);
	}
	/*
	 * Brand: Apple
Product Code: Product 16
Reward Points: 600
Availability: In Stock
$602.00
Ex Tax: $500.00
	 */
	@Test
	public void productInfoTest() {
		searchResultPage=accpage.doSearch("macbook");
		productInfoPage=searchResultPage.selectProduct("MacBook Pro");
	Map<String,String> productinftomap=	productInfoPage.getProductInfoMap();
	System.out.println("=======Product Info==========");
	System.out.println(productinftomap);
	softAssert.assertEquals(productinftomap.get("productname"), "MacBook Pro");
	softAssert.assertEquals(productinftomap.get("Brand"), "Apple");
	softAssert.assertEquals(productinftomap.get("Product Code"), "Product 18");
	softAssert.assertEquals(productinftomap.get("Reward Points"), "800");
	softAssert.assertEquals(productinftomap.get("Availability"), "In Stock");
	softAssert.assertEquals(productinftomap.get("ProdcutPrice"), "$2,000.00");
	softAssert.assertEquals(productinftomap.get("externalPrice"), "$2,000.00");
	softAssert.assertAll();//Failure Infomation
	}
}
