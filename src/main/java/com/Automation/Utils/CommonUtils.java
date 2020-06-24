package com.Automation.Utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface CommonUtils {
	
	public static Logger logger=LogManager.getRootLogger();
	
	public static Function<String,String> getFormattedDateTime=pattern ->DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.now());
	
	public static BinaryOperator<Integer> randomNumberInRange=(minValue, maxValue)-> new Random().ints(minValue, (maxValue + 1)).findFirst().getAsInt();
	
	public static BiConsumer<WebDriver,String> captureScreenshot=(driver,screenshotPath)-> {
			try{
					File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(srcFile, new File(screenshotPath));
			}catch(Exception e){
				logger.fatal("Exception in Capture Screenshot method. Exception details:"+e.getLocalizedMessage());
		}
	};

	public static Function<WebDriver,String> getCurrentURL=driver->driver.getCurrentUrl();

	
	public static Function<String, HashMap<String, Object>> getDataFromXML=filePath-> {
			HashMap<String,Object> XMLConstants=new HashMap<>();
			try {
					File fXmlFile = new File(filePath);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("data");
					for (int temp = 0; temp < nList.getLength(); temp++) {
						Node nNode = nList.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							XMLConstants.put(eElement.getAttribute("id"), eElement.getTextContent());
						}
					}
			   } catch (Exception e) {
				e.printStackTrace();
			}
			return XMLConstants;
	};
	
	
	public static Function<WebDriver,String> getCurrentPageTitle=driver->driver.getTitle();
	
}
