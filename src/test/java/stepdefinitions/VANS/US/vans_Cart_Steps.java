package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.VANS.US.vans_FavoritesPage;
import pages.VANS.US.vans_cartPage;
import pages.VANS.US.vans_productDetailsPage;
import utils.PlaywrightFactory;
import pages.VANS.US.vans_HeaderPage;
public class vans_Cart_Steps {

    private Page page;
    private vans_cartPage getVansCartPage;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_cartPage getVansCartPage() {
        if (getVansCartPage == null) {
            getVansCartPage = new vans_cartPage(getPage());
        }
        return getVansCartPage;
    }
    private vans_HeaderPage headerPage;

    private vans_HeaderPage getHeaderPage() {
        if (headerPage == null) {
            headerPage = new vans_HeaderPage(getPage());
        }
        return headerPage;
    }

    private vans_FavoritesPage vansFavoritesPage;

    private vans_FavoritesPage getFavoritesPage() {
        if (vansFavoritesPage == null) {
            vansFavoritesPage = new vans_FavoritesPage(getPage());
        }
        return vansFavoritesPage;
    }

    @Then("User proceeds to checkout")
    public void userProceedsToCheckout() {
        getVansCartPage().Click_CheckoutButton_vans();
    }

    @Then("User selects Pick up Instore option")
    public void userSelectsPickUpInstoreOption() {
        getVansCartPage().vans_PickupInStoreSelect_CartPage();
    }

    @And("User able to find and select the store")
    public void userAbleToFindAndSelectTheStore() {
        getVansCartPage().vans_PickupInStoreWindow_Distance_Select_CartPage();
        getVansCartPage().vans_PickupInStoreWindow_enterPostalCodeFill_CartPage();
        getVansCartPage().vans_PickupInStoreWindow_FindStore_CartPage_Click();
        getVansCartPage().vans_PickupInStoreWindow_SelectStore_CartPage_Click();
    }

       @Then("User navigates to {string} page from cart page")
    public void userNavigatesToPageFromCartPage(String pageName) {
        getHeaderPage().vans_Profile_MyAccount_Click();

        if (pageName.equalsIgnoreCase("favourites")) {
            getHeaderPage().vans_Favorites_MyAccount_Click();
        } else {
            throw new IllegalArgumentException("Unsupported navigation target: " + pageName);
        }



}

    @And("User clicks on save later option in cart page")
    public void userClicksOnSaveLaterOptionInCartPage() {
        getVansCartPage().vans_SaveForLaterClick_CartPage();
    }


    @And("User should be able to increase the quantity in cart page")
    public void userShouldBeAbleToIncreaseTheQuantityInCartPage() {
        getVansCartPage().vans_increaseItemFromCartPage_Click();
        //getVansCartPage().vans_IncItemToastMessage_CartPage_Visible();
    }
}
