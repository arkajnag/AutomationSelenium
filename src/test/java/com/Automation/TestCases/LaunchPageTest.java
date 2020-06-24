package com.Automation.TestCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import com.Automation.Utils.CommonUtils;
import com.Automation.pages.LaunchPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Automation Practise Framework Testing")
@Feature("Launch Page Test Features")
public class LaunchPageTest extends BaseTest{
	
	  LaunchPage launchPage=null;
	  SoftAssert sftAssert=null;
	
	  @BeforeMethod
	  public void setUpTestMethod(){
		  launchPage=new LaunchPage();
		  sftAssert=new SoftAssert();
	  }
	  
	  @Story("Click on SignIn Link in Launch Page")
	  @Description("Verify Authentication Page is Opened on click on SignIn link")
	  @Severity(SeverityLevel.NORMAL)
	  @Test(priority=1,description="Verify Authentication Page is Opened on click on SignIn link")
	  public void tc_VerifyClickOnSignIn() {
		  launchPage.fnc_clickOnSignInButton();
		  AssertJUnit.assertEquals(CommonUtils.getCurrentPageTitle.apply(getDriver()), CommonUtils.getDataFromXML.apply(System.getProperty("user.dir")+"/src/test/resources/Constants/statics.xml").get("AuthenticationTitle"));
		  sftAssert.assertAll();
	  }
}
