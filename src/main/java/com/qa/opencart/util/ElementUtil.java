package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;

import io.qameta.allure.Step;



public class ElementUtil {

	private WebDriver driver;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	public void nullcheck(String input) {
		if (input == null) {
			throw new ElementException("value is nulll" + input);
		}
	}
@Step("Enter SendKeys with locator:{0} and input:{1}")
	public void doSendKeys(By locator, String input) {
		nullcheck(input);
		GetElement(locator).clear();
		GetElement(locator).sendKeys(input);
	}

@Step("Enter SendKeys with timeout locator:{0} with input :{1} and timout:{2}")
	public void doSendKeys(By locator, String input,int timeOut) {
		nullcheck(input);
		waitElementVisible(locator,timeOut).clear();
		waitElementVisible(locator,timeOut).sendKeys(input);
	}

@Step("Get Element locator:{0}")
	public WebElement GetElement(By locator) {
		try {
			return driver.findElement(locator);
		} catch (NoSuchElementException ex) {
			System.out.println("Element is not present on the page" + locator);
			ex.printStackTrace();
			return null;
		}
	}
@Step("Click Element with locator:{0}")
	public void doClick(By locator) {
		GetElement(locator).click();
	}
	
@Step("Do click with Wait locator:{0} and timeout:{1}")
	public void doClickWithWait(By locater,int timeOut) {
		waitElementVisible(locater,timeOut).click();
	}
	public String doGetText(By locator) {
		return GetElement(locator).getText();
	}
@Step("Get Attribut with locator:{0} and value:{1}")
	public String doGetAttribute(By locator, String Atvalue) {
		return GetElement(locator).getAttribute(Atvalue);
	}
@Step("Verify Display with locator:{0}")
	public  boolean doIsDisplyed(By locator) {
		try {
		boolean flag= getElement(locator).isDisplayed();
		return flag;
		}catch(NoSuchElementException ex) {
			System.out.println("Element with locator"+locator+"Not displayed");
			return false;
		}
	}
@Step("Verify Is ElementDisplayed with locator:{0}")
	public  boolean isElementIsDisplayed(By locator) {
		int elementcount=driver.findElements(locator).size();
		if(elementcount==1) {
			System.out.println("Element is Displayed"+locator);
			return true;
		}else {
			System.out.println("Mutiple or zero element is Displyed"+locator);
			return false;
		}
	}
@Step("Verify Is ElementDiplayed with locator:{0} and timout:{1}")
	public  boolean isElementIsDisplayed(By locator,int exceptedcount) {
		int elementcount=driver.findElements(locator).size();
		if(elementcount==exceptedcount) {
			System.out.println("Element is Displayed"+locator);
			return true;
		}else {
			System.out.println("Mutiple element is Displyed"+locator);
			return false;
		}
	}

@Step("Verify GetElement with locator:{0}")
	public List<WebElement> getWebElements(By locator) {
		return driver.findElements(locator);
	}
@Step("Verify Element Coiunt By locator:{0}")
	public int getElementcount(By locator) {
		return getWebElements(locator).size();
	}
@Step("Verify Get Element Text with locator:{0}")
	public List<String> getElementText(By locator) {
		List<WebElement> list = getWebElements(locator);
		List<String> elelist = new ArrayList();
		for (WebElement e : list) {
			String text = e.getText();
			if (text.length() != 0) {
				elelist.add(text);
			}
		}
		return elelist;
	}

@Step("Verify the AttributList with locator:{0} and name:{1}")
	public  List getAttributeList(By locator, String attrname) {
		List<WebElement> imglist = getWebElements(locator);
		List<String> attlist = new ArrayList();
		for (WebElement e : imglist) {
			String attvalue = e.getAttribute(attrname);
			if (attvalue != null)
				System.out.println(attvalue);
			attlist.add(attvalue);
		}
		return attlist;
	}
	
	/*****************select drop down utility***********************/
	
	public  WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	public  void doselectByIndex(By locator, int id) {
		Select select=new Select(getElement(locator));
		select.selectByIndex(id);
	}
	
	public  void doselectVisibleText(By locator, String text) {
		Select select=new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
	public  void doselectVisibleValue(By locator, String value) {
		Select select=new Select(getElement(locator));
		select.selectByVisibleText(value);
	}
	/******************Actions Util******************/
	public  void handleparentSubMenu(By Parentlocator, By childlocator) throws InterruptedException {
		Actions ac=new Actions(driver);
		ac.moveToElement(getElement(Parentlocator)).perform();
		Thread.sleep(2000);
		getElement(childlocator).click();
		doClick(childlocator);
	}
	/***wait Util***********************/
	
	
	/**
	 * 
	An expectation for checking that an element is present on the DOM of a page. 
	This does notnecessarily mean that the element is visible.
	 * @param locatort
	 * @param timeOut
	 * @return
	 */
		public  WebElement waitForElementPresent(By locatort,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locatort));
		}
		/**
		 * An expectation for checking that an element is present on the DOM of a page
		 * and visible. Visibility means that the element is not only displayed but also
		 * has a height and width that is greater than 0.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		}


		public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchElementException.class)
					.withMessage("===element is not found===");

			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		}

		/**
		 * An expectation for checking that there is at least one element present on a
		 * web page.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForPresenceOfElemenetsLocated(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}

		/**
		 * An expectation for checking that all elements present on the web page that
		 * match the locator are visible. Visibility means that the elements are not
		 * only displayed but also have a height and width that is greater than 0.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForVisibilityOfElemenetsLocated(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			} catch (Exception e) {
				return List.of();//return empty arraylist
			}
		}
		
		/**
		 * 
		 * 

	An expectation for checking that an element is present on the DOM of a page and visible.
	Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.

		 * @param locatort
		 * @param timeOut
		 * @return
		 */
		
		public  WebElement waitElementVisible(By locatort,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locatort));
		}
		/**
		 * An expectation for checking an element is visible and enabled such that you can click it.
		 * @param locator
		 * @param timeOut
		 */
		public void clickWhenReady(By locator,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
		/**
		 * An expectation for checking that the title contains a case-sensitive substring
		 * @param titleFraction
		 * @param timeOut
		 * @return
		 */
		public  String waitForTitleContains(String titleFraction,int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
			}catch(TimeoutException ex) {
				System.out.println("Title not found");
				return driver.getTitle();
			}
			return driver.getTitle();
		}
		
		public  String waitForTitleToBe(String title,int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
			}catch(TimeoutException ex) {
				System.out.println("Title not found");
				return driver.getTitle();
			}
			return driver.getTitle();
		}
		
		public  String waitForURLContains(String URLFraction,int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if (wait.until(ExpectedConditions.urlContains(URLFraction))) {
				return driver.getCurrentUrl();
			}
			}catch(TimeoutException ex) {
				System.out.println("URL not found");
				return driver.getCurrentUrl();
			}
			return driver.getCurrentUrl();
		}
		
		public  String waitForURLToBe(String URL,int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if (wait.until(ExpectedConditions.urlToBe(URL))) {
				return driver.getCurrentUrl();
			}
			}catch(TimeoutException ex) {
				System.out.println("URL not found");
				return driver.getCurrentUrl();
			}
			return driver.getCurrentUrl();
		}

}
