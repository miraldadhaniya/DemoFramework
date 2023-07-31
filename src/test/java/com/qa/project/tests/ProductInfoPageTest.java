package com.qa.project.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.project.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	
	/**
	 * Pre-condition for product page tests
	 */
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	}
	
	
	@DataProvider
	public Object[][] getProductImagesTestData(){
		return new Object[][] {
			{"Macbook", "MacBook Pro",4},
			{"iMac", "iMac",3},
			{"Apple","Apple Cinema 30\"",6},
			{"Samsung","Samsung SyncMaster 941BW",1},
		};
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imageCount) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imageCount);
		
	}
	
	
	
	@Test
	public void productInfoTest() {
		searchPage = accPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		System.out.println(actProductInfoMap);
		softAssert.assertEquals(actProductInfoMap.get("Brand"),"Apple");     //Usecase of softAssert giving equal opportunity to test multiple items without creating seprate test methods
		softAssert.assertEquals(actProductInfoMap.get("Product Code"),"Product 18");
		softAssert.assertEquals(actProductInfoMap.get("productname"),"MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("productprice"),"$2,000.00");
		
		softAssert.assertAll();
	}
	
	
	
	
	
	@Test
	public void addToCartTest() {
		searchPage = accPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		productInfoPage.enterQuantity(2);
		String actCartMsg = productInfoPage.addproductToCart();
		softAssert.assertTrue(actCartMsg.indexOf("Success")>=0);
		softAssert.assertTrue(actCartMsg.indexOf("MacBook Pro")>=0);
		softAssert.assertEquals(actCartMsg,"Success: You have added MacBook Pro to your shopping cart!");
	}
	
	}

