 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import java.util.Vector;
 
 final class DisconnectedConfHandler
   implements ConfHandler
 {
   TSConnection conn;
   boolean handleIt = true;
   int pdu;
 
   DisconnectedConfHandler(TSConnection _conn, int _pdu)
   {
     this.conn = _conn;
     this.pdu = _pdu;
   }
 
   public void handleConf(CSTAEvent event)
   {
     if ((!this.handleIt) || (event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
     {
       return;
     }
 
     this.conn.replyConnPriv = event.getPrivData();
 
     Vector eventList = new Vector();
     this.conn.setConnectionState(89, eventList);
 
     if (eventList.size() <= 0)
       return;
     Vector observers = this.conn.getTSCall().getObservers();
     for (int j = 0; j < observers.size(); ++j)
     {
       TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
       callback.deliverEvents(eventList, 100, false);
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.DisconnectedConfHandler
 * JD-Core Version:    0.5.4
 */