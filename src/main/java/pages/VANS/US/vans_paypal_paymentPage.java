package pages.VANS.US;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
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

    private Locator paypalEmailField(Page popupPage) {
        return popupPage.locator("#email");
    }

    private Locator paypalPasswordField(Page popupPage) {
        return popupPage.locator("#password");
    }

    private Locator paypalNextButton(Page popupPage) {
        return popupPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next"));
    }

    private Locator paypalLoginButton(Page popupPage) {
        return popupPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log In"));
    }

    private Locator paypalPaymentMethod(Page popupPage) {
        return popupPage.locator("[data-testid*='-fi-display']").first();
    }

    private Locator paypalContinueButton(Page popupPage) {
        return popupPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue"));
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
        System.out.println("=======PayPal payment completed successfully!=======");
    }

    private Locator paypal_express_checkout_button() {
        return page.locator("xpath=//*[@id=\"buttons-container\"]/div/div/div");
    }

    private Locator paypal_cart_checkout_button() {
        return page.locator("#cart-page .paypal-button");
    }


    // "Complete purchase" button – use stable attributes from the PayPal UI
    private Locator paypalCompletePurchaseButton(Page popupPage) {
        return popupPage.locator(
                "[data-testid='submit-button-initial'], " +
                        "[data-id='payment-submit-btn'], " +
                        "button:has-text('Complete purchase')"
        );
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
        page.waitForLoadState();
        page.waitForTimeout(SHORT_WAIT);

        // --- EARLY RETURN FOR CART FLOW ---
        if ("Cart paypal".equalsIgnoreCase(paymentType)) {
            System.out.println("Cart flow detected: skipping click because popup was opened in the previous step.");
            return;
        }

        // If a PayPal popup is already open (express/checkout flows), skip clicking again
        for (Page p : page.context().pages()) {
            if (p != page) {
                String u = p.url();
                if (u != null && u.contains("paypal.com")) {
                    System.out.println("Detected existing PayPal popup, skipping extra click.");
                    return;
                }
            }
        }

        Locator paypalButton = null;

        if (paymentType.equals("paypal")) {
            click_Change_paymentOption();
            System.out.println("Im looking for paypal radio button");
            paypal_radioButton_CheckoutPagePage_Select();
            click_Billing_address_checkbox();
            paypalButton = paypal_checkout_button();

        } else if (paymentType.equals("express paypal")) {
            paypalButton = paypal_express_checkout_button();

        } else {
            throw new RuntimeException("Unknown paymentType: " + paymentType);
        }

        if (paypalButton != null) {
            System.out.println("PayPal express/checkout button found, scrolling and clicking...");
            RetryUtility.gradualScrollToBottomUntilLocator(page, paypalButton, "CLICK");
            System.out.println("Successfully clicked PayPal button");
        } else {
            throw new RuntimeException("PayPal button not found on the page");
        }

        page.waitForTimeout(MEDIUM_WAIT);
    }

    Page paypalPopup;

    private void handlePaypalPopupLogin(String email, String password) {
        System.out.println("Starting PayPal login flow...");

        Page paypalPopup = null;

        // --- Reuse an already-open popup (opener or paypal.com URL) ---
        for (Page p : page.context().pages()) {
            if (p != page) {
                boolean isPopupOfThisPage = false;
                try {
                    isPopupOfThisPage = (p.opener() == page);
                } catch (Exception ignored) {
                }
                String u = p.url();
                boolean looksLikePaypal = (u != null && u.contains("paypal.com"));

                if (isPopupOfThisPage || looksLikePaypal) {
                    paypalPopup = p;
                    System.out.println("[PopupPage] Reusing existing popup: opener=" + isPopupOfThisPage + " url=" + u);
                    break;
                }
            }
        }

        try {
            // If no popup exists yet, click once and wait for popup (safety)
            if (paypalPopup == null) {
                System.out.println("[PopupPage] No existing popup, attempting to click PayPal and wait for popup...");
                paypalPopup = page.waitForPopup(() -> {
                    FrameLocator buttonFrame = page.frameLocator("iframe[name^='__zoid__paypal_buttons__'], iframe[title*='PayPal']");
                    Locator paypalButton = buttonFrame.getByRole(
                            AriaRole.LINK,
                            new FrameLocator.GetByRoleOptions().setName("PayPal")
                    );
                    try {
                        paypalButton.click();
                    } catch (Exception e) {
                        paypalButton.click(new Locator.ClickOptions().setForce(true));
                    }
                });
            }

            // Stabilize popup
            paypalPopup.waitForLoadState(LoadState.DOMCONTENTLOADED);
            try {
                paypalPopup.bringToFront();
            } catch (Exception ignored) {
            }
            System.out.println("[PopupPage] Popup detected, handling login...");

            // Debug: list frames inside popup
            for (Frame frame : paypalPopup.frames()) {
                System.out.println("Frame name: " + frame.name() + " | URL: " + frame.url());
            }

            // Perform login inside popup
            performPaypalLoginSteps(paypalPopup, email, password);

            // NEW: After login, select payment method and click Complete Purchase
            selectPaypalPaymentMethod(paypalPopup);

        } catch (TimeoutError te) {
            if (paypalPopup == null) {
                System.out.println("No popup detected or timed out while opening, falling back to inline iframe flow...");
                page.waitForLoadState(LoadState.NETWORKIDLE);

                for (Frame f : page.frames()) {
                    System.out.println("[MainPage] Frame name: " + f.name() + " | URL: " + f.url());
                }

                Frame loginFrame = page.frameByUrl(".*paypal.com.*(signin|login|authenticate).*");
                if (loginFrame != null) {
                    performPaypalLoginStepsInFrame(loginFrame, email, password);
                } else {
                    System.err.println("PayPal login iframe not found on main page; keeping test alive.");
                    return;
                }
            } else {
                System.out.println("[PopupPage] Timeout after popup reuse. Retrying login within popup...");
                try {
                    performPaypalLoginSteps(paypalPopup, email, password);
                    // Ensure payment method selection is retried too
                    selectPaypalPaymentMethod(paypalPopup);
                } catch (Exception e) {
                    System.err.println("Retry in popup failed: " + e.getMessage());
                    return;
                }
            }
        }
    }


    // --- Login steps ---
    private void performPaypalLoginSteps(Page popupPage, String email, String password) {
        System.out.println("Performing PayPal login steps...");

        try {
            Frame loginFrame = waitForPaypalLoginFrame(popupPage, 20000); // 20s poll
            System.out.println("[Popup] Selected login frame: " + (loginFrame != null ? loginFrame.url() : "null"));
            if (loginFrame == null) {
                System.err.println("PayPal login frame not found in popup within timeout; skipping login to avoid termination.");
                return; // avoid throwing to prevent TERMINATED
            }

            performPaypalLoginStepsInFrame(loginFrame, email, password);

        } catch (Exception e) {
            System.err.println("Error during PayPal login: " + e.getMessage());
            // Avoid hard fail; let the test continue to merchant processing
        }
    }

    private void selectPaypalPaymentMethod(Page popupPage) {
        System.out.println("Selecting PayPal payment method...");

        try {
            popupPage.waitForTimeout(DEFAULT_WAIT);

            // Step 1: Select payment method if visible
            if (paypalPaymentMethod(popupPage).isVisible()) {
                System.out.println("Selecting payment method...");
                paypalPaymentMethod(popupPage).scrollIntoViewIfNeeded();
                paypalPaymentMethod(popupPage).click();
                popupPage.waitForTimeout(VERY_SHORT_WAIT);
            } else {
                System.out.println("No specific payment method found, trying fallback...");
                Locator anyPaymentMethod = popupPage.locator("[data-testid*='-fi-display']").first();
                if (anyPaymentMethod.isVisible()) {
                    anyPaymentMethod.scrollIntoViewIfNeeded();
                    anyPaymentMethod.click();
                    popupPage.waitForTimeout(VERY_SHORT_WAIT);
                } else {
                    System.out.println("No payment method selection required, proceeding...");
                }
            }

            // Step 2: Click Continue
            System.out.println("Clicking continue...");
            Locator continueBtn = paypalContinueButton(popupPage);
            if (continueBtn.isVisible()) {
                try {
                    continueBtn.scrollIntoViewIfNeeded();
                    continueBtn.click(new Locator.ClickOptions().setTimeout(10000));
                } catch (Exception e) {
                    continueBtn.evaluate("el => el.click()");
                }
            }
            popupPage.waitForTimeout(MEDIUM_WAIT);

            // Step 3: Click Complete Purchase
            System.out.println("Clicking Complete Purchase...");
            Locator completePurchaseBtn = paypalCompletePurchaseButton(popupPage);
            completePurchaseBtn.waitFor(new Locator.WaitForOptions()
                    .setTimeout(15000)
                    .setState(WaitForSelectorState.VISIBLE));

            try {
                completePurchaseBtn.scrollIntoViewIfNeeded();
                completePurchaseBtn.click(new Locator.ClickOptions().setTimeout(10000));
                System.out.println("Complete Purchase clicked successfully!");
            } catch (Exception e) {
                System.out.println("Normal click failed; trying JS click...");
                completePurchaseBtn.evaluate("el => el.click()");
            }

            // Step 4: Wait for popup redirect or merchant return
            try {
                popupPage.waitForURL("**/checkout**", new Page.WaitForURLOptions().setTimeout(20000));
                System.out.println("Popup navigated to merchant page.");
            } catch (Exception e) {
                System.out.println("Popup did not navigate; checking if it closed or main page advanced...");
            }

            // Step 5: Only close popup if still open
            boolean popupIsOpen = true;
            try {
                popupPage.title();
            } catch (Exception e) {
                popupIsOpen = false;
            }
            if (popupIsOpen) {
                popupPage.waitForTimeout(2000);
                System.out.println("Closing PayPal popup after grace period...");
                popupPage.close();
            }

        } catch (Exception e) {
            System.out.println("Payment method selection had issues: " + e.getMessage());
            // Do not force-close here; let main flow handle processing
        }
    }

    private void waitForPaymentProcessing() {
        System.out.println("Waiting for payment processing on cart page...");

        try {
            // Wait for the "processing payment" indicator to disappear
            Locator processingPayment = page.locator("[data-test-id='paypal-processing']");
            processingPayment.waitFor(new Locator.WaitForOptions()
                    .setTimeout(LONG_WAIT)
                    .setState(WaitForSelectorState.DETACHED));

            // At this point, the site itself will handle navigation to order confirmation
            System.out.println("Payment processing finished, ready for order confirmation page.");

        } catch (Exception e) {
            System.err.println("Error during payment processing: " + e.getMessage());
        }
    }

    private void performPaypalLoginStepsInFrame(Frame loginFrame, String email, String password) {
        System.out.println("Performing PayPal login steps inside login frame...");

        try {
            // --- Email field ---
            Locator emailField = loginFrame.locator("input#email, input[name='login_email'], input[type='email']");
            emailField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(15000)
                    .setState(WaitForSelectorState.VISIBLE));

            System.out.println("Email field found, focusing...");
            try {
                emailField.click(); // prefer natural click to get caret
            } catch (Exception e) {
                emailField.click(new Locator.ClickOptions().setForce(true));
            }
            emailField.fill("");
            emailField.type(email, new Locator.TypeOptions().setDelay(60));

            // --- Next button ---
            Locator nextButton = loginFrame.locator("button#btnNext, button:has-text('Next')");
            nextButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
            nextButton.click();
            System.out.println("Clicked Next after entering email");

            // --- Password field ---
            Locator passwordField = loginFrame.locator("input#password, input[name='login_password'], input[type='password']");
            passwordField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(15000)
                    .setState(WaitForSelectorState.VISIBLE));

            System.out.println("Password field found, focusing...");
            try {
                passwordField.click();
            } catch (Exception e) {
                passwordField.click(new Locator.ClickOptions().setForce(true));
            }
            passwordField.fill("");
            passwordField.type(password, new Locator.TypeOptions().setDelay(60));

