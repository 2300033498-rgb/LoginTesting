Feature: Boundary Value Analysis Testing
  As a QA Engineer
  I want to test boundary conditions of login fields
  So that I can ensure proper validation

  Background:
    Given the user is on the login page

  @boundary @negative
  Scenario: Login with empty username
    When the user enters username ""
    And the user enters password "admin123"
    And the user attempts to click the login button
    Then the login button should be disabled
    And a validation error should be displayed for username field
    And the error message should contain "Username or email is required"

  @boundary @negative
  Scenario: Login with empty password
    When the user enters username "admin"
    And the user enters password ""
    And the user attempts to click the login button
    Then the login button should be disabled
    And a validation error should be displayed for password field
    And the error message should contain "Password is required"

  @boundary @negative
  Scenario: Login with both fields empty
    When the user enters username ""
    And the user enters password ""
    Then the login button should be disabled

  @boundary @negative
  Scenario: Login with password below minimum length (5 characters)
    When the user enters username "admin"
    And the user enters password "admin"
    Then a validation error should be displayed for password field
    And the error message should contain "Password must be at least 6 characters"
    And the login button should be disabled

  @boundary @positive
  Scenario: Login with password at minimum length (6 characters)
    When the user enters username "admin"
    And the user enters password "admin1"
    Then the login button should be enabled

  @boundary @negative
  Scenario: Login with username exceeding maximum length (256 characters)
    When the user enters a username with 256 characters
    And the user enters password "admin123"
    Then a validation error should be displayed for username field
    And the error message should contain "must not exceed 255 characters"

  @boundary @positive
  Scenario: Login with username at maximum length (255 characters)
    When the user enters a username with 255 characters
    And the user enters password "admin123"
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should contain "Invalid username or password"

  @boundary @negative
  Scenario: Login with password exceeding maximum length (129 characters)
    When the user enters username "admin"
    And the user enters a password with 129 characters
    Then a validation error should be displayed for password field
    And the error message should contain "must not exceed 128 characters"

  @boundary @positive
  Scenario: Login with password at maximum length (128 characters)
    When the user enters username "admin"
    And the user enters a password with 128 characters
    Then the login button should be enabled

  @boundary @negative
  Scenario: Login with very long username (1000 characters)
    When the user enters a username with 1000 characters
    And the user enters password "admin123"
    Then a validation error should be displayed for username field

  @boundary @special
  Scenario: Login with special characters in username
    When the user enters username "admin@test.com"
    And the user enters password "admin123"
    And the user clicks the login button
    Then an error message should be displayed

  @boundary @special
  Scenario: Login with spaces in username
    When the user enters username "admin user"
    And the user enters password "admin123"
    And the user clicks the login button
    Then an error message should be displayed

  @boundary @special
  Scenario: Login with leading and trailing spaces
    When the user enters username "  admin  "
    And the user enters password "admin123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
