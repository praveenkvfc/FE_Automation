Feature: Login using a existing user account

  As a existing user
  I want to Login an account with valid information

  Background:
    Given the user is on the "Create an Account" page

#  @Vans_Smoke @Vans_Regression
  Scenario: Successful account creation and place order successfully
    When the user enters the email for vans
    And the user provides the password for vans to Signup or SignIn
#    Then user loggedin successfully with confirmation message "You have successfully loggedin"