/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*     */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*     */ import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
/*     */ import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TsapiEventQueue extends Thread
/*     */   implements TsapiEventHandler
/*     */ {
/*  46 */   private static Logger log = Logger.getLogger(TsapiEventQueue.class);
/*     */ 
/*  48 */   private static AtomicInteger queueLength = new AtomicInteger();
/*     */   private String debugID;
/*     */   private Vector<CSTAEvent> fifo;
/*  51 */   private int maxsize = 0;
/*     */   private TsapiEventHandler realHandler;
/*  53 */   private boolean keepRunning = true;
/*     */ 
/*  55 */   private static int DEFAULT_TIMEOUT = 180000;
/*     */ 
/*     */   public TsapiEventQueue(TsapiEventHandler _realHandler, String _debugID)
/*     */   {
/*  70 */     super("DistributeCSTAEvent");
/*  71 */     this.debugID = _debugID;
/*  72 */     this.fifo = new Vector();
/*  73 */     this.realHandler = _realHandler;
/*  74 */     start();
/*     */   }
/*     */ 
/*     */   public void handleEvent(CSTAEvent event)
/*     */   {
/*  87 */     put(event);
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  99 */     while (this.keepRunning) {
/* 100 */       CSTAEvent event = get();
/*     */ 
/* 102 */       if (event != null)
/* 103 */         this.realHandler.handleEvent(event);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 118 */     this.keepRunning = false;
/* 119 */     synchronized (this)
/*     */     {
/* 121 */       super.notify();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setUnsolicitedHandler(TsapiUnsolicitedHandler handler)
/*     */   {
/* 135 */     this.realHandler.setUnsolicitedHandler(handler);
/*     */   }
/*     */ 
/*     */   private void put(CSTAEvent event)
/*     */   {
/*     */     int prevsize;
/*     */     int debugMaxsize;
/* 144 */     synchronized (this)
/*     */     {
/* 146 */       prevsize = this.fifo.size();
/*     */ 
/* 148 */       this.fifo.insertElementAt(event, 0);
/*     */ 
/* 150 */       event.setQueuedTimeStamp(System.currentTimeMillis());
/*     */ 
/* 152 */       if (JTAPILoggingAdapter.isPerformanceLoggingEnabled())
/*     */       {
/* 154 */         int evType = event.getEventHeader().getEventClass();
/*     */ 
/* 156 */         if ((evType == 1) || (evType == 4)) {
/* 157 */           log.debug("Updating statistics collector with info for EVENTS/SEC.");
/* 158 */           PerfStatisticsCollector.updateEventCount();
/*     */         }
/*     */       }
/*     */ 
/* 162 */       if (prevsize == 0)
/*     */       {
/* 164 */         super.notify();
/*     */       }
/*     */ 
/* 167 */       ++prevsize;
/* 168 */       if (prevsize > this.maxsize)
/*     */       {
/* 170 */         this.maxsize = prevsize;
/*     */       }
/* 172 */       debugMaxsize = this.maxsize;
/*     */     }
/*     */ 
/* 175 */     log.info("Putting event " + event + ". EVENT Q SIZE = " + prevsize + " MAX Q SIZE = " + debugMaxsize + " for " + this.debugID);
/*     */ 
/* 178 */     if ((this.fifo.size() == 100) || ((this.fifo.size() > 100) && (this.fifo.size() % 300 == 0)))
/*     */     {
/* 180 */       log.info("Doing Thread dumps");
/* 181 */       ThreadDump threadDump = new ThreadDump();
/* 182 */       threadDump.start();
/*     */     }
/*     */ 
/* 185 */     if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
/* 186 */       log.debug("Incrementing statistics collector with queue length");
/* 187 */       PerfStatisticsCollector.updateQueueLength(queueLength.incrementAndGet());
/*     */     }
/*     */ 
/* 190 */     if (this.fifo.size() == 1000) {
/* 191 */       ThreadDump threadDump = new ThreadDump();
/* 192 */       threadDump.start();
/* 193 */       throw new TsapiPlatformException(4, 0, "Event queue size has reached the threshold of 1000. Shutting down.");
/*     */     }
/*     */   }
/*     */ 
/*     */   // ERROR //
/*     */   private CSTAEvent get()
/*     */   { return null;
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_2
/*     */     //   3: monitorenter
/*     */     //   4: iconst_0
/*     */     //   5: istore_3
/*     */     //   6: aload_0
/*     */     //   7: getfield 4	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:keepRunning	Z
/*     */     //   10: iconst_1
/*     */     //   11: if_icmpne +31 -> 42
/*     */     //   14: aload_0
/*     */     //   15: getfield 8	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:fifo	Ljava/util/Vector;
/*     */     //   18: invokevirtual 16	java/util/Vector:size	()I
/*     */     //   21: dup
/*     */     //   22: istore_3
/*     */     //   23: ifne +19 -> 42
/*     */     //   26: aload_0
/*     */     //   27: getstatic 49	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:DEFAULT_TIMEOUT	I
/*     */     //   30: i2l
/*     */     //   31: invokevirtual 50	java/lang/Object:wait	(J)V
/*     */     //   34: goto -28 -> 6
/*     */     //   37: astore 4
/*     */     //   39: goto -33 -> 6
/*     */     //   42: aload_0
/*     */     //   43: getfield 4	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:keepRunning	Z
/*     */     //   46: ifne +7 -> 53
/*     */     //   49: aconst_null
/*     */     //   50: aload_2
/*     */     //   51: monitorexit
/*     */     //   52: areturn
/*     */     //   53: aload_0
/*     */     //   54: getfield 8	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:fifo	Ljava/util/Vector;
/*     */     //   57: iload_3
/*     */     //   58: iconst_1
/*     */     //   59: isub
/*     */     //   60: invokevirtual 52	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*     */     //   63: checkcast 53	com/avaya/jtapi/tsapi/csta1/CSTAEvent
/*     */     //   66: astore_1
/*     */     //   67: aload_0
/*     */     //   68: getfield 8	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:fifo	Ljava/util/Vector;
/*     */     //   71: iload_3
/*     */     //   72: iconst_1
/*     */     //   73: isub
/*     */     //   74: invokevirtual 54	java/util/Vector:removeElementAt	(I)V
/*     */     //   77: aload_2
/*     */     //   78: monitorexit
/*     */     //   79: goto +10 -> 89
/*     */     //   82: astore 5
/*     */     //   84: aload_2
/*     */     //   85: monitorexit
/*     */     //   86: aload 5
/*     */     //   88: athrow
/*     */     //   89: getstatic 23	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:log	Lorg/apache/log4j/Logger;
/*     */     //   92: new 27	java/lang/StringBuilder
/*     */     //   95: dup
/*     */     //   96: invokespecial 28	java/lang/StringBuilder:<init>	()V
/*     */     //   99: ldc 55
/*     */     //   101: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   104: aload_1
/*     */     //   105: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   108: ldc 35
/*     */     //   110: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   113: aload_0
/*     */     //   114: getfield 5	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:debugID	Ljava/lang/String;
/*     */     //   117: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   120: invokevirtual 36	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   123: invokevirtual 37	org/apache/log4j/Logger:info	(Ljava/lang/Object;)V
/*     */     //   126: invokestatic 20	com/avaya/jtapi/tsapi/util/JTAPILoggingAdapter:isPerformanceLoggingEnabled	()Z
/*     */     //   129: ifeq +22 -> 151
/*     */     //   132: getstatic 23	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:log	Lorg/apache/log4j/Logger;
/*     */     //   135: ldc 56
/*     */     //   137: invokevirtual 25	org/apache/log4j/Logger:debug	(Ljava/lang/Object;)V
/*     */     //   140: invokestatic 18	java/lang/System:currentTimeMillis	()J
/*     */     //   143: aload_1
/*     */     //   144: invokevirtual 57	com/avaya/jtapi/tsapi/csta1/CSTAEvent:getQueuedTimeStamp	()J
/*     */     //   147: lsub
/*     */     //   148: invokestatic 58	com/avaya/jtapi/tsapi/util/PerfStatisticsCollector:updateMessageLatency	(J)V
/*     */     //   151: invokestatic 20	com/avaya/jtapi/tsapi/util/JTAPILoggingAdapter:isPerformanceLoggingEnabled	()Z
/*     */     //   154: ifeq +21 -> 175
/*     */     //   157: getstatic 23	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:log	Lorg/apache/log4j/Logger;
/*     */     //   160: ldc 59
/*     */     //   162: invokevirtual 25	org/apache/log4j/Logger:debug	(Ljava/lang/Object;)V
/*     */     //   165: getstatic 43	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:queueLength	Ljava/util/concurrent/atomic/AtomicInteger;
/*     */     //   168: invokevirtual 60	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
/*     */     //   171: i2l
/*     */     //   172: invokestatic 45	com/avaya/jtapi/tsapi/util/PerfStatisticsCollector:updateQueueLength	(J)V
/*     */     //   175: aload_1
/*     */     //   176: areturn
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   26	34	37	java/lang/InterruptedException
/*     */     //   4	52	82	finally
/*     */     //   53	79	82	finally
/*     */     //   82	86	82	finally
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiEventQueue
 * JD-Core Version:    0.5.4
 */