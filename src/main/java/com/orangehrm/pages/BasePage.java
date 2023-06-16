package com.orangehrm.pages;

import com.orangehrm.driver.DriverManager;
import com.orangehrm.enums.WaitStrategy;
import com.orangehrm.factories.ExplicitWaitFactory;
import org.openqa.selenium.By;
import com.orangehrm.reports.ExtentLogger;

public class BasePage {

    protected void sendKeys(By by, String value, WaitStrategy waitStrategy,String elementName){
        ExplicitWaitFactory.performExplicitWait(by,waitStrategy).sendKeys(value);
        ExtentLogger.pass(value +" is entered in the "+elementName);
    }

    protected void click(By by,WaitStrategy waitStrategy,String elementName){
        ExplicitWaitFactory.performExplicitWait(by,waitStrategy).click();
        ExtentLogger.pass(elementName +" is clicked");
    }

    protected String getText(By by,WaitStrategy waitStrategy){
        ExplicitWaitFactory.performExplicitWait(by,waitStrategy);
        return DriverManager.getDriver().findElement(by).getText();
    }
}
