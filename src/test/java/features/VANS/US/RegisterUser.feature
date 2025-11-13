Feature: Create a new user account

  As a new user
  I want to create an account with valid information

  Background:
    Given the user is on the "Create an Account" page

#  @Vans_Smoke @Vans_Regression
  Scenario: Successful account creation and place order successfully
    When the user enters the email for vans
    And the user provides the password for vans to Signup or SignIn
    And the user agrees Vans Terms and Conditions
    And the user agree privacy policy
    And the user agrees to receive mails
    And the user clicks the Create an Account button
    Then account created successfully with confirmation message "Welcome to the Vans Family"