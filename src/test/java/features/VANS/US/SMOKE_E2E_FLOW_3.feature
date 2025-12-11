Feature: Create a new user account and place order successfully
  As a new user
  I want to create an account with valid information
  and I am able to place order successfully from cart page

  Background:
    Given the user is on the "Create an Account" page

  @Vans_SmokePaypal2
  Scenario: Account creation and place order using paypal from cart page Successfully
    When the user enters the email for vans
    And the user provides the password for vans to Signup or SignIn
    And the user agree privacy policy
    And the user agrees Vans Terms and Conditions
    And the user agrees to receive mails
    And the user clicks the Create an Account button
    Then account created successfully with confirmation message "Welcome to the Vans Family"
    When User navigates to the MyAccount page as "registeredUser"
    And User opens the Credit Cards page
    And User adds a default "VISA" credit card for "registeredUser"
    Then User should see a success message "Credit card added successfully!"
    When User navigates to the Address Book page
    Then Address Book should display correct details for "registeredUser"
#    When User opens the add "Shipping" address form
    When User opens the add "Shipping" address form
    And User fills "Shipping" address details for "registeredUser"
    And User saves the "Shipping" address
    When User navigates to the PLP for "MENS FOOTWEAR" category
    And User navigates to PDP page by selecting "a" product
    Then User should click the "View Cart" CTA in Mini Cart
    Then User navigates to "Save for later" page from cart page
    And User clicks on Add to Cart button from Save for Later page for "multiple" product
    Then User places the order by clicking pay now using paypal in cart page
    And Order confirmation should display
    And User should be able to see the order in Order history page


