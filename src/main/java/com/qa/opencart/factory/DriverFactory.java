package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.io.FileHandler;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowseException;
import com.qa.opencart.exceptions.FrameWorkException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {
		// cross browser logic
		String browsername = prop.getProperty("browser");

		highlight = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		switch (browsername.toLowerCase().trim()) {
		case "chrome":
			//driver=new ChromeDriver();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			 //driver=new FirefoxDriver();
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			 //driver=new EdgeDriver();
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			//driver=new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			System.out.println("plz pass the right browser name..." + browsername);
			throw new BrowseException(AppError.BROWSER_NOT_FOUND);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("URL"));
		return getDriver();
		

	}

	/*
	 * Get local thread copy of driver
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		// cross prop
		FileInputStream ip = null;
		try {
			//we are passing envirnment from -Denv where -D reperesnt envirnemnt env is the name we given and "qa" is value
			//same is reading from the System.getProperty
			// mvn clean install -Denv="qa"
			prop = new Properties();
			String envname = System.getProperty("env");
			
			System.out.println("Running test in=>" + envname);
			if (envname == null) {
				System.out.println("Envirnent name is null, hence running it on QA Envirnment");
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_CONST);
			} 
			else {
				switch (envname.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_CONST);
					break;

				case "dev":
					ip = new FileInputStream(AppConstants.CONFIG_Dev_FILE_CONST);
					break;
					
				case "stag":
					ip = new FileInputStream(AppConstants.CONFIG_STAG_FILE_CONST);
					break;
					
				case "Prod":
					ip = new FileInputStream(AppConstants.CONFIG_FILE_CONST);
					break;

				default:
					System.out.println("Please pass right Env name" + envname);
					throw new FrameWorkException("Wrong Env Name");

				}

				
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;

	}
	/*
	 * Take screenshot
	 */
	public static String GetScreenshot(String methodname)
	{
			File sourcefile=((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);//temp location
			String path=System.getProperty("user.dir")+"/screenshot/"+methodname+"_"+System.currentTimeMillis()+".png";
			File desctination=new File(path);
			
			try {
				FileHandler.copy(sourcefile, desctination);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return path;
	}
}
