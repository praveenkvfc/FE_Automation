package pages.VANS.US;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.SortUtility;

import java.awt.*;
import java.util.*;
import java.util.List;

import static utils.Constants.DEFAULT_WAIT;
import static utils.Constants.SHORT_WAIT;
public class vans_UserProfilePage {
    private Page page;
    Random random;

    public vans_UserProfilePage(Page page) {
        this.page = page;
        this.random = new Random();
    }
//
//    private Locator vans_ProfileTile(){
//        return page
//    }



}
