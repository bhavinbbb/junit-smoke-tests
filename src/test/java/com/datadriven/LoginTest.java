package com.datadriven;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

@RunWith(Parameterized.class)
public class LoginTest extends BaseTest{

  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String communityName;

  //private String testEnv = System.getProperty("exeEnvironment");

  @Before
  public void setUpMethods() throws Exception {
    setUp();
  }

  @Parameters
  public static Iterable<? extends Object> paraCommunityName() {
	String communities="youtube,sephora,xbox,giffgaff";
	  ArrayList<String> listCommunities = null;
	  communities=System.getProperty("communities");
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
	  System.out.println("Inside Test:"+communityName);
	  String [][]data= getTableArray("/Users/bhavin.br/git/junit-smoke-tests/src/test/java/com/Base/LocatorData.xls","LocatorData.xls","Production");
      LocatorData locatorData=getSelectedRow(communityName,data);
	  System.out.println("CommunityName:"+locatorData.getCommunityName());
	  System.out.println("CommunityURL:"+locatorData.getCommunityURL());
	  System.out.println("LOGIN LINK LOCATOR:"+locatorData.getLoginLinkLocator());

  }



  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }


}
