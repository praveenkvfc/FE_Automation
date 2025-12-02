
package stepdefinitions.VANS.US;

import com.microsoft.playwright.Page;
import config.ConfigReader;
import io.cucumber.java.en.*;
import pages.TBL.US.CreateAccount_Page;
import pages.TBL.US.Home_Page;
import pages.VANS.US.vans_SignInSignUp_Page;
import pages.VANS.US.vans_homePage;
import utils.RandomDataGenerator;
import utils.PlaywrightFactory;

import static org.testng.Assert.assertTrue;
import static utils.RandomDataGenerator.generateRandomName;

public class AccountCreationSteps {

    private Page page = PlaywrightFactory.getPage();
    private final String firstname = RandomDataGenerator.generateRandomName();
    private final String lastname = RandomDataGenerator.generateRandomName();
    private final String mobileNumber = RandomDataGenerator.generateMobileNumber(ConfigReader.get("region"));
    private final String dateOfBirth = RandomDataGenerator.generateDOB();
    private final String inputPassword = "Test1234";

    private final CreateAccount_Page createAccountPage = new CreateAccount_Page(page);
    private final Home_Page homePage = new Home_Page(page);
    private final vans_SignInSignUp_Page vansSignInSignUpPage = new vans_SignInSignUp_Page(page);
    private final vans_homePage vansHomePage = new vans_homePage(page);

    @Given("the user is on the {string} page")
    public void userIsOnPage(String pageName) {
        createAccountPage.launchApplication(ConfigReader.get("url"));
        if (pageName.equalsIgnoreCase("guest")) {
            System.out.println("Launched Site as a guest user");
            return;
        }
        if (ConfigReader.get("brand").equals("tbl")) {
            homePage.clickWelcomeIcon();
            homePage.clickCreateAccountButton();
        } else if (ConfigReader.get("brand").equals("vans")) {
            if (ConfigReader.get("region").equals("ca")) {
                System.out.println("In home Page");
                vansHomePage.click_vansProfileButton();
                vansHomePage.click_createAccount();
                String firstname = generateRandomName();
                String lastname = generateRandomName();
                vansSignInSignUpPage.vans_enter_FirstName(firstname);
                vansSignInSignUpPage.vans_enter_LastName(lastname);
            }else{
                vansHomePage.vans_homePopup_closeButton();
                vansHomePage.click_vansProfileButton();
            }
        }
    }

    @When("the user enters the all required personal details")
    public void theUserEntersAllRequiredPersonalDetails() {
        createAccountPage.enterFirstName(firstname);
        createAccountPage.enterLastName(lastname);
        createAccountPage.enterBirthDate(dateOfBirth);
        createAccountPage.enterMobileNumber(mobileNumber);
    }

    @And("the user provides the email and password")
    public void theUserProvidesEmailAndPassword() {
        String email = RandomDataGenerator.generateRandomEmail(firstname, lastname);
        createAccountPage.enterEmail(email);
        createAccountPage.enterPassword(inputPassword);
    }

    @And("the user agrees to receive text messages about orders and Timberland offers")
    public void agreeToTextMessages() {
        createAccountPage.clickCheckBoxText();
        createAccountPage.clickIAcceptButton();
    }

    @And("the user accepts the Community Terms of Service")
    public void acceptCommunityTOS() {
        createAccountPage.acceptCommunityTnS();
    }

    @And("the user accepts Timberland's Terms and Conditions")
    public void acceptTimberlandTnC() {
        createAccountPage.acceptTblTnC();
    }

    @And("the user clicks the {string} button")
    public void clickButton(String buttonText) {
        if (ConfigReader.get("brand").equals("tbl")) {
            createAccountPage.clickCreateAccountButton();
        } else if (ConfigReader.get("brand").equals("vans")) {
            vansSignInSignUpPage.vans_click_continueButton();
        }

    }

    @And("the user accepts the latest Timberland offers")
    public void theUserAcceptsTheLatestTimberlandOffers() {
        createAccountPage.acceptLatestTblOffers();
    }

    //QA-Kajal kabade
    @Then("account created successfully with confirmation message {string}")
    public void accountCreatedSuccessfullyWithConfirmationMessage(String expectedMessage) {
        String actualMessage = null;
        if (ConfigReader.get("brand").equals("tbl")) {
            actualMessage = createAccountPage.getCreateAccountSuccessMessage();
        } else if (ConfigReader.get("brand").equals("vans")) {
            if (ConfigReader.get("region").equals("us")) {
                actualMessage = vansSignInSignUpPage.getVans_successMessage();
            } else if (ConfigReader.get("region").equals("ca")) {
                actualMessage = vansSignInSignUpPage.getCA_successMessage();
            }
            System.out.println("Actual message: " + actualMessage);
            assertTrue(actualMessage.contains(expectedMessage), "Expected: " + expectedMessage + ", but got: " + actualMessage);
        }
    }


}

