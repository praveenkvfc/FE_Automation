
package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.cucumber.java.en.Then;

import static utils.Constants.DEFAULT_WAIT;
import static utils.Constants.SHORT_WAIT;

public class vans_SignInSignUp_Page {
    private Page page;

    public vans_SignInSignUp_Page(Page page) {
        this.page = page;
    }

    private Locator vans_signin_email() {
        return page.locator("xpath=//input[@name='email']");
    }

    public void Setvans_signin_email(String input) {
        vans_signin_email().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans_signin_email().pressSequentially(input); //pressSequentially or fill we can use but this will enter char by char as real user
        page.waitForTimeout(500);
    }

    private Locator vans__FirstName() {
        return page.locator("[data-test-id=\"vf-form-field-firstName\"] [data-test-id=\"base-input\"]");
    }

    public void vans_enter_FirstName(String input) {
        vans__FirstName().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans__FirstName().pressSequentially(input); //pressSequentially or fill we can use but this will enter char by char as real user
        page.waitForTimeout(500);
    }

    private Locator vans__LastName() {
        return page.locator("[data-test-id=\"vf-form-field-lastName\"] [data-test-id=\"base-input\"]");
    }

    public void vans_enter_LastName(String input) {
        vans__FirstName().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans__FirstName().pressSequentially(input); //pressSequentially or fill we can use but this will enter char by char as real user
        page.waitForTimeout(500);
    }

    private Locator vans_continueButton() {
        return page.locator("[data-test-id=\"vf-button\"]");
    }

    private Locator vans_createAccountButton() {
        return page.locator("[data-test-id=\"vf-button\"]");
    }

    public void vans_click_createAccountButton() {
        vans_createAccountButton().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans_createAccountButton().click();
    }

