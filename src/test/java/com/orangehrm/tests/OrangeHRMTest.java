package com.orangehrm.tests;

import com.orangehrm.annotations.FrameworkAnnotation;
import com.orangehrm.annotations.JiraCreateIssue;
import com.orangehrm.enums.CategoryTypes;
import org.testng.annotations.Test;
import com.orangehrm.pages.OrangeHRMLoginPage;

import java.util.Map;

public final class OrangeHRMTest extends BaseTest {

    private OrangeHRMTest(){}

    @FrameworkAnnotation(category = {CategoryTypes.SMOKE})
    @JiraCreateIssue(isCreateIssue = true)
    @Test(priority = 0)
    public void log_In_With_Admin_Credentials_Test(Map<String,String> map) {
        OrangeHRMLoginPage lp = new OrangeHRMLoginPage();
        lp.login(map.get("username"), map.get("password"));
    }

    @FrameworkAnnotation(category = {CategoryTypes.SMOKE})
    @JiraCreateIssue(isCreateIssue = true)
    @Test(priority = 1)
    public void log_In_With_User_Credentials_Test(Map<String,String> map) {
        OrangeHRMLoginPage lp = new OrangeHRMLoginPage();
        lp.login("ValidUser", "ValidPassword");
    }

    @FrameworkAnnotation(category = {CategoryTypes.SANITY})
    @JiraCreateIssue(isCreateIssue = true)
    @Test(priority = 2)
    public void log_Out_Test(Map<String,String> map){
        OrangeHRMLoginPage lp = new OrangeHRMLoginPage();
        lp.login(map.get("username"), map.get("password"))
                .clickUserDropdown()
                .clickLogoutButton();
    }

    @FrameworkAnnotation(category = {CategoryTypes.REGRESSION})
    @JiraCreateIssue(isCreateIssue = true)
    @Test(priority = 3)
    public void go_To_MyProfile_From_User_Dashboard_Test(Map<String,String> map){
        OrangeHRMLoginPage lp = new OrangeHRMLoginPage();
        lp.login(map.get("username"), map.get("password"))
                .clickUserDropdown()
                .clickMyProfileButton();
    }

    @FrameworkAnnotation(category = {CategoryTypes.REGRESSION})
    @JiraCreateIssue(isCreateIssue = true)
    @Test(dependsOnMethods = "go_To_MyProfile_From_User_Dashboard_Test")
    public void check_Employee_Id_In_MyProfile_Test(Map<String,String> map){
        OrangeHRMLoginPage lp = new OrangeHRMLoginPage();
        lp.login(map.get("username"), map.get("password"))
                .clickUserDropdown()
                .clickMyProfileButton();
    }

}
