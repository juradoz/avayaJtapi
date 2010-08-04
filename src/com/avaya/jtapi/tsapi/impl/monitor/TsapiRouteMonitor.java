/*     */ package com.avaya.jtapi.tsapi.impl.monitor;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
/*     */ import com.avaya.jtapi.tsapi.impl.TsapiRouteSession;
/*     */ import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
/*     */ import com.avaya.jtapi.tsapi.impl.events.route.TsapiReRouteEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteCallbackEndedEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteEndEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteSessionEvent;
/*     */ import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteUsedEvent;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Vector;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.Terminal;
/*     */ import javax.telephony.callcenter.RouteAddress;
/*     */ import javax.telephony.callcenter.RouteCallback;
/*     */ import javax.telephony.callcenter.RouteSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class TsapiRouteMonitor
/*     */   implements TsapiMonitor
/*     */ {
/*  32 */   private static Logger log = Logger.getLogger(TsapiRouteMonitor.class);
/*     */   TSProviderImpl provider;
/*  35 */   private final Vector<Object> eventList = new Vector();
/*  36 */   RouteCallback observer = null;
/*  37 */   long reference = 0L;
/*     */ 
/* 179 */   Object syncObject = new Object();
/*     */ 
/*     */   protected void dump(String indent)
/*     */   {
/*  41 */     log.trace(indent + "***** TsapiRouteMonitor DUMP *****");
/*  42 */     log.trace(indent + "TsapiRouteMonitor: " + this);
/*  43 */     log.trace(indent + "observer: " + this.observer);
/*  44 */     log.trace(indent + "***** TsapiRouteMonitor DUMP END *****");
/*     */   }
/*     */ 
/*     */   public TsapiRouteMonitor(TSProviderImpl _provider, RouteCallback _observer)
/*     */   {
/*  51 */     this.provider = _provider;
/*  52 */     this.observer = _observer;
/*     */ 
/*  54 */     this.provider.addRouteMonitorThread(this);
/*     */   }
/*     */ 
/*     */   public RouteCallback getObserver()
/*     */   {
/*  59 */     return this.observer;
/*     */   }
/*     */ 
/*     */   public synchronized void addReference()
/*     */   {
/*  64 */     this.reference += 1L;
/*     */   }
/*     */ 
/*     */   public synchronized void deleteReference(TSDevice device)
/*     */   {
/*  69 */     this.reference -= 1L;
/*     */ 
/*  71 */     if (device.sessionHash == null)
/*     */     {
/*  73 */       return;
/*     */     }
/*     */ 
/*  76 */     log.debug("ROUTECALLBACKENDEDEVENT for " + device + " for callback " + this.observer);
/*     */ 
/*  79 */     synchronized (this.eventList) {
/*  80 */       for (Enumeration e = device.sessionHash.elements(); e.hasMoreElements(); )
/*     */       {
/*     */         TSRouteSession tsRouteSession;
/*     */         try
/*     */         {
/*  85 */           tsRouteSession = (TSRouteSession)e.nextElement();
/*     */         }
/*     */         catch (NoSuchElementException e1)
/*     */         {
/*  89 */           log.error(e1.getMessage(), e1);
continue;
/*  90 */         }
/*     */ 
/*  92 */         Object tsapiEvent = tsRouteSession.setState(3);
/*  93 */         this.eventList.addElement(tsapiEvent);
/*     */       }
/*     */ 
/*  97 */       TsapiRouteCallbackEndedEvent event = new TsapiRouteCallbackEndedEvent(createRouteAddress(device));
/*     */ 
/*  99 */       this.eventList.addElement(event);
/*     */     }
/* 101 */     if (this.reference <= 0L)
/*     */     {
/* 103 */       this.provider.removeRouteMonitorThread(this);
/*     */     }
/*     */ 
/* 106 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   public synchronized void deliverEvent(TSEvent event)
/*     */   {
/* 111 */     TSRouteSession targetRouteSession = (TSRouteSession)event.getEventTarget();
/*     */ 
/* 113 */     switch (event.getEventType())
/*     */     {
/*     */     case 59:
/* 116 */       log.info("REROUTEEVENT for callback " + this.observer);
/* 117 */       this.eventList.addElement(new TsapiReRouteEvent(createRouteSession(targetRouteSession)));
/* 118 */       break;
/*     */     case 61:
/* 121 */       log.info("ROUTEENDEVENT for callback " + this.observer);
/* 122 */       this.eventList.addElement(new TsapiRouteEndEvent(createRouteSession(targetRouteSession)));
/* 123 */       break;
/*     */     case 62:
/* 126 */       log.info("ROUTEEVENT for callback " + this.observer);
/* 127 */       this.eventList.addElement(new TsapiRouteEvent(createRouteSession(targetRouteSession), createRouteAddress(targetRouteSession.currentRouteDevice), createAddress(targetRouteSession.callingAddress), createTerminal(targetRouteSession.callingTerminal), targetRouteSession.routeSelectAlgorithm, targetRouteSession.isdnSetupMessage));
/*     */ 
/* 133 */       break;
/*     */     case 63:
/* 136 */       log.info("ROUTEUSEDEVENT for callback " + this.observer);
/* 137 */       this.eventList.addElement(new TsapiRouteUsedEvent(createRouteSession(targetRouteSession), createAddress(targetRouteSession.routeUsedDevice), createTerminal(targetRouteSession.routeUsedDevice), createAddress(targetRouteSession.callingAddress), createTerminal(targetRouteSession.callingTerminal), targetRouteSession.outOfDomain));
/*     */     case 60:
/*     */     }
/*     */ 
/* 146 */     JtapiEventThreadManager.execute(this);
/*     */   }
/*     */ 
/*     */   private Address createAddress(TSDevice device) {
/* 150 */     if (device == null) {
/* 151 */       return null;
/*     */     }
/*     */ 
/* 154 */     return (Address)TsapiCreateObject.getTsapiObject(device, true);
/*     */   }
/*     */ 
/*     */   private Terminal createTerminal(TSDevice device) {
/* 158 */     if (device == null) {
/* 159 */       return null;
/*     */     }
/*     */ 
/* 162 */     return (Terminal)TsapiCreateObject.getTsapiObject(device, false);
/*     */   }
/*     */ 
/*     */   private RouteAddress createRouteAddress(TSDevice device) {
/* 166 */     if (device == null) {
/* 167 */       return null;
/*     */     }
/* 169 */     return (RouteAddress)TsapiCreateObject.getTsapiObject(device, true);
/*     */   }
/*     */ 
/*     */   private RouteSession createRouteSession(TSRouteSession session) {
/* 173 */     if (session == null) {
/* 174 */       return null;
/*     */     }
/* 176 */     return (RouteSession)TsapiCreateObject.getTsapiObject(session, false);
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 184 */     TsapiTrace.traceEntry("run[]", this);
/* 185 */     synchronized (this.syncObject)
/*     */     {
/* 187 */       Vector sendEventList = null;
/* 188 */       synchronized (this)
/*     */       {
/* 190 */         synchronized (this.eventList) {
/* 191 */           sendEventList = new Vector(this.eventList);
/* 192 */           this.eventList.clear();
/*     */         }
/*     */       }
/* 195 */       for (int i = 0; i < sendEventList.size(); ++i)
/*     */       {
/* 197 */         Object event = sendEventList.elementAt(i);
/* 199 */         i = -1;
/* 200 */         if (event instanceof TsapiRouteSessionEvent)
/*     */         {
/* 203 */           TsapiRouteSessionEvent rSesE = (TsapiRouteSessionEvent)event;
/* 204 */           TsapiRouteSession rSes = (TsapiRouteSession)rSesE.getRouteSession();
/* 205 */           i = rSes.getRouteCrossRefID();
/*     */         }
/*     */ 
/* 208 */         if (event instanceof TsapiRouteEvent)
/*     */         {
/* 210 */           log.debug("calling routeEvent in " + this.observer + " CrossRef " + i);
/* 211 */           this.observer.routeEvent((TsapiRouteEvent)event);
/* 212 */           log.debug("returned from routeEvent in " + this.observer + " CrossRef " + i);
/*     */         }
/* 214 */         else if (event instanceof TsapiReRouteEvent)
/*     */         {
/* 216 */           log.debug("calling reRouteEvent in " + this.observer + " CrossRef " + i);
/* 217 */           this.observer.reRouteEvent((TsapiReRouteEvent)event);
/* 218 */           log.debug("returned from reRouteEvent in " + this.observer + " CrossRef " + i);
/*     */         }
/* 220 */         else if (event instanceof TsapiRouteUsedEvent)
/*     */         {
/* 222 */           log.debug("calling routeUsedEvent in " + this.observer + " CrossRef " + i);
/* 223 */           this.observer.routeUsedEvent((TsapiRouteUsedEvent)event);
/* 224 */           log.debug("returned from routeUsedEvent in " + this.observer + " CrossRef " + i);
/*     */         }
/* 226 */         else if (event instanceof TsapiRouteEndEvent)
/*     */         {
/* 228 */           log.debug("calling routeEndEvent in " + this.observer + " CrossRef " + i);
/* 229 */           this.observer.routeEndEvent((TsapiRouteEndEvent)event);
/* 230 */           log.debug("returned from routeEndEvent in " + this.observer + " CrossRef " + i);
/*     */         } else {
/* 232 */           if (!(event instanceof TsapiRouteCallbackEndedEvent))
/*     */             continue;
/* 234 */           log.debug("calling routeCallbackEndedEvent in " + this.observer + " CrossRef " + i);
/* 235 */           this.observer.routeCallbackEndedEvent((TsapiRouteCallbackEndedEvent)event);
/* 236 */           log.debug("returned from routeCallbackEndedEvent in " + this.observer + " CrossRef " + i);
/*     */         }
/*     */       }
/*     */     }
/* 240 */     TsapiTrace.traceExit("run[]", this);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor
 * JD-Core Version:    0.5.4
 */