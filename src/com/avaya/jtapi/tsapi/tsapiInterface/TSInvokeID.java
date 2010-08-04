/*    */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*    */ import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
/*    */ import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ final class TSInvokeID
/*    */ {
/* 14 */   private static Logger log = Logger.getLogger(TSInvokeID.class);
/*    */   int value;
/*    */   CSTAEvent conf;
/*    */   ConfHandler handler;
/*    */   String debugID;
/*    */   long serviceRequestTurnaroundTime;
/*    */ 
/*    */   TSInvokeID(int _value, ConfHandler _handler, String _debugID)
/*    */   {
/* 23 */     this.value = _value;
/* 24 */     this.handler = _handler;
/* 25 */     this.debugID = _debugID;
/* 26 */     this.conf = null;
/*    */   }
/*    */ 
/*    */   int getValue()
/*    */   {
/* 31 */     return this.value;
/*    */   }
/*    */ 
/*    */   ConfHandler getConfHandler()
/*    */   {
/* 36 */     return this.handler;
/*    */   }
/*    */ 
/*    */   public long getServiceRequestTurnaroundTime() {
/* 40 */     return this.serviceRequestTurnaroundTime;
/*    */   }
/*    */ 
/*    */   public void setServiceRequestTurnaroundTime(long serviceRequestTurnaroundTime) {
/* 44 */     this.serviceRequestTurnaroundTime = serviceRequestTurnaroundTime;
/*    */   }
/*    */ 
/*    */   synchronized void setConf(CSTAEvent _conf)
/*    */   {
/*    */     try
/*    */     {
/* 51 */       log.info("Handling INVOKE ID " + this.value + " for " + this.debugID);
/* 52 */       this.conf = _conf;
/* 53 */       if (this.handler != null)
/* 54 */         this.handler.handleConf(this.conf);
/* 55 */       log.info("DONE handling INVOKE ID " + this.value + " for " + this.debugID);
/*    */     }
/*    */     finally
/*    */     {
/* 62 */       setServiceRequestTurnaroundTime(System.currentTimeMillis() - getServiceRequestTurnaroundTime());
/* 63 */       if (JTAPILoggingAdapter.isPerformanceLoggingEnabled())
/*    */       {
/* 65 */         PerfStatisticsCollector.updateServiceRequestTurnaroundTime(getServiceRequestTurnaroundTime());
/*    */       }
/* 67 */       super.notify();
/*    */     }
/*    */   }
/*    */ 
/*    */   synchronized CSTAEvent waitForConf(int timeout)
/*    */   {
/* 73 */     if (this.conf == null)
/*    */     {
/*    */       try
/*    */       {
/* 77 */         super.wait(timeout);
/*    */       } catch (InterruptedException e) {
/*    */       }
/*    */     }
/* 81 */     return this.conf;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TSInvokeID
 * JD-Core Version:    0.5.4
 */