/*     */ package com.avaya.jtapi.tsapi.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PropertyConfigurator;
/*     */ 
/*     */ public class JTAPILoggingAdapter
/*     */ {
/*     */   private static boolean traceLoggingEnabled;
/*     */   private static boolean errorLoggingEnabled;
/*     */   private static boolean performanceLoggingEnabled;
/*  84 */   private static boolean somethingChanged = false;
/*     */   private static String traceLoggerLevel;
/*     */   private static String altTraceFile;
/*     */   private static String traceFileCount;
/*     */   private static final String DEFAULT_TRACE_FILE_COUNT = "9";
/*     */   private static String traceFileSize;
/*     */   private static final String DEFAULT_TRACE_FILE_SIZE = "50MB";
/*     */   private static String errorFile;
/*     */   private static String errorFileCount;
/*     */   private static final String DEFAULT_ERROR_FILE_COUNT = "9";
/*     */   private static String errorFileSize;
/*     */   private static final String DEFAULT_ERROR_FILE_SIZE = "50MB";
/*     */   private static String perfFile;
/*     */   private static String perfFileCount;
/*     */   private static final String DEFAULT_PERFORMANCE_FILE_COUNT = "9";
/*     */   private static String perfFileSize;
/*     */   private static final String DEFAULT_PERFORMANCE_FILE_SIZE = "50MB";
/*     */   private static final String JTAPI_APPENDER = "org.apache.log4j.RollingFileAppender";
/*     */   private static final String JTAPI_DEFAULT_APPENDER = "org.apache.log4j.ConsoleAppender";
/*     */   private static final String JTAPI_APPENDER_LAYOUT = "org.apache.log4j.PatternLayout";
/*     */   private static final String JTAPI_APPENDER_LAYOUT_PATTERN = "%d [%t] %-5p %c{1} - %m%n";
/*     */   private static final String PROP_ALT_TRACE_FILE = "altTraceFile";
/*     */   private static final String PROP_TRACE_FILE_COUNT = "traceFileCount";
/*     */   private static final String PROP_TRACE_FILE_SIZE = "traceFileSize";
/*     */   private static final String PROP_ERROR_FILE = "errorFile";
/*     */   private static final String PROP_ERROR_FILE_COUNT = "errorFileCount";
/*     */   private static final String PROP_ERROR_FILE_SIZE = "errorFileSize";
/*     */   private static final String PROP_TRACE_LEVEL = "traceLoggerLevel";
/*     */   private static final String PROP_PERFORMANCE_FILE = "perfFile";
/*     */   private static final String PROP_PERFORMANCE_FILE_COUNT = "perfFileCount";
/*     */   private static final String PROP_PERFORMANCE_FILE_SIZE = "perfFileSize";
/*     */   private static final String PERFORMANCE_LOGGER = "log4j.logger.jtapi.performanceLogger";
/*  85 */   private static Hashtable<String, Boolean> propertyStatusTable = new Hashtable();
/*     */ 
/*     */   private static void resetPropertyStatusTable()
/*     */   {
/*  90 */     propertyStatusTable.put("altTraceFile", Boolean.FALSE);
/*  91 */     propertyStatusTable.put("traceFileCount", Boolean.FALSE);
/*  92 */     propertyStatusTable.put("traceFileSize", Boolean.FALSE);
/*  93 */     propertyStatusTable.put("errorFile", Boolean.FALSE);
/*  94 */     propertyStatusTable.put("errorFileCount", Boolean.FALSE);
/*  95 */     propertyStatusTable.put("errorFileSize", Boolean.FALSE);
/*  96 */     propertyStatusTable.put("traceLoggerLevel", Boolean.FALSE);
/*  97 */     propertyStatusTable.put("perfFile", Boolean.FALSE);
/*  98 */     propertyStatusTable.put("perfFileCount", Boolean.FALSE);
/*  99 */     propertyStatusTable.put("perfFileSize", Boolean.FALSE);
/*     */   }
/*     */ 
/*     */   private static void hasChanged(String oldValue, String newValue) {
/* 103 */     if ((oldValue == null) && (newValue == null))
/* 104 */       return;
/* 105 */     if ((oldValue != null) && (newValue == null))
/* 106 */       somethingChanged = true;
/* 107 */     else if ((oldValue == null) && (newValue != null))
/* 108 */       somethingChanged = true;
/* 109 */     else if (!oldValue.equalsIgnoreCase(newValue))
/* 110 */       somethingChanged = true;
/*     */   }
/*     */ 
/*     */   public static void initializeLogging()
/*     */   {
/* 116 */     if (somethingChanged) {
/* 117 */       somethingChanged = false;
/* 118 */       Properties loggingProperties = new Properties();
/*     */ 
/* 120 */       if ((isTraceLoggingEnabled()) || (isErrorLoggingEnabled())) {
/* 121 */         if (!isErrorLoggingEnabled())
/* 122 */           loggingProperties.put("log4j.logger.com.avaya.jtapi.tsapi", traceLoggerLevel + ",defaultAppender");
/* 123 */         else if (!isTraceLoggingEnabled())
/* 124 */           loggingProperties.put("log4j.logger.com.avaya.jtapi.tsapi", "ERROR,errorAppender");
/*     */         else {
/* 126 */           loggingProperties.put("log4j.logger.com.avaya.jtapi.tsapi", traceLoggerLevel + ",defaultAppender,errorAppender");
/*     */         }
/*     */       }
/* 129 */       if (isTraceLoggingEnabled())
/*     */       {
/* 131 */         if (altTraceFile != null)
/*     */         {
/* 133 */           loggingProperties.put("log4j.appender.defaultAppender", "org.apache.log4j.RollingFileAppender");
/* 134 */           loggingProperties.put("log4j.appender.defaultAppender.File", altTraceFile);
/*     */ 
/* 137 */           if (traceFileCount != null)
/* 138 */             loggingProperties.put("log4j.appender.defaultAppender.MaxBackupIndex", Integer.valueOf(Integer.parseInt(traceFileCount) - 1));
/*     */           else {
/* 140 */             loggingProperties.put("log4j.appender.defaultAppender.MaxBackupIndex", "9");
/*     */           }
/* 142 */           if (traceFileSize != null)
/* 143 */             loggingProperties.put("log4j.appender.defaultAppender.MaxFileSize", traceFileSize);
/*     */           else
/* 145 */             loggingProperties.put("log4j.appender.defaultAppender.MaxFileSize", "50MB");
/*     */         }
/*     */         else
/*     */         {
/* 149 */           loggingProperties.put("log4j.appender.defaultAppender", "org.apache.log4j.ConsoleAppender");
/* 150 */           loggingProperties.put("log4j.appender.defaultAppender.Target", "System.out");
/*     */         }
/*     */ 
/* 153 */         loggingProperties.put("log4j.appender.defaultAppender.layout", "org.apache.log4j.PatternLayout");
/* 154 */         loggingProperties.put("log4j.appender.defaultAppender.layout.ConversionPattern", "%d [%t] %-5p %c{1} - %m%n");
/*     */       }
/*     */ 
/* 157 */       if (isErrorLoggingEnabled())
/*     */       {
/* 159 */         loggingProperties.put("log4j.appender.errorAppender", "org.apache.log4j.RollingFileAppender");
/* 160 */         loggingProperties.put("log4j.appender.errorAppender.File", errorFile);
/* 161 */         loggingProperties.put("log4j.appender.errorAppender.threshold", "ERROR");
/*     */ 
/* 164 */         if (errorFileCount != null)
/* 165 */           loggingProperties.put("log4j.appender.errorAppender.MaxBackupIndex", Integer.valueOf(Integer.parseInt(errorFileCount) - 1));
/*     */         else {
/* 167 */           loggingProperties.put("log4j.appender.errorAppender.MaxBackupIndex", "9");
/*     */         }
/* 169 */         if (errorFileSize != null)
/* 170 */           loggingProperties.put("log4j.appender.errorAppender.MaxFileSize", errorFileSize);
/*     */         else
/* 172 */           loggingProperties.put("log4j.appender.errorAppender.MaxFileSize", "50MB");
/* 173 */         loggingProperties.put("log4j.appender.errorAppender.layout", "org.apache.log4j.PatternLayout");
/* 174 */         loggingProperties.put("log4j.appender.errorAppender.layout.ConversionPattern", "%d [%t] %-5p %c{1} - %m%n");
/*     */       }
/* 176 */       if (isPerformanceLoggingEnabled()) {
/* 177 */         loggingProperties.put("log4j.logger.jtapi.performanceLogger", Level.TRACE + ",performanceAppender");
/* 178 */         loggingProperties.put("log4j.appender.performanceAppender", "org.apache.log4j.RollingFileAppender");
/* 179 */         loggingProperties.put("log4j.appender.performanceAppender.File", perfFile);
/*     */ 
/* 182 */         if (perfFileCount != null)
/* 183 */           loggingProperties.put("log4j.appender.performanceAppender.MaxBackupIndex", Integer.valueOf(Integer.parseInt(perfFileCount) - 1));
/*     */         else {
/* 185 */           loggingProperties.put("log4j.appender.performanceAppender.MaxBackupIndex", "9");
/*     */         }
/* 187 */         if (perfFileSize != null)
/* 188 */           loggingProperties.put("log4j.appender.performanceAppender.MaxFileSize", perfFileSize);
/*     */         else
/* 190 */           loggingProperties.put("log4j.appender.performanceAppender.MaxFileSize", "50MB");
/* 191 */         loggingProperties.put("log4j.appender.performanceAppender.layout", "org.apache.log4j.PatternLayout");
/* 192 */         loggingProperties.put("log4j.appender.performanceAppender.layout.ConversionPattern", "%d [%t] %-5p %c{1} - %m%n");
/*     */       }
/* 194 */       if ((isTraceLoggingEnabled()) || (isErrorLoggingEnabled()) || (isPerformanceLoggingEnabled())) {
/* 195 */         PropertyConfigurator.configure(loggingProperties);
/* 196 */         Logger theLogger = Logger.getLogger(JTAPILoggingAdapter.class);
/* 197 */         theLogger.info("Logging initialized correctly");
/* 198 */         theLogger.info("Logging properties = " + loggingProperties.toString());
/* 199 */         if (performanceLoggingEnabled)
/* 200 */           PerfStatisticsCollector.initPerfStatisticsCollector();
/*     */       }
/*     */     }
/* 203 */     resetPropertyStatusTable();
/*     */   }
/*     */ 
/*     */   public static void updateLoggingProperties() {
/* 207 */     Set keySet = propertyStatusTable.keySet();
/* 208 */     Iterator keySetIterator = keySet.iterator();
/* 209 */     while (keySetIterator.hasNext()) {
/* 210 */       String key = (String)keySetIterator.next();
/* 211 */       Boolean value = (Boolean)propertyStatusTable.get(key);
/* 212 */       if (!value.booleanValue())
/* 213 */         if (key.equals("altTraceFile")) {
/* 214 */           setAltTraceFile(null);
/* 215 */         } else if (key.equals("errorFile")) {
/* 216 */           setErrorFile(null);
/* 217 */         } else if (key.equals("errorFileCount")) {
/* 218 */           setErrorFileCount(null);
/* 219 */         } else if (key.equals("errorFileSize")) {
/* 220 */           setErrorFileSize(null);
/* 221 */         } else if (key.equals("traceFileCount")) {
/* 222 */           setTraceFileCount(null);
/* 223 */         } else if (key.equals("traceFileSize")) {
/* 224 */           setTraceFileSize(null);
/* 225 */         } else if (key.equals("traceLoggerLevel")) {
/* 226 */           setTraceLoggerLevel(null);
/* 227 */         } else if (key.equals("perfFile")) {
/* 228 */           setPerfFile(null);
/* 229 */           PerfStatisticsCollector.shutdown();
/* 230 */         } else if (key.equals("perfFileCount")) {
/* 231 */           setPerfFileCount(null);
/* 232 */         } else if (key.equals("perfFileSize")) {
/* 233 */           setPerfFileSize(null);
/*     */         }
/*     */     }
/* 236 */     initializeLogging();
/* 237 */     if (perfFile != null)
/* 238 */       PerfStatisticsCollector.updatePerfStatisticsCollectorConfig();
/*     */   }
/*     */ 
/*     */   public static String getAltTraceFile()
/*     */   {
/* 245 */     return altTraceFile;
/*     */   }
/*     */ 
/*     */   public static void setAltTraceFile(String altTraceFile)
/*     */   {
/* 252 */     hasChanged(altTraceFile, altTraceFile);
/* 253 */     altTraceFile = altTraceFile;
/* 254 */     if (altTraceFile != null)
/* 255 */       propertyStatusTable.put("altTraceFile", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static String getTraceFileCount()
/*     */   {
/* 262 */     return traceFileCount;
/*     */   }
/*     */ 
/*     */   public static void setTraceFileCount(String traceFileCount)
/*     */   {
/* 269 */     hasChanged(traceFileCount, traceFileCount);
/* 270 */     traceFileCount = traceFileCount;
/* 271 */     if (traceFileCount != null)
/* 272 */       propertyStatusTable.put("traceFileCount", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static String getTraceFileSize()
/*     */   {
/* 279 */     return traceFileSize;
/*     */   }
/*     */ 
/*     */   public static void setTraceFileSize(String traceFileSize)
/*     */   {
/* 286 */     hasChanged(traceFileSize, traceFileSize);
/* 287 */     traceFileSize = traceFileSize;
/* 288 */     if (traceFileSize != null)
/* 289 */       propertyStatusTable.put("traceFileSize", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static String getErrorFile()
/*     */   {
/* 296 */     return errorFile;
/*     */   }
/*     */ 
/*     */   public static void setErrorFile(String errorFile)
/*     */   {
/* 303 */     hasChanged(errorFile, errorFile);
/* 304 */     errorFile = errorFile;
/* 305 */     if (errorFile != null) {
/* 306 */       setErrorLoggingEnabled(true);
/* 307 */       propertyStatusTable.put("errorFile", Boolean.TRUE);
/*     */     } else {
/* 309 */       setErrorLoggingEnabled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getErrorFileCount()
/*     */   {
/* 316 */     return errorFileCount;
/*     */   }
/*     */ 
/*     */   public static void setErrorFileCount(String errorFileCount)
/*     */   {
/* 323 */     hasChanged(errorFileCount, errorFileCount);
/* 324 */     errorFileCount = errorFileCount;
/* 325 */     if (errorFileCount != null)
/* 326 */       propertyStatusTable.put("errorFileCount", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static String getErrorFileSize()
/*     */   {
/* 333 */     return errorFileSize;
/*     */   }
/*     */ 
/*     */   public static void setErrorFileSize(String errorFileSize)
/*     */   {
/* 340 */     hasChanged(errorFileSize, errorFileSize);
/* 341 */     errorFileSize = errorFileSize;
/* 342 */     if (errorFileSize != null)
/* 343 */       propertyStatusTable.put("errorFileSize", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static boolean isTraceLoggingEnabled()
/*     */   {
/* 350 */     return traceLoggingEnabled;
/*     */   }
/*     */ 
/*     */   public static void setTraceLoggingEnabled(boolean traceLoggingEnabled)
/*     */   {
/* 357 */     traceLoggingEnabled = traceLoggingEnabled;
/*     */   }
/*     */ 
/*     */   public static boolean isErrorLoggingEnabled()
/*     */   {
/* 364 */     return errorLoggingEnabled;
/*     */   }
/*     */ 
/*     */   public static void setErrorLoggingEnabled(boolean errorLoggingEnabled)
/*     */   {
/* 371 */     errorLoggingEnabled = errorLoggingEnabled;
/*     */   }
/*     */ 
/*     */   public static String getTraceLoggerLevel()
/*     */   {
/* 378 */     return traceLoggerLevel;
/*     */   }
/*     */ 
/*     */   public static String getPerfFile()
/*     */   {
/* 385 */     return perfFile;
/*     */   }
/*     */ 
/*     */   public static void setPerfFile(String perfFile)
/*     */   {
/* 392 */     hasChanged(perfFile, perfFile);
/* 393 */     perfFile = perfFile;
/* 394 */     if (perfFile != null) {
/* 395 */       setPerformanceLoggingEnabled(true);
/* 396 */       propertyStatusTable.put("perfFile", Boolean.TRUE);
/*     */     } else {
/* 398 */       setPerformanceLoggingEnabled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getPerfFileCount()
/*     */   {
/* 405 */     return perfFileCount;
/*     */   }
/*     */ 
/*     */   public static void setPerfFileCount(String perfFileCount)
/*     */   {
/* 412 */     hasChanged(perfFileCount, perfFileCount);
/* 413 */     perfFileCount = perfFileCount;
/* 414 */     if (perfFileCount != null)
/* 415 */       propertyStatusTable.put("perfFileCount", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static String getPerfFileSize()
/*     */   {
/* 422 */     return perfFileSize;
/*     */   }
/*     */ 
/*     */   public static void setPerfFileSize(String perfFileSize)
/*     */   {
/* 429 */     hasChanged(perfFileSize, perfFileSize);
/* 430 */     perfFileSize = perfFileSize;
/* 431 */     if (perfFileSize != null)
/* 432 */       propertyStatusTable.put("perfFileSize", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static boolean isPerformanceLoggingEnabled()
/*     */   {
/* 439 */     return performanceLoggingEnabled;
/*     */   }
/*     */ 
/*     */   public static void setPerformanceLoggingEnabled(boolean performanceLoggingEnabled)
/*     */   {
/* 447 */     performanceLoggingEnabled = performanceLoggingEnabled;
/*     */   }
/*     */ 
/*     */   public static void setTraceLoggerLevel(String traceLoggerLevel)
/*     */   {
/* 454 */     String log4jLevel = null;
/*     */ 
/* 456 */     if (traceLoggerLevel == null) {
/* 457 */       setTraceLoggingEnabled(false);
/* 458 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 462 */       int debugLevel = Integer.parseInt(traceLoggerLevel);
/* 463 */       if ((debugLevel >= 0) && (debugLevel <= 7)) {
/* 464 */         setTraceLoggingEnabled(true);
/* 465 */         if (debugLevel == 0) {
/* 466 */           log4jLevel = Level.OFF.toString();
/* 467 */           setTraceLoggingEnabled(false);
/* 468 */         } else if ((debugLevel > 0) && (debugLevel < 6)) {
/* 469 */           log4jLevel = Level.INFO.toString();
/* 470 */         } else if (debugLevel == 6) {
/* 471 */           log4jLevel = Level.DEBUG.toString();
/* 472 */         } else if (debugLevel == 7) {
/* 473 */           log4jLevel = Level.TRACE.toString();
/* 474 */         }hasChanged(traceLoggerLevel, log4jLevel);
/* 475 */         traceLoggerLevel = log4jLevel;
/*     */       }
/* 477 */       else if (JtapiUtils.isLog4jConfigured()) {
/* 478 */         Logger theLogger = Logger.getLogger(JTAPILoggingAdapter.class);
/* 479 */         theLogger.error("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
/* 480 */         theLogger.error("Error occured while reading logger level property. You entered : " + traceLoggerLevel);
/*     */       } else {
/* 482 */         System.out.println("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
/* 483 */         System.out.println("Error occured while reading logger level property. You entered : " + traceLoggerLevel);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 487 */       if (JtapiUtils.isLog4jConfigured()) {
/* 488 */         Logger theLogger = Logger.getLogger(JTAPILoggingAdapter.class);
/* 489 */         theLogger.error("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
/* 490 */         theLogger.error("Error occured while reading logger level property. You entered : " + traceLoggerLevel, e);
/*     */       } else {
/* 492 */         System.out.println("JTAPILoggingAdapter - Invalid value for debugLevel. Enter a value between 1 and 7");
/* 493 */         System.out.println("Error occured while reading logger level property. You entered : " + traceLoggerLevel);
/* 494 */         e.printStackTrace();
/*     */       }
/* 496 */       return;
/*     */     }
/* 498 */     propertyStatusTable.put("traceLoggerLevel", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  86 */     resetPropertyStatusTable();
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter
 * JD-Core Version:    0.5.4
 */