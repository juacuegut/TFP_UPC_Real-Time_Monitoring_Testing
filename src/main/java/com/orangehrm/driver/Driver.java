package com.orangehrm.driver;

import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.exceptions.BrowserInvocationFailedException;
import com.orangehrm.factories.DriverFactory;
import org.openqa.selenium.WebDriver;
import com.orangehrm.utils.ReadPropertyFile;

import java.net.MalformedURLException;
import java.util.Objects;

public final class Driver {

    private Driver(){}
    private static WebDriver driver;

    public static void initDriver(){
        if(Objects.isNull(DriverManager.getDriver())) {
            try {
                DriverManager.setDriver(DriverFactory.getDriver());
            } catch (MalformedURLException e) {
                throw new BrowserInvocationFailedException("Please check the browser capabilities",e);
            }
            DriverManager.getDriver().get(ReadPropertyFile.getValue(ConfigProperties.URL));
        }
    }

    public static void quitDriver(){
        if(Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }

}
