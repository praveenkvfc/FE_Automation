package hooks;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.ConfigReader;
import io.cucumber.java.*;
import utils.PlaywrightFactory;
import utils.ScreenshotUtil;
import utils.ExtentTestManager;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Hooks {
    private static Playwright playwright;
    public static String env;
    public static String brand;
    public static String region;
    public static String tag;

    @Before
    public void setUp(Scenario scenario) {
        env = System.getProperty("env");
        brand = System.getProperty("brand");
        region = System.getProperty("region");
        tag = System.getProperty("tag");
        if (env == null || brand == null || region == null) {
            throw new RuntimeException("Missing system properties. please provide -Denv, -Dbrand,-Dregion");
        }
        ConfigReader.load(env, brand, region, tag);
        String browser = ConfigReader.get("browser");

        // Start test in ExtentTestManager (ThreadLocal)
        ExtentTestManager.startTest(scenario.getName());

        PlaywrightFactory.initBrowser(browser);
    }

    @After
    public void tearDown(Scenario scenario) {
        Page page = PlaywrightFactory.getPage();
        ExtentTest test = ExtentTestManager.getTest();

        if (scenario.isFailed()) {
            String screenshotPath =
                    ScreenshotUtil.captureScreenshot(page, scenario.getName(), "failure");
            test.fail("Scenario Failed Screenshot",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.pass("Scenario passed successfully: " + scenario.getName());
        }
        PlaywrightFactory.close();
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) {
        Page page = PlaywrightFactory.getPage();
        ExtentTest test = ExtentTestManager.getTest();

        if (!scenario.isFailed()) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(page, scenario.getName(), "success");
            test.info("Step Passed Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
    }
}


