package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.PlaywrightFactory;

public class vans_LoginPage {
    private Page page;

    public vans_LoginPage(Page page) {
        this.page = page;
    }

    private Locator vans_LoginSuccessMessage() {
        return page.locator("xpath=//h2[normalize-space()='Welcome to the Vans Family']");
    }
}
