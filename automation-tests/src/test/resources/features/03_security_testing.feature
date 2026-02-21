Feature: Security Testing
  As a security tester
  I want to ensure the login system is secure
  So that it prevents common security vulnerabilities

  Background:
    Given the user is on the login page

  @security @sql-injection
  Scenario: Attempt SQL injection in username field - OR statement
    When the user enters username "' OR '1'='1"
    And the user enters password "anything"
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should contain "Invalid"
    And the user should remain on the login page

  @security @sql-injection
  Scenario: Attempt SQL injection in password field
    When the user enters username "admin"
    And the user enters password "' OR '1'='1"
    And the user clicks the login button
    Then an error message should be displayed
    And the user should remain on the login page

  @security @sql-injection
  Scenario: Attempt SQL injection with comment characters
    When the user enters username "admin'--"
    And the user enters password "anything"
    And the user clicks the login button
    Then an error message should be displayed

  @security @sql-injection
  Scenario: Attempt SQL injection with UNION statement
    When the user enters username "admin' UNION SELECT * FROM users--"
    And the user enters password "password"
    And the user clicks the login button
    Then an error message should be displayed

  @security @xss
  Scenario: Attempt XSS attack in username field
    When the user enters username "<script>alert('XSS')</script>"
    And the user enters password "admin123"
    Then a validation error should be displayed for username field
    And the error message should contain "Invalid characters"

  @security @xss
  Scenario: Attempt XSS attack in password field
    When the user enters username "admin"
    And the user enters password "<script>alert('XSS')</script>"
    Then a validation error should be displayed for password field
    And the error message should contain "Invalid characters"

  @security @xss
  Scenario: Attempt XSS with image tag
    When the user enters username "<img src=x onerror=alert('XSS')>"
    And the user enters password "admin123"
    Then a validation error should be displayed for username field

  @security @xss
  Scenario: Attempt XSS with javascript protocol
    When the user enters username "javascript:alert('XSS')"
    And the user enters password "admin123"
    Then a validation error should be displayed for username field

  @security @special-chars
  Scenario: Test HTML special characters handling
    When the user enters username "<test>"
    And the user enters password "admin123"
    Then a validation error should be displayed for username field

  @security @rate-limiting
  Scenario: Multiple failed login attempts
    When the user enters username "admin"
    And the user enters password "wrongpassword1"
    And the user clicks the login button
    And the user enters username "admin"
    And the user enters password "wrongpassword2"
    And the user clicks the login button
    And the user enters username "admin"
    And the user enters password "wrongpassword3"
    And the user clicks the login button
    Then an error message should be displayed
    And the user should remain on the login page

  @security @sensitive-data
  Scenario: Verify password field is masked
    When the user enters password "admin123"
    Then the password field should not display plain text
    And the password field type should be "password"
