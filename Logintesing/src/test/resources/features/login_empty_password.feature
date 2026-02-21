Feature: Login with Empty Password

  Scenario: Login with empty password
    Given the user is on the login page
    When the user enters username "validUser"
    And the user leaves the password field empty
    And the user clicks the login button
    Then a validation message for password should be displayed
