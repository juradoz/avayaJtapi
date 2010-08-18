package com.avaya.jtapi.tsapi.impl.core;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.csta1.CSTAConferenceCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAConnection;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTATransferCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentConferenceCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentTransferCallConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class ConfXferConfHandler implements ConfHandler {
	private static Logger log = Logger.getLogger(ConfXferConfHandler.class);
	TSCall call;
	TSCall otherCall;
	int pdu;
	TSConnection newConn;
	CSTAConnectionID newCall;

	ConfXferConfHandler(TSCall _call, TSCall _otherCall, int _pdu) {
		call = _call;
		otherCall = _otherCall;
		pdu = _pdu;
		newConn = null;
		newCall = null;
	}

	private void addOldCallParams(Vector<TSEvent> evList) {
		ArrayList<TSCall> oldCallList = new ArrayList<TSCall>();
		if (call != null) {
			oldCallList.add(call);
		}
		if (otherCall != null) {
			oldCallList.add(otherCall);
		}
		for (TSEvent ev : evList) {
			ev
					.setTransferredEventParams(new TransferredEventParams(
							oldCallList));
		}
	}

	TSConnection createNewCallConnectionTryToGetStateFromOtherCall(TSCall call,
			TSCall otherCall, CSTAConnectionID newConnID,
			Vector<TSEvent> priEventList, Vector<TSConnection> snapConnections) {
		TSConnection conn = null;

		TSDevice deviceToFind = call.getTSProviderImpl().createDevice(
				newConnID.getDeviceID(), newConnID);

		Vector<TSEvent> tempEventList = new Vector<TSEvent>();

		conn = call.getTSProviderImpl().createTerminalConnection(newConnID,
				deviceToFind, tempEventList, deviceToFind);

		int oldConnState = conn.getCallControlConnState();
		int oldTermConnState = conn.getCallControlTermConnState();

		if ((oldConnState == 89) || (oldTermConnState == 102)) {
			call.getTSProviderImpl().deleteConnectionFromHash(newConnID);
			conn = call.getTSProviderImpl().createTerminalConnection(newConnID,
					deviceToFind, tempEventList, deviceToFind);
		}

		TSConnection foundTSConn = (otherCall == null) ? null : otherCall
				.findTSConnectionForDevice(deviceToFind);

		if (foundTSConn != null) {
			for (int m = 0; m < tempEventList.size(); ++m) {
				priEventList.addElement(tempEventList.elementAt(m));
			}
			conn.setConnectionState(foundTSConn.getCallControlConnState(),
					priEventList);
			conn.setTermConnState(foundTSConn.getCallControlTermConnState(),
					priEventList);
		} else {
			if (call.getTSProviderImpl().getCapabilities().getSnapshotCallReq() != 0) {
				snapConnections.addElement(conn.getTSConn());
			}

			for (int m = 0; m < tempEventList.size(); ++m) {
				priEventList.addElement(tempEventList.elementAt(m));
			}
			conn.setConnectionState(91, priEventList);
			conn.setTermConnState(103, priEventList);
		}

		return conn;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != pdu)) {
			return;
		}

		if (call.getTSState() == 34) {
			return;
		}

		CSTAConnection[] connList = null;
		int cause = 0;

		switch (pdu) {
		case 12:
			newCall = ((CSTAConferenceCallConfEvent) event.getEvent())
					.getNewCall();
			connList = ((CSTAConferenceCallConfEvent) event.getEvent())
					.getConnList();
			cause = 207;
			if (event.getPrivData() instanceof LucentConferenceCallConfEvent) {
				call.setUCID(((LucentConferenceCallConfEvent) event
						.getPrivData()).getUcid());
			}
			break;
		case 52:
			newCall = ((CSTATransferCallConfEvent) event.getEvent())
					.getNewCall();
			connList = ((CSTATransferCallConfEvent) event.getEvent())
					.getConnList();
			cause = 212;
			if (event.getPrivData() instanceof LucentTransferCallConfEvent) {
				call
						.setUCID(((LucentTransferCallConfEvent) event
								.getPrivData()).getUcid());
			}
			break;
		case 90:
			if (event.getPrivData() instanceof LucentSingleStepConferenceCallConfEvent) {
				newCall = ((LucentSingleStepConferenceCallConfEvent) event
						.getPrivData()).getNewCall();
				connList = ((LucentSingleStepConferenceCallConfEvent) event
						.getPrivData()).getConnList();
				cause = 207;
				call.setUCID(((LucentSingleStepConferenceCallConfEvent) event
						.getPrivData()).getUcid());
			} else if (event.getPrivData() instanceof LucentSingleStepTransferCallConfEvent) {
				newCall = ((LucentSingleStepTransferCallConfEvent) event
						.getPrivData()).getTransferredCall();
				connList = null;
				cause = 212;
			} else {
				return;
			}

		}

		call.replyPriv = event.getPrivData();

		Vector<TSConnection> snapConnections = new Vector<TSConnection>();

		Vector<TSEvent> priEventList = new Vector<TSEvent>();

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		if (connList == null) {
			call.setCallID(newCall.getCallID());
			call = call.getHandOff();

			call.updateConnectionCallIDs(newCall.getCallID());

			if (call.getTSProviderImpl().getConnection(newCall) == null) {
				TSConnection sstDestConnection = createNewCallConnectionTryToGetStateFromOtherCall(
						call, otherCall, newCall, priEventList, snapConnections);

				if (!sstDestConnection.isTerminalConnection()) {
					call.addConnection(sstDestConnection, eventList);
				}
			}
		} else if ((connList != null) && (connList.length > 0)) {
			call.setCallID(newCall.getCallID());
			call = call.getHandOff();

			TSDevice device = null;
			Vector<TSConnection> newConnections = new Vector<TSConnection>();
			TSConnection conn = null;

			TSConnection foundTSConn = null;

			for (int i = 0; i < connList.length; ++i) {
				device = call.getTSProviderImpl().createDevice(
						connList[i].getStaticDevice(), connList[i].getParty());
				if (device == null) {
					continue;
				}
				foundTSConn = call.findTSConnectionForDevice(device);
				if (foundTSConn != null) {
					try {
						foundTSConn.setConnID(connList[i].getParty());
					} catch (TsapiPlatformException e) {
						log
								.error("TSCall.handleConf() caught TsapiPlatformException from setConnID() while processing connList, i="
										+ i
										+ ", party="
										+ connList[i].getParty());
						log.error(e.getMessage(), e);
						log.trace("Dumping call (" + call + "):");
						call.dump("   ");
						log.trace("Dumping foundTSConn (" + foundTSConn + "):");
						foundTSConn.dump("   ");
						log.trace("Dumping call provider ("
								+ call.getTSProviderImpl() + "):");
						call.getTSProviderImpl().dump("   ");

						throw e;
					}

					newConnections.addElement(foundTSConn);

					if (foundTSConn.isTerminalConnection()) {
						int tcs = foundTSConn.getCallControlTermConnState();
						if (tcs == 96) {
							if (call.getTSProviderImpl().getCapabilities()
									.getSnapshotCallReq() != 0) {
								snapConnections.addElement(foundTSConn
										.getTSConn());
							}

							foundTSConn.setConnectionState(91, null);
							foundTSConn.setTermConnState(103, null);
						}

					} else {
						int cs = foundTSConn.getCallControlConnState();
						if (cs == 80) {
							if (call.getTSProviderImpl().getCapabilities()
									.getSnapshotCallReq() != 0) {
								snapConnections.addElement(foundTSConn
										.getTSConn());
							}

							foundTSConn.setConnectionState(91, null);
							foundTSConn.setTermConnState(103, null);
						}

					}

				} else {
					conn = createNewCallConnectionTryToGetStateFromOtherCall(
							call, otherCall, connList[i].getParty(),
							priEventList, snapConnections);

					newConnections.addElement(conn);
				}

				if (!connList[i].getParty().equals(newCall)) {
					continue;
				}
				newConn = conn;
			}

			call.replaceConnections(newConnections, eventList);
		}

		for (int m = 0; m < priEventList.size(); ++m) {
			eventList.addElement(priEventList.elementAt(m));
		}

		Vector<TSEvent> otherEventList = new Vector<TSEvent>();
		if (otherCall != null) {
			otherCall.delayVDNremoveCallFromDomain = true;
			otherCall.setState(34, otherEventList);
			otherCall.delayVDNremoveCallFromDomain = false;
		}

		if (otherEventList.size() > 0) {
			Vector<TsapiCallMonitor> observers = otherCall.getObservers();
			addOldCallParams(otherEventList);
			for (int j = 0; j < observers.size(); ++j) {
				TsapiCallMonitor callback = observers.elementAt(j);
				callback.deliverEvents(otherEventList, cause, false);
			}
		}
		if (otherCall != null) {
			call.moveStuff(otherCall);
			otherCall.setStateForVDN();

			otherCall.endCVDObservers(cause, null);
			otherCall.endNonCVDObservers(cause);
			otherCall.staleObsCleanup(cause);
		}

		if (snapConnections.size() > 0) {
			call.setNeedSnapshot(true);

			SnapshotCallExtraConfHandler handler = new XferConfSnapshotCallConfHandler(
					call, cause, null, snapConnections);

			call.doSnapshot((snapConnections.elementAt(0)).getConnID(),
					handler, false);
		}

		if (eventList.size() <= 0) {
			return;
		}
		Vector<TsapiCallMonitor> observers = call.getObservers();
		addOldCallParams(eventList);
		for (int j = 0; j < observers.size(); ++j) {
			TsapiCallMonitor callback = observers.elementAt(j);
			callback.deliverEvents(eventList, cause, false);
		}
	}
}

