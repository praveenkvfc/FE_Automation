package utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;

public class waitUtil {
    public static void waitForElementVisible(Page page, String selector, int timeout)
    {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setTimeout(timeout)
                .setState(WaitForSelectorState.VISIBLE));
    }

    public static void waitForElementHidden(Page page, String selector, int timeout)
    {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setTimeout(timeout)
                .setState(WaitForSelectorState.VISIBLE));
    }
}
