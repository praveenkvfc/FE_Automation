Feature: Create a new user account and create user profile successfully

  As a new user
  I want to create an account with valid information
  and I am able to create user profile successfully

  Background:
    Given the user is on the "Create an Account" page

  @Vans_Divya
  Scenario: Account creation and add item to favorites Successfully
    When the user enters the email for vans
    And the user provides the password for vans to Signup or SignIn
    And the user agrees Vans Terms and Conditions
    And the user agree privacy policy
    And the user agrees to receive mails
    And the user clicks the Create an Account button
    Then account created successfully with confirmation message "Welcome to the Vans Family"

#    When User navigates to the MyAccount page as "registeredUser"
#    And User opens the my profile page
#    And User adds basic information under profile
#    And User should be able to change the password
#    And User should be able to add Interests
#    And User shouild be able to add Newsletter Preferences
#    Then User clicks on Subscribe button
#

