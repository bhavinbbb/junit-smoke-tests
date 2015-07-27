package com.datadriven;

import static org.junit.Assert.fail;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.Base.BaseTest;
import com.Base.LocatorData;
import com.Base.LoginData;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class)
public class LoginTest extends BaseTest{


  private StringBuffer verificationErrors = new StringBuffer();
  private String communityName;
  private static List messages = new ArrayList();
  private static List failed = 	new ArrayList();
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
  public static final String time = dateFormat.format(now);
  public static final String dirpath="Screenshots/"+time+"/";
  private String status="Begining";


  @Before
  public void setUpMethods() throws Exception {
    setUp();
  }

  @Parameters
  public static Iterable<? extends Object> paraCommunityName() {
	  ArrayList<String> listCommunities = null;
      String communities=System.getProperty("communities");
	  //String communities="";
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
  public void loginCheckTest() throws Exception {
      try {
		  status=communityName+"_CommunityLoad";
		  System.out.println("Inside Test:" + communityName);
		  String[][] data = getTableArray("src/test/Resources/Data/LocatorData.xls", "LocatorData.xls", "Production");
		  String[][] salesforcedata=getTableArray("src/test/Resources/Data/SalesForceData.xls","SalesForceData.xls","Production");
		  LocatorData locatorData = getSelectedRowForLocator(communityName, data);
		  LoginData loginData= getSelectedRowForLogin(communityName,salesforcedata);

		  System.out.println("CommunityName:" + locatorData.getCommunityName());
		  System.out.println("CommunityURL:" + locatorData.getCommunityURL());
		  System.out.println("LOGIN LINK LOCATOR:" + locatorData.getLoginLinkLocator());

		  driver.get(locatorData.getCommunityURL());
		  screenshotSubmit();
		  Assert.assertTrue("Community "+communityName+"  Page is Not Loaded",driver.findElement(By.xpath(locatorData.getSiteVerifier())).isDisplayed());
		  status=communityName+"_On_SignIn_Page";
		  new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath(locatorData.getLoginLinkLocator())));
		  driver.findElement(By.xpath(locatorData.getLoginLinkLocator())).click();
		  screenshotSubmit();


		  if(!locatorData.getLoginIframeLocator().isEmpty()){
			  driver.switchTo().frame(driver.findElement(By.xpath(locatorData.getLoginIframeLocator())));
		  }

		  Assert.assertTrue("Community "+communityName+"  Login Page is Not Loaded",driver.findElement(By.xpath(locatorData.getLoginLocator())).isDisplayed());
		  status=communityName+"_After_Signing_Into_Community";
		  new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath(locatorData.getLoginLocator())));
		  driver.findElement(By.xpath(locatorData.getLoginLocator())).sendKeys(loginData.getCommunityLogin());
		  driver.findElement(By.xpath(locatorData.getPasswordLocator())).sendKeys(loginData.getCommunityPassword());
		  driver.findElement(By.xpath(locatorData.getButtonLocator())).click();
		  if(!locatorData.getLoginIframeLocator().isEmpty()){
			  driver.switchTo().defaultContent();
		  }
		  new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath(locatorData.getAfterLoginLocator())));
		  screenshotSubmit();
		  Assert.assertTrue("Community "+communityName+"  After Login Page is Not Loaded",driver.findElement(By.xpath(locatorData.getAfterLoginLocator())).isDisplayed());
		  status=communityName+"_After_SignOut";
		  if(!locatorData.getSignoutHover().isEmpty()){
			  driver.findElement(By.xpath(locatorData.getSignoutHover())).click();
			  new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath(locatorData.getSignoutLocator())));
			  driver.findElement(By.xpath(locatorData.getSignoutLocator())).click();
		  }
		  else {
			  driver.findElement(By.xpath(locatorData.getSignoutLocator())).click();
		  }
		  screenshotSubmit();
		  new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath(locatorData.getAfterSignoutLocator())));
		  Assert.assertTrue("Community "+communityName+"  After Signout Page is Not Loaded",driver.findElement(By.xpath(locatorData.getAfterSignoutLocator())).isDisplayed());
		  messages.add("Community " + communityName + " Passing Successfully");
	  }
	  catch(Exception e){
		  e.printStackTrace();
		  failed.add("Community " + communityName + " Failed."+" Possible Error "+status);
		  status=communityName+"_At_Error_Page";
		  takeScreenshot();
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
		for (int successmessagecount=0;successmessagecount < messages.size();successmessagecount++)
		{
			System.out.println(messages.get(successmessagecount));
		}
		System.out.println();
		for (int failuremsgcount=0;failuremsgcount < failed.size();failuremsgcount++)
		{
			System.out.println(failed.get(failuremsgcount));
		}
		String current = new java.io.File( "." ).getCanonicalPath();
		System.out.println("\nScreenShots are located at:"+current+"/"+dirpath);
		System.out.println("\n################################################\n");


	}

	public void takeScreenshot() throws IOException{
		screenCapture(dirpath+status);

	}

	public void screenshotSubmit() throws Exception{
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Thread.sleep(1000);
		takeScreenshot();
	}


}
