package com.orangehrm.tests;

import com.orangehrm.driver.Driver;
import com.orangehrm.utils.ScreenshotUtils;
import org.testng.annotations.*;
import com.orangehrm.utils.InfluxDBUtils;

public class BaseTest {

    private static final String IMAGES_FOLDER = "extent-test-output/screenshots";

    protected BaseTest(){}

    @BeforeSuite
    public void setUp() throws Exception {
        ScreenshotUtils.deleteContentInImageFolder(IMAGES_FOLDER);
        InfluxDBUtils.initializeDatabase();
    }

    @BeforeMethod
    public void startUp() throws Exception {
        Driver.initDriver();
    }

    @AfterMethod
    public void tearDown(){
        Driver.quitDriver();
    }

}
