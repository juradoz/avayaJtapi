package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import java.util.Vector;
import org.apache.log4j.Logger;

final class UnsolicitedSnapshotCallConfHandler implements
		SnapshotCallExtraConfHandler {
	private static Logger log = Logger
			.getLogger(UnsolicitedSnapshotCallConfHandler.class);
	TSEventHandler eventHandler;
	int eventType;
	short cause;
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
	short csta3Cause;

	UnsolicitedSnapshotCallConfHandler(TSEventHandler _eventHandler,
			int _eventType, short _cause, short _csta3Cause,
			CSTAExtendedDeviceID _subjectDeviceID, TSDevice _subjectDevice,
			CSTAConnectionID _connID, TSCall _call, Object _privateData,
			CSTAExtendedDeviceID _callingDeviceID,
			CSTAExtendedDeviceID _calledDeviceID,
			CSTAExtendedDeviceID _lastRedirectionDeviceID, int _connState,
			int _termConnState, TSDevice _connectionDevice) {
		this.eventHandler = _eventHandler;
		this.eventType = _eventType;
		this.cause = _cause;
		this.csta3Cause = _csta3Cause;
		this.subjectDeviceID = _subjectDeviceID;
		this.subjectDevice = _subjectDevice;
		this.connID = _connID;
		this.call = _call;
		this.privateData = _privateData;
		this.callingDeviceID = _callingDeviceID;
		this.calledDeviceID = _calledDeviceID;
		this.lastRedirectionDeviceID = _lastRedirectionDeviceID;
		this.connState = _connState;
		this.termConnState = _termConnState;
		this.connectionDevice = _connectionDevice;
	}

	public Object handleConf(boolean rc, Vector<TSEvent> eventList,
			Object _privateData) {
		if (this.call.getTSState() == 34) {
			return null;
		}

		if (this.call.getNeedRedoSnapshotCall()) {
			this.call.setNeedRedoSnapshotCall(false);
			log.info("redo snapshot call");
			this.call.doSnapshot(this.connID, this, false);
			return null;
		}

		this.call.setSnapshotCallConfPending(false);

		log.debug("UnsolicitedSnapshotCallConfHandler " + this
				+ " handling conf for call " + this.call + ", eventType "
				+ this.eventType + ", subjectDevice " + this.subjectDevice
				+ ", connID " + this.connID + ", rc " + rc + ", cause "
				+ this.cause + ", privateData " + this.privateData
				+ ", provider " + this.call.getTSProviderImpl());

		if (this.privateData == null) {
			this.privateData = _privateData;
		}

		if (eventList == null) {
			eventList = new Vector<TSEvent>();
		}
		TSConnection connection;
		int oldConnState;
		int oldTermConnState;
		if (rc) {
			if (eventList.size() == 0) {
				this.call.getSnapshot(eventList);
			}

			connection = this.eventHandler.provider
					.createTerminalConnection(this.connID, this.subjectDevice,
							null, this.connectionDevice);
			oldConnState = connection.getCallControlConnState();
			oldTermConnState = connection.getCallControlTermConnState();

			boolean isTermConn = connection.isTerminalConnection();
			TSConnection connToCheck = null;
			TSConnection tcToCheck = null;

			if (isTermConn) {
				connToCheck = connection.getTSConn();
				tcToCheck = connection;
			} else {
				connToCheck = connection;
			}

			TSEvent nEv = null;

			boolean foundConn = false;
			boolean foundTC = false;

			for (int j = 0; j < eventList.size(); j++) {
				nEv = (TSEvent) eventList.elementAt(j);

				if ((connToCheck == nEv.getEventTarget())
						&& (nEv.getEventType() == 6)) {
					foundConn = true;
					if ((this.connState != 83) || (oldConnState == 83)
							|| (connToCheck.getCallControlConnState() == 83)) {
						break;
					}
					log.debug("adding ConnAlertingEv to events derived from SnapshotCallConf for call "
							+ this.call);

					if (j + 1 >= eventList.size()) {
						eventList.addElement(new TSEvent(9, connToCheck));
						eventList.addElement(new TSEvent(26, connToCheck));
						break;
					}

					eventList.insertElementAt(new TSEvent(9, connToCheck),
							j + 1);
					eventList.insertElementAt(new TSEvent(26, connToCheck),
							j + 1);
					break;
				}

			}

			if (isTermConn) {
				for (int j = 0; j < eventList.size(); j++) {
					nEv = (TSEvent) eventList.elementAt(j);

					if ((tcToCheck == nEv.getEventTarget())
							&& (nEv.getEventType() == 13)) {
						foundTC = true;
						if ((this.termConnState != 97)
								|| (oldTermConnState == 97)
								|| (tcToCheck.getCallControlTermConnState() == 97)) {
							break;
						}
						if (j + 1 >= eventList.size()) {
							eventList.addElement(new TSEvent(15, tcToCheck));
							eventList.addElement(new TSEvent(35, tcToCheck));
							break;
						}

						eventList.insertElementAt(new TSEvent(15, tcToCheck),
								j + 1);
						eventList.insertElementAt(new TSEvent(35, tcToCheck),
								j + 1);
						break;
					}

				}

			}

			if (isTermConn) {
				if (!foundConn) {
					connToCheck.setConnectionState(this.connState, null);
					if (!foundTC) {
						tcToCheck.setTermConnState(this.termConnState, null);
					}
					Vector<TSEvent> snapEventList = new Vector<TSEvent>();
					connToCheck.getSnapshot(snapEventList);
					int index = 0;
					if (eventList.size() == 0) {
						eventList = snapEventList;
					} else {
						if (((TSEvent) eventList.elementAt(0)).getEventType() == 4) {
							index++;
						}
						for (int i = 0; i < snapEventList.size(); i++) {
							eventList.insertElementAt(
									snapEventList.elementAt(i), index);
							index++;
						}
					}
				} else if (!foundTC) {
					tcToCheck.setTermConnState(this.termConnState, null);
					Vector<TSEvent> snapEventList = new Vector<TSEvent>();
					tcToCheck.getSnapshot(snapEventList);
					int index = 0;
					if (eventList.size() == 0) {
						eventList = snapEventList;
					} else {
						if (((TSEvent) eventList.elementAt(0)).getEventType() == 4) {
							index++;
						}
						for (int i = 0; i < snapEventList.size(); i++) {
							eventList.insertElementAt(
									snapEventList.elementAt(i), index);
							index++;
						}
					}
				}
			} else if (!foundConn) {
				connToCheck.setConnectionState(this.connState, null);
				Vector<TSEvent> snapEventList = new Vector<TSEvent>();
				connToCheck.getSnapshot(snapEventList);
				int index = 0;
				if (eventList.size() == 0) {
					eventList = snapEventList;
				} else {
					if (((TSEvent) eventList.elementAt(0)).getEventType() == 4) {
						index++;
					}
					for (int i = 0; i < snapEventList.size(); i++) {
						eventList.insertElementAt(snapEventList.elementAt(i),
								index);
						index++;
					}

				}

			}

			for (int j = 0; j < eventList.size(); j++) {
				nEv = (TSEvent) eventList.elementAt(j);

				if (connToCheck == nEv.getEventTarget()) {
					if (shouldOverrideConnEventCSTACauseValue(nEv)) {
						log.debug("overriding cause for TSEvent " + nEv + " ("
								+ nEv.getEventType() + ","
								+ nEv.getEventTarget() + " from eventType "
								+ this.eventType + " with new cause "
								+ this.cause);

						nEv.setSnapshotCstaCause(this.cause);
						nEv.setSnapshotCsta3Cause(this.csta3Cause);
					}
				}
			}
		} else {
			connection = this.eventHandler.provider.createTerminalConnection(
					this.connID, this.subjectDevice, eventList,
					this.connectionDevice);
			oldConnState = connection.getCallControlConnState();
			oldTermConnState = connection.getCallControlTermConnState();
			connection.setConnectionState(this.connState, eventList);
			connection.setTermConnState(this.termConnState, eventList);
		}

		this.eventHandler.finishConnEvents(null, this.eventType, this.cause,
				110, this.subjectDeviceID, this.subjectDevice, connection,
				this.call, this.privateData, this.callingDeviceID,
				this.calledDeviceID, this.lastRedirectionDeviceID,
				this.connState, oldConnState, oldTermConnState, eventList);

		return this.privateData;
	}

	boolean shouldOverrideConnEventCSTACauseValue(TSEvent eventToModify) {
		boolean result = false;

		switch (eventToModify.getEventType()) {
		case 6:
			result = eventToModify.getSnapshotCstaCause() == -1;
			break;
		case 9:
		case 23:
		case 26:
			result = this.eventType == 57;
			break;
		case 10:
		case 27:
			result = this.eventType == 56;
			break;
		case 7:
		case 21:
			result = this.eventType == 59;
			break;
		case 11:
		case 24:
		case 28:
			result = this.eventType == 60;
			break;
		case 22:
			result = this.eventType == 62;
			break;
		case 25:
			result = this.eventType == 64;
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