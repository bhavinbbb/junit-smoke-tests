package com.Base;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import jxl.*;
import java.io.File;

/**
 * Created by bhavin.br on 7/13/15.
 */
public class BaseTest {
	protected WebDriver driver;
	public String browser="firefox";
	public static Date now = new Date();


	public BaseTest(){
		browser = System.getProperty("browserType");
	}

	public void setUp() throws Exception {
		// String browser = System.getProperty("browserType");

		if(browser.equals("chrome"))
		{
			driver = new ChromeDriver();
		}
		else if (browser.equals("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}


	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName){
		String[][] tabArray=null;
		try{
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow,startCol, endRow, endCol,ci,cj;
			Cell tableStart=sheet.findCell(tableName);
			startRow=tableStart.getRow();
			startCol=tableStart.getColumn();

			Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);

			endRow=tableEnd.getRow();
			endCol=tableEnd.getColumn();
			System.out.println("startRow="+startRow+", endRow="+endRow+", " +
					"startCol="+startCol+", endCol="+endCol);
			tabArray=new String[endRow-startRow-1][endCol-startCol-1];
			ci=0;

			for (int i=startRow+1;i<endRow;i++,ci++){
				cj=0;
				for (int j=startCol+1;j<endCol;j++,cj++){
					tabArray[ci][cj]=sheet.getCell(j,i).getContents();
				}
			}
		}
		catch (Exception e)    {
			e.printStackTrace();
		}

		return(tabArray);
	}


    public LocatorData getSelectedRowForLocator(String key,String[][]Data){
		LocatorData locatorData= new LocatorData();
		for(int i=0;i<Data.length;i++){
			if(Data[i][0].equals(key)) {
			  locatorData.setCommunityName(Data[i][0]);
			  locatorData.setCommunityURL(Data[i][1]);
			  locatorData.setSiteVerifier(Data[i][2]);
			  locatorData.setLoginLinkLocator(Data[i][3]);
			  locatorData.setLoginLocator(Data[i][4]);
			  locatorData.setPasswordLocator(Data[i][5]);
			  locatorData.setButtonLocator(Data[i][6]);
			  locatorData.setAfterLoginLocator(Data[i][7]);
			  locatorData.setSignoutLocator(Data[i][8]);
			  locatorData.setAfterSignoutLocator(Data[i][9]);
			  locatorData.setSignoutHover(Data[i][10]);
			  locatorData.setLoginIframeLocator(Data[i][11]);
			}
		}
		return locatorData;
	}

	public LoginData getSelectedRowForLogin(String key,String[][]Data){
		LoginData loginData= new LoginData();
		for(int i=0;i<Data.length;i++){
			if(Data[i][0].equals(key)) {
				loginData.setCommunityName(Data[i][0]);
				loginData.setCommunityLogin(Data[i][1]);
				loginData.setCommunityPassword(Data[i][2]);
			}
		}
		return loginData;
	}

	public void screenCapture(String filename) throws IOException {
		driver.manage().window().maximize();
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(filename+".png"));
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}


}
