package stepdefinitions.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.*;
import utils.PlaywrightFactory;
import utils.SortUtility;

import static org.testng.Assert.assertTrue;
import static utils.Constants.DEFAULT_WAIT;

public class vans_PLP_Steps {

    Page page;
    private vans_homePage vansHomePage;
    private vans_categoryPage vansCategoryPage;
    private vans_productListpage vansProductListpage;
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
        String ExpectedTitle = "Shoes & Sneakers";
        String actualTitle = getVansProductListpage().check_ShoesAndSneaker_Title();
        assertTrue(actualTitle.contains(ExpectedTitle), "Expected: " + ExpectedTitle + ", but got: " + actualTitle);
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
        flag=true;
//        getVansProductListpage().printAllProductPrices();
    }

    @When("User navigates to PDP page by selecting a product")
    public void userNavigatesToPDPPageBySelectingAProduct() {
       // getVansProductListpage().vans_ProductWithoutFilter_PLP_Click();
        getVansProductListpage().click_SelectProductinPLP(flag);


    }
}
