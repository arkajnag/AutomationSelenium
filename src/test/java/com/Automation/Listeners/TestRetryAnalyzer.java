package com.Automation.Listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer{

	private int initialTestCounter=0;
	private int finalTestCounter=2;
	public boolean retry(ITestResult result) {
		if(initialTestCounter<finalTestCounter){
			initialTestCounter++;
			return true;
		}
		return false;
	}

}
