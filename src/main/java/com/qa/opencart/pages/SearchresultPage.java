package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

public class SearchresultPage {
private WebDriver driver;
private ElementUtil util;
private By SerachResull=By.cssSelector("div.product-thumb");

	public SearchresultPage(WebDriver driver) {
		this.driver=driver;
		util=new ElementUtil(driver);
	}
	public int  getseachResultCount() {
		List<WebElement> resultcnt =util.waitForVisibilityOfElemenetsLocated(SerachResull, TimeUtil.DEFAULT_TIME);
		System.out.println("Product search result count"+resultcnt.size());
		return resultcnt.size();
	}
	public ProdcutInfoPage selectProduct(String prodcutname) {
		System.out.println("prodcutname  "+prodcutname);
		util.doClickWithWait(By.linkText(prodcutname), TimeUtil.DEFAULT_TIME);
		return new ProdcutInfoPage(driver);
		
	}
}
