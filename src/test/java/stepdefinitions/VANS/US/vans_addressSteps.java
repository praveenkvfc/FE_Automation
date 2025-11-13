package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.vans_SignInSignUp_Page;
import pages.VANS.US.vans_addressPage;
import pages.VANS.US.vans_saveCreditcard_page;
import utils.PlaywrightFactory;

import static org.testng.Assert.assertTrue;

public class vans_addressSteps {
    Page page;
    private vans_addressPage vansAddressPage;
    private vans_saveCreditcard_page vansSavedCreditcardPage;
    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_addressPage getAddressPage() {
        if (vansAddressPage == null) {
            vansAddressPage = new vans_addressPage(getPage());
        }
        return vansAddressPage;
    }
    private vans_saveCreditcard_page getCreditCardPage() {
        if (vansSavedCreditcardPage == null) {
            vansSavedCreditcardPage = new vans_saveCreditcard_page(getPage(),"VISA");
        }
        return vansSavedCreditcardPage;
    }

    @When("User navigates to the Address Book page")
    public void userNavigatesToTheAddressBookPage() {

        getAddressPage().click_vansAddressPage();
    }

    @Then("Address Book should display correct details for {string}")
    public void addressBookShouldDisplayCorrectDetailsFor(String arg0) {
        String label = getAddressPage().Verify_BillingAddressLabel();
        System.out.println("label : "+label);
        String expected_label="Billing Address";
        System.out.println( "expected_label : "+ expected_label);
        assertTrue(label.contains(expected_label), "Expected: " + expected_label + ", but got: " + label);
        System.out.println("Able to see billing address label");
    }

    @When("User opens the add {string} address form")
    public void userOpensTheAddAddressForm(String arg0) {

        getAddressPage().click_vansAddBillingAddressButton();
    }

    @And("User fills {string} address details for {string}")
    public void userFillsAddressDetailsFor(String arg0, String arg1) {
        getCreditCardPage().enter_vans_firstName();
        getCreditCardPage().enter_vans_lastName();
        getCreditCardPage().enter_vans_creditcard_addressLine();
        getCreditCardPage().enter_vans_creditcard_state();
        getCreditCardPage().enter_vans_creditcard_city();
        getCreditCardPage().enter_vans_creditcard_zipcode();
        getCreditCardPage().enter_vans_creditcard_phoneNumber();
        getCreditCardPage().enter_vans_creditcard_email();

    }

    @And("User saves the {string} address")
    public void userSavesTheAddress(String arg0) {
        getAddressPage().click_vans_SaveThisAddressButton();
    }
}

