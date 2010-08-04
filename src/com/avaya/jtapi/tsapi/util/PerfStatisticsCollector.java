/*     */ package com.avaya.jtapi.tsapi.util;
/*     */ 
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PerfStatisticsCollector
/*     */ {
/*     */   private static final String PROP_PERF_LOGGER_NAME = "jtapi.performanceLogger";
/*     */   private static Timer perfTimer;
/*  22 */   private static boolean perfWindowChanged = true;
/*     */ 
/*  24 */   private static Logger logger = Logger.getLogger("jtapi.performanceLogger");
/*     */ 
/*  26 */   private static int performanceWindow = 60;
/*     */ 
/*  28 */   private static long unsolicitedHandlingTimeThreshold = 100L;
/*  29 */   private static long serviceRequestTurnaroundTimeThreshold = 100L;
/*  30 */   private static long queueLengthThreshold = 10L;
/*  31 */   private static long messageLatencyThreshold = 20L;
/*  32 */   private static long numWindowsRun = 0L;
/*     */   private static AtomicInteger callCount;
/*  35 */   private static float callCountHistorySum = 0.0F;
/*     */   private static float minCallHistory;
/*     */   private static float maxCallHistory;
/*  38 */   private static boolean callHistoryInitialized = false;
/*     */   private static AtomicInteger eventCount;
/*  41 */   private static float eventCountHistorySum = 0.0F;
/*     */   private static float minEventHistory;
/*     */   private static float maxEventHistory;
/*  44 */   private static boolean eventHistoryInitialized = false;
/*     */   private static Vector<Long> unsolicitedHandlingTime;
/*     */   private static PerfHistoryBean unsolHandlingTimeHistory;
/*     */   private static AtomicInteger unsolicitedHandlingTimeCounter;
/*     */   private static Vector<Long> serviceRequestTurnaroundTime;
/*     */   private static PerfHistoryBean serviceRequestTurnaroundTimeHistory;
/*     */   private static AtomicInteger serviceRequestTurnaroundTimeCounter;
/*     */   private static Vector<Long> queueLength;
/*     */   private static long queueOverallMax;
/*     */   private static long queueMaxWindowSum;
/*     */   private static AtomicInteger queueLengthCounter;
/*     */   private static Vector<Long> messageLatency;
/*     */   private static PerfHistoryBean messageLatencyHistory;
/*     */   private static AtomicInteger messageLatencyCounter;
/*  63 */   private static boolean isInitialized = false;
/*     */ 
/*     */   public static void initPerfStatisticsCollector()
/*     */   {
/*  70 */     if (!isInitialized) {
/*  71 */       initializeDataStructures();
/*  72 */       if (perfTimer != null) {
/*  73 */         perfTimer.cancel();
/*  74 */         perfTimer = null;
/*     */       }
/*  76 */       perfTimer = new Timer(true);
/*  77 */       perfTimer.schedule(new PerfTimerTask(), performanceWindow * 1000, performanceWindow * 1000);
/*  78 */       logger.info("##########Performance statistics monitoring start##########");
/*  79 */       isInitialized = true;
/*  80 */       perfWindowChanged = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void shutdown() {
/*  85 */     if (isInitialized) {
/*  86 */       resetDataStructures();
/*  87 */       logger.info("##########Performance statistics monitoring stop##########");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updatePerfStatisticsCollectorConfig() {
/*  92 */     if (perfWindowChanged) {
/*  93 */       resetDataStructures();
/*  94 */       initializeDataStructures();
/*  95 */       perfTimer = new Timer(true);
/*  96 */       perfTimer.schedule(new PerfTimerTask(), performanceWindow * 1000, performanceWindow * 1000);
/*  97 */       perfWindowChanged = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateMessageLatency(long newValue) {
/* 102 */     if (isInitialized) {
/* 103 */       messageLatency.add(Long.valueOf(newValue));
/* 104 */       messageLatencyCounter.incrementAndGet();
/* 105 */       if (newValue > messageLatencyThreshold)
/* 106 */         logger.info("Message latency threshold value crossed. New value is : " + newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateCallCount()
/*     */   {
/* 112 */     if (isInitialized)
/* 113 */       callCount.incrementAndGet();
/*     */   }
/*     */ 
/*     */   public static void updateEventCount()
/*     */   {
/* 118 */     if (isInitialized)
/* 119 */       eventCount.incrementAndGet();
/*     */   }
/*     */ 
/*     */   public static void updateQueueLength(long newValue) {
/* 123 */     if (isInitialized) {
/* 124 */       queueLength.add(new Long(newValue));
/* 125 */       queueLengthCounter.incrementAndGet();
/* 126 */       if (newValue > queueLengthThreshold)
/* 127 */         logger.info("Queue length threshold value crossed. New value is : " + newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateServiceRequestTurnaroundTime(long newValue)
/*     */   {
/* 133 */     if (isInitialized) {
/* 134 */       serviceRequestTurnaroundTime.add(new Long(newValue));
/* 135 */       serviceRequestTurnaroundTimeCounter.incrementAndGet();
/* 136 */       if (newValue > serviceRequestTurnaroundTimeThreshold)
/* 137 */         logger.info("Service request turnaround time threshold value crossed. New value is : " + newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateUnsolicitedHandlingTime(long newValue)
/*     */   {
/* 143 */     if (isInitialized) {
/* 144 */       unsolicitedHandlingTime.add(new Long(newValue));
/* 145 */       unsolicitedHandlingTimeCounter.incrementAndGet();
/* 146 */       if (newValue > unsolicitedHandlingTimeThreshold)
/* 147 */         logger.info("Unsolicited handling time threshold value crossed. New value is : " + newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void initializeDataStructures()
/*     */   {
/* 153 */     callCount = new AtomicInteger();
/* 154 */     callCountHistorySum = 0.0F;
/*     */ 
/* 156 */     eventCount = new AtomicInteger();
/* 157 */     eventCountHistorySum = 0.0F;
/*     */ 
/* 159 */     unsolHandlingTimeHistory = new PerfHistoryBean("UNSOLICITED HANDLING TIME");
/* 160 */     unsolicitedHandlingTime = new Vector();
/* 161 */     unsolicitedHandlingTimeCounter = new AtomicInteger();
/*     */ 
/* 163 */     serviceRequestTurnaroundTimeHistory = new PerfHistoryBean("SERVICE REQUEST TURNAROUND TIME");
/* 164 */     serviceRequestTurnaroundTime = new Vector();
/* 165 */     serviceRequestTurnaroundTimeCounter = new AtomicInteger();
/*     */ 
/* 167 */     queueLength = new Vector();
/* 168 */     queueMaxWindowSum = 0L;
/* 169 */     queueOverallMax = 0L;
/* 170 */     queueLengthCounter = new AtomicInteger();
/*     */ 
/* 172 */     messageLatencyHistory = new PerfHistoryBean("MESSAGE LATENCY");
/* 173 */     messageLatency = new Vector();
/* 174 */     messageLatencyCounter = new AtomicInteger();
/*     */   }
/*     */ 
/*     */   private static void resetDataStructures() {
/* 178 */     unsolicitedHandlingTime.clear();
/* 179 */     unsolicitedHandlingTime = null;
/* 180 */     unsolicitedHandlingTimeCounter = null;
/* 181 */     unsolHandlingTimeHistory = null;
/* 182 */     numWindowsRun = 0L;
/*     */ 
/* 184 */     serviceRequestTurnaroundTime.clear();
/* 185 */     serviceRequestTurnaroundTime = null;
/* 186 */     serviceRequestTurnaroundTimeCounter = null;
/* 187 */     serviceRequestTurnaroundTimeHistory = null;
/*     */ 
/* 189 */     queueLength.clear();
/* 190 */     queueOverallMax = 0L;
/* 191 */     queueLength = null;
/* 192 */     queueLengthCounter = null;
/*     */ 
/* 194 */     messageLatency.clear();
/* 195 */     messageLatency = null;
/* 196 */     messageLatencyCounter = null;
/* 197 */     messageLatencyHistory = null;
/*     */ 
/* 199 */     callCount = null;
/* 200 */     minCallHistory = PerfStatisticsCollector.maxCallHistory = PerfStatisticsCollector.callCountHistorySum = 0.0F;
/* 201 */     callHistoryInitialized = false;
/*     */ 
/* 203 */     eventCount = null;
/* 204 */     eventCountHistorySum = PerfStatisticsCollector.minEventHistory = PerfStatisticsCollector.maxEventHistory = 0.0F;
/* 205 */     eventHistoryInitialized = false;
/*     */ 
/* 207 */     perfTimer.cancel();
/* 208 */     perfTimer = null;
/*     */ 
/* 210 */     isInitialized = false;
/*     */   }
/*     */ 
/*     */   public static void setUnsolicitedHandlingTimeThreshold(long unsolicitedHandlingTimeThreshold)
/*     */   {
/* 218 */     unsolicitedHandlingTimeThreshold = unsolicitedHandlingTimeThreshold;
/*     */   }
/*     */ 
/*     */   public static void setServiceRequestTurnaroundTimeThreshold(long serviceRequestTurnaroundTimeThreshold)
/*     */   {
/* 226 */     serviceRequestTurnaroundTimeThreshold = serviceRequestTurnaroundTimeThreshold;
/*     */   }
/*     */ 
/*     */   public static void setQueueLengthThreshold(long queueLengthThreshold)
/*     */   {
/* 233 */     queueLengthThreshold = queueLengthThreshold;
/*     */   }
/*     */ 
/*     */   public static void setMessageLatencyThreshold(long messageLatencyThreshold)
/*     */   {
/* 240 */     messageLatencyThreshold = messageLatencyThreshold;
/*     */   }
/*     */ 
/*     */   public static int getPerformanceWindow()
/*     */   {
/* 248 */     return performanceWindow;
/*     */   }
/*     */ 
/*     */   public static void setPerformanceWindow(int performanceWindow)
/*     */   {
/* 256 */     if (performanceWindow != performanceWindow)
/* 257 */       perfWindowChanged = true;
/* 258 */     performanceWindow = performanceWindow;
/*     */   }
/*     */ 
/*     */   static class PerfHistoryBean
/*     */   {
/*     */     private long historyMin;
/*     */     private long historyMax;
/*     */     private long windowMin;
/*     */     private long windowMax;
/*     */     private long windowSum;
/*     */     private long windowCount;
/*     */     private long historySum;
/*     */     private int historyCount;
/*     */     private String name;
/* 552 */     private boolean historyInitialized = false;
/*     */ 
/*     */     public PerfHistoryBean(String name) {
/* 555 */       this.historySum = 0L;
/* 556 */       this.historyCount = 0;
/* 557 */       this.name = name;
/*     */     }
/*     */ 
/*     */     public void printWindowStats(Logger logger) {
/* 561 */       if (this.windowSum != 0L)
/* 562 */         logger.info(this.name + ": Min = " + this.windowMin + "\tMax = " + this.windowMax + "\tAverage = " + (float)this.windowSum / (float)this.windowCount);
/*     */       else
/* 564 */         logger.info(this.name + ": Min = " + this.windowMin + "\tMax = " + this.windowMax + "\tAverage = " + 0);
/*     */     }
/*     */ 
/*     */     public void printHistoryStats(Logger logger) {
/* 568 */       if (this.historyCount != 0)
/* 569 */         logger.info(this.name + ": Min = " + this.historyMin + "\tMax = " + this.historyMax + "\tAverage = " + (float)this.historySum / this.historyCount);
/*     */       else
/* 571 */         logger.info(this.name + ": Min = " + this.historyMin + "\tMax = " + this.historyMax + "\tAverage = " + 0);
/*     */     }
/*     */ 
/*     */     public void copyToHistory(Vector<Long> windowDetails, int count) {
/* 575 */       if (windowDetails.size() == 0)
/* 576 */         return;
/* 577 */       long sum = 0L;
/* 578 */       this.windowMax = (this.windowMin = ((Long)windowDetails.firstElement()).longValue());
/*     */ 
/* 580 */       for (Long value : windowDetails) {
/* 581 */         sum += value.longValue();
/* 582 */         if (value.longValue() < this.windowMin)
/* 583 */           this.windowMin = value.longValue();
/* 584 */         if (value.longValue() > this.windowMax)
/* 585 */           this.windowMax = value.longValue();
/*     */       }
/* 587 */       this.windowSum = sum;
/* 588 */       this.windowCount = count;
/* 589 */       if (sum != 0L) {
/* 590 */         this.historySum += sum;
/* 591 */         this.historyCount += count;
/* 592 */         if (!this.historyInitialized) {
/* 593 */           this.historyMin = this.windowMin;
/* 594 */           this.historyInitialized = true;
/* 595 */         } else if (this.windowMin < this.historyMin) {
/* 596 */           this.historyMin = this.windowMin;
/* 597 */         }if (this.windowMax > this.historyMax)
/* 598 */           this.historyMax = this.windowMax;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class PerfStatisticsCalculator
/*     */   {
/* 370 */     private static Logger logger = Logger.getLogger("jtapi.performanceLogger");
/*     */     private Vector<Long> data;
/*     */     private long min;
/*     */     private long max;
/*     */     private float average;
/*     */     private long historyMin;
/*     */     private long historMax;
/*     */     private float historyAverage;
/*     */     private long window;
/*     */ 
/*     */     public PerfStatisticsCalculator()
/*     */     {
/*     */     }
/*     */ 
/*     */     public PerfStatisticsCalculator(Vector<Long> theData, long window)
/*     */     {
/* 389 */       this.data = theData;
/* 390 */       this.window = window;
/*     */     }
/*     */ 
/*     */     public void reset() {
/* 394 */       this.min = (this.max = this.historMax = this.historyMin = this.window = 0L);
/* 395 */       this.average = (this.historyAverage = 0.0F);
/* 396 */       this.data.clear();
/* 397 */       this.data = null;
/*     */     }
/*     */ 
/*     */     public void computeMinMaxAverage() {
/* 401 */       computeHistoryMinMaxAvg();
/* 402 */       computeMinMaxAvgWindow();
/*     */     }
/*     */ 
/*     */     private void computeMinMaxAvgWindow() {
/* 406 */       if (this.data.size() == 0)
/* 407 */         return;
/* 408 */       this.min = ((Long)this.data.get(0)).longValue();
/* 409 */       this.max = 0L;
/* 410 */       this.average = 0.0F;
/* 411 */       if (this.window > 0L) {
/* 412 */         for (int i = (int)(this.data.size() - this.window); i < this.data.size(); ++i) {
/* 413 */           long dataValue = ((Long)this.data.get(i)).longValue();
/* 414 */           if (dataValue < this.min)
/* 415 */             this.min = dataValue;
/* 416 */           if (dataValue > this.max)
/* 417 */             this.max = dataValue;
/* 418 */           this.average += (float)dataValue;
/*     */         }
/* 420 */         this.average /= (float)this.window;
/*     */       }
/*     */     }
/*     */ 
/*     */     private void computeHistoryMinMaxAvg() {
/* 425 */       if (this.data.size() == 0)
/* 426 */         return;
/* 427 */       this.historyMin = ((Long)this.data.get(0)).longValue();
/* 428 */       this.historMax = 0L;
/* 429 */       this.historyAverage = 0.0F;
/* 430 */       if (this.data.size() > 0) {
/* 431 */         for (Long tempData : this.data) {
/* 432 */           long dataValue = tempData.longValue();
/* 433 */           if (dataValue < this.historyMin)
/* 434 */             this.historyMin = dataValue;
/* 435 */           if (dataValue > this.historMax)
/* 436 */             this.historMax = dataValue;
/* 437 */           this.historyAverage += (float)dataValue;
/*     */         }
/* 439 */         this.historyAverage /= this.data.size();
/*     */       }
/*     */     }
/*     */ 
/*     */     public static void printMaxAverage(String name, Vector<Long> data) {
/* 444 */       long max = 0L;
/* 445 */       float average = 0.0F;
/* 446 */       if (data.size() > 0) {
/* 447 */         for (Long dataValue : data) {
/* 448 */           if (dataValue.longValue() > max)
/* 449 */             max = dataValue.longValue();
/* 450 */           average += (float)dataValue.longValue();
/*     */         }
/* 452 */         average /= data.size();
/*     */       }
/* 454 */       logger.info(name + ": Max = " + max + "\tAverage = " + average);
/*     */     }
/*     */ 
/*     */     public static void printMinMaxAverageData(Vector<Float> data, String name) {
/* 458 */       if (data.size() == 0)
/* 459 */         return;
/* 460 */       float min = ((Float)data.get(0)).floatValue();
/* 461 */       float max = 0.0F; float average = 0.0F;
/* 462 */       if (data.size() > 0) {
/* 463 */         for (Float dataValue : data) {
/* 464 */           if (dataValue.floatValue() < min)
/* 465 */             min = dataValue.floatValue();
/* 466 */           else if (dataValue.floatValue() > max)
/* 467 */             max = dataValue.floatValue();
/* 468 */           average += dataValue.floatValue();
/*     */         }
/* 470 */         average /= data.size();
/*     */       }
/* 472 */       logger.info(name + " Min = " + min + "\tMax = " + max + "\tAverage = " + average);
/*     */     }
/*     */ 
/*     */     public Vector<Long> getData()
/*     */     {
/* 479 */       return this.data;
/*     */     }
/*     */ 
/*     */     public long getMin()
/*     */     {
/* 485 */       return this.min;
/*     */     }
/*     */ 
/*     */     public long getMax()
/*     */     {
/* 491 */       return this.max;
/*     */     }
/*     */ 
/*     */     public float getAverage()
/*     */     {
/* 497 */       return this.average;
/*     */     }
/*     */ 
/*     */     public long getHistoryMin()
/*     */     {
/* 503 */       return this.historyMin;
/*     */     }
/*     */ 
/*     */     public long getHistorMax()
/*     */     {
/* 509 */       return this.historMax;
/*     */     }
/*     */ 
/*     */     public float getHistoryAverage()
/*     */     {
/* 515 */       return this.historyAverage;
/*     */     }
/*     */ 
/*     */     public long getWindow()
/*     */     {
/* 522 */       return this.window;
/*     */     }
/*     */ 
/*     */     public void setWindow(long window)
/*     */     {
/* 529 */       this.window = window;
/*     */     }
/*     */ 
/*     */     public void setData(Vector<Long> data)
/*     */     {
/* 536 */       this.data = data;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class PerfTimerTask extends TimerTask
/*     */   {
/*     */     public void run()
/*     */     {
///* 266 */       PerfStatisticsCollector.access$108();
/*     */       Vector queueLengthCloneVector;
/*     */       int queueLengthCloneCounter;
/*     */       int callCountClone;
/*     */       int eventCountClone;
/* 277 */       synchronized (PerfStatisticsCollector.class) {
/* 278 */         Vector unsolCloneVector = (Vector)PerfStatisticsCollector.unsolicitedHandlingTime.clone();
/* 279 */         int unsolCloneCounter = PerfStatisticsCollector.unsolicitedHandlingTimeCounter.getAndSet(0);
/* 280 */         PerfStatisticsCollector.unsolicitedHandlingTime.clear();
/* 281 */         PerfStatisticsCollector.unsolHandlingTimeHistory.copyToHistory(unsolCloneVector, unsolCloneCounter);
/*     */ 
/* 283 */         Vector serviceTurnaroundCloneVector = (Vector)PerfStatisticsCollector.serviceRequestTurnaroundTime.clone();
/* 284 */         int serviceTurnaroundCloneCounter = PerfStatisticsCollector.serviceRequestTurnaroundTimeCounter.getAndSet(0);
/* 285 */         PerfStatisticsCollector.serviceRequestTurnaroundTime.clear();
/* 286 */         PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory.copyToHistory(serviceTurnaroundCloneVector, serviceTurnaroundCloneCounter);
/*     */ 
/* 288 */         queueLengthCloneVector = (Vector)PerfStatisticsCollector.queueLength.clone();
/* 289 */         PerfStatisticsCollector.queueLength.clear();
/* 290 */         queueLengthCloneCounter = PerfStatisticsCollector.queueLengthCounter.getAndSet(0);
/*     */ 
/* 292 */         Vector messageLatencyCloneVector = (Vector)PerfStatisticsCollector.messageLatency.clone();
/* 293 */         int messageLatencyCloneCounter = PerfStatisticsCollector.messageLatencyCounter.getAndSet(0);
/* 294 */         PerfStatisticsCollector.messageLatency.clear();
/* 295 */         PerfStatisticsCollector.messageLatencyHistory.copyToHistory(messageLatencyCloneVector, messageLatencyCloneCounter);
/*     */ 
/* 297 */         callCountClone = PerfStatisticsCollector.callCount.getAndSet(0);
/*     */ 
/* 299 */         eventCountClone = PerfStatisticsCollector.eventCount.getAndSet(0);
/*     */       }
/* 301 */       PerfStatisticsCollector.PerfStatisticsCalculator qPerfCalc = new PerfStatisticsCollector.PerfStatisticsCalculator();
/* 302 */       compute(queueLengthCloneVector, queueLengthCloneCounter, qPerfCalc);
/*     */ 
/* 305 */       PerfStatisticsCollector.logger.info("Statistics for current window : ");
/* 306 */       PerfStatisticsCollector.unsolHandlingTimeHistory.printWindowStats(PerfStatisticsCollector.logger);
/* 307 */       PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory.printWindowStats(PerfStatisticsCollector.logger);
/* 308 */       PerfStatisticsCollector.messageLatencyHistory.printWindowStats(PerfStatisticsCollector.logger);
/*     */ 
/* 310 */       if (queueLengthCloneVector.size() > 0) {
/* 311 */         PerfStatisticsCollector.logger.info("QUEUE LENGTH: Current = " + queueLengthCloneVector.lastElement() + "\tMax = " + qPerfCalc.getMax());
/*     */       }
/* 313 */       if (callCountClone != 0) {
/* 314 */         float currentCallsPerSecond = callCountClone / PerfStatisticsCollector.performanceWindow;
/* 315 */         PerfStatisticsCollector.logger.info("CALLS PER SECOND: " + currentCallsPerSecond);
///* 316 */         PerfStatisticsCollector.access$1716(currentCallsPerSecond);
/* 317 */         if (!PerfStatisticsCollector.callHistoryInitialized) {
///* 318 */           PerfStatisticsCollector.access$1802(true);
///* 319 */           PerfStatisticsCollector.access$1902(PerfStatisticsCollector.access$2002(currentCallsPerSecond));
/*     */         }
/* 321 */         if (currentCallsPerSecond < PerfStatisticsCollector.minCallHistory)
///* 322 */           PerfStatisticsCollector.access$1902(currentCallsPerSecond);
/* 323 */         if (currentCallsPerSecond > PerfStatisticsCollector.maxCallHistory) {
///* 324 */           PerfStatisticsCollector.access$2002(currentCallsPerSecond);
/*     */         }
/*     */       }
/* 327 */       if (eventCountClone != 0) {
/* 328 */         float currentEventsPerSecond = eventCountClone / PerfStatisticsCollector.performanceWindow;
/* 329 */         PerfStatisticsCollector.logger.info("EVENTS PER SECOND:" + currentEventsPerSecond);
///* 330 */         PerfStatisticsCollector.access$2116(currentEventsPerSecond);
/* 331 */         if (!PerfStatisticsCollector.eventHistoryInitialized) {
///* 332 */           PerfStatisticsCollector.access$2202(true);
///* 333 */           PerfStatisticsCollector.access$2302(PerfStatisticsCollector.access$2402(currentEventsPerSecond));
/*     */         }
/* 335 */         if (currentEventsPerSecond < PerfStatisticsCollector.minEventHistory)
///* 336 */           PerfStatisticsCollector.access$2302(currentEventsPerSecond);
/* 337 */         if (currentEventsPerSecond > PerfStatisticsCollector.maxEventHistory) {
///* 338 */           PerfStatisticsCollector.access$2402(currentEventsPerSecond);
/*     */         }
/*     */       }
/*     */ 
/* 342 */       PerfStatisticsCollector.logger.info("Cumulative Statistics Since Performance Monitoring Started :");
/* 343 */       PerfStatisticsCollector.unsolHandlingTimeHistory.printHistoryStats(PerfStatisticsCollector.logger);
/* 344 */       PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory.printHistoryStats(PerfStatisticsCollector.logger);
/* 345 */       PerfStatisticsCollector.messageLatencyHistory.printHistoryStats(PerfStatisticsCollector.logger);
/* 346 */       long currentQMax = qPerfCalc.getMax();
///* 347 */       PerfStatisticsCollector.access$2514(qPerfCalc.getMax());
/* 348 */       if (currentQMax > PerfStatisticsCollector.queueOverallMax) {
///* 349 */         PerfStatisticsCollector.access$2602(currentQMax);
/*     */       }
/* 351 */       PerfStatisticsCollector.logger.info("QUEUE LENGTH: Max = " + PerfStatisticsCollector.queueOverallMax + "\tAverage = " + (float)PerfStatisticsCollector.queueMaxWindowSum / (float)PerfStatisticsCollector.numWindowsRun);
/* 352 */       if (callCountClone != 0) {
/* 353 */         PerfStatisticsCollector.logger.info("CALLS PER SECOND: Min = " + PerfStatisticsCollector.minCallHistory + "\tMax = " + PerfStatisticsCollector.maxCallHistory + "\tAverage = " + PerfStatisticsCollector.callCountHistorySum / (float)PerfStatisticsCollector.numWindowsRun);
/*     */       }
/* 355 */       if (eventCountClone != 0)
/* 356 */         PerfStatisticsCollector.logger.info("EVENTS PER SECOND: Min = " + PerfStatisticsCollector.minEventHistory + "\tMax = " + PerfStatisticsCollector.maxEventHistory + "\tAverage = " + PerfStatisticsCollector.eventCountHistorySum / (float)PerfStatisticsCollector.numWindowsRun);
/*     */     }
/*     */ 
/*     */     private void compute(Vector<Long> statData, int counter, PerfStatisticsCollector.PerfStatisticsCalculator perfBean)
/*     */     {
/* 362 */       perfBean.setData(statData);
/* 363 */       perfBean.setWindow(counter);
/* 364 */       perfBean.computeMinMaxAverage();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.util.PerfStatisticsCollector
 * JD-Core Version:    0.5.4
 */