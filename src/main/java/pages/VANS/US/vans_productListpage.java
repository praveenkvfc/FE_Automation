
package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.SortUtility;

import java.util.Random;

import static utils.Constants.DEFAULT_WAIT;
import static utils.Constants.SHORT_WAIT;

public class VansProductListPage {
    private final Page page;
    private final Random random;

    public VansProductListPage(Page page) {
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
        return page.locator(".relative.w-full > a").first();
    }

    public void click_SelectProductinPLP(boolean isFilterApplied) {
        if (isFilterApplied) {
            vans_product_in_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_ProductWithoutFilter_PLP().click();
        } else {
            vans_ProductWithoutFilter_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_ProductWithoutFilter_PLP().click();
        }
    }

    private Locator vans_SecondProductWithoutFilter_PLP() {
        return page.locator("div:nth-child(3) > div > .relative.overflow-hidden > .max-w-full > .flex > div > a").first();
    }

    public void click_SelectSecondProductinPLP(boolean isFilterApplied) {
        if (isFilterApplied) {
            vans_product_in_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_ProductWithoutFilter_PLP().click();
        } else {
            vans_SecondProductWithoutFilter_PLP().scrollIntoViewIfNeeded();
            vans_SecondProductWithoutFilter_PLP().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_SecondProductWithoutFilter_PLP().click();
        }
    }

    private Locator vans_ProductWithoutFilter_PLP() {
        return page.locator("div:nth-child(2) > div > .relative.overflow-hidden > .max-w-full > .flex > div > a").first();
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
        vans_Sort_pricelowtohigh_PLP().click();
    }

    private Locator vans_Sort_pricelowtohigh_PLP() {
        return page.getByText("Prices: Low to high");
    }

    private Locator vans_searchOption_addressPage() {
        System.out.println("inside search");
        return page.locator("//section[@class='grid cols-[1fr_auto_1fr] w-full <lg:hidden']/section/button[3]");
    }

    public void click_searchOption_inAddressPage() {
