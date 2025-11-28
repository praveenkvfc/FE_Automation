package stepdefinitions.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.VANS.US.*;
import utils.PaymentDataReader;
import utils.PlaywrightFactory;
import utils.RandomDataGenerator;
import utils.RetryUtility;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.details;
import static org.testng.Assert.assertEquals;
import static utils.Constants.LONG_WAIT;

public class vans_checkoutSteps {
    private Page page;
    private vans_checkoutPage getVansCheckoutPage;
    private vans_paypal_paymentPage getVansPaypalPaymentPage;
    private vans_HeaderPage getVansHeaderPage;
    private vans_OrderHistorypage getVansOrderHistoryPage;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_checkoutPage getGetVansCheckoutPage() {
        if (getVansCheckoutPage == null) {
            getVansCheckoutPage = new vans_checkoutPage(getPage());
        }
        return getVansCheckoutPage;
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
        getGetVansCheckoutPage().check_standardShippingMethod();
    }

    @And("Order confirmation should display")
    public void orderConfirmationShouldDisplay() {
        getGetVansCheckoutPage().check_orderConfirmation();
    }

    @When("User places the order by clicking pay now using credit card")
    public void userPlacesTheOrderByClickingPayNowUsingCreditCard() {
        getGetVansCheckoutPage().click_payNow_creditCard();
    }

    @When("User places the order by clicking pay now using {string}")
    public void userPlacesTheOrderByClickingPayNowUsing(String input) {
        if (input.equals("paypal") || input.equals("express paypal")) {
            getGetVans_paypal_Page().complete_paypal_payment(input);
        }
        if (input.equals("credit card")) {
            getGetVansCheckoutPage().click_payNow_creditCard();
        }
    }

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

    @When("User applies a gift card")
    public void userAppliesAGiftCard() {
        getGetVansCheckoutPage().giftCardButton_CheckoutPage_Click(getPage());
        PaymentDataReader reader = PaymentDataReader.getInstance("VALID_GIFTCARDS_FULL_PAYMENT", 0);
        getGetVansCheckoutPage().giftCardNumberInputFill_CheckoutPage(getPage(), reader.getCardNumber());
        getGetVansCheckoutPage().giftCardPinFill_CheckoutPage(getPage(), reader.getPin());
        getGetVansCheckoutPage().applyGiftCardButton_Click_CheckoutPage(getPage());
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

    @Then("User enters CC details after selecting the {string}")
    public void userEntersCCDetailsAfterSelectingThe(String input) {
        String cardKey = input.equalsIgnoreCase("credit card") ? "VISA" : input;
        getGetVansCheckoutPage().enterCreditCardDetails_CheckoutPage(cardKey);
    }

    @And("User enters mandatory inputs for billing address")
    public void userEntersMandatoryInputsForBillingAddress() {
        getGetVansCheckoutPage().fillBillingAddressFields();
    }

    @And("User confirms and submits the order")
    public void userConfirmsAndSubmitsTheOrder() {
        getGetVansCheckoutPage().click_payNow_creditCard();
    }


    @And("User enters mandatory details for who is picking up the order")
    public void userEnterMandatoryDetailsForWhoIsPickingUpTheOrder() {
        // Generate random data
        String firstName = RandomDataGenerator.generateRandomName();
        String lastName = RandomDataGenerator.generateRandomName();
        // Fill into form using checkout page methods
        getGetVansCheckoutPage().vans_PickingUpTheOrder_FirstName_Fill(firstName);
        getGetVansCheckoutPage().vans_PickingUpTheOrder_LastName_Fill(lastName);
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

    @And("User varifies the order details in order confirmation page")
    public void userTemporarilySavesTheOrderDetails() {
        getGetVansCheckoutPage().saveOrderDetails();
    }

    @And("User validates order confirmation details in order history page")
    public void userTemporarilyVerifiesTheOrderDetailsInOrderHistoryPage() {
        getGetVansCheckoutPage().verifyOrderDetails();
    }

    @And("User clicks on view order details button")
    public void userClickOnViewOrderDetails() {
        getVansHeaderPage().navigateFromProfileTo("order history");
        getGetVansCheckoutPage().clickOnViewOrderDetails();
    }

    @And("waits for manual task")
    public void waitsForManualTask() {
        getGetVansCheckoutPage().waitsForManualTask();
    }
}
