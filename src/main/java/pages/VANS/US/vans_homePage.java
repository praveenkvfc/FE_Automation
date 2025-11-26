package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import static utils.Constants.DEFAULT_WAIT;
import static utils.Constants.SHORT_WAIT;

public class vans_homePage {

    private Page page;
    public vans_homePage(Page page) {
        this.page = page;
    }


    private Locator vans_MyAccountHeader() {
        return page.locator("xpath=//div[@class='grow overflow-y-auto p-4 lg:p-6 p-6']");
    }
    private Locator Vans_createAccount()
    {
        return page.locator("[data-test-id=\"base-form\"] [data-test-id=\"vf-link\"]");
    }
    public void click_createAccount()
    {
        page.waitForTimeout(DEFAULT_WAIT);
        Vans_createAccount().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        Vans_createAccount().click();
    }


    private Locator vans_searchOption_HomePage()
    {
        return page.locator("#__nuxt > div:nth-child(1) > div > div.pointer-within.sticky.top-0.z-above-header > header > div > div > section.grid.cols-\\[1fr_auto_1fr\\].w-full.\\<lg\\:hidden > section > button:nth-child(3) > span > i");
    }


    private Locator vans_searchField()
    {
       return page.locator("[data-test-id=\"base-input\"]");
    }

    public void enter_searchInputField(String searchInput)
    {
        vans_searchField().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        vans_searchField().fill(searchInput);
        page.waitForTimeout(DEFAULT_WAIT);
    }
    private Locator vans_HamburgerMenu() {
        return page.locator("#__nuxt > div:nth-child(1) > div > div.pointer-within.sticky.top-0.z-above-header > header > div > div > section.grid.cols-\\[1fr_auto_1fr\\].w-full.\\<lg\\:hidden > button > span > i");
    }
    public void click_vansHamburgerMenu() {
        vans_HamburgerMenu().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );

        for(int i = 0; i < 3; i++) {
            System.out.println("Attempting to click Hamburger Menu - attempt " + (i + 1));
            if (vans_HamburgerMenu().isEnabled()) {
                vans_HamburgerMenu().click(new Locator.ClickOptions().setForce(true));
                page.waitForTimeout(2000);
                // Check if we moved to next step
                vans_categoryPage VCP=new vans_categoryPage(page);
                if(VCP.vans_viewall_Header().isVisible()) {
                    System.out.println("Successfully clicked Hamburger Menu");
                    return;
                }
            }
            page.waitForTimeout(1000);
        }
        System.out.println("Clicked Hamburger menu in home");
    }

    private Locator vans_profile() {
        return page.locator("xpath=//*[@id='__nuxt']/div[1]/div/div[3]/header/div/div/section[2]/section/button[1]/span");
    }

    private Locator Vans_Profile_icon() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("My Account"));
    }

    public Locator Vans_popup_closeButton() {
        return page.locator("xpath=//div[@class='bcClose']");
    }

    public void click_vansProfileButton() {
        Vans_Profile_icon().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        Vans_Profile_icon().click();
        System.out.println("Clicked profile button in home");
        page.waitForTimeout(DEFAULT_WAIT);
    }

    public void vans_homePopup_closeButton() {

        int maxAttempts =10;
        boolean isClosed=false;
        int attempt =0;
        page.waitForTimeout(DEFAULT_WAIT);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(!isClosed && attempt<maxAttempts) {
            try {
                if (Vans_popup_closeButton().count() >0 && Vans_popup_closeButton().isVisible()) {
                    System.out.println("detected the popup in home");
                    Vans_popup_closeButton().click();
                    Vans_popup_closeButton().waitFor(new Locator.WaitForOptions()
                            .setTimeout(DEFAULT_WAIT)
                            .setState(WaitForSelectorState.HIDDEN));
                    System.out.println("close the popup in home successfully");
                    isClosed=true;
                }else{
                    System.out.println("Popup is not visible, so attempt :"+(attempt+1)+" .Retrying...");
                    isClosed=true;
                }

            } catch (Exception e) {
                System.out.println("exception while closing popup, so attempt :" +(attempt+1)+" .. Retrying");
            }
            attempt++;
        }
        if(!isClosed)
        {
            System.out.println("Popup didnt appera or couldnot be closed after "+maxAttempts+" attempts");
        }
    }

}
