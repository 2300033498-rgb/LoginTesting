Feature: Password Field Security

  Scenario: Password field should be masked
    Given the user is on the login page
    When the user enters password "ValidPass123"
    Then the password should appear masked
