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

    //QA-Kajal kabade
    private Locator Tnf_SignInLink() {
        return page.locator("xpath=(//nav//a[text()='Sign In'])[2]");
    }
    private Locator Tnf_SignIn_Dailog() {
        return page.locator("xpath=//h2[@id='reka-dialog-title-v-0']");
    }

    public void clickSignINLink() {
        Tnf_SignInLink().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        Tnf_SignInLink().click();
        Tnf_SignIn_Dailog().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
    }
    private Locator Tnf_createAccount_Button() {
        return page.locator("xpath=//span[text()='Join XPLR Pass']//ancestor::button");
    }

    public void tnf_createAccountButton() {
        Tnf_createAccount_Button().scrollIntoViewIfNeeded();
        Tnf_createAccount_Button().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        Tnf_createAccount_Button().click();
    }
}
