package com.Automation.Listeners;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.Automation.Reporting.ExtentReporter;
import com.Automation.Utils.CommonUtils;
import com.Automation.baseClass.BaseClass;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.qameta.allure.Attachment;


public class TestListeners implements ITestListener{
	
	private Logger logger=LogManager.getLogger(TestListeners.class.getName());

	@Override
	public void onFinish(ITestContext result) {
		ExtentReporter.getReporter().flush();
		
	}

	@Override
	public void onStart(ITestContext result) {
		
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
	}

	
	public void onTestFailure(ITestResult result) {
		HashMap<String,String> allParameters=(HashMap<String, String>) result.getTestContext().getCurrentXmlTest().getAllParameters();
		logger.fatal("Test Case:"+result.getName()+" has failed");
		String screenShotPath="Screenshots"+File.separator+CommonUtils.getFormattedDateTime.apply("yyyy-MM-dd-HH-mm-ss")
		+File.separator+allParameters.get("browserName")+"_"+allParameters.get("platformName")+File.separator+result.getMethod().getConstructorOrMethod().getName()+".png";
		CommonUtils.captureScreenshot.accept(BaseClass.getDriver(), screenShotPath);
		File file = ((TakesScreenshot)BaseClass.getDriver()).getScreenshotAs(OutputType.FILE);
		byte[] encoded = null;
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		logger.info(screenShotPath);
		try {
			ExtentReporter.getTest().fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExtentReporter.getTest().fail(result.getThrowable());
		attachScreenshotForFailureInAllure(BaseClass.getDriver());
	}
	
	@Attachment(value="Screenshot",type="image/png")
	public byte[] attachScreenshotForFailureInAllure(WebDriver driver){
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReporter.getTest().log(Status.SKIP, "Test Case:"+result.getName()+" is skipped");
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		logger.info("Test Case:"+result.getName()+" has started");
		ExtentReporter.startTest(result.getName(),result.getMethod().getDescription())
		.assignCategory(BaseClass.getBrowserName()+"_"+BaseClass.getPlatformName())
		.assignAuthor("Arkajyoti");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Test Case:"+result.getName()+" has executed successfully");
	}
	
	

}
