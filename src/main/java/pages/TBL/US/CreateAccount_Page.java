package pages.TBL.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;
import utils.UserDetailsReader;

import static utils.Constants.*;

public class CreateAccount_Page {
    private Page page;
    private UserDetailsReader user =
            UserDetailsReader.getInstance(REGISTERED_USER_ALL);

    public CreateAccount_Page(Page page) {
        this.page = page;
    }

    private Locator firstName() {
        return page.locator("[name='firstName']");
    }

    private Locator lastName() {
        return page.locator("[name='lastName']");
    }

    private Locator birthDate() {
        return page.locator("xpath=//*[@data-test-id='base-input' and @aria-describedby='birthDate']");
    }

    private Locator mobileNumber() {
        return page.locator("xpath=//*[@data-test-id='base-input' and @type='tel']");
    }

    private Locator email() {
        return page.locator("xpath=//form[@class='gap-4 grid']//input[@name='email']");
    }

    private Locator password() {
        return page.locator("xpath=//input[@aria-describedby='password-requirements']");
    }

    private Locator acceptCommunityTermOfServices() {
        return page.locator("xpath=//*[@id='dialogs']/div[2]/div[2]/div/div[2]/div/form/div[9]/div[2]/label/span[1]/i");
    }

    private Locator getacceptTblTnC() {
        return page.locator("xpath=//*[@id='dialogs']/div[2]/div[2]/div/div[2]/div/form/div[9]/div[3]/label/span[1]/i");
    }

    private Locator createAccountButton() {
        return page.locator("xpath=//*[@id=\"dialogs\"]/div[2]/div[2]/div/div[2]/div/form/button/span");
    }

    private Locator acceptsLatestTblOffers() {
        return page.locator("xpath=//*[@id='dialogs']/div[2]/div[2]/div/div[2]/div/form/div[9]/div[1]/label/span[1]/i");
    }

    private Locator getclickCheckBoxText() {
        return page.locator("xpath=//*[@id=\"dialogs\"]/div[2]/div[2]/div/div[2]/div/form/div[5]/span/label/span[1]/i");
    }

    private Locator iAcceptButton() {
        return page.locator("xpath=//span[normalize-space()='I Accept']");
    }

    private Locator createAccountSuccessMsg() {
        return page.locator("xpath=//h2[@id='reka-dialog-title-v-6']");
    }

    private Locator createAccountContainer() {
        return page.locator("xpath=//*[@id=\"dialogs\"]/div[2]/div[2]/div/div[2]");
    }

    public void launchApplication(String url) {
        System.out.println("Navigating to url : " + url);
        page.navigate(url, new Page.NavigateOptions()
                .setTimeout(PAGELOAD_WAIT)
                .setWaitUntil(WaitUntilState.LOAD)
        );
    }

    public void enterFirstName(String input) {
        firstName().scrollIntoViewIfNeeded();
        firstName().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        firstName().fill(input);
    }

    public void enterLastName(String input) {
        lastName().scrollIntoViewIfNeeded();
        lastName().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        lastName().fill(input);
    }

    public void enterBirthDate(String input) {
        birthDate().scrollIntoViewIfNeeded();
        birthDate().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        birthDate().fill(input);
    }

    public void enterMobileNumber(String input) {
        mobileNumber().scrollIntoViewIfNeeded();
        mobileNumber().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        mobileNumber().fill(input);
    }

    public void enterEmail(String input) {
        email().scrollIntoViewIfNeeded();
        email().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        email().fill(input);
    }

    public void enterPassword(String input) {
        password().scrollIntoViewIfNeeded();
        password().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        password().fill(input);
    }

    public void acceptCommunityTnS() {
        acceptCommunityTermOfServices().click();
    }

    public void acceptTblTnC() {
        getacceptTblTnC().click();
    }

    public void acceptLatestTblOffers() {
        acceptsLatestTblOffers().click();
    }

    public void clickCheckBoxText() {
        getclickCheckBoxText().scrollIntoViewIfNeeded();
        getclickCheckBoxText().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        if(getclickCheckBoxText().isEnabled()){
        getclickCheckBoxText().click();}
    }

    public void clickIAcceptButton() {
        iAcceptButton().scrollIntoViewIfNeeded();
        iAcceptButton().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        page.waitForTimeout(VERY_SHORT_WAIT);
        if (iAcceptButton().isEnabled() && iAcceptButton().isVisible()) {
            iAcceptButton().click();
        } else {
            iAcceptButton().click(new Locator.ClickOptions().setForce(true));
        }
    }

    public void clickCreateAccountButton() {
        createAccountContainer().evaluate("node => node.scrollTop = node.scrollHeight");
        createAccountButton().scrollIntoViewIfNeeded();
        createAccountButton().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        page.waitForTimeout(VERY_SHORT_WAIT);
        if (createAccountButton().isEnabled() && createAccountButton().isVisible()) {
            createAccountButton().click();
        } else {
            createAccountButton().click(new Locator.ClickOptions().setForce(true));
        }
    }

    public String getCreateAccountSuccessMessage() {
        createAccountSuccessMsg().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        return createAccountSuccessMsg().textContent().trim();
    }
    //QA- Kajal kabade
    private Locator tnf_zipcode() {
        return page.locator("xpath=//div[@data-test-id='vf-form-field-postalCode']//input");
    }

    public void enter_tnf_zipcode() {
        tnf_zipcode().scrollIntoViewIfNeeded();
        tnf_zipcode().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT)
        );
        String postalCode = user.getPostalCode();
        tnf_zipcode().fill(postalCode);
        System.out.println("Zipcode entered: " + postalCode);
    }
}
