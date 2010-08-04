/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ class TsapiHeartbeatStatus
/*     */ {
/*  30 */   private static Logger log = Logger.getLogger(TsapiHeartbeatStatus.class);
/*     */   private static final short HEARTBEAT_INTERVAL_DEFAULT = 20;
/*     */   private boolean enabled;
/*     */   private short interval;
/*     */   private TsapiHeartbeatTimer timer;
/*     */ 
/*     */   TsapiHeartbeatStatus()
/*     */   {
/*  43 */     this.enabled = false;
/*  44 */     this.interval = 20;
/*  45 */     this.timer = new TsapiHeartbeatTimer(getTimeout());
/*     */   }
/*     */ 
/*     */   private int getTimeout()
/*     */   {
/*  54 */     return 2 * this.interval;
/*     */   }
/*     */ 
/*     */   synchronized void enableHeartbeat()
/*     */   {
/*  63 */     if (this.enabled)
/*     */       return;
/*  65 */     log.info("Enabling the TSAPI heartbeat with a heartbeat interval of " + this.interval + " seconds.");
/*     */ 
/*  68 */     this.timer.reset(getTimeout());
/*  69 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */   synchronized void disableHeartbeat()
/*     */   {
/*  79 */     this.enabled = false;
/*  80 */     this.timer.cancel();
/*     */   }
/*     */ 
/*     */   synchronized boolean heartbeatIsEnabled()
/*     */   {
/*  88 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   synchronized void setHeartbeatInterval(short heartbeatInterval)
/*     */     throws IllegalArgumentException
/*     */   {
/* 101 */     if (heartbeatInterval < 0)
/*     */     {
/* 103 */       throw new IllegalArgumentException("Heartbeat interval must be non-negative.");
/*     */     }
/*     */ 
/* 107 */     this.interval = heartbeatInterval;
/* 108 */     if (!this.enabled)
/*     */       return;
/* 110 */     this.timer.reset(getTimeout());
/*     */   }
/*     */ 
/*     */   short getHeartbeatInterval()
/*     */   {
/* 120 */     return this.interval;
/*     */   }
/*     */ 
/*     */   synchronized void receivedEvent()
/*     */   {
/* 130 */     if (!this.enabled)
/*     */       return;
/* 132 */     this.timer.reset();
/*     */   }
/*     */ 
/*     */   synchronized void setHeartbeatTimeoutListener(ITsapiHeartbeatTimeoutListener listener)
/*     */   {
/* 148 */     this.timer.setHeartbeatTimeoutListener(listener);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiHeartbeatStatus
 * JD-Core Version:    0.5.4
 */