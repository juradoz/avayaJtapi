 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
 import com.avaya.jtapi.tsapi.csta1.CSTAConferenceCallConfEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnection;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTATransferCallConfEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentConferenceCallConfEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCallConfEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCallConfEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentTransferCallConfEvent;
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import java.util.ArrayList;
 import java.util.Vector;
import org.apache.log4j.Logger;
 
 final class ConfXferConfHandler
   implements ConfHandler
 {
   private static Logger log = Logger.getLogger(ConfXferConfHandler.class);
   TSCall call;
   TSCall otherCall;
   int pdu;
   TSConnection newConn;
   CSTAConnectionID newCall;
 
   ConfXferConfHandler(TSCall _call, TSCall _otherCall, int _pdu)
   {
     this.call = _call;
     this.otherCall = _otherCall;
     this.pdu = _pdu;
     this.newConn = null;
     this.newCall = null;
   }
 
   public void handleConf(CSTAEvent event)
   {
     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
     {
       return;
     }
 
     if (this.call.getTSState() == 34)
     {
       return;
     }
 
     CSTAConnection[] connList = null;
     int cause = 0;
 
     switch (this.pdu)
     {
     case 12:
       this.newCall = ((CSTAConferenceCallConfEvent)event.getEvent()).getNewCall();
       connList = ((CSTAConferenceCallConfEvent)event.getEvent()).getConnList();
       cause = 207;
       if (event.getPrivData() instanceof LucentConferenceCallConfEvent)
       {
         this.call.setUCID(((LucentConferenceCallConfEvent)event.getPrivData()).getUcid()); } break;
     case 52:
       this.newCall = ((CSTATransferCallConfEvent)event.getEvent()).getNewCall();
       connList = ((CSTATransferCallConfEvent)event.getEvent()).getConnList();
       cause = 212;
       if (event.getPrivData() instanceof LucentTransferCallConfEvent)
       {
         this.call.setUCID(((LucentTransferCallConfEvent)event.getPrivData()).getUcid()); } break;
     case 90:
       if (event.getPrivData() instanceof LucentSingleStepConferenceCallConfEvent)
       {
         this.newCall = ((LucentSingleStepConferenceCallConfEvent)event.getPrivData()).getNewCall();
         connList = ((LucentSingleStepConferenceCallConfEvent)event.getPrivData()).getConnList();
         cause = 207;
         this.call.setUCID(((LucentSingleStepConferenceCallConfEvent)event.getPrivData()).getUcid());
       }
       else if (event.getPrivData() instanceof LucentSingleStepTransferCallConfEvent)
       {
         this.newCall = ((LucentSingleStepTransferCallConfEvent)event.getPrivData()).getTransferredCall();
         connList = null;
         cause = 212;
       }
       else
       {
         return;
       }
 
     }
 
     this.call.replyPriv = event.getPrivData();
 
     Vector snapConnections = new Vector();
 
     Vector priEventList = new Vector();
 
     Vector eventList = new Vector();
 
     if (connList == null)
     {
       this.call.setCallID(this.newCall.getCallID());
       this.call = this.call.getHandOff();
 
       this.call.updateConnectionCallIDs(this.newCall.getCallID());
 
       if (this.call.getTSProviderImpl().getConnection(this.newCall) == null)
       {
         TSConnection sstDestConnection = createNewCallConnectionTryToGetStateFromOtherCall(this.call, this.otherCall, this.newCall, priEventList, snapConnections);
 
         if (!sstDestConnection.isTerminalConnection()) {
           this.call.addConnection(sstDestConnection, eventList);
         }
       }
     }
     else if ((connList != null) && (connList.length > 0))
     {
       this.call.setCallID(this.newCall.getCallID());
       this.call = this.call.getHandOff();
 
       TSDevice device = null;
       Vector newConnections = new Vector();
       TSConnection conn = null;
 
       TSConnection foundTSConn = null;
 
       for (int i = 0; i < connList.length; ++i)
       {
         device = this.call.getTSProviderImpl().createDevice(connList[i].getStaticDevice(), connList[i].getParty());
         if (device == null) {
           continue;
         }
         foundTSConn = this.call.findTSConnectionForDevice(device);
         if (foundTSConn != null)
         {
           try
           {
             foundTSConn.setConnID(connList[i].getParty());
           }
           catch (TsapiPlatformException e)
           {
             log.error("TSCall.handleConf() caught TsapiPlatformException from setConnID() while processing connList, i=" + i + ", party=" + connList[i].getParty());
             log.error(e.getMessage(), e);
             log.trace("Dumping call (" + this.call + "):");
             this.call.dump("   ");
             log.trace("Dumping foundTSConn (" + foundTSConn + "):");
             foundTSConn.dump("   ");
             log.trace("Dumping call provider (" + this.call.getTSProviderImpl() + "):");
             this.call.getTSProviderImpl().dump("   ");
 
             throw e;
           }
 
           newConnections.addElement(foundTSConn);
 
           if (foundTSConn.isTerminalConnection())
           {
             int tcs = foundTSConn.getCallControlTermConnState();
             if (tcs == 96)
             {
               if (this.call.getTSProviderImpl().getCapabilities().getSnapshotCallReq() != 0)
               {
                 snapConnections.addElement(foundTSConn.getTSConn());
               }
 
               foundTSConn.setConnectionState(91, null);
               foundTSConn.setTermConnState(103, null);
             }
 
           }
           else
           {
             int cs = foundTSConn.getCallControlConnState();
             if (cs == 80)
             {
               if (this.call.getTSProviderImpl().getCapabilities().getSnapshotCallReq() != 0)
               {
                 snapConnections.addElement(foundTSConn.getTSConn());
               }
 
               foundTSConn.setConnectionState(91, null);
               foundTSConn.setTermConnState(103, null);
             }
 
           }
 
         }
         else
         {
           conn = createNewCallConnectionTryToGetStateFromOtherCall(this.call, this.otherCall, connList[i].getParty(), priEventList, snapConnections);
 
           newConnections.addElement(conn);
         }
 
         if (!connList[i].getParty().equals(this.newCall))
           continue;
         this.newConn = conn;
       }
 
       this.call.replaceConnections(newConnections, eventList);
     }
 
     for (int m = 0; m < priEventList.size(); ++m)
     {
       eventList.addElement(priEventList.elementAt(m));
     }
 
     Vector otherEventList = new Vector();
     if (this.otherCall != null)
     {
       this.otherCall.delayVDNremoveCallFromDomain = true;
       this.otherCall.setState(34, otherEventList);
       this.otherCall.delayVDNremoveCallFromDomain = false;
     }
 
     if (otherEventList.size() > 0)
     {
       Vector observers = this.otherCall.getObservers();
       addOldCallParams(otherEventList);
       for (int j = 0; j < observers.size(); ++j)
       {
         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
         callback.deliverEvents(otherEventList, cause, false);
       }
     }
     if (this.otherCall != null)
     {
       this.call.moveStuff(this.otherCall);
       this.otherCall.setStateForVDN();
 
       this.otherCall.endCVDObservers(cause, null);
       this.otherCall.endNonCVDObservers(cause);
       this.otherCall.staleObsCleanup(cause);
     }
 
     if (snapConnections.size() > 0)
     {
       this.call.setNeedSnapshot(true);
 
       SnapshotCallExtraConfHandler handler = new XferConfSnapshotCallConfHandler(this.call, cause, null, snapConnections);
 
       this.call.doSnapshot(((TSConnection)snapConnections.elementAt(0)).getConnID(), handler, false);
     }
 
     if (eventList.size() <= 0)
       return;
     Vector observers = this.call.getObservers();
     addOldCallParams(eventList);
     for (int j = 0; j < observers.size(); ++j)
     {
       TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
       callback.deliverEvents(eventList, cause, false);
     }
   }
 
   private void addOldCallParams(Vector<TSEvent> evList)
   {
     ArrayList oldCallList = new ArrayList();
     if (this.call != null)
       oldCallList.add(this.call);
     if (this.otherCall != null)
       oldCallList.add(this.otherCall);
     for (TSEvent ev : evList)
       ev.setTransferredEventParams(new TransferredEventParams(oldCallList));
   }
 
   TSConnection createNewCallConnectionTryToGetStateFromOtherCall(TSCall call, TSCall otherCall, CSTAConnectionID newConnID, Vector<TSEvent> priEventList, Vector<TSConnection> snapConnections)
   {
     TSConnection conn = null;
 
     TSDevice deviceToFind = call.getTSProviderImpl().createDevice(newConnID.getDeviceID(), newConnID);
 
     Vector tempEventList = new Vector();
 
     conn = call.getTSProviderImpl().createTerminalConnection(newConnID, deviceToFind, tempEventList, deviceToFind);
 
     int oldConnState = conn.getCallControlConnState();
     int oldTermConnState = conn.getCallControlTermConnState();
 
     if ((oldConnState == 89) || (oldTermConnState == 102))
     {
       call.getTSProviderImpl().deleteConnectionFromHash(newConnID);
       conn = call.getTSProviderImpl().createTerminalConnection(newConnID, deviceToFind, tempEventList, deviceToFind);
     }
 
     TSConnection foundTSConn = (otherCall == null) ? null : otherCall.findTSConnectionForDevice(deviceToFind);
 
     if (foundTSConn != null)
     {
       for (int m = 0; m < tempEventList.size(); ++m)
       {
         priEventList.addElement((TSEvent) tempEventList.elementAt(m));
       }
       conn.setConnectionState(foundTSConn.getCallControlConnState(), priEventList);
       conn.setTermConnState(foundTSConn.getCallControlTermConnState(), priEventList);
     }
     else
     {
       if (call.getTSProviderImpl().getCapabilities().getSnapshotCallReq() != 0)
       {
         snapConnections.addElement(conn.getTSConn());
       }
 
       for (int m = 0; m < tempEventList.size(); ++m)
       {
         priEventList.addElement((TSEvent) tempEventList.elementAt(m));
       }
       conn.setConnectionState(91, priEventList);
       conn.setTermConnState(103, priEventList);
     }
 
     return conn;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.ConfXferConfHandler
 * JD-Core Version:    0.5.4
 */