package com.securelogin.steps;

import com.securelogin.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks for setup and teardown operations
 * Executes before and after each scenario
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
public class Hooks {
    
    private DriverManager driverManager;
    private WebDriver driver;
    
    /**
     * Executes before each scenario
     * Initializes WebDriver and opens browser
     * 
     * @param scenario Current scenario information
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  Starting Test: " + scenario.getName());
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        driverManager = new DriverManager();
        driver = driverManager.getDriver();
        
        System.out.println("✓ Browser initialized successfully");
    }
    
    /**
     * Executes after each scenario
     * Takes screenshot on failure and closes browser
     * 
     * @param scenario Current scenario information
     */
    @After
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            // Take screenshot if scenario failed
            if (scenario.isFailed()) {
                System.out.println("✗ Test Failed - Capturing screenshot");
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            } else {
                System.out.println("✓ Test Passed");
            }
            
            // Close browser
            driverManager.quitDriver();
            System.out.println("✓ Browser closed");
        }
        
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println();
    }
}
