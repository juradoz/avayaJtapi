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
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.ACDAddressEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.AddressEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.AddressEventParams;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.CallControlAddressEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.LucentAddressMsgWaitingEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.LucentCallControlAddressMsgWaitingEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrBusyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrLogOffEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrLogOnEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrNotReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrObservationEndedEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrWorkNotReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrWorkReadyEv;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressDNDEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressForwardEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressMsgWaitingEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.addr.TsapiPrivateAddressEvent;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import java.util.BitSet;
/*     */ import java.util.Vector;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.AddressEvent;
/*     */ import javax.telephony.AddressListener;
/*     */ import javax.telephony.AddressObserver;
/*     */ import javax.telephony.Event;
/*     */ import javax.telephony.callcenter.ACDAddressEvent;
/*     */ import javax.telephony.callcenter.ACDAddressListener;
/*     */ import javax.telephony.callcenter.ACDAddressObserver;
/*     */ import javax.telephony.callcenter.Agent;
/*     */ import javax.telephony.callcontrol.CallControlAddressEvent;
/*     */ import javax.telephony.callcontrol.CallControlAddressListener;
/*     */ import javax.telephony.callcontrol.CallControlAddressObserver;
/*     */ import javax.telephony.events.AddrEv;
/*     */ import javax.telephony.privatedata.PrivateDataAddressListener;
/*     */ import javax.telephony.privatedata.PrivateDataEvent;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class TsapiAddressMonitor
/*     */   implements TsapiMonitor
/*     */ {
/*  57 */   private static Logger log = Logger.getLogger(TsapiAddressMonitor.class);
/*     */   TSProviderImpl provider;
/*  60 */   AddressObserver observer = null;
/*     */   AddressListener addressListener;
/*     */   private final Vector<AddrEv> eventList;
/*     */   private final Vector<Event> listenerEventList;
/*  64 */   long reference = 0L;
/*  65 */   BitSet observerType = new BitSet(8);
/*     */ 
/* 635 */   Object syncObject = new Object();
/*     */ 
/*     */   public void dump(String indent)
/*     */   {
/*  70 */     log.trace(indent + "***** TsapiAddressMonitor DUMP *****");
/*  71 */     if (this.observer != null)
/*     */     {
/*  73 */       log.trace(indent + "TsapiAddressMonitor: " + this);
/*  74 */       log.trace(indent + "observer: " + this.observer);
/*     */     }
/*     */     else
/*     */     {
/*  78 */       log.trace(indent + "TsapiAddressListener: " + this);
/*  79 */       log.trace(indent + "listener: " + this.addressListener);
/*     */     }
/*  81 */     log.trace(indent + "***** TsapiAddressMonitor DUMP END *****");
/*     */   }
/*     */ 
/*     */   public TsapiAddressMonitor(TSProviderImpl _provider, AddressObserver _observer)
/*     */   {
/*  88 */     this.provider = _provider;
/*  89 */     this.observer = _observer;
/*  90 */     this.eventList = new Vector();
/*  91 */     this.listenerEventList = null;
/*  92 */     this.provider.addAddressMonitorThread(this);
/*     */ 
/*  94 */     this.observerType.set(0);
/*  95 */     if (this.observer instanceof CallControlAddressObserver)
/*     */     {
/*  97 */       this.observerType.set(1);
/*     */     }
/*  99 */     if (this.observer instanceof ACDAddressObserver)
/*     */     {
/* 101 */       this.observerType.set(2);
/*     */     }
/*     */ 
/* 106 */     this.observerType.set(5);
/*     */ 
/* 110 */     deliverEvents(null, false);
/*     */   }
/*     */ 
/*     */   public TsapiAddressMonitor(TSProviderImpl _provider, AddressListener _addressListener)
/*     */   {
/* 116 */     this.provider = _provider;
/* 117 */     this.addressListener = _addressListener;
/* 118 */     this.listenerEventList = new Vector();
/* 119 */     this.eventList = null;
/* 120 */     this.provider.addAddressMonitorThread(this);
/*     */ 
/* 123 */     deliverEvents(null, false);
/*     */   }
/*     */ 
/*     */   public AddressObserver getObserver()
/*     */   {
/* 129 */     return this.observer;
/*     */   }
/*     */ 
/*     */   public synchronized void addReference()
/*     */   {
/* 134 */     this.reference += 1L;
/*     */   }
/*     */ 
/*     */   public void deleteReference(TSDevice device, int cause, Object privateData)
/*     */   {
/* 143 */     log.debug("Getting TsapiAddressMonitor lock to deliver deleteReference events for observer " + this.observer);
/* 144 */     if (this.observer != null)
/*     */     {
/* 146 */       deleteReferenceInternalForObserver(device, cause, privateData);
/*     */     }
/*     */     else
/*     */     {
/* 150 */       deleteReferenceInternalForListener(device, cause, privateData);
/*     */     }
/* 152 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   private synchronized void deleteReferenceInternalForListener(TSDevice device, int cause, Object privateData)
/*     */   {
/* 158 */     String tsEventLog = null;
/* 159 */     this.reference -= 1L;
/* 160 */     tsEventLog = "OBSERVATIONENDEDEVENT for " + device;
/*     */ 
/* 163 */     AddressEventParams addressEventParams = new AddressEventParams();
/* 164 */     addressEventParams.setCause(cause);
/* 165 */     addressEventParams.setEventId(100);
/* 166 */     Address address = createAddress(device);
/* 167 */     addressEventParams.setSource(address);
/* 168 */     addCoreAddressEvents(new AddressEventImpl(addressEventParams, address), tsEventLog);
/* 169 */     if (privateData != null)
/*     */     {
/* 171 */       tsEventLog = "PRIVATEEVENT for " + device;
/* 172 */       addPrivateEvents(new PrivateDataEventImpl(600, cause, null, address, privateData), tsEventLog);
/*     */     }
/*     */ 
/* 175 */     if (this.reference > 0L)
/*     */       return;
/* 177 */     this.provider.removeAddressMonitorThread(this);
/*     */   }
/*     */ 
/*     */   private synchronized void deleteReferenceInternalForObserver(TSDevice device, int cause, Object privateData)
/*     */   {
/* 185 */     String tsEventLog = null;
/* 186 */     this.reference -= 1L;
/*     */ 
/* 188 */     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.observer);
/* 189 */     tsEventLog = "OBSERVATIONENDEDEVENT for " + device;
/*     */ 
/* 191 */     synchronized (this.eventList) {
/* 192 */       int nextMetaEventIndex = this.eventList.size();
/*     */ 
/* 195 */       addEvent(new TsapiAddrObservationEndedEvent(createAddress(device), cause, privateData), tsEventLog);
/*     */ 
/* 197 */       ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/* 198 */       if (privateData != null)
/*     */       {
/* 200 */         tsEventLog = "PRIVATEEVENT for " + device;
/* 201 */         addEvent(new TsapiPrivateAddressEvent(createAddress(device), cause, 136, privateData), tsEventLog);
/*     */       }
/* 203 */       log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
/*     */     }
/* 205 */     if (this.reference > 0L)
/*     */       return;
/* 207 */     this.provider.removeAddressMonitorThread(this);
/*     */   }
/*     */ 
/*     */   private void addCallControlAddressEvents(CallControlAddressEvent event, String tsEventLog)
/*     */   {
/* 213 */     if (!(this.addressListener instanceof CallControlAddressListener))
/*     */       return;
/* 215 */     log.debug(tsEventLog + " for listener " + this.addressListener);
/*     */ 
/* 217 */     this.listenerEventList.addElement(event);
/*     */   }
/*     */ 
/*     */   private void addCallCenterAddressEvents(ACDAddressEvent event, String tsEventLog)
/*     */   {
/* 223 */     if (!(this.addressListener instanceof ACDAddressListener))
/*     */       return;
/* 225 */     log.debug(tsEventLog + " for listener " + this.addressListener);
/*     */ 
/* 227 */     this.listenerEventList.addElement(event);
/*     */   }
/*     */ 
/*     */   private void addCoreAddressEvents(AddressEvent event, String tsEventLog)
/*     */   {
/* 233 */     if (!(this.addressListener instanceof AddressListener))
/*     */       return;
/* 235 */     log.debug(tsEventLog + " for listener " + this.addressListener);
/*     */ 
/* 237 */     this.listenerEventList.addElement(event);
/*     */   }
/*     */ 
/*     */   private void addPrivateEvents(PrivateDataEvent event, String tsEventLog)
/*     */   {
/* 243 */     if (!(this.addressListener instanceof PrivateDataAddressListener))
/*     */       return;
/* 245 */     log.debug(tsEventLog + " for listener " + this.addressListener);
/*     */ 
/* 247 */     this.listenerEventList.addElement(event);
/*     */   }
/*     */ 
/*     */   void addEvent(AddrEv event, String tsEventLog)
/*     */   {
/* 259 */     if ((this.observerType.get(0)) && (((ITsapiEvent)event).getEventPackage() == 0))
/*     */     {
/* 262 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 264 */       this.eventList.addElement(event);
/*     */     }
/* 266 */     else if ((this.observerType.get(1)) && (((ITsapiEvent)event).getEventPackage() == 1))
/*     */     {
/* 269 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 271 */       this.eventList.addElement(event);
/*     */     }
/* 273 */     else if ((this.observerType.get(2)) && (((ITsapiEvent)event).getEventPackage() == 2))
/*     */     {
/* 276 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 278 */       this.eventList.addElement(event);
/*     */     }
/* 280 */     else if ((this.observerType.get(5)) && (((ITsapiEvent)event).getEventPackage() == 5))
/*     */     {
/* 283 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 285 */       this.eventList.addElement(event);
/*     */     }
/*     */     else
/*     */     {
/* 289 */       log.debug(tsEventLog + " ignored");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 299 */     if ((_eventList == null) || (_eventList.size() == 0)) {
/* 300 */       return;
/*     */     }
/* 302 */     if (this.observer != null)
/* 303 */       log.debug("Getting TsapiAddressMonitor lock to deliver events for observer " + this.observer);
/*     */     else
/* 305 */       log.debug("Getting TsapiAddressMonitor lock to deliver events for listener " + this.addressListener);
/* 306 */     synchronized (_eventList) {
/* 307 */       deliverEventsInternal(_eventList, snapshot);
/*     */     }
/*     */   }
/*     */ 
/*     */   private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 313 */     if (this.observer != null)
/*     */     {
/* 315 */       createEventsForObserver(_eventList, snapshot);
/*     */     }
/*     */     else
/*     */     {
/* 319 */       createEventsForListener(_eventList, snapshot);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createEventsForListener(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 327 */     if (_eventList == null)
/*     */     {
/* 329 */       return;
/*     */     }
/*     */     int cause;
/* 332 */     if (snapshot)
/*     */     {
/* 334 */       cause = 110;
/*     */     }
/*     */     else
/*     */     {
/* 338 */       cause = 100;
/*     */     }
/*     */ 
/* 342 */     TSEvent tsEvent = null;
/* 343 */     Object tsTarget = null;
/* 344 */     TSDevice target = null;
/* 345 */     TSAgent targetAgent = null;
/* 346 */     Object privateData = null;
/* 347 */     Object previousPrivateData = null;
/* 348 */     Address address = null;
/* 349 */     Agent agent = null;
/* 350 */     Object source = null;
/* 351 */     for (int i = 0; i < _eventList.size(); ++i) {
/* 352 */       tsEvent = (TSEvent)_eventList.elementAt(i);
/* 353 */       tsTarget = tsEvent.getEventTarget();
/* 354 */       if (tsTarget instanceof TSDevice)
/*     */       {
/* 356 */         target = (TSDevice)tsTarget;
/* 357 */         address = createAddress(target);
/* 358 */         source = address;
/*     */       }
/* 360 */       else if (tsTarget instanceof TSAgent)
/*     */       {
/* 362 */         targetAgent = (TSAgent)tsTarget;
/* 363 */         agent = createAgent(targetAgent);
/* 364 */         source = agent;
/* 365 */         target = targetAgent.getTSACDDevice();
/* 366 */         if (target == null)
/* 367 */           target = tsEvent.getSkillDevice();
/* 368 */         address = createAddress(target);
/*     */       }
/* 370 */       privateData = tsEvent.getPrivateData();
/* 371 */       if (privateData != null)
/*     */       {
/* 373 */         if (!privateData.equals(previousPrivateData))
/*     */         {
/* 375 */           _eventList.add(new TSEvent(9999, tsEvent.getEventTarget(), privateData, this.provider));
/* 376 */           previousPrivateData = privateData;
/*     */         }
/*     */ 
/*     */       }
/*     */       else {
/* 381 */         previousPrivateData = null;
/*     */       }
/* 383 */       AddressEventParams addressEventParams = new AddressEventParams();
/* 384 */       addressEventParams.setCause(cause);
/* 385 */       addressEventParams.setPrivateData(privateData);
/*     */       String tsEventLog;
/* 386 */       switch (tsEvent.getEventType())
/*     */       {
/*     */       case 37:
/* 389 */         tsEventLog = "ADDRESSDONOTDISTURBEVENT for " + target;
/* 390 */         addressEventParams.setEventId(350);
/* 391 */         addressEventParams.setDoNotDisturbState(target.dndState);
/* 392 */         addressEventParams.setSource(source);
/* 393 */         addCallControlAddressEvents(new CallControlAddressEventImpl(addressEventParams, address), tsEventLog);
/* 394 */         break;
/*     */       case 38:
/* 396 */         tsEventLog = "ADDRESSMESSAGEWAITINGEVENT for " + target;
/* 397 */         addressEventParams.setSource(source);
/* 398 */         addressEventParams.setEventId(352);
/* 399 */         addressEventParams.setMwBits(target.msgWaitingBits);
/* 400 */         if (this.provider.isLucent())
/*     */         {
/* 402 */           addCallControlAddressEvents(new LucentCallControlAddressMsgWaitingEventImpl(addressEventParams, address), tsEventLog);
/*     */         }
/*     */         else
/*     */         {
/* 406 */           addCallControlAddressEvents(new CallControlAddressEventImpl(addressEventParams, address), tsEventLog);
/*     */         }
/* 408 */         break;
/*     */       case 39:
/* 410 */         tsEventLog = "ADDRESSFORWARDEVENT for " + target;
/* 411 */         addressEventParams.setSource(source);
/* 412 */         addressEventParams.setEventId(351);
/* 413 */         addressEventParams.setCallControlForwarding(target.createForwarding());
/* 414 */         addCallControlAddressEvents(new CallControlAddressEventImpl(addressEventParams, address), tsEventLog);
/* 415 */         break;
/*     */       case 40:
/* 417 */         tsEventLog = "ADDRESSLOGGEDONEVENT for " + targetAgent;
/* 418 */         addressEventParams.setEventId(302);
/* 419 */         addressEventParams.setSource(source);
/* 420 */         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
/* 421 */         break;
/*     */       case 41:
/* 423 */         tsEventLog = "ADDRESSLOGGEDOFFEVENT for " + targetAgent;
/* 424 */         addressEventParams.setEventId(301);
/* 425 */         addressEventParams.setSource(source);
/* 426 */         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
/* 427 */         break;
/*     */       case 42:
/* 429 */         tsEventLog = "ADDRESSREADYEVENT for " + targetAgent;
/* 430 */         addressEventParams.setEventId(304);
/* 431 */         addressEventParams.setSource(source);
/* 432 */         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
/* 433 */         break;
/*     */       case 43:
/* 435 */         tsEventLog = "ADDRESSNOTREADYEVENT for " + targetAgent;
/* 436 */         addressEventParams.setEventId(303);
/* 437 */         addressEventParams.setSource(source);
/* 438 */         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
/* 439 */         break;
/*     */       case 44:
/* 441 */         tsEventLog = "ADDRESSWORKREADYEVENT for " + targetAgent;
/* 442 */         addressEventParams.setEventId(307);
/* 443 */         addressEventParams.setSource(source);
/* 444 */         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
/* 445 */         break;
/*     */       case 45:
/* 447 */         tsEventLog = "ADDRESSWORKNOTREADYEVENT for " + targetAgent;
/* 448 */         addressEventParams.setEventId(306);
/* 449 */         addressEventParams.setSource(source);
/* 450 */         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
/* 451 */         break;
/*     */       case 46:
/* 453 */         tsEventLog = "ADDRESSBUSYEVENT for " + targetAgent;
/* 454 */         addressEventParams.setEventId(300);
/* 455 */         addressEventParams.setSource(source);
/* 456 */         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
/* 457 */         break;
/*     */       case 9999:
/* 459 */         tsEventLog = "PRIVATEEVENT for " + target;
/* 460 */         addPrivateEvents(new PrivateDataEventImpl(600, cause, null, source, privateData), tsEventLog);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 465 */     if (this.listenerEventList.size() == 0)
/*     */     {
/* 467 */       log.debug("no events to send to " + this.addressListener);
/* 468 */       return;
/*     */     }
/* 470 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   private void createEventsForObserver(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 476 */     String tsEventLog = null;
/* 477 */     if (_eventList == null)
/*     */     {
/* 479 */       return;
/*     */     }
/*     */     int cause;
/*     */     int metaCode;
/* 483 */     if (snapshot)
/*     */     {
/* 485 */       metaCode = 135;
/* 486 */       cause = 110;
/*     */     }
/*     */     else
/*     */     {
/* 490 */       metaCode = 136;
/* 491 */       cause = 100;
/*     */     }
/*     */ 
/* 494 */     int nextMetaEventIndex = this.eventList.size();
/*     */ 
/* 497 */     TSEvent ev = null;
/* 498 */     Object tsTarget = null;
/* 499 */     TSDevice target = null;
/* 500 */     TSAgent agent = null;
/* 501 */     Object privateData = null;
/* 502 */     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + metaCode + ")" + " for " + this.observer);
/* 503 */     for (int i = 0; i < _eventList.size(); ++i) {
/* 504 */       ev = (TSEvent)_eventList.elementAt(i);
/* 505 */       tsTarget = ev.getEventTarget();
/* 506 */       if (tsTarget instanceof TSDevice)
/*     */       {
/* 508 */         target = (TSDevice)tsTarget;
/*     */       }
/* 510 */       else if (tsTarget instanceof TSAgent)
/*     */       {
/* 512 */         agent = (TSAgent)tsTarget;
/* 513 */         target = agent.getTSACDDevice();
/* 514 */         if (target == null) {
/* 515 */           target = ev.getSkillDevice();
/*     */         }
/*     */       }
/* 518 */       privateData = ev.getPrivateData();
/*     */ 
/* 520 */       switch (ev.getEventType())
/*     */       {
/*     */       case 37:
/* 523 */         tsEventLog = "ADDRESSDONOTDISTURBEVENT for " + target;
/*     */ 
/* 525 */         addEvent(new TsapiAddressDNDEvent(createAddress(target), target.dndState, cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 527 */         break;
/*     */       case 38:
/* 529 */         tsEventLog = "ADDRESSMESSAGEWAITINGEVENT for " + target;
/*     */ 
/* 531 */         if (this.provider.isLucent())
/*     */         {
/* 533 */           addEvent(new LucentAddressMsgWaitingEventImpl(createAddress(target), target.msgWaitingBits, cause, metaCode, privateData), tsEventLog);
/*     */         }
/*     */         else
/*     */         {
/* 538 */           addEvent(new TsapiAddressMsgWaitingEvent(createAddress(target), target.msgWaitingBits, cause, metaCode, privateData), tsEventLog);
/*     */         }
/*     */ 
/* 541 */         break;
/*     */       case 39:
/* 543 */         tsEventLog = "ADDRESSFORWARDEVENT for " + target;
/*     */ 
/* 545 */         addEvent(new TsapiAddressForwardEvent(createAddress(target), target.createForwarding(), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 548 */         break;
/*     */       case 40:
/* 550 */         tsEventLog = "ADDRESSLOGGEDONEVENT for " + agent;
/*     */ 
/* 552 */         addEvent(new TsapiAddrLogOnEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 554 */         break;
/*     */       case 41:
/* 556 */         tsEventLog = "ADDRESSLOGGEDOFFEVENT for " + agent;
/*     */ 
/* 558 */         addEvent(new TsapiAddrLogOffEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 560 */         break;
/*     */       case 42:
/* 562 */         tsEventLog = "ADDRESSREADYEVENT for " + agent;
/*     */ 
/* 564 */         addEvent(new TsapiAddrReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 566 */         break;
/*     */       case 43:
/* 568 */         tsEventLog = "ADDRESSNOTREADYEVENT for " + agent;
/*     */ 
/* 570 */         addEvent(new TsapiAddrNotReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 572 */         break;
/*     */       case 44:
/* 574 */         tsEventLog = "ADDRESSWORKREADYEVENT for " + agent;
/*     */ 
/* 576 */         addEvent(new TsapiAddrWorkReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 578 */         break;
/*     */       case 45:
/* 580 */         tsEventLog = "ADDRESSWORKNOTREADYEVENT for " + agent;
/*     */ 
/* 582 */         addEvent(new TsapiAddrWorkNotReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 584 */         break;
/*     */       case 46:
/* 586 */         tsEventLog = "ADDRESSBUSYEVENT for " + agent;
/*     */ 
/* 588 */         addEvent(new TsapiAddrBusyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 590 */         break;
/*     */       case 9999:
/* 592 */         tsEventLog = "PRIVATEEVENT for " + target;
/*     */ 
/* 594 */         addEvent(new TsapiPrivateAddressEvent(createAddress(target), cause, metaCode, privateData), tsEventLog);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 599 */     synchronized (this.eventList) {
/* 600 */       log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
/*     */ 
/* 603 */       if (this.eventList.size() == 0)
/*     */       {
/* 605 */         log.debug("no events to send to " + this.observer);
/* 606 */         return;
/*     */       }
/*     */ 
/* 610 */       if (nextMetaEventIndex < this.eventList.size())
/*     */       {
/* 613 */         ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/*     */       }
/*     */     }
/* 616 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   private Address createAddress(TSDevice device) {
/* 620 */     if (device == null) {
/* 621 */       return null;
/*     */     }
/*     */ 
/* 624 */     return (Address)TsapiCreateObject.getTsapiObject(device, true);
/*     */   }
/*     */ 
/*     */   private Agent createAgent(TSAgent agent) {
/* 628 */     if (agent == null) {
/* 629 */       return null;
/*     */     }
/*     */ 
/* 632 */     return (Agent)TsapiCreateObject.getTsapiObject(agent, true);
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 641 */     TsapiTrace.traceEntry("run[]", this);
/* 642 */     synchronized (this.syncObject)
/*     */     {
/* 644 */       if (this.observer != null)
/*     */       {
/* 646 */         dispatchEventsForObservers();
/*     */       }
/*     */       else
/*     */       {
/* 650 */         dispatchEventsForlisteners();
/*     */       }
/*     */     }
/* 653 */     TsapiTrace.traceExit("run[]", this);
/*     */   }
/*     */ 
/*     */   private void dispatchEventsForlisteners()
/*     */   {
/* 658 */     log.debug("Got syncObject for AddressListener - " + this.addressListener);
/* 659 */     Event[] eventArray = null;
/* 660 */     synchronized (this)
/*     */     {
/* 662 */       log.debug("Got this for AddressListener - " + this.addressListener);
/* 663 */       synchronized (this.listenerEventList) {
/* 664 */         if (this.listenerEventList.size() == 0) {
/* 665 */           log.debug("TsapiAddressMonitor: events delivered by previous thread; no events to deliver in this thread");
/* 666 */           TsapiTrace.traceExit("run[]", this);
/* 667 */           return;
/*     */         }
/* 669 */         eventArray = new Event[this.listenerEventList.size()];
/* 670 */         this.listenerEventList.copyInto(eventArray);
/* 671 */         this.listenerEventList.clear();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*     */       int i$;
/* 677 */       for (Event event : eventArray)
/*     */       {
/* 679 */         switch (event.getID())
/*     */         {
/*     */         case 100:
/* 682 */           log.debug("calling addressListenerEnded in " + this.addressListener);
/* 683 */           this.addressListener.addressListenerEnded((AddressEvent)event);
/* 684 */           log.debug("returned from addressListenerEnded in " + this.addressListener);
/* 685 */           break;
/*     */         case 350:
/* 687 */           log.debug("calling addressDoNotDisturb in " + this.addressListener);
/* 688 */           ((CallControlAddressListener)this.addressListener).addressDoNotDisturb((CallControlAddressEvent)event);
/* 689 */           log.debug("returned from addressDoNotDisturb in " + this.addressListener);
/* 690 */           break;
/*     */         case 351:
/* 692 */           log.debug("calling addressForwarded in " + this.addressListener);
/* 693 */           ((CallControlAddressListener)this.addressListener).addressForwarded((CallControlAddressEvent)event);
/* 694 */           log.debug("returned from addressForwarded in " + this.addressListener);
/* 695 */           break;
/*     */         case 352:
/* 697 */           log.debug("calling addressMessageWaiting in " + this.addressListener);
/* 698 */           ((CallControlAddressListener)this.addressListener).addressMessageWaiting((CallControlAddressEvent)event);
/* 699 */           log.debug("returned from addressMessageWaiting in " + this.addressListener);
/* 700 */           break;
/*     */         case 300:
/* 702 */           log.debug("calling acdAddressBusy in " + this.addressListener);
/* 703 */           ((ACDAddressListener)this.addressListener).acdAddressBusy((ACDAddressEvent)event);
/* 704 */           log.debug("returned from acdAddressBusy in " + this.addressListener);
/* 705 */           break;
/*     */         case 301:
/* 707 */           log.debug("calling acdAddressLoggedOff in " + this.addressListener);
/* 708 */           ((ACDAddressListener)this.addressListener).acdAddressLoggedOff((ACDAddressEvent)event);
/* 709 */           log.debug("returned from acdAddressLoggedOff in " + this.addressListener);
/* 710 */           break;
/*     */         case 302:
/* 712 */           log.debug("calling acdAddressLoggedOn in " + this.addressListener);
/* 713 */           ((ACDAddressListener)this.addressListener).acdAddressLoggedOn((ACDAddressEvent)event);
/* 714 */           log.debug("returned from acdAddressLoggedOn in " + this.addressListener);
/* 715 */           break;
/*     */         case 303:
/* 717 */           log.debug("calling acdAddressNotReady in " + this.addressListener);
/* 718 */           ((ACDAddressListener)this.addressListener).acdAddressNotReady((ACDAddressEvent)event);
/* 719 */           log.debug("returned from acdAddressNotReady in " + this.addressListener);
/* 720 */           break;
/*     */         case 304:
/* 722 */           log.debug("calling acdAddressReady in " + this.addressListener);
/* 723 */           ((ACDAddressListener)this.addressListener).acdAddressReady((ACDAddressEvent)event);
/* 724 */           log.debug("returned from acdAddressReady in " + this.addressListener);
/* 725 */           break;
/*     */         case 305:
/* 727 */           log.debug("calling acdAddressUnknown in " + this.addressListener);
/* 728 */           ((ACDAddressListener)this.addressListener).acdAddressUnknown((ACDAddressEvent)event);
/* 729 */           log.debug("returned from acdAddressUnknown in " + this.addressListener);
/* 730 */           break;
/*     */         case 306:
/* 732 */           log.debug("calling acdAddressWorkNotReady in " + this.addressListener);
/* 733 */           ((ACDAddressListener)this.addressListener).acdAddressWorkNotReady((ACDAddressEvent)event);
/* 734 */           log.debug("returned from acdAddressWorkNotReady in " + this.addressListener);
/* 735 */           break;
/*     */         case 307:
/* 737 */           log.debug("calling acdAddressWorkReady in " + this.addressListener);
/* 738 */           ((ACDAddressListener)this.addressListener).acdAddressWorkReady((ACDAddressEvent)event);
/* 739 */           log.debug("returned from acdAddressWorkReady in " + this.addressListener);
/* 740 */           break;
/*     */         case 600:
/* 742 */           log.debug("calling addressPrivateData in " + this.addressListener);
/* 743 */           ((PrivateDataAddressListener)this.addressListener).addressPrivateData((PrivateDataEvent)event);
/* 744 */           log.debug("returned from addressPrivateData in " + this.addressListener);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 750 */       log.error("EXCEPTION thrown by addressChangedEvent in " + this.addressListener + " - " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void dispatchEventsForObservers()
/*     */   {
/* 757 */     log.debug("Got syncObject for Addressobserver - " + this.observer);
/* 758 */     AddrEv[] eventArray = null;
/* 759 */     synchronized (this)
/*     */     {
/* 761 */       log.debug("Got this for Addressobserver - " + this.observer);
/* 762 */       synchronized (this.eventList) {
/* 763 */         if (this.eventList.size() == 0) {
/* 764 */           log.debug("TsapiAddressMonitor: events delivered by previous thread; no events to deliver in this thread");
/* 765 */           TsapiTrace.traceExit("run[]", this);
/* 766 */           return;
/*     */         }
/* 768 */         eventArray = new AddrEv[this.eventList.size()];
/* 769 */         this.eventList.copyInto(eventArray);
/* 770 */         this.eventList.clear();
/*     */       }
/*     */     }
/* 773 */     log.debug("calling addressChangedEvent in " + this.observer);
/*     */     try
/*     */     {
/* 778 */       this.observer.addressChangedEvent(eventArray);
/*     */     }
/*     */     catch (Exception e) {
/* 781 */       log.error("EXCEPTION thrown by addressChangedEvent in " + this.observer + " - " + e.getMessage(), e);
/*     */     }
/*     */ 
/* 784 */     log.debug("returned from addressChangedEvent in " + this.observer);
/*     */   }
/*     */ 
/*     */   public AddressListener getAddressListener()
/*     */   {
/* 790 */     return this.addressListener;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor
 * JD-Core Version:    0.5.4
 */