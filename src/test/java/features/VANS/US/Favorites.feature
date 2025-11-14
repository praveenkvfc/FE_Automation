Feature: Create a new user account and add item to favorites successfully

  As a new user
  I want to create an account with valid information
  and I am able to add item to favorites

  Background:
    Given the user is on the "Create an Account" page

  @Vans_DivyaFav
  Scenario: Account creation and add item to favorites Successfully
    When the user enters the email for vans
    And the user provides the password for vans to Signup or SignIn
    And the user agrees Vans Terms and Conditions
    And the user agree privacy policy
    And the user agrees to receive mails
    And the user clicks the Create an Account button
    Then account created successfully with confirmation message "Welcome to the Vans Family"

    When User navigates to the MyAccount page as "registeredUser"
    And user closes the my account window
    Given user clicks on search button
    Then enter the text "shoe" in search field

    When User navigates to PDP page by selecting a product
    And User clicks on favorite icon and navigates to favorites page
    Then User should be able to see the item in my favorites page

  @Vans_DivyaRemoveFav
  Scenario: Account creation and add item to favorites Successfully
    When the user enters the email for vans
    And the user provides the password for vans to Signup or SignIn
    And the user agrees Vans Terms and Conditions
    And the user agree privacy policy
    And the user agrees to receive mails
    And the user clicks the Create an Account button
    Then account created successfully with confirmation message "Welcome to the Vans Family"

    When User navigates to the MyAccount page as "registeredUser"
    And user closes the my account window
    Given user clicks on search button
    Then enter the text "shoe" in search field

    When User navigates to PDP page by selecting a product
    And User clicks on favorite icon and navigates to favorites page
    Then User should be able to see the item in my favorites page
    And User should be able to remove the item from favorites page
