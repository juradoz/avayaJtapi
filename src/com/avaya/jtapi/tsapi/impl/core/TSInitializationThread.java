 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;
 import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
 import java.util.List;
 import java.util.Vector;
 import org.apache.log4j.Logger;
 
 final class TSInitializationThread extends Thread
 {
   private static Logger log = Logger.getLogger(TSInitializationThread.class);
   TSProviderImpl provider;
 
   TSInitializationThread(TSProviderImpl _provider)
   {
     super("ProviderInitialization");
     this.provider = _provider;
   }
 
   public void run()
   {
     try
     {
       Vector eventList = new Vector();
       this.provider.setState(1, eventList);
       if (eventList.size() > 0)
       {
         Vector observers = this.provider.getMonitors();
 
         for (int j = 0; j < observers.size(); ++j)
         {
           TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
           callback.deliverEvents(eventList, false);
         }
 
       }
 
       List monitorableDevices = this.provider.getMonitorableDevices();
       if ((monitorableDevices != null) && (monitorableDevices.size() != 0))
       {
         this.provider.tsMonitorableDevices.addAll(monitorableDevices);
       }
 
       this.provider.setRouteDevices();
 
       eventList = new Vector();
       this.provider.setState(2, eventList);
       if (eventList.size() > 0)
       {
         Vector observers = this.provider.getMonitors();
 
         for (int j = 0; j < observers.size(); ++j)
         {
           TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
           callback.deliverEvents(eventList, false);
         }
       }
 
       synchronized (this) { super.notify(); }
     }
     catch (Exception e)
     {
       log.error("INIT Thread Exception - shutting down provider " + this.provider);
       log.error(e.getMessage(), e);
       try
       {
         this.provider.shutdown();
       }
       catch (Exception e1)
       {
         try
         {
           this.provider.tsapi.shutdown();
         }
         catch (Exception e2)
         {
         }
       }
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSInitializationThread
 * JD-Core Version:    0.5.4
 */