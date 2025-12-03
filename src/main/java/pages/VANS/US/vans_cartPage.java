package pages.VANS.US;

import com.microsoft.playwright.*;
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
        page.waitForTimeout(VERY_SHORT_WAIT);

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
        page.waitForTimeout(5000);
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

    public void vans_removeItemFromCartPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_Product_DecreaseButton_CartPage().click();
    }

    private Locator vans_Product_IncreaseButton_CartPage() {
        //return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Old Skool Shoe"));
        // return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Lil Pergs Tote Bag"));
        return page.locator("xpath=//*[@id=\"__nuxt\"]/div[1]/div/main/div[2]/div[2]/section/div[1]/section[1]/div/div[3]/div[3]/span/button[2]/i");
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

private Locator vans_PayPalButton_CartPage() {
    FrameLocator paypalFrame = page.frameLocator("iframe[title*='PayPal']");
    return paypalFrame.locator("div[role='link'][aria-label='PayPal']");
}

    // Action method called from step definition
    public void vans_paypal_CartPage_Click() {
        // Scope into PayPal iframe
        FrameLocator paypalFrame = page.frameLocator("iframe[title*='PayPal']");
        Locator paypalButton = paypalFrame.locator("div[role='link'][aria-label='PayPal']");

        // Wait until visible
        paypalButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));

        // Instead of scrollIntoViewIfNeeded (which can hang), use evaluate directly
        paypalButton.evaluate("el => el.scrollIntoView({behavior: 'instant', block: 'center'})");

        // Click and capture popup
        Page popup = page.waitForPopup(() -> {
            paypalButton.click(new Locator.ClickOptions().setForce(true));
        });

        popup.waitForLoadState();
        System.out.println("Clicked PayPal button on Cart Page and captured popup window.");
    }

    private Locator vans_SaveForLater_CartPage() {
        return page.locator("[data-test-id='save-for-later']").first();
    }

    public void vans_SaveForLaterClick_CartPage() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_SaveForLater_CartPage().scrollIntoViewIfNeeded();
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











