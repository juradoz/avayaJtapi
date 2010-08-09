package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;

final class XferConfSnapshotCallConfHandler implements
		SnapshotCallExtraConfHandler {
	private static Logger log = Logger
			.getLogger(XferConfSnapshotCallConfHandler.class);
	TSEventHandler eventHandler;
	int jtapiCause;
	TSCall call;
	Object privateData;
	Vector<TSConnection> snapConnections;

	XferConfSnapshotCallConfHandler(TSCall _call, int _jtapiCause,
			Object _privateData, Vector<TSConnection> _snapConnections) {
		this(null, _call, _jtapiCause, _privateData, _snapConnections);
	}

	XferConfSnapshotCallConfHandler(TSEventHandler _eventHandler, TSCall _call,
			int _jtapiCause, Object _privateData,
			Vector<TSConnection> _snapConnections) {
		eventHandler = _eventHandler;
		call = _call;
		jtapiCause = _jtapiCause;
		privateData = _privateData;

		snapConnections = _snapConnections;
	}

	public Object handleConf(boolean rc, Vector<TSEvent> _eventList,
			Object _privateData) {
		if (call.getNeedRedoSnapshotCall()) {
			call.setNeedRedoSnapshotCall(false);
			log.info("redo snapshot call");
			call.doSnapshot((snapConnections.elementAt(0)).getConnID(), this,
					false);
			return null;
		}

		call.setSnapshotCallConfPending(false);

		call.setNeedSnapshot(false);

		Vector eventList = new Vector();

		if (rc) {
			for (int i = 0; i < snapConnections.size(); ++i) {
				TSConnection conn = snapConnections.elementAt(i);
				conn.getSnapshot(eventList, false);
			}

		} else {
			return privateData;
		}

		if (eventHandler != null) {
			eventHandler.doCallMonitors(call, eventList, jtapiCause,
					privateData);
		} else if (eventList.size() > 0) {
			Vector observers = call.getObservers();
			for (int j = 0; j < observers.size(); ++j) {
				TsapiCallMonitor callback = (TsapiCallMonitor) observers
						.elementAt(j);

				callback.deliverEvents(eventList, jtapiCause, true);
			}
		}

		return privateData;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.XferConfSnapshotCallConfHandler JD-Core
 * Version: 0.5.4
 */