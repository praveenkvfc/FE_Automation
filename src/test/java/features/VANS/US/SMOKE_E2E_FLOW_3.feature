Feature: Create a new user account and place order successfully
  As a new user
  I want to create an account with valid information
  and I am able to place order successfully from cart page

  Background:
    Given the user is on the "Create an Account" page

  @Vans_SmokePaypal2
  Scenario: Account creation and place order using paypal from cart page Successfully

    When User navigates to the PLP for "MENS FOOTWEAR" category
    And User navigates to PDP page by selecting "multiple" product
    Then User should click the "View Cart" CTA in Mini Cart
    Then User navigates to "Save for later" page from cart page
    And User clicks on Add to Cart button from Save for Later page for "multiple" product
    Then User places the order by clicking pay now using paypal in cart page
    And Order confirmation should display
#    And User should be able to see the order in Order history page

