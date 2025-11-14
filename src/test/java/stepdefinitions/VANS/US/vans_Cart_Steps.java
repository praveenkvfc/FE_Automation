package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.VANS.US.vans_cartPage;
import pages.VANS.US.vans_productDetailsPage;
import utils.PlaywrightFactory;

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

}
