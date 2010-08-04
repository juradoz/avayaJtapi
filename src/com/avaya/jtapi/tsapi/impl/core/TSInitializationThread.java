/*    */ package com.avaya.jtapi.tsapi.impl.core;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;
/*    */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ final class TSInitializationThread extends Thread
/*    */ {
/* 15 */   private static Logger log = Logger.getLogger(TSInitializationThread.class);
/*    */   TSProviderImpl provider;
/*    */ 
/*    */   TSInitializationThread(TSProviderImpl _provider)
/*    */   {
/* 25 */     super("ProviderInitialization");
/* 26 */     this.provider = _provider;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 34 */       Vector eventList = new Vector();
/* 35 */       this.provider.setState(1, eventList);
/* 36 */       if (eventList.size() > 0)
/*    */       {
/* 39 */         Vector observers = this.provider.getMonitors();
/*    */ 
/* 41 */         for (int j = 0; j < observers.size(); ++j)
/*    */         {
/* 43 */           TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
/* 44 */           callback.deliverEvents(eventList, false);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 49 */       List monitorableDevices = this.provider.getMonitorableDevices();
/* 50 */       if ((monitorableDevices != null) && (monitorableDevices.size() != 0))
/*    */       {
/* 52 */         this.provider.tsMonitorableDevices.addAll(monitorableDevices);
/*    */       }
/*    */ 
/* 55 */       this.provider.setRouteDevices();
/*    */ 
/* 57 */       eventList = new Vector();
/* 58 */       this.provider.setState(2, eventList);
/* 59 */       if (eventList.size() > 0)
/*    */       {
/* 62 */         Vector observers = this.provider.getMonitors();
/*    */ 
/* 64 */         for (int j = 0; j < observers.size(); ++j)
/*    */         {
/* 66 */           TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
/* 67 */           callback.deliverEvents(eventList, false);
/*    */         }
/*    */       }
/*    */ 
/* 71 */       synchronized (this) { super.notify(); }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 75 */       log.error("INIT Thread Exception - shutting down provider " + this.provider);
/* 76 */       log.error(e.getMessage(), e);
/*    */       try
/*    */       {
/* 80 */         this.provider.shutdown();
/*    */       }
/*    */       catch (Exception e1)
/*    */       {
/*    */         try
/*    */         {
/* 86 */           this.provider.tsapi.shutdown();
/*    */         }
/*    */         catch (Exception e2)
/*    */         {
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSInitializationThread
 * JD-Core Version:    0.5.4
 */