Feature: Place order successfully as a Guest User

  As a guest user
  I want to search and purchase a product
  so that I can place an order successfully without account creation

  Background:
    Given the user is on the "guest" page

  @Vans_Smoke_Guest
  Scenario Outline: Account creation and place order using "<paymentType>" Successfully

    Given user clicks on search button
    Then enter the text "bag" in search field
    When User navigates to PDP page by selecting a product
    And User adds "bag" product to cart
    Then User should click the "View Cart" CTA in Mini Cart
    When User navigates to the Cart page
    And User should be able to increase the quantity in cart page
    Then User proceeds to checkout
    Then user should be able to toggle the accordion section for payment method
    And User enters email for contact info in guestUser
    And User enters mandatory inputs for billing address
    When User applies a gift card
    When User selects the payment method using "<paymentType>"
    Then User enters CC details after selecting the "<paymentType>"
    And User confirms and submits the order
    And Order confirmation should display
    Examples:
      | paymentType    |
#      | gift card      |
#      | express paypal |
      | credit card    |
