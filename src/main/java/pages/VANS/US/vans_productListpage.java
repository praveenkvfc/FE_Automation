package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.SortUtility;

import java.awt.*;
import java.util.*;
import java.util.List;

import static utils.Constants.DEFAULT_WAIT;
import static utils.Constants.SHORT_WAIT;

public class vans_productListpage {
    private Page page;
    Random random;

    public vans_productListpage(Page page) {
        this.page = page;
        this.random = new Random();
    }

    protected Locator ShoeAndSneakers_Title() {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Shoes & Sneakers"));
    }

    private Locator vans_FilterOption_PLP() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Show Filters"));

    }


    private Locator vans_product_in_PLP() {
//        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(ProductName));
        return page.locator(".relative.w-full > a").first();
    }

    public void click_SelectProductinPLP(boolean isFilterApplied) {
        if(isFilterApplied) {
            vans_product_in_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_ProductWithoutFilter_PLP().click();
        }
        else {
            vans_ProductWithoutFilter_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_ProductWithoutFilter_PLP().click();
        }
    }
    private Locator vans_ProductWithoutFilter_PLP(){
        return page.locator("div:nth-child(4) > div > .relative.overflow-hidden > .max-w-full > .flex > div > a").first();

    }

    public void click_vans_FilterOption() {
        page.waitForTimeout(SHORT_WAIT);
        vans_FilterOption_PLP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        vans_FilterOption_PLP().click();
    }

    public void click_vans_Sort_PriceLowToHigh() {
        vans_Sort_pricelowtohigh_PLP().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
//        vans_Sort_pricelowtohigh_PLP().hover();
        vans_Sort_pricelowtohigh_PLP().click();
    }

    private Locator vans_Sort_pricelowtohigh_PLP() {
        return page.getByText("Prices: Low to high");
    }

    private Locator vans_searchOption_addressPage() {
         return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open search"));
        //return page.locator("xpath=//*[@id=\"__nuxt\"]/div[1]/div/div[2]/header/div/div/section[2]/section/button[3]/span/i");
    }
    public void click_searchOption_inAddressPage() {
        page.waitForTimeout(DEFAULT_WAIT);
        vans_searchOption_addressPage().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(DEFAULT_WAIT)
        );
        vans_searchOption_addressPage().click(new Locator.ClickOptions().setForce(true));
        page.waitForTimeout(2000);

    }

    public String check_ShoesAndSneaker_Title() {
        ShoeAndSneakers_Title().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        return ShoeAndSneakers_Title().textContent().trim();

    }

    // Better: Create locator methods that return Locator objects
    protected Locator getProductCards() {
        return page.locator("xpath=//*[@id=\"__nuxt\"]/div[1]/div/main/div[3]/div[3]/div/div[2]/div[1]");
    }

    public record ProductInfo(String name, String price, int index) {
        @Override
        public String toString() {
            return String.format("Product[%d]: %s - %s", index, name, price);
        }
    }

    public boolean check_sortedProducts() {
        SortUtility SU = new SortUtility(page);
        SU.printAllProducts();
        boolean isSorted = SU.validatePriceSorting("low_to_high", page, getProductCards());
        return true;
    }
    //QA-Kajal kabade
    private Locator tnfUs_searchField() {
        return page.locator("xpath=//input[@placeholder='Search']");
    }
    public void click_tnfUs_searchField() {
        page.waitForTimeout(DEFAULT_WAIT);
        tnfUs_searchField().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(DEFAULT_WAIT)
        );
        tnfUs_searchField().click(new Locator.ClickOptions().setForce(true));
        page.waitForTimeout(2000);

    }

    private Locator tnf_product_in_PLP() {
        return page.locator("[data-test-id='product-card']").first();
    }

    public void click_Tnf_SelectProductinPLP(boolean isFilterApplied) {
        if(isFilterApplied) {
            tnf_product_in_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
//            tnf_ProductWithoutFilter_PLP().click();
        }
        else {
            tnf_product_in_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            tnf_product_in_PLP().click();
        }
    }

}
