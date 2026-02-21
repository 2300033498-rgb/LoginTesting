Feature: Login Functionality Testing
  As a user
  I want to login to the application
  So that I can access my dashboard

  Background:
    Given the user is on the login page

  @functional @positive
  Scenario: Successful login with valid credentials
    When the user enters username "admin"
    And the user enters password "admin123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
    And the welcome message should display "Welcome back, admin!"

  @functional @negative
  Scenario: Login with invalid username
    When the user enters username "invaliduser"
    And the user enters password "admin123"
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should contain "Invalid username or password"
    And the user should remain on the login page

  @functional @negative
  Scenario: Login with invalid password
    When the user enters username "admin"
    And the user enters password "wrongpassword"
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should contain "Invalid username or password"

  @functional @negative
  Scenario: Login with non-existing user
    When the user enters username "nonexistentuser123"
    And the user enters password "password123"
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should contain "Invalid username or password"

  @functional @ui
  Scenario: Login button disabled state with empty fields
    Then the login button should be disabled
    When the user enters username "admin"
    Then the login button should be disabled
    When the user enters password "admin123"
    Then the login button should be enabled

  @functional @ui
  Scenario: Login with multiple valid users
    When the user enters username "testuser"
    And the user enters password "test1234"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
    And the welcome message should display "Welcome back, testuser!"
