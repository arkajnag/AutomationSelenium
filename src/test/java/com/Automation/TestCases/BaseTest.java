package com.Automation.TestCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.Automation.baseClass.BaseClass;

public class BaseTest extends BaseClass{
	
	@Parameters({"platformName","browserName"})
	@BeforeMethod
	public void setUpTestCase(@Optional("local")String platformName,@Optional("chrome")String browserName){
		initializeDriver(platformName, browserName);
	}
	
	
	@AfterMethod
	public void tearDownTestCase(){
		tearDownDriver();
	}

}
