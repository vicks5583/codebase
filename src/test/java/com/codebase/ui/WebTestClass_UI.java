package com.codebase.ui;

import junit.framework.Assert;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

import com.baseClass.BaseUi;
import com.codebase.WebTestClass;

public class WebTestClass_UI extends BaseUi {
	WebDriver driver;
	

	public WebTestClass_UI(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	@FindBy(css = "[id = 'hplogo']")
	private WebElement googleTitle;
	
	@FindBy(css = "[value = 'Google Search']")
	private WebElement googleSearchButton;
	
	@FindBy(css = "[value = 'I'm Feeling Lucky']")
	private WebElement googleFeelingLuckyButton;
	
	@FindBy(css = "[id = 'lst-ib']")
	private WebElement googleField;
	
	@FindBy(css = "[id = '_fZl']")
	private WebElement lookupButton;
	
	@FindBy(css = "[class = 'r']")
	private WebElement searchResult;
	
	@FindBy(css = "[class = 'Q8LRLc']")
	private WebElement countryName;
	
	@FindBy(linkText = "Advertising")
	private WebElement lnkAdvertising;
	
	
	public void CheckGoogleTitle() {
		Assert.assertEquals("Google", driver.getTitle()); 
		// OR
		// isElementDisplayed(googleTitle);
	}
	
	public void CheckGoogleSearchButton() {
		isElementDisplayed(googleSearchButton);
	}
	
	public void CheckGoogleFeelingLuckyButton() {
		isElementDisplayed(googleSearchButton);
	}
	
	public void CheckGoogleField() {
		isElementDisplayed(googleField);
	}
	
	public void EnterGoogleField() {
		googleField.sendKeys("how to do automation testing using selenium webdriver");
	}
	
	public void ClickLookup() {
		lookupButton.click();
		hardwait(2);
	}
	
	public void CheckSearchResult() throws IOException {
		isElementDisplayed(searchResult);
	}
	
	public void CheckFooterCountryName() {
		isElementDisplayed(countryName);
		String footerCountryName = countryName.getText();
		if (footerCountryName.contentEquals("Pakistan")) {
			System.out.println("Country Name found");
		 
		}else {
			System.out.println("Country Name not found");
			
		}
	}
	
	public void CheckFooterLinkAdvertising() {
		isElementDisplayed(lnkAdvertising);
	}
	

}
