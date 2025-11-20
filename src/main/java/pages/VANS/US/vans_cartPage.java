package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.RetryUtility;
import utils.ScreenshotUtil;
import utils.UserDetailsReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.Constants.*;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;


public class vans_cartPage {

    private Page page;
    private UserDetailsReader user =
            UserDetailsReader.getInstance(REGISTERED_USER_ALL);

    public vans_cartPage(Page page) {
        this.page = page;
    }

    private Locator vans_checkoutButton_cartPage() {
        return page.locator("[data-test-id=\"cart-checkout-button\"]");
    }

    public void Click_CheckoutButton_vans() {
        RetryUtility.gradualScrollToBottomUntilLocator(page, vans_checkoutButton_cartPage(), "CLICK");
        page.waitForTimeout(2000);

        page.waitForTimeout(SHORT_WAIT);
    }

    private Locator vans_PickupInStore_CartPage() {
        return page.getByText("Pickup in Store");
    }

    public void vans_PickupInStoreSelect_CartPage() {
        page.waitForTimeout(SHORT_WAIT);
        //assertThat(vans_PickupInStore_CartPage()).isVisible();
        vans_PickupInStore_CartPage().click();

    }

    private Locator vans_PickupInStore_Window_CartPage() {
        page.waitForTimeout(SHORT_WAIT);
        return page.getByText("getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(\"Pickup in Store\")");
    }

    public void vans_PickupInStoreWindow_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_PickupInStore_Window_CartPage()).isVisible();

    }


    private Locator vans_PickupInStore_Window_Distance_CartPage() {
        Locator dropdown = page.locator("[data-test-id=\"base-select\"]");
        dropdown.selectOption("10");
        return dropdown;
    }

    public void vans_PickupInStoreWindow_Distance_Select_CartPage() {
        page.waitForTimeout(SHORT_WAIT);
        vans_PickupInStore_Window_Distance_CartPage().click();

    }

    private Locator vans_PickupInStoreWindow_PostalCodeField_CartPage() {
        return page.locator("[data-test-id=\"vf-form-field-postalCode\"] [data-test-id=\"vf-input-label\"]");
    }

    public void vans_PickupInStoreWindow_enterPostalCodeFill_CartPage() {
        page.waitForTimeout(SHORT_WAIT);
        String postalCode = user.getPostalCode();
        vans_PickupInStoreWindow_PostalCodeField_CartPage().fill(postalCode);
    }

    private Locator vans_PickupInStoreWindow_FindStore_CartPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Find Store"));
       // return page.locator("button:has-text('Find Store')");
//        return page.locator("xpath=//*[@id=\"dialogs\"]/div/div[2]/div/div[2]/div/form/button");
    }

    public void vans_PickupInStoreWindow_FindStore_CartPage_Click() {
        page.waitForTimeout(SHORT_WAIT);
        vans_PickupInStoreWindow_FindStore_CartPage().click();
        if (vans_PickupInStoreWindow_FindStore_CartPage().isVisible()) {
            vans_PickupInStoreWindow_FindStore_CartPage().scrollIntoViewIfNeeded();

            // Wait for button to be enabled
            page.waitForCondition(() -> vans_PickupInStoreWindow_FindStore_CartPage().isEnabled(),
                    new Page.WaitForConditionOptions().setTimeout(DEFAULT_WAIT));

            for (int i = 0; i < 3; i++) {
                System.out.println("Attempting to click find store button - attempt " + (i + 1));
                if (vans_PickupInStoreWindow_FindStore_CartPage().isEnabled()) {
                    vans_PickupInStoreWindow_FindStore_CartPage().click(new Locator.ClickOptions().setForce(true));
                    page.waitForTimeout(2000);

                    // Check if we moved to next step
                    if (vans_PickupInStoreWindow_SelectStore_CartPage().isVisible()) {
                        System.out.println("Able to see select store button");
                        return;
                    }
                }
                page.waitForTimeout(1000);
            }

            // If clicking didn't work, try Enter key
            vans_PickupInStoreWindow_FindStore_CartPage().focus();
            page.keyboard().press("Enter");
            page.waitForTimeout(2000);

        } else {
            System.out.println("Continue button is not visible");
        }
    }

    private Locator vans_PickupInStoreWindow_SelectStore_CartPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select store"));
    }

    public void vans_PickupInStoreWindow_SelectStore_CartPage_Click() {
        page.waitForTimeout(SHORT_WAIT);
        vans_PickupInStoreWindow_SelectStore_CartPage().click();
    }

    private Locator vans_CartItem_CartPage() {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cart (1)"));
    }

    public void vans_CartItem_CarPage_visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_CartItem_CartPage()).isVisible();
    }

    private Locator vans_Product_DecreaseButton_CartPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Decrease Old Skool Shoe"));
    }

    public void vans_removeItemFromCartPage_Click(){
     page.waitForTimeout(DEFAULT_WAIT);
         vans_Product_DecreaseButton_CartPage().click();
    }

    private Locator vans_Product_IncreaseButton_CartPage() {
        //return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Old Skool Shoe"));
       // return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Lil Pergs Tote Bag"));
    return page.locator ("xpath=//*[@id=\"__nuxt\"]/div[1]/div/main/div[2]/div[2]/section/div[1]/section[1]/div/div[3]/div[3]/span/button[2]/i");
    }
    public void vans_increaseItemFromCartPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_Product_IncreaseButton_CartPage().click();
    }

        private Locator vans_ToastMessageForIncreaseItem_CarPage() {
        return page.locator("[data-test-id=\"vf-toast\"]").getByText("Lil Pergs Tote Bag");
    }

    public void vans_IncItemToastMessage_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_ToastMessageForIncreaseItem_CarPage()).isVisible();
    }

    private Locator vans_ExpandGotAPromoCode_cartPage() {
        return page.locator("[data-test-id=\"cart-promocode\"] [data-test-id=\"base-button\"]");
    }

    public void vans_GotAPromoCode_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_ExpandGotAPromoCode_cartPage()).isVisible();
    }

    private Locator vans_GotAPromoCodeInputField_CartPage() {
        return page.locator("[data-test-id=\"base-input\"]");
    }

    public void vans_enterPromoCode_CartPage_Fill(String promoCode) {
        vans_GotAPromoCodeInputField_CartPage().fill(promoCode);
    }

    private Locator vans_ApplyPromoCodeButton_CartPage() {
        return page.locator("[data-test-id=\"vf-button\"]");
    }

    public void vans_clickApplyPromoCodeButton_CartPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_ApplyPromoCodeButton_CartPage().click();
    }

    public void vans_expandPromoCodeSection_CartPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_ExpandGotAPromoCode_cartPage().click();
    }

    private Locator vans_InvalidPromoCodeMessage_CartPage() {
        return page.getByText("The promocode you have");
    }

    public void vans_invalidPromoCodeMessage_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT); // Optional wait
        assertThat(vans_InvalidPromoCodeMessage_CartPage()).isVisible();
    }

    private Locator vans_OrderSummaryText_CartPage() {
        return page.getByText("Order Summary");
    }

    public void vans_OrderSummary_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderSummarySubtotalText_CartPage()).isVisible();
    }

    private Locator vans_OrderSummarySubtotalText_CartPage() {
        return page.getByText("Subtotal");
    }

    public void vans_OrderSummarySubtotalText_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderSummarySubtotalText_CartPage()).isVisible();
    }

    private Locator vans_OrderSummaryItemCountText_CartPage() {
        return page.getByText("(2 items)");
    }

    public void vans_OrderSummaryItemCountText_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderSummaryItemCountText_CartPage()).isVisible();
    }

    private Locator vans_SubtotalPriceInOrderSummary_CartPage() {
        return page.locator("[data-test-id=\"cart-order-summary\"]").getByText("$140.00");
    }

    public void vans_itemSubtotalPriceInOrderSummary_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SubtotalPriceInOrderSummary_CartPage()).isVisible();
    }

    private Locator vans_DiscountsAppliedInOrderSummary_CartPage() {
        return page.getByText("Discounts Applied");
    }

    public void discountsAppliedText_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_DiscountsAppliedInOrderSummary_CartPage()).isVisible();
    }

    private Locator vans_DiscountsAppliedAmount_CartPage() {
        return page.getByText("-$").first();
    }

    public void firstDiscountAmount_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_DiscountsAppliedAmount_CartPage()).isVisible();
    }

    private Locator vans_EstimatedShipping_CartPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More Info About Estimated Shipping"));
    }

    public void vans_EstimatedShipping_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_EstimatedShipping_CartPage()).isVisible();
    }

    private Locator vans_EstimatedShippingAmount_CartPage() {
        return page.getByText("$5.00", new Page.GetByTextOptions().setExact(true));
    }

    public void vans_estimatedShippingAmount_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_EstimatedShippingAmount_CartPage()).isVisible();
    }

    private Locator vans_PromoFreeShipping_OrderSummary_CartPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More Info About Promo: Free"));
    }

    public void vans_PromoFreeShipping_OrderSummary_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_PromoFreeShipping_OrderSummary_CartPage()).isVisible();
    }

    private Locator vans_PromoFreeShippingAmount_CartPage() {
        return page.getByText("-$5.00");
    }

    public void vans_PromoFreeShippingAmount_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_PromoFreeShippingAmount_CartPage()).isVisible();
    }

    private Locator vans_EstimatedTax_CartPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More Info About Estimated Tax"));
    }

    public void vans_EstimatedTax_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_EstimatedTax_CartPage()).isVisible();
    }

    private Locator vans_EstimatedTaxAmount_CartPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More Info About Estimated Tax"));
    }

    public void vans_EstimatedTaxAmount_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_EstimatedTaxAmount_CartPage()).isVisible();
    }

    private Locator vans_OrderTotal_CartPage() {
        return page.getByText("Order Total");
    }

    public void vans_OrderTotal_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderTotal_CartPage()).isVisible();
    }

    private Locator vans_OrderTotalAmount_CartPage() {
        return page.locator("[data-test-id=\"cart-order-summary\"]").getByText("$100.00");
    }

    public void vans_OrderTotalAmount_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderTotalAmount_CartPage()).isVisible();
    }

    private Locator vans_paypal_CartPage() {
        return page.locator("xpath=//*[@id=\"buttons-container\"]/div/div/div/div[1]");
    }

    public void vans_paypal_CartPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_paypal_CartPage().click();
    }

    private Locator vans_SaveForLater_CartPage() {

        return page.locator("[data-test-id=\"save-for-later\"]");
    }

    public void vans_SaveForLaterClick_CartPage() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_SaveForLater_CartPage().click();
    }

    private Locator vans_SaveForLaterSuccessMessage_CartPage() {
        return page.getByText("Old Skool Shoe saved for");
    }

    public void vans_SaveForLaterSuccessMessage_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SaveForLaterSuccessMessage_CartPage()).isVisible();
    }

    private Locator vans_SaveForLaterPage_CartPage() {
        return page.getByText("Saved For Later");
    }

    public void vans_SaveForLaterPage_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SaveForLaterPage_CartPage()).isVisible();
    }

    private Locator vans_ProductName_SaveForLaterPage() {
        return page.locator("[data-test-id=\"name\"]");
    }

    public void vans_ProductName_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_ProductName_SaveForLaterPage()).isVisible();
    }

    private Locator vans_ProductColor_SaveForLaterPage() {
        return page.getByText("Color: Black / White");
    }

    public void vans_ProductColor_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_ProductColor_SaveForLaterPage()).isVisible();
    }

    private Locator vans_ProductSize_SaveForLaterPage() {
        return page.getByText("Size: 4.5 Boys = 6.0 Women");
    }

    public void vans_ProductSize_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_ProductSize_SaveForLaterPage()).isVisible();
    }

    private Locator vans_Price_SaveForLaterPage() {
        return page.getByText("$70.00");
    }

    public void vans_Price_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_Price_SaveForLaterPage()).isVisible();
    }

    private Locator vans_OrderSummary_SaveForLaterPage() {
        return page.getByText("Order Summary");
    }

    public void vans_OrderSummary_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderSummary_SaveForLaterPage()).isVisible();
    }

    private Locator vans_OrderTotal_SaveForLaterPage() {
        return page.getByText("Order Total");
    }

    public void vans_OrderTotal_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderTotal_SaveForLaterPage()).isVisible();
    }

    private Locator vans_OrderTotalAmount_SaveForLaterPage() {
        return page.getByText("$0.00").nth(1);
    }

    public void vans_OrderTotalAmount_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_OrderTotalAmount_SaveForLaterPage()).isVisible();
    }

    private Locator vans_AddToCart_SaveForLaterPage() {
        return page.locator("[data-test-id=\"move-to-cart\"]");
    }

    public void vans_AddToCart_SaveForLaterPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_AddToCart_SaveForLaterPage().click();
    }

    private Locator vans_AddToCartSuccessMessage_SaveForLaterPage() {
        return page.getByText("Old Skool Shoe moved to cart");
    }

    public void vans_AddToCartSuccessMessage_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_AddToCartSuccessMessage_SaveForLaterPage()).isVisible();
    }

    private Locator vans_Remove_SaveForLaterPage() {
        return page.locator("[data-test-id=\"remove\"]");
    }

    public void vans_Remove_SaveForLaterPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_Remove_SaveForLaterPage().click();
    }

    private Locator vans_RemoveSuccessMessage_SaveForLaterPage() {
        return page.getByText("Old Skool Shoe removed from");
    }

    public void vans_RemoveSuccessMessage_SaveForLaterPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_RemoveSuccessMessage_SaveForLaterPage()).isVisible();
    }

    private Locator vans_AddGiftOption_CartPage() {
        return page.locator("[data-test-id=\"add-gift-option\"]");
    }

    public void vans_AddGiftOption_CartPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_AddGiftOption_CartPage().click();
    }

    private Locator vans_SelectGiftOptionsForItem_CartPage() {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Select gift options for item"));
    }

    public void vans_SelectGiftOptionsForItem_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SelectGiftOptionsForItem_CartPage()).isVisible();
    }

    private Locator vans_SelectGiftOptionsForItemBaseImg_CartPage() {
        return page.locator("[data-test-id=\"base-form\"] img");
    }

    public void vans_SelectGiftOptionsForItemBaseImg_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SelectGiftOptionsForItemBaseImg_CartPage()).isVisible();
    }

    private Locator vans_SelectGiftOptionsForItemReqFields_CartPage() {
        return page.getByText("*Required fields");
    }

    public void vans_SelectGiftOptionsForItemReqFields_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SelectGiftOptionsForItemReqFields_CartPage()).isVisible();
    }

    public void vans_SelectGiftOptionsForItem_EnterTextInToNameField(String text) {
        page.locator("[data-test-id=\"vf-form-field-to\"] [data-test-id=\"base-input\"]").fill(text);
    }

    private Locator vans_SelectGiftOptionsSendersName_CartPage() {
        return page.getByText("*Required fields");
    }

    public void vans_SelectGiftOptionsSendersName_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SelectGiftOptionsSendersName_CartPage()).isVisible();
    }

    public void vans_EnterTextIntoSendersNameField(String text) {
        page.locator("[data-test-id=\"vf-form-field-from\"] [data-test-id=\"base-input\"]").fill(text);
    }

    private Locator vans_SelectGiftOptionsMessageField_CartPage() {
        return page.getByText("*Required fields");
    }

    public void vans_SelectGiftOptionsMessageField_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_SelectGiftOptionsMessageField_CartPage()).isVisible();
    }

    public void vans_SelectGiftOptionsEnterMessageField(String text) {
        page.locator("[data-test-id=\"base-textarea\"]").fill(text);
    }

    private Locator vans_SelectGiftOptions_Save() {
        return page.locator("[data-test-id=\"confirm\"]");
    }

    public void vans_SelectGiftOptions_Save_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_SelectGiftOptions_Save().click();
    }

    private Locator vans_SelectGiftOptions_Cancel() {
        return page.locator("[data-test-id=\"cancel\"]");
    }

    public void vans_SelectGiftOptions_Save_Cancel_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_SelectGiftOptions_Save().click();
    }

    private Locator vans_Progressbar_CartPage() {
        return page.locator("[data-test-id=\"vf-progress-bar\"] div");
    }

    public void vans_Progressbar_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_Progressbar_CartPage()).isVisible();
    }

    private Locator vans_ProgressbarAmount_CartPage() {
        return page.getByText("$99.00");
    }

    public void vans_ProgressbarAmount_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_ProgressbarAmount_CartPage()).isVisible();
    }

    private Locator vans_ProgressbarAmountForFreeShipping_CartPage() {
        return page.getByText("Spend $49.00 more for Free");
    }

    public void vans_ProgressbarAmountForFreeShipping_CartPage_Visible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(vans_ProgressbarAmountForFreeShipping_CartPage()).isVisible();
    }

    private Locator vans_CheckoutButton_CartPage() {
        return page.locator("[data-test-id=\"cart-checkout-button\"]");
    }

    public void vans_CheckoutButton_CartPage_Click() {
        vans_CheckoutButton_CartPage().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }


}












//Select gift options for item - page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Select gift options for item"))
//Select gift options for item - page.locator("[data-test-id=\"base-form\"]").getByText("Old Skool Shoe")
//Select gift options for item|required fields | Recipients name -  page.locator("[data-test-id=\"vf-form-field-to\"] [data-test-id=\"vf-input-label\"]")
//Select gift options for item|required fields | Senders name - page.locator("[data-test-id=\"vf-form-field-from\"] [data-test-id=\"base-input\"]")
//Select gift options for item|required fields | message - page.locator("[data-test-id=\"base-textarea\"]")
//characters remaining -  page.getByText("Characters Remaining")
//Cart page locators - vans us
//page.locator("[data-test-id=\"vf-notification\"]").getByText("$20 Off In Cart.")
//Cart(2)- page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cart (2)")).click();
//page.getByText("Customs with your original")
//page.getByText("Customs with our design |")
// product name - page.locator("[data-test-id=\"name\"]")
//Product color - page.getByText("Color: Black / White")
//Product selected size - page.getByText("Size: 4.5 Boys = 6.0 Women")
//product discount - page.locator("[data-test-id=\"cart-product\"]").getByText("$20 Off In Cart.")
//edit - page.locator("[data-test-id=\"edit-product\"]")
//quick shop - page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Quickshop"))
//quick shop|choose your size - page.getByText("Choose Your Size")
//quick shop|select size - page.getByText("B4.5 / W6")
//quick shop|Update in cart - page.locator("[data-test-id=\"vf-dialog-layout\"] [data-test-id=\"vf-button\"]")
//success message-  page.locator("[data-test-id=\"vf-toast\"]").getByText("Old Skool Shoe")
//Progress bar - page.locator("[data-test-id=\"vf-progress-bar\"] div")
// Free standard shipping - page.getByText("Free Standard Shipping")
//delete - page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Decrease Old Skool Shoe"))
//Quantity-page.locator("[data-test-id=\"qty-stepper\"]").getByText("1")
//inc quantity -  page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Old Skool Shoe"))
//ship to home - page.locator("[data-test-id=\"shipment-selector-b491678a52da25de0f10bd89ec-standard\"] [data-test-id=\"vf-radio-input\"] span")
// pickup in store| select store - locator("[data-test-id=\"pickup-location\"]")
//pickup in store|use my location - page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Use My Location"))
//pickup in store|distance - page.locator("[data-test-id=\"base-select\"]")
//pickup in store|city, state or zip code - page.locator("[data-test-id=\"vf-form-field-postalCode\"] [data-test-id=\"base-input\"]")
//pickup in store|find store - page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Find Store"))
//pickup in store|vans store - page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("VANS STORE - SOUTH SHORE MALL"))
//pickup in store|select store - page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select store"))
//out of stock - page.getByText("Out of Stock At This Store")
// Have an Account - page.getByText("Have an Account?")
// join now or signin - page.locator("[data-test-id=\"checkout-sign-in-button\"]")
//email - page.locator("[data-test-id=\"vf-form-field-email\"] [data-test-id=\"base-input\"]"
//continue - page.locator("[data-test-id=\"base-form\"] [data-test-id=\"vf-button\"]"
//got a promocode - page.locator("[data-test-id=\"cart-promocode\"] [data-test-id=\"base-button\"]")
//got a promocode input field - page.locator("[data-test-id=\"base-input\"]")
//Apply - age.locator("[data-test-id=\"vf-button\"]")
//order summary - page.getByText("Order Summary")
//page.getByText("Subtotal")
//page.locator("[data-test-id=\"cart-order-summary\"]").getByText("$140.00")
// page.getByText("(2 items)"
// page.getByText("Discounts Applied")
//promo 20% - age.getByText("-$").first()
//page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More Info About Promo: $20"))
//page.getByText("-$").nth(1)
//page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More Info About Estimated Shipping"))
// page.getByText("$5.00")
//page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More Info About Estimated Tax"))
//page.getByText("$0.00")
//page.getByText("Order Total")
//page.locator("[data-test-id=\"cart-order-summary\"]").getByText("$100.00"
//Save for later - page.locator("[data-test-id=\"save-for-later\"]"
//paypal - page.locator("iframe[name=\"__zoid__paypal_buttons__eyJzZW5kZXIiOnsiZG9tYWluIjoiaHR0cHM6Ly9wcmVwcm9kMy52YW5zLmNvbSJ9LCJtZXRhRGF0YSI6eyJ3aW5kb3dSZWYiOnsidHlwZSI6InBhcmVudCIsImRpc3RhbmNlIjowfX0sInJlZmVyZW5jZSI6eyJ0eXBlIjoicmF3IiwidmFsIjoie1widWlkXCI6XCJ6b2lkLXBheXBhbC1idXR0b25zLXVpZF8xYzk5YWQ5MjZhX21keTZtZGE2bWpnXCIsXCJjb250ZXh0XCI6XCJpZnJhbWVcIixcInRhZ1wiOlwicGF5cGFsLWJ1dHRvbnNcIixcImNoaWxkRG9tYWluTWF0Y2hcIjp7XCJfX3R5cGVfX1wiOlwicmVnZXhcIixcIl9fdmFsX19cIjpcIlxcXFwucGF5cGFsXFxcXC4oY29tfGNuKSg6XFxcXGQrKT8kXCJ9LFwidmVyc2lvblwiOlwiMTBfNF8wXCIsXCJwcm9wc1wiOntcImNyZWF0ZU9yZGVyXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfYzFhM2M4MzZkNF9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwiYm91bmQgaGFuZGxlU3VibWl0XCJ9fSxcIm9uU2hpcHBpbmdBZGRyZXNzQ2hhbmdlXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfMGJlYjgyNzA5NF9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwiYm91bmQgaGFuZGxlT25TaGlwcGluZ0FkZHJlc3NDaGFuZ2VcIn19LFwiZnVuZGluZ1NvdXJjZVwiOlwicGF5cGFsXCIsXCJzdHlsZVwiOntcImxhYmVsXCI6XCJwYXlwYWxcIixcImxheW91dFwiOlwiaG9yaXpvbnRhbFwiLFwiY29sb3JcIjpcIndoaXRlXCIsXCJzaGFwZVwiOlwicmVjdFwiLFwidGFnbGluZVwiOmZhbHNlLFwiaGVpZ2h0XCI6NDgsXCJwZXJpb2RcIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwibWVudVBsYWNlbWVudFwiOlwiYmVsb3dcIixcImRpc2FibGVNYXhXaWR0aFwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJkaXNhYmxlTWF4SGVpZ2h0XCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImJvcmRlclJhZGl1c1wiOjIsXCJzaG91bGRBcHBseVJlYnJhbmRlZFN0eWxlc1wiOmZhbHNlLFwiaXNCdXR0b25Db2xvckFCVGVzdE1lcmNoYW50XCI6ZmFsc2V9LFwib25Jbml0XCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfZGYxZjdiMmM4Yl9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwib25Jbml0XCJ9fSxcIm9uQ2xpY2tcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF8wYzJmOGFkODVhX21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJvbkNsaWNrXCJ9fSxcIm9uQ2FuY2VsXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfMTJlMjRlYzEwOV9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwib25DYW5jZWxcIn19LFwib25BcHByb3ZlXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfMjllYjZiYTAwNV9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwib25BcHByb3ZlXCJ9fSxcImNzcE5vbmNlXCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImFwcFN3aXRjaFdoZW5BdmFpbGFibGVcIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwic2hvd1BheVBhbEFwcFN3aXRjaE92ZXJsYXlcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF8xZDU3NGJiYTQyX21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJzaG93UGF5UGFsQXBwU3dpdGNoT3ZlcmxheVwifX0sXCJoaWRlUGF5UGFsQXBwU3dpdGNoT3ZlcmxheVwiOntcIl9fdHlwZV9fXCI6XCJjcm9zc19kb21haW5fZnVuY3Rpb25cIixcIl9fdmFsX19cIjp7XCJpZFwiOlwidWlkXzg0OTgyN2Q4NjlfbWR5Nm1kYTZtamdcIixcIm5hbWVcIjpcImhpZGVQYXlQYWxBcHBTd2l0Y2hPdmVybGF5XCJ9fSxcInJlZGlyZWN0XCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfNWYwZmNlNDg1YV9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwicmVkaXJlY3RcIn19LFwibGlzdGVuRm9ySGFzaENoYW5nZXNcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF9jMmMyMjcxYmI4X21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJsaXN0ZW5Gb3JIYXNoQ2hhbmdlc1wifX0sXCJyZW1vdmVMaXN0ZW5lckZvckhhc2hDaGFuZ2VzXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfZDI3N2MxNDc1Yl9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwicmVtb3ZlTGlzdGVuZXJGb3JIYXNoQ2hhbmdlc1wifX0sXCJsaXN0ZW5Gb3JWaXNpYmlsaXR5Q2hhbmdlXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfYzEzYjFlMDU1Zl9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwibGlzdGVuRm9yVmlzaWJpbGl0eUNoYW5nZVwifX0sXCJyZW1vdmVMaXN0ZW5lckZvclZpc2liaWxpdHlDaGFuZ2VzXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfYzBhNTMxMjUwYV9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwicmVtb3ZlTGlzdGVuZXJGb3JWaXNpYmlsaXR5Q2hhbmdlc1wifX0sXCJhbGxvd0JpbGxpbmdQYXltZW50c1wiOnRydWUsXCJhbW91bnRcIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwiYXBpU3RhZ2VIb3N0XCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImFwcGxlUGF5XCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImFwcGxlUGF5U3VwcG9ydFwiOmZhbHNlLFwiYnJhbmRlZFwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJidXR0b25Mb2NhdGlvblwiOlwicHJlcHJvZDMudmFucy5jb21cIixcImJ1dHRvblNlc3Npb25JRFwiOlwidWlkX2UxZjljOWU2NjlfbWR5Nm1kYTZtamdcIixcImJ1dHRvblNpemVcIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwiYnV5ZXJDb3VudHJ5XCI6XCJVU1wiLFwiY2xpZW50QWNjZXNzVG9rZW5cIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwiY3VzdG9tZXJJZFwiOlwiXCIsXCJjbGllbnRJRFwiOlwiQVh5OWhJeldCNmhfTGpaVUhqSG1zYnNpaWNTSWJMNEdLT3JjZ29tRWVkVmpkdVVpbklVNEMybGx4a1c1cDBPRzB6VE5ndmlZRmNlYVhFbmpcIixcImNsaWVudE1ldGFkYXRhSURcIjpcInVpZF9jYWRmZmY2ZjFlX21keTZtZGE2bWpnXCIsXCJjb21taXRcIjp0cnVlLFwiY29tcG9uZW50c1wiOltcImJ1dHRvbnNcIixcImZ1bmRpbmctZWxpZ2liaWxpdHlcIl0sXCJjcmVhdGVCaWxsaW5nQWdyZWVtZW50XCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImNyZWF0ZVN1YnNjcmlwdGlvblwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJjcmVhdGVWYXVsdFNldHVwVG9rZW5cIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwiY3NwXCI6e1wibm9uY2VcIjpcIlwifSxcImN1cnJlbmN5XCI6XCJVU0RcIixcImRlYnVnXCI6ZmFsc2UsXCJkaXNhYmxlQ2FyZFwiOltdLFwiZGlzYWJsZUZ1bmRpbmdcIjpbXSxcImRpc2FibGVTZXRDb29raWVcIjp0cnVlLFwiZGlzcGxheU9ubHlcIjpbXSxcImVhZ2VyT3JkZXJDcmVhdGlvblwiOmZhbHNlLFwiZW5hYmxlRnVuZGluZ1wiOltcInBheWxhdGVyXCIsXCJ2ZW5tb1wiXSxcImVuYWJsZVRocmVlRG9tYWluU2VjdXJlXCI6ZmFsc2UsXCJlbmFibGVWYXVsdFwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJlbnZcIjpcInNhbmRib3hcIixcImV4cGVyaW1lbnRcIjp7XCJlbmFibGVWZW5tb1wiOnRydWUsXCJ2ZW5tb1ZhdWx0V2l0aG91dFB1cmNoYXNlXCI6ZmFsc2UsXCJzcGJFYWdlck9yZGVyQ3JlYXRpb25cIjpmYWxzZSxcInZlbm1vV2ViRW5hYmxlZFwiOmZhbHNlLFwiaXNXZWJWaWV3RW5hYmxlZFwiOmZhbHNlLFwiaXNQYXlwYWxSZWJyYW5kRW5hYmxlZFwiOmZhbHNlLFwiaXNQYXlwYWxSZWJyYW5kQUJUZXN0RW5hYmxlZFwiOmZhbHNlLFwiZGVmYXVsdEJsdWVCdXR0b25Db2xvclwiOlwiZGVmYXVsdEJsdWVfbGlnaHRCbHVlXCIsXCJpc0VkZ2VDYWNoZVN0YWxlRW5hYmxlZFwiOmZhbHNlLFwiaXNDc253RXJyb3JUZXN0aW5nRW5hYmxlZFwiOmZhbHNlLFwidmVubW9FbmFibGVXZWJPbk5vbk5hdGl2ZUJyb3dzZXJcIjpmYWxzZSxcInBheXBhbENyZWRpdEJ1dHRvbkNyZWF0ZVZhdWx0U2V0dXBUb2tlbkV4aXN0c1wiOmZhbHNlfSxcImV4cGVyaW1lbnRhdGlvblwiOnt9LFwiZmxvd1wiOlwicHVyY2hhc2VcIixcImZ1bmRpbmdFbGlnaWJpbGl0eVwiOntcInBheXBhbFwiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhdWx0YWJsZVwiOnRydWV9LFwicGF5bGF0ZXJcIjp7XCJlbGlnaWJsZVwiOnRydWUsXCJ2YXVsdGFibGVcIjpmYWxzZSxcInByb2R1Y3RzXCI6e1wicGF5SW4zXCI6e1wiZWxpZ2libGVcIjpmYWxzZSxcInZhcmlhbnRcIjpudWxsfSxcInBheUluNFwiOntcImVsaWdpYmxlXCI6ZmFsc2UsXCJ2YXJpYW50XCI6bnVsbH0sXCJwYXlsYXRlclwiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhcmlhbnRcIjpudWxsfX19LFwiY2FyZFwiOntcImVsaWdpYmxlXCI6dHJ1ZSxcImJyYW5kZWRcIjp0cnVlLFwiaW5zdGFsbG1lbnRzXCI6ZmFsc2UsXCJ2ZW5kb3JzXCI6e1widmlzYVwiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhdWx0YWJsZVwiOnRydWV9LFwibWFzdGVyY2FyZFwiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhdWx0YWJsZVwiOnRydWV9LFwiYW1leFwiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhdWx0YWJsZVwiOnRydWV9LFwiZGlzY292ZXJcIjp7XCJlbGlnaWJsZVwiOnRydWUsXCJ2YXVsdGFibGVcIjp0cnVlfSxcImhpcGVyXCI6e1wiZWxpZ2libGVcIjpmYWxzZSxcInZhdWx0YWJsZVwiOmZhbHNlfSxcImVsb1wiOntcImVsaWdpYmxlXCI6ZmFsc2UsXCJ2YXVsdGFibGVcIjp0cnVlfSxcImpjYlwiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhdWx0YWJsZVwiOnRydWV9LFwibWFlc3Ryb1wiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhdWx0YWJsZVwiOnRydWV9LFwiZGluZXJzXCI6e1wiZWxpZ2libGVcIjp0cnVlLFwidmF1bHRhYmxlXCI6dHJ1ZX0sXCJjdXBcIjp7XCJlbGlnaWJsZVwiOnRydWUsXCJ2YXVsdGFibGVcIjp0cnVlfSxcImNiX25hdGlvbmFsZVwiOntcImVsaWdpYmxlXCI6ZmFsc2UsXCJ2YXVsdGFibGVcIjp0cnVlfX0sXCJndWVzdEVuYWJsZWRcIjpmYWxzZX0sXCJ2ZW5tb1wiOntcImVsaWdpYmxlXCI6dHJ1ZSxcInZhdWx0YWJsZVwiOnRydWV9LFwiaXRhdVwiOntcImVsaWdpYmxlXCI6ZmFsc2V9LFwiY3JlZGl0XCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJhcHBsZXBheVwiOntcImVsaWdpYmxlXCI6ZmFsc2V9LFwic2VwYVwiOntcImVsaWdpYmxlXCI6ZmFsc2V9LFwiaWRlYWxcIjp7XCJlbGlnaWJsZVwiOmZhbHNlfSxcImJhbmNvbnRhY3RcIjp7XCJlbGlnaWJsZVwiOmZhbHNlfSxcImdpcm9wYXlcIjp7XCJlbGlnaWJsZVwiOmZhbHNlfSxcImVwc1wiOntcImVsaWdpYmxlXCI6ZmFsc2V9LFwic29mb3J0XCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJteWJhbmtcIjp7XCJlbGlnaWJsZVwiOmZhbHNlfSxcInAyNFwiOntcImVsaWdpYmxlXCI6ZmFsc2V9LFwid2VjaGF0cGF5XCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJwYXl1XCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJibGlrXCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJ0cnVzdGx5XCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJveHhvXCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJib2xldG9cIjp7XCJlbGlnaWJsZVwiOmZhbHNlfSxcImJvbGV0b2JhbmNhcmlvXCI6e1wiZWxpZ2libGVcIjpmYWxzZX0sXCJtZXJjYWRvcGFnb1wiOntcImVsaWdpYmxlXCI6ZmFsc2V9LFwibXVsdGliYW5jb1wiOntcImVsaWdpYmxlXCI6ZmFsc2V9LFwic2F0aXNwYXlcIjp7XCJlbGlnaWJsZVwiOmZhbHNlfSxcInBhaWR5XCI6e1wiZWxpZ2libGVcIjpmYWxzZX19LFwiZ2V0UGFnZVVybFwiOntcIl9fdHlwZV9fXCI6XCJjcm9zc19kb21haW5fZnVuY3Rpb25cIixcIl9fdmFsX19cIjp7XCJpZFwiOlwidWlkXzAxZTBjOWZiNzZfbWR5Nm1kYTZtamdcIixcIm5hbWVcIjpcImdldFBhZ2VVcmxcIn19LFwiZ2V0UG9wdXBCcmlkZ2VcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF81ODJjODllMTI2X21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJnZXRQb3B1cEJyaWRnZVwifX0sXCJnZXRQcmVyZW5kZXJEZXRhaWxzXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfNDE3YTM2MzYzYV9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwiZ2V0UHJlcmVuZGVyRGV0YWlsc1wifX0sXCJnZXRRdWVyaWVkRWxpZ2libGVGdW5kaW5nXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfMzQzMjExYTNkZl9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwiZ2V0UXVlcmllZEVsaWdpYmxlRnVuZGluZ1wifX0sXCJob3N0ZWRCdXR0b25JZFwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJpbnRlbnRcIjpcImF1dGhvcml6ZVwiLFwianNTZGtMaWJyYXJ5XCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImxvY2FsZVwiOntcImxhbmdcIjpcImVuXCIsXCJjb3VudHJ5XCI6XCJVU1wifSxcIm1lcmNoYW50SURcIjpbXSxcIm1lcmNoYW50UmVxdWVzdGVkUG9wdXBzRGlzYWJsZWRcIjpmYWxzZSxcIm1lc3NhZ2VcIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwibm9uY2VcIjpcIlwiLFwib25Db21wbGV0ZVwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJvbk1lc3NhZ2VDbGlja1wiOntcIl9fdHlwZV9fXCI6XCJjcm9zc19kb21haW5fZnVuY3Rpb25cIixcIl9fdmFsX19cIjp7XCJpZFwiOlwidWlkXzFiY2U1NTA4YWVfbWR5Nm1kYTZtamdcIixcIm5hbWVcIjpcIm9uTWVzc2FnZUNsaWNrXCJ9fSxcIm9uTWVzc2FnZUhvdmVyXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfNzZjNDU0ZTljZl9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwib25NZXNzYWdlSG92ZXJcIn19LFwib25NZXNzYWdlUmVhZHlcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF9lOGQwMGIwZmI0X21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJvbk1lc3NhZ2VSZWFkeVwifX0sXCJvblNoaXBwaW5nQ2hhbmdlXCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcIm9uU2hpcHBpbmdPcHRpb25zQ2hhbmdlXCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImhhc1NoaXBwaW5nQ2FsbGJhY2tcIjp0cnVlLFwicGFnZVR5cGVcIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwicGFydG5lckF0dHJpYnV0aW9uSURcIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwicGF5bWVudE1ldGhvZE5vbmNlXCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcInBheW1lbnRNZXRob2RUb2tlblwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJwYXltZW50UmVxdWVzdFwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJwbGF0Zm9ybVwiOlwiZGVza3RvcFwiLFwicmVmZXJyZXJEb21haW5cIjp7XCJfX3R5cGVfX1wiOlwidW5kZWZpbmVkXCJ9LFwicmVtZW1iZXJcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF81YzhjYjg1OGQ2X21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJyZW1lbWJlclwifX0sXCJyZW1lbWJlcmVkXCI6W10sXCJyZW5kZXJlZEJ1dHRvbnNcIjpbXCJwYXlwYWxcIl0sXCJzZXNzaW9uSURcIjpcInVpZF9jYWRmZmY2ZjFlX21keTZtZGE2bWpnXCIsXCJzZGtDb3JyZWxhdGlvbklEXCI6XCJwcmVidWlsZFwiLFwic2RrSW5pdFRpbWluZ3NcIjp7XCJzZGtJbml0VGltZVN0YW1wXCI6MTc2MTcxNzYyODY1OCxcInNka1NjcmlwdERvd25sb2FkRHVyYXRpb25cIjoyNjM1LjYwMDAwMDAyMzg0MixcImlzU2RrQ2FjaGVkXCI6XCJub1wifSxcInNka1Rva2VuXCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcInNlc3Npb25TdGF0ZVwiOntcImdldFwiOntcIl9fdHlwZV9fXCI6XCJjcm9zc19kb21haW5fZnVuY3Rpb25cIixcIl9fdmFsX19cIjp7XCJpZFwiOlwidWlkXzVjYTk0ZWEwZWZfbWR5Nm1kYTZtamdcIixcIm5hbWVcIjpcImdldFwifX0sXCJzZXRcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF9mNjI1NTk3N2IwX21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJzZXRcIn19fSxcInNob3BwZXJTZXNzaW9uSWRcIjpcIlwiLFwiZ2V0U2hvcHBlckluc2lnaHRzVXNlZFwiOntcIl9fdHlwZV9fXCI6XCJjcm9zc19kb21haW5fZnVuY3Rpb25cIixcIl9fdmFsX19cIjp7XCJpZFwiOlwidWlkX2Q2ZjRjMGQ2NmNfbWR5Nm1kYTZtamdcIixcIm5hbWVcIjpcIl9yXCJ9fSxcInN0YWdlSG9zdFwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJzdG9yYWdlSURcIjpcInVpZF84ZTI5NjY3MTE3X210eTZtemU2bnRpXCIsXCJzdG9yYWdlU3RhdGVcIjp7XCJnZXRcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF81ZTU3NjUzMTJkX21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJnZXRcIn19LFwic2V0XCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfMzk0MWQzMTY1Nl9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwic2V0XCJ9fX0sXCJidXR0b25Db2xvclwiOntcInNob3VsZEFwcGx5UmVicmFuZGVkU3R5bGVzXCI6ZmFsc2UsXCJjb2xvclwiOlwid2hpdGVcIixcImlzQnV0dG9uQ29sb3JBQlRlc3RNZXJjaGFudFwiOmZhbHNlfSxcInN1cHBvcnRlZE5hdGl2ZUJyb3dzZXJcIjpmYWxzZSxcInN1cHBvcnRzUG9wdXBzXCI6dHJ1ZSxcInRlc3RcIjp7XCJhY3Rpb25cIjpcImNoZWNrb3V0XCJ9LFwidXNlckV4cGVyaWVuY2VGbG93XCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcInVzZXJJRFRva2VuXCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcInNka1NvdXJjZVwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJ2YXVsdFwiOmZhbHNlLFwid2FsbGV0XCI6e1wiX190eXBlX19cIjpcInVuZGVmaW5lZFwifSxcImhpZGVTdWJtaXRCdXR0b25Gb3JDYXJkRm9ybVwiOntcIl9fdHlwZV9fXCI6XCJ1bmRlZmluZWRcIn0sXCJ1c2VyQWdlbnRcIjpcIk1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS8xMzkuMC4wLjAgU2FmYXJpLzUzNy4zNlwifSxcImV4cG9ydHNcIjp7XCJpbml0XCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfMGU1ZGI2MWY1Ml9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwiaW5pdFwifX0sXCJjbG9zZVwiOntcIl9fdHlwZV9fXCI6XCJjcm9zc19kb21haW5fZnVuY3Rpb25cIixcIl9fdmFsX19cIjp7XCJpZFwiOlwidWlkX2QzZTU5Y2VjMWJfbWR5Nm1kYTZtamdcIixcIm5hbWVcIjpcImNsb3NlOjptZW1vaXplZFwifX0sXCJjaGVja0Nsb3NlXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfN2Y0NmZkYzE5YV9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwiY2hlY2tDbG9zZVwifX0sXCJyZXNpemVcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF84ODQ2ZTU4N2ZhX21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJ4blwifX0sXCJvbkVycm9yXCI6e1wiX190eXBlX19cIjpcImNyb3NzX2RvbWFpbl9mdW5jdGlvblwiLFwiX192YWxfX1wiOntcImlkXCI6XCJ1aWRfMWEwNTRmZDdkYl9tZHk2bWRhNm1qZ1wiLFwibmFtZVwiOlwia25cIn19LFwic2hvd1wiOntcIl9fdHlwZV9fXCI6XCJjcm9zc19kb21haW5fZnVuY3Rpb25cIixcIl9fdmFsX19cIjp7XCJpZFwiOlwidWlkX2JlMzYxMTNiNjVfbWR5Nm1kYTZtamdcIixcIm5hbWVcIjpcImxuXCJ9fSxcImhpZGVcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF85MGNmNjg0MGRmX21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJjblwifX0sXCJleHBvcnRcIjp7XCJfX3R5cGVfX1wiOlwiY3Jvc3NfZG9tYWluX2Z1bmN0aW9uXCIsXCJfX3ZhbF9fXCI6e1wiaWRcIjpcInVpZF81M2U1NTIwMDg2X21keTZtZGE2bWpnXCIsXCJuYW1lXCI6XCJJblwifX19fSJ9fQ__\"]").contentFrame().getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("PayPal")).click();
//Page2.navigate("https://www.sandbox.paypal.com/checkoutnow?atomic-event-state=eyJkb21haW4iOiJzZGtfcGF5cGFsX3Y1IiwiZXZlbnRzIjpbXSwiaW50ZW50IjoiY2xpY2tfcGF5bWVudF9idXR0b24iLCJpbnRlbnRUeXBlIjoiY2xpY2siLCJpbnRlcmFjdGlvblN0YXJ0VGltZSI6MTU1Mjc1Ni4yOTk5OTk5NTIzLCJ0aW1lU3RhbXAiOjE1NTI3NTYsInRpbWVPcmlnaW4iOjE3NjE3MTc2Mjg4NjIuNSwidGFzayI6InNlbGVjdF9vbmVfdGltZV9jaGVja291dCIsImZsb3ciOiJvbmUtdGltZS1jaGVja291dCIsInVpU3RhdGUiOiJ3YWl0aW5nIiwicGF0aCI6Ii9zbWFydC9idXR0b25zIiwidmlld05hbWUiOiJwYXlwYWwtc2RrIn0%3D&sessionID=uid_cadfff6f1e_mdy6mda6mjg&buttonSessionID=uid_e1f9c9e669_mdy6mda6mjg&stickinessID=uid_ca4e4f47b3_mty6mze6ntc&smokeHash=&sign_out_user=false&fundingSource=paypal&buyerCountry=US&locale.x=en_US&commit=true&client-metadata-id=uid_cadfff6f1e_mdy6mda6mjg&enableFunding.0=paylater&enableFunding.1=venmo&standaloneFundingSource=paypal&branded=true&token=2DN33229J6809700F&clientID=AXy9hIzWB6h_LjZUHjHmsbsiicSIbL4GKOrcgomEedVjduUinIU4C2llxkW5p0OG0zTNgviYFceaXEnj&env=sandbox&sdkMeta=eyJ1cmwiOiJodHRwczovL3d3dy5wYXlwYWwuY29tL3Nkay9qcz9sb2NhbGU9ZW5fVVMmYnV5ZXItY291bnRyeT1VUyZjdXJyZW5jeT1VU0QmaW50ZW50PWF1dGhvcml6ZSZjb21taXQ9dHJ1ZSZ2YXVsdD1mYWxzZSZjbGllbnQtaWQ9QVh5OWhJeldCNmhfTGpaVUhqSG1zYnNpaWNTSWJMNEdLT3JjZ29tRWVkVmpkdVVpbklVNEMybGx4a1c1cDBPRzB6VE5ndmlZRmNlYVhFbmomaW50ZWdyYXRpb24tZGF0ZT0yMDIwLTAyLTAxJmVuYWJsZS1mdW5kaW5nPXBheWxhdGVyLHZlbm1vJmNvbXBvbmVudHM9YnV0dG9ucyxmdW5kaW5nLWVsaWdpYmlsaXR5IiwiYXR0cnMiOnsiZGF0YS11aWQiOiJ1aWRfeXVmeGltYnplc2lxb3V3aXNhaHRyYmpoaW96Ymp4In19&country.x=US&xcomponent=1&integration_artifact=PAYPAL_JS_SDK&version=5.0.515&hasShippingCallback=true");
// page2.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email or mobile number"))
//page2.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next"))
//Saved for later title -  page.getByText("Saved For Later")
//page.locator("[data-test-id=\"move-to-cart\"]")
//remove- page.locator("[data-test-id=\"remove\"]")
// after remove - page.getByText("Success")
//There are no items in your cart - page.getByText("There are no items in your")
//add gift option - page.locator("[data-test-id=\"add-gift-option\"]")
//Select gift options for item - page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Select gift options for item"))
//Select gift options for item - page.locator("[data-test-id=\"base-form\"]").getByText("Old Skool Shoe")
//Select gift options for item|required fields | Recipients name -  page.locator("[data-test-id=\"vf-form-field-to\"] [data-test-id=\"vf-input-label\"]")
//Select gift options for item|required fields | Senders name - page.locator("[data-test-id=\"vf-form-field-from\"] [data-test-id=\"base-input\"]")
//Select gift options for item|required fields | message - page.locator("[data-test-id=\"base-textarea\"]")
//characters remaining -  page.getByText("Characters Remaining")
//save -  page.locator("[data-test-id=\"confirm\"]")
//cancel-  page.locator("[data-test-id=\"cancel\"]")
//Free standard shipping bar- page.locator("[data-test-id=\"vf-progress-bar\"] div")
//page.getByText("Free Standard Shipping")

//klarna
//credit card
//gift card
