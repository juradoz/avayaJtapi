 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.csta1.CSTAClearCallConfEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import java.util.Vector;
 
 final class ClearCallConfHandler
   implements ConfHandler
 {
   TSCall call;
 
   ClearCallConfHandler(TSCall _call)
   {
     this.call = _call;
   }
 
   public void handleConf(CSTAEvent event)
   {
     if ((event == null) || (!(event.getEvent() instanceof CSTAClearCallConfEvent)))
     {
       return;
     }
 
     this.call.replyPriv = event.getPrivData();
 
     Vector eventList = new Vector();
     this.call.setState(34, eventList);
     if (eventList.size() > 0)
     {
       Vector observers = this.call.getObservers();
       for (int j = 0; j < observers.size(); ++j)
       {
         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
         callback.deliverEvents(eventList, 100, false);
       }
     }
     this.call.endCVDObservers(100, null);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.ClearCallConfHandler
 * JD-Core Version:    0.5.4
 */