package com.orangehrm.listeners;

import com.orangehrm.annotations.FrameworkAnnotation;
import com.orangehrm.annotations.JiraCreateIssue;
import com.aventstack.extentreports.model.Log;
import com.orangehrm.driver.DriverManager;
import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.utils.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.influxdb.dto.Point;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;
import com.orangehrm.reports.ExtentLogger;
import com.orangehrm.reports.ExtentManager;
import com.orangehrm.reports.ExtentReport;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Listener implements ITestListener, ISuiteListener {

    String JiraURL = null;
    String screenshotName = null;

    @Override
    public void onStart(ISuite suite) {
        ExtentReport.initReports();
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        this.postTestClassStatus(iTestContext);
        ExtentReport.flushReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReport.createTest(result.getMethod().getMethodName());
        ExtentReport.addCategories(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).category());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentLogger.pass(result.getMethod().getMethodName() + " is passed");
        this.postTestMethodStatus(result, "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentLogger.fail(result.getMethod().getMethodName() + " is failed");
        ExtentLogger.failWithoutImage(result.getThrowable().toString());
        ExtentLogger.failWithoutImage(Arrays.toString(result.getThrowable().getStackTrace()));

        boolean islogIssue = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraCreateIssue.class).isCreateIssue();

        if (islogIssue) {

            JiraUtils JiraUtils = new JiraUtils(EnvUtils.getEnvVariable("JIRA_URL"),

                    EnvUtils.getEnvVariable("JIRA_USER"),EnvUtils.getEnvVariable("JIRA_TOKEN"), EnvUtils.getEnvVariable("JIRA_PROJECT"));

            List<Log> logs = ExtentManager.getExtentTest().getModel().getLogs();
            StringBuilder detailsList = new StringBuilder();


            for (int i = 0; i < logs.size()-2; i++) {
                String details = logs.get(i).getDetails();
                detailsList.append(i + 1).append(". ").append(details).append("\n");
            }


            String issueDescription =
                    "Environment:\n\n" + "OS: " + System.getProperty("os.name") + "\n" + "OS version: " + System.getProperty("os.version") + "\n" + "Architecture: " + System.getProperty("os.arch") + "\n\n" +
                            "Browser: " + ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities().getBrowserName() + "\n" + "Browser version: " + ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities().getVersion() + "\n\n"  +
                            "URL: " + ReadPropertyFile.getValue(ConfigProperties.URL) + "\n\n" +
                            "Description: " + "\n\n" +
                            "Failure Reason from Automation Testing\n\n" + result.getThrowable().getMessage() + "\n\n" +
                            "Steps to Reproduce:" + "\n\n" + detailsList;

            issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));

            String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName()

                    + " Failed in Automation Testing";

            screenshotName = ScreenshotUtils.getPNGImage();

            JiraUtils.createJiraIssue("Bug", issueSummary, issueDescription,
                    "extent-test-output/screenshots/" + screenshotName);

            JiraURL = JiraUtils.getJiraUrl();
        }


        //InfluxDBLogging
        this.postTestMethodStatus(result, "FAIL");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentLogger.skip(result.getMethod().getMethodName() + " is skipped");
        this.postTestMethodStatus(result, "SKIPPED");
    }

    private void postTestMethodStatus(ITestResult iTestResult, String status) {
        long durationInMillis = iTestResult.getEndMillis() - iTestResult.getStartMillis();
        double durationInSeconds = durationInMillis / 1000.0;

        String errorCode = null;

        if (status.equals("FAIL")) {
            errorCode = iTestResult.getThrowable().getMessage();
        }

        Point point = null;
        if (status.equals("FAIL")) {
            point = Point.measurement("testmethod").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("testclass", iTestResult.getTestClass().getName()).tag("name", iTestResult.getName())
                    .tag("description", iTestResult.getMethod().getDescription()).tag("result", status)
                    .addField("duration", (durationInSeconds))
                    .addField("errorCode", errorCode)
                    .addField("screenshot", "http://localhost:8888/" + screenshotName)
                    .addField("name_field", iTestResult.getName())
                    .addField("result_field", status)
                    .addField("JiraURL", JiraURL)
                    .build();
        } else {
            point = Point.measurement("testmethod").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("testclass", iTestResult.getTestClass().getName()).tag("name", iTestResult.getName())
                    .tag("description", iTestResult.getMethod().getDescription()).tag("result", status)
                    .addField("duration", (durationInSeconds))
                    .addField("name_field", iTestResult.getName())
                    .addField("result_field", status)
                    .build();
        }
        InfluxDBUtils.post(point);
    }

    private void postTestClassStatus(ITestContext iTestContext) {
        long durationInMillis = iTestContext.getEndDate().getTime() - iTestContext.getStartDate().getTime();
        double durationInSeconds = durationInMillis / 1000.0;

        Point point = Point.measurement("testclass").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("name", iTestContext.getAllTestMethods()[0].getTestClass().getName())
                .addField("duration", (durationInSeconds))
                .build();
        InfluxDBUtils.post(point);
    }

}