    public void vans_click_continueButton() {
        // Close any popup if present
        Locator popupClose = page.locator("xpath=//div[@class='bcClose']");
        if (popupClose.isVisible()) {
            popupClose.waitFor(new Locator.WaitForOptions()
                    .setTimeout(DEFAULT_WAIT)
                    .setState(WaitForSelectorState.VISIBLE));
            popupClose.click();
        }

        // Wait for continue button to be visible
        vans_continueButton().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));

        System.out.println("continue button enabled : " + vans_continueButton().isEnabled());
        System.out.println("disabled attr : " + vans_continueButton().getAttribute("disabled"));
        System.out.println("class attr: " + vans_continueButton().getAttribute("class"));

        if (vans_continueButton().isVisible()) {
            vans_continueButton().scrollIntoViewIfNeeded();

            // Wait for button to be enabled
            page.waitForCondition(() -> vans_continueButton().isEnabled(),
                    new Page.WaitForConditionOptions().setTimeout(DEFAULT_WAIT));

            for (int i = 0; i < 3; i++) {
                System.out.println("Attempting to click continue button - attempt " + (i + 1));
                if (vans_continueButton().isEnabled()) {
                    vans_continueButton().click(new Locator.ClickOptions().setForce(true));
                    page.waitForTimeout(2000);

                    // Check if we moved to next step
                    if (vans_signup_password().isVisible()) {
                        System.out.println("Successfully moved to password step");
                        return;
                    }
                }
                page.waitForTimeout(1000);
            }

            // If clicking didn't work, try Enter key
            vans_continueButton().focus();
            page.keyboard().press("Enter");
            page.waitForTimeout(2000);

        } else {
            System.out.println("Continue button is not visible");
        }
    }

    private Locator vans_signup_password() {
        return page.locator("[data-test-id=\"input-password\"] [data-test-id=\"base-input\"]");
    }

    public void Setvans_signup_password(String input) {
        vans_signup_password().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans_signup_password().fill(input);
    }

    private Locator vans_agree_Vans_TnC_checkbox() {
        return page.locator("[data-test-id=\"vf-form-field-loyaltyTerms\"] i");
    }

    public void click_vans_agree_Vans_TnC_checkbox() {
        vans_agree_Vans_TnC_checkbox().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans_agree_Vans_TnC_checkbox().click();
    }

    private Locator vans_agree_privacyPolicy_checkbox() {
        return page.locator("[data-test-id=\"vf-form-field-policy\"] i");
    }

    public void Click_vans_agree_privacyPolicy_checkbox() {
        vans_agree_privacyPolicy_checkbox().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans_agree_privacyPolicy_checkbox().click();
    }

    private Locator vans_agree_receiveEmails_checkbox() {
        return page.locator("[data-test-id=\"vf-form-field-newsletter\"] i");
    }

    public void click_vans_agree_receiveEmails_checkbox() {
        vans_agree_receiveEmails_checkbox().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        vans_agree_receiveEmails_checkbox().click();
    }

    private Locator vans_completeProfile_Button() {
        return page.locator("xpath=//button[contains(text(),'Complete Profile')]");
    }

    public void click_vans_completeProfile_Button() {
        vans_completeProfile_Button().click();
    }

    private Locator vans_successMessage() {
        return page.locator("xpath=//h2[normalize-space()='Welcome to the Vans Family']");
    }

    public String getVans_successMessage() {
        vans_successMessage().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        return vans_successMessage().textContent().trim();
    }

    public void click_vans_successMessage() {
        vans_successMessage().click();
    }

    private Locator vans_MayBeLater_Button() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Maybe Later"));
    }

    private Locator vans_SkipForNow_button() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Skip for Now"));
    }

    public void click_SkipForNow_Button() {
        vans_SkipForNow_button().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));

        if (vans_SkipForNow_button().isEnabled() &&
                vans_MayBeLater_Button().isVisible()) {
            System.out.println("Skip for now button is visible");
            vans_SkipForNow_button().click(new
                    Locator.ClickOptions().setForce(true));
        }
    }
    public void click_vans_MayBeLater_Button() {
        vans_MayBeLater_Button().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));

        if (vans_MayBeLater_Button().isEnabled() &&
                vans_MayBeLater_Button().isVisible()) {
            System.out.println("May be later button is visible");
            vans_MayBeLater_Button().click(new
                    Locator.ClickOptions().setForce(true));
        }
    }

    private Locator vans_close_successMessage_MyAccount() {
        return page.locator("xpath=//*[@id=\"dialogs\"]/div/div[2]/div/div[1]/button");
    }

    public void click_vans_close_successMessage_MyAccount() {
        System.out.println("In success message popup");
        vans_close_successMessage_MyAccount().waitFor(new
                Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        if (vans_close_successMessage_MyAccount().isVisible() &&
                vans_close_successMessage_MyAccount().isEnabled()) {
            vans_close_successMessage_MyAccount().click(new
                    Locator.ClickOptions().setForce(true));
        }
    }
    //QA-Kajal kabade
    private Locator ca_agree_TnC_checkbox() {
        return page.locator("[data-test-id=\"vf-form-field-brandTerms\"] i");

    }
    //QA-Kajal kabade
    public void click_ca_agree_TnC_checkbox() {
        ca_agree_TnC_checkbox().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        ca_agree_TnC_checkbox().click();
    }

    //QA-Kajal kabade
    private Locator CA_successMessage() {
        return page.locator("xpath=//h2[text()='Your account has been created!']");
    }
    //QA-Kajal kabade
    public String getCA_successMessage() {
        CA_successMessage().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        return CA_successMessage().textContent().trim();
    }

    //QA- Kajal kabade
    private Locator Ca_GoToMyAccount_icon() {
        return page.locator("xpath=(//a[text()='Go to My Account'])[1]");
    }
    //QA- Kajal kabade
    public void click_GoToMyAccount_Button() {
        Ca_GoToMyAccount_icon().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_WAIT)
        );
        Ca_GoToMyAccount_icon().click();
        System.out.println("Clicked Go To My Account button in home");
        page.waitForTimeout(DEFAULT_WAIT);
    }



}
