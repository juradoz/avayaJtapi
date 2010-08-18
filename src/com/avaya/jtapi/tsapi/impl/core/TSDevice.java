package com.avaya.jtapi.tsapi.impl.core;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.telephony.callcontrol.CallControlForwarding;
import javax.telephony.capabilities.TerminalCapabilities;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplit;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplitConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLogin;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginResp;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentState;
import com.avaya.jtapi.tsapi.csta1.LucentQueryCallClassifier;
import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceName;
import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceNameConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSetAgentState;
import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5SetAgentState;
import com.avaya.jtapi.tsapi.impl.TsapiAddressCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiCallControlForwarding;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.TsapiTerminalCapabilities;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;

public final class TSDevice implements IDomainDevice {
	private static Logger log = Logger.getLogger(TSDevice.class);

	static int g_CreationCnt = 0;
	static int g_RefCnt = 0;

	public static boolean hasMixOfPublicAndPrivate(
			final Vector<CSTAExtendedDeviceID> deviceList) {
		if (deviceList.size() <= 1)
			return false;
		CSTAExtendedDeviceID tempDevID = (CSTAExtendedDeviceID) deviceList
				.elementAt(0);
		String type = null;

		if (tempDevID.hasPrivateDeviceIDType())
			type = "PRIVATE";
		else if (tempDevID.hasPublicDeviceIDType())
			type = "PUBLIC";
		else
			return true;
		final int i = 1;
		if (i < deviceList.size()) {
			tempDevID = (CSTAExtendedDeviceID) deviceList.elementAt(i);

			if (type.equals("PUBLIC") && !tempDevID.hasPublicDeviceIDType())
				return true;
			return type.equals("PRIVATE")
					&& !tempDevID.hasPrivateDeviceIDType();
		}

		return false;
	}

	private boolean wasReferenced = false;
	public static final short STATION = 0;
	public static final short VDN = 1;
	public static final short ACD = 2;
	Object obsSync;
	TSDeviceState curState;
	TSProviderImpl provider;
	int refCount = 0;
	private final Vector<CSTAExtendedDeviceID> devNameVector;
	int monitorDeviceXRefID = 0;

	int monitorCallsViaDeviceXRefID = 0;
	private boolean monitorPredictiveCallsViaDevice = false;
	private final Vector<TSConnection> connections;
	private final Vector<TSConnection> terminalConnections;
	private final Vector<TSCall> internalDeviceMonitors;
	private final Vector<TsapiTerminalMonitor> terminalMonitorThreads;
	private final Vector<TsapiAddressMonitor> addressMonitorThreads;
	private final Vector<TsapiCallMonitor> callsAtAddressMonitorThreads;
	final Vector<TsapiCallMonitor> callsViaAddressMonitorThreads;
	private final Vector<TsapiCallMonitor> callsAtTerminalMonitorThreads;
	TsapiRouteMonitor tsRouteCallback = null;
	int registerReqID;
	public Hashtable<Integer, TSRouteSession> sessionHash;
	int numQueued;
	private final Vector<TSAgent> tsAgentTermVector;
	private final Vector<TSAgent> tsACDVector;
	public boolean dndInitialized;
	public boolean mwiInitialized;
	public boolean forwardingInitialized;
	public boolean dndState;
	public int msgWaitingBits;
	private final Vector<TsapiCallControlForwarding> fwdVector;
	private boolean isATerminal;
	private boolean isExternal;
	Object replyAddrPriv = null;
	Object replyTermPriv = null;
	private short deviceType;

	boolean waitingForAgents = false;
	private boolean asyncInitializationComplete = false;
	boolean changesWereHeldPending;
	private String associatedDevice = null;

	private short associatedClass;

	TSDevice(final TSProviderImpl _provider, final CSTAExtendedDeviceID deviceID) {
		asyncInitializationComplete = false;

		provider = _provider;
		dndInitialized = false;
		mwiInitialized = false;
		forwardingInitialized = false;
		dndState = false;
		msgWaitingBits = 0;
		numQueued = 0;
		isATerminal = true;
		isExternal = false;
		deviceType = 0;
		TSDevice.g_CreationCnt += 1;

		devNameVector = new Vector<CSTAExtendedDeviceID>();
		devNameVector.addElement(deviceID);
		connections = new Vector<TSConnection>();
		terminalConnections = new Vector<TSConnection>();
		internalDeviceMonitors = new Vector<TSCall>();
		terminalMonitorThreads = new Vector<TsapiTerminalMonitor>();
		addressMonitorThreads = new Vector<TsapiAddressMonitor>();
		callsViaAddressMonitorThreads = new Vector<TsapiCallMonitor>();
		callsAtAddressMonitorThreads = new Vector<TsapiCallMonitor>();
		callsAtTerminalMonitorThreads = new Vector<TsapiCallMonitor>();
		tsACDVector = new Vector<TSAgent>();
		tsAgentTermVector = new Vector<TSAgent>();
		fwdVector = new Vector<TsapiCallControlForwarding>();

		curState = new TSDeviceStateActive();

		obsSync = new Object();

		TSDevice.log.info("Constructing device " + this + " with name "
				+ getName() + " for " + provider);
		try {
			if (provider.isLucent()
					&& getName().regionMatches(true, 0, "T", 0, 1)
					|| (!provider.isLucent() || deviceID.getDeviceIDType() != 55)
					&& (!provider.isLucent() || deviceID.getDeviceIDType() != 40)
					&& (!provider.isLucent() || deviceID.getDeviceIDType() != 0)) {
				setIsATerminal(false);
				setIsExternal(true);
				notifyAsyncInitializationComplete();
			} else if (provider.getCapabilities().getQueryDeviceInfo() != 0)
				provider.tsapi.queryDeviceInfoAsync(getName(), null,
						new AsyncQueryDeviceInfoConfHandler(this));
			else
				notifyAsyncInitializationComplete();
		} catch (final TsapiUnableToSendException tue) {
			throw tue;
		} catch (final Exception e) {
			setIsATerminal(false);
			setIsExternal(true);
			notifyAsyncInitializationComplete();
		}
	}

	public void addAddressCallMonitor(final TsapiCallMonitor obs,
			final boolean followCall) throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		addAddressCallMonitor(obs, followCall, false, null);
	}

	public void addAddressCallMonitor(final TsapiCallMonitor obs,
			final boolean followCall, final boolean predictive,
			final CSTAPrivate reqPriv)
			throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		recreate();

		if (followCall) {
			if (provider.getCapabilities().getMonitorCallsViaDevice() == 0)
				throw new TsapiMethodNotSupportedException(0, 0,
						"unsupported by driver");
			if (predictive && !provider.getMonitorCallsViaDevice())
				throw new TsapiMethodNotSupportedException(0, 0,
						"unsupported by driver");

		} else if (provider.getCapabilities().getMonitorDevice() == 0)
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");

		synchronized (obsSync) {
			if (callsViaAddressMonitorThreads.contains(obs))
				return;

			if (followCall) {
				if (callsAtAddressMonitorThreads.removeElement(obs)) {
					callsViaAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(true, true, predictive, reqPriv);
					} catch (final TsapiResourceUnavailableException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (final TsapiPlatformException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					}

				} else {
					callsViaAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(true, true, predictive, reqPriv);

						obs.addReference();
					} catch (final TsapiResourceUnavailableException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (final TsapiPlatformException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					}
				}

			} else
				synchronized (obsSync) {
					if (callsAtAddressMonitorThreads.contains(obs))
						return;

					callsAtAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(false, true);

						obs.addReference();
					} catch (final TsapiResourceUnavailableException e) {
						provider.removeCallMonitorThread(obs);
						callsAtAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (final TsapiPlatformException e) {
						provider.removeCallMonitorThread(obs);
						callsAtAddressMonitorThreads.removeElement(obs);
						throw e;
					}
				}

		}

		sendCallSnapshot(obs, false);
	}

	public void addAddressMonitor(final TsapiAddressMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0)
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");

		synchronized (obsSync) {
			if (addressMonitorThreads.contains(obs))
				return;

			addressMonitorThreads.addElement(obs);
			try {
				setMonitor(false, false);

				obs.addReference();
			} catch (final TsapiResourceUnavailableException e) {
				provider.removeAddressMonitorThread(obs);
				addressMonitorThreads.removeElement(obs);
				throw e;
			} catch (final TsapiPlatformException e) {
				provider.removeAddressMonitorThread(obs);
				addressMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendAddressSnapshot(obs);
	}

	void addConnection(final TSConnection tsConn) {
		recreate();

		boolean doTerminalObservers = false;
		boolean doAddressObservers = false;

		if (tsConn.isTerminalConnection() && isTerminal())
			synchronized (terminalConnections) {
				if (!terminalConnections.contains(tsConn)) {
					terminalConnections.addElement(tsConn);
					doTerminalObservers = true;
				}

			}

		if (!provider.isLucent() || !tsConn.isTerminalConnection())
			synchronized (connections) {
				if (!connections.contains(tsConn)) {
					connections.addElement(tsConn);
					doAddressObservers = true;
				}
			}

		if (!doTerminalObservers && !doAddressObservers)
			return;

		final TSCall call = tsConn.getTSCall();
		call
				.addDeviceObservers(this,
						doTerminalObservers ? callsAtTerminalMonitorThreads
								: null,
						doAddressObservers ? callsAtAddressMonitorThreads
								: null,
						doAddressObservers ? callsViaAddressMonitorThreads
								: null, true);
	}

	void addName(final CSTAExtendedDeviceID deviceID) {
		recreate();
		final String devName = getName();

		synchronized (devNameVector) {
			if (!devNameVector.contains(deviceID)) {
				TSDevice.log.info("Renaming device " + this + " with name "
						+ devName + " to " + deviceID + " for " + provider);
				devNameVector.addElement(deviceID);
			}
		}
	}

	public void addRouteMonitor(final TsapiRouteMonitor observer) {
		recreate();

		synchronized (obsSync) {
			registerRoute();

			tsRouteCallback = observer;
			tsRouteCallback.addReference();
		}
	}

	void addSession(final int routingCrossRefID,
			final TSRouteSession routeSession) {
		recreate();

		if (sessionHash == null)
			return;
		final Object oldObj = sessionHash.put(new Integer(routingCrossRefID),
				routeSession);
		if (oldObj != null)
			TSDevice.log.info("NOTICE: sessionHash.put() replaced " + oldObj
					+ " for " + this);
	}

	public void addTerminalCallMonitor(final TsapiCallMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0)
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");

		synchronized (obsSync) {
			if (callsAtTerminalMonitorThreads.contains(obs))
				return;

			callsAtTerminalMonitorThreads.addElement(obs);
			try {
				setMonitor(false, true);

				obs.addReference();
			} catch (final TsapiResourceUnavailableException e) {
				provider.removeCallMonitorThread(obs);
				callsAtTerminalMonitorThreads.removeElement(obs);
				throw e;
			} catch (final TsapiPlatformException e) {
				provider.removeCallMonitorThread(obs);
				callsAtTerminalMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendCallSnapshot(obs, true);
	}

	public void addTerminalMonitor(final TsapiTerminalMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0)
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");

		synchronized (obsSync) {
			if (terminalMonitorThreads.contains(obs))
				return;

			terminalMonitorThreads.addElement(obs);
			try {
				setMonitor(false, false);

				obs.addReference();
			} catch (final TsapiResourceUnavailableException e) {
				provider.removeTerminalMonitorThread(obs);
				terminalMonitorThreads.removeElement(obs);
				throw e;
			} catch (final TsapiPlatformException e) {
				provider.removeTerminalMonitorThread(obs);
				terminalMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendTerminalSnapshot(obs);
	}

	void addToACDVector(final TSAgent tsAgent) {
		recreate();

		tsACDVector.addElement(tsAgent);
	}

	void addToAgentTermVector(final TSAgent tsAgent) {
		recreate();

		tsAgentTermVector.addElement(tsAgent);
	}

	public TSAgent addTSAgent(final TSDevice tsACDDevice,
			final int initialState, int workMode, final int reasonCode,
			final String agentID, final String password, CSTAPrivate reqPriv)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		recreate();

		String acdName = null;

		if (tsACDDevice != null)
			acdName = tsACDDevice.getName();

		TSAgentKey agentKey = null;
		agentKey = new TSAgentKey(getName(), acdName, agentID);
		TSAgent tsAgent = null;
		tsAgent = provider.findAgent(agentKey);
		if (tsAgent != null)
			return tsAgent;

		if (initialState != 4 && initialState != 3 && initialState != 1)
			throw new TsapiInvalidArgumentException(3, 0,
					"initial state not valid");

		final int state = 1;

		boolean reqPrivPresent = false;
		if (provider.isLucent())
			if (reqPriv != null)
				reqPrivPresent = true;
			else {
				LucentSetAgentState lsas = null;
				if (workMode == 1)
					lsas = createLucentSetAgentState((short) 3);
				else if (workMode == 2)
					lsas = createLucentSetAgentState((short) 4);
				else if (initialState == 4) {
					workMode = 1;
					lsas = createLucentSetAgentState((short) 3);
				} else if (initialState == 3 || initialState == 1) {
					workMode = 0;

					lsas = createLucentSetAgentState((short) 1, reasonCode);
				}

				reqPriv = lsas.makeTsapiPrivate();
			}

		try {
			setTSAgent(acdName, state, agentID, password, reqPriv);

			tsAgent = provider.createAgent(agentKey, agentID, password);
			if (tsAgent.getInternalState() == 2) {
				provider.dumpAgent(agentKey);
				tsAgent = provider.createAgent(agentKey, agentID, password);
			}

			if (provider.isLucent()) {
				if (!reqPrivPresent)
					tsAgent.updateState(initialState, workMode, reasonCode,
							null);

			} else
				tsAgent.updateState(1, 0, 0, null);
			tsAgent.getState();
			return tsAgent;
		} catch (final TsapiInvalidStateException e) {
			if (acdName != null && e.getErrorType() == 2
					&& e.getErrorCode() == 21) {
				snapshotAgentsInTerminal(acdName, agentID);

				agentKey = new TSAgentKey(getName(), acdName, null);
				tsAgent = provider.findAgent(agentKey);
				if (tsAgent != null)
					return tsAgent;

				throw new TsapiPlatformException(4, 0, "add Agent failure");
			}
			if (e.getErrorType() == 2 && e.getErrorCode() == 22)
				throw new TsapiPlatformException(4, 0,
						"Agent is already logged into another Station");

			throw new TsapiPlatformException(4, 0, "add Agent failure");
		} catch (final TsapiPlatformException e) {
			if (acdName != null && e.getErrorType() == 2
					&& e.getErrorCode() == 1) {
				snapshotAgentsInTerminal(acdName, agentID);

				agentKey = new TSAgentKey(getName(), acdName, null);
				tsAgent = provider.findAgent(agentKey);
				if (tsAgent != null)
					return tsAgent;

				throw new TsapiPlatformException(4, 0,
						"There is already an Agent logged on at the station");
			}
			if (acdName == null && e.getErrorType() == 2
					&& e.getErrorCode() == 1)
				throw new TsapiPlatformException(4, 0,
						"There is already an Agent logged on at the station");
			if (e.getErrorType() == 2 && e.getErrorCode() == 0)
				throw new TsapiPlatformException(4, 0,
						"An attempt to log in an ACD agent with an incorrect password");
			if (e.getErrorType() == 2 && e.getErrorCode() == 12)
				throw new TsapiPlatformException(4, 0,
						"Invalid AgentId is specified");

			throw e;
		}
	}

	public void cancelForwarding(final CSTAPrivate reqPriv)
			throws TsapiInvalidStateException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetFwd() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		getForwarding();

		final CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];

		short forwardingType = 0;
		final boolean forwardingOn = false;

		synchronized (fwdVector) {
			for (int i = 0; i < fwdVector.size(); ++i) {
				final TsapiCallControlForwarding fwd = (TsapiCallControlForwarding) fwdVector
						.elementAt(i);
				final String forwardDN = fwd.getDestinationAddress();
				switch (fwd.getType()) {
				case 2:
					switch (fwd.getFilter()) {
					case 3:
						forwardingType = 4;
						break;
					case 2:
						forwardingType = 3;
						break;
					case 1:
						forwardingType = 1;
					}

					break;
				case 3:
					switch (fwd.getFilter()) {
					case 3:
						forwardingType = 6;
						break;
					case 2:
						forwardingType = 5;
						break;
					case 1:
						forwardingType = 2;
					}

					break;
				case 1:
					forwardingType = 0;
				}

				fwdArray[0] = new CSTAForwardingInfo(forwardingType,
						forwardingOn, forwardDN);
				final ConfHandler handler = new FwdConfHandler(this, fwdArray);
				try {
					provider.tsapi.setFwd(getName(), forwardingType,
							forwardingOn, forwardDN, reqPriv, handler);
				} catch (final TsapiInvalidStateException e) {
					throw e;
				} catch (final TsapiPlatformException e) {
					throw e;
				} catch (final Exception e) {
					if (e instanceof ITsapiException)
						throw new TsapiPlatformException(((ITsapiException) e)
								.getErrorType(), ((ITsapiException) e)
								.getErrorCode(), "setFwd failure");
					throw new TsapiPlatformException(4, 0, "setFwd failure");
				}
			}
		}
	}

	boolean cleanUCIDsInCallsInConnections() {
		boolean bfound = false;
		final Vector<TSConnection> conns = new Vector<TSConnection>(connections);
		for (int i = 0; i < conns.size(); ++i) {
			final TSConnection conn = (TSConnection) conns.elementAt(i);
			final TSCall call = conn.getTSCall();

			if (call.state == 34 || call.cleanUCIDInCall())
				continue;
			bfound = true;
		}

		return !bfound;
	}

	private void clearMonitorCallsViaDeviceAttributes() {
		monitorCallsViaDeviceXRefID = 0;
		monitorPredictiveCallsViaDevice = false;
	}

	public CallControlForwarding[] createForwarding() {
		TsapiCallControlForwarding[] tsapiInstructions;
		synchronized (fwdVector) {
			tsapiInstructions = new TsapiCallControlForwarding[fwdVector.size()];
			fwdVector.copyInto(tsapiInstructions);
		}
		if (tsapiInstructions.length == 0)
			return null;
		final CallControlForwarding[] instructions = new CallControlForwarding[tsapiInstructions.length];
		int i;
		for (i = 0; i < tsapiInstructions.length; ++i)
			if (tsapiInstructions[i].getFilter() == 1)
				instructions[i] = new CallControlForwarding(
						tsapiInstructions[i].getDestinationAddress(),
						tsapiInstructions[i].getType());
			else
				instructions[i] = new CallControlForwarding(
						tsapiInstructions[i].getDestinationAddress(),
						tsapiInstructions[i].getType(), tsapiInstructions[i]
								.getFilter() == 2);

		return instructions;
	}

	private LucentSetAgentState createLucentSetAgentState(final short workMode) {
		return createLucentSetAgentState(workMode, 0);
	}

	private LucentSetAgentState createLucentSetAgentState(final short workMode,
			final int reasonCode) {
		if (provider.isLucentV5())
			return new LucentV5SetAgentState(workMode, reasonCode);
		return new LucentSetAgentState(workMode);
	}

	void deleteSession(final int routingCrossRefID) {
		recreate();

		if (sessionHash == null)
			return;
		sessionHash.remove(new Integer(routingCrossRefID));
	}

	boolean doAddressSnapshot() {
		recreate();

		if (getDeviceType() == 1)
			return true;

		if (provider.getCapabilities().getQueryDnd() != 0) {
			final ConfHandler handler = new DNDConfHandler(this);
			try {
				provider.tsapi.queryDoNotDisturb(getName(), null, handler);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSDevice.log.error(e.getMessage(), e);
			}
		}
		if (provider.getCapabilities().getQueryMwi() != 0) {
			final ConfHandler handler = new MWIConfHandler(this);
			try {
				provider.tsapi.queryMsgWaitingInd(getName(), null, handler);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSDevice.log.error(e.getMessage(), e);
			}
		}
		if (provider.getCapabilities().getQueryFwd() != 0) {
			final ConfHandler handler = new FwdConfHandler(this);
			try {
				provider.tsapi.queryFwd(getName(), null, handler);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSDevice.log.error(e.getMessage(), e);
			}
		}
		snapshotAgentsInACD();

		return true;
	}

	boolean doCallSnapshot() {
		recreate();

		if (provider.getCapabilities().getSnapshotDeviceReq() == 0)
			return false;

		final SnapshotDeviceConfHandler handler = new SnapshotDeviceConfHandler(
				this);
		try {
			provider.tsapi.snapshotDevice(getName(), null, handler);
		} catch (final TsapiUnableToSendException tue) {
			throw tue;
		} catch (final Exception e) {
			TSDevice.log.error(e.getMessage(), e);
			return false;
		}

		final Vector<TSCall> newCalls = new Vector<TSCall>();

		if (handler.info != null) {
			TSCall call = null;
			for (int i = 0; i < handler.info.length; ++i) {
				try {
					call = provider.createCall(handler.info[i]
							.getCallIdentifier().getCallID());
					if (call.getTSState() == 34) {
						provider.dumpCall(handler.info[i].getCallIdentifier()
								.getCallID());
						call = provider.createCall(handler.info[i]
								.getCallIdentifier().getCallID());
					}
					call.doSnapshot(handler.info[i].getCallIdentifier(), null,
							true);
					if (isMonitorSet())
						call.setNeedSnapshot(false);

				} catch (final TsapiPlatformException e) {
					// break label215:
					break;
				}

				// label215:
				if (!newCalls.contains(call))
					newCalls.addElement(call);

			}

		}

		Vector<TSConnection> conns = new Vector<TSConnection>(connections);
		for (int i = 0; i < conns.size(); ++i) {
			final TSConnection conn = (TSConnection) conns.elementAt(i);
			if (newCalls.contains(conn.getTSCall()))
				continue;
			conn.setConnectionState(89, null);
		}

		conns = new Vector<TSConnection>(terminalConnections);
		for (int i = 0; i < conns.size(); ++i) {
			final TSConnection conn = (TSConnection) conns.elementAt(i);
			if (newCalls.contains(conn.getTSCall()))
				continue;
			conn.setTermConnState(102, null);
		}

		return true;
	}

	boolean doTerminalSnapshot() {
		recreate();

		if (!isTerminal())
			return true;

		snapshotAgentsInTerminal(null, null);

		return true;
	}

	@SuppressWarnings("unchecked")
	void dump(final String indent) {
		TSDevice.log.trace(indent + "***** DEVICE DUMP *****");
		TSDevice.log.trace(indent + "TSDevice: " + this);
		TSDevice.log.trace(indent + "TSDevice names: ");
		synchronized (devNameVector) {
			for (int i = 0; i < devNameVector.size(); ++i)
				TSDevice.log.trace(indent + devNameVector.elementAt(i));
		}

		TSDevice.log.trace(indent + "TSDevice connections: ");
		final Vector<TSConnection> connectionsClone = (Vector<TSConnection>) connections
				.clone();
		int i;
		for (i = 0; i < connectionsClone.size(); ++i) {
			final TSConnection conn = (TSConnection) connectionsClone
					.elementAt(i);
			conn.dump(indent + " ");
		}

		TSDevice.log.trace(indent + "TSDevice terminalConnections: ");
		final Vector<TSConnection> terminalConnectionsClone = (Vector<TSConnection>) terminalConnections
				.clone();
		TSConnection conn = null;
		for (i = 0; i < terminalConnectionsClone.size(); ++i) {
			conn = (TSConnection) terminalConnectionsClone.elementAt(i);
			conn.dump(indent + " ");
		}

		TSDevice.log.trace(indent + "TSDevice ACD Agents: ");
		final Vector<TSAgent> tsACDVectorClone = (Vector<TSAgent>) tsACDVector
				.clone();

		for (i = 0; i < tsACDVectorClone.size(); ++i) {
			final TSAgent agent = (TSAgent) tsACDVectorClone.elementAt(i);
			agent.dump(indent + " ");
		}

		TSDevice.log.trace(indent + "TSDevice Terminal Agents: ");
		synchronized (tsAgentTermVector) {
			TSAgent agent = null;
			for (i = 0; i < tsAgentTermVector.size(); ++i) {
				agent = (TSAgent) tsAgentTermVector.elementAt(i);
				agent.dump(indent + " ");
			}
		}
		if (sessionHash != null) {
			TSDevice.log.trace(indent + "TSDevice Route Sessions: ");
			synchronized (sessionHash) {
				final Enumeration<TSRouteSession> sessionEnum = sessionHash
						.elements();

				while (sessionEnum.hasMoreElements()) {
					TSRouteSession routeSession;
					try {
						routeSession = (TSRouteSession) sessionEnum
								.nextElement();
					} catch (final NoSuchElementException e) {
						TSDevice.log.error(e.getMessage(), e);
						continue;
					}

					routeSession.dump(indent + " ");
				}
			}
		}
		TSDevice.log.trace(indent + "TSDevice Terminal Monitor Threads: ");

		final Vector<TsapiTerminalMonitor> terminalMonitorThreadsClone = (Vector<TsapiTerminalMonitor>) terminalMonitorThreads
				.clone();
		int j;
		for (j = 0; j < terminalMonitorThreadsClone.size(); ++j) {
			final TsapiTerminalMonitor tOThreads = (TsapiTerminalMonitor) terminalMonitorThreadsClone
					.elementAt(j);
			tOThreads.dump(indent + " ");
		}

		TSDevice.log.trace(indent + "TSDevice Address Monitor Threads: ");

		final Vector<TsapiAddressMonitor> addressMonitorThreadsClone = (Vector<TsapiAddressMonitor>) addressMonitorThreads
				.clone();
		int k;
		for (k = 0; k < addressMonitorThreadsClone.size(); ++k) {
			final TsapiAddressMonitor aOThreads = (TsapiAddressMonitor) addressMonitorThreadsClone
					.elementAt(k);
			aOThreads.dump(indent + " ");
		}

		TSDevice.log.trace(indent
				+ "TSDevice Calls At Address Monitor Threads: ");
		final Vector<TsapiCallMonitor> callsAtAddressMonitorThreadsClone = (Vector<TsapiCallMonitor>) callsAtAddressMonitorThreads
				.clone();

		for (i = 0; i < callsAtAddressMonitorThreadsClone.size(); ++i) {
			final TsapiCallMonitor cAAOThreads = (TsapiCallMonitor) callsAtAddressMonitorThreadsClone
					.elementAt(i);
			cAAOThreads.dump(indent + " ");
		}

		TSDevice.log.trace(indent
				+ "TSDevice Calls Via Address Monitor Threads: ");
		final Vector<TsapiCallMonitor> callsViaAddressMonitorThreadsClone = (Vector<TsapiCallMonitor>) callsViaAddressMonitorThreads
				.clone();

		for (i = 0; i < callsViaAddressMonitorThreadsClone.size(); ++i) {
			final TsapiCallMonitor cVAOThreads = (TsapiCallMonitor) callsViaAddressMonitorThreadsClone
					.elementAt(i);
			cVAOThreads.dump(indent + " ");
		}

		TSDevice.log.trace(indent
				+ "TSDevice Calls At Terminal Monitor Threads: ");
		final Vector<TsapiCallMonitor> callsAtTerminalMonitorThreadsClone = (Vector<TsapiCallMonitor>) callsAtTerminalMonitorThreads
				.clone();

		for (i = 0; i < callsAtTerminalMonitorThreadsClone.size(); ++i) {
			final TsapiCallMonitor cATOThreads = (TsapiCallMonitor) callsAtTerminalMonitorThreadsClone
					.elementAt(i);
			cATOThreads.dump(indent + " ");
		}
		TSDevice.log.trace(indent + "wasReferenced is " + wasReferenced);
		TSDevice.log.trace(indent + "***** DEVICE DUMP END *****");
	}

	protected void finalize() throws Throwable {
		try {
			TSDevice.log.info("TSDevice " + this + " - finalize() in state "
					+ curState);
			realDelete();
		} finally {
			super.finalize();
		}
	}

	@SuppressWarnings("unchecked")
	public Vector<TsapiCallMonitor> getAddressCallObservers() {
		recreate();

		final Vector<TsapiCallMonitor> obs = (Vector) callsViaAddressMonitorThreads
				.clone();
		for (int i = 0; i < callsAtAddressMonitorThreads.size(); ++i)
			obs.addElement(callsAtAddressMonitorThreads.elementAt(i));
		return obs;
	}

	public Vector<TsapiAddressMonitor> getAddressObservers() {
		recreate();

		return new Vector<TsapiAddressMonitor>(addressMonitorThreads);
	}

	public Object getAddrPrivateData() {
		recreate();

		if (replyAddrPriv instanceof CSTAPrivate)
			return replyAddrPriv;
		return null;
	}

	String getAgentID() {
		recreate();

		if (provider.getCapabilities().getQueryDeviceInfo() != 0)
			if (provider.isLucentV5())
				try {
					final CSTAEvent event = provider.tsapi.queryDeviceInfo(
							getName(), null);
					if (event != null) {
						final Object replyPriv = event.getPrivData();
						if (replyPriv instanceof LucentV5QueryDeviceInfoConfEvent) {
							final LucentV5QueryDeviceInfoConfEvent V5devInfoConf = (LucentV5QueryDeviceInfoConfEvent) replyPriv;
							return V5devInfoConf.getAssociatedDevice();
						}

					}

				} catch (final TsapiUnableToSendException tue) {
					throw tue;
				} catch (final Exception e) {
					TSDevice.log.error(e.getMessage(), e);
				}
			else if (provider.isLucent()) {
				LucentQueryCallClassifier lqcc = null;
				lqcc = new LucentQueryCallClassifier();
				final CSTAPrivate reqPriv = lqcc.makeTsapiPrivate();
				try {
					final CSTAEvent event = provider.tsapi.queryDeviceInfo(
							getName(), reqPriv);
					if (event != null) {
						final CSTAQueryDeviceInfoConfEvent devInfoConf = (CSTAQueryDeviceInfoConfEvent) event
								.getEvent();
						return devInfoConf.getDevice();
					}
					return null;
				} catch (final TsapiUnableToSendException tue) {
					throw tue;
				} catch (final Exception e) {
					TSDevice.log.error(e.getMessage(), e);
				}
			}

		return null;
	}

	short getAssociatedClass() {
		recreate();

		if (!asyncInitializationComplete)
			TSDevice.log.info("getAssociatedClass() for " + this);

		waitForAsyncInitialization();

		return associatedClass;
	}

	String getAssociatedDevice() {
		recreate();

		if (!asyncInitializationComplete)
			TSDevice.log.info("getAssociatedDevice() for " + this);

		waitForAsyncInitialization();

		return associatedDevice;
	}

	@SuppressWarnings("unchecked")
	Vector<TSConnection> getConns() {
		recreate();

		return (Vector<TSConnection>) (Vector) connections.clone();
	}

	@SuppressWarnings("unchecked")
	Vector<TsapiCallMonitor> getCVDObservers() {
		recreate();

		final Vector<TsapiCallMonitor> obs = (Vector<TsapiCallMonitor>) callsViaAddressMonitorThreads
				.clone();
		return obs;
	}

	public short getDeviceType() {
		recreate();

		if (!asyncInitializationComplete)
			TSDevice.log.info("getDeviceType() for " + this);

		waitForAsyncInitialization();

		return deviceType;
	}

	public String getDirectoryName() {
		recreate();
		try {
			final LucentQueryDeviceName lqdn = new LucentQueryDeviceName(
					getName());
			final Object lqdnConf = provider.sendPrivateData(lqdn
					.makeTsapiPrivate(), null, true);
			if (lqdnConf instanceof LucentQueryDeviceNameConfEvent)
				return ((LucentQueryDeviceNameConfEvent) lqdnConf).getName();
			return null;
		} catch (final TsapiPlatformException e) {
			if (e.getErrorType() == 2 && e.getErrorCode() == 12)
				return null;

			throw e;
		} catch (final Exception e) {
			if (e instanceof TsapiInvalidArgumentException)
				return null;
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"queryDeviceName failure");

			throw new TsapiPlatformException(4, 0, "queryDeviceName failure");
		}
	}

	public String getDomainName() {
		return internalGetName();
	}

	public boolean getDoNotDisturb() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getDoNotDisturbEvent() == 0) {
			if (provider.getCapabilities().getQueryDnd() == 0)
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");

			final ConfHandler handler = new DNDConfHandler(this);
			try {
				provider.tsapi.queryDoNotDisturb(getName(), null, handler);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSDevice.log.error(e.getMessage(), e);
				return dndState;
			}
		}
		return dndState;
	}

	public Vector<TsapiCallControlForwarding> getForwarding()
			throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getForwardingEvent() == 0
				|| fwdVector.size() == 0) {
			if (provider.getCapabilities().getQueryFwd() == 0)
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");

			final ConfHandler handler = new FwdConfHandler(this);
			try {
				provider.tsapi.queryFwd(getName(), null, handler);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSDevice.log.error(e.getMessage(), e);
				return fwdVector;
			}
		}

		return fwdVector;
	}

	Vector<CSTAExtendedDeviceID> getKeys() {
		recreate();

		return devNameVector;
	}

	public int getMessageWaitingBits() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getMessageWaitingEvent() == 0) {
			if (provider.getCapabilities().getQueryMwi() == 0)
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");

			final ConfHandler handler = new MWIConfHandler(this);
			try {
				provider.tsapi.queryMsgWaitingInd(getName(), null, handler);
			} catch (final TsapiUnableToSendException tue) {
				throw tue;
			} catch (final Exception e) {
				TSDevice.log.error(e.getMessage(), e);
				return msgWaitingBits;
			}
		}

		return msgWaitingBits;
	}

	String getName() {
		recreate();

		return internalGetName();
	}

	public int getNumberQueued() {
		recreate();

		return numQueued;
	}

	int getRegisterReqID() {
		recreate();

		return registerReqID;
	}

	public Vector<TsapiRouteMonitor> getRouteObservers() {
		recreate();

		final Vector<TsapiRouteMonitor> obs = new Vector<TsapiRouteMonitor>(1);
		if (tsRouteCallback != null)
			obs.addElement(tsRouteCallback);
		return obs;
	}

	Vector<TSRouteSession> getSessions() {
		recreate();

		if (sessionHash == null)
			return null;

		final Vector<TSRouteSession> sessionVector = new Vector<TSRouteSession>();
		final Enumeration<TSRouteSession> sessionEnum = sessionHash.elements();
		while (sessionEnum.hasMoreElements())
			try {
				sessionVector.addElement((TSRouteSession) sessionEnum
						.nextElement());
			} catch (final NoSuchElementException e) {
				TSDevice.log.error(e.getMessage(), e);
			}

		return sessionVector;
	}

	@SuppressWarnings("unchecked")
	Vector<TSConnection> getTermConns() {
		recreate();

		return (Vector<TSConnection>) (Vector<TSConnection>) terminalConnections
				.clone();
	}

	@SuppressWarnings("unchecked")
	public Vector<TsapiCallMonitor> getTerminalCallObservers() {
		recreate();

		return (Vector<TsapiCallMonitor>) callsAtTerminalMonitorThreads.clone();
	}

	TerminalCapabilities getTerminalCapabilities(final TSDevice cevice)
			throws TsapiInvalidArgumentException {
		recreate();

		return null;
	}

	public Vector<TsapiTerminalMonitor> getTerminalObservers() {
		recreate();

		return new Vector<TsapiTerminalMonitor>(terminalMonitorThreads);
	}

	Object getTermPrivateData() {
		recreate();

		if (replyTermPriv instanceof CSTAPrivate)
			return replyTermPriv;
		return null;
	}

	public Vector<TSDevice> getTSACDDevices()
			throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public TSDevice[] getTSACDManagerDevice()
			throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public Vector<TSDevice> getTSAddressDevices() {
		recreate();

		final Vector<TSDevice> devVector = new Vector<TSDevice>();

		devVector.addElement(this);
		return devVector;
	}

	public int getTSAgentsAvailable() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.isLucent())
			try {
				final LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(
						getName());
				final Object lqasConf = provider.sendPrivateData(lqas
						.makeTsapiPrivate());
				if (lqasConf instanceof LucentQueryAcdSplitConfEvent)
					return ((LucentQueryAcdSplitConfEvent) lqasConf)
							.getAvailableAgents();
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			} catch (final Exception e) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}
		return 0;
	}

	public Vector<TSAgent> getTSAgentsForACDAddr() {
		recreate();

		if (monitorDeviceXRefID == 0)
			snapshotAgentsInACD();

		return tsACDVector;
	}

	public Vector<TSAgent> getTSAgentsForAgentTerm() {
		recreate();

		if (monitorDeviceXRefID == 0 || provider.isLucent())
			snapshotAgentsInTerminal(null, null);
		return tsAgentTermVector;
	}

	public int getTSAgentsLoggedIn() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.isLucent())
			try {
				final LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(
						getName());
				final Object lqasConf = provider.sendPrivateData(lqas
						.makeTsapiPrivate());
				if (lqasConf instanceof LucentQueryAcdSplitConfEvent)
					return ((LucentQueryAcdSplitConfEvent) lqasConf)
							.getAgentsLoggedIn();
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			} catch (final Exception e) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}
		return 0;
	}

	public TsapiAddressCapabilities getTsapiAddressCapabilities() {
		recreate();

		return provider.getTsapiAddressCapabilities();
	}

	public TsapiTerminalCapabilities getTsapiTerminalCapabilities() {
		recreate();

		return provider.getTsapiTerminalCapabilities();
	}

	public Vector<TSConnection> getTSConnections() {
		recreate();

		updateObject();
		return getConns();
	}

	public int getTSNumberQueued() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.isLucent())
			try {
				final LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(
						getName());
				final Object lqasConf = provider.sendPrivateData(lqas
						.makeTsapiPrivate());
				if (lqasConf instanceof LucentQueryAcdSplitConfEvent)
					return ((LucentQueryAcdSplitConfEvent) lqasConf)
							.getCallsInQueue();
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			} catch (final Exception e) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

		return numQueued;
	}

	public TSCall getTSOldestCallQueued()
			throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public TSProviderImpl getTSProviderImpl() {
		recreate();

		return provider;
	}

	public int getTSQueueWaitTime() throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public int getTSRelativeQueueLoad() throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	TsapiRouteMonitor getTSRouteCallback() {
		recreate();

		return tsRouteCallback;
	}

	public Vector<TSRouteSession> getTSRouteSessions() {
		recreate();

		if (tsRouteCallback != null)
			return getSessions();
		return null;
	}

	public Vector<TSConnection> getTSTerminalConnections() {
		recreate();

		updateObject();
		return getTermConns();
	}

	public Vector<TSDevice> getTSTerminalDevices() {
		recreate();

		final Vector<TSDevice> devVector = new Vector<TSDevice>();
		if (isTerminal())
			devVector.addElement(this);
		return devVector;
	}

	public TSConnection groupPickup(final TSDevice terminalAddress,
			final CSTAPrivate privData) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getGroupPickupCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		final ConfHandler handler = new TermPrivConfHandler(this, 20);
		try {
			provider.tsapi.groupPickupCall(terminalAddress.getName(), privData,
					handler);
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
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"groupPickupCall failure");
			throw new TsapiPlatformException(4, 0, "groupPickupCall failure");
		}

		throw new TsapiPlatformException(4, 0,
				"Could not meet post-conditions of groupPickup()");
	}

	void handleAgentLoginResponse(final LucentQueryAgentLoginResp event) {
		recreate();

		final int xrefID = event.getPrivEventCrossRefID();
		if (event.getDevices() == null || event.getDevices().length == 0) {
			provider.deletePrivateXref(xrefID);
			synchronized (this) {
				if (waitingForAgents) {
					waitingForAgents = false;
					super.notify();
				}
			}
		} else
			for (int i = 0; i < event.getDevices().length; ++i) {
				final TSDevice agentDevice = provider.createDevice(event
						.getDevices()[i]);
				final String agentID = agentDevice.getAgentID();
				final TSAgentKey agentKey = new TSAgentKey(
						event.getDevices()[i], getName(), agentID);
				TSAgent agent = provider.createAgent(agentKey, "", "");
				if (agent.getInternalState() != 2)
					continue;
				provider.dumpAgent(agentKey);
				agent = provider.createAgent(agentKey, "", "");
			}
	}

	private String internalGetName() {
		return ((CSTAExtendedDeviceID) devNameVector.lastElement())
				.getDeviceID();
	}

	synchronized void internalRecreate() {
		final Vector<CSTAExtendedDeviceID> keys = new Vector<CSTAExtendedDeviceID>(
				devNameVector);
		TSDevice.log.info("Recreating deleted device " + this);
		for (int i = 0; i < keys.size(); ++i) {
			final String key = ((CSTAExtendedDeviceID) keys.elementAt(i))
					.getDeviceID();
			provider.addDeviceToHash(key, this);
		}

		setState(new TSDeviceStateActive());

		TSDevice.log.info("Device "
				+ ((CSTAExtendedDeviceID) devNameVector.lastElement())
						.getDeviceID() + " (object= " + this
				+ ") being re-added" + " for " + provider);
	}

	boolean isCallsViaDeviceMonitorSet() {
		recreate();

		return monitorCallsViaDeviceXRefID != 0;
	}

	boolean isDeviceExternal() {
		recreate();

		if (!asyncInitializationComplete)
			TSDevice.log.info("isDeviceExternal() for " + this);

		waitForAsyncInitialization();

		return isExternal;
	}

	boolean isForExternalDeviceMatchingLocalExtensionNumber(
			final CSTAExtendedDeviceID devIDOnCall) {
		if (TSDevice.hasMixOfPublicAndPrivate(devNameVector)) {
			final StringBuffer tmpStrBuf = new StringBuffer();
			for (final CSTAExtendedDeviceID tmpDevID : devNameVector)
				tmpStrBuf.append(tmpDevID + " ");
			TSDevice.log
					.info("TSDevice ["
							+ this
							+ "] has both public and private deviceID types. Here is a list : "
							+ tmpStrBuf.toString());
		}

		final CSTAExtendedDeviceID lastDeviceExtDevID = (CSTAExtendedDeviceID) getKeys()
				.lastElement();

		return lastDeviceExtDevID.hasPrivateDeviceIDType()
				&& devIDOnCall.hasPublicDeviceIDType();
	}

	boolean isMonitorSet() {
		recreate();

		return monitorDeviceXRefID != 0 || monitorCallsViaDeviceXRefID != 0;
	}

	boolean isPredictiveCallsViaDeviceMonitorSet() {
		recreate();

		return monitorCallsViaDeviceXRefID != 0
				&& monitorPredictiveCallsViaDevice;
	}

	public boolean isTerminal() {
		recreate();

		if (!asyncInitializationComplete)
			TSDevice.log.info("isTerminal() for " + this);

		waitForAsyncInitialization();

		return isATerminal;
	}

	CSTAConnectionID matchConn(final CSTAConnectionID connID) {
		recreate();

		if (connID == null)
			return null;

		synchronized (terminalConnections) {
			for (int i = 0; i < terminalConnections.size(); ++i) {
				final TSConnection conn = (TSConnection) terminalConnections
						.elementAt(i);
				if (connID.equals(conn.connID))
					return conn.connID;
			}
		}
		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				final TSConnection conn = (TSConnection) connections
						.elementAt(i);
				if (connID.equals(conn.connID))
					return conn.connID;
			}
		}

		return null;
	}

	synchronized void notifyAsyncInitializationComplete() {
		recreate();

		TSDevice.log.info("Initialization complete for TSDevice " + this
				+ " - making values available - for " + provider);
		asyncInitializationComplete = true;
		super.notifyAll();
	}

	public TSConnection pickup(final TSConnection pickConnection,
			final TSDevice terminalAddress, final CSTAPrivate privData)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getPickupCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		if (pickConnection.getTSCall().updateObject()) {
			final int state = pickConnection.getTSConnState();
			if (state != 50 && state != 49 && state != 54)
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(pickConnection, true), 2, state,
						"connection not alerting or in progress");

		}

		final ConfHandler handler = new PickupConfHandler(this,
				terminalAddress, pickConnection);
		try {
			provider.tsapi.pickupCall(pickConnection.getConnID(),
					terminalAddress.getName(), privData, handler);
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
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"pickupCall failure");
			throw new TsapiPlatformException(4, 0, "pickupCall failure");
		}

		throw new TsapiPlatformException(4, 0,
				"Could not meet post-conditions of pickup()");
	}

	public TSConnection pickup(final TSDevice pickAddress,
			final TSDevice terminalAddress, final CSTAPrivate privData)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getPickupCall() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		TSConnection pickConnection = null;

		pickAddress.updateObject();
		synchronized (pickAddress.connections) {
			for (int i = 0; i < pickAddress.connections.size(); ++i) {
				pickConnection = (TSConnection) pickAddress.connections
						.elementAt(i);
				if (pickConnection.getTSConnState() == 50
						|| pickConnection.getTSConnState() == 49
						|| pickConnection.getTSConnState() == 54)
					return pickup(pickConnection, terminalAddress, privData);
			}
		}
		throw new TsapiInvalidArgumentException(3, 0,
				"no connection found to pickup");
	}

	private void realDelete() {
		if (curState.wasDeleteDone()) {
			TSDevice.log
					.info("Device "
							+ this
							+ " realDelete: already deleted - no further action taken, "
							+ provider);
			return;
		}

		TSDevice.log.info("Device " + this + " being deleted for " + provider);

		stopMonitorForThisDevice();

		if (Tsapi.getTSDevicePerformanceOptimization() == true
				&& asyncInitializationComplete && !isDeviceExternal()) {
			TSDevice.log.info("NOT deleting " + this
					+ " due to TSDevice Performance Optimization");
			return;
		}

		setState(new TSDeviceStateBeingDeleted());

		provider.deleteInstanceOfDeviceFromHash(this);

		setState(new TSDeviceStateDeleted());
	}

	synchronized void recreate() {
		curState.recreate(this);
	}

	public String referenced() {
		recreate();

		refCount += 1;
		if (!wasReferenced) {
			TSDevice.g_RefCnt += 1;
			wasReferenced = true;
		}

		return ((CSTAExtendedDeviceID) devNameVector.lastElement())
				.getDeviceID();
	}

	void registerRoute() {
		recreate();

		final ConfHandler handler = new RouteRegisterConfHandler(this);
		try {
			if (getName().equals("AllRouteAddress"))
				provider.tsapi.registerRouteCallback(null, null, handler);
			else
				provider.tsapi.registerRouteCallback(getName(), null, handler);
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"registerRouteCallback failure");
			throw new TsapiPlatformException(4, 0,
					"registerRouteCallback failure");
		}
		provider.addRoute(registerReqID, this);
		sessionHash = new Hashtable<Integer, TSRouteSession>(3);
	}

	public void removeAddressCallMonitor(final TsapiCallMonitor obs) {
		recreate();

		removeAddressCallMonitor(obs, 100, null);
	}

	protected void removeAddressCallMonitor(final TsapiCallMonitor obs,
			final int cause, final Object privateData) {
		recreate();

		if (!callsViaAddressMonitorThreads.removeElement(obs))
			callsAtAddressMonitorThreads.removeElement(obs);
		obs.deleteReference(this, false, cause, privateData);

		testDelete();
	}

	public void removeAddressMonitor(final TsapiAddressMonitor obs) {
		recreate();

		removeAddressMonitor(obs, 100, null);
	}

	protected void removeAddressMonitor(final TsapiAddressMonitor obs,
			final int cause, final Object privateData) {
		recreate();

		if (!addressMonitorThreads.removeElement(obs))
			return;
		obs.deleteReference(this, cause, privateData);
		testDelete();
	}

	void removeConnection(final TSConnection tsConn) {
		recreate();

		if (connections.removeElement(tsConn)) {
			final TSCall call = tsConn.getTSCall();
			call.removeDefaultDeviceObservers(this, false);
		}
		if (terminalConnections.removeElement(tsConn)) {
			final TSCall call = tsConn.getTSCall();
			call.removeDefaultDeviceObservers(this, true);
		}

		synchronized (this) {
			synchronized (devNameVector) {
				final Vector<CSTAExtendedDeviceID> keys = new Vector<CSTAExtendedDeviceID>(
						devNameVector);
				for (int i = 0; i < keys.size(); ++i) {
					final CSTAExtendedDeviceID devID = (CSTAExtendedDeviceID) keys
							.elementAt(i);
					if (devID.getDeviceIDStatus() != 0
							|| devID.getDeviceIDType() != 70
							&& devID.getDeviceIDType() != 71)
						continue;

					if (devNameVector.size() == 1)
						addName(new CSTAExtendedDeviceID("", (short) 70,
								(short) 1));
					provider.deleteDeviceFromHash(devID.getDeviceID());
					devNameVector.removeElement(devID);
				}

			}

		}

		testDelete();
	}

	void removeFromACDVector(final TSAgent tsAgent) {
		recreate();

		tsACDVector.removeElement(tsAgent);
		testDelete();
	}

	void removeFromAgentTermVector(final TSAgent tsAgent) {
		recreate();

		tsAgentTermVector.removeElement(tsAgent);
		testDelete();
	}

	void removeInternalMonitor(final TSCall call) {
		recreate();

		internalDeviceMonitors.removeElement(call);

		testDelete();
	}

	void removeObservers(final int cause, final Object privateData,
			final int xrefID) {
		recreate();

		if (xrefID != 0) {
			if (xrefID == monitorDeviceXRefID) {
				provider.deleteMonitor(monitorDeviceXRefID);
				monitorDeviceXRefID = 0;
			}
			if (xrefID == monitorCallsViaDeviceXRefID) {
				provider.deleteMonitor(monitorCallsViaDeviceXRefID);
				clearMonitorCallsViaDeviceAttributes();
			}
		}

		final Vector<TsapiAddressMonitor> observers = new Vector<TsapiAddressMonitor>(
				addressMonitorThreads);
		for (int i = 0; i < observers.size(); ++i)
			removeAddressMonitor((TsapiAddressMonitor) observers.elementAt(i),
					cause, privateData);
		final Vector<TsapiTerminalMonitor> terminalObservers = new Vector<TsapiTerminalMonitor>(
				terminalMonitorThreads);
		for (int i = 0; i < terminalObservers.size(); ++i)
			removeTerminalMonitor((TsapiTerminalMonitor) terminalObservers
					.elementAt(i), cause, privateData);
		final Vector<TsapiCallMonitor> callsViaAddressObservers = new Vector<TsapiCallMonitor>(
				callsViaAddressMonitorThreads);
		for (int i = 0; i < callsViaAddressObservers.size(); ++i)
			removeAddressCallMonitor(
					(TsapiCallMonitor) callsViaAddressObservers.elementAt(i),
					cause, privateData);

		final Vector<TsapiCallMonitor> callsAtAddressObservers = new Vector<TsapiCallMonitor>(
				callsAtAddressMonitorThreads);
		for (int i = 0; i < callsAtAddressObservers.size(); ++i)
			removeAddressCallMonitor((TsapiCallMonitor) callsAtAddressObservers
					.elementAt(i), cause, privateData);

		final Vector<TsapiCallMonitor> callsAtTerminalObservers = new Vector<TsapiCallMonitor>(
				callsAtTerminalMonitorThreads);
		for (int i = 0; i < callsAtTerminalObservers.size(); ++i)
			removeTerminalCallMonitor(
					(TsapiCallMonitor) callsAtTerminalObservers.elementAt(i),
					cause, privateData);

		internalDeviceMonitors.removeAllElements();

		stopMonitorForThisDevice();
	}

	public void removeRouteMonitor(final TsapiRouteMonitor observer) {
		recreate();

		removeRouteMonitor(observer, 100, null);
	}

	protected void removeRouteMonitor(final TsapiRouteMonitor observer,
			final int cause, final Object privateData) {
		recreate();
		try {
			provider.tsapi.cancelRouteCallback(registerReqID, null, null);
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"cancelRouteCallback failure");
			throw new TsapiPlatformException(4, 0,
					"cancelRouteCallback failure");
		}
		observer.deleteReference(this);
		testDelete();
	}

	public void removeTerminalCallMonitor(final TsapiCallMonitor obs) {
		recreate();

		removeTerminalCallMonitor(obs, 100, null);
	}

	protected void removeTerminalCallMonitor(final TsapiCallMonitor obs,
			final int cause, final Object privateData) {
		recreate();

		callsAtTerminalMonitorThreads.removeElement(obs);

		obs.deleteReference(this, true, cause, privateData);

		testDelete();
	}

	public void removeTerminalMonitor(final TsapiTerminalMonitor obs) {
		recreate();

		removeTerminalMonitor(obs, 100, null);
	}

	protected void removeTerminalMonitor(final TsapiTerminalMonitor obs,
			final int cause, final Object privateData) {
		recreate();

		if (!terminalMonitorThreads.removeElement(obs))
			return;
		obs.deleteReference(this, cause, privateData);
		testDelete();
	}

	public void removeTSAgent(TSAgent tsAgent, final int reasonCode)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		recreate();

		if (tsAgent == null) {
			if (tsAgentTermVector.size() == 0)
				throw new TsapiInvalidArgumentException(3, 0,
						"No agents logged into specified Terminal");

			final Vector<TSAgent> agentVector = new Vector<TSAgent>(
					tsAgentTermVector);
			for (int i = 0; i < agentVector.size(); ++i) {
				tsAgent = (TSAgent) agentVector.elementAt(i);
				if (tsAgent.getState() == 2)
					continue;

				tsAgent.setState(2, 0, reasonCode);
			}

		} else {
			if (tsAgent.getState() == 2)
				return;

			if (!tsAgentTermVector.contains(tsAgent))
				throw new TsapiInvalidArgumentException(3, 0,
						"Agent not logged into specified Terminal");

			tsAgent.setState(2, 0, reasonCode);
		}
	}

	void sendAddressSnapshot(final TsapiAddressMonitor callback) {
		recreate();

		if (callback == null)
			return;

		final Vector<TSEvent> eventList = new Vector<TSEvent>();

		eventList.addElement(new TSEvent(37, this));
		eventList.addElement(new TSEvent(38, this));
		eventList.addElement(new TSEvent(39, this));

		for (int i = 0; i < tsACDVector.size(); ++i)
			((TSAgent) tsACDVector.elementAt(i)).getSnapshot(eventList);

		if (eventList.size() <= 0)
			return;
		callback.deliverEvents(eventList, true);
	}

	void sendCallSnapshot(final TsapiCallMonitor callback,
			final boolean forTerminal) {
		recreate();

		if (callback == null)
			return;

		final Vector<TSEvent> eventList = new Vector<TSEvent>();

		if (forTerminal)
			synchronized (terminalConnections) {
				for (int i = 0; i < terminalConnections.size(); ++i) {
					final TSCall call = ((TSConnection) terminalConnections
							.elementAt(i)).getTSCall();

					if (call.getCallObservers().contains(callback))
						continue;

					call.getSnapshot(eventList);
					call.addDeviceObservers(this,
							callsAtTerminalMonitorThreads, null, null, false);
				}
			}
		else
			synchronized (connections) {
				for (int i = 0; i < connections.size(); ++i) {
					final TSCall call = ((TSConnection) connections
							.elementAt(i)).getTSCall();

					if (call.getCallObservers().contains(callback))
						continue;

					call.getSnapshot(eventList);
					call.addDeviceObservers(this, null,
							callsAtAddressMonitorThreads,
							callsViaAddressMonitorThreads, false);
				}
			}

		if (eventList.size() <= 0)
			return;
		callback.deliverEvents(eventList, 110, true);
	}

	public Object sendPrivateData(final CSTAPrivate data) {
		recreate();
		try {
			return provider.sendPrivateData(data);
		} catch (final Exception e) {
			throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
		}
	}

	void sendTerminalSnapshot(final TsapiTerminalMonitor callback) {
		recreate();

		if (callback == null)
			return;

		final Vector<TSEvent> eventList = new Vector<TSEvent>();

		eventList.addElement(new TSEvent(58, this));

		for (int i = 0; i < tsAgentTermVector.size(); ++i)
			((TSAgent) tsAgentTermVector.elementAt(i)).getSnapshot(eventList);

		if (eventList.size() <= 0)
			return;
		callback.deliverEvents(eventList, true);
	}

	void setAssociatedClass(final short associatedClass) {
		recreate();

		this.associatedClass = associatedClass;
	}

	void setAssociatedDevice(final String associatedDevice) {
		recreate();

		this.associatedDevice = associatedDevice;
	}

	void setDeviceType(final short _deviceType) {
		recreate();

		deviceType = _deviceType;
	}

	public void setDoNotDisturb(final boolean enable, final CSTAPrivate reqPriv)
			throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetDnd() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		final ConfHandler handler = new DNDConfHandler(this, enable);
		try {
			provider.tsapi.setDnd(getName(), enable, reqPriv, handler);
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setDnd failure");
			throw new TsapiPlatformException(4, 0, "setDnd failure");
		}
	}

	public void setForwarding(
			final Vector<TsapiCallControlForwarding> _fwdVector,
			final CSTAPrivate reqPriv) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetFwd() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		cancelForwarding(null);

		final CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];

		short forwardingType = 0;
		final boolean forwardingOn = true;

		synchronized (_fwdVector) {
			for (int i = 0; i < _fwdVector.size(); ++i) {
				final TsapiCallControlForwarding fwd = (TsapiCallControlForwarding) _fwdVector
						.elementAt(i);
				final String forwardDN = fwd.getDestinationAddress();
				switch (fwd.getType()) {
				case 2:
					switch (fwd.getFilter()) {
					case 3:
						forwardingType = 4;
						break;
					case 2:
						forwardingType = 3;
						break;
					case 1:
						forwardingType = 1;
					}

					break;
				case 3:
					switch (fwd.getFilter()) {
					case 3:
						forwardingType = 6;
						break;
					case 2:
						forwardingType = 5;
						break;
					case 1:
						forwardingType = 2;
					}

					break;
				case 1:
					forwardingType = 0;
				}

				fwdArray[0] = new CSTAForwardingInfo(forwardingType,
						forwardingOn, forwardDN);

				final ConfHandler handler = new FwdConfHandler(this, fwdArray);
				try {
					provider.tsapi.setFwd(getName(), forwardingType,
							forwardingOn, forwardDN, reqPriv, handler);
				} catch (final TsapiInvalidStateException e) {
					throw e;
				} catch (final TsapiInvalidArgumentException e) {
					throw e;
				} catch (final TsapiPlatformException e) {
					throw e;
				} catch (final Exception e) {
					if (e instanceof ITsapiException)
						throw new TsapiPlatformException(((ITsapiException) e)
								.getErrorType(), ((ITsapiException) e)
								.getErrorCode(), "setFwd failure");
					throw new TsapiPlatformException(4, 0, "setFwd failure");
				}
			}
		}
	}

	TSDevice setInternalMonitor(final TSCall call)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0)
			throw new TsapiResourceUnavailableException(2, 0, 0,
					"no capability to monitor device");
		synchronized (obsSync) {
			if (isMonitorSet()) {
				internalDeviceMonitors.addElement(call);
				return this;
			}
			synchronized (this) {
				if (monitorDeviceXRefID != 0) {
					internalDeviceMonitors.addElement(call);
					return this;
				}

				CSTAEvent event;
				try {
					event = provider.tsapi.monitorDevice(getName(),
							new CSTAMonitorFilter(), null);
				} catch (final TsapiUnableToSendException tue) {
					throw tue;
				} catch (final TsapiResourceUnavailableException e) {
					throw e;
				} catch (final Exception e) {
					throw new TsapiResourceUnavailableException(2, 0, 0,
							"failure to monitor device");
				}

				final CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
						.getEvent();
				monitorDeviceXRefID = monitorConf.getMonitorCrossRefID();
			}

			doTerminalSnapshot();
			doAddressSnapshot();
			doCallSnapshot();

			provider.addMonitor(monitorDeviceXRefID, this);

			internalDeviceMonitors.addElement(call);
		}
		return this;
	}

	void setIsATerminal(final boolean b) {
		recreate();

		isATerminal = b;
	}

	void setIsExternal(final boolean b) {
		recreate();

		isExternal = b;
	}

	public void setMessageWaiting(final boolean enable,
			final CSTAPrivate reqPriv) throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetMwi() == 0)
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		final ConfHandler handler = new MWIConfHandler(this,
				msgWaitingBits |= 268435456);
		try {
			provider.tsapi
					.setMsgWaitingInd(getName(), enable, reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setMsgWaitingInd failure");
			throw new TsapiPlatformException(4, 0, "setMsgWaitingInd failure");
		}
	}

	void setMonitor(final boolean followCall, final boolean callObserver)
			throws TsapiResourceUnavailableException {
		setMonitor(followCall, callObserver, false, null);
	}

	void setMonitor(final boolean followCall, final boolean callObserver,
			final boolean predictive, final CSTAPrivate reqPriv)
			throws TsapiResourceUnavailableException {
		recreate();
		CSTAEvent event;
		if (followCall || callObserver
				&& (getDeviceType() == 2 || getDeviceType() == 1))
			synchronized (this) {
				if (monitorCallsViaDeviceXRefID != 0)
					return;
				if (provider.getCapabilities().getMonitorCallsViaDevice() != 0)
					try {
						event = provider.tsapi.monitorCallsViaDevice(getName(),
								new CSTAMonitorFilter(), reqPriv);
						if (event != null) {
							final CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
									.getEvent();
							monitorCallsViaDeviceXRefID = monitorConf
									.getMonitorCrossRefID();
							monitorPredictiveCallsViaDevice = predictive;
						}
					} catch (final TsapiUnableToSendException tue) {
						throw tue;
					} catch (final Exception e) {
						TSDevice.log
								.error("MonitorCallsViaDevice request failed - retrying");
						try {
							event = provider.tsapi
									.monitorCallsViaDevice(getName(),
											new CSTAMonitorFilter(), reqPriv);
							final CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
									.getEvent();
							monitorCallsViaDeviceXRefID = monitorConf
									.getMonitorCrossRefID();
							monitorPredictiveCallsViaDevice = predictive;
						} catch (final TsapiResourceUnavailableException e1) {
							throw e1;
						} catch (final Exception e1) {
							if (followCall)
								throw new TsapiResourceUnavailableException(2,
										0, 0,
										"failure to monitor calls via device");
						}

					}

			}

		if (!followCall
				&& (!provider.isLucent() || provider.isLucent()
						&& getDeviceType() != 1)) {
			if (provider.getCapabilities().getMonitorDevice() == 0)
				throw new TsapiResourceUnavailableException(2, 0, 0,
						"no capability to monitor device");
			synchronized (this) {
				if (monitorDeviceXRefID != 0)
					return;

				try {
					event = provider.tsapi.monitorDevice(getName(),
							new CSTAMonitorFilter(), null);
				} catch (final TsapiUnableToSendException tue) {
					throw tue;
				} catch (final Exception e) {
					TSDevice.log.error(
							"MonitorDevice request failed - retrying", e);
					TSDevice.log
							.info("MonitorDevice request failed - retrying");
					try {
						event = provider.tsapi.monitorDevice(getName(),
								new CSTAMonitorFilter(), null);
					} catch (final TsapiUnableToSendException tue) {
						throw tue;
					} catch (final TsapiResourceUnavailableException e1) {
						throw e1;
					} catch (final Exception e1) {
						throw new TsapiResourceUnavailableException(2, 0, 0,
								"failure to monitor device");
					}
				}
				if (event != null) {
					final CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
							.getEvent();
					monitorDeviceXRefID = monitorConf.getMonitorCrossRefID();
				}
			}

		}

		if (followCall || callObserver
				&& (getDeviceType() == 2 || getDeviceType() == 1))
			provider.addMonitor(monitorCallsViaDeviceXRefID, this);

		if (followCall || provider.isLucent()
				&& (!provider.isLucent() || getDeviceType() == 1))
			return;

		doTerminalSnapshot();
		doAddressSnapshot();
		doCallSnapshot();
		provider.addMonitor(monitorDeviceXRefID, this);
	}

	void setNumberQueued(final int _numQueued) {
		recreate();

		numQueued = _numQueued;
	}

	private void setState(final TSDeviceState _newState) {
		TSDevice.log.info("TSDevice state transition: " + curState + " -> "
				+ _newState + ", device " + this);

		curState = _newState;
	}

	void setTSAgent(final String acdDeviceName, final int state,
			final String agentID, final String password,
			final CSTAPrivate reqPriv) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException {
		recreate();
		int agentMode;
		switch (state) {
		case 1:
			agentMode = 0;
			break;
		case 2:
			agentMode = 1;
			break;
		case 4:
			agentMode = 3;
			break;
		case 3:
			agentMode = 2;
			break;
		case 6:
			agentMode = 5;
			break;
		case 5:
			agentMode = 4;
			break;
		case 0:
		case 7:
		default:
			throw new TsapiPlatformException(4, 0, "Unknown Agent state ["
					+ state + "]");
		}

		final ConfHandler handler = new LucentSetAgentStateConfHandler(this);
		try {
			provider.tsapi.setAgentState(getName(), (short) agentMode, agentID,
					acdDeviceName, password, reqPriv, handler);
		} catch (final TsapiInvalidStateException e) {
			throw e;
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setAgentState failure");
			throw new TsapiPlatformException(4, 0, "setAgentState failure");
		}
	}

	void snapshotAgentsInACD() {
		recreate();

		if (!provider.isLucent())
			return;
		try {
			final LucentQueryAgentLogin lqal = new LucentQueryAgentLogin(
					getName());
			waitingForAgents = true;
			final ConfHandler handler = new QueryAgentLoginConfHandler(this);
			final Object lqalConf = provider.sendPrivateData(lqal
					.makeTsapiPrivate(), handler);

			if (lqalConf instanceof LucentQueryAgentLoginConfEvent) {
				synchronized (this) {
					if (waitingForAgents) {
						try {
							super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
						} catch (final InterruptedException e) {
						}
						if (waitingForAgents) {
							waitingForAgents = false;
							throw new TsapiPlatformException(4, 0,
									"snapshot time-out");
						}
					}
				}
				for (int i = 0; i < tsACDVector.size(); ++i)
					((TSAgent) tsACDVector.elementAt(i)).getState();
			}
		} catch (final Exception e) {
		}
	}

	void snapshotAgentsInTerminal(final String acdName, String agentID) {
		recreate();

		if (provider.getCapabilities().getQueryAgentState() == 0)
			return;
		final QueryAgentStateConfHandler2 handler = new QueryAgentStateConfHandler2(
				this, acdName, agentID);
		CSTAPrivate priv = null;
		if (provider.isLucent() && acdName != null) {
			final LucentQueryAgentState lqas = new LucentQueryAgentState(
					acdName);
			priv = lqas.makeTsapiPrivate();
		} else if (provider.isLucent()) {
			final TSAgentKey agentKey = new TSAgentKey(getName(), acdName,
					agentID);
			TSAgent agent = null;
			agent = getTSProviderImpl().findAgent(agentKey);
			if (agent == null)
				synchronized (tsAgentTermVector) {
					if (tsAgentTermVector.size() > 0)
						agent = (TSAgent) tsAgentTermVector.elementAt(0);
				}

			if (agent != null && agent.getACDDeviceID() != null) {
				final LucentQueryAgentState lqas = new LucentQueryAgentState(
						agent.getACDDeviceID().getName());
				priv = lqas.makeTsapiPrivate();
			}
		}
		try {
			provider.tsapi.queryAgentState(getName(), priv, handler);
		} catch (final TsapiUnableToSendException tue) {
			throw tue;
		} catch (final Exception e) {
			TSDevice.log.error(e.getMessage(), e);
		}

		if (acdName == null) {
			agentID = getAgentID();
			final TSAgentKey currentAgentKey = new TSAgentKey(getName(),
					acdName, agentID);
			if (agentID != null && tsAgentTermVector.size() > 0) {
				final TSAgent previousAgent = (TSAgent) tsAgentTermVector
						.get(0);
				final TSAgentKey previousTsAgentKey = previousAgent
						.getAgentKey();
				if (!previousTsAgentKey.equals(currentAgentKey)) {
					TSDevice.log.info("deleting agent "
							+ previousAgent.getAgentID());
					previousAgent.updateState(2, -1, 0, null);
				}
			}
		}

		final TSAgentKey agentKey = new TSAgentKey(getName(), acdName, agentID);
		TSAgent agent = null;

		if (handler.getAgentState() == -1)
			return;

		if (handler.getAgentState() == 1) {
			agent = getTSProviderImpl().findAgent(agentKey);
			if (agent == null)
				synchronized (tsAgentTermVector) {
					if (tsAgentTermVector.size() > 0)
						agent = (TSAgent) tsAgentTermVector.elementAt(0);
				}
			if (agent != null)
				agent.updateState(2, handler.getWorkMode(), 0, null);
			return;
		}

		agent = getTSProviderImpl().createAgent(agentKey, agentID, null);

		final TSDevice agentDevice = agent.getTSAgentDevice();
		final TSDeviceState tsdeviceState = agentDevice.curState;
		if (tsdeviceState.wasDeleteDone())
			updateAllAgentsInDeletedDeviceInstance(agentDevice.tsAgentTermVector);
		if (agent.getInternalState() == 2) {
			provider.dumpAgent(agentKey);
			agent = provider.createAgent(agentKey, agentID, null);
		}
		int i = 0;
		if (handler.getState() == 7)
			i = 7;
		else
			switch (handler.getAgentState()) {
			case 0:
				i = 3;
				break;
			case 1:
				i = 2;
				break;
			case 2:
				i = 4;
				break;
			case 3:
				i = 5;
				break;
			case 4:
				i = 6;
			}

		agent.updateState(i, handler.getWorkMode(), 0, null);
	}

	private void stopMonitorForThisDevice() {
		TSDevice.log.info("stopMonitorForThisDevice: Device " + this
				+ " about to consider DevMon stop (xref=" + monitorDeviceXRefID
				+ " mcvdxref=" + monitorCallsViaDeviceXRefID + "), for "
				+ provider);

		if (monitorDeviceXRefID != 0)
			provider.deleteMonitor(monitorDeviceXRefID);

		if (monitorCallsViaDeviceXRefID != 0)
			provider.deleteMonitor(monitorCallsViaDeviceXRefID);

		if (monitorDeviceXRefID != 0) {
			if (provider.getCapabilities().getMonitorStop() != 0)
				if (provider.getState() != 18)
					try {
						provider.tsapi.monitorStop(monitorDeviceXRefID, null,
								null);
					} catch (final Exception e) {
						TSDevice.log.error(e.getMessage(), e);
					}
				else
					TSDevice.log.info("stopMonitorForThisDevice: " + this
							+ " Skipping Monitor Stop because Provider is "
							+ "in SHUTDOWN state, monitorDeviceXRefID="
							+ monitorDeviceXRefID + " for " + provider);

			monitorDeviceXRefID = 0;
		}

		if (monitorCallsViaDeviceXRefID == 0)
			return;
		if (provider.getCapabilities().getMonitorStop() != 0)
			if (provider.getState() != 18)
				try {
					provider.tsapi.monitorStop(monitorCallsViaDeviceXRefID,
							null, null);
				} catch (final Exception e) {
					TSDevice.log.error(e.getMessage(), e);
				}
			else
				TSDevice.log.info("stopMonitorForThisDevice: " + this
						+ " Skipping Monitor Stop because Provider is "
						+ "in SHUTDOWN state, "
						+ "monitorCallsViaDeviceXRefID="
						+ monitorCallsViaDeviceXRefID + " for " + provider);

		clearMonitorCallsViaDeviceAttributes();

		provider.removeAllCallsForDomain(this);
	}

	synchronized void testDelete() {
		if (!terminalMonitorThreads.isEmpty()
				|| !addressMonitorThreads.isEmpty()
				|| !callsViaAddressMonitorThreads.isEmpty()
				|| !callsAtAddressMonitorThreads.isEmpty()
				|| !callsAtTerminalMonitorThreads.isEmpty()
				|| tsRouteCallback != null || !tsACDVector.isEmpty()
				|| !internalDeviceMonitors.isEmpty() || connections.size() > 0
				|| terminalConnections.size() > 0)
			return;

		stopMonitorForThisDevice();

		if (refCount > 0)
			return;

		for (int i = 0; i < tsAgentTermVector.size(); ++i) {
			final TSAgent tsAgent = (TSAgent) tsAgentTermVector.elementAt(i);
			if (tsAgent != null && tsAgent.isReferenced())
				return;

		}

		realDelete();
	}

	public String toString() {
		return "TSDevice[" + internalGetName() + "]@"
				+ Integer.toHexString(super.hashCode());
	}

	public void unreferenced() {
		recreate();

		refCount -= 1;
		testDelete();
	}

	private void updateAllAgentsInDeletedDeviceInstance(
			final Vector<TSAgent> agentsForAgentTerm) {
		for (int i = 0; i < agentsForAgentTerm.size(); ++i) {
			final TSAgent agent = (TSAgent) agentsForAgentTerm.elementAt(i);
			TSDevice.log
					.info("agent represented by agentKey "
							+ agent.getAgentKey()
							+ " is holding reference to agentDevice which is in deleted state");
			agent.agentDevice = this;
			TSDevice.log
					.info("updated the agent device reference to the new agentdevice "
							+ getName());
			addToAgentTermVector(agent);
			TSDevice.log
					.debug("agent has been added in the list of agents for device "
							+ getName());
		}
	}

	void updateDNDState(final boolean _dndState, final Vector<TSEvent> eventList) {
		recreate();

		synchronized (this) {
			if (dndState == _dndState) {
				if (!dndInitialized)
					dndInitialized = true;
				return;
			}

			dndState = _dndState;
		}

		if (eventList != null && dndInitialized) {
			eventList.addElement(new TSEvent(37, this));
			eventList.addElement(new TSEvent(58, this));
		}

		if (!dndInitialized)
			dndInitialized = true;
	}

	void updateForwarding(final CSTAForwardingInfo[] fwdInfo,
			final Vector<TSEvent> eventList) {
		recreate();

		boolean update = false;
		boolean typeMatch = false;
		synchronized (this) {
			TsapiCallControlForwarding newFwd = null;
			TsapiCallControlForwarding oldFwd = null;
			for (int i = 0; i < fwdInfo.length; ++i) {
				switch (fwdInfo[i].getForwardingType()) {
				case 1:
					newFwd = new TsapiCallControlForwarding(fwdInfo[i]
							.getForwardDN(), 2);

					break;
				case 4:
					newFwd = new TsapiCallControlForwarding(fwdInfo[i]
							.getForwardDN(), 2, false);

					break;
				case 3:
					newFwd = new TsapiCallControlForwarding(fwdInfo[i]
							.getForwardDN(), 2, true);

					break;
				case 0:
					newFwd = new TsapiCallControlForwarding(fwdInfo[i]
							.getForwardDN(), 1);

					break;
				case 2:
					newFwd = new TsapiCallControlForwarding(fwdInfo[i]
							.getForwardDN(), 3);

					break;
				case 6:
					newFwd = new TsapiCallControlForwarding(fwdInfo[i]
							.getForwardDN(), 3, false);

					break;
				case 5:
					newFwd = new TsapiCallControlForwarding(fwdInfo[i]
							.getForwardDN(), 3, true);
				}

				typeMatch = false;
				synchronized (fwdVector) {
					for (int j = 0; j < fwdVector.size(); ++j) {
						oldFwd = (TsapiCallControlForwarding) fwdVector
								.elementAt(j);
						if (oldFwd.getType() != newFwd.getType()
								|| oldFwd.getFilter() != newFwd.getFilter())
							continue;
						typeMatch = true;
						if (!fwdInfo[i].isForwardingOn()) {
							update = true;

							fwdVector.removeElement(oldFwd);
							break;
						}
						if (newFwd.getDestinationAddress() == null) {
							if (oldFwd.getDestinationAddress() == null) {
								update = true;
								continue;
							}

							update = true;

							fwdVector.removeElement(oldFwd);
							fwdVector.addElement(newFwd);
							break;
						}

						if (newFwd.getDestinationAddress().equals(
								oldFwd.getDestinationAddress())
								|| newFwd.getDestinationAddress().equals(
										oldFwd.getDestinationAddress() + "#"))
							continue;

						update = true;

						fwdVector.removeElement(oldFwd);
						fwdVector.addElement(newFwd);
						break;
					}

					if (!typeMatch && fwdInfo[i].isForwardingOn() == true) {
						update = true;
						fwdVector.addElement(newFwd);
					}
				}
			}
		}

		if (eventList != null && update == true && forwardingInitialized)
			eventList.addElement(new TSEvent(39, this));

		if (!forwardingInitialized)
			forwardingInitialized = true;
	}

	void updateMessageWaitingBits(final int _msgWaitingBits,
			final Vector<TSEvent> eventList) {
		recreate();

		synchronized (this) {
			if (msgWaitingBits == _msgWaitingBits) {
				if (!mwiInitialized)
					mwiInitialized = true;
				return;
			}

			msgWaitingBits = _msgWaitingBits;
		}

		if (eventList != null && mwiInitialized)
			eventList.addElement(new TSEvent(38, this));

		if (!mwiInitialized)
			mwiInitialized = true;
	}

	boolean updateObject() {
		recreate();

		if (isMonitorSet() == true && cleanUCIDsInCallsInConnections())
			return true;

		return doCallSnapshot();
	}

	synchronized void waitForAsyncInitialization() {
		recreate();

		if (asyncInitializationComplete)
			return;
		try {
			TSDevice.log.info(this + " waiting for initialization to complete");
			super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
		} catch (final InterruptedException e) {
		}
		if (asyncInitializationComplete)
			return;
		throw new TsapiPlatformException(4, 0,
				"could not finish address/terminal construction");
	}

	boolean wereChangesHeldPending() {
		recreate();

		return changesWereHeldPending;
	}
}
