package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import java.util.Vector;
import org.apache.log4j.Logger;

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
		this.eventHandler = _eventHandler;
		this.call = _call;
		this.jtapiCause = _jtapiCause;
		this.privateData = _privateData;

		this.snapConnections = _snapConnections;
	}

	public Object handleConf(boolean rc, Vector<TSEvent> _eventList,
			Object _privateData) {
		if (this.call.getNeedRedoSnapshotCall()) {
			this.call.setNeedRedoSnapshotCall(false);
			log.info("redo snapshot call");
			this.call.doSnapshot(((TSConnection) this.snapConnections
					.elementAt(0)).getConnID(), this, false);
			return null;
		}

		this.call.setSnapshotCallConfPending(false);

		this.call.setNeedSnapshot(false);

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		if (rc) {
			for (int i = 0; i < this.snapConnections.size(); i++) {
				TSConnection conn = (TSConnection) this.snapConnections
						.elementAt(i);
				conn.getSnapshot(eventList, false);
			}
		} else {
			return this.privateData;
		}

		if (this.eventHandler != null) {
			this.eventHandler.doCallMonitors(this.call, eventList,
					this.jtapiCause, this.privateData);
		} else if (eventList.size() > 0) {
			Vector<?> observers = this.call.getObservers();
			for (int j = 0; j < observers.size(); j++) {
				TsapiCallMonitor callback = (TsapiCallMonitor) observers
						.elementAt(j);

				callback.deliverEvents(eventList, this.jtapiCause, true);
			}
		}

		return this.privateData;
	}
}