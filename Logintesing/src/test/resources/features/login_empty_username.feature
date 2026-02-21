Feature: Login with Empty Username

  Scenario: Login with empty username
    Given the user is on the login page
    When the user leaves the username field empty
    And the user enters password "ValidPass123"
    And the user clicks the login button
    Then a validation message for username should be displayed
