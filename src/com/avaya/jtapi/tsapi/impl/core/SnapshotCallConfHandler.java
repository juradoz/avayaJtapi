package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.acs.ACSUniversalFailureConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallResponseInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAUniversalFailureConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSnapshotCallInfoConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.TSErrorMap;
import java.util.Vector;
import org.apache.log4j.Logger;

final class SnapshotCallConfHandler implements ConfHandler {
	private static Logger log = Logger.getLogger(SnapshotCallConfHandler.class);
	TSCall call;
	Vector<SnapshotCallExtraConfHandler> extraHandlerVector = null;
	boolean synchronous;
	boolean handled = false;
	boolean rc = false;

	SnapshotCallConfHandler(TSCall _call,
			SnapshotCallExtraConfHandler _extraHandler, boolean _synchronous) {
		this.call = _call;
		this.synchronous = _synchronous;
		if (!this.synchronous) {
			synchronized (this.call.callbackAndTypeVector) {
				this.call.futureAsynchronousSnapshotHandler = this;
			}
		}
		addExtraHandler(_extraHandler);
	}

	void addExtraHandler(SnapshotCallExtraConfHandler _extraHandler) {
		if (_extraHandler == null) {
			return;
		}

		if (this.extraHandlerVector == null) {
			this.extraHandlerVector = new Vector<SnapshotCallExtraConfHandler>();
		}

		this.extraHandlerVector.addElement(_extraHandler);
	}

