package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;

final class UnsolicitedSnapshotCallConfHandler implements
		SnapshotCallExtraConfHandler {
	private static Logger log = Logger
			.getLogger(UnsolicitedSnapshotCallConfHandler.class);
	TSEventHandler eventHandler;
	int eventType;
	int cause;
	CSTAExtendedDeviceID subjectDeviceID;
	TSDevice subjectDevice;
	CSTAConnectionID connID;
	TSCall call;
	Object privateData;
	CSTAExtendedDeviceID callingDeviceID;
	CSTAExtendedDeviceID calledDeviceID;
	CSTAExtendedDeviceID lastRedirectionDeviceID;
	int connState;
	int termConnState;
	TSDevice connectionDevice;

	UnsolicitedSnapshotCallConfHandler(final TSEventHandler _eventHandler,
			final int _eventType, final int _cause,
			final CSTAExtendedDeviceID _subjectDeviceID,
			final TSDevice _subjectDevice, final CSTAConnectionID _connID,
			final TSCall _call, final Object _privateData,
			final CSTAExtendedDeviceID _callingDeviceID,
			final CSTAExtendedDeviceID _calledDeviceID,
			final CSTAExtendedDeviceID _lastRedirectionDeviceID,
			final int _connState, final int _termConnState,
			final TSDevice _connectionDevice) {
		eventHandler = _eventHandler;
		eventType = _eventType;
		cause = _cause;
		subjectDeviceID = _subjectDeviceID;
		subjectDevice = _subjectDevice;
		connID = _connID;
		call = _call;
		privateData = _privateData;
		callingDeviceID = _callingDeviceID;
		calledDeviceID = _calledDeviceID;
		lastRedirectionDeviceID = _lastRedirectionDeviceID;
		connState = _connState;
		termConnState = _termConnState;
		connectionDevice = _connectionDevice;
	}

	public Object handleConf(final boolean rc, Vector<TSEvent> eventList,
			final Object _privateData) {
		if (call.getTSState() == 34)
			return null;

		if (call.getNeedRedoSnapshotCall()) {
			call.setNeedRedoSnapshotCall(false);
			UnsolicitedSnapshotCallConfHandler.log.info("redo snapshot call");
			call.doSnapshot(connID, this, false);
			return null;
		}

		call.setSnapshotCallConfPending(false);

		UnsolicitedSnapshotCallConfHandler.log
				.debug("UnsolicitedSnapshotCallConfHandler " + this
						+ " handling conf for call " + call + ", eventType "
						+ eventType + ", subjectDevice " + subjectDevice
						+ ", connID " + connID + ", rc " + rc + ", cause "
						+ cause + ", privateData " + privateData
						+ ", provider " + call.getTSProviderImpl());

		if (privateData == null)
			privateData = _privateData;

		if (eventList == null)
			eventList = new Vector<TSEvent>();
		TSConnection connection;
		int oldConnState;
		int oldTermConnState;
		if (rc) {
			if (eventList.size() == 0)
				call.getSnapshot(eventList);

			connection = eventHandler.provider.createTerminalConnection(connID,
					subjectDevice, null, connectionDevice);
			oldConnState = connection.getCallControlConnState();
			oldTermConnState = connection.getCallControlTermConnState();

			final boolean isTermConn = connection.isTerminalConnection();
			TSConnection connToCheck = null;
			TSConnection tcToCheck = null;

			if (isTermConn) {
				connToCheck = connection.getTSConn();
				tcToCheck = connection;
			} else
				connToCheck = connection;

			TSEvent nEv = null;

			boolean foundConn = false;
			boolean foundTC = false;

			for (int j = 0; j < eventList.size(); ++j) {
				nEv = eventList.elementAt(j);

				if (connToCheck != nEv.getEventTarget()
						|| nEv.getEventType() != 6)
					continue;
				foundConn = true;
				if (connState != 83 || oldConnState == 83
						|| connToCheck.getCallControlConnState() == 83)
					break;

				UnsolicitedSnapshotCallConfHandler.log
						.debug("adding ConnAlertingEv to events derived from SnapshotCallConf for call "
								+ call);

				if (j + 1 >= eventList.size()) {
					eventList.addElement(new TSEvent(9, connToCheck));
					eventList.addElement(new TSEvent(26, connToCheck));
					break;
				}

				eventList.insertElementAt(new TSEvent(9, connToCheck), j + 1);
				eventList.insertElementAt(new TSEvent(26, connToCheck), j + 1);
				break;
			}

			if (isTermConn)
				for (int j = 0; j < eventList.size(); ++j) {
					nEv = eventList.elementAt(j);

					if (tcToCheck != nEv.getEventTarget()
							|| nEv.getEventType() != 13)
						continue;
					foundTC = true;
					if (termConnState != 97 || oldTermConnState == 97
							|| tcToCheck.getCallControlTermConnState() == 97)
						break;

					if (j + 1 >= eventList.size()) {
						eventList.addElement(new TSEvent(15, tcToCheck));
						eventList.addElement(new TSEvent(35, tcToCheck));
						break;
					}

					eventList
							.insertElementAt(new TSEvent(15, tcToCheck), j + 1);
					eventList
							.insertElementAt(new TSEvent(35, tcToCheck), j + 1);
					break;
				}

			if (isTermConn) {
				if (!foundConn) {
					connToCheck.setConnectionState(connState, null);
					if (!foundTC)
						tcToCheck.setTermConnState(termConnState, null);
					final Vector<TSEvent> snapEventList = new Vector<TSEvent>();
					connToCheck.getSnapshot(snapEventList);
					int index = 0;
					if (eventList.size() == 0)
						eventList = snapEventList;
					else {
						if (eventList.elementAt(0).getEventType() == 4)
							++index;
						for (int i = 0; i < snapEventList.size(); ++i) {
							eventList.insertElementAt(snapEventList
									.elementAt(i), index);
							++index;
						}
					}
				} else if (!foundTC) {
					tcToCheck.setTermConnState(termConnState, null);
					final Vector<TSEvent> snapEventList = new Vector<TSEvent>();
					tcToCheck.getSnapshot(snapEventList);
					int index = 0;
					if (eventList.size() == 0)
						eventList = snapEventList;
					else {
						if (eventList.elementAt(0).getEventType() == 4)
							++index;
						for (int i = 0; i < snapEventList.size(); ++i) {
							eventList.insertElementAt(snapEventList
									.elementAt(i), index);
							++index;
						}
					}
				}
			} else if (!foundConn) {
				connToCheck.setConnectionState(connState, null);
				final Vector<TSEvent> snapEventList = new Vector<TSEvent>();
				connToCheck.getSnapshot(snapEventList);
				int index = 0;
				if (eventList.size() == 0)
					eventList = snapEventList;
				else {
					if (eventList.elementAt(0).getEventType() == 4)
						++index;
					for (int i = 0; i < snapEventList.size(); ++i) {
						eventList.insertElementAt(snapEventList.elementAt(i),
								index);
						++index;
					}

				}

			}

			for (int j = 0; j < eventList.size(); ++j) {
				nEv = eventList.elementAt(j);

				if (connToCheck != nEv.getEventTarget()
						|| !shouldOverrideConnEventCSTACauseValue(nEv))
					continue;

				UnsolicitedSnapshotCallConfHandler.log
						.debug("overriding cause for TSEvent " + nEv + " ("
								+ nEv.getEventType() + ","
								+ nEv.getEventTarget() + " from eventType "
								+ eventType + " with new cause " + cause);

				nEv.setSnapshotCstaCause((short) cause);
			}
		} else {
			connection = eventHandler.provider.createTerminalConnection(connID,
					subjectDevice, eventList, connectionDevice);
			oldConnState = connection.getCallControlConnState();
			oldTermConnState = connection.getCallControlTermConnState();
			connection.setConnectionState(connState, eventList);
			connection.setTermConnState(termConnState, eventList);
		}

		eventHandler.finishConnEvents(null, eventType, cause, 110,
				subjectDeviceID, subjectDevice, connection, call, privateData,
				callingDeviceID, calledDeviceID, lastRedirectionDeviceID,
				connState, oldConnState, oldTermConnState, eventList);

		return privateData;
	}

	boolean shouldOverrideConnEventCSTACauseValue(final TSEvent eventToModify) {
		boolean result = false;

		switch (eventToModify.getEventType()) {
		case 6:
			result = eventToModify.getSnapshotCstaCause() == -1;
			break;
		case 9:
		case 23:
		case 26:
			result = eventType == 57;
			break;
		case 10:
		case 27:
			result = eventType == 56;
			break;
		case 7:
		case 21:
			result = eventType == 59;
			break;
		case 11:
		case 24:
		case 28:
			result = eventType == 60;
			break;
		case 22:
			result = eventType == 62;
			break;
		case 25:
			result = eventType == 64;
			break;
		case 8:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		default:
			result = false;
		}

		return result;
	}
}
