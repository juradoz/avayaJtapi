package com.avaya.jtapi.tsapi.impl.monitor;

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
import com.avaya.jtapi.tsapi.impl.events.call.CallCenterTrunkEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallControlCallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventUtil;
import com.avaya.jtapi.tsapi.impl.events.call.PrivateDataCallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallActiveEvent;
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

	public TsapiCallMonitor(TSProviderImpl _provider, CallListener listener) {
		provider = _provider;
		observer = null;
		callListener = listener;
		listenerEventList = new Vector();
		eventList = null;
		provider.addCallMonitorThread(this);

		isVDN = false;

		deliverEvents(null, 0, false);
	}

	public TsapiCallMonitor(TSProviderImpl _provider, CallObserver _observer) {
		provider = _provider;
		observer = _observer;
		callListener = null;
		eventList = new Vector();
		listenerEventList = null;
		provider.addCallMonitorThread(this);

		isVDN = false;

		eventSubscriptionType.set(0);
		if (observer instanceof CallControlCallObserver) {
			eventSubscriptionType.set(1);
		}
		if (observer instanceof CallCenterCallObserver) {
			eventSubscriptionType.set(2);
		}
		// if (this.observer instanceof MediaCallObserver)
		// {
		// this.eventSubscriptionType.set(3);
		// }

		eventSubscriptionType.set(5);

		deliverEvents(null, 0, false);
	}

	private void addCallCenterConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		ConnEventParams connEventParams = createConnParams(connTarget, cause,
				metaCode, privateData, ev);
		if (callListener instanceof CallCenterCallListener) {
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				if ((eventId == 319)
						&& (callListener instanceof ConnectionListener)) {
					if (provider.isLucentV5()) {
						listenerEventList
								.add(new LucentV5CallCenterConnectionEvent(
										connEventParams, metaEvent, eventId));
					} else if (provider.isLucent()) {
						listenerEventList
								.add(new LucentCallCenterConnectionEvent(
										connEventParams, metaEvent, eventId));
					} else {
						listenerEventList
								.add(new CallCenterConnectionEventImpl(
										connEventParams, metaEvent, eventId));
					}
				} else if (provider.isLucentV5()) {
					listenerEventList
							.add(new LucentV5CallCenterConnectionEvent(
									connEventParams, metaEvent, eventId));
				} else if (provider.isLucent()) {
					listenerEventList.add(new LucentCallCenterConnectionEvent(
							connEventParams, metaEvent, eventId));
				} else {
					listenerEventList.add(new CallCenterConnectionEventImpl(
							connEventParams, metaEvent, eventId));
				}
			}
			log.debug(tsEventLog + " for listener " + callListener);
		}
	}

	private void addCallCenterEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSTrunk trkTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		if (callListener instanceof CallCenterCallListener) {
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				CallEventParams params = createCallParams(trkTarget, cause,
						metaCode, privateData, ev);
				listenerEventList.add(new CallCenterTrunkEventImpl(params,
						metaEvent, eventId));
			}
			log.debug(tsEventLog + " for listener " + callListener);
		}
	}

	private void addCallControlConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, int numInQueue, String tsEventLog, TSEvent ev) {
		if (callListener instanceof CallControlConnectionListener) {
			String digits = connTarget.getCall().getDigits();
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				ConnEventParams connEventParams = createConnParams(connTarget,
						cause, metaCode, privateData, ev);
				if (provider.isLucentV5()) {
					listenerEventList
							.add(new LucentV5CallControlConnectionEvent(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				} else if (provider.isLucent()) {
					listenerEventList.add(new LucentCallControlConnectionEvent(
							connEventParams, metaEvent, eventId, numInQueue,
							digits));
				} else {
					listenerEventList.add(new CallControlConnectionEventImpl(
							connEventParams, metaEvent, eventId, numInQueue,
							digits));
				}
			}
			log.debug(tsEventLog + " for listener " + callListener);
		}
	}

	private void addCallControlConnectionNetworkReachedEvent(int cause,
			int metaCode, MetaEvent metaEvent, TSConnection connTarget,
			Object privateData, int eventId, int numInQueue, String tsEventLog,
			TSEvent ev) {
		if (callListener instanceof CallControlConnectionListener) {
			String digits = connTarget.getCall().getDigits();
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				ConnEventParams connEventParams = createConnParams(connTarget,
						cause, metaCode, privateData, ev);
				if (provider.isLucentV5()) {
					listenerEventList
							.add(new LucentV5CallControlConnectionNetworkReachedEvent(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				} else if (provider.isLucent()) {
					listenerEventList
							.add(new LucentCallControlConnectionNetworkReachedEvent(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				} else {
					listenerEventList
							.add(new CallControlConnectionNetworkReachedEventImpl(
									connEventParams, metaEvent, eventId,
									numInQueue, digits));
				}
			}
			log.debug(tsEventLog + " for listener " + callListener);
		}
	}

	private void addCallControlTerminalConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		if (callListener instanceof CallControlTerminalConnectionListener) {
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				TermConnEventParams connEventParams = createTermConnParams(
						connTarget, cause, metaCode, privateData, ev);
				if (provider.isLucentV5()) {
					listenerEventList
							.add(new LucentV5CallControlTerminalConnectionEvent(
									connEventParams, metaEvent, eventId));
				} else if (provider.isLucent()) {
					listenerEventList
							.add(new LucentCallControlTerminalConnectionEvent(
									connEventParams, metaEvent, eventId));
				} else {
					listenerEventList
							.add(new CallControlTerminalConnectionEventImpl(
									connEventParams, metaEvent, eventId));
				}
			}
			log.debug(tsEventLog + " for listener " + callListener);
		}
	}

	private void addConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		if (callListener instanceof ConnectionListener) {
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				ConnEventParams connEventParams = createConnParams(connTarget,
						cause, metaCode, privateData, ev);
				listenerEventList.add(new ConnectionEventImpl(connEventParams,
						metaEvent, eventId));
			}
			log.debug(tsEventLog + " for listener " + callListener);
		}
	}

	void addObserverEvent(CallEv event, String tsEventLog) {
		if ((eventSubscriptionType.get(0))
				&& (((ITsapiEvent) event).getEventPackage() == 0)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else if ((eventSubscriptionType.get(1))
				&& (((ITsapiEvent) event).getEventPackage() == 1)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else if ((eventSubscriptionType.get(2))
				&& (((ITsapiEvent) event).getEventPackage() == 2)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else if ((eventSubscriptionType.get(3))
				&& (((ITsapiEvent) event).getEventPackage() == 3)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else if ((eventSubscriptionType.get(5))
				&& (((ITsapiEvent) event).getEventPackage() == 5)) {
			log.debug(tsEventLog + " for observer " + observer);

			eventList.addElement(event);
		} else {
			log.debug(tsEventLog + " ignored");
		}
	}

	public synchronized void addReference() {
		reference += 1L;
	}

	private void addTerminalConnectionEvent(int cause, int metaCode,
			MetaEvent metaEvent, TSConnection connTarget, Object privateData,
			int eventId, String tsEventLog, TSEvent ev) {
		if (callListener instanceof TerminalConnectionListener) {
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				TermConnEventParams tcEventParams = createTermConnParams(
						connTarget, cause, metaCode, privateData, ev);
				listenerEventList.add(new TerminalConnectionEventImpl(
						tcEventParams, metaEvent, eventId));
			}
			log.debug(tsEventLog + " for listener " + callListener);
		}
	}

	private void checkAndAddPrivateEvent(Object eventTarget,
			PrivateDataCallEventImpl privateEvent, Event metaEvent) {
		if (callListener instanceof PrivateDataCallListener) {
			synchronized (listenerEventList) {
				if (!metaStartAdded) {
					listenerEventList.add(metaEvent);
					metaStartAdded = true;
				}
				listenerEventList.add(privateEvent);
			}
			log.info("PRIVATEEVENT for " + eventTarget + " for listener "
					+ callListener);
		}
	}

	private CallEventParams createCallParams(CallEventParams params,
			TSCall call, int cause, int metaCode, Object privateData,
			TSEvent tsEvent) {
		params.setCause(cause);
		params.setMetaCode(metaCode);
		params.setPrivateData(TsapiPromoter.promotePrivateEvent(provider,
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
		} else {
			params.setCstaCause(call.getCSTACause());
		}
		params.setTrunks(tsapiCall.getTrunks());
		return params;
	}

	private CallEventParams createCallParams(TSCall call, int cause,
			int metaCode, Object privateData, ArrayList<TSCall> oldCalls,
			TSEvent tsEvent) {
		CallEventParams callEventParams = new CallEventParams();
		ArrayList oldCallList = new ArrayList();
		for (TSCall tsCall : oldCalls) {
			oldCallList.add((Call) TsapiCreateObject.getTsapiObject(tsCall,
					false));
		}
		callEventParams.setOldCalls(oldCallList);
		return createCallParams(callEventParams, call, cause, metaCode,
				privateData, tsEvent);
	}

	private CallEventParams createCallParams(TSCall call, int cause,
			int metaCode, Object privateData, TSEvent tsEvent) {
		return createCallParams(new CallEventParams(), call, cause, metaCode,
				privateData, tsEvent);
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

	private ConnEventParams createConnParams(TSConnection conn, int cause,
			int metaCode, Object privateData, ArrayList<TSCall> oldCalls,
			TSEvent tsEvent) {
		TSCall call = null;
		if (conn != null) {
			call = conn.getCall();
		}

		ConnEventParams params = new ConnEventParams();
		ArrayList oldCallList = new ArrayList();
		for (TSCall tsCall : oldCalls) {
			oldCallList.add((Call) TsapiCreateObject.getTsapiObject(tsCall,
					false));
		}
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

	private void createListenerEvents(Vector<TSEvent> _eventList, int cause,
			boolean snapshot) {
		TSCall callTarget = null;
		TSConnection connTarget = null;
		TSTrunk trkTarget = null;
		Object privateData = null;
		CallEventParams callEventParams = null;
		int metaCode = -1;
		MetaEvent[] metaEventPair = null;
		int nextMetaEventIndex = listenerEventList.size();

		TSEvent event = (TSEvent) _eventList.get(0);

		if (event.getEventTarget() instanceof TSCall) {
			callTarget = (TSCall) event.getEventTarget();
			if ((((cause == 212) || (cause == 207)))
					&& (event.getTransferredEventParams() != null)) {
				callEventParams = createCallParams(callTarget, cause, -1, event
						.getPrivateData(), event.getTransferredEventParams()
						.getOldCalls(), event);
			} else {
				callEventParams = createCallParams(callTarget, cause, -1, event
						.getPrivateData(), event);
			}
		} else if (event.getEventTarget() instanceof TSTrunk) {
			trkTarget = (TSTrunk) event.getEventTarget();
			if ((((cause == 212) || (cause == 207)))
					&& (event.getTransferredEventParams() != null)) {
				callEventParams = createCallParams(callTarget, cause, -1, event
						.getPrivateData(), event.getTransferredEventParams()
						.getOldCalls(), event);
			} else {
				callEventParams = createCallParams(trkTarget, cause, -1, event
						.getPrivateData(), event);
			}
		} else {
			connTarget = (TSConnection) event.getEventTarget();
			if ((((cause == 212) || (cause == 207)))
					&& (event.getTransferredEventParams() != null)) {
				callEventParams = createConnParams(connTarget, cause, -1, event
						.getPrivateData(), event.getTransferredEventParams()
						.getOldCalls(), event);
			} else {
				callEventParams = createConnParams(connTarget, cause, -1, event
						.getPrivateData(), event);
			}

		}

		metaEventPair = CallEventUtil.getListenerMetaObject(cause,
				callEventParams, snapshot);

		MetaEvent metaEvent = metaEventPair[0];
		metaCode = metaEvent.getID();

		String tsEventLog = null;

		log.debug("Meta event BEGIN: cause (" + cause + ") metaCode ("
				+ metaCode + ")" + " for "
				+ ((observer == null) ? callListener : observer));
		Object prevPrivateData = null;
		for (TSEvent ev : _eventList) {
			if (ev.getEventTarget() instanceof TSCall) {
				callTarget = (TSCall) ev.getEventTarget();
			} else if (ev.getEventTarget() instanceof TSTrunk) {
				trkTarget = (TSTrunk) ev.getEventTarget();
			} else {
				connTarget = (TSConnection) ev.getEventTarget();
			}

			privateData = ev.getPrivateData();
			PrivateDataCallEventImpl privateEvent = null;

			if ((privateData != null)
					&& (ev.getEventType() != 9999)
					&& (((privateData instanceof TsapiPrivate)
							|| (privateData instanceof LucentChargeAdviceEvent) || (privateData instanceof TsapiPrivateStateEvent)))) {
				if (!privateData.equals(prevPrivateData)) {
					prevPrivateData = privateData;
					Object source = null;
					if (callTarget != null) {
						source = callTarget;
					} else if (connTarget != null) {
						source = connTarget;
					} else if (trkTarget != null) {
						source = trkTarget;
					}
					privateEvent = new PrivateDataCallEventImpl(privateData,
							source, cause, metaEvent);
					checkAndAddPrivateEvent(source, privateEvent, metaEvent);
				} else {
					prevPrivateData = null;
				}
			}
			CallEventParams params = null;

			switch (ev.getEventType()) {
			case 4:
				params = createCallParams(callTarget, cause, metaCode,
						privateData, ev);
				synchronized (listenerEventList) {
					if (!metaStartAdded) {
						listenerEventList.add(metaEvent);
						metaStartAdded = true;
					}
					listenerEventList.add(new CallEventImpl(params, metaEvent,
							101));
				}
				tsEventLog = "CALLACTIVEEVENT for " + callTarget;
				log.debug(tsEventLog + " for listener " + callListener);
				break;
			case 5:
				params = createCallParams(callTarget, cause, metaCode,
						privateData, ev);
				synchronized (listenerEventList) {
					if (!metaStartAdded) {
						listenerEventList.add(metaEvent);
						metaStartAdded = true;
					}
					listenerEventList.add(new CallControlCallEventImpl(params,
							metaEvent, 102));
				}
				tsEventLog = "CALLINVALIDEVENT for " + callTarget;
				log.debug(tsEventLog + " for listener " + callListener);
				break;
			case 6:
				tsEventLog = "CONNECTIONCREATEDEVENT for " + connTarget;
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 106, tsEventLog, ev);
				break;
			case 7:
				tsEventLog = "CONNECTIONCONNECTEDEVENT for " + connTarget;
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 105, tsEventLog, ev);
				break;
			case 8:
				tsEventLog = "CONNECTIONINPROGRESSEVENT for " + connTarget;
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 109, tsEventLog, ev);
				break;
			case 9:
				tsEventLog = "CONNECTIONALERTINGEVENT for " + connTarget;
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 104, tsEventLog, ev);
				break;
			case 10:
				tsEventLog = "CONNECTIONDISCONNECTEDEVENT for " + connTarget;
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 107, tsEventLog, ev);
				break;
			case 11:
				tsEventLog = "CONNECTIONFAILEDEVENT for " + connTarget;
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 108, tsEventLog, ev);
				break;
			case 12:
				tsEventLog = "CONNECTIONUNKNOWNEVENT for " + connTarget;
				addConnectionEvent(cause, metaCode, metaEvent, connTarget,
						privateData, 110, tsEventLog, ev);
				break;
			case 13:
				tsEventLog = "TERMINALCONNECTIONCREATEDEVENT for " + connTarget;
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 116, tsEventLog, ev);
				break;
			case 14:
				tsEventLog = "TERMINALCONNECTIONACTIVEEVENT for " + connTarget;
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 115, tsEventLog, ev);
				break;
			case 15:
				tsEventLog = "TERMINALCONNECTIONRINGINGEVENT for " + connTarget;
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 119, tsEventLog, ev);
				break;
			case 16:
				tsEventLog = "TERMINALCONNECTIONPASSIVEEVENT for " + connTarget;
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 118, tsEventLog, ev);
				break;
			case 17:
				tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT for " + connTarget;
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 117, tsEventLog, ev);
				break;
			case 18:
				tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT for " + connTarget;
				addTerminalConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 120, tsEventLog, ev);
				break;
			case 19:
				tsEventLog = "CONNECTIONOFFEREDEVENT for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 361, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 20:
				tsEventLog = "CONNECTIONDIALINGEVENT for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 354, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 21:
				tsEventLog = "CONNECTIONESTABLISHEDEVENT for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 356, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 22:
				tsEventLog = "CONNECTIONNETWORKREACHEDEVENT for " + connTarget;
				addCallControlConnectionNetworkReachedEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 360, connTarget
								.getTSDevice().getNumberQueued(), tsEventLog,
						ev);
				break;
			case 23:
				tsEventLog = "CONNECTIONNETWORKALERTINGEVENT for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 359, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 24:
				tsEventLog = "CONNECTIONINITIATEDEVENT for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 358, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 25:
				tsEventLog = "CONNECTIONQUEUEDEVENT for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 362, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 26:
				tsEventLog = "CONNECTIONALERTINGEVENT_CC for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 353, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 27:
				tsEventLog = "CONNECTIONDISCONNECTEDEVENT_CC for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 355, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 28:
				tsEventLog = "CONNECTIONFAILEDEVENT_CC for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 357, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 29:
				tsEventLog = "CONNECTIONUNKNOWNEVENT_CC for " + connTarget;
				addCallControlConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 363, connTarget.getTSDevice()
								.getNumberQueued(), tsEventLog, ev);
				break;
			case 56:
				tsEventLog = "CONNECTIONINPROGRESSEVENT_CC for " + connTarget;
				addCallCenterConnectionEvent(cause, metaCode, metaEvent,
						connTarget, privateData, 319, tsEventLog, ev);
				break;
			case 30:
				tsEventLog = "TERMINALCONNECTIONTALKINGEVENT for " + connTarget;
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 369, tsEventLog, ev);
				break;
			case 31:
				tsEventLog = "TERMINALCONNECTIONHELDEVENT for " + connTarget;
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 366, tsEventLog, ev);
				break;
			case 32:
				tsEventLog = "TERMINALCONNECTIONBRIDGEDEVENT for " + connTarget;
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 364, tsEventLog, ev);
				break;
			case 33:
				tsEventLog = "TERMINALCONNECTIONINUSEEVENT for " + connTarget;
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 367, tsEventLog, ev);
				break;
			case 34:
				tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT_CC for "
						+ connTarget;
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 365, tsEventLog, ev);
				break;
			case 35:
				tsEventLog = "TERMINALCONNECTIONRINGINGEVENT_CC for "
						+ connTarget;
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 368, tsEventLog, ev);
				break;
			case 36:
				tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT_CC for "
						+ connTarget;
				addCallControlTerminalConnectionEvent(cause, metaCode,
						metaEvent, connTarget, privateData, 370, tsEventLog, ev);
				break;
			case 54:
				tsEventLog = "TRUNKVALIDEVENT for " + trkTarget;
				addCallCenterEvent(cause, metaCode, metaEvent, trkTarget,
						privateData, 317, tsEventLog, ev);
				break;
			case 55:
				tsEventLog = "TRUNKINVALIDEVENT for " + trkTarget;
				addCallCenterEvent(cause, metaCode, metaEvent, trkTarget,
						privateData, 318, tsEventLog, ev);
				break;
			case 57:
				String digits = callTarget.getDigits();
				int i;
				for (i = 0; i < digits.length(); ++i) {
					tsEventLog = "CALLDTMFEVENT for " + callTarget;
					PrivateDataCallEventImpl privEvent = new PrivateDataCallEventImpl(
							new PrivateDtmfEventImpl(digits.charAt(i)), ev
									.getEventTarget(), cause, metaEvent);
					checkAndAddPrivateEvent(ev.getEventTarget(), privEvent,
							metaEvent);
				}
				break;
			case 9999:
				params = createCallParams(callTarget, cause, metaCode, ev
						.getPrivateData(), ev);
				PrivateDataCallEventImpl privEvent = new PrivateDataCallEventImpl(
						params.getPrivateData(), params.getCall(), cause,
						metaEvent);
				checkAndAddPrivateEvent(ev.getEventTarget(), privEvent,
						metaEvent);
			}
		}

		synchronized (listenerEventList) {
			if (listenerEventList.size() == 0) {
				log.debug("no events to send to " + observer);
				return;
			}

			if ((nextMetaEventIndex < listenerEventList.size())
					&& (metaStartAdded)) {
				listenerEventList.add(metaEventPair[1]);
				metaStartAdded = false;
			}
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

		int nextMetaEventIndex = eventList.size();

		TSEvent ev = null;
		TSCall callTarget = null;
		TSConnection connTarget = null;
		TSTrunk trkTarget = null;
		Object privateData = null;
		log.debug("meta event BEGIN: cause (" + cause + ") metaCode ("
				+ metaCode + ")" + " for " + observer);

		for (int i = 0; i < _eventList.size(); ++i) {
			ev = (TSEvent) _eventList.elementAt(i);
			if (ev.getEventTarget() instanceof TSCall) {
				callTarget = (TSCall) ev.getEventTarget();
			} else if (ev.getEventTarget() instanceof TSTrunk) {
				trkTarget = (TSTrunk) ev.getEventTarget();
			} else {
				connTarget = (TSConnection) ev.getEventTarget();
			}

			privateData = ev.getPrivateData();

			switch (ev.getEventType()) {
			case 4:
				tsEventLog = "CALLACTIVEEVENT for " + callTarget;

				addObserverEvent(new TsapiCallActiveEvent(createCallParams(
						callTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 5:
				tsEventLog = "CALLINVALIDEVENT for " + callTarget;

				addObserverEvent(new TsapiCallInvalidEvent(createCallParams(
						callTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 6:
				tsEventLog = "CONNECTIONCREATEDEVENT for " + connTarget;

				addObserverEvent(new TsapiConnCreatedEvent(createConnParams(
						connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 7:
				tsEventLog = "CONNECTIONCONNECTEDEVENT for " + connTarget;

				addObserverEvent(new TsapiConnConnectedEvent(createConnParams(
						connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 8:
				tsEventLog = "CONNECTIONINPROGRESSEVENT for " + connTarget;

				addObserverEvent(new TsapiConnInProgressEvent(createConnParams(
						connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 9:
				tsEventLog = "CONNECTIONALERTINGEVENT for " + connTarget;

				addObserverEvent(new TsapiConnAlertingEvent(createConnParams(
						connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 10:
				tsEventLog = "CONNECTIONDISCONNECTEDEVENT for " + connTarget;

				addObserverEvent(new TsapiConnDisconnectedEvent(
						createConnParams(connTarget, cause, metaCode,
								privateData, ev)), tsEventLog);

				break;
			case 11:
				tsEventLog = "CONNECTIONFAILEDEVENT for " + connTarget;

				addObserverEvent(new TsapiConnFailedEvent(createConnParams(
						connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 12:
				tsEventLog = "CONNECTIONUNKNOWNEVENT for " + connTarget;

				addObserverEvent(new TsapiConnUnknownEvent(createConnParams(
						connTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 13:
				tsEventLog = "TERMINALCONNECTIONCREATEDEVENT for " + connTarget;

				addObserverEvent(new TsapiTermConnCreatedEvent(
						createTermConnParams(connTarget, cause, metaCode,
								privateData, ev)), tsEventLog);

				break;
			case 14:
				tsEventLog = "TERMINALCONNECTIONACTIVEEVENT for " + connTarget;

				addObserverEvent(new TsapiTermConnActiveEvent(
						createTermConnParams(connTarget, cause, metaCode,
								privateData, ev)), tsEventLog);

				break;
			case 15:
				tsEventLog = "TERMINALCONNECTIONRINGINGEVENT for " + connTarget;

				addObserverEvent(new TsapiTermConnRingingEvent(
						createTermConnParams(connTarget, cause, metaCode,
								privateData, ev)), tsEventLog);

				break;
			case 16:
				tsEventLog = "TERMINALCONNECTIONPASSIVEEVENT for " + connTarget;

				addObserverEvent(new TsapiTermConnPassiveEvent(
						createTermConnParams(connTarget, cause, metaCode,
								privateData, ev)), tsEventLog);

				break;
			case 17:
				tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT for " + connTarget;

				addObserverEvent(new TsapiTermConnDroppedEvent(
						createTermConnParams(connTarget, cause, metaCode,
								privateData, ev)), tsEventLog);

				break;
			case 18:
				tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT for " + connTarget;

				addObserverEvent(new TsapiTermConnUnknownEvent(
						createTermConnParams(connTarget, cause, metaCode,
								privateData, ev)), tsEventLog);

				break;
			case 19:
				tsEventLog = "CONNECTIONOFFEREDEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnOfferedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnOfferedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnOfferedEvent(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 20:
				tsEventLog = "CONNECTIONDIALINGEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnDialingEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnDialingEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnDialingEvent(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 21:
				tsEventLog = "CONNECTIONESTABLISHEDEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnEstablishedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnEstablishedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnEstablishedEvent(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 22:
				tsEventLog = "CONNECTIONNETWORKREACHEDEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnNetworkReachedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnNetworkReachedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnNetworkReachedEvent(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 23:
				tsEventLog = "CONNECTIONNETWORKALERTINGEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnNetworkAlertingEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnNetworkAlertingEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnNetworkAlertingEvent(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 24:
				tsEventLog = "CONNECTIONINITIATEDEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnInitiatedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnInitiatedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnInitiatedEvent(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 25:
				tsEventLog = "CONNECTIONQUEUEDEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnQueuedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev), connTarget.getTSDevice()
									.getNumberQueued()), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnQueuedEventImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev), connTarget.getTSDevice()
									.getNumberQueued()), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnQueuedEvent(createConnParams(
							connTarget, cause, metaCode, privateData, ev),
							connTarget.getTSDevice().getNumberQueued()),
							tsEventLog);
				}

				break;
			case 26:
				tsEventLog = "CONNECTIONALERTINGEVENT_CC for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnAlertingEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnAlertingEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnAlertingEventCC(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 27:
				tsEventLog = "CONNECTIONDISCONNECTEDEVENT_CC for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnDisconnectedEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnDisconnectedEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnDisconnectedEventCC(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 28:
				tsEventLog = "CONNECTIONFAILEDEVENT_CC for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnFailedEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnFailedEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnFailedEventCC(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 29:
				tsEventLog = "CONNECTIONUNKNOWNEVENT_CC for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnUnknownEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnUnknownEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnUnknownEventCC(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 56:
				tsEventLog = "CONNECTIONINPROGRESSEVENT_CC for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5ConnInProgressEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentConnInProgressEventCCImpl(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiConnInProgressEventCC(
							createConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 30:
				tsEventLog = "TERMINALCONNECTIONTALKINGEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5TermConnTalkingEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentTermConnTalkingEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiTermConnTalkingEvent(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 31:
				tsEventLog = "TERMINALCONNECTIONHELDEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5TermConnHeldEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentTermConnHeldEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiTermConnHeldEvent(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 32:
				tsEventLog = "TERMINALCONNECTIONBRIDGEDEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5TermConnBridgedEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentTermConnBridgedEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiTermConnBridgedEvent(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 33:
				tsEventLog = "TERMINALCONNECTIONINUSEEVENT for " + connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5TermConnInUseEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentTermConnInUseEventImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiTermConnInUseEvent(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 34:
				tsEventLog = "TERMINALCONNECTIONDROPPEDEVENT_CC for "
						+ connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5TermConnDroppedEventCCImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentTermConnDroppedEventCCImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiTermConnDroppedEventCC(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 35:
				tsEventLog = "TERMINALCONNECTIONRINGINGEVENT_CC for "
						+ connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5TermConnRingingEventCCImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentTermConnRingingEventCCImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiTermConnRingingEventCC(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 36:
				tsEventLog = "TERMINALCONNECTIONUNKNOWNEVENT_CC for "
						+ connTarget;

				if (provider.isLucentV5()) {
					addObserverEvent(new LucentV5TermConnUnknownEventCCImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else if (provider.isLucent()) {
					addObserverEvent(new LucentTermConnUnknownEventCCImpl(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				} else {
					addObserverEvent(new TsapiTermConnUnknownEventCC(
							createTermConnParams(connTarget, cause, metaCode,
									privateData, ev)), tsEventLog);
				}

				break;
			case 54:
				tsEventLog = "TRUNKVALIDEVENT for " + trkTarget;

				addObserverEvent(new TsapiTrunkValidEv(createCallParams(
						trkTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 55:
				tsEventLog = "TRUNKINVALIDEVENT for " + trkTarget;

				addObserverEvent(new TsapiTrunkInvalidEv(createCallParams(
						trkTarget, cause, metaCode, privateData, ev)),
						tsEventLog);

				break;
			case 57:
				String digits = callTarget.getDigits();
				for (int strIndex = 0; strIndex < digits.length(); ++strIndex) {
					tsEventLog = "CALLDTMFEVENT for " + callTarget;

					addObserverEvent(new TsapiTermConnDTMFEvent(
							createCallParams(callTarget, cause, metaCode,
									privateData, ev), digits.charAt(strIndex)),
							tsEventLog);
				}

				break;
			case 9999:
				tsEventLog = "PRIVATEEVENT for " + callTarget;

				addObserverEvent(new TsapiPrivateCallEvent(createCallParams(
						callTarget, cause, metaCode, privateData, ev)),
						tsEventLog);
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

	public void deleteReference(Object observed, boolean isTerminal, int cause,
			Object privateData) {
		if (observer != null) {
			log
					.debug("Getting TsapiCallMonitor lock to deliver deleteReference events for monitor "
							+ observer);
		} else {
			log
					.debug("Getting TsapiCallMonitor lock to deliver deleteReference events for monitor "
							+ callListener);
		}
		deleteReferenceInternal(observed, isTerminal, cause, privateData);
	}

	private synchronized void deleteReferenceInternal(Object observed,
			boolean isTerminal, int cause, Object privateData) {
		String tsEventLog = null;
		reference -= 1L;

		if (observer != null) {
			log.debug("meta event BEGIN: cause (" + cause + ") metaCode ("
					+ 136 + ")" + " for " + observer);
		}
		tsEventLog = "OBSERVATIONENDEDEVENT for observed: " + observed;

		TSCall targetCall = (observed instanceof TSCall) ? (TSCall) observed
				: null;
		if ((targetCall != null) && (!targetCall.checkForMonitors())) {
			targetCall.setNeedSnapshot(true);
		}

		if (observed != null) {
			observed = TsapiCreateObject.getTsapiObject(observed, !isTerminal);
		}

		int nextMetaEventIndex = 0;

		if (observer != null) {
			synchronized (eventList) {
				nextMetaEventIndex = eventList.size();
				CallEventParams params = createCallParams(targetCall, cause,
						136, privateData, null);

				addObserverEvent(new TsapiCallObservationEndedEvent(params,
						observed), tsEventLog);

				((TsapiObserverEvent) eventList.elementAt(nextMetaEventIndex))
						.setNewMetaEventFlag();

				if ((targetCall != null) && (privateData != null)) {
					tsEventLog = "PRIVATEEVENT for " + targetCall;
					addObserverEvent(new TsapiPrivateCallEvent(
							createCallParams(targetCall, cause, 136,
									privateData, null)), tsEventLog);
				}

				log.debug("meta event END for " + observer + " eventList size="
						+ eventList.size());
			}
		} else if (callListener != null) {
			CallEventParams callEvParams = createCallParams(targetCall, cause,
					-1, privateData, null);
			MetaEvent[] metaEvents = CallEventUtil.getListenerMetaObject(cause,
					callEvParams, false);
			synchronized (listenerEventList) {
				listenerEventList.add(metaEvents[0]);
				listenerEventList.add(new CallEventImpl(callEvParams,
						metaEvents[0], 103));
				if ((targetCall != null) && (privateData != null)
						&& (callListener instanceof PrivateDataCallListener)) {
					tsEventLog = "PRIVATEEVENT for " + targetCall
							+ "for listener " + callListener;
					listenerEventList.add(new PrivateDataCallEventImpl(
							privateData, targetCall, cause, metaEvents[0]));
					log.debug(tsEventLog);
				}
				listenerEventList.add(metaEvents[1]);
			}
		}

		if (reference <= 0L) {
			provider.removeCallMonitorThread(this);
		}

		JtapiEventThreadManager.execute(this);
	}

	public void deliverEvents(Vector<TSEvent> _eventList, int cause,
			boolean snapshot) {
		if ((_eventList == null) || (_eventList.size() == 0)) {
			return;
		}
		log.debug("Getting TsapiCallMonitor lock to deliver events for call "
				+ ((observer == null) ? "listener " : "observer ")
				+ ((observer == null) ? callListener : observer));
		synchronized (_eventList) {
			deliverEventsInternal(_eventList, cause, snapshot);
		}
	}

	private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList,
			int cause, boolean snapshot) {
		if (observer != null) {
			createObserverEvents(_eventList, cause, snapshot);
		} else {
			createListenerEvents(_eventList, cause, snapshot);
		}
		JtapiEventThreadManager.execute(this);
	}

	private void deliverEventToCallback(Event callEvent) {
		switch (callEvent.getID()) {
		case 101:
			callListener.callActive((CallEvent) callEvent);
			break;
		case 102:
			callListener.callInvalid((CallEvent) callEvent);
			break;
		case 106:
			((ConnectionListener) callListener)
					.connectionCreated((ConnectionEvent) callEvent);
			break;
		case 105:
			((ConnectionListener) callListener)
					.connectionConnected((ConnectionEvent) callEvent);
			break;
		case 109:
			((ConnectionListener) callListener)
					.connectionInProgress((ConnectionEvent) callEvent);
			break;
		case 104:
			((ConnectionListener) callListener)
					.connectionAlerting((ConnectionEvent) callEvent);
			break;
		case 107:
			((ConnectionListener) callListener)
					.connectionDisconnected((ConnectionEvent) callEvent);
			break;
		case 108:
			((ConnectionListener) callListener)
					.connectionFailed((ConnectionEvent) callEvent);
			break;
		case 110:
			((ConnectionListener) callListener)
					.connectionUnknown((ConnectionEvent) callEvent);
			break;
		case 116:
			((TerminalConnectionListener) callListener)
					.terminalConnectionCreated((TerminalConnectionEvent) callEvent);
			break;
		case 115:
			((TerminalConnectionListener) callListener)
					.terminalConnectionActive((TerminalConnectionEvent) callEvent);
			break;
		case 119:
			((TerminalConnectionListener) callListener)
					.terminalConnectionRinging((TerminalConnectionEvent) callEvent);
			break;
		case 118:
			((TerminalConnectionListener) callListener)
					.terminalConnectionPassive((TerminalConnectionEvent) callEvent);
			break;
		case 117:
			((TerminalConnectionListener) callListener)
					.terminalConnectionDropped((TerminalConnectionEvent) callEvent);
			break;
		case 120:
			((TerminalConnectionListener) callListener)
					.terminalConnectionUnknown((TerminalConnectionEvent) callEvent);
			break;
		case 361:
			((CallControlConnectionListener) callListener)
					.connectionOffered((CallControlConnectionEvent) callEvent);
			break;
		case 354:
			((CallControlConnectionListener) callListener)
					.connectionDialing((CallControlConnectionEvent) callEvent);
			break;
		case 356:
			((CallControlConnectionListener) callListener)
					.connectionEstablished((CallControlConnectionEvent) callEvent);
			break;
		case 360:
			((CallControlConnectionListener) callListener)
					.connectionNetworkReached((CallControlConnectionEvent) callEvent);
			break;
		case 359:
			((CallControlConnectionListener) callListener)
					.connectionNetworkAlerting((CallControlConnectionEvent) callEvent);
			break;
		case 358:
			((CallControlConnectionListener) callListener)
					.connectionInitiated((CallControlConnectionEvent) callEvent);
			break;
		case 362:
			((CallControlConnectionListener) callListener)
					.connectionQueued((CallControlConnectionEvent) callEvent);
			break;
		case 353:
			((CallControlConnectionListener) callListener)
					.connectionAlerting((CallControlConnectionEvent) callEvent);
			break;
		case 355:
			((CallControlConnectionListener) callListener)
					.connectionDisconnected((CallControlConnectionEvent) callEvent);
			break;
		case 357:
			((CallControlConnectionListener) callListener)
					.connectionFailed((CallControlConnectionEvent) callEvent);
			break;
		case 363:
			((CallControlConnectionListener) callListener)
					.connectionUnknown((CallControlConnectionEvent) callEvent);
			break;
		case 319:
			((ConnectionListener) callListener)
					.connectionInProgress((ConnectionEvent) callEvent);
			break;
		case 369:
			((CallControlTerminalConnectionListener) callListener)
					.terminalConnectionTalking((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 366:
			((CallControlTerminalConnectionListener) callListener)
					.terminalConnectionHeld((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 364:
			((CallControlTerminalConnectionListener) callListener)
					.terminalConnectionBridged((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 367:
			((CallControlTerminalConnectionListener) callListener)
					.terminalConnectionInUse((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 365:
			((CallControlTerminalConnectionListener) callListener)
					.terminalConnectionDropped((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 368:
			((CallControlTerminalConnectionListener) callListener)
					.terminalConnectionRinging((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 370:
			((CallControlTerminalConnectionListener) callListener)
					.terminalConnectionUnknown((CallControlTerminalConnectionEvent) callEvent);
			break;
		case 318:
			((CallCenterCallListener) callListener)
					.trunkInvalid((CallCenterTrunkEvent) callEvent);
			break;
		case 317:
			((CallCenterCallListener) callListener)
					.trunkValid((CallCenterTrunkEvent) callEvent);
			break;
		case 601:
			((PrivateDataCallListener) callListener)
					.callPrivateData((PrivateDataEvent) callEvent);
			break;
		case 210:
			callListener.singleCallMetaProgressStarted((MetaEvent) callEvent);
			break;
		case 211:
			callListener.singleCallMetaProgressEnded((MetaEvent) callEvent);
			break;
		case 212:
			callListener.singleCallMetaSnapshotStarted((MetaEvent) callEvent);
			break;
		case 213:
			callListener.singleCallMetaSnapshotEnded((MetaEvent) callEvent);
			break;
		case 200:
			callListener.multiCallMetaMergeStarted((MetaEvent) callEvent);
			break;
		case 201:
			callListener.multiCallMetaMergeEnded((MetaEvent) callEvent);
			break;
		case 202:
			callListener.multiCallMetaTransferStarted((MetaEvent) callEvent);
			break;
		case 203:
			callListener.multiCallMetaTransferEnded((MetaEvent) callEvent);
			break;
		case 103:
			callListener.callEventTransmissionEnded((CallEvent) callEvent);
		}
	}

	private void deliverListenerEvents() {
		synchronized (listenerEventList) {
			for (Event event : listenerEventList) {
				deliverEventToCallback(event);
			}
			listenerEventList.clear();
		}
	}

	public void dump(String indent) {
		log.trace(indent + "***** TsapiCallMonitor DUMP *****");
		log.trace(indent + "TsapiCallMonitor: " + this);
		log.trace(indent + "observer: " + observer);
		log.trace(indent + "***** TsapiCallMonitor DUMP END *****");
	}

	public CallListener getListener() {
		return callListener;
	}

	public CallObserver getObserver() {
		return observer;
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
		for (int i = 0; i < _eventList.size(); ++i) {
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
			case 57:
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

	public boolean isVDN() {
		return isVDN;
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		if (observer != null) {
			synchronized (syncObject) {
				log
						.debug("TP thread woke up: Locked callChangedEvent, attempting to Lock TsapiCallMonitor for CallObserver "
								+ observer);

				CallEv[] eventArray = null;
				synchronized (this) {
					synchronized (eventList) {
						if (eventList.size() == 0) {
							log
									.debug("TsapiCallMonitor: events delivered by previous thread; no events to deliver in this thread");
							TsapiTrace.traceExit("run[]", this);
							return;
						}
						log
								.debug("TP thread Locked TsapiCallMonitor: removing events, itself for CallObserver "
										+ observer);

						eventArray = new CallEv[eventList.size()];
						eventList.copyInto(eventArray);
						eventList.clear();
					}
				}
				log
						.debug("TP thread Unlocked TsapiCallMonitor: calling callChangedEvent, delivering events for CallObserver "
								+ observer);
				try {
					observer.callChangedEvent(eventArray);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by callChangedEvent in "
							+ observer + " - " + e.getMessage(), e);
				}

				log
						.debug("TP thread returned from callChangedEvent, Unlocked callChangedEvent for CallObserver "
								+ observer);
			}

		} else {
			deliverListenerEvents();
		}
		TsapiTrace.traceExit("run[]", this);
	}

	public void setVDN(boolean isVDN) {
		this.isVDN = isVDN;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor JD-Core Version: 0.5.4
 */