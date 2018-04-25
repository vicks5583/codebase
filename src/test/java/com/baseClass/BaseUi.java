package com.baseClass;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.util.StringMatcher;
import com.util.YamlReader;

import junit.framework.Assert;


public class BaseUi extends AbstractClass {

	public WebDriver driver;
	private int waitTime = 120000;
	protected static String setting;
	
	static String XLData[][];
	public static int XLRows;
	public static int XLCols;
	public static int count;

	public WebElement waitForElementToload(WebElement element) {
		String str = YamlReader.getData("Timeout");
		WebDriverWait wait = new WebDriverWait(driver, 80);
		return wait.until(ExpectedConditions.visibilityOf(element));

	}

	public BaseUi(WebDriver driver) {
		super(driver);
		this.driver = driver;
		setting = System.getProperty("server");
		if (setting == null || setting == "")
			setting = YamlReader.getYamlValue("CurrentServer");

	}

	public void launchApp() {
		driver.get(YamlReader.getYamlValue("appUrl"));
		Assert.assertEquals(YamlReader.getYamlValue("appUrl"), getCurrentUrl());
	}

	public void launchSpecificUrl(String url) {
		driver.get(url);
	}

	protected String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public boolean _clickOnUnexpectedPopUp() {
		waitTillTime(500);
		try {
			driver.switchTo().alert().dismiss();
		} catch (Exception e) {
			System.out.println("No Unexpected Pop-Up");
		}
		return true;
	}

	public boolean _clickOnExpectedPopUp() {
		waitTillTime(500);
		try {
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			System.out.println("No Expected Pop-Up");
		}
		return true;
	}

	public void changeWindow(int i) {
		Set<String> windows = driver.getWindowHandles();
		String wins[] = windows.toArray(new String[windows.size()]);
		System.out.println("windows count is " + wins.length);
		driver.switchTo().window(wins[i]);
	}

	protected void changeWindowForMindtap(int i) {

		Set<String> windows = driver.getWindowHandles();
		if (i > 0) {
			for (int j = 0; j < 19; j++) {
				waitTillTime(2000);
				System.out.println("Windows: " + windows.size());
				if (windows.size() >= 2) {
					try {
						Thread.sleep(5000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				}
				windows = driver.getWindowHandles();
			}
		}
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[i]);

		System.out.println("Title: "
				+ driver.switchTo().window(wins[i]).getTitle());
	}

	public boolean verifyPageTitle(String pageTitle) {
		if (!driver.getTitle().equals(pageTitle)) {
			try {
				System.out.println("Page Title : " + driver.getTitle());
				StringMatcher.match(driver.getTitle(), pageTitle, false);
			} catch (TimeoutException e) {
				throw new AssertionError("expected page title to contain : "
						+ pageTitle + " , found : " + driver.getTitle());
			}
		} else {
			System.out.println("Page Title : " + driver.getTitle());
		}
		return true;
	}

	public boolean verifyPageURLContains(String urlSnippet) {
		if (!driver.getCurrentUrl().contains(urlSnippet)) {
			try {
				System.out.println("Page Title : " + driver.getCurrentUrl());
				StringMatcher.match(driver.getCurrentUrl(), urlSnippet, false);
			} catch (TimeoutException e) {
				throw new AssertionError("expected page title to contain : "
						+ urlSnippet + " , found : " + driver.getTitle());
			}
		} else {
			System.out.println("Page url : " + driver.getCurrentUrl());
		}
		return true;
	}

	protected boolean isElementDisplayed(WebElement elementName) {
		waitForElementToVisible(elementName);
		boolean result = elementName.isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "with text ' is not displayed.");
		System.out.println("Assertion Passed: element " + elementName
				+ " with text is displayed.");

		return result;
	}
	
	/*protected boolean isElementAvailableOrNot(WebElement elementName) {
		if (!driver.findElements(By.linkText(elementName)).isEmpty()) {
			System.out.println(elementName + "is available");
		}
			if (!driver.findElements(By.className(elementName)).isEmpty()) {
				System.out.println(elementName + "is available");
			}
				if (!driver.findElements(By.id(elementName)).isEmpty()) {
					System.out.println(elementName + "is available");
				}
					if (!driver.findElements(By.name(elementName)).isEmpty()) {
						System.out.println(elementName + "is available");
					}
						if (!driver.findElements(By.partialLinkText(elementName)).isEmpty()) {
							System.out.println(elementName + "is available");
						}
							if (!driver.findElements(By.tagName(elementName)).isEmpty()) {
								System.out.println(elementName + "is available");
							}
								if (!driver.findElements(By.xpath(elementName)).isEmpty()) {
									System.out.println(elementName + "is available");
			
		} else {
			System.out.println(elementName + "not available");
		}
								
	}*/

	protected boolean isElementDisplayedAvailableOrNot(WebElement elementName) { // this
																					// function
																					// was
																					// defined
																					// to
																					// use
																					// for
																					// element
																					// that
																					// are
																					// not
																					// visible
																					// on
																					// page.
		waitForElementToVisible(elementName);
		boolean result = elementName.isDisplayed();
		if (result == true) {
			assertTrue(result, "Assertion Failed: element '" + elementName
					+ "with text ' is not displayed.");
			System.out.println("Assertion Passed: element " + elementName
					+ " with text is displayed.");

		} else {

			System.out.println(elementName
					+ " element not available in the given bill");

		}

		return result;
	}

	public void sendKeysUsingActions(WebElement e, String str) {
		Actions act = new Actions(driver);
		act.sendKeys(e, str).build().perform();
	}

	public void handleAlert() {
		try {
			// waitTillTime(2000);
			switchToAlert().accept();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logMessage(String info) {
		System.out.println(info);
		Reporter.log(info);
	}

	private Alert switchToAlert() {
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElements(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void handleSSLCertificateConditionForInstructor() {
		if (driver.getTitle().contains("Certificate Error"))
			driver.get("javascript:document.getElementById('overridelink').click();");
		// waitTillTime(10000);
		if (driver.getTitle().contains("Certificate Error"))
			driver.get("javascript:document.getElementById('overridelink').click();");
	}

	public void handleSSLCertificateConditionForStudent() {
		if (driver.getTitle().contains("Certificate Error"))
			driver.get("javascript:document.getElementById('overridelink').click();");
		// waitTillTime(10000);
		if (driver.getTitle().contains("Certificate Error"))
			driver.get("javascript:document.getElementById('overridelink').click();");
	}

	public void waitTillTime(int wTime) {
		try {
			Thread.sleep(wTime);
		} catch (Exception ex) {
		}
	}

	protected boolean isElementNotDisplayed(WebElement elementName) {
		// waitForPageToLoadCompletely();
		try {
			elementName.isDisplayed();
			Reporter.log(elementName + " is displayed");
			return elementName.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean waitForElementToAppear(WebElement element) {
		// waitForForeSeeToAppear();
		long endTime = System.currentTimeMillis() + waitTime;

		while (System.currentTimeMillis() < endTime) {

			try {
				if (element.isDisplayed()) {
					return true;
				}
			} catch (Exception e) {
				// waitTillTime(1000);
			}
		}// end while
		return false;
	}

	public void hardwait(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean waitForElementTakeButtonToAppear(WebElement element) {
		long endTime = System.currentTimeMillis() + waitTime;
		int elementDisplayed = 0;
		while (System.currentTimeMillis() < endTime) {
			printOutputOnConsole("elementDisplayed : " + elementDisplayed);
			System.out.println("Check For Elemement Enabled : "
					+ element.isEnabled());
			// waitTillTime(3000);
			if (element.isEnabled()) {
				return true;
			}
		}// end while
		return false;
	}

	public void printOutputOnConsole(String str) {
		System.out.println("\n");
		System.out.println(str);
		System.out.println("\n");
	}

	/**
	 * Methods
	 */

	public boolean waitForSpinnerToDisappear() {
		boolean flag = false;
		try {
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			WebElement spinnerHead = driver.findElement(By
					.xpath("//li[contains(@class,'action-button')]"));
			if (spinnerHead.getAttribute("class").contains("disabled")) {
				do {
					waitTillTime(2000);
				} while (driver
						.findElement(
								By.xpath("//li[contains(@class,'action-button')]/div"))
						.getAttribute("class").contains("spinner"));
				flag = true;
			}
			System.out.println("Spinner was visible.");
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			return flag;
		} catch (Exception e) {
			// System.out.println("ForeSee Overlay is not visible.");
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			return flag;
		}
	}

	public void performClick(WebElement element) {
		waitForElementToload(element);
		element.click();
	}

	public void performMouseOver(WebElement el) {
		Actions act = new Actions(driver);
		act.moveToElement(el).build().perform();

	}

	public void clickUsingJS(WebElement element) {
		waitTillTime(300);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

	}// end of method

	public void performClickWithScrollUp(WebElement element) {
		waitTillTime(300);
		waitForElementToAppear(element);
		element.click();
	}

	public void scrollUp(WebElement element) {
		((JavascriptExecutor) driver)
				.executeScript("jse.executeScript('scroll(0,250)')");
	}

	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}

	public void scrollDownForMobile(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}

	
	public void performClear(WebElement element) {
		element.clear();
	}

	public void waitForElementToVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void performSendKey(WebElement element, String typeText) {
		waitForElementToload(element);
		element.sendKeys(typeText);
	}

	public void performButtonSendKey(WebElement element, Keys key) {
		waitForElementToload(element);
		element.sendKeys(key);
	}

	public void performClickFromAction(WebElement element) {
		Actions builder = new Actions(driver);
		Action click = builder.doubleClick(element).build();
		click.perform();
	}

	public String performGetText(WebElement element) {
		waitForElementToAppear(element);
		return element.getText();
	}

	public void waitForElementToLoad(String loc, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		if (loc.equalsIgnoreCase("partialLinkText")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.partialLinkText(locator)));
		}
		if (loc.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath(locator)));
		}
		if (loc.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.id(locator)));
		}
		if (loc.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.name(locator)));
		}
		if (loc.equalsIgnoreCase("cssSelector")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.cssSelector(locator)));
		}
		if (loc.equalsIgnoreCase("linkText")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.linkText(locator)));
		}
		if (loc.equalsIgnoreCase("className")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.className(locator)));
		}
		if (loc.equalsIgnoreCase("value")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.className(locator)));
		}
		if (loc.equalsIgnoreCase("href")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.className(locator)));
		}
		
	}

	protected void switchToFrame(WebElement element) {

		waitForElementToVisible(element);
		driver.switchTo().frame(element);
		logMessage("switch to frame");
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected void selectProvidedTextFromDropDown(WebElement el, String text) {
		waitForElementToVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.selectByVisibleText(text);
		logMessage("The selection made in the drop down is " + text);
	}

	protected void selectProvidedIndexFromDropDown(WebElement el, int index) {
		waitForElementToVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.selectByIndex(index);
		// logMessage("The selection made in the drop down is " + text);
	}

	protected void selectFirstOptionFromDropDown(WebElement el) {
		waitForElementToVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.getFirstSelectedOption();
		logMessage("First dropdown value selected ");
	}

	public void launchMTReader(WebElement elementPath) {
		String winHandleBefore = driver.getWindowHandle();
		performClick(elementPath);
		do {
			waitTillTime(1);
		} while (driver.getWindowHandles().size() == 1);

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			System.out.println(winHandle);
			int counter = 0;
			do {
				waitTillTime(1000);
				counter++;
				if (counter == 5) {
					break;
				}
			} while (!driver.switchTo().window(winHandle).getTitle()
					.equals("MindTap - Cengage Learning"));
			if (driver.switchTo().window(winHandle).getTitle()
					.equals("MindTap - Cengage Learning")) {
				System.out.println(driver.getTitle());
				driver.close();
			} else {
				System.out.println("Child Window not available");
			}
		}

		driver.switchTo().window(winHandleBefore);
		System.out.println(driver.getTitle());
	}

	public void Logout() {

		driver.findElement(By.className("subnav-toggle")).click();
		driver.findElement(
				By.className("politico-auth auth-log-out auth-logged-in"))
				.click();

	}

	public void WindowHandling() {
		// login with the window handle feature.

		// Store you current window handle in a String variable.
		String parentWindow = driver.getWindowHandle();

		// Click on the the page element which causes a new window to be
		// opened,suppose a link.
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.linkText("Log In")).click();
		// driver.findElement(By.xpath("//html/body/div/div[1]/div/nav[1]/ul/li[3]/a")).click();
		// driver.findElement(By.xpath("//html/body/div/div[1]/div/nav[1]/ul/li[3]/div/ul/li/a[1]")).click();

		hardwait(15);

		// Get the window handle of the new browser window opened.
		String childWindow = (String) driver.getWindowHandles().toArray()[1];

		// Switch to newly opened window.
		driver.switchTo().window(childWindow);

		// providing details and submitting
		driver.findElement(By.id("email"))
				.sendKeys("osama.anwar@cubixlabs.com");
		driver.findElement(By.id("password")).sendKeys("Welcome123!");
		driver.findElement(
				By.xpath("//html/body/div[2]/section/div[2]/form/fieldset/ul/li[4]/button"))
				.click();

		hardwait(15);

		// Close the new window, if that window no more required
		// driver.close();

		// Switch back to main window.
		driver.switchTo().window(parentWindow);
		hardwait(15);
	}
	
	public void handlingWindowsChild(){
		String childWindow = (String) driver.getWindowHandles().toArray()[1];

		// Switch to newly opened window.
		driver.switchTo().window(childWindow);	
		
	}
	
	public void handlingWindowsParent(){
		String parentWindow = driver.getWindowHandle();
		
		// Switch back to main window.
				driver.switchTo().window(parentWindow);
				hardwait(15);
	}
	public void WindowHandling1() {
		// login with the window handle feature.

		// Store you current window handle in a String variable.
		String parentWindow = driver.getWindowHandle();

		// Click on the the page element which causes a new window to be
		// opened,suppose a link.
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.linkText("Log In")).click();
		// driver.findElement(By.xpath("//html/body/div/div[1]/div/nav[1]/ul/li[3]/a")).click();
		// driver.findElement(By.xpath("//html/body/div/div[1]/div/nav[1]/ul/li[3]/div/ul/li/a[1]")).click();

		hardwait(15);

		// Get the window handle of the new browser window opened.
		String childWindow = (String) driver.getWindowHandles().toArray()[1];

		// Switch to newly opened window.
		driver.switchTo().window(childWindow);

		// providing details and submitting
		driver.findElement(By.id("email")).sendKeys(
				"ambreen.sahar@cubixlabs.com");
		driver.findElement(By.id("password")).sendKeys("Welcome123!");
		driver.findElement(
				By.xpath("//html/body/div[2]/section/div[2]/form/fieldset/ul/li[4]/button"))
				.click();

		hardwait(15);

		// Close the new window, if that window no more required
		// driver.close();

		// Switch back to main window.
		driver.switchTo().window(parentWindow);
		hardwait(15);

	}

	public String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); // 2016/11/16
		return dateFormat.format(date);

	}

	// Comparing Dates if dates are passed in string

	public boolean compareDate(String Date1, String Date2) throws Exception {

		// 06/02/2017 - 05:17 date formate applied
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy - hh:mm a");

		Date date1 = df.parse(Date1);
		Date date2 = df.parse(Date2);
		if (date1.after(date2)) {
			return true;
		}

		else {
			return false;
		}

	}
	
	public void handleNewTab() {
		// Get the window handle of the new browser window opened.
			String childWindow = (String) driver.getWindowHandles().toArray()[1];

			// Switch to newly opened window.
			driver.switchTo().window(childWindow);
	}
	
public static void xlwrite(String XLWriteOn, String[][] xldata) throws Exception{
	
		
		File outFile = new File(XLWriteOn);
		XSSFWorkbook myworkbook = new XSSFWorkbook();
		XSSFSheet osheet = myworkbook.createSheet("Results");
		
		

		//iterating r number of rows
		for (int r=0;r < XLRows; r++ )
		{
			XSSFRow row = osheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < XLCols; c++ )
			{
				XSSFCell cell = row.createCell(c);
	
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(xldata[r][c]);
				System.out.println("Writting row "+(r+1)+" and column "+(c+1));
				//osheet.autoSizeColumn(c);
				
				
			} 
		}
		
		FileOutputStream fOut = new FileOutputStream(outFile);

		//Set column width
		for(int i=0;i<XLCols;i++) {
		osheet.setColumnWidth(i, 5000);	
		}
		
		//write this workbook to an Outputstream.
		myworkbook.write(fOut);
		fOut.flush();
		fOut.close();
		
	}
	
		
}
