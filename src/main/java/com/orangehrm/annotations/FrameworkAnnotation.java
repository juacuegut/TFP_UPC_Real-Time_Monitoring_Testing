package com.orangehrm.annotations;

import com.orangehrm.enums.CategoryTypes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface FrameworkAnnotation {

    public CategoryTypes[] category();
}