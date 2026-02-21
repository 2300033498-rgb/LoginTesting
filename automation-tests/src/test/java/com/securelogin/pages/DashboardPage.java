package com.securelogin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for Dashboard Page
 * Contains all locators and methods related to post-login dashboard
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
public class DashboardPage extends BasePage {
    
    // ==================== LOCATORS ====================
    
    // Dashboard Elements
    private final By welcomeMessage = By.id("welcome-message");
    private final By logoutButton = By.id("logout-button");
    private final By dashboardContainer = By.className("card-gradient");
    
    // Success Message
    private final By successMessage = By.xpath("//div[contains(@class, 'bg-green-50')]");
    
    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor initializes the DashboardPage with WebDriver
     * 
     * @param driver WebDriver instance
     */
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    // ==================== VALIDATION METHODS ====================
    
    /**
     * Checks if user is on dashboard page
     * 
     * @return true if on dashboard, false otherwise
     */
    public boolean isOnDashboard() {
        return isElementDisplayed(welcomeMessage) && 
               isElementDisplayed(logoutButton);
    }
    
    /**
     * Gets the welcome message text
     * 
     * @return Welcome message text
     */
    public String getWelcomeMessage() {
        return getElementText(welcomeMessage);
    }
    
    /**
     * Checks if welcome message contains specific text
     * 
     * @param expectedText Text to check for
     * @return true if text is found, false otherwise
     */
    public boolean welcomeMessageContains(String expectedText) {
        String actualMessage = getWelcomeMessage();
        return actualMessage.contains(expectedText);
    }
    
    /**
     * Checks if logout button is visible
     * 
     * @return true if logout button is visible, false otherwise
     */
    public boolean isLogoutButtonVisible() {
        return isElementDisplayed(logoutButton);
    }
    
    /**
     * Checks if success message is displayed
     * 
     * @return true if success message is visible, false otherwise
     */
    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(successMessage);
    }
    
    // ==================== ACTIONS ====================
    
    /**
     * Clicks the logout button
     */
    public void clickLogout() {
        clickElement(logoutButton);
    }
    
    /**
     * Waits for dashboard to load completely
     * 
     * @return true if dashboard loaded successfully, false otherwise
     */
    public boolean waitForDashboardToLoad() {
        try {
            waitForElementVisible(welcomeMessage);
            waitForElementVisible(logoutButton);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
