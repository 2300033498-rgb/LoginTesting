package com.securelogin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for Login Page
 * Contains all locators and methods related to login functionality
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
public class LoginPage extends BasePage {
    
    // ==================== LOCATORS ====================
    
    // Input Fields
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    
    // Buttons
    private final By loginButton = By.id("login-button");
    
    // Error Messages
    private final By generalErrorMessage = By.id("error-message");
    private final By usernameErrorMessage = By.id("username-error");
    private final By passwordErrorMessage = By.id("password-error");
    
    // UI Elements
    private final By loginCard = By.className("card-gradient");
    private final By usernameIcon = By.xpath("//input[@id='username']/preceding-sibling::div//svg");
    private final By passwordIcon = By.xpath("//input[@id='password']/preceding-sibling::div//svg");
    
    // Loading State
    private final By loadingSpinner = By.xpath("//button[@id='login-button']//svg[contains(@class, 'animate-spin')]");
    
    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor initializes the LoginPage with WebDriver
     * 
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // ==================== NAVIGATION ====================
    
    /**
     * Navigates to the login page
     * 
     * @param url Base URL of the application
     */
    public void navigateToLoginPage(String url) {
        driver.get(url);
        waitForPageLoad();
    }
    
    /**
     * Checks if user is on login page
     * 
     * @return true if on login page, false otherwise
     */
    public boolean isOnLoginPage() {
        return isElementDisplayed(loginCard) && 
               isElementDisplayed(usernameField) && 
               isElementDisplayed(passwordField);
    }
    
    // ==================== INPUT ACTIONS ====================
    
    /**
     * Enters username in the username field
     * 
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        clearAndType(usernameField, username);
    }
    
    /**
     * Enters password in the password field
     * 
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        clearAndType(passwordField, password);
    }
    
    /**
     * Clears the username field
     */
    public void clearUsername() {
        waitForElementVisible(usernameField).clear();
    }
    
    /**
     * Clears the password field
     */
    public void clearPassword() {
        waitForElementVisible(passwordField).clear();
    }
    
    /**
     * Moves focus away from username field
     */
    public void blurUsernameField() {
        clickElement(passwordField);
    }
    
    /**
     * Moves focus away from password field
     */
    public void blurPasswordField() {
        clickElement(loginButton);
    }
    
    // ==================== BUTTON ACTIONS ====================
    
    /**
     * Clicks the login button
     */
    public void clickLoginButton() {
        clickElement(loginButton);
    }
    
    /**
     * Attempts to click login button (even if disabled)
     */
    public void attemptToClickLoginButton() {
        try {
            driver.findElement(loginButton).click();
        } catch (Exception e) {
            // Button is disabled, which is expected behavior
        }
    }
    
    // ==================== VALIDATION METHODS ====================
    
    /**
     * Checks if login button is enabled
     * 
     * @return true if button is enabled, false otherwise
     */
    public boolean isLoginButtonEnabled() {
        return isElementEnabled(loginButton);
    }
    
    /**
     * Checks if login button is disabled
     * 
     * @return true if button is disabled, false otherwise
     */
    public boolean isLoginButtonDisabled() {
        return !isElementEnabled(loginButton);
    }
    
    /**
     * Checks if general error message is displayed
     * 
     * @return true if error message is visible, false otherwise
     */
    public boolean isGeneralErrorDisplayed() {
        return isElementDisplayed(generalErrorMessage);
    }
    
    /**
     * Gets the general error message text
     * 
     * @return Error message text
     */
    public String getGeneralErrorMessage() {
        return getElementText(generalErrorMessage);
    }
    
    /**
     * Checks if username validation error is displayed
     * 
     * @return true if username error is visible, false otherwise
     */
    public boolean isUsernameErrorDisplayed() {
        return isElementDisplayed(usernameErrorMessage);
    }
    
    /**
     * Gets the username validation error text
     * 
     * @return Username error message text
     */
    public String getUsernameErrorMessage() {
        return getElementText(usernameErrorMessage);
    }
    
