package com.datadriven;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.Base.BaseTest;
import com.Base.LocatorData;
import com.Base.LoginData;

import org.junit.Assert;

@RunWith(Parameterized.class)
public class LoginTest extends BaseTest{

  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String communityName;
  private static List messages = new ArrayList();
  private static List failed = 	new ArrayList();

  //private String testEnv = System.getProperty("exeEnvironment");
  @BeforeClass
  public static void setClass(){
	  makedir();
  }

  @Before
  public void setUpMethods() throws Exception {
    setUp();
  }

  @Parameters
  public static Iterable<? extends Object> paraCommunityName() {
	String communities="youtube,sephora,xbox,giffgaff";
	  ArrayList<String> listCommunities = null;
	  //communities=System.getProperty("communities");
	  communities="sony";
	  listCommunities= new ArrayList(Arrays.asList(communities.split("\\s*,\\s*")));
	  Collection<Object[]> params = new ArrayList<Object[]>();
	  for (String s : listCommunities) {
		  params.add(new Object[] { s });
	  }
	  return params;
  }

 public LoginTest(String communityName){
	 this.communityName=communityName;
 }

  @Test
  public void baseTest() throws Exception {
      try {
		  System.out.println("Inside Test:" + communityName);
		  String[][] data = getTableArray("src/test/Resources/Data/LocatorData.xls", "LocatorData.xls", "Production");
		  String[][] salesforcedata=getTableArray("src/test/Resources/Data/SalesForceData.xls","SalesForceData.xls","Production");
		  LocatorData locatorData = getSelectedRowForLocator(communityName, data);
		  LoginData loginData= getSelectedRowForLogin(communityName,salesforcedata);

		  System.out.println("CommunityName:" + locatorData.getCommunityName());
		  System.out.println("CommunityURL:" + locatorData.getCommunityURL());
		  System.out.println("LOGIN LINK LOCATOR:" + locatorData.getLoginLinkLocator());

		  driver.get(locatorData.getCommunityURL());
		  driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		  Assert.assertTrue("Community " + communityName + "  Page is Not Loaded", driver.findElement(By.className("CommunityPage")).isDisplayed());
		  Assert.assertTrue("Community "+communityName+"  Page is Not Loaded",driver.findElement(By.xpath(locatorData.getSiteVerifier())).isDisplayed());
		  messages.add("Community " + communityName + " Passing Successfully");
	  }
	  catch(Exception e){
		  e.printStackTrace();
		  failed.add("Community " + communityName + " Failed.");
		  throw new Exception();
	  }
  }



  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

	@AfterClass
	public static void last() throws Exception{
		System.out.println("\n############## SUMMARY #########################\n");
		for (int i=0;i < messages.size();i++)
		{
			System.out.println(messages.get(i));
		}
		System.out.println();
		for (int i=0;i < failed.size();i++)
		{
			System.out.println(failed.get(i));
		}
		System.out.println("\n################################################\n");
	}


}
