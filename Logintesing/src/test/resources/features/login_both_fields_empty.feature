Feature: Login with Both Fields Empty

  Scenario: Login with both fields empty
    Given the user is on the login page
    When the user leaves both username and password fields empty
    And the user clicks the login button
    Then validation messages should be displayed
