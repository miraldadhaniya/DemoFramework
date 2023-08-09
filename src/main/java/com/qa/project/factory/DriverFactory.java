package com.qa.project.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.io.FileHandler;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;




import io.github.bonigarcia.wdm.WebDriverManager;
import pom.qa.project.exception.FrameworkException;
/**
 * This class contains methods to inititalized driver, provide driver manager options and intitialized property file
 * @author Miral
 *
 */
public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * This method is initializing the driver on the basis of given browser name
	 * @param browserName
	 * @return this returns the driver
	 */
	public WebDriver initDriver(Properties prop) {

		optionsManager = new OptionsManager(prop);	
		
		highlight= prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").toLowerCase().trim();
		//String browserName = system.getProperty("browser");
		System.out.println("browser name is : " + browserName);

		//Chrome:
		
		if (browserName.equalsIgnoreCase("chrome")) {	
			WebDriverManager.chromedriver().setup();
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//run on remote/grid:
				init_remoteDriver("chrome");
			}
			else {
				//local execution
				//driver = new ChromeDriver(optionsManager.getChromeOptions());	
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}	
		}
		
		//firefox:
		else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//run on remote/grid:
				init_remoteDriver("firefox");
			}else {
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
		}
		
		//edge:
		else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//run on remote/grid:
				init_remoteDriver("edge");
			}else {
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
		}
		else
		{
			System.out.println("please pass the right browser...." + browserName);
			throw new FrameworkException("NO BROWSER FOUND EXCEPTION");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}
	
	private void init_remoteDriver(String browser) {
		System.out.println("Running tests on grid server:::" + browser);

		try {
			switch (browser.toLowerCase()) {
			case "chrome":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;
			default:
				System.out.println("plz pass the right browser for remote execution..." + browser);
				throw new FrameworkException("NOREMOTEBROWSEREXCEPTION");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get the local thread copy of the driver
	 */
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	/**
	 * this method is reading the properties from .properties file
	 * @return
	 */
	public Properties initProp() {
		
		//mvn clean install -Denv="stage"
		//mvn clean install 
		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: " + envName);
		
		try {
		if(envName == null) {
			System.out.println("no env is passed....Running tests on QA env...");
	
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			
		    }
	        else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "stage":
				 ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "dev":
				 ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "prod":
				 ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
				break;
			default:
				System.out.println("...Wrong env is passed ....No need to run the test cases...");
				throw new FrameworkException("WRONG ENV IS PASSED...");
			
			}
		}
		}catch (FileNotFoundException e) {
			// TODO: handle exception
		}
			try {
				prop.load(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   return prop;
	}
	
	/**
	 * take screenshot
	 */
   public static String getScreenshot() {
	  File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
	  String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
	  File destination = new File(path);
	  try {
		  FileUtil.copyFile(srcFile, destination);
	  }catch (IOException e) {
		// TODO: handle exception
		  e.printStackTrace();
	}
	  return path;
   }
   
}
