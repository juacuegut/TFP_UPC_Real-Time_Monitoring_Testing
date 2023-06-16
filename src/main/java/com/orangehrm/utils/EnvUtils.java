package com.orangehrm.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtils {

    private static Dotenv dotenv = Dotenv.configure().load();

    public static String getEnvVariable(String variableName) {
        return dotenv.get(variableName);
    }

}

