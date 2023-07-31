package com.qa.project.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.project.constants.AppConstants;
import com.qa.project.utils.ElementUtil;
/**
 * This Page class is HomePage or next Landing page after Successful login
 * @author Miral
 *
 */
public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// 1. private By locators:
	
	private By logoutLink = By.linkText("Logout");
	private By accheaders =  By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("#search button");
	
	// 2. page constructor
			public AccountsPage(WebDriver driver) {
				this.driver = driver;
				eleUtil = new ElementUtil(driver);
				}
			
	// 3. page actions/methods:
			
			public String getAccountPageTitle() {
				String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT,AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
				System.out.println("Account page title: "+ title);
				return title;
			}
			
			
			public String getAccountPageURL() {
				String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT,AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE);
				System.out.println("Account page URL: "+ url);
				return url;
			}	
			
			
			public boolean isLogoutLinkExist() {
				return eleUtil.waitForElementVisible(logoutLink,AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
				
			}
			
			public boolean isSearchExist() {
				return eleUtil.waitForElementVisible(search,AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
			}
			
			public List<String> getAccountsPageHeadersList() {
				
				List<WebElement> accHeadersList =eleUtil.waitForElementsVisible(accheaders,AppConstants.DEFAULT_MEDIUM_TIME_OUT);
				List<String> accHeadersValList = new ArrayList<String>();
				
				for(WebElement e : accHeadersList) {
					String text = e.getText();
					accHeadersValList.add(text);
				}
				
				return accHeadersValList;
			}
			
			public SearchPage performSearch(String searchKey) {
				if(isSearchExist()) {
					eleUtil.doSendKeys(search, searchKey);
					eleUtil.doClick(searchIcon);
					return new SearchPage(driver);
					
				}else
				{
					System.out.println("Search field is not present on the page...");
					return null;
				}
				
			}
			
}