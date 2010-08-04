/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ 
/*     */ class TsapiHeartbeatTimer
/*     */ {
/*     */   private int delay;
/*     */   private Timer timer;
/*     */   private ITsapiHeartbeatTimeoutListener listener;
/*     */ 
/*     */   TsapiHeartbeatTimer(int delay)
/*     */   {
/*  44 */     this.delay = delay;
/*  45 */     this.timer = null;
/*     */   }
/*     */ 
/*     */   int getDelay()
/*     */   {
/*  54 */     return this.delay;
/*     */   }
/*     */ 
/*     */   void reset()
/*     */     throws IllegalArgumentException, IllegalStateException
/*     */   {
/*  67 */     cancelCurrentTimer();
/*  68 */     scheduleNewTimer();
/*     */   }
/*     */ 
/*     */   void reset(int delay)
/*     */     throws IllegalArgumentException, IllegalStateException
/*     */   {
/*  82 */     this.delay = delay;
/*  83 */     cancelCurrentTimer();
/*  84 */     scheduleNewTimer();
/*     */   }
/*     */ 
/*     */   void cancel()
/*     */   {
/*  92 */     cancelCurrentTimer();
/*     */   }
/*     */ 
/*     */   void setHeartbeatTimeoutListener(ITsapiHeartbeatTimeoutListener listener)
/*     */   {
/* 102 */     this.listener = listener;
/*     */   }
/*     */ 
/*     */   private void cancelCurrentTimer()
/*     */   {
/* 110 */     if (this.timer == null)
/*     */       return;
/* 112 */     this.timer.cancel();
/* 113 */     this.timer = null;
/*     */   }
/*     */ 
/*     */   private void scheduleNewTimer()
/*     */     throws IllegalArgumentException, IllegalStateException
/*     */   {
/* 128 */     this.timer = new Timer();
/* 129 */     this.timer.schedule(new TsapiHeartbeatTimerTask(), this.delay * 1000);
/*     */   }
/*     */ 
/*     */   class TsapiHeartbeatTimerTask extends TimerTask
/*     */   {
/*     */     TsapiHeartbeatTimerTask()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 149 */       if (TsapiHeartbeatTimer.this.listener == null)
/*     */         return;
/* 151 */       TsapiHeartbeatTimer.this.listener.heartbeatTimeout();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiHeartbeatTimer
 * JD-Core Version:    0.5.4
 */