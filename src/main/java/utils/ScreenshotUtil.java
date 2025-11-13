package utils;

import com.microsoft.playwright.Page;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static String captureScreenshot(Page page, String scenarioName, String status) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String sanitizedScenario = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
        String env= System.getProperty("env");
        String brand= System.getProperty("brand");
        String region=System.getProperty("region");
        String folderPath = "test-output/reports/"+env+"/"+brand+"/"+region+"/screenshots/" + sanitizedScenario + "/" + status;
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        String screenshotPath = folderPath + "/step_" + timestamp + ".png";

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(screenshotPath))
                .setFullPage(false));

        return screenshotPath;
    }

}
