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

	public TsapiProviderMonitor(final TSProviderImpl impl,
			final ProviderListener _listener) {
		listener = _listener;
		observer = null;
		provider = impl;
		provider.addProviderMonitorThread(this);

		deliverEvents(null, false);
	}

	public TsapiProviderMonitor(final TSProviderImpl _provider,
			final ProviderObserver _observer) {
		provider = _provider;
		observer = _observer;

		provider.addProviderMonitorThread(this);
		listener = null;

		deliverEvents(null, false);
	}

	private void addEv(final ProvEv event, final String tsEventLog) {
		if (event instanceof PrivateProvEv
				&& ((ITsapiEvent) event).getEventPackage() == 5) {
			TsapiProviderMonitor.log.debug(tsEventLog + " for observer "
					+ observer);

			observerEventList.addElement(event);
		} else if (((ITsapiEvent) event).getEventPackage() == 0) {
			TsapiProviderMonitor.log.debug(tsEventLog + " for observer "
					+ observer);

			observerEventList.addElement(event);
		} else
			TsapiProviderMonitor.log.debug(tsEventLog + " ignored");
	}

	private void addEvent(final TsapiListenerEvent impl, final String tsEventLog) {
		if (impl instanceof PrivateDataEvent
				&& listener instanceof PrivateDataProviderListener)
			listenerEventList.add(impl);
		else if (impl instanceof ProviderEvent
				&& listener instanceof ProviderListener)
			listenerEventList.add(impl);
	}

	public synchronized void addReference() {
		reference += 1L;
	}

	Provider createProvider(final TSProviderImpl tsProvider) {
		if (tsProvider == null)
			return null;
		return (Provider) TsapiCreateObject.getTsapiObject(tsProvider, false);
	}

	private synchronized void deleteListenerReference(final int cause,
			final Object privateData) {
		String tsEventLog = null;
		reference -= 1L;

		TsapiProviderMonitor.log.debug("meta event BEGIN: cause (" + cause
				+ ") metaCode (" + 136 + ")" + " for " + listener);
		tsEventLog = "OBSERVATIONENDEDEVENT for " + provider;

		final ProviderEventParams params = new ProviderEventParams(
				createProvider(provider), 112, cause, null,
				createProvider(provider), privateData);

		addEvent(new ProviderEventImpl(params), tsEventLog);

		if (privateData != null) {
			tsEventLog = "PRIVATEEVENT for " + provider;
			addEvent(new PrivateDataEventImpl(602, cause, null,
					createProvider(provider), privateData), tsEventLog);
		}

		TsapiProviderMonitor.log.debug("meta event END for " + listener
				+ " eventList size=" + listenerEventList.size());

		if (reference > 0L)
			return;
		provider.removeProviderMonitorThread(this);
	}

	private synchronized void deleteObserverReference(final int cause,
			final Object privateData) {
		String tsEventLog = null;
		reference -= 1L;

		TsapiProviderMonitor.log.debug("meta event BEGIN: cause (" + cause
				+ ") metaCode (" + 136 + ")" + " for " + observer);
		tsEventLog = "OBSERVATIONENDEDEVENT for " + provider;

		synchronized (observerEventList) {
			final int nextMetaEventIndex = observerEventList.size();

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

		TsapiProviderMonitor.log.debug("meta event END for " + observer
				+ " eventList size=" + observerEventList.size());

		if (reference > 0L)
			return;
		provider.removeProviderMonitorThread(this);
	}

	public void deleteReference(final int cause, final Object privateData) {
		TsapiProviderMonitor.log
				.debug("Getting TsapiProviderMonitor lock to deliver deleteReference events for observer "
						+ observer);
		if (observer != null)
			deleteObserverReference(cause, privateData);
		else
			deleteListenerReference(cause, privateData);
		JtapiEventThreadManager.execute(this);
	}

	public void deliverEvents(final Vector<TSEvent> _eventList,
			final boolean snapshot) {
		TsapiProviderMonitor.log
				.debug("Getting TsapiProviderMonitor lock to deliver events for observer "
						+ observer);
		if (_eventList == null || _eventList.size() == 0)
			return;
		synchronized (_eventList) {
			if (observer != null)
				deliverObserverEvents(_eventList, snapshot);
			else
				deliverListenerEvents(_eventList, snapshot);
			if (_eventList != null && _eventList.size() != 0)
				JtapiEventThreadManager.execute(this);
		}
	}

	protected synchronized void deliverListenerEvents(
			final Vector<TSEvent> _eventList, final boolean snapshot) {
		String tsEventLog = null;
		if (_eventList == null)
			return;
		int cause;
		if (snapshot)
			cause = 110;
		else
			cause = 100;

		Object privateData = null;

		TsapiProviderMonitor.log.debug("meta event BEGIN: cause (" + cause
				+ ") for " + listener);

		for (int i = 0; i < _eventList.size(); ++i) {
			final TSEvent ev = _eventList.elementAt(i);

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

		final int size = listenerEventList.size();
		TsapiProviderMonitor.log.debug("meta event END for " + listener
				+ " eventList size=" + size);

		if (size != 0)
			return;
		TsapiProviderMonitor.log.debug("no events to send to " + listener);
	}

	protected synchronized void deliverObserverEvents(
			final Vector<TSEvent> _eventList, final boolean snapshot) {
		String tsEventLog = null;
		if (_eventList == null)
			return;
		int cause;
		int metaCode;
		if (snapshot) {
			metaCode = 135;
			cause = 110;
		} else {
			metaCode = 136;
			cause = 100;
		}

		final int nextMetaEventIndex = observerEventList.size();

		Object privateData = null;

		TsapiProviderMonitor.log.debug("meta event BEGIN: cause (" + cause
				+ ") metaCode (" + metaCode + ")" + " for " + observer);

		for (int i = 0; i < _eventList.size(); ++i) {
			final TSEvent ev = _eventList.elementAt(i);

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
			TsapiProviderMonitor.log.debug("meta event END for " + observer
					+ " eventList size=" + observerEventList.size());

			if (observerEventList.size() == 0) {
				TsapiProviderMonitor.log.debug("no events to send to "
						+ observer);
				return;
			}

			if (nextMetaEventIndex < observerEventList.size())
				((TsapiObserverEvent) observerEventList
						.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
		}
	}

	public Object getMonitor() {
		if (listener != null)
			return listener;
		return observer;
	}

	private void notifyListener() {
		Event[] eventArray = null;
		synchronized (listenerEventList) {
			TsapiProviderMonitor.log.debug("Got this for ProviderListener - "
					+ listener);
			eventArray = new Event[listenerEventList.size()];
			listenerEventList.copyInto(eventArray);
			listenerEventList.clear();
		}
		TsapiProviderMonitor.log.debug("calling callback in " + listener);
		try {
			for (final Event event : eventArray)
				switch (event.getID()) {
				case 111:
					TsapiProviderMonitor.log
							.debug("calling providerInService in " + listener);
					listener.providerInService((ProviderEvent) event);
					TsapiProviderMonitor.log
							.debug("returned from providerInService in "
									+ listener);
					break;
				case 113:
					TsapiProviderMonitor.log
							.debug("calling providerOutOfService in "
									+ listener);
					listener.providerOutOfService((ProviderEvent) event);
					TsapiProviderMonitor.log
							.debug("returned from providerOutOfService in "
									+ listener);
					break;
				case 114:
					TsapiProviderMonitor.log
							.debug("calling providerShutdown in " + listener);
					listener.providerShutdown((ProviderEvent) event);
					TsapiProviderMonitor.log
							.debug("returned from providerShutdown in "
									+ listener);
					break;
				case 112:
					TsapiProviderMonitor.log
							.debug("calling providerEventTransmissionEnded in "
									+ listener);
					listener
							.providerEventTransmissionEnded((ProviderEvent) event);
					TsapiProviderMonitor.log
							.debug("returned from providerEventTransmissionEnded in "
									+ listener);
					break;
				case 602:
					if (listener instanceof PrivateDataProviderListener) {
						TsapiProviderMonitor.log
								.debug("calling providerPrivateData in "
										+ listener);
						((PrivateDataProviderListener) listener)
								.providerPrivateData((PrivateDataEvent) event);
						TsapiProviderMonitor.log
								.debug("returned from providerPrivateData in "
										+ listener);
					}
				}
		} catch (final Exception e) {
			TsapiProviderMonitor.log.error("EXCEPTION thrown by callback in "
					+ listener + " - " + e.getMessage(), e);
		}
		TsapiProviderMonitor.log.debug("returned from callback in " + listener);
	}

	private void notifyObserver() {
		ProvEv[] eventArray = null;
		synchronized (observerEventList) {
			TsapiProviderMonitor.log.debug("Got this for ProviderObserver - "
					+ observer);
			eventArray = new ProvEv[observerEventList.size()];
			observerEventList.copyInto(eventArray);
			observerEventList.clear();
		}
		TsapiProviderMonitor.log.debug("calling providerChangedEvent in "
				+ observer);
		try {
			observer.providerChangedEvent(eventArray);
		} catch (final Exception e) {
			TsapiProviderMonitor.log.error(
					"EXCEPTION thrown by providerChangedEvent in " + observer
							+ " - " + e.getMessage(), e);
		}
		TsapiProviderMonitor.log.debug("returned from providerChangedEvent in "
				+ observer);
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		synchronized (syncObject) {
			if (listener != null)
				notifyListener();
			else
				notifyObserver();
		}
		TsapiTrace.traceExit("run[]", this);
	}
}
