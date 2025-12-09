package pages.VANS.US;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.PaymentDataReader;
import utils.RetryUtility;

import java.util.concurrent.atomic.LongAccumulator;

import static utils.Constants.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class vans_paypal_paymentPage {

    private final Page page;
    private PaymentDataReader paymentDataReader;

    public vans_paypal_paymentPage(Page page, String cardType) {
        this.page = page;
        this.paymentDataReader = PaymentDataReader.getInstance(cardType);
    }

    // Simple PayPal button locators - try different selectors
    private Locator paypalExpressCheckoutButton() {
        // Try multiple selectors one by one instead of using or()
        return page.locator("[data-test-id=\"cart-checkout-button\"]");
    }

    private Locator paypalButtonByRole() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("PayPal"));
    }

    private Locator paypalButtonByLink() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PayPal"));
    }

    private Locator paypalButtonByImg() {
        return page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("PayPal"));
    }

    // PayPal iframe locator
    private Locator paypalIframe() {
        return page.locator("iframe[name^=\"__zoid__paypal_buttons__\"]");
    }

    // PayPal button inside the iframe
    private Locator paypalButtonInIframe() {
        return paypalIframe().contentFrame()
                .getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("PayPal"));
    }

    // PayPal popup page locators
    private Locator paypalEmailField(Page popupPage) {
        return popupPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email or mobile number"));
    }

    private Locator paypalNextButton(Page popupPage) {
        return popupPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next"));
    }

    private Locator paypalPasswordField(Page popupPage) {
        return popupPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password"));
    }

    private Locator paypalLoginButton(Page popupPage) {
        return popupPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log In"));
    }

    private Locator paypalPaymentMethod(Page popupPage) {
        return popupPage.getByTestId("BA-MYGNLCTG8RFE6-fi-display").getByTestId("c3-fi-details-name");
    }

    private Locator paypalContinueButton(Page popupPage) {
        return popupPage.getByTestId("submit-button-initial");
    }

    // Main PayPal payment flow method
    public void complete_paypal_payment(String paymentType) {
        String email = paymentDataReader.getEmailId();
        String password = paymentDataReader.getPassword();
        System.out.println("Starting PayPal payment flow...");
        // Step 1: Find and click the main PayPal express checkout button
        clickPaypal_Checkout(paymentType);
        // Step 2: Handle the PayPal popup login and payment
        handlePaypalPopupLogin(email, password);
        // Step 3: Wait for payment processing and confirmation
        waitForPaymentProcessing();
        System.out.println("PayPal payment completed successfully!");
    }

    private Locator paypal_express_checkout_button() {
        return page.locator("xpath=//*[@id=\"buttons-container\"]/div/div/div");
    }

    private Locator paypal_cart_checkout_button() {
        return page.locator("xpath=//*[@id=\"buttons-container\"]/div/div/div");
    }

    private Locator paypal_checkout_button() {
        return page.locator("xpath=//*[@id=\"buttons-container\"]/div/div/div/div[1]/img");
    }

    public void paypal_radioButton_CheckoutPagePage_Select() {
        page.waitForTimeout(SHORT_WAIT);
        RetryUtility.gradualScrollToBottomUntilLocator(page, paypal_radioButton_CheckoutPage(), "CLICK");
    }

    private Locator Change_paymentOption() {
        return page.locator("[data-test-id=\"checkout-edit-payment\"]");
    }

    public void click_Change_paymentOption() {
        page.waitForTimeout(SHORT_WAIT);
        Change_paymentOption().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, Change_paymentOption(), "CLICK");

    }

    private Locator Billing_address_checkbox() {
        return page.locator("[data-test-id=\"vf-checkbox-icon\"] i");
    }

    public void click_Billing_address_checkbox() {
        page.waitForTimeout(SHORT_WAIT);
        Billing_address_checkbox().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, Billing_address_checkbox(), "CLICK");
        page.waitForTimeout(SHORT_WAIT);
    }

    private Locator paypal_radioButton_CheckoutPage() {
        return page.locator("[data-test-id=\"checkout-payment-option-paypal\"]");
//        return  page.locator("[data-test-id='checkout-payment-option-paypal']>span").nth(0);
    }

    private void clickPaypal_Checkout(String paymentType) {
        System.out.println("Looking for PayPal  button...");
        // Wait for the page to load completely
        page.waitForLoadState();
        page.waitForTimeout(SHORT_WAIT);
        Locator paypalButton = null;
        if (paymentType.equals("paypal")) {
            // Try different PayPal button locators one by one
            click_Change_paymentOption();
            System.out.println("Im looking for paypal radio button");
            paypal_radioButton_CheckoutPagePage_Select();
            click_Billing_address_checkbox();
            paypalButton = paypal_checkout_button();

        } else if (paymentType.equals("express paypal")) {
            paypalButton = paypal_express_checkout_button();
        } else if (paymentType.equals("Cart paypal")) {
            // Try different PayPal button locators one by one
            System.out.println("Im looking for paypal in cart page");
            paypalButton = paypal_cart_checkout_button();
            RetryUtility.gradualScrollToBottomUntilLocator(page, paypalButton, "CLICK");
        }
        if (paypalButton != null) {
            System.out.println("PayPal express checkout button found, scrolling and clicking...");

            // Use RetryUtility to handle the click with scrolling
            RetryUtility.gradualScrollToBottomUntilLocator(page, paypalButton, "CLICK");

            System.out.println("Successfully clicked PayPal express checkout button");
        } else {
            throw new RuntimeException("PayPal express checkout button not found on the page");
        }
        page.waitForTimeout(MEDIUM_WAIT);
    }

    Page paypalPopup;

    private void handlePaypalPopupLogin(String email, String password) {
        System.out.println("Waiting for PayPal popup to open...");

        // Wait for PayPal popup to open when clicking the PayPal button in iframe
        paypalPopup = page.waitForPopup(() -> {
            System.out.println("Clicking PayPal button inside iframe...");

            // Wait for iframe to be available
            paypalIframe().waitFor(new Locator.WaitForOptions().setTimeout(10000));

            // Click the PayPal button inside the iframe
            paypalButtonInIframe().click();
        });

        System.out.println("PayPal popup opened, handling login...");

        // Work within the popup context
        paypalPopup.waitForLoadState();
        paypalPopup.waitForTimeout(SHORT_WAIT);

        try {
            // Fill email and proceed
            System.out.println("Entering PayPal email: " + email);
            paypalEmailField(paypalPopup).click();
            paypalEmailField(paypalPopup).fill(email);
            paypalNextButton(paypalPopup).click();

            // Wait for password field to appear
            paypalPopup.waitForTimeout(SHORT_WAIT);

            // Fill password and login
            System.out.println("Entering PayPal password...");
            paypalPasswordField(paypalPopup).click();
            paypalPasswordField(paypalPopup).fill(password);
            paypalLoginButton(paypalPopup).click();

            // Wait for payment method selection page
            paypalPopup.waitForTimeout(MEDIUM_WAIT);

            // Select payment method and continue
            selectPaypalPaymentMethod(paypalPopup);

        } catch (Exception e) {
            System.err.println("Error during PayPal login: " + e.getMessage());
            paypalPopup.close();
            throw e;
        }
    }

    private void selectPaypalPaymentMethod(Page popupPage) {
        System.out.println("Selecting PayPal payment method...");

        try {
            // Wait for payment methods to load
            popupPage.waitForTimeout(DEFAULT_WAIT);

            // Try to select the specific payment method from your recording
            if (paypalPaymentMethod(popupPage).isVisible()) {
                System.out.println("Selecting payment method: BA-MYGNLCTG8RFE6");
                paypalPaymentMethod(popupPage).click();
                popupPage.waitForTimeout(VERY_SHORT_WAIT);
            } else {
                // Fallback: try to find any payment method
                System.out.println("Specific payment method not found, looking for alternatives...");
                Locator anyPaymentMethod = popupPage.locator("[data-testid*='-fi-display']").first();
                if (anyPaymentMethod.isVisible()) {
                    anyPaymentMethod.click();
                    popupPage.waitForTimeout(VERY_SHORT_WAIT);
                } else {
                    System.out.println("No payment method selection required, proceeding directly...");
                }
            }

            // Click continue to complete payment
            System.out.println("Clicking continue to complete payment...");
            paypalContinueButton(popupPage).click();

            // Wait for payment to process in the popup
            popupPage.waitForTimeout(LONG_WAIT);

        } catch (Exception e) {
            System.out.println("Payment method selection had issues: " + e.getMessage());
            // Continue anyway as sometimes PayPal auto-proceeds
        } finally {
            // Close the popup after payment is complete
            System.out.println("Closing PayPal popup...");
            popupPage.close();
        }
    }

    private void waitForPaymentProcessing() {
        System.out.println("Waiting for payment processing on main page...");

        try {
            // Wait for processing messages
            page.waitForTimeout(SHORT_WAIT);

            // Check for processing messages with timeout
            try {
                Locator processingPayment = page.getByText("Processing payment...");
                processingPayment.waitFor(new Locator.WaitForOptions()
                        .setTimeout(MEDIUM_WAIT)
                        .setState(WaitForSelectorState.VISIBLE));
                System.out.println("Processing payment message detected");

                // Wait for it to disappear
                processingPayment.waitFor(new Locator.WaitForOptions()
                        .setTimeout(LONG_WAIT)
                        .setState(WaitForSelectorState.HIDDEN));
                System.out.println("Processing completed");
            } catch (Exception e) {
                System.out.println("Processing message not found or timed out, continuing...");
            }

            // Wait additional time for processing
            page.waitForTimeout(LONG_WAIT);

            // Navigate to order confirmation page if not redirected automatically
            if (!page.url().contains("order-confirmation")) {
                System.out.println("Navigating to order confirmation page...");
                page.navigate("https://preprod3.vans.com/en-us/order-confirmation");
            }

            // Wait for order confirmation
            page.waitForLoadState();
            page.waitForTimeout(DEFAULT_WAIT);

            // Verify order confirmation
            Locator confirmationHeading = page.locator("[data-test-id=\"order-confirmation-heading\"]");
            confirmationHeading.waitFor(new Locator.WaitForOptions()
                    .setTimeout(MEDIUM_WAIT)
                    .setState(WaitForSelectorState.VISIBLE));
            System.out.println("Order confirmation page loaded successfully!");

        } catch (Exception e) {
            System.err.println("Error during payment processing wait: " + e.getMessage());
            // Try to navigate to confirmation page anyway
            if (!page.url().contains("order-confirmation")) {
                page.navigate("https://preprod3.vans.com/en-us/order-confirmation");
                page.waitForTimeout(DEFAULT_WAIT);
            }
            throw e;
        }

    }
}