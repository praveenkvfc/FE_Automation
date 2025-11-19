Feature: Create a new user account and elect pick up store in cart successfully

  As a new user
  I want to create an account with valid information
  and I am able to select pick up store in cart

  Background:
    Given the user is on the "Create an Account" page

  @Vans_Smoke1
  Scenario: Account creation and select pick up store in cart Successfully
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
    And user selects Sort option in PLP page
    And user select "low_to_high" sort option
    Then Products should be sorted in ascending price order

    When User navigates to PDP page by selecting a product
    And User adds product to cart
    Then User should click the "View Cart" CTA in Mini Cart

    When User navigates to the Cart page
    Then User selects Pick up Instore option
    And User able to find and select the store
