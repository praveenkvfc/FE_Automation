package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.*;
import utils.PlaywrightFactory;
import utils.RandomDataGenerator;
import utils.RetryUtility;
import utils.PaymentDataReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static utils.Constants.DEFAULT_WAIT;
import static utils.Constants.SHORT_WAIT;

public class vans_checkoutSteps {
    private Page page;
    private vans_checkoutPage getVansCheckoutPage;
    private vans_paypal_paymentPage getVansPaypalPaymentPage;
    private vans_addressPage getVansAddressPage;
    private vans_HeaderPage getVansHeaderPage;
    private vans_OrderHistorypage getVansOrderHistoryPage;
    private vans_Klarna_paymentPage getvansKlarnaPaymentPage;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_addressPage getVansAddressPage() {
        if (getVansAddressPage == null) {
            getVansAddressPage = new vans_addressPage(getPage());
        }
        return getVansAddressPage;
    }

    private vans_checkoutPage getGetVansCheckoutPage() {
        if (getVansCheckoutPage == null) {
            getVansCheckoutPage = new vans_checkoutPage(getPage());
        }
        return getVansCheckoutPage;
    }


    private vans_Klarna_paymentPage getvansKlarnaPaymentPage() {
        if (getvansKlarnaPaymentPage == null) {
            getvansKlarnaPaymentPage = new vans_Klarna_paymentPage(getPage());
        }
        return getvansKlarnaPaymentPage;
    }

    private vans_paypal_paymentPage getGetVans_paypal_Page() {
        if (getVansPaypalPaymentPage == null) {
            getVansPaypalPaymentPage = new vans_paypal_paymentPage(getPage(), "PAYPAL");
        }
        return getVansPaypalPaymentPage;
    }
    private vans_HeaderPage getVansHeaderPage() {
        if (getVansHeaderPage == null) {
            getVansHeaderPage = new vans_HeaderPage(getPage());
        }
        return getVansHeaderPage;
    }
    private vans_OrderHistorypage getVansOrderHistoryPage() {
        if (getVansOrderHistoryPage == null) {
            getVansOrderHistoryPage = new vans_OrderHistorypage(getPage());
        }
        return getVansOrderHistoryPage;
    }
    @And("select default the shipping method")
    public void selectDefaultTheShippingMethod() {


    }
//    @And("select default the shipping method")
//    public void selectDefaultTheShippingMethod() {
//
//        getGetVansCheckoutPage().check_standardShippingMethod();
//    }

    @And("select {string} the shipping method")
    public void selectTheShippingMethod(String method) {
        if (method.equals("default")) {
            getGetVansCheckoutPage().check_standardShippingMethod();
        } else if (method.equals("overnight")) {
            getGetVansCheckoutPage().check_OvernightShippingMethod();
        }
    }

    @And("Order confirmation should display")
    public void orderConfirmationShouldDisplay() {
        getGetVansCheckoutPage().check_orderConfirmation();
    }

    @When("User places the order by clicking pay now using credit card")
    public void userPlacesTheOrderByClickingPayNowUsingCreditCard() {
        getGetVansCheckoutPage().click_payNow();
    }

    @When("User selects the payment method using {string}")
    public void userSelectsThePaymentMethodUsing(String input) {
        if (input.equalsIgnoreCase("paypal") || input.equalsIgnoreCase("express paypal")) {
            getGetVans_paypal_Page().complete_paypal_payment(input);
        } else if (input.equalsIgnoreCase("credit card")) {
            // No action needed if credit card is default
            System.out.println("Credit card is selected by default.");
        } else if (input.equalsIgnoreCase("gift card")) {
            userAppliesAGiftCard(); // Reuse existing method
        }
    }

//
//        if (input.equalsIgnoreCase("gift card")) {
//            // Click Gift Card button
//            getGetVansCheckoutPage().giftCardButton_CheckoutPage_Click(getPage());
//
//            // Load gift card details from JSON using correct key
//            int giftCardIndex = 1; // Change to 1 or 2 if needed
//            PaymentDataReader reader = PaymentDataReader.getInstance("VALID_GIFTCARDS_FULL_PAYMENT", giftCardIndex);
//
//            // Fill gift card number
//            getGetVansCheckoutPage().giftCardNumberInputFill_CheckoutPage(getPage(), reader.getCardNumber());
//
//            // Fill PIN
//            getGetVansCheckoutPage().giftCardPinFill_CheckoutPage(getPage(), reader.getPin());
//
//            // Click Apply Gift Card
//            getGetVansCheckoutPage().applyGiftCardButton_Click_CheckoutPage(getPage());
//
//      }


    @And("User enters mandatory details for who is picking up the order")
    public void userEnterMandatoryDetailsForWhoIsPickingUpTheOrder() {
        if (ConfigReader.get("brand").equals("vans")) {
            if (ConfigReader.get("region").equals("us")) {
                // Generate random data
                String firstName = RandomDataGenerator.generateRandomName();
                String lastName = RandomDataGenerator.generateRandomName();
                // Fill into form using checkout page methods
                getGetVansCheckoutPage().vans_PickingUpTheOrder_FirstName_Fill(firstName);
                getGetVansCheckoutPage().vans_PickingUpTheOrder_LastName_Fill(lastName);
            }
        }
    }

    @And("User enters mandatory inputs for billing address")
    public void userEntersMandatoryInputsForBillingAddress() {
        getGetVansCheckoutPage().fillBillingAddressFields();
    }

    @Then("User enters CC details after selecting the {string}")
    public void userEntersCCDetailsAfterSelectingThe(String input) {
        String cardKey = input.equalsIgnoreCase("credit card") ? "VISA" : input;
        getGetVansCheckoutPage().enterCreditCardDetails_CheckoutPage(cardKey);
    }



    @When("User applies a gift card")
    public void userAppliesAGiftCard() {
        getGetVansCheckoutPage().giftCardButton_CheckoutPage_Click(getPage());
        //PaymentDataReader reader = PaymentDataReader.getInstance("VALID_GIFTCARDS_FULL_PAYMENT", 0);
        PaymentDataReader reader = PaymentDataReader.getInstance("VALID_GIFTCARDS_FULL_PAYMENT");
        getGetVansCheckoutPage().giftCardNumberInputFill_CheckoutPage(getPage(), reader.getCardNumber());
        getGetVansCheckoutPage().giftCardPinFill_CheckoutPage(getPage(), reader.getPin());
        getGetVansCheckoutPage().applyGiftCardButton_Click_CheckoutPage(getPage());
    }

    @And("User confirms and submits the order")
    public void userConfirmsAndSubmitsTheOrder() {
        getGetVansCheckoutPage().click_payNow_creditCard();
        if (ConfigReader.get("brand").equals("vans")) {
            if (ConfigReader.get("region").equals("ca")) {
                page.waitForTimeout(SHORT_WAIT);
                if(getGetVansCheckoutPage().Vans_Ca_ConfirmAddress_Dailog().isVisible())
                {
                    getGetVansCheckoutPage().click_Ca_Confirm_Button();
                }
            }
        }
    }

    @And("User should be able to see the order in Order history page")
    public void userShouldBeAbleToSeeTheOrderInOrderHistoryPage() {
        getVansHeaderPage().navigateFromProfileTo("order history");
        getVansOrderHistoryPage().vansorderhistorytextisvisible();
        String headingText = getVansOrderHistoryPage().getOrderHistoryHeadingText();
        System.out.println("Order History Heading: " + headingText);
        //assert it matches expected value
        assertEquals(headingText, "Order History", "Heading text does not match!");
        getVansOrderHistoryPage().vansordernumberisvisible();
        //to capture and print the order number
        String orderNumber = getVansOrderHistoryPage().getOrderNumber();
        System.out.println("Order Number: " + orderNumber);
        if (orderNumber.equals("credit card")) {
            getGetVansCheckoutPage().click_payNow();
        }
    }

    @When("User places the order by clicking pay now using paypal")
    public void userPlacesTheOrderByClickingPayNowUsingPaypal() {

    }


    @When("User places the order by clicking pay now using Klarna")
    public void userPlacesTheOrderByClickingPayNowUsingKlarna() {

        getvansKlarnaPaymentPage().complete_klarna_payment();

    }

    //swathi changes

    @Then("user should be able to toggle the accordion section for payment method")
    public void userShouldBeAbleToToggleTheAccordionSectionForPaymentMethod() {
        getGetVansCheckoutPage().validatePaymentMethodClickability();
    }

    @And("^User enters email for contact info in guestUser$")
    public void userEntersEmailForContactInfoInGuestUser() {
        getGetVansCheckoutPage().emailforcontactinfoforGuestUser();
    }


    @And("user select change payment option")
    public void userSelectChangePaymentOption() {
        getGetVansCheckoutPage().click_changePaymentButton();
    }

    //resma changes

    @And("User clicks on Change link in the shipping address section")
    public void userClickOnChangeLink() {
        getGetVansCheckoutPage().click_changeLink_ShippingAddress();
    }

    @And("User clicks on New Address in the shipping address section")
    public void userClicksOnNewAddressLink() {
        getGetVansCheckoutPage().click_NewAddress_ShippingAddress();
    }

    @Then("User clicks on Save button")
    public void userClicksOnSaveButton() {
        getGetVansCheckoutPage().click_SaveButton_ShippingAddress();
    }

    @And("User varifies the order details in order confirmation page")
    public void userTemporarilySavesTheOrderDetails() {
        if (ConfigReader.get("brand").equals("vans")) {
            getGetVansCheckoutPage().saveOrderDetails();
        }else if(ConfigReader.get("brand").equals("tnf")){
            getGetVansCheckoutPage().saveOrderDetails_TNF();
        }
    }

    @And("User validates order confirmation details in order history page")
    public void userTemporarilyVerifiesTheOrderDetailsInOrderHistoryPage() {
        if (ConfigReader.get("brand").equals("vans")) {
            getGetVansCheckoutPage().verifyOrderDetails();
        } else if (ConfigReader.get("brand").equals("tnf")) {
            getGetVansCheckoutPage().verifyOrderDetails_TNF();
        }
    }

    @And("User clicks on view order details button")
    public void userClickOnViewOrderDetails() {
        if (ConfigReader.get("brand").equals("vans")) {
            getVansHeaderPage().navigateFromProfileTo("order history");
            getGetVansCheckoutPage().clickOnViewOrderDetails();
        }else if(ConfigReader.get("brand").equals("tnf")){
            getVansHeaderPage().tnf_Profile_MyAccount_Click();
            getVansHeaderPage().tnf_MyOrder_MyAccount_Click();
        }
    }

}


