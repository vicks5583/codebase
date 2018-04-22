package com.baseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;



/**
 * This is user interface class for abstract 
 * @author Waqas Ahmed Siddiqui
 *
 */
public abstract class AbstractClass {
	/**
	 * constructor of this class
	 * @param driver
	 */
	 public AbstractClass(WebDriver driver){
		 PageFactory.initElements(driver, this);
	 }
	 
}
