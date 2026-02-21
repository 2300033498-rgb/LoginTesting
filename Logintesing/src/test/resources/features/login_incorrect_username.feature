Feature: Login with Incorrect Username

  Scenario: Login with incorrect username
    Given the user is on the login page
    When the user enters username "wrongUser"
    And the user enters password "ValidPass123"
    And the user clicks the login button
    Then an error message should be displayed
