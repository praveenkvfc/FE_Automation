Feature: Create a new user account

  As a new user
  I want to register with valid information
  So that I can create an account successfully

  Background:
    Given the user is on the "Create an Account" page

  @Tbl_US_Smoke
  Scenario: Successful account creation with valid details
    When the user enters the all required personal details
    And the user agrees to receive text messages about orders and Timberland offers
    And the user provides the email and password
    And the user accepts the latest Timberland offers
    And the user accepts the Community Terms of Service
    And the user accepts Timberland's Terms and Conditions
    And the user clicks the "Create an Account" button
    Then account created successfully with confirmation message "Your account has been created!"