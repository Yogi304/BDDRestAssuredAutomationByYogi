package com.api.bookstore.report;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.api.bookstore.logs.ConsoleColors;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;



public class ExtentReporter implements ITestListener {
	
	private static ThreadLocal<ExtentTest> extent = new ThreadLocal<>();
	private static ExtentReports report;
	private static ExtentReporter instance = new ExtentReporter();

	public static ExtentReporter getInstance() {
		return instance;
	} 
	
	public ExtentTest getExtentTest() {
		return extent.get();
	}

	public void onStart(ITestContext context) {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpledate = new SimpleDateFormat("dd_MMMM_hh_a_mm_ss");
		String name = simpledate.format(cal.getTime());

		// Path makes sure that codes works with both ubuntu and windows
		String path = Paths.get(System.getProperty("user.dir"), "report", name + "_Report.html").toString();

		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Extent Report for Book API");
		reporter.config().setDocumentTitle("RestAssured API Automation Report");

		report = new ExtentReports();
		report.attachReporter(reporter);

		report.setSystemInfo("Automation Tester", "Yogeeswara G");

	}

	public static void createTests(String scenarioName, String featureName) {

		ExtentTest extentTest = null;

		try {
			extentTest = report.createTest(scenarioName)
					.createNode("Feature: " + featureName + "<br>Scenario: " + scenarioName + "<br/>");

		} catch (Exception e) {
			e.printStackTrace();
		}

		extent.set(extentTest);

	}


	public void onTestStart(ITestResult result) {
		

		Object[] paramNames = Reporter.getCurrentTestResult().getParameters();
		String scenarioName = paramNames[0].toString().replaceAll("\"", "");
		String featureName = paramNames[1].toString().replaceAll("Optional|\\[|\\]|\"", "");
		createTests(scenarioName, featureName);
		getExtentTest().assignCategory(featureName);
		System.out.println(ConsoleColors.BLUE_UNDERLINED+result.getMethod().getMethodName()+ConsoleColors.RESET);
		System.out.println(ConsoleColors.BLUE+result.getMethod().getDescription()+ConsoleColors.RESET);
		
	}


	public void onTestSkipped(ITestResult result) {
		extent.get().fail(result.getThrowable().getMessage() + " skipped");
		System.out.println("===============TEST CASES ENDED======================");
	}
	
	public void onTestFailure(ITestResult result) {
		extent.get().fail(result.getThrowable().getMessage() + " failed: " + result.getThrowable());
		System.out.println("===============TEST CASES ENDED======================");
	}
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("===============TEST CASES ENDED======================");
	}


	public void onFinish(ITestContext context) {
		report.flush();
	}
}
