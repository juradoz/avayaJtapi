 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiMonitor;
 import org.apache.log4j.Logger;
 
 public class JtapiEventDeliveryThread
   implements Runnable
 {
   private static Logger log = Logger.getLogger(JtapiEventDeliveryThread.class);
   private TsapiMonitor tsapiObserver;
   private long creationTime;
 
   public JtapiEventDeliveryThread(TsapiMonitor observer, long systemTime)
   {
     this.tsapiObserver = observer;
     this.creationTime = systemTime;
   }
 
   public void run()
   {
     if (System.currentTimeMillis() - this.creationTime >= 5000L)
       log.info("Queue processing is very slow. Consider increasing maxThreadPoolSize property");
     this.tsapiObserver.run();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.JtapiEventDeliveryThread
 * JD-Core Version:    0.5.4
 */