package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.HasUCID;
import com.avaya.jtapi.tsapi.csta1.LucentDeviceHistoryEntry;
import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
import com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCall;
import com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo;
import com.avaya.jtapi.tsapi.csta1.LucentQueryUcid;
import com.avaya.jtapi.tsapi.csta1.LucentQueryUcidConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSetBillRate;
import com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCall;
import com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCall;
import com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode;
import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV6MakePredictiveCall;
import com.avaya.jtapi.tsapi.impl.TsapiAddress;
import com.avaya.jtapi.tsapi.impl.TsapiCallCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;

public final class TSCall implements IDomainCall {
	static enum CallUCIDStatus {
		OK, UCIDMISMATCH, NONEXISTING;
	}

	private static Logger log = Logger.getLogger(TSCall.class);
	Object obsSync;
	TSProviderImpl provider;
	int state;
	int callID;
	int nonCallID = -1;
	private final Vector<TSConnection> connections;
	private final Vector<TSConnection> staleConnections;
	TSDevice internalDeviceMonitor = null;
	TSConnection confController;
	TSConnection xferController;
	boolean confEnable = true;
	boolean xferEnable = true;

	boolean delayVDNremoveCallFromDomain = false;

	boolean receivedCallClearedTransfer = false;
	long callClearedTransferReceiptTime = 0L;
	private final Vector<TsapiCallMonitor> monitorThreads;

	boolean needSnapshot = true;

	int monitorCrossRefID = 0;

	boolean wasEverMonitoredByCallsViaDevice = false;

	boolean monitorPending = false;

	Object replyPriv = null;

	TSDevice calledDevice = null;
	TSDevice callingAddress = null;

	TSDevice callingTerminal = null;

	TSDevice lastRedirectionDevice = null;
	TSDevice distributingDevice = null;

	TSDevice deliveringACDDevice = null;
	TSDevice distributingVDN = null;
	private final Vector<DeviceObs> deviceObsVector;
	private final Vector<TsapiCallMonitor> staleObsVector;
	UserToUserInfo uui = null;
	LookaheadInfo lai = null;
	UserEnteredCode uec = null;
	OriginalCallInfo oci = null;

	short reason = 0;
	String ucid = null;
	CSTACallOriginatorInfo callOriginatorInfo;
	boolean flexibleBilling;
	V7DeviceHistoryEntry[] deviceHistory = null;
	private final Vector<TSTrunk> trkVector;
	TSCall handOffCall = null;
	final Vector<CallbackAndType> callbackAndTypeVector;
	SnapshotCallConfHandler currentSnapshotHandler = null;
	SnapshotCallConfHandler futureAsynchronousSnapshotHandler = null;
	String digits;

	private short cstaCause = -1;

	private boolean snapshotCallConfPending = false;

	private boolean needRedoSnapshotCall = false;
	int refCount = 0;
	int connection_wait_limit;

	private final TSCallObjectAge my_age = new TSCallObjectAge();

	TsapiAddress refVDN = null;

