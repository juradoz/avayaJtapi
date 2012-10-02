package com.avaya.jtapi.tsapi.impl.monitor;

import com.avaya.jtapi.tsapi.ITsapiEvent;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.impl.TsapiCall;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSEvent;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;
import com.avaya.jtapi.tsapi.impl.events.call.CallCenterCallApplicationDataEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallCenterTrunkEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallControlCallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventUtil;
import com.avaya.jtapi.tsapi.impl.events.call.PrivateDataCallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallActiveEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallCentCallAppDataEv;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallInvalidEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallObservationEndedEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiPrivateCallEvent;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiTrunkInvalidEv;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiTrunkValidEv;
import com.avaya.jtapi.tsapi.impl.events.conn.CallCenterConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.CallControlConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.CallControlConnectionNetworkReachedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.ConnEventParams;
import com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentCallCenterConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentCallControlConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentCallControlConnectionNetworkReachedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnAlertingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnDialingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnDisconnectedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnEstablishedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnFailedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnInProgressEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnInitiatedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnNetworkAlertingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnNetworkReachedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnOfferedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnQueuedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallCenterConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallControlConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallControlConnectionNetworkReachedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnAlertingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnDialingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnDisconnectedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnEstablishedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnFailedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnInProgressEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnInitiatedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnNetworkAlertingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnNetworkReachedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnOfferedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnQueuedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.LucentV5ConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnAlertingEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnAlertingEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnConnectedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnCreatedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDialingEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDisconnectedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDisconnectedEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnEstablishedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnFailedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnFailedEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInProgressEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInProgressEventCC;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInitiatedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkAlertingEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkReachedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnOfferedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnQueuedEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnUnknownEvent;
import com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnUnknownEventCC;
import com.avaya.jtapi.tsapi.impl.events.termConn.CallControlTerminalConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentCallControlTerminalConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnBridgedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnDroppedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnHeldEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnInUseEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnRingingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnTalkingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentTermConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5CallControlTerminalConnectionEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnBridgedEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnDroppedEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnHeldEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnInUseEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnRingingEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnTalkingEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.LucentV5TermConnUnknownEventCCImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.PrivateDtmfEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.TermConnEventParams;
import com.avaya.jtapi.tsapi.impl.events.termConn.TerminalConnectionEventImpl;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnActiveEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnBridgedEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnCreatedEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDTMFEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDroppedEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDroppedEventCC;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnHeldEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnInUseEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnPassiveEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnRingingEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnRingingEventCC;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnTalkingEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnUnknownEvent;
import com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnUnknownEventCC;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Vector;
import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.CallEvent;
import javax.telephony.CallListener;
import javax.telephony.CallObserver;
import javax.telephony.Connection;
import javax.telephony.ConnectionEvent;
import javax.telephony.ConnectionListener;
import javax.telephony.Event;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;
import javax.telephony.TerminalConnectionEvent;
import javax.telephony.TerminalConnectionListener;
import javax.telephony.callcenter.CallCenterCallEvent;
import javax.telephony.callcenter.CallCenterCallListener;
import javax.telephony.callcenter.CallCenterCallObserver;
import javax.telephony.callcenter.CallCenterTrunkEvent;
import javax.telephony.callcontrol.CallControlCallObserver;
import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlConnectionListener;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionListener;
import javax.telephony.events.CallEv;
import javax.telephony.privatedata.PrivateDataCallListener;
import javax.telephony.privatedata.PrivateDataEvent;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public final class TsapiCallMonitor implements TsapiMonitor {
	private static Logger log = Logger.getLogger(TsapiCallMonitor.class);
	TSProviderImpl provider;
	CallObserver observer = null;
	CallListener callListener = null;
	boolean isVDN;
	private final Vector<CallEv> eventList;
	private final Vector<Event> listenerEventList;
	long reference = 0L;
	BitSet eventSubscriptionType = new BitSet(8);
	private boolean metaStartAdded;
	Object syncObject = new Object();

	public void dump(String indent) {
		log.trace(new StringBuilder().append(indent)
				.append("***** TsapiCallMonitor DUMP *****").toString());
		log.trace(new StringBuilder().append(indent)
				.append("TsapiCallMonitor: ").append(this).toString());
		log.trace(new StringBuilder().append(indent).append("observer: ")
				.append(this.observer).toString());
		log.trace(new StringBuilder().append(indent)
				.append("***** TsapiCallMonitor DUMP END *****").toString());
	}

	public TsapiCallMonitor(TSProviderImpl _provider, CallObserver _observer) {
		this.provider = _provider;
		this.observer = _observer;
		this.callListener = null;
		this.eventList = new Vector<CallEv>();
		this.listenerEventList = null;
		this.provider.addCallMonitorThread(this);

		this.isVDN = false;

		this.eventSubscriptionType.set(0);
		if ((this.observer instanceof CallControlCallObserver)) {
			this.eventSubscriptionType.set(1);
		}
		if ((this.observer instanceof CallCenterCallObserver)) {
			this.eventSubscriptionType.set(2);
		}

		this.eventSubscriptionType.set(5);

		deliverEvents(null, 0, false);
	}

	public TsapiCallMonitor(TSProviderImpl _provider, CallListener listener) {
		this.provider = _provider;
		this.observer = null;
		this.callListener = listener;
		this.listenerEventList = new Vector<Event>();
		this.eventList = null;
		this.provider.addCallMonitorThread(this);

		this.isVDN = false;

		deliverEvents(null, 0, false);
	}

	public void setVDN(boolean isVDN) {
		this.isVDN = isVDN;
	}

	public boolean isVDN() {
		return this.isVDN;
	}

	public CallObserver getObserver() {
		return this.observer;
	}

	public CallListener getListener() {
		return this.callListener;
	}

	public synchronized void addReference() {
		this.reference += 1L;
	}

	public void deleteReference(Object observed, boolean isTerminal, int cause,
			Object privateData) {
		if (this.observer != null)
			log.debug(new StringBuilder()
					.append("Getting TsapiCallMonitor lock to deliver deleteReference events for monitor ")
					.append(this.observer).toString());
		else
			log.debug(new StringBuilder()
					.append("Getting TsapiCallMonitor lock to deliver deleteReference events for monitor ")
					.append(this.callListener).toString());
		deleteReferenceInternal(observed, isTerminal, cause, privateData);
	}

	private synchronized void deleteReferenceInternal(Object observed,
			boolean isTerminal, int cause, Object privateData) {
		String tsEventLog = null;
		this.reference -= 1L;

		if (this.observer != null)
			log.debug(new StringBuilder().append("meta event BEGIN: cause (")
					.append(cause).append(") metaCode (").append(136)
					.append(")").append(" for ").append(this.observer)
					.toString());
		tsEventLog = new StringBuilder()
				.append("OBSERVATIONENDEDEVENT for observed: ")
				.append(observed).toString();

		TSCall targetCall = (observed instanceof TSCall) ? (TSCall) observed
				: null;
		if (targetCall != null) {
			if (!targetCall.checkForMonitors()) {
				targetCall.setNeedSnapshot(true);
			}

		}

		if (observed != null) {
			observed = TsapiCreateObject.getTsapiObject(observed, !isTerminal);
		}

		int nextMetaEventIndex = 0;

		if (this.observer != null) {
			synchronized (this.eventList) {
				nextMetaEventIndex = this.eventList.size();
				CallEventParams params = createCallParams(targetCall, cause,
						136, privateData, null);

				addObserverEvent(new TsapiCallObservationEndedEvent(params,
						observed), tsEventLog);

				((TsapiObserverEvent) this.eventList
						.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();

				if ((targetCall != null) && (privateData != null)) {
					tsEventLog = new StringBuilder()
							.append("PRIVATEEVENT for ").append(targetCall)
							.toString();
					addObserverEvent(
							new TsapiPrivateCallEvent(createCallParams(
									targetCall, cause, 136, privateData, null)),
							tsEventLog);
				}

				log.debug(new StringBuilder().append("meta event END for ")
						.append(this.observer).append(" eventList size=")
						.append(this.eventList.size()).toString());
			}
		} else if (this.callListener != null) {
			CallEventParams callEvParams = createCallParams(targetCall, cause,
					-1, privateData, null);
			MetaEvent[] metaEvents = CallEventUtil.getListenerMetaObject(cause,
					callEvParams, false);
			synchronized (this.listenerEventList) {
				this.listenerEventList.add(metaEvents[0]);
				this.listenerEventList.add(new CallEventImpl(callEvParams,
						metaEvents[0], 103));
				if ((targetCall != null)
						&& (privateData != null)
						&& ((this.callListener instanceof PrivateDataCallListener))) {
					tsEventLog = new StringBuilder()
							.append("PRIVATEEVENT for ").append(targetCall)
							.append("for listener ").append(this.callListener)
							.toString();
					this.listenerEventList.add(new PrivateDataCallEventImpl(
							privateData, targetCall, cause, metaEvents[0]));
					log.debug(tsEventLog);
				}
				this.listenerEventList.add(metaEvents[1]);
			}
		}

		if (this.reference <= 0L) {
			this.provider.removeCallMonitorThread(this);
		}

		JtapiEventThreadManager.execute(this);
	}

	void addObserverEvent(CallEv event, String tsEventLog) {
		if ((this.eventSubscriptionType.get(0))
				&& (((ITsapiEvent) event).getEventPackage() == 0)) {
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for observer ").append(this.observer).toString());

			this.eventList.addElement(event);
		} else if ((this.eventSubscriptionType.get(1))
				&& (((ITsapiEvent) event).getEventPackage() == 1)) {
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for observer ").append(this.observer).toString());

			this.eventList.addElement(event);
		} else if ((this.eventSubscriptionType.get(2))
				&& (((ITsapiEvent) event).getEventPackage() == 2)) {
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for observer ").append(this.observer).toString());

			this.eventList.addElement(event);
		} else if ((this.eventSubscriptionType.get(3))
				&& (((ITsapiEvent) event).getEventPackage() == 3)) {
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for observer ").append(this.observer).toString());

			this.eventList.addElement(event);
		} else if ((this.eventSubscriptionType.get(5))
				&& (((ITsapiEvent) event).getEventPackage() == 5)) {
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for observer ").append(this.observer).toString());

			this.eventList.addElement(event);
		} else {
			log.debug(new StringBuilder().append(tsEventLog).append(" ignored")
					.toString());
		}
	}

	int getObserverMetaCode(int cause, Vector<TSEvent> _eventList) {
		switch (cause) {
		case 207:
			return 133;
		case 212:
			return 134;
		}

		boolean created = false;
		boolean disconnected = false;
		boolean dtmf = false;
		for (int i = 0; i < _eventList.size(); i++) {
			switch (((TSEvent) _eventList.elementAt(i)).getEventType()) {
			case 4:
				return 128;
			case 5:
				return 132;
			case 6:
				created = true;
				break;
			case 10:
				disconnected = true;
				break;
			case 58:
				dtmf = true;
			}

		}

		if (created) {
			return 130;
		}
		if (disconnected) {
			return 131;
		}
		if (dtmf) {
			return 136;
		}

		return 129;
	}

	public void deliverEvents(Vector<TSEvent> _eventList, int cause,
			boolean snapshot) {
		if ((_eventList == null) || (_eventList.size() == 0)) {
			return;
		}
		log.debug(new StringBuilder()
				.append("Getting TsapiCallMonitor lock to deliver events for call ")
				.append(this.observer == null ? "listener " : "observer ")
				.append(this.observer == null ? this.callListener
						: this.observer).toString());
		synchronized (_eventList) {
			deliverEventsInternal(_eventList, cause, snapshot);
		}
	}

	private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList,
			int cause, boolean snapshot) {
		if (this.observer != null)
			createObserverEvents(_eventList, cause, snapshot);
		else {
			createListenerEvents(_eventList, cause, snapshot);
		}
		JtapiEventThreadManager.execute(this);
	}

	private void createListenerEvents(Vector<TSEvent> _eventList, int cause,
			boolean snapshot) {
		TSCall callTarget = null;
		TSConnection connTarget = null;
		TSTrunk trkTarget = null;
		Object privateData = null;
		CallEventParams callEventParams = null;
		int metaCode = -1;
		MetaEvent[] metaEventPair = null;
		int nextMetaEventIndex = this.listenerEventList.size();

		TSEvent event = (TSEvent) _eventList.get(0);

		if ((event.getEventTarget() instanceof TSCall)) {
			callTarget = (TSCall) event.getEventTarget();
			if (((cause == 212) || (cause == 207))
					&& (event.getTransferredEventParams() != null))
				callEventParams = createCallParams(callTarget, cause, -1,
						event.getPrivateData(), event
								.getTransferredEventParams().getOldCalls(),
						event);
			else
				callEventParams = createCallParams(callTarget, cause, -1,
						event.getPrivateData(), event);
		} else if ((event.getEventTarget() instanceof TSTrunk)) {
			trkTarget = (TSTrunk) event.getEventTarget();
			if (((cause == 212) || (cause == 207))
					&& (event.getTransferredEventParams() != null))
				callEventParams = createCallParams(callTarget, cause, -1,
						event.getPrivateData(), event
								.getTransferredEventParams().getOldCalls(),
						event);
			else
				callEventParams = createCallParams(trkTarget, cause, -1,
						event.getPrivateData(), event);
		} else {
			connTarget = (TSConnection) event.getEventTarget();
			if (((cause == 212) || (cause == 207))
					&& (event.getTransferredEventParams() != null))
				callEventParams = createConnParams(connTarget, cause, -1,
						event.getPrivateData(), event
								.getTransferredEventParams().getOldCalls(),
						event);
			else {
				callEventParams = createConnParams(connTarget, cause, -1,
						event.getPrivateData(), event);
			}

		}

		metaEventPair = CallEventUtil.getListenerMetaObject(cause,
				callEventParams, snapshot);

		MetaEvent metaEvent = metaEventPair[0];
		metaCode = metaEvent.getID();

		String tsEventLog = null;

		log.debug(new StringBuilder()
				.append("Meta event BEGIN: cause (")
				.append(cause)
				.append(") metaCode (")
				.append(metaCode)
				.append(")")
				.append(" for ")
				.append(this.observer == null ? this.callListener
						: this.observer).toString());
		Object prevPrivateData = null;
		for (TSEvent ev : _eventList) {
			if ((ev.getEventTarget() instanceof TSCall))
				callTarget = (TSCall) ev.getEventTarget();
			else if ((ev.getEventTarget() instanceof TSTrunk))
				trkTarget = (TSTrunk) ev.getEventTarget();
			else {
				connTarget = (TSConnection) ev.getEventTarget();
			}

			privateData = ev.getPrivateData();
			PrivateDataCallEventImpl privateEvent = null;

			if ((privateData != null)
					&& (ev.getEventType() != 9999)
					&& (((privateData instanceof TsapiPrivate))
							|| ((privateData instanceof LucentChargeAdviceEvent)) || ((privateData instanceof TsapiPrivateStateEvent)))) {
				if (!privateData.equals(prevPrivateData)) {
					prevPrivateData = privateData;
					Object source = null;
					if (callTarget != null)
						source = callTarget;
					else if (connTarget != null)
						source = connTarget;
					else if (trkTarget != null) {
						source = trkTarget;
					}
					privateEvent = new PrivateDataCallEventImpl(privateData,
							source, cause, metaEvent);
					checkAndAddPrivateEvent(source, privateEvent, metaEvent);
				}
			} else
				prevPrivateData = null;

			CallEventParams params = null;

			switch (ev.getEventType()) {
			case 4:
				params = createCallParams(callTarget, cause, metaCode,
						privateData, ev);
				synchronized (this.listenerEventList) {
					if (!this.metaStartAdded) {
						this.listenerEventList.add(metaEvent);
						this.metaStartAdded = true;
					}
					this.listenerEventList.add(new CallEventImpl(params,
							metaEvent, 101));
				}
				tsEventLog = new StringBuilder().append("CALLACTIVEEVENT for ")
						.append(callTarget).toString();
				log.debug(new StringBuilder().append(tsEventLog)
						.append(" for listener ").append(this.callListener)
						.toString());
				break;
			case 5:
				params = createCallParams(callTarget, cause, metaCode,
						privateData, ev);
				synchronized (this.listenerEventList) {
					if (!this.metaStartAdded) {
						this.listenerEventList.add(metaEvent);
						this.metaStartAdded = true;
					}
					this.listenerEventList.add(new CallControlCallEventImpl(
							params, metaEvent, 102));
				}
				tsEventLog = new StringBuilder()
						.append("CALLINVALIDEVENT for ").append(callTarget)
						.toString();
				log.debug(new StringBuilder().append(tsEventLog)
						.append(" for listener ").append(this.callListener)
						.toString());
				break;
			case 6:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONCREATEDEVENT for ")
						.append(connTarget).toString();
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 106, tsEventLog, ev);
				break;
			case 7:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONCONNECTEDEVENT for ")
						.append(connTarget).toString();
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 105, tsEventLog, ev);
				break;
			case 8:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONINPROGRESSEVENT for ")
						.append(connTarget).toString();
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 109, tsEventLog, ev);
				break;
			case 9:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONALERTINGEVENT for ")
						.append(connTarget).toString();
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 104, tsEventLog, ev);
				break;
			case 10:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONDISCONNECTEDEVENT for ")
						.append(connTarget).toString();
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 107, tsEventLog, ev);
				break;
			case 11:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONFAILEDEVENT for ")
						.append(connTarget).toString();
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 108, tsEventLog, ev);
				break;
			case 12:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONUNKNOWNEVENT for ")
						.append(connTarget).toString();
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 110, tsEventLog, ev);
				break;
			case 13:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONCREATEDEVENT for ")
						.append(connTarget).toString();
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 116, tsEventLog, ev);
				break;
			case 14:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONACTIVEEVENT for ")
						.append(connTarget).toString();
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 115, tsEventLog, ev);
				break;
			case 15:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONRINGINGEVENT for ")
						.append(connTarget).toString();
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 119, tsEventLog, ev);
				break;
			case 16:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONPASSIVEEVENT for ")
						.append(connTarget).toString();
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 118, tsEventLog, ev);
				break;
			case 17:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONDROPPEDEVENT for ")
						.append(connTarget).toString();
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 117, tsEventLog, ev);
				break;
			case 18:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONUNKNOWNEVENT for ")
						.append(connTarget).toString();
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 120, tsEventLog, ev);
				break;
			case 19:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONOFFEREDEVENT for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 361, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 20:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONDIALINGEVENT for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 354, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 21:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONESTABLISHEDEVENT for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 356, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 22:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONNETWORKREACHEDEVENT for ")
						.append(connTarget).toString();
				addCallControlConnectionNetworkReachedEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 360, connTarget
								.getTSDevice().getNumberQueued(), tsEventLog,
						ev);
				break;
			case 23:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONNETWORKALERTINGEVENT for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 359, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 24:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONINITIATEDEVENT for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 358, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 25:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONQUEUEDEVENT for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 362, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 26:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONALERTINGEVENT_CC for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 353, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 27:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONDISCONNECTEDEVENT_CC for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 355, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 28:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONFAILEDEVENT_CC for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 357, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 29:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONUNKNOWNEVENT_CC for ")
						.append(connTarget).toString();
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 363, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 56:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONINPROGRESSEVENT_CC for ")
						.append(connTarget).toString();
				addCallCenterConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 319, tsEventLog, ev);
				break;
			case 30:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONTALKINGEVENT for ")
						.append(connTarget).toString();
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 369, tsEventLog, ev);
				break;
			case 31:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONHELDEVENT for ")
						.append(connTarget).toString();
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 366, tsEventLog, ev);
				break;
			case 32:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONBRIDGEDEVENT for ")
						.append(connTarget).toString();
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 364, tsEventLog, ev);
				break;
			case 33:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONINUSEEVENT for ")
						.append(connTarget).toString();
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 367, tsEventLog, ev);
				break;
			case 34:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONDROPPEDEVENT_CC for ")
						.append(connTarget).toString();
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 365, tsEventLog, ev);
				break;
			case 35:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONRINGINGEVENT_CC for ")
						.append(connTarget).toString();
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 368, tsEventLog, ev);
				break;
			case 36:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONUNKNOWNEVENT_CC for ")
						.append(connTarget).toString();
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 370, tsEventLog, ev);
				break;
			case 54:
				tsEventLog = new StringBuilder().append("TRUNKVALIDEVENT for ")
						.append(trkTarget).toString();
				addCallCenterEvent(cause, metaCode, metaEvent, trkTarget, null,
						privateData, 317, tsEventLog, ev);
				break;
			case 55:
				tsEventLog = new StringBuilder()
						.append("TRUNKINVALIDEVENT for ").append(trkTarget)
						.toString();
				addCallCenterEvent(cause, metaCode, metaEvent, trkTarget, null,
						privateData, 318, tsEventLog, ev);
				break;
			case 57:
				tsEventLog = new StringBuilder()
						.append("APPLICATIONDATAEVENT for ").append(callTarget)
						.toString();
				addCallCenterEvent(cause, metaCode, metaEvent, null,
						callTarget, privateData, 316, tsEventLog, ev);
				break;
			case 58:
				String digits = callTarget.getDigits();
				for (int strIndex = 0; strIndex < digits.length(); strIndex++) {
					tsEventLog = new StringBuilder()
							.append("CALLDTMFEVENT for ").append(callTarget)
							.toString();
					PrivateDataCallEventImpl privEvent = new PrivateDataCallEventImpl(
							new PrivateDtmfEventImpl(digits.charAt(strIndex)),
							ev.getEventTarget(), cause, metaEvent);
					checkAndAddPrivateEvent(ev.getEventTarget(), privEvent,
							metaEvent);
				}
				break;
			case 9999:
				params = createCallParams(callTarget, cause, metaCode,
						ev.getPrivateData(), ev);
				PrivateDataCallEventImpl privEvent = new PrivateDataCallEventImpl(
						params.getPrivateData(), params.getCall(), cause,
						metaEvent);
				checkAndAddPrivateEvent(ev.getEventTarget(), privEvent,
						metaEvent);
			}
		}

		synchronized (this.listenerEventList) {
			if (this.listenerEventList.size() == 0) {
				log.debug(new StringBuilder().append("no events to send to ")
						.append(this.observer).toString());
				return;
			}

			if ((nextMetaEventIndex < this.listenerEventList.size())
					&& (this.metaStartAdded)) {
				this.listenerEventList.add(metaEventPair[1]);
				this.metaStartAdded = false;
			}
		}
	}

	private void checkAndAddPrivateEvent(Object eventTarget,
			PrivateDataCallEventImpl privateEvent, Event metaEvent) {
		if ((this.callListener instanceof PrivateDataCallListener)) {
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}
				this.listenerEventList.add(privateEvent);
			}
			log.info(new StringBuilder().append("PRIVATEEVENT for ")
					.append(eventTarget).append(" for listener ")
					.append(this.callListener).toString());
		}
	}

	private void addCallControlTerminalConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		if ((this.callListener instanceof CallControlTerminalConnectionListener)) {
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}
				TermConnEventParams connEventParams = createTermConnParams(
						connTarget, cause, metaCode, privateData, ev);
				if (this.provider.isLucentV5()) {
					this.listenerEventList
							.add(new LucentV5CallControlTerminalConnectionEvent(
									connEventParams, metaEvent, eventId));
				} else if (this.provider.isLucent()) {
					this.listenerEventList
							.add(new LucentCallControlTerminalConnectionEvent(
									connEventParams, metaEvent, eventId));
				} else {
					this.listenerEventList
							.add(new CallControlTerminalConnectionEventImpl(
									connEventParams, metaEvent, eventId));
				}
			}
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for listener ").append(this.callListener)
					.toString());
		}
	}

	private void addCallControlConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, int numInQueue, String tsEventLog, TSEvent ev) {
		if ((this.callListener instanceof CallControlConnectionListener)) {
			String digits = connTarget.getCall().getDigits();
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}
				ConnEventParams connEventParams = createConnParams(connTarget,
						cause, metaCode, privateData, ev);
				if (this.provider.isLucentV5()) {
					this.listenerEventList
							.add(new LucentV5CallControlConnectionEvent(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				} else if (this.provider.isLucent()) {
					this.listenerEventList
							.add(new LucentCallControlConnectionEvent(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				} else {
					this.listenerEventList
							.add(new CallControlConnectionEventImpl(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				}
			}
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for listener ").append(this.callListener)
					.toString());
		}
	}

	private void addCallControlConnectionNetworkReachedEvent(int cause,
			int metaCode, MetaEvent metaEvent, TSConnection connTarget,
			Object privateData, int eventId, int numInQueue, String tsEventLog,
			TSEvent ev) {
		if ((this.callListener instanceof CallControlConnectionListener)) {
			String digits = connTarget.getCall().getDigits();
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}
				ConnEventParams connEventParams = createConnParams(connTarget,
						cause, metaCode, privateData, ev);
				if (this.provider.isLucentV5())
					this.listenerEventList
							.add(new LucentV5CallControlConnectionNetworkReachedEvent(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				else if (this.provider.isLucent())
					this.listenerEventList
							.add(new LucentCallControlConnectionNetworkReachedEvent(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				else
					this.listenerEventList
							.add(new CallControlConnectionNetworkReachedEventImpl(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
			}
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for listener ").append(this.callListener)
					.toString());
		}
	}

	private void addCallCenterConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		ConnEventParams connEventParams = createConnParams(connTarget, cause,
				metaCode, privateData, ev);
		if ((this.callListener instanceof CallCenterCallListener)) {
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}
				if ((eventId == 319)
						&& ((this.callListener instanceof ConnectionListener))) {
					if (this.provider.isLucentV5())
						this.listenerEventList
								.add(new LucentV5CallCenterConnectionEvent(
										connEventParams, metaEvent, eventId));
					else if (this.provider.isLucent())
						this.listenerEventList
								.add(new LucentCallCenterConnectionEvent(
										connEventParams, metaEvent, eventId));
					else
						this.listenerEventList
								.add(new CallCenterConnectionEventImpl(
										connEventParams, metaEvent, eventId));
				} else if (this.provider.isLucentV5())
					this.listenerEventList
							.add(new LucentV5CallCenterConnectionEvent(
									connEventParams, metaEvent, eventId));
				else if (this.provider.isLucent())
					this.listenerEventList
							.add(new LucentCallCenterConnectionEvent(
									connEventParams, metaEvent, eventId));
				else {
					this.listenerEventList
							.add(new CallCenterConnectionEventImpl(
									connEventParams, metaEvent, eventId));
				}
			}
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for listener ").append(this.callListener)
					.toString());
		}
	}

	private void addCallCenterEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSTrunk trkTarget, TSCall callTarget,
			Object privateData, int eventId, String tsEventLog, TSEvent ev) {
		if ((this.callListener instanceof CallCenterCallListener)) {
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}

				if (eventId == 316) {
					CallEventParams params = createCallParams(callTarget,
							cause, metaCode, null, ev);
					this.listenerEventList
							.add(new CallCenterCallApplicationDataEventImpl(
									params, metaEvent, eventId, ((TSCall) ev
											.getEventTarget())
											.getApplicationData()));
				} else {
					CallEventParams params = createCallParams(trkTarget, cause,
							metaCode, privateData, ev);
					this.listenerEventList.add(new CallCenterTrunkEventImpl(
							params, metaEvent, eventId));
				}
			}
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for listener ").append(this.callListener)
					.toString());
		}
	}

	private void addTerminalConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		if ((this.callListener instanceof TerminalConnectionListener)) {
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}
				TermConnEventParams tcEventParams = createTermConnParams(
						connTarget, cause, metaCode, privateData, ev);
				this.listenerEventList.add(new TerminalConnectionEventImpl(
						tcEventParams, metaEvent, eventId));
			}
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for listener ").append(this.callListener)
					.toString());
		}
	}

	private void addConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		if ((this.callListener instanceof ConnectionListener)) {
			synchronized (this.listenerEventList) {
				if (!this.metaStartAdded) {
					this.listenerEventList.add(metaEvent);
					this.metaStartAdded = true;
				}
				ConnEventParams connEventParams = createConnParams(connTarget,
						cause, metaCode, privateData, ev);
				this.listenerEventList.add(new ConnectionEventImpl(
						connEventParams, metaEvent, eventId));
			}
			log.debug(new StringBuilder().append(tsEventLog)
					.append(" for listener ").append(this.callListener)
					.toString());
		}
	}

	private void createObserverEvents(Vector<TSEvent> _eventList, int cause,
			boolean snapshot) {
		String tsEventLog = null;
		int metaCode;
		if (snapshot) {
			metaCode = 135;
		} else {
			metaCode = getObserverMetaCode(cause, _eventList);
		}

		int nextMetaEventIndex = this.eventList.size();

		TSEvent ev = null;
		TSCall callTarget = null;
		TSConnection connTarget = null;
		TSTrunk trkTarget = null;
		Object privateData = null;
		log.debug(new StringBuilder().append("meta event BEGIN: cause (")
				.append(cause).append(") metaCode (").append(metaCode)
				.append(")").append(" for ").append(this.observer).toString());

		for (int i = 0; i < _eventList.size(); i++) {
			ev = (TSEvent) _eventList.elementAt(i);
			if ((ev.getEventTarget() instanceof TSCall)) {
				callTarget = (TSCall) ev.getEventTarget();
			} else if ((ev.getEventTarget() instanceof TSTrunk)) {
				trkTarget = (TSTrunk) ev.getEventTarget();
			} else {
				connTarget = (TSConnection) ev.getEventTarget();
			}

			privateData = ev.getPrivateData();

			switch (ev.getEventType()) {
			case 4:
				tsEventLog = new StringBuilder().append("CALLACTIVEEVENT for ")
						.append(callTarget).toString();

				addObserverEvent(
						new TsapiCallActiveEvent(createCallParams(callTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 5:
				tsEventLog = new StringBuilder()
						.append("CALLINVALIDEVENT for ").append(callTarget)
						.toString();

				addObserverEvent(
						new TsapiCallInvalidEvent(createCallParams(callTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 6:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONCREATEDEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiConnCreatedEvent(createConnParams(connTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 7:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONCONNECTEDEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiConnConnectedEvent(createConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 8:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONINPROGRESSEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiConnInProgressEvent(createConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 9:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONALERTINGEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiConnAlertingEvent(createConnParams(connTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 10:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONDISCONNECTEDEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiConnDisconnectedEvent(createConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 11:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONFAILEDEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiConnFailedEvent(createConnParams(connTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 12:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONUNKNOWNEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiConnUnknownEvent(createConnParams(connTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 13:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONCREATEDEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiTermConnCreatedEvent(createTermConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 14:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONACTIVEEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiTermConnActiveEvent(createTermConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 15:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONRINGINGEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiTermConnRingingEvent(createTermConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 16:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONPASSIVEEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiTermConnPassiveEvent(createTermConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 17:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONDROPPEDEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiTermConnDroppedEvent(createTermConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 18:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONUNKNOWNEVENT for ")
						.append(connTarget).toString();

				addObserverEvent(
						new TsapiTermConnUnknownEvent(createTermConnParams(
								connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 19:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONOFFEREDEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnOfferedEventImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnOfferedEventImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnOfferedEvent(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 20:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONDIALINGEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnDialingEventImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnDialingEventImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnDialingEvent(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 21:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONESTABLISHEDEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnEstablishedEventImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnEstablishedEventImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnEstablishedEvent(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 22:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONNETWORKREACHEDEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnNetworkReachedEventImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnNetworkReachedEventImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnNetworkReachedEvent(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 23:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONNETWORKALERTINGEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnNetworkAlertingEventImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnNetworkAlertingEventImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnNetworkAlertingEvent(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 24:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONINITIATEDEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnInitiatedEventImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnInitiatedEventImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnInitiatedEvent(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 25:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONQUEUEDEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnQueuedEventImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev), connTarget.getTSDevice()
									.getNumberQueued()), tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnQueuedEventImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev), connTarget.getTSDevice()
									.getNumberQueued()), tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnQueuedEvent(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev), connTarget.getTSDevice()
									.getNumberQueued()), tsEventLog);
				}

				break;
			case 26:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONALERTINGEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnAlertingEventCCImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnAlertingEventCCImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnAlertingEventCC(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 27:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONDISCONNECTEDEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnDisconnectedEventCCImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnDisconnectedEventCCImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnDisconnectedEventCC(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 28:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONFAILEDEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnFailedEventCCImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnFailedEventCCImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnFailedEventCC(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 29:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONUNKNOWNEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnUnknownEventCCImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnUnknownEventCCImpl(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnUnknownEventCC(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 56:
				tsEventLog = new StringBuilder()
						.append("CONNECTIONINPROGRESSEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5ConnInProgressEventCCImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentConnInProgressEventCCImpl(
									createConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiConnInProgressEventCC(createConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 30:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONTALKINGEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5TermConnTalkingEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentTermConnTalkingEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiTermConnTalkingEvent(createTermConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 31:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONHELDEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5TermConnHeldEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentTermConnHeldEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiTermConnHeldEvent(createTermConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 32:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONBRIDGEDEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5TermConnBridgedEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentTermConnBridgedEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiTermConnBridgedEvent(createTermConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 33:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONINUSEEVENT for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5TermConnInUseEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentTermConnInUseEventImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiTermConnInUseEvent(createTermConnParams(
									connTarget, cause, metaCode, privateData,
									ev)), tsEventLog);
				}

				break;
			case 34:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONDROPPEDEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5TermConnDroppedEventCCImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentTermConnDroppedEventCCImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiTermConnDroppedEventCC(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				}

				break;
			case 35:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONRINGINGEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5TermConnRingingEventCCImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentTermConnRingingEventCCImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiTermConnRingingEventCC(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				}

				break;
			case 36:
				tsEventLog = new StringBuilder()
						.append("TERMINALCONNECTIONUNKNOWNEVENT_CC for ")
						.append(connTarget).toString();

				if (this.provider.isLucentV5()) {
					addObserverEvent(
							new LucentV5TermConnUnknownEventCCImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else if (this.provider.isLucent()) {
					addObserverEvent(
							new LucentTermConnUnknownEventCCImpl(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				} else {
					addObserverEvent(
							new TsapiTermConnUnknownEventCC(
									createTermConnParams(connTarget, cause,
											metaCode, privateData, ev)),
							tsEventLog);
				}

				break;
			case 54:
				tsEventLog = new StringBuilder().append("TRUNKVALIDEVENT for ")
						.append(trkTarget).toString();

				addObserverEvent(
						new TsapiTrunkValidEv(createCallParams(trkTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 55:
				tsEventLog = new StringBuilder()
						.append("TRUNKINVALIDEVENT for ").append(trkTarget)
						.toString();

				addObserverEvent(
						new TsapiTrunkInvalidEv(createCallParams(trkTarget,
								cause, metaCode, privateData, ev)), tsEventLog);

				break;
			case 57:
				tsEventLog = new StringBuilder()
						.append("APPLICATIONDATAEVENT for ").append(callTarget)
						.toString();

				addObserverEvent(
						new TsapiCallCentCallAppDataEv(createCallParams(
								callTarget, cause, metaCode, privateData, ev),
								((TSCall) ev.getEventTarget())
										.getApplicationData()), tsEventLog);

				break;
			case 58:
				String digits = callTarget.getDigits();
				for (int strIndex = 0; strIndex < digits.length(); strIndex++) {
					tsEventLog = new StringBuilder()
							.append("CALLDTMFEVENT for ").append(callTarget)
							.toString();

					addObserverEvent(
							new TsapiTermConnDTMFEvent(createCallParams(
									callTarget, cause, metaCode, privateData,
									ev), digits.charAt(strIndex)), tsEventLog);
				}

				break;
			case 9999:
				tsEventLog = new StringBuilder().append("PRIVATEEVENT for ")
						.append(callTarget).toString();

				addObserverEvent(
						new TsapiPrivateCallEvent(createCallParams(callTarget,
								cause, metaCode, privateData, ev)), tsEventLog);
			}

		}

		synchronized (this.eventList) {
			log.debug(new StringBuilder().append("meta event END for ")
					.append(this.observer).append(" eventList size=")
					.append(this.eventList.size()).toString());

			if (this.eventList.size() == 0) {
				log.debug(new StringBuilder().append("no events to send to ")
						.append(this.observer).toString());
				return;
			}

			if (nextMetaEventIndex < this.eventList.size()) {
				((TsapiObserverEvent) this.eventList
						.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
			}
		}
	}

	private ConnEventParams createConnParams(TSConnection conn, int cause,
			int metaCode, Object privateData, ArrayList<TSCall> oldCalls,
			TSEvent tsEvent) {
		TSCall call = null;
		if (conn != null) {
			call = conn.getCall();
		}

		ConnEventParams params = new ConnEventParams();
		ArrayList<Call> oldCallList = new ArrayList<Call>();
		for (TSCall tsCall : oldCalls)
			oldCallList.add((Call) TsapiCreateObject.getTsapiObject(tsCall,
					false));
		params.setOldCalls(oldCallList);

		params.setConnection((Connection) TsapiCreateObject.getTsapiObject(
				conn, true));
		return (ConnEventParams) createCallParams(params, call, cause,
				metaCode, privateData, tsEvent);
	}

	private ConnEventParams createConnParams(TSConnection conn, int cause,
			int metaCode, Object privateData, TSEvent tsEvent) {
		TSCall call = null;
		if (conn != null) {
			call = conn.getCall();
		}

		ConnEventParams params = new ConnEventParams();
		params.setConnection((Connection) TsapiCreateObject.getTsapiObject(
				conn, true));
		return (ConnEventParams) createCallParams(params, call, cause,
				metaCode, privateData, tsEvent);
	}

	private TermConnEventParams createTermConnParams(TSConnection conn,
			int cause, int metaCode, Object privateData, TSEvent tsEvent) {
		TSCall call = null;
		if (conn != null) {
			call = conn.getCall();
		}

		TermConnEventParams params = new TermConnEventParams();
		params.setTermConn((TerminalConnection) TsapiCreateObject
				.getTsapiObject(conn, false));
		return (TermConnEventParams) createCallParams(params, call, cause,
				metaCode, privateData, tsEvent);
	}

	private CallEventParams createCallParams(TSTrunk trunk, int cause,
			int metaCode, Object privateData, TSEvent tsEvent) {
		CallEventParams params = new CallEventParams();
		params.setTrunk((TsapiTrunk) TsapiCreateObject.getTsapiObject(trunk,
				false));

		TSCall call = trunk.getTSCall();
		return createCallParams(params, call, cause, metaCode, privateData,
				tsEvent);
	}

	private CallEventParams createCallParams(TSCall call, int cause,
			int metaCode, Object privateData, ArrayList<TSCall> oldCalls,
			TSEvent tsEvent) {
		CallEventParams callEventParams = new CallEventParams();
		ArrayList<Call> oldCallList = new ArrayList<Call>();
		for (TSCall tsCall : oldCalls)
			oldCallList.add((Call) TsapiCreateObject.getTsapiObject(tsCall,
					false));
		callEventParams.setOldCalls(oldCallList);
		return createCallParams(callEventParams, call, cause, metaCode,
				privateData, tsEvent);
	}

	private CallEventParams createCallParams(TSCall call, int cause,
			int metaCode, Object privateData, TSEvent tsEvent) {
		return createCallParams(new CallEventParams(), call, cause, metaCode,
				privateData, tsEvent);
	}

	private CallEventParams createCallParams(CallEventParams params,
			TSCall call, int cause, int metaCode, Object privateData,
			TSEvent tsEvent) {
		params.setCause(cause);
		params.setMetaCode(metaCode);
		params.setPrivateData(TsapiPromoter.promotePrivateEvent(this.provider,
				privateData));
		if (call == null) {
			return params;
		}

		TSDevice callingAddress = call.getCallingAddress();
		if (callingAddress != null) {
			params.setCallingAddress((Address) TsapiCreateObject
					.getTsapiObject(callingAddress, true));
		}
		TSDevice callingTerminal = call.getCallingTerminal();
		if (callingTerminal != null) {
			params.setCallingTerminal((Terminal) TsapiCreateObject
					.getTsapiObject(callingTerminal, false));
		}
		TSDevice calledAddress = call.getCalledDevice();
		if (calledAddress != null) {
			params.setCalledAddress((Address) TsapiCreateObject.getTsapiObject(
					calledAddress, true));
		}
		TSDevice lastRedirectionDevice = call.getLastRedirectionDevice();
		if (lastRedirectionDevice != null) {
			params.setLastRedirectionAddress((Address) TsapiCreateObject
					.getTsapiObject(lastRedirectionDevice, true));
		}

		TsapiCall tsapiCall = (TsapiCall) TsapiCreateObject.getTsapiObject(
				call, false);
		params.setCall(tsapiCall);
		if ((tsEvent != null) && (tsEvent.getSnapshotCstaCause() != -1)) {
			params.setCstaCause(tsEvent.getSnapshotCstaCause());
			params.setCsta3Cause(tsEvent.getSnapshotCsta3Cause());
		} else {
			params.setCstaCause(call.getCSTACause());
			params.setCsta3Cause(call.getCSTA3Cause());
		}

		params.setTrunks(tsapiCall.getTrunks());
		return params;
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		if (this.observer != null) {
			synchronized (this.syncObject) {
				log.debug(new StringBuilder()
						.append("TP thread woke up: Locked callChangedEvent, attempting to Lock TsapiCallMonitor for CallObserver ")
						.append(this.observer).toString());

				CallEv[] eventArray = null;
				synchronized (this) {
					synchronized (this.eventList) {
						if (this.eventList.size() == 0) {
							log.debug("TsapiCallMonitor: events delivered by previous thread; no events to deliver in this thread");
							TsapiTrace.traceExit("run[]", this);
							return;
						}
						log.debug(new StringBuilder()
								.append("TP thread Locked TsapiCallMonitor: removing events, itself for CallObserver ")
								.append(this.observer).toString());

						eventArray = new CallEv[this.eventList.size()];
						this.eventList.copyInto(eventArray);
						this.eventList.clear();
					}
				}
				log.debug(new StringBuilder()
						.append("TP thread Unlocked TsapiCallMonitor: calling callChangedEvent, delivering events for CallObserver ")
						.append(this.observer).toString());
				try {
					this.observer.callChangedEvent(eventArray);
				} catch (Exception e) {
					log.error(
							new StringBuilder()
									.append("EXCEPTION thrown by callChangedEvent in ")
									.append(this.observer).append(" - ")
									.append(e.getMessage()).toString(), e);
				}

				log.debug(new StringBuilder()
						.append("TP thread returned from callChangedEvent, Unlocked callChangedEvent for CallObserver ")
						.append(this.observer).toString());
			}

		} else {
			deliverListenerEvents();
		}
		TsapiTrace.traceExit("run[]", this);
	}

	private void deliverListenerEvents() {
		Event[] arrayOfEvents = null;
		synchronized (this.syncObject) {
			log.debug(new StringBuilder()
					.append("Got lock on syncObject for CallListener - ")
					.append(this.callListener).toString());
			synchronized (this.listenerEventList) {
				arrayOfEvents = new Event[this.listenerEventList.size()];
				this.listenerEventList.copyInto(arrayOfEvents);
				this.listenerEventList.clear();
			}

			for (Event event : arrayOfEvents) {
				deliverEventToCallback(event);
			}
		}
		log.debug(new StringBuilder()
				.append("Released lock on syncObject for CallListener - ")
				.append(this.callListener).toString());
	}

	private void deliverEventToCallback(Event callEvent) {
		switch (callEvent.getID()) {
		case 101:
			this.callListener.callActive((CallEvent) callEvent);
			break;
		case 102:
			this.callListener.callInvalid((CallEvent) callEvent);
			break;
		case 106:
			((ConnectionListener) this.callListener)
					.connectionCreated((ConnectionEvent) callEvent);
			break;
		case 105:
			((ConnectionListener) this.callListener)
					.connectionConnected((ConnectionEvent) callEvent);
			break;
		case 109:
			((ConnectionListener) this.callListener)
					.connectionInProgress((ConnectionEvent) callEvent);
			break;
		case 104:
			((ConnectionListener) this.callListener)
					.connectionAlerting((ConnectionEvent) callEvent);
			break;
		case 107:
			((ConnectionListener) this.callListener)
					.connectionDisconnected((ConnectionEvent) callEvent);
			break;
		case 108:
			((ConnectionListener) this.callListener)
					.connectionFailed((ConnectionEvent) callEvent);
			break;
		case 110:
			((ConnectionListener) this.callListener)
					.connectionUnknown((ConnectionEvent) callEvent);
			break;
		case 116:
			((TerminalConnectionListener) this.callListener)
					.terminalConnectionCreated((TerminalConnectionEvent) callEvent);
			break;
		case 115:
			((TerminalConnectionListener) this.callListener)
					.terminalConnectionActive((TerminalConnectionEvent) callEvent);
			break;
		case 119:
			((TerminalConnectionListener) this.callListener)
					.terminalConnectionRinging((TerminalConnectionEvent) callEvent);
			break;
		case 118:
			((TerminalConnectionListener) this.callListener)
					.terminalConnectionPassive((TerminalConnectionEvent) callEvent);
			break;
		case 117:
			((TerminalConnectionListener) this.callListener)
					.terminalConnectionDropped((TerminalConnectionEvent) callEvent);
			break;
		case 120:
			((TerminalConnectionListener) this.callListener)
					.terminalConnectionUnknown((TerminalConnectionEvent) callEvent);
			break;
		case 361:
			((CallControlConnectionListener) this.callListener)
					.connectionOffered((CallControlConnectionEvent) callEvent);
			break;
		case 354:
			((CallControlConnectionListener) this.callListener)
					.connectionDialing((CallControlConnectionEvent) callEvent);
			break;
		case 356:
			((CallControlConnectionListener) this.callListener)
					.connectionEstablished((CallControlConnectionEvent) callEvent);
			break;
		case 360:
			((CallControlConnectionListener) this.callListener)
					.connectionNetworkReached((CallControlConnectionEvent) callEvent);
			break;
		case 359:
			((CallControlConnectionListener) this.callListener)
					.connectionNetworkAlerting((CallControlConnectionEvent) callEvent);
			break;
		case 358:
			((CallControlConnectionListener) this.callListener)
					.connectionInitiated((CallControlConnectionEvent) callEvent);
			break;
		case 362:
			((CallControlConnectionListener) this.callListener)
					.connectionQueued((CallControlConnectionEvent) callEvent);
			break;
		case 353:
			((CallControlConnectionListener) this.callListener)
					.connectionAlerting((CallControlConnectionEvent) callEvent);
			break;
		case 355:
			((CallControlConnectionListener) this.callListener)
					.connectionDisconnected((CallControlConnectionEvent) callEvent);
			break;
		case 357:
			((CallControlConnectionListener) this.callListener)
					.connectionFailed((CallControlConnectionEvent) callEvent);
			break;
		case 363:
			((CallControlConnectionListener) this.callListener)
					.connectionUnknown((CallControlConnectionEvent) callEvent);
			break;
		case 319:
			((ConnectionListener) this.callListener)
					.connectionInProgress((ConnectionEvent) callEvent);
			break;
		case 369:
			((CallControlTerminalConnectionListener) this.callListener)
					.terminalConnectionTalking((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 366:
			((CallControlTerminalConnectionListener) this.callListener)
					.terminalConnectionHeld((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 364:
			((CallControlTerminalConnectionListener) this.callListener)
					.terminalConnectionBridged((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 367:
			((CallControlTerminalConnectionListener) this.callListener)
					.terminalConnectionInUse((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 365:
			((CallControlTerminalConnectionListener) this.callListener)
					.terminalConnectionDropped((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 368:
			((CallControlTerminalConnectionListener) this.callListener)
					.terminalConnectionRinging((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 370:
			((CallControlTerminalConnectionListener) this.callListener)
					.terminalConnectionUnknown((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 318:
			((CallCenterCallListener) this.callListener)
					.trunkInvalid((CallCenterTrunkEvent) callEvent);
			break;
		case 317:
			((CallCenterCallListener) this.callListener)
					.trunkValid((CallCenterTrunkEvent) callEvent);
			break;
		case 316:
			((CallCenterCallListener) this.callListener)
					.applicationData((CallCenterCallEvent) callEvent);
			break;
		case 601:
			((PrivateDataCallListener) this.callListener)
					.callPrivateData((PrivateDataEvent) callEvent);
			break;
		case 210:
			this.callListener
					.singleCallMetaProgressStarted((MetaEvent) callEvent);
			break;
		case 211:
			this.callListener
					.singleCallMetaProgressEnded((MetaEvent) callEvent);
			break;
		case 212:
			this.callListener
					.singleCallMetaSnapshotStarted((MetaEvent) callEvent);
			break;
		case 213:
			this.callListener
					.singleCallMetaSnapshotEnded((MetaEvent) callEvent);
			break;
		case 200:
			this.callListener.multiCallMetaMergeStarted((MetaEvent) callEvent);
			break;
		case 201:
			this.callListener.multiCallMetaMergeEnded((MetaEvent) callEvent);
			break;
		case 202:
			this.callListener
					.multiCallMetaTransferStarted((MetaEvent) callEvent);
			break;
		case 203:
			this.callListener.multiCallMetaTransferEnded((MetaEvent) callEvent);
			break;
		case 103:
			this.callListener.callEventTransmissionEnded((CallEvent) callEvent);
		}
	}
}