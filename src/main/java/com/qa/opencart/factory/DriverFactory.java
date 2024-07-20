package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
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
		System.out.println("Browser name:" + browsername);
		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);
		switch (browsername.toLowerCase().trim()) {
		case "chrome":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run the tcs on selenum grid
				intitRemoteDriver("chrome");
			} else {

				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			// driver=new ChromeDriver();

			break;
		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run the tcs on selenum grid
				intitRemoteDriver("firefox");
			} else {
				// driver=new FirefoxDriver();
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				break;
			}
		case "edge":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run the tcs on selenum grid
				intitRemoteDriver("edge");
			} else {
				// driver=new EdgeDriver();
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				break;
			}
		case "safari":
			// driver=new SafariDriver();
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
	/**
	 * Intialize remote driver to run on remote machine
	 * @param browsername
	 */

	private void intitRemoteDriver(String browsername) {
		System.out.println("Running on remote selenium gride:" + browsername);
		try {
			switch (browsername) {
			case "chrome":

				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));

				break;
			case "firefox":

				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));

				break;
			case "edge":

				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));

				break;

			default:
				System.out.println("Please pass right browser ");
				throw new BrowseException(AppError.BROWSER_NOT_FOUND);
							}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		prop = new Properties();
		String envname = System.getProperty("qa");
		try {
			//we are passing envirnment from -Denv where -D reperesnt envirnemnt env is the name we given and "qa" is value
			//same is reading from the System.getProperty
			// mvn clean install -Denv="qa"
			
			
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
