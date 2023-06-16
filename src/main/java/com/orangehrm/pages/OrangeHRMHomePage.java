package com.orangehrm.pages;

import com.orangehrm.driver.DriverManager;
import com.orangehrm.enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class OrangeHRMHomePage extends BasePage {

    private final By userDropdown = By.xpath("//span[@class='oxd-userdropdown-tab']");

    private final By logoutButton = By.xpath("//a[contains(text(),'Logout')]");

    private final By myProfileButton = By.xpath("//a[contains(text(),'My Profile')]");

    public OrangeHRMHomePage() {
        isHomePageOpen();
    }

    public OrangeHRMHomePage clickUserDropdown(){
        click(userDropdown, WaitStrategy.CLICKABLE,"User Dropdown");
        return this;
    }

    public OrangeHRMLoginPage clickLogoutButton() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),5);
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        click(logoutButton,WaitStrategy.CLICKABLE,"Logout Button");
        return new OrangeHRMLoginPage();
    }

    public void clickMyProfileButton() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),5);
        wait.until(ExpectedConditions.elementToBeClickable(myProfileButton));
        click(myProfileButton,WaitStrategy.CLICKABLE,"My Profile Button");
    }

    public OrangeHRMHomePage isHomePageOpen(){
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(userDropdown));
        return this;
    }

}
