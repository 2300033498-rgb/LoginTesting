package com.securelogin.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * WebDriver Manager Utility
 * Manages WebDriver instance creation and configuration
 * Implements Singleton pattern to ensure single driver instance
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
public class DriverManager {
    
    private static WebDriver driver;
    private static final String BROWSER = System.getProperty("browser", "chrome");
    private static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", "false"));
    
    /**
     * Gets the WebDriver instance (creates if not exists)
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }
    
    /**
     * Creates and configures WebDriver based on browser type
     * 
     * @return Configured WebDriver instance
     */
    private static WebDriver createDriver() {
        WebDriver newDriver;
        
        switch (BROWSER.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                
                if (HEADLESS) {
                    chromeOptions.addArguments("--headless");
                }
                
                // Chrome performance and stability options
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--ignore-certificate-errors");
                
                newDriver = new ChromeDriver(chromeOptions);
                System.out.println("✓ Chrome browser initialized");
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                
                if (HEADLESS) {
                    firefoxOptions.addArguments("--headless");
                }
                
                newDriver = new FirefoxDriver(firefoxOptions);
                newDriver.manage().window().maximize();
                System.out.println("✓ Firefox browser initialized");
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                
                if (HEADLESS) {
                    edgeOptions.addArguments("--headless");
                }
                
                newDriver = new EdgeDriver(edgeOptions);
                newDriver.manage().window().maximize();
                System.out.println("✓ Edge browser initialized");
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + BROWSER);
        }
        
        // Set implicit wait
        newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Set page load timeout
        newDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        return newDriver;
    }
    
    /**
     * Quits the WebDriver and sets instance to null
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    /**
     * Gets current browser name
     * 
     * @return Browser name
     */
    public static String getBrowserName() {
        return BROWSER;
    }
    
    /**
     * Checks if running in headless mode
     * 
     * @return true if headless, false otherwise
     */
    public static boolean isHeadless() {
        return HEADLESS;
    }
}