    /**
     * Checks if password validation error is displayed
     * 
     * @return true if password error is visible, false otherwise
     */
    public boolean isPasswordErrorDisplayed() {
        return isElementDisplayed(passwordErrorMessage);
    }
    
    /**
     * Gets the password validation error text
     * 
     * @return Password error message text
     */
    public String getPasswordErrorMessage() {
        return getElementText(passwordErrorMessage);
    }
    
    /**
     * Checks if loading indicator is displayed
     * 
     * @return true if loading spinner is visible, false otherwise
     */
    public boolean isLoadingIndicatorDisplayed() {
        return isElementDisplayed(loadingSpinner);
    }
    
    /**
     * Gets the login button text
     * 
     * @return Button text
     */
    public String getLoginButtonText() {
        return getElementText(loginButton);
    }
    
    // ==================== UI ELEMENT CHECKS ====================
    
    /**
     * Checks if username field has a label
     * 
     * @return true if label exists, false otherwise
     */
    public boolean hasUsernameLabel() {
        By usernameLabel = By.xpath("//label[@for='username']");
        return isElementDisplayed(usernameLabel);
    }
    
    /**
     * Checks if password field has a label
     * 
     * @return true if label exists, false otherwise
     */
    public boolean hasPasswordLabel() {
        By passwordLabel = By.xpath("//label[@for='password']");
        return isElementDisplayed(passwordLabel);
    }
    
    /**
     * Gets placeholder text from username field
     * 
     * @return Placeholder text
     */
    public String getUsernamePlaceholder() {
        return getElementAttribute(usernameField, "placeholder");
    }
    
    /**
     * Gets placeholder text from password field
     * 
     * @return Placeholder text
     */
    public String getPasswordPlaceholder() {
        return getElementAttribute(passwordField, "placeholder");
    }
    
    /**
     * Gets type attribute of password field
     * 
     * @return Type attribute value
     */
    public String getPasswordFieldType() {
        return getElementAttribute(passwordField, "type");
    }
    
    /**
     * Checks if username field has aria-label
     * 
     * @return true if aria-label exists, false otherwise
     */
    public boolean hasUsernameAriaLabel() {
        String ariaLabel = getElementAttribute(usernameField, "aria-label");
        return ariaLabel != null && !ariaLabel.isEmpty();
    }
    
    /**
     * Checks if password field has aria-label
     * 
     * @return true if aria-label exists, false otherwise
     */
    public boolean hasPasswordAriaLabel() {
        String ariaLabel = getElementAttribute(passwordField, "aria-label");
        return ariaLabel != null && !ariaLabel.isEmpty();
    }
    
    /**
     * Checks if login button has aria-label
     * 
     * @return true if aria-label exists, false otherwise
     */
    public boolean hasLoginButtonAriaLabel() {
        String ariaLabel = getElementAttribute(loginButton, "aria-label");
        return ariaLabel != null && !ariaLabel.isEmpty();
    }
    
    /**
     * Checks if username icon is displayed
     * 
     * @return true if icon is visible, false otherwise
     */
    public boolean isUsernameIconDisplayed() {
        return isElementDisplayed(usernameIcon);
    }
    
    /**
     * Checks if password icon is displayed
     * 
     * @return true if icon is visible, false otherwise
     */
    public boolean isPasswordIconDisplayed() {
        return isElementDisplayed(passwordIcon);
    }
    
    /**
     * Checks if login card has shake animation
     * 
     * @return true if shake class is present, false otherwise
     */
    public boolean hasShakeAnimation() {
        return hasClass(loginCard, "error-shake");
    }
    
    // ==================== COMPLETE LOGIN ACTION ====================
    
    /**
     * Performs complete login action with username and password
     * 
     * @param username Username to login with
     * @param password Password to login with
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Generates a string with specified number of characters
     * Helper method for boundary testing
     * 
     * @param length Number of characters
     * @return String with specified length
     */
    public String generateStringWithLength(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("a");
        }
        return sb.toString();
    }
}
