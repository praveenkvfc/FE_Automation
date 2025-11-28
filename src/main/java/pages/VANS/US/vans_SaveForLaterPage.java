package pages.VANS.US;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.RetryUtility;

import static utils.Constants.*;

public class vans_SaveForLaterPage {
    private Page page;

    public vans_SaveForLaterPage(Page page) {
        this.page = page;
    }

    private Locator addToCart_SaveForLater() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart").setExact(true));

    }

    public void clickAddToCartFromSaveForLater() {
        page.waitForTimeout(SHORT_WAIT);
        System.out.println("Im here to see item moved from save for later page to cart");
        addToCart_SaveForLater().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, addToCart_SaveForLater(), "CLICK");
        page.waitForTimeout(MEDIUM_WAIT);
        page.evaluate("window.scrollTo(0, 0);");
        page.waitForTimeout(SHORT_WAIT);
    }

    private Locator addToCart_Multiple_SaveForLater() {
        return page.getByLabel("Saved For Later").locator("div").filter(new Locator.FilterOptions().setHasText("Classic Slip-On Checkerboard Shoe $60.00Color: Black/Off WhiteSize: 3.5 Boys =")).locator("[data-test-id=\"move-to-cart\"]");
    }
    public void clickAddToCart_Multiple_SaveForLater() {
        page.waitForTimeout(SHORT_WAIT);
        System.out.println("Im here to see item moved from save for later page to cart");
        addToCart_Multiple_SaveForLater().scrollIntoViewIfNeeded();
        RetryUtility.gradualScrollToBottomUntilLocator(page, addToCart_Multiple_SaveForLater(), "CLICK");
        page.waitForTimeout(MEDIUM_WAIT);
        page.evaluate("window.scrollTo(0, 0);");
        page.waitForTimeout(SHORT_WAIT);
    }
}
