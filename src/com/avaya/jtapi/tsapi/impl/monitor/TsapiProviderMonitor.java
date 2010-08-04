/*     */ package com.avaya.jtapi.tsapi.impl.monitor;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiEvent;
/*     */ import com.avaya.jtapi.tsapi.TSProvider;
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
/*     */ import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.PrivateDataEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventParams;
/*     */ import com.avaya.jtapi.tsapi.impl.events.provider.TsapiPrivateProviderEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProvObservationEndedEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderInServiceEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderOutOfServiceEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderShutdownEvent;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import java.util.Vector;
/*     */ import javax.telephony.Event;
/*     */ import javax.telephony.Provider;
/*     */ import javax.telephony.ProviderEvent;
/*     */ import javax.telephony.ProviderListener;
/*     */ import javax.telephony.ProviderObserver;
/*     */ import javax.telephony.events.ProvEv;
/*     */ import javax.telephony.privatedata.PrivateDataEvent;
/*     */ import javax.telephony.privatedata.PrivateDataProviderListener;
/*     */ import javax.telephony.privatedata.events.PrivateProvEv;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TsapiProviderMonitor
/*     */   implements TsapiMonitor
/*     */ {
/*  39 */   private static final Logger log = Logger.getLogger(TsapiProviderMonitor.class);
/*     */   protected final TSProviderImpl provider;
/*     */   private final ProviderObserver observer;
/*  42 */   protected final Vector<ProvEv> observerEventList = new Vector();
/*  43 */   protected final Vector<TsapiListenerEvent> listenerEventList = new Vector();
/*  44 */   private long reference = 0L;
/*  45 */   private final Object syncObject = new Object();
/*     */   private final ProviderListener listener;
/*     */ 
/*     */   public TsapiProviderMonitor(TSProviderImpl _provider, ProviderObserver _observer)
/*     */   {
/*  52 */     this.provider = _provider;
/*  53 */     this.observer = _observer;
/*     */ 
/*  55 */     this.provider.addProviderMonitorThread(this);
/*  56 */     this.listener = null;
/*     */ 
/*  58 */     deliverEvents(null, false);
/*     */   }
/*     */ 
/*     */   public TsapiProviderMonitor(TSProviderImpl impl, ProviderListener _listener)
/*     */   {
/*  63 */     this.listener = _listener;
/*  64 */     this.observer = null;
/*  65 */     this.provider = impl;
/*  66 */     this.provider.addProviderMonitorThread(this);
/*     */ 
/*  68 */     deliverEvents(null, false);
/*     */   }
/*     */ 
/*     */   public Object getMonitor()
/*     */   {
/*  73 */     if (this.listener != null) {
/*  74 */       return this.listener;
/*     */     }
/*  76 */     return this.observer;
/*     */   }
/*     */ 
/*     */   public synchronized void addReference()
/*     */   {
/*  81 */     this.reference += 1L;
/*     */   }
/*     */ 
/*     */   public void deleteReference(int cause, Object privateData)
/*     */   {
/*  90 */     log.debug("Getting TsapiProviderMonitor lock to deliver deleteReference events for observer " + this.observer);
/*  91 */     if (this.observer != null) {
/*  92 */       deleteObserverReference(cause, privateData);
/*     */     }
/*     */     else {
/*  95 */       deleteListenerReference(cause, privateData);
/*     */     }
/*  97 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   private synchronized void deleteObserverReference(int cause, Object privateData) {
/* 101 */     String tsEventLog = null;
/* 102 */     this.reference -= 1L;
/*     */ 
/* 104 */     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.observer);
/* 105 */     tsEventLog = "OBSERVATIONENDEDEVENT for " + this.provider;
/*     */ 
/* 107 */     synchronized (this.observerEventList) {
/* 108 */       int nextMetaEventIndex = this.observerEventList.size();
/*     */ 
/* 111 */       addEv(new TsapiProvObservationEndedEvent(createProvider(this.provider), cause, privateData), tsEventLog);
/*     */ 
/* 113 */       ((TsapiObserverEvent)this.observerEventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/*     */     }
/*     */ 
/* 116 */     if (privateData != null)
/*     */     {
/* 118 */       tsEventLog = "PRIVATEEVENT for " + this.provider;
/* 119 */       addEv(new TsapiPrivateProviderEvent(createProvider(this.provider), cause, 136, privateData), tsEventLog);
/*     */     }
/*     */ 
/* 122 */     log.debug("meta event END for " + this.observer + " eventList size=" + this.observerEventList.size());
/*     */ 
/* 124 */     if (this.reference > 0L)
/*     */       return;
/* 126 */     this.provider.removeProviderMonitorThread(this);
/*     */   }
/*     */ 
/*     */   private synchronized void deleteListenerReference(int cause, Object privateData)
/*     */   {
/* 131 */     String tsEventLog = null;
/* 132 */     this.reference -= 1L;
/*     */ 
/* 134 */     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.listener);
/* 135 */     tsEventLog = "OBSERVATIONENDEDEVENT for " + this.provider;
/*     */ 
/* 138 */     ProviderEventParams params = new ProviderEventParams(createProvider(this.provider), 112, cause, null, createProvider(this.provider), privateData);
/*     */ 
/* 141 */     addEvent(new ProviderEventImpl(params), tsEventLog);
/*     */ 
/* 143 */     if (privateData != null)
/*     */     {
/* 145 */       tsEventLog = "PRIVATEEVENT for " + this.provider;
/* 146 */       addEvent(new PrivateDataEventImpl(602, cause, null, createProvider(this.provider), privateData), tsEventLog);
/*     */     }
/*     */ 
/* 149 */     log.debug("meta event END for " + this.listener + " eventList size=" + this.listenerEventList.size());
/*     */ 
/* 151 */     if (this.reference > 0L)
/*     */       return;
/* 153 */     this.provider.removeProviderMonitorThread(this);
/*     */   }
/*     */ 
/*     */   private void addEvent(TsapiListenerEvent impl, String tsEventLog)
/*     */   {
/* 158 */     if ((impl instanceof PrivateDataEvent) && (this.listener instanceof PrivateDataProviderListener)) {
/* 159 */       this.listenerEventList.add(impl);
/*     */     }
/* 161 */     else if ((impl instanceof ProviderEvent) && (this.listener instanceof ProviderListener))
/* 162 */       this.listenerEventList.add(impl);
/*     */   }
/*     */ 
/*     */   private void addEv(ProvEv event, String tsEventLog)
/*     */   {
/* 168 */     if ((event instanceof PrivateProvEv) && (((ITsapiEvent)event).getEventPackage() == 5))
/*     */     {
/* 171 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 173 */       this.observerEventList.addElement(event);
/*     */     }
/* 175 */     else if (((ITsapiEvent)event).getEventPackage() == 0) {
/* 176 */       log.debug(tsEventLog + " for observer " + this.observer);
/*     */ 
/* 178 */       this.observerEventList.addElement(event);
/*     */     }
/*     */     else
/*     */     {
/* 182 */       log.debug(tsEventLog + " ignored");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 192 */     log.debug("Getting TsapiProviderMonitor lock to deliver events for observer " + this.observer);
/* 193 */     if ((_eventList == null) || (_eventList.size() == 0)) {
/* 194 */       return;
/*     */     }
/* 196 */     synchronized (_eventList) {
/* 197 */       if (this.observer != null) {
/* 198 */         deliverObserverEvents(_eventList, snapshot);
/*     */       }
/*     */       else {
/* 201 */         deliverListenerEvents(_eventList, snapshot);
/*     */       }
/* 203 */       if ((_eventList != null) && (_eventList.size() != 0))
/* 204 */         JtapiEventThreadManager.execute(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected synchronized void deliverObserverEvents(Vector<TSEvent> _eventList, boolean snapshot)
/*     */   {
/* 211 */     String tsEventLog = null;
/* 212 */     if (_eventList == null)
/*     */     {
/* 214 */       return;
/*     */     }
/*     */     int cause;
/*     */     int metaCode;
/* 219 */     if (snapshot)
/*     */     {
/* 221 */       metaCode = 135;
/* 222 */       cause = 110;
/*     */     }
/*     */     else
/*     */     {
/* 226 */       metaCode = 136;
/* 227 */       cause = 100;
/*     */     }
/*     */ 
/* 230 */     int nextMetaEventIndex = this.observerEventList.size();
/*     */ 
/* 232 */     Object privateData = null;
/*     */ 
/* 234 */     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + metaCode + ")" + " for " + this.observer);
/*     */ 
/* 236 */     for (int i = 0; i < _eventList.size(); ++i) {
/* 237 */       TSEvent ev = (TSEvent)_eventList.elementAt(i);
/*     */ 
/* 239 */       privateData = ev.getPrivateData();
/*     */ 
/* 241 */       switch (ev.getEventType())
/*     */       {
/*     */       case 1:
/* 244 */         tsEventLog = "PROVIDERINSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 246 */         addEv(new TsapiProviderInServiceEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 248 */         break;
/*     */       case 2:
/* 250 */         tsEventLog = "PROVIDEROUTOFSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 252 */         addEv(new TsapiProviderOutOfServiceEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 254 */         break;
/*     */       case 3:
/* 256 */         tsEventLog = "PROVIDERSHUTDOWNEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 258 */         addEv(new TsapiProviderShutdownEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
/*     */ 
/* 260 */         break;
/*     */       case 9999:
/* 262 */         tsEventLog = "PRIVATEEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 264 */         addEv(new TsapiPrivateProviderEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 269 */     synchronized (this.observerEventList) {
/* 270 */       log.debug("meta event END for " + this.observer + " eventList size=" + this.observerEventList.size());
/*     */ 
/* 273 */       if (this.observerEventList.size() == 0)
/*     */       {
/* 275 */         log.debug("no events to send to " + this.observer);
/* 276 */         return;
/*     */       }
/*     */ 
/* 280 */       if (nextMetaEventIndex < this.observerEventList.size())
/*     */       {
/* 283 */         ((TsapiObserverEvent)this.observerEventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected synchronized void deliverListenerEvents(Vector<TSEvent> _eventList, boolean snapshot) {
/* 289 */     String tsEventLog = null;
/* 290 */     if (_eventList == null)
/* 291 */       return;
/*     */     int cause;
/* 295 */     if (snapshot) {
/* 296 */       cause = 110;
/*     */     }
/*     */     else {
/* 299 */       cause = 100;
/*     */     }
/*     */ 
/* 302 */     Object privateData = null;
/*     */ 
/* 304 */     log.debug("meta event BEGIN: cause (" + cause + ") for " + this.listener);
/*     */ 
/* 306 */     for (int i = 0; i < _eventList.size(); ++i)
/*     */     {
/* 308 */       TSEvent ev = (TSEvent)_eventList.elementAt(i);
/*     */ 
/* 310 */       privateData = ev.getPrivateData();
/* 311 */       ProviderEventParams params = null;
/* 312 */       switch (ev.getEventType())
/*     */       {
/*     */       case 1:
/* 315 */         tsEventLog = "PROVIDERINSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 317 */         params = new ProviderEventParams(createProvider(this.provider), 111, cause, null, createProvider(this.provider), privateData);
/*     */ 
/* 320 */         addEvent(new ProviderEventImpl(params), tsEventLog);
/* 321 */         break;
/*     */       case 2:
/* 323 */         tsEventLog = "PROVIDEROUTOFSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 325 */         params = new ProviderEventParams(createProvider(this.provider), 113, cause, null, createProvider(this.provider), privateData);
/*     */ 
/* 328 */         addEvent(new ProviderEventImpl(params), tsEventLog);
/* 329 */         break;
/*     */       case 3:
/* 331 */         tsEventLog = "PROVIDERSHUTDOWNEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 333 */         params = new ProviderEventParams(createProvider(this.provider), 114, cause, null, createProvider(this.provider), privateData);
/*     */ 
/* 336 */         addEvent(new ProviderEventImpl(params), tsEventLog);
/* 337 */         break;
/*     */       case 9999:
/* 339 */         tsEventLog = "PRIVATEEVENT for " + (TSProvider)ev.getEventTarget();
/*     */ 
/* 341 */         addEvent(new PrivateDataEventImpl(602, cause, null, createProvider(this.provider), privateData), tsEventLog);
/*     */       }
/*     */     }
/*     */ 
/* 345 */     int size = this.listenerEventList.size();
/* 346 */     log.debug("meta event END for " + this.listener + " eventList size=" + size);
/*     */ 
/* 349 */     if (size != 0)
/*     */       return;
/* 351 */     log.debug("no events to send to " + this.listener);
/*     */   }
/*     */ 
/*     */   Provider createProvider(TSProviderImpl tsProvider)
/*     */   {
/* 356 */     if (tsProvider == null) {
/* 357 */       return null;
/*     */     }
/* 359 */     return (Provider)TsapiCreateObject.getTsapiObject(tsProvider, false);
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 366 */     TsapiTrace.traceEntry("run[]", this);
/* 367 */     synchronized (this.syncObject)
/*     */     {
/* 369 */       if (this.listener != null) {
/* 370 */         notifyListener();
/*     */       }
/*     */       else {
/* 373 */         notifyObserver();
/*     */       }
/*     */     }
/* 376 */     TsapiTrace.traceExit("run[]", this);
/*     */   }
/*     */ 
/*     */   private void notifyListener() {
/* 380 */     Event[] eventArray = null;
/* 381 */     synchronized (this.listenerEventList) {
/* 382 */       log.debug("Got this for ProviderListener - " + this.listener);
/* 383 */       eventArray = new Event[this.listenerEventList.size()];
/* 384 */       this.listenerEventList.copyInto(eventArray);
/* 385 */       this.listenerEventList.clear();
/*     */     }
/* 387 */     log.debug("calling callback in " + this.listener);
/*     */     try
/*     */     {
/* 391 */       for (Event event : eventArray) {
/* 392 */         switch (event.getID())
/*     */         {
/*     */         case 111:
/* 394 */           log.debug("calling providerInService in " + this.listener);
/* 395 */           this.listener.providerInService((ProviderEvent)event);
/* 396 */           log.debug("returned from providerInService in " + this.listener);
/* 397 */           break;
/*     */         case 113:
/* 399 */           log.debug("calling providerOutOfService in " + this.listener);
/* 400 */           this.listener.providerOutOfService((ProviderEvent)event);
/* 401 */           log.debug("returned from providerOutOfService in " + this.listener);
/* 402 */           break;
/*     */         case 114:
/* 404 */           log.debug("calling providerShutdown in " + this.listener);
/* 405 */           this.listener.providerShutdown((ProviderEvent)event);
/* 406 */           log.debug("returned from providerShutdown in " + this.listener);
/* 407 */           break;
/*     */         case 112:
/* 409 */           log.debug("calling providerEventTransmissionEnded in " + this.listener);
/* 410 */           this.listener.providerEventTransmissionEnded((ProviderEvent)event);
/* 411 */           log.debug("returned from providerEventTransmissionEnded in " + this.listener);
/* 412 */           break;
/*     */         case 602:
/* 414 */           if (this.listener instanceof PrivateDataProviderListener) {
/* 415 */             log.debug("calling providerPrivateData in " + this.listener);
/* 416 */             ((PrivateDataProviderListener)this.listener).providerPrivateData((PrivateDataEvent)event);
/* 417 */             log.debug("returned from providerPrivateData in " + this.listener);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 424 */       log.error("EXCEPTION thrown by callback in " + this.listener + " - " + e.getMessage(), e);
/*     */     }
/* 426 */     log.debug("returned from callback in " + this.listener);
/*     */   }
/*     */ 
/*     */   private void notifyObserver() {
/* 430 */     ProvEv[] eventArray = null;
/* 431 */     synchronized (this.observerEventList)
/*     */     {
/* 433 */       log.debug("Got this for ProviderObserver - " + this.observer);
/* 434 */       eventArray = new ProvEv[this.observerEventList.size()];
/* 435 */       this.observerEventList.copyInto(eventArray);
/* 436 */       this.observerEventList.clear();
/*     */     }
/* 438 */     log.debug("calling providerChangedEvent in " + this.observer);
/*     */     try
/*     */     {
/* 443 */       this.observer.providerChangedEvent(eventArray);
/*     */     }
/*     */     catch (Exception e) {
/* 446 */       log.error("EXCEPTION thrown by providerChangedEvent in " + this.observer + " - " + e.getMessage(), e);
/*     */     }
/* 448 */     log.debug("returned from providerChangedEvent in " + this.observer);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor
 * JD-Core Version:    0.5.4
 */