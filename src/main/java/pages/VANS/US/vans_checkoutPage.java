package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.PaymentDataReader;
import utils.RetryUtility;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.Constants.*;
import utils.UserDetailsReader;
import com.microsoft.playwright.FrameLocator;
import utils.RandomDataGenerator;
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

    //Divya
    private Locator CreditCard_payNow() {
        return page.locator("[data-test-id=\"checkout-payment-continue\"]");
    }
    private Locator vans_PickingUpTheOrder_FirstName() {
        return page.locator("[data-test-id=\"vf-form-field-firstName\"] [data-test-id=\"base-input\"]");
    }

    public void vans_PickingUpTheOrder_FirstName_Fill(String firstName) {
        vans_PickingUpTheOrder_FirstName().fill(firstName);
    }

    private Locator vans_PickingUpTheOrder_LastName() {
        return page.locator("[data-test-id=\"vf-form-field-lastName\"] [data-test-id=\"base-input\"]");
    }

    public void vans_PickingUpTheOrder_LastName_Fill(String lastName) {
        vans_PickingUpTheOrder_LastName().fill(lastName);
    }



    // Billing Address at checkout
    public void fillBillingAddressFields() {
        UserDetailsReader user = UserDetailsReader.getInstance(REGISTERED_USER_ALL);
        String province = user.getProvince();
        Locator billingSection = page.locator("[data-test-id=\"vf-form-field-recipientFirstName\"]");
        billingSection.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
        // Fill basic fields
        page.locator("[data-test-id=\"vf-form-field-recipientFirstName\"] [data-test-id=\"base-input\"]").fill(user.getFirstName());
        page.locator("[data-test-id=\"vf-form-field-recipientLastName\"] [data-test-id=\"base-input\"]").fill(user.getLastName());
        page.locator("[data-test-id=\"vf-form-field-addressLine1\"] [data-test-id=\"base-input\"]").fill(user.getStreetAddress());
        page.locator("[data-test-id=\"vf-form-field-city\"] [data-test-id=\"base-input\"]").fill(user.getCity());

        // Select province from native <select>
        Locator provinceDropdown = page.locator("[data-test-id='vf-form-field-stateCityProvDept'] select");
        provinceDropdown.scrollIntoViewIfNeeded();
        provinceDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        provinceDropdown.selectOption(user.getProvince());

        // Fill remaining fields
        page.locator("[data-test-id=\"vf-form-field-postalCode\"] label").fill(user.getPostalCode());
        page.locator("[data-test-id=\"input-phone\"] [data-test-id=\"base-input\"]").fill(user.getPhone());

        System.out.println("Filled billing address on checkout page");
    }
    public void enterCreditCardDetails_CheckoutPage(String cardType) {
        Locator cardIframe = page.locator("iframe[title=\"Iframe for card number\"]");

        // If iframe is not present, assume gift card covered full payment
        if (!cardIframe.isVisible()) {
            System.out.println("Credit card iframe not visible — possibly covered by gift card. Skipping CC entry.");
            return;
        }

        cardIframe.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));

        PaymentDataReader ccpayment = PaymentDataReader.getInstance(cardType);
        System.out.println("Filling credit card details...");

        page.frameLocator("iframe[title=\"Iframe for card number\"]")
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Card number"))
                .fill(ccpayment.getCardNumber());

        page.frameLocator("iframe[title=\"Iframe for expiry date\"]")
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Expiry date"))
                .fill(ccpayment.getFormattedExpiryWithSlash());

        page.frameLocator("iframe[title=\"Iframe for security code\"]")
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Security code"))
                .fill(ccpayment.getSecurityCode());

        System.out.println("Credit card fields filled.");
    }

    //GiftCard payment

    private Locator giftCardButton_CheckoutPage(Page page) {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Gift Card"));
    }

    public void giftCardButton_CheckoutPage_Click(Page page) {
        Locator button = giftCardButton_CheckoutPage(page);
        assertThat(button).isVisible(); // Ensure it's visible
        button.click();                 // Click the button
    }
    private Locator giftCardNumberInput_CheckoutPage(Page page) {
        return page.locator("[data-test-id=\"vf-form-field-cardNumber\"] [data-test-id=\"base-input\"]");
    }

    public void giftCardNumberInputFill_CheckoutPage(Page page, String cardNumber) {
        // Load gift card details from JSON
        PaymentDataReader reader = PaymentDataReader.getInstance("VALID_GIFTCARDS");

        // Get the card number from the JSON file
        String actualCardNumber = reader.getCardNumber();

        Locator input = giftCardNumberInput_CheckoutPage(page);
        assertThat(input).isVisible();   // Ensure it's visible
        input.click();                   // Click the input field
        input.fill(actualCardNumber);   // Fill the card number from JSON
    }


    private Locator giftCardPinTitle_CheckoutPage(Page page) {
        return page.locator("[data-test-id=\"vf-form-field-cardPin\"] [data-test-id=\"vf-input-label\"]");
    }

    public void giftCardInputPinTitle_CheckoutPage_Visible(Page page) {
        assertThat(giftCardPinTitle_CheckoutPage(page)).isVisible();
    }

    private Locator giftCardPinInput_CheckoutPage(Page page) {
        return page.locator("[data-test-id=\"vf-form-field-cardPin\"] [data-test-id=\"base-input\"]");
    }

    public void giftCardPinFill_CheckoutPage(Page page, String cardNumber) {
        // Load gift card details from JSON
        PaymentDataReader reader = PaymentDataReader.getInstance("VALID_GIFTCARDS");

        // Get the PIN from the JSON file
        String pin = reader.getPin();

        Locator input = giftCardPinInput_CheckoutPage(page);
        assertThat(input).isVisible();   // Ensure it's visible
        input.click();                   // Click the input field
        input.fill(pin);                // Fill the PIN from JSON
    }


    private Locator applyGiftCardButton_CheckoutPage(Page page) {
        return page.locator("[data-test-id=\"gift-card-payment-accordion\"] [data-test-id=\"vf-button\"]");
    }

    public void applyGiftCardButton_Click_CheckoutPage(Page page) {
        Locator button = applyGiftCardButton_CheckoutPage(page);
        button.click();                 // Click the "Apply" button
    }
    public void click_payNow_creditCard() {
        CreditCard_payNow().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, CreditCard_payNow(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
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
    //QA-Kajal kabade
    public Locator Vans_Ca_ConfirmAddress_Dailog() {
        return page.locator("xpath=//div[@data-test-id='vf-dialog-content']//h2[text()='Confirm Address']");
    }

    //QA-Kajal kabade
    private Locator Vans_Ca_Confirm_Button() {
        return page.locator("xpath=//button[@data-test-id='confirm']");
    }
    //QA-Kajal kabade
    public void click_Ca_Confirm_Button() {
        Vans_Ca_Confirm_Button().scrollIntoViewIfNeeded();
        Vans_Ca_Confirm_Button().click();
        page.waitForTimeout(SHORT_WAIT);
    }

    //Swathi changes
    private Locator CreditCardRadio_Button() {
        return page.locator("span[data-test-id='vf-radio-input']").nth(2);
    }

    private Locator PayPalRadio_Button() {
        return page.locator("span[data-test-id='vf-radio-input']").nth(4);
    }

    private Locator ApplePayRadio_Button() {
        return page.locator("span[data-test-id='vf-radio-input']").nth(3);
    }

    private Locator KlarnaRadio_Button() {
        return page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("KLARNA"));
    }

    public void validatePaymentMethodClickability() {
        try {
            // PayPal
            if (PayPalRadio_Button().isVisible()) {
                PayPalRadio_Button().click();
                System.out.println("PayPal button clicked successfully.");
            } else {
                System.out.println("PayPal button is NOT visible.");
            }

            // Apple Pay
            if (ApplePayRadio_Button().isVisible()) {
                ApplePayRadio_Button().click();
                System.out.println("Apple Pay button clicked successfully.");
            } else {
                System.out.println("Apple Pay button is NOT visible.");
            }

            //Credit Card
            if (CreditCardRadio_Button().isVisible()) {
                CreditCardRadio_Button().click();
                System.out.println("Credit Card button clicked successfully.");
            } else {
                System.out.println("Credit Card button is NOT visible.");
            }

        } catch (Exception e) {
            System.out.println(" Error while clicking payment methods " + e.getMessage());
        }
    }

    //swathi changes

    public void emailforcontactinfoforGuestUser(){
        UserDetailsReader user = UserDetailsReader.getInstance(REGISTERED_USER_ALL);
        Locator contactInfoEmail = page.locator("[data-test-id='vf-form-field-email'] [data-test-id='base-input']");
        if (contactInfoEmail.isVisible()) {
            String randomEmail = RandomDataGenerator.generateRandomEmail(user.getFirstName(), user.getLastName());
            contactInfoEmail.fill(randomEmail);
            System.out.println("Filled contact info email for guest user: " + randomEmail);
        }
    }





}
