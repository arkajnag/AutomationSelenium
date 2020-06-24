package com.Automation.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;



public class BaseClass {
	
	private static Logger logger=LogManager.getLogger(BaseClass.class.getName());
	
	protected static ThreadLocal<WebDriver> driver=new ThreadLocal<WebDriver>();
	protected static ThreadLocal<Properties> props=new ThreadLocal<Properties>();
	protected static ThreadLocal<String> platformName=new ThreadLocal<String>();
	protected static ThreadLocal<String> browserName=new ThreadLocal<String>();
	
	public static String getPlatformName(){
		return platformName.get();
	}
	
	public static String getBrowserName(){
		return browserName.get();
	}
	
	public static WebDriver getDriver(){
		return driver.get();
	}
	
	public static Properties getProps(){
		return props.get();
	}
	
	public static void setDriver(WebDriver driver2){
		driver.set(driver2);
	}
	
	public static void setProps(Properties props2){
		props.set(props2);
	}
	
	public static void setPlatformName(String platformName2){
		platformName.set(platformName2);
	}
	
	public static void setBrowserName(String browserName2){
		platformName.set(browserName2);
	}
	
	public static void initializeDriver(String platformName, String browserName){
		try{
				String logFilePath="Logs"+File.separator+platformName+"_"+browserName;
				File logfile=new File(logFilePath);
				synchronized (logfile) {
					if(!logfile.exists()){
						logfile.mkdirs();
					}
				}
				ThreadContext.put("ROUTINGKEY", logFilePath);
				setProps(new Properties());
				InputStream is=new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/config.properties");
				getProps().load(is);
				DesiredCapabilities caps=null;
				logger.info("Initializing Selenium WebDriver to start execution");
				switch(platformName.toLowerCase()){
				case "local":
					switch(browserName.toLowerCase()){
					case "chrome":
						logger.info("Launching Chrome Browser in Local");
//						System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/Drivers/chromedriver.exe");
						WebDriverManager.chromedriver().setup();
						ChromeOptions chromeOptions=new ChromeOptions();
						chromeOptions.setAcceptInsecureCerts(true);
						chromeOptions.addArguments("--disable-notifications");
						setDriver(new ChromeDriver(chromeOptions));
						logger.info("Chrome Browser is launched in Local");
						break;
					case "firefox":
						logger.info("Launching Firefox Browser in Local");
//						System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test/resources/Drivers/geckodriver.exe");
						WebDriverManager.firefoxdriver().setup();
						FirefoxOptions firefoxOptions=new FirefoxOptions();
						firefoxOptions.setAcceptInsecureCerts(true);
						setDriver(new FirefoxDriver(firefoxOptions));
						logger.info("Firefox Browser in Local");
						break;
					default:
						logger.error("Sorry No Browser found to launch for Option:"+browserName+" in Local Instance");
						Assert.fail("Sorry No Browser found to launch for Option:"+browserName+" in Local Instance", new NoSuchSessionException());
					}
					break;
				case "remote":
					switch(browserName.toLowerCase()){
					case "chrome":
						logger.info("Launching Chrome Browser in Remote");
						caps=DesiredCapabilities.chrome();
						caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
						break;
					case "firefox":
						logger.info("Launching Firefox Browser in Remote");
						caps=DesiredCapabilities.firefox();
						caps.setCapability(CapabilityType.BROWSER_NAME, "firefox");
						break;
					default:
						logger.error("Sorry No Browser found to launch for Option:"+browserName+" in Remote Instance");
						Assert.fail("Sorry No Browser found to launch for Option:"+browserName+" in Remote Instance", new NoSuchSessionException());
					}
					caps.setCapability("build", "AutomationPractise Remote Test Build");
					caps.setCapability("name", "AutomationPractise-Test");
					caps.setCapability("visual", true);
					setDriver(new RemoteWebDriver(new URL(getProps().getProperty("REMOTE_URL")),caps));
					break;
				case "docker-image":
					switch(browserName.toLowerCase()){
					case "chrome":
						logger.info("Launching Chrome Browser in Docker Machine");
						caps=DesiredCapabilities.chrome();
						break;
					case "firefox":
						logger.info("Launching Firefox Browser in Docker Machine");
						caps=DesiredCapabilities.firefox();
						break;
					default:
						logger.error("Sorry No Browser found to launch for Option:"+browserName+" in Remote Instance");
						Assert.fail("Sorry No Browser found to launch for Option:"+browserName+" in Remote Instance", new NoSuchSessionException());
					}
					setDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps));
					break;
				default:
					logger.error("Sorry No Platform found to launch for Option:"+browserName);
					Assert.fail("Sorry No Platform found to launch for Option:"+browserName, new NoSuchSessionException());
				}
				WebDriver lDriver=getDriver();
				lDriver.manage().window().maximize();
				lDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				lDriver.manage().deleteAllCookies();
				lDriver.navigate().to(getProps().getProperty("APP_BASE_URL"));
				setPlatformName(platformName);
				setBrowserName(browserName);
				logger.info("Selenium WebDriver is initialized successfully to start execution");
		}catch(Exception e){
			logger.fatal("Driver is not Initialized and Error is:"+e.getLocalizedMessage(),new NoSuchSessionException(e.getMessage()));
			Assert.fail();
		}
	}
	
	
	public static void tearDownDriver(){
		try{
			logger.info("Driver is being closed.");
			if(getDriver()!=null){
				getDriver().quit();
			}
			logger.info("Driver closed successfully.");
		}catch(Exception e){
			logger.fatal("Driver is not closed due to Exception:"+e.getMessage());
		}
	}

}
