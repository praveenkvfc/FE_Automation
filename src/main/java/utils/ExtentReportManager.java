package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static String reportDir;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            synchronized (ExtentReportManager.class) {
                if (extent == null) {
                    reportDir = getReportDirectory();
                    new File(reportDir).mkdirs();
                    String reportPath = reportDir + "/extent-report.html";

                    ExtentSparkReporter htmlReporter = new
                            ExtentSparkReporter(reportPath);
                    htmlReporter.config().setDocumentTitle("Automation Test Report");
                            htmlReporter.config().setReportName("Execution Results");
                    htmlReporter.config().setTheme(Theme.STANDARD);

                    extent = new ExtentReports();
                    extent.attachReporter(htmlReporter);

                    extent.setSystemInfo("Environment",
                            System.getProperty("env", "Not Set"));
                    extent.setSystemInfo("Browser",
                            System.getProperty("browser", "chrome"));
                    extent.setSystemInfo("Brand",
                            System.getProperty("brand", "Not Set"));
                    extent.setSystemInfo("Region",
                            System.getProperty("region", "Not Set"));
                }
            }
        }
        return extent;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static String getReportDirectory() {
        String env = System.getProperty("env", "unknown");
        String brand = System.getProperty("brand", "unknown");
        String region = System.getProperty("region", "unknown");
        String timestamp = new
                SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return System.getProperty("user.dir") + "/test-output/reports/" +
                env + "/" + brand + "/" + region + "/";
    }
}

