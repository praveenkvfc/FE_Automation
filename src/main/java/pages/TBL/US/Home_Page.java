package pages.TBL.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import static utils.Constants.DEFAULT_WAIT;

public class Home_Page {
    private final Page page;

    public Home_Page(Page page) {
        this.page = page;
    }

    private Locator getWelcomeIcon() {
        return page.locator("xpath=//*[@id=\"__nuxt\"]/div[1]/div/div[1]/button");
    }

    private Locator getCreateAccountButton() {
        return page.locator("xpath=//*[@id=\"dialogs\"]//button[.=\"Create Account\"]");
    }

    public void clickWelcomeIcon() {
        getWelcomeIcon().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        getWelcomeIcon().click();
    }

    public void clickCreateAccountButton() {
        getCreateAccountButton().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        getCreateAccountButton().click();
    }
}
