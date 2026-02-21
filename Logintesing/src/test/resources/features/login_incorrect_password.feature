Feature: Login with Incorrect Password

  Scenario: Login with incorrect password
    Given the user is on the login page
    When the user enters username "validUser"
    And the user enters password "wrongPass"
    And the user clicks the login button
    Then an error message should be displayed
