package com.avaya.jtapi.tsapi.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class JTAPILoggingAdapter {
	private static boolean traceLoggingEnabled;
	private static boolean errorLoggingEnabled;
	private static boolean performanceLoggingEnabled;
	private static boolean somethingChanged = false;
	private static String traceLoggerLevel;
	private static String altTraceFile;
	private static String traceFileCount;
	// private static final String DEFAULT_TRACE_FILE_COUNT = "9";
	private static String traceFileSize;
	// private static final String DEFAULT_TRACE_FILE_SIZE = "50MB";
	private static String errorFile;
	private static String errorFileCount;
	// private static final String DEFAULT_ERROR_FILE_COUNT = "9";
	private static String errorFileSize;
	// private static final String DEFAULT_ERROR_FILE_SIZE = "50MB";
	private static String perfFile;
	private static String perfFileCount;
	// private static final String DEFAULT_PERFORMANCE_FILE_COUNT = "9";
	private static String perfFileSize;
	// private static final String DEFAULT_PERFORMANCE_FILE_SIZE = "50MB";
	// private static final String JTAPI_APPENDER =
	// "org.apache.log4j.RollingFileAppender";
	// private static final String JTAPI_DEFAULT_APPENDER =
	// "org.apache.log4j.ConsoleAppender";
	// private static final String JTAPI_APPENDER_LAYOUT =
	// "org.apache.log4j.PatternLayout";
	// private static final String JTAPI_APPENDER_LAYOUT_PATTERN =
	// "%d [%t] %-5p %c{1} - %m%n";
	// private static final String PROP_ALT_TRACE_FILE = "altTraceFile";
	// private static final String PROP_TRACE_FILE_COUNT = "traceFileCount";
	// private static final String PROP_TRACE_FILE_SIZE = "traceFileSize";
	// private static final String PROP_ERROR_FILE = "errorFile";
	// private static final String PROP_ERROR_FILE_COUNT = "errorFileCount";
	// private static final String PROP_ERROR_FILE_SIZE = "errorFileSize";
	// private static final String PROP_TRACE_LEVEL = "traceLoggerLevel";
	// private static final String PROP_PERFORMANCE_FILE = "perfFile";
	// private static final String PROP_PERFORMANCE_FILE_COUNT =
	// "perfFileCount";
	// private static final String PROP_PERFORMANCE_FILE_SIZE = "perfFileSize";
	// private static final String PERFORMANCE_LOGGER =
	// "log4j.logger.jtapi.performanceLogger";
	private static Hashtable<String, Boolean> propertyStatusTable = new Hashtable<String, Boolean>();

	static {
		JTAPILoggingAdapter.resetPropertyStatusTable();
	}

	public static String getAltTraceFile() {
		return JTAPILoggingAdapter.altTraceFile;
	}

	public static String getErrorFile() {
		return JTAPILoggingAdapter.errorFile;
	}

	public static String getErrorFileCount() {
		return JTAPILoggingAdapter.errorFileCount;
	}

	public static String getErrorFileSize() {
		return JTAPILoggingAdapter.errorFileSize;
	}

	public static String getPerfFile() {
		return JTAPILoggingAdapter.perfFile;
	}

	public static String getPerfFileCount() {
		return JTAPILoggingAdapter.perfFileCount;
	}

	public static String getPerfFileSize() {
		return JTAPILoggingAdapter.perfFileSize;
	}

	public static String getTraceFileCount() {
		return JTAPILoggingAdapter.traceFileCount;
	}

	public static String getTraceFileSize() {
		return JTAPILoggingAdapter.traceFileSize;
	}

	public static String getTraceLoggerLevel() {
		return JTAPILoggingAdapter.traceLoggerLevel;
	}

	private static void hasChanged(final String oldValue, final String newValue) {
		if (oldValue == null && newValue == null)
			return;
		if (oldValue != null && newValue == null)
			JTAPILoggingAdapter.somethingChanged = true;
		else if (oldValue == null && newValue != null)
			JTAPILoggingAdapter.somethingChanged = true;
		else if (!oldValue.equalsIgnoreCase(newValue))
			JTAPILoggingAdapter.somethingChanged = true;
	}

	public static void initializeLogging() {
		if (JTAPILoggingAdapter.somethingChanged) {
			JTAPILoggingAdapter.somethingChanged = false;
			final Properties loggingProperties = new Properties();

			if (JTAPILoggingAdapter.isTraceLoggingEnabled()
					|| JTAPILoggingAdapter.isErrorLoggingEnabled())
				if (!JTAPILoggingAdapter.isErrorLoggingEnabled())
					loggingProperties.put("log4j.logger.com.avaya.jtapi.tsapi",
							JTAPILoggingAdapter.traceLoggerLevel
									+ ",defaultAppender");
				else if (!JTAPILoggingAdapter.isTraceLoggingEnabled())
					loggingProperties.put("log4j.logger.com.avaya.jtapi.tsapi",
							"ERROR,errorAppender");
				else
					loggingProperties.put("log4j.logger.com.avaya.jtapi.tsapi",
							JTAPILoggingAdapter.traceLoggerLevel
									+ ",defaultAppender,errorAppender");
			if (JTAPILoggingAdapter.isTraceLoggingEnabled()) {
				if (JTAPILoggingAdapter.altTraceFile != null) {
					loggingProperties.put("log4j.appender.defaultAppender",
							"org.apache.log4j.RollingFileAppender");
					loggingProperties.put(
							"log4j.appender.defaultAppender.File",
							JTAPILoggingAdapter.altTraceFile);

					if (JTAPILoggingAdapter.traceFileCount != null)
						loggingProperties
								.put("log4j.appender.defaultAppender.MaxBackupIndex",
										Integer.valueOf(Integer
												.parseInt(JTAPILoggingAdapter.traceFileCount) - 1));
					else
						loggingProperties
								.put("log4j.appender.defaultAppender.MaxBackupIndex",
										"9");
					if (JTAPILoggingAdapter.traceFileSize != null)
						loggingProperties.put(
								"log4j.appender.defaultAppender.MaxFileSize",
								JTAPILoggingAdapter.traceFileSize);
					else
						loggingProperties.put(
								"log4j.appender.defaultAppender.MaxFileSize",
								"50MB");
				} else {
					loggingProperties.put("log4j.appender.defaultAppender",
							"org.apache.log4j.ConsoleAppender");
					loggingProperties.put(
							"log4j.appender.defaultAppender.Target",
							"System.out");
				}

				loggingProperties.put("log4j.appender.defaultAppender.layout",
						"org.apache.log4j.PatternLayout");
				loggingProperties
						.put("log4j.appender.defaultAppender.layout.ConversionPattern",
								"%d [%t] %-5p %c{1} - %m%n");
			}

			if (JTAPILoggingAdapter.isErrorLoggingEnabled()) {
				loggingProperties.put("log4j.appender.errorAppender",
						"org.apache.log4j.RollingFileAppender");
				loggingProperties.put("log4j.appender.errorAppender.File",
						JTAPILoggingAdapter.errorFile);
				loggingProperties.put("log4j.appender.errorAppender.threshold",
						"ERROR");

				if (JTAPILoggingAdapter.errorFileCount != null)
					loggingProperties
							.put("log4j.appender.errorAppender.MaxBackupIndex",
									Integer.valueOf(Integer
											.parseInt(JTAPILoggingAdapter.errorFileCount) - 1));
				else
					loggingProperties.put(
							"log4j.appender.errorAppender.MaxBackupIndex", "9");
				if (JTAPILoggingAdapter.errorFileSize != null)
					loggingProperties.put(
							"log4j.appender.errorAppender.MaxFileSize",
							JTAPILoggingAdapter.errorFileSize);
				else
					loggingProperties.put(
							"log4j.appender.errorAppender.MaxFileSize", "50MB");
				loggingProperties.put("log4j.appender.errorAppender.layout",
						"org.apache.log4j.PatternLayout");
				loggingProperties
						.put("log4j.appender.errorAppender.layout.ConversionPattern",
								"%d [%t] %-5p %c{1} - %m%n");
			}
			if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
				loggingProperties.put("log4j.logger.jtapi.performanceLogger",
						Level.TRACE + ",performanceAppender");
				loggingProperties.put("log4j.appender.performanceAppender",
						"org.apache.log4j.RollingFileAppender");
				loggingProperties.put(
						"log4j.appender.performanceAppender.File",
						JTAPILoggingAdapter.perfFile);

				if (JTAPILoggingAdapter.perfFileCount != null)
					loggingProperties
							.put("log4j.appender.performanceAppender.MaxBackupIndex",
									Integer.valueOf(Integer
											.parseInt(JTAPILoggingAdapter.perfFileCount) - 1));
				else
					loggingProperties
							.put("log4j.appender.performanceAppender.MaxBackupIndex",
									"9");
				if (JTAPILoggingAdapter.perfFileSize != null)
					loggingProperties.put(
							"log4j.appender.performanceAppender.MaxFileSize",
							JTAPILoggingAdapter.perfFileSize);
				else
					loggingProperties.put(
							"log4j.appender.performanceAppender.MaxFileSize",
							"50MB");
				loggingProperties.put(
						"log4j.appender.performanceAppender.layout",
						"org.apache.log4j.PatternLayout");
				loggingProperties
						.put("log4j.appender.performanceAppender.layout.ConversionPattern",
								"%d [%t] %-5p %c{1} - %m%n");
			}
			if (JTAPILoggingAdapter.isTraceLoggingEnabled()
					|| JTAPILoggingAdapter.isErrorLoggingEnabled()
					|| JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
				PropertyConfigurator.configure(loggingProperties);
				final Logger theLogger = Logger
						.getLogger(JTAPILoggingAdapter.class);
				theLogger.info("Logging initialized correctly");
				theLogger.info("Logging properties = "
						+ loggingProperties.toString());
				if (JTAPILoggingAdapter.performanceLoggingEnabled)
					PerfStatisticsCollector.initPerfStatisticsCollector();
			}
		}
		JTAPILoggingAdapter.resetPropertyStatusTable();
	}

	public static boolean isErrorLoggingEnabled() {
		return JTAPILoggingAdapter.errorLoggingEnabled;
	}

	public static boolean isPerformanceLoggingEnabled() {
		return JTAPILoggingAdapter.performanceLoggingEnabled;
	}

	public static boolean isTraceLoggingEnabled() {
		return JTAPILoggingAdapter.traceLoggingEnabled;
	}

	private static void resetPropertyStatusTable() {
		JTAPILoggingAdapter.propertyStatusTable.put("altTraceFile",
				Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("traceFileCount",
				Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("traceFileSize",
				Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("errorFile", Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("errorFileCount",
				Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("errorFileSize",
				Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("traceLoggerLevel",
				Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("perfFile", Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("perfFileCount",
				Boolean.FALSE);
		JTAPILoggingAdapter.propertyStatusTable.put("perfFileSize",
				Boolean.FALSE);
	}

	public static void setAltTraceFile(final String altTraceFile) {
		JTAPILoggingAdapter.hasChanged(altTraceFile, altTraceFile);
		JTAPILoggingAdapter.altTraceFile = altTraceFile;
		if (altTraceFile != null)
			JTAPILoggingAdapter.propertyStatusTable.put("altTraceFile",
					Boolean.TRUE);
	}

	public static void setErrorFile(final String errorFile) {
		JTAPILoggingAdapter.hasChanged(errorFile, errorFile);
		JTAPILoggingAdapter.errorFile = errorFile;
		if (errorFile != null) {
			JTAPILoggingAdapter.setErrorLoggingEnabled(true);
			JTAPILoggingAdapter.propertyStatusTable.put("errorFile",
					Boolean.TRUE);
		} else
			JTAPILoggingAdapter.setErrorLoggingEnabled(false);
	}

	public static void setErrorFileCount(final String errorFileCount) {
		JTAPILoggingAdapter.hasChanged(errorFileCount, errorFileCount);
		JTAPILoggingAdapter.errorFileCount = errorFileCount;
		if (errorFileCount != null)
			JTAPILoggingAdapter.propertyStatusTable.put("errorFileCount",
					Boolean.TRUE);
	}

	public static void setErrorFileSize(final String errorFileSize) {
		JTAPILoggingAdapter.hasChanged(errorFileSize, errorFileSize);
		JTAPILoggingAdapter.errorFileSize = errorFileSize;
		if (errorFileSize != null)
			JTAPILoggingAdapter.propertyStatusTable.put("errorFileSize",
					Boolean.TRUE);
	}

	public static void setErrorLoggingEnabled(final boolean errorLoggingEnabled) {
		JTAPILoggingAdapter.errorLoggingEnabled = errorLoggingEnabled;
	}

	public static void setPerfFile(final String perfFile) {
		JTAPILoggingAdapter.hasChanged(perfFile, perfFile);
		JTAPILoggingAdapter.perfFile = perfFile;
		if (perfFile != null) {
			JTAPILoggingAdapter.setPerformanceLoggingEnabled(true);
			JTAPILoggingAdapter.propertyStatusTable.put("perfFile",
					Boolean.TRUE);
		} else
			JTAPILoggingAdapter.setPerformanceLoggingEnabled(false);
	}

	public static void setPerfFileCount(final String perfFileCount) {
		JTAPILoggingAdapter.hasChanged(perfFileCount, perfFileCount);
		JTAPILoggingAdapter.perfFileCount = perfFileCount;
		if (perfFileCount != null)
			JTAPILoggingAdapter.propertyStatusTable.put("perfFileCount",
					Boolean.TRUE);
	}

	public static void setPerfFileSize(final String perfFileSize) {
		JTAPILoggingAdapter.hasChanged(perfFileSize, perfFileSize);
		JTAPILoggingAdapter.perfFileSize = perfFileSize;
		if (perfFileSize != null)
			JTAPILoggingAdapter.propertyStatusTable.put("perfFileSize",
					Boolean.TRUE);
	}

	public static void setPerformanceLoggingEnabled(
			final boolean performanceLoggingEnabled) {
		JTAPILoggingAdapter.performanceLoggingEnabled = performanceLoggingEnabled;
	}

	public static void setTraceFileCount(final String traceFileCount) {
		JTAPILoggingAdapter.hasChanged(traceFileCount, traceFileCount);
		JTAPILoggingAdapter.traceFileCount = traceFileCount;
		if (traceFileCount != null)
			JTAPILoggingAdapter.propertyStatusTable.put("traceFileCount",
					Boolean.TRUE);
	}

	public static void setTraceFileSize(final String traceFileSize) {
		JTAPILoggingAdapter.hasChanged(traceFileSize, traceFileSize);
		JTAPILoggingAdapter.traceFileSize = traceFileSize;
		if (traceFileSize != null)
			JTAPILoggingAdapter.propertyStatusTable.put("traceFileSize",
					Boolean.TRUE);
	}

	public static void setTraceLoggerLevel(String traceLoggerLevel) {
		String log4jLevel = null;

		if (traceLoggerLevel == null) {
			JTAPILoggingAdapter.setTraceLoggingEnabled(false);
			return;
		}
		try {
			final int debugLevel = Integer.parseInt(traceLoggerLevel);
			if (debugLevel >= 0 && debugLevel <= 7) {
				JTAPILoggingAdapter.setTraceLoggingEnabled(true);
				if (debugLevel == 0) {
					log4jLevel = Level.OFF.toString();
					JTAPILoggingAdapter.setTraceLoggingEnabled(false);
				} else if (debugLevel > 0 && debugLevel < 6)
					log4jLevel = Level.INFO.toString();
				else if (debugLevel == 6)
					log4jLevel = Level.DEBUG.toString();
				else if (debugLevel == 7)
					log4jLevel = Level.TRACE.toString();
				JTAPILoggingAdapter.hasChanged(traceLoggerLevel, log4jLevel);
				traceLoggerLevel = log4jLevel;
			} else if (JtapiUtils.isLog4jConfigured()) {
				final Logger theLogger = Logger
						.getLogger(JTAPILoggingAdapter.class);
				theLogger
						.error("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
				theLogger
						.error("Error occured while reading logger level property. You entered : "
								+ traceLoggerLevel);
			} else {
				System.out
						.println("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
				System.out
						.println("Error occured while reading logger level property. You entered : "
								+ traceLoggerLevel);
			}
		} catch (final Exception e) {
			if (JtapiUtils.isLog4jConfigured()) {
				final Logger theLogger = Logger
						.getLogger(JTAPILoggingAdapter.class);
				theLogger
						.error("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
				theLogger.error(
						"Error occured while reading logger level property. You entered : "
								+ traceLoggerLevel, e);
			} else {
				System.out
						.println("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
				System.out
						.println("Error occured while reading logger level property. You entered : "
								+ traceLoggerLevel);
				e.printStackTrace();
			}
			return;
		}
		JTAPILoggingAdapter.propertyStatusTable.put("traceLoggerLevel",
				Boolean.TRUE);
	}

	public static void setTraceLoggingEnabled(final boolean traceLoggingEnabled) {
		JTAPILoggingAdapter.traceLoggingEnabled = traceLoggingEnabled;
	}

	public static void updateLoggingProperties() {
		final Set<String> keySet = JTAPILoggingAdapter.propertyStatusTable
				.keySet();
		final Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
			final String key = keySetIterator.next();
			final Boolean value = JTAPILoggingAdapter.propertyStatusTable
					.get(key);
			if (!value.booleanValue())
				if (key.equals("altTraceFile"))
					JTAPILoggingAdapter.setAltTraceFile(null);
				else if (key.equals("errorFile"))
					JTAPILoggingAdapter.setErrorFile(null);
				else if (key.equals("errorFileCount"))
					JTAPILoggingAdapter.setErrorFileCount(null);
				else if (key.equals("errorFileSize"))
					JTAPILoggingAdapter.setErrorFileSize(null);
				else if (key.equals("traceFileCount"))
					JTAPILoggingAdapter.setTraceFileCount(null);
				else if (key.equals("traceFileSize"))
					JTAPILoggingAdapter.setTraceFileSize(null);
				else if (key.equals("traceLoggerLevel"))
					JTAPILoggingAdapter.setTraceLoggerLevel(null);
				else if (key.equals("perfFile")) {
					JTAPILoggingAdapter.setPerfFile(null);
					PerfStatisticsCollector.shutdown();
				} else if (key.equals("perfFileCount"))
					JTAPILoggingAdapter.setPerfFileCount(null);
				else if (key.equals("perfFileSize"))
					JTAPILoggingAdapter.setPerfFileSize(null);
		}
		JTAPILoggingAdapter.initializeLogging();
		if (JTAPILoggingAdapter.perfFile != null)
			PerfStatisticsCollector.updatePerfStatisticsCollectorConfig();
	}
}
