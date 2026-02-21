package com.securelogin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Page Object class containing common methods used across all page objects
 * Implements the Page Object Model design pattern
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    
    // Timeout constants
    private static final int DEFAULT_TIMEOUT = 10;
    private static final int LOADING_TIMEOUT = 15;
    
    /**
     * Constructor initializes WebDriver and WebDriverWait
     * 
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.js = (JavascriptExecutor) driver;
    }
    
    /**
     * Waits for an element to be visible and returns it
     * 
     * @param by Locator strategy for the element
     * @return WebElement that is visible
     */
    protected WebElement waitForElementVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
    /**
     * Waits for an element to be clickable and returns it
     * 
     * @param by Locator strategy for the element
     * @return WebElement that is clickable
     */
    protected WebElement waitForElementClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
    
    /**
     * Checks if an element is displayed
     * 
     * @param by Locator strategy for the element
     * @return true if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Checks if an element is enabled
     * 
     * @param by Locator strategy for the element
     * @return true if element is enabled, false otherwise
     */
    protected boolean isElementEnabled(By by) {
        try {
            return driver.findElement(by).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets text from an element
     * 
     * @param by Locator strategy for the element
     * @return Text content of the element
     */
    protected String getElementText(By by) {
        return waitForElementVisible(by).getText();
    }
    
    /**
     * Gets attribute value from an element
     * 
     * @param by Locator strategy for the element
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getElementAttribute(By by, String attribute) {
        return driver.findElement(by).getAttribute(attribute);
    }
    
    /**
     * Clears and types text into an input field
     * 
     * @param by Locator strategy for the element
     * @param text Text to type
     */
    protected void clearAndType(By by, String text) {
        WebElement element = waitForElementVisible(by);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Clicks an element
     * 
     * @param by Locator strategy for the element
     */
    protected void clickElement(By by) {
        waitForElementClickable(by).click();
    }
    
    /**
     * Scrolls element into view using JavaScript
     * 
     * @param by Locator strategy for the element
     */
    protected void scrollToElement(By by) {
        WebElement element = driver.findElement(by);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Waits for page to load completely
     */
    protected void waitForPageLoad() {
        wait.until(driver -> 
            js.executeScript("return document.readyState").equals("complete")
        );
    }
    
    /**
     * Gets current URL
     * 
     * @return Current URL string
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Gets page title
     * 
     * @return Page title string
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Pauses execution for specified milliseconds
     * Used sparingly, prefer explicit waits
     * 
     * @param milliseconds Duration to wait
     */
    protected void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Checks if element has a specific CSS class
     * 
     * @param by Locator strategy for the element
     * @param className CSS class name to check
     * @return true if element has the class, false otherwise
     */
    protected boolean hasClass(By by, String className) {
        String classes = getElementAttribute(by, "class");
        return classes != null && classes.contains(className);
    }
    
    /**
     * Waits for element to disappear
     * 
     * @param by Locator strategy for the element
     * @return true if element is invisible, false otherwise
     */
    protected boolean waitForElementToDisappear(By by) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Exception e) {
            return false;
        }
    }
}
