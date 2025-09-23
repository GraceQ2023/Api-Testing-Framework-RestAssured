package com.myorg.apitests.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.myorg.apitests.utils.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;


/**
 * ClassName: TestListener
 * Package: com.myorg.apitests.listeners
 * Description:
 *
 * @Author Grace
 * @Create 22/9/2025 11:13 pm
 * Version 1.0
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();  // Thread-safe drawer for each test
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    public static ExtentTest getExtentTest(){
        return extentTest.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        String description = result.getMethod().getDescription();

        ExtentTest test = extent.createTest(className + " :: " + methodName)
            .assignCategory(className); // create a new "test" entry in the report

        extentTest.set(test); // store in ThreadLocal(its own test drawer), so parallel tests don’t collide

        // log addition contex
        if(description != null){
            getExtentTest().info("Description: " + description);
        }

        Object[] params = result.getParameters();
        if(params.length > 0){
            getExtentTest().info("Test parameters " + Arrays.toString(params));
        }

        logger.info("Test started: " + methodName);
        getExtentTest().info("Test started: " + methodName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✅ Test passed: " + result.getMethod().getMethodName());
        getExtentTest().pass("✅ Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ Test failed: ", result.getMethod().getMethodName(), result.getThrowable());
        getExtentTest().fail("❌ Test failed: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.error("⚠️ Test skipped: " +result.getMethod().getMethodName());
        getExtentTest().skip("⚠️ Test skipped");
    }


    // call when ALL tests finish, each drawer writes its collected info to the final report
    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); // write everything into the HTML file
    }
}
