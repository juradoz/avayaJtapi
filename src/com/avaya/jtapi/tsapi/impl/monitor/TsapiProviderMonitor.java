 package com.avaya.jtapi.tsapi.impl.monitor;
 
 import com.avaya.jtapi.tsapi.ITsapiEvent;
 import com.avaya.jtapi.tsapi.TSProvider;
 import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
 import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
 import com.avaya.jtapi.tsapi.impl.core.TSEvent;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.impl.events.PrivateDataEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;
 import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
 import com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventParams;
 import com.avaya.jtapi.tsapi.impl.events.provider.TsapiPrivateProviderEvent;
 import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProvObservationEndedEvent;
 import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderInServiceEvent;
 import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderOutOfServiceEvent;
 import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderShutdownEvent;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.util.Vector;
 import javax.telephony.Event;
 import javax.telephony.Provider;
 import javax.telephony.ProviderEvent;
 import javax.telephony.ProviderListener;
 import javax.telephony.ProviderObserver;
 import javax.telephony.events.ProvEv;
 import javax.telephony.privatedata.PrivateDataEvent;
 import javax.telephony.privatedata.PrivateDataProviderListener;
 import javax.telephony.privatedata.events.PrivateProvEv;
 import org.apache.log4j.Logger;
 
 public class TsapiProviderMonitor
   implements TsapiMonitor
 {
   private static final Logger log = Logger.getLogger(TsapiProviderMonitor.class);
   protected final TSProviderImpl provider;
   private final ProviderObserver observer;
   protected final Vector<ProvEv> observerEventList = new Vector();
   protected final Vector<TsapiListenerEvent> listenerEventList = new Vector();
   private long reference = 0L;
   private final Object syncObject = new Object();
   private final ProviderListener listener;
 
   public TsapiProviderMonitor(TSProviderImpl _provider, ProviderObserver _observer)
   {
     this.provider = _provider;
     this.observer = _observer;
 
     this.provider.addProviderMonitorThread(this);
     this.listener = null;
 
     deliverEvents(null, false);
   }
 
   public TsapiProviderMonitor(TSProviderImpl impl, ProviderListener _listener)
   {
     this.listener = _listener;
     this.observer = null;
     this.provider = impl;
     this.provider.addProviderMonitorThread(this);
 
     deliverEvents(null, false);
   }
 
   public Object getMonitor()
   {
     if (this.listener != null) {
       return this.listener;
     }
     return this.observer;
   }
 
   public synchronized void addReference()
   {
     this.reference += 1L;
   }
 
   public void deleteReference(int cause, Object privateData)
   {
     log.debug("Getting TsapiProviderMonitor lock to deliver deleteReference events for observer " + this.observer);
     if (this.observer != null) {
       deleteObserverReference(cause, privateData);
     }
     else {
       deleteListenerReference(cause, privateData);
     }
     JtapiEventThreadManager.execute(this);
   }
 
   private synchronized void deleteObserverReference(int cause, Object privateData) {
     String tsEventLog = null;
     this.reference -= 1L;
 
     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.observer);
     tsEventLog = "OBSERVATIONENDEDEVENT for " + this.provider;
 
     synchronized (this.observerEventList) {
       int nextMetaEventIndex = this.observerEventList.size();
 
       addEv(new TsapiProvObservationEndedEvent(createProvider(this.provider), cause, privateData), tsEventLog);
 
       ((TsapiObserverEvent)this.observerEventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
     }
 
     if (privateData != null)
     {
       tsEventLog = "PRIVATEEVENT for " + this.provider;
       addEv(new TsapiPrivateProviderEvent(createProvider(this.provider), cause, 136, privateData), tsEventLog);
     }
 
     log.debug("meta event END for " + this.observer + " eventList size=" + this.observerEventList.size());
 
     if (this.reference > 0L)
       return;
     this.provider.removeProviderMonitorThread(this);
   }
 
   private synchronized void deleteListenerReference(int cause, Object privateData)
   {
     String tsEventLog = null;
     this.reference -= 1L;
 
     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.listener);
     tsEventLog = "OBSERVATIONENDEDEVENT for " + this.provider;
 
     ProviderEventParams params = new ProviderEventParams(createProvider(this.provider), 112, cause, null, createProvider(this.provider), privateData);
 
     addEvent(new ProviderEventImpl(params), tsEventLog);
 
     if (privateData != null)
     {
       tsEventLog = "PRIVATEEVENT for " + this.provider;
       addEvent(new PrivateDataEventImpl(602, cause, null, createProvider(this.provider), privateData), tsEventLog);
     }
 
     log.debug("meta event END for " + this.listener + " eventList size=" + this.listenerEventList.size());
 
     if (this.reference > 0L)
       return;
     this.provider.removeProviderMonitorThread(this);
   }
 
   private void addEvent(TsapiListenerEvent impl, String tsEventLog)
   {
     if ((impl instanceof PrivateDataEvent) && (this.listener instanceof PrivateDataProviderListener)) {
       this.listenerEventList.add(impl);
     }
     else if ((impl instanceof ProviderEvent) && (this.listener instanceof ProviderListener))
       this.listenerEventList.add(impl);
   }
 
   private void addEv(ProvEv event, String tsEventLog)
   {
     if ((event instanceof PrivateProvEv) && (((ITsapiEvent)event).getEventPackage() == 5))
     {
       log.debug(tsEventLog + " for observer " + this.observer);
 
       this.observerEventList.addElement(event);
     }
     else if (((ITsapiEvent)event).getEventPackage() == 0) {
       log.debug(tsEventLog + " for observer " + this.observer);
 
       this.observerEventList.addElement(event);
     }
     else
     {
       log.debug(tsEventLog + " ignored");
     }
   }
 
   public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot)
   {
     log.debug("Getting TsapiProviderMonitor lock to deliver events for observer " + this.observer);
     if ((_eventList == null) || (_eventList.size() == 0)) {
       return;
     }
     synchronized (_eventList) {
       if (this.observer != null) {
         deliverObserverEvents(_eventList, snapshot);
       }
       else {
         deliverListenerEvents(_eventList, snapshot);
       }
       if ((_eventList != null) && (_eventList.size() != 0))
         JtapiEventThreadManager.execute(this);
     }
   }
 
   protected synchronized void deliverObserverEvents(Vector<TSEvent> _eventList, boolean snapshot)
   {
     String tsEventLog = null;
     if (_eventList == null)
     {
       return;
     }
     int cause;
     int metaCode;
     if (snapshot)
     {
       metaCode = 135;
       cause = 110;
     }
     else
     {
       metaCode = 136;
       cause = 100;
     }
 
     int nextMetaEventIndex = this.observerEventList.size();
 
     Object privateData = null;
 
     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + metaCode + ")" + " for " + this.observer);
 
     for (int i = 0; i < _eventList.size(); ++i) {
       TSEvent ev = (TSEvent)_eventList.elementAt(i);
 
       privateData = ev.getPrivateData();
 
       switch (ev.getEventType())
       {
       case 1:
         tsEventLog = "PROVIDERINSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
 
         addEv(new TsapiProviderInServiceEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 2:
         tsEventLog = "PROVIDEROUTOFSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
 
         addEv(new TsapiProviderOutOfServiceEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 3:
         tsEventLog = "PROVIDERSHUTDOWNEVENT for " + (TSProvider)ev.getEventTarget();
 
         addEv(new TsapiProviderShutdownEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 9999:
         tsEventLog = "PRIVATEEVENT for " + (TSProvider)ev.getEventTarget();
 
         addEv(new TsapiPrivateProviderEvent(createProvider((TSProviderImpl)ev.getEventTarget()), cause, metaCode, privateData), tsEventLog);
       }
 
     }
 
     synchronized (this.observerEventList) {
       log.debug("meta event END for " + this.observer + " eventList size=" + this.observerEventList.size());
 
       if (this.observerEventList.size() == 0)
       {
         log.debug("no events to send to " + this.observer);
         return;
       }
 
       if (nextMetaEventIndex < this.observerEventList.size())
       {
         ((TsapiObserverEvent)this.observerEventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
       }
     }
   }
 
   protected synchronized void deliverListenerEvents(Vector<TSEvent> _eventList, boolean snapshot) {
     String tsEventLog = null;
     if (_eventList == null)
       return;
     int cause;
     if (snapshot) {
       cause = 110;
     }
     else {
       cause = 100;
     }
 
     Object privateData = null;
 
     log.debug("meta event BEGIN: cause (" + cause + ") for " + this.listener);
 
     for (int i = 0; i < _eventList.size(); ++i)
     {
       TSEvent ev = (TSEvent)_eventList.elementAt(i);
 
       privateData = ev.getPrivateData();
       ProviderEventParams params = null;
       switch (ev.getEventType())
       {
       case 1:
         tsEventLog = "PROVIDERINSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
 
         params = new ProviderEventParams(createProvider(this.provider), 111, cause, null, createProvider(this.provider), privateData);
 
         addEvent(new ProviderEventImpl(params), tsEventLog);
         break;
       case 2:
         tsEventLog = "PROVIDEROUTOFSERVICEEVENT for " + (TSProvider)ev.getEventTarget();
 
         params = new ProviderEventParams(createProvider(this.provider), 113, cause, null, createProvider(this.provider), privateData);
 
         addEvent(new ProviderEventImpl(params), tsEventLog);
         break;
       case 3:
         tsEventLog = "PROVIDERSHUTDOWNEVENT for " + (TSProvider)ev.getEventTarget();
 
         params = new ProviderEventParams(createProvider(this.provider), 114, cause, null, createProvider(this.provider), privateData);
 
         addEvent(new ProviderEventImpl(params), tsEventLog);
         break;
       case 9999:
         tsEventLog = "PRIVATEEVENT for " + (TSProvider)ev.getEventTarget();
 
         addEvent(new PrivateDataEventImpl(602, cause, null, createProvider(this.provider), privateData), tsEventLog);
       }
     }
 
     int size = this.listenerEventList.size();
     log.debug("meta event END for " + this.listener + " eventList size=" + size);
 
     if (size != 0)
       return;
     log.debug("no events to send to " + this.listener);
   }
 
   Provider createProvider(TSProviderImpl tsProvider)
   {
     if (tsProvider == null) {
       return null;
     }
     return (Provider)TsapiCreateObject.getTsapiObject(tsProvider, false);
   }
 
   public void run()
   {
     TsapiTrace.traceEntry("run[]", this);
     synchronized (this.syncObject)
     {
       if (this.listener != null) {
         notifyListener();
       }
       else {
         notifyObserver();
       }
     }
     TsapiTrace.traceExit("run[]", this);
   }
 
   private void notifyListener() {
     Event[] eventArray = null;
     synchronized (this.listenerEventList) {
       log.debug("Got this for ProviderListener - " + this.listener);
       eventArray = new Event[this.listenerEventList.size()];
       this.listenerEventList.copyInto(eventArray);
       this.listenerEventList.clear();
     }
     log.debug("calling callback in " + this.listener);
     try
     {
       for (Event event : eventArray) {
         switch (event.getID())
         {
         case 111:
           log.debug("calling providerInService in " + this.listener);
           this.listener.providerInService((ProviderEvent)event);
           log.debug("returned from providerInService in " + this.listener);
           break;
         case 113:
           log.debug("calling providerOutOfService in " + this.listener);
           this.listener.providerOutOfService((ProviderEvent)event);
           log.debug("returned from providerOutOfService in " + this.listener);
           break;
         case 114:
           log.debug("calling providerShutdown in " + this.listener);
           this.listener.providerShutdown((ProviderEvent)event);
           log.debug("returned from providerShutdown in " + this.listener);
           break;
         case 112:
           log.debug("calling providerEventTransmissionEnded in " + this.listener);
           this.listener.providerEventTransmissionEnded((ProviderEvent)event);
           log.debug("returned from providerEventTransmissionEnded in " + this.listener);
           break;
         case 602:
           if (this.listener instanceof PrivateDataProviderListener) {
             log.debug("calling providerPrivateData in " + this.listener);
             ((PrivateDataProviderListener)this.listener).providerPrivateData((PrivateDataEvent)event);
             log.debug("returned from providerPrivateData in " + this.listener);
           }
         }
       }
     }
     catch (Exception e)
     {
       log.error("EXCEPTION thrown by callback in " + this.listener + " - " + e.getMessage(), e);
     }
     log.debug("returned from callback in " + this.listener);
   }
 
   private void notifyObserver() {
     ProvEv[] eventArray = null;
     synchronized (this.observerEventList)
     {
       log.debug("Got this for ProviderObserver - " + this.observer);
       eventArray = new ProvEv[this.observerEventList.size()];
       this.observerEventList.copyInto(eventArray);
       this.observerEventList.clear();
     }
     log.debug("calling providerChangedEvent in " + this.observer);
     try
     {
       this.observer.providerChangedEvent(eventArray);
     }
     catch (Exception e) {
       log.error("EXCEPTION thrown by providerChangedEvent in " + this.observer + " - " + e.getMessage(), e);
     }
     log.debug("returned from providerChangedEvent in " + this.observer);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor
 * JD-Core Version:    0.5.4
 */