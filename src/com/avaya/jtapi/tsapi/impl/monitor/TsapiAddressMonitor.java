 package com.avaya.jtapi.tsapi.impl.monitor;
 
 import com.avaya.jtapi.tsapi.ITsapiEvent;
 import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
 import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
 import com.avaya.jtapi.tsapi.impl.core.TSAgent;
 import com.avaya.jtapi.tsapi.impl.core.TSDevice;
 import com.avaya.jtapi.tsapi.impl.core.TSEvent;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.impl.events.PrivateDataEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
 import com.avaya.jtapi.tsapi.impl.events.addr.ACDAddressEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.addr.AddressEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.addr.AddressEventParams;
 import com.avaya.jtapi.tsapi.impl.events.addr.CallControlAddressEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.addr.LucentAddressMsgWaitingEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.addr.LucentCallControlAddressMsgWaitingEventImpl;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrBusyEv;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrLogOffEv;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrLogOnEv;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrNotReadyEv;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrObservationEndedEvent;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrReadyEv;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrWorkNotReadyEv;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrWorkReadyEv;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressDNDEvent;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressForwardEvent;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressMsgWaitingEvent;
 import com.avaya.jtapi.tsapi.impl.events.addr.TsapiPrivateAddressEvent;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.util.BitSet;
 import java.util.Vector;
 import javax.telephony.Address;
 import javax.telephony.AddressEvent;
 import javax.telephony.AddressListener;
 import javax.telephony.AddressObserver;
 import javax.telephony.Event;
 import javax.telephony.callcenter.ACDAddressEvent;
 import javax.telephony.callcenter.ACDAddressListener;
 import javax.telephony.callcenter.ACDAddressObserver;
 import javax.telephony.callcenter.Agent;
 import javax.telephony.callcontrol.CallControlAddressEvent;
 import javax.telephony.callcontrol.CallControlAddressListener;
 import javax.telephony.callcontrol.CallControlAddressObserver;
 import javax.telephony.events.AddrEv;
 import javax.telephony.privatedata.PrivateDataAddressListener;
 import javax.telephony.privatedata.PrivateDataEvent;
 import org.apache.log4j.Logger;
 
 public final class TsapiAddressMonitor
   implements TsapiMonitor
 {
   private static Logger log = Logger.getLogger(TsapiAddressMonitor.class);
   TSProviderImpl provider;
   AddressObserver observer = null;
   AddressListener addressListener;
   private final Vector<AddrEv> eventList;
   private final Vector<Event> listenerEventList;
   long reference = 0L;
   BitSet observerType = new BitSet(8);
 
   Object syncObject = new Object();
 
   public void dump(String indent)
   {
     log.trace(indent + "***** TsapiAddressMonitor DUMP *****");
     if (this.observer != null)
     {
       log.trace(indent + "TsapiAddressMonitor: " + this);
       log.trace(indent + "observer: " + this.observer);
     }
     else
     {
       log.trace(indent + "TsapiAddressListener: " + this);
       log.trace(indent + "listener: " + this.addressListener);
     }
     log.trace(indent + "***** TsapiAddressMonitor DUMP END *****");
   }
 
   public TsapiAddressMonitor(TSProviderImpl _provider, AddressObserver _observer)
   {
     this.provider = _provider;
     this.observer = _observer;
     this.eventList = new Vector();
     this.listenerEventList = null;
     this.provider.addAddressMonitorThread(this);
 
     this.observerType.set(0);
     if (this.observer instanceof CallControlAddressObserver)
     {
       this.observerType.set(1);
     }
     if (this.observer instanceof ACDAddressObserver)
     {
       this.observerType.set(2);
     }
 
     this.observerType.set(5);
 
     deliverEvents(null, false);
   }
 
   public TsapiAddressMonitor(TSProviderImpl _provider, AddressListener _addressListener)
   {
     this.provider = _provider;
     this.addressListener = _addressListener;
     this.listenerEventList = new Vector();
     this.eventList = null;
     this.provider.addAddressMonitorThread(this);
 
     deliverEvents(null, false);
   }
 
   public AddressObserver getObserver()
   {
     return this.observer;
   }
 
   public synchronized void addReference()
   {
     this.reference += 1L;
   }
 
   public void deleteReference(TSDevice device, int cause, Object privateData)
   {
     log.debug("Getting TsapiAddressMonitor lock to deliver deleteReference events for observer " + this.observer);
     if (this.observer != null)
     {
       deleteReferenceInternalForObserver(device, cause, privateData);
     }
     else
     {
       deleteReferenceInternalForListener(device, cause, privateData);
     }
     JtapiEventThreadManager.execute(this);
   }
 
   private synchronized void deleteReferenceInternalForListener(TSDevice device, int cause, Object privateData)
   {
     String tsEventLog = null;
     this.reference -= 1L;
     tsEventLog = "OBSERVATIONENDEDEVENT for " + device;
 
     AddressEventParams addressEventParams = new AddressEventParams();
     addressEventParams.setCause(cause);
     addressEventParams.setEventId(100);
     Address address = createAddress(device);
     addressEventParams.setSource(address);
     addCoreAddressEvents(new AddressEventImpl(addressEventParams, address), tsEventLog);
     if (privateData != null)
     {
       tsEventLog = "PRIVATEEVENT for " + device;
       addPrivateEvents(new PrivateDataEventImpl(600, cause, null, address, privateData), tsEventLog);
     }
 
     if (this.reference > 0L)
       return;
     this.provider.removeAddressMonitorThread(this);
   }
 
   private synchronized void deleteReferenceInternalForObserver(TSDevice device, int cause, Object privateData)
   {
     String tsEventLog = null;
     this.reference -= 1L;
 
     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136 + ")" + " for " + this.observer);
     tsEventLog = "OBSERVATIONENDEDEVENT for " + device;
 
     synchronized (this.eventList) {
       int nextMetaEventIndex = this.eventList.size();
 
       addEvent(new TsapiAddrObservationEndedEvent(createAddress(device), cause, privateData), tsEventLog);
 
       ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
       if (privateData != null)
       {
         tsEventLog = "PRIVATEEVENT for " + device;
         addEvent(new TsapiPrivateAddressEvent(createAddress(device), cause, 136, privateData), tsEventLog);
       }
       log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
     }
     if (this.reference > 0L)
       return;
     this.provider.removeAddressMonitorThread(this);
   }
 
   private void addCallControlAddressEvents(CallControlAddressEvent event, String tsEventLog)
   {
     if (!(this.addressListener instanceof CallControlAddressListener))
       return;
     log.debug(tsEventLog + " for listener " + this.addressListener);
 
     this.listenerEventList.addElement(event);
   }
 
   private void addCallCenterAddressEvents(ACDAddressEvent event, String tsEventLog)
   {
     if (!(this.addressListener instanceof ACDAddressListener))
       return;
     log.debug(tsEventLog + " for listener " + this.addressListener);
 
     this.listenerEventList.addElement(event);
   }
 
   private void addCoreAddressEvents(AddressEvent event, String tsEventLog)
   {
     if (!(this.addressListener instanceof AddressListener))
       return;
     log.debug(tsEventLog + " for listener " + this.addressListener);
 
     this.listenerEventList.addElement(event);
   }
 
   private void addPrivateEvents(PrivateDataEvent event, String tsEventLog)
   {
     if (!(this.addressListener instanceof PrivateDataAddressListener))
       return;
     log.debug(tsEventLog + " for listener " + this.addressListener);
 
     this.listenerEventList.addElement(event);
   }
 
   void addEvent(AddrEv event, String tsEventLog)
   {
     if ((this.observerType.get(0)) && (((ITsapiEvent)event).getEventPackage() == 0))
     {
       log.debug(tsEventLog + " for observer " + this.observer);
 
       this.eventList.addElement(event);
     }
     else if ((this.observerType.get(1)) && (((ITsapiEvent)event).getEventPackage() == 1))
     {
       log.debug(tsEventLog + " for observer " + this.observer);
 
       this.eventList.addElement(event);
     }
     else if ((this.observerType.get(2)) && (((ITsapiEvent)event).getEventPackage() == 2))
     {
       log.debug(tsEventLog + " for observer " + this.observer);
 
       this.eventList.addElement(event);
     }
     else if ((this.observerType.get(5)) && (((ITsapiEvent)event).getEventPackage() == 5))
     {
       log.debug(tsEventLog + " for observer " + this.observer);
 
       this.eventList.addElement(event);
     }
     else
     {
       log.debug(tsEventLog + " ignored");
     }
   }
 
   public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot)
   {
     if ((_eventList == null) || (_eventList.size() == 0)) {
       return;
     }
     if (this.observer != null)
       log.debug("Getting TsapiAddressMonitor lock to deliver events for observer " + this.observer);
     else
       log.debug("Getting TsapiAddressMonitor lock to deliver events for listener " + this.addressListener);
     synchronized (_eventList) {
       deliverEventsInternal(_eventList, snapshot);
     }
   }
 
   private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList, boolean snapshot)
   {
     if (this.observer != null)
     {
       createEventsForObserver(_eventList, snapshot);
     }
     else
     {
       createEventsForListener(_eventList, snapshot);
     }
   }
 
   private void createEventsForListener(Vector<TSEvent> _eventList, boolean snapshot)
   {
     if (_eventList == null)
     {
       return;
     }
     int cause;
     if (snapshot)
     {
       cause = 110;
     }
     else
     {
       cause = 100;
     }
 
     TSEvent tsEvent = null;
     Object tsTarget = null;
     TSDevice target = null;
     TSAgent targetAgent = null;
     Object privateData = null;
     Object previousPrivateData = null;
     Address address = null;
     Agent agent = null;
     Object source = null;
     for (int i = 0; i < _eventList.size(); ++i) {
       tsEvent = (TSEvent)_eventList.elementAt(i);
       tsTarget = tsEvent.getEventTarget();
       if (tsTarget instanceof TSDevice)
       {
         target = (TSDevice)tsTarget;
         address = createAddress(target);
         source = address;
       }
       else if (tsTarget instanceof TSAgent)
       {
         targetAgent = (TSAgent)tsTarget;
         agent = createAgent(targetAgent);
         source = agent;
         target = targetAgent.getTSACDDevice();
         if (target == null)
           target = tsEvent.getSkillDevice();
         address = createAddress(target);
       }
       privateData = tsEvent.getPrivateData();
       if (privateData != null)
       {
         if (!privateData.equals(previousPrivateData))
         {
           _eventList.add(new TSEvent(9999, tsEvent.getEventTarget(), privateData, this.provider));
           previousPrivateData = privateData;
         }
 
       }
       else {
         previousPrivateData = null;
       }
       AddressEventParams addressEventParams = new AddressEventParams();
       addressEventParams.setCause(cause);
       addressEventParams.setPrivateData(privateData);
       String tsEventLog;
       switch (tsEvent.getEventType())
       {
       case 37:
         tsEventLog = "ADDRESSDONOTDISTURBEVENT for " + target;
         addressEventParams.setEventId(350);
         addressEventParams.setDoNotDisturbState(target.dndState);
         addressEventParams.setSource(source);
         addCallControlAddressEvents(new CallControlAddressEventImpl(addressEventParams, address), tsEventLog);
         break;
       case 38:
         tsEventLog = "ADDRESSMESSAGEWAITINGEVENT for " + target;
         addressEventParams.setSource(source);
         addressEventParams.setEventId(352);
         addressEventParams.setMwBits(target.msgWaitingBits);
         if (this.provider.isLucent())
         {
           addCallControlAddressEvents(new LucentCallControlAddressMsgWaitingEventImpl(addressEventParams, address), tsEventLog);
         }
         else
         {
           addCallControlAddressEvents(new CallControlAddressEventImpl(addressEventParams, address), tsEventLog);
         }
         break;
       case 39:
         tsEventLog = "ADDRESSFORWARDEVENT for " + target;
         addressEventParams.setSource(source);
         addressEventParams.setEventId(351);
         addressEventParams.setCallControlForwarding(target.createForwarding());
         addCallControlAddressEvents(new CallControlAddressEventImpl(addressEventParams, address), tsEventLog);
         break;
       case 40:
         tsEventLog = "ADDRESSLOGGEDONEVENT for " + targetAgent;
         addressEventParams.setEventId(302);
         addressEventParams.setSource(source);
         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
         break;
       case 41:
         tsEventLog = "ADDRESSLOGGEDOFFEVENT for " + targetAgent;
         addressEventParams.setEventId(301);
         addressEventParams.setSource(source);
         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
         break;
       case 42:
         tsEventLog = "ADDRESSREADYEVENT for " + targetAgent;
         addressEventParams.setEventId(304);
         addressEventParams.setSource(source);
         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
         break;
       case 43:
         tsEventLog = "ADDRESSNOTREADYEVENT for " + targetAgent;
         addressEventParams.setEventId(303);
         addressEventParams.setSource(source);
         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
         break;
       case 44:
         tsEventLog = "ADDRESSWORKREADYEVENT for " + targetAgent;
         addressEventParams.setEventId(307);
         addressEventParams.setSource(source);
         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
         break;
       case 45:
         tsEventLog = "ADDRESSWORKNOTREADYEVENT for " + targetAgent;
         addressEventParams.setEventId(306);
         addressEventParams.setSource(source);
         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
         break;
       case 46:
         tsEventLog = "ADDRESSBUSYEVENT for " + targetAgent;
         addressEventParams.setEventId(300);
         addressEventParams.setSource(source);
         addCallCenterAddressEvents(new ACDAddressEventImpl(addressEventParams, address, agent, privateData), tsEventLog);
         break;
       case 9999:
         tsEventLog = "PRIVATEEVENT for " + target;
         addPrivateEvents(new PrivateDataEventImpl(600, cause, null, source, privateData), tsEventLog);
       }
 
     }
 
     if (this.listenerEventList.size() == 0)
     {
       log.debug("no events to send to " + this.addressListener);
       return;
     }
     JtapiEventThreadManager.execute(this);
   }
 
   private void createEventsForObserver(Vector<TSEvent> _eventList, boolean snapshot)
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
 
     int nextMetaEventIndex = this.eventList.size();
 
     TSEvent ev = null;
     Object tsTarget = null;
     TSDevice target = null;
     TSAgent agent = null;
     Object privateData = null;
     log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + metaCode + ")" + " for " + this.observer);
     for (int i = 0; i < _eventList.size(); ++i) {
       ev = (TSEvent)_eventList.elementAt(i);
       tsTarget = ev.getEventTarget();
       if (tsTarget instanceof TSDevice)
       {
         target = (TSDevice)tsTarget;
       }
       else if (tsTarget instanceof TSAgent)
       {
         agent = (TSAgent)tsTarget;
         target = agent.getTSACDDevice();
         if (target == null) {
           target = ev.getSkillDevice();
         }
       }
       privateData = ev.getPrivateData();
 
       switch (ev.getEventType())
       {
       case 37:
         tsEventLog = "ADDRESSDONOTDISTURBEVENT for " + target;
 
         addEvent(new TsapiAddressDNDEvent(createAddress(target), target.dndState, cause, metaCode, privateData), tsEventLog);
 
         break;
       case 38:
         tsEventLog = "ADDRESSMESSAGEWAITINGEVENT for " + target;
 
         if (this.provider.isLucent())
         {
           addEvent(new LucentAddressMsgWaitingEventImpl(createAddress(target), target.msgWaitingBits, cause, metaCode, privateData), tsEventLog);
         }
         else
         {
           addEvent(new TsapiAddressMsgWaitingEvent(createAddress(target), target.msgWaitingBits, cause, metaCode, privateData), tsEventLog);
         }
 
         break;
       case 39:
         tsEventLog = "ADDRESSFORWARDEVENT for " + target;
 
         addEvent(new TsapiAddressForwardEvent(createAddress(target), target.createForwarding(), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 40:
         tsEventLog = "ADDRESSLOGGEDONEVENT for " + agent;
 
         addEvent(new TsapiAddrLogOnEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 41:
         tsEventLog = "ADDRESSLOGGEDOFFEVENT for " + agent;
 
         addEvent(new TsapiAddrLogOffEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 42:
         tsEventLog = "ADDRESSREADYEVENT for " + agent;
 
         addEvent(new TsapiAddrReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 43:
         tsEventLog = "ADDRESSNOTREADYEVENT for " + agent;
 
         addEvent(new TsapiAddrNotReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 44:
         tsEventLog = "ADDRESSWORKREADYEVENT for " + agent;
 
         addEvent(new TsapiAddrWorkReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 45:
         tsEventLog = "ADDRESSWORKNOTREADYEVENT for " + agent;
 
         addEvent(new TsapiAddrWorkNotReadyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 46:
         tsEventLog = "ADDRESSBUSYEVENT for " + agent;
 
         addEvent(new TsapiAddrBusyEv(createAddress(target), createAgent(agent), cause, metaCode, privateData), tsEventLog);
 
         break;
       case 9999:
         tsEventLog = "PRIVATEEVENT for " + target;
 
         addEvent(new TsapiPrivateAddressEvent(createAddress(target), cause, metaCode, privateData), tsEventLog);
       }
 
     }
 
     synchronized (this.eventList) {
       log.debug("meta event END for " + this.observer + " eventList size=" + this.eventList.size());
 
       if (this.eventList.size() == 0)
       {
         log.debug("no events to send to " + this.observer);
         return;
       }
 
       if (nextMetaEventIndex < this.eventList.size())
       {
         ((TsapiObserverEvent)this.eventList.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
       }
     }
     JtapiEventThreadManager.execute(this);
   }
 
   private Address createAddress(TSDevice device) {
     if (device == null) {
       return null;
     }
 
     return (Address)TsapiCreateObject.getTsapiObject(device, true);
   }
 
   private Agent createAgent(TSAgent agent) {
     if (agent == null) {
       return null;
     }
 
     return (Agent)TsapiCreateObject.getTsapiObject(agent, true);
   }
 
   public void run()
   {
     TsapiTrace.traceEntry("run[]", this);
     synchronized (this.syncObject)
     {
       if (this.observer != null)
       {
         dispatchEventsForObservers();
       }
       else
       {
         dispatchEventsForlisteners();
       }
     }
     TsapiTrace.traceExit("run[]", this);
   }
 
   private void dispatchEventsForlisteners()
   {
     log.debug("Got syncObject for AddressListener - " + this.addressListener);
     Event[] eventArray = null;
     synchronized (this)
     {
       log.debug("Got this for AddressListener - " + this.addressListener);
       synchronized (this.listenerEventList) {
         if (this.listenerEventList.size() == 0) {
           log.debug("TsapiAddressMonitor: events delivered by previous thread; no events to deliver in this thread");
           TsapiTrace.traceExit("run[]", this);
           return;
         }
         eventArray = new Event[this.listenerEventList.size()];
         this.listenerEventList.copyInto(eventArray);
         this.listenerEventList.clear();
       }
     }
     try
     {
       int i$;
       for (Event event : eventArray)
       {
         switch (event.getID())
         {
         case 100:
           log.debug("calling addressListenerEnded in " + this.addressListener);
           this.addressListener.addressListenerEnded((AddressEvent)event);
           log.debug("returned from addressListenerEnded in " + this.addressListener);
           break;
         case 350:
           log.debug("calling addressDoNotDisturb in " + this.addressListener);
           ((CallControlAddressListener)this.addressListener).addressDoNotDisturb((CallControlAddressEvent)event);
           log.debug("returned from addressDoNotDisturb in " + this.addressListener);
           break;
         case 351:
           log.debug("calling addressForwarded in " + this.addressListener);
           ((CallControlAddressListener)this.addressListener).addressForwarded((CallControlAddressEvent)event);
           log.debug("returned from addressForwarded in " + this.addressListener);
           break;
         case 352:
           log.debug("calling addressMessageWaiting in " + this.addressListener);
           ((CallControlAddressListener)this.addressListener).addressMessageWaiting((CallControlAddressEvent)event);
           log.debug("returned from addressMessageWaiting in " + this.addressListener);
           break;
         case 300:
           log.debug("calling acdAddressBusy in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressBusy((ACDAddressEvent)event);
           log.debug("returned from acdAddressBusy in " + this.addressListener);
           break;
         case 301:
           log.debug("calling acdAddressLoggedOff in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressLoggedOff((ACDAddressEvent)event);
           log.debug("returned from acdAddressLoggedOff in " + this.addressListener);
           break;
         case 302:
           log.debug("calling acdAddressLoggedOn in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressLoggedOn((ACDAddressEvent)event);
           log.debug("returned from acdAddressLoggedOn in " + this.addressListener);
           break;
         case 303:
           log.debug("calling acdAddressNotReady in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressNotReady((ACDAddressEvent)event);
           log.debug("returned from acdAddressNotReady in " + this.addressListener);
           break;
         case 304:
           log.debug("calling acdAddressReady in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressReady((ACDAddressEvent)event);
           log.debug("returned from acdAddressReady in " + this.addressListener);
           break;
         case 305:
           log.debug("calling acdAddressUnknown in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressUnknown((ACDAddressEvent)event);
           log.debug("returned from acdAddressUnknown in " + this.addressListener);
           break;
         case 306:
           log.debug("calling acdAddressWorkNotReady in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressWorkNotReady((ACDAddressEvent)event);
           log.debug("returned from acdAddressWorkNotReady in " + this.addressListener);
           break;
         case 307:
           log.debug("calling acdAddressWorkReady in " + this.addressListener);
           ((ACDAddressListener)this.addressListener).acdAddressWorkReady((ACDAddressEvent)event);
           log.debug("returned from acdAddressWorkReady in " + this.addressListener);
           break;
         case 600:
           log.debug("calling addressPrivateData in " + this.addressListener);
           ((PrivateDataAddressListener)this.addressListener).addressPrivateData((PrivateDataEvent)event);
           log.debug("returned from addressPrivateData in " + this.addressListener);
         }
       }
     }
     catch (Exception e)
     {
       log.error("EXCEPTION thrown by addressChangedEvent in " + this.addressListener + " - " + e.getMessage(), e);
     }
   }
 
   private void dispatchEventsForObservers()
   {
     log.debug("Got syncObject for Addressobserver - " + this.observer);
     AddrEv[] eventArray = null;
     synchronized (this)
     {
       log.debug("Got this for Addressobserver - " + this.observer);
       synchronized (this.eventList) {
         if (this.eventList.size() == 0) {
           log.debug("TsapiAddressMonitor: events delivered by previous thread; no events to deliver in this thread");
           TsapiTrace.traceExit("run[]", this);
           return;
         }
         eventArray = new AddrEv[this.eventList.size()];
         this.eventList.copyInto(eventArray);
         this.eventList.clear();
       }
     }
     log.debug("calling addressChangedEvent in " + this.observer);
     try
     {
       this.observer.addressChangedEvent(eventArray);
     }
     catch (Exception e) {
       log.error("EXCEPTION thrown by addressChangedEvent in " + this.observer + " - " + e.getMessage(), e);
     }
 
     log.debug("returned from addressChangedEvent in " + this.observer);
   }
 
   public AddressListener getAddressListener()
   {
     return this.addressListener;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor
 * JD-Core Version:    0.5.4
 */