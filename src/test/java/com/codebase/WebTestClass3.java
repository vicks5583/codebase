package com.codebase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.baseClass.BaseUi;
import com.resources.DSLLibDesktop;

public class WebTestClass3 {
	DSLLibDesktop dsl;
	  //public String xPath = "D:/Excels/MyExcel.xls";
	  //public String xPath = "D:/Excels/MyFirstExcel.xlsx";
	  //public String xlPath_Res = "D:/Excels/userStatuses.xlsx";
	  //public static String XData[][];
	  //public String xlData[][];
	  

	  @BeforeClass
	  public void beforeClass() throws Exception {
		  	dsl = new DSLLibDesktop();
			//dsl.launchCubixCo();
		  	dsl.launchGoogle();
		  	//dsl.xlRead(xPath);
		  	
	  }
	  
	  @Test
	  public void Test01_Verify_Google_Title() {
		  dsl.webtestclass.CheckGoogleTitle();
	  }
	  
	  @Test
	  public void Test02_Verify_Google_Search_Button() {
		  dsl.webtestclass.CheckGoogleSearchButton();
	  }
	  
	  @Test
	  public void Test03_Verify_Google_Feeling_Lucky_Button() {
		  dsl.webtestclass.CheckGoogleFeelingLuckyButton();
	  }
	  
	  @Test
	  public void Test04_Verify_Google_Field() {
		  dsl.webtestclass.CheckGoogleField();
	  }
	  
	  @Test
	  public void Test05_Type_In_Google_Field() {
		  dsl.webtestclass.EnterGoogleField();
	  }
	 /* 
	  @Test
	  public void Test06_Click_Lookup() {
		  dsl.cubixclass.ClickLookup();
	  }
	  
	  @Test
	  public void Test07_Verify_Search_results() {
		  dsl.cubixclass.CheckSearchResult();
	  }*/
	  
	  @Test
	  public void Test06_Verify_Country_Name() {
		  dsl.webtestclass.CheckFooterCountryName();
	  }
	  
	  @AfterClass
	  public void afterClass() throws Exception {
		  dsl.closeBrowser();
		  //xlwrite(xlPath_Res, XData);//Writes the results
		  //BaseUi.xlwrite(xlPath_Res, XData);
		  
	  }
}
