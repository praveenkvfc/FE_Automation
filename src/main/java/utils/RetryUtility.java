package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RetryUtility {
    public static void retryClicking(Locator ElementTobeClicked,Locator ConfirmationElement){

    }

    public static void gradualScrollToBottomUntilLocator(Page page, Locator targetElement,String Action) {
        Integer lastHeight = (Integer) page.evaluate("document.body.scrollHeight");
        int maxAttempts = 50; // Safety limit to prevent infinite loops
        int attempts = 0;

        while (attempts < maxAttempts) {
            // Check if locator exists and is visible before scrolling
            if (targetElement.isVisible()) {
                System.out.println("Locator found! Clicking the button...");
                targetElement.click();
                return;
            }

            // Scroll to bottom
            page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
            page.waitForTimeout(2000); // Wait for content to load

            // Check again after scrolling (in case element became visible during scroll)
           if(Action.equals("CLICK")) {
               if (targetElement.isVisible()) {
                   System.out.println("Locator found after scroll! Clicking the button...");
                   targetElement.click();
                   return;
               }
           } else if(Action.equals("ASSERT"))
           {
               assertThat(targetElement).isVisible();
           }
            Integer newHeight = (Integer) page.evaluate("document.body.scrollHeight");
            if (newHeight == lastHeight) {
                System.out.println("Reached bottom of page without finding the locator");
                break;
            }

            lastHeight = newHeight;
            attempts++;
        }

        // Final check after loop ends
        if(Action.equals("CLICK")) {
            if (targetElement.isVisible()) {
                System.out.println("Locator found at the end! Clicking the button...");
                targetElement.click();
            } else {
                System.out.println("Locator not found after " + attempts + " scroll attempts");
            }
        } else if(Action.equals("ASSERT"))
        {
            assertThat(targetElement).isVisible();
        }
    }


}