	TSCall(final TSProviderImpl _provider, final int _callID) {
		provider = _provider;
		state = 32;
		connections = new Vector<TSConnection>();
		staleConnections = new Vector<TSConnection>();
		trkVector = new Vector<TSTrunk>();
		monitorThreads = new Vector<TsapiCallMonitor>();
		deviceObsVector = new Vector<DeviceObs>();
		staleObsVector = new Vector<TsapiCallMonitor>();
		callbackAndTypeVector = new Vector<CallbackAndType>();
		obsSync = new Object();
		setCallID(_callID);
		setConnection_wait_limit(2);
		TSCall.log.info("Constructing call " + this + " with ID " + _callID
				+ " for " + provider);
		if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
			TSCall.log
					.debug("Updating call count in the statistics collector.");
			PerfStatisticsCollector.updateCallCount();
		}
	}

	public void addCallMonitor(final TsapiCallMonitor obs)
			throws TsapiResourceUnavailableException {
		if (provider.getCapabilities().getMonitorCall() == 0)
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");

		if (!provider.allowCallMonitoring())
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"no permissions");

		switch (state) {
		case 32:
			if (monitorThreads.contains(obs))
				return;

			synchronized (this) {
				monitorPending = true;
				callbackAndTypeVector
						.addElement(new CallbackAndType(obs, null));
			}
			break;
		case 34:
			if (monitorThreads.contains(obs))
				return;

			obs.addReference();
			obs.deleteReference(this, false, 100, null);
			break;
		default:
			synchronized (obsSync) {
				if (monitorThreads.contains(obs))
					return;
				int i = 0;
				synchronized (deviceObsVector) {
					for (i = 0; i < deviceObsVector.size(); ++i) {
						if (((DeviceObs) deviceObsVector.elementAt(i)).callback != obs)
							continue;
						i = 1;

						monitorThreads.addElement(((DeviceObs) deviceObsVector
								.elementAt(i)).callback);
						deviceObsVector.removeElementAt(i);

						break;
					}

				}

				if (i != 0) {
					setMonitor(true);
					return;
				}

				if (staleObsVector.removeElement(obs)) {
					setMonitor(true);

					monitorThreads.addElement(obs);

					return;
				}

				setMonitor(true);

				monitorThreads.addElement(obs);
			}

			obs.addReference();

			sendSnapshot(obs, null, true);
		}
	}

	void addConnection(final TSConnection tsConn,
			final Vector<TSEvent> eventList) {
		synchronized (connections) {
			if (connections.contains(tsConn))
				return;

			connections.addElement(tsConn);

			if (connections.size() > 0)
				setState(33, eventList);

			if (connections.size() >= getConnection_wait_limit())
				connections.notify();
		}
	}

	void addDeviceObservers(final TSDevice tsDevice,
			final Vector<TsapiCallMonitor> _terminalObservers,
			final Vector<TsapiCallMonitor> _addressObservers,
			final Vector<TsapiCallMonitor> _cvdObservers,
			final boolean sendSnapshotEvents) {
		TsapiCallMonitor callback = null;

		boolean found = false;

		CSTAConnectionID snapConnID = null;
		synchronized (connections) {
			if (connections.size() > 0)
				try {
					snapConnID = ((TSConnection) connections.elementAt(0))
							.getConnID();
				} catch (final TsapiPlatformException e) {
					TSCall.log.error("Ignoring exception: " + e);
					if (callID != 0)
						snapConnID = new CSTAConnectionID(callID, "", (short) 0);
				}
			else if (callID != 0)
				snapConnID = new CSTAConnectionID(callID, "", (short) 0);
		}

		if (_cvdObservers != null) {
			final Vector<TsapiCallMonitor> cvdObservers = new Vector<TsapiCallMonitor>(
					_cvdObservers);
			for (int i = 0; i < cvdObservers.size(); ++i) {
				found = false;
				callback = (TsapiCallMonitor) cvdObservers.elementAt(i);
				synchronized (monitorThreads) {
					if (monitorThreads.contains(callback))
						found = true;

					if (!found) {
						synchronized (staleObsVector) {
							for (int j = 0; j < staleObsVector.size(); ++j) {
								if (staleObsVector.elementAt(j) != callback)
									continue;
								monitorThreads.addElement(callback);
								staleObsVector.removeElementAt(j);

								found = true;
								break;
							}
						}

						if (!found)
							synchronized (deviceObsVector) {
								for (int j = 0; j < deviceObsVector.size(); ++j) {
									if (((DeviceObs) deviceObsVector
											.elementAt(j)).callback != callback)
										continue;
									monitorThreads.addElement(callback);
									deviceObsVector.removeElementAt(j);

									found = true;
									break;
								}
							}

					}

					if (!found && (snapConnID == null || !sendSnapshotEvents)) {
						monitorThreads.addElement(callback);
						callback.addReference();
						if (sendSnapshotEvents)
							sendSnapshot(callback, null, false);
					}
				}
				if (found || snapConnID == null || !sendSnapshotEvents)
					continue;
				callbackAndTypeVector.addElement(new CallbackAndType(callback,
						null));
			}

		}

		DeviceObs devObs = null;
		DevWithType devWithType = null;

		if (_addressObservers != null) {
			found = false;

			devWithType = new DevWithType(tsDevice, false);

			final Vector<TsapiCallMonitor> addressObservers = new Vector<TsapiCallMonitor>(
					_addressObservers);

			for (int i = 0; i < addressObservers.size(); ++i) {
				callback = (TsapiCallMonitor) addressObservers.elementAt(i);
				found = false;
				if (monitorThreads.contains(callback))
					found = true;

				if (!found)
					synchronized (deviceObsVector) {
						synchronized (staleObsVector) {
							int k;
							for (k = 0; k < staleObsVector.size(); ++k) {
								if (staleObsVector.elementAt(k) != callback)
									continue;
								found = true;
								devObs = new DeviceObs(callback);
								devObs.devWithTypeVector
										.addElement(devWithType);

								deviceObsVector.addElement(devObs);
								staleObsVector.removeElementAt(k);
								break;
							}
						}

						if (!found) {
							for (i = 0; i < deviceObsVector.size(); ++i) {
								devObs = (DeviceObs) deviceObsVector
										.elementAt(i);
								if (devObs.callback != callback)
									continue;
								found = true;
								synchronized (devObs.devWithTypeVector) {
									if (!devObs.devWithTypeVector
											.contains(devWithType))
										devObs.devWithTypeVector
												.addElement(devWithType);
								}
								break;
							}

							if (!found
									&& (snapConnID == null || !sendSnapshotEvents)) {
								devObs = new DeviceObs(callback);
								devObs.devWithTypeVector
										.addElement(devWithType);
								deviceObsVector.addElement(devObs);
								callback.addReference();
								if (sendSnapshotEvents)
									sendSnapshot(callback, null, false);
							}
						}
					}
				if (found || snapConnID == null || !sendSnapshotEvents)
					continue;
				callbackAndTypeVector.addElement(new CallbackAndType(callback,
						devWithType));
			}
		}

		if (_terminalObservers == null)
			return;
		found = false;

		devWithType = new DevWithType(tsDevice, true);

		final Vector<TsapiCallMonitor> terminalObservers = new Vector<TsapiCallMonitor>(
				_terminalObservers);

		for (int i = 0; i < terminalObservers.size(); ++i) {
			callback = (TsapiCallMonitor) terminalObservers.elementAt(i);
			found = false;
			if (monitorThreads.contains(callback))
				found = true;
			if (!found)
				synchronized (deviceObsVector) {
					synchronized (staleObsVector) {
						int l;
						for (l = 0; l < staleObsVector.size(); ++l) {
							if (staleObsVector.elementAt(l) != callback)
								continue;
							found = true;
							devObs = new DeviceObs(callback);
							devObs.devWithTypeVector.addElement(devWithType);

							deviceObsVector.addElement(devObs);
							staleObsVector.removeElementAt(l);
							break;
						}
					}

					if (!found) {
						int j;
						for (j = 0; j < deviceObsVector.size(); ++j) {
							devObs = (DeviceObs) deviceObsVector.elementAt(j);
							if (devObs.callback != callback)
								continue;
							found = true;
							synchronized (devObs.devWithTypeVector) {
								if (!devObs.devWithTypeVector
										.contains(devWithType))
									devObs.devWithTypeVector
											.addElement(devWithType);
							}
							break;
						}

						if (!found
								&& (snapConnID == null || !sendSnapshotEvents)) {
							devObs = new DeviceObs(callback);
							devObs.devWithTypeVector.addElement(devWithType);
							deviceObsVector.addElement(devObs);
							callback.addReference();
							if (sendSnapshotEvents)
								sendSnapshot(callback, null, false);
						}
					}
				}
			if (found || snapConnID == null || !sendSnapshotEvents)
				continue;
			callbackAndTypeVector.addElement(new CallbackAndType(callback,
					devWithType));
		}
	}

	public TSConnection addParty(final String newParty, final boolean active)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		if (!provider.isLucentV5())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		if (updateObject() && state != 33)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call is not active");

		final CSTAConnectionID connID = selectConnectionIdForAddParty();

		if (connID == null)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call is not active (no connections)");

		final LucentSingleStepConferenceCall ssc = new LucentSingleStepConferenceCall(
				connID, newParty, (short) (active ? 1 : 0), false);

		final ConfHandler handler = new ConfXferConfHandler(this, null, 90);
		try {
			provider.sendPrivateData(ssc.makeTsapiPrivate(), handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidPartyException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"addParty failure, exception: " + e);

			throw new TsapiPlatformException(3, 0,
					"addParty failure, exception: " + e);
		}

		return ((ConfXferConfHandler) handler).newConn;
	}

	public void addTrunk(final TSTrunk trunk, final Vector<TSEvent> eventList) {
		synchronized (trkVector) {
			if (trkVector.contains(trunk))
				return;

			if (trunk.setCall(this, eventList))
				trkVector.addElement(trunk);
		}
	}

	public boolean callIsInVDNDomain(final TSCall callToCheck) {
		return callToCheck.refVDN != null;
	}

	public boolean canSetBillRate() {
		return flexibleBilling;
	}

	public boolean checkForMonitors() {
		if (isMonitorSet() == true)
			return true;

		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				final TSConnection conn = (TSConnection) connections
						.elementAt(i);
				if (conn.getTSDevice().isMonitorSet())
					return true;
				final Vector<TSConnection> termConns = conn.getTermConns();
				if (termConns == null)
					continue;
				synchronized (termConns) {
					for (int j = 0; j < termConns.size(); ++j) {
						final TSConnection termconn = (TSConnection) termConns
								.elementAt(j);
						if (!termconn.getTSDevice().isMonitorSet())
							continue;
						return true;
					}

				}

			}

		}

		return false;
	}

	boolean cleanUCIDInCall() {
		CallUCIDStatus callsttype = CallUCIDStatus.OK;
		try {
			final Object ucidConf = getQueryUCIDConf();
			TSCall.log.debug("UCID obtained from the switch is "
					+ ((HasUCID) ucidConf).getUcid());

			if (getUCID() != null
					&& !getUCID().contentEquals(((HasUCID) ucidConf).getUcid())) {
				callsttype = CallUCIDStatus.UCIDMISMATCH;
				TSCall.log.info("ERROR: mismatched ucid, for call: " + this
						+ " - setting call state to INVALID.");
			}
		} catch (final TsapiPlatformException e) {
			if (e.getErrorType() == 2 && e.getErrorCode() == 24) {
				callsttype = CallUCIDStatus.NONEXISTING;
				TSCall.log
						.info("ERROR: Attempted cleanUCIDInCall() but no active call: "
								+ this + " - setting call state to INVALID.");
			} else {
				callsttype = CallUCIDStatus.OK;
				TSCall.log
						.info("ERROR: Saw & ignored exception (TsapiPlatformException)  for cleanUCIDsInCallsInConnections(), for call "
								+ this
								+ " - Perhaps UCID queries are disabled on the switch. "
								+ e);
			}
		} catch (final Exception e) {
			callsttype = CallUCIDStatus.OK;
			TSCall.log
					.info("ERROR: Saw & ignored unexpected exception  for cleanUCIDsInCallsInConnections(), for call "
							+ this
							+ " - Perhaps UCID queries are disabled on the switch. "
							+ e);
		} finally {
			int i;
			if (callsttype != CallUCIDStatus.OK) {
				i = getCallID();
				setState(34, null);
				provider.dumpCall(i);
			}

		}

		return callsttype == CallUCIDStatus.OK;
	}

	public void conference(final TSCall otherCall, final CSTAPrivate reqPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getConferenceCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		final boolean thisCallUpdate = updateObject();
		final boolean otherCallUpdate = otherCall.updateObject();

		if (thisCallUpdate && state != 33)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call is not active");

		if (!confEnable || !otherCall.confEnable)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"conferencing disabled");

		TSConnection activeTermConn = confController;
		TSConnection heldTermConn = otherCall.confController;
		if (activeTermConn == null) {
			if (heldTermConn == null) {
				TSConnection conn = null;
				TSConnection termConn = null;
				boolean found = false;
				synchronized (connections) {
					for (int i = 0; i < connections.size(); ++i) {
						conn = (TSConnection) connections.elementAt(i);
						final Vector<TSConnection> termConns = conn
								.getTermConns();
						if (termConns == null)
							continue;
						final Vector<TSConnection> tcs = new Vector<TSConnection>(
								termConns);
						for (int j = 0; j < tcs.size(); ++j) {
							termConn = (TSConnection) tcs.elementAt(j);
							if (termConn.getCallControlTermConnState() != 98
									&& termConn.getCallControlTermConnState() != 103)
								continue;
							activeTermConn = termConn;

							heldTermConn = otherCall
									.findHeldTermConnection(activeTermConn
											.getTSDevice());
							if (heldTermConn == null)
								continue;
							found = true;
							break;
						}

						if (found)
							break;
					}
				}
				if (activeTermConn == null)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "no active terminal connection found");

				if (heldTermConn == null)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "no held terminal connection found");

			} else {
				if (otherCallUpdate
						&& heldTermConn.getCallControlTermConnState() != 99
						&& heldTermConn.getCallControlTermConnState() != 103)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "terminal connection not held");

				activeTermConn = findActiveTermConnection(heldTermConn
						.getTSDevice());
				if (activeTermConn == null)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "no active terminal connection found");

			}

		} else if (heldTermConn == null) {
			if (thisCallUpdate
					&& activeTermConn.getCallControlTermConnState() != 98
					&& activeTermConn.getCallControlTermConnState() != 103)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "Terminal connection not active. It's state is "
								+ activeTermConn.getCallControlTermConnState());

			heldTermConn = otherCall.findHeldTermConnection(activeTermConn
					.getTSDevice());
			if (heldTermConn == null)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "no held terminal connection found");

		} else if (thisCallUpdate && otherCallUpdate) {
			if (activeTermConn.getCallControlTermConnState() != 98
					&& activeTermConn.getCallControlTermConnState() != 103)
				throw new TsapiInvalidStateException(
						3,
						0,
						TsapiCreateObject.getTsapiObject(this, false),
						1,
						state,
						"the state of the active terminal connection is not TALKING or UNKNOWN; its state is "
								+ activeTermConn.getCallControlTermConnState());

			if (heldTermConn.getCallControlTermConnState() != 99
					&& heldTermConn.getCallControlTermConnState() != 103)
				throw new TsapiInvalidStateException(
						3,
						0,
						TsapiCreateObject.getTsapiObject(this, false),
						1,
						state,
						"the state of the held terminal connection is not HELD or UNKNOWN; its state is "
								+ heldTermConn.getCallControlTermConnState());

			if (!activeTermConn.getTSDevice().getTermConns()
					.contains(heldTermConn))
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state,
						"the held terminal connection is not associated with the device ("
								+ activeTermConn.getTSDevice()
								+ ") of the active terminal connection");

		}

		final ConfHandler handler = new ConfXferConfHandler(this, otherCall, 12);
		try {
			provider.tsapi.conferenceCall(heldTermConn.getConnID(),
					activeTermConn.getConnID(), reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"conferenceCall failure");
			throw new TsapiPlatformException(4, 0, "conferenceCall failure");
		}
	}

	public Vector<TSConnection> connect(final TSDevice device,
			final String destAddress, final CSTAPrivate reqPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidArgumentException, TsapiInvalidPartyException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getMakeCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		if (state != 32)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call not idle");

		final String devName = device.getName();

		if ((provider.getCapabilities().getSnapshotCallReq() == 0 || monitorPending)
				&& internalDeviceMonitor == null)
			try {
				internalDeviceMonitor = device.setInternalMonitor(this);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSCall.log.error(e.getMessage(), e);
			}

		final MakeCallConfHandler handler = new MakeCallConfHandler(this,
				device, destAddress, 24);
		try {
			if (provider.getDeviceExt(destAddress) == 1) {
				recordVDNDomainEntry(destAddress);

				final TSDevice tsDevice = provider.createDevice(destAddress);
				tsDevice.setMonitor(false, true);
			}
			provider.tsapi.makeCall(devName, destAddress, reqPriv, handler);
			TSCall.log.info("TSCall.connect: finished makeCall for Call ID "
					+ callID);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidPartyException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"makeCall failure");
			throw new TsapiPlatformException(4, 0, "makeCall failure");
		}

		TSCall.log
				.info("TSCall.connect: about to call connectFinish() for callID "
						+ callID);
		if (handOffCall != null)
			return handOffCall.connectFinish(device, destAddress,
					handler.newCall);

		return connectFinish(device, destAddress, handler.newCall);
	}

	Vector<TSConnection> connectFinish(final TSDevice device,
			final String destAddress, final CSTAConnectionID newCall) {
		if (device.isMonitorSet())
			needSnapshot = false;
		else {
			TSCall.log.info("TSCall.connect: calling doSnapshot() for callID "
					+ callID);

			doSnapshot(newCall, null, false);
		}

		TSCall.log
				.info("TSCall.connect: about to wait for 2 connections for callID "
						+ callID);

		synchronized (connections) {
			if (connections.size() < 2) {
				setConnection_wait_limit(2);
				if (!waitForConnections("connect", 2) && state != 34) {
					TSCall.log.info("failed to get 2 connections for call ID "
							+ callID);

					throw new TsapiPlatformException(4, 0,
							"Could not meet post-conditions of connect()");
				}
			}
		}

		return connections;
	}

	public Vector<TSConnection> connectPredictive(final TSDevice device,
			final String dialedDigits, final int connectionState,
			final int maxRings, final int answeringTreatment,
			final int answeringEndpointType, final String destRoute,
			final boolean priorityCall, final UserToUserInfo userInfo,
			CSTAPrivate reqPriv) throws TsapiPrivilegeViolationException,
			TsapiInvalidArgumentException, TsapiInvalidPartyException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getMakePredictiveCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		if (state != 32)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call not idle");

		final String devName = device.getName();
		short allocState;
		switch (connectionState) {
		case 50:
			allocState = 0;
			break;
		case 51:
			allocState = 1;
			break;
		default:
			throw new TsapiInvalidArgumentException(0, 0,
					"invalid connectionState");
		}
		if (provider.isLucent()) {
			short answerTreat;
			switch (answeringTreatment) {
			case 1:
				answerTreat = 1;
				break;
			case 2:
				answerTreat = 2;
				break;
			case 3:
				answerTreat = 3;
				break;
			case 4:
				answerTreat = 0;
				break;
			default:
				throw new TsapiInvalidArgumentException(0, 0,
						"invalid answeringTreatment");
			}
			final LucentMakePredictiveCall lmc = createLucentMakePredictiveCall(
					priorityCall, maxRings, answerTreat, destRoute, userInfo);

			reqPriv = lmc.makeTsapiPrivate();
		}

		final MakeCallConfHandler handler = new MakeCallConfHandler(this,
				device, dialedDigits, 26);
		try {
			provider.tsapi.makePredictiveCall(devName, dialedDigits,
					allocState, reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidPartyException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"makePredictiveCall failure");
			throw new TsapiPlatformException(4, 0, "makePredictiveCall failure");
		}

		final boolean hasPredictiveCallMonitor = device
				.isPredictiveCallsViaDeviceMonitorSet();

		if (handOffCall != null)
			return handOffCall.connectPredictiveFinish(handler.newCall,
					hasPredictiveCallMonitor);

		return connectPredictiveFinish(handler.newCall,
				hasPredictiveCallMonitor);
	}

	Vector<TSConnection> connectPredictiveFinish(final CSTAConnectionID connID,
			final boolean hasPredictiveCallMonitor) {
		if (!hasPredictiveCallMonitor)
			try {
				monitorCall(connID, true);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSCall.log.error(e.getMessage(), e);
			}

		synchronized (connections) {
			if (connections.size() < 2
					&& !waitForConnections("connectPredictive", 2)
					&& state != 34) {
				TSCall.log.error("failed to get 2 connections for call ID "
						+ callID);

				throw new TsapiPlatformException(4, 0,
						"Could not meet post-conditions of connectPredictive()");
			}
		}

		return connections;
	}

	void considerAddingVDNMonitorCallObservers(final Object monitored) {
		if (!(monitored instanceof TSDevice))
			return;
		final TSDevice monitoredTSDevice = (TSDevice) monitored;
		if (!monitoredTSDevice.isPredictiveCallsViaDeviceMonitorSet())
			return;
		addDeviceObservers(monitoredTSDevice, null, null,
				monitoredTSDevice.callsViaAddressMonitorThreads, true);
	}

	public Vector<TSConnection> consult(final TSConnection termconn,
			final String address, final CSTAPrivate reqPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getConsultationCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		if (state != 32)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call not idle");

		final boolean otherCallUpdate = termconn.getTSCall().updateObject();
		if (otherCallUpdate && termconn.getTSCall().state != 33)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(termconn.getTSCall(),
							false), 1, state, "other call not active");

		if (otherCallUpdate && termconn.getCallControlTermConnState() != 98
				&& termconn.getCallControlTermConnState() != 103)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(termconn, false), 5,
					state, "terminal connection not talking");

		if ((provider.getCapabilities().getSnapshotCallReq() == 0 || monitorPending)
				&& internalDeviceMonitor == null)
			try {
				internalDeviceMonitor = termconn.getTSDevice()
						.setInternalMonitor(this);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSCall.log.error(e.getMessage(), e);
			}

		final MakeCallConfHandler handler = new MakeCallConfHandler(this,
				termconn.getTSDevice(), address, 14);
		try {
			provider.tsapi.consultationCall(termconn.getConnID(), address,
					reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"consultationCall failure");
			throw new TsapiPlatformException(4, 0, "consultationCall failure");
		}

		if (handOffCall != null)
			return handOffCall
					.consultFinish(termconn, address, handler.newCall);

		return consultFinish(termconn, address, handler.newCall);
	}

	Vector<TSConnection> consultFinish(final TSConnection termconn,
			final String address, final CSTAConnectionID newCall) {
		final Vector<TSEvent> eventList = new Vector<TSEvent>();
		termconn.setTermConnState(99, eventList);
		if (eventList.size() > 0) {
			final Vector<TsapiCallMonitor> observers = termconn.getTSCall()
					.getObservers();
			for (int j = 0; j < observers.size(); ++j) {
				final TsapiCallMonitor callback = (TsapiCallMonitor) observers
						.elementAt(j);
				callback.deliverEvents(eventList, 100, false);
			}

		}

		if (termconn.getTSDevice().isMonitorSet())
			needSnapshot = false;
		else
			doSnapshot(newCall, null, false);

		synchronized (connections) {
			if (connections.size() < 2) {
				try {
					connections.wait(Tsapi.getCallCompletionTimeout());
				} catch (final InterruptedException e) {
				}
				if (connections.size() < 2 && state != 34) {
					TSCall.log.error("failed to get 2 connections for call ID "
							+ callID);

					throw new TsapiPlatformException(4, 0,
							"Could not meet post-conditions of consult()");
				}
			}
		}
		return connections;
	}

	void copyStuff(final TSCall otherCall) {
		if (!callIsInVDNDomain(otherCall))
			return;
		final Vector<TsapiCallMonitor> observers = otherCall.getCallObservers();

		for (int i = 0; i < observers.size(); ++i)
			if (((TsapiCallMonitor) observers.elementAt(i)).isVDN()
					&& !monitorThreads.contains(observers.elementAt(i))) {
				monitorThreads.addElement((TsapiCallMonitor) observers
						.elementAt(i));
				((TsapiCallMonitor) observers.elementAt(i)).addReference();
			} else
				otherCall.removeCallMonitor((TsapiCallMonitor) observers
						.elementAt(i));
	}

	private LucentMakePredictiveCall createLucentMakePredictiveCall(
			final boolean priorityCall, final int maxRings,
			final short answerTreat, final String destRoute,
			final UserToUserInfo userInfo) {
		final LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (provider.isLucentV6())
			return new LucentV6MakePredictiveCall(priorityCall, maxRings,
					answerTreat, destRoute, asn_uui);
		return new LucentMakePredictiveCall(priorityCall, maxRings,
				answerTreat, destRoute, asn_uui);
	}

	synchronized void delete() {
		TSCall.log.info("Call object= " + this + " being deleted" + " for "
				+ provider);

		if (!delayVDNremoveCallFromDomain)
			provider.removeCallFromDomain(this);

		if (internalDeviceMonitor != null) {
			internalDeviceMonitor.removeInternalMonitor(this);
			internalDeviceMonitor = null;
		}

		if (nonCallID != -1)
			provider.deleteNonCallFromHash(nonCallID);

		if (callID == 0)
			return;
		provider.deleteCallFromHash(callID);
		provider.addCallToSaveHash(this);
		synchronized (staleConnections) {
			for (int i = 0; i < staleConnections.size(); ++i)
				((TSConnection) staleConnections.elementAt(i)).delete();
		}
	}

	void doCallbackSnapshots(final Vector<TSEvent> eventList, final int cause) {
		CallbackAndType cbAndType = null;
		synchronized (callbackAndTypeVector) {
			try {
				for (int i = 0; i < callbackAndTypeVector.size(); ++i) {
					cbAndType = (CallbackAndType) callbackAndTypeVector
							.elementAt(i);
					if (cbAndType.devWithType == null)
						synchronized (monitorThreads) {
							if (!monitorThreads.contains(cbAndType.callback)) {
								monitorThreads.addElement(cbAndType.callback);
								cbAndType.callback.addReference();
								sendSnapshot(cbAndType.callback, eventList,
										false, cause);
							}
						}
					else {
						boolean found = false;
						DeviceObs devObs = null;
						synchronized (deviceObsVector) {
							for (int j = 0; j < deviceObsVector.size(); ++j) {
								devObs = (DeviceObs) deviceObsVector
										.elementAt(j);
								if (devObs.callback != cbAndType.callback)
									continue;
								found = true;
								break;
							}

							if (!found) {
								devObs = new DeviceObs(cbAndType.callback);
								devObs.devWithTypeVector
										.addElement(cbAndType.devWithType);
								deviceObsVector.addElement(devObs);
								cbAndType.callback.addReference();
								sendSnapshot(cbAndType.callback, eventList,
										false, cause);
							}
						}

					}

				}

			} finally {
				callbackAndTypeVector.removeAllElements();
			}
		}
		if (!checkForMonitors())
			return;
		needSnapshot = false;
	}

	boolean doHeldTalkingMatch(final TSCall otherCall) {
		TSConnection conn = null;
		TSConnection termConn = null;
		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				conn = (TSConnection) connections.elementAt(i);
				final Vector<TSConnection> termConns = conn.getTermConns();
				if (termConns == null)
					continue;
				synchronized (termConns) {
					for (int j = 0; j < termConns.size(); ++j) {
						termConn = (TSConnection) termConns.elementAt(j);
						if (termConn.getCallControlTermConnState() != 99
								|| otherCall.findActiveTermConnection(termConn
										.getTSDevice()) == null)
							continue;
						return true;
					}
				}

			}

		}

		return false;
	}

	boolean doSnapshot(final CSTAConnectionID connID,
			final SnapshotCallExtraConfHandler extraHandler,
			final boolean waitForSnapshotConf) {
		return doSnapshot(connID, extraHandler, waitForSnapshotConf, 110);
	}

	boolean doSnapshot(final CSTAConnectionID connID,
			final SnapshotCallExtraConfHandler extraHandler,
			final boolean waitForSnapshotConf, final int cause) {
		if (!needSnapshot) {
			try {
				if (extraHandler != null)
					extraHandler.handleConf(true, null, null);
			} finally {
				doCallbackSnapshots(null, cause);
			}
			return true;
		}

		if (provider.getCapabilities().getSnapshotCallReq() == 0) {
			try {
				if (extraHandler != null)
					extraHandler.handleConf(false, null, null);
			} finally {
				doCallbackSnapshots(null, cause);
			}
			return false;
		}

		if (!waitForSnapshotConf)
			synchronized (callbackAndTypeVector) {
				if (futureAsynchronousSnapshotHandler != null) {
					futureAsynchronousSnapshotHandler
							.addExtraHandler(extraHandler);
					return true;
				}
			}

		final SnapshotCallConfHandler handler = new SnapshotCallConfHandler(
				this, extraHandler, waitForSnapshotConf);

		synchronized (handler) {
			snapshotCallConfPending = true;
			provider.tsapi.snapshotCall(connID, null, handler);

			if (waitForSnapshotConf) {
				try {
					handler.wait(TSProviderImpl.TSAPI_RESPONSE_TIME);
				} catch (final InterruptedException e) {
				}
				if (!handler.handled) {
					try {
						if (extraHandler != null)
							extraHandler.handleConf(false, null, null);
					} finally {
						doCallbackSnapshots(null, cause);
					}
					return false;
				}
				return handler.rc;
			}
		}

		return true;
	}

	public void drop(final CSTAPrivate reqPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getClearCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		if (updateObject() && state != 33)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call is not active");

		CSTAConnectionID clearConnID = null;
		synchronized (connections) {
			if (connections.size() > 0)
				clearConnID = ((TSConnection) connections.elementAt(0))
						.getConnID();
		}
		if (clearConnID == null)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call is not active (no connections)");

		final ConfHandler handler = new ClearCallConfHandler(this);
		try {
			provider.tsapi.clearCall(clearConnID, reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			setState(34, null);
			endCVDObservers(100, null);
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"clearCall failure");
			throw new TsapiPlatformException(4, 0, "clearCall failure");
		}
	}

	void dump(final String indent) {
		TSCall.log.trace(indent + "***** CALL DUMP *****");
		TSCall.log.trace(indent + "TSCall: " + this);
		TSCall.log.trace(indent + "TSCall ID: " + callID);
		TSCall.log.trace(indent + "TSCall UCID: " + ucid);
		TSCall.log.trace(indent + "TSCall non-Call ID: " + nonCallID);
		TSCall.log.trace(indent + "TSCall state: " + state);
		TSCall.log.trace(indent + "TSCall needSnapshot: " + needSnapshot);
		TSCall.log.trace(indent + "TSCall age: " + my_age);
		TSCall.log.trace(indent + "TSCall connections: ");
		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				final TSConnection conn = (TSConnection) connections
						.elementAt(i);
				conn.dump(indent + " ");
			}
		}
		TSCall.log.trace(indent + "TSCall trunks: ");
		synchronized (trkVector) {
			for (int i = 0; i < trkVector.size(); ++i) {
				final TSTrunk trk = (TSTrunk) trkVector.elementAt(i);
				trk.dump(indent + " ");
			}
		}
		TSCall.log.trace(indent + "TSCall handOffCall: " + handOffCall);
		TSCall.log.trace(indent + "TSCall stale connections: ");
		synchronized (staleConnections) {
			for (int i = 0; i < staleConnections.size(); ++i) {
				final TSConnection conn = (TSConnection) staleConnections
						.elementAt(i);
				conn.dump(indent + " ");
			}
		}
		TSCall.log.trace(indent + "TSCall trunks: ");
		TSCall.log.trace(indent + "TSCall Monitor Threads: ");
		synchronized (monitorThreads) {
			for (int i = 0; i < monitorThreads.size(); ++i) {
				final TsapiCallMonitor oThreads = (TsapiCallMonitor) monitorThreads
						.elementAt(i);
				oThreads.dump(indent + " ");
			}
		}
		TSCall.log.trace(indent + "TSCall Device Monitor Threads: ");
		synchronized (deviceObsVector) {
			for (int i = 0; i < deviceObsVector.size(); ++i) {
				final TsapiCallMonitor oThreads = ((DeviceObs) deviceObsVector
						.elementAt(i)).callback;
				oThreads.dump(indent + " ");
			}
		}
		TSCall.log.trace(indent + "TSCall Stale Monitor Threads: ");
		synchronized (staleObsVector) {
			for (int i = 0; i < staleObsVector.size(); ++i) {
				final TsapiCallMonitor oThreads = (TsapiCallMonitor) staleObsVector
						.elementAt(i);
				oThreads.dump(indent + " ");
			}
		}
		TSCall.log.trace(indent + "TSCall CallbackAndType Monitor Threads: ");
		synchronized (callbackAndTypeVector) {
			CallbackAndType cbAndType = null;
			int i;
			for (i = 0; i < callbackAndTypeVector.size(); ++i) {
				cbAndType = (CallbackAndType) callbackAndTypeVector
						.elementAt(i);
				final TsapiCallMonitor oThreads = cbAndType.callback;
				oThreads.dump(indent + " ");
			}

		}

		int i = 0;
		for (final String str : LucentUserToUserInfo.print(
				TsapiPromoter.demoteUserToUserInfo(getUUI()), "CallUUI", indent
						+ " ")) {
			if (i == 0)
				TSCall.log.trace(indent + "TSCALL UUI" + str);
			else
				TSCall.log.trace(str);
			++i;
		}
		TSCall.log.trace(indent + "***** CALL DUMP END *****");
	}

	void endCVDObservers(final int cause, final Object privateData) {
		for (int i = 0; i < staleConnections.size(); ++i) {
			final TSDevice dev = ((TSConnection) staleConnections.elementAt(i))
					.getTSDevice();
			if (dev == null)
				continue;
			final Vector<TsapiCallMonitor> cvd = dev.getCVDObservers();
			for (int j = 0; j < cvd.size(); ++j) {
				final TsapiCallMonitor obs = (TsapiCallMonitor) cvd
						.elementAt(j);
				removeCallMonitor(obs, cause, privateData);
			}

			dev.testDelete();
		}
	}

	void endNonCVDObservers(final int cause) {
		if (monitorCrossRefID != 0 && wasEverMonitoredByCallsViaDevice != true)
			return;
		removeObservers(cause, null, 0);
	}

	public Vector<TSConnection> fastConnect(final TSDevice device,
			final String destAddress, final CSTAPrivate reqPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidArgumentException, TsapiInvalidPartyException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getMakeCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		if (state != 32)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call not idle");

		final String devName = device.getName();

		if ((provider.getCapabilities().getSnapshotCallReq() == 0 || monitorPending)
				&& internalDeviceMonitor == null)
			try {
				internalDeviceMonitor = device.setInternalMonitor(this);
			} catch (final Exception e) {
			}

		setConnection_wait_limit(1);

		final MakeCallConfHandler handler = new MakeCallConfHandler(this,
				device, destAddress, 24);
		try {
			provider.tsapi.makeCall(devName, destAddress, reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidPartyException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"makeCall failure");

			throw new TsapiPlatformException(4, 0, "makeCall failure");
		}

		synchronized (connections) {
			if (handOffCall != null)
				return handOffCall.fastConnectFinish(device, destAddress,
						handler.newCall);

			return fastConnectFinish(device, destAddress, handler.newCall);
		}
	}

	Vector<TSConnection> fastConnectFinish(final TSDevice device,
			final String destAddress, final CSTAConnectionID newCall) {
		if (device.isMonitorSet())
			needSnapshot = false;
		else
			doSnapshot(newCall, null, false);

		synchronized (connections) {
			if (connections.size() < 1) {
				try {
					connections.wait(Tsapi.getCallCompletionTimeout());
				} catch (final InterruptedException e) {
				}

				if (connections.size() < 1 && state != 34) {
					TSCall.log
							.info("after succesfully initiating, fastConnect returns null since found a DISCONNECTED originating Connection for call ID "
									+ callID);
					return null;
				}
			}
		}

		return connections;
	}

	TSConnection findActiveTermConnection(final TSDevice matchDevice) {
		TSConnection conn = null;
		TSConnection termConn = null;

		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				conn = (TSConnection) connections.elementAt(i);
				final Vector<TSConnection> termConns = conn.getTermConns();
				if (termConns == null)
					continue;
				synchronized (termConns) {
					for (int j = 0; j < termConns.size(); ++j) {
						termConn = (TSConnection) termConns.elementAt(j);
						if (termConn.getCallControlTermConnState() != 98
								&& termConn.getCallControlTermConnState() != 103
								|| termConn.getTSDevice() != matchDevice)
							continue;

						return termConn;
					}
				}
			}

		}

		return null;
	}

	TSConnection findHeldTermConnection(final TSDevice matchDevice) {
		TSConnection conn = null;
		TSConnection termConn = null;

		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				conn = (TSConnection) connections.elementAt(i);
				final Vector<TSConnection> termConns = conn.getTermConns();
				if (termConns == null)
					continue;
				synchronized (termConns) {
					for (int j = 0; j < termConns.size(); ++j) {
						termConn = (TSConnection) termConns.elementAt(j);
						if (termConn.getCallControlTermConnState() != 99
								&& termConn.getCallControlTermConnState() != 103
								|| termConn.getTSDevice() != matchDevice)
							continue;

						return termConn;
					}
				}

			}

		}

		return null;
	}

	TSConnection findOtherConnection(final TSConnection conn) {
		synchronized (connections) {
			final int size = connections.size();
			if (size != 2)
				return null;
			TSConnection otherConn = null;
			for (int i = 0; i < size; ++i) {
				otherConn = (TSConnection) connections.elementAt(i);
				if (conn != otherConn)
					return otherConn;
			}
			return null;
		}
	}

	TSConnection findTSConnectionForDevice(final TSDevice device) {
		final Vector<TSConnection> clonedConnsToCheck = new Vector<TSConnection>(
				connections);

		for (int j = 0; j < clonedConnsToCheck.size(); ++j) {
			final TSConnection conn = (TSConnection) clonedConnsToCheck
					.elementAt(j);
			final Vector<TSConnection> cv = conn.getTermConns();
			if (cv != null && cv.size() > 0) {
				final Vector<TSConnection> termConns = new Vector<TSConnection>(
						cv);
				for (int k = 0; k < termConns.size(); ++k) {
					final TSConnection tc = (TSConnection) termConns
							.elementAt(k);
					if (tc.getTSDevice() == device)
						return tc;

				}

			} else if (conn.getTSDevice() == device)
				return conn;

		}

		return null;
	}

	public long getCallClearedTransferReceiptTime() {
		return callClearedTransferReceiptTime;
	}

	public TSDevice getCalledDevice() {
		return calledDevice;
	}

	public int getCallID() {
		return callID;
	}

	public TSDevice getCallingAddress() {
		return callingAddress;
	}

	public TSDevice getCallingTerminal() {
		return callingTerminal;
	}

	public Vector<TsapiCallMonitor> getCallObservers() {
		final Vector<TsapiCallMonitor> allObservers = getObservers();
		synchronized (callbackAndTypeVector) {
			CallbackAndType cbAndType = null;
			for (int i = 0; i < callbackAndTypeVector.size(); ++i) {
				cbAndType = (CallbackAndType) callbackAndTypeVector
						.elementAt(i);
				final TsapiCallMonitor obs = cbAndType.callback;
				if (allObservers.contains(obs))
					continue;
				allObservers.addElement(obs);
			}
		}

		return allObservers;
	}

	public int getCallOriginatorType() {
		if (hasCallOriginatorType())
			return callOriginatorInfo.getCallOriginatorType();
		return -1;
	}

	public TSConnection getConfController() {
		return confController;
	}

	public boolean getConfEnable() {
		return confEnable;
	}

	TSConnection getConnAtDevice(final TSDevice matchDevice) {
		synchronized (connections) {
			TSConnection conn = null;
			for (int i = 0; i < connections.size(); ++i) {
				conn = (TSConnection) connections.elementAt(i);
				if (conn.getTSDevice() == matchDevice)
					return conn;
			}
			return null;
		}
	}

	private int getConnection_wait_limit() {
		return connection_wait_limit;
	}

	Vector<TSConnection> getConnections() {
		return connections;
	}

	public short getCSTACause() {
		return cstaCause;
	}

	public TSDevice getDeliveringACDDevice() {
		return deliveringACDDevice;
	}

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		return deviceHistory;
	}

	public String getDigits() {
		return digits;
	}

	public TSDevice getDistributingDevice() {
		return distributingDevice;
	}

	public TSDevice getDistributingVDN() {
		return distributingVDN;
	}

	public int getDomainCallID() {
		return getCallID();
	}

	public TSCall getHandOff() {
		if (handOffCall != null)
			return handOffCall;

		return this;
	}

	public LookaheadInfo getLAI() {
		return lai;
	}

	public TSDevice getLastRedirectionDevice() {
		return lastRedirectionDevice;
	}

	private String getMyCustomString() {
		if (callID == 0)
			return "[0(nonCallID=" + nonCallID + ")]";

		return "[" + callID + "]";
	}

	boolean getNeedRedoSnapshotCall() {
		return needRedoSnapshotCall;
	}

	int getNonCallID() {
		return nonCallID;
	}

	Vector<TsapiCallMonitor> getObservers() {
		final Vector<TsapiCallMonitor> allObservers = new Vector<TsapiCallMonitor>(
				monitorThreads);
		synchronized (deviceObsVector) {
			for (int i = 0; i < deviceObsVector.size(); ++i)
				if (!allObservers.contains(((DeviceObs) deviceObsVector
						.elementAt(i)).callback))
					allObservers.addElement(((DeviceObs) deviceObsVector
							.elementAt(i)).callback);
		}
		synchronized (staleObsVector) {
			for (int i = 0; i < staleObsVector.size(); ++i)
				if (!allObservers.contains(staleObsVector.elementAt(i)))
					allObservers.addElement(staleObsVector.elementAt(i));
		}
		return allObservers;
	}

	public OriginalCallInfo getOCI() {
		return oci;
	}

	public Object getPrivateData() {
		if (replyPriv instanceof CSTAPrivate)
			return replyPriv;
		return null;
	}

	public LucentQueryUcidConfEvent getQueryUCIDConf() {
		Object lquConf = null;
		try {
			final LucentQueryUcid lqu = new LucentQueryUcid(
					new CSTAConnectionID(callID, "", (short) 0));
			lquConf = provider.sendPrivateData(lqu.makeTsapiPrivate());
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"getUCIDConf failure, exception: " + e);

			throw new TsapiPlatformException(4, 0,
					"getUCIDConf failure, exception: " + e);
		}

		return (LucentQueryUcidConfEvent) lquConf;
	}

	public short getReason() {
		return reason;
	}

	void getSnapshot(final Vector<TSEvent> eventList) {
		if (eventList == null)
			return;

		switch (state) {
		case 33:
			eventList.addElement(new TSEvent(4, this));
			break;
		case 34:
			eventList.addElement(new TSEvent(5, this));
		}

		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i)
				((TSConnection) connections.elementAt(i))
						.getSnapshot(eventList);
		}
	}

	boolean getSnapshotCallConfPending() {
		return snapshotCallConfPending;
	}

	public int getState() {
		updateObject();

		return state;
	}

	int getStateFromServer() {
		if (!updateSuspiciousObject())
			throw new TsapiPlatformException(4, 0,
					"Could not get state from the telephony server. [CallId="
							+ getCallID() + "]");
		return state;
	}

	public TsapiCallCapabilities getTsapiCallCapabilities() {
		return provider.getTsapiCallCapabilities();
	}

	public Vector<TSConnection> getTSConnections() {
		updateObject();

		final Vector<TSConnection> tsConnections = new Vector<TSConnection>();

		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				final TSConnection tsConn = (TSConnection) connections
						.elementAt(i);
				if (tsConn.getTSDevice().getDeviceType() == 2
						&& tsConn.getACDManagerConn() != null)
					continue;
				tsConnections.addElement(tsConn);
			}

		}

		return tsConnections;
	}

	public TSProviderImpl getTSProviderImpl() {
		return provider;
	}

	int getTSState() {
		return state;
	}

	public Vector<TSTrunk> getTSTrunks() {
		return trkVector;
	}

	public String getUCID() {
		if (ucid == null)
			setUCID(queryUCID());

		return ucid;
	}

	public UserEnteredCode getUEC() {
		return uec;
	}

	public UserToUserInfo getUUI() {
		return uui;
	}

	public TSConnection getXferController() {
		return xferController;
	}

	public boolean getXferEnable() {
		return xferEnable;
	}

	public boolean hasCallOriginatorType() {
		return callOriginatorInfo != null;
	}

	public boolean hasReceivedCallClearedTransfer() {
		return receivedCallClearedTransfer;
	}

	boolean isMonitorSet() {
		return monitorCrossRefID != 0 || provider.isCallInAnyDomain(this);
	}

	void monitorCall(final CSTAConnectionID connID,
			final boolean waitForSnapshotConf)
			throws TsapiResourceUnavailableException {
		if (provider.getCapabilities().getMonitorCall() == 0 || connID == null)
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"no capability to monitor call");

		if (monitorCrossRefID != 0)
			return;
		CSTAEvent event;
		try {
			event = provider.tsapi.monitorCall(connID, new CSTAMonitorFilter(),
					null);
		} catch (final TsapiUnableToSendException tue) {
			throw tue;
		} catch (final Exception e) {
			TSCall.log.error("MonitorCall request failed - retrying");
			try {
				event = provider.tsapi.monitorCall(connID,
						new CSTAMonitorFilter(), null);
			} catch (final TsapiResourceUnavailableException e1) {
				throw e1;
			} catch (final Exception e1) {
				throw new TsapiResourceUnavailableException(0, 0, 0,
						"monitor call failure");
			}
		}

		final CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
				.getEvent();

		final SnapshotCallExtraConfHandler handler = new AddCallMonitorHandler(
				this, monitorConf.getMonitorCrossRefID());
		if (needSnapshot)
			doSnapshot(connID, handler, waitForSnapshotConf);
		else
			handler.handleConf(true, null, null);
	}

	protected void moveInternalStuff(final TSCall otherCall) {
		TsapiCallMonitor callback = null;

		synchronized (otherCall.monitorThreads) {
			for (int i = 0; i < otherCall.monitorThreads.size(); ++i) {
				callback = (TsapiCallMonitor) otherCall.monitorThreads
						.elementAt(i);
				synchronized (monitorThreads) {
					if (!monitorThreads.contains(callback))
						monitorThreads.addElement(callback);
				}
			}
		}
		DeviceObs devObs = null;
		DeviceObs otherDevObs = null;
		DevWithType devWithType = null;

		synchronized (otherCall.deviceObsVector) {
			for (int i = 0; i < otherCall.deviceObsVector.size(); ++i) {
				otherDevObs = (DeviceObs) otherCall.deviceObsVector
						.elementAt(i);
				i = 0;
				synchronized (deviceObsVector) {
					for (int j = 0; j < deviceObsVector.size(); ++j) {
						devObs = (DeviceObs) deviceObsVector.elementAt(j);
						if (otherDevObs.callback != devObs.callback)
							continue;
						i = 1;
						synchronized (otherDevObs.devWithTypeVector) {
							for (int k = 0; k < otherDevObs.devWithTypeVector
									.size(); ++k) {
								devWithType = (DevWithType) otherDevObs.devWithTypeVector
										.elementAt(k);
								synchronized (devObs.devWithTypeVector) {
									if (!devObs.devWithTypeVector
											.contains(devWithType))
										devObs.devWithTypeVector
												.addElement(devWithType);
								}
							}
						}
						break;
					}

					if (i == 0)
						deviceObsVector.addElement(otherDevObs);
				}
			}
		}

		CallbackAndType otherCbAndType = null;
		CallbackAndType cbAndType = null;
		synchronized (otherCall.callbackAndTypeVector) {
			for (int i = 0; i < otherCall.callbackAndTypeVector.size(); ++i) {
				otherCbAndType = (CallbackAndType) otherCall.callbackAndTypeVector
						.elementAt(i);
				int j = 0;
				synchronized (callbackAndTypeVector) {
					for (j = 0; j < callbackAndTypeVector.size(); ++j) {
						cbAndType = (CallbackAndType) callbackAndTypeVector
								.elementAt(j);
						if (!otherCbAndType.callback.equals(cbAndType.callback)
								|| !otherCbAndType.devWithType
										.equals(cbAndType.devWithType))
							continue;
						j = 1;
						break;
					}

					if (j == 0)
						callbackAndTypeVector.addElement(otherCbAndType);
				}
			}
		}

		synchronized (otherCall) {
			synchronized (this) {
				if (!monitorPending)
					monitorPending = otherCall.monitorPending;
				if (internalDeviceMonitor == null)
					internalDeviceMonitor = otherCall.internalDeviceMonitor;
			}
		}

		moveStuff(otherCall);
	}

	void moveStuff(final TSCall otherCall) {
		if (otherCall == null)
			return;

		if (callIsInVDNDomain(otherCall)) {
			final Vector<TsapiCallMonitor> observers = otherCall
					.getCallObservers();

			for (int i = 0; i < observers.size(); ++i)
				if (((TsapiCallMonitor) observers.elementAt(i)).isVDN()
						&& !monitorThreads.contains(observers.elementAt(i))) {
					monitorThreads.addElement((TsapiCallMonitor) observers
							.elementAt(i));
					((TsapiCallMonitor) observers.elementAt(i)).addReference();
				} else
					otherCall.removeCallMonitor((TsapiCallMonitor) observers
							.elementAt(i));
		} else
			otherCall.removeObservers(100, null, 0);

		if (callingAddress == null)
			callingAddress = otherCall.callingAddress;
		if (callingTerminal == null)
			callingTerminal = otherCall.callingTerminal;
		if (calledDevice == null)
			calledDevice = otherCall.calledDevice;
		if (lastRedirectionDevice == null)
			lastRedirectionDevice = otherCall.lastRedirectionDevice;
		if (confController == null)
			confController = otherCall.confController;
		if (xferController == null)
			xferController = otherCall.xferController;
		if (uui == null)
			uui = otherCall.uui;
		if (lai == null)
			lai = otherCall.lai;
		if (uec == null)
			uec = otherCall.uec;
		if (oci == null)
			oci = otherCall.oci;
		if (getDeviceHistory() == null)
			setDeviceHistory(otherCall.getDeviceHistory());

		replyPriv = otherCall.replyPriv;
		confEnable = otherCall.confEnable;
		xferEnable = otherCall.xferEnable;

		TSTrunk trk = null;
		synchronized (otherCall.trkVector) {
			for (int i = 0; i < otherCall.trkVector.size(); ++i) {
				trk = (TSTrunk) otherCall.trkVector.elementAt(i);
				trk.setCall(this, null);
				synchronized (trkVector) {
					if (!trkVector.contains(trk))
						trkVector.addElement(trk);
				}
			}
		}

		if (checkForMonitors())
			return;
		setNeedSnapshot(true);
	}

	boolean needsSnapshot() {
		return needSnapshot;
	}

	public void notifyCallAdded(final IDomainDevice d) {
		recordVDNDomainEntry(d.getDomainName());
	}

	public void notifyCallRemoved(final IDomainDevice d) {
		recordVDNDomainExit();
	}

	void processCallbackSnapshots(final int cause) {
		boolean doDoSnapshot = false;
		synchronized (callbackAndTypeVector) {
			if (callbackAndTypeVector.size() > 0
					&& currentSnapshotHandler == null)
				doDoSnapshot = true;
		}
		if (!doDoSnapshot)
			return;
		CSTAConnectionID snapConnID = null;
		synchronized (connections) {
			if (connections.size() > 0)
				try {
					snapConnID = ((TSConnection) connections.elementAt(0))
							.getConnID();
				} catch (final TsapiPlatformException e) {
					TSCall.log.error("Ignoring exception: " + e);
					if (callID != 0)
						snapConnID = new CSTAConnectionID(callID, "", (short) 0);
				}
			else if (callID != 0)
				snapConnID = new CSTAConnectionID(callID, "", (short) 0);
		}
		if (snapConnID != null)
			doSnapshot(snapConnID, null, false, cause);
	}

	String queryUCID() {
		if (!provider.isLucentV5())
			return null;
		final LucentQueryUcidConfEvent lquConf = getQueryUCIDConf();

		if (lquConf != null)
			return lquConf.getUcid();
		return null;
	}

	void recordVDNDomainEntry(final String vdn_domain_we_are_entering) {
		TSCall.log
				.info("recordVDNDomainEntry: -- entering VDN domain for Address "
						+ vdn_domain_we_are_entering
						+ " - wasEverMonitoredByCallsViaDevice="
						+ wasEverMonitoredByCallsViaDevice
						+ " refVDN="
						+ refVDN);

		String found_name = null;

		wasEverMonitoredByCallsViaDevice = true;

		if (refVDN != null) {
			found_name = refVDN.getName();
			if (!found_name.equals(vdn_domain_we_are_entering))
				recordVDNDomainExit();

		}

		if (refVDN != null)
			return;

		final TSDevice tsDevice = provider
				.createDevice(vdn_domain_we_are_entering);
		refVDN = (TsapiAddress) TsapiCreateObject
				.getTsapiObject(tsDevice, true);
	}

	void recordVDNDomainExit() {
		TSCall.log
				.info("recordVDNDomainExit: -- leaving VDN domain for Address "
						+ refVDN);

		refVDN = null;
	}

	public void referenced() {
		refCount += 1;
	}

	public void removeCallMonitor(final TsapiCallMonitor obs) {
		removeCallMonitor(obs, 100, null);
	}

	protected void removeCallMonitor(final TsapiCallMonitor obs,
			final int cause, final Object privateData) {
		CallbackAndType cbAndType = null;
		synchronized (callbackAndTypeVector) {
			for (int i = 0; i < callbackAndTypeVector.size(); ++i) {
				cbAndType = (CallbackAndType) callbackAndTypeVector
						.elementAt(i);
				if (cbAndType.callback != obs)
					continue;
				if (cbAndType.devWithType == null)
					synchronized (monitorThreads) {
						if (!monitorThreads.contains(cbAndType.callback)) {
							monitorThreads.addElement(cbAndType.callback);
							cbAndType.callback.addReference();
						}
					}
				else {
					boolean found = false;
					DeviceObs devObs = null;
					synchronized (deviceObsVector) {
						for (int j = 0; j < deviceObsVector.size(); ++j) {
							devObs = (DeviceObs) deviceObsVector.elementAt(j);
							if (devObs.callback != cbAndType.callback)
								continue;
							found = true;
							break;
						}

						if (!found) {
							devObs = new DeviceObs(cbAndType.callback);
							devObs.devWithTypeVector
									.addElement(cbAndType.devWithType);
							deviceObsVector.addElement(devObs);
							cbAndType.callback.addReference();
						}
					}
				}
				callbackAndTypeVector.removeElement(cbAndType);
			}

		}

		synchronized (monitorThreads) {
			if (monitorThreads.removeElement(obs)) {
				obs.deleteReference(this, false, cause, privateData);
				if (monitorThreads.isEmpty() && monitorCrossRefID != 0) {
					provider.deleteMonitor(monitorCrossRefID);
					if (provider.getCapabilities().getMonitorStop() != 0)
						try {
							provider.tsapi.monitorStop(monitorCrossRefID, null,
									null);
						} catch (final TsapiUnableToSendException tue) {
							throw tue;
						} catch (final Exception e) {
							TSCall.log.error(e.getMessage(), e);
						}

					monitorCrossRefID = 0;

					if (!checkForMonitors())
						setNeedSnapshot(true);
				}

			} else {
				synchronized (deviceObsVector) {
					for (int i = 0; i < deviceObsVector.size(); ++i) {
						if (((DeviceObs) deviceObsVector.elementAt(i)).callback != obs)
							continue;
						deviceObsVector.removeElementAt(i);
						obs.deleteReference(this, false, cause, privateData);
						return;
					}

				}

				if (staleObsVector.removeElement(obs)) {
					obs.deleteReference(this, false, cause, privateData);
					return;
				}
			}
		}
	}

	void removeConnection(final TSConnection tsConn,
			final Vector<TSEvent> eventList) {
		if (!connections.removeElement(tsConn))
			return;

		if (confController == tsConn)
			confController = null;
		if (xferController == tsConn)
			xferController = null;

		synchronized (staleConnections) {
			if (!staleConnections.contains(tsConn))
				staleConnections.addElement(tsConn);
		}
		if (checkForMonitors())
			return;
		setNeedSnapshot(true);
	}

	void removeDefaultDeviceObservers(final TSDevice tsDevice,
			final boolean isTerminal) {
		final Vector<DeviceObs> cbKeepVector = new Vector<DeviceObs>();
		DeviceObs devObs = null;
		final DevWithType devWithType = new DevWithType(tsDevice, isTerminal);

		synchronized (deviceObsVector) {
			for (int j = 0; j < deviceObsVector.size(); ++j) {
				devObs = (DeviceObs) deviceObsVector.elementAt(j);
				synchronized (devObs.devWithTypeVector) {
					if (devObs.devWithTypeVector.removeElement(devWithType)) {
						if (devObs.devWithTypeVector.isEmpty()
								&& !devObs.callback.isVDN())
							staleObsVector.addElement(devObs.callback);
						else
							cbKeepVector.addElement(devObs);

					} else
						cbKeepVector.addElement(devObs);
				}
			}
			deviceObsVector.clear();
			deviceObsVector.addAll(cbKeepVector);
		}

		if (internalDeviceMonitor == null
				|| !internalDeviceMonitor.equals(tsDevice))
			return;
		internalDeviceMonitor.removeInternalMonitor(this);
		internalDeviceMonitor = null;
		if (checkForMonitors())
			return;
		setNeedSnapshot(true);
	}

	void removeObservers(final int cause, final Object privateData,
			final int xrefID) {
		if (xrefID != 0 && monitorCrossRefID == xrefID) {
			provider.deleteMonitor(monitorCrossRefID);
			monitorCrossRefID = 0;
		}

		CallbackAndType cbAndType = null;
		synchronized (callbackAndTypeVector) {
			try {
				for (int i = 0; i < callbackAndTypeVector.size(); ++i) {
					cbAndType = (CallbackAndType) callbackAndTypeVector
							.elementAt(i);
					if (cbAndType.devWithType == null)
						synchronized (monitorThreads) {
							if (!monitorThreads.contains(cbAndType.callback)) {
								monitorThreads.addElement(cbAndType.callback);
								cbAndType.callback.addReference();
							}
						}
					else {
						boolean found = false;
						DeviceObs devObs = null;
						synchronized (deviceObsVector) {
							for (int j = 0; j < deviceObsVector.size(); ++j) {
								devObs = (DeviceObs) deviceObsVector
										.elementAt(j);
								if (devObs.callback != cbAndType.callback)
									continue;
								found = true;
								break;
							}

							if (!found) {
								devObs = new DeviceObs(cbAndType.callback);
								devObs.devWithTypeVector
										.addElement(cbAndType.devWithType);
								deviceObsVector.addElement(devObs);
								cbAndType.callback.addReference();
							}
						}

					}

				}

			} finally {
				callbackAndTypeVector.removeAllElements();
			}
		}

		final Vector<TsapiCallMonitor> observers = getCallObservers();
		for (int i = 0; i < observers.size(); ++i)
			removeCallMonitor((TsapiCallMonitor) observers.elementAt(i), cause,
					privateData);

		if (monitorCrossRefID == 0)
			return;
		provider.deleteMonitor(monitorCrossRefID);
		if (provider.getCapabilities().getMonitorStop() != 0)
			try {
				provider.tsapi.monitorStop(monitorCrossRefID, null, null);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSCall.log.error(e.getMessage(), e);
			}

		monitorCrossRefID = 0;

		if (checkForMonitors())
			return;
		setNeedSnapshot(true);
	}

	void removeTrunk(final TSTrunk trunk, final Vector<TSEvent> eventList) {
		if (!trkVector.removeElement(trunk))
			return;
		trunk.unsetCall(eventList);
	}

	void replaceConnections(final Vector<TSConnection> newConnections,
			final Vector<TSEvent> eventList) {
		for (int i = 0; i < newConnections.size(); ++i) {
			TSConnection conn = (TSConnection) newConnections.elementAt(i);
			if (conn.isTerminalConnection()) {
				conn = conn.getTSConn();
				newConnections.setElementAt(conn, i);
			}

			addConnection(conn, null);
		}

		final Vector<TSConnection> conns = new Vector<TSConnection>(connections);
		for (int i = 0; i < conns.size(); ++i) {
			final TSConnection conn = (TSConnection) conns.elementAt(i);
			if (newConnections.contains(conn))
				continue;
			conn.setConnectionState(89, eventList);
		}
	}

	private CSTAConnectionID selectConnectionIdForAddParty() {
		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				final CSTAConnectionID connID = ((TSConnection) connections
						.elementAt(i)).getConnID();

				if (connID == null)
					continue;

				if (connID.getDevIDType() == 0)
					return connID;

				if (connID.getDevIDType() != 1)
					continue;

				final String deviceID = connID.getDeviceID();
				if (deviceID.regionMatches(true, 0, "T", 0, 1))
					return connID;

			}

			if (connections.size() > 0)
				return ((TSConnection) connections.elementAt(0)).getConnID();

			return null;
		}
	}

	public Object sendPrivateData(final CSTAPrivate data) {
		try {
			return provider.sendPrivateData(data);
		} catch (final Exception e) {
			throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
		}
	}

	void sendSnapshot(final TsapiCallMonitor callback,
			final Vector<TSEvent> _eventList, final boolean snapMetaCode) {
		sendSnapshot(callback, _eventList, snapMetaCode, 110);
	}

	void sendSnapshot(final TsapiCallMonitor callback,
			final Vector<TSEvent> _eventList, final boolean snapMetaCode,
			final int cause) {
		if (callback == null)
			return;

		Vector<TSEvent> eventList = null;

		if (_eventList == null) {
			eventList = new Vector<TSEvent>();
			getSnapshot(eventList);
		} else
			eventList = _eventList;

		setCSTACause((short) -1);

		if (eventList.size() <= 0)
			return;
		callback.deliverEvents(eventList, cause, snapMetaCode);
	}

	public void setBillRate(final short billType, final float billRate)
			throws TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException {
		if (!provider.isLucentV5())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		switch (billType) {
		case 16:
		case 17:
		case 18:
		case 19:
		case 24:
			break;
		case 20:
		case 21:
		case 22:
		case 23:
		default:
			throw new TsapiInvalidArgumentException(0, 0, "invalid billType");
		}
		try {
			final CSTAConnectionID connID = new CSTAConnectionID(callID, "",
					(short) 0);

			final LucentSetBillRate sbr = new LucentSetBillRate(connID,
					billType, billRate);
			provider.sendPrivateData(sbr.makeTsapiPrivate());
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"setBillRate failure, exception: " + e);

			throw new TsapiPlatformException(4, 0,
					"setBillRate failure, exception: " + e);
		}
	}

	void setCalledDevice(final TSDevice _calledDevice) {
		if (_calledDevice == null)
			return;
		calledDevice = _calledDevice;
	}

	synchronized void setCallID(final int newCallID) {
		if (newCallID == 0) {
			final int rc = provider.getNonCallID();
			if (rc != -1) {
				nonCallID = rc;
				provider.addNonCallToHash(this);
			}
		} else if (callID == 0) {
			callID = newCallID;
			if (nonCallID != -1) {
				provider.deleteNonCallFromHash(nonCallID);
				provider.releaseNonCallID(nonCallID);
				nonCallID = -1;
			}

			final TSCall tmpCall = provider.addCallToHash(this);
			if (tmpCall != null)
				if (ucid != null && tmpCall.ucid != null
						&& ucid.compareTo(tmpCall.ucid) == 0) {
					handOffCall = tmpCall;

					provider.addCallToHash(handOffCall);

					synchronized (connections) {
						for (int i = 0; i < connections.size(); ++i) {
							final TSConnection conn = (TSConnection) connections
									.elementAt(i);
							conn.setCall(handOffCall);
							handOffCall.addConnection(conn, null);
						}
					}

					handOffCall.moveInternalStuff(this);
				} else {
					tmpCall.setState(34, null);
					provider.dumpCall(tmpCall.getCallID());
					handOffCall = null;

					TSCall.log
							.info("Mismatched UCID for setCallID removing stale call obj "
									+ tmpCall);
					TSCall.log.info("UCID for setCallID for the new call is "
							+ ucid);

					provider.addCallToHash(this);
				}
		} else {
			if (newCallID == callID)
				return;
			provider.changeCallIDInDomain(callID, newCallID);

			provider.deleteCallFromHash(callID);

			final int saveCallID = callID;
			callID = newCallID;

			final TSCall saveCall = provider.addCallToHash(this);

			if (saveCall == null)
				return;
			saveCall.callID = saveCallID;
			provider.addCallToHash(saveCall);

			final Vector<TSConnection> conns = new Vector<TSConnection>(
					saveCall.connections);
			for (int i = 0; i < conns.size(); ++i) {
				final TSConnection conn = (TSConnection) conns.elementAt(i);
				final Vector<TSConnection> cv = conn.getTermConns();
				if (cv != null && cv.size() > 0) {
					final Vector<TSConnection> termConns = new Vector<TSConnection>(
							cv);
					for (int j = 0; j < termConns.size(); ++j) {
						final TSConnection tc = (TSConnection) termConns
								.elementAt(j);
						final CSTAConnectionID connID = tc.getConnID();
						connID.setCallID(saveCallID);
						tc.setConnID(connID);
					}
				} else {
					final CSTAConnectionID connID = conn.getConnID();
					connID.setCallID(saveCallID);
					conn.setConnID(connID);
				}
			}
			moveInternalStuff(saveCall);
		}
	}

	void setCallingDevices(final TSDevice _callingDevice) {
		if (_callingDevice == null)
			return;
		if (_callingDevice.isTerminal())
			callingTerminal = _callingDevice;
		else
			callingTerminal = null;
		callingAddress = _callingDevice;
	}

	void setCallOriginatorInfo(final CSTACallOriginatorInfo _callOriginatorInfo) {
		callOriginatorInfo = _callOriginatorInfo;
	}

	public void setConfController(final TSConnection termconn)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		if (updateObject()) {
			if (state != 33)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "call is not active");

			if (termconn.getCallControlTermConnState() == 102)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(termconn, false), 5,
						state, "terminal connection is dropped");

			boolean contains = false;

			synchronized (connections) {
				for (int i = 0; i < connections.size(); ++i) {
					final TSConnection conn = (TSConnection) connections
							.elementAt(i);
					final Vector<TSConnection> termConns = conn.getTermConns();
					if (termConns == null || !termConns.contains(termconn))
						continue;
					contains = true;
					break;
				}
			}

			if (!contains)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "terminal connection is not in this call");

		}

		confController = termconn;
	}

	public void setConfEnable(final boolean enable) {
		confEnable = enable;
	}

	private void setConnection_wait_limit(final int connection_wait_limit) {
		this.connection_wait_limit = connection_wait_limit;
	}

	void setCSTACause(final short cause) {
		cstaCause = cause;
	}

	void setDeliveringACDDevice(final TSDevice _deliveringACDDevice) {
		if (_deliveringACDDevice == null)
			return;
		deliveringACDDevice = _deliveringACDDevice;
	}

	void setDeviceHistory(final LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = TsapiPromoter.promoteDeviceHistory(deviceHistory);
	}

	void setDeviceHistory(final V7DeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}

	void setDigits(final String _digits) {
		digits = _digits;
	}

	void setDistributingDevice(final TSDevice _distributingDevice) {
		if (_distributingDevice == null)
			return;
		distributingDevice = _distributingDevice;
	}

	void setDistributingVDN(final TSDevice _distributingVDN) {
		if (_distributingVDN == null)
			return;
		distributingVDN = _distributingVDN;
	}

	void setFlexibleBilling(final boolean _flexibleBilling) {
		flexibleBilling = _flexibleBilling;
	}

	void setLAI(final LookaheadInfo _lai) {
		if (_lai == null)
			return;
		lai = _lai;
	}

	void setLAI(final LucentLookaheadInfo _lai) {
		if (_lai == null)
			return;
		lai = TsapiPromoter.promoteLookaheadInfo(_lai);
	}

	void setLastRedirectionDevice(final TSDevice _lastRedirectionDevice) {
		if (_lastRedirectionDevice == null)
			return;
		lastRedirectionDevice = _lastRedirectionDevice;
	}

	void setMonitor(final boolean waitForSnapshotConf)
			throws TsapiResourceUnavailableException {
		CSTAConnectionID monConnID = null;
		if (!isMonitorSet())
			synchronized (connections) {
				if (connections.size() > 0)
					try {
						monConnID = ((TSConnection) connections.elementAt(0))
								.getConnID();
					} catch (final TsapiPlatformException e) {
						TSCall.log.error("Ignoring exception: " + e);
						if (callID != 0)
							monConnID = new CSTAConnectionID(callID, "",
									(short) 0);
					}
				else if (callID != 0)
					monConnID = new CSTAConnectionID(callID, "", (short) 0);
			}
		if (monConnID != null)
			monitorCall(monConnID, waitForSnapshotConf);
	}

	void setNeedRedoSnapshotCall(final boolean flag) {
		needRedoSnapshotCall = flag;
	}

	public synchronized void setNeedSnapshot(final boolean flag) {
		needSnapshot = flag;
	}

	void setOCI(final LucentOriginalCallInfo _oci) {
		if (_oci == null)
			return;
		oci = TsapiPromoter.promoteOriginalCallInfo(provider, _oci);
	}

	void setOCI(final OriginalCallInfo _oci) {
		if (_oci == null)
			return;
		oci = _oci;
	}

	void setReason(final short _reason) {
		reason = _reason;
	}

	public void setReceivedCallClearedTransfer(
			final boolean receivedCallClearedTransfer) {
		this.receivedCallClearedTransfer = receivedCallClearedTransfer;
		callClearedTransferReceiptTime = System.currentTimeMillis();
	}

	void setSnapshotCallConfPending(final boolean flag) {
		snapshotCallConfPending = flag;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void setState(final int _state, final Vector<TSEvent> eventList) {
		synchronized (this) {
			if (state == _state || state == 34)
				return;

			state = _state;
		}

		switch (state) {
		case 33:
			if (eventList != null)
				eventList.addElement(new TSEvent(4, this));

			boolean tryMonitor = false;
			synchronized (this) {
				if (monitorPending) {
					tryMonitor = true;
					monitorPending = false;
				}
			}
			if (!tryMonitor)
				return;
			try {
				setMonitor(false);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSCall.log.error(e.getMessage(), e);
				removeObservers(107, null, 0);
			}
			break;
		case 34:
			Vector<TSTrunk> trkVectorClone = (Vector) trkVector.clone();
			int i;
			for (i = 0; i < trkVectorClone.size(); ++i) {
				TSTrunk tmpTrunk = (TSTrunk) trkVectorClone.elementAt(i);

				synchronized (tmpTrunk) {
					if (trkVector.removeElement(tmpTrunk))
						tmpTrunk.setState(1, eventList);
				}
				tmpTrunk = null;
			}
			trkVectorClone.removeAllElements();
			trkVectorClone = null;

			final Vector<TSConnection> conn = new Vector<TSConnection>(
					connections);
			for (i = 0; i < conn.size(); ++i)
				((TSConnection) conn.elementAt(i)).setConnectionState(89,
						eventList);
			if (eventList != null)
				eventList.addElement(new TSEvent(5, this));

			needSnapshot = false;

			synchronized (connections) {
				connections.notify();
			}

			delete();
		}
	}

	void setStateForVDN() {
		provider.removeCallFromDomain(this);
	}

	void setUCID(final String _ucid) {
		if (_ucid == null)
			return;
		ucid = _ucid;
	}

	void setUEC(final LucentUserEnteredCode _uec) {
		if (_uec == null)
			return;
		uec = TsapiPromoter.promoteUserEnteredCode(provider, _uec);
	}

	void setUEC(final UserEnteredCode _uec) {
		if (_uec == null)
			return;
		uec = _uec;
	}

	void setUUI(final LucentUserToUserInfo _uui) {
		if (_uui == null)
			return;
		uui = TsapiPromoter.promoteUserToUserInfo(_uui);
	}

	void setUUI(final UserToUserInfo _uui) {
		if (_uui == null)
			return;
		uui = _uui;
	}

	public void setXferController(final TSConnection termconn)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		if (updateObject()) {
			if (state != 33)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "call is not active");

			if (termconn.getCallControlTermConnState() == 102)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(termconn, false), 5,
						102, "terminal connection is dropped");

			boolean contains = false;

			synchronized (connections) {
				for (int i = 0; i < connections.size(); ++i) {
					final TSConnection conn = (TSConnection) connections
							.elementAt(i);
					final Vector<TSConnection> termConns = conn.getTermConns();
					if (termConns == null || !termConns.contains(termconn))
						continue;
					contains = true;
					break;
				}
			}

			if (!contains)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "terminal connection is not in this call");

		}

		xferController = termconn;
	}

	public void setXferEnable(final boolean enable) {
		xferEnable = enable;
	}

	void staleObsCleanup(final int cause) {
		synchronized (staleObsVector) {
			for (int i = 0; i < staleObsVector.size(); ++i)
				((TsapiCallMonitor) staleObsVector.elementAt(i))
						.deleteReference(this, false, cause, null);

			staleObsVector.removeAllElements();
		}
	}

	public String toString() {
		return "TSCall" + getMyCustomString() + "@"
				+ Integer.toHexString(super.hashCode());
	}

	public TSConnection transfer(final String xferDestAddress,
			final CSTAPrivate reqPriv) throws TsapiPrivilegeViolationException,
			TsapiInvalidArgumentException, TsapiInvalidPartyException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getTransferCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"transfer unsupported by driver");

		if (!provider.getTsapiCallCapabilities().canTransfer((String) null))
			throw new TsapiMethodNotSupportedException(4, 0,
					"transfer(String) unsupported by driver");

		final boolean thisCallUpdate = updateObject();

		if (thisCallUpdate && state != 33)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call not active");

		if (!xferEnable)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"transfer disabled");

		final TSConnection activeTermConn = xferController;

		if (activeTermConn == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"transfer(String) with null TransferController not supported");

		if (thisCallUpdate
				&& activeTermConn.getCallControlTermConnState() != 98
				&& activeTermConn.getCallControlTermConnState() != 103)
			throw new TsapiInvalidStateException(
					3,
					0,
					TsapiCreateObject.getTsapiObject(this, false),
					2,
					state,
					"transfer controller terminal connection not in valid state (TALKING or UNKNOWN)");

		setConnection_wait_limit(1);

		final LucentSingleStepTransferCall sst = new LucentSingleStepTransferCall(
				activeTermConn.getConnID(), xferDestAddress);

		final ConfXferConfHandler handler = new ConfXferConfHandler(this, null,
				90);
		try {
			provider.sendPrivateData(sst.makeTsapiPrivate(), handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidPartyException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"transfer(String) failure");
			throw new TsapiPlatformException(4, 0, "transfer(String) failure: "
					+ e);
		}

		return provider.getConnection(handler.newCall);
	}

	public void transfer(final TSCall otherCall, final CSTAPrivate reqPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidArgumentException, TsapiInvalidPartyException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getTransferCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		final boolean thisCallUpdate = updateObject();
		final boolean otherCallUpdate = otherCall.updateObject();

		if (thisCallUpdate && state != 33)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"call not active");

		if (!xferEnable || !otherCall.xferEnable)
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1, state,
					"transfer disabled");

		TSConnection activeTermConn = xferController;
		TSConnection heldTermConn = otherCall.xferController;
		if (activeTermConn == null) {
			if (heldTermConn == null) {
				TSConnection conn = null;
				TSConnection termConn = null;
				boolean found = false;
				synchronized (connections) {
					for (int i = 0; i < connections.size(); ++i) {
						conn = (TSConnection) connections.elementAt(i);
						final Vector<TSConnection> termConns = conn
								.getTermConns();
						if (termConns == null)
							continue;
						final Vector<TSConnection> tcs = new Vector<TSConnection>(
								termConns);
						for (int j = 0; j < tcs.size(); ++j) {
							termConn = (TSConnection) tcs.elementAt(j);
							if (termConn.getCallControlTermConnState() != 98
									&& termConn.getCallControlTermConnState() != 103)
								continue;
							activeTermConn = termConn;

							heldTermConn = otherCall
									.findHeldTermConnection(activeTermConn
											.getTSDevice());
							if (heldTermConn == null)
								continue;
							found = true;
							break;
						}

						if (found)
							break;
					}
				}
				if (activeTermConn == null)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "no active terminal connection found");

				if (heldTermConn == null)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "no held terminal connection found");

			} else {
				if (otherCallUpdate
						&& heldTermConn.getCallControlTermConnState() != 99
						&& heldTermConn.getCallControlTermConnState() != 103)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "terminal connection not held");

				activeTermConn = findActiveTermConnection(heldTermConn
						.getTSDevice());
				if (activeTermConn == null)
					throw new TsapiInvalidStateException(3, 0,
							TsapiCreateObject.getTsapiObject(this, false), 1,
							state, "no active terminal connection found");

			}

		} else if (heldTermConn == null) {
			if (thisCallUpdate
					&& activeTermConn.getCallControlTermConnState() != 98
					&& activeTermConn.getCallControlTermConnState() != 103)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "terminal connection not active. It's state is "
								+ activeTermConn.getCallControlTermConnState());

			heldTermConn = otherCall.findHeldTermConnection(activeTermConn
					.getTSDevice());
			if (heldTermConn == null)
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state, "no held terminal connection found");

		} else if (thisCallUpdate && otherCallUpdate) {
			if (activeTermConn.getCallControlTermConnState() != 98
					&& activeTermConn.getCallControlTermConnState() != 103)
				throw new TsapiInvalidStateException(
						3,
						0,
						TsapiCreateObject.getTsapiObject(this, false),
						1,
						state,
						"the state of the active terminal connection is not TALKING or UNKNOWN; its state is "
								+ activeTermConn.getCallControlTermConnState());

			if (heldTermConn.getCallControlTermConnState() != 99
					&& heldTermConn.getCallControlTermConnState() != 103)
				throw new TsapiInvalidStateException(
						3,
						0,
						TsapiCreateObject.getTsapiObject(this, false),
						1,
						state,
						"the state of the held terminal connection is not HELD or UNKNOWN; its state is "
								+ heldTermConn.getCallControlTermConnState());

			if (!activeTermConn.getTSDevice().getTermConns()
					.contains(heldTermConn))
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(this, false), 1,
						state,
						"the held terminal connection is not associated with the device ("
								+ activeTermConn.getTSDevice()
								+ ") of the active terminal connection");

		}

		final ConfHandler handler = new ConfXferConfHandler(this, otherCall, 52);
		try {
			provider.tsapi.transferCall(heldTermConn.getConnID(),
					activeTermConn.getConnID(), reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidPartyException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPrivilegeViolationException e) {
			throw e;
		} catch (final TsapiResourceUnavailableException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"transferCall failure");
			throw new TsapiPlatformException(4, 0, "transferCall failure");
		}
	}

	public void unreferenced() {
		refCount -= 1;
		if (refCount > 0 || callID != 0)
			return;
		setState(34, null);
		removeObservers(100, null, 0);
	}

	void updateConnectionCallIDs(final int newCallID) {
		final Vector<TSConnection> clonedConnectionsToUpdate = new Vector<TSConnection>(
				connections);

		for (int j = 0; j < clonedConnectionsToUpdate.size(); ++j) {
			final TSConnection conn = (TSConnection) clonedConnectionsToUpdate
					.elementAt(j);
			final Vector<TSConnection> cv = conn.getTermConns();
			if (cv != null && cv.size() > 0) {
				final Vector<TSConnection> termConns = new Vector<TSConnection>(
						cv);
				for (int k = 0; k < termConns.size(); ++k) {
					final TSConnection tsc = (TSConnection) termConns
							.elementAt(k);
					tsc.updateConnIDCallID(newCallID);
				}

			} else
				conn.updateConnIDCallID(newCallID);
		}
	}

	boolean updateObject() {
		if (isMonitorSet() == true || state == 32 && callID == 0 || state == 34)
			return true;

		CSTAConnectionID snapConnID = null;
		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				final TSConnection conn = (TSConnection) connections
						.elementAt(i);
				if (conn.getTSDevice().isMonitorSet())
					return true;
				final Vector<TSConnection> termConns = conn.getTermConns();
				if (termConns == null)
					continue;
				synchronized (termConns) {
					for (int j = 0; j < termConns.size(); ++j) {
						final TSConnection termconn = (TSConnection) termConns
								.elementAt(j);
						if (!termconn.getTSDevice().isMonitorSet())
							continue;
						return true;
					}

				}

			}

			if (connections.size() > 0)
				try {
					snapConnID = ((TSConnection) connections.elementAt(0))
							.getConnID();
				} catch (final TsapiPlatformException e) {
					TSCall.log.error("Ignoring exception: " + e);
					if (callID != 0)
						snapConnID = new CSTAConnectionID(callID, "", (short) 0);
				}
		}
		if (snapConnID != null)
			return doSnapshot(snapConnID, null, true);
		if (callID != 0) {
			snapConnID = new CSTAConnectionID(callID, "", (short) 0);
			return doSnapshot(snapConnID, null, true);
		}

		return false;
	}

	boolean updateSuspiciousObject() {
		if (callID != 0) {
			TSCall.log.info("Mark " + this + " (callID " + callID
					+ ") for immediate and future snapshots for " + provider);

			needSnapshot = true;

			final CSTAConnectionID snapConnID = new CSTAConnectionID(callID,
					"", (short) 0);
			return doSnapshot(snapConnID, null, true);
		}

		return false;
	}

	boolean waitForConnections(final String forWhom,
			final int requiredMinNumberOfConnections) {
		if (connections.size() >= requiredMinNumberOfConnections) {
			TSCall.log.info("waitForConnections: called for " + forWhom
					+ ": had right# of connections on entry, for call ID "
					+ callID);

			return true;
		}

		if (state == 34) {
			TSCall.log.info("waitForConnections: called for " + forWhom
					+ ": call was INVALID on entry, for call ID " + callID);

			return false;
		}

		final long startTime = System.currentTimeMillis();
		long currentTime = startTime;

		final long endTime = currentTime + Tsapi.getCallCompletionTimeout();
		while (true) {
			try {
				connections.wait(endTime - currentTime);
			} catch (final InterruptedException e) {
			}
			currentTime = System.currentTimeMillis();

			if (connections.size() >= requiredMinNumberOfConnections) {
				TSCall.log.info("waitForConnections: waiting for " + forWhom
						+ ": succeeded after waiting a total of "
						+ (currentTime - startTime) / 1000L
						+ " seconds, to get " + requiredMinNumberOfConnections
						+ " connections for call ID " + callID);

				return true;
			}

			if (currentTime >= endTime) {
				TSCall.log.info("waitForConnections: waited for " + forWhom
						+ ": Failed.  After waiting a total of "
						+ (currentTime - startTime) / 1000L
						+ " seconds, did not get "
						+ requiredMinNumberOfConnections
						+ " connections for call ID " + callID);

				return false;
			}

			if (state == 34) {
				TSCall.log.info("waitForConnections: waited for " + forWhom
						+ ": Failed.  After waiting a total of "
						+ (currentTime - startTime) / 1000L
						+ " seconds, the call went INVALID (ended) - call ID "
						+ callID);

				return false;
			}

			TSCall.log.info("waitForConnections: waiting for " + forWhom
					+ ": woke up after waiting a total of "
					+ (currentTime - startTime) / 1000L
					+ " seconds, and failed to see "
					+ requiredMinNumberOfConnections
					+ " connections for call ID " + callID + " - #connections="
					+ connections.size() + " - waiting again");
		}
	}
}
