package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pages.VANS.US.*;
import utils.PlaywrightFactory;
import utils.RetryUtility;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class vans_checkoutSteps {
    private Page page;
    private vans_checkoutPage getVansCheckoutPage;
    private vans_paypal_paymentPage getVansPaypalPaymentPage;
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

    @And("select default the shipping method")
    public void selectDefaultTheShippingMethod() {


    }

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

    @When("User places the order by clicking pay now using {string}")
    public void userPlacesTheOrderByClickingPayNowUsing(String input) {
        if (input.equals("paypal") || input.equals("express paypal")) {
            getGetVans_paypal_Page().complete_paypal_payment(input);
        }
        if (input.equals("credit card")) {
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


    @And("user select change payment option")
    public void userSelectChangePaymentOption() {
        getGetVansCheckoutPage().click_changePaymentButton();
    }
}
