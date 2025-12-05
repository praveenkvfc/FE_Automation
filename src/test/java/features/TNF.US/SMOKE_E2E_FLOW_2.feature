Feature: Create a new user account and place order successfully

  As a new user
  I want to create an account with valid information
  and I am able to place order successfully

  Background:
    Given the user is on the "Create an Account" page

  @TNF_US_Smoke2
  Scenario Outline: Account creation and place order using "<paymentType>" Successfully
    When the user enters the email for vans
    And the user provides the password for vans to Signup or SignIn
    And the user agree privacy policy
    And the user agrees Vans Terms and Conditions
    And the user agrees to receive mails
    And the user clicks the Create an Account button
    Then account created successfully with confirmation message "Your account has been created!"

    When User navigates to the MyAccount page as "registeredUser"
    And user closes the my account window
    Given user clicks on search button
    Then enter the text "cap" in search field
    When User navigates to PDP page by selecting a product
    And User clicks on favorite icon
    And User adds "cap" product to cart
    Then User should click the "View Cart" CTA in Mini Cart
    When User navigates to the Cart page
    And User should be able to increase the quantity in cart page
    And User clicks on save later option in cart page
    Then User navigates to "favourites" page from cart page
    Then User should be able to see the item in my favorites page
    And User add product to cart from favourites page
    And User selects Pick up Instore option
    And User able to find and select the store
    Then User proceeds to checkout
    And User enters mandatory details for who is picking up the order
    When User applies a gift card
    When User selects the payment method using "<paymentType>"
    Then User enters CC details after selecting the "<paymentType>"
    And User enters mandatory inputs for billing address
    And User confirms and submits the order
    And Order confirmation should display
    And User should be able to see the order in Order history page
    Examples:
      | paymentType    |
#      | gift card      |
#      | express paypal |
      | credit card    |
