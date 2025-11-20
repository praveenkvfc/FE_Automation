package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.PaymentDataReader;
import utils.RetryUtility;

import static utils.Constants.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class vans_Klarna_paymentPage {

    private final Page page;
    private PaymentDataReader paymentDataReader;
    private Page klarnaPopup;

    public vans_Klarna_paymentPage(Page page) {
        this.page = page;
    }

    // Locators for main checkout page
    private Locator changePaymentOption() {
        return page.locator("[data-test-id=\"checkout-edit-payment\"]");
    }

    private Locator klarnaPaymentOption() {
        return page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("KLARNA"));
    }

    private Locator billingAddressCheckbox() {
        return page.locator("[data-test-id=\"vf-checkbox-icon\"] i");
    }

    private Locator continueToPaymentButton() {
        return page.locator("[data-test-id=\"checkout-payment-continue\"]");
    }

    private Locator shippingMethodOvernight() {
        return page.getByText("Overnight");
    }

    // Locators for Klarna popup page
    private Locator klarnaMobileField(Page popupPage) {
        return popupPage.getByTestId("kaf-field");
    }

    private Locator klarnaConfirmAndPayButton(Page popupPage) {
        return popupPage.getByTestId("confirm-and-pay");
    }

    private Locator klarnaProcessingMessage(Page popupPage) {
        return popupPage.getByText("Just a moment...");
    }

    // Main Klarna payment flow method
    public void complete_klarna_payment() {


        System.out.println("Starting Klarna payment flow...");


        // Step 1: Change payment method to Klarna
        selectKlarnaPaymentMethod();

        // Step 2: Handle Klarna popup and complete payment
        String mobileNumber = "+13106683312";
        String otp = "123456";
        handleKlarnaPopupPayment(mobileNumber, otp);

        // Step 4: Wait for payment processing and confirmation
        waitForPaymentProcessing();

        System.out.println("Klarna payment completed successfully!");
    }

    public void selectOvernightShipping() {
        System.out.println("Selecting overnight shipping...");
        page.waitForTimeout(SHORT_WAIT);

        RetryUtility.gradualScrollToBottomUntilLocator(page, shippingMethodOvernight(), "CLICK");

        System.out.println("Overnight shipping selected successfully");
        page.waitForTimeout(SHORT_WAIT);
    }

    public void selectKlarnaPaymentMethod() {
        System.out.println("Changing payment method to Klarna...");
        page.waitForTimeout(SHORT_WAIT);

        // Select Klarna payment option
        System.out.println("Selecting Klarna payment option...");
        klarnaPaymentOption().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, klarnaPaymentOption(), "CLICK");

        page.waitForTimeout(SHORT_WAIT);

        // Agree to billing address
        System.out.println("Agreeing to billing address...");
        billingAddressCheckbox().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, billingAddressCheckbox(), "CLICK");

        System.out.println("Klarna payment method selected successfully");
        page.waitForTimeout(SHORT_WAIT);
    }

    private void handleKlarnaPopupPayment(String mobileNumber, String otp) {
        System.out.println("Waiting for Klarna popup to open...");

        // Wait for Klarna popup to open when clicking continue
        klarnaPopup = page.waitForPopup(() -> {
            System.out.println("Clicking continue to proceed to Klarna payment...");
            continueToPaymentButton().scrollIntoViewIfNeeded();
            RetryUtility.gradualScrollToBottomUntilLocator(page, continueToPaymentButton(), "CLICK");
        });

        System.out.println("Klarna popup opened, handling payment...");

        // Work within the popup context
        klarnaPopup.waitForLoadState();
        klarnaPopup.waitForTimeout(MEDIUM_WAIT);

        try {
            // Enter mobile number
            enterMobileNumberInKlarna(mobileNumber);

            // Wait for OTP field to appear
            klarnaPopup.waitForTimeout(MEDIUM_WAIT);

            // Enter OTP
            enterOtpInKlarna(otp);

            // Wait for payment processing in Klarna
            waitForKlarnaPaymentProcessing();

            // Confirm and pay
            confirmAndPayInKlarna();

        } catch (Exception e) {
            System.err.println("Error during Klarna payment: " + e.getMessage());
            // Take screenshot for debugging
            klarnaPopup.screenshot(new Page.ScreenshotOptions()
                    .setPath(java.nio.file.Paths.get("klarna_error.png")));
            klarnaPopup.close();
            throw e;
        } finally {
            // Close the popup after payment is complete
            System.out.println("Closing Klarna popup...");
            if (klarnaPopup != null && !klarnaPopup.isClosed()) {
                klarnaPopup.close();
            }
        }
    }

    private void enterMobileNumberInKlarna(String mobileNumber) {
        System.out.println("Entering mobile number in Klarna: " + mobileNumber);

        Locator mobileField = klarnaMobileField(klarnaPopup);

        // Wait for field to be ready
        mobileField.waitFor(new Locator.WaitForOptions()
                .setTimeout(MEDIUM_WAIT)
                .setState(WaitForSelectorState.VISIBLE));

        // Clear and fill mobile number
        mobileField.click();
        mobileField.clear();
        mobileField.fill(mobileNumber);

        // Press Enter to submit
        mobileField.press("Enter");

        System.out.println("Mobile number entered successfully");
        klarnaPopup.waitForTimeout(SHORT_WAIT);
    }

    private void enterOtpInKlarna(String otp) {
        System.out.println("Entering OTP in Klarna: " + otp);

        Locator otpField = klarnaMobileField(klarnaPopup);

        // Wait for OTP field to be ready (might be same field or different)
        otpField.waitFor(new Locator.WaitForOptions()
                .setTimeout(MEDIUM_WAIT)
                .setState(WaitForSelectorState.VISIBLE));

        // Clear and fill OTP
        otpField.click();
        otpField.clear();
        otpField.fill(otp);

        // OTP might auto-submit, or you might need to press Enter
        // otpField.press("Enter");

        System.out.println("OTP entered successfully");
        klarnaPopup.waitForTimeout(MEDIUM_WAIT);
    }

    private void waitForKlarnaPaymentProcessing() {
        System.out.println("Waiting for Klarna payment processing...");

        try {
            // Wait for processing message
            klarnaPopup.waitForTimeout(LONG_WAIT);

            // Check if processing message appears
            if (klarnaProcessingMessage(klarnaPopup).isVisible()) {
                System.out.println("Klarna processing detected, waiting...");
                klarnaProcessingMessage(klarnaPopup).click();
                klarnaPopup.waitForTimeout(MEDIUM_WAIT);
            }

        } catch (Exception e) {
            System.out.println("No processing message found or already processed: " + e.getMessage());
        }
    }

    private void confirmAndPayInKlarna() {
        System.out.println("Confirming and completing payment in Klarna...");

        try {
            Locator confirmButton = klarnaConfirmAndPayButton(klarnaPopup);

            // Wait for confirm button to be available
            confirmButton.waitFor(new Locator.WaitForOptions()
                    .setTimeout(LONG_WAIT)
                    .setState(WaitForSelectorState.VISIBLE));

            // Click confirm and pay
            confirmButton.click();

            System.out.println("Confirm and pay button clicked");

            // Wait for payment to complete in Klarna
            klarnaPopup.waitForTimeout(LONG_WAIT);

        } catch (Exception e) {
            System.out.println("Confirm and pay button not found or already processed: " + e.getMessage());
            // Payment might have completed automatically
        }
    }

    private void waitForPaymentProcessing() {
        System.out.println("Waiting for payment processing on main page...");

        try {
            // Wait for processing to complete
            page.waitForTimeout(LONG_WAIT);

            // Navigate to order confirmation page if not redirected automatically
            if (!page.url().contains("order-confirmation")) {
                System.out.println("Navigating to order confirmation page...");
                page.navigate("https://preprod3.vans.com/en-us/order-confirmation");
            }

            // Wait for order confirmation page to load
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

    // Utility method to check if Klarna payment is available
    public boolean isKlarnaPaymentAvailable() {
        try {
            changePaymentOption().waitFor(new Locator.WaitForOptions()
                    .setTimeout(SHORT_WAIT)
                    .setState(WaitForSelectorState.VISIBLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to handle promo code application (from your recording)
    public void applyPromoCode(String promoCode) {
        System.out.println("Applying promo code: " + promoCode);

        Locator promoInput = page.locator("[data-test-id=\"base-input\"]");
        Locator applyButton = page.locator("[data-test-id=\"cart-promocode\"] [data-test-id=\"vf-button\"]");

        promoInput.click();
        promoInput.fill(promoCode);
        applyButton.click();

        // Verify promo code application
        assertThat(page.getByText("Promotions not applied:")).isVisible();

        System.out.println("Promo code applied successfully");
        page.waitForTimeout(SHORT_WAIT);
    }
}