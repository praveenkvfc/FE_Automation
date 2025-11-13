package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import static utils.Constants.SHORT_WAIT;

public class vans_MyAccountPage {

    private Page page;

    public vans_MyAccountPage(Page page) {
        this.page = page;
    }

    private Locator vans_MyAccountHeader() {
        return page.locator("xpath=//div[@class='grow overflow-y-auto p-4 lg:p-6 p-6']");

    }

//    private Locator vans_profile() {
//        return page.locator("xpath=//a[@href='/en-us/account/profile']");
//    }

    private Locator vans_favourites() {
        return page.locator("xpath=//div[@id='dialogs']//li[2]//a[1]]");
    }

    private Locator vans_addressBook() {
        return page.locator("xpath=//div[@id='dialogs']//li[3]//a[1]");
    }

    private Locator vans_orderHistory() {
        return page.locator("xpath=//div[@id='dialogs']//li[4]//a[1]");
    }

    private Locator vans_creditCards() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Credit Cards"));
    }

    private Locator vans_VansFamily() {
        return page.locator("xpath=//div[@id='dialogs']//li[6]//a[1]");
    }

    private Locator vans_Signout() {
        return page.locator("xpath=//button[@class='vf-button vf-button-secondary vf-button-sm dark:vf-button-secondary w-full']");
    }


    public void setVans_MyAccountHeader() {
        vans_MyAccountHeader().click();
    }

    public void setVans_creditCards() {
        vans_creditCards().scrollIntoViewIfNeeded();
        vans_creditCards().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        vans_creditCards().click();

    }
    private Locator vans_CloseWindow_MyAccount() {
        return page.locator("[data-test-id=\"vf-dialog-close\"]");
    }
public void vans_CloseWindow_MyAccount_Click(){
    vans_CloseWindow_MyAccount().click();
}
}
