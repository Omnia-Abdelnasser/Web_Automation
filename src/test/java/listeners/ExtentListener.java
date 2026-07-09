package listeners;

import com.aventstack.extentreports.*;
import org.testng.*;
import org.testng.ITestContext;
import reports.ExtentManager;

public class ExtentListener implements ITestListener {

    // make sure to initialize the ExtentReports instance only once
    ExtentReports extent = ExtentManager.getReport();
    // make results for each test available in the listener methods
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getName());
        test = extent.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass(result.getName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getName() + " FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip(result.getName() + " SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
