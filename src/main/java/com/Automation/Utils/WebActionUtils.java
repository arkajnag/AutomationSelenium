package com.Automation.Utils;

import java.time.Duration;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.Automation.baseClass.BaseClass;

public interface WebActionUtils {
	
	public static Function<By,WebElement> fluentWaitForElement=locator ->{
			Wait<WebDriver> fluentWait=new FluentWait<WebDriver>(BaseClass.getDriver())
					.pollingEvery(Duration.ofSeconds(2))
					.withTimeout(Duration.ofSeconds(15))
					.ignoring(NoSuchElementException.class);
			return fluentWait.until(driver-> driver.findElement(locator));
	};
	
	public static Function<By,List<WebElement>> fluentWaitForElements=locator->{
			Wait<WebDriver> fluentWait=new FluentWait<WebDriver>(BaseClass.getDriver())
					.pollingEvery(Duration.ofSeconds(2))
					.withTimeout(Duration.ofSeconds(15))
					.ignoring(NoSuchElementException.class);
			return fluentWait.until(driver->driver.findElements(locator));
	};
	
	public static BiConsumer<By,String> sendKeysToElement=(locator,textToEnter)-> fluentWaitForElement.apply(locator).sendKeys(textToEnter);

	public static Consumer<By> clickOnElement=locator->fluentWaitForElement.apply(locator).click();
	
	public static BiConsumer<By,String> selectByVisibleText=(locator, visibleText)-> new Select(fluentWaitForElement.apply(locator)).selectByVisibleText(visibleText);

	public static BiConsumer<By,Integer> selectByIndex=(locator, index)-> new Select(fluentWaitForElement.apply(locator)).selectByIndex(index);		
	
	public static BiConsumer<List<WebElement>,String> clickElementFromListBasedOnMatchedText=(locators,textToMatched)-> locators.parallelStream().filter(element-> element.getText().equalsIgnoreCase(textToMatched)).findFirst().get().click();
	
	public static BiConsumer<WebDriver,String> switchToWindow=(driver,windowName)-> driver.switchTo().window(windowName);
	
	public static BiConsumer<WebDriver,String> switchToChildWindow=(driver, parentWindowName)-> driver.switchTo().window(driver.getWindowHandles().stream().filter(currentWindowName->!parentWindowName.equalsIgnoreCase(currentWindowName)).findFirst().get());
	
	public static Function<By,String> getTextFromElement=locator->fluentWaitForElement.apply(locator).getText();
	
	public static BiFunction<By,String,Object> getAttributeValueOfElement=(locator,attributeType) ->fluentWaitForElement.apply(locator).getAttribute(attributeType);

	public static BiConsumer<WebDriver,By> moveToElement=(driver, locator)-> new Actions(driver).moveToElement(fluentWaitForElement.apply(locator)).perform();
	

}
