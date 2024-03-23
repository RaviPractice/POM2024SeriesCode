package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchPage;
import com.qa.opencart.pages.ViewCartPopUpPage;

public class BaseTest {
	
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginpage;
	protected AccountsPage accountspage;
	protected SearchPage searchpage;
	protected ProductInfoPage prodinfopage; 
	protected SoftAssert softassert;
	protected RegisterPage registerpage;   
	protected ViewCartPopUpPage viewCartPopUpPage;
	
	
	@BeforeTest
	public void setUp() {
		df = new DriverFactory();
		prop = df.initProp();
		driver = df.initDriver(prop);
		loginpage = new LoginPage(driver); 
		softassert = new SoftAssert();
		
	}      
	
	@AfterTest
	public void tearDown() {
		driver.quit();
		
	}      
  
}  
  