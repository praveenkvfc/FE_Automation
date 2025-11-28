package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.VANS.US.vans_HeaderPage;
import pages.VANS.US.vans_SaveForLaterPage;
import utils.PlaywrightFactory;

public class vans_SaveForLater_Steps {
    private Page page;
    private vans_SaveForLaterPage saveForLaterPage;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
        }
        return page;
    }

    private vans_SaveForLaterPage getSaveForLaterPage() {
        if (saveForLaterPage == null) {
            saveForLaterPage = new vans_SaveForLaterPage(getPage());
        }
        return saveForLaterPage;
    }

    private vans_HeaderPage headerPage;

    private vans_HeaderPage getHeaderPage() {
        if (headerPage == null) {
            headerPage = new vans_HeaderPage(getPage());
        }
        return headerPage;
    }

//    @And("User clicks on Add to Cart button from Save for Later page")
//    public void userClicksOnAddToCartFromSaveForLater() {
//        getSaveForLaterPage().clickAddToCartFromSaveForLater();
//    }

    @Then("Item should be moved back to cart from Save for Later")
    public void itemShouldBeMovedBackToCartFromSaveForLater() {
   getSaveForLaterPage().clickAddToCartFromSaveForLater();
        System.out.println("Confirmed item moved back to cart from Save for Later.");
    }

    @And("User clicks on Add to Cart button from Save for Later page for {string} product")
    public void userClicksOnAddToCartButtonFromSaveForLaterPageForProduct(String arg0) {
        if (arg0.equals("a")) {
            // Call your existing single-product method
            getSaveForLaterPage().clickAddToCartFromSaveForLater();

        } else if (arg0.equals("multiple")) {
            // Call your existing multiple-product method
            getSaveForLaterPage().clickAddToCart_Multiple_SaveForLater();
        }

    }
}