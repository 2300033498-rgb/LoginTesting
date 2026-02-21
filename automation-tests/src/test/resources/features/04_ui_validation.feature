Feature: UI Validation Testing
  As a user
  I want to see proper UI feedback
  So that I know the status of my actions

  Background:
    Given the user is on the login page

  @ui @error-messages
  Scenario: Verify error message appears for invalid login
    When the user enters username "wronguser"
    And the user enters password "wrongpass"
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should be visible

  @ui @loading-state
  Scenario: Verify loading state during login
    When the user enters username "admin"
    And the user enters password "admin123"
    And the user clicks the login button
    Then a loading indicator should be displayed
    And the login button should show "Logging in..." text

  @ui @field-validation
  Scenario: Verify real-time username validation
    When the user enters username ""
    And the user moves focus away from username field
    Then a validation error should be displayed for username field

  @ui @field-validation
  Scenario: Verify real-time password validation
    When the user enters password "12345"
    And the user moves focus away from password field
    Then a validation error should be displayed for password field

  @ui @button-state
  Scenario: Verify login button initial state
    Then the login button should be disabled
    And the login button should have reduced opacity

  @ui @button-state
  Scenario: Verify login button enabled state
    When the user enters username "admin"
    And the user enters password "admin123"
    Then the login button should be enabled
    And the login button should be clickable

  @ui @visual-feedback
  Scenario: Verify error animation on failed login
    When the user enters username "wronguser"
    And the user enters password "wrongpass"
    And the user clicks the login button
    Then the login card should shake
    And an error message should appear with animation

  @ui @accessibility
  Scenario: Verify form fields have proper labels
    Then the username field should have a label
    And the password field should have a label
    And the login button should have accessible text

  @ui @accessibility
  Scenario: Verify form fields have aria labels
    Then the username field should have aria-label attribute
    And the password field should have aria-label attribute
    And the login button should have aria-label attribute

  @ui @placeholder
  Scenario: Verify placeholder text in fields
    Then the username field should have placeholder "Enter username or email"
    And the password field should have placeholder "Enter your password"

  @ui @icons
  Scenario: Verify input field icons are displayed
    Then the username field should display a user icon
    And the password field should display a lock icon

  @ui @responsive
  Scenario: Verify error message clears on new input
    When the user enters username "wronguser"
    And the user enters password "wrongpass"
    And the user clicks the login button
    Then an error message should be displayed
    When the user enters username "admin"
    Then the error message should be cleared

  @ui @success-redirect
  Scenario: Verify successful login redirects correctly
    When the user enters username "admin"
    And the user enters password "admin123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
    And the login page should not be visible
    And a logout button should be visible
