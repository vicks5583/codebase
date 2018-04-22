package com.baseClass;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import com.codebase.ui.WebTestClass_UI;
import com.util.YamlReader;

/**
 * This is the base class which provides functionalities to other classes
 * 
 * @author Waqas Ahmed Siddiqui
 * 
 */

public class BaseClass {

	public static String URL1 = "";
	public static WebDriver driver;
	public static String browser = null;
	public static String user = null;
	protected static String setting;
	protected static DesiredCapabilities capabilities;
	public static boolean baseURL = true;
	protected static WebTestClass_UI src;

	public BaseClass() {
		setting = YamlReader.getYamlValue("CurrentServer");
		capabilities = new DesiredCapabilities();
		startBrowser();
	}

	/**
	 * function to start the browser
	 * 
	 * @throws MalformedURLException
	 */

	public static void startBrowser() {
		capabilities.setJavascriptEnabled(true);
		URL1 = YamlReader.getYamlValue(setting + ".BaseURL");
		browser = YamlReader.getYamlValue(setting + ".browser"); // firefox
																	// //chrome
																	// // ie
		if (browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver",
					"ChromeDriverServer/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.firefox.bin",
					"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			driver = new FirefoxDriver();
			/*
			 * try { driver=new RemoteWebDriver(new URL(
			 * "http://10.0.9.60:4444/wd/hub"), DesiredCapabilities.firefox());
			 * } catch (MalformedURLException e) { e.printStackTrace(); }
			 */
		} else if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"ChromeDriverServer/chromedriver.exe");
			driver = new ChromeDriver();
//			 try {
//			 driver = new RemoteWebDriver(new URL("http://10.161.129.122:4444/wd/hub"),DesiredCapabilities.chrome());
//			 } catch (MalformedURLException e) {
//			 // 
//			 e.printStackTrace();
//			 }
		} else if (browser.equals("safari")) {
			driver = new SafariDriver();
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		if (baseURL == true) {
			driver.get(URL1);
		}
		_initObjects();
	}

	/**
	 * function to initialize object
	 */
	private static void _initObjects() {
		src = new WebTestClass_UI(driver);
		
	}

	/**
	 * function to close the browser
	 */
	public static void closeBrowser() {
		driver.quit();
	}

}
