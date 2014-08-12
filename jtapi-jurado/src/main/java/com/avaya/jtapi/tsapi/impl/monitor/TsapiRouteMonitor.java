package com.avaya.jtapi.tsapi.impl.monitor;

import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.TsapiRouteSession;
import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSEvent;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.impl.events.route.TsapiReRouteEvent;
import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteCallbackEndedEvent;
import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteEndEvent;
import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteEvent;
import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteSessionEvent;
import com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteUsedEvent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcenter.RouteCallback;
import javax.telephony.callcenter.RouteSession;
import org.apache.log4j.Logger;

public final class TsapiRouteMonitor implements TsapiMonitor {
	private static Logger log = Logger.getLogger(TsapiRouteMonitor.class);
	TSProviderImpl provider;
	private final Vector<Object> eventList = new Vector<Object>();
	RouteCallback observer = null;
	long reference = 0L;

	Object syncObject = new Object();

	protected void dump(String indent) {
		log.trace(indent + "***** TsapiRouteMonitor DUMP *****");
		log.trace(indent + "TsapiRouteMonitor: " + this);
		log.trace(indent + "observer: " + this.observer);
		log.trace(indent + "***** TsapiRouteMonitor DUMP END *****");
	}

	public TsapiRouteMonitor(TSProviderImpl _provider, RouteCallback _observer) {
		this.provider = _provider;
		this.observer = _observer;

		this.provider.addRouteMonitorThread(this);
	}

	public RouteCallback getObserver() {
		return this.observer;
	}

	public synchronized void addReference() {
		this.reference += 1L;
	}

	public synchronized void deleteReference(TSDevice device) {
		this.reference -= 1L;

		if (device.sessionHash == null) {
			return;
		}

		log.debug("ROUTECALLBACKENDEDEVENT for " + device + " for callback "
				+ this.observer);

		synchronized (this.eventList) {
			for (Enumeration<?> e = device.sessionHash.elements(); e
					.hasMoreElements();) {
				TSRouteSession tsRouteSession;
				try {
					tsRouteSession = (TSRouteSession) e.nextElement();
					Object tsapiEvent = tsRouteSession.setState(3);
					this.eventList.addElement(tsapiEvent);
				} catch (NoSuchElementException e1) {
					log.error(e1.getMessage(), e1);
				}
				continue;

			}

			TsapiRouteCallbackEndedEvent event = new TsapiRouteCallbackEndedEvent(
					createRouteAddress(device));

			this.eventList.addElement(event);
		}
		if (this.reference <= 0L) {
			this.provider.removeRouteMonitorThread(this);
		}

		JtapiEventThreadManager.execute(this);
	}

	public synchronized void deliverEvent(TSEvent event) {
		TSRouteSession targetRouteSession = (TSRouteSession) event
				.getEventTarget();

		switch (event.getEventType()) {
		case 60:
			log.info("REROUTEEVENT for callback " + this.observer);
			this.eventList.addElement(new TsapiReRouteEvent(
					createRouteSession(targetRouteSession)));
			break;
		case 62:
			log.info("ROUTEENDEVENT for callback " + this.observer);
			this.eventList.addElement(new TsapiRouteEndEvent(
					createRouteSession(targetRouteSession)));
			break;
		case 63:
			log.info("ROUTEEVENT for callback " + this.observer);
			this.eventList.addElement(new TsapiRouteEvent(
					createRouteSession(targetRouteSession),
					createRouteAddress(targetRouteSession.currentRouteDevice),
					createAddress(targetRouteSession.callingAddress),
					createTerminal(targetRouteSession.callingTerminal),
					targetRouteSession.routeSelectAlgorithm,
					targetRouteSession.isdnSetupMessage));

			break;
		case 64:
			log.info("ROUTEUSEDEVENT for callback " + this.observer);
			this.eventList.addElement(new TsapiRouteUsedEvent(
					createRouteSession(targetRouteSession),
					createAddress(targetRouteSession.routeUsedDevice),
					createTerminal(targetRouteSession.routeUsedDevice),
					createAddress(targetRouteSession.callingAddress),
					createTerminal(targetRouteSession.callingTerminal),
					targetRouteSession.outOfDomain));
		case 61:
		}

		JtapiEventThreadManager.execute(this);
	}

	private Address createAddress(TSDevice device) {
		if (device == null) {
			return null;
		}

		return (Address) TsapiCreateObject.getTsapiObject(device, true);
	}

	private Terminal createTerminal(TSDevice device) {
		if (device == null) {
			return null;
		}

		return (Terminal) TsapiCreateObject.getTsapiObject(device, false);
	}

	private RouteAddress createRouteAddress(TSDevice device) {
		if (device == null) {
			return null;
		}
		return (RouteAddress) TsapiCreateObject.getTsapiObject(device, true);
	}

	private RouteSession createRouteSession(TSRouteSession session) {
		if (session == null) {
			return null;
		}
		return (RouteSession) TsapiCreateObject.getTsapiObject(session, false);
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		synchronized (this.syncObject) {
			Vector<Object> sendEventList = null;
			synchronized (this) {
				synchronized (this.eventList) {
					sendEventList = new Vector<Object>(this.eventList);
					this.eventList.clear();
				}
			}
			for (int i = 0; i < sendEventList.size(); i++) {
				Object event = sendEventList.elementAt(i);

				int crossRef = -1;
				if ((event instanceof TsapiRouteSessionEvent)) {
					TsapiRouteSessionEvent rSesE = (TsapiRouteSessionEvent) event;
					TsapiRouteSession rSes = (TsapiRouteSession) rSesE
							.getRouteSession();
					crossRef = rSes.getRouteCrossRefID();
				}

				if ((event instanceof TsapiRouteEvent)) {
					log.debug("calling routeEvent in " + this.observer
							+ " CrossRef " + crossRef);
					this.observer.routeEvent((TsapiRouteEvent) event);
					log.debug("returned from routeEvent in " + this.observer
							+ " CrossRef " + crossRef);
				} else if ((event instanceof TsapiReRouteEvent)) {
					log.debug("calling reRouteEvent in " + this.observer
							+ " CrossRef " + crossRef);
					this.observer.reRouteEvent((TsapiReRouteEvent) event);
					log.debug("returned from reRouteEvent in " + this.observer
							+ " CrossRef " + crossRef);
				} else if ((event instanceof TsapiRouteUsedEvent)) {
					log.debug("calling routeUsedEvent in " + this.observer
							+ " CrossRef " + crossRef);
					this.observer.routeUsedEvent((TsapiRouteUsedEvent) event);
					log.debug("returned from routeUsedEvent in "
							+ this.observer + " CrossRef " + crossRef);
				} else if ((event instanceof TsapiRouteEndEvent)) {
					log.debug("calling routeEndEvent in " + this.observer
							+ " CrossRef " + crossRef);
					this.observer.routeEndEvent((TsapiRouteEndEvent) event);
					log.debug("returned from routeEndEvent in " + this.observer
							+ " CrossRef " + crossRef);
				} else if ((event instanceof TsapiRouteCallbackEndedEvent)) {
					log.debug("calling routeCallbackEndedEvent in "
							+ this.observer + " CrossRef " + crossRef);
					this.observer
							.routeCallbackEndedEvent((TsapiRouteCallbackEndedEvent) event);
					log.debug("returned from routeCallbackEndedEvent in "
							+ this.observer + " CrossRef " + crossRef);
				}
			}
		}
		TsapiTrace.traceExit("run[]", this);
	}
}