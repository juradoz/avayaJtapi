/*     */ package com.avaya.jtapi.tsapi.impl.monitor;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
/*     */ import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSAgent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.PrivateDataEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.AgentTerminalEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.CallControlTerminalEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventParams;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiPrivateTerminalEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermBusyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermLogOffEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermLogOnEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermNotReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermObservationEndedEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermWorkNotReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermWorkReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTerminalDNDEvent;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import java.util.BitSet;
/*     */ import java.util.Vector;
/*     */ import javax.telephony.Event;
/*     */ import javax.telephony.Terminal;
/*     */ import javax.telephony.TerminalEvent;
/*     */ import javax.telephony.TerminalListener;
/*     */ import javax.telephony.TerminalObserver;
/*     */ import javax.telephony.callcenter.Agent;
/*     */ import javax.telephony.callcenter.AgentTerminalEvent;
/*     */ import javax.telephony.callcenter.AgentTerminalListener;
/*     */ import javax.telephony.callcenter.AgentTerminalObserver;
/*     */ import javax.telephony.callcontrol.CallControlTerminalEvent;
/*     */ import javax.telephony.callcontrol.CallControlTerminalListener;
/*     */ import javax.telephony.callcontrol.CallControlTerminalObserver;
/*     */ import javax.telephony.events.TermEv;
/*     */ import javax.telephony.phone.PhoneTerminalObserver;
/*     */ import javax.telephony.privatedata.PrivateDataEvent;
/*     */ import javax.telephony.privatedata.PrivateDataTerminalListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class TsapiTerminalMonitor
/*     */   implements TsapiMonitor
/*     */ {
/*  54 */   private static Logger log = Logger.getLogger(TsapiTerminalMonitor.class);
/*     */   TSProviderImpl provider;
/*  57 */   TerminalObserver observer = null;
/*  58 */   TerminalListener terminalListener = null;
/*  59 */   private final Vector<TermEv> eventList = new Vector();
/*  60 */   private final Vector<Event> listenerEventList = new Vector();
/*     */ 
/*  62 */   long reference = 0L;
/*  63 */   BitSet observerType = new BitSet(8);
/*     */ 
/* 613 */   Object syncObject = new Object();
/*     */ 
/*     */   public void dump(String indent)
/*     */   {
/*  67 */     log.trace(indent + "***** TsapiTerminalMonitor DUMP *****");
/*  68 */     log.trace(indent + "TsapiTerminalMonitor: " + this);
/*  69 */     if (this.observer != null)
/*  70 */       log.trace(indent + "observer: " + this.observer);
/*     */     else
/*  72 */       log.trace(indent + "listener: " + this.terminalListener);
/*  73 */     log.trace(indent + "***** TsapiTerminalMonitor DUMP END *****");
/*     */   }
/*     */ 
/*     */   public TsapiTerminalMonitor(TSProviderImpl _provider, TerminalObserver _observer)
/*     */   {
/*  80 */     this.provider = _provider;
/*  81 */     this.observer = _observer;
/*     */ 
/*  83 */     this.provider.addTerminalMonitorThread(this);
/*     */ 
/*  85 */     this.observerType.set(0);
/*  86 */     if (this.observer instanceof CallControlTerminalObserver)
/*     */     {
/*  88 */       this.observerType.set(1);
/*     */     }
/*  90 */     if (this.observer instanceof AgentTerminalObserver)
/*     */     {
/*  92 */       this.observerType.set(2);
/*     */     }
/*  94 */     if (this.observer instanceof PhoneTerminalObserver)
/*     */     {
/*  96 */       this.observerType.set(4);
/*     */     }
/*     */ 
/* 101 */     this.observerType.set(5);
/*     */ 
/* 105 */     deliverEvents(null, false);
/*     */   }
/*     */ 
/*     */   public TsapiTerminalMonitor(TSProviderImpl _provider, TerminalListener _listener)
/*     */   {
/* 111 */     this.provider = _provider;
/* 112 */     this.terminalListener = _listener;
/*     */ 
/* 114 */     this.provider.addTerminalMonitorThread(this);
/*     */ 
/* 117 */     deliverEvents(null, false);
/*     */   }
/*     */ 
/*     */   public TerminalObserver getObserver()
/*     */   {
/* 122 */     return this.observer;
/*     */   }
/*     */ 
/*     */   public TerminalListener getListener()
/*     */   {
/* 127 */     return this.terminalListener;
/*     */   }
/*     */ 
/*     */   public synchronized void addReference() {
/* 131 */     this.reference += 1L;
/*     */   }
/*     */ 
/*     */   public void deleteReference(TSDevice device, int cause, Object privateData)
/*     */   {
/* 140 */     if (this.observer != null)
/* 141 */       log.debug("Getting TsapiTerminalMonitor lock to deliver deleteReference events for observer " + this.observer);
/*     */     else
/* 143 */       log.debug("Getting TsapiTerminalMonitor lock to deliver deleteReference events for listener " + this.terminalListener);
/* 144 */     deleteReferenceInternal(device, cause, privateData);
/*     */   }
/*     */ 
/*     */   private synchronized void deleteReferenceInternal(TSDevice device, int cause, Object privateData)
/*     */   {
/* 149 */     String tsEventLog = null;
/* 150 */     this.reference -= 1L;
/*     */ 
/* 152 */     if (this.observer != null)
/*     */     {
/* 155 */       log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.observer);
/* 156 */       tsEventLog = "OBSERVATIONENDEDEVENT for " + device;
/* 157 */       synchronized (this.eventList) {
/* 158 */         int nextMetaEventIndex = this.eventList.size();
/*     */ 
/* 161 */         addObserverEvent(new TsapiTermObservationEndedEvent(createTerminal(device), cause, privateData), tsEventLog);
/*     */ 
/* 163 */         ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/*     */       }
/* 165 */       if (privateData != null)
/*     */       {
/* 167 */         tsEventLog = "PRIVATEEVENT for " + device;
/* 168 */         addObserverEvent(new TsapiPrivateTerminalEvent(createTerminal(device), cause, 136, privateData), tsEventLog);
/*     */       }
/*     */ 
/* 171 */       log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
/*     */     }
/*     */     else {
/* 174 */       log.debug("meta event BEGIN: cause (" + cause + ") for " + this.terminalListener);
/* 175 */       tsEventLog = "OBSERVATIONENDEDEVENT for " + device;
/*     */ 
/* 177 */       this.listenerEventList.addElement(new TerminalEventImpl(121, cause, createTerminal(device), privateData));
/*     */ 
/* 180 */       log.debug(tsEventLog + " for listener " + this.terminalListener);
/* 181 */       if (privateData != null) {
/* 182 */         tsEventLog = "PRIVATEEVENT for " + device;
/* 183 */         addPrivateDataEvent(603, cause, createTerminal(device), privateData, tsEventLog);
/*     */       }
/*     */     }
/*     */ 
/* 187 */     if (this.reference <= 0L)
/*     */     {
/* 189 */       this.provider.removeTerminalMonitorThread(this);
/*     */     }
/*     */ 
/* 192 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   void addObserverEvent(TermEv event, String tsEventLog)
/*     */   {
/* 197 */     if ((this.observerType.get(0)) && (((ITsapiEvent)event).getEventPackage() == 0))
/*     */     {
/* 200 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 202 */       this.eventList.addElement(event);
/*     */     }
/* 204 */     else if ((this.observerType.get(1)) && (((ITsapiEvent)event).getEventPackage() == 1))
/*     */     {
/* 207 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 209 */       this.eventList.addElement(event);
/*     */     }
/* 211 */     else if ((this.observerType.get(2)) && (((ITsapiEvent)event).getEventPackage() == 2))
/*     */     {
/* 214 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 216 */       this.eventList.addElement(event);
/*     */     }
/* 218 */     else if ((this.observerType.get(4)) && (((ITsapiEvent)event).getEventPackage() == 4))
/*     */     {
/* 221 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 223 */       this.eventList.addElement(event);
/*     */     }
/* 225 */     else if ((this.observerType.get(5)) && (((ITsapiEvent)event).getEventPackage() == 5))
/*     */     {
/* 228 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 230 */       this.eventList.addElement(event);
/*     */     }
/*     */     else
/*     */     {
/* 234 */       log.debug(tsEventLog + " ignored");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addAgentTerminalEvent(TerminalEventParams terminalEventParams, Agent agent, String tsEventLog)
/*     */   {
/* 248 */     if (this.terminalListener instanceof AgentTerminalListener) {
/* 249 */       this.listenerEventList.addElement(new AgentTerminalEventImpl(terminalEventParams, agent));
/* 250 */       log.debug(tsEventLog + " for listener " + this.terminalListener);
/*     */     }
/*     */     else {
/* 253 */       log.debug(tsEventLog + " ignored since listener " + this.terminalListener + " is not of type AgentTerminalListener");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addCallControlTerminalEvent(TerminalEventParams terminalEventParams, boolean state, String tsEventLog)
/*     */   {
/* 266 */     if (this.terminalListener instanceof CallControlTerminalListener) {
/* 267 */       this.listenerEventList.addElement(new CallControlTerminalEventImpl(terminalEventParams, state));
/* 268 */       log.debug(tsEventLog + " for listener " + this.terminalListener);
/*     */     }
/*     */     else {
/* 271 */       log.debug(tsEventLog + " ignored since listener " + this.terminalListener + " is not of type CallControlTerminalListener");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addPrivateDataEvent(int eventId, int cause, Object source, Object privateData, String tsEventLog)
/*     */   {
/* 284 */     if (this.terminalListener instanceof PrivateDataTerminalListener) {
/* 285 */       this.listenerEventList.addElement(new PrivateDataEventImpl(eventId, cause, null, source, privateData));
/* 286 */       log.debug(tsEventLog + " for listener " + this.terminalListener);
/*     */     }
/*     */     else {
/* 289 */       log.debug(tsEventLog + " ignored since listener " + this.terminalListener + " is not of type PrivateDataTerminalListener");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 298 */     if (this.observer != null)
/* 299 */       log.debug("Getting TsapiTerminalMonitor lock to deliver events for observer " + this.observer);
/*     */     else
/* 301 */       log.debug("Getting TsapiTerminalMonitor lock to deliver events for listener " + this.terminalListener);
/* 302 */     if ((_eventList == null) || (_eventList.size() == 0)) {
/* 303 */       return;
/*     */     }
/* 305 */     synchronized (_eventList) {
/* 306 */       deliverEventsInternal(_eventList, snapshot);
/*     */     }
/*     */   }
/*     */ 
/*     */   private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 312 */     if (this.observer != null) {
/* 313 */       createObserverEvents(_eventList, snapshot);
/* 314 */       if (this.eventList.size() == 0)
/* 315 */         return;
/*     */     } else {
/* 317 */       createListenerEvents(_eventList, snapshot);
/* 318 */       if (this.listenerEventList.size() == 0)
/* 319 */         return;
/*     */     }
/* 321 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   private void createObserverEvents(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 331 */     String tsEventLog = null;
/* 332 */     if (_eventList == null)
/*     */     {
/* 334 */       return;
/*     */     }
/*     */     int cause;
/*     */     int metaCode;
/* 339 */     if (snapshot)
/*     */     {
/* 341 */       metaCode = 135;
/* 342 */       cause = 110;
/*     */     }
/*     */     else
/*     */     {
/* 346 */       metaCode = 136;
/* 347 */       cause = 100;
/*     */     }
/*     */ 
/* 350 */     int nextMetaEventIndex = this.eventList.size();
/*     */ 
/* 353 */     TSEvent ev = null;
/* 354 */     Object tsTarget = null;
/* 355 */     TSDevice target = null;
/* 356 */     TSAgent agent = null;
/* 357 */     Object privateData = null;
/* 358 */     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + metaCode + ")" + " for " + this.observer);
/* 359 */     for (int i = 0; i < _eventList.size(); ++i) {
/* 360 */       ev = (TSEvent)_eventList.elementAt(i);
/* 361 */       tsTarget = ev.getEventTarget();
/* 362 */       if (tsTarget instanceof TSDevice)
/*     */       {
/* 364 */         target = (TSDevice)tsTarget;
/*     */       }
/* 366 */       else if (tsTarget instanceof TSAgent)
/*     */       {
/* 368 */         agent = (TSAgent)tsTarget;
/* 369 */         target = agent.getTSAgentDevice();
/*     */       }
/*     */ 
/* 372 */       privateData = ev.getPrivateData();
/*     */ 
/* 374 */       switch (ev.getEventType())
/*     */       {
/*     */       case 58:
/* 377 */         tsEventLog = "TERMINALDONOTDISTURBEVENT for " + target;
/*     */ 
/* 379 */         addObserverEvent(new TsapiTerminalDNDEvent(createTerminal(target), target.dndState, cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 381 */         break;
/*     */       case 47:
/* 383 */         tsEventLog = "TERMINALLOGGEDONEVENT for " + agent;
/*     */ 
/* 385 */         addObserverEvent(new TsapiTermLogOnEv(createTerminal(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 387 */         break;
/*     */       case 48:
/* 389 */         tsEventLog = "TERMINALLOGGEDOFFEVENT for " + agent;
/*     */ 
/* 391 */         addObserverEvent(new TsapiTermLogOffEv(createTerminal(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 393 */         break;
/*     */       case 49:
/* 395 */         tsEventLog = "TERMINALREADYEVENT for " + agent;
/*     */ 
/* 397 */         addObserverEvent(new TsapiTermReadyEv(createTerminal(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 399 */         break;
/*     */       case 50:
/* 401 */         tsEventLog = "TERMINALNOTREADYEVENT for " + agent;
/*     */ 
/* 403 */         addObserverEvent(new TsapiTermNotReadyEv(createTerminal(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 405 */         break;
/*     */       case 51:
/* 407 */         tsEventLog = "TERMINALWORKREADYEVENT for " + agent;
/*     */ 
/* 409 */         addObserverEvent(new TsapiTermWorkReadyEv(createTerminal(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 411 */         break;
/*     */       case 52:
/* 413 */         tsEventLog = "TERMINALWORKNOTREADYEVENT for " + agent;
/*     */ 
/* 415 */         addObserverEvent(new TsapiTermWorkNotReadyEv(createTerminal(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 417 */         break;
/*     */       case 53:
/* 419 */         tsEventLog = "TERMINALBUSYEVENT for " + agent;
/*     */ 
/* 421 */         addObserverEvent(new TsapiTermBusyEv(createTerminal(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 423 */         break;
/*     */       case 9999:
/* 426 */         tsEventLog = "PRIVATEEVENT for " + target;
/*     */ 
/* 428 */         addObserverEvent(new TsapiPrivateTerminalEvent(createTerminal(target), cause, metaCode, privateData), tsEventLog);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 434 */     synchronized (this.eventList) {
/* 435 */       log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
/*     */ 
/* 438 */       if (this.eventList.size() == 0)
/*     */       {
/* 440 */         log.debug("no events to send to " + this.observer);
/* 441 */         return;
/*     */       }
/*     */ 
/* 445 */       if (nextMetaEventIndex < this.eventList.size())
/*     */       {
/* 448 */         ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createListenerEvents(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 460 */     String tsEventLog = null;
/* 461 */     if (_eventList == null)
/*     */     {
/* 463 */       return;
/*     */     }
/*     */     int cause;
/* 466 */     if (snapshot)
/* 467 */       cause = 110;
/*     */     else {
/* 469 */       cause = 100;
/*     */     }
/*     */ 
/* 472 */     TSEvent ev = null;
/* 473 */     Object tsTarget = null;
/* 474 */     TSDevice target = null;
/* 475 */     TSAgent targetAgent = null;
/* 476 */     Object privateData = null;
/* 477 */     Object prevPrivateData = null;
/* 478 */     Terminal terminal = null;
/* 479 */     Agent agent = null;
/* 480 */     Object source = null;
/* 481 */     log.debug("meta event BEGIN: cause (" + cause + ")  for " + this.terminalListener);
/* 482 */     for (int i = 0; i < _eventList.size(); ++i)
/*     */     {
/* 484 */       ev = (TSEvent)_eventList.elementAt(i);
/* 485 */       tsTarget = ev.getEventTarget();
/* 486 */       if (tsTarget instanceof TSDevice)
/*     */       {
/* 488 */         target = (TSDevice)tsTarget;
/* 489 */         terminal = createTerminal(target);
/* 490 */         source = terminal;
/*     */       }
/* 492 */       else if (tsTarget instanceof TSAgent)
/*     */       {
/* 494 */         targetAgent = (TSAgent)tsTarget;
/* 495 */         target = targetAgent.getTSAgentDevice();
/* 496 */         agent = createAgent(targetAgent);
/* 497 */         terminal = createTerminal(target);
/* 498 */         source = agent;
/*     */       }
/*     */ 
/* 501 */       privateData = ev.getPrivateData();
/*     */ 
/* 516 */       if ((privateData != null) && (ev.getEventType() != 9999))
/* 517 */         if (!privateData.equals(prevPrivateData)) {
/* 518 */           prevPrivateData = privateData;
/* 519 */           tsEventLog = "PRIVATEEVENT for " + target;
/* 520 */           addPrivateDataEvent(603, cause, target, privateData, tsEventLog);
/*     */         }
/*     */       else {
/* 523 */         prevPrivateData = null;
/*     */       }
/*     */ 
/* 526 */       TerminalEventParams terminalEventParams = new TerminalEventParams();
/* 527 */       terminalEventParams.setCause(cause);
/* 528 */       terminalEventParams.setTerminal(terminal);
/* 529 */       terminalEventParams.setPrivateData(privateData);
/* 530 */       terminalEventParams.setSource(source);
/* 531 */       switch (ev.getEventType())
/*     */       {
/*     */       case 58:
/* 534 */         tsEventLog = "TERMINALDONOTDISTURBEVENT for " + target;
/*     */ 
/* 536 */         terminalEventParams.setEventId(371);
/* 537 */         addCallControlTerminalEvent(terminalEventParams, target.dndState, tsEventLog);
/* 538 */         break;
/*     */       case 47:
/* 540 */         tsEventLog = "TERMINALLOGGEDONEVENT for " + targetAgent;
/*     */ 
/* 542 */         terminalEventParams.setEventId(310);
/* 543 */         addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
/* 544 */         break;
/*     */       case 48:
/* 546 */         tsEventLog = "TERMINALLOGGEDOFFEVENT for " + targetAgent;
/*     */ 
/* 548 */         terminalEventParams.setEventId(309);
/* 549 */         addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
/* 550 */         break;
/*     */       case 49:
/* 552 */         tsEventLog = "TERMINALREADYEVENT for " + targetAgent;
/*     */ 
/* 554 */         terminalEventParams.setEventId(312);
/* 555 */         addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
/* 556 */         break;
/*     */       case 50:
/* 558 */         tsEventLog = "TERMINALNOTREADYEVENT for " + targetAgent;
/*     */ 
/* 560 */         terminalEventParams.setEventId(311);
/* 561 */         addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
/* 562 */         break;
/*     */       case 51:
/* 564 */         tsEventLog = "TERMINALWORKREADYEVENT for " + targetAgent;
/*     */ 
/* 566 */         terminalEventParams.setEventId(315);
/* 567 */         addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
/* 568 */         break;
/*     */       case 52:
/* 570 */         tsEventLog = "TERMINALWORKNOTREADYEVENT for " + targetAgent;
/*     */ 
/* 572 */         terminalEventParams.setEventId(314);
/* 573 */         addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
/* 574 */         break;
/*     */       case 53:
/* 576 */         tsEventLog = "TERMINALBUSYEVENT for " + targetAgent;
/*     */ 
/* 578 */         terminalEventParams.setEventId(308);
/* 579 */         addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
/* 580 */         break;
/*     */       case 9999:
/* 583 */         tsEventLog = "PRIVATEEVENT for " + target;
/*     */ 
/* 585 */         addPrivateDataEvent(603, cause, terminal, privateData, tsEventLog);
/*     */       }
/*     */     }
/*     */ 
/* 589 */     log.debug("meta event END for " + this.terminalListener + " eventList size=" + this.listenerEventList.size());
/*     */ 
/* 592 */     if (this.listenerEventList.size() != 0)
/*     */       return;
/* 594 */     log.debug("no events to send to " + this.terminalListener);
/* 595 */     return;
/*     */   }
/*     */ 
/*     */   Terminal createTerminal(TSDevice device)
/*     */   {
/* 600 */     if (device == null) {
/* 601 */       return null;
/*     */     }
/* 603 */     return (Terminal)TsapiCreateObject.getTsapiObject(device, false);
/*     */   }
/*     */ 
/*     */   Agent createAgent(TSAgent agent) {
/* 607 */     if (agent == null) {
/* 608 */       return null;
/*     */     }
/* 610 */     return (Agent)TsapiCreateObject.getTsapiObject(agent, false);
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 619 */     TsapiTrace.traceEntry("run[]", this);
/* 620 */     synchronized (this.syncObject)
/*     */     {
/* 622 */       if (this.observer != null)
/* 623 */         log.debug("Got syncObject for TerminalObserver - " + this.observer);
/*     */       else
/* 625 */         log.debug("Got syncObject for TerminalListener - " + this.terminalListener);
/* 626 */       TermEv[] eventArray = null;
/* 627 */       Event[] listenerEventArray = null;
/* 628 */       synchronized (this)
/*     */       {
/* 630 */         if (this.observer != null)
/* 631 */           synchronized (this.eventList)
/*     */           {
/* 633 */             if (this.eventList.size() == 0)
/*     */             {
/* 635 */               log.debug("TsapiTerminalMonitor: events delivered by previous thread; no events to deliver in this thread");
/* 636 */               TsapiTrace.traceExit("run[]", this);
/* 637 */               return;
/*     */             }
/* 639 */             log.debug("Got this for TerminalObserver - " + this.observer);
/* 640 */             eventArray = new TermEv[this.eventList.size()];
/* 641 */             this.eventList.copyInto(eventArray);
/* 642 */             this.eventList.clear();
/*     */           }
/*     */         else {
/* 645 */           synchronized (this.listenerEventList)
/*     */           {
/* 647 */             if (this.listenerEventList.size() == 0)
/*     */             {
/* 649 */               log.debug("TsapiTerminalMonitor: events delivered by previous thread; no events to deliver in this thread");
/* 650 */               TsapiTrace.traceExit("run[]", this);
/* 651 */               return;
/*     */             }
/* 653 */             log.debug("Got this for TerminalListener - " + this.terminalListener);
/* 654 */             listenerEventArray = new Event[this.listenerEventList.size()];
/* 655 */             this.listenerEventList.copyInto(listenerEventArray);
/* 656 */             this.listenerEventList.clear();
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 661 */       if (this.observer != null) {
/* 662 */         log.debug("calling terminalChangedEvent in " + this.observer);
/*     */         try
/*     */         {
/* 666 */           this.observer.terminalChangedEvent(eventArray);
/*     */         }
/*     */         catch (Exception e) {
/* 669 */           log.error("EXCEPTION thrown by terminalChangedEvent in " + this.observer + " - " + e.getMessage(), e);
/*     */         }
/* 671 */         log.debug("returned from terminalChangedEvent in " + this.observer);
/*     */       } else {
/* 673 */         deliverListenerEvents(listenerEventArray);
/*     */       }
/*     */     }
/* 676 */     TsapiTrace.traceExit("run[]", this);
/*     */   }
/*     */ 
/*     */   private void deliverListenerEvents(Event[] listenerEventArray) {
/* 680 */     for (Event event : listenerEventArray)
/* 681 */       switch (event.getID())
/*     */       {
/*     */       case 121:
/* 683 */         log.debug("calling terminalListenerEnded in " + this.terminalListener);
/*     */         try {
/* 685 */           this.terminalListener.terminalListenerEnded((TerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 688 */           log.error("EXCEPTION thrown by terminalListenerEnded in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 690 */         log.debug("returned from terminalListenerEnded in " + this.terminalListener);
/* 691 */         break;
/*     */       case 308:
/* 693 */         log.debug("calling agentTerminalBusy in " + this.terminalListener);
/*     */         try {
/* 695 */           ((AgentTerminalListener)this.terminalListener).agentTerminalBusy((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 698 */           log.error("EXCEPTION thrown by agentTerminalBusy in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 700 */         log.debug("returned from agentTerminalBusy in " + this.terminalListener);
/* 701 */         break;
/*     */       case 309:
/* 703 */         log.debug("calling agentTerminalLoggedOff in " + this.terminalListener);
/*     */         try {
/* 705 */           ((AgentTerminalListener)this.terminalListener).agentTerminalLoggedOff((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 708 */           log.error("EXCEPTION thrown by agentTerminalLoggedOff in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 710 */         log.debug("returned from agentTerminalLoggedOff in " + this.terminalListener);
/* 711 */         break;
/*     */       case 310:
/* 713 */         log.debug("calling agentTerminalLoggedOn in " + this.terminalListener);
/*     */         try {
/* 715 */           ((AgentTerminalListener)this.terminalListener).agentTerminalLoggedOn((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 718 */           log.error("EXCEPTION thrown by agentTerminalLoggedOn in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 720 */         log.debug("returned from agentTerminalLoggedOn in " + this.terminalListener);
/* 721 */         break;
/*     */       case 311:
/* 723 */         log.debug("calling agentTerminalNotReady in " + this.terminalListener);
/*     */         try {
/* 725 */           ((AgentTerminalListener)this.terminalListener).agentTerminalNotReady((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 728 */           log.error("EXCEPTION thrown by agentTerminalNotReady in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 730 */         log.debug("returned from agentTerminalNotReady in " + this.terminalListener);
/* 731 */         break;
/*     */       case 312:
/* 733 */         log.debug("calling agentTerminalReady in " + this.terminalListener);
/*     */         try {
/* 735 */           ((AgentTerminalListener)this.terminalListener).agentTerminalReady((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 738 */           log.error("EXCEPTION thrown by agentTerminalReady in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 740 */         log.debug("returned from agentTerminalReady in " + this.terminalListener);
/* 741 */         break;
/*     */       case 313:
/* 743 */         log.debug("calling agentTerminalUnknown in " + this.terminalListener);
/*     */         try {
/* 745 */           ((AgentTerminalListener)this.terminalListener).agentTerminalUnknown((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 748 */           log.error("EXCEPTION thrown by agentTerminalUnknown in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 750 */         log.debug("returned from agentTerminalUnknown in " + this.terminalListener);
/* 751 */         break;
/*     */       case 314:
/* 753 */         log.debug("calling agentTerminalWorkNotReady in " + this.terminalListener);
/*     */         try {
/* 755 */           ((AgentTerminalListener)this.terminalListener).agentTerminalWorkNotReady((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 758 */           log.error("EXCEPTION thrown by agentTerminalWorkNotReady in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 760 */         log.debug("returned from agentTerminalWorkNotReady in " + this.terminalListener);
/* 761 */         break;
/*     */       case 315:
/* 763 */         log.debug("calling agentTerminalWorkReady in " + this.terminalListener);
/*     */         try {
/* 765 */           ((AgentTerminalListener)this.terminalListener).agentTerminalWorkReady((AgentTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 768 */           log.error("EXCEPTION thrown by agentTerminalWorkReady in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 770 */         log.debug("returned from agentTerminalWorkReady in " + this.terminalListener);
/* 771 */         break;
/*     */       case 371:
/* 773 */         log.debug("calling terminalDoNotDisturb in " + this.terminalListener);
/*     */         try {
/* 775 */           ((CallControlTerminalListener)this.terminalListener).terminalDoNotDisturb((CallControlTerminalEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 778 */           log.error("EXCEPTION thrown by terminalDoNotDisturb in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 780 */         log.debug("returned from terminalDoNotDisturb in " + this.terminalListener);
/* 781 */         break;
/*     */       case 603:
/* 783 */         log.debug("calling terminalPrivateData in " + this.terminalListener);
/*     */         try {
/* 785 */           ((PrivateDataTerminalListener)this.terminalListener).terminalPrivateData((PrivateDataEvent)event);
/*     */         }
/*     */         catch (Exception e) {
/* 788 */           log.error("EXCEPTION thrown by terminalPrivateData in " + this.terminalListener + " - " + e.getMessage(), e);
/*     */         }
/* 790 */         log.debug("returned from terminalPrivateData in " + this.terminalListener);
/*     */       }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor
 * JD-Core Version:    0.5.4
 */