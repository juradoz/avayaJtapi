/*    */ package com.avaya.jtapi.tsapi.impl.core;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiMonitor;
/*    */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*    */ import java.util.concurrent.BlockingQueue;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class JtapiEventThreadManager
/*    */ {
/* 26 */   private static Logger logger = Logger.getLogger(JtapiEventThreadManager.class);
/*    */   private static ExecutorService threadPoolExecutor;
/*    */   private static BlockingQueue<Runnable> fifoBuffer;
/*    */ 
/*    */   public static synchronized void initialize()
/*    */   {
/* 35 */     if (threadPoolExecutor == null) {
/* 36 */       int defaultValue = Integer.parseInt("20");
/* 37 */       fifoBuffer = new LinkedBlockingQueue();
/* 38 */       threadPoolExecutor = new ThreadPoolExecutor(defaultValue, defaultValue, 200L, TimeUnit.MILLISECONDS, fifoBuffer, new JtapiEventThreadRejectionHandler());
/*    */     }
/*    */   }
/*    */ 
/*    */   public static int getQueueSize()
/*    */   {
/* 46 */     if (fifoBuffer == null)
/* 47 */       fifoBuffer = new LinkedBlockingQueue();
/*    */     int toReturn;
/* 48 */     synchronized (fifoBuffer) {
/* 49 */       toReturn = fifoBuffer.size();
/*    */     }
/*    */ 
/* 52 */     return toReturn;
/*    */   }
/*    */ 
/*    */   public static void execute(TsapiMonitor _obs) {
/* 56 */     if (threadPoolExecutor == null) {
/* 57 */       logger.error("ThreadPoolExecutor is not initialized. This can happen when the only provider is SHUTDOWN.");
/*    */     }
/*    */     else {
/* 60 */       if (((ThreadPoolExecutor)threadPoolExecutor).getCorePoolSize() != Tsapi.getMaxThreadPoolSize()) {
/* 61 */         ((ThreadPoolExecutor)threadPoolExecutor).setCorePoolSize(Tsapi.getMaxThreadPoolSize());
/* 62 */         ((ThreadPoolExecutor)threadPoolExecutor).setMaximumPoolSize(Tsapi.getMaxThreadPoolSize());
/*    */       }
/* 64 */       threadPoolExecutor.execute(new JtapiEventDeliveryThread(_obs, System.currentTimeMillis()));
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void drainThreads() {
/* 69 */     if (threadPoolExecutor != null)
/* 70 */       threadPoolExecutor.shutdown();
/* 71 */     threadPoolExecutor = null;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager
 * JD-Core Version:    0.5.4
 */