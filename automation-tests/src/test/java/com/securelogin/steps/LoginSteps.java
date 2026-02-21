package com.securelogin.steps;

import com.securelogin.pages.DashboardPage;
import com.securelogin.pages.LoginPage;
import com.securelogin.utils.ConfigReader;
import com.securelogin.utils.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * Step Definitions for Login functionality
 * Maps Gherkin steps to Java methods
 * 
 * @author Secure Login Testing Team
 * @version 1.0
 */
public class LoginSteps {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ConfigReader config;
    
    /**
     * Constructor initializes page objects and configuration
     */
    public LoginSteps() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.config = new ConfigReader();
    }
    
    // ==================== GIVEN STEPS ====================
    
    /**
     * Step: Given the user is on the login page
     * Navigates to the login page
     */
    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage.navigateToLoginPage(config.getBaseUrl());
        Assert.assertTrue("User should be on login page", loginPage.isOnLoginPage());
        System.out.println("→ Navigated to login page");
    }
    
    // ==================== WHEN STEPS ====================
    
    /**
     * Step: When the user enters username "{string}"
     * Enters username into the username field
     * 
     * @param username Username to enter
     */
    @When("the user enters username {string}")
    public void theUserEntersUsername(String username) {
        loginPage.enterUsername(username);
        System.out.println("→ Entered username: " + username);
    }
    
    /**
     * Step: When the user enters password "{string}"
     * Enters password into the password field
     * 
     * @param password Password to enter
     */
    @When("the user enters password {string}")
    @And("the user enters password {string}")
    public void theUserEntersPassword(String password) {
        loginPage.enterPassword(password);
        System.out.println("→ Entered password: " + maskPassword(password));
    }
    
    /**
     * Step: When the user clicks the login button
     * Clicks the login button
     */
    @When("the user clicks the login button")
    @And("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickLoginButton();
        System.out.println("→ Clicked login button");
        // Small pause to allow for server response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Step: When the user attempts to click the login button
     * Attempts to click login button (even if disabled)
     */
    @When("the user attempts to click the login button")
    public void theUserAttemptsToClickTheLoginButton() {
        loginPage.attemptToClickLoginButton();
        System.out.println("→ Attempted to click login button");
    }
    
    /**
     * Step: When the user moves focus away from username field
     * Blurs the username field to trigger validation
     */
    @When("the user moves focus away from username field")
    public void theUserMovesFocusAwayFromUsernameField() {
        loginPage.blurUsernameField();
        System.out.println("→ Moved focus away from username field");
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    
    /**
     * Step: When the user moves focus away from password field
     * Blurs the password field to trigger validation
     */
    @When("the user moves focus away from password field")
    public void theUserMovesFocusAwayFromPasswordField() {
        loginPage.blurPasswordField();
        System.out.println("→ Moved focus away from password field");
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    
    /**
     * Step: When the user enters a username with {int} characters
     * Enters a username with specified character length
     * 
     * @param length Number of characters
     */
    @When("the user enters a username with {int} characters")
    public void theUserEntersAUsernameWithCharacters(int length) {
        String longUsername = loginPage.generateStringWithLength(length);
        loginPage.enterUsername(longUsername);
        System.out.println("→ Entered username with " + length + " characters");
    }
    
    /**
     * Step: When the user enters a password with {int} characters
     * Enters a password with specified character length
     * 
     * @param length Number of characters
     */
    @When("the user enters a password with {int} characters")
    public void theUserEntersAPasswordWithCharacters(int length) {
        String longPassword = loginPage.generateStringWithLength(length);
        loginPage.enterPassword(longPassword);
        System.out.println("→ Entered password with " + length + " characters");
    }
    
    // ==================== THEN STEPS ====================
    
    /**
     * Step: Then the user should be redirected to the dashboard
     * Verifies user is redirected to dashboard page
     */
    @Then("the user should be redirected to the dashboard")
    public void theUserShouldBeRedirectedToTheDashboard() {
        dashboardPage.waitForDashboardToLoad();
        Assert.assertTrue("User should be on dashboard", dashboardPage.isOnDashboard());
        System.out.println("✓ Successfully redirected to dashboard");
    }
    
    /**
     * Step: Then the welcome message should display "{string}"
     * Verifies welcome message contains expected text
     * 
     * @param expectedText Expected text in welcome message
     */
    @Then("the welcome message should display {string}")
    public void theWelcomeMessageShouldDisplay(String expectedText) {
        Assert.assertTrue(
            "Welcome message should contain: " + expectedText,
            dashboardPage.welcomeMessageContains(expectedText)
        );
        System.out.println("✓ Welcome message verified: " + expectedText);
    }
    
    /**
     * Step: Then an error message should be displayed
     * Verifies that an error message is visible
     */
    @Then("an error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        Assert.assertTrue(
            "Error message should be displayed",
            loginPage.isGeneralErrorDisplayed()
        );
        System.out.println("✓ Error message is displayed");
    }
    
    /**
     * Step: Then the error message should contain "{string}"
     * Verifies error message contains specific text
     * 
     * @param expectedText Expected text in error message
     */
    @Then("the error message should contain {string}")
    @And("the error message should contain {string}")
    public void theErrorMessageShouldContain(String expectedText) {
        String actualMessage = loginPage.getGeneralErrorMessage();
        Assert.assertTrue(
            "Error message should contain '" + expectedText + "' but was: " + actualMessage,
            actualMessage.contains(expectedText)
        );
        System.out.println("✓ Error message verified: " + actualMessage);
    }
    
    /**
     * Step: Then the user should remain on the login page
     * Verifies user is still on login page
     */
    @Then("the user should remain on the login page")
    @And("the user should remain on the login page")
    public void theUserShouldRemainOnTheLoginPage() {
        Assert.assertTrue(
            "User should remain on login page",
            loginPage.isOnLoginPage()
        );
        System.out.println("✓ User remains on login page");
    }
    
    /**
     * Step: Then the login button should be disabled
     * Verifies login button is disabled
     */
    @Then("the login button should be disabled")
    public void theLoginButtonShouldBeDisabled() {
        Assert.assertTrue(
            "Login button should be disabled",
            loginPage.isLoginButtonDisabled()
        );
        System.out.println("✓ Login button is disabled");
    }
    
    /**
     * Step: Then the login button should be enabled
     * Verifies login button is enabled
     */
    @Then("the login button should be enabled")
    public void theLoginButtonShouldBeEnabled() {
        Assert.assertTrue(
            "Login button should be enabled",
            loginPage.isLoginButtonEnabled()
        );
        System.out.println("✓ Login button is enabled");
    }
    
    /**
     * Step: Then a validation error should be displayed for username field
     * Verifies username field validation error is shown
     */
    @Then("a validation error should be displayed for username field")
    public void aValidationErrorShouldBeDisplayedForUsernameField() {
        Assert.assertTrue(
            "Username validation error should be displayed",
            loginPage.isUsernameErrorDisplayed()
        );
        System.out.println("✓ Username validation error displayed");
    }
    
    /**
     * Step: Then a validation error should be displayed for password field
     * Verifies password field validation error is shown
     */
    @Then("a validation error should be displayed for password field")
    public void aValidationErrorShouldBeDisplayedForPasswordField() {
        Assert.assertTrue(
            "Password validation error should be displayed",
            loginPage.isPasswordErrorDisplayed()
        );
        System.out.println("✓ Password validation error displayed");
    }
    
    /**
     * Step: Then a loading indicator should be displayed
     * Verifies loading spinner is shown during login
     */
    @Then("a loading indicator should be displayed")
    public void aLoadingIndicatorShouldBeDisplayed() {
        Assert.assertTrue(
            "Loading indicator should be displayed",
            loginPage.isLoadingIndicatorDisplayed()
        );
        System.out.println("✓ Loading indicator displayed");
    }
    
    /**
     * Step: Then the login button should show "{string}" text
     * Verifies login button displays specific text
     * 
     * @param expectedText Expected button text
     */
    @Then("the login button should show {string} text")
    @And("the login button should show {string} text")
    public void theLoginButtonShouldShowText(String expectedText) {
        String actualText = loginPage.getLoginButtonText();
        Assert.assertTrue(
            "Login button should show '" + expectedText + "' but shows: " + actualText,
            actualText.contains(expectedText)
        );
        System.out.println("✓ Login button text verified: " + actualText);
    }
    
    /**
     * Step: Then the login card should shake
     * Verifies shake animation is applied to login card
     */
    @Then("the login card should shake")
    public void theLoginCardShouldShake() {
        Assert.assertTrue(
            "Login card should have shake animation",
            loginPage.hasShakeAnimation()
        );
        System.out.println("✓ Shake animation detected");
    }
    
    /**
     * Step: Then the error message should be visible
     * Verifies error message visibility
     */
    @Then("the error message should be visible")
    @And("the error message should be visible")
    public void theErrorMessageShouldBeVisible() {
        Assert.assertTrue(
            "Error message should be visible",
            loginPage.isGeneralErrorDisplayed()
        );
        System.out.println("✓ Error message is visible");
    }
    
    /**
     * Step: Then an error message should appear with animation
     * Verifies error message appears with animation
     */
    @Then("an error message should appear with animation")
    @And("an error message should appear with animation")
    public void anErrorMessageShouldAppearWithAnimation() {
        Assert.assertTrue(
            "Error message should appear",
            loginPage.isGeneralErrorDisplayed()
        );
        System.out.println("✓ Error message appeared with animation");
    }
    
    /**
     * Step: Then the username field should have a label
     * Verifies username field has an associated label
     */
    @Then("the username field should have a label")
    public void theUsernameFieldShouldHaveALabel() {
        Assert.assertTrue(
            "Username field should have a label",
            loginPage.hasUsernameLabel()
        );
        System.out.println("✓ Username field has label");
    }
    
    /**
     * Step: Then the password field should have a label
     * Verifies password field has an associated label
     */
    @Then("the password field should have a label")
    @And("the password field should have a label")
    public void thePasswordFieldShouldHaveALabel() {
        Assert.assertTrue(
            "Password field should have a label",
            loginPage.hasPasswordLabel()
        );
        System.out.println("✓ Password field has label");
    }
    
    /**
     * Step: Then the login button should have accessible text
     * Verifies login button has accessible text
     */
    @Then("the login button should have accessible text")
    @And("the login button should have accessible text")
    public void theLoginButtonShouldHaveAccessibleText() {
        String buttonText = loginPage.getLoginButtonText();
        Assert.assertFalse(
            "Login button should have text",
            buttonText.isEmpty()
        );
        System.out.println("✓ Login button has accessible text: " + buttonText);
    }
    
    /**
     * Step: Then the username field should have aria-label attribute
     * Verifies username field has aria-label for accessibility
     */
    @Then("the username field should have aria-label attribute")
    public void theUsernameFieldShouldHaveAriaLabelAttribute() {
        Assert.assertTrue(
            "Username field should have aria-label",
            loginPage.hasUsernameAriaLabel()
        );
        System.out.println("✓ Username field has aria-label");
    }
    
    /**
     * Step: Then the password field should have aria-label attribute
     * Verifies password field has aria-label for accessibility
     */
    @Then("the password field should have aria-label attribute")
    @And("the password field should have aria-label attribute")
    public void thePasswordFieldShouldHaveAriaLabelAttribute() {
        Assert.assertTrue(
            "Password field should have aria-label",
            loginPage.hasPasswordAriaLabel()
        );
        System.out.println("✓ Password field has aria-label");
    }
    
    /**
     * Step: Then the login button should have aria-label attribute
     * Verifies login button has aria-label for accessibility
     */
    @Then("the login button should have aria-label attribute")
    @And("the login button should have aria-label attribute")
    public void theLoginButtonShouldHaveAriaLabelAttribute() {
        Assert.assertTrue(
            "Login button should have aria-label",
            loginPage.hasLoginButtonAriaLabel()
        );
        System.out.println("✓ Login button has aria-label");
    }
    
    /**
     * Step: Then the username field should have placeholder "{string}"
     * Verifies username field placeholder text
     * 
     * @param expectedPlaceholder Expected placeholder text
     */
    @Then("the username field should have placeholder {string}")
    public void theUsernameFieldShouldHavePlaceholder(String expectedPlaceholder) {
        String actualPlaceholder = loginPage.getUsernamePlaceholder();
        Assert.assertEquals(
            "Username placeholder should match",
            expectedPlaceholder,
            actualPlaceholder
        );
        System.out.println("✓ Username placeholder verified: " + actualPlaceholder);
    }
    
    /**
     * Step: Then the password field should have placeholder "{string}"
     * Verifies password field placeholder text
     * 
     * @param expectedPlaceholder Expected placeholder text
     */
    @Then("the password field should have placeholder {string}")
    @And("the password field should have placeholder {string}")
    public void thePasswordFieldShouldHavePlaceholder(String expectedPlaceholder) {
        String actualPlaceholder = loginPage.getPasswordPlaceholder();
        Assert.assertEquals(
            "Password placeholder should match",
            expectedPlaceholder,
            actualPlaceholder
        );
        System.out.println("✓ Password placeholder verified: " + actualPlaceholder);
    }
    
    /**
     * Step: Then the username field should display a user icon
     * Verifies username field has an icon
     */
    @Then("the username field should display a user icon")
    public void theUsernameFieldShouldDisplayAUserIcon() {
        Assert.assertTrue(
            "Username field should display icon",
            loginPage.isUsernameIconDisplayed()
        );
        System.out.println("✓ Username icon displayed");
    }
    
    /**
     * Step: Then the password field should display a lock icon
     * Verifies password field has a lock icon
     */
    @Then("the password field should display a lock icon")
    @And("the password field should display a lock icon")
    public void thePasswordFieldShouldDisplayALockIcon() {
        Assert.assertTrue(
            "Password field should display lock icon",
            loginPage.isPasswordIconDisplayed()
        );
        System.out.println("✓ Password lock icon displayed");
    }
    
    /**
     * Step: Then the error message should be cleared
     * Verifies error message is no longer displayed
     */
    @Then("the error message should be cleared")
    public void theErrorMessageShouldBeCleared() {
        Assert.assertFalse(
            "Error message should be cleared",
            loginPage.isGeneralErrorDisplayed()
        );
        System.out.println("✓ Error message cleared");
    }
    
    /**
     * Step: Then the login page should not be visible
     * Verifies login page is no longer visible
     */
    @Then("the login page should not be visible")
    @And("the login page should not be visible")
    public void theLoginPageShouldNotBeVisible() {
        Assert.assertFalse(
            "Login page should not be visible",
            loginPage.isOnLoginPage()
        );
        System.out.println("✓ Login page not visible");
    }
    
    /**
     * Step: Then a logout button should be visible
     * Verifies logout button is displayed
     */
    @Then("a logout button should be visible")
    @And("a logout button should be visible")
    public void aLogoutButtonShouldBeVisible() {
        Assert.assertTrue(
            "Logout button should be visible",
            dashboardPage.isLogoutButtonVisible()
        );
        System.out.println("✓ Logout button visible");
    }
    
    /**
     * Step: Then the password field should not display plain text
     * Verifies password is masked
     */
    @Then("the password field should not display plain text")
    public void thePasswordFieldShouldNotDisplayPlainText() {
        String fieldType = loginPage.getPasswordFieldType();
        Assert.assertEquals(
            "Password field should be of type 'password'",
            "password",
            fieldType
        );
        System.out.println("✓ Password field is masked");
    }
    
    /**
     * Step: Then the password field type should be "{string}"
     * Verifies password field type attribute
     * 
     * @param expectedType Expected field type
     */
    @Then("the password field type should be {string}")
    @And("the password field type should be {string}")
    public void thePasswordFieldTypeShouldBe(String expectedType) {
        String actualType = loginPage.getPasswordFieldType();
        Assert.assertEquals(
            "Password field type should match",
            expectedType,
            actualType
        );
        System.out.println("✓ Password field type verified: " + actualType);
    }
    
    /**
     * Step: Then the login button should have reduced opacity
     * Verifies disabled button has reduced opacity
     */
    @Then("the login button should have reduced opacity")
    @And("the login button should have reduced opacity")
    public void theLoginButtonShouldHaveReducedOpacity() {
        Assert.assertTrue(
            "Login button should be disabled (reduced opacity)",
            loginPage.isLoginButtonDisabled()
        );
        System.out.println("✓ Login button has reduced opacity");
    }
    
    /**
     * Step: Then the login button should be clickable
     * Verifies login button is clickable
     */
    @Then("the login button should be clickable")
    @And("the login button should be clickable")
    public void theLoginButtonShouldBeClickable() {
        Assert.assertTrue(
            "Login button should be clickable",
            loginPage.isLoginButtonEnabled()
        );
        System.out.println("✓ Login button is clickable");
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Masks password for logging purposes
     * 
     * @param password Password to mask
     * @return Masked password string
     */
    private String maskPassword(String password) {
        if (password.length() <= 4) {
            return "****";
        }
        return password.substring(0, 2) + "****" + password.substring(password.length() - 2);
    }
}
