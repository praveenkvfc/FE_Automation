package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pages.VANS.US.vans_cartPage;
import pages.VANS.US.vans_checkoutPage;
import pages.VANS.US.vans_paypal_paymentPage;
import pages.VANS.US.vans_saveCreditcard_page;
import utils.PlaywrightFactory;
import utils.RetryUtility;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class vans_checkoutSteps {
    private Page page;
    private vans_checkoutPage getVansCheckoutPage;
    private vans_paypal_paymentPage getVansPaypalPaymentPage;

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
}
