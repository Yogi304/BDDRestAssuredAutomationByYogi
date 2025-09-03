package com.api.bookstore.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.bookstore.report.ExtentReporter;


public class WrappedReportLogger {

	private static final Logger logger = LogManager.getLogger(WrappedReportLogger.class);
	

	public static void trace(String message) {
		logger.trace(message);
		ExtentReporter.getInstance().getExtentTest().info(message);
	}

	public static void debug(String message) {
		logger.debug(message);
		ExtentReporter.getInstance().getExtentTest().info(message);
	}


	public static void info(String message) {
		logger.info(ConsoleColors.GREEN+message+ConsoleColors.RESET);
	}
	
	
	public static void warn(String message) {
		logger.warn(ConsoleColors.YELLOW+message+ConsoleColors.RESET);
		ExtentReporter.getInstance().getExtentTest().warning(message);
	}


	public static void error(String message) {
		logger.error(ConsoleColors.RED_BOLD+message+ConsoleColors.RESET);
		
	}

	public static void fatal(String message) {
		logger.fatal(ConsoleColors.RED_BOLD+message+ConsoleColors.RESET);
		ExtentReporter.getInstance().getExtentTest().fail(message);
	}

}
