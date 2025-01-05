package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class CheckOutPage {
	
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By shoppingCartHeader = By.xpath("//a[contains(text(),'Shopping Cart')]");
	
	
	
	public CheckOutPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getShoppingCardHeaderName() {
		String shoppingCartName = 
				eleUtil.waitForElementVisible(shoppingCartHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		System.out.println("Product Header ===> " + shoppingCartName);
		return shoppingCartName;
	}

}
