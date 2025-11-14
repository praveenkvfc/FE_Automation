package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import utils.PlaywrightFactory;

public class vans_placeOrderSteps {
    Page page;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

//    protected String firstname = generateRandomName();
//    protected String lastname = generateRandomName();
//    PaymentDataReader reader = new PaymentDataReader("C:\\Users\\MA00885233\\IdeaProjects\\FE_Automation\\src\\test\\resources\\config_files\\paymentDetails.json");
//    WebDriver driver = WebDriverFactory.getDriver();
//    vans_SignInSignUp_Page vansSignInSignUpPagePage = new vans_SignInSignUp_Page();
//    vans_accountPage vansAccountPage = new vans_accountPage();
//    vans_savedCreditcard_page vansSavedCreditcardPage = new vans_savedCreditcard_page();
//
//    public vans_placeOrderSteps() throws IOException {
//        PageFactory.initElements(driver, this);
//    }
//
//    @When("User navigates to the MyAccount page as {string}")
//    public void userNavigatesToTheMyAccountPageAs(String arg0) {
//        waitForVisibility(vansSignInSignUpPagePage.vans_MayBeLater_Button);
//        vansSignInSignUpPagePage.vans_MayBeLater_Button.click();
//        assert vansAccountPage.vans_MyAccountHeader.isDisplayed();
//    }
//
//    @And("User opens the Credit Cards page")
//    public void userOpensTheCreditCardsPage() {
//        waitForVisibility(vansAccountPage.vans_creditCards);
//        vansAccountPage.vans_creditCards.click();
//        vansSavedCreditcardPage.vans_savedCreditCardsPage.isDisplayed();
//    }
//
//    @And("User adds a default {string} credit card for {string}")
//    public void userAddsADefaultCreditCardFor(String arg0, String arg1) {
//        vansSavedCreditcardPage.vans_addCreditCard.click();
//        vansSavedCreditcardPage.vans_creditCard_Num.sendKeys(reader.getVisaCardNumber());
//        vansSavedCreditcardPage.vans_creditCard_Expiry.sendKeys(reader.getVisaCardExpiry());
//        vansSavedCreditcardPage.vans_creditCard_Cvv.sendKeys(reader.getVisaCardCvv());
//        vansSavedCreditcardPage.vans_creditcard_firstName.sendKeys(firstname);
//        vansSavedCreditcardPage.vans_creditcard_lastName.sendKeys(lastname);
//        vansSavedCreditcardPage.vans_creditcard_addressLine.sendKeys("Eastridge Loop");
//        vansSavedCreditcardPage.vans_creditcard_phoneNumber.sendKeys(ConfigReader.get("mobileNumber"));
//        vansSavedCreditcardPage.vans_creditcard_email.sendKeys(generateRandomEmail(firstname, lastname));
//        vansSavedCreditcardPage.vans_creditcard_makedefaultcard_checkbox.click();
//        vansSavedCreditcardPage.vans_savedCreditCardsPage.click();
//
//
//    }
//
//    @Then("User should see a success message confirming the card is added")
//    public void userShouldSeeASuccessMessageConfirmingTheCardIsAdded() {
//    }
//
//    @When("User navigates to the Address Book page")
//    public void userNavigatesToTheAddressBookPage() {
//    }
//
//    @Then("Address Book should display correct details for {string}")
//    public void addressBookShouldDisplayCorrectDetailsFor(String arg0) {
//    }
//
//    @When("User opens the add {string} address form")
//    public void userOpensTheAddAddressForm(String arg0) {
//    }
//
//    @And("User fills {string} address details for {string}")
//    public void userFillsAddressDetailsFor(String arg0, String arg1) {
//    }
//
//    @And("User saves the {string} address")
//    public void userSavesTheAddress(String arg0) {
//    }
//
//    @When("User navigates to the PLP for {string} category")
//    public void userNavigatesToThePLPForCategory(String arg0) {
//    }
//
//    @And("User selects Sort option {string}")
//    public void userSelectsSortOption(String arg0) {
//    }
//
//    @Then("Products should be sorted in ascending price order")
//    public void productsShouldBeSortedInAscendingPriceOrder() {
//    }
//
//    @And("User clears all items from cart using {string} for {string}")
//    public void userClearsAllItemsFromCartUsingFor(String arg0, String arg1) {
//    }
//
//    @When("User navigates to PDP of product {string}")
//    public void userNavigatesToPDPOfProduct(String arg0) {
//    }
//
//    @And("User adds product {string} to cart")
//    public void userAddsProductToCart(String arg0) {
//    }
//
//    @Then("User should see the {string} CTA in Mini Cart")
//    public void userShouldSeeTheCTAInMiniCart(String arg0) {
//    }
//
//    @When("User navigates to the Cart page")
//    public void userNavigatesToTheCartPage() {
//    }
//
//    @Then("User should see product with {string} control")
//    public void userShouldSeeProductWithControl(String arg0) {
//    }
//
//    @And("User clicks {string} to increase product quantity")
//    public void userClicksToIncreaseProductQuantity(String arg0) {
//    }
//
//    @Then("Product quantity should be incremented")
//    public void productQuantityShouldBeIncremented() {
//    }
//
//    @And("User proceeds to checkout")
//    public void userProceedsToCheckout() {
//    }
//
//    @And("User fills in shipping information for {string}")
//    public void userFillsInShippingInformationFor(String arg0) {
//    }
//
//    @And("User saves shipping and delivery preferences")
//    public void userSavesShippingAndDeliveryPreferences() {
//    }
//
//    @And("User provides {string} card details")
//    public void userProvidesCardDetails(String arg0) {
//    }
//
//    @When("User places the order")
//    public void userPlacesTheOrder() {
//    }
//
//    @Then("User should be redirected to the Order Confirmation page")
//    public void userShouldBeRedirectedToTheOrderConfirmationPage() {
//    }
//
//    @And("Order confirmation should display correct order details")
//    public void orderConfirmationShouldDisplayCorrectOrderDetails() {
//    }
//

}
