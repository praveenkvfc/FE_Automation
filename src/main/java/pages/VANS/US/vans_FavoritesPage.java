package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Random;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.Constants.DEFAULT_WAIT;

public class vans_FavoritesPage {
    private final Page page;

    public vans_FavoritesPage(Page page) {
        this.page = page;
    }

    private Locator vans_FavProductName_FavoritesPage() {
        return page.locator("[data-test-id=\"product-card-title\"]");
    }

    public void vans_ProductName_FavoritesPage_Visible() {
        vans_FavProductName_FavoritesPage().scrollIntoViewIfNeeded();
           assertThat(vans_FavProductName_FavoritesPage()).isVisible();
        }

    private Locator vans_MyFavoritesHeading_FavoritesPage() {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("My Favorites"));
    }

    public void vans_MyFavoritesHeading_FavoritesPage_Visible() {
        assertThat(vans_MyFavoritesHeading_FavoritesPage()).isVisible();
    }
    private Locator vans_ProductCount_FavoritesPage() {
        return page.getByText("1 Product", new Page.GetByTextOptions().setExact(true));
    }
    public void vans_ProductCount_FavoritesPage_Visible() {
        assertThat(vans_MyFavoritesHeading_FavoritesPage()).isVisible();
    }
    private Locator vans_AddToQuickShop_FavoritesPage() {
        return page.locator("[data-test-id=\"product-card-add\"]");
    }
    public void vans_AddToQuickShop_FavoritesPage_Click() {
        page.waitForTimeout(3000);
        vans_AddToQuickShop_FavoritesPage().click();
    }
    private Locator vans_QuickShopSizeOptions_FavoritesPage() {
        return page.locator("xpath=//*[@id=\"dialogs\"]/div/div[2]/div/div[2]/div/section/fieldset/label/span");
    }
    public void vans_QuickShopSizeOptions_FavoritesPage_Click() {
        vans_QuickShopSizeOptions_FavoritesPage().first().click();
    }
    private Locator vans_QuickShopAddToCartButton_FavoritesPage() {
        return page.locator("xpath=//div[@data-test-id='vf-dialog-content']//span[text()='Add to Cart']//ancestor::button"); // Final Add to Cart button
    }
    public void vans_QuickShopAddToCartButton_FavoritesPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_QuickShopAddToCartButton_FavoritesPage().click();
    }
    private Locator vans_QuickShopIncQuantity_FavoritesPage() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("^Increase")));
    }
    public void vans_QuickShopIncQuantity_FavoritesPage_Click() {

        vans_QuickShopIncQuantity_FavoritesPage().click();
        page.waitForTimeout(DEFAULT_WAIT);
    }

    private Locator vans_QuickShopViewCartButton_FavoritesPage() {
        return page.locator("[data-test-id=\"mini-cart-view-cart-button\"]");
    }
    public void vans_QuickShopViewCartButton_FavoritesPage_Click() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_QuickShopViewCartButton_FavoritesPage().click();
    }

    private Locator vans_removeProduct_FavoritesPage() {
        return page.locator("[data-test-id=\"base-grid\"] [data-test-id=\"base-button\"]");
    }
    public void vans_removeProduct_FavoritesPage_Click() {
        page.waitForTimeout(3000);
        vans_removeProduct_FavoritesPage().click();
    }
    private Locator vans_removeProductNotification_FavoritesPage() {
        return page.getByText("This item has been removed");
    }
    public void vans_removeProductNotification_FavoritesPage_Visible() {
        page.waitForTimeout(3000);
        assertThat(vans_removeProductNotification_FavoritesPage()).isVisible();
    }
    private Locator vans_NoItem_FavoritesPage() {
        return page.getByText("There are no items in your");
    }
    public void vans_NoItem_FavoritesPage_Visible() {
        page.waitForTimeout(3000);
        assertThat(vans_NoItem_FavoritesPage()).isVisible();
    }
    }

