package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.VANS.US.vans_FavoritesPage;
import pages.VANS.US.vans_addressPage;
import pages.VANS.US.vans_cartPage;
import pages.VANS.US.vans_productDetailsPage;
import utils.PlaywrightFactory;


public class vans_Favorites_Steps {
    private Page page;
private vans_FavoritesPage vansFavoritesPage;
    private vans_productDetailsPage vansProductDetialsPage;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

            private vans_FavoritesPage getFavoritesPage(){
                if (vansFavoritesPage == null) {
                    vansFavoritesPage = new vans_FavoritesPage(getPage());
                }
                return vansFavoritesPage;
            }


            @Then("User should be able to see the item in my favorites page")
            public void userShouldBeAbleToSeeTheItemInMyFavoritesPage () {
                getFavoritesPage().vans_MyFavoritesHeading_FavoritesPage_Visible();
                getFavoritesPage().vans_ProductName_FavoritesPage_Visible();
                getFavoritesPage().vans_ProductCount_FavoritesPage_Visible();


            }

    @And("User should be able to remove the item from favorites page")
    public void userShouldBeAbleToRemoveTheItemFromFavoritesPage() {
        getFavoritesPage().vans_removeProduct_FavoritesPage_Click();
        getFavoritesPage().vans_removeProductNotification_FavoritesPage_Visible();
        getFavoritesPage().vans_NoItem_FavoritesPage_Visible();
    }

    @And("User add product to cart from favourites page")
    public void userAddProductToCartFromFavouritesPage() {
        getFavoritesPage().vans_AddToQuickShop_FavoritesPage_Click();
        getFavoritesPage().vans_QuickShopSizeOptions_FavoritesPage_Click();
        getFavoritesPage().vans_QuickShopAddToCartButton_FavoritesPage_Click();
        getFavoritesPage().vans_QuickShopIncQuantity_FavoritesPage_Click();
        getFavoritesPage().vans_QuickShopViewCartButton_FavoritesPage_Click();
    }
}


