package pages.VANS.US;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.PaymentDataReader;
import utils.UserDetailsReader;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static utils.Constants.*;
public class vans_saveCreditcard_page {

    private Page page;
    private PaymentDataReader paymentDataReader;
    private UserDetailsReader user =
            UserDetailsReader.getInstance(REGISTERED_USER_ALL);

    // Updated constructor to accept cardType
    public vans_saveCreditcard_page(Page page, String cardType) {
        this.page = page;
        this.paymentDataReader = PaymentDataReader.getInstance(cardType);
    }

    // If you can't change the constructor, use this alternative:
    public void initializePaymentReader(String cardType) {
        this.paymentDataReader = PaymentDataReader.getInstance(cardType);
    }


    private Locator vans_savedCreditCardsPage() {
        return page.locator("xpath=//h2[normalize-space()='Saved Credit Cards']");
    }

    private Locator vans_addCreditCard() {
        return page.locator("xpath=//a[@class='vf-button vf-button-primary vf-button-sm dark:vf-button-primary <md:w-full']");
    }

    public void click_addCreditCard() {
        vans_addCreditCard().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(SHORT_WAIT));
        vans_addCreditCard().scrollIntoViewIfNeeded();
        page.waitForTimeout(DEFAULT_WAIT);
        vans_addCreditCard().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_addCreditCard().click();
        System.out.println("Clicked add credit card option");
        page.waitForTimeout(5000);
    }

    private FrameLocator vans_creditCard_Num_iFrame() {
        return page.frameLocator("css=div#card-number-container > iframe");
    }

    private FrameLocator vans_creditCard_Expiry_iFrame() {
        return page.frameLocator("css=div#card-expiry-container > iframe");
    }

    private FrameLocator vans_creditCard_CVV_iFrame() {
        return page.frameLocator("css=div#security-code-container > iframe");
    }

    private Locator vans_creditCard_Num() {
        return vans_creditCard_Num_iFrame().locator("css=input#encryptedCardNumber[aria-label='Card number']");
    }


    public void enter_vans_creditCard_Num() {
        System.out.println("Entering credit card number");
        vans_creditCard_Num().scrollIntoViewIfNeeded();
        vans_creditCard_Num().waitFor(new Locator.WaitForOptions().setTimeout(PAGELOAD_WAIT).setState(WaitForSelectorState.VISIBLE));

        if (vans_creditCard_Num().isEnabled()) {
            String cardNumber = paymentDataReader.getCardNumber();
            System.out.println("Card number: " + cardNumber.substring(0, 4) + "****" +
                    cardNumber.substring(cardNumber.length() - 4));

            vans_creditCard_Num().fill("");
            vans_creditCard_Num().fill(cardNumber);
            page.waitForTimeout(2000);

            String value = vans_creditCard_Num().inputValue().trim();
            System.out.println("Card number field value: '" + value + "'");

            if (!value.isEmpty()) {
                System.out.println("Card number entered successfully");
            } else {
                System.out.println("Card number field appears empty but continuing");
            }
        } else {
            throw new RuntimeException("Card number field is not enabled");
        }
    }

    private Locator vans_creditCard_Expiry() {
        return vans_creditCard_Expiry_iFrame().locator("xpath=//*[@id=\"encryptedExpiryDate\"]");
    }

    public void enter_vans_creditCard_Expiry() {
        System.out.println("Entering credit card expiry");
        vans_creditCard_Expiry().scrollIntoViewIfNeeded();
        vans_creditCard_Expiry().waitFor(new
                Locator.WaitForOptions().setTimeout(PAGELOAD_WAIT).setState(WaitForSelectorState.VISIBLE));

        if (vans_creditCard_Expiry().isEnabled()) {
            String expiry = paymentDataReader.getFormattedExpiryWithSlash();
            System.out.println("Expiry: " + expiry);

            vans_creditCard_Expiry().fill("");
            vans_creditCard_Expiry().fill(expiry);
            page.waitForTimeout(2000);

            String value = vans_creditCard_Expiry().inputValue().trim();
            System.out.println("Expiry field value: '" + value + "'");

            if (!value.isEmpty()) {
                System.out.println("Expiry entered successfully");
            } else {
                System.out.println("Expiry field appears empty but continuing");
            }
        } else {
            throw new RuntimeException("Expiry field is not enabled");
        }
    }

    private Locator vans_creditCard_Cvv() {
        return vans_creditCard_CVV_iFrame().locator("xpath=//*[@id='encryptedSecurityCode']");
    }

    public void enter_vans_creditCard_Cvv() {
        System.out.println("Entering credit card CVV");
        vans_creditCard_Cvv().scrollIntoViewIfNeeded();
        vans_creditCard_Cvv().waitFor(new Locator.WaitForOptions().setTimeout(PAGELOAD_WAIT).setState(WaitForSelectorState.VISIBLE));

        if (vans_creditCard_Cvv().isEnabled()) {
            String cvv = paymentDataReader.getSecurityCode();
            System.out.println("CVV: " + cvv);

            vans_creditCard_Cvv().click();
            page.waitForTimeout(500);
            vans_creditCard_Cvv().fill("");
            vans_creditCard_Cvv().fill(cvv);
            page.waitForTimeout(2000);

            String value = vans_creditCard_Cvv().inputValue().trim();
            System.out.println("CVV field value: '" + value + "'");

            if (!value.isEmpty()) {
                System.out.println("CVV entered successfully");
            } else {
                System.out.println("CVV field appears empty but continuing");
            }
        } else {
            throw new RuntimeException("CVV field is not enabled");
        }
    }

    private Locator vans_creditcard_lastName() {
        return page.locator("xpath=//*[@name='lastName']");
    }

    public void enter_vans_lastName() {
        System.out.println("Entering last name");
        vans_creditcard_lastName().scrollIntoViewIfNeeded();
        vans_creditcard_lastName().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_lastName().click();
        vans_creditcard_lastName().fill(user.getLastName());
        System.out.println("Last name entered: " + user.getLastName());
    }

    private Locator vans_creditcard_firstName() {
        return page.locator("xpath=//*[@name='firstName']");
    }

    public void enter_vans_firstName() {
        System.out.println("Entering first name");
        // Press Tab multiple times to navigate to first name field
        for (int i = 0; i < 5; i++) {
            page.keyboard().press("Tab");
            page.waitForTimeout(300);
        }

        vans_creditcard_firstName().scrollIntoViewIfNeeded();
        vans_creditcard_firstName().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_firstName().click();
        vans_creditcard_firstName().fill(user.getFirstName());
        System.out.println("First name entered: " + user.getFirstName());
    }

    public Locator vans_creditcard_state() {
        return page.locator("[data-test-id='vf-form-field-stateCityProvDept'] select");
    }

    public void enter_vans_creditcard_state() {
        System.out.println("Checking state field");
        vans_creditcard_state().scrollIntoViewIfNeeded();
        vans_creditcard_state().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_state().click();
        vans_creditcard_state().selectOption(user.getProvince());
        System.out.println("State entered: " + user.getProvince());
    }

    public Locator vans_creditcard_city() {
        return page.locator("xpath=//div[@data-test-id='vf-form-field-city']//input[@type='text']");
    }

    public void enter_vans_creditcard_city() {
        System.out.println("Checking City field");
        vans_creditcard_city().scrollIntoViewIfNeeded();
        vans_creditcard_city().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_city().click();
        vans_creditcard_city().fill(user.getCity());
        System.out.println("City entered: " + user.getCity());
    }

    private Locator vans_creditcard_addressLine() {
        return page.locator("xpath=//input[@class='pac-target-input']");
    }

    public void enter_vans_creditcard_addressLine() {
        System.out.println("Entering address");
        vans_creditcard_addressLine().scrollIntoViewIfNeeded();
        vans_creditcard_addressLine().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_addressLine().click();
        vans_creditcard_addressLine().fill(user.getStreetAddress());
        page.keyboard().press("Enter");
        System.out.println("Address entered: " + user.getStreetAddress());;
        page.waitForTimeout(3000);
    }

    private Locator vans_creditcard_email() {
        return page.locator("xpath=//input[@name='email']");
    }

    public void enter_vans_creditcard_email() {
        System.out.println("Entering email");
        vans_creditcard_email().scrollIntoViewIfNeeded();
        vans_creditcard_email().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_email().fill(user.getEmail());
        System.out.println("Email entered: " + user.getEmail());
    }

    private Locator vans_creditcard_phoneNumber() {
        return page.locator("xpath=//input[@type='tel']");
    }

    public void enter_vans_creditcard_phoneNumber() {
        System.out.println("Entering phone number");
        System.out.println("Phone number: " + user.getPhone());
        vans_creditcard_phoneNumber().scrollIntoViewIfNeeded();
        vans_creditcard_phoneNumber().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_phoneNumber().fill(user.getPhone());
        System.out.println("Phone number entered: " + user.getPhone());
    }

    private Locator vans_creditcard_zipcode() {
        return page.locator("xpath=//div[@data-test-id='vf-form-field-postalCode']//input[@type='text']");
    }

    public void enter_vans_creditcard_zipcode() {
        System.out.println("Checking zip code field");
        vans_creditcard_zipcode().scrollIntoViewIfNeeded();
        vans_creditcard_zipcode().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_zipcode().fill(user.getPostalCode());
        System.out.println("Zipcode entered: " + user.getPostalCode());

        // Press Tab to move to next field
        for (int i = 0; i < 2; i++) {
            page.keyboard().press("Tab");
            page.waitForTimeout(300);
        }
    }

    private Locator vans_creditcard_makedefaultcard_checkbox() {
        return page.locator("xpath=//span[@data-test-id='vf-checkbox-icon']");
    }

    public void click_vans_creditcard_makedefaultcard_checkbox() {
        System.out.println("Clicking make default card checkbox");
        vans_creditcard_makedefaultcard_checkbox().scrollIntoViewIfNeeded();
        vans_creditcard_makedefaultcard_checkbox().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_makedefaultcard_checkbox().click();
        System.out.println("Default card checkbox clicked");
    }

    private Locator vans_creditcard_saveThisCard_button() {
        return page.locator("xpath=//button[@type='submit']");
    }

    private Locator Vans_cardAdded_successMsg() {
        return page.locator("xpath=//*[@id='v-0-2']");
    }

    public String getAddCardSuccessMessage() {
        Vans_cardAdded_successMsg().waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT)
                .setState(WaitForSelectorState.VISIBLE));
        return Vans_cardAdded_successMsg().textContent().trim();
    }

    public void click_vans_creditcard_saveThisCard_button() {
        System.out.println("Clicking save card button");
        vans_creditcard_saveThisCard_button().scrollIntoViewIfNeeded();
        vans_creditcard_saveThisCard_button().waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(SHORT_WAIT));
        vans_creditcard_saveThisCard_button().click();
        System.out.println("Save card button clicked");
    }

    private Locator vans_creditcard_cancel_button() {
        return page.locator("xpath=//a[@class='vf-button vf-button-secondary vf-button-sm dark:vf-button-tertiary col-span-full lg:col-span-2']");
    }

    // US State → Sample ZIP code mapping
    private static final Map<String, String> stateToZipMap = Map.ofEntries(
            Map.entry("California", "90001"),
            Map.entry("New York", "10001"),
            Map.entry("Texas", "73301"),
            Map.entry("Florida", "33101"),
            Map.entry("Illinois", "60007"),
            Map.entry("Massachusetts", "02111"),
            Map.entry("Pennsylvania", "19019"),
            Map.entry("Arizona", "85001"),
            Map.entry("Georgia", "30301"),
            Map.entry("Washington", "98001")
    );

    public void selectRandomStateAndZip(Locator stateDropdown, Locator
            zipInput) {
        stateDropdown.waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Get all options from dropdown
        List<String> allOptions =
                stateDropdown.locator("option").allInnerTexts();

        // Remove the placeholder like "Select State" or empty option
        List<String> stateList = allOptions.stream().filter(opt ->
                !opt.trim().isEmpty() &&
                        stateToZipMap.containsKey(opt.trim())).toList();

        if (stateList.isEmpty()) {
            throw new RuntimeException("No valid states found in dropdown that match the ZIP map.");
        }

        // Pick a random state
        String selectedState = stateList.get(new Random().nextInt(stateList.size()));
        String zipCode = stateToZipMap.get(selectedState);

        System.out.println("Selected State: " + selectedState + ", ZIP: " + zipCode);

        // Select the state in dropdown
        stateDropdown.selectOption(new SelectOption().setLabel(selectedState));

        // Fill ZIP code
        zipInput.waitFor(new
                Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        zipInput.fill(zipCode);
    }
}
