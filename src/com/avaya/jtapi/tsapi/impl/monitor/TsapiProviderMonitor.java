package com.avaya.jtapi.tsapi.impl.monitor;

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

import com.avaya.jtapi.tsapi.ITsapiEvent;
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

@SuppressWarnings("deprecation")
public class TsapiProviderMonitor implements TsapiMonitor {
	private static final Logger log = Logger
			.getLogger(TsapiProviderMonitor.class);
	protected final TSProviderImpl provider;
	private final ProviderObserver observer;
	protected final Vector<ProvEv> observerEventList = new Vector<ProvEv>();
	protected final Vector<TsapiListenerEvent> listenerEventList = new Vector<TsapiListenerEvent>();
	private long reference = 0L;
	private final Object syncObject = new Object();
	private final ProviderListener listener;

	public TsapiProviderMonitor(TSProviderImpl impl, ProviderListener _listener) {
		listener = _listener;
		observer = null;
		provider = impl;
		provider.addProviderMonitorThread(this);

		deliverEvents(null, false);
	}

	public TsapiProviderMonitor(TSProviderImpl _provider,
			ProviderObserver _observer) {
		provider = _provider;
		observer = _observer;

		provider.addProviderMonitorThread(this);
		listener = null;

		deliverEvents(null, false);
	}

	private void addEv(ProvEv event, String tsEventLog) {
		if ((event instanceof PrivateProvEv)
				&& (((ITsapiEvent) event).getEventPackage() == 5)) {
			log.debug(tsEventLog + " for observer " + observer);

			observerEventList.addElement(event);
		} else if (((ITsapiEvent) event).getEventPackage() == 0) {
			log.debug(tsEventLog + " for observer " + observer);

			observerEventList.addElement(event);
		} else {
			log.debug(tsEventLog + " ignored");
		}
	}

	private void addEvent(TsapiListenerEvent impl, String tsEventLog) {
		if ((impl instanceof PrivateDataEvent)
				&& (listener instanceof PrivateDataProviderListener)) {
			listenerEventList.add(impl);
		} else if ((impl instanceof ProviderEvent)
				&& (listener instanceof ProviderListener)) {
			listenerEventList.add(impl);
		}
	}

	public synchronized void addReference() {
		reference += 1L;
	}

	Provider createProvider(TSProviderImpl tsProvider) {
		if (tsProvider == null) {
			return null;
		}
		return (Provider) TsapiCreateObject.getTsapiObject(tsProvider, false);
	}

	private synchronized void deleteListenerReference(int cause,
			Object privateData) {
		String tsEventLog = null;
		reference -= 1L;

		log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136
				+ ")" + " for " + listener);
		tsEventLog = "OBSERVATIONENDEDEVENT for " + provider;

		ProviderEventParams params = new ProviderEventParams(
				createProvider(provider), 112, cause, null,
				createProvider(provider), privateData);

		addEvent(new ProviderEventImpl(params), tsEventLog);

		if (privateData != null) {
			tsEventLog = "PRIVATEEVENT for " + provider;
			addEvent(new PrivateDataEventImpl(602, cause, null,
					createProvider(provider), privateData), tsEventLog);
		}

		log.debug("meta event END for " + listener + " eventList size="
				+ listenerEventList.size());

		if (reference > 0L) {
			return;
		}
		provider.removeProviderMonitorThread(this);
	}

	private synchronized void deleteObserverReference(int cause,
			Object privateData) {
		String tsEventLog = null;
		reference -= 1L;

		log.debug("meta event BEGIN: cause (" + cause + ") metaCode (" + 136
				+ ")" + " for " + observer);
		tsEventLog = "OBSERVATIONENDEDEVENT for " + provider;

		synchronized (observerEventList) {
			int nextMetaEventIndex = observerEventList.size();

			addEv(new TsapiProvObservationEndedEvent(createProvider(provider),
					cause, privateData), tsEventLog);

			((TsapiObserverEvent) observerEventList
					.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
		}

		if (privateData != null) {
			tsEventLog = "PRIVATEEVENT for " + provider;
			addEv(new TsapiPrivateProviderEvent(createProvider(provider),
					cause, 136, privateData), tsEventLog);
		}

		log.debug("meta event END for " + observer + " eventList size="
				+ observerEventList.size());

		if (reference > 0L) {
			return;
		}
		provider.removeProviderMonitorThread(this);
	}

	public void deleteReference(int cause, Object privateData) {
		log
				.debug("Getting TsapiProviderMonitor lock to deliver deleteReference events for observer "
						+ observer);
		if (observer != null) {
			deleteObserverReference(cause, privateData);
		} else {
			deleteListenerReference(cause, privateData);
		}
		JtapiEventThreadManager.execute(this);
	}

	public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot) {
		log
				.debug("Getting TsapiProviderMonitor lock to deliver events for observer "
						+ observer);
		if ((_eventList == null) || (_eventList.size() == 0)) {
			return;
		}
		synchronized (_eventList) {
			if (observer != null) {
				deliverObserverEvents(_eventList, snapshot);
			} else {
				deliverListenerEvents(_eventList, snapshot);
			}
			if ((_eventList != null) && (_eventList.size() != 0)) {
				JtapiEventThreadManager.execute(this);
			}
		}
	}

	protected synchronized void deliverListenerEvents(
			Vector<TSEvent> _eventList, boolean snapshot) {
		String tsEventLog = null;
		if (_eventList == null) {
			return;
		}
		int cause;
		if (snapshot) {
			cause = 110;
		} else {
			cause = 100;
		}

		Object privateData = null;

		log.debug("meta event BEGIN: cause (" + cause + ") for " + listener);

		for (int i = 0; i < _eventList.size(); ++i) {
			TSEvent ev = _eventList.elementAt(i);

			privateData = ev.getPrivateData();
			ProviderEventParams params = null;
			switch (ev.getEventType()) {
			case 1:
				tsEventLog = "PROVIDERINSERVICEEVENT for "
						+ ev.getEventTarget();

				params = new ProviderEventParams(createProvider(provider), 111,
						cause, null, createProvider(provider), privateData);

				addEvent(new ProviderEventImpl(params), tsEventLog);
				break;
			case 2:
				tsEventLog = "PROVIDEROUTOFSERVICEEVENT for "
						+ ev.getEventTarget();

				params = new ProviderEventParams(createProvider(provider), 113,
						cause, null, createProvider(provider), privateData);

				addEvent(new ProviderEventImpl(params), tsEventLog);
				break;
			case 3:
				tsEventLog = "PROVIDERSHUTDOWNEVENT for " + ev.getEventTarget();

				params = new ProviderEventParams(createProvider(provider), 114,
						cause, null, createProvider(provider), privateData);

				addEvent(new ProviderEventImpl(params), tsEventLog);
				break;
			case 9999:
				tsEventLog = "PRIVATEEVENT for " + ev.getEventTarget();

				addEvent(new PrivateDataEventImpl(602, cause, null,
						createProvider(provider), privateData), tsEventLog);
			}
		}

		int size = listenerEventList.size();
		log.debug("meta event END for " + listener + " eventList size=" + size);

		if (size != 0) {
			return;
		}
		log.debug("no events to send to " + listener);
	}

	protected synchronized void deliverObserverEvents(
			Vector<TSEvent> _eventList, boolean snapshot) {
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

		int nextMetaEventIndex = observerEventList.size();

		Object privateData = null;

		log.debug("meta event BEGIN: cause (" + cause + ") metaCode ("
				+ metaCode + ")" + " for " + observer);

		for (int i = 0; i < _eventList.size(); ++i) {
			TSEvent ev = _eventList.elementAt(i);

			privateData = ev.getPrivateData();

			switch (ev.getEventType()) {
			case 1:
				tsEventLog = "PROVIDERINSERVICEEVENT for "
						+ ev.getEventTarget();

				addEv(new TsapiProviderInServiceEvent(
						createProvider((TSProviderImpl) ev.getEventTarget()),
						cause, metaCode, privateData), tsEventLog);

				break;
			case 2:
				tsEventLog = "PROVIDEROUTOFSERVICEEVENT for "
						+ ev.getEventTarget();

				addEv(new TsapiProviderOutOfServiceEvent(
						createProvider((TSProviderImpl) ev.getEventTarget()),
						cause, metaCode, privateData), tsEventLog);

				break;
			case 3:
				tsEventLog = "PROVIDERSHUTDOWNEVENT for " + ev.getEventTarget();

				addEv(new TsapiProviderShutdownEvent(
						createProvider((TSProviderImpl) ev.getEventTarget()),
						cause, metaCode, privateData), tsEventLog);

				break;
			case 9999:
				tsEventLog = "PRIVATEEVENT for " + ev.getEventTarget();

				addEv(new TsapiPrivateProviderEvent(
						createProvider((TSProviderImpl) ev.getEventTarget()),
						cause, metaCode, privateData), tsEventLog);
			}

		}

		synchronized (observerEventList) {
			log.debug("meta event END for " + observer + " eventList size="
					+ observerEventList.size());

			if (observerEventList.size() == 0) {
				log.debug("no events to send to " + observer);
				return;
			}

			if (nextMetaEventIndex < observerEventList.size()) {
				((TsapiObserverEvent) observerEventList
						.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
			}
		}
	}

	public Object getMonitor() {
		if (listener != null) {
			return listener;
		}
		return observer;
	}

	private void notifyListener() {
		Event[] eventArray = null;
		synchronized (listenerEventList) {
			log.debug("Got this for ProviderListener - " + listener);
			eventArray = new Event[listenerEventList.size()];
			listenerEventList.copyInto(eventArray);
			listenerEventList.clear();
		}
		log.debug("calling callback in " + listener);
		try {
			for (Event event : eventArray) {
				switch (event.getID()) {
				case 111:
					log.debug("calling providerInService in " + listener);
					listener.providerInService((ProviderEvent) event);
					log.debug("returned from providerInService in " + listener);
					break;
				case 113:
					log.debug("calling providerOutOfService in " + listener);
					listener.providerOutOfService((ProviderEvent) event);
					log.debug("returned from providerOutOfService in "
							+ listener);
					break;
				case 114:
					log.debug("calling providerShutdown in " + listener);
					listener.providerShutdown((ProviderEvent) event);
					log.debug("returned from providerShutdown in " + listener);
					break;
				case 112:
					log.debug("calling providerEventTransmissionEnded in "
							+ listener);
					listener
							.providerEventTransmissionEnded((ProviderEvent) event);
					log
							.debug("returned from providerEventTransmissionEnded in "
									+ listener);
					break;
				case 602:
					if (listener instanceof PrivateDataProviderListener) {
						log.debug("calling providerPrivateData in " + listener);
						((PrivateDataProviderListener) listener)
								.providerPrivateData((PrivateDataEvent) event);
						log.debug("returned from providerPrivateData in "
								+ listener);
					}
				}
			}
		} catch (Exception e) {
			log.error("EXCEPTION thrown by callback in " + listener + " - "
					+ e.getMessage(), e);
		}
		log.debug("returned from callback in " + listener);
	}

	private void notifyObserver() {
		ProvEv[] eventArray = null;
		synchronized (observerEventList) {
			log.debug("Got this for ProviderObserver - " + observer);
			eventArray = new ProvEv[observerEventList.size()];
			observerEventList.copyInto(eventArray);
			observerEventList.clear();
		}
		log.debug("calling providerChangedEvent in " + observer);
		try {
			observer.providerChangedEvent(eventArray);
		} catch (Exception e) {
			log.error("EXCEPTION thrown by providerChangedEvent in " + observer
					+ " - " + e.getMessage(), e);
		}
		log.debug("returned from providerChangedEvent in " + observer);
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		synchronized (syncObject) {
			if (listener != null) {
				notifyListener();
			} else {
				notifyObserver();
			}
		}
		TsapiTrace.traceExit("run[]", this);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor JD-Core Version:
 * 0.5.4
 */