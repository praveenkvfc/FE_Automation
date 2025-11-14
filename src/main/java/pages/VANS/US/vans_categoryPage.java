package pages.VANS.US;

import com.microsoft.playwright.Locator; import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import static utils.Constants.*;

public class vans_categoryPage {

    private Page page;

    public vans_categoryPage(Page page) {
        this.page = page;
    }

    // Wait for and get the dialog panel
    private Locator getDialogPanel() {
        return page.locator("vf-panel[role='dialog']");
    }

    // Get Mens button from within the panel
    private Locator vans_mensButton() {
        return  page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Mens").setExact(true));
    }

    public void click_vansMensHeader() {
        vans_mensButton().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        vans_mensButton().click();
        System.out.println("Clicked Mens button");
        page.waitForTimeout(3000);
    }

    private Locator vans_mensShoeCategory() {

        return  page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Shoes"));
    }

    protected Locator vans_viewall_Header() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View All"));
    }

    public void click_mensShoeCategory() {
        System.out.println("Clicking Mens Shoe Category...");

        vans_mensShoeCategory().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );

        vans_mensShoeCategory().click();
        System.out.println("Clicked Mens Shoe Category");
        page.waitForTimeout(2000);
    }

    public void click_viewAllHeader() {
        System.out.println("Clicking View All Header...");
        vans_viewall_Header().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );

        vans_viewall_Header().click();
        System.out.println("✅ Clicked View All Header");
        page.waitForTimeout(2000);
    }
}
