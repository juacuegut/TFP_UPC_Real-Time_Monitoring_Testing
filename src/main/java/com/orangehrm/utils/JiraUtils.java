package com.orangehrm.utils;

import net.rcarz.jiraclient.*;

import java.io.File;

public class JiraUtils {

    private JiraClient Jira;

    private String project;

    private String JiraUrl;

    public Issue newJiraIssue = null;

    public JiraUtils(String JiraUrl, String username, String password, String project) {

        this.JiraUrl=JiraUrl;

        // create basic authentication object

        BasicCredentials creds = new BasicCredentials(username, password);

        // initialize the Jira client with the url and the credentials

        Jira = new JiraClient(JiraUrl, creds);

        this.project = project;

    }


    public void createJiraIssue(String issueType, String summary, String description, String screenshotPath) {
        try {

            //Avoid Creating Duplicate Issue

            Issue.SearchResult sr = Jira.searchIssues("summary ~ \""+summary+"\"");

            if(sr.total!=0) {

                //The ticket already exists
                Issue existingIssue = null;

                for (Issue issue : sr.issues) {
                    existingIssue = issue;
                }

                newJiraIssue = existingIssue;

                return;

            }

            //Create issue if not exists

            Issue.FluentCreate fluentCreate = Jira.createIssue(project, issueType);

            fluentCreate.field(Field.SUMMARY, summary);

            fluentCreate.field(Field.DESCRIPTION, description);

            Issue newIssue = fluentCreate.execute();

            File screenshot = new File(screenshotPath);
            newIssue.addAttachment(screenshot);

            newIssue.update()
                    .fieldAdd(Field.LABELS, "Selenium-Automation")
                    .execute();

            /* Assign the issue */
            newIssue.update()
                    .field(Field.ASSIGNEE, "Juan Manuel Cuerva Gutiérrez")
                    .execute();

            newJiraIssue = newIssue;

        } catch (JiraException e) {

            e.printStackTrace();

        }

    }

    public String getJiraUrl() {
        return JiraUrl+"/browse/"+newJiraIssue;
    }

}
