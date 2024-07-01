package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

public class ProdcutInfoPage {
	
	private WebDriver driver;
	private ElementUtil util;
	private By productheader=By.cssSelector("div#content h1");
	private By prodcutimgcnt=By.cssSelector("#content a.thumbnail");
	private By prodcutMetaData=By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[1]/li");
	private By prodcutPriceData=By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[2]/li");
	
	private Map<String,String> productMap=new HashMap<>();
	public ProdcutInfoPage(WebDriver driver) {
		this.driver=driver;
		util=new ElementUtil(driver);
	}

	public String getProdcutHeader() {
		String producttxt = util.doGetText(productheader);
		System.out.println("product Header"+ producttxt);
		return producttxt;
	}
	
	public int getImgesCount() {
	int imagecnt=util.waitForVisibilityOfElemenetsLocated(prodcutimgcnt, TimeUtil.DEFAULT_TIME).size();
	System.out.println(imagecnt);
	return imagecnt;
	}
	
	public Map<String,String> getProductInfoMap() {
		productMap=new HashMap<String,String>();
		productMap.put("productname", getProdcutHeader());
		productMap.put("productImgCnt", String.valueOf(getImgesCount()));
		getProductMetaData();
		getProdcutPriceData();
		return productMap;
	}
	private void getProductMetaData() {
		List<WebElement> MetaList = util.getWebElements(prodcutMetaData);
		for(WebElement e:MetaList) {
			String metaData=e.getText();
			String[] meta=metaData.split(":");
			String metaKey=meta[0].trim();
			String metavalue=meta[1].trim();
			productMap.put(metaKey, metavalue);
		}
		
	}
	//$602.00
	//Ex Tax: $500.00
	private void getProdcutPriceData() {
		List<WebElement> priceList = util.getWebElements(prodcutPriceData);
		String Procutprice=priceList.get(0).getText();
		String extprice=priceList.get(1).getText().split(":")[1].trim();
		productMap.put("ProdcutPrice", Procutprice);
		productMap.put("externalPrice", extprice);
		System.out.println("ProdcutPrice"+Procutprice);
		System.out.println("externalPrice"+extprice);
	}
}
