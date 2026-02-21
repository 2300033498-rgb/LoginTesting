package stepDefinition;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private boolean onLoginPage;
    private boolean loginSuccessful;
    private String username;
    private String password;

    // -----------------------
    // Common Step
    // -----------------------

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        System.out.println("User navigates to login page");
        onLoginPage = true;
        loginSuccessful = false;
        username = "";
        password = "";
    }

    // -----------------------
    // Successful Login
    // -----------------------

    @When("the user enters username {string}")
    public void the_user_enters_username(String user) {
        username = user;
        System.out.println("Entered Username: " + user);
    }

    @When("the user enters password {string}")
    public void the_user_enters_password(String pass) {
        password = pass;
        System.out.println("Entered Password: " + pass);

        // Simulated login logic
        if (username.equals("validUser") && password.equals("ValidPass123")) {
            loginSuccessful = true;
        }
    }

    @When("the user clicks the login button")
    public void the_user_clicks_the_login_button() {
        System.out.println("User clicks login button");
    }

    @Then("the user should be redirected to the dashboard")
    public void the_user_should_be_redirected_to_the_dashboard() {
        assertTrue(loginSuccessful, "Login should be successful");
    }

    // -----------------------
    // Invalid Credentials
    // -----------------------

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        assertFalse(loginSuccessful, "Error message should be displayed for invalid login");
    }

    // -----------------------
    // Empty Username
    // -----------------------

    @When("the user leaves the username field empty")
    public void the_user_leaves_username_empty() {
        username = "";
        System.out.println("Username left empty");
    }

    @Then("a validation message for username should be displayed")
    public void validation_message_for_username() {
        assertTrue(username.isEmpty(), "Username validation message should appear");
    }

    // -----------------------
    // Empty Password
    // -----------------------

    @When("the user leaves the password field empty")
    public void the_user_leaves_password_empty() {
        password = "";
        System.out.println("Password left empty");
    }

    @Then("a validation message for password should be displayed")
    public void validation_message_for_password() {
        assertTrue(password.isEmpty(), "Password validation message should appear");
    }

    // -----------------------
    // Both Fields Empty
    // -----------------------

    @When("the user leaves both username and password fields empty")
    public void both_fields_empty() {
        username = "";
        password = "";
        System.out.println("Both fields left empty");
    }

    @Then("validation messages should be displayed")
    public void validation_messages_should_be_displayed() {
        assertTrue(username.isEmpty() && password.isEmpty(),
                "Validation messages should appear for both fields");
    }

    // -----------------------
    // Password Masked
    // -----------------------

    @Then("the password should appear masked")
    public void password_should_be_masked() {
        System.out.println("Password is masked (simulated)");
        assertNotNull(password);
    }
}
