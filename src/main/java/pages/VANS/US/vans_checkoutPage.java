package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.testng.Assert;
import utils.PaymentDataReader;
import utils.RetryUtility;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.Constants.*;
import utils.UserDetailsReader;
import com.microsoft.playwright.FrameLocator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //reshma
    private Locator ChangeLink_ShippingAddress() {
        //return page.getByRole(AriaRole.BUTTON, new com.microsoft.playwright.Page.GetByRoleOptions().setName("Change"));
        //return page.getByText("Change");
        return page.locator("[data-test-id=\"checkout-edit-shipping-address\"]");
        //return page.locator("xpath=//button[@data-test-id='checkout-edit-shipping-address']");
        //return page.locator("//section/div/button[text()='Change']");
        //return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Change"));
        //return page.locator("xpath=//div[@class='flex items-center between']/button");
    }
    //reshma
    public void click_changeLink_ShippingAddress() {
        ChangeLink_ShippingAddress().scrollIntoViewIfNeeded();
        page.waitForTimeout(DEFAULT_WAIT);
        RetryUtility.gradualScrollToBottomUntilLocator(page, ChangeLink_ShippingAddress(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
    }
    //reshma
    private Locator NewAddress_ShippingAddress() {
        //return page.getByText(" + New Address");
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" + New Address"));
        //return page.locator("[data-test-id=\"change-pickup-location-link\"]");
    }
    //reshma
    public void click_NewAddress_ShippingAddress() {
        NewAddress_ShippingAddress().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, NewAddress_ShippingAddress(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
    }
    //reshma
    private Locator SaveButton_ShippingAddress() {
        return page.locator("xpath=//form[contains(@aria-label,'Add Shipping Address')]/div/button/span");
    }
    //reshma
    private Locator CloseButton_ShippingAddress() {
        return page.locator("[data-test-id='vf-dialog-close']");
    }
    //reshma
    public void click_SaveButton_ShippingAddress() {
        SaveButton_ShippingAddress().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, SaveButton_ShippingAddress(), "CLICK");
        page.waitForTimeout(DEFAULT_WAIT);
        RetryUtility.gradualScrollToBottomUntilLocator(page, CloseButton_ShippingAddress(), "CLICK");
    }

    // ==========================
    //reshma
    // Save all details from Order Confirmation
    // ==========================
    private static String orderNumber;
    private static String orderDate;
    private static String shippingMethod;
    private static String shippingAddress;
    private static String billingAddress;
    private static String paymentMethod;
    private static String orderTotalRaw;
    private static String contactEmail;
    private static String phoneNumber;
    private static String itemsSubtotal;
    private static String shippingCharge;
    private static String taxAmount;
    private static String discountAmount;
    private static String productName;
    private static String productColor;
    private static String productSize;
    private static String productQty;
    private static String productPrice;

// ==========================
// Save all details from Order Confirmation
// ==========================

    public void saveOrderDetails() {

        waitPageReady(page); // optional

        // --- Order basics ---
        String orderNumberRaw = safeTextContent(page.locator("[data-test-id='order-number']"), 10000);
        this.orderNumber = extractOrderId(orderNumberRaw);

        String orderDateRaw = safeTextContent(page.locator("[data-test-id='order-date']"), 10000);
        this.orderDate = normalizeDate(orderDateRaw);

        // Shipping Method (confirmation usually has "Shipping Method: Standard")
        String shippingMethodRaw = safeGetByText(page, "Shipping Method:", 8000);
        if (shippingMethodRaw.isEmpty()) {
            shippingMethodRaw = safeGetByText(page, "Shipping Method: Standard", 6000); // fallback
        }
        this.shippingMethod = normalizeShippingMethod(shippingMethodRaw);

        // --- Addresses (confirmation page often shows them inline) ---
        // Try explicit labels first, then fall back to nearby containers.
        String confShippingBlock = safeGetByText(page, "Shipping Address", 5000);
        if (confShippingBlock.isEmpty() || confShippingBlock.length() < 25) {
            confShippingBlock = getContainerTextAround(page, "Contact Information", 2, 8000);
        }
        this.shippingAddress = normalizeAddressBlock(confShippingBlock);

        String confBillingBlock = safeGetByText(page, "Billing Address", 5000);
        if (confBillingBlock.isEmpty() || confBillingBlock.length() < 25) {
            confBillingBlock = getContainerTextAround(page, "Billing Information", 2, 8000);
        }
        this.billingAddress = normalizeAddressBlock(confBillingBlock);

        // --- Payment ---
        // Confirmation often displays "Ending in #### Exp ..."
        String paymentRaw = safeGetByText(page, "Ending in", 6000);
        if (paymentRaw.isEmpty()) {
            paymentRaw = safeGetByText(page, "Payment Method:", 6000);
        }
        this.paymentMethod = normalizePaymentMethodBlock(paymentRaw); // -> "Ending in 1142"

        // --- Money ---

        // Items Subtotal (confirmation page)
        String itemsSubtotalRaw = safeTextContent(page.locator("[data-test-id='checkout-summary-subtotal']"), 8000);

        if (itemsSubtotalRaw.isEmpty()) {
            // Text fallback
            itemsSubtotalRaw = safeGetByText(page, "Items Subtotal (", 6000); // e.g., "Items Subtotal (1) $4.00"
            if (itemsSubtotalRaw.isEmpty()) {
                itemsSubtotalRaw = safeGetByText(page, "Items Subtotal", 6000);
            }
        }

// Normalize (now picks the last $ amount, not the quantity in parentheses)
        this.itemsSubtotal = normalizeCurrency(itemsSubtotalRaw);
        System.out.println("CONF Items Subtotal: " + this.itemsSubtotal);

        this.shippingCharge = normalizeCurrency(safeGetByText(page, "More Info About Shipping", 8000)); // $5.00 on confirmation
        this.taxAmount      = normalizeCurrency(safeGetByText(page, "Tax", 8000));
        this.discountAmount = normalizeDiscount(safeGetByText(page, "-$", 8000));
        this.orderTotalRaw  = normalizeCurrency(safeGetByText(page, "Order Total", 8000));

        // --- Product (single-line summary on confirmation) ---
        String productBlockConf = normalizeSpaces(
                safeGetByText(page, "Color:", 6000) + " " + safeGetByText(page, "Qty:", 6000)
        );
        if (productBlockConf.isEmpty() || productBlockConf.length() < 20) {
            // fallback: get the vicinity of product name/price
            productBlockConf = normalizeSpaces(getContainerTextAround(page, "Color:", 2, 6000));
        }
        ProductParsed confParsed = parseProductBlock(productBlockConf);

        this.productName  = normalize(confParsed.name);
        this.productColor = normalize(confParsed.color);
        this.productSize  = normalize(confParsed.size);
        this.productQty   = normalize(confParsed.qty);
        this.productPrice = normalize(confParsed.price);

        // Debug prints (optional)
        System.out.println("Order confirmation saved:");
        System.out.println(orderDate);
        System.out.println(shippingMethod);
        System.out.println(shippingAddress);
        System.out.println(billingAddress);
        System.out.println(paymentMethod);
        //System.out.println(itemsSubtotal);
        System.out.println(shippingCharge);
        System.out.println(taxAmount);
        System.out.println(discountAmount);
        System.out.println(orderTotalRaw);
        System.out.println(productName + " " + productColor + " " + productSize + " Qty:" + productQty + " Price:" + productPrice);

        contactEmail = String.valueOf(page.getByText("Contact Information"));
        System.out.println(contactEmail);
        phoneNumber = page.locator("[data-test-id=\"sms-phone-input\"]").inputValue();
        System.out.println(phoneNumber);
    }

// ==========================
// Verify all details in Order History / Details
// ==========================

    public void verifyOrderDetails() {

        waitPageReady(page); // optional

        // --- Order number ---
        String raw = safeTextContent(page.locator("[data-test-id='order-number']"), 10000);
        String historyOrderNumber = extractOrderId(raw);

        // --- Order date ---
        String historyOrderDate = normalizeDate(safeTextContent(page.locator("[data-test-id='order-date']"), 10000));
        System.out.println(historyOrderDate);

        // --- Shipping method ---

// --- Shipping method (robust capture) ---
        String smVal = safeTextContent(page.locator("[data-test-id='order-shipping-method']"), 8000);
        String historyShippingMethod = normalizeShippingMethod(smVal);

// If we only captured the label or got nothing, try text label then heuristics
        if (historyShippingMethod.isEmpty() || historyShippingMethod.equalsIgnoreCase("Shipping Method")) {
            String smText = safeGetByText(page, "Shipping Method:", 6000);
            String parsed = normalizeShippingMethod(smText);
            if (!parsed.isEmpty() && !parsed.equalsIgnoreCase("Shipping Method")) {
                historyShippingMethod = parsed;
            }
        }

// As a last fallback, try common shipping keywords near the section
        if (historyShippingMethod.isEmpty() || historyShippingMethod.equalsIgnoreCase("Shipping Method")) {
            // Known possibilities; extend if your site uses more
            String[] candidates = {"Standard", "Express", "Ground", "Overnight", "Two Day", "Next Day"};
            for (String c : candidates) {
                String hit = safeGetByText(page, c, 3000);
                String parsed = normalizeShippingMethod(hit);
                if (!parsed.isEmpty() && !parsed.equalsIgnoreCase("Shipping Method")) {
                    historyShippingMethod = parsed;
                    break;
                }
            }
        }

// Defensive: if still not parsed, try a tiny container up from the label
        if (historyShippingMethod.isEmpty() || historyShippingMethod.equalsIgnoreCase("Shipping Method")) {
            String smContainer = getContainerTextAround(page, "Shipping Method", 1, 5000);
            String parsed = normalizeShippingMethod(smContainer);
            if (!parsed.isEmpty() && !parsed.equalsIgnoreCase("Shipping Method")) {
                historyShippingMethod = parsed;
            }
        }

        System.out.println(historyShippingMethod);

        // === Shipping Address (combine "Shipping to:" + "Shipping Address:") ===

        String shipToVal   = safeTextContent(page.locator("[data-test-id='order-shipping-info']"), 8000);
        String shipAddrVal = safeTextContent(page.locator("[data-test-id='order-shipping-address']"), 8000);

        // Conservative fallbacks if attributes are missing in a variant
        if (shipToVal.isEmpty() || shipToVal.length() < 3) {
            shipToVal = safeGetByText(page, "Shipping to:", 6000);
        }
        if (shipAddrVal.isEmpty() || shipAddrVal.length() < 5) {
            shipAddrVal = safeGetByText(page, "Shipping Address:", 6000);
            if (shipAddrVal.isEmpty()) {
                shipAddrVal = getContainerTextAround(page, "Shipping Address:", 1, 5000);
            }
        }

        String historyShippingAddress = normalizeAddressBlock(
                normalizeSpaces("Shipping to: " + shipToVal + " Shipping Address: " + shipAddrVal)
        );
        System.out.println(historyShippingAddress);

        // === Billing Address (combine "Billing to" + "Billing Address") ===
        String billToVal   = safeTextContent(page.locator("[data-test-id='order-billing-info']"), 8000);
        String billAddrVal = safeTextContent(page.locator("[data-test-id='order-billing-address']"), 8000);

        if (billToVal.isEmpty() || billToVal.length() < 3) {
            billToVal = safeGetByText(page, "Billing to:", 6000);
        }
        if (billAddrVal.isEmpty() || billAddrVal.length() < 5) {
            billAddrVal = safeGetByText(page, "Billing Address:", 6000);
            if (billAddrVal.isEmpty()) {
                billAddrVal = getContainerTextAround(page, "Billing Address:", 1, 5000);
            }
        }

        String historyBillingAddress = normalizeAddressBlock(
                normalizeSpaces("Billing to: " + billToVal + " Billing Address: " + billAddrVal)
        );
        System.out.println(historyBillingAddress);

        // === Payment method ===
        // Use text anchor to avoid mixing up with shipping method locator
        String historyPaymentMethodRaw = safeGetByText(page, "order-payment-method", 10000);
        String historyPaymentMethod = normalizePaymentMethodBlock(historyPaymentMethodRaw); // -> "Ending in ####"
        System.out.println(historyPaymentMethod);

        // === Money ===

// Items Subtotal (order history page)
        String historyItemsSubtotalRaw = safeTextContent(page.locator("[data-test-id='order-summary-subtotal']"), 8000);

// If the attribute is not available, fall back to text in the summary block
        if (historyItemsSubtotalRaw.isEmpty()) {
            // This will capture something like "Item Subtotal $4.00"
            historyItemsSubtotalRaw = safeGetByText(page, "Item Subtotal", 8000);

            // If label-only was captured, try grabbing the immediate value to the right (sibling cell/span)
            if (!historyItemsSubtotalRaw.toLowerCase().contains("$")) {
                try {
                    // Get the first following sibling node's text near "Item Subtotal"
                    Locator label = page.getByText("Item Subtotal");
                    String rightCell = label.locator("xpath=following-sibling::*[1]").textContent(
                            new Locator.TextContentOptions().setTimeout(3000.0)
                    );
                    if (rightCell != null && !rightCell.isEmpty()) {
                        historyItemsSubtotalRaw = rightCell;
                    }
                } catch (RuntimeException ignored) {
                    // Keep existing raw if sibling fetch fails
                }
            }
        }

// Normalize to canonical currency
        String historyItemsSubtotal = normalizeCurrency(historyItemsSubtotalRaw);
        System.out.println("HIST Items Subtotal: " + historyItemsSubtotal);

        String historyShippingCharge = normalizeCurrency(safeGetByText(page, "Shipping", 10000)); // "ShippingFree" -> $0.00
        System.out.println(historyShippingCharge);

        String historyTaxAmount      = normalizeCurrency(safeGetByText(page, "Taxes", 10000));
        System.out.println(historyTaxAmount);

        String historyDiscountAmount = normalizeDiscount(safeGetByText(page, "-$", 10000));
        System.out.println(historyDiscountAmount);

        String historyOrderTotalRaw  = normalizeCurrency(safeGetByText(page, "Order Total", 10000));
        System.out.println(historyOrderTotalRaw);

        // === Product block ===
        String historyProductBlock = normalizeSpaces(safeGetByText(page, "Item Summary", 10000));
        if (historyProductBlock.isEmpty() || historyProductBlock.length() < 30) {
            historyProductBlock = normalizeSpaces(getContainerTextAround(page, "Item Summary", 2, 8000));
        }
        ProductParsed histParsed = parseProductBlock(historyProductBlock);

        String historyProductName  = histParsed.name;  System.out.println(historyProductName);
        String historyProductColor = histParsed.color; System.out.println(historyProductColor);
        String historyProductSize  = histParsed.size;  System.out.println(historyProductSize);
        String historyProductQty   = histParsed.qty;   System.out.println(historyProductQty);
        String historyProductPrice = histParsed.price; System.out.println(historyProductPrice);

        // ===== assertions =====
        Assert.assertEquals("Order number mismatch",    normalize(orderNumber),    normalize(historyOrderNumber));
        Assert.assertEquals("Order date mismatch",      normalize(orderDate),      normalize(historyOrderDate));
        Assert.assertEquals("Shipping method mismatch", normalize(shippingMethod), normalize(historyShippingMethod));
        //Assert.assertEquals("Shipping address mismatch", normalize(shippingAddress), normalize(historyShippingAddress));
        //Assert.assertEquals("Billing address mismatch",  normalize(billingAddress),  normalize(historyBillingAddress));
        //Assert.assertEquals("Payment method mismatch",   normalize(paymentMethod),   normalize(historyPaymentMethod));
        Assert.assertEquals("Item Subtotal mismatch",    normalize(itemsSubtotal),   normalize(historyItemsSubtotal));
        Assert.assertEquals("Shipping charge mismatch",  normalize(shippingCharge),  normalize(historyShippingCharge));
        Assert.assertEquals("Tax Amount mismatch",       normalize(taxAmount),       normalize(historyTaxAmount));
        Assert.assertEquals("Discount amount mismatch",  normalize(discountAmount),  normalize(historyDiscountAmount));
        Assert.assertEquals("Order total mismatch",      normalize(orderTotalRaw),   normalize(historyOrderTotalRaw));
        //Assert.assertEquals("Product name mismatch",     normalize(productName),    normalize(historyProductName));
        //Assert.assertEquals("Product color mismatch",    normalize(productColor),   normalize(historyProductColor));
        //Assert.assertEquals("Product size mismatch",     normalize(productSize),    normalize(historyProductSize));
        //Assert.assertEquals("Product quantity mismatch", normalize(productQty),     normalize(historyProductQty));
        //Assert.assertEquals("Product price mismatch",    normalize(productPrice),   normalize(historyProductPrice));
    }

// ==========================
// Helpers
// ==========================

    private static String safeTextContent(Locator locator, int timeoutMs) {
        try {
            return locator.textContent(
                    new Locator.TextContentOptions().setTimeout((double) timeoutMs)
            );
        } catch (TimeoutError e) {
            System.out.println("[warn] Timeout getting textContent within " + timeoutMs + "ms for locator: " + locator);
            return ""; // gracefully degrade
        } catch (RuntimeException e) {
            System.out.println("[warn] Exception getting textContent for locator: " + locator + " -> " + e.getMessage());
            return "";
        }
    }

    private static String safeGetByText(Page page, String text, int timeoutMs) {
        Locator loc = page.getByText(text);
        return safeTextContent(loc, timeoutMs);
    }

    private static void waitPageReady(Page page) {
        try {
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            page.waitForLoadState(LoadState.NETWORKIDLE);
            // Anchor present on both pages
            page.locator("[data-test-id='order-number']")
                    .waitFor(new Locator.WaitForOptions().setTimeout(15000.0));
        } catch (TimeoutError e) {
            System.out.println("[warn] Page did not reach expected ready state anchors within timeout");
        } catch (RuntimeException e) {
            System.out.println("[warn] waitPageReady encountered: " + e.getMessage());
        }
    }

// ---------- String helpers / normalizers ----------

    private static String extract(String text, String regex) {
        if (text == null) return "";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1).trim() : "";
    }

    private static String normalizeSpaces(String s) {
        if (s == null) return "";
        return s.replace("\u00A0", " ") // NBSP -> space
                .replaceAll("\\s+", " ")
                .trim();
    }


    private static String normalizeCurrency(String s) {
        if (s == null) return "$0.00";
        String lower = s.toLowerCase();
        if (lower.contains("free")) return "$0.00";

        // Prefer explicit $ amounts; take the LAST $ amount in the string
        Pattern dollar = Pattern.compile("\\$\\s*([\\d.,]+)");
        Matcher m = dollar.matcher(s);
        String lastAmount = "";
        while (m.find()) {
            lastAmount = m.group(1);
        }
        if (!lastAmount.isEmpty()) {
            BigDecimal bd = new BigDecimal(lastAmount.replace(",", ""));
            return String.format("$%.2f", bd);
        }

        // Fallback: no $ amounts found → take the first numeric (rare)
        String num = extract(s, "([\\d.,]+)");
        if (num.isEmpty()) return "$0.00";
        BigDecimal bd = new BigDecimal(num.replace(",", ""));
        return String.format("$%.2f", bd);
    }

    private static String normalizeDiscount(String s) {
        // Compare discount by magnitude (e.g., "-$5.00" vs "$5.00")
        return normalizeCurrency(s);
    }


    private static String normalizeShippingMethod(String s) {
        if (s == null) return "";
        String t = s;

        // Prefer explicit value after "Shipping Method:"
        String method = extract(t, "(?i)Shipping\\s*Method\\s*:?\\s*([A-Za-z ]+)");
        if (method.isEmpty()) {
            // If no explicit value, try known tokens anywhere in the text
            method = extract(t, "(?i)\\b(Standard|Express|Ground|Overnight|Two\\s*Day|Next\\s*Day)\\b");
            if (method.isEmpty()) method = t; // fallback to raw
        }

        // Remove trailing descriptors
        method = method
                .replaceAll("(?i)\\s*Estimated\\s*Delivery.*$", "")
                .replaceAll("(?i)\\s*\\d+\\s*-\\s*\\d+\\s*business\\s*days.*$", "")
                .replaceAll("(?i)\\s*business\\s*days.*$", "");

        // Keep only letters/spaces
        method = method.replaceAll("[^A-Za-z ]", "");
        method = normalizeSpaces(method);

        // If we ended up with just the label, treat as empty (force fallbacks to run)
        if (method.equalsIgnoreCase("Shipping Method")) return "";

        return method; // e.g., "Standard"
    }

    private static String normalizeDate(String s) {
        if (s == null) return "";
        String t = s.replaceAll("(?i)^\\s*Order\\s*Date\\s*:\\s*", ""); // strip "Order Date:"
        t = normalizeSpaces(t);
        String extracted = extract(t, "([A-Za-z]+\\s+\\d{1,2},\\s*\\d{4})");
        if (!extracted.isEmpty()) return extracted;
        return t;
    }

    private static String normalizeAddressBlock(String s) {
        if (s == null) return "";

        // Prefer bounded "Shipping Address" segment
        String raw = s;
        String addrSegment = extract(
                raw,
                "(?is)Shipping\\s*Address:\\s*([\\s\\S]*?)(?=Billing\\s*Information|Billing\\s*Address:|Payment\\s*Method|Item\\s*Summary|Item\\s*Subtotal|Order\\s*Total|Taxes|Shipping\\s*Method|$)"
        );
        String t = addrSegment.isEmpty() ? raw : addrSegment;

        // Strip labels/noise
        t = t.replaceAll("(?i)Shipping\\s*Information", "")
                .replaceAll("(?i)Shipping\\s*to:\\s*", "")
                .replaceAll("(?i)Shipping\\s*Address:\\s*", "")
                .replaceAll("(?i)Billing\\s*Information", "")
                .replaceAll("(?i)Billing\\s*to:\\s*", "")
                .replaceAll("(?i)Billing\\s*Address:\\s*", "")
                .replaceAll("(?i)Shipping\\s*Method:[^\\n]*", "");

        // Hard stop at residual section markers
        t = t.replaceAll("(?is)\\b(Billing|Payment)\\b[\\s\\S]*$", "");

        // Remove phones/emails/country/zip
        t = t.replaceAll("\\+?\\d[\\d\\s().-]{6,}", "")
                .replaceAll("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+", "")
                .replaceAll("\\bUS\\b", "")
                .replaceAll("\\b\\d{5}\\b", "");

        // Normalize punctuation/spaces
        t = t.replace(",", " ").replaceAll("\\s*\\n\\s*", " ");
        t = normalizeSpaces(t);

        // Name
        String name = extract(t, "^([A-Za-z][A-Za-z\\s'.-]+?)\\s+\\d");
        if (name.isEmpty()) name = extract(t, "^([A-Za-z][A-Za-z\\s'.-]{2,30})");

        // Fallback: history page often has name under "Shipping to:"
        if (name.isEmpty()) {
            String fromTo = extract(raw, "(?is)Shipping\\s*to:\\s*([A-Za-z][A-Za-z\\s'.-]+)");
            name = normalizeSpaces(fromTo);
        }

        // City/State
        String city = extract(t, "([A-Za-z]+(?:\\s+[A-Za-z]+)*)\\s+[A-Z]{2}\\b");
        String state = extract(t, "\\b([A-Z]{2})\\b");

        // Street
        String street = "";
        if (!city.isEmpty() && !state.isEmpty()) {
            street = extract(t, "(\\d+\\s+[A-Za-z0-9'\\.\\-\\s]+?)\\s+" + Pattern.quote(city) + "\\s+" + Pattern.quote(state));
            if (street.isEmpty()) {
                street = extract(t, "(\\d+\\s+[A-Za-z0-9'\\.\\-\\s]+?)\\s+" + Pattern.quote(state));
            }
        }
        if (street.isEmpty()) {
            street = extract(t, "(\\d+\\s+[A-Za-z0-9'\\.\\-\\s]+)");
        }

        // De-duplicate if street already contains city/state
        if (!city.isEmpty() && street.toLowerCase().contains(city.toLowerCase())) city = "";
        if (!state.isEmpty() && street.toUpperCase().contains(state.toUpperCase())) state = "";

        StringBuilder canonical = new StringBuilder();
        if (!name.isEmpty()) canonical.append(name).append(" ");
        if (!street.isEmpty()) canonical.append(street).append(" ");
        if (!city.isEmpty()) canonical.append(city).append(" ");
        if (!state.isEmpty()) canonical.append(state);

        return normalizeSpaces(canonical.toString());
    }
    private static String getPaymentBrand(String s) {
        String brand = extract(s, "Payment\\s*Method:\\s*([A-Z]+)");
        if (brand.isEmpty()) brand = extract(s, "\\b([A-Z]{2,})\\b(?=.*Ending\\s*in)");
        return brand;
    }

    private static String getPaymentLast4(String s) {
        return extract(s, "Ending\\s*in\\s*(\\d{4})");
    }

    // Canonical payment comparison by last4 (makes confirmation/history consistent)
    private static String normalizePaymentMethodBlock(String s) {
        String last4 = getPaymentLast4(s);
        if (!last4.isEmpty()) return "Ending in " + last4;
        return normalizeSpaces(s);
    }

    private static String normalize(String s) {
        return normalizeSpaces(s);
    }

    private static String extractOrderId(String raw) {
        String id = extract(raw, "(\\d+)");
        return id.isEmpty() ? normalizeSpaces(raw) : id;
    }
    //reshma
    private Locator ViewOrderDetails() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View Order Details"));
    }
    //reshma
    public void clickOnViewOrderDetails() {
        ViewOrderDetails().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, ViewOrderDetails(), "CLICK");
        page.waitForTimeout(LONG_WAIT);
    }

// ---------- Product parsing ----------

    private static class ProductParsed {
        String name = "";
        String color = "";
        String size = "";
        String qty = "";
        String price = "";
    }

    private static ProductParsed parseProductBlock(String block) {
        ProductParsed p = new ProductParsed();
        if (block == null) block = "";

        String nameLine = extract(block, "^(.+?)\\s*\\$\\s*[\\d.,]+");
        if (nameLine.isEmpty()) {
            nameLine = extract(block, "Item\\s*Description\\s*([\\s\\S]*?)Color:");
        }
        p.name = normalizeSpaces(nameLine);

        p.color = normalizeSpaces(extract(block, "Color:\\s*([^\\n]+)"));
        p.size  = normalizeSpaces(extract(block,  "Size:\\s*([^\\n]+)"));
        p.qty   = normalizeSpaces(extract(block,  "Qty:\\s*(\\d+)"));

        String price = extract(block, "Price:\\s*\\$\\s*([\\d.,]+)");
        if (price.isEmpty()) price = extract(block, "\\$\\s*([\\d.,]+)");
        p.price = normalizeCurrency(price);

        return p;
    }

    private static String getContainerTextAround(Page page, String anchorText, int upLevels, int timeoutMs) {
        Locator anchor = page.getByText(anchorText);
        Locator container = anchor;
        for (int i = 0; i < upLevels; i++) {
            container = container.locator("xpath=.."); // climb up
        }
        return safeTextContent(container, timeoutMs);
    }

}
