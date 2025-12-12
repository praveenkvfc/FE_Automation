package stepdefinitions.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import config.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VANS.US.vans_SignInSignUp_Page;
import utils.PlaywrightFactory;

import static org.testng.Assert.assertTrue;
import static utils.Constants.DEFAULT_WAIT;
import static utils.RandomDataGenerator.generateRandomEmail;
import static utils.RandomDataGenerator.generateRandomName;

public class vans_AccountCreationSteps {
    private Page page;
    private String firstname;
    private String lastname;
    private String input_password = "Test1234";
    private vans_SignInSignUp_Page vansSignInSignUpPagePage;

    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_SignInSignUp_Page getVansSignInSignUpPage() {
        if (vansSignInSignUpPagePage == null) {
            vansSignInSignUpPagePage = new vans_SignInSignUp_Page(getPage());
        }
        return vansSignInSignUpPagePage;
    }
    //QA-Kajal kabade
    @When("the user enters the email for vans")
    public void theUserEntersTheEmailForVans() {
        if (!ConfigReader.get("brand").equals("tnf")) {
            firstname = generateRandomName();
            lastname = generateRandomName();

            getVansSignInSignUpPage().Setvans_signin_email(generateRandomEmail(firstname,
                    lastname));
            getVansSignInSignUpPage().vans_click_continueButton();
            System.out.println("Clicked Continue after entering email.");
        }
    }

    //QA-Kajal kabade
    @And("the user agrees Vans Terms and Conditions")
    public void theUserAgreesVansTermsAndConditions() {
        if (ConfigReader.get("brand").equals("vans")) {
            if (ConfigReader.get("region").equals("ca")) {
                System.out.println("clicking on Terms and condition checkbox for CA");
                getVansSignInSignUpPage().click_ca_agree_TnC_checkbox();

            } else if (ConfigReader.get("region").equals("us")) {
                System.out.println("clicking on Terms and condition checkbox for US");
                getVansSignInSignUpPage().click_vans_agree_Vans_TnC_checkbox();
            }
        }
        if (ConfigReader.get("brand").equals("tnf")) {
            getVansSignInSignUpPage().click_vans_agree_Vans_TnC_checkbox();
        }
    }


    //QA-Kajal kabade
    @And("the user agree privacy policy")
    public void theUserAgreePrivacyPolicy() {
        if (ConfigReader.get("brand").equals("tnf")) {
            getVansSignInSignUpPage().Click_tnf_agree_privacyPolicy_checkbox();
        }else{
            getVansSignInSignUpPage().Click_vans_agree_privacyPolicy_checkbox();
        }
    }

    @And("the user agrees to receive mails")
    public void theUserAgreesToReceiveMails() {
        //This method is working for vans- CA,US and TNF- US, CA
        getVansSignInSignUpPage().click_vans_agree_receiveEmails_checkbox();
    }

    @And("the user clicks the Create an Account button")
    public void theUserClicksTheCreateAnAccountButton() {
        getVansSignInSignUpPage().vans_click_createAccountButton();
    }
    //QA-Kajal kabade
    @And("the user provides the password for vans to Signup or SignIn")
    public void theUserProvidesThePasswordForVansToSignupSignIn() {
        if (!ConfigReader.get("brand").equals("tnf")) {
            getVansSignInSignUpPage().Setvans_signup_password(input_password);
        }
    }
}
