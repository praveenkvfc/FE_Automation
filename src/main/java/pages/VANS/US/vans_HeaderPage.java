package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.UserDetailsReader;

import static utils.Constants.DEFAULT_WAIT;
import static utils.Constants.REGISTERED_USER_ALL;

public class vans_HeaderPage {
    private Page page;
    private UserDetailsReader user = UserDetailsReader.getInstance(REGISTERED_USER_ALL);

    public vans_HeaderPage(Page page) {
        this.page = page;
    }

    // Profile button
    private Locator vans_Profile_MyAccount() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("My Account"));
    }

    public void vans_Profile_MyAccount_Click() {
        Locator profileButton = vans_Profile_MyAccount();
        profileButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000)
        );
        profileButton.click(new Locator.ClickOptions().setForce(true));
    }

    // Favorites link
    private Locator vans_Favorites_MyAccount() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Favorites"));
    }

    public void vans_Favorites_MyAccount_Click() {
        Locator favoritesLink = vans_Favorites_MyAccount();
        favoritesLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000)
        );
        favoritesLink.click(new Locator.ClickOptions().setForce(true));
    }

    // Order History link
    private Locator Vans_Orderhistory_icon() {
        return page.locator("a:has-text(\"Order History\")");
    }

    public void click_vansOrderhistoryButton() {
        Locator orderHistoryLink = Vans_Orderhistory_icon();
        orderHistoryLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000)
        );
        orderHistoryLink.click(new Locator.ClickOptions().setForce(true));
    }

    // Navigation method
    public void navigateFromProfileTo(String pageName) {
        vans_Profile_MyAccount_Click();

        switch (pageName.toLowerCase()) {
            case "favourites":
                vans_Favorites_MyAccount_Click();
                break;
            case "order history":
                click_vansOrderhistoryButton();
                break;
            default:
                throw new IllegalArgumentException("Unsupported navigation target: " + pageName);
        }
    }

    // QA- Kajal kabade
    private Locator tnf_MyAccount() {
        return page.locator("xpath=(//a[text()='My Account '])[1]");
    }

    public void tnf_Profile_MyAccount_Click() {
        Locator profileButton = tnf_MyAccount();
        profileButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000)
        );
        profileButton.click(new Locator.ClickOptions().setForce(true));
    }

    // Favorites link
    private Locator tnf_Favorites_MyAccount() {
        return page.locator("xpath=//a[text()='Favorites']");
    }

    public void tnf_Favorites_MyAccount_Click() {
        Locator favoritesLink = tnf_Favorites_MyAccount();
        favoritesLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000)
        );
        favoritesLink.click(new Locator.ClickOptions().setForce(true));
    }

    private Locator tnf_MyOrder_MyAccount() {
        return page.locator("xpath=//a[text()='My Orders']");
    }

    private Locator tnf_ViewOrderDetails() {
        return page.locator("xpath=(//span[text()='View Order Details'])[1]");
    }

    public void tnf_MyOrder_MyAccount_Click() {
        Locator myOrderLink = tnf_MyOrder_MyAccount();
        myOrderLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000)
        );
        myOrderLink.click(new Locator.ClickOptions().setForce(true));
        tnf_ViewOrderDetails().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000)
        );
        tnf_ViewOrderDetails().click(new Locator.ClickOptions().setForce(true));
    }

}