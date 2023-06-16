package com.orangehrm.reports;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.orangehrm.driver.DriverManager;
import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.utils.ReadPropertyFile;
import com.orangehrm.utils.ScreenshotUtils;

public final class ExtentLogger {

    private ExtentLogger() {
    }

    public static void pass(String message) {
        try {
            if (ReadPropertyFile.getValue(ConfigProperties.PASSEDSTEPSSCREENSHOT).equalsIgnoreCase("yes")) {
                ExtentManager.getExtentTest().pass(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            } else {
                ExtentManager.getExtentTest().pass(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fail(String message) {
        try {
            ExtentManager.getExtentTest().fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void failWithoutImage(String message) {
        try {
            ExtentManager.getExtentTest().fail(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void skip(String message) {
        try {
            if (ReadPropertyFile.getValue(ConfigProperties.SKIPEDSTEPSSCREENSHOT).equalsIgnoreCase("yes")) {
                ExtentManager.getExtentTest().skip(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            } else {
                ExtentManager.getExtentTest().skip(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
