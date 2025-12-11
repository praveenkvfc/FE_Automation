package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.vans_SignInSignUp_Page;
import pages.VANS.US.vans_MyAccountPage;
import pages.VANS.US.vans_addressPage;
import pages.VANS.US.vans_saveCreditcard_page;
import utils.PlaywrightFactory;

import static org.testng.Assert.assertTrue;

public class vans_MyaccountSteps {
    Page page;
    vans_SignInSignUp_Page vansSignInSignUpPagePage;
    vans_MyAccountPage vansAccountPage ;
    vans_saveCreditcard_page vansSavedCreditcardPage ;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_SignInSignUp_Page getSignupSignInPage() {
        if (vansSignInSignUpPagePage == null) {
            vansSignInSignUpPagePage = new vans_SignInSignUp_Page(getPage());
        }
        return vansSignInSignUpPagePage;
    }
    private vans_MyAccountPage getMyaccountPage() {
        if (vansAccountPage == null) {
            vansAccountPage = new vans_MyAccountPage(getPage());
        }
        return vansAccountPage;
    }
    private vans_saveCreditcard_page getCreditCardPage() {
        if (vansSavedCreditcardPage == null) {
            vansSavedCreditcardPage = new vans_saveCreditcard_page(getPage(),"VISA");
        }
        return vansSavedCreditcardPage;
    }


    @When("User navigates to the MyAccount page as {string}")
    public void userNavigatesToTheMyAccountPageAs(String userType) {
//        vansSignInSignUpPagePage.click_vans_close_successMessage_MyAccount();
        if((userType).equals("registeredUser")) {
            if (ConfigReader.get("brand").equals("vans")) {
                if (ConfigReader.get("region").equals("us")) {
                    getSignupSignInPage().click_vans_MayBeLater_Button();
                } else if (ConfigReader.get("region").equals("ca")) {
                    getSignupSignInPage().click_SkipForNow_Button();
                }
            }
        }else if(userType.equals("ExistingUser"))
        {

        }
    }

    @And("User opens the Credit Cards page")
    public void userOpensTheCreditCardsPage() {
        if (ConfigReader.get("region").equals("ca")) {
            getSignupSignInPage().click_GoToMyAccount_Button();
        }
        getMyaccountPage().setVans_creditCards();
    }
    @And("User adds a default {string} credit card for {string}")
    public void userAddsADefaultCreditCardFor(String arg0, String arg1) {
        //credit card methods
        getCreditCardPage().click_addCreditCard();
        getCreditCardPage().enter_vans_creditCard_Num();
        getCreditCardPage().enter_vans_creditCard_Expiry();
        getCreditCardPage().enter_vans_creditCard_Cvv();
        getCreditCardPage().enter_vans_firstName();
        getCreditCardPage().enter_vans_lastName();
        getCreditCardPage().enter_vans_creditcard_addressLine();
        getCreditCardPage().enter_vans_creditcard_city();
        getCreditCardPage().enter_vans_creditcard_state();
        getCreditCardPage().enter_vans_creditcard_zipcode();
        getCreditCardPage().enter_vans_creditcard_phoneNumber();
        getCreditCardPage().enter_vans_creditcard_email();
        getCreditCardPage().click_vans_creditcard_makedefaultcard_checkbox();
        getCreditCardPage().click_vans_creditcard_saveThisCard_button();
    }


    @Then("User should see a success message {string}")
    public void userShouldSeeASuccessMessage(String expectedMessage) {
        if (ConfigReader.get("env").equals("prepod")) {
            String actualMessage = null;
            actualMessage = getCreditCardPage().getAddCardSuccessMessage();
            System.out.println("Actual message: " + actualMessage);
            assertTrue(actualMessage.contains(expectedMessage), "Expected: " + expectedMessage + ", but got: " + actualMessage);
        } else if (ConfigReader.get("env").equals("prod")) {

        }

    }

    @And("user closes the my account window")
    public void userClosesTheMyAccountWindow() {
        getMyaccountPage().vans_CloseWindow_MyAccount_Click();
    }
}
