package com.Automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.Automation.Utils.CommonUtils;
import com.Automation.Utils.WebActionUtils;
import com.Automation.baseClass.BaseClass;

import io.qameta.allure.Step;

public class LaunchPage {
	
	private Logger logger=LogManager.getLogger(LaunchPage.class);
	By txt_SearchItem_By_ID=By.id("search_query_top");
	By btn_SearchItem_By_CSS=By.cssSelector("button[name='submit_search']");
	By link_SignIn_By_PartialLinkText=By.partialLinkText("Sign in");
	By list_searchItems_By_CSS=By.cssSelector(".ac_results li");
	By list_topMenu_By_CSS=By.cssSelector("#block_top_menu ul.sf-menu li a");
	
	@Step("Method to Click on SignIn Link from Launch Page and Navigate to Authentication Page")
	public AuthenticationPage fnc_clickOnSignInButton(){
		try{
				logger.info("Start Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
				WebActionUtils.clickOnElement.accept(link_SignIn_By_PartialLinkText);
				logger.info("End Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
				return new AuthenticationPage();
		}catch(Exception e){
			logger.fatal("Method:"+Thread.currentThread().getStackTrace()[2].getMethodName()+" has thrown Exception. Exception details:"+e.getMessage());
			return null;
		}
	}
	
	@Step("Method to enter Search Item:{0} in Item search field")
	public void fnc_inputItemInSearchBox(String searchItemName){
		try{
			logger.info("Start Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
			WebActionUtils.sendKeysToElement.accept(txt_SearchItem_By_ID, searchItemName);
			logger.info("End Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
		}catch(Exception e){
			logger.fatal("Method:"+Thread.currentThread().getStackTrace()[2].getMethodName()+" has thrown Exception. Exception details:"+e.getMessage());
		}
	}
	
	@Step("Method to click on Search Button when Customer provided his/her data to Search")
	public String fnc_clickOnSearchBox(){
		try{
			logger.info("Start Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
			WebActionUtils.clickOnElement.accept(btn_SearchItem_By_CSS);
			logger.info("End Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
			return CommonUtils.getCurrentURL.apply(BaseClass.getDriver());
		}catch(Exception e){
			logger.fatal("Method:"+Thread.currentThread().getStackTrace()[2].getMethodName()+" has thrown Exception. Exception details:"+e.getMessage());
			return null;
		}
	}
	
	@Step("Method to select Search Item:{0} from the Search List and Navigate to Specific Searched Item Page")
	public String fnc_selectItemMatchedCondition(String itemToMatch){
		try{
				logger.info("Start Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
				if(!BaseClass.getDriver().findElements(list_searchItems_By_CSS).isEmpty()){
					WebActionUtils.clickElementFromListBasedOnMatchedText.accept(WebActionUtils.fluentWaitForElements.apply(list_searchItems_By_CSS), itemToMatch);
				}
				logger.info("End Method:"+Thread.currentThread().getStackTrace()[2].getMethodName());
				return CommonUtils.getCurrentURL.apply(BaseClass.getDriver());
		}catch(Exception e){
			logger.fatal("Method:"+Thread.currentThread().getStackTrace()[2].getMethodName()+" has thrown Exception. Exception details:"+e.getMessage());
			return null;
		}
	}

}
