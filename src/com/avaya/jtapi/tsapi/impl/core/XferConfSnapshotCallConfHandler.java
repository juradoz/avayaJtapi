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

	XferConfSnapshotCallConfHandler(final TSCall _call, final int _jtapiCause,
			final Object _privateData,
			final Vector<TSConnection> _snapConnections) {
		this(null, _call, _jtapiCause, _privateData, _snapConnections);
	}

	XferConfSnapshotCallConfHandler(final TSEventHandler _eventHandler,
			final TSCall _call, final int _jtapiCause,
			final Object _privateData,
			final Vector<TSConnection> _snapConnections) {
		eventHandler = _eventHandler;
		call = _call;
		jtapiCause = _jtapiCause;
		privateData = _privateData;

		snapConnections = _snapConnections;
	}

	public Object handleConf(final boolean rc,
			final Vector<TSEvent> _eventList, final Object _privateData) {
		if (call.getNeedRedoSnapshotCall()) {
			call.setNeedRedoSnapshotCall(false);
			XferConfSnapshotCallConfHandler.log.info("redo snapshot call");
			call.doSnapshot(snapConnections.elementAt(0).getConnID(), this,
					false);
			return null;
		}

		call.setSnapshotCallConfPending(false);

		call.setNeedSnapshot(false);

		final Vector<TSEvent> eventList = new Vector<TSEvent>();

		if (rc)
			for (int i = 0; i < snapConnections.size(); ++i) {
				final TSConnection conn = snapConnections.elementAt(i);
				conn.getSnapshot(eventList, false);
			}
		else
			return privateData;

		if (eventHandler != null)
			eventHandler.doCallMonitors(call, eventList, jtapiCause,
					privateData);
		else if (eventList.size() > 0) {
			final Vector<TsapiCallMonitor> observers = call.getObservers();
			for (int j = 0; j < observers.size(); ++j) {
				final TsapiCallMonitor callback = observers.elementAt(j);

				callback.deliverEvents(eventList, jtapiCause, true);
			}
		}

		return privateData;
	}
}
