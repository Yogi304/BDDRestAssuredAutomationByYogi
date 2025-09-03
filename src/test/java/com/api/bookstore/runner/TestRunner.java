package com.api.bookstore.runner;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Listeners({com.api.bookstore.report.ExtentReporter.class})
@CucumberOptions(
	    features = "src/test/resources/features/",
	    glue = "com/api/bookstore",
	    plugin = {
	            "pretty",
	            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
	        }
	)
public class TestRunner extends AbstractTestNGCucumberTests{
	 private static Process fastApiProcess;

	    @BeforeSuite
	    public static void startServer() {
	        System.out.println("Starting FastAPI server...");
	        try {
	           
	            ProcessBuilder pb = new ProcessBuilder(
	                "uvicorn",
	                "main:app",
	                "--host", "0.0.0.0",
	                "--port", "8000"
	            );
	     
	            pb.directory(new java.io.File(System.getProperty("user.dir")+"/bookstoreApp/"));
	            pb.redirectErrorStream(true); 
	            fastApiProcess = pb.start();

	           
	            System.out.println("Waiting for server to initialize...");
	            TimeUnit.SECONDS.sleep(10); 
	            System.out.println("Server should be up and running!");

	        } catch(Exception e)
	        {
	        	e.fillInStackTrace();
	        	
	        }
	    }

	    @AfterSuite
	    public static void stopServer() {
	        if (fastApiProcess != null) {
	            System.out.println("Stopping FastAPI server...");
	            fastApiProcess.destroy(); 
	            try {
	                if (!fastApiProcess.waitFor(5, TimeUnit.SECONDS)) {
	                    System.out.println("Server did not stop gracefully, forcing shutdown...");
	                    fastApiProcess.destroyForcibly(); 
	                }
	                System.out.println("Server stopped.");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	                fastApiProcess.destroyForcibly();
	            }
	        }
	    }
}
