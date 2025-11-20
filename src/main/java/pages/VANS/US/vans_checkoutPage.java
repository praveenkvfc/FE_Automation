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
    String promo = "10_OFF";

    public vans_checkoutPage(Page page) {
        this.page = page;
    }

    // Existing methods remain the same...
    private Locator standarShippingMethod() {
        return page.locator("label").filter(new Locator.FilterOptions().setHasText("StandardEstimated Delivery:")).locator("[data-test-id=\"vf-radio-input\"] span");
    }

    private Locator paynow_button_checkout() {
        return page.locator("[data-test-id=\"checkout-payment-continue\"]");
    }


    public void click_Billing_address_checkbox() {
        paynow_button_checkout().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, paynow_button_checkout(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
    }


    public void click_payNow() {
        paynow_button_checkout().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, paynow_button_checkout(), "CLICK");
        page.waitForTimeout(LONG_WAIT);
    }

    private Locator vans_Klarna_paymentOption() {
        return page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("KLARNA"));
    }

    private Locator vans_billingAddress_checkbox() {
        return page.locator("[data-test-id=\"vf-checkbox-icon\"] i");
    }

    public void click_default_address_checkbox() {

        vans_billingAddress_checkbox().scrollIntoViewIfNeeded();
        vans_billingAddress_checkbox().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    public void click_payNow_klarna() {

        vans_Klarna_paymentOption().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, vans_Klarna_paymentOption(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
    }


    private Locator vans_orderConfirmationMessage() {
        return page.locator("[data-test-id=\"order-confirmation-heading\"]");
    }

    private Locator overnightShipping() {
        return page.getByText("Overnight");
    }

    private Locator klarnaOption() {
        return page.locator("[data-test-id*=\"klarna\"]");
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

    public void check_OvernightShippingMethod() {
        page.waitForTimeout(DEFAULT_WAIT);
        overnightShipping().scrollIntoViewIfNeeded();
        overnightShipping().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    private final Locator changePaymentButton() {
        return page.locator("[data-test-id=\"checkout-edit-payment\"]");
    }

    public void click_changePaymentButton() {
        page.waitForTimeout(DEFAULT_WAIT);
        changePaymentButton().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    private Locator Vans_GotAPromoCodeinput_Checkout() {
        return page.locator(" [data-test-id=\"vf-accordion-content\"] [data-test-id=\"base-input\"]");
    }

    private Locator Vans_GotAPromoCodeSubmit_Checkout() {
        return page.locator("[data-test-id=\"cart-promocode\"] [data-test-id=\"vf-button\"]");
    }

    public void check_GotAPromoCodeinput() {
        page.waitForTimeout(DEFAULT_WAIT);
        Vans_GotAPromoCodeinput_Checkout().fill(promo);
    }

    public void check_GotAPromoCodeaply() {
        page.waitForTimeout(DEFAULT_WAIT);
        Vans_GotAPromoCodeSubmit_Checkout().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }


}
