package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.*;
import utils.PlaywrightFactory;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class vans_PLP_Steps {

    Page page;
    private vans_homePage vansHomePage;
    private vans_categoryPage vansCategoryPage;
    private vans_productListpage vansProductListpage;
    private vans_productDetailsPage productDetailsPage;
    // single definition of flag
    private static boolean flag = false;

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

    private vans_homePage getVansHomePage() {
        if (vansHomePage == null) {
            vansHomePage = new vans_homePage(getPage());
        }
        return vansHomePage;
    }

    private vans_categoryPage getVansCategoryPage() {
        if (vansCategoryPage == null) {
            vansCategoryPage = new vans_categoryPage(getPage());
        }
        return vansCategoryPage;
    }

    private vans_productListpage getVansProductListpage() {
        if (vansProductListpage == null) {
            vansProductListpage = new vans_productListpage(getPage());
        }
        return vansProductListpage;
    }

    @When("User navigates to the PLP for {string} category")
    public void userNavigatesToThePLPForCategory(String arg0) {
        getVansHomePage().click_vansHamburgerMenu();
        getVansCategoryPage().click_vansMensHeader();
        getVansCategoryPage().click_mensShoeCategory();
        getVansCategoryPage().click_viewAllHeader();

        List<String> validTitles = Arrays.asList("Shoes & Sneakers", "Shoes");
        String actualTitle = getVansProductListpage().check_ShoesAndSneaker_Title();

        assertTrue(validTitles.stream().anyMatch(actualTitle::contains),
                "Expected one of " + validTitles + ", but got: " + actualTitle);
    }

    @Then("Products should be sorted in ascending price order")
    public void productsShouldBeSortedInAscendingPriceOrder() {
        assertTrue(getVansProductListpage().check_sortedProducts());
    }

    @Given("user clicks on search button")
    public void userClicksOnSearchButton() {
        getVansProductListpage().click_searchOption_inAddressPage();
    }

    @And("enter the text {string} in search field")
    public void enterTheTextInSearchField(String SearchInput) {
        getVansHomePage().enter_searchInputField(SearchInput);
        page.keyboard().press("Enter");
    }

    @And("user selects Sort option in PLP page")
    public void userSelectsSortOptionInPLPPage() {
        getVansProductListpage().click_vans_FilterOption();
    }

    @And("user select {string} sort option")
    public void userSelectSortOption(String sortType) {
        getVansProductListpage().click_vans_Sort_PriceLowToHigh();
        flag = true; // works with boolean flag
    }

    @When("User navigates to PDP page by selecting a product")
    public void userNavigatesToPDPPageBySelectingAProduct() {
        getVansProductListpage().click_SelectProductinPLP(flag);
    }

    @And("User navigates to PDP page by selecting {string} product")
    public void userNavigatesToPDPPageBySelectingProduct(String arg0) {
        if (arg0.equals("a")) {
            getVansProductListpage().click_SelectProductinPLP(flag);
//            getProductDetailsPage().clickFavoriteIcon_FavoritesPage();
            getProductDetailsPage().click_SizeDropDownOption_PDP();
            getProductDetailsPage().click_SelectSize_PDP();

            getProductDetailsPage().click_closeDialog_PDP();
            getProductDetailsPage().click_addTocartButton_PDP();
//            getProductDetailsPage().click_incrementQty_MiniCart();
        } else if (arg0.equals("multiple")) {
            getVansProductListpage().click_SelectProductinPLP(flag);
            getProductDetailsPage().clickFavoriteIcon_FavoritesPage();
            getProductDetailsPage().click_SizeDropDownOption_PDP();
            getProductDetailsPage().click_SelectSize_PDP();
            getProductDetailsPage().click_closeDialog_PDP();
            getProductDetailsPage().click_addTocartButton_PDP();
            getProductDetailsPage().click_incrementQty_MiniCart();
            getProductDetailsPage().vans_closeMiniCartWindow_Click();
            getProductDetailsPage().NavigateBack();

            // explicitly select the second product by name
            getVansProductListpage().click_SelectSecondProductinPLP(flag);
            getProductDetailsPage().click_SizeDropDownOption_PDP();
            getProductDetailsPage().click_SelectSize_PDP();
            getProductDetailsPage().click_closeDialog_PDP();
            getProductDetailsPage().click_addTocartButton_PDP();
            getProductDetailsPage().click_vans_incrementQtyButtonForSecondProduct_MiniCart();
            getProductDetailsPage().click_vans_decrementQtyButtonForSecondProduct_MiniCart();
        }
    }
}