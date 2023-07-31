package com.qa.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.project.constants.AppConstants;
import com.qa.project.utils.ElementUtil;

import io.qameta.allure.Step;

/**
 * This Page class is Related to login page contains login page action methods
 * @author Miral
 *
 */

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. private By locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	// 2. page constructor
		public LoginPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementUtil(driver);
			
		}
		
	// 3. page actions/methods:
		@Step(".....getting the login page title.........")
		public String getLoginPageTitle() {
			String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE_VALUE);
			System.out.println("Login page title: "+ title);
			return title;
		}
		
		@Step("...........getting the login page url..........")
		public String getLoginPageURL() {
			String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT,AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
			System.out.println("Login page URL: "+ url);
			return url;
		}
		@Step("...........getting the forgot pwd link..........")
		public boolean isForgotPwdLinkExist() {
			return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
		}
		
		/**
		 * This method used to perform login action
		 * @param username
		 * @param Password
		 * @return next landing page(AccountsPage) object
		 */
		@Step("login with the username : {0} and password: {1}")
		public AccountsPage doLogin(String username, String Password) {
			System.out.println("App Credential are: " + username + ":" + Password);
			eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(username);
			eleUtil.doSendKeys(password, Password);
			eleUtil.doClick(loginBtn);
			return new AccountsPage(driver);
			
			
		}
		
		/**
		 * this method is responsible for navigate to register page
		 * @return register page class object
		 */
		@Step("navigating to registration page")
		public RegisterPage navigateToRegisterPage() {
			eleUtil.doClick(registerLink);
			return new RegisterPage(driver);
		}
		
		
		
		
		
		
		
		
		

}
