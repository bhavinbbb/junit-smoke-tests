package com.datadriven;

import static org.junit.Assert.fail;

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

public class LoginTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String testEnv = System.getProperty("exeEnvironment");

  @Before
  public void setUp() throws Exception {
	  String browser = System.getProperty("browserType");
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
    baseUrl = testEnv;
    driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
  }

  @Test
  public void testLogin() throws Exception {
	driver.get(baseUrl);
    driver.findElement(By.id("loginPageV2")).click();
    driver.findElement(By.name("login")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("arfarf");
    
    driver.findElement(By.xpath("//div[contains(@class,'ui-dialog')]//input[contains(@class,'lia-button-Submit-action')]")).click();
    
    driver.findElement(By.id("viewUserProfile")).click();
    driver.findElement(By.id("logoutPage")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
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

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
