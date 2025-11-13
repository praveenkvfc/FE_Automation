package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.cucumber.java.an.E;
import utils.PaymentDataReader;
import utils.UserDetailsReader;

import static utils.Constants.*;

public class vans_addressPage {

    private Page page;
    PaymentDataReader paymentDataReader = new PaymentDataReader();
    UserDetailsReader user = UserDetailsReader.getInstance(REGISTERED_USER_ALL);

    public vans_addressPage(Page page) {
        this.page = page;
    }

    private Locator vans_MyAddressPage() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Address Book"));
    }

    private Locator vans_AddBillingAddressButton() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add Shipping Address"));
    }
    private Locator vans_SaveThisAddressButton() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save this address"));
    }

    private Locator vans_BillingAddressLabel() {
//        return page.locator("//h2[@class='title-2 mb-2']");
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Billing Address"));
    }


    public void click_vansAddressPage() {
        try {
            Thread.sleep(6000);
            vans_MyAddressPage().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_WAIT)
            );
            vans_MyAddressPage().scrollIntoViewIfNeeded();
            vans_MyAddressPage().click(new Locator.ClickOptions().setForce(true));
            System.out.println("Clicked My Address book in My account");
            Thread.sleep(6000);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void click_vansAddBillingAddressButton() {
        try {
            Thread.sleep(4000);
            System.out.println("scrolling the page");
            page.evaluate("window.scrollBy(0,500)");
            vans_AddBillingAddressButton().scrollIntoViewIfNeeded();
            vans_AddBillingAddressButton().waitFor(new Locator.WaitForOptions()
                    .setTimeout(DEFAULT_WAIT)
                    .setState(WaitForSelectorState.VISIBLE)
            );
            if (vans_AddBillingAddressButton().isEnabled() && vans_AddBillingAddressButton().isVisible()) {
                System.out.println("add billing address button  button is visible");
                vans_AddBillingAddressButton().click(new Locator.ClickOptions().setForce(true));
            } else {
                System.out.println("add billing address button is not visible");
            }
        }catch (Exception e)
        {
            e.getMessage();
        }

    }

    public String Verify_BillingAddressLabel() {
        vans_BillingAddressLabel().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        return vans_BillingAddressLabel().textContent().trim();
    }

    public void click_vans_SaveThisAddressButton() {
        vans_SaveThisAddressButton().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        page.waitForTimeout(DEFAULT_WAIT);
        vans_SaveThisAddressButton().click();

    }

}
