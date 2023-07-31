package com.qa.project.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.project.base.BaseTest;
import com.qa.project.constants.AppConstants;


public class AccountsPageTest extends BaseTest {
	/**
	 * Pre-condition for account page tests
	 */
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	}

	
	@Test
	public void AccountsPageTitleTest() {
		String actualTitle = accPage.getAccountPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
		
	}
	
	@Test
	public void AccountsPageURLTest() {
		String actualURL = accPage.getAccountPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		List<String> actualAccHeadersList = accPage.getAccountsPageHeadersList();
		System.out.println("Acc Page Headers List: " + actualAccHeadersList);
		Assert.assertEquals(actualAccHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccHeadersList = accPage.getAccountsPageHeadersList();
		System.out.println("Actual Acc Page Headers List: " + actualAccHeadersList);
		System.out.println("Expected Acc Page Headers List: " + AppConstants.EXPECTED_ACCOUNT_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccHeadersList, AppConstants.EXPECTED_ACCOUNT_PAGE_HEADERS_LIST);
	}
	
	/**
	 * Test Related to searchPage
	 */
	
	@DataProvider
	public Object[][] getProductData(){
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
		};
	}
	
	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductCount()>0);
	}
	
	@DataProvider
	public Object[][] getProductTestData(){
		return new Object[][] {
			{"Macbook", "MacBook Pro"},
			{"Macbook", "MacBook Air"},
			{"iMac", "iMac"},
			{"Apple","Apple Cinema 30\""},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung", "Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void searchProductTest(String searchKey, String productName) {
		searchPage = accPage.performSearch(searchKey);
		
		if(searchPage.getSearchProductCount()>0) {
			productInfoPage = searchPage.selectProduct(productName);
			String actProductHeader = productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
		}
		
	}
}
