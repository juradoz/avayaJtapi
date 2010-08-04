/*    */ package com.avaya.jtapi.tsapi.impl.core;
/*    */ 
/*    */ import java.util.concurrent.BlockingQueue;
/*    */ import java.util.concurrent.RejectedExecutionHandler;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class JtapiEventThreadRejectionHandler
/*    */   implements RejectedExecutionHandler
/*    */ {
/* 18 */   private static Logger log = Logger.getLogger(JtapiEventThreadRejectionHandler.class);
/*    */ 
/*    */   public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
/*    */   {
/* 25 */     log.info("There are already " + executor.getActiveCount() + " active threads delivering events and . " + executor.getQueue().size() + " requests in queue." + "No free threads were available to assign. Kindly consider setting the maxThreadPoolSize to a higher value");
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadRejectionHandler
 * JD-Core Version:    0.5.4
 */