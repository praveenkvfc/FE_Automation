package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.vans_FavoritesPage;
import pages.VANS.US.vans_cartPage;
import utils.PlaywrightFactory;
import pages.VANS.US.vans_productDetailsPage;

public class Vans_PDP_Steps {
    private Page page;
    private vans_productDetailsPage productDetailsPage;
    private vans_cartPage cartPage;
    private vans_FavoritesPage favoritesPage;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_productDetailsPage getProductDetailsPage() {
        if (productDetailsPage == null) {
            productDetailsPage = new vans_productDetailsPage(getPage());
        }
        return productDetailsPage;
    }

    private vans_cartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new vans_cartPage(getPage());
        }
        return cartPage;
    }

    // --- Step Definitions ---

    @When("User navigates to the Cart page")
    public void userNavigatesToTheCartPage() {
       getProductDetailsPage().check_GotAPromoCode_isVisible();

    }

    @Then("User should click the {string} CTA in Mini Cart")
    public void userShouldClickTheCTAInMiniCart(String buttonName) {
        getProductDetailsPage().click_viewCartButton();
    }

    @And("User clicks on favorite icon and navigates to favorites page")
    public void userClicksOnFavoriteIconAndNavigatesToFavoritesPage() {
        getProductDetailsPage().vans_FavProductName_PDP_Visible();
        getProductDetailsPage().clickFavoriteAndGoToFavoritesPage();
    }

    @And("User adds {string} product to cart")
    public void userAddsProductToCart(String productType) {
        System.out.println("Adding product to cart: " + productType);
        getProductDetailsPage().click_addTocartButton_PDP();
    }


    @And("User clicks on favorite icon")
    public void userClicksOnFavoriteIcon() {
        getProductDetailsPage().clickFavoriteIcon_FavoritesPage();
    }

    @And("User selects the size for a {string} product")
    public void userSelectsTheSizeForAProduct(String productType) {
        System.out.println("Selecting size for product: " + productType);
        getProductDetailsPage().click_SizeDropDownOption_PDP();
        getProductDetailsPage().click_SelectSize_PDP();
        getProductDetailsPage().click_closeDialog_PDP();
    }


    @And("User increments product quantity in Mini Cart")
    public void userIncrementsProductQuantityInMiniCart() {
        getProductDetailsPage().click_incrementQty_MiniCart();
    }

    @And("User decrements product quantity in Mini Cart")
    public void userDecrementsProductQuantityInMiniCart() {
        getProductDetailsPage().click_decrementQty_MiniCart();
    }

    @And("User should be able to close the Mini Cart")
    public void userShouldBeAbleToCloseTheMiniCart() {
        getProductDetailsPage().vans_closeMiniCartWindow_Click();
    }
}