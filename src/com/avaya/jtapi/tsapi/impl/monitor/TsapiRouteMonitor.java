package com.avaya.jtapi.tsapi.impl.monitor;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcenter.RouteCallback;
import javax.telephony.callcenter.RouteSession;

import org.apache.log4j.Logger;

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

public final class TsapiRouteMonitor implements TsapiMonitor {
	private static Logger log = Logger.getLogger(TsapiRouteMonitor.class);
	TSProviderImpl provider;
	private final Vector<Object> eventList = new Vector<Object>();
	RouteCallback observer = null;
	long reference = 0L;

	Object syncObject = new Object();

	public TsapiRouteMonitor(TSProviderImpl _provider, RouteCallback _observer) {
		provider = _provider;
		observer = _observer;

		provider.addRouteMonitorThread(this);
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

	private Terminal createTerminal(TSDevice device) {
		if (device == null) {
			return null;
		}

		return (Terminal) TsapiCreateObject.getTsapiObject(device, false);
	}

	public synchronized void deleteReference(TSDevice device) {
		reference -= 1L;

		if (device.sessionHash == null) {
			return;
		}

		log.debug("ROUTECALLBACKENDEDEVENT for " + device + " for callback "
				+ observer);

		synchronized (eventList) {
			for (Enumeration<TSRouteSession> e = device.sessionHash.elements(); e
					.hasMoreElements();) {
				TSRouteSession tsRouteSession;
				try {
					tsRouteSession = e.nextElement();
				} catch (NoSuchElementException e1) {
					log.error(e1.getMessage(), e1);
					continue;
				}

				Object tsapiEvent = tsRouteSession.setState(3);
				eventList.addElement(tsapiEvent);
			}

			TsapiRouteCallbackEndedEvent event = new TsapiRouteCallbackEndedEvent(
					createRouteAddress(device));

			eventList.addElement(event);
		}
		if (reference <= 0L) {
			provider.removeRouteMonitorThread(this);
		}

		JtapiEventThreadManager.execute(this);
	}

	public synchronized void deliverEvent(TSEvent event) {
		TSRouteSession targetRouteSession = (TSRouteSession) event
				.getEventTarget();

		switch (event.getEventType()) {
		case 59:
			log.info("REROUTEEVENT for callback " + observer);
			eventList.addElement(new TsapiReRouteEvent(
					createRouteSession(targetRouteSession)));
			break;
		case 61:
			log.info("ROUTEENDEVENT for callback " + observer);
			eventList.addElement(new TsapiRouteEndEvent(
					createRouteSession(targetRouteSession)));
			break;
		case 62:
			log.info("ROUTEEVENT for callback " + observer);
			eventList.addElement(new TsapiRouteEvent(
					createRouteSession(targetRouteSession),
					createRouteAddress(targetRouteSession.currentRouteDevice),
					createAddress(targetRouteSession.callingAddress),
					createTerminal(targetRouteSession.callingTerminal),
					targetRouteSession.routeSelectAlgorithm,
					targetRouteSession.isdnSetupMessage));

			break;
		case 63:
			log.info("ROUTEUSEDEVENT for callback " + observer);
			eventList.addElement(new TsapiRouteUsedEvent(
					createRouteSession(targetRouteSession),
					createAddress(targetRouteSession.routeUsedDevice),
					createTerminal(targetRouteSession.routeUsedDevice),
					createAddress(targetRouteSession.callingAddress),
					createTerminal(targetRouteSession.callingTerminal),
					targetRouteSession.outOfDomain));
		case 60:
		}

		JtapiEventThreadManager.execute(this);
	}

	protected void dump(String indent) {
		log.trace(indent + "***** TsapiRouteMonitor DUMP *****");
		log.trace(indent + "TsapiRouteMonitor: " + this);
		log.trace(indent + "observer: " + observer);
		log.trace(indent + "***** TsapiRouteMonitor DUMP END *****");
	}

	public RouteCallback getObserver() {
		return observer;
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		synchronized (syncObject) {
			Vector<Object> sendEventList = null;
			synchronized (this) {
				synchronized (eventList) {
					sendEventList = new Vector<Object>(eventList);
					eventList.clear();
				}
			}
			for (int i = 0; i < sendEventList.size(); ++i) {
				Object event = sendEventList.elementAt(i);
				i = -1;
				if (event instanceof TsapiRouteSessionEvent) {
					TsapiRouteSessionEvent rSesE = (TsapiRouteSessionEvent) event;
					TsapiRouteSession rSes = (TsapiRouteSession) rSesE
							.getRouteSession();
					i = rSes.getRouteCrossRefID();
				}

				if (event instanceof TsapiRouteEvent) {
					log.debug("calling routeEvent in " + observer
							+ " CrossRef " + i);
					observer.routeEvent((TsapiRouteEvent) event);
					log.debug("returned from routeEvent in " + observer
							+ " CrossRef " + i);
				} else if (event instanceof TsapiReRouteEvent) {
					log.debug("calling reRouteEvent in " + observer
							+ " CrossRef " + i);
					observer.reRouteEvent((TsapiReRouteEvent) event);
					log.debug("returned from reRouteEvent in " + observer
							+ " CrossRef " + i);
				} else if (event instanceof TsapiRouteUsedEvent) {
					log.debug("calling routeUsedEvent in " + observer
							+ " CrossRef " + i);
					observer.routeUsedEvent((TsapiRouteUsedEvent) event);
					log.debug("returned from routeUsedEvent in " + observer
							+ " CrossRef " + i);
				} else if (event instanceof TsapiRouteEndEvent) {
					log.debug("calling routeEndEvent in " + observer
							+ " CrossRef " + i);
					observer.routeEndEvent((TsapiRouteEndEvent) event);
					log.debug("returned from routeEndEvent in " + observer
							+ " CrossRef " + i);
				} else {
					if (!(event instanceof TsapiRouteCallbackEndedEvent)) {
						continue;
					}
					log.debug("calling routeCallbackEndedEvent in " + observer
							+ " CrossRef " + i);
					observer
							.routeCallbackEndedEvent((TsapiRouteCallbackEndedEvent) event);
					log.debug("returned from routeCallbackEndedEvent in "
							+ observer + " CrossRef " + i);
				}
			}
		}
		TsapiTrace.traceExit("run[]", this);
	}
}

