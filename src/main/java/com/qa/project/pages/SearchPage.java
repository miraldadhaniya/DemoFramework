package com.qa.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.project.constants.AppConstants;
import com.qa.project.utils.ElementUtil;


/**
 * This Page class is next landing page after successful search
 * @author Miral
 *
 */
public class SearchPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By searchProductResult = By.cssSelector("div#content div.product-layout");

	//Page Constructor
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//Page Methods
	
	public int getSearchProductCount() {
		int productCount= eleUtil.waitForElementsVisible(searchProductResult, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Product count:::" + productCount);
		return productCount;
	}
	
	/**
	 * This method used to perform select product
	 * @param productName
	 * @return next landing page(ProductInfoPage) object
	 */
	
	public ProductInfoPage selectProduct(String productName) {
		By productLocator = By.linkText(productName);
		eleUtil.waitForElementVisible(productLocator, AppConstants.DEFAULT_MEDIUM_TIME_OUT).click();
		return new ProductInfoPage(driver);
	

}
}
