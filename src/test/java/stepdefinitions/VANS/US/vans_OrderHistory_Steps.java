package stepdefinitions.VANS.US;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.VANS.US.*;
import utils.PlaywrightFactory;

public class vans_OrderHistory_Steps {
    private Page page;
    private vans_homePage vansHomePage;
    private vans_OrderHistorypage getvansorderhistorypage;
    private Page getPage() {
        if (page == null) {
            page = PlaywrightFactory.getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized");
            }
        }
        return page;
    }

    private vans_OrderHistorypage getvansorderhistorypage() {
        if (getvansorderhistorypage == null) {
            getvansorderhistorypage = new vans_OrderHistorypage(getPage());
        }
        return getvansorderhistorypage;
    }

    @And("User verifies orderhistory with OrderDetails")
    public void  UserverifiesorderhistorywithOrderDetails(){
//        vansHomePage.click_vansProfileButton();
//        vansHomePage.click_vansOrderhistoryButton();

        getvansorderhistorypage.vansorderhistorytextisvisible();
        getvansorderhistorypage.vansordernumberisvisible();
    }
}