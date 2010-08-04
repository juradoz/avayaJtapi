/*      */ package com.avaya.jtapi.tsapi.impl.monitor;
/*      */ 
/*      */ import java.util.ArrayList;
import java.util.BitSet;
import java.util.Vector;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.CallEvent;
import javax.telephony.CallListener;
import javax.telephony.CallObserver;
import javax.telephony.Connection;
import javax.telephony.ConnectionEvent;
import javax.telephony.ConnectionListener;
import javax.telephony.Event;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;
import javax.telephony.TerminalConnectionEvent;
import javax.telephony.TerminalConnectionListener;
import javax.telephony.callcenter.CallCenterCallListener;
import javax.telephony.callcenter.CallCenterCallObserver;
import javax.telephony.callcenter.CallCenterTrunkEvent;
import javax.telephony.callcontrol.CallControlCallObserver;
import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlConnectionListener;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionListener;
import javax.telephony.events.CallEv;
import javax.telephony.privatedata.PrivateDataCallListener;
import javax.telephony.privatedata.PrivateDataEvent;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.ITsapiEvent;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.impl.TsapiCall;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSEvent;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;
import com.avaya.jtapi.tsapi.impl.events.call.CallCenterTrunkEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallControlCallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventUtil;
import com.avaya.jtapi.tsapi.impl.events.call.PrivateDataCallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallActiveEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallInvalidEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallObservationEndedEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiPrivateCallEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiTrunkInvalidEv;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiTrunkValidEv;
import com.avaya.jtapi.tsapi.impl.events.conn.CallCenterConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.CallControlConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.CallControlConnectionNetworkReachedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.ConnEventParams;
import com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentCallCenterConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentCallControlConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentCallControlConnectionNetworkReachedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnAlertingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnDialingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnDisconnectedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnEstablishedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnFailedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnInProgressEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnInitiatedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnNetworkAlertingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnNetworkReachedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnOfferedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnQueuedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallCenterConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallControlConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallControlConnectionNetworkReachedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnAlertingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnDialingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnDisconnectedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnEstablishedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnFailedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnInProgressEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnInitiatedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnNetworkAlertingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnNetworkReachedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnOfferedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnQueuedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnAlertingEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnAlertingEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnConnectedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnCreatedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDialingEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDisconnectedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDisconnectedEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnEstablishedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnFailedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnFailedEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInProgressEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInProgressEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInitiatedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkAlertingEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkReachedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnOfferedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnQueuedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnUnknownEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnUnknownEventCC;
import com.avaya.jtapi.tsapi.impl.events.termConn.CallControlTerminalConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentCallControlTerminalConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnBridgedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnDroppedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnHeldEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnInUseEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnRingingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnTalkingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5CallControlTerminalConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnBridgedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnDroppedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnHeldEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnInUseEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnRingingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnTalkingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.PrivateDtmfEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.TermConnEventParams;
import com.avaya.jtapi.tsapi.impl.events.termConn.TerminalConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnActiveEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnBridgedEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnCreatedEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDTMFEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDroppedEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDroppedEventCC;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnHeldEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnInUseEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnPassiveEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnRingingEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnRingingEventCC;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnTalkingEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnUnknownEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnUnknownEventCC;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*      */ 
/*      */ public final class TsapiCallMonitor
/*      */   implements TsapiMonitor
/*      */ {
/*  164 */   private static Logger log = Logger.getLogger(TsapiCallMonitor.class);
/*      */   TSProviderImpl provider;
/*  167 */   CallObserver observer = null;
/*  168 */   CallListener callListener = null;
/*      */   boolean isVDN;
/*      */   private final Vector<CallEv> eventList;
/*      */   private final Vector<Event> listenerEventList;
/*  172 */   long reference = 0L;
/*  173 */   BitSet eventSubscriptionType = new BitSet(8);
/*      */   private boolean metaStartAdded;
/* 1640 */   Object syncObject = new Object();
/*      */ 
/*      */   public void dump(String indent)
/*      */   {
/*  179 */     log.trace(indent + "***** TsapiCallMonitor DUMP *****");
/*  180 */     log.trace(indent + "TsapiCallMonitor: " + this);
/*  181 */     log.trace(indent + "observer: " + this.observer);
/*  182 */     log.trace(indent + "***** TsapiCallMonitor DUMP END *****");
/*      */   }
/*      */ 
/*      */   public TsapiCallMonitor(TSProviderImpl _provider, CallObserver _observer)
/*      */   {
/*  189 */     this.provider = _provider;
/*  190 */     this.observer = _observer;
/*  191 */     this.callListener = null;
/*  192 */     this.eventList = new Vector();
/*  193 */     this.listenerEventList = null;
/*  194 */     this.provider.addCallMonitorThread(this);
/*      */ 
/*  196 */     this.isVDN = false;
/*      */ 
/*  198 */     this.eventSubscriptionType.set(0);
/*  199 */     if (this.observer instanceof CallControlCallObserver)
/*      */     {
/*  201 */       this.eventSubscriptionType.set(1);
/*      */     }
/*  203 */     if (this.observer instanceof CallCenterCallObserver)
/*      */     {
/*  205 */       this.eventSubscriptionType.set(2);
/*      */     }
///*  207 */     if (this.observer instanceof MediaCallObserver)
///*      */     {
///*  209 */       this.eventSubscriptionType.set(3);
///*      */     }
/*      */ 
/*  214 */     this.eventSubscriptionType.set(5);
/*      */ 
/*  218 */     deliverEvents(null, 0, false);
/*      */   }
/*      */ 
/*      */   public TsapiCallMonitor(TSProviderImpl _provider, CallListener listener)
/*      */   {
/*  223 */     this.provider = _provider;
/*  224 */     this.observer = null;
/*  225 */     this.callListener = listener;
/*  226 */     this.listenerEventList = new Vector();
/*  227 */     this.eventList = null;
/*  228 */     this.provider.addCallMonitorThread(this);
/*      */ 
/*  230 */     this.isVDN = false;
/*      */ 
/*  234 */     deliverEvents(null, 0, false);
/*      */   }
/*      */ 
/*      */   public void setVDN(boolean isVDN)
/*      */   {
/*  239 */     this.isVDN = isVDN;
/*      */   }
/*      */ 
/*      */   public boolean isVDN()
/*      */   {
/*  244 */     return this.isVDN;
/*      */   }
/*      */ 
/*      */   public CallObserver getObserver()
/*      */   {
/*  249 */     return this.observer;
/*      */   }
/*      */ 
/*      */   public CallListener getListener() {
/*  253 */     return this.callListener;
/*      */   }
/*      */ 
/*      */   public synchronized void addReference()
/*      */   {
/*  258 */     this.reference += 1L;
/*      */   }
/*      */ 
/*      */   public void deleteReference(Object observed, boolean isTerminal, int cause, Object privateData)
/*      */   {
/*  268 */     if (this.observer != null)
/*  269 */       log.debug("Getting TsapiCallMonitor lock to deliver deleteReference events for monitor " + this.observer);
/*      */     else
/*  271 */       log.debug("Getting TsapiCallMonitor lock to deliver deleteReference events for monitor " + this.callListener);
/*  272 */     deleteReferenceInternal(observed, isTerminal, cause, privateData);
/*      */   }
/*      */ 
/*      */   private synchronized void deleteReferenceInternal(Object observed, boolean isTerminal, int cause, Object privateData)
/*      */   {
/*  278 */     String tsEventLog = null;
/*  279 */     this.reference -= 1L;
/*      */ 
/*  281 */     if (this.observer != null)
/*  282 */       log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.observer);
/*  283 */     tsEventLog = "OBSERVATIONENDEDEVENT for observed: " + observed;
/*      */ 
/*  286 */     TSCall targetCall = (observed instanceof TSCall) ? (TSCall)observed : null;
/*  287 */     if ((targetCall != null) && 
/*  289 */       (!targetCall.checkForMonitors()))
/*      */     {
/*  291 */       targetCall.setNeedSnapshot(true);
/*      */     }
/*      */ 
/*  296 */     if (observed != null)
/*      */     {
/*  298 */       observed = TsapiCreateObject.getTsapiObject(observed, !isTerminal);
/*      */     }
/*      */ 
/*  301 */     int nextMetaEventIndex = 0;
/*      */ 
/*  303 */     if (this.observer != null) {
/*  304 */       synchronized (this.eventList) {
/*  305 */         nextMetaEventIndex = this.eventList.size();
/*  306 */         CallEventParams params = createCallParams(targetCall, cause, 136, privateData, null);
/*      */ 
/*  308 */         addObserverEvent(new TsapiCallObservationEndedEvent(params, observed), tsEventLog);
/*      */ 
/*  310 */         ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/*      */ 
/*  312 */         if ((targetCall != null) && (privateData != null))
/*      */         {
/*  314 */           tsEventLog = "PRIVATEEVENT for " + targetCall;
/*  315 */           addObserverEvent(new TsapiPrivateCallEvent(createCallParams(targetCall, cause, 136, privateData, null)), tsEventLog);
/*      */         }
/*      */ 
/*  319 */         log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
/*      */       }
/*  321 */     } else if (this.callListener != null) {
/*  322 */       CallEventParams callEvParams = createCallParams(targetCall, cause, -1, privateData, null);
/*  323 */       MetaEvent[] metaEvents = CallEventUtil.getListenerMetaObject(cause, callEvParams, false);
/*  324 */       synchronized (this.listenerEventList) {
/*  325 */         this.listenerEventList.add(metaEvents[0]);
/*  326 */         this.listenerEventList.add(new CallEventImpl(callEvParams, metaEvents[0], 103));
/*  327 */         if ((targetCall != null) && (privateData != null) && (this.callListener instanceof PrivateDataCallListener)) {
/*  328 */           tsEventLog = "PRIVATEEVENT for " + targetCall + "for listener " + this.callListener;
/*  329 */           this.listenerEventList.add(new PrivateDataCallEventImpl(privateData, targetCall, cause, metaEvents[0]));
/*  330 */           log.debug(tsEventLog);
/*      */         }
/*  332 */         this.listenerEventList.add(metaEvents[1]);
/*      */       }
/*      */     }
/*      */ 
/*  336 */     if (this.reference <= 0L)
/*      */     {
/*  338 */       this.provider.removeCallMonitorThread(this);
/*      */     }
/*      */ 
/*  341 */     JtapiEventThreadManager.execute(this);
/*      */   }
/*      */ 
/*      */   void addObserverEvent(CallEv event, String tsEventLog)
/*      */   {
/*  347 */     if ((this.eventSubscriptionType.get(0)) && (((ITsapiEvent)event).getEventPackage() == 0))
/*      */     {
/*  350 */       log.debug(tsEventLog + " for observer " + this.observer);
/*      */ 
/*  352 */       this.eventList.addElement(event);
/*      */     }
/*  354 */     else if ((this.eventSubscriptionType.get(1)) && (((ITsapiEvent)event).getEventPackage() == 1))
/*      */     {
/*  357 */       log.debug(tsEventLog + " for observer " + this.observer);
/*      */ 
/*  359 */       this.eventList.addElement(event);
/*      */     }
/*  361 */     else if ((this.eventSubscriptionType.get(2)) && (((ITsapiEvent)event).getEventPackage() == 2))
/*      */     {
/*  364 */       log.debug(tsEventLog + " for observer " + this.observer);
/*      */ 
/*  366 */       this.eventList.addElement(event);
/*      */     }
/*  368 */     else if ((this.eventSubscriptionType.get(3)) && (((ITsapiEvent)event).getEventPackage() == 3))
/*      */     {
/*  371 */       log.debug(tsEventLog + " for observer " + this.observer);
/*      */ 
/*  373 */       this.eventList.addElement(event);
/*      */     }
/*  375 */     else if ((this.eventSubscriptionType.get(5)) && (((ITsapiEvent)event).getEventPackage() == 5))
/*      */     {
/*  378 */       log.debug(tsEventLog + " for observer " + this.observer);
/*      */ 
/*  380 */       this.eventList.addElement(event);
/*      */     }
/*      */     else
/*      */     {
/*  384 */       log.debug(tsEventLog + " ignored");
/*      */     }
/*      */   }
/*      */ 
/*      */   int getObserverMetaCode(int cause, Vector<TSEvent> _eventList)
/*      */   {
/*  390 */     switch (cause)
/*      */     {
/*      */     case 207:
/*  393 */       return 133;
/*      */     case 212:
/*  395 */       return 134;
/*      */     }
/*      */ 
/*  400 */     boolean created = false;
/*  401 */     boolean disconnected = false;
/*  402 */     boolean dtmf = false;
/*  403 */     for (int i = 0; i < _eventList.size(); ++i)
/*      */     {
/*  405 */       switch (((TSEvent)_eventList.elementAt(i)).getEventType())
/*      */       {
/*      */       case 4:
/*  408 */         return 128;
/*      */       case 5:
/*  410 */         return 132;
/*      */       case 6:
/*  412 */         created = true;
/*  413 */         break;
/*      */       case 10:
/*  415 */         disconnected = true;
/*  416 */         break;
/*      */       case 57:
/*  418 */         dtmf = true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  423 */     if (created)
/*      */     {
/*  425 */       return 130;
/*      */     }
/*  427 */     if (disconnected)
/*      */     {
/*  429 */       return 131;
/*      */     }
/*  431 */     if (dtmf)
/*      */     {
/*  433 */       return 136;
/*      */     }
/*      */ 
/*  436 */     return 129;
/*      */   }
/*      */ 
/*      */   public void deliverEvents(Vector<TSEvent> _eventList, int cause, boolean snapshot)
/*      */   {
/*  445 */     if ((_eventList == null) || (_eventList.size() == 0)) {
/*  446 */       return;
/*      */     }
/*  448 */     log.debug("Getting TsapiCallMonitor lock to deliver events for call " + ((this.observer == null) ? "listener " : "observer ") + ((this.observer == null) ? this.callListener : this.observer));
/*  449 */     synchronized (_eventList) {
/*  450 */       deliverEventsInternal(_eventList, cause, snapshot);
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList, int cause, boolean snapshot)
/*      */   {
/*  456 */     if (this.observer != null)
/*  457 */       createObserverEvents(_eventList, cause, snapshot);
/*      */     else {
/*  459 */       createListenerEvents(_eventList, cause, snapshot);
/*      */     }
/*  461 */     JtapiEventThreadManager.execute(this);
/*      */   }
/*      */ 
/*      */   private void createListenerEvents(Vector<TSEvent> _eventList, int cause, boolean snapshot)
/*      */   {
/*  466 */     TSCall callTarget = null;
/*  467 */     TSConnection connTarget = null;
/*  468 */     TSTrunk trkTarget = null;
/*  469 */     Object privateData = null;
/*  470 */     CallEventParams callEventParams = null;
/*  471 */     int metaCode = -1;
/*  472 */     MetaEvent[] metaEventPair = null;
/*  473 */     int nextMetaEventIndex = this.listenerEventList.size();
/*      */ 
/*  475 */     TSEvent event = (TSEvent)_eventList.get(0);
/*      */ 
/*  477 */     if (event.getEventTarget() instanceof TSCall) {
/*  478 */       callTarget = (TSCall)event.getEventTarget();
/*  479 */       if ((((cause == 212) || (cause == 207))) && (event.getTransferredEventParams() != null))
/*  480 */         callEventParams = createCallParams(callTarget, cause, -1, event.getPrivateData(), event.getTransferredEventParams().getOldCalls(), event);
/*      */       else
/*  482 */         callEventParams = createCallParams(callTarget, cause, -1, event.getPrivateData(), event);
/*  483 */     } else if (event.getEventTarget() instanceof TSTrunk) {
/*  484 */       trkTarget = (TSTrunk)event.getEventTarget();
/*  485 */       if ((((cause == 212) || (cause == 207))) && (event.getTransferredEventParams() != null))
/*  486 */         callEventParams = createCallParams(callTarget, cause, -1, event.getPrivateData(), event.getTransferredEventParams().getOldCalls(), event);
/*      */       else
/*  488 */         callEventParams = createCallParams(trkTarget, cause, -1, event.getPrivateData(), event);
/*      */     } else {
/*  490 */       connTarget = (TSConnection)event.getEventTarget();
/*  491 */       if ((((cause == 212) || (cause == 207))) && (event.getTransferredEventParams() != null))
/*  492 */         callEventParams = createConnParams(connTarget, cause, -1, event.getPrivateData(), event.getTransferredEventParams().getOldCalls(), event);
/*      */       else {
/*  494 */         callEventParams = createConnParams(connTarget, cause, -1, event.getPrivateData(), event);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  503 */     metaEventPair = CallEventUtil.getListenerMetaObject(cause, callEventParams, snapshot);
/*      */ 
/*  505 */     MetaEvent metaEvent = metaEventPair[0];
/*  506 */     metaCode = metaEvent.getID();
/*      */ 
/*  509 */     String tsEventLog = null;
/*      */ 
/*  511 */     log.debug("Meta event BEGIN: cause (" + cause + ") metaCode (" + metaCode + ")" + " for " + ((this.observer == null) ? this.callListener : this.observer));
/*  512 */     Object prevPrivateData = null;
/*  513 */     for (TSEvent ev : _eventList) {
/*  514 */       if (ev.getEventTarget() instanceof TSCall)
/*  515 */         callTarget = (TSCall)ev.getEventTarget();
/*  516 */       else if (ev.getEventTarget() instanceof TSTrunk)
/*  517 */         trkTarget = (TSTrunk)ev.getEventTarget();
/*      */       else {
/*  519 */         connTarget = (TSConnection)ev.getEventTarget();
/*      */       }
/*      */ 
/*  522 */       privateData = ev.getPrivateData();
/*  523 */       PrivateDataCallEventImpl privateEvent = null;
/*      */ 
/*  525 */       if ((privateData != null) && (ev.getEventType() != 9999) && (((privateData instanceof TsapiPrivate) || (privateData instanceof LucentChargeAdviceEvent) || (privateData instanceof TsapiPrivateStateEvent))))
/*  526 */         if (!privateData.equals(prevPrivateData)) {
/*  527 */           prevPrivateData = privateData;
/*  528 */           Object source = null;
/*  529 */           if (callTarget != null)
/*  530 */             source = callTarget;
/*  531 */           else if (connTarget != null)
/*  532 */             source = connTarget;
/*  533 */           else if (trkTarget != null) {
/*  534 */             source = trkTarget;
/*      */           }
/*  536 */           privateEvent = new PrivateDataCallEventImpl(privateData, source, cause, metaEvent);
/*  537 */           checkAndAddPrivateEvent(source, privateEvent, metaEvent);
/*      */         }
/*      */       else {
/*  540 */         prevPrivateData = null;
/*      */       }
/*  542 */       CallEventParams params = null;
/*      */ 
/*  544 */       switch (ev.getEventType())
/*      */       {
/*      */       case 4:
/*  546 */         params = createCallParams(callTarget, cause, metaCode, privateData, ev);
/*  547 */         synchronized (this.listenerEventList) {
/*  548 */           if (!this.metaStartAdded) {
/*  549 */             this.listenerEventList.add(metaEvent);
/*  550 */             this.metaStartAdded = true;
/*      */           }
/*  552 */           this.listenerEventList.add(new CallEventImpl(params, metaEvent, 101));
/*      */         }
/*  554 */         tsEventLog = "CALLACTIVEEVENT for " + callTarget;
/*  555 */         log.debug(tsEventLog + " for listener " + this.callListener);
/*  556 */         break;
/*      */       case 5:
/*  559 */         params = createCallParams(callTarget, cause, metaCode, privateData, ev);
/*  560 */         synchronized (this.listenerEventList) {
/*  561 */           if (!this.metaStartAdded) {
/*  562 */             this.listenerEventList.add(metaEvent);
/*  563 */             this.metaStartAdded = true;
/*      */           }
/*  565 */           this.listenerEventList.add(new CallControlCallEventImpl(params, metaEvent, 102));
/*      */         }
/*  567 */         tsEventLog = "CALLINVALIDEVENT for " + callTarget;
/*  568 */         log.debug(tsEventLog + " for listener " + this.callListener);
/*  569 */         break;
/*      */       case 6:
/*  572 */         tsEventLog = "CONNECTIONCREATEDEVENT for " + connTarget;
/*  573 */         addConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 106, tsEventLog, ev);
/*  574 */         break;
/*      */       case 7:
/*  577 */         tsEventLog = "CONNECTIONCONNECTEDEVENT for " + connTarget;
/*  578 */         addConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 105, tsEventLog, ev);
/*  579 */         break;
/*      */       case 8:
/*  582 */         tsEventLog = "CONNECTIONINPROGRESSEVENT for " + connTarget;
/*  583 */         addConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 109, tsEventLog, ev);
/*  584 */         break;
/*      */       case 9:
/*  587 */         tsEventLog = "CONNECTIONALERTINGEVENT for " + connTarget;
/*  588 */         addConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 104, tsEventLog, ev);
/*  589 */         break;
/*      */       case 10:
/*  592 */         tsEventLog = "CONNECTIONDISCONNECTEDEVENT for " + connTarget;
/*  593 */         addConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 107, tsEventLog, ev);
/*  594 */         break;
/*      */       case 11:
/*  597 */         tsEventLog = "CONNECTIONFAILEDEVENT for " + connTarget;
/*  598 */         addConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 108, tsEventLog, ev);
/*  599 */         break;
/*      */       case 12:
/*  602 */         tsEventLog = "CONNECTIONUNKNOWNEVENT for " + connTarget;
/*  603 */         addConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 110, tsEventLog, ev);
/*  604 */         break;
/*      */       case 13:
/*  607 */         tsEventLog = "TERMINALCONNECTIONCREATEDEVENT for " + connTarget;
/*  608 */         addTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 116, tsEventLog, ev);
/*  609 */         break;
/*      */       case 14:
/*  612 */         tsEventLog = "TERMINALCONNECTIONACTIVEEVENT for " + connTarget;
/*  613 */         addTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 115, tsEventLog, ev);
/*  614 */         break;
/*      */       case 15:
/*  617 */         tsEventLog = "TERMINALCONNECTIONRINGINGEVENT for " + connTarget;
/*  618 */         addTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 119, tsEventLog, ev);
/*  619 */         break;
/*      */       case 16:
/*  622 */         tsEventLog = "TERMINALCONNECTIONPASSIVEEVENT for " + connTarget;
/*  623 */         addTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 118, tsEventLog, ev);
/*  624 */         break;
/*      */       case 17:
/*  627 */         tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT for " + connTarget;
/*  628 */         addTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 117, tsEventLog, ev);
/*  629 */         break;
/*      */       case 18:
/*  632 */         tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT for " + connTarget;
/*  633 */         addTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 120, tsEventLog, ev);
/*  634 */         break;
/*      */       case 19:
/*  637 */         tsEventLog = "CONNECTIONOFFEREDEVENT for " + connTarget;
/*  638 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 361, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  639 */         break;
/*      */       case 20:
/*  642 */         tsEventLog = "CONNECTIONDIALINGEVENT for " + connTarget;
/*  643 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 354, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  644 */         break;
/*      */       case 21:
/*  647 */         tsEventLog = "CONNECTIONESTABLISHEDEVENT for " + connTarget;
/*  648 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 356, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  649 */         break;
/*      */       case 22:
/*  652 */         tsEventLog = "CONNECTIONNETWORKREACHEDEVENT for " + connTarget;
/*  653 */         addCallControlConnectionNetworkReachedEvent(cause, metaCode, metaEvent, connTarget, privateData, 360, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  654 */         break;
/*      */       case 23:
/*  657 */         tsEventLog = "CONNECTIONNETWORKALERTINGEVENT for " + connTarget;
/*  658 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 359, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  659 */         break;
/*      */       case 24:
/*  662 */         tsEventLog = "CONNECTIONINITIATEDEVENT for " + connTarget;
/*  663 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 358, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  664 */         break;
/*      */       case 25:
/*  667 */         tsEventLog = "CONNECTIONQUEUEDEVENT for " + connTarget;
/*  668 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 362, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  669 */         break;
/*      */       case 26:
/*  672 */         tsEventLog = "CONNECTIONALERTINGEVENT_CC for " + connTarget;
/*  673 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 353, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  674 */         break;
/*      */       case 27:
/*  677 */         tsEventLog = "CONNECTIONDISCONNECTEDEVENT_CC for " + connTarget;
/*  678 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 355, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  679 */         break;
/*      */       case 28:
/*  682 */         tsEventLog = "CONNECTIONFAILEDEVENT_CC for " + connTarget;
/*  683 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 357, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  684 */         break;
/*      */       case 29:
/*  687 */         tsEventLog = "CONNECTIONUNKNOWNEVENT_CC for " + connTarget;
/*  688 */         addCallControlConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 363, connTarget.getTSDevice().getNumberQueued(), tsEventLog, ev);
/*  689 */         break;
/*      */       case 56:
/*  692 */         tsEventLog = "CONNECTIONINPROGRESSEVENT_CC for " + connTarget;
/*  693 */         addCallCenterConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 319, tsEventLog, ev);
/*  694 */         break;
/*      */       case 30:
/*  697 */         tsEventLog = "TERMINALCONNECTIONTALKINGEVENT for " + connTarget;
/*  698 */         addCallControlTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 369, tsEventLog, ev);
/*  699 */         break;
/*      */       case 31:
/*  702 */         tsEventLog = "TERMINALCONNECTIONHELDEVENT for " + connTarget;
/*  703 */         addCallControlTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 366, tsEventLog, ev);
/*  704 */         break;
/*      */       case 32:
/*  707 */         tsEventLog = "TERMINALCONNECTIONBRIDGEDEVENT for " + connTarget;
/*  708 */         addCallControlTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 364, tsEventLog, ev);
/*  709 */         break;
/*      */       case 33:
/*  712 */         tsEventLog = "TERMINALCONNECTIONINUSEEVENT for " + connTarget;
/*  713 */         addCallControlTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 367, tsEventLog, ev);
/*  714 */         break;
/*      */       case 34:
/*  717 */         tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT_CC for " + connTarget;
/*  718 */         addCallControlTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 365, tsEventLog, ev);
/*  719 */         break;
/*      */       case 35:
/*  722 */         tsEventLog = "TERMINALCONNECTIONRINGINGEVENT_CC for " + connTarget;
/*  723 */         addCallControlTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 368, tsEventLog, ev);
/*  724 */         break;
/*      */       case 36:
/*  727 */         tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT_CC for " + connTarget;
/*  728 */         addCallControlTerminalConnectionEvent(cause, metaCode, metaEvent, connTarget, privateData, 370, tsEventLog, ev);
/*  729 */         break;
/*      */       case 54:
/*  732 */         tsEventLog = "TRUNKVALIDEVENT for " + trkTarget;
/*  733 */         addCallCenterEvent(cause, metaCode, metaEvent, trkTarget, privateData, 317, tsEventLog, ev);
/*  734 */         break;
/*      */       case 55:
/*  737 */         tsEventLog = "TRUNKINVALIDEVENT for " + trkTarget;
/*  738 */         addCallCenterEvent(cause, metaCode, metaEvent, trkTarget, privateData, 318, tsEventLog, ev);
/*  739 */         break;
/*      */       case 57:
/*  742 */         String digits = callTarget.getDigits();
/*      */         int i;
/*  743 */         for (i = 0; i < digits.length(); ++i) {
/*  744 */           tsEventLog = "CALLDTMFEVENT for " + callTarget;
/*  745 */           PrivateDataCallEventImpl privEvent = new PrivateDataCallEventImpl(new PrivateDtmfEventImpl(digits.charAt(i)), ev.getEventTarget(), cause, metaEvent);
/*  746 */           checkAndAddPrivateEvent(ev.getEventTarget(), privEvent, metaEvent);
/*      */         }
/*  748 */         break;
/*      */       case 9999:
/*  751 */         params = createCallParams(callTarget, cause, metaCode, ev.getPrivateData(), ev);
/*  752 */         PrivateDataCallEventImpl privEvent = new PrivateDataCallEventImpl(params.getPrivateData(), params.getCall(), cause, metaEvent);
/*  753 */         checkAndAddPrivateEvent(ev.getEventTarget(), privEvent, metaEvent);
/*      */       }
/*      */     }
/*      */ 
/*  757 */     synchronized (this.listenerEventList)
/*      */     {
/*  759 */       if (this.listenerEventList.size() == 0) {
/*  760 */         log.debug("no events to send to " + this.observer);
/*  761 */         return;
/*      */       }
/*      */ 
/*  766 */       if ((nextMetaEventIndex < this.listenerEventList.size()) && (this.metaStartAdded)) {
/*  767 */         this.listenerEventList.add(metaEventPair[1]);
/*  768 */         this.metaStartAdded = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkAndAddPrivateEvent(Object eventTarget, PrivateDataCallEventImpl privateEvent, Event metaEvent)
/*      */   {
/*  780 */     if (this.callListener instanceof PrivateDataCallListener) {
/*  781 */       synchronized (this.listenerEventList) {
/*  782 */         if (!this.metaStartAdded) {
/*  783 */           this.listenerEventList.add(metaEvent);
/*  784 */           this.metaStartAdded = true;
/*      */         }
/*  786 */         this.listenerEventList.add(privateEvent);
/*      */       }
/*  788 */       log.info("PRIVATEEVENT for " + eventTarget + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addCallControlTerminalConnectionEvent(int cause, int metaCode, MetaEvent metaEvent, TSConnection connTarget, Object privateData, int eventId, String tsEventLog, TSEvent ev)
/*      */   {
/*  806 */     if (this.callListener instanceof CallControlTerminalConnectionListener) {
/*  807 */       synchronized (this.listenerEventList) {
/*  808 */         if (!this.metaStartAdded) {
/*  809 */           this.listenerEventList.add(metaEvent);
/*  810 */           this.metaStartAdded = true;
/*      */         }
/*  812 */         TermConnEventParams connEventParams = createTermConnParams(connTarget, cause, metaCode, privateData, ev);
/*  813 */         if (this.provider.isLucentV5()) {
/*  814 */           this.listenerEventList.add(new LucentV5CallControlTerminalConnectionEvent(connEventParams, metaEvent, eventId));
/*      */         }
/*  816 */         else if (this.provider.isLucent()) {
/*  817 */           this.listenerEventList.add(new LucentCallControlTerminalConnectionEvent(connEventParams, metaEvent, eventId));
/*      */         }
/*      */         else {
/*  820 */           this.listenerEventList.add(new CallControlTerminalConnectionEventImpl(connEventParams, metaEvent, eventId));
/*      */         }
/*      */       }
/*  823 */       log.debug(tsEventLog + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addCallControlConnectionEvent(int cause, int metaCode, MetaEvent metaEvent, TSConnection connTarget, Object privateData, int eventId, int numInQueue, String tsEventLog, TSEvent ev)
/*      */   {
/*  842 */     if (this.callListener instanceof CallControlConnectionListener) {
/*  843 */       String digits = connTarget.getCall().getDigits();
/*  844 */       synchronized (this.listenerEventList) {
/*  845 */         if (!this.metaStartAdded) {
/*  846 */           this.listenerEventList.add(metaEvent);
/*  847 */           this.metaStartAdded = true;
/*      */         }
/*  849 */         ConnEventParams connEventParams = createConnParams(connTarget, cause, metaCode, privateData, ev);
/*  850 */         if (this.provider.isLucentV5()) {
/*  851 */           this.listenerEventList.add(new LucentV5CallControlConnectionEvent(connEventParams, metaEvent, eventId, numInQueue, digits));
/*      */         }
/*  853 */         else if (this.provider.isLucent()) {
/*  854 */           this.listenerEventList.add(new LucentCallControlConnectionEvent(connEventParams, metaEvent, eventId, numInQueue, digits));
/*      */         }
/*      */         else {
/*  857 */           this.listenerEventList.add(new CallControlConnectionEventImpl(connEventParams, metaEvent, eventId, numInQueue, digits));
/*      */         }
/*      */       }
/*  860 */       log.debug(tsEventLog + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addCallControlConnectionNetworkReachedEvent(int cause, int metaCode, MetaEvent metaEvent, TSConnection connTarget, Object privateData, int eventId, int numInQueue, String tsEventLog, TSEvent ev)
/*      */   {
/*  867 */     if (this.callListener instanceof CallControlConnectionListener) {
/*  868 */       String digits = connTarget.getCall().getDigits();
/*  869 */       synchronized (this.listenerEventList) {
/*  870 */         if (!this.metaStartAdded) {
/*  871 */           this.listenerEventList.add(metaEvent);
/*  872 */           this.metaStartAdded = true;
/*      */         }
/*  874 */         ConnEventParams connEventParams = createConnParams(connTarget, cause, metaCode, privateData, ev);
/*  875 */         if (this.provider.isLucentV5())
/*  876 */           this.listenerEventList.add(new LucentV5CallControlConnectionNetworkReachedEvent(connEventParams, metaEvent, eventId, numInQueue, digits));
/*  877 */         else if (this.provider.isLucent())
/*  878 */           this.listenerEventList.add(new LucentCallControlConnectionNetworkReachedEvent(connEventParams, metaEvent, eventId, numInQueue, digits));
/*      */         else
/*  880 */           this.listenerEventList.add(new CallControlConnectionNetworkReachedEventImpl(connEventParams, metaEvent, eventId, numInQueue, digits));
/*      */       }
/*  882 */       log.debug(tsEventLog + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addCallCenterConnectionEvent(int cause, int metaCode, MetaEvent metaEvent, TSConnection connTarget, Object privateData, int eventId, String tsEventLog, TSEvent ev)
/*      */   {
/*  900 */     ConnEventParams connEventParams = createConnParams(connTarget, cause, metaCode, privateData, ev);
/*  901 */     if (this.callListener instanceof CallCenterCallListener) {
/*  902 */       synchronized (this.listenerEventList) {
/*  903 */         if (!this.metaStartAdded) {
/*  904 */           this.listenerEventList.add(metaEvent);
/*  905 */           this.metaStartAdded = true;
/*      */         }
/*  907 */         if ((eventId == 319) && (this.callListener instanceof ConnectionListener)) {
/*  908 */           if (this.provider.isLucentV5())
/*  909 */             this.listenerEventList.add(new LucentV5CallCenterConnectionEvent(connEventParams, metaEvent, eventId));
/*  910 */           else if (this.provider.isLucent())
/*  911 */             this.listenerEventList.add(new LucentCallCenterConnectionEvent(connEventParams, metaEvent, eventId));
/*      */           else
/*  913 */             this.listenerEventList.add(new CallCenterConnectionEventImpl(connEventParams, metaEvent, eventId));
/*      */         }
/*  915 */         else if (this.provider.isLucentV5())
/*  916 */           this.listenerEventList.add(new LucentV5CallCenterConnectionEvent(connEventParams, metaEvent, eventId));
/*  917 */         else if (this.provider.isLucent())
/*  918 */           this.listenerEventList.add(new LucentCallCenterConnectionEvent(connEventParams, metaEvent, eventId));
/*      */         else {
/*  920 */           this.listenerEventList.add(new CallCenterConnectionEventImpl(connEventParams, metaEvent, eventId));
/*      */         }
/*      */       }
/*  923 */       log.debug(tsEventLog + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addCallCenterEvent(int cause, int metaCode, MetaEvent metaEvent, TSTrunk trkTarget, Object privateData, int eventId, String tsEventLog, TSEvent ev)
/*      */   {
/*  942 */     if (this.callListener instanceof CallCenterCallListener) {
/*  943 */       synchronized (this.listenerEventList) {
/*  944 */         if (!this.metaStartAdded) {
/*  945 */           this.listenerEventList.add(metaEvent);
/*  946 */           this.metaStartAdded = true;
/*      */         }
/*  948 */         CallEventParams params = createCallParams(trkTarget, cause, metaCode, privateData, ev);
/*  949 */         this.listenerEventList.add(new CallCenterTrunkEventImpl(params, metaEvent, eventId));
/*      */       }
/*  951 */       log.debug(tsEventLog + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addTerminalConnectionEvent(int cause, int metaCode, MetaEvent metaEvent, TSConnection connTarget, Object privateData, int eventId, String tsEventLog, TSEvent ev)
/*      */   {
/*  959 */     if (this.callListener instanceof TerminalConnectionListener) {
/*  960 */       synchronized (this.listenerEventList) {
/*  961 */         if (!this.metaStartAdded) {
/*  962 */           this.listenerEventList.add(metaEvent);
/*  963 */           this.metaStartAdded = true;
/*      */         }
/*  965 */         TermConnEventParams tcEventParams = createTermConnParams(connTarget, cause, metaCode, privateData, ev);
/*  966 */         this.listenerEventList.add(new TerminalConnectionEventImpl(tcEventParams, metaEvent, eventId));
/*      */       }
/*  968 */       log.debug(tsEventLog + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addConnectionEvent(int cause, int metaCode, MetaEvent metaEvent, TSConnection connTarget, Object privateData, int eventId, String tsEventLog, TSEvent ev)
/*      */   {
/*  974 */     if (this.callListener instanceof ConnectionListener) {
/*  975 */       synchronized (this.listenerEventList) {
/*  976 */         if (!this.metaStartAdded) {
/*  977 */           this.listenerEventList.add(metaEvent);
/*  978 */           this.metaStartAdded = true;
/*      */         }
/*  980 */         ConnEventParams connEventParams = createConnParams(connTarget, cause, metaCode, privateData, ev);
/*  981 */         this.listenerEventList.add(new ConnectionEventImpl(connEventParams, metaEvent, eventId));
/*      */       }
/*  983 */       log.debug(tsEventLog + " for listener " + this.callListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void createObserverEvents(Vector<TSEvent> _eventList, int cause, boolean snapshot) {
/*  988 */     String tsEventLog = null;
/*      */     int metaCode;
/*  990 */     if (snapshot)
/*      */     {
/*  992 */       metaCode = 135;
/*      */     }
/*      */     else
/*      */     {
/*  996 */       metaCode = getObserverMetaCode(cause, _eventList);
/*      */     }
/*      */ 
/*  999 */     int nextMetaEventIndex = this.eventList.size();
/*      */ 
/* 1002 */     TSEvent ev = null;
/* 1003 */     TSCall callTarget = null;
/* 1004 */     TSConnection connTarget = null;
/* 1005 */     TSTrunk trkTarget = null;
/* 1006 */     Object privateData = null;
/* 1007 */     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + metaCode + ")" + " for " + this.observer);
/*      */ 
/* 1009 */     for (int i = 0; i < _eventList.size(); ++i) {
/* 1010 */       ev = (TSEvent)_eventList.elementAt(i);
/* 1011 */       if (ev.getEventTarget() instanceof TSCall)
/*      */       {
/* 1013 */         callTarget = (TSCall)ev.getEventTarget();
/*      */       }
/* 1015 */       else if (ev.getEventTarget() instanceof TSTrunk)
/*      */       {
/* 1017 */         trkTarget = (TSTrunk)ev.getEventTarget();
/*      */       }
/*      */       else
/*      */       {
/* 1021 */         connTarget = (TSConnection)ev.getEventTarget();
/*      */       }
/*      */ 
/* 1024 */       privateData = ev.getPrivateData();
/*      */ 
/* 1026 */       switch (ev.getEventType())
/*      */       {
/*      */       case 4:
/* 1029 */         tsEventLog = "CALLACTIVEEVENT for " + callTarget;
/*      */ 
/* 1031 */         addObserverEvent(new TsapiCallActiveEvent(createCallParams(callTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1033 */         break;
/*      */       case 5:
/* 1035 */         tsEventLog = "CALLINVALIDEVENT for " + callTarget;
/*      */ 
/* 1037 */         addObserverEvent(new TsapiCallInvalidEvent(createCallParams(callTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1039 */         break;
/*      */       case 6:
/* 1041 */         tsEventLog = "CONNECTIONCREATEDEVENT for " + connTarget;
/*      */ 
/* 1043 */         addObserverEvent(new TsapiConnCreatedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1045 */         break;
/*      */       case 7:
/* 1047 */         tsEventLog = "CONNECTIONCONNECTEDEVENT for " + connTarget;
/*      */ 
/* 1049 */         addObserverEvent(new TsapiConnConnectedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1051 */         break;
/*      */       case 8:
/* 1053 */         tsEventLog = "CONNECTIONINPROGRESSEVENT for " + connTarget;
/*      */ 
/* 1055 */         addObserverEvent(new TsapiConnInProgressEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1057 */         break;
/*      */       case 9:
/* 1059 */         tsEventLog = "CONNECTIONALERTINGEVENT for " + connTarget;
/*      */ 
/* 1061 */         addObserverEvent(new TsapiConnAlertingEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1063 */         break;
/*      */       case 10:
/* 1065 */         tsEventLog = "CONNECTIONDISCONNECTEDEVENT for " + connTarget;
/*      */ 
/* 1067 */         addObserverEvent(new TsapiConnDisconnectedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1069 */         break;
/*      */       case 11:
/* 1071 */         tsEventLog = "CONNECTIONFAILEDEVENT for " + connTarget;
/*      */ 
/* 1073 */         addObserverEvent(new TsapiConnFailedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1075 */         break;
/*      */       case 12:
/* 1077 */         tsEventLog = "CONNECTIONUNKNOWNEVENT for " + connTarget;
/*      */ 
/* 1079 */         addObserverEvent(new TsapiConnUnknownEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1081 */         break;
/*      */       case 13:
/* 1083 */         tsEventLog = "TERMINALCONNECTIONCREATEDEVENT for " + connTarget;
/*      */ 
/* 1085 */         addObserverEvent(new TsapiTermConnCreatedEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1087 */         break;
/*      */       case 14:
/* 1089 */         tsEventLog = "TERMINALCONNECTIONACTIVEEVENT for " + connTarget;
/*      */ 
/* 1091 */         addObserverEvent(new TsapiTermConnActiveEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1093 */         break;
/*      */       case 15:
/* 1095 */         tsEventLog = "TERMINALCONNECTIONRINGINGEVENT for " + connTarget;
/*      */ 
/* 1097 */         addObserverEvent(new TsapiTermConnRingingEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1099 */         break;
/*      */       case 16:
/* 1101 */         tsEventLog = "TERMINALCONNECTIONPASSIVEEVENT for " + connTarget;
/*      */ 
/* 1103 */         addObserverEvent(new TsapiTermConnPassiveEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1105 */         break;
/*      */       case 17:
/* 1107 */         tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT for " + connTarget;
/*      */ 
/* 1109 */         addObserverEvent(new TsapiTermConnDroppedEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1111 */         break;
/*      */       case 18:
/* 1113 */         tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT for " + connTarget;
/*      */ 
/* 1115 */         addObserverEvent(new TsapiTermConnUnknownEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1117 */         break;
/*      */       case 19:
/* 1119 */         tsEventLog = "CONNECTIONOFFEREDEVENT for " + connTarget;
/*      */ 
/* 1121 */         if (this.provider.isLucentV5())
/*      */         {
/* 1123 */           addObserverEvent(new LucentV5ConnOfferedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1126 */         else if (this.provider.isLucent())
/*      */         {
/* 1128 */           addObserverEvent(new LucentConnOfferedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1133 */           addObserverEvent(new TsapiConnOfferedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1136 */         break;
/*      */       case 20:
/* 1138 */         tsEventLog = "CONNECTIONDIALINGEVENT for " + connTarget;
/*      */ 
/* 1140 */         if (this.provider.isLucentV5())
/*      */         {
/* 1142 */           addObserverEvent(new LucentV5ConnDialingEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1145 */         else if (this.provider.isLucent())
/*      */         {
/* 1147 */           addObserverEvent(new LucentConnDialingEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1152 */           addObserverEvent(new TsapiConnDialingEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1155 */         break;
/*      */       case 21:
/* 1157 */         tsEventLog = "CONNECTIONESTABLISHEDEVENT for " + connTarget;
/*      */ 
/* 1159 */         if (this.provider.isLucentV5())
/*      */         {
/* 1161 */           addObserverEvent(new LucentV5ConnEstablishedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1164 */         else if (this.provider.isLucent())
/*      */         {
/* 1166 */           addObserverEvent(new LucentConnEstablishedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1171 */           addObserverEvent(new TsapiConnEstablishedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1174 */         break;
/*      */       case 22:
/* 1176 */         tsEventLog = "CONNECTIONNETWORKREACHEDEVENT for " + connTarget;
/*      */ 
/* 1178 */         if (this.provider.isLucentV5())
/*      */         {
/* 1180 */           addObserverEvent(new LucentV5ConnNetworkReachedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1183 */         else if (this.provider.isLucent())
/*      */         {
/* 1185 */           addObserverEvent(new LucentConnNetworkReachedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1190 */           addObserverEvent(new TsapiConnNetworkReachedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1193 */         break;
/*      */       case 23:
/* 1195 */         tsEventLog = "CONNECTIONNETWORKALERTINGEVENT for " + connTarget;
/*      */ 
/* 1197 */         if (this.provider.isLucentV5())
/*      */         {
/* 1199 */           addObserverEvent(new LucentV5ConnNetworkAlertingEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1202 */         else if (this.provider.isLucent())
/*      */         {
/* 1204 */           addObserverEvent(new LucentConnNetworkAlertingEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1209 */           addObserverEvent(new TsapiConnNetworkAlertingEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1212 */         break;
/*      */       case 24:
/* 1214 */         tsEventLog = "CONNECTIONINITIATEDEVENT for " + connTarget;
/*      */ 
/* 1216 */         if (this.provider.isLucentV5())
/*      */         {
/* 1218 */           addObserverEvent(new LucentV5ConnInitiatedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1221 */         else if (this.provider.isLucent())
/*      */         {
/* 1223 */           addObserverEvent(new LucentConnInitiatedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1228 */           addObserverEvent(new TsapiConnInitiatedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1231 */         break;
/*      */       case 25:
/* 1233 */         tsEventLog = "CONNECTIONQUEUEDEVENT for " + connTarget;
/*      */ 
/* 1235 */         if (this.provider.isLucentV5())
/*      */         {
/* 1237 */           addObserverEvent(new LucentV5ConnQueuedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev), connTarget.getTSDevice().getNumberQueued()), tsEventLog);
/*      */         }
/* 1241 */         else if (this.provider.isLucent())
/*      */         {
/* 1243 */           addObserverEvent(new LucentConnQueuedEventImpl(createConnParams(connTarget, cause, metaCode, privateData, ev), connTarget.getTSDevice().getNumberQueued()), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1249 */           addObserverEvent(new TsapiConnQueuedEvent(createConnParams(connTarget, cause, metaCode, privateData, ev), connTarget.getTSDevice().getNumberQueued()), tsEventLog);
/*      */         }
/*      */ 
/* 1253 */         break;
/*      */       case 26:
/* 1255 */         tsEventLog = "CONNECTIONALERTINGEVENT_CC for " + connTarget;
/*      */ 
/* 1257 */         if (this.provider.isLucentV5())
/*      */         {
/* 1259 */           addObserverEvent(new LucentV5ConnAlertingEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1262 */         else if (this.provider.isLucent())
/*      */         {
/* 1264 */           addObserverEvent(new LucentConnAlertingEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1269 */           addObserverEvent(new TsapiConnAlertingEventCC(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1272 */         break;
/*      */       case 27:
/* 1274 */         tsEventLog = "CONNECTIONDISCONNECTEDEVENT_CC for " + connTarget;
/*      */ 
/* 1276 */         if (this.provider.isLucentV5())
/*      */         {
/* 1278 */           addObserverEvent(new LucentV5ConnDisconnectedEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1281 */         else if (this.provider.isLucent())
/*      */         {
/* 1283 */           addObserverEvent(new LucentConnDisconnectedEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1288 */           addObserverEvent(new TsapiConnDisconnectedEventCC(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1291 */         break;
/*      */       case 28:
/* 1293 */         tsEventLog = "CONNECTIONFAILEDEVENT_CC for " + connTarget;
/*      */ 
/* 1295 */         if (this.provider.isLucentV5())
/*      */         {
/* 1297 */           addObserverEvent(new LucentV5ConnFailedEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1300 */         else if (this.provider.isLucent())
/*      */         {
/* 1302 */           addObserverEvent(new LucentConnFailedEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1307 */           addObserverEvent(new TsapiConnFailedEventCC(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1310 */         break;
/*      */       case 29:
/* 1312 */         tsEventLog = "CONNECTIONUNKNOWNEVENT_CC for " + connTarget;
/*      */ 
/* 1314 */         if (this.provider.isLucentV5())
/*      */         {
/* 1316 */           addObserverEvent(new LucentV5ConnUnknownEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1319 */         else if (this.provider.isLucent())
/*      */         {
/* 1321 */           addObserverEvent(new LucentConnUnknownEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1326 */           addObserverEvent(new TsapiConnUnknownEventCC(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1329 */         break;
/*      */       case 56:
/* 1331 */         tsEventLog = "CONNECTIONINPROGRESSEVENT_CC for " + connTarget;
/*      */ 
/* 1333 */         if (this.provider.isLucentV5())
/*      */         {
/* 1335 */           addObserverEvent(new LucentV5ConnInProgressEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1338 */         else if (this.provider.isLucent())
/*      */         {
/* 1340 */           addObserverEvent(new LucentConnInProgressEventCCImpl(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           addObserverEvent(new TsapiConnInProgressEventCC(createConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1348 */         break;
/*      */       case 30:
/* 1350 */         tsEventLog = "TERMINALCONNECTIONTALKINGEVENT for " + connTarget;
/*      */ 
/* 1352 */         if (this.provider.isLucentV5())
/*      */         {
/* 1354 */           addObserverEvent(new LucentV5TermConnTalkingEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1357 */         else if (this.provider.isLucent())
/*      */         {
/* 1359 */           addObserverEvent(new LucentTermConnTalkingEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1364 */           addObserverEvent(new TsapiTermConnTalkingEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1367 */         break;
/*      */       case 31:
/* 1369 */         tsEventLog = "TERMINALCONNECTIONHELDEVENT for " + connTarget;
/*      */ 
/* 1371 */         if (this.provider.isLucentV5())
/*      */         {
/* 1373 */           addObserverEvent(new LucentV5TermConnHeldEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1376 */         else if (this.provider.isLucent())
/*      */         {
/* 1378 */           addObserverEvent(new LucentTermConnHeldEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1383 */           addObserverEvent(new TsapiTermConnHeldEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1386 */         break;
/*      */       case 32:
/* 1388 */         tsEventLog = "TERMINALCONNECTIONBRIDGEDEVENT for " + connTarget;
/*      */ 
/* 1390 */         if (this.provider.isLucentV5())
/*      */         {
/* 1392 */           addObserverEvent(new LucentV5TermConnBridgedEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1395 */         else if (this.provider.isLucent())
/*      */         {
/* 1397 */           addObserverEvent(new LucentTermConnBridgedEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1402 */           addObserverEvent(new TsapiTermConnBridgedEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1405 */         break;
/*      */       case 33:
/* 1407 */         tsEventLog = "TERMINALCONNECTIONINUSEEVENT for " + connTarget;
/*      */ 
/* 1409 */         if (this.provider.isLucentV5())
/*      */         {
/* 1411 */           addObserverEvent(new LucentV5TermConnInUseEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1414 */         else if (this.provider.isLucent())
/*      */         {
/* 1416 */           addObserverEvent(new LucentTermConnInUseEventImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1421 */           addObserverEvent(new TsapiTermConnInUseEvent(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1424 */         break;
/*      */       case 34:
/* 1426 */         tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT_CC for " + connTarget;
/*      */ 
/* 1428 */         if (this.provider.isLucentV5())
/*      */         {
/* 1430 */           addObserverEvent(new LucentV5TermConnDroppedEventCCImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1433 */         else if (this.provider.isLucent())
/*      */         {
/* 1435 */           addObserverEvent(new LucentTermConnDroppedEventCCImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1440 */           addObserverEvent(new TsapiTermConnDroppedEventCC(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1443 */         break;
/*      */       case 35:
/* 1445 */         tsEventLog = "TERMINALCONNECTIONRINGINGEVENT_CC for " + connTarget;
/*      */ 
/* 1447 */         if (this.provider.isLucentV5())
/*      */         {
/* 1449 */           addObserverEvent(new LucentV5TermConnRingingEventCCImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1452 */         else if (this.provider.isLucent())
/*      */         {
/* 1454 */           addObserverEvent(new LucentTermConnRingingEventCCImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1459 */           addObserverEvent(new TsapiTermConnRingingEventCC(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1462 */         break;
/*      */       case 36:
/* 1464 */         tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT_CC for " + connTarget;
/*      */ 
/* 1466 */         if (this.provider.isLucentV5())
/*      */         {
/* 1468 */           addObserverEvent(new LucentV5TermConnUnknownEventCCImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/* 1471 */         else if (this.provider.isLucent())
/*      */         {
/* 1473 */           addObserverEvent(new LucentTermConnUnknownEventCCImpl(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */         else
/*      */         {
/* 1478 */           addObserverEvent(new TsapiTermConnUnknownEventCC(createTermConnParams(connTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */         }
/*      */ 
/* 1481 */         break;
/*      */       case 54:
/* 1483 */         tsEventLog = "TRUNKVALIDEVENT for " + trkTarget;
/*      */ 
/* 1485 */         addObserverEvent(new TsapiTrunkValidEv(createCallParams(trkTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1487 */         break;
/*      */       case 55:
/* 1489 */         tsEventLog = "TRUNKINVALIDEVENT for " + trkTarget;
/*      */ 
/* 1491 */         addObserverEvent(new TsapiTrunkInvalidEv(createCallParams(trkTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */ 
/* 1493 */         break;
/*      */       case 57:
/* 1495 */         String digits = callTarget.getDigits();
/* 1496 */         for (int strIndex = 0; strIndex < digits.length(); ++strIndex)
/*      */         {
/* 1498 */           tsEventLog = "CALLDTMFEVENT for " + callTarget;
/*      */ 
/* 1500 */           addObserverEvent(new TsapiTermConnDTMFEvent(createCallParams(callTarget, cause, metaCode, privateData, ev), digits.charAt(strIndex)), tsEventLog);
/*      */         }
/*      */ 
/* 1503 */         break;
/*      */       case 9999:
/* 1505 */         tsEventLog = "PRIVATEEVENT for " + callTarget;
/*      */ 
/* 1507 */         addObserverEvent(new TsapiPrivateCallEvent(createCallParams(callTarget, cause, metaCode, privateData, ev)), tsEventLog);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1512 */     synchronized (this.eventList) {
/* 1513 */       log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
/*      */ 
/* 1516 */       if (this.eventList.size() == 0)
/*      */       {
/* 1518 */         log.debug("no events to send to " + this.observer);
/* 1519 */         return;
/*      */       }
/*      */ 
/* 1523 */       if (nextMetaEventIndex < this.eventList.size())
/*      */       {
/* 1526 */         ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private ConnEventParams createConnParams(TSConnection conn, int cause, int metaCode, Object privateData, ArrayList<TSCall> oldCalls, TSEvent tsEvent)
/*      */   {
/* 1534 */     TSCall call = null;
/* 1535 */     if (conn != null) {
/* 1536 */       call = conn.getCall();
/*      */     }
/*      */ 
/* 1539 */     ConnEventParams params = new ConnEventParams();
/* 1540 */     ArrayList oldCallList = new ArrayList();
/* 1541 */     for (TSCall tsCall : oldCalls)
/* 1542 */       oldCallList.add((Call)TsapiCreateObject.getTsapiObject(tsCall, false));
/* 1543 */     params.setOldCalls(oldCallList);
/*      */ 
/* 1545 */     params.setConnection((Connection)TsapiCreateObject.getTsapiObject(conn, true));
/* 1546 */     return (ConnEventParams)createCallParams(params, call, cause, metaCode, privateData, tsEvent);
/*      */   }
/*      */ 
/*      */   private ConnEventParams createConnParams(TSConnection conn, int cause, int metaCode, Object privateData, TSEvent tsEvent)
/*      */   {
/* 1553 */     TSCall call = null;
/* 1554 */     if (conn != null) {
/* 1555 */       call = conn.getCall();
/*      */     }
/*      */ 
/* 1558 */     ConnEventParams params = new ConnEventParams();
/* 1559 */     params.setConnection((Connection)TsapiCreateObject.getTsapiObject(conn, true));
/* 1560 */     return (ConnEventParams)createCallParams(params, call, cause, metaCode, privateData, tsEvent);
/*      */   }
/*      */ 
/*      */   private TermConnEventParams createTermConnParams(TSConnection conn, int cause, int metaCode, Object privateData, TSEvent tsEvent)
/*      */   {
/* 1567 */     TSCall call = null;
/* 1568 */     if (conn != null) {
/* 1569 */       call = conn.getCall();
/*      */     }
/*      */ 
/* 1572 */     TermConnEventParams params = new TermConnEventParams();
/* 1573 */     params.setTermConn((TerminalConnection)TsapiCreateObject.getTsapiObject(conn, false));
/* 1574 */     return (TermConnEventParams)createCallParams(params, call, cause, metaCode, privateData, tsEvent);
/*      */   }
/*      */ 
/*      */   private CallEventParams createCallParams(TSTrunk trunk, int cause, int metaCode, Object privateData, TSEvent tsEvent)
/*      */   {
/* 1581 */     CallEventParams params = new CallEventParams();
/* 1582 */     params.setTrunk((TsapiTrunk)TsapiCreateObject.getTsapiObject(trunk, false));
/*      */ 
/* 1584 */     TSCall call = trunk.getTSCall();
/* 1585 */     return createCallParams(params, call, cause, metaCode, privateData, tsEvent);
/*      */   }
/*      */ 
/*      */   private CallEventParams createCallParams(TSCall call, int cause, int metaCode, Object privateData, ArrayList<TSCall> oldCalls, TSEvent tsEvent)
/*      */   {
/* 1590 */     CallEventParams callEventParams = new CallEventParams();
/* 1591 */     ArrayList oldCallList = new ArrayList();
/* 1592 */     for (TSCall tsCall : oldCalls)
/* 1593 */       oldCallList.add((Call)TsapiCreateObject.getTsapiObject(tsCall, false));
/* 1594 */     callEventParams.setOldCalls(oldCallList);
/* 1595 */     return createCallParams(callEventParams, call, cause, metaCode, privateData, tsEvent);
/*      */   }
/*      */ 
/*      */   private CallEventParams createCallParams(TSCall call, int cause, int metaCode, Object privateData, TSEvent tsEvent) {
/* 1599 */     return createCallParams(new CallEventParams(), call, cause, metaCode, privateData, tsEvent);
/*      */   }
/*      */ 
/*      */   private CallEventParams createCallParams(CallEventParams params, TSCall call, int cause, int metaCode, Object privateData, TSEvent tsEvent)
/*      */   {
/* 1605 */     params.setCause(cause);
/* 1606 */     params.setMetaCode(metaCode);
/* 1607 */     params.setPrivateData(TsapiPromoter.promotePrivateEvent(this.provider, privateData));
/* 1608 */     if (call == null) {
/* 1609 */       return params;
/*      */     }
/*      */ 
/* 1612 */     TSDevice callingAddress = call.getCallingAddress();
/* 1613 */     if (callingAddress != null) {
/* 1614 */       params.setCallingAddress((Address)TsapiCreateObject.getTsapiObject(callingAddress, true));
/*      */     }
/* 1616 */     TSDevice callingTerminal = call.getCallingTerminal();
/* 1617 */     if (callingTerminal != null) {
/* 1618 */       params.setCallingTerminal((Terminal)TsapiCreateObject.getTsapiObject(callingTerminal, false));
/*      */     }
/* 1620 */     TSDevice calledAddress = call.getCalledDevice();
/* 1621 */     if (calledAddress != null) {
/* 1622 */       params.setCalledAddress((Address)TsapiCreateObject.getTsapiObject(calledAddress, true));
/*      */     }
/* 1624 */     TSDevice lastRedirectionDevice = call.getLastRedirectionDevice();
/* 1625 */     if (lastRedirectionDevice != null) {
/* 1626 */       params.setLastRedirectionAddress((Address)TsapiCreateObject.getTsapiObject(lastRedirectionDevice, true));
/*      */     }
/*      */ 
/* 1629 */     TsapiCall tsapiCall = (TsapiCall)TsapiCreateObject.getTsapiObject(call, false);
/* 1630 */     params.setCall(tsapiCall);
/* 1631 */     if ((tsEvent != null) && (tsEvent.getSnapshotCstaCause() != -1))
/* 1632 */       params.setCstaCause(tsEvent.getSnapshotCstaCause());
/*      */     else {
/* 1634 */       params.setCstaCause(call.getCSTACause());
/*      */     }
/* 1636 */     params.setTrunks(tsapiCall.getTrunks());
/* 1637 */     return params;
/*      */   }
/*      */ 
/*      */   public void run()
/*      */   {
/* 1646 */     TsapiTrace.traceEntry("run[]", this);
/* 1647 */     if (this.observer != null) {
/* 1648 */       synchronized (this.syncObject)
/*      */       {
/* 1650 */         log.debug("TP thread woke up: Locked callChangedEvent, attempting to Lock TsapiCallMonitor for CallObserver " + this.observer);
/*      */ 
/* 1655 */         CallEv[] eventArray = null;
/* 1656 */         synchronized (this) {
/* 1657 */           synchronized (this.eventList)
/*      */           {
/* 1659 */             if (this.eventList.size() == 0) {
/* 1660 */               log.debug("TsapiCallMonitor: events delivered by previous thread; no events to deliver in this thread");
/* 1661 */               TsapiTrace.traceExit("run[]", this);
/* 1662 */               return;
/*      */             }
/* 1664 */             log.debug("TP thread Locked TsapiCallMonitor: removing events, itself for CallObserver " + this.observer);
/*      */ 
/* 1669 */             eventArray = new CallEv[this.eventList.size()];
/* 1670 */             this.eventList.copyInto(eventArray);
/* 1671 */             this.eventList.clear();
/*      */           }
/*      */         }
/* 1674 */         log.debug("TP thread Unlocked TsapiCallMonitor: calling callChangedEvent, delivering events for CallObserver " + this.observer);
/*      */         try
/*      */         {
/* 1682 */           this.observer.callChangedEvent(eventArray);
/*      */         }
/*      */         catch (Exception e) {
/* 1685 */           log.error("EXCEPTION thrown by callChangedEvent in " + this.observer + " - " + e.getMessage(), e);
/*      */         }
/*      */ 
/* 1689 */         log.debug("TP thread returned from callChangedEvent, Unlocked callChangedEvent for CallObserver " + this.observer);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1695 */       deliverListenerEvents();
/*      */     }
/* 1697 */     TsapiTrace.traceExit("run[]", this);
/*      */   }
/*      */ 
/*      */   private void deliverListenerEvents()
/*      */   {
/* 1702 */     synchronized (this.listenerEventList) {
/* 1703 */       for (Event event : this.listenerEventList) {
/* 1704 */         deliverEventToCallback(event);
/*      */       }
/* 1706 */       this.listenerEventList.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deliverEventToCallback(Event callEvent)
/*      */   {
/* 1715 */     switch (callEvent.getID())
/*      */     {
/*      */     case 101:
/* 1717 */       this.callListener.callActive((CallEvent)callEvent);
/* 1718 */       break;
/*      */     case 102:
/* 1721 */       this.callListener.callInvalid((CallEvent)callEvent);
/* 1722 */       break;
/*      */     case 106:
/* 1725 */       ((ConnectionListener)this.callListener).connectionCreated((ConnectionEvent)callEvent);
/* 1726 */       break;
/*      */     case 105:
/* 1729 */       ((ConnectionListener)this.callListener).connectionConnected((ConnectionEvent)callEvent);
/* 1730 */       break;
/*      */     case 109:
/* 1733 */       ((ConnectionListener)this.callListener).connectionInProgress((ConnectionEvent)callEvent);
/* 1734 */       break;
/*      */     case 104:
/* 1737 */       ((ConnectionListener)this.callListener).connectionAlerting((ConnectionEvent)callEvent);
/* 1738 */       break;
/*      */     case 107:
/* 1741 */       ((ConnectionListener)this.callListener).connectionDisconnected((ConnectionEvent)callEvent);
/* 1742 */       break;
/*      */     case 108:
/* 1745 */       ((ConnectionListener)this.callListener).connectionFailed((ConnectionEvent)callEvent);
/* 1746 */       break;
/*      */     case 110:
/* 1749 */       ((ConnectionListener)this.callListener).connectionUnknown((ConnectionEvent)callEvent);
/* 1750 */       break;
/*      */     case 116:
/* 1753 */       ((TerminalConnectionListener)this.callListener).terminalConnectionCreated((TerminalConnectionEvent)callEvent);
/* 1754 */       break;
/*      */     case 115:
/* 1757 */       ((TerminalConnectionListener)this.callListener).terminalConnectionActive((TerminalConnectionEvent)callEvent);
/* 1758 */       break;
/*      */     case 119:
/* 1761 */       ((TerminalConnectionListener)this.callListener).terminalConnectionRinging((TerminalConnectionEvent)callEvent);
/* 1762 */       break;
/*      */     case 118:
/* 1765 */       ((TerminalConnectionListener)this.callListener).terminalConnectionPassive((TerminalConnectionEvent)callEvent);
/* 1766 */       break;
/*      */     case 117:
/* 1769 */       ((TerminalConnectionListener)this.callListener).terminalConnectionDropped((TerminalConnectionEvent)callEvent);
/* 1770 */       break;
/*      */     case 120:
/* 1773 */       ((TerminalConnectionListener)this.callListener).terminalConnectionUnknown((TerminalConnectionEvent)callEvent);
/* 1774 */       break;
/*      */     case 361:
/* 1777 */       ((CallControlConnectionListener)this.callListener).connectionOffered((CallControlConnectionEvent)callEvent);
/* 1778 */       break;
/*      */     case 354:
/* 1781 */       ((CallControlConnectionListener)this.callListener).connectionDialing((CallControlConnectionEvent)callEvent);
/* 1782 */       break;
/*      */     case 356:
/* 1785 */       ((CallControlConnectionListener)this.callListener).connectionEstablished((CallControlConnectionEvent)callEvent);
/* 1786 */       break;
/*      */     case 360:
/* 1789 */       ((CallControlConnectionListener)this.callListener).connectionNetworkReached((CallControlConnectionEvent)callEvent);
/* 1790 */       break;
/*      */     case 359:
/* 1793 */       ((CallControlConnectionListener)this.callListener).connectionNetworkAlerting((CallControlConnectionEvent)callEvent);
/* 1794 */       break;
/*      */     case 358:
/* 1797 */       ((CallControlConnectionListener)this.callListener).connectionInitiated((CallControlConnectionEvent)callEvent);
/* 1798 */       break;
/*      */     case 362:
/* 1801 */       ((CallControlConnectionListener)this.callListener).connectionQueued((CallControlConnectionEvent)callEvent);
/* 1802 */       break;
/*      */     case 353:
/* 1805 */       ((CallControlConnectionListener)this.callListener).connectionAlerting((CallControlConnectionEvent)callEvent);
/* 1806 */       break;
/*      */     case 355:
/* 1809 */       ((CallControlConnectionListener)this.callListener).connectionDisconnected((CallControlConnectionEvent)callEvent);
/* 1810 */       break;
/*      */     case 357:
/* 1813 */       ((CallControlConnectionListener)this.callListener).connectionFailed((CallControlConnectionEvent)callEvent);
/* 1814 */       break;
/*      */     case 363:
/* 1817 */       ((CallControlConnectionListener)this.callListener).connectionUnknown((CallControlConnectionEvent)callEvent);
/* 1818 */       break;
/*      */     case 319:
/* 1821 */       ((ConnectionListener)this.callListener).connectionInProgress((ConnectionEvent)callEvent);
/* 1822 */       break;
/*      */     case 369:
/* 1825 */       ((CallControlTerminalConnectionListener)this.callListener).terminalConnectionTalking((CallControlTerminalConnectionEvent)callEvent);
/* 1826 */       break;
/*      */     case 366:
/* 1829 */       ((CallControlTerminalConnectionListener)this.callListener).terminalConnectionHeld((CallControlTerminalConnectionEvent)callEvent);
/* 1830 */       break;
/*      */     case 364:
/* 1833 */       ((CallControlTerminalConnectionListener)this.callListener).terminalConnectionBridged((CallControlTerminalConnectionEvent)callEvent);
/* 1834 */       break;
/*      */     case 367:
/* 1837 */       ((CallControlTerminalConnectionListener)this.callListener).terminalConnectionInUse((CallControlTerminalConnectionEvent)callEvent);
/* 1838 */       break;
/*      */     case 365:
/* 1841 */       ((CallControlTerminalConnectionListener)this.callListener).terminalConnectionDropped((CallControlTerminalConnectionEvent)callEvent);
/* 1842 */       break;
/*      */     case 368:
/* 1845 */       ((CallControlTerminalConnectionListener)this.callListener).terminalConnectionRinging((CallControlTerminalConnectionEvent)callEvent);
/* 1846 */       break;
/*      */     case 370:
/* 1849 */       ((CallControlTerminalConnectionListener)this.callListener).terminalConnectionUnknown((CallControlTerminalConnectionEvent)callEvent);
/* 1850 */       break;
/*      */     case 318:
/* 1853 */       ((CallCenterCallListener)this.callListener).trunkInvalid((CallCenterTrunkEvent)callEvent);
/* 1854 */       break;
/*      */     case 317:
/* 1857 */       ((CallCenterCallListener)this.callListener).trunkValid((CallCenterTrunkEvent)callEvent);
/* 1858 */       break;
/*      */     case 601:
/* 1861 */       ((PrivateDataCallListener)this.callListener).callPrivateData((PrivateDataEvent)callEvent);
/* 1862 */       break;
/*      */     case 210:
/* 1865 */       this.callListener.singleCallMetaProgressStarted((MetaEvent)callEvent);
/* 1866 */       break;
/*      */     case 211:
/* 1869 */       this.callListener.singleCallMetaProgressEnded((MetaEvent)callEvent);
/* 1870 */       break;
/*      */     case 212:
/* 1873 */       this.callListener.singleCallMetaSnapshotStarted((MetaEvent)callEvent);
/* 1874 */       break;
/*      */     case 213:
/* 1877 */       this.callListener.singleCallMetaSnapshotEnded((MetaEvent)callEvent);
/* 1878 */       break;
/*      */     case 200:
/* 1881 */       this.callListener.multiCallMetaMergeStarted((MetaEvent)callEvent);
/* 1882 */       break;
/*      */     case 201:
/* 1885 */       this.callListener.multiCallMetaMergeEnded((MetaEvent)callEvent);
/* 1886 */       break;
/*      */     case 202:
/* 1889 */       this.callListener.multiCallMetaTransferStarted((MetaEvent)callEvent);
/* 1890 */       break;
/*      */     case 203:
/* 1893 */       this.callListener.multiCallMetaTransferEnded((MetaEvent)callEvent);
/* 1894 */       break;
/*      */     case 103:
/* 1897 */       this.callListener.callEventTransmissionEnded((CallEvent)callEvent);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor
 * JD-Core Version:    0.5.4
 */