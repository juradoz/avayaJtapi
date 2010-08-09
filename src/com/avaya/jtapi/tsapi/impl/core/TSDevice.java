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
			Vector<CSTAExtendedDeviceID> deviceList) {
		if (deviceList.size() <= 1) {
			return false;
		}
		CSTAExtendedDeviceID tempDevID = (CSTAExtendedDeviceID) deviceList
				.elementAt(0);
		String type = null;

		if (tempDevID.hasPrivateDeviceIDType()) {
			type = "PRIVATE";
		} else if (tempDevID.hasPublicDeviceIDType()) {
			type = "PUBLIC";
		} else {
			return true;
		}
		int i = 1;
		if (i < deviceList.size()) {
			tempDevID = (CSTAExtendedDeviceID) deviceList.elementAt(i);

			if ((type.equals("PUBLIC")) && (!tempDevID.hasPublicDeviceIDType())) {
				return true;
			}
			return (type.equals("PRIVATE"))
					&& (!tempDevID.hasPrivateDeviceIDType());
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

	TSDevice(TSProviderImpl _provider, CSTAExtendedDeviceID deviceID) {
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
		g_CreationCnt += 1;

		devNameVector = new Vector();
		devNameVector.addElement(deviceID);
		connections = new Vector();
		terminalConnections = new Vector();
		internalDeviceMonitors = new Vector();
		terminalMonitorThreads = new Vector();
		addressMonitorThreads = new Vector();
		callsViaAddressMonitorThreads = new Vector();
		callsAtAddressMonitorThreads = new Vector();
		callsAtTerminalMonitorThreads = new Vector();
		tsACDVector = new Vector();
		tsAgentTermVector = new Vector();
		fwdVector = new Vector();

		curState = new TSDeviceStateActive();

		obsSync = new Object();

		log.info("Constructing device " + this + " with name " + getName()
				+ " for " + provider);
		try {
			if (((provider.isLucent()) && (getName().regionMatches(true, 0,
					"T", 0, 1)))
					|| ((((!provider.isLucent()) || (deviceID.getDeviceIDType() != 55)))
							&& (((!provider.isLucent()) || (deviceID
									.getDeviceIDType() != 40))) && (((!provider
							.isLucent()) || (deviceID.getDeviceIDType() != 0))))) {
				setIsATerminal(false);
				setIsExternal(true);
				notifyAsyncInitializationComplete();
			} else if (provider.getCapabilities().getQueryDeviceInfo() != 0) {
				provider.tsapi.queryDeviceInfoAsync(getName(), null,
						new AsyncQueryDeviceInfoConfHandler(this));
			} else {
				notifyAsyncInitializationComplete();
			}
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			setIsATerminal(false);
			setIsExternal(true);
			notifyAsyncInitializationComplete();
		}
	}

	public void addAddressCallMonitor(TsapiCallMonitor obs, boolean followCall)
			throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		addAddressCallMonitor(obs, followCall, false, null);
	}

	public void addAddressCallMonitor(TsapiCallMonitor obs, boolean followCall,
			boolean predictive, CSTAPrivate reqPriv)
			throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		recreate();

		if (followCall) {
			if (provider.getCapabilities().getMonitorCallsViaDevice() == 0) {
				throw new TsapiMethodNotSupportedException(0, 0,
						"unsupported by driver");
			}
			if ((predictive) && (!provider.getMonitorCallsViaDevice())) {
				throw new TsapiMethodNotSupportedException(0, 0,
						"unsupported by driver");
			}

		} else if (provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (obsSync) {
			if (callsViaAddressMonitorThreads.contains(obs)) {
				return;
			}

			if (followCall) {
				if (callsAtAddressMonitorThreads.removeElement(obs)) {
					callsViaAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(true, true, predictive, reqPriv);
					} catch (TsapiResourceUnavailableException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (TsapiPlatformException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					}

				} else {
					callsViaAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(true, true, predictive, reqPriv);

						obs.addReference();
					} catch (TsapiResourceUnavailableException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (TsapiPlatformException e) {
						provider.removeCallMonitorThread(obs);
						callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					}
				}

			} else {
				synchronized (obsSync) {
					if (callsAtAddressMonitorThreads.contains(obs)) {
						return;
					}

					callsAtAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(false, true);

						obs.addReference();
					} catch (TsapiResourceUnavailableException e) {
						provider.removeCallMonitorThread(obs);
						callsAtAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (TsapiPlatformException e) {
						provider.removeCallMonitorThread(obs);
						callsAtAddressMonitorThreads.removeElement(obs);
						throw e;
					}
				}
			}

		}

		sendCallSnapshot(obs, false);
	}

	public void addAddressMonitor(TsapiAddressMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (obsSync) {
			if (addressMonitorThreads.contains(obs)) {
				return;
			}

			addressMonitorThreads.addElement(obs);
			try {
				setMonitor(false, false);

				obs.addReference();
			} catch (TsapiResourceUnavailableException e) {
				provider.removeAddressMonitorThread(obs);
				addressMonitorThreads.removeElement(obs);
				throw e;
			} catch (TsapiPlatformException e) {
				provider.removeAddressMonitorThread(obs);
				addressMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendAddressSnapshot(obs);
	}

	void addConnection(TSConnection tsConn) {
		recreate();

		boolean doTerminalObservers = false;
		boolean doAddressObservers = false;

		if ((tsConn.isTerminalConnection()) && (isTerminal())) {
			synchronized (terminalConnections) {
				if (!terminalConnections.contains(tsConn)) {
					terminalConnections.addElement(tsConn);
					doTerminalObservers = true;
				}

			}

		}

		if ((!provider.isLucent()) || (!tsConn.isTerminalConnection())) {
			synchronized (connections) {
				if (!connections.contains(tsConn)) {
					connections.addElement(tsConn);
					doAddressObservers = true;
				}
			}
		}

		if ((!doTerminalObservers) && (!doAddressObservers)) {
			return;
		}

		TSCall call = tsConn.getTSCall();
		call.addDeviceObservers(this,
				(doTerminalObservers) ? callsAtTerminalMonitorThreads : null,
				(doAddressObservers) ? callsAtAddressMonitorThreads : null,
				(doAddressObservers) ? callsViaAddressMonitorThreads : null,
				true);
	}

	void addName(CSTAExtendedDeviceID deviceID) {
		recreate();
		String devName = getName();

		synchronized (devNameVector) {
			if (!devNameVector.contains(deviceID)) {
				log.info("Renaming device " + this + " with name " + devName
						+ " to " + deviceID + " for " + provider);
				devNameVector.addElement(deviceID);
			}
		}
	}

	public void addRouteMonitor(TsapiRouteMonitor observer) {
		recreate();

		synchronized (obsSync) {
			registerRoute();

			tsRouteCallback = observer;
			tsRouteCallback.addReference();
		}
	}

	void addSession(int routingCrossRefID, TSRouteSession routeSession) {
		recreate();

		if (sessionHash == null) {
			return;
		}
		Object oldObj = sessionHash.put(new Integer(routingCrossRefID),
				routeSession);
		if (oldObj != null) {
			log.info("NOTICE: sessionHash.put() replaced " + oldObj + " for "
					+ this);
		}
	}

	public void addTerminalCallMonitor(TsapiCallMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (obsSync) {
			if (callsAtTerminalMonitorThreads.contains(obs)) {
				return;
			}

			callsAtTerminalMonitorThreads.addElement(obs);
			try {
				setMonitor(false, true);

				obs.addReference();
			} catch (TsapiResourceUnavailableException e) {
				provider.removeCallMonitorThread(obs);
				callsAtTerminalMonitorThreads.removeElement(obs);
				throw e;
			} catch (TsapiPlatformException e) {
				provider.removeCallMonitorThread(obs);
				callsAtTerminalMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendCallSnapshot(obs, true);
	}

	public void addTerminalMonitor(TsapiTerminalMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (obsSync) {
			if (terminalMonitorThreads.contains(obs)) {
				return;
			}

			terminalMonitorThreads.addElement(obs);
			try {
				setMonitor(false, false);

				obs.addReference();
			} catch (TsapiResourceUnavailableException e) {
				provider.removeTerminalMonitorThread(obs);
				terminalMonitorThreads.removeElement(obs);
				throw e;
			} catch (TsapiPlatformException e) {
				provider.removeTerminalMonitorThread(obs);
				terminalMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendTerminalSnapshot(obs);
	}

	void addToACDVector(TSAgent tsAgent) {
		recreate();

		tsACDVector.addElement(tsAgent);
	}

	void addToAgentTermVector(TSAgent tsAgent) {
		recreate();

		tsAgentTermVector.addElement(tsAgent);
	}

	public TSAgent addTSAgent(TSDevice tsACDDevice, int initialState,
			int workMode, int reasonCode, String agentID, String password,
			CSTAPrivate reqPriv) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException {
		recreate();

		String acdName = null;

		if (tsACDDevice != null) {
			acdName = tsACDDevice.getName();
		}

		TSAgentKey agentKey = null;
		agentKey = new TSAgentKey(getName(), acdName, agentID);
		TSAgent tsAgent = null;
		tsAgent = provider.findAgent(agentKey);
		if (tsAgent != null) {
			return tsAgent;
		}

		if ((initialState != 4) && (initialState != 3) && (initialState != 1)) {
			throw new TsapiInvalidArgumentException(3, 0,
					"initial state not valid");
		}

		int state = 1;

		boolean reqPrivPresent = false;
		if (provider.isLucent()) {
			if (reqPriv != null) {
				reqPrivPresent = true;
			} else {
				LucentSetAgentState lsas = null;
				if (workMode == 1) {
					lsas = createLucentSetAgentState((short) 3);
				} else if (workMode == 2) {
					lsas = createLucentSetAgentState((short) 4);
				} else if (initialState == 4) {
					workMode = 1;
					lsas = createLucentSetAgentState((short) 3);
				} else if ((initialState == 3) || (initialState == 1)) {
					workMode = 0;

					lsas = createLucentSetAgentState((short) 1, reasonCode);
				}

				reqPriv = lsas.makeTsapiPrivate();
			}

		}

		try {
			setTSAgent(acdName, state, agentID, password, reqPriv);

			tsAgent = provider.createAgent(agentKey, agentID, password);
			if (tsAgent.getInternalState() == 2) {
				provider.dumpAgent(agentKey);
				tsAgent = provider.createAgent(agentKey, agentID, password);
			}

			if (provider.isLucent()) {
				if (!reqPrivPresent) {
					tsAgent.updateState(initialState, workMode, reasonCode,
							null);
				}

			} else {
				tsAgent.updateState(1, 0, 0, null);
			}
			tsAgent.getState();
			return tsAgent;
		} catch (TsapiInvalidStateException e) {
			if ((acdName != null) && (e.getErrorType() == 2)
					&& (e.getErrorCode() == 21)) {
				snapshotAgentsInTerminal(acdName, agentID);

				agentKey = new TSAgentKey(getName(), acdName, null);
				tsAgent = provider.findAgent(agentKey);
				if (tsAgent != null) {
					return tsAgent;
				}

				throw new TsapiPlatformException(4, 0, "add Agent failure");
			}
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 22)) {
				throw new TsapiPlatformException(4, 0,
						"Agent is already logged into another Station");
			}

			throw new TsapiPlatformException(4, 0, "add Agent failure");
		} catch (TsapiPlatformException e) {
			if ((acdName != null) && (e.getErrorType() == 2)
					&& (e.getErrorCode() == 1)) {
				snapshotAgentsInTerminal(acdName, agentID);

				agentKey = new TSAgentKey(getName(), acdName, null);
				tsAgent = provider.findAgent(agentKey);
				if (tsAgent != null) {
					return tsAgent;
				}

				throw new TsapiPlatformException(4, 0,
						"There is already an Agent logged on at the station");
			}
			if ((acdName == null) && (e.getErrorType() == 2)
					&& (e.getErrorCode() == 1)) {
				throw new TsapiPlatformException(4, 0,
						"There is already an Agent logged on at the station");
			}
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 0)) {
				throw new TsapiPlatformException(4, 0,
						"An attempt to log in an ACD agent with an incorrect password");
			}
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 12)) {
				throw new TsapiPlatformException(4, 0,
						"Invalid AgentId is specified");
			}

			throw e;
		}
	}

	public void cancelForwarding(CSTAPrivate reqPriv)
			throws TsapiInvalidStateException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetFwd() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		getForwarding();

		CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];

		short forwardingType = 0;
		boolean forwardingOn = false;

		synchronized (fwdVector) {
			for (int i = 0; i < fwdVector.size(); ++i) {
				TsapiCallControlForwarding fwd = (TsapiCallControlForwarding) fwdVector
						.elementAt(i);
				String forwardDN = fwd.getDestinationAddress();
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
				ConfHandler handler = new FwdConfHandler(this, fwdArray);
				try {
					provider.tsapi.setFwd(getName(), forwardingType,
							forwardingOn, forwardDN, reqPriv, handler);
				} catch (TsapiInvalidStateException e) {
					throw e;
				} catch (TsapiPlatformException e) {
					throw e;
				} catch (Exception e) {
					if (e instanceof ITsapiException) {
						throw new TsapiPlatformException(((ITsapiException) e)
								.getErrorType(), ((ITsapiException) e)
								.getErrorCode(), "setFwd failure");
					}
					throw new TsapiPlatformException(4, 0, "setFwd failure");
				}
			}
		}
	}

	boolean cleanUCIDsInCallsInConnections() {
		boolean bfound = false;
		Vector conns = new Vector(connections);
		for (int i = 0; i < conns.size(); ++i) {
			TSConnection conn = (TSConnection) conns.elementAt(i);
			TSCall call = conn.getTSCall();

			if ((call.state == 34) || (call.cleanUCIDInCall())) {
				continue;
			}
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
		if (tsapiInstructions.length == 0) {
			return null;
		}
		CallControlForwarding[] instructions = new CallControlForwarding[tsapiInstructions.length];
		int i;
		for (i = 0; i < tsapiInstructions.length; ++i) {
			if (tsapiInstructions[i].getFilter() == 1) {
				instructions[i] = new CallControlForwarding(
						tsapiInstructions[i].getDestinationAddress(),
						tsapiInstructions[i].getType());
			} else {
				instructions[i] = new CallControlForwarding(
						tsapiInstructions[i].getDestinationAddress(),
						tsapiInstructions[i].getType(), tsapiInstructions[i]
								.getFilter() == 2);
			}

		}

		return instructions;
	}

	private LucentSetAgentState createLucentSetAgentState(short workMode) {
		return createLucentSetAgentState(workMode, 0);
	}

	private LucentSetAgentState createLucentSetAgentState(short workMode,
			int reasonCode) {
		if (provider.isLucentV5()) {
			return new LucentV5SetAgentState(workMode, reasonCode);
		}
		return new LucentSetAgentState(workMode);
	}

	void deleteSession(int routingCrossRefID) {
		recreate();

		if (sessionHash == null) {
			return;
		}
		sessionHash.remove(new Integer(routingCrossRefID));
	}

	boolean doAddressSnapshot() {
		recreate();

		if (getDeviceType() == 1) {
			return true;
		}

		if (provider.getCapabilities().getQueryDnd() != 0) {
			ConfHandler handler = new DNDConfHandler(this);
			try {
				provider.tsapi.queryDoNotDisturb(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		if (provider.getCapabilities().getQueryMwi() != 0) {
			ConfHandler handler = new MWIConfHandler(this);
			try {
				provider.tsapi.queryMsgWaitingInd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		if (provider.getCapabilities().getQueryFwd() != 0) {
			ConfHandler handler = new FwdConfHandler(this);
			try {
				provider.tsapi.queryFwd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		snapshotAgentsInACD();

		return true;
	}

	boolean doCallSnapshot() {
		recreate();

		if (provider.getCapabilities().getSnapshotDeviceReq() == 0) {
			return false;
		}

		SnapshotDeviceConfHandler handler = new SnapshotDeviceConfHandler(this);
		try {
			provider.tsapi.snapshotDevice(getName(), null, handler);
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}

		Vector newCalls = new Vector();

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
					if (isMonitorSet()) {
						call.setNeedSnapshot(false);
					}

				} catch (TsapiPlatformException e) {
					// break label215:
					break;
				}

				label215: if (!newCalls.contains(call)) {
					newCalls.addElement(call);
				}

			}

		}

		Vector conns = new Vector(connections);
		for (int i = 0; i < conns.size(); ++i) {
			TSConnection conn = (TSConnection) conns.elementAt(i);
			if (newCalls.contains(conn.getTSCall())) {
				continue;
			}
			conn.setConnectionState(89, null);
		}

		conns = new Vector(terminalConnections);
		for (int i = 0; i < conns.size(); ++i) {
			TSConnection conn = (TSConnection) conns.elementAt(i);
			if (newCalls.contains(conn.getTSCall())) {
				continue;
			}
			conn.setTermConnState(102, null);
		}

		return true;
	}

	boolean doTerminalSnapshot() {
		recreate();

		if (!isTerminal()) {
			return true;
		}

		snapshotAgentsInTerminal(null, null);

		return true;
	}

	void dump(String indent) {
		log.trace(indent + "***** DEVICE DUMP *****");
		log.trace(indent + "TSDevice: " + this);
		log.trace(indent + "TSDevice names: ");
		synchronized (devNameVector) {
			for (int i = 0; i < devNameVector.size(); ++i) {
				log.trace(indent + devNameVector.elementAt(i));
			}
		}

		log.trace(indent + "TSDevice connections: ");
		Vector connectionsClone = (Vector) connections.clone();
		int i;
		for (i = 0; i < connectionsClone.size(); ++i) {
			TSConnection conn = (TSConnection) connectionsClone.elementAt(i);
			conn.dump(indent + " ");
		}

		log.trace(indent + "TSDevice terminalConnections: ");
		Vector terminalConnectionsClone = (Vector) terminalConnections.clone();
		TSConnection conn = null;
		for (i = 0; i < terminalConnectionsClone.size(); ++i) {
			conn = (TSConnection) terminalConnectionsClone.elementAt(i);
			conn.dump(indent + " ");
		}

		log.trace(indent + "TSDevice ACD Agents: ");
		Vector tsACDVectorClone = (Vector) tsACDVector.clone();

		for (i = 0; i < tsACDVectorClone.size(); ++i) {
			TSAgent agent = (TSAgent) tsACDVectorClone.elementAt(i);
			agent.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Terminal Agents: ");
		synchronized (tsAgentTermVector) {
			TSAgent agent = null;
			for (i = 0; i < tsAgentTermVector.size(); ++i) {
				agent = (TSAgent) tsAgentTermVector.elementAt(i);
				agent.dump(indent + " ");
			}
		}
		if (sessionHash != null) {
			log.trace(indent + "TSDevice Route Sessions: ");
			synchronized (sessionHash) {
				Enumeration sessionEnum = sessionHash.elements();

				while (sessionEnum.hasMoreElements()) {
					TSRouteSession routeSession;
					try {
						routeSession = (TSRouteSession) sessionEnum
								.nextElement();
					} catch (NoSuchElementException e) {
						log.error(e.getMessage(), e);
						continue;
					}

					routeSession.dump(indent + " ");
				}
			}
		}
		log.trace(indent + "TSDevice Terminal Monitor Threads: ");

		Vector terminalMonitorThreadsClone = (Vector) terminalMonitorThreads
				.clone();
		int j;
		for (j = 0; j < terminalMonitorThreadsClone.size(); ++j) {
			TsapiTerminalMonitor tOThreads = (TsapiTerminalMonitor) terminalMonitorThreadsClone
					.elementAt(j);
			tOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Address Monitor Threads: ");

		Vector addressMonitorThreadsClone = (Vector) addressMonitorThreads
				.clone();
		int k;
		for (k = 0; k < addressMonitorThreadsClone.size(); ++k) {
			TsapiAddressMonitor aOThreads = (TsapiAddressMonitor) addressMonitorThreadsClone
					.elementAt(k);
			aOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Calls At Address Monitor Threads: ");
		Vector callsAtAddressMonitorThreadsClone = (Vector) callsAtAddressMonitorThreads
				.clone();

		for (i = 0; i < callsAtAddressMonitorThreadsClone.size(); ++i) {
			TsapiCallMonitor cAAOThreads = (TsapiCallMonitor) callsAtAddressMonitorThreadsClone
					.elementAt(i);
			cAAOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Calls Via Address Monitor Threads: ");
		Vector callsViaAddressMonitorThreadsClone = (Vector) callsViaAddressMonitorThreads
				.clone();

		for (i = 0; i < callsViaAddressMonitorThreadsClone.size(); ++i) {
			TsapiCallMonitor cVAOThreads = (TsapiCallMonitor) callsViaAddressMonitorThreadsClone
					.elementAt(i);
			cVAOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Calls At Terminal Monitor Threads: ");
		Vector callsAtTerminalMonitorThreadsClone = (Vector) callsAtTerminalMonitorThreads
				.clone();

		for (i = 0; i < callsAtTerminalMonitorThreadsClone.size(); ++i) {
			TsapiCallMonitor cATOThreads = (TsapiCallMonitor) callsAtTerminalMonitorThreadsClone
					.elementAt(i);
			cATOThreads.dump(indent + " ");
		}
		log.trace(indent + "wasReferenced is " + wasReferenced);
		log.trace(indent + "***** DEVICE DUMP END *****");
	}

	protected void finalize() throws Throwable {
		try {
			log.info("TSDevice " + this + " - finalize() in state " + curState);
			realDelete();
		} finally {
			super.finalize();
		}
	}

	public Vector<TsapiCallMonitor> getAddressCallObservers() {
		recreate();

		Vector obs = (Vector) callsViaAddressMonitorThreads.clone();
		for (int i = 0; i < callsAtAddressMonitorThreads.size(); ++i) {
			obs.addElement(callsAtAddressMonitorThreads.elementAt(i));
		}
		return obs;
	}

	public Vector<TsapiAddressMonitor> getAddressObservers() {
		recreate();

		return new Vector(addressMonitorThreads);
	}

	public Object getAddrPrivateData() {
		recreate();

		if (replyAddrPriv instanceof CSTAPrivate) {
			return replyAddrPriv;
		}
		return null;
	}

	String getAgentID() {
		recreate();

		if (provider.getCapabilities().getQueryDeviceInfo() != 0) {
			if (provider.isLucentV5()) {
				try {
					CSTAEvent event = provider.tsapi.queryDeviceInfo(getName(),
							null);
					if (event != null) {
						Object replyPriv = event.getPrivData();
						if (replyPriv instanceof LucentV5QueryDeviceInfoConfEvent) {
							LucentV5QueryDeviceInfoConfEvent V5devInfoConf = (LucentV5QueryDeviceInfoConfEvent) replyPriv;
							return V5devInfoConf.getAssociatedDevice();
						}

					}

				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else if (provider.isLucent()) {
				LucentQueryCallClassifier lqcc = null;
				lqcc = new LucentQueryCallClassifier();
				CSTAPrivate reqPriv = lqcc.makeTsapiPrivate();
				try {
					CSTAEvent event = provider.tsapi.queryDeviceInfo(getName(),
							reqPriv);
					if (event != null) {
						CSTAQueryDeviceInfoConfEvent devInfoConf = (CSTAQueryDeviceInfoConfEvent) event
								.getEvent();
						return devInfoConf.getDevice();
					}
					return null;
				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		return null;
	}

	short getAssociatedClass() {
		recreate();

		if (!asyncInitializationComplete) {
			log.info("getAssociatedClass() for " + this);
		}

		waitForAsyncInitialization();

		return associatedClass;
	}

	String getAssociatedDevice() {
		recreate();

		if (!asyncInitializationComplete) {
			log.info("getAssociatedDevice() for " + this);
		}

		waitForAsyncInitialization();

		return associatedDevice;
	}

	Vector<TSConnection> getConns() {
		recreate();

		return (Vector) (Vector) connections.clone();
	}

	Vector<TsapiCallMonitor> getCVDObservers() {
		recreate();

		Vector obs = (Vector) callsViaAddressMonitorThreads.clone();
		return obs;
	}

	public short getDeviceType() {
		recreate();

		if (!asyncInitializationComplete) {
			log.info("getDeviceType() for " + this);
		}

		waitForAsyncInitialization();

		return deviceType;
	}

	public String getDirectoryName() {
		recreate();
		try {
			LucentQueryDeviceName lqdn = new LucentQueryDeviceName(getName());
			Object lqdnConf = provider.sendPrivateData(lqdn.makeTsapiPrivate(),
					null, true);
			if (lqdnConf instanceof LucentQueryDeviceNameConfEvent) {
				return ((LucentQueryDeviceNameConfEvent) lqdnConf).getName();
			}
			return null;
		} catch (TsapiPlatformException e) {
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 12)) {
				return null;
			}

			throw e;
		} catch (Exception e) {
			if (e instanceof TsapiInvalidArgumentException) {
				return null;
			}
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"queryDeviceName failure");
			}

			throw new TsapiPlatformException(4, 0, "queryDeviceName failure");
		}
	}

	public String getDomainName() {
		return internalGetName();
	}

	public boolean getDoNotDisturb() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getDoNotDisturbEvent() == 0) {
			if (provider.getCapabilities().getQueryDnd() == 0) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

			ConfHandler handler = new DNDConfHandler(this);
			try {
				provider.tsapi.queryDoNotDisturb(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return dndState;
			}
		}
		return dndState;
	}

	public Vector<TsapiCallControlForwarding> getForwarding()
			throws TsapiMethodNotSupportedException {
		recreate();

		if ((provider.getCapabilities().getForwardingEvent() == 0)
				|| (fwdVector.size() == 0)) {
			if (provider.getCapabilities().getQueryFwd() == 0) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

			ConfHandler handler = new FwdConfHandler(this);
			try {
				provider.tsapi.queryFwd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
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
			if (provider.getCapabilities().getQueryMwi() == 0) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

			ConfHandler handler = new MWIConfHandler(this);
			try {
				provider.tsapi.queryMsgWaitingInd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
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

		Vector obs = new Vector(1);
		if (tsRouteCallback != null) {
			obs.addElement(tsRouteCallback);
		}
		return obs;
	}

	Vector<TSRouteSession> getSessions() {
		recreate();

		if (sessionHash == null) {
			return null;
		}

		Vector sessionVector = new Vector();
		Enumeration sessionEnum = sessionHash.elements();
		while (sessionEnum.hasMoreElements()) {
			try {
				sessionVector.addElement((TSRouteSession) sessionEnum
						.nextElement());
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
			}
		}

		return sessionVector;
	}

	Vector<TSConnection> getTermConns() {
		recreate();

		return (Vector) (Vector) terminalConnections.clone();
	}

	public Vector<TsapiCallMonitor> getTerminalCallObservers() {
		recreate();

		return (Vector) callsAtTerminalMonitorThreads.clone();
	}

	TerminalCapabilities getTerminalCapabilities(TSDevice cevice)
			throws TsapiInvalidArgumentException {
		recreate();

		return null;
	}

	public Vector<TsapiTerminalMonitor> getTerminalObservers() {
		recreate();

		return new Vector(terminalMonitorThreads);
	}

	Object getTermPrivateData() {
		recreate();

		if (replyTermPriv instanceof CSTAPrivate) {
			return replyTermPriv;
		}
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

		Vector devVector = new Vector();

		devVector.addElement(this);
		return devVector;
	}

	public int getTSAgentsAvailable() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.isLucent()) {
			try {
				LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(getName());
				Object lqasConf = provider.sendPrivateData(lqas
						.makeTsapiPrivate());
				if (lqasConf instanceof LucentQueryAcdSplitConfEvent) {
					return ((LucentQueryAcdSplitConfEvent) lqasConf)
							.getAvailableAgents();
				}
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			} catch (Exception e) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

		}
		return 0;
	}

	public Vector<TSAgent> getTSAgentsForACDAddr() {
		recreate();

		if (monitorDeviceXRefID == 0) {
			snapshotAgentsInACD();
		}

		return tsACDVector;
	}

	public Vector<TSAgent> getTSAgentsForAgentTerm() {
		recreate();

		if ((monitorDeviceXRefID == 0) || (provider.isLucent())) {
			snapshotAgentsInTerminal(null, null);
		}
		return tsAgentTermVector;
	}

	public int getTSAgentsLoggedIn() throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.isLucent()) {
			try {
				LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(getName());
				Object lqasConf = provider.sendPrivateData(lqas
						.makeTsapiPrivate());
				if (lqasConf instanceof LucentQueryAcdSplitConfEvent) {
					return ((LucentQueryAcdSplitConfEvent) lqasConf)
							.getAgentsLoggedIn();
				}
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			} catch (Exception e) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

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

		if (provider.isLucent()) {
			try {
				LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(getName());
				Object lqasConf = provider.sendPrivateData(lqas
						.makeTsapiPrivate());
				if (lqasConf instanceof LucentQueryAcdSplitConfEvent) {
					return ((LucentQueryAcdSplitConfEvent) lqasConf)
							.getCallsInQueue();
				}
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			} catch (Exception e) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

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

		if (tsRouteCallback != null) {
			return getSessions();
		}
		return null;
	}

	public Vector<TSConnection> getTSTerminalConnections() {
		recreate();

		updateObject();
		return getTermConns();
	}

	public Vector<TSDevice> getTSTerminalDevices() {
		recreate();

		Vector devVector = new Vector();
		if (isTerminal()) {
			devVector.addElement(this);
		}
		return devVector;
	}

	public TSConnection groupPickup(TSDevice terminalAddress,
			CSTAPrivate privData) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getGroupPickupCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		ConfHandler handler = new TermPrivConfHandler(this, 20);
		try {
			provider.tsapi.groupPickupCall(terminalAddress.getName(), privData,
					handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiInvalidArgumentException e) {
			throw e;
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"groupPickupCall failure");
			}
			throw new TsapiPlatformException(4, 0, "groupPickupCall failure");
		}

		throw new TsapiPlatformException(4, 0,
				"Could not meet post-conditions of groupPickup()");
	}

	void handleAgentLoginResponse(LucentQueryAgentLoginResp event) {
		recreate();

		int xrefID = event.getPrivEventCrossRefID();
		if ((event.getDevices() == null) || (event.getDevices().length == 0)) {
			provider.deletePrivateXref(xrefID);
			synchronized (this) {
				if (waitingForAgents) {
					waitingForAgents = false;
					super.notify();
				}
			}
		} else {
			for (int i = 0; i < event.getDevices().length; ++i) {
				TSDevice agentDevice = provider
						.createDevice(event.getDevices()[i]);
				String agentID = agentDevice.getAgentID();
				TSAgentKey agentKey = new TSAgentKey(event.getDevices()[i],
						getName(), agentID);
				TSAgent agent = provider.createAgent(agentKey, "", "");
				if (agent.getInternalState() != 2) {
					continue;
				}
				provider.dumpAgent(agentKey);
				agent = provider.createAgent(agentKey, "", "");
			}
		}
	}

	private String internalGetName() {
		return ((CSTAExtendedDeviceID) devNameVector.lastElement())
				.getDeviceID();
	}

	synchronized void internalRecreate() {
		Vector keys = new Vector(devNameVector);
		log.info("Recreating deleted device " + this);
		for (int i = 0; i < keys.size(); ++i) {
			String key = ((CSTAExtendedDeviceID) keys.elementAt(i))
					.getDeviceID();
			provider.addDeviceToHash(key, this);
		}

		setState(new TSDeviceStateActive());

		log.info("Device "
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

		if (!asyncInitializationComplete) {
			log.info("isDeviceExternal() for " + this);
		}

		waitForAsyncInitialization();

		return isExternal;
	}

	boolean isForExternalDeviceMatchingLocalExtensionNumber(
			CSTAExtendedDeviceID devIDOnCall) {
		if (hasMixOfPublicAndPrivate(devNameVector)) {
			StringBuffer tmpStrBuf = new StringBuffer();
			for (CSTAExtendedDeviceID tmpDevID : devNameVector) {
				tmpStrBuf.append(tmpDevID + " ");
			}
			log
					.info("TSDevice ["
							+ this
							+ "] has both public and private deviceID types. Here is a list : "
							+ tmpStrBuf.toString());
		}

		CSTAExtendedDeviceID lastDeviceExtDevID = (CSTAExtendedDeviceID) getKeys()
				.lastElement();

		return (lastDeviceExtDevID.hasPrivateDeviceIDType())
				&& (devIDOnCall.hasPublicDeviceIDType());
	}

	boolean isMonitorSet() {
		recreate();

		return (monitorDeviceXRefID != 0) || (monitorCallsViaDeviceXRefID != 0);
	}

	boolean isPredictiveCallsViaDeviceMonitorSet() {
		recreate();

		return (monitorCallsViaDeviceXRefID != 0)
				&& (monitorPredictiveCallsViaDevice);
	}

	public boolean isTerminal() {
		recreate();

		if (!asyncInitializationComplete) {
			log.info("isTerminal() for " + this);
		}

		waitForAsyncInitialization();

		return isATerminal;
	}

	CSTAConnectionID matchConn(CSTAConnectionID connID) {
		recreate();

		if (connID == null) {
			return null;
		}

		synchronized (terminalConnections) {
			for (int i = 0; i < terminalConnections.size(); ++i) {
				TSConnection conn = (TSConnection) terminalConnections
						.elementAt(i);
				if (connID.equals(conn.connID)) {
					return conn.connID;
				}
			}
		}
		synchronized (connections) {
			for (int i = 0; i < connections.size(); ++i) {
				TSConnection conn = (TSConnection) connections.elementAt(i);
				if (connID.equals(conn.connID)) {
					return conn.connID;
				}
			}
		}

		return null;
	}

	synchronized void notifyAsyncInitializationComplete() {
		recreate();

		log.info("Initialization complete for TSDevice " + this
				+ " - making values available - for " + provider);
		asyncInitializationComplete = true;
		super.notifyAll();
	}

	public TSConnection pickup(TSConnection pickConnection,
			TSDevice terminalAddress, CSTAPrivate privData)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getPickupCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		if (pickConnection.getTSCall().updateObject()) {
			int state = pickConnection.getTSConnState();
			if ((state != 50) && (state != 49) && (state != 54)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(pickConnection, true), 2, state,
						"connection not alerting or in progress");
			}

		}

		ConfHandler handler = new PickupConfHandler(this, terminalAddress,
				pickConnection);
		try {
			provider.tsapi.pickupCall(pickConnection.getConnID(),
					terminalAddress.getName(), privData, handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiInvalidArgumentException e) {
			throw e;
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"pickupCall failure");
			}
			throw new TsapiPlatformException(4, 0, "pickupCall failure");
		}

		throw new TsapiPlatformException(4, 0,
				"Could not meet post-conditions of pickup()");
	}

	public TSConnection pickup(TSDevice pickAddress, TSDevice terminalAddress,
			CSTAPrivate privData) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getPickupCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		TSConnection pickConnection = null;

		pickAddress.updateObject();
		synchronized (pickAddress.connections) {
			for (int i = 0; i < pickAddress.connections.size(); ++i) {
				pickConnection = (TSConnection) pickAddress.connections
						.elementAt(i);
				if ((pickConnection.getTSConnState() == 50)
						|| (pickConnection.getTSConnState() == 49)
						|| (pickConnection.getTSConnState() == 54)) {
					return pickup(pickConnection, terminalAddress, privData);
				}
			}
		}
		throw new TsapiInvalidArgumentException(3, 0,
				"no connection found to pickup");
	}

	private void realDelete() {
		if (curState.wasDeleteDone()) {
			log
					.info("Device "
							+ this
							+ " realDelete: already deleted - no further action taken, "
							+ provider);
			return;
		}

		log.info("Device " + this + " being deleted for " + provider);

		stopMonitorForThisDevice();

		if ((Tsapi.getTSDevicePerformanceOptimization() == true)
				&& (asyncInitializationComplete) && (!isDeviceExternal())) {
			log.info("NOT deleting " + this
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
			g_RefCnt += 1;
			wasReferenced = true;
		}

		return ((CSTAExtendedDeviceID) devNameVector.lastElement())
				.getDeviceID();
	}

	void registerRoute() {
		recreate();

		ConfHandler handler = new RouteRegisterConfHandler(this);
		try {
			if (getName().equals("AllRouteAddress")) {
				provider.tsapi.registerRouteCallback(null, null, handler);
			} else {
				provider.tsapi.registerRouteCallback(getName(), null, handler);
			}
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"registerRouteCallback failure");
			}
			throw new TsapiPlatformException(4, 0,
					"registerRouteCallback failure");
		}
		provider.addRoute(registerReqID, this);
		sessionHash = new Hashtable(3);
	}

	public void removeAddressCallMonitor(TsapiCallMonitor obs) {
		recreate();

		removeAddressCallMonitor(obs, 100, null);
	}

	protected void removeAddressCallMonitor(TsapiCallMonitor obs, int cause,
			Object privateData) {
		recreate();

		if (!callsViaAddressMonitorThreads.removeElement(obs)) {
			callsAtAddressMonitorThreads.removeElement(obs);
		}
		obs.deleteReference(this, false, cause, privateData);

		testDelete();
	}

	public void removeAddressMonitor(TsapiAddressMonitor obs) {
		recreate();

		removeAddressMonitor(obs, 100, null);
	}

	protected void removeAddressMonitor(TsapiAddressMonitor obs, int cause,
			Object privateData) {
		recreate();

		if (!addressMonitorThreads.removeElement(obs)) {
			return;
		}
		obs.deleteReference(this, cause, privateData);
		testDelete();
	}

	void removeConnection(TSConnection tsConn) {
		recreate();

		if (connections.removeElement(tsConn)) {
			TSCall call = tsConn.getTSCall();
			call.removeDefaultDeviceObservers(this, false);
		}
		if (terminalConnections.removeElement(tsConn)) {
			TSCall call = tsConn.getTSCall();
			call.removeDefaultDeviceObservers(this, true);
		}

		synchronized (this) {
			synchronized (devNameVector) {
				Vector keys = new Vector(devNameVector);
				for (int i = 0; i < keys.size(); ++i) {
					CSTAExtendedDeviceID devID = (CSTAExtendedDeviceID) keys
							.elementAt(i);
					if ((devID.getDeviceIDStatus() != 0)
							|| ((devID.getDeviceIDType() != 70) && (devID
									.getDeviceIDType() != 71))) {
						continue;
					}

					if (devNameVector.size() == 1) {
						addName(new CSTAExtendedDeviceID("", (short) 70,
								(short) 1));
					}
					provider.deleteDeviceFromHash(devID.getDeviceID());
					devNameVector.removeElement(devID);
				}

			}

		}

		testDelete();
	}

	void removeFromACDVector(TSAgent tsAgent) {
		recreate();

		tsACDVector.removeElement(tsAgent);
		testDelete();
	}

	void removeFromAgentTermVector(TSAgent tsAgent) {
		recreate();

		tsAgentTermVector.removeElement(tsAgent);
		testDelete();
	}

	void removeInternalMonitor(TSCall call) {
		recreate();

		internalDeviceMonitors.removeElement(call);

		testDelete();
	}

	void removeObservers(int cause, Object privateData, int xrefID) {
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

		Vector observers = new Vector(addressMonitorThreads);
		for (int i = 0; i < observers.size(); ++i) {
			removeAddressMonitor((TsapiAddressMonitor) observers.elementAt(i),
					cause, privateData);
		}
		Vector terminalObservers = new Vector(terminalMonitorThreads);
		for (int i = 0; i < terminalObservers.size(); ++i) {
			removeTerminalMonitor((TsapiTerminalMonitor) terminalObservers
					.elementAt(i), cause, privateData);
		}
		Vector callsViaAddressObservers = new Vector(
				callsViaAddressMonitorThreads);
		for (int i = 0; i < callsViaAddressObservers.size(); ++i) {
			removeAddressCallMonitor(
					(TsapiCallMonitor) callsViaAddressObservers.elementAt(i),
					cause, privateData);
		}

		Vector callsAtAddressObservers = new Vector(
				callsAtAddressMonitorThreads);
		for (int i = 0; i < callsAtAddressObservers.size(); ++i) {
			removeAddressCallMonitor((TsapiCallMonitor) callsAtAddressObservers
					.elementAt(i), cause, privateData);
		}

		Vector callsAtTerminalObservers = new Vector(
				callsAtTerminalMonitorThreads);
		for (int i = 0; i < callsAtTerminalObservers.size(); ++i) {
			removeTerminalCallMonitor(
					(TsapiCallMonitor) callsAtTerminalObservers.elementAt(i),
					cause, privateData);
		}

		internalDeviceMonitors.removeAllElements();

		stopMonitorForThisDevice();
	}

	public void removeRouteMonitor(TsapiRouteMonitor observer) {
		recreate();

		removeRouteMonitor(observer, 100, null);
	}

	protected void removeRouteMonitor(TsapiRouteMonitor observer, int cause,
			Object privateData) {
		recreate();
		try {
			provider.tsapi.cancelRouteCallback(registerReqID, null, null);
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"cancelRouteCallback failure");
			}
			throw new TsapiPlatformException(4, 0,
					"cancelRouteCallback failure");
		}
		observer.deleteReference(this);
		testDelete();
	}

	public void removeTerminalCallMonitor(TsapiCallMonitor obs) {
		recreate();

		removeTerminalCallMonitor(obs, 100, null);
	}

	protected void removeTerminalCallMonitor(TsapiCallMonitor obs, int cause,
			Object privateData) {
		recreate();

		callsAtTerminalMonitorThreads.removeElement(obs);

		obs.deleteReference(this, true, cause, privateData);

		testDelete();
	}

	public void removeTerminalMonitor(TsapiTerminalMonitor obs) {
		recreate();

		removeTerminalMonitor(obs, 100, null);
	}

	protected void removeTerminalMonitor(TsapiTerminalMonitor obs, int cause,
			Object privateData) {
		recreate();

		if (!terminalMonitorThreads.removeElement(obs)) {
			return;
		}
		obs.deleteReference(this, cause, privateData);
		testDelete();
	}

	public void removeTSAgent(TSAgent tsAgent, int reasonCode)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		recreate();

		if (tsAgent == null) {
			if (tsAgentTermVector.size() == 0) {
				throw new TsapiInvalidArgumentException(3, 0,
						"No agents logged into specified Terminal");
			}

			Vector agentVector = new Vector(tsAgentTermVector);
			for (int i = 0; i < agentVector.size(); ++i) {
				tsAgent = (TSAgent) agentVector.elementAt(i);
				if (tsAgent.getState() == 2) {
					continue;
				}

				tsAgent.setState(2, 0, reasonCode);
			}

		} else {
			if (tsAgent.getState() == 2) {
				return;
			}

			if (!tsAgentTermVector.contains(tsAgent)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"Agent not logged into specified Terminal");
			}

			tsAgent.setState(2, 0, reasonCode);
		}
	}

	void sendAddressSnapshot(TsapiAddressMonitor callback) {
		recreate();

		if (callback == null) {
			return;
		}

		Vector eventList = new Vector();

		eventList.addElement(new TSEvent(37, this));
		eventList.addElement(new TSEvent(38, this));
		eventList.addElement(new TSEvent(39, this));

		for (int i = 0; i < tsACDVector.size(); ++i) {
			((TSAgent) tsACDVector.elementAt(i)).getSnapshot(eventList);
		}

		if (eventList.size() <= 0) {
			return;
		}
		callback.deliverEvents(eventList, true);
	}

	void sendCallSnapshot(TsapiCallMonitor callback, boolean forTerminal) {
		recreate();

		if (callback == null) {
			return;
		}

		Vector eventList = new Vector();

		if (forTerminal) {
			synchronized (terminalConnections) {
				for (int i = 0; i < terminalConnections.size(); ++i) {
					TSCall call = ((TSConnection) terminalConnections
							.elementAt(i)).getTSCall();

					if (call.getCallObservers().contains(callback)) {
						continue;
					}

					call.getSnapshot(eventList);
					call.addDeviceObservers(this,
							callsAtTerminalMonitorThreads, null, null, false);
				}
			}

		} else {
			synchronized (connections) {
				for (int i = 0; i < connections.size(); ++i) {
					TSCall call = ((TSConnection) connections.elementAt(i))
							.getTSCall();

					if (call.getCallObservers().contains(callback)) {
						continue;
					}

					call.getSnapshot(eventList);
					call.addDeviceObservers(this, null,
							callsAtAddressMonitorThreads,
							callsViaAddressMonitorThreads, false);
				}
			}
		}

		if (eventList.size() <= 0) {
			return;
		}
		callback.deliverEvents(eventList, 110, true);
	}

	public Object sendPrivateData(CSTAPrivate data) {
		recreate();
		try {
			return provider.sendPrivateData(data);
		} catch (Exception e) {
			throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
		}
	}

	void sendTerminalSnapshot(TsapiTerminalMonitor callback) {
		recreate();

		if (callback == null) {
			return;
		}

		Vector eventList = new Vector();

		eventList.addElement(new TSEvent(58, this));

		for (int i = 0; i < tsAgentTermVector.size(); ++i) {
			((TSAgent) tsAgentTermVector.elementAt(i)).getSnapshot(eventList);
		}

		if (eventList.size() <= 0) {
			return;
		}
		callback.deliverEvents(eventList, true);
	}

	void setAssociatedClass(short associatedClass) {
		recreate();

		this.associatedClass = associatedClass;
	}

	void setAssociatedDevice(String associatedDevice) {
		recreate();

		this.associatedDevice = associatedDevice;
	}

	void setDeviceType(short _deviceType) {
		recreate();

		deviceType = _deviceType;
	}

	public void setDoNotDisturb(boolean enable, CSTAPrivate reqPriv)
			throws TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetDnd() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		ConfHandler handler = new DNDConfHandler(this, enable);
		try {
			provider.tsapi.setDnd(getName(), enable, reqPriv, handler);
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setDnd failure");
			}
			throw new TsapiPlatformException(4, 0, "setDnd failure");
		}
	}

	public void setForwarding(Vector<TsapiCallControlForwarding> _fwdVector,
			CSTAPrivate reqPriv) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetFwd() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		cancelForwarding(null);

		CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];

		short forwardingType = 0;
		boolean forwardingOn = true;

		synchronized (_fwdVector) {
			for (int i = 0; i < _fwdVector.size(); ++i) {
				TsapiCallControlForwarding fwd = (TsapiCallControlForwarding) _fwdVector
						.elementAt(i);
				String forwardDN = fwd.getDestinationAddress();
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

				ConfHandler handler = new FwdConfHandler(this, fwdArray);
				try {
					provider.tsapi.setFwd(getName(), forwardingType,
							forwardingOn, forwardDN, reqPriv, handler);
				} catch (TsapiInvalidStateException e) {
					throw e;
				} catch (TsapiInvalidArgumentException e) {
					throw e;
				} catch (TsapiPlatformException e) {
					throw e;
				} catch (Exception e) {
					if (e instanceof ITsapiException) {
						throw new TsapiPlatformException(((ITsapiException) e)
								.getErrorType(), ((ITsapiException) e)
								.getErrorCode(), "setFwd failure");
					}
					throw new TsapiPlatformException(4, 0, "setFwd failure");
				}
			}
		}
	}

	TSDevice setInternalMonitor(TSCall call)
			throws TsapiResourceUnavailableException {
		recreate();

		if (provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(2, 0, 0,
					"no capability to monitor device");
		}
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
				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (TsapiResourceUnavailableException e) {
					throw e;
				} catch (Exception e) {
					throw new TsapiResourceUnavailableException(2, 0, 0,
							"failure to monitor device");
				}

				CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
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

	void setIsATerminal(boolean b) {
		recreate();

		isATerminal = b;
	}

	void setIsExternal(boolean b) {
		recreate();

		isExternal = b;
	}

	public void setMessageWaiting(boolean enable, CSTAPrivate reqPriv)
			throws TsapiInvalidStateException, TsapiMethodNotSupportedException {
		recreate();

		if (provider.getCapabilities().getSetMwi() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		ConfHandler handler = new MWIConfHandler(this,
				msgWaitingBits |= 268435456);
		try {
			provider.tsapi
					.setMsgWaitingInd(getName(), enable, reqPriv, handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setMsgWaitingInd failure");
			}
			throw new TsapiPlatformException(4, 0, "setMsgWaitingInd failure");
		}
	}

	void setMonitor(boolean followCall, boolean callObserver)
			throws TsapiResourceUnavailableException {
		setMonitor(followCall, callObserver, false, null);
	}

	void setMonitor(boolean followCall, boolean callObserver,
			boolean predictive, CSTAPrivate reqPriv)
			throws TsapiResourceUnavailableException {
		recreate();
		CSTAEvent event;
		if ((followCall)
				|| ((callObserver) && (((getDeviceType() == 2) || (getDeviceType() == 1))))) {
			synchronized (this) {
				if (monitorCallsViaDeviceXRefID != 0) {
					return;
				}
				if (provider.getCapabilities().getMonitorCallsViaDevice() != 0) {
					try {
						event = provider.tsapi.monitorCallsViaDevice(getName(),
								new CSTAMonitorFilter(), reqPriv);
						if (event != null) {
							CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
									.getEvent();
							monitorCallsViaDeviceXRefID = monitorConf
									.getMonitorCrossRefID();
							monitorPredictiveCallsViaDevice = predictive;
						}
					} catch (TsapiUnableToSendException tue) {
						throw tue;
					} catch (Exception e) {
						log
								.error("MonitorCallsViaDevice request failed - retrying");
						try {
							event = provider.tsapi
									.monitorCallsViaDevice(getName(),
											new CSTAMonitorFilter(), reqPriv);
							CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
									.getEvent();
							monitorCallsViaDeviceXRefID = monitorConf
									.getMonitorCrossRefID();
							monitorPredictiveCallsViaDevice = predictive;
						} catch (TsapiResourceUnavailableException e1) {
							throw e1;
						} catch (Exception e1) {
							if (followCall) {
								throw new TsapiResourceUnavailableException(2,
										0, 0,
										"failure to monitor calls via device");
							}
						}

					}

				}

			}

		}

		if ((!followCall)
				&& (((!provider.isLucent()) || ((provider.isLucent()) && (getDeviceType() != 1))))) {
			if (provider.getCapabilities().getMonitorDevice() == 0) {
				throw new TsapiResourceUnavailableException(2, 0, 0,
						"no capability to monitor device");
			}
			synchronized (this) {
				if (monitorDeviceXRefID != 0) {
					return;
				}

				try {
					event = provider.tsapi.monitorDevice(getName(),
							new CSTAMonitorFilter(), null);
				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (Exception e) {
					log.error("MonitorDevice request failed - retrying", e);
					log.info("MonitorDevice request failed - retrying");
					try {
						event = provider.tsapi.monitorDevice(getName(),
								new CSTAMonitorFilter(), null);
					} catch (TsapiUnableToSendException tue) {
						throw tue;
					} catch (TsapiResourceUnavailableException e1) {
						throw e1;
					} catch (Exception e1) {
						throw new TsapiResourceUnavailableException(2, 0, 0,
								"failure to monitor device");
					}
				}
				if (event != null) {
					CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
							.getEvent();
					monitorDeviceXRefID = monitorConf.getMonitorCrossRefID();
				}
			}

		}

		if ((followCall)
				|| ((callObserver) && (((getDeviceType() == 2) || (getDeviceType() == 1))))) {
			provider.addMonitor(monitorCallsViaDeviceXRefID, this);
		}

		if ((followCall)
				|| ((provider.isLucent()) && (((!provider.isLucent()) || (getDeviceType() == 1))))) {
			return;
		}

		doTerminalSnapshot();
		doAddressSnapshot();
		doCallSnapshot();
		provider.addMonitor(monitorDeviceXRefID, this);
	}

	void setNumberQueued(int _numQueued) {
		recreate();

		numQueued = _numQueued;
	}

	private void setState(TSDeviceState _newState) {
		log.info("TSDevice state transition: " + curState + " -> " + _newState
				+ ", device " + this);

		curState = _newState;
	}

	void setTSAgent(String acdDeviceName, int state, String agentID,
			String password, CSTAPrivate reqPriv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException {
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

		ConfHandler handler = new LucentSetAgentStateConfHandler(this);
		try {
			provider.tsapi.setAgentState(getName(), (short) agentMode, agentID,
					acdDeviceName, password, reqPriv, handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiInvalidArgumentException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setAgentState failure");
			}
			throw new TsapiPlatformException(4, 0, "setAgentState failure");
		}
	}

	void snapshotAgentsInACD() {
		recreate();

		if (!provider.isLucent()) {
			return;
		}
		try {
			LucentQueryAgentLogin lqal = new LucentQueryAgentLogin(getName());
			waitingForAgents = true;
			ConfHandler handler = new QueryAgentLoginConfHandler(this);
			Object lqalConf = provider.sendPrivateData(lqal.makeTsapiPrivate(),
					handler);

			if (lqalConf instanceof LucentQueryAgentLoginConfEvent) {
				synchronized (this) {
					if (waitingForAgents) {
						try {
							super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
						} catch (InterruptedException e) {
						}
						if (waitingForAgents) {
							waitingForAgents = false;
							throw new TsapiPlatformException(4, 0,
									"snapshot time-out");
						}
					}
				}
				for (int i = 0; i < tsACDVector.size(); ++i) {
					((TSAgent) tsACDVector.elementAt(i)).getState();
				}
			}
		} catch (Exception e) {
		}
	}

	void snapshotAgentsInTerminal(String acdName, String agentID) {
		recreate();

		if (provider.getCapabilities().getQueryAgentState() == 0) {
			return;
		}
		QueryAgentStateConfHandler2 handler = new QueryAgentStateConfHandler2(
				this, acdName, agentID);
		CSTAPrivate priv = null;
		if ((provider.isLucent()) && (acdName != null)) {
			LucentQueryAgentState lqas = new LucentQueryAgentState(acdName);
			priv = lqas.makeTsapiPrivate();
		} else if (provider.isLucent()) {
			TSAgentKey agentKey = new TSAgentKey(getName(), acdName, agentID);
			TSAgent agent = null;
			agent = getTSProviderImpl().findAgent(agentKey);
			if (agent == null) {
				synchronized (tsAgentTermVector) {
					if (tsAgentTermVector.size() > 0) {
						agent = (TSAgent) tsAgentTermVector.elementAt(0);
					}
				}
			}

			if ((agent != null) && (agent.getACDDeviceID() != null)) {
				LucentQueryAgentState lqas = new LucentQueryAgentState(agent
						.getACDDeviceID().getName());
				priv = lqas.makeTsapiPrivate();
			}
		}
		try {
			provider.tsapi.queryAgentState(getName(), priv, handler);
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (acdName == null) {
			agentID = getAgentID();
			TSAgentKey currentAgentKey = new TSAgentKey(getName(), acdName,
					agentID);
			if ((agentID != null) && (tsAgentTermVector.size() > 0)) {
				TSAgent previousAgent = (TSAgent) tsAgentTermVector.get(0);
				TSAgentKey previousTsAgentKey = previousAgent.getAgentKey();
				if (!previousTsAgentKey.equals(currentAgentKey)) {
					log.info("deleting agent " + previousAgent.getAgentID());
					previousAgent.updateState(2, -1, 0, null);
				}
			}
		}

		TSAgentKey agentKey = new TSAgentKey(getName(), acdName, agentID);
		TSAgent agent = null;

		if (handler.getAgentState() == -1) {
			return;
		}

		if (handler.getAgentState() == 1) {
			agent = getTSProviderImpl().findAgent(agentKey);
			if (agent == null) {
				synchronized (tsAgentTermVector) {
					if (tsAgentTermVector.size() > 0) {
						agent = (TSAgent) tsAgentTermVector.elementAt(0);
					}
				}
			}
			if (agent != null) {
				agent.updateState(2, handler.getWorkMode(), 0, null);
			}
			return;
		}

		agent = getTSProviderImpl().createAgent(agentKey, agentID, null);

		TSDevice agentDevice = agent.getTSAgentDevice();
		TSDeviceState tsdeviceState = agentDevice.curState;
		if (tsdeviceState.wasDeleteDone()) {
			updateAllAgentsInDeletedDeviceInstance(agentDevice.tsAgentTermVector);
		}
		if (agent.getInternalState() == 2) {
			provider.dumpAgent(agentKey);
			agent = provider.createAgent(agentKey, agentID, null);
		}
		int i = 0;
		if (handler.getState() == 7) {
			i = 7;
		} else {
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

		}

		agent.updateState(i, handler.getWorkMode(), 0, null);
	}

	private void stopMonitorForThisDevice() {
		log.info("stopMonitorForThisDevice: Device " + this
				+ " about to consider DevMon stop (xref=" + monitorDeviceXRefID
				+ " mcvdxref=" + monitorCallsViaDeviceXRefID + "), for "
				+ provider);

		if (monitorDeviceXRefID != 0) {
			provider.deleteMonitor(monitorDeviceXRefID);
		}

		if (monitorCallsViaDeviceXRefID != 0) {
			provider.deleteMonitor(monitorCallsViaDeviceXRefID);
		}

		if (monitorDeviceXRefID != 0) {
			if (provider.getCapabilities().getMonitorStop() != 0) {
				if (provider.getState() != 18) {
					try {
						provider.tsapi.monitorStop(monitorDeviceXRefID, null,
								null);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}

				} else {
					log.info("stopMonitorForThisDevice: " + this
							+ " Skipping Monitor Stop because Provider is "
							+ "in SHUTDOWN state, monitorDeviceXRefID="
							+ monitorDeviceXRefID + " for " + provider);
				}

			}

			monitorDeviceXRefID = 0;
		}

		if (monitorCallsViaDeviceXRefID == 0) {
			return;
		}
		if (provider.getCapabilities().getMonitorStop() != 0) {
			if (provider.getState() != 18) {
				try {
					provider.tsapi.monitorStop(monitorCallsViaDeviceXRefID,
							null, null);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else {
				log.info("stopMonitorForThisDevice: " + this
						+ " Skipping Monitor Stop because Provider is "
						+ "in SHUTDOWN state, "
						+ "monitorCallsViaDeviceXRefID="
						+ monitorCallsViaDeviceXRefID + " for " + provider);
			}

		}

		clearMonitorCallsViaDeviceAttributes();

		provider.removeAllCallsForDomain(this);
	}

	synchronized void testDelete() {
		if ((!terminalMonitorThreads.isEmpty())
				|| (!addressMonitorThreads.isEmpty())
				|| (!callsViaAddressMonitorThreads.isEmpty())
				|| (!callsAtAddressMonitorThreads.isEmpty())
				|| (!callsAtTerminalMonitorThreads.isEmpty())
				|| (tsRouteCallback != null) || (!tsACDVector.isEmpty())
				|| (!internalDeviceMonitors.isEmpty())
				|| (connections.size() > 0) || (terminalConnections.size() > 0)) {
			return;
		}

		stopMonitorForThisDevice();

		if (refCount > 0) {
			return;
		}

		for (int i = 0; i < tsAgentTermVector.size(); ++i) {
			TSAgent tsAgent = (TSAgent) tsAgentTermVector.elementAt(i);
			if ((tsAgent != null) && (tsAgent.isReferenced())) {
				return;
			}

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
			Vector<TSAgent> agentsForAgentTerm) {
		for (int i = 0; i < agentsForAgentTerm.size(); ++i) {
			TSAgent agent = (TSAgent) agentsForAgentTerm.elementAt(i);
			log
					.info("agent represented by agentKey "
							+ agent.getAgentKey()
							+ " is holding reference to agentDevice which is in deleted state");
			agent.agentDevice = this;
			log
					.info("updated the agent device reference to the new agentdevice "
							+ getName());
			addToAgentTermVector(agent);
			log.debug("agent has been added in the list of agents for device "
					+ getName());
		}
	}

	void updateDNDState(boolean _dndState, Vector<TSEvent> eventList) {
		recreate();

		synchronized (this) {
			if (dndState == _dndState) {
				if (!dndInitialized) {
					dndInitialized = true;
				}
				return;
			}

			dndState = _dndState;
		}

		if ((eventList != null) && (dndInitialized)) {
			eventList.addElement(new TSEvent(37, this));
			eventList.addElement(new TSEvent(58, this));
		}

		if (!dndInitialized) {
			dndInitialized = true;
		}
	}

	void updateForwarding(CSTAForwardingInfo[] fwdInfo,
			Vector<TSEvent> eventList) {
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
						if ((oldFwd.getType() != newFwd.getType())
								|| (oldFwd.getFilter() != newFwd.getFilter())) {
							continue;
						}
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

						if ((newFwd.getDestinationAddress().equals(oldFwd
								.getDestinationAddress()))
								|| (newFwd.getDestinationAddress()
										.equals(oldFwd.getDestinationAddress()
												+ "#"))) {
							continue;
						}

						update = true;

						fwdVector.removeElement(oldFwd);
						fwdVector.addElement(newFwd);
						break;
					}

					if ((!typeMatch) && (fwdInfo[i].isForwardingOn() == true)) {
						update = true;
						fwdVector.addElement(newFwd);
					}
				}
			}
		}

		if ((eventList != null) && (update == true) && (forwardingInitialized)) {
			eventList.addElement(new TSEvent(39, this));
		}

		if (!forwardingInitialized) {
			forwardingInitialized = true;
		}
	}

	void updateMessageWaitingBits(int _msgWaitingBits, Vector<TSEvent> eventList) {
		recreate();

		synchronized (this) {
			if (msgWaitingBits == _msgWaitingBits) {
				if (!mwiInitialized) {
					mwiInitialized = true;
				}
				return;
			}

			msgWaitingBits = _msgWaitingBits;
		}

		if ((eventList != null) && (mwiInitialized)) {
			eventList.addElement(new TSEvent(38, this));
		}

		if (!mwiInitialized) {
			mwiInitialized = true;
		}
	}

	boolean updateObject() {
		recreate();

		if ((isMonitorSet() == true) && (cleanUCIDsInCallsInConnections())) {
			return true;
		}

		return doCallSnapshot();
	}

	synchronized void waitForAsyncInitialization() {
		recreate();

		if (asyncInitializationComplete) {
			return;
		}
		try {
			log.info(this + " waiting for initialization to complete");
			super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
		} catch (InterruptedException e) {
		}
		if (asyncInitializationComplete) {
			return;
		}
		throw new TsapiPlatformException(4, 0,
				"could not finish address/terminal construction");
	}

	boolean wereChangesHeldPending() {
		recreate();

		return changesWereHeldPending;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSDevice JD-Core Version: 0.5.4
 */