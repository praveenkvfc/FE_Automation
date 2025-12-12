package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.VANS.US.*;
import io.cucumber.java.en.When;
import utils.PlaywrightFactory;

public class vans_Cart_Steps {

    private Page page;
    private vans_cartPage getVansCartPage;
    private vans_checkoutPage getVansCheckoutPage;
    private vans_paypal_paymentPage getVansPaypalPaymentPage;



    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_paypal_paymentPage getVans_Paypal_page() {
        if (getVansPaypalPaymentPage == null) {
            getVansPaypalPaymentPage = new vans_paypal_paymentPage(getPage(),"PAYPAL");
        }
        return getVansPaypalPaymentPage;
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

    private vans_checkoutPage getvansCheckoutPage() {
        if (getVansCheckoutPage == null) {
            getVansCheckoutPage = new vans_checkoutPage(getPage());
        }
        return getVansCheckoutPage;
    }

    @Then("User proceeds to checkout")
    public void userProceedsToCheckout() {
        getVansCartPage().Click_CheckoutButton_vans();
    }
    //QA-Kajal kabade
    @Then("User selects Pick up Instore option")
    public void userSelectsPickUpInstoreOption() {
        if (ConfigReader.get("brand").equals("vans") || ConfigReader.get("brand").equals("tnf")) {
            if (ConfigReader.get("region").equals("us")) {
                getVansCartPage().vans_PickupInStoreSelect_CartPage();
            }
        }
    }

    //QA-Kajal kabade
    @And("User able to find and select the store")
    public void userAbleToFindAndSelectTheStore() {
        if (ConfigReader.get("brand").equals("vans") || ConfigReader.get("brand").equals("tnf")) {
            if (ConfigReader.get("region").equals("us")) {
                getVansCartPage().vans_PickupInStoreWindow_Distance_Select_CartPage();
                getVansCartPage().vans_PickupInStoreWindow_enterPostalCodeFill_CartPage();
                getVansCartPage().vans_PickupInStoreWindow_FindStore_CartPage_Click();
                getVansCartPage().vans_PickupInStoreWindow_SelectStore_CartPage_Click();
            }
        }
    }


     @Then("User navigates to {string} page from cart page")
    public void userNavigatesToPageFromCartPage(String pageName) {
        if (pageName.equalsIgnoreCase("favourites")) {
            if (ConfigReader.get("brand").equals("vans")) {
                getHeaderPage().vans_Profile_MyAccount_Click();
                getHeaderPage().vans_Favorites_MyAccount_Click();
            }else if(ConfigReader.get("brand").equals("tnf")){
                getHeaderPage().tnf_Profile_MyAccount_Click();
                getHeaderPage().tnf_Favorites_MyAccount_Click();
               }
        }
        else if (pageName.equalsIgnoreCase("Save for later")) {
            System.out.println("=======================================");
            System.out.println("Im here in save for later page");
            System.out.println("=======================================");
            getVansCartPage().vans_SaveForLaterClick_CartPage();
        } else {
            throw new IllegalArgumentException("Unsupported navigation target: " + pageName);
        }
}



    @Then("User places the order by clicking pay now using paypal in cart page")
    public void userPlacesTheOrderByClickingPayNowUsingPaypalInCartPage() {
        System.out.println("====================");
        System.out.println("======Moved to top of cart page==============");
       // System.out.println("======Moved to top of cart page==============");
        getVansCartPage().vans_paypal_CartPage_Click();
        getVans_Paypal_page().complete_paypal_payment("Cart paypal");
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
    @When("User able to select save for later option")
    public void userAbleToSelectSaveForLaterOption() {
    }

    @And("User able to move product to cart from save for later page")
    public void userAbleToMoveProductToCartFromSaveForLaterPage() {
    }

    @And("User should be able to {string} the quantity in {string} page")
    public void userShouldBeAbleToTheQuantityInPage(String arg0, String arg1) {
    }

    @And("User apply promo code in the cart page")
    public void userApplyPromoCodeInTheCartPage() {
        getvansCheckoutPage().check_GotAPromoCodeinput();
        getvansCheckoutPage().check_GotAPromoCodeaply();
    }


}
