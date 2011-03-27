package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import org.apache.log4j.Logger;

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

final class SnapshotCallConfHandler implements ConfHandler {
	private static Logger log = Logger.getLogger(SnapshotCallConfHandler.class);
	TSCall call;
	Vector<SnapshotCallExtraConfHandler> extraHandlerVector = null;
	boolean synchronous;
	boolean handled = false;
	boolean rc = false;

	SnapshotCallConfHandler(final TSCall _call,
			final SnapshotCallExtraConfHandler _extraHandler,
			final boolean _synchronous) {
		call = _call;
		synchronous = _synchronous;
		if (!synchronous)
			synchronized (call.callbackAndTypeVector) {
				call.futureAsynchronousSnapshotHandler = this;
			}
		addExtraHandler(_extraHandler);
	}

	void addExtraHandler(final SnapshotCallExtraConfHandler _extraHandler) {
		if (_extraHandler == null)
			return;

		if (extraHandlerVector == null)
			extraHandlerVector = new Vector<SnapshotCallExtraConfHandler>();

		extraHandlerVector.addElement(_extraHandler);
	}

	@Override
	public void handleConf(final CSTAEvent event) {
		try {
			synchronized (call.callbackAndTypeVector) {
				if (!synchronous)
					call.futureAsynchronousSnapshotHandler = null;
				call.currentSnapshotHandler = this;
			}
			handled = true;
			try {
				if (event == null)
					throw new TsapiPlatformException(4, 0, "no conf event");

				if (event.getEvent() instanceof CSTAUniversalFailureConfEvent)
					TSErrorMap
							.throwCSTAException(((CSTAUniversalFailureConfEvent) event
									.getEvent()).getError());
				if (event.getEvent() instanceof ACSUniversalFailureConfEvent)
					TSErrorMap
							.throwACSException(((ACSUniversalFailureConfEvent) event
									.getEvent()).getError());
				if (!(event.getEvent() instanceof CSTASnapshotCallConfEvent))
					throw new TsapiPlatformException(4, 1,
							"expected CSTASnapshotCallConfEvent");
			} catch (final TsapiInvalidStateException e) {
				call.setState(34, null);
				call.endCVDObservers(100, null);
				rc = true;
				return;
			} catch (final Exception e) {
				rc = false;
				return;
			}

			final Vector<TSConnection> newConns = new Vector<TSConnection>();

			final CSTASnapshotCallResponseInfo[] info = ((CSTASnapshotCallConfEvent) event
					.getEvent()).getSnapshotData();

			if (info == null) {
				rc = false;
				return;
			}

			TSConnection connection = null;
			TSDevice device = null;
			CSTAExtendedDeviceID extDevID = null;

			for (int i = 0; i < info.length; ++i) {
				try {
					extDevID = info[i].getDeviceOnCall();

					device = call.getTSProviderImpl().createDevice(
							info[i].getDeviceOnCall(),
							info[i].getCallIdentifier());

					if (device == null)
						break;

					if (device
							.isForExternalDeviceMatchingLocalExtensionNumber(extDevID)) {
						connection = call.getTSProviderImpl().createConnection(
								info[i].getCallIdentifier(), device, null);
						connection.setDoNotExpectConnectionClearedEvent(true);
					} else
						connection = call.getTSProviderImpl()
								.createTerminalConnection(
										info[i].getCallIdentifier(), device,
										null, device);

					final int oldConnState = connection
							.getCallControlConnState();
					final int oldTermConnState = connection
							.getCallControlTermConnState();

					if (oldConnState == 89 || oldTermConnState == 102) {
						SnapshotCallConfHandler.log
								.info("SnapshotCallConfHandler.handleConf(): recreating connection "
										+ connection
										+ "; oldConnState="
										+ oldConnState
										+ ", oldTermConnState="
										+ oldTermConnState);

						connection.delete();
						call.getTSProviderImpl().dumpConn(
								info[i].getCallIdentifier());

						connection = call.getTSProviderImpl()
								.createTerminalConnection(
										info[i].getCallIdentifier(), device,
										null, device);
					}
				} catch (final TsapiPlatformException e) {
					rc = false;
					return;
				}
				connection.setStateFromLocalConnState(info[i]
						.getLocalConnectionState());

				if (!newConns.contains(connection))
					newConns.addElement(connection);

				device.addConnection(connection);
			}

			call.replaceConnections(newConns, null);

			final Vector<TSConnection> connections = call.getConnections();

			boolean found = false;
			if (call.confController != null) {
				synchronized (connections) {
					for (int i = 0; i < connections.size(); ++i) {
						final TSConnection conn = connections.elementAt(i);
						final Vector<TSConnection> termConns = conn
								.getTermConns();
						if (termConns == null
								|| !termConns.contains(call.confController))
							continue;
						found = true;
						break;
					}
				}

				if (!found)
					call.confController = null;
			}
			found = false;
			if (call.xferController != null) {
				synchronized (connections) {
					for (int i = 0; i < connections.size(); ++i) {
						final TSConnection conn = connections.elementAt(i);
						final Vector<TSConnection> termConns = conn
								.getTermConns();
						if (termConns == null
								|| !termConns.contains(call.xferController))
							continue;
						found = true;
						break;
					}
				}

				if (!found)
					call.xferController = null;
			}
			rc = true;

			final Object privateData = event.getPrivData();

			if (privateData instanceof LucentSnapshotCallInfoConfEvent) {
				final LucentSnapshotCallInfoConfEvent luV7PrivData = (LucentSnapshotCallInfoConfEvent) privateData;
				call.setDeviceHistory(luV7PrivData.getDeviceHistory());
			}
		} finally {
			Vector<TSEvent> eventList = null;
			try {
				if (extraHandlerVector != null) {
					eventList = new Vector<TSEvent>();
					Object privateData = null;
					for (int i = 0; i < extraHandlerVector.size(); ++i)
						try {
							final SnapshotCallExtraConfHandler extraHandler = extraHandlerVector
									.elementAt(i);

							final Object pd = extraHandler.handleConf(rc,
									eventList, privateData);
							if (pd != null)
								privateData = pd;
						} catch (final Exception e) {
						}
				}
			} finally {
				synchronized (call.callbackAndTypeVector) {
					call.currentSnapshotHandler = null;
				}
				if (eventList != null && eventList.size() == 0)
					eventList = null;
				call.doCallbackSnapshots(eventList, 110);
				synchronized (this) {
					super.notify();
				}
			}
		}
	}
}
