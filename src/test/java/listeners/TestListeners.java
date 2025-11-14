package listeners;

import io.cucumber.java.an.E;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

public class TestListeners implements ITestListener{

    @Override
    public void onTestStart(ITestResult result)
    {
        String testName= result.getMethod().getMethodName();
        ExtentTestManager.startTest(testName);
        System.out.println("Test Started : "+ testName);
    }

    @Override
    public void onTestSuccess(ITestResult result)
    {
        ExtentTestManager.getTest().pass("Test Passed");
    }

//    @Override
//    public void onTestFailure(ITestResult result)
//    {
//        String testname = result.getMethod().getMethodName();
//        ExtentTestManager.getTest().fail(result.getThrowable());
//        try{
//            String screenshotpath= ScreenshotUtil.captureScreenshot(testname);
//            ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotpath);
//        }catch (Exception e){
//            System.out.println("Im in ITestListener class: Screenshot not captured");
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public void onFinish(ITestContext context)
    {
       ExtentTestManager.flush();
    }

    @Override
    public void onTestSkipped(ITestResult result)
    {
        System.out.println("Test Skipped : "+ result.getName());
    }

    @Override
    public void onStart(ITestContext context)
    {
        System.out.println("Test Suite started : "+ context.getName());
    }
}
