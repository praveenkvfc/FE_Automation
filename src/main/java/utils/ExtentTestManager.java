package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private static final ExtentReports extent = ExtentReportManager.getExtentReports();

    public static ExtentTest getTest() {
        return extentTestThreadLocal.get();
    }

    public static synchronized ExtentTest startTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTestThreadLocal.set(test);
        return test;
    }

    public static synchronized void endTest() {
        ExtentTest test = extentTestThreadLocal.get();
        if (test != null) {
            extentTestThreadLocal.remove();
        }
    }

    public static void flush() {
        ExtentReportManager.flushReport();
    }
}
