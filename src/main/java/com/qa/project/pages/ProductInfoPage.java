package com.qa.project.pages;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.project.constants.AppConstants;
import com.qa.project.utils.ElementUtil;



/**
 * This Page class is next landing page after clicking/selecting particular product and navigate to product information page
 * @author Miral
 *
 */


public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	// By Locators:
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[1]/li");
	private By productPriceData = By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMsg = By.cssSelector("div.alert.alert-success");
	
	private Map<String,String> productInfoMap ;
    
	//Constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//Action Methods
	
	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("Product header: " + productHeaderVal);
		return productHeaderVal;
		
	}
	
	public int getProductImagesCount() {
		int imageCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product images count:" + imageCount);
		return imageCount;
		
	}
	
	public void enterQuantity(int qty) {
		System.out.println("Product Quantity: " + qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));
		
	}
	
	
	
	public Map<String,String> getProductInfo() {
		//productInfoMap = new HashMap<String, String>();
		//productInfoMap = new LinkedHashMap<String, String>();
		productInfoMap = new TreeMap<String, String>();
		
		//header:
		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();
		System.out.println(productInfoMap);
		return productInfoMap;
	}
	
	//fetching meta data:
	private void getProductMetaData() {
		//meta data:
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e: metaList) {
			String meta = e.getText();  //Brand:Apple
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
			
		}
	}
		
	// fetching price data:
	private void getProductPriceData() {
		//price:
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String extaxVal = exTax.split(":")[1].trim();
		productInfoMap.put("productprice", price);
		productInfoMap.put("exTax", extaxVal);
	}
	
	
	
	/**
	 * this method add product in cart and capture success message pop up.
	 * @return success message as string
	 */
	
	public String addproductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMsg = eleUtil.waitForElementVisible(cartSuccessMsg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		
		StringBuilder sb = new StringBuilder(successMsg);
		String msg = sb.substring(0,successMsg.length()-1).replace("\n","").toString();
		System.out.println("Cart Success Message : " + sb);
		return msg;
	}
	
}
	

