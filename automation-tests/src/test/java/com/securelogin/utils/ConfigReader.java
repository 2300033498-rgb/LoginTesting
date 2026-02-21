package com.securelogin.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Reader Utility
 * Reads configuration properties from config.properties file
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
public class ConfigReader {
    
    private Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";
    
    /**
     * Constructor loads properties from config file
     */
    public ConfigReader() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
            // Set default values if config file not found
            setDefaultProperties();
        }
    }
    
    /**
     * Sets default property values
     */
    private void setDefaultProperties() {
        properties.setProperty("base.url", "http://localhost:3000");
        properties.setProperty("api.url", "http://localhost:5000");
        properties.setProperty("valid.username", "admin");
        properties.setProperty("valid.password", "admin123");
    }
    
    /**
     * Gets base URL of the application
     * 
     * @return Base URL
     */
    public String getBaseUrl() {
        return properties.getProperty("base.url", "http://localhost:3000");
    }
    
    /**
     * Gets API URL
     * 
     * @return API URL
     */
    public String getApiUrl() {
        return properties.getProperty("api.url", "http://localhost:5000");
    }
    
    /**
     * Gets valid test username
     * 
     * @return Valid username
     */
    public String getValidUsername() {
        return properties.getProperty("valid.username", "admin");
    }
    
    /**
     * Gets valid test password
     * 
     * @return Valid password
     */
    public String getValidPassword() {
        return properties.getProperty("valid.password", "admin123");
    }
    
    /**
     * Gets property by key
     * 
     * @param key Property key
     * @return Property value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Gets property with default value
     * 
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
