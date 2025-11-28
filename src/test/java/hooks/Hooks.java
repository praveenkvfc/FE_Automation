package hooks;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import config.ConfigReader;
import io.cucumber.java.*;
import utils.PlaywrightFactory;
import utils.ScreenshotUtil;
import utils.ExtentTestManager;

public class Hooks {
    public static String env;
    public static String brand;
    public static String region;
    public static String tag;

    @Before
    public void setUp(Scenario scenario) {
        // Read required system properties for environment
        env = System.getProperty("env");
        brand = System.getProperty("brand");
        region = System.getProperty("region");
        tag = System.getProperty("tag");

        if (env == null || brand == null || region == null) {
            throw new RuntimeException("Missing system properties. Please provide -Denv, -Dbrand, -Dregion (and optionally -Dtag).");
        }

        // Load configuration for this run
        ConfigReader.load(env, brand, region, tag);
        String browser = ConfigReader.get("browser"); // e.g., "chrome", "firefox", "webkit"
        if (browser == null || browser.isBlank()) {
            // Safe default: use Chrome to avoid Playwright bundled downloads
            browser = "chrome";
        }

        // Start test in Extent (thread-safe)
        ExtentTestManager.startTest(scenario.getName());

        // Initialize Playwright & Browser using our factory (ThreadLocal-aware)
        PlaywrightFactory.initBrowser(browser);
    }

    @After
    public void tearDown(Scenario scenario) {
        ExtentTest test = ExtentTestManager.getTest();

        try {
            Page page = PlaywrightFactory.getPage();

            if (scenario.isFailed()) {
                String screenshotPath = ScreenshotUtil.captureScreenshot(page, scenario.getName(), "failure");
                if (test != null) {
                    test.fail("Scenario Failed: " + scenario.getName(),
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            } else {
                if (test != null) {
                    test.pass("Scenario passed successfully: " + scenario.getName());
                }
            }
        } catch (Exception ex) {
            if (test != null) {
                test.warning("TearDown encountered an issue while capturing screenshot/logs: " + ex.getMessage());
            }
        } finally {
            // Ensure all Playwright resources are closed and ThreadLocals removed
            PlaywrightFactory.close();
        }
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) {
        ExtentTest test = ExtentTestManager.getTest();

        // Only capture step screenshots if the scenario isn't already failed
        if (!scenario.isFailed()) {
            try {
                Page page = PlaywrightFactory.getPage();
                String screenshotPath = ScreenshotUtil.captureScreenshot(page, scenario.getName(), "success");
                if (test != null) {
                    test.info("Step Passed",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            } catch (Exception ex) {
                if (test != null) {
                    test.warning("AfterStep screenshot failed: " + ex.getMessage());
                }
            }
        }
    }
}