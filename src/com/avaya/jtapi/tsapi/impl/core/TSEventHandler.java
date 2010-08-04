/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTACallClearedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConferencedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnection;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionClearedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTADeliveredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTADivertedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTADoNotDisturbEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEstablishedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAFailedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAForwardingEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAHeldEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTALoggedOffEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTALoggedOnEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMessageWaitingEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorEndedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTANetworkReachedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTANotReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAOriginatedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueuedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAReRouteRequest;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARequest;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARetrievedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteEndEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterAbortEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRequestEv;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteRequestExtEv;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteUsedEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTARouteUsedExtEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAServiceInitiatedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASysStatEventReport;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTATransferredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTATrunkInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAUnsolicited;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAWorkNotReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAWorkReadyEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentAgentModeChangeEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentCallClearedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentChargeAdvice;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentConferencedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentConnectionClearedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentDeliveredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentDivertedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentEnteredDigitsEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentEstablishedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentFailedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentLoggedOffEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentLoggedOnEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentOriginatedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginResp;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryMwiConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueuedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentRouteRequestEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentServiceInitiatedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentTrunkConnectionMapping;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5ConferencedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5DeliveredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5EstablishedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5NetworkProgressInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5RouteRequestEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5TransferredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7ConferencedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7ConnectionClearedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7DeliveredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7EstablishedEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7NetworkProgressInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7RouteRequestEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV7TransferredEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV8FailedEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.LucentACDAddressImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.LucentCallImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.LucentTerminalImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.LucentTrunkInfoMapItem;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiAddress;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiTrunkImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiUnsolicitedHandler;
/*      */ import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
/*      */ import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
import org.apache.log4j.Logger;
/*      */ 
/*      */ final class TSEventHandler
/*      */   implements TsapiUnsolicitedHandler
/*      */ {
/*  129 */   private static Logger log = Logger.getLogger(TSEventHandler.class);
/*      */   TSProviderImpl provider;
/* 1242 */   final short EVAL_UNLIKELY = 1;
/* 1243 */   final short EVAL_POSSIBLE = 2;
/* 1244 */   final short EVAL_LIKELY = 3;
/*      */ 
/*      */   public void acsUnsolicited(CSTAEvent event)
/*      */   {
/*  135 */     Object privateData = event.getPrivData();
/*      */ 
/*  137 */     switch (event.getEventHeader().getEventType())
/*      */     {
/*      */     case 7:
/*  141 */       log.info("Handling ACS_UNIVERSAL_FAILURE event " + event + " for " + this.provider);
/*      */ 
/*  144 */       this.provider.shutdown(privateData);
/*      */ 
/*  146 */       log.info("DONE handling ACS_UNIVERSAL_FAILURE for " + this.provider);
/*  147 */       break;
/*      */     case 16:
/*  150 */       log.info("Handling ACS_CLIENT_HEARTBEAT event for " + this.provider);
/*      */ 
/*  154 */       if (!this.provider.heartbeatIsEnabled())
/*      */       {
/*  156 */         this.provider.enableHeartbeat();
/*      */       }
/*      */ 
/*  175 */       log.info("DONE handling ACS_CLIENT_HEARTBEAT event for " + this.provider);
/*  176 */       break;
/*      */     default:
/*  179 */       log.info("WARNING: event " + event.getEventHeader().getEventType() + " not implemented");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cstaUnsolicited(CSTAEvent event)
/*      */   {
/*  187 */     Runtime rt = Runtime.getRuntime();
/*      */ 
/*  189 */     log.info("CSTA Unsolicited Event: " + event);
/*  190 */     log.info("Free memory: " + rt.freeMemory());
/*  191 */     log.info("Total memory: " + rt.totalMemory());
/*      */ 
/*  193 */     CSTAUnsolicited cstaEvent = (CSTAUnsolicited)event.getEvent();
/*      */ 
/*  196 */     Object privateData = event.getPrivData();
/*      */ 
/*  199 */     Object monitored = this.provider.getMonitoredObject(cstaEvent.getMonitorCrossRefID());
/*  200 */     if (monitored == null)
/*      */     {
/*  202 */       if (event.getEventHeader().getEventType() != 54)
/*      */       {
/*  204 */         log.info("CSTA event " + event + "(" + event.getEventHeader().getEventType() + ") ignored for " + this.provider);
/*  205 */         return;
/*      */       }
/*      */ 
/*  214 */       CSTACallClearedEvent callCleared = (CSTACallClearedEvent)cstaEvent;
/*  215 */       TSCall call = this.provider.createCall(callCleared.getClearedCall().getCallID());
/*  216 */       if (call != null)
/*      */       {
/*  218 */         call.getTSProviderImpl().addMonitor(cstaEvent.getMonitorCrossRefID(), call);
/*      */       }
/*      */ 
/*  221 */       monitored = call;
/*      */     }
/*      */ 
/*  227 */     switch (event.getEventHeader().getEventType())
/*      */     {
/*      */     case 66:
/*  230 */       log.info("Handling CSTA_SERVICE_INITIATED for " + this.provider);
/*  231 */       CSTAServiceInitiatedEvent initiated = (CSTAServiceInitiatedEvent)cstaEvent;
/*  232 */       doConnEvents(66, monitored, initiated.getCause(), null, initiated.getInitiatedConnection(), 84, 98, privateData, null, null, null, true, 0);
/*      */ 
/*  236 */       log.info("DONE handling CSTA_SERVICE_INITIATED for " + this.provider);
/*  237 */       break;
/*      */     case 63:
/*  239 */       log.info("Handling CSTA_ORIGINATED for " + this.provider);
/*  240 */       CSTAOriginatedEvent originated = (CSTAOriginatedEvent)cstaEvent;
/*  241 */       doConnEvents(63, monitored, originated.getCause(), originated.getCallingDevice(), originated.getOriginatedConnection(), 88, 98, privateData, originated.getCallingDevice(), originated.getCalledDevice(), null, true, 0);
/*      */ 
/*  245 */       log.info("DONE handling CSTA_ORIGINATED for " + this.provider);
/*  246 */       break;
/*      */     case 57:
/*  248 */       log.info("Handling CSTA_DELIVERED for " + this.provider);
/*  249 */       CSTADeliveredEvent delivered = (CSTADeliveredEvent)cstaEvent;
/*  250 */       doConnEvents(57, monitored, delivered.getCause(), delivered.getAlertingDevice(), delivered.getConnection(), 83, 97, privateData, delivered.getCallingDevice(), delivered.getCalledDevice(), delivered.getLastRedirectionDevice(), false, 0);
/*      */ 
/*  254 */       log.info("DONE handling CSTA_DELIVERED for " + this.provider);
/*  255 */       break;
/*      */     case 59:
/*  257 */       log.info("Handling CSTA_ESTABLISHED for " + this.provider);
/*  258 */       CSTAEstablishedEvent established = (CSTAEstablishedEvent)cstaEvent;
/*  259 */       doConnEvents(59, monitored, established.getCause(), established.getAnsweringDevice(), established.getEstablishedConnection(), 88, 98, privateData, established.getCallingDevice(), established.getCalledDevice(), established.getLastRedirectionDevice(), false, 0);
/*      */ 
/*  263 */       log.info("DONE handling CSTA_ESTABLISHED for " + this.provider);
/*  264 */       break;
/*      */     case 62:
/*  266 */       log.info("Handling CSTA_NETWORK_REACHED for " + this.provider);
/*  267 */       CSTANetworkReachedEvent networkReached = (CSTANetworkReachedEvent)cstaEvent;
/*  268 */       doConnEvents(62, monitored, networkReached.getCause(), networkReached.getTrunkUsed(), networkReached.getConnection(), 86, 98, privateData, null, networkReached.getCalledDevice(), null, false, 0);
/*      */ 
/*  272 */       log.info("DONE handling CSTA_NETWORK_REACHED for " + this.provider);
/*  273 */       break;
/*      */     case 65:
/*  275 */       log.info("Handling CSTA_RETRIEVED for " + this.provider);
/*  276 */       CSTARetrievedEvent retrieved = (CSTARetrievedEvent)cstaEvent;
/*  277 */       doConnEvents(65, monitored, retrieved.getCause(), retrieved.getRetrievingDevice(), retrieved.getRetrievedConnection(), 88, 98, privateData, null, null, null, false, 0);
/*      */ 
/*  281 */       log.info("DONE handling CSTA_RETRIEVED for " + this.provider);
/*  282 */       break;
/*      */     case 61:
/*  284 */       log.info("Handling CSTA_HELD for " + this.provider);
/*  285 */       CSTAHeldEvent held = (CSTAHeldEvent)cstaEvent;
/*  286 */       doConnEvents(61, monitored, held.getCause(), held.getHoldingDevice(), held.getHeldConnection(), 88, 99, privateData, null, null, null, false, 0);
/*      */ 
/*  290 */       log.info("DONE handling CSTA_HELD for " + this.provider);
/*  291 */       break;
/*      */     case 56:
/*  293 */       log.info("Handling CSTA_CONNECTION_CLEARED for " + this.provider);
/*  294 */       CSTAConnectionClearedEvent connCleared = (CSTAConnectionClearedEvent)cstaEvent;
/*  295 */       doConnEvents(56, monitored, connCleared.getCause(), connCleared.getReleasingDevice(), connCleared.getDroppedConnection(), 89, 102, privateData, null, null, null, false, 0);
/*      */ 
/*  299 */       log.info("DONE handling CSTA_CONNECTION_CLEARED for " + this.provider);
/*  300 */       break;
/*      */     case 60:
/*  302 */       log.info("Handling CSTA_FAILED for " + this.provider);
/*  303 */       CSTAFailedEvent failed = (CSTAFailedEvent)cstaEvent;
/*  304 */       doConnEvents(60, monitored, failed.getCause(), failed.getFailingDevice(), failed.getFailedConnection(), 90, 102, privateData, null, failed.getCalledDevice(), null, false, 0);
/*      */ 
/*  308 */       log.info("DONE handling CSTA_FAILED for " + this.provider);
/*  309 */       break;
/*      */     case 119:
/*  311 */       log.info("Handling CSTA_MONITOR_ENDED for " + this.provider);
/*  312 */       CSTAMonitorEndedEvent monitorEnded = (CSTAMonitorEndedEvent)cstaEvent;
/*  313 */       doMonitorEnded(monitorEnded.getCause(), cstaEvent.getMonitorCrossRefID(), monitored, privateData);
/*  314 */       log.info("DONE handling CSTA_MONITOR_ENDED for " + this.provider);
/*  315 */       break;
/*      */     case 64:
/*  317 */       log.info("Handling CSTA_QUEUED for " + this.provider);
/*  318 */       CSTAQueuedEvent queued = (CSTAQueuedEvent)cstaEvent;
/*  319 */       doConnEvents(64, monitored, queued.getCause(), queued.getQueueingDevice(), queued.getQueuedConnection(), 82, -1, privateData, queued.getCallingDevice(), queued.getCalledDevice(), queued.getLastRedirectionDevice(), false, queued.getNumberQueued());
/*      */ 
/*  323 */       log.info("DONE handling CSTA_QUEUED for " + this.provider);
/*  324 */       break;
/*      */     case 58:
/*  326 */       log.info("Handling CSTA_DIVERTED for " + this.provider);
/*  327 */       CSTADivertedEvent diverted = (CSTADivertedEvent)cstaEvent;
/*      */ 
/*  331 */       if ((Tsapi.isPatch_enable_PreserveRedirectedVDNAsUNKNOWN()) && (this.provider.getDeviceExt(diverted.getDivertingDevice().getDeviceID()) == 1))
/*      */       {
/*  334 */         log.info("Preserving redirecting VDN party of CSTA_DIVERTED for " + this.provider);
/*      */ 
/*  336 */         doConnEvents(58, monitored, diverted.getCause(), diverted.getDivertingDevice(), diverted.getConnection(), 91, 103, privateData, null, diverted.getNewDestination(), null, false, 0);
/*      */       }
/*      */       else
/*      */       {
/*  341 */         log.info("Dropping party due to handling of CSTA_DIVERTED for " + this.provider);
/*      */ 
/*  343 */         doConnEvents(58, monitored, diverted.getCause(), diverted.getDivertingDevice(), diverted.getConnection(), 89, 102, privateData, null, diverted.getNewDestination(), null, false, 0);
/*      */       }
/*      */ 
/*  348 */       log.info("DONE handling CSTA_DIVERTED for " + this.provider);
/*  349 */       break;
/*      */     case 55:
/*  351 */       log.info("Handling CSTA_CONFERENCED for " + this.provider);
/*  352 */       CSTAConferencedEvent conferenced = (CSTAConferencedEvent)cstaEvent;
/*  353 */       doConfXfer(207, conferenced.getPrimaryOldCall(), conferenced.getSecondaryOldCall(), conferenced.getConferenceConnections(), privateData, conferenced.getCause());
/*      */ 
/*  356 */       log.info("DONE handling CSTA_CONFERENCED for " + this.provider);
/*  357 */       break;
/*      */     case 67:
/*  359 */       log.info("Handling CSTA_TRANSFERRED for " + this.provider);
/*  360 */       CSTATransferredEvent transferred = (CSTATransferredEvent)cstaEvent;
/*  361 */       doConfXfer(212, transferred.getPrimaryOldCall(), transferred.getSecondaryOldCall(), transferred.getTransferredConnections(), privateData, transferred.getCause());
/*      */ 
/*  364 */       log.info("DONE handling CSTA_TRANSFERRED for " + this.provider);
/*  365 */       break;
/*      */     case 54:
/*  367 */       log.info("Handling CSTA_CALL_CLEARED for " + this.provider);
/*  368 */       CSTACallClearedEvent callCleared = (CSTACallClearedEvent)cstaEvent;
/*  369 */       doCallEvents(54, monitored, callCleared.getCause(), callCleared.getClearedCall(), 34, privateData);
/*      */ 
/*  371 */       log.info("DONE handling CSTA_CALL_CLEARED for " + this.provider);
/*  372 */       break;
/*      */     case 72:
/*  374 */       log.info("Handling CSTA_LOGGED_ON for " + this.provider);
/*  375 */       CSTALoggedOnEvent loggedOn = (CSTALoggedOnEvent)cstaEvent;
/*  376 */       doAgentEvents(72, monitored, loggedOn.getAgentDevice(), loggedOn.getAgentID(), loggedOn.getAgentGroup(), loggedOn.getPassword(), 1, privateData);
/*      */ 
/*  378 */       log.info("DONE handling CSTA_LOGGED_ON for " + this.provider);
/*  379 */       break;
/*      */     case 73:
/*  381 */       log.info("Handling CSTA_LOGGED_OFF for " + this.provider);
/*  382 */       CSTALoggedOffEvent loggedOff = (CSTALoggedOffEvent)cstaEvent;
/*  383 */       doAgentEvents(73, monitored, loggedOff.getAgentDevice(), loggedOff.getAgentID(), loggedOff.getAgentGroup(), null, 2, privateData);
/*      */ 
/*  385 */       log.info("DONE handling CSTA_LOGGED_OFF for " + this.provider);
/*  386 */       break;
/*      */     case 74:
/*  388 */       log.info("Handling CSTA_NOT_READY for " + this.provider);
/*  389 */       CSTANotReadyEvent notReady = (CSTANotReadyEvent)cstaEvent;
/*  390 */       doAgentEvents(74, monitored, notReady.getAgentDevice(), notReady.getAgentID(), null, null, 3, privateData);
/*      */ 
/*  392 */       log.info("DONE handling CSTA_NOT_READY for " + this.provider);
/*  393 */       break;
/*      */     case 75:
/*  395 */       log.info("Handling CSTA_READY for " + this.provider);
/*  396 */       CSTAReadyEvent ready = (CSTAReadyEvent)cstaEvent;
/*  397 */       doAgentEvents(75, monitored, ready.getAgentDevice(), ready.getAgentID(), null, null, 4, privateData);
/*      */ 
/*  399 */       log.info("DONE handling CSTA_READY for " + this.provider);
/*  400 */       break;
/*      */     case 76:
/*  402 */       log.info("Handling CSTA_WORK_NOT_READY for " + this.provider);
/*  403 */       CSTAWorkNotReadyEvent workNotReady = (CSTAWorkNotReadyEvent)cstaEvent;
/*  404 */       doAgentEvents(76, monitored, workNotReady.getAgentDevice(), workNotReady.getAgentID(), null, null, 5, privateData);
/*      */ 
/*  406 */       log.info("DONE handling CSTA_WORK_NOT_READY for " + this.provider);
/*  407 */       break;
/*      */     case 77:
/*  409 */       log.info("Handling CSTA_WORK_READY for " + this.provider);
/*  410 */       CSTAWorkReadyEvent workReady = (CSTAWorkReadyEvent)cstaEvent;
/*  411 */       doAgentEvents(77, monitored, workReady.getAgentDevice(), workReady.getAgentID(), null, null, 6, privateData);
/*      */ 
/*  413 */       log.info("DONE handling CSTA_WORK_READY for " + this.provider);
/*  414 */       break;
/*      */     case 69:
/*  416 */       log.info("Handling CSTA_DO_NOT_DISTURB for " + this.provider);
/*  417 */       CSTADoNotDisturbEvent doNotDisturb = (CSTADoNotDisturbEvent)cstaEvent;
/*      */ 
/*  419 */       doDeviceEvents(69, monitored, doNotDisturb.getDevice(), doNotDisturb.isDoNotDisturbOn(), null, privateData);
/*      */ 
/*  421 */       log.info("DONE handling CSTA_DO_NOT_DISTURB for " + this.provider);
/*  422 */       break;
/*      */     case 71:
/*  424 */       log.info("Handling CSTA_MESSAGE_WAITING for " + this.provider);
/*  425 */       CSTAMessageWaitingEvent messageWaiting = (CSTAMessageWaitingEvent)cstaEvent;
/*      */ 
/*  427 */       doDeviceEvents(71, monitored, messageWaiting.getDeviceForMessage(), messageWaiting.isMessageWaitingOn(), null, privateData);
/*      */ 
/*  429 */       log.info("DONE handling CSTA_MESSAGE_WAITING for " + this.provider);
/*  430 */       break;
/*      */     case 70:
/*  432 */       log.info("Handling CSTA_FORWARDING for " + this.provider);
/*  433 */       CSTAForwardingEvent forwarding = (CSTAForwardingEvent)cstaEvent;
/*      */ 
/*  435 */       doDeviceEvents(70, monitored, forwarding.getDevice(), false, forwarding.getForwardingInformation(), privateData);
/*      */ 
/*  437 */       log.info("DONE handling CSTA_FORWARDING for " + this.provider);
/*  438 */       break;
/*      */     case 94:
/*  440 */       log.info("Handling CSTA_PRIVATE_STATUS for " + this.provider);
/*  441 */       if ((monitored instanceof TSCall) || (privateData instanceof LucentEnteredDigitsEvent) || (privateData instanceof LucentChargeAdvice))
/*      */       {
/*  445 */         CSTAConnectionID connID = null;
/*      */ 
/*  447 */         if (privateData instanceof LucentEnteredDigitsEvent)
/*      */         {
/*  449 */           connID = ((LucentEnteredDigitsEvent)privateData).getConnection_asn();
/*      */         }
/*  451 */         else if (privateData instanceof LucentChargeAdvice)
/*      */         {
/*  453 */           connID = ((LucentChargeAdvice)privateData).getConnection_asn();
/*      */         }
/*  455 */         doCallEvents(94, monitored, (short) 0, connID, 0, privateData);
/*      */       }
/*      */       else
/*      */       {
/*  460 */         doDeviceEvents(94, monitored, null, false, null, privateData);
/*      */       }
/*      */ 
/*  463 */       log.info("DONE handling CSTA_PRIVATE_STATUS for " + this.provider);
/*  464 */       break;
/*      */     case 68:
/*      */     case 78:
/*      */     case 79:
/*      */     case 80:
/*      */     case 81:
/*      */     case 82:
/*      */     case 83:
/*      */     case 84:
/*      */     case 85:
/*      */     case 86:
/*      */     case 87:
/*      */     case 88:
/*      */     case 89:
/*      */     case 90:
/*      */     case 91:
/*      */     case 92:
/*      */     case 93:
/*      */     case 95:
/*      */     case 96:
/*      */     case 97:
/*      */     case 98:
/*      */     case 99:
/*      */     case 100:
/*      */     case 101:
/*      */     case 102:
/*      */     case 103:
/*      */     case 104:
/*      */     case 105:
/*      */     case 106:
/*      */     case 107:
/*      */     case 108:
/*      */     case 109:
/*      */     case 110:
/*      */     case 111:
/*      */     case 112:
/*      */     case 113:
/*      */     case 114:
/*      */     case 115:
/*      */     case 116:
/*      */     case 117:
/*      */     case 118:
/*      */     default:
/*  466 */       log.info("WARNING: event " + event.getEventHeader().getEventType() + " not implemented");
/*      */     }
/*      */ 
/*  470 */     if (!JTAPILoggingAdapter.isPerformanceLoggingEnabled())
/*      */       return;
/*  472 */     log.debug("updating statistics collector with unsolicited handling time for this iteration");
/*  473 */     PerfStatisticsCollector.updateUnsolicitedHandlingTime(System.currentTimeMillis() - event.getQueuedTimeStamp());
/*      */   }
/*      */ 
/*      */   public void cstaRequest(CSTAEvent event)
/*      */   {
/*  479 */     TSDevice tsDevice = null;
/*  480 */     TsapiRouteMonitor tsRouteCallback = null;
/*  481 */     TSRouteSession tsRouteSession = null;
/*  482 */     CSTARequest cstaEvent = (CSTARequest)event.getEvent();
/*  483 */     Object privateData = event.getPrivData();
/*      */ 
/*  486 */     switch (event.getEventHeader().getEventType())
/*      */     {
/*      */     case 130:
/*  489 */       log.info("Handling CSTA_ROUTE_REQUEST for " + this.provider);
/*  490 */       CSTARouteRequestExtEv routeRequestExt = (CSTARouteRequestExtEv)cstaEvent;
/*      */ 
/*  492 */       tsDevice = (TSDevice)this.provider.routeRegHash.get(new Integer(routeRequestExt.getRouteRegisterReqID()));
/*  493 */       if (tsDevice != null)
/*      */       {
/*  495 */         tsRouteCallback = tsDevice.getTSRouteCallback();
/*  496 */         if (tsRouteCallback != null)
/*      */         {
/*  498 */           TSDevice currentRouteDevice = null;
/*  499 */           TSDevice callingDevice = null;
/*  500 */           TSCall call = null;
/*  501 */           currentRouteDevice = this.provider.createDevice(routeRequestExt.getCurrentRoute());
/*  502 */           callingDevice = this.provider.createDevice(routeRequestExt.getCallingDevice());
/*  503 */           call = this.provider.createCall(routeRequestExt.getRoutedCall().getCallID(), privateData);
/*      */ 
/*  505 */           tsRouteSession = new TSRouteSession(this.provider, tsDevice, routeRequestExt.getRoutingCrossRefID(), currentRouteDevice, callingDevice, call, routeRequestExt.getRoutedSelAlgorithm(), null);
/*      */ 
/*  511 */           if (privateData instanceof LucentRouteRequestEvent)
/*      */           {
/*  513 */             LucentRouteRequestEvent luPrivData = (LucentRouteRequestEvent)privateData;
/*  514 */             tsRouteSession.setUUI(luPrivData.getUserInfo());
/*  515 */             tsRouteSession.setLAI(luPrivData.getLookaheadInfo());
/*  516 */             tsRouteSession.setUEC(luPrivData.getUserEnteredCode());
/*  517 */             TsapiTrunkImpl tsapiTrunk = TsapiPromoter.promoteTrunk(this.provider, luPrivData.getTrunkGroup(), null);
/*      */ 
/*  521 */             if (tsapiTrunk != null)
/*      */             {
/*  523 */               TSTrunk trk = tsapiTrunk.getTSTrunk();
/*  524 */               tsRouteSession.setTrunk(trk);
/*      */             }
/*  526 */             if (privateData instanceof LucentV5RouteRequestEvent)
/*      */             {
/*  528 */               LucentV5RouteRequestEvent luV5PrivData = (LucentV5RouteRequestEvent)privateData;
/*  529 */               tsRouteSession.setUCID(luV5PrivData.getUcid());
/*  530 */               tsRouteSession.setCallOriginatorInfo(luV5PrivData.getCallOriginatorInfo());
/*  531 */               tsRouteSession.setFlexibleBilling(luV5PrivData.isFlexibleBilling());
/*      */ 
/*  533 */               if (privateData instanceof LucentV7RouteRequestEvent)
/*      */               {
/*  535 */                 LucentV7RouteRequestEvent luV7PrivData = (LucentV7RouteRequestEvent)privateData;
/*  536 */                 tsRouteSession.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*  541 */           TSEvent tsapiEvent = tsRouteSession.setState(1);
/*  542 */           if (tsapiEvent != null)
/*      */           {
/*  544 */             log.info("ROUTEEVENT for " + tsDevice + " for " + this.provider);
/*  545 */             tsRouteCallback.deliverEvent(tsapiEvent);
/*      */           }
/*      */         }
/*      */       }
/*  549 */       log.info("DONE handling CSTA_ROUTE_REQUEST for " + this.provider);
/*  550 */       break;
/*      */     case 83:
/*  552 */       log.info("Handling CSTA_ROUTE_REQUEST for " + this.provider);
/*  553 */       CSTARouteRequestEv routeRequest = (CSTARouteRequestEv)cstaEvent;
/*      */ 
/*  555 */       tsDevice = (TSDevice)this.provider.routeRegHash.get(new Integer(routeRequest.getRouteRegisterReqID()));
/*  556 */       if (tsDevice != null)
/*      */       {
/*  558 */         tsRouteCallback = tsDevice.getTSRouteCallback();
/*  559 */         if (tsRouteCallback != null)
/*      */         {
/*  561 */           TSDevice currentRouteDevice = null;
/*  562 */           TSDevice callingDevice = null;
/*  563 */           TSCall call = null;
/*  564 */           currentRouteDevice = this.provider.createDevice(routeRequest.getCurrentRoute());
/*  565 */           callingDevice = this.provider.createDevice(routeRequest.getCallingDevice());
/*  566 */           call = this.provider.createCall(routeRequest.getRoutedCall().getCallID(), privateData);
/*      */ 
/*  568 */           tsRouteSession = new TSRouteSession(this.provider, tsDevice, routeRequest.getRoutingCrossRefID(), currentRouteDevice, callingDevice, call, routeRequest.getRoutedSelAlgorithm(), null);
/*      */ 
/*  574 */           if (privateData instanceof LucentRouteRequestEvent)
/*      */           {
/*  576 */             LucentRouteRequestEvent luPrivData = (LucentRouteRequestEvent)privateData;
/*  577 */             tsRouteSession.setUUI(luPrivData.getUserInfo());
/*  578 */             tsRouteSession.setLAI(luPrivData.getLookaheadInfo());
/*  579 */             tsRouteSession.setUEC(luPrivData.getUserEnteredCode());
/*  580 */             TsapiTrunkImpl tsapiTrunk = TsapiPromoter.promoteTrunk(this.provider, luPrivData.getTrunkGroup(), null);
/*      */ 
/*  584 */             if (tsapiTrunk != null)
/*      */             {
/*  586 */               TSTrunk trk = tsapiTrunk.getTSTrunk();
/*  587 */               tsRouteSession.setTrunk(trk);
/*      */             }
/*      */           }
/*  590 */           TSEvent tsapiEvent = tsRouteSession.setState(1);
/*  591 */           if (tsapiEvent != null)
/*      */           {
/*  593 */             log.info("ROUTEEVENT for " + tsDevice + " for " + this.provider);
/*  594 */             tsRouteCallback.deliverEvent(tsapiEvent);
/*      */           }
/*      */         }
/*      */       }
/*  598 */       log.info("DONE handling CSTA_ROUTE_REQUEST for " + this.provider);
/*  599 */       break;
/*      */     case 85:
/*  601 */       log.info("Handling CSTA_RE_ROUTE_REQUEST for " + this.provider);
/*  602 */       CSTAReRouteRequest reRouteRequest = (CSTAReRouteRequest)cstaEvent;
/*      */ 
/*  604 */       tsDevice = (TSDevice)this.provider.routeRegHash.get(new Integer(reRouteRequest.getRouteRegisterReqID()));
/*  605 */       if (tsDevice != null)
/*      */       {
/*  607 */         tsRouteCallback = tsDevice.getTSRouteCallback();
/*  608 */         if ((tsRouteCallback != null) && (tsDevice.sessionHash != null))
/*      */         {
/*  610 */           tsRouteSession = (TSRouteSession)tsDevice.sessionHash.get(new Integer(reRouteRequest.getRoutingCrossRefID()));
/*  611 */           if (tsRouteSession != null)
/*      */           {
/*  613 */             TSEvent tsapiEvent = tsRouteSession.setState(4);
/*  614 */             if (tsapiEvent != null)
/*      */             {
/*  616 */               log.info("REROUTEEVENT for " + tsDevice + " for " + this.provider);
/*  617 */               tsRouteCallback.deliverEvent(tsapiEvent);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  622 */       log.info("DONE handling CSTA_RE_ROUTE_REQUEST for " + this.provider);
/*  623 */       break;
/*      */     default:
/*  625 */       log.info("WARNING: event " + event.getEventHeader().getEventType() + " not implemented");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cstaEventReport(CSTAEvent event)
/*      */   {
/*  633 */     TSDevice tsDevice = null;
/*  634 */     TsapiRouteMonitor tsRouteCallback = null;
/*  635 */     TSRouteSession tsRouteSession = null;
/*  636 */     CSTAEventReport cstaEvent = (CSTAEventReport)event.getEvent();
/*  637 */     TSDevice routeUsedDevice = null;
/*  638 */     TSDevice callingDevice = null;
/*      */ 
/*  640 */     switch (event.getEventHeader().getEventType())
/*      */     {
/*      */     case 131:
/*  643 */       log.info("Handling CSTA_ROUTE_USED for " + this.provider);
/*  644 */       CSTARouteUsedExtEventReport routeUsedEventExt = (CSTARouteUsedExtEventReport)cstaEvent;
/*  645 */       tsDevice = (TSDevice)this.provider.routeRegHash.get(new Integer(routeUsedEventExt.getRouteRegisterReqID()));
/*  646 */       if (tsDevice != null)
/*      */       {
/*  648 */         tsRouteCallback = tsDevice.getTSRouteCallback();
/*  649 */         if ((tsRouteCallback != null) && (tsDevice.sessionHash != null))
/*      */         {
/*  651 */           tsRouteSession = (TSRouteSession)tsDevice.sessionHash.get(new Integer(routeUsedEventExt.getRoutingCrossRefID()));
/*  652 */           if (tsRouteSession != null)
/*      */           {
/*  654 */             routeUsedDevice = this.provider.createDevice(routeUsedEventExt.getRouteUsed());
/*  655 */             callingDevice = this.provider.createDevice(routeUsedEventExt.getCallingDevice());
/*  656 */             tsRouteSession.setRouteUsedDevice(routeUsedDevice);
/*  657 */             tsRouteSession.setCallingDevice(callingDevice);
/*  658 */             tsRouteSession.setDomain(routeUsedEventExt.isDomain());
/*  659 */             TSEvent tsapiEvent = tsRouteSession.setState(2);
/*  660 */             if (tsapiEvent != null)
/*      */             {
/*  662 */               log.info("ROUTEUSEDEVENT for " + tsDevice + " for " + this.provider);
/*  663 */               tsRouteCallback.deliverEvent(tsapiEvent);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  668 */       log.info("DONE handling CSTA_ROUTE_USED for " + this.provider);
/*  669 */       break;
/*      */     case 86:
/*  671 */       log.info("Handling CSTA_ROUTE_USED for " + this.provider);
/*  672 */       CSTARouteUsedEventReport routeUsedEvent = (CSTARouteUsedEventReport)cstaEvent;
/*  673 */       tsDevice = (TSDevice)this.provider.routeRegHash.get(new Integer(routeUsedEvent.getRouteRegisterReqID()));
/*  674 */       if (tsDevice != null)
/*      */       {
/*  676 */         tsRouteCallback = tsDevice.getTSRouteCallback();
/*  677 */         if ((tsRouteCallback != null) && (tsDevice.sessionHash != null))
/*      */         {
/*  679 */           tsRouteSession = (TSRouteSession)tsDevice.sessionHash.get(new Integer(routeUsedEvent.getRoutingCrossRefID()));
/*  680 */           if (tsRouteSession != null)
/*      */           {
/*  682 */             routeUsedDevice = this.provider.createDevice(routeUsedEvent.getRouteUsed());
/*  683 */             callingDevice = this.provider.createDevice(routeUsedEvent.getCallingDevice());
/*  684 */             tsRouteSession.setRouteUsedDevice(routeUsedDevice);
/*  685 */             tsRouteSession.setCallingDevice(callingDevice);
/*  686 */             tsRouteSession.setDomain(routeUsedEvent.isDomain());
/*  687 */             TSEvent tsapiEvent = tsRouteSession.setState(2);
/*  688 */             if (tsapiEvent != null)
/*      */             {
/*  690 */               log.info("ROUTEUSEDEVENT for " + tsDevice + " for " + this.provider);
/*  691 */               tsRouteCallback.deliverEvent(tsapiEvent);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  696 */       log.info("DONE handling CSTA_ROUTE_USED for " + this.provider);
/*  697 */       break;
/*      */     case 87:
/*  699 */       log.info("Handling CSTA_ROUTE_END for " + this.provider);
/*  700 */       CSTARouteEndEventReport routeEndEvent = (CSTARouteEndEventReport)cstaEvent;
/*  701 */       tsDevice = (TSDevice)this.provider.routeRegHash.get(new Integer(routeEndEvent.getRouteRegisterReqID()));
/*  702 */       if (tsDevice != null)
/*      */       {
/*  704 */         tsRouteCallback = tsDevice.getTSRouteCallback();
/*  705 */         if ((tsRouteCallback != null) && (tsDevice.sessionHash != null))
/*      */         {
/*  707 */           tsRouteSession = (TSRouteSession)tsDevice.sessionHash.get(new Integer(routeEndEvent.getRoutingCrossRefID()));
/*  708 */           if (tsRouteSession != null)
/*      */           {
/*  710 */             tsRouteSession.setCause(routeEndEvent.getCause());
/*  711 */             TSEvent tsapiEvent = tsRouteSession.setState(3);
/*  712 */             if (tsapiEvent != null)
/*      */             {
/*  714 */               log.info("ROUTEENDEVENT for " + tsDevice + " for " + this.provider);
/*  715 */               tsRouteCallback.deliverEvent(tsapiEvent);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  720 */       log.info("DONE handling CSTA_ROUTE_END for " + this.provider);
/*  721 */       break;
/*      */     case 82:
/*  723 */       log.info("Handling CSTA_ROUTE_REGISTER_ABORT for " + this.provider);
/*  724 */       CSTARouteRegisterAbortEventReport registerAbortEvent = (CSTARouteRegisterAbortEventReport)cstaEvent;
/*      */ 
/*  726 */       tsDevice = (TSDevice)this.provider.routeRegHash.get(new Integer(registerAbortEvent.getRouteRegisterReqID()));
/*  727 */       if (tsDevice != null)
/*      */       {
/*  729 */         tsRouteCallback = tsDevice.getTSRouteCallback();
/*  730 */         if (tsRouteCallback != null)
/*  731 */           tsRouteCallback.deleteReference(tsDevice);
/*      */       }
/*  733 */       log.info("DONE handling CSTA_ROUTE_REGISTER_ABORT for " + this.provider);
/*  734 */       break;
/*      */     case 93:
/*  736 */       log.info("Handling CSTA_PRIVATE for " + this.provider);
/*  737 */       Object replyPriv = event.getPrivData();
/*  738 */       if (replyPriv instanceof LucentQueryAgentLoginResp)
/*      */       {
/*  740 */         LucentQueryAgentLoginResp respEvent = (LucentQueryAgentLoginResp)replyPriv;
/*  741 */         tsDevice = this.provider.findACDDevice(respEvent.getPrivEventCrossRefID());
/*  742 */         if (tsDevice != null)
/*      */         {
/*  744 */           tsDevice.handleAgentLoginResponse(respEvent);
/*      */         }
/*      */       }
/*  747 */       log.info("DONE handling CSTA_PRIVATE for " + this.provider);
/*  748 */       break;
/*      */     case 106:
/*  750 */       CSTASysStatEventReport sysStat = (CSTASysStatEventReport)cstaEvent;
/*  751 */       Vector eventList = new Vector();
/*      */ 
/*  753 */       if ((sysStat.getState() == 6) || (sysStat.getState() == 5) || (sysStat.getState() == 4))
/*      */       {
/*  758 */         this.provider.setState(0, eventList);
/*      */       }
/*  760 */       else if ((sysStat.getState() == 2) || (sysStat.getState() == 1))
/*      */       {
/*  762 */         this.provider.setState(2, eventList);
/*      */       }
/*  764 */       if (eventList.size() <= 0) return;
/*  765 */       Vector monitors = this.provider.getMonitors();
/*  766 */       for (int j = 0; j < monitors.size(); ++j) {
/*  767 */         TsapiProviderMonitor callback = (TsapiProviderMonitor)monitors.elementAt(j);
/*      */ 
/*  769 */         callback.deliverEvents(eventList, false);
/*      */       }
/*  771 */       break;
/*      */     default:
/*  774 */       log.info("WARNING: event " + event.getEventHeader().getEventType() + " not implemented");
/*      */     }
/*      */   }
/*      */ 
/*      */   TSEventHandler(TSProviderImpl _provider)
/*      */   {
/*  783 */     this.provider = _provider;
/*      */   }
/*      */ 
/*      */   void doConnEvents(int eventType, Object monitored, short cause, CSTAExtendedDeviceID subjectDeviceID, CSTAConnectionID connID, int connState, int termConnState, Object privateData, CSTAExtendedDeviceID callingDeviceID, CSTAExtendedDeviceID calledDeviceID, CSTAExtendedDeviceID lastRedirectionDeviceID, boolean dontNeedSnapshot, int numQueued)
/*      */   {
/*  794 */     int jtapiCause = getJtapiCause(cause);
/*  795 */     TSDevice subjectDevice = this.provider.createDevice(subjectDeviceID, connID);
/*  796 */     if (subjectDevice == null)
/*      */     {
/*  798 */       if ((((subjectDeviceID == null) || (subjectDeviceID.getDeviceIDStatus() == 2))) && (monitored instanceof TSDevice))
/*      */       {
/*  806 */         if (this.provider.isLucent())
/*      */         {
/*  808 */           subjectDevice = this.provider.createDevice(connID.getDeviceID(), connID);
/*      */         }
/*      */         else
/*      */         {
/*  812 */           subjectDevice = (TSDevice)monitored;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  817 */         if (connID == null)
/*      */         {
/*  819 */           return;
/*      */         }
/*      */ 
/*  824 */         subjectDevice = this.provider.createDevice(connID.getDeviceID(), connID);
/*      */       }
/*  826 */       if (subjectDevice == null)
/*      */       {
/*  828 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  835 */     if ((this.provider.isLucent()) && (connID.getDevIDType() == 1))
/*      */     {
/*  838 */       CSTAConnectionID realConnID = subjectDevice.matchConn(connID);
/*  839 */       if (realConnID != null)
/*      */       {
/*  841 */         connID = realConnID;
/*      */       }
/*      */     }
/*      */ 
/*  845 */     if (eventType == 64)
/*      */     {
/*  847 */       subjectDevice.setNumberQueued(numQueued);
/*      */     }
/*      */ 
/*  861 */     TSDevice connectionDevice = subjectDevice;
/*  862 */     if (cause == 17)
/*      */     {
/*  864 */       TSDevice confDevice = null;
/*  865 */       switch (eventType)
/*      */       {
/*      */       case 57:
/*      */       case 59:
/*  869 */         confDevice = this.provider.createDevice(calledDeviceID);
/*  870 */         break;
/*      */       case 63:
/*  872 */         if (privateData instanceof LucentOriginatedEvent)
/*      */         {
/*  874 */           LucentOriginatedEvent luPrivData = (LucentOriginatedEvent)privateData;
/*  875 */           LucentTerminalImpl physicalTerminal = null;
/*      */           try {
/*  877 */             physicalTerminal = TsapiPromoter.promoteTerminal(this.provider, luPrivData.getPhysicalTerminal_asn());
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/*  881 */             log.error(e.getMessage(), e);
/*      */           }
/*  883 */           if (physicalTerminal != null)
/*      */           {
/*  887 */             subjectDevice = physicalTerminal.getTSDevice();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  892 */           confDevice = this.provider.createDevice(callingDeviceID);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  898 */       if (confDevice != null)
/*      */       {
/*  900 */         connectionDevice = confDevice;
/*      */       }
/*      */     }
/*  903 */     else if (privateData instanceof LucentOriginatedEvent)
/*      */     {
/*  905 */       LucentOriginatedEvent luPrivData = (LucentOriginatedEvent)privateData;
/*  906 */       LucentTerminalImpl physicalTerminal = null;
/*      */       try {
/*  908 */         physicalTerminal = TsapiPromoter.promoteTerminal(this.provider, luPrivData.getPhysicalTerminal_asn());
/*      */       }
/*      */       catch (TsapiPlatformException e)
/*      */       {
/*  912 */         log.error(e.getMessage(), e);
/*      */       }
/*  914 */       if (physicalTerminal != null)
/*      */       {
/*  918 */         subjectDevice = physicalTerminal.getTSDevice();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  926 */     TSCall call = this.provider.createCall(connID.getCallID(), privateData);
/*      */ 
/*  930 */     call = this.provider.validateCall(privateData, call, connID.getCallID());
/*      */ 
/*  933 */     call.setCSTACause(cause);
/*      */ 
/*  941 */     if ((monitored != null) && (monitored instanceof TSDevice) && (((TSDevice)monitored).getDeviceType() == 1))
/*      */     {
/*  945 */       this.provider.addCallToDomain((TSDevice)monitored, call);
/*      */     }
/*      */ 
/*  957 */     call.considerAddingVDNMonitorCallObservers(monitored);
/*      */ 
/*  961 */     if ((((eventType == 58) || (eventType == 56))) && 
/*  964 */       (call.getSnapshotCallConfPending()))
/*      */     {
/*  967 */       log.info("set redo snapshot call to true");
/*  968 */       call.setNeedRedoSnapshotCall(true);
/*      */     }
/*      */ 
/*  973 */     if (call.getTSState() == 34)
/*      */     {
/*  975 */       switch (eventType)
/*      */       {
/*      */       case 56:
/*      */       case 58:
/*      */       case 60:
/*  980 */         break;
/*      */       case 57:
/*      */       case 59:
/*      */       default:
/*  985 */         this.provider.dumpCall(connID.getCallID());
/*  986 */         call = this.provider.createCall(connID.getCallID());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  993 */     switch (eventType)
/*      */     {
/*      */     case 61:
/*      */     case 65:
/*  997 */       subjectDevice.setIsATerminal(true);
/*      */     }
/*      */ 
/* 1001 */     Vector eventList = new Vector();
/*      */ 
/* 1003 */     switch (eventType)
/*      */     {
/*      */     case 57:
/* 1022 */       if (this.provider.isConnInAnyHash(connID) == true)
/*      */       {
/* 1034 */         TSConnection dumpingConn = this.provider.createTerminalConnection(connID, subjectDevice, eventList, connectionDevice);
/* 1035 */         int oldCState = dumpingConn.getCallControlConnState();
/* 1036 */         int oldTCState = dumpingConn.getCallControlTermConnState();
/*      */ 
/* 1038 */         if ((((oldCState == 89) || (oldTCState == 102))) && ((
/* 1041 */           ((!dumpingConn.isTerminalConnection()) && (connState != 89)) || ((dumpingConn.isTerminalConnection() == true) && (termConnState != 102)))))
/*      */         {
/* 1047 */           eventList = new Vector();
/*      */ 
/* 1050 */           dumpingConn.delete();
/* 1051 */           this.provider.dumpConn(connID);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1060 */     if (!dontNeedSnapshot)
/*      */     {
/* 1062 */       if (call.needsSnapshot())
/*      */       {
/* 1065 */         SnapshotCallExtraConfHandler handler = new UnsolicitedSnapshotCallConfHandler(this, eventType, cause, subjectDeviceID, subjectDevice, connID, call, privateData, callingDeviceID, calledDeviceID, lastRedirectionDeviceID, connState, termConnState, connectionDevice);
/*      */ 
/* 1071 */         call.doSnapshot(connID, handler, false);
/* 1072 */         call.staleObsCleanup(100);
/*      */ 
/* 1074 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */     else {
/* 1079 */       call.setNeedSnapshot(false);
/*      */     }
/*      */ 
/* 1086 */     TSConnection connection = this.provider.createTerminalConnection(connID, subjectDevice, eventList, connectionDevice);
/* 1087 */     int oldConnState = connection.getCallControlConnState();
/* 1088 */     int oldTermConnState = connection.getCallControlTermConnState();
/*      */ 
/* 1090 */     if ((oldConnState == 89) || (oldTermConnState == 102))
/*      */     {
/* 1093 */       switch (eventType)
/*      */       {
/*      */       case 63:
/*      */       case 66:
/* 1099 */         eventList = new Vector();
/*      */ 
/* 1101 */         call.setState(34, eventList);
/* 1102 */         doCallMonitors(call, eventList, 102, null);
/* 1103 */         this.provider.dumpCall(connID.getCallID());
/*      */ 
/* 1106 */         eventList = new Vector();
/* 1107 */         call = this.provider.createCall(connID.getCallID());
/* 1108 */         call.setNeedSnapshot(false);
/* 1109 */         connection = this.provider.createTerminalConnection(connID, subjectDevice, eventList, connectionDevice);
/*      */ 
/* 1111 */         oldConnState = connection.getCallControlConnState();
/* 1112 */         oldTermConnState = connection.getCallControlTermConnState();
/* 1113 */         break;
/*      */       default:
/* 1117 */         if (((!connection.isTerminalConnection()) && (connState != 89)) || ((connection.isTerminalConnection() == true) && (termConnState != 102)))
/*      */         {
/* 1127 */           eventList = new Vector();
/*      */ 
/* 1129 */           connection.delete();
/* 1130 */           this.provider.dumpConn(connID);
/*      */ 
/* 1132 */           connection = this.provider.createTerminalConnection(connID, subjectDevice, eventList, connectionDevice);
/*      */ 
/* 1134 */           oldConnState = connection.getCallControlConnState();
/* 1135 */           oldTermConnState = connection.getCallControlTermConnState();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1141 */     if (eventType == 56)
/*      */     {
/* 1148 */       Vector allConnections = call.getConnections();
/* 1149 */       if (allConnections.size() <= 2)
/*      */       {
/* 1153 */         for (int i = 0; i < allConnections.size(); ++i)
/*      */         {
/* 1155 */           TSConnection tmpconn = (TSConnection)allConnections.elementAt(i);
/* 1156 */           if ((tmpconn == connection) || (
/* 1158 */             (tmpconn.getTSConnState() != 53) && (tmpconn.getTSConnState() != 52))) {
/*      */             continue;
/*      */           }
/* 1161 */           call.setState(34, eventList);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1168 */     connection.setConnectionState(connState, eventList);
/* 1169 */     connection.setTermConnState(termConnState, eventList);
/*      */ 
/* 1180 */     if (eventType == 56)
/*      */     {
/* 1182 */       Vector allConnections = call.getConnections();
/* 1183 */       if (allConnections.size() >= 1)
/*      */       {
/* 1186 */         Vector listOfConnsBelongToDiffDevIDType = new Vector();
/*      */ 
/* 1188 */         for (int i = 0; i < allConnections.size(); ++i)
/*      */         {
/* 1190 */           TSConnection tmpconn = (TSConnection)allConnections.elementAt(i);
/* 1191 */           if (tmpconn.isDoNotExpectConnectionClearedEvent())
/*      */           {
/* 1193 */             listOfConnsBelongToDiffDevIDType.addElement(tmpconn);
/*      */           }
/* 1195 */           tmpconn = null;
/*      */         }
/*      */ 
/* 1198 */         if (allConnections.equals(listOfConnsBelongToDiffDevIDType))
/*      */         {
/* 1200 */           for (int i = 0; i < allConnections.size(); ++i)
/*      */           {
/* 1202 */             TSConnection tmpconn = (TSConnection)allConnections.elementAt(i);
/* 1203 */             if (tmpconn.isDoNotExpectConnectionClearedEvent())
/*      */             {
/* 1205 */               log.info("Conn " + tmpconn + ", has 'connBelongToDifferentDeviceIDType' flag set. Clearing connection.");
/* 1206 */               tmpconn.setConnectionState(connState, eventList);
/* 1207 */               tmpconn.setTermConnState(termConnState, eventList);
/*      */             }
/* 1209 */             tmpconn = null;
/*      */           }
/*      */         }
/* 1212 */         listOfConnsBelongToDiffDevIDType = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1217 */     if (dontNeedSnapshot)
/*      */     {
/* 1219 */       call.setNeedSnapshot(false);
/*      */     }
/*      */ 
/* 1222 */     finishConnEvents(monitored, eventType, cause, jtapiCause, subjectDeviceID, subjectDevice, connection, call, privateData, callingDeviceID, calledDeviceID, lastRedirectionDeviceID, connState, oldConnState, oldTermConnState, eventList);
/*      */ 
/* 1234 */     if (call.checkForMonitors())
/*      */       return;
/* 1236 */     call.setNeedSnapshot(true);
/*      */   }
/*      */ 
/*      */   short endpointEvaluation(short deviceIDType)
/*      */   {
/* 1254 */     switch (deviceIDType)
/*      */     {
/*      */     case 0:
/* 1258 */       return 3;
/*      */     case 70:
/* 1261 */       return 1;
/*      */     case 55:
/* 1265 */       return 3;
/*      */     case 40:
/* 1268 */       return 2;
/*      */     }
/*      */ 
/* 1271 */     return 1;
/*      */   }
/*      */ 
/*      */   boolean isCalledBetterEndpoint(CSTAExtendedDeviceID calling, CSTAExtendedDeviceID called)
/*      */   {
/* 1290 */     if ((calling != null) && (called != null) && (calling.getDeviceIDStatus() == 0) && (called.getDeviceIDStatus() == 0))
/*      */     {
/* 1295 */       short callingEval = endpointEvaluation(calling.getDeviceIDType());
/* 1296 */       short calledEval = endpointEvaluation(called.getDeviceIDType());
/*      */ 
/* 1299 */       return calledEval >= callingEval;
/*      */     }
/*      */ 
/* 1303 */     return true;
/*      */   }
/*      */ 
/*      */   void finishConnEvents(Object monitored, int eventType, int cause, int jtapiCause, CSTAExtendedDeviceID subjectDeviceID, TSDevice subjectDevice, TSConnection connection, TSCall call, Object privateData, CSTAExtendedDeviceID callingDeviceID, CSTAExtendedDeviceID calledDeviceID, CSTAExtendedDeviceID lastRedirectionDeviceID, int connState, int oldConnState, int oldTermConnState, Vector<TSEvent> eventList)
/*      */   {
/* 1313 */     TSDevice callingDevice = this.provider.createDevice(callingDeviceID);
/* 1314 */     TSDevice calledDevice = this.provider.createDevice(calledDeviceID);
/* 1315 */     TSDevice lastRedirectionDevice = this.provider.createDevice(lastRedirectionDeviceID);
/*      */ 
/* 1317 */     call.setCallingDevices(callingDevice);
/* 1318 */     call.setCalledDevice(calledDevice);
/* 1319 */     call.setLastRedirectionDevice(lastRedirectionDevice);
/*      */ 
/* 1321 */     if ((eventType == 62) && (subjectDeviceID != null))
/*      */     {
/* 1332 */       TSTrunk trk = null;
/*      */ 
/* 1334 */       if (privateData instanceof LucentV5NetworkProgressInfo)
/*      */       {
/* 1342 */         LucentV5NetworkProgressInfo luPrivData = (LucentV5NetworkProgressInfo)privateData;
/* 1343 */         if (luPrivData != null)
/*      */         {
/* 1351 */           TsapiTrunkImpl tsapiTrunk = TsapiPromoter.promoteTrunk(this.provider, luPrivData.getTrunkGroup(), luPrivData.getTrunkMember());
/*      */ 
/* 1355 */           if (tsapiTrunk != null)
/*      */           {
/* 1357 */             trk = tsapiTrunk.getTSTrunk();
/* 1358 */             if (trk != null)
/*      */             {
/* 1360 */               connection.setTrunk(trk);
/* 1361 */               trk.setTSConnection(connection);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1369 */         trk = this.provider.createTrunk(subjectDevice.getName(), 2);
/* 1370 */         if ((trk != null) && 
/* 1379 */           (privateData instanceof LucentV5NetworkProgressInfo))
/*      */         {
/* 1395 */           LucentV5NetworkProgressInfo luPrivData = (LucentV5NetworkProgressInfo)privateData;
/* 1396 */           if (luPrivData != null)
/*      */           {
/* 1398 */             trk.setGroupName(luPrivData.getTrunkGroup());
/* 1399 */             trk.setMemberName(luPrivData.getTrunkMember());
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1405 */       if (trk != null)
/*      */       {
/* 1408 */         call.addTrunk(trk, eventList);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1417 */     TSDevice deliveringACDDevice = null;
/* 1418 */     TSDevice distributingDevice = null;
/* 1419 */     TSDevice distributingVDN = null;
/* 1420 */     if (privateData instanceof LucentDeliveredEvent)
/*      */     {
/* 1422 */       LucentDeliveredEvent luPrivData = (LucentDeliveredEvent)privateData;
/* 1423 */       LucentACDAddressImpl acdAddr = TsapiPromoter.promoteACDAddress(this.provider, ((LucentDeliveredEvent)privateData).getSplit_asn());
/*      */ 
/* 1426 */       if (acdAddr != null)
/*      */       {
/* 1428 */         deliveringACDDevice = acdAddr.getTSDevice();
/* 1429 */         call.setDeliveringACDDevice(deliveringACDDevice);
/*      */       }
/* 1431 */       TsapiAddress address = TsapiPromoter.promoteDeviceIDToAddress(this.provider, luPrivData.getDistributingDevice_asn());
/*      */ 
/* 1433 */       if (address != null)
/*      */       {
/* 1435 */         distributingDevice = address.getTSDevice();
/* 1436 */         call.setDistributingDevice(distributingDevice);
/*      */       }
/*      */ 
/* 1439 */       call.setUUI(luPrivData.getUserInfo());
/* 1440 */       call.setLAI(luPrivData.getLookaheadInfo());
/* 1441 */       call.setUEC(luPrivData.getUserEnteredCode());
/* 1442 */       call.setOCI(luPrivData.getOriginalCallInfo());
/* 1443 */       call.setReason(luPrivData.getReason());
/* 1444 */       TsapiTrunkImpl tsapiTrunk = TsapiPromoter.promoteTrunk(this.provider, luPrivData.getTrunkGroup(), luPrivData.getTrunkMember());
/*      */ 
/* 1448 */       if (tsapiTrunk != null)
/*      */       {
/* 1450 */         TSTrunk trk = tsapiTrunk.getTSTrunk();
/*      */ 
/* 1455 */         if (!this.provider.isDeviceMonitorable(subjectDeviceID.getDeviceID()))
/*      */         {
/* 1457 */           trk.setType(2);
/*      */         }
/* 1459 */         TSConnection trkConn = call.findOtherConnection(connection);
/* 1460 */         if (trkConn != null)
/*      */         {
/* 1462 */           trkConn.setTrunk(trk);
/* 1463 */           trk.setTSConnection(trkConn);
/*      */         }
/* 1465 */         call.addTrunk(trk, eventList);
/*      */       }
/* 1467 */       if (privateData instanceof LucentV5DeliveredEvent)
/*      */       {
/* 1469 */         LucentV5DeliveredEvent luV5PrivData = (LucentV5DeliveredEvent)privateData;
/* 1470 */         call.setUCID(luV5PrivData.getUcid());
/* 1471 */         call.setCallOriginatorInfo(luV5PrivData.getCallOriginatorInfo());
/* 1472 */         call.setFlexibleBilling(luV5PrivData.isFlexibleBilling());
/*      */ 
/* 1474 */         if (privateData instanceof LucentV7DeliveredEvent)
/*      */         {
/* 1476 */           LucentV7DeliveredEvent luV7PrivData = (LucentV7DeliveredEvent)privateData;
/* 1477 */           call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */ 
/* 1479 */           if (luV7PrivData.getDistributingVDN_asn() != null)
/*      */           {
/* 1481 */             distributingVDN = TsapiPromoter.promoteExtendedDeviceIDToTSDevice(this.provider, luV7PrivData.getDistributingVDN_asn());
/*      */ 
/* 1487 */             call.setDistributingVDN(distributingVDN);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1492 */     else if (privateData instanceof LucentEstablishedEvent)
/*      */     {
/* 1494 */       LucentEstablishedEvent luPrivData = (LucentEstablishedEvent)privateData;
/* 1495 */       LucentACDAddressImpl acdAddr = TsapiPromoter.promoteACDAddress(this.provider, luPrivData.getSplit_asn());
/*      */ 
/* 1498 */       if (acdAddr != null)
/*      */       {
/* 1500 */         deliveringACDDevice = acdAddr.getTSDevice();
/* 1501 */         call.setDeliveringACDDevice(deliveringACDDevice);
/*      */       }
/* 1503 */       TsapiAddress address = TsapiPromoter.promoteDeviceIDToAddress(this.provider, luPrivData.getDistributingDevice_asn());
/*      */ 
/* 1506 */       if (address != null)
/*      */       {
/* 1508 */         distributingDevice = address.getTSDevice();
/* 1509 */         call.setDistributingDevice(distributingDevice);
/*      */       }
/* 1511 */       call.setUUI(luPrivData.getUserInfo());
/* 1512 */       call.setLAI(luPrivData.getLookaheadInfo());
/* 1513 */       call.setUEC(luPrivData.getUserEnteredCode());
/* 1514 */       call.setOCI(luPrivData.getOriginalCallInfo());
/* 1515 */       call.setReason(luPrivData.getReason());
/* 1516 */       TsapiTrunkImpl tsapiTrunk = TsapiPromoter.promoteTrunk(this.provider, luPrivData.getTrunkGroup(), luPrivData.getTrunkMember());
/*      */ 
/* 1520 */       if (tsapiTrunk != null)
/*      */       {
/* 1522 */         TSTrunk trk = tsapiTrunk.getTSTrunk();
/*      */ 
/* 1527 */         if (!this.provider.isDeviceMonitorable(subjectDeviceID.getDeviceID()))
/*      */         {
/* 1529 */           trk.setType(2);
/*      */         }
/* 1531 */         TSConnection otherConn = call.findOtherConnection(connection);
/*      */ 
/* 1539 */         TSConnection trkConn = (isCalledBetterEndpoint(callingDeviceID, calledDeviceID)) ? otherConn : connection;
/*      */ 
/* 1541 */         if (trkConn != null)
/*      */         {
/* 1543 */           trkConn.setTrunk(trk);
/* 1544 */           trk.setTSConnection(trkConn);
/*      */         }
/* 1546 */         call.addTrunk(trk, eventList);
/*      */       }
/* 1548 */       if (privateData instanceof LucentV5EstablishedEvent)
/*      */       {
/* 1550 */         LucentV5EstablishedEvent luV5PrivData = (LucentV5EstablishedEvent)privateData;
/* 1551 */         call.setUCID(luV5PrivData.getUcid());
/* 1552 */         call.setCallOriginatorInfo(luV5PrivData.getCallOriginatorInfo());
/* 1553 */         call.setFlexibleBilling(luV5PrivData.isFlexibleBilling());
/*      */ 
/* 1556 */         if (privateData instanceof LucentV7EstablishedEvent)
/*      */         {
/* 1558 */           LucentV7EstablishedEvent luV7PrivData = (LucentV7EstablishedEvent)privateData;
/* 1559 */           call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */ 
/* 1561 */           if (luV7PrivData.getDistributingVDN_asn() != null)
/*      */           {
/* 1563 */             distributingVDN = TsapiPromoter.promoteExtendedDeviceIDToTSDevice(this.provider, luV7PrivData.getDistributingVDN_asn());
/*      */ 
/* 1569 */             call.setDistributingVDN(distributingVDN);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1574 */     else if (privateData instanceof LucentConnectionClearedEvent)
/*      */     {
/* 1576 */       LucentConnectionClearedEvent luPrivData = (LucentConnectionClearedEvent)privateData;
/* 1577 */       call.setUUI(luPrivData.getUserInfo());
/*      */ 
/* 1579 */       if (privateData instanceof LucentV7ConnectionClearedEvent)
/*      */       {
/* 1581 */         LucentV7ConnectionClearedEvent luV7PrivData = (LucentV7ConnectionClearedEvent)privateData;
/* 1582 */         call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */       }
/*      */ 
/*      */     }
/* 1586 */     else if (privateData instanceof LucentOriginatedEvent)
/*      */     {
/* 1588 */       LucentOriginatedEvent luPrivData = (LucentOriginatedEvent)privateData;
/* 1589 */       call.setUUI(luPrivData.getUserInfo());
/*      */     }
/* 1591 */     else if (privateData instanceof LucentServiceInitiatedEvent)
/*      */     {
/* 1593 */       LucentServiceInitiatedEvent luPrivData = (LucentServiceInitiatedEvent)privateData;
/* 1594 */       call.setUCID(luPrivData.getUcid());
/*      */     }
/* 1596 */     else if (privateData instanceof LucentQueuedEvent)
/*      */     {
/* 1598 */       LucentQueuedEvent luV7PrivData = (LucentQueuedEvent)privateData;
/* 1599 */       call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */     }
/* 1602 */     else if (privateData instanceof LucentV7NetworkProgressInfo)
/*      */     {
/* 1604 */       LucentV7NetworkProgressInfo luV7PrivData = (LucentV7NetworkProgressInfo)privateData;
/* 1605 */       call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */     }
/* 1608 */     else if (privateData instanceof LucentDivertedEvent)
/*      */     {
/* 1610 */       LucentDivertedEvent luV7PrivData = (LucentDivertedEvent)privateData;
/* 1611 */       call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */     }
/* 1614 */     else if (privateData instanceof LucentFailedEvent)
/*      */     {
/* 1616 */       LucentFailedEvent luV7PrivData = (LucentFailedEvent)privateData;
/* 1617 */       call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */ 
/* 1619 */       if (privateData instanceof LucentV8FailedEvent)
/*      */       {
/* 1621 */         LucentV8FailedEvent luV8PrivData = (LucentV8FailedEvent)privateData;
/*      */ 
/* 1623 */         if (luV8PrivData.getCallingDevice() != null) {
/* 1624 */           call.setCallingDevices(TsapiPromoter.promoteExtendedDeviceIDToTSDevice(this.provider, luV8PrivData.getCallingDevice()));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1642 */     if ((eventType == 57) && (getJtapiCause(cause) == 210))
/*      */     {
/* 1644 */       TSDevice[] dropDevices = new TSDevice[3];
/* 1645 */       dropDevices[0] = lastRedirectionDevice;
/* 1646 */       dropDevices[1] = deliveringACDDevice;
/*      */ 
/* 1648 */       dropDevices[2] = ((distributingVDN == null) ? distributingDevice : distributingVDN);
/* 1649 */       for (int j = 0; j < 3; ++j)
/*      */       {
/* 1651 */         if ((dropDevices[j] == null) || (
/* 1653 */           (dropDevices[j].getDeviceType() != 2) && (dropDevices[j].getDeviceType() != 1)))
/*      */           continue;
/* 1655 */         TSConnection dropConn = call.getConnAtDevice(dropDevices[j]);
/* 1656 */         if (dropConn == null)
/*      */           continue;
/* 1658 */         dropConn.setConnectionState(89, eventList);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1665 */     if ((eventType == 64) && (cause == 28) && (lastRedirectionDevice != null))
/*      */     {
/* 1670 */       call.setDeliveringACDDevice(subjectDevice);
/*      */ 
/* 1675 */       if (oldConnState != 83)
/*      */       {
/* 1677 */         TSConnection lastRedirConn = call.getConnAtDevice(lastRedirectionDevice);
/* 1678 */         if (lastRedirConn != null)
/*      */         {
/* 1680 */           if (lastRedirectionDevice.getDeviceType() == 1)
/*      */           {
/* 1683 */             lastRedirConn.addACDConns(connection);
/*      */ 
/* 1685 */             connection.setACDManagerConn(lastRedirConn);
/*      */           }
/*      */           else
/*      */           {
/* 1690 */             TSConnection acdManagerConn = lastRedirConn.getACDManagerConn();
/* 1691 */             if (acdManagerConn != null)
/*      */             {
/* 1694 */               acdManagerConn.addACDConns(connection);
/*      */ 
/* 1696 */               connection.setACDManagerConn(acdManagerConn);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1704 */     if ((monitored != null) && (eventType == 58) && (monitored instanceof TSDevice) && (((TSDevice)monitored).getDeviceType() == 1))
/*      */     {
/* 1710 */       this.provider.removeCallFromDomain(call);
/*      */     }
/*      */ 
/* 1719 */     doCallMonitors(call, eventList, jtapiCause, privateData);
/*      */   }
/*      */ 
/*      */   void doCallEvents(int eventType, Object monitored, short cause, CSTAConnectionID connID, int callState, Object privateData)
/*      */   {
/* 1726 */     Vector eventList = new Vector();
/*      */ 
/* 1728 */     int jtapiCause = getJtapiCause(cause);
/* 1729 */     TSCall call = null;
/* 1730 */     if (connID == null)
/*      */     {
/* 1732 */       if (!(monitored instanceof TSCall))
/*      */       {
/* 1734 */         return;
/*      */       }
/* 1736 */       call = (TSCall)monitored;
/*      */     }
/*      */     else
/*      */     {
/* 1742 */       call = this.provider.createCall(connID.getCallID(), privateData);
/*      */ 
/* 1747 */       call = this.provider.validateCall(privateData, call, connID.getCallID());
/*      */ 
/* 1749 */       if (call.getTSState() == 34)
/*      */       {
/* 1751 */         switch (eventType)
/*      */         {
/*      */         case 54:
/* 1754 */           break;
/*      */         case 94:
/* 1756 */           if (privateData instanceof LucentChargeAdvice);
/*      */         default:
/* 1765 */           this.provider.dumpCall(connID.getCallID());
/* 1766 */           call = this.provider.createCall(connID.getCallID());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1773 */     call.setCSTACause(cause);
/* 1774 */     if (privateData instanceof LucentCallClearedEvent)
/*      */     {
/* 1776 */       LucentCallClearedEvent luPrivData = (LucentCallClearedEvent)privateData;
/* 1777 */       call.setReason(luPrivData.getReason());
/*      */     }
/*      */ 
/* 1793 */     if ((eventType == 54) && (cause == 32))
/*      */     {
/* 1795 */       call.setReceivedCallClearedTransfer(true);
/* 1796 */       return;
/*      */     }
/*      */ 
/* 1799 */     if (eventType == 94)
/*      */     {
/* 1801 */       if (privateData instanceof LucentEnteredDigitsEvent)
/*      */       {
/* 1803 */         LucentEnteredDigitsEvent luPrivData = (LucentEnteredDigitsEvent)privateData;
/* 1804 */         LucentCallImpl connection = TsapiPromoter.promoteConnection(this.provider, luPrivData.getConnection_asn());
/*      */ 
/* 1807 */         call = connection.getTSCall();
/* 1808 */         call.setDigits(luPrivData.getDigits());
/* 1809 */         jtapiCause = getJtapiCause(luPrivData.getCause());
/* 1810 */         eventList.addElement(new TSEvent(57, call));
/*      */ 
/* 1812 */         privateData = null;
/*      */       }
/*      */ 
/*      */     }
/*      */     else {
/* 1817 */       call.setState(callState, eventList);
/*      */     }
/*      */ 
/* 1820 */     doCallMonitors(call, eventList, jtapiCause, privateData);
/*      */   }
/*      */ 
/*      */   void doCallMonitors(TSCall call, Vector<TSEvent> eventList, int jtapiCause, Object privateData)
/*      */   {
/* 1826 */     if (call == null)
/*      */     {
/* 1828 */       return;
/*      */     }
/*      */ 
/* 1832 */     if (privateData != null)
/*      */     {
/* 1835 */       for (int i = 0; i < eventList.size(); ++i)
/*      */       {
/* 1837 */         TSEvent ev = (TSEvent)eventList.elementAt(i);
/* 1838 */         if (ev.getPrivateData() != null)
/*      */           continue;
/* 1840 */         ev.setPrivateData(privateData);
/*      */       }
/*      */ 
/* 1843 */       if (!this.provider.isLucent()) {
/* 1844 */         eventList.addElement(new TSEvent(9999, call, privateData, this.provider));
/*      */       }
/* 1846 */       else if (privateData instanceof LucentChargeAdvice)
/*      */       {
/* 1848 */         eventList.addElement(new TSEvent(9999, call, privateData, this.provider));
/*      */       }
/*      */     }
/*      */ 
/* 1852 */     Vector observers = null;
/*      */ 
/* 1854 */     if (eventList.size() > 0)
/*      */     {
/* 1857 */       observers = call.getObservers();
/*      */ 
/* 1859 */       for (int j = 0; j < observers.size(); ++j)
/*      */       {
/* 1861 */         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 1862 */         callback.deliverEvents(eventList, jtapiCause, false);
/*      */       }
/*      */     }
/*      */ 
/* 1866 */     call.processCallbackSnapshots(jtapiCause);
/*      */ 
/* 1869 */     if (call.getTSState() == 34)
/*      */     {
/* 1886 */       call.endCVDObservers(jtapiCause, null);
/* 1887 */       call.endNonCVDObservers(jtapiCause);
/*      */     }
/*      */ 
/* 1891 */     call.staleObsCleanup(jtapiCause);
/*      */   }
/*      */ 
/*      */   void doAgentEvents(int eventType, Object monitored, CSTAExtendedDeviceID subjectDeviceID, String agentID, String agentGroup, String agentPassword, int agentState, Object privateData)
/*      */   {
/* 1898 */     Vector eventList = new Vector();
/*      */ 
/* 1900 */     TSDevice subjectDevice = this.provider.createDevice(subjectDeviceID);
/*      */ 
/* 1902 */     if (subjectDevice == null)
/*      */     {
/* 1904 */       if (monitored instanceof TSDevice)
/*      */       {
/* 1906 */         subjectDevice = (TSDevice)monitored;
/*      */       }
/*      */       else
/*      */       {
/* 1910 */         return;
/*      */       }
/*      */     }
/*      */ 
/* 1914 */     if ((agentID == null) && (agentState != 2))
/* 1915 */       agentID = subjectDevice.getAgentID();
/* 1916 */     TSAgentKey agentKey = new TSAgentKey(subjectDevice.getName(), agentGroup, agentID);
/* 1917 */     TSAgent agent = this.provider.createAgent(agentKey, agentGroup, agentPassword, TSProviderImpl.CREATEAGENT_REFUSE_DELETED);
/*      */ 
/* 1924 */     if (agent == null)
/*      */     {
/* 1926 */       return;
/*      */     }
/*      */ 
/* 1929 */     int workMode = 0;
/* 1930 */     int reasonCode = 0;
/*      */ 
/* 1932 */     if (privateData instanceof LucentLoggedOnEvent);
/* 1934 */     switch (((LucentLoggedOnEvent)privateData).getWorkMode())
/*      */     {
/*      */     case 3:
/* 1937 */       workMode = 1;
/* 1938 */       break;
/*      */     case 4:
/* 1940 */       workMode = 2;
/* 1941 */       break;
/*      */     default:
/* 1943 */       workMode = 0;
/* 1944 */       break label205:
/*      */ 
/* 1947 */       if (privateData instanceof LucentLoggedOffEvent)
/*      */       {
/* 1949 */         reasonCode = ((LucentLoggedOffEvent)privateData).getReasonCode();
/*      */       }
/* 1951 */       else if (privateData instanceof LucentAgentModeChangeEvent)
/*      */       {
/* 1953 */         reasonCode = ((LucentAgentModeChangeEvent)privateData).getReasonCode();
/*      */       }
/*      */     }
/*      */ 
/* 1957 */     label205: agent.updateState(agentState, workMode, reasonCode, eventList);
/*      */ 
/* 1959 */     if (privateData instanceof LucentLoggedOnEvent)
/*      */     {
/* 1961 */       switch (((LucentLoggedOnEvent)privateData).getWorkMode())
/*      */       {
/*      */       case 3:
/*      */       case 4:
/* 1965 */         agentState = 4;
/* 1966 */         break;
/*      */       default:
/* 1968 */         agentState = 3;
/*      */       }
/*      */ 
/* 1971 */       agent.updateState(agentState, workMode, reasonCode, eventList);
/*      */     }
/*      */ 
/* 1974 */     doAgentMonitors(agent, eventList, privateData);
/*      */   }
/*      */ 
/*      */   void doAgentMonitors(TSAgent agent, Vector<TSEvent> eventList, Object privateData)
/*      */   {
/* 1980 */     if ((eventList.size() == 0) && 
/* 1983 */       (privateData == null))
/*      */     {
/* 1985 */       return;
/*      */     }
/*      */ 
/* 1990 */     if (privateData != null)
/*      */     {
/* 1993 */       for (int i = 0; i < eventList.size(); ++i)
/*      */       {
/* 1995 */         TSEvent ev = (TSEvent)eventList.elementAt(i);
/* 1996 */         if (ev.getPrivateData() != null)
/*      */           continue;
/* 1998 */         ev.setPrivateData(privateData);
/*      */       }
/*      */ 
/* 2001 */       if (!this.provider.isLucent()) {
/* 2002 */         eventList.addElement(new TSEvent(9999, agent, privateData, this.provider));
/*      */       }
/*      */     }
/* 2005 */     Vector observers = null;
/*      */ 
/* 2007 */     TSDevice acdDevice = agent.getTSACDDevice();
/* 2008 */     if (acdDevice != null)
/*      */     {
/* 2010 */       observers = acdDevice.getAddressObservers();
/* 2011 */       for (int j = 0; j < observers.size(); ++j)
/*      */       {
/* 2013 */         TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 2014 */         callback.deliverEvents(eventList, false);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2020 */       Vector skillsVector = agent.getSkillsVector();
/* 2021 */       for (int i = 0; i < skillsVector.size(); ++i)
/*      */       {
/* 2023 */         TSDevice skillDevice = (TSDevice)skillsVector.elementAt(i);
/* 2024 */         observers = skillDevice.getAddressObservers();
/* 2025 */         for (int j = 0; j < eventList.size(); ++j)
/*      */         {
/* 2027 */           TSEvent ev = (TSEvent)eventList.elementAt(j);
/* 2028 */           Object tsTarget = ev.getEventTarget();
/* 2029 */           if (!(tsTarget instanceof TSAgent))
/*      */             continue;
/* 2031 */           ev.setSkillDevice(skillDevice);
/*      */         }
/*      */ 
/* 2034 */         for (int j = 0; j < observers.size(); ++j)
/*      */         {
/* 2036 */           TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 2037 */           callback.deliverEvents(eventList, false);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2042 */     Vector terminalObservers = agent.getTerminalObservers();
/*      */ 
/* 2044 */     for (int j = 0; j < terminalObservers.size(); ++j)
/*      */     {
/* 2046 */       TsapiTerminalMonitor callback = (TsapiTerminalMonitor)terminalObservers.elementAt(j);
/* 2047 */       callback.deliverEvents(eventList, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   void doDeviceEvents(int eventType, Object monitored, CSTAExtendedDeviceID subjectDeviceID, boolean state, CSTAForwardingInfo fwdInfo, Object privateData)
/*      */   {
/* 2056 */     Vector eventList = new Vector();
/* 2057 */     TSDevice subjectDevice = this.provider.createDevice(subjectDeviceID);
/*      */ 
/* 2059 */     if (subjectDevice == null)
/*      */     {
/* 2061 */       if (monitored instanceof TSDevice)
/*      */       {
/* 2063 */         subjectDevice = (TSDevice)monitored;
/*      */       }
/*      */       else
/*      */       {
/* 2067 */         return;
/*      */       }
/*      */     }
/*      */ 
/* 2071 */     switch (eventType)
/*      */     {
/*      */     case 69:
/* 2074 */       subjectDevice.updateDNDState(state, eventList);
/* 2075 */       break;
/*      */     case 71:
/* 2077 */       int newBits = 0;
/* 2078 */       if (privateData instanceof LucentQueryMwiConfEvent)
/*      */       {
/* 2080 */         LucentQueryMwiConfEvent luPrivData = (LucentQueryMwiConfEvent)privateData;
/* 2081 */         newBits = luPrivData.getApplicationType();
/*      */       }
/* 2083 */       else if (state)
/*      */       {
/* 2086 */         newBits = -1;
/*      */       }
/* 2088 */       subjectDevice.updateMessageWaitingBits(newBits, eventList);
/* 2089 */       break;
/*      */     case 70:
/* 2091 */       CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];
/* 2092 */       fwdArray[0] = fwdInfo;
/* 2093 */       subjectDevice.updateForwarding(fwdArray, eventList);
/*      */     }
/*      */ 
/* 2097 */     doDeviceMonitors(subjectDevice, eventList, privateData);
/*      */   }
/*      */ 
/*      */   void doDeviceMonitors(TSDevice device, Vector<TSEvent> eventList, Object privateData)
/*      */   {
/* 2103 */     if ((eventList.size() == 0) && 
/* 2106 */       (privateData == null))
/*      */     {
/* 2108 */       return;
/*      */     }
/*      */ 
/* 2113 */     if (privateData != null)
/*      */     {
/* 2116 */       for (int i = 0; i < eventList.size(); ++i)
/*      */       {
/* 2118 */         TSEvent ev = (TSEvent)eventList.elementAt(i);
/* 2119 */         if (ev.getPrivateData() != null)
/*      */           continue;
/* 2121 */         ev.setPrivateData(privateData);
/*      */       }
/*      */ 
/* 2124 */       if (!this.provider.isLucent()) {
/* 2125 */         eventList.addElement(new TSEvent(9999, device, privateData, this.provider));
/*      */       }
/*      */     }
/*      */ 
/* 2129 */     Vector observers = device.getAddressObservers();
/*      */ 
/* 2131 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 2133 */       TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 2134 */       callback.deliverEvents(eventList, false);
/*      */     }
/*      */ 
/* 2138 */     Vector terminalObservers = device.getTerminalObservers();
/*      */ 
/* 2140 */     for (int j = 0; j < terminalObservers.size(); ++j)
/*      */     {
/* 2142 */       TsapiTerminalMonitor callback = (TsapiTerminalMonitor)terminalObservers.elementAt(j);
/* 2143 */       callback.deliverEvents(eventList, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   void doMonitorEnded(int cause, int xrefID, Object monitored, Object privateData)
/*      */   {
/* 2149 */     int jtapiCause = getJtapiCause(cause);
/*      */ 
/* 2151 */     log.info("Monitor Ended, jtapiCause = " + jtapiCause);
/*      */ 
/* 2155 */     if (monitored instanceof TSDevice)
/*      */     {
/* 2157 */       ((TSDevice)monitored).removeObservers(jtapiCause, privateData, xrefID);
/*      */     } else {
/* 2159 */       if (!(monitored instanceof TSCall))
/*      */         return;
/* 2161 */       ((TSCall)monitored).removeObservers(jtapiCause, privateData, xrefID);
/*      */     }
/*      */   }
/*      */ 
/*      */   void doConfXfer(int jtapiCause, CSTAConnectionID primaryConnID, CSTAConnectionID secondaryConnID, CSTAConnection[] connList, Object privateData, short cause)
/*      */   {
/* 2170 */     if ((connList == null) || (connList.length == 0))
/*      */     {
/* 2172 */       return;
/*      */     }
/*      */ 
/* 2175 */     TSCall call = null;
/* 2176 */     TSCall secCall = null;
/*      */ 
/* 2180 */     if ((this.provider.isLucentV5()) && (primaryConnID != null) && (primaryConnID.getCallID() == 0))
/*      */     {
/* 2182 */       primaryConnID = null;
/*      */     }
/* 2184 */     if ((this.provider.isLucentV5()) && (secondaryConnID != null) && (secondaryConnID.getCallID() == 0))
/*      */     {
/* 2186 */       secondaryConnID = null;
/*      */     }
/*      */ 
/* 2189 */     if (connList.length > 0)
/*      */     {
/* 2193 */       call = this.provider.createCall(connList[0].getParty().getCallID(), privateData);
/*      */ 
/* 2197 */       call = this.provider.validateCall(privateData, call, connList[0].getParty().getCallID());
/*      */ 
/* 2199 */       if (primaryConnID == null)
/*      */       {
/* 2201 */         if ((secondaryConnID != null) && (connList[0].getParty().getCallID() != secondaryConnID.getCallID()) && 
/* 2204 */           (call.getTSState() == 33))
/*      */         {
/* 2206 */           return;
/*      */         }
/*      */ 
/*      */       }
/* 2210 */       else if (secondaryConnID == null)
/*      */       {
/* 2212 */         if ((primaryConnID != null) && (connList[0].getParty().getCallID() != primaryConnID.getCallID()) && 
/* 2215 */           (call.getTSState() == 33))
/*      */         {
/* 2217 */           return;
/*      */         }
/*      */ 
/*      */       }
/* 2221 */       else if ((connList[0].getParty().getCallID() != primaryConnID.getCallID()) && (connList[0].getParty().getCallID() != secondaryConnID.getCallID()) && 
/* 2224 */         (call.getTSState() == 33))
/*      */       {
/* 2226 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2232 */     if (primaryConnID != null)
/*      */     {
/* 2236 */       call = this.provider.createCall(primaryConnID.getCallID(), privateData);
/*      */ 
/* 2240 */       call = this.provider.validateCall(privateData, call, primaryConnID.getCallID());
/*      */     }
/*      */ 
/* 2243 */     if (secondaryConnID != null)
/*      */     {
/* 2247 */       secCall = this.provider.createCall(secondaryConnID.getCallID(), privateData);
/*      */ 
/* 2251 */       secCall = this.provider.validateCall(privateData, secCall, secondaryConnID.getCallID());
/*      */     }
/*      */ 
/* 2255 */     if (call == secCall)
/*      */     {
/* 2257 */       secCall = null;
/*      */     }
/*      */ 
/* 2261 */     if ((call != null) && (call.getTSState() == 34) && (secCall != null) && (secCall.getTSState() == 34))
/*      */     {
/* 2264 */       return;
/*      */     }
/*      */ 
/* 2270 */     boolean swapCalls = false;
/* 2271 */     if ((secCall != null) && (secCall.getSnapshotCallConfPending()))
/*      */     {
/* 2278 */       log.info("a snapshot call is pending for call " + secCall + " call id =" + secCall.callID);
/* 2279 */       swapCalls = true;
/*      */     }
/* 2281 */     else if ((call != null) && (call.getCallObservers().size() == 0) && (secCall != null) && (secCall.getCallObservers().size() > 0))
/*      */     {
/* 2285 */       swapCalls = true;
/*      */     }
/* 2287 */     else if ((call == null) || (call.getCallObservers().size() <= 0) || (secCall == null) || (secCall.getCallObservers().size() != 0))
/*      */     {
/* 2293 */       if ((call == null) || (secCall != null))
/*      */       {
/* 2300 */         if ((call != null) && (call.doHeldTalkingMatch(secCall)))
/*      */         {
/* 2303 */           swapCalls = true;
/*      */         }
/* 2305 */         else if ((secCall == null) || (secCall.getTSState() != 34))
/*      */         {
/* 2310 */           if ((call != null) && (call.getTSState() == 34))
/*      */           {
/* 2313 */             swapCalls = true;
/*      */           }
/* 2315 */           else if ((call == null) && (secCall != null))
/*      */           {
/* 2318 */             swapCalls = true;
/*      */           } else {
/* 2320 */             if (call == null)
/*      */             {
/* 2322 */               return;
/*      */             }
/* 2324 */             if ((secCall != null) && (secCall.getCallID() == connList[0].getParty().getCallID()))
/*      */             {
/* 2341 */               swapCalls = true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2349 */     if (swapCalls)
/*      */     {
/* 2351 */       TSCall tempCall = call;
/* 2352 */       call = secCall;
/* 2353 */       secCall = tempCall;
/*      */     }
/*      */ 
/* 2359 */     call.setCSTACause(cause);
/*      */ 
/* 2361 */     Vector oldConns = null;
/* 2362 */     Vector oldSecConns = null;
/*      */ 
/* 2364 */     oldConns = call.getConnections();
/* 2365 */     if (connList.length > 0)
/*      */     {
/* 2367 */       call.setCallID(connList[0].getParty().getCallID());
/* 2368 */       call = call.getHandOff();
/*      */     }
/* 2370 */     call.setNeedSnapshot(false);
/* 2371 */     if (secCall != null)
/*      */     {
/* 2373 */       oldSecConns = secCall.getConnections();
/*      */     }
/*      */ 
/* 2377 */     Vector priEventList = new Vector();
/* 2378 */     Vector secEventList = new Vector();
/*      */ 
/* 2380 */     TSConnection conn = null;
/* 2381 */     TSConnection secConn = null;
/* 2382 */     TSConnection tc = null;
/*      */ 
/* 2384 */     Vector newConnections = new Vector();
/* 2385 */     TSDevice device = null;
/*      */ 
/* 2387 */     Vector snapConnections = new Vector();
/*      */ 
/* 2395 */     for (int i = 0; i < connList.length; ++i)
/*      */     {
/* 2397 */       boolean found = false;
/* 2398 */       device = this.provider.createDevice(connList[i].getStaticDevice(), connList[i].getParty());
/* 2399 */       if (device == null) {
/*      */         continue;
/*      */       }
/*      */ 
/* 2403 */       if (oldConns != null)
/*      */       {
/* 2405 */         Vector oldConnections = new Vector(oldConns);
/* 2406 */         for (int j = 0; j < oldConnections.size(); ++j)
/*      */         {
/* 2408 */           conn = (TSConnection)oldConnections.elementAt(j);
/* 2409 */           Vector cv = conn.getTermConns();
/* 2410 */           if ((cv != null) && (cv.size() > 0))
/*      */           {
/* 2412 */             Vector termConns = new Vector(cv);
/* 2413 */             for (int k = 0; k < termConns.size(); ++k)
/*      */             {
/* 2415 */               tc = (TSConnection)termConns.elementAt(k);
/* 2416 */               if (tc.getTSDevice() != device)
/*      */                 continue;
/* 2418 */               tc.setConnID(connList[i].getParty());
/* 2419 */               newConnections.addElement(tc);
/* 2420 */               found = true;
/* 2421 */               int tcs = tc.getCallControlTermConnState();
/* 2422 */               if (tcs != 96) {
/*      */                 break;
/*      */               }
/* 2425 */               if (this.provider.getCapabilities().getSnapshotCallReq() != 0)
/*      */               {
/* 2427 */                 snapConnections.addElement(tc.getTSConn());
/*      */               }
/*      */ 
/* 2432 */               tc.setConnectionState(91, null);
/* 2433 */               tc.setTermConnState(103, null); break;
/*      */             }
/*      */ 
/*      */           }
/* 2442 */           else if (conn.getTSDevice() == device)
/*      */           {
/*      */             try
/*      */             {
/* 2455 */               conn.setConnID(connList[i].getParty());
/*      */             }
/*      */             catch (TsapiPlatformException e)
/*      */             {
/* 2459 */               log.error("TSEventHandler.doConfXfer() caught TsapiPlatformException from setConnID() while processing connList, i=" + i + ", j=" + j + ", party=" + connList[i].getParty());
/* 2460 */               log.error(e.getMessage(), e);
/* 2461 */               log.trace("Dumping call (" + call + "):");
/* 2462 */               call.dump("   ");
/* 2463 */               log.trace("Dumping conn (" + conn + "):");
/* 2464 */               conn.dump("   ");
/* 2465 */               log.trace("Dumping provider (" + this.provider + "):");
/* 2466 */               this.provider.dump("   ");
/*      */ 
/* 2468 */               throw e;
/*      */             }
/*      */ 
/* 2471 */             newConnections.addElement(conn);
/* 2472 */             found = true;
/* 2473 */             int cs = conn.getCallControlConnState();
/* 2474 */             if (cs != 80) {
/*      */               break;
/*      */             }
/* 2477 */             if (this.provider.getCapabilities().getSnapshotCallReq() != 0)
/*      */             {
/* 2479 */               snapConnections.addElement(conn.getTSConn());
/*      */             }
/*      */ 
/* 2484 */             conn.setConnectionState(91, null);
/* 2485 */             conn.setTermConnState(103, null); break;
/*      */           }
/*      */ 
/* 2491 */           if (found) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2497 */       if (found)
/*      */         continue;
/* 2499 */       Vector tempEventList = new Vector();
/*      */ 
/* 2501 */       conn = this.provider.createTerminalConnection(connList[i].getParty(), device, tempEventList, device);
/*      */ 
/* 2503 */       int oldConnState = conn.getCallControlConnState();
/* 2504 */       int oldTermConnState = conn.getCallControlTermConnState();
/*      */ 
/* 2506 */       if ((oldConnState == 89) || (oldTermConnState == 102))
/*      */       {
/* 2510 */         tempEventList = new Vector();
/*      */ 
/* 2512 */         conn.delete();
/* 2513 */         this.provider.dumpConn(connList[i].getParty());
/*      */ 
/* 2515 */         conn = this.provider.createTerminalConnection(connList[i].getParty(), device, tempEventList, device);
/*      */       }
/*      */ 
/* 2518 */       if (oldSecConns != null)
/*      */       {
/* 2520 */         Vector oldSecConnections = new Vector(oldSecConns);
/* 2521 */         for (int j = 0; j < oldSecConnections.size(); ++j)
/*      */         {
/* 2523 */           secConn = (TSConnection)oldSecConnections.elementAt(j);
/* 2524 */           Vector cv = secConn.getTermConns();
/* 2525 */           if ((cv != null) && (cv.size() > 0))
/*      */           {
/* 2527 */             Vector termConns = new Vector(cv);
/* 2528 */             for (int k = 0; k < termConns.size(); ++k)
/*      */             {
/* 2530 */               tc = (TSConnection)termConns.elementAt(k);
/* 2531 */               if (conn.getTSDevice() != tc.getTSDevice())
/*      */                 continue;
/* 2533 */               for (int m = 0; m < tempEventList.size(); ++m)
/*      */               {
/* 2535 */                 priEventList.addElement(tempEventList.elementAt(m));
/*      */               }
/* 2537 */               conn.setConnectionState(tc.getCallControlConnState(), priEventList);
/* 2538 */               conn.setTermConnState(tc.getCallControlTermConnState(), priEventList);
/* 2539 */               found = true;
/* 2540 */               break;
/*      */             }
/*      */ 
/*      */           }
/* 2546 */           else if (conn.getTSDevice() == secConn.getTSDevice())
/*      */           {
/* 2548 */             for (int m = 0; m < tempEventList.size(); ++m)
/*      */             {
/* 2550 */               priEventList.addElement(tempEventList.elementAt(m));
/*      */             }
/* 2552 */             conn.setConnectionState(secConn.getCallControlConnState(), priEventList);
/* 2553 */             conn.setTermConnState(secConn.getCallControlTermConnState(), priEventList);
/* 2554 */             found = true;
/* 2555 */             break;
/*      */           }
/*      */ 
/* 2558 */           if (found) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2564 */       if (!found)
/*      */       {
/* 2567 */         if (this.provider.getCapabilities().getSnapshotCallReq() != 0)
/*      */         {
/* 2569 */           snapConnections.addElement(conn.getTSConn());
/*      */         }
/*      */ 
/* 2574 */         for (int m = 0; m < tempEventList.size(); ++m)
/*      */         {
/* 2576 */           priEventList.addElement(tempEventList.elementAt(m));
/*      */         }
/* 2578 */         conn.setConnectionState(91, priEventList);
/* 2579 */         conn.setTermConnState(103, priEventList);
/*      */       }
/*      */ 
/* 2583 */       newConnections.addElement(conn);
/*      */     }
/*      */ 
/* 2587 */     Vector eventList = new Vector();
/*      */ 
/* 2589 */     call.replaceConnections(newConnections, eventList);
/* 2590 */     for (int m = 0; m < priEventList.size(); ++m)
/*      */     {
/* 2592 */       eventList.addElement(priEventList.elementAt(m));
/*      */     }
/*      */ 
/* 2599 */     if (secCall != null)
/*      */     {
/* 2601 */       if (swapCalls) {
/* 2602 */         secCall.delayVDNremoveCallFromDomain = true;
/* 2603 */         secCall.setState(34, secEventList);
/* 2604 */         secCall.delayVDNremoveCallFromDomain = false;
/* 2605 */         call.copyStuff(secCall);
/*      */       }
/*      */       else {
/* 2608 */         secCall.setState(34, secEventList);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2613 */     doCallMonitors(secCall, secEventList, jtapiCause, privateData);
/*      */ 
/* 2620 */     TSDevice distributingDevice = null;
/* 2621 */     TSDevice distributingVDN = null;
/* 2622 */     if (privateData instanceof LucentConferencedEvent)
/*      */     {
/* 2624 */       LucentConferencedEvent luPrivData = (LucentConferencedEvent)privateData;
/* 2625 */       TsapiAddress address = TsapiPromoter.promoteDeviceIDToAddress(this.provider, luPrivData.getDistributingDevice_asn());
/*      */ 
/* 2627 */       if (address != null)
/*      */       {
/* 2629 */         distributingDevice = address.getTSDevice();
/* 2630 */         call.setDistributingDevice(distributingDevice);
/*      */       }
/* 2632 */       call.setOCI(luPrivData.getOriginalCallInfo());
/* 2633 */       if (privateData instanceof LucentV5ConferencedEvent)
/*      */       {
/* 2635 */         LucentV5ConferencedEvent luV5PrivData = (LucentV5ConferencedEvent)privateData;
/* 2636 */         call.setUCID(luV5PrivData.getUcid());
/*      */ 
/* 2638 */         if (privateData instanceof LucentV7ConferencedEvent)
/*      */         {
/* 2640 */           LucentV7ConferencedEvent luV7PrivData = (LucentV7ConferencedEvent)privateData;
/* 2641 */           call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */ 
/* 2643 */           if (luV7PrivData.getDistributingVDN_asn() != null)
/*      */           {
/* 2645 */             distributingVDN = TsapiPromoter.promoteExtendedDeviceIDToTSDevice(this.provider, luV7PrivData.getDistributingVDN_asn());
/*      */ 
/* 2651 */             call.setDistributingVDN(distributingVDN);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/* 2657 */     else if (privateData instanceof LucentTransferredEvent)
/*      */     {
/* 2659 */       LucentTransferredEvent luPrivData = (LucentTransferredEvent)privateData;
/* 2660 */       TsapiAddress address = TsapiPromoter.promoteDeviceIDToAddress(this.provider, luPrivData.getDistributingDevice_asn());
/*      */ 
/* 2662 */       if (address != null)
/*      */       {
/* 2664 */         distributingDevice = address.getTSDevice();
/* 2665 */         call.setDistributingDevice(distributingDevice);
/*      */       }
/* 2667 */       call.setOCI(luPrivData.getOriginalCallInfo());
/* 2668 */       if (privateData instanceof LucentV5TransferredEvent)
/*      */       {
/* 2670 */         LucentV5TransferredEvent luV5PrivData = (LucentV5TransferredEvent)privateData;
/* 2671 */         call.setUCID(luV5PrivData.getUcid());
/*      */ 
/* 2673 */         if (privateData instanceof LucentV7TransferredEvent)
/*      */         {
/* 2675 */           LucentV7TransferredEvent luV7PrivData = (LucentV7TransferredEvent)privateData;
/* 2676 */           call.setDeviceHistory(TsapiPromoter.promoteDeviceHistory(luV7PrivData.getDeviceHistory()));
/*      */ 
/* 2678 */           if (luV7PrivData.getDistributingVDN_asn() != null)
/*      */           {
/* 2680 */             distributingVDN = TsapiPromoter.promoteExtendedDeviceIDToTSDevice(this.provider, luV7PrivData.getDistributingVDN_asn());
/*      */ 
/* 2686 */             call.setDistributingVDN(distributingVDN);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2693 */     if (privateData instanceof LucentTrunkConnectionMapping)
/*      */     {
/* 2696 */       CSTATrunkInfo[] trunkList = ((LucentTrunkConnectionMapping)privateData).getLucentTrunkInfo();
/*      */ 
/* 2698 */       LucentTrunkInfoMapItem[] trunkMapItems = LucentTrunkInfoMapItem.createLucentTrunkInfoMapItemArray(trunkList, this.provider);
/*      */ 
/* 2701 */       if (trunkMapItems != null)
/*      */       {
/* 2703 */         for (int i = 0; i < trunkMapItems.length; ++i)
/*      */         {
/* 2705 */           LucentTrunkInfoMapItem item = trunkMapItems[i];
/* 2706 */           if (item == null)
/*      */             continue;
/* 2708 */           item.interLinkConnectionCallAndTrunk(eventList);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2716 */     call.moveStuff(secCall);
/*      */ 
/* 2718 */     if (secCall != null) {
/* 2719 */       secCall.setStateForVDN();
/*      */     }
/* 2721 */     if (snapConnections.size() > 0)
/*      */     {
/* 2723 */       call.setNeedSnapshot(true);
/*      */ 
/* 2725 */       SnapshotCallExtraConfHandler handler = new XferConfSnapshotCallConfHandler(this, call, jtapiCause, privateData, snapConnections);
/*      */ 
/* 2727 */       call.doSnapshot(((TSConnection)snapConnections.elementAt(0)).getConnID(), handler, false);
/*      */     }
/*      */     TransferredEventParams transferredEventParams;
/* 2731 */     if ((jtapiCause == 212) || (jtapiCause == 207)) {
/* 2732 */       TSCall call1 = null;
/* 2733 */       TSCall call2 = null;
/* 2734 */       if (primaryConnID != null)
/* 2735 */         call1 = this.provider.findCall(primaryConnID.getCallID());
/* 2736 */       if (secondaryConnID != null)
/* 2737 */         call2 = this.provider.findCall(secondaryConnID.getCallID());
/* 2738 */       ArrayList callList = new ArrayList();
/* 2739 */       if (call1 != null)
/* 2740 */         callList.add(call1);
/* 2741 */       if (call2 != null)
/* 2742 */         callList.add(call2);
/* 2743 */       transferredEventParams = new TransferredEventParams(callList);
/* 2744 */       for (Object ev : eventList) {
/* 2745 */         ((TSEvent) ev).setTransferredEventParams(transferredEventParams);
/*      */       }
/*      */     }
/* 2748 */     doCallMonitors(call, eventList, jtapiCause, privateData);
/*      */ 
/* 2759 */     if (call.checkForMonitors())
/*      */       return;
/* 2761 */     call.setNeedSnapshot(true);
/*      */   }
/*      */ 
/*      */   int getJtapiCause(int cstaCause)
/*      */   {
/* 2768 */     switch (cstaCause) { case -1:
/*      */     case 12:
/*      */     case 17:
/*      */     case 22:
/*      */     case 24:
/*      */     case 27:
/*      */     case 31:
/*      */     case 34:
/* 2779 */       return 100;
/*      */     case 1:
/* 2782 */       return 207;
/*      */     case 2:
/* 2784 */       return 202;
/*      */     case 3:
/* 2786 */       return 203;
/*      */     case 4:
/* 2788 */       return 204;
/*      */     case 5:
/* 2790 */       return 102;
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/*      */     case 9:
/*      */     case 28:
/* 2796 */       return 210;
/*      */     case 10:
/* 2798 */       return 205;
/*      */     case 11:
/* 2800 */       return 206;
/*      */     case 13:
/*      */     case 16:
/*      */     case 19:
/*      */     case 46:
/*      */     case 60:
/*      */     case 65:
/*      */     case 80:
/*      */     case 81:
/*      */     case 87:
/* 2810 */       return 103;
/*      */     case 14:
/* 2812 */       return 208;
/*      */     case 15:
/* 2814 */       return 104;
/*      */     case 18:
/* 2816 */       return 105;
/*      */     case 20:
/* 2818 */       return 108;
/*      */     case 21:
/* 2820 */       return 109;
/*      */     case 23:
/* 2822 */       return 302;
/*      */     case 25:
/* 2824 */       return 209;
/*      */     case 26:
/*      */     case 30:
/* 2827 */       return 107;
/*      */     case 29:
/* 2829 */       return 211;
/*      */     case 32:
/*      */     case 52:
/* 2832 */       return 212;
/*      */     case 33:
/* 2834 */       return 213;
/*      */     case 0:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/*      */     case 40:
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 45:
/*      */     case 47:
/*      */     case 48:
/*      */     case 49:
/*      */     case 50:
/*      */     case 51:
/*      */     case 53:
/*      */     case 54:
/*      */     case 55:
/*      */     case 56:
/*      */     case 57:
/*      */     case 58:
/*      */     case 59:
/*      */     case 61:
/*      */     case 62:
/*      */     case 63:
/*      */     case 64:
/*      */     case 66:
/*      */     case 67:
/*      */     case 68:
/*      */     case 69:
/*      */     case 70:
/*      */     case 71:
/*      */     case 72:
/*      */     case 73:
/*      */     case 74:
/*      */     case 75:
/*      */     case 76:
/*      */     case 77:
/*      */     case 78:
/*      */     case 79:
/*      */     case 82:
/*      */     case 83:
/*      */     case 84:
/*      */     case 85:
/*      */     case 86: } return 101;
/*      */   }
/*      */ 
/*      */   public void eventDistributorException(Exception e)
/*      */   {
/* 2841 */     if (log != null) {
/* 2842 */       log.error("Event Distributor Exception - shutting down provider " + this.provider);
/* 2843 */       log.error(e.getMessage(), e);
/*      */     }
/*      */     else {
/* 2846 */       System.out.println("Event Distributor Exception - shutting down provider " + this.provider);
/* 2847 */       e.printStackTrace();
/*      */     }
/* 2849 */     this.provider.shutdown();
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2856 */     return this.provider.toString();
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSEventHandler
 * JD-Core Version:    0.5.4
 */