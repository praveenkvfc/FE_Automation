package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.PaymentDataReader;
import utils.RetryUtility;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.Constants.*;

public class vans_checkoutPage {

    private final Page page;
    private PaymentDataReader paymentDataReader;

    public vans_checkoutPage(Page page) {
        this.page = page;
    }
    // Existing methods remain the same...
    private Locator standarShippingMethod() {
        return page.locator("label").filter(new Locator.FilterOptions().setHasText("StandardEstimated Delivery:")).locator("[data-test-id=\"vf-radio-input\"] span");
    }

    private Locator CreditCard_payNow() {
        return page.locator("[data-test-id=\"checkout-payment-continue\"]");
    }



    private Locator Billing_address_checkbox() {
        return page.locator("[data-test-id=\"checkout-payment-continue\"]");
    }
    public void click_Billing_address_checkbox() {
        CreditCard_payNow().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, CreditCard_payNow(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
    }


    public void click_payNow_creditCard() {
        CreditCard_payNow().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, CreditCard_payNow(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
    }

    private Locator vans_orderConfirmationMessage() {
        return page.locator("[data-test-id=\"order-confirmation-heading\"]");
    }

    private Locator vans_FeedBack_Popup_close() {
        return page.getByRole(AriaRole.BUTTON, new com.microsoft.playwright.Page.GetByRoleOptions().setName("Close"));
    }

    public void close_feedBack_popup() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_FeedBack_Popup_close()).isVisible();
        vans_FeedBack_Popup_close().click();
    }

    public void check_orderConfirmation() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_orderConfirmationMessage()).isVisible();
    }

    public void check_standardShippingMethod() {
        page.waitForTimeout(DEFAULT_WAIT);
        RetryUtility.gradualScrollToBottomUntilLocator(page, standarShippingMethod(), "ASSERT");
    }
}
