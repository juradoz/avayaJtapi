/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConferenceCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnection;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTATransferCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentConferenceCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentTransferCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Vector;
import org.apache.log4j.Logger;
/*      */ 
/*      */ final class ConfXferConfHandler
/*      */   implements ConfHandler
/*      */ {
/* 5716 */   private static Logger log = Logger.getLogger(ConfXferConfHandler.class);
/*      */   TSCall call;
/*      */   TSCall otherCall;
/*      */   int pdu;
/*      */   TSConnection newConn;
/*      */   CSTAConnectionID newCall;
/*      */ 
/*      */   ConfXferConfHandler(TSCall _call, TSCall _otherCall, int _pdu)
/*      */   {
/* 5725 */     this.call = _call;
/* 5726 */     this.otherCall = _otherCall;
/* 5727 */     this.pdu = _pdu;
/* 5728 */     this.newConn = null;
/* 5729 */     this.newCall = null;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 5790 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 5794 */       return;
/*      */     }
/*      */ 
/* 5800 */     if (this.call.getTSState() == 34)
/*      */     {
/* 5802 */       return;
/*      */     }
/*      */ 
/* 5805 */     CSTAConnection[] connList = null;
/* 5806 */     int cause = 0;
/*      */ 
/* 5808 */     switch (this.pdu)
/*      */     {
/*      */     case 12:
/* 5811 */       this.newCall = ((CSTAConferenceCallConfEvent)event.getEvent()).getNewCall();
/* 5812 */       connList = ((CSTAConferenceCallConfEvent)event.getEvent()).getConnList();
/* 5813 */       cause = 207;
/* 5814 */       if (event.getPrivData() instanceof LucentConferenceCallConfEvent)
/*      */       {
/* 5816 */         this.call.setUCID(((LucentConferenceCallConfEvent)event.getPrivData()).getUcid()); } break;
/*      */     case 52:
/* 5821 */       this.newCall = ((CSTATransferCallConfEvent)event.getEvent()).getNewCall();
/* 5822 */       connList = ((CSTATransferCallConfEvent)event.getEvent()).getConnList();
/* 5823 */       cause = 212;
/* 5824 */       if (event.getPrivData() instanceof LucentTransferCallConfEvent)
/*      */       {
/* 5826 */         this.call.setUCID(((LucentTransferCallConfEvent)event.getPrivData()).getUcid()); } break;
/*      */     case 90:
/* 5831 */       if (event.getPrivData() instanceof LucentSingleStepConferenceCallConfEvent)
/*      */       {
/* 5833 */         this.newCall = ((LucentSingleStepConferenceCallConfEvent)event.getPrivData()).getNewCall();
/* 5834 */         connList = ((LucentSingleStepConferenceCallConfEvent)event.getPrivData()).getConnList();
/* 5835 */         cause = 207;
/* 5836 */         this.call.setUCID(((LucentSingleStepConferenceCallConfEvent)event.getPrivData()).getUcid());
/*      */       }
/* 5838 */       else if (event.getPrivData() instanceof LucentSingleStepTransferCallConfEvent)
/*      */       {
/* 5852 */         this.newCall = ((LucentSingleStepTransferCallConfEvent)event.getPrivData()).getTransferredCall();
/* 5853 */         connList = null;
/* 5854 */         cause = 212;
/*      */       }
/*      */       else
/*      */       {
/* 5859 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 5864 */     this.call.replyPriv = event.getPrivData();
/*      */ 
/* 5868 */     Vector snapConnections = new Vector();
/*      */ 
/* 5872 */     Vector priEventList = new Vector();
/*      */ 
/* 5877 */     Vector eventList = new Vector();
/*      */ 
/* 5879 */     if (connList == null)
/*      */     {
/* 5896 */       this.call.setCallID(this.newCall.getCallID());
/* 5897 */       this.call = this.call.getHandOff();
/*      */ 
/* 5899 */       this.call.updateConnectionCallIDs(this.newCall.getCallID());
/*      */ 
/* 5909 */       if (this.call.getTSProviderImpl().getConnection(this.newCall) == null)
/*      */       {
/* 5919 */         TSConnection sstDestConnection = createNewCallConnectionTryToGetStateFromOtherCall(this.call, this.otherCall, this.newCall, priEventList, snapConnections);
/*      */ 
/* 5933 */         if (!sstDestConnection.isTerminalConnection()) {
/* 5934 */           this.call.addConnection(sstDestConnection, eventList);
/*      */         }
/*      */       }
/*      */     }
/* 5938 */     else if ((connList != null) && (connList.length > 0))
/*      */     {
/* 5941 */       this.call.setCallID(this.newCall.getCallID());
/* 5942 */       this.call = this.call.getHandOff();
/*      */ 
/* 5946 */       TSDevice device = null;
/* 5947 */       Vector newConnections = new Vector();
/* 5948 */       TSConnection conn = null;
/*      */ 
/* 5950 */       TSConnection foundTSConn = null;
/*      */ 
/* 5959 */       for (int i = 0; i < connList.length; ++i)
/*      */       {
/* 5961 */         device = this.call.getTSProviderImpl().createDevice(connList[i].getStaticDevice(), connList[i].getParty());
/* 5962 */         if (device == null) {
/*      */           continue;
/*      */         }
/* 5965 */         foundTSConn = this.call.findTSConnectionForDevice(device);
/* 5966 */         if (foundTSConn != null)
/*      */         {
/*      */           try
/*      */           {
/* 5983 */             foundTSConn.setConnID(connList[i].getParty());
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/* 5987 */             log.error("TSCall.handleConf() caught TsapiPlatformException from setConnID() while processing connList, i=" + i + ", party=" + connList[i].getParty());
/* 5988 */             log.error(e.getMessage(), e);
/* 5989 */             log.trace("Dumping call (" + this.call + "):");
/* 5990 */             this.call.dump("   ");
/* 5991 */             log.trace("Dumping foundTSConn (" + foundTSConn + "):");
/* 5992 */             foundTSConn.dump("   ");
/* 5993 */             log.trace("Dumping call provider (" + this.call.getTSProviderImpl() + "):");
/* 5994 */             this.call.getTSProviderImpl().dump("   ");
/*      */ 
/* 5996 */             throw e;
/*      */           }
/*      */ 
/* 5999 */           newConnections.addElement(foundTSConn);
/*      */ 
/* 6001 */           if (foundTSConn.isTerminalConnection())
/*      */           {
/* 6003 */             int tcs = foundTSConn.getCallControlTermConnState();
/* 6004 */             if (tcs == 96)
/*      */             {
/* 6007 */               if (this.call.getTSProviderImpl().getCapabilities().getSnapshotCallReq() != 0)
/*      */               {
/* 6009 */                 snapConnections.addElement(foundTSConn.getTSConn());
/*      */               }
/*      */ 
/* 6014 */               foundTSConn.setConnectionState(91, null);
/* 6015 */               foundTSConn.setTermConnState(103, null);
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 6021 */             int cs = foundTSConn.getCallControlConnState();
/* 6022 */             if (cs == 80)
/*      */             {
/* 6025 */               if (this.call.getTSProviderImpl().getCapabilities().getSnapshotCallReq() != 0)
/*      */               {
/* 6027 */                 snapConnections.addElement(foundTSConn.getTSConn());
/*      */               }
/*      */ 
/* 6032 */               foundTSConn.setConnectionState(91, null);
/* 6033 */               foundTSConn.setTermConnState(103, null);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 6046 */           conn = createNewCallConnectionTryToGetStateFromOtherCall(this.call, this.otherCall, connList[i].getParty(), priEventList, snapConnections);
/*      */ 
/* 6055 */           newConnections.addElement(conn);
/*      */         }
/*      */ 
/* 6058 */         if (!connList[i].getParty().equals(this.newCall))
/*      */           continue;
/* 6060 */         this.newConn = conn;
/*      */       }
/*      */ 
/* 6065 */       this.call.replaceConnections(newConnections, eventList);
/*      */     }
/*      */ 
/* 6074 */     for (int m = 0; m < priEventList.size(); ++m)
/*      */     {
/* 6076 */       eventList.addElement(priEventList.elementAt(m));
/*      */     }
/*      */ 
/* 6079 */     Vector otherEventList = new Vector();
/* 6080 */     if (this.otherCall != null)
/*      */     {
/* 6083 */       this.otherCall.delayVDNremoveCallFromDomain = true;
/* 6084 */       this.otherCall.setState(34, otherEventList);
/* 6085 */       this.otherCall.delayVDNremoveCallFromDomain = false;
/*      */     }
/*      */ 
/* 6089 */     if (otherEventList.size() > 0)
/*      */     {
/* 6091 */       Vector observers = this.otherCall.getObservers();
/* 6092 */       addOldCallParams(otherEventList);
/* 6093 */       for (int j = 0; j < observers.size(); ++j)
/*      */       {
/* 6095 */         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 6096 */         callback.deliverEvents(otherEventList, cause, false);
/*      */       }
/*      */     }
/* 6099 */     if (this.otherCall != null)
/*      */     {
/* 6102 */       this.call.moveStuff(this.otherCall);
/* 6103 */       this.otherCall.setStateForVDN();
/*      */ 
/* 6106 */       this.otherCall.endCVDObservers(cause, null);
/* 6107 */       this.otherCall.endNonCVDObservers(cause);
/* 6108 */       this.otherCall.staleObsCleanup(cause);
/*      */     }
/*      */ 
/* 6111 */     if (snapConnections.size() > 0)
/*      */     {
/* 6113 */       this.call.setNeedSnapshot(true);
/*      */ 
/* 6115 */       SnapshotCallExtraConfHandler handler = new XferConfSnapshotCallConfHandler(this.call, cause, null, snapConnections);
/*      */ 
/* 6117 */       this.call.doSnapshot(((TSConnection)snapConnections.elementAt(0)).getConnID(), handler, false);
/*      */     }
/*      */ 
/* 6122 */     if (eventList.size() <= 0)
/*      */       return;
/* 6124 */     Vector observers = this.call.getObservers();
/* 6125 */     addOldCallParams(eventList);
/* 6126 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 6128 */       TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 6129 */       callback.deliverEvents(eventList, cause, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addOldCallParams(Vector<TSEvent> evList)
/*      */   {
/* 6142 */     ArrayList oldCallList = new ArrayList();
/* 6143 */     if (this.call != null)
/* 6144 */       oldCallList.add(this.call);
/* 6145 */     if (this.otherCall != null)
/* 6146 */       oldCallList.add(this.otherCall);
/* 6147 */     for (TSEvent ev : evList)
/* 6148 */       ev.setTransferredEventParams(new TransferredEventParams(oldCallList));
/*      */   }
/*      */ 
/*      */   TSConnection createNewCallConnectionTryToGetStateFromOtherCall(TSCall call, TSCall otherCall, CSTAConnectionID newConnID, Vector<TSEvent> priEventList, Vector<TSConnection> snapConnections)
/*      */   {
/* 6198 */     TSConnection conn = null;
/*      */ 
/* 6201 */     TSDevice deviceToFind = call.getTSProviderImpl().createDevice(newConnID.getDeviceID(), newConnID);
/*      */ 
/* 6206 */     Vector tempEventList = new Vector();
/*      */ 
/* 6208 */     conn = call.getTSProviderImpl().createTerminalConnection(newConnID, deviceToFind, tempEventList, deviceToFind);
/*      */ 
/* 6210 */     int oldConnState = conn.getCallControlConnState();
/* 6211 */     int oldTermConnState = conn.getCallControlTermConnState();
/*      */ 
/* 6213 */     if ((oldConnState == 89) || (oldTermConnState == 102))
/*      */     {
/* 6217 */       call.getTSProviderImpl().deleteConnectionFromHash(newConnID);
/* 6218 */       conn = call.getTSProviderImpl().createTerminalConnection(newConnID, deviceToFind, tempEventList, deviceToFind);
/*      */     }
/*      */ 
/* 6222 */     TSConnection foundTSConn = (otherCall == null) ? null : otherCall.findTSConnectionForDevice(deviceToFind);
/*      */ 
/* 6225 */     if (foundTSConn != null)
/*      */     {
/* 6227 */       for (int m = 0; m < tempEventList.size(); ++m)
/*      */       {
/* 6229 */         priEventList.addElement((TSEvent) tempEventList.elementAt(m));
/*      */       }
/* 6231 */       conn.setConnectionState(foundTSConn.getCallControlConnState(), priEventList);
/* 6232 */       conn.setTermConnState(foundTSConn.getCallControlTermConnState(), priEventList);
/*      */     }
/*      */     else
/*      */     {
/* 6241 */       if (call.getTSProviderImpl().getCapabilities().getSnapshotCallReq() != 0)
/*      */       {
/* 6243 */         snapConnections.addElement(conn.getTSConn());
/*      */       }
/*      */ 
/* 6248 */       for (int m = 0; m < tempEventList.size(); ++m)
/*      */       {
/* 6250 */         priEventList.addElement((TSEvent) tempEventList.elementAt(m));
/*      */       }
/* 6252 */       conn.setConnectionState(91, priEventList);
/* 6253 */       conn.setTermConnState(103, priEventList);
/*      */     }
/*      */ 
/* 6260 */     return conn;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.ConfXferConfHandler
 * JD-Core Version:    0.5.4
 */