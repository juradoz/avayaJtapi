 package com.avaya.jtapi.tsapi.tsapiInterface;
 
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
 import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;
 import java.util.Vector;
 import java.util.concurrent.atomic.AtomicInteger;
 import org.apache.log4j.Logger;
 
 public class TsapiEventQueue extends Thread
   implements TsapiEventHandler
 {
   private static Logger log = Logger.getLogger(TsapiEventQueue.class);
 
   private static AtomicInteger queueLength = new AtomicInteger();
   private String debugID;
   private Vector<CSTAEvent> fifo;
   private int maxsize = 0;
   private TsapiEventHandler realHandler;
   private boolean keepRunning = true;
 
   private static int DEFAULT_TIMEOUT = 180000;
 
   public TsapiEventQueue(TsapiEventHandler _realHandler, String _debugID)
   {
     super("DistributeCSTAEvent");
     this.debugID = _debugID;
     this.fifo = new Vector();
     this.realHandler = _realHandler;
     start();
   }
 
   public void handleEvent(CSTAEvent event)
   {
     put(event);
   }
 
   public void run()
   {
     while (this.keepRunning) {
       CSTAEvent event = get();
 
       if (event != null)
         this.realHandler.handleEvent(event);
     }
   }
 
   public void close()
   {
     this.keepRunning = false;
     synchronized (this)
     {
       super.notify();
     }
   }
 
   public void setUnsolicitedHandler(TsapiUnsolicitedHandler handler)
   {
     this.realHandler.setUnsolicitedHandler(handler);
   }
 
   private void put(CSTAEvent event)
   {
     int prevsize;
     int debugMaxsize;
     synchronized (this)
     {
       prevsize = this.fifo.size();
 
       this.fifo.insertElementAt(event, 0);
 
       event.setQueuedTimeStamp(System.currentTimeMillis());
 
       if (JTAPILoggingAdapter.isPerformanceLoggingEnabled())
       {
         int evType = event.getEventHeader().getEventClass();
 
         if ((evType == 1) || (evType == 4)) {
           log.debug("Updating statistics collector with info for EVENTS/SEC.");
           PerfStatisticsCollector.updateEventCount();
         }
       }
 
       if (prevsize == 0)
       {
         super.notify();
       }
 
       ++prevsize;
       if (prevsize > this.maxsize)
       {
         this.maxsize = prevsize;
       }
       debugMaxsize = this.maxsize;
     }
 
     log.info("Putting event " + event + ". EVENT Q SIZE = " + prevsize + " MAX Q SIZE = " + debugMaxsize + " for " + this.debugID);
 
     if ((this.fifo.size() == 100) || ((this.fifo.size() > 100) && (this.fifo.size() % 300 == 0)))
     {
       log.info("Doing Thread dumps");
       ThreadDump threadDump = new ThreadDump();
       threadDump.start();
     }
 
     if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
       log.debug("Incrementing statistics collector with queue length");
       PerfStatisticsCollector.updateQueueLength(queueLength.incrementAndGet());
     }
 
     if (this.fifo.size() == 1000) {
       ThreadDump threadDump = new ThreadDump();
       threadDump.start();
       throw new TsapiPlatformException(4, 0, "Event queue size has reached the threshold of 1000. Shutting down.");
     }
   }
 
   // ERROR //
   private CSTAEvent get()
   { return null;
     // Byte code:
     //   0: aload_0
     //   1: dup
     //   2: astore_2
     //   3: monitorenter
     //   4: iconst_0
     //   5: istore_3
     //   6: aload_0
     //   7: getfield 4	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:keepRunning	Z
     //   10: iconst_1
     //   11: if_icmpne +31 -> 42
     //   14: aload_0
     //   15: getfield 8	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:fifo	Ljava/util/Vector;
     //   18: invokevirtual 16	java/util/Vector:size	()I
     //   21: dup
     //   22: istore_3
     //   23: ifne +19 -> 42
     //   26: aload_0
     //   27: getstatic 49	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:DEFAULT_TIMEOUT	I
     //   30: i2l
     //   31: invokevirtual 50	java/lang/Object:wait	(J)V
     //   34: goto -28 -> 6
     //   37: astore 4
     //   39: goto -33 -> 6
     //   42: aload_0
     //   43: getfield 4	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:keepRunning	Z
     //   46: ifne +7 -> 53
     //   49: aconst_null
     //   50: aload_2
     //   51: monitorexit
     //   52: areturn
     //   53: aload_0
     //   54: getfield 8	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:fifo	Ljava/util/Vector;
     //   57: iload_3
     //   58: iconst_1
     //   59: isub
     //   60: invokevirtual 52	java/util/Vector:elementAt	(I)Ljava/lang/Object;
     //   63: checkcast 53	com/avaya/jtapi/tsapi/csta1/CSTAEvent
     //   66: astore_1
     //   67: aload_0
     //   68: getfield 8	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:fifo	Ljava/util/Vector;
     //   71: iload_3
     //   72: iconst_1
     //   73: isub
     //   74: invokevirtual 54	java/util/Vector:removeElementAt	(I)V
     //   77: aload_2
     //   78: monitorexit
     //   79: goto +10 -> 89
     //   82: astore 5
     //   84: aload_2
     //   85: monitorexit
     //   86: aload 5
     //   88: athrow
     //   89: getstatic 23	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:log	Lorg/apache/log4j/Logger;
     //   92: new 27	java/lang/StringBuilder
     //   95: dup
     //   96: invokespecial 28	java/lang/StringBuilder:<init>	()V
     //   99: ldc 55
     //   101: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   104: aload_1
     //   105: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
     //   108: ldc 35
     //   110: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   113: aload_0
     //   114: getfield 5	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:debugID	Ljava/lang/String;
     //   117: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   120: invokevirtual 36	java/lang/StringBuilder:toString	()Ljava/lang/String;
     //   123: invokevirtual 37	org/apache/log4j/Logger:info	(Ljava/lang/Object;)V
     //   126: invokestatic 20	com/avaya/jtapi/tsapi/util/JTAPILoggingAdapter:isPerformanceLoggingEnabled	()Z
     //   129: ifeq +22 -> 151
     //   132: getstatic 23	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:log	Lorg/apache/log4j/Logger;
     //   135: ldc 56
     //   137: invokevirtual 25	org/apache/log4j/Logger:debug	(Ljava/lang/Object;)V
     //   140: invokestatic 18	java/lang/System:currentTimeMillis	()J
     //   143: aload_1
     //   144: invokevirtual 57	com/avaya/jtapi/tsapi/csta1/CSTAEvent:getQueuedTimeStamp	()J
     //   147: lsub
     //   148: invokestatic 58	com/avaya/jtapi/tsapi/util/PerfStatisticsCollector:updateMessageLatency	(J)V
     //   151: invokestatic 20	com/avaya/jtapi/tsapi/util/JTAPILoggingAdapter:isPerformanceLoggingEnabled	()Z
     //   154: ifeq +21 -> 175
     //   157: getstatic 23	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:log	Lorg/apache/log4j/Logger;
     //   160: ldc 59
     //   162: invokevirtual 25	org/apache/log4j/Logger:debug	(Ljava/lang/Object;)V
     //   165: getstatic 43	com/avaya/jtapi/tsapi/tsapiInterface/TsapiEventQueue:queueLength	Ljava/util/concurrent/atomic/AtomicInteger;
     //   168: invokevirtual 60	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
     //   171: i2l
     //   172: invokestatic 45	com/avaya/jtapi/tsapi/util/PerfStatisticsCollector:updateQueueLength	(J)V
     //   175: aload_1
     //   176: areturn
     //
     // Exception table:
     //   from	to	target	type
     //   26	34	37	java/lang/InterruptedException
     //   4	52	82	finally
     //   53	79	82	finally
     //   82	86	82	finally
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiEventQueue
 * JD-Core Version:    0.5.4
 */