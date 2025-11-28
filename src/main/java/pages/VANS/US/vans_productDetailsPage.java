

package pages.VANS.US;
import java.util.regex.Pattern;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.Constants.DEFAULT_WAIT;

public class vans_productDetailsPage {
    private Page page;

    public vans_productDetailsPage(Page page) {
        this.page = page;
    }

    private Locator vans_selectSizeDropdown_PDP() {
        return page.locator("[data-test-id=\"size-groups-item\"] [data-test-id=\"base-button\"]");
    }

    public void click_SizeDropDownOption_PDP() {
        vans_selectSizeDropdown_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_selectSizeDropdown_PDP().click();
    }
public void NavigateBack(){
    page.goBack();
}
    private Locator vans_selectShoeSize_PDP() {
        return page.locator("[data-test-id=\"vf-size-picker\"]").getByText("One Size");

    }

    private Locator vans_selectPantSize_PDP() {
        return page.locator("span").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^L$"))).first();
    }

    private Locator vans_selectSize_PDP() {
        return page.locator("[data-test-id=\"vf-size-picker\"]").getByText("One Size");
    }

    public void click_SelectSize_PDP(String searchItem) {
        if (searchItem.equals("shoe")) {
            vans_selectShoeSize_PDP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_selectShoeSize_PDP().click();
        }
        if (searchItem.equals("pant")) {
            vans_selectPantSize_PDP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_selectPantSize_PDP().click();
        }
    }

    // Finds all size options inside the size picker
    private Locator vans_allSizes_PDP() {
        return page.getByText("B3.5 / W5");

    }

    // Clicks the first available size option
    public void click_SelectSize_PDP() {
        vans_allSizes_PDP().first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_allSizes_PDP().first().click();
    }

    private Locator vans_closeDialog_PDP() {
        return page.locator("[data-test-id=\"vf-dialog-close\"]");
    }

    public void click_closeDialog_PDP() {
        vans_closeDialog_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_closeDialog_PDP().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    private Locator vans_AddToCartButton_PDP() {
        // return page.locator("[data-test-id=\"vf-dialog-layout\"] [data-test-id=\"vf-button\"]");
        //return page.locator("[data-test-id=\"vf-button\"]").filter(new Locator.FilterOptions().setHasText("Add to Cart"));
        return page.locator("#pdp-add-to-cart");
    }

    private Locator vans_ViewCartButton_PDP() {
        return page.locator("[data-test-id=\"mini-cart-view-cart-button\"]");
    }

    private Locator Vans_GotAPromoCode_Checkout() {
        return page.locator("[data-test-id=\"cart-promocode\"] [data-test-id=\"base-button\"]");
    }

    public void check_GotAPromoCode_isVisible() {
        page.waitForTimeout(DEFAULT_WAIT);
        assertThat(Vans_GotAPromoCode_Checkout()).isVisible();
    }

    public void click_addTocartButton_PDP() {
        vans_AddToCartButton_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_AddToCartButton_PDP().click();
        page.waitForTimeout(DEFAULT_WAIT);
        vans_ViewCartButton_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
    }

    public void click_viewCartButton() {
        vans_ViewCartButton_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        page.waitForTimeout(DEFAULT_WAIT);
        vans_ViewCartButton_PDP().click();
    }

    // PDP: Favorite icon
    private Locator vans_FavoriteIcon_PDP() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Favorites"));
    }

    public void vans_FavoriteIcon_PDP_Visible() {
        assertThat(vans_FavoriteIcon_PDP()).isVisible();
    }

    public void vans_FavoriteIcon_PDP_Click() {
        vans_FavoriteIcon_PDP().click();
    }

    // PDP: Toaster "View Favorites" link
    private Locator vans_ViewFavoritesLink_PDP() {
        return page.locator("[data-test-id=\"vf-toast\"] [data-test-id=\"vf-link\"]");
    }

    public void vans_ViewFavoritesLink_PDP_Visible() {
        vans_ViewFavoritesLink_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        assertThat(vans_ViewFavoritesLink_PDP()).isVisible();
    }

    public void vans_ViewFavoritesLink_PDP_Click() {
        vans_ViewFavoritesLink_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_ViewFavoritesLink_PDP().click();
    }

    // PDP: Product name (used to verify item in Favorites)
    private Locator vans_FavProductName_PDP() {
        return page.locator("[data-test-id=\"product-name\"]");
    }

    public void vans_FavProductName_PDP_Visible() {
        assertThat(vans_FavProductName_PDP()).isVisible();
    }

    //User clicks on favorite icon
    public void clickFavoriteIcon_FavoritesPage() {
        vans_FavoriteIcon_PDP_Click();
    }

    // Combined flow: Favorite → Toaster → Navigate
    public void clickFavoriteAndGoToFavoritesPage() {
        vans_FavoriteIcon_PDP_Click();

        vans_ViewFavoritesLink_PDP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));

        vans_ViewFavoritesLink_PDP().click();

        page.waitForURL("**/favorites"); // Adjust if your URL differs

        vans_FavProductName_PDP_Visible(); // Confirm item is present
    }

    // Mini Cart: Increment quantity button
    private Locator vans_incrementQtyButton_MiniCart() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Classic Slip-On"));
    }

    // Mini Cart: Decrement quantity button
    private Locator vans_decrementQtyButton_MiniCart() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Decrease Classic Slip-On"));
    }

       public void click_incrementQty_MiniCart() {
        vans_incrementQtyButton_MiniCart().scrollIntoViewIfNeeded();
        vans_incrementQtyButton_MiniCart().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_incrementQtyButton_MiniCart().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    public void click_decrementQty_MiniCart() {
        vans_decrementQtyButton_MiniCart().scrollIntoViewIfNeeded();
        vans_decrementQtyButton_MiniCart().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_decrementQtyButton_MiniCart().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }
    // Mini Cart: Increment quantity button
    private Locator vans_incrementQtyButtonForSecondProduct_MiniCart() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increase Classic Slip-On Shoe"));
    }

    // Mini Cart: Decrement quantity button
    private Locator vans_decrementQtyButtonForSecondProduct_MiniCart() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Decrease Classic Slip-On Shoe"));
    }

    public void click_vans_incrementQtyButtonForSecondProduct_MiniCart() {
        vans_incrementQtyButtonForSecondProduct_MiniCart().scrollIntoViewIfNeeded();
        vans_incrementQtyButtonForSecondProduct_MiniCart().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_incrementQtyButtonForSecondProduct_MiniCart().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    public void click_vans_decrementQtyButtonForSecondProduct_MiniCart() {
        vans_decrementQtyButtonForSecondProduct_MiniCart().scrollIntoViewIfNeeded();
        vans_decrementQtyButtonForSecondProduct_MiniCart().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT));
        vans_decrementQtyButtonForSecondProduct_MiniCart().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    private Locator vans_closeMiniCartWindow() {
        return page.locator("[data-test-id=\"vf-dialog-close\"]");
    }
    public void vans_closeMiniCartWindow_Click(){
        vans_closeMiniCartWindow().click();
    }
}