	public void handleConf(CSTAEvent event) {
		try {
			synchronized (this.call.callbackAndTypeVector) {
				if (!this.synchronous) {
					this.call.futureAsynchronousSnapshotHandler = null;
				}
				this.call.currentSnapshotHandler = this;
			}
			this.handled = true;
			try {
				if (event == null) {
					throw new TsapiPlatformException(4, 0, "no conf event");
				}

				if ((event.getEvent() instanceof CSTAUniversalFailureConfEvent)) {
					TSErrorMap
							.throwCSTAException(((CSTAUniversalFailureConfEvent) event
									.getEvent()).getError());
				}
				if ((event.getEvent() instanceof ACSUniversalFailureConfEvent)) {
					TSErrorMap
							.throwACSException(((ACSUniversalFailureConfEvent) event
									.getEvent()).getError());
				}
				if (!(event.getEvent() instanceof CSTASnapshotCallConfEvent)) {
					throw new TsapiPlatformException(4, 1,
							"expected CSTASnapshotCallConfEvent");
				}
			} catch (TsapiInvalidStateException e) {
				this.call.setState(34, null);
				this.call.endCVDObservers(100, null);
				this.rc = true;

				Vector<TSEvent> eventList = null;
				try {
					if (this.extraHandlerVector != null) {
						eventList = new Vector<TSEvent>();
						Object privateData = null;
						for (int i = 0; i < this.extraHandlerVector.size(); i++) {
							try {
								SnapshotCallExtraConfHandler extraHandler = (SnapshotCallExtraConfHandler) this.extraHandlerVector
										.elementAt(i);

								Object pd = extraHandler.handleConf(this.rc,
										eventList, privateData);
								if (pd != null)
									privateData = pd;
							} catch (Exception e1) {
							}
						}
					}
				} finally {
					synchronized (this.call.callbackAndTypeVector) {
						this.call.currentSnapshotHandler = null;
					}
					if ((eventList != null) && (eventList.size() == 0)) {
						eventList = null;
					}
					this.call.doCallbackSnapshots(eventList, 110);
					synchronized (this) {
						notify();
					}
				}
				return;
			} catch (Exception e) {
				this.rc = false;

				Vector<TSEvent> eventList = null;
				try {
					if (this.extraHandlerVector != null) {
						eventList = new Vector<TSEvent>();
						Object privateData = null;
						for (int i = 0; i < this.extraHandlerVector.size(); i++) {
							try {
								SnapshotCallExtraConfHandler extraHandler = (SnapshotCallExtraConfHandler) this.extraHandlerVector
										.elementAt(i);

								Object pd = extraHandler.handleConf(this.rc,
										eventList, privateData);
								if (pd != null)
									privateData = pd;
							} catch (Exception e1) {
							}
						}
					}
				} finally {
					synchronized (this.call.callbackAndTypeVector) {
						this.call.currentSnapshotHandler = null;
					}
					if ((eventList != null) && (eventList.size() == 0)) {
						eventList = null;
					}
					this.call.doCallbackSnapshots(eventList, 110);
					synchronized (this) {
						notify();
					}
				}
				return;
			}
			Vector<TSConnection> newConns = new Vector<TSConnection>();

			CSTASnapshotCallResponseInfo[] info = ((CSTASnapshotCallConfEvent) event
					.getEvent()).getSnapshotData();
			Vector<TSEvent> eventList;
			Object privateData;
			int i;
			SnapshotCallExtraConfHandler extraHandler;
			Object pd;
			if (info == null) {
				this.rc = false;
			} else {
				TSConnection connection = null;
				TSDevice device = null;
				CSTAExtendedDeviceID extDevID = null;

				for (i = 0; i < info.length; i++) {
					try {
						extDevID = info[i].getDeviceOnCall();

						device = this.call.getTSProviderImpl().createDevice(
								info[i].getDeviceOnCall(),
								info[i].getCallIdentifier());

						if (device == null) {
							continue;
						}

						if (device
								.isForExternalDeviceMatchingLocalExtensionNumber(extDevID)) {
							connection = this.call.getTSProviderImpl()
									.createConnection(
											info[i].getCallIdentifier(),
											device, null);
							connection
									.setDoNotExpectConnectionClearedEvent(true);
						} else {
							connection = this.call.getTSProviderImpl()
									.createTerminalConnection(
											info[i].getCallIdentifier(),
											device, null, device);
						}

						int oldConnState = connection.getCallControlConnState();
						int oldTermConnState = connection
								.getCallControlTermConnState();

						if ((oldConnState == 89) || (oldTermConnState == 102)) {
							log.info("SnapshotCallConfHandler.handleConf(): recreating connection "
									+ connection
									+ "; oldConnState="
									+ oldConnState
									+ ", oldTermConnState="
									+ oldTermConnState);

							connection.delete();
							this.call.getTSProviderImpl().dumpConn(
									info[i].getCallIdentifier());

							connection = this.call.getTSProviderImpl()
									.createTerminalConnection(
											info[i].getCallIdentifier(),
											device, null, device);
						}
					} catch (TsapiPlatformException e) {
						this.rc = false;
						return;
					}
					connection.setStateFromLocalConnState(info[i]
							.getLocalConnectionState());

					if (!newConns.contains(connection)) {
						newConns.addElement(connection);
					}

					device.addConnection(connection);
				}

				this.call.replaceConnections(newConns, null);

				Vector<?> connections = this.call.getConnections();

				boolean found = false;
				if (this.call.confController != null) {
					synchronized (connections) {
						for (i = 0; i < connections.size(); i++) {
							TSConnection conn = (TSConnection) connections
									.elementAt(i);
							Vector<?> termConns = conn.getTermConns();
							if ((termConns != null)
									&& (termConns
											.contains(this.call.confController))) {
								found = true;
								break;
							}
						}
					}
					if (!found)
						this.call.confController = null;
				}
				found = false;
				if (this.call.xferController != null) {
					synchronized (connections) {
						for (i = 0; i < connections.size(); i++) {
							TSConnection conn = (TSConnection) connections
									.elementAt(i);
							Vector<?> termConns = conn.getTermConns();
							if ((termConns != null)
									&& (termConns
											.contains(this.call.xferController))) {
								found = true;
								break;
							}
						}
					}
					if (!found)
						this.call.xferController = null;
				}
				this.rc = true;

				privateData = event.getPrivData();

				if ((privateData instanceof LucentSnapshotCallInfoConfEvent)) {
					LucentSnapshotCallInfoConfEvent luV7PrivData = (LucentSnapshotCallInfoConfEvent) privateData;
					this.call.setDeviceHistory(luV7PrivData.getDeviceHistory());
				}

				eventList = null;
				try {
					if (this.extraHandlerVector != null) {
						eventList = new Vector<TSEvent>();
						privateData = null;
						for (i = 0; i < this.extraHandlerVector.size(); i++) {
							try {
								extraHandler = (SnapshotCallExtraConfHandler) this.extraHandlerVector
										.elementAt(i);

								pd = extraHandler.handleConf(this.rc,
										eventList, privateData);
								if (pd != null)
									privateData = pd;
							} catch (Exception e) {
							}
						}
					}
				} finally {
					synchronized (this.call.callbackAndTypeVector) {
						this.call.currentSnapshotHandler = null;
					}
					if ((eventList != null) && (eventList.size() == 0)) {
						eventList = null;
					}
					this.call.doCallbackSnapshots(eventList, 110);
					synchronized (this) {
						notify();
					}
				}
			}
		} finally {
			Vector<TSEvent> eventList = null;
			try {
				if (this.extraHandlerVector != null) {
					eventList = new Vector<TSEvent>();
					Object privateData = null;
					for (int i = 0; i < this.extraHandlerVector.size(); i++) {
						try {
							SnapshotCallExtraConfHandler extraHandler = (SnapshotCallExtraConfHandler) this.extraHandlerVector
									.elementAt(i);

							Object pd = extraHandler.handleConf(this.rc,
									eventList, privateData);
							if (pd != null)
								privateData = pd;
						} catch (Exception e) {
						}
					}
				}
			} finally {
				synchronized (this.call.callbackAndTypeVector) {
					this.call.currentSnapshotHandler = null;
				}
				if ((eventList != null) && (eventList.size() == 0)) {
					eventList = null;
				}
				this.call.doCallbackSnapshots(eventList, 110);
				synchronized (this) {
					notify();
				}
			}
		}
	}
}