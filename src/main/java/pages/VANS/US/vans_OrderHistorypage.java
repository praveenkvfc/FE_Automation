package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import javax.swing.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.Constants.SHORT_WAIT;

public class vans_OrderHistorypage {
    private Page page;

    public vans_OrderHistorypage(Page page) {
        this.page = page;
    }

    private Locator vansordernumber() {
        return page.locator("[data-test-id=\"order-number\"]");
    }
    private Locator vansorderhistorytext(){
        return page.locator("h2:has-text(\"Order History\")");

    }
    public void vansordernumberisvisible(){
        assertThat(vansordernumber()).isVisible();
        page.waitForTimeout(SHORT_WAIT);
    }
    public String getOrderNumber() {
        return vansordernumber().innerText().trim();
    }

    public  void vansorderhistorytextisvisible(){
        assertThat(vansorderhistorytext()).isVisible();
        page.waitForTimeout(SHORT_WAIT);
    }
    public String getOrderHistoryHeadingText() {
        return vansorderhistorytext().innerText().trim();
    }
}
