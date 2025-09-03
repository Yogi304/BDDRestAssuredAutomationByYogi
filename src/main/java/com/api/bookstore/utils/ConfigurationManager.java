package com.api.bookstore.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

    private static Properties properties = new Properties();

    public static String get(String key) {
        String configFilePath = "config.properties";
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
            
            String value=  properties.getProperty(key);
            if (value == null) {
                throw new IllegalArgumentException(
                    "Key '" + key + "' not found in " + configFilePath
                );
            }
            
            return value;
        } catch (IOException e) {
            throw new RuntimeException(
                "Failed to load configuration from " + configFilePath, e
            );
        }
    }
    
   
   
}
