package com.qa.project.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.project.factory.DriverFactory;
import com.qa.project.pages.AccountsPage;
import com.qa.project.pages.LoginPage;
import com.qa.project.pages.ProductInfoPage;
import com.qa.project.pages.RegisterPage;
import com.qa.project.pages.SearchPage;



/**
 * This class set up common initialization and cleanup in tests.
 * @author Miral
 *
 */
public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	
	//as this class extends by all other page classes created page references 
	
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchPage  searchPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	
	protected SoftAssert softAssert;
	
	@BeforeTest
	public void setup() {
		df =  new DriverFactory();
		prop = df.initProp();
        driver = df.initDriver(prop);
        loginPage = new LoginPage(driver);
        
        softAssert = new SoftAssert();
   	}
	
	@AfterTest
	public void teardown() {
		driver.quit();
	}
	

}
