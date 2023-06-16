package com.orangehrm.pages;

import com.orangehrm.driver.DriverManager;
import com.orangehrm.enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class OrangeHRMLoginPage extends BasePage {

    private final By usernameBox = By.name("username");

    private final By passwordBox = By.name("password");

    private final By loginButton = By.xpath("//button[@type='submit']");

    private final By invalidCredsError = By.id("spanMessage");

    public OrangeHRMLoginPage enterUsername(String usernameValue){
        sendKeys(usernameBox,usernameValue, WaitStrategy.PRESENCE,"Username");
        return this;
    }

    public OrangeHRMLoginPage enterPassword(String passwordValue){
        sendKeys(passwordBox,passwordValue,WaitStrategy.PRESENCE,"Password");
        return this;
    }

    public OrangeHRMHomePage clickLoginButton(){
        click(loginButton,WaitStrategy.CLICKABLE,"Login Button");
        return new OrangeHRMHomePage();
    }

    public String invalidCredsErrorText() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),10);
        wait.until(ExpectedConditions.elementToBeClickable(invalidCredsError));
        return getText(invalidCredsError,WaitStrategy.PRESENCE);
    }

    public OrangeHRMHomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }

}
