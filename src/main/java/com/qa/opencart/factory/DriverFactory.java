package com.qa.opencart.factory;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;

import com.qa.opencart.exceptions.BrowserExceptions;
import com.qa.opencart.exceptions.FrameworkException;



public class DriverFactory {

	WebDriver driver;
	Properties prop;
	public static String isHighlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");

		System.out.println("browser name is : " + browserName);
		isHighlight= prop.getProperty("highlight");

		switch (browserName.toLowerCase().trim()) {

		case "chrome":

			driver = new ChromeDriver();
			break;

		case "firefox":
			driver = new FirefoxDriver();
			break;

		case "edge":

			driver = new EdgeDriver();
			break;

		case "safari":
			driver = new SafariDriver();
			break;

		default:
			System.out.println(AppError.INVALID_BROWSER_MSG + browserName);
			throw new BrowserExceptions(AppError.INVALID_BROWSER_MSG + browserName);

		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// driver.get("https://www.opencart.com/");

		String websiteurl = prop.getProperty("url");

		driver.get(websiteurl);

		return driver;

	}
	
	/**
	 * this method is returning the driver with threadlocal
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {

		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");

		System.out.println("Running test on env :" + envName);

		try {
			if (envName == null) {
				System.out.println("env is null ..hence running in QA env");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");

			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");

					break;

				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");

					break;

				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");

					break;

				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");

					break;

				default:
					System.out.println("plz pass the right env..." + envName);
					throw new FrameworkException("INVALID ENV NAME");

				}

			}

			prop.load(ip);
		}

		catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return prop;

	}
	
	
	/**
	 * take screenshot 
	 */
	
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);//temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()+ ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
