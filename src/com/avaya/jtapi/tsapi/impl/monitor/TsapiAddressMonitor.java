package com.avaya.jtapi.tsapi.impl.monitor;

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

public final class TsapiAddressMonitor implements TsapiMonitor {
	private static Logger log = Logger.getLogger(TsapiAddressMonitor.class);
	TSProviderImpl provider;
	AddressObserver observer = null;
	AddressListener addressListener;
	private final Vector<AddrEv> eventList;
	private final Vector<Event> listenerEventList;
	long reference = 0L;
	BitSet observerType = new BitSet(8);

	Object syncObject = new Object();

	public TsapiAddressMonitor(TSProviderImpl _provider,
			AddressListener _addressListener) {
		provider = _provider;
		addressListener = _addressListener;
		listenerEventList = new Vector();
		eventList = null;
		provider.addAddressMonitorThread(this);

		deliverEvents(null, false);
	}

	public TsapiAddressMonitor(TSProviderImpl _provider,
			AddressObserver _observer) {
		provider = _provider;
		observer = _observer;
		eventList = new Vector();
		listenerEventList = null;
		provider.addAddressMonitorThread(this);

		observerType.set(0);
		if (observer instanceof CallControlAddressObserver) {
			observerType.set(1);
		}
		if (observer instanceof ACDAddressObserver) {
			observerType.set(2);
		}

		observerType.set(5);

		deliverEvents(null, false);
	}

	private void addCallCenterAddressEvents(ACDAddressEvent event,
			String tsEventLog) {
		if (!(addressListener instanceof ACDAddressListener)) {
			return;
		}
		log.debug(tsEventLog + " for listener " + addressListener);

		listenerEventList.addElement(event);
	}

	private void addCallControlAddressEvents(CallControlAddressEvent event,
			String tsEventLog) {
		if (!(addressListener instanceof CallControlAddressListener)) {
			return;
		}
		log.debug(tsEventLog + " for listener " + addressListener);

		listenerEventList.addElement(event);
	}

	private void addCoreAddressEvents(AddressEvent event, String tsEventLog) {
		if (!(addressListener instanceof AddressListener)) {
			return;
		}
		log.debug(tsEventLog + " for listener " + addressListener);

		listenerEventList.addElement(event);
	}

	void addEvent(AddrEv event, String tsEventLog) {
		if ((observerType.get(0))
				&& (((ITsapiEvent) event).getEventPackage() == 0)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else if ((observerType.get(1))
				&& (((ITsapiEvent) event).getEventPackage() == 1)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else if ((observerType.get(2))
				&& (((ITsapiEvent) event).getEventPackage() == 2)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else if ((observerType.get(5))
				&& (((ITsapiEvent) event).getEventPackage() == 5)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else {
			log.debug(tsEventLog + " ignored");
		}
	}

	private void addPrivateEvents(PrivateDataEvent event, String tsEventLog) {
		if (!(addressListener instanceof PrivateDataAddressListener)) {
			return;
		}
		log.debug(tsEventLog + " for listener " + addressListener);

		listenerEventList.addElement(event);
	}

	public synchronized void addReference() {
		reference += 1L;
	}

	private Address createAddress(TSDevice device) {
		if (device == null) {
			return null;
		}

		return (Address) TsapiCreateObject.getTsapiObject(device, true);
	}

	private Agent createAgent(TSAgent agent) {
		if (agent == null) {
			return null;
		}

		return (Agent) TsapiCreateObject.getTsapiObject(agent, true);
	}

	private void createEventsForListener(Vector<TSEvent> _eventList,
			boolean snapshot) {
		if (_eventList == null) {
			return;
		}
		int cause;
		if (snapshot) {
			cause = 110;
		} else {
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
			tsEvent = (TSEvent) _eventList.elementAt(i);
			tsTarget = tsEvent.getEventTarget();
			if (tsTarget instanceof TSDevice) {
				target = (TSDevice) tsTarget;
				address = createAddress(target);
				source = address;
			} else if (tsTarget instanceof TSAgent) {
				targetAgent = (TSAgent) tsTarget;
				agent = createAgent(targetAgent);
				source = agent;
				target = targetAgent.getTSACDDevice();
				if (target == null) {
					target = tsEvent.getSkillDevice();
				}
				address = createAddress(target);
			}
			privateData = tsEvent.getPrivateData();
			if (privateData != null) {
				if (!privateData.equals(previousPrivateData)) {
					_eventList.add(new TSEvent(9999, tsEvent.getEventTarget(),
							privateData, provider));
					previousPrivateData = privateData;
				}

			} else {
				previousPrivateData = null;
			}
			AddressEventParams addressEventParams = new AddressEventParams();
			addressEventParams.setCause(cause);
			addressEventParams.setPrivateData(privateData);
			String tsEventLog;
			switch (tsEvent.getEventType()) {
			case 37:
				tsEventLog = "ADDRESSDONOTDISTURBEVENT for " + target;
				addressEventParams.setEventId(350);
				addressEventParams.setDoNotDisturbState(target.dndState);
				addressEventParams.setSource(source);
				addCallControlAddressEvents(new CallControlAddressEventImpl(
						addressEventParams, address), tsEventLog);
				break;
			case 38:
				tsEventLog = "ADDRESSMESSAGEWAITINGEVENT for " + target;
				addressEventParams.setSource(source);
				addressEventParams.setEventId(352);
				addressEventParams.setMwBits(target.msgWaitingBits);
				if (provider.isLucent()) {
					addCallControlAddressEvents(
							new LucentCallControlAddressMsgWaitingEventImpl(
									addressEventParams, address), tsEventLog);
				} else {
					addCallControlAddressEvents(
							new CallControlAddressEventImpl(addressEventParams,
									address), tsEventLog);
				}
				break;
			case 39:
				tsEventLog = "ADDRESSFORWARDEVENT for " + target;
				addressEventParams.setSource(source);
				addressEventParams.setEventId(351);
				addressEventParams.setCallControlForwarding(target
						.createForwarding());
				addCallControlAddressEvents(new CallControlAddressEventImpl(
						addressEventParams, address), tsEventLog);
				break;
			case 40:
				tsEventLog = "ADDRESSLOGGEDONEVENT for " + targetAgent;
				addressEventParams.setEventId(302);
				addressEventParams.setSource(source);
				addCallCenterAddressEvents(new ACDAddressEventImpl(
						addressEventParams, address, agent, privateData),
						tsEventLog);
				break;
			case 41:
				tsEventLog = "ADDRESSLOGGEDOFFEVENT for " + targetAgent;
				addressEventParams.setEventId(301);
				addressEventParams.setSource(source);
				addCallCenterAddressEvents(new ACDAddressEventImpl(
						addressEventParams, address, agent, privateData),
						tsEventLog);
				break;
			case 42:
				tsEventLog = "ADDRESSREADYEVENT for " + targetAgent;
				addressEventParams.setEventId(304);
				addressEventParams.setSource(source);
				addCallCenterAddressEvents(new ACDAddressEventImpl(
						addressEventParams, address, agent, privateData),
						tsEventLog);
				break;
			case 43:
				tsEventLog = "ADDRESSNOTREADYEVENT for " + targetAgent;
				addressEventParams.setEventId(303);
				addressEventParams.setSource(source);
				addCallCenterAddressEvents(new ACDAddressEventImpl(
						addressEventParams, address, agent, privateData),
						tsEventLog);
				break;
			case 44:
				tsEventLog = "ADDRESSWORKREADYEVENT for " + targetAgent;
				addressEventParams.setEventId(307);
				addressEventParams.setSource(source);
				addCallCenterAddressEvents(new ACDAddressEventImpl(
						addressEventParams, address, agent, privateData),
						tsEventLog);
				break;
			case 45:
				tsEventLog = "ADDRESSWORKNOTREADYEVENT for " + targetAgent;
				addressEventParams.setEventId(306);
				addressEventParams.setSource(source);
				addCallCenterAddressEvents(new ACDAddressEventImpl(
						addressEventParams, address, agent, privateData),
						tsEventLog);
				break;
			case 46:
				tsEventLog = "ADDRESSBUSYEVENT for " + targetAgent;
				addressEventParams.setEventId(300);
				addressEventParams.setSource(source);
				addCallCenterAddressEvents(new ACDAddressEventImpl(
						addressEventParams, address, agent, privateData),
						tsEventLog);
				break;
			case 9999:
				tsEventLog = "PRIVATEEVENT for " + target;
				addPrivateEvents(new PrivateDataEventImpl(600, cause, null,
						source, privateData), tsEventLog);
			}

		}

		if (listenerEventList.size() == 0) {
			log.debug("no events to send to " + addressListener);
			return;
		}
		JtapiEventThreadManager.execute(this);
	}

	private void createEventsForObserver(Vector<TSEvent> _eventList,
			boolean snapshot) {
		String tsEventLog = null;
		if (_eventList == null) {
			return;
		}
		int cause;
		int metaCode;
		if (snapshot) {
			metaCode = 135;
			cause = 110;
		} else {
			metaCode = 136;
			cause = 100;
		}

		int nextMetaEventIndex = eventList.size();

		TSEvent ev = null;
		Object tsTarget = null;
		TSDevice target = null;
		TSAgent agent = null;
		Object privateData = null;
		log.debug("meta event BEGIN: cause (" + cause + ") metaCode ("
				+ metaCode + ")" + " for " + observer);
		for (int i = 0; i < _eventList.size(); ++i) {
			ev = (TSEvent) _eventList.elementAt(i);
			tsTarget = ev.getEventTarget();
			if (tsTarget instanceof TSDevice) {
				target = (TSDevice) tsTarget;
			} else if (tsTarget instanceof TSAgent) {
				agent = (TSAgent) tsTarget;
				target = agent.getTSACDDevice();
				if (target == null) {
					target = ev.getSkillDevice();
				}
			}
			privateData = ev.getPrivateData();

			switch (ev.getEventType()) {
			case 37:
				tsEventLog = "ADDRESSDONOTDISTURBEVENT for " + target;

				addEvent(new TsapiAddressDNDEvent(createAddress(target),
						target.dndState, cause, metaCode, privateData),
						tsEventLog);

				break;
			case 38:
				tsEventLog = "ADDRESSMESSAGEWAITINGEVENT for " + target;

				if (provider.isLucent()) {
					addEvent(new LucentAddressMsgWaitingEventImpl(
							createAddress(target), target.msgWaitingBits,
							cause, metaCode, privateData), tsEventLog);
				} else {
					addEvent(new TsapiAddressMsgWaitingEvent(
							createAddress(target), target.msgWaitingBits,
							cause, metaCode, privateData), tsEventLog);
				}

				break;
			case 39:
				tsEventLog = "ADDRESSFORWARDEVENT for " + target;

				addEvent(
						new TsapiAddressForwardEvent(createAddress(target),
								target.createForwarding(), cause, metaCode,
								privateData), tsEventLog);

				break;
			case 40:
				tsEventLog = "ADDRESSLOGGEDONEVENT for " + agent;

				addEvent(new TsapiAddrLogOnEv(createAddress(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 41:
				tsEventLog = "ADDRESSLOGGEDOFFEVENT for " + agent;

				addEvent(new TsapiAddrLogOffEv(createAddress(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 42:
				tsEventLog = "ADDRESSREADYEVENT for " + agent;

				addEvent(new TsapiAddrReadyEv(createAddress(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 43:
				tsEventLog = "ADDRESSNOTREADYEVENT for " + agent;

				addEvent(new TsapiAddrNotReadyEv(createAddress(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 44:
				tsEventLog = "ADDRESSWORKREADYEVENT for " + agent;

				addEvent(new TsapiAddrWorkReadyEv(createAddress(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 45:
				tsEventLog = "ADDRESSWORKNOTREADYEVENT for " + agent;

				addEvent(new TsapiAddrWorkNotReadyEv(createAddress(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 46:
				tsEventLog = "ADDRESSBUSYEVENT for " + agent;

				addEvent(new TsapiAddrBusyEv(createAddress(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 9999:
				tsEventLog = "PRIVATEEVENT for " + target;

				addEvent(new TsapiPrivateAddressEvent(createAddress(target),
						cause, metaCode, privateData), tsEventLog);
			}

		}

		synchronized (eventList) {
			log.debug("meta event END for " + observer + " eventList size="
					+ eventList.size());

			if (eventList.size() == 0) {
				log.debug("no events to send to " + observer);
				return;
			}

			if (nextMetaEventIndex < eventList.size()) {
				((TsapiObserverEvent) eventList.elementAt(nextMetaEventIndex))
						.setNewMetaEventFlag();
			}
		}
		JtapiEventThreadManager.execute(this);
	}

	public void deleteReference(TSDevice device, int cause, Object privateData) {
		log
				.debug("Getting TsapiAddressMonitor lock to deliver deleteReference events for observer "
						+ observer);
		if (observer != null) {
			deleteReferenceInternalForObserver(device, cause, privateData);
		} else {
			deleteReferenceInternalForListener(device, cause, privateData);
		}
		JtapiEventThreadManager.execute(this);
	}

	private synchronized void deleteReferenceInternalForListener(
			TSDevice device, int cause, Object privateData) {
		String tsEventLog = null;
		reference -= 1L;
		tsEventLog = "OBSERVATIONENDEDEVENT for " + device;

		AddressEventParams addressEventParams = new AddressEventParams();
		addressEventParams.setCause(cause);
		addressEventParams.setEventId(100);
		Address address = createAddress(device);
		addressEventParams.setSource(address);
		addCoreAddressEvents(new AddressEventImpl(addressEventParams, address),
				tsEventLog);
		if (privateData != null) {
			tsEventLog = "PRIVATEEVENT for " + device;
			addPrivateEvents(new PrivateDataEventImpl(600, cause, null,
					address, privateData), tsEventLog);
		}

		if (reference > 0L) {
			return;
		}
		provider.removeAddressMonitorThread(this);
	}

	private synchronized void deleteReferenceInternalForObserver(
			TSDevice device, int cause, Object privateData) {
		String tsEventLog = null;
		reference -= 1L;

		log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136
				+ ")" + " for " + observer);
		tsEventLog = "OBSERVATIONENDEDEVENT for " + device;

		synchronized (eventList) {
			int nextMetaEventIndex = eventList.size();

			addEvent(new TsapiAddrObservationEndedEvent(createAddress(device),
					cause, privateData), tsEventLog);

			((TsapiObserverEvent) eventList.elementAt(nextMetaEventIndex))
					.setNewMetaEventFlag();
			if (privateData != null) {
				tsEventLog = "PRIVATEEVENT for " + device;
				addEvent(new TsapiPrivateAddressEvent(createAddress(device),
						cause, 136, privateData), tsEventLog);
			}
			log.debug("meta event END for " + observer + " eventList size="
					+ eventList.size());
		}
		if (reference > 0L) {
			return;
		}
		provider.removeAddressMonitorThread(this);
	}

	public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot) {
		if ((_eventList == null) || (_eventList.size() == 0)) {
			return;
		}
		if (observer != null) {
			log
					.debug("Getting TsapiAddressMonitor lock to deliver events for observer "
							+ observer);
		} else {
			log
					.debug("Getting TsapiAddressMonitor lock to deliver events for listener "
							+ addressListener);
		}
		synchronized (_eventList) {
			deliverEventsInternal(_eventList, snapshot);
		}
	}

	private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList,
			boolean snapshot) {
		if (observer != null) {
			createEventsForObserver(_eventList, snapshot);
		} else {
			createEventsForListener(_eventList, snapshot);
		}
	}

	private void dispatchEventsForlisteners() {
		log.debug("Got syncObject for AddressListener - " + addressListener);
		Event[] eventArray = null;
		synchronized (this) {
			log.debug("Got this for AddressListener - " + addressListener);
			synchronized (listenerEventList) {
				if (listenerEventList.size() == 0) {
					log
							.debug("TsapiAddressMonitor: events delivered by previous thread; no events to deliver in this thread");
					TsapiTrace.traceExit("run[]", this);
					return;
				}
				eventArray = new Event[listenerEventList.size()];
				listenerEventList.copyInto(eventArray);
				listenerEventList.clear();
			}
		}
		try {
			int i$;
			for (Event event : eventArray) {
				switch (event.getID()) {
				case 100:
					log.debug("calling addressListenerEnded in "
							+ addressListener);
					addressListener.addressListenerEnded((AddressEvent) event);
					log.debug("returned from addressListenerEnded in "
							+ addressListener);
					break;
				case 350:
					log.debug("calling addressDoNotDisturb in "
							+ addressListener);
					((CallControlAddressListener) addressListener)
							.addressDoNotDisturb((CallControlAddressEvent) event);
					log.debug("returned from addressDoNotDisturb in "
							+ addressListener);
					break;
				case 351:
					log.debug("calling addressForwarded in " + addressListener);
					((CallControlAddressListener) addressListener)
							.addressForwarded((CallControlAddressEvent) event);
					log.debug("returned from addressForwarded in "
							+ addressListener);
					break;
				case 352:
					log.debug("calling addressMessageWaiting in "
							+ addressListener);
					((CallControlAddressListener) addressListener)
							.addressMessageWaiting((CallControlAddressEvent) event);
					log.debug("returned from addressMessageWaiting in "
							+ addressListener);
					break;
				case 300:
					log.debug("calling acdAddressBusy in " + addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressBusy((ACDAddressEvent) event);
					log.debug("returned from acdAddressBusy in "
							+ addressListener);
					break;
				case 301:
					log.debug("calling acdAddressLoggedOff in "
							+ addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressLoggedOff((ACDAddressEvent) event);
					log.debug("returned from acdAddressLoggedOff in "
							+ addressListener);
					break;
				case 302:
					log.debug("calling acdAddressLoggedOn in "
							+ addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressLoggedOn((ACDAddressEvent) event);
					log.debug("returned from acdAddressLoggedOn in "
							+ addressListener);
					break;
				case 303:
					log.debug("calling acdAddressNotReady in "
							+ addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressNotReady((ACDAddressEvent) event);
					log.debug("returned from acdAddressNotReady in "
							+ addressListener);
					break;
				case 304:
					log.debug("calling acdAddressReady in " + addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressReady((ACDAddressEvent) event);
					log.debug("returned from acdAddressReady in "
							+ addressListener);
					break;
				case 305:
					log
							.debug("calling acdAddressUnknown in "
									+ addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressUnknown((ACDAddressEvent) event);
					log.debug("returned from acdAddressUnknown in "
							+ addressListener);
					break;
				case 306:
					log.debug("calling acdAddressWorkNotReady in "
							+ addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressWorkNotReady((ACDAddressEvent) event);
					log.debug("returned from acdAddressWorkNotReady in "
							+ addressListener);
					break;
				case 307:
					log.debug("calling acdAddressWorkReady in "
							+ addressListener);
					((ACDAddressListener) addressListener)
							.acdAddressWorkReady((ACDAddressEvent) event);
					log.debug("returned from acdAddressWorkReady in "
							+ addressListener);
					break;
				case 600:
					log.debug("calling addressPrivateData in "
							+ addressListener);
					((PrivateDataAddressListener) addressListener)
							.addressPrivateData((PrivateDataEvent) event);
					log.debug("returned from addressPrivateData in "
							+ addressListener);
				}
			}
		} catch (Exception e) {
			log.error("EXCEPTION thrown by addressChangedEvent in "
					+ addressListener + " - " + e.getMessage(), e);
		}
	}

	private void dispatchEventsForObservers() {
		log.debug("Got syncObject for Addressobserver - " + observer);
		AddrEv[] eventArray = null;
		synchronized (this) {
			log.debug("Got this for Addressobserver - " + observer);
			synchronized (eventList) {
				if (eventList.size() == 0) {
					log
							.debug("TsapiAddressMonitor: events delivered by previous thread; no events to deliver in this thread");
					TsapiTrace.traceExit("run[]", this);
					return;
				}
				eventArray = new AddrEv[eventList.size()];
				eventList.copyInto(eventArray);
				eventList.clear();
			}
		}
		log.debug("calling addressChangedEvent in " + observer);
		try {
			observer.addressChangedEvent(eventArray);
		} catch (Exception e) {
			log.error("EXCEPTION thrown by addressChangedEvent in " + observer
					+ " - " + e.getMessage(), e);
		}

		log.debug("returned from addressChangedEvent in " + observer);
	}

	public void dump(String indent) {
		log.trace(indent + "***** TsapiAddressMonitor DUMP *****");
		if (observer != null) {
			log.trace(indent + "TsapiAddressMonitor: " + this);
			log.trace(indent + "observer: " + observer);
		} else {
			log.trace(indent + "TsapiAddressListener: " + this);
			log.trace(indent + "listener: " + addressListener);
		}
		log.trace(indent + "***** TsapiAddressMonitor DUMP END *****");
	}

	public AddressListener getAddressListener() {
		return addressListener;
	}

	public AddressObserver getObserver() {
		return observer;
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		synchronized (syncObject) {
			if (observer != null) {
				dispatchEventsForObservers();
			} else {
				dispatchEventsForlisteners();
			}
		}
		TsapiTrace.traceExit("run[]", this);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor JD-Core Version: 0.5.4
 */