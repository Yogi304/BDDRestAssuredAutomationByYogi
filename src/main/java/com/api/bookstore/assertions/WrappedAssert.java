package com.api.bookstore.assertions;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.api.bookstore.logs.WrappedReportLogger;
import com.api.bookstore.report.ExtentReporter;
import com.api.bookstore.requestBuilder.ApiClient;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


public class WrappedAssert {

	
	public static void assertTrue(boolean condition, String message) {

		try {
			Assert.assertTrue(condition, message);
			WrappedReportLogger.info("[VERIFICATION - PASS] " + message );
			ExtentReporter.getInstance().getExtentTest().log(Status.PASS, "[VERIFICATION - PASS] " + message);

		} catch (AssertionError e) { 
			WrappedReportLogger.error("[VERIFICATION - FAIL] " + message );
			ExtentReporter.getInstance().getExtentTest().log(Status.FAIL, "[VERIFICATION - FAIL] " + message);
			Assert.fail("[VERIFICATION - FAIL] " + message);
		}

	}

	public static void assertFalse(boolean condition, String message) {
		try {
			Assert.assertFalse(condition, message);
			WrappedReportLogger.info("[VERIFICATION - PASS] " + message );
			ExtentReporter.getInstance().getExtentTest().log(Status.PASS, "[VERIFICATION - PASS] " + message);
		} catch (AssertionError e) { 
			WrappedReportLogger.error("[VERIFICATION - FAIL] " + message );
			ExtentReporter.getInstance().getExtentTest().log(Status.FAIL, "[VERIFICATION - FAIL] " + message);
			Assert.fail("[VERIFICATION - FAIL] " + message);
		}
	}
	
	
	public static void assertEquals(Object actual, Object expected, String message) {
		try {
			
			if(expected == null || expected.equals("")) {
				expected="BLANK";
			}
			
			if(actual == null || actual.equals("")) {
				actual="BLANK";
			}
			
			Assert.assertEquals(actual, expected, message);
			WrappedReportLogger.info("[VERIFICATION - PASS] " + message + ". EXPECTED ["+ expected +"] "+" ACTUAL ["+actual+"]");
			ExtentReporter.getInstance().getExtentTest().log(Status.PASS, "[VERIFICATION - PASS] " + message + ". EXPECTED ["+ expected +"] "+" ACTUAL ["+actual+"]");
		} catch (AssertionError e) { 
			WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + ". EXPECTED ["+ expected +"] "+" ACTUAL ["+actual+"]");
			ExtentReporter.getInstance().getExtentTest().log(Status.FAIL, "[VERIFICATION - FAIL] " + message + ". EXPECTED ["+ expected +"] "+" ACTUAL ["+actual+"]");
			Assert.fail("[VERIFICATION - FAIL] " + message + ". EXPECTED ["+ expected +"] "+" ACTUAL ["+actual+"]");
		}
	}

	
	public static void assertNotNull(Object value, String message) {
	    try {
	        Assert.assertNotNull(value, message);
	        WrappedReportLogger.info("[VERIFICATION - PASS] " + message + " | Value: " + value);
	        ExtentReporter.getInstance().getExtentTest()
	                .log(Status.PASS, "[VERIFICATION - PASS] " + message + " | Value: " + value);
	    } catch (AssertionError e) {
	        WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + " | Value is NULL");
	        ExtentReporter.getInstance().getExtentTest()
	                .log(Status.FAIL, "[VERIFICATION - FAIL] " + message + " | Value is NULL");
	        Assert.fail("[VERIFICATION - FAIL] " + message + " | Value is NULL");
	    }
	}


	public static void assertJsonSchema(Response response, String schemaPath, String message) {
	    try {
	        if (schemaPath == null || schemaPath.trim().isEmpty()) {
	            schemaPath = "BLANK";
	        }
	        
	        boolean matchesSchema;
	        
	     // Check if schema file exists in classpath
	        if (ApiClient.class.getClassLoader().getResource(schemaPath) == null) {
	            WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + ". SCHEMA FILE NOT FOUND [" + schemaPath + "]");
		        ExtentReporter.getInstance().getExtentTest()
		                .log(Status.FAIL, "[VERIFICATION - FAIL] " + message + ". SCHEMA FILE NOT FOUND [" + schemaPath + "]");
		        Assert.fail("[VERIFICATION - FAIL] " + message + ". SCHEMA FILE NOT FOUND [" + schemaPath + "]");
	           
	        }
	        
	        try {
	            response.then().assertThat()
	                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
	            matchesSchema = true;
	        } catch (AssertionError e) {
	            matchesSchema = false;
	        }

	        Assert.assertTrue(matchesSchema, message);
	        WrappedReportLogger.info("[VERIFICATION - PASS] " + message + ". SCHEMA [" + schemaPath + "]");
	        ExtentReporter.getInstance().getExtentTest()
	                .log(Status.PASS, "[VERIFICATION - PASS] " + message + ". SCHEMA [" + schemaPath + "]");

	    } catch (AssertionError e) {
	        WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + ". SCHEMA [" + schemaPath + "]");
	        ExtentReporter.getInstance().getExtentTest()
	                .log(Status.FAIL, "[VERIFICATION - FAIL] " + message + ". SCHEMA [" + schemaPath + "]");
	        Assert.fail("[VERIFICATION - FAIL] " + message + ". SCHEMA [" + schemaPath + "]");
	    }
	}

}
