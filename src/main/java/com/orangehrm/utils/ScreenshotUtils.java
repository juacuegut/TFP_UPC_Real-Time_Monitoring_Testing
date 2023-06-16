package com.orangehrm.utils;

import com.orangehrm.driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtils {

    private ScreenshotUtils(){}

    public static String getBase64Image() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

    public static String getPNGImage() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyy_HH-mm-ss");
        Date date = new Date();

        File screenshotFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

        String screenshotName = "screenshot_"+formatter.format(date)+".png";

        String filePath = "extent-test-output/screenshots/"+screenshotName;

        try {
            FileUtils.copyFile(screenshotFile, new File(filePath));
        } catch (IOException e) {
            System.out.println("Error al guardar la captura de pantalla: " + e.getMessage());
        }
        return screenshotName;
    }

    public static void deleteContentInImageFolder(String imageFolder) {

        File folder = new File(imageFolder);

        if (folder.exists()) {
            File[] screenshots = folder.listFiles();

            for (File screenshot : screenshots) {
                screenshot.delete();
            }
        } else {
            System.out.println("Fodler does not exist.");
        }
    }
}
