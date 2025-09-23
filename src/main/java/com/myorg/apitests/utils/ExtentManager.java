package com.myorg.apitests.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.myorg.apitests.listeners.TestListener;

/**
 * ClassName: ExtentManager
 * Package: com.myorg.apitests.utils
 * Description:
 *
 * @Author Grace
 * @Create 22/9/2025 11:12 pm
 * Version 1.0
 */
public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance(){
        // only one notebook (ExtentReport) is used for the whole test run
        if(extent == null){
            String reportPath =  System.getProperty("user.dir") + "/src/test/resources/report/extent-report.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath); // connects the HTML reporter(pen) to the report manager(notebook)
            reporter.config().setReportName("API Automation Report");  // shown on browser tab
            reporter.config().setDocumentTitle("Test Result"); // title of the report
            reporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Grace");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    // wrapper to log steps
    public static void logStep(String message){
        ExtentTest extentTest = TestListener.getExtentTest(); // get the assigned extentTest(drawer)from Listener
        if(extentTest != null){
            extentTest.info(message);
        }
    }

    public static void logPass(String message){
        ExtentTest extentTest = TestListener.getExtentTest();
        if(extentTest != null){
            extentTest.pass("✅ " + message);
        }
    }

    public static void logFail(String message){
        ExtentTest extentTest = TestListener.getExtentTest();
        if(extentTest != null){
            extentTest.fail("❌ " + message);
        }
    }


}
