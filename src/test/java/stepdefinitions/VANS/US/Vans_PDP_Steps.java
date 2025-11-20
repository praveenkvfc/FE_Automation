package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.vans_FavoritesPage;
import pages.VANS.US.vans_SignInSignUp_Page;
import pages.VANS.US.vans_cartPage;
import utils.PlaywrightFactory;
import pages.VANS.US.vans_productDetailsPage;

public class Vans_PDP_Steps {
    private Page page;
    private vans_productDetailsPage vansProductDetialsPage;
    private vans_cartPage vansCartPage;
    private vans_FavoritesPage vansFavoritesPage;
    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_productDetailsPage getVansProductDetialsPage() {
        if (vansProductDetialsPage == null) {
            vansProductDetialsPage = new vans_productDetailsPage(getPage());
        }
        return vansProductDetialsPage;
    }

    private vans_cartPage getVansCartPage() {
        if (vansCartPage == null) {
            vansCartPage = new vans_cartPage(getPage());
        }
        return vansCartPage;
    }

    @When("User navigates to the Cart page")
    public void userNavigatesToTheCartPage() {
        getVansProductDetialsPage().check_GotAPromoCode_isVisible();

    }

    @Then("User should click the {string} CTA in Mini Cart")
    public void userShouldClickTheCTAInMiniCart(String arg0) {
        getVansProductDetialsPage().click_viewCartButton();


    }

    @And("User clicks on favorite icon and navigates to favorites page")
    public void userClicksOnFavoriteIconAndNavigatesToFavoritesPage() {
        getVansProductDetialsPage().vans_FavProductName_PDP_Visible();
        getVansProductDetialsPage().clickFavoriteAndGoToFavoritesPage();

    }

            @And("User adds {string} product to cart")
        public void userAddsProductToCart(String searchItem) {
//            getVansProductDetialsPage().click_SizeDropDownOption_PDP();
//            getVansProductDetialsPage().click_SelectSize_PDP(searchItem);
            getVansProductDetialsPage().click_addTocartButton_PDP();
        }

    @And("User clicks on favorite icon")
    public void userClicksOnFavoriteIcon() {
        getVansProductDetialsPage().clickFavoriteIcon_FavoritesPage();
    }
}

