package com.orangehrm.listeners;

import com.orangehrm.enums.ConfigProperties;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.orangehrm.utils.ReadPropertyFile;

public class RetryFailedTests implements IRetryAnalyzer {

    private int count = 0;
    private int retry = 2;

    @Override
    public boolean retry(ITestResult result) {
        try {
            if(ReadPropertyFile.getValue(ConfigProperties.RETRYFAILEDTESTS).equalsIgnoreCase("yes")){
                if(count<retry){
                    count++;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
