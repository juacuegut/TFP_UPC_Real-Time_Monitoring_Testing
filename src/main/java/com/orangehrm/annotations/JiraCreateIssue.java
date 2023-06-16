package com.orangehrm.annotations;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)

public @interface JiraCreateIssue {

    boolean isCreateIssue();

}