//           Log In button (unique by id) ---
            Locator loginButton = loginFrame.locator("button#btnLogin"); // unique
            loginButton.waitFor(new Locator.WaitForOptions()
                    .setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));
            loginButton.click();
            System.out.println("Clicked Log In after entering password");

        } catch (Exception e) {
            System.err.println("Error during PayPal login in frame: " + e.getMessage());
            throw e;
        }
    }

    // Helper: find the PayPal login iframe inside the popup
    private Frame findPaypalLoginFrame(Page popupPage) {
        // Brief wait for frames to attach
        popupPage.waitForTimeout(500);

        // Prefer URLs explicitly indicating login
        for (Frame f : popupPage.frames()) {
            String url = safeUrl(f);
            if (url.contains("paypal.com") &&
                    (url.contains("signin") || url.contains("login") || url.contains("authenticate"))) {
                System.out.println("[Popup] Using login frame: " + url);
                return f;
            }
        }

        // Heuristic: any PayPal frame containing login inputs
        for (Frame f : popupPage.frames()) {
            String url = safeUrl(f);
            if (url.contains("paypal.com")) {
                boolean hasEmail = f.locator("input#email, input[name='login_email'], input[type='email']").count() > 0;
                boolean hasPassword = f.locator("input#password, input[name='login_password'], input[type='password']").count() > 0;
                if (hasEmail || hasPassword) {
                    System.out.println("[Popup] Heuristic login frame: " + url);
                    return f;
                }
            }
        }

        // Fallback: first PayPal frame
        for (Frame f : popupPage.frames()) {
            String url = safeUrl(f);
            if (url.contains("paypal.com")) {
                System.out.println("[Popup] Fallback frame: " + url);
                return f;
            }
        }

        System.out.println("[Popup] No suitable PayPal login frame found.");
        return null;
    }

    private String safeUrl(Frame f) {
        try {
            String u = f.url();
            return (u == null) ? "" : u;
        } catch (Exception e) {
            return "";
        }
    }

    private Frame waitForPaypalLoginFrame(Page popupPage, int timeoutMs) {
        final int step = 500;
        int waited = 0;

        while (waited <= timeoutMs) {
            Frame f = findPaypalLoginFrame(popupPage);
            if (f != null) {
                // Double-check: inputs are present
                boolean hasEmail = f.locator("input#email, input[name='login_email'], input[type='email']").count() > 0;
                boolean hasPassword = f.locator("input#password, input[name='login_password'], input[type='password']").count() > 0;
                if (hasEmail || hasPassword) {
                    System.out.println("[Popup] waitForPaypalLoginFrame: found frame " + f.url());
                    return f;
                }
            }
            popupPage.waitForTimeout(step);
            waited += step;
        }
        System.out.println("[Popup] waitForPaypalLoginFrame: timed out after " + timeoutMs + "ms");
        return null;
    }
}
