 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTAPickupCallConfEvent;
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import java.util.Vector;
 
 final class PickupConfHandler
   implements ConfHandler
 {
   TSDevice device;
   TSDevice terminalAddress;
   TSConnection pickConnection;
 
   PickupConfHandler(TSDevice _device, TSDevice _terminalAddress, TSConnection _pickConnection)
   {
     this.device = _device;
     this.terminalAddress = _terminalAddress;
     this.pickConnection = _pickConnection;
   }
 
   public void handleConf(CSTAEvent event)
   {
     if ((event == null) || (!(event.getEvent() instanceof CSTAPickupCallConfEvent)))
     {
       return;
     }
 
     this.device.replyTermPriv = event.getPrivData();
 
     Vector eventList = new Vector();
     if (this.terminalAddress == this.pickConnection.getTSDevice())
     {
       this.pickConnection.setConnectionState(88, eventList);
 
       if (eventList.size() <= 0)
         return;
       TSCall pickCall = this.pickConnection.getTSCall();
       Vector observers = pickCall.getObservers();
       for (int j = 0; j < observers.size(); ++j)
       {
         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
         callback.deliverEvents(eventList, 100, false);
       }
 
     }
     else
     {
       this.pickConnection.setConnectionState(89, eventList);
 
       if (eventList.size() <= 0)
         return;
       TSCall pickCall = this.pickConnection.getTSCall();
       Vector observers = pickCall.getObservers();
       for (int j = 0; j < observers.size(); ++j)
       {
         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
         callback.deliverEvents(eventList, 100, false);
       }
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.PickupConfHandler
 * JD-Core Version:    0.5.4
 */