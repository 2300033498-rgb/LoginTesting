Feature: Successful Login

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters username "validUser"
    And the user enters password "ValidPass123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
