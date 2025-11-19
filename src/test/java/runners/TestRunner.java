package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


@CucumberOptions(
        features = "src/test/java/features",
        glue = {"stepdefinitions", "hooks"},
        dryRun = false,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true
)


public class TestRunner extends AbstractTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;

    @Factory
    public static Object[] createInstances() {
        String env = System.getProperty("env");
        String tag = System.getProperty("tag");
        String brand = System.getProperty("brand");
        String region = System.getProperty("region");

        if (env == null || tag == null || region == null || brand == null) {
            throw new RuntimeException("Please pass -Denv, -Dbrand, -Dregion and -Dtag while executing.");
        }

        String configpath = Paths.get(System.getProperty("user.dir"),
                "src", "test", "resources","config_files",env , brand,
                brand+".properties").toString();
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream(configpath)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config :" + configpath, e);
        }

        String regionURLkey = region + "_url";
        String baseUrl = prop.getProperty(regionURLkey);

        if (baseUrl == null) {
            throw new RuntimeException("URL for region key " + regionURLkey + " not found in " + configpath);
        }

        System.setProperty("url", baseUrl);
        for (String key : prop.stringPropertyNames()) {
            if (key.equals(regionURLkey)) {
                System.setProperty(key, prop.getProperty(key));
            }
        }

        return new Object[]{new TestRunner()};
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        String tag = System.getProperty("tag","@smoke");
        System.setProperty("cucumber.filter.tags", tag);
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner != null) {
            testNGCucumberRunner.finish();
        }
    }
}


