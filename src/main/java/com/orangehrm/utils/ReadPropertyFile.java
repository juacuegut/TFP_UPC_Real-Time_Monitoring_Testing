package com.orangehrm.utils;

import com.orangehrm.constants.FrameworkConstants;
import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.exceptions.InvalidFilePathException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public final class ReadPropertyFile {

    private ReadPropertyFile(){}

    private static Properties properties= new Properties();
    static {
        try(FileInputStream fs = new FileInputStream(FrameworkConstants.getConfigfilepath());) {
            properties.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static String getValue(ConfigProperties key) {
        String value="";
        value = properties.getProperty(key.name().toLowerCase());
        if(Objects.isNull(value)){
            throw new InvalidFilePathException("Property name "+key+ " is not found. Please check config.properties");
        }
        return value;
    }
}
