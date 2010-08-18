package com.avaya.jtapi.tsapi.impl.core;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.CallClassifierInfo;
import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.TSProvider;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiProviderUnavailableException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.csta1.CSTAGetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceListConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryCallMonitorConfEvent;
import com.avaya.jtapi.tsapi.csta1.HasUCID;
import com.avaya.jtapi.tsapi.csta1.LucentCallClassifierInfo;
import com.avaya.jtapi.tsapi.csta1.LucentConferencedEvent;
import com.avaya.jtapi.tsapi.csta1.LucentGetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentPrivateData;
import com.avaya.jtapi.tsapi.csta1.LucentQueryCallClassifier;
import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryTg;
import com.avaya.jtapi.tsapi.csta1.LucentQueryTod;
import com.avaya.jtapi.tsapi.csta1.LucentQueryTodConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSetAdviceOfCharge;
import com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent;
import com.avaya.jtapi.tsapi.csta1.LucentTrunkGroupInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV5GetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV7GetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.impl.TsapiAddressCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiCallCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiConnCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiProviderCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiTermConnCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiTerminalCapabilities;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiInServiceEvent;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiInitializingEvent;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiOutOfServiceEvent;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiShutdownEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.ITsapiHeartbeatTimeoutListener;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiFactory;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSession;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
import com.avaya.jtapi.tsapi.util.JtapiUtils;

public final class TSProviderImpl extends TSProvider implements IDomainTracker,
		IDomainContainer, ITsapiHeartbeatTimeoutListener {
	class TsapiProReaderTask extends TimerTask {
		TsapiProReaderTask() {
		}

		public void run() {
			Tsapi.updateVolatileConfigurationValues();
			if (!Tsapi.isRefreshPeriodChanged()) {
				return;
			}
			timerThread.cancel();
			int interval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
			timerThread = new Timer("TsapiProReader");
			timerThread.schedule(new TsapiProReaderTask(), interval, interval);
		}
	}

	private static Logger log = Logger.getLogger(TSProviderImpl.class);
	Tsapi tsapi;
	private final Object obsSync;
	private final Vector<TsapiProviderMonitor> monitors;
	private final Vector<TsapiProviderMonitor> providerMonitorThreads;
	private final Vector<TsapiAddressMonitor> addressMonitorThreads;
	private final Vector<TsapiTerminalMonitor> terminalMonitorThreads;
	private final Vector<TsapiCallMonitor> callMonitorThreads;
	private final Vector<TsapiRouteMonitor> routeMonitorThreads;
	private final ConnectStringData connectStringData;
	int state;
	Object replyPriv = null;
	TSEventHandler tsEHandler;
	TSAuditThread auditor;
	Timer timerThread;
	private final Hashtable<String, TSDevice> devHash;
	private final Hashtable<String, TSTrunk> trkHash;
	private final Hashtable<TSAgentKey, TSAgent> agentHash;

	boolean lucent = false;

	private int lucentPDV = -1;
	final int LUCENT_PDV_UNINITIALIZED = -1;
	private final Hashtable<Integer, TSCall> callHash;
	private final Hashtable<Integer, TSCall> nonCallHash;
	private final Hashtable<CSTAConnectionID, TSConnection> connHash;
	final Hashtable<Integer, Object> xrefHash;
	final Hashtable<Integer, Object> routeRegHash;
	private final Hashtable<Integer, Object> privXrefHash;
	TSCapabilities tsCaps;
	Vector<String> tsMonitorableDevices;
	Vector<String> tsRouteDevices;
	boolean callMonitoring;
	int[] nonCallIDArray;

	int nonCallID = 0;

	static int NOT_IN_USE = 0;

	static int IN_USE = 1;

	static int CSTA_HOME_WORK_TOP = 1;

	static int CSTA_AWAY_WORK_TOP = 2;

	static int CSTA_DEVICE_DEVICE_MONITOR = 3;

	static int CSTA_ROUTING = 4;

	static int GET_DEVICE_INITIAL_INDEX = -1;

	static int GET_DEVICE_NO_MORE_INDEX = -1;

	static int TSAPI_RESPONSE_TIME = 30000;
	static int DEFAULT_TIMEOUT = 180000;
	TSInitializationThread initThread;

	boolean securityOn = true;

	private String administeredSwitchSoftwareVersion = "";

	private String switchSoftwareVersion = "";

	private String offerType = "";

	private String serverType = "";

	private boolean monitorCallsViaDevice = false;

	private boolean enableTsapiHeartbeat = false;

	private static int g_instanceNumber = 0;

	private static Object g_lock = new Object();
	private int m_instanceNumber = 0;

	static int provider_count = 0;

	private static final Object provider_count_lock = new Object();

	private final Object shutdown_single_thread_lock = new Object();

	static int CREATEAGENT_ACCEPT_DELETED = 1;

	static int CREATEAGENT_REFUSE_DELETED = 2;

	IDomainTracker m_providerTracker = new TSDomainTracker(this);

	public TSProviderImpl(String _url, Vector<TsapiVendor> vendors) {
		setInstanceNumber();

		log.info("TSProvider: version '" + getProviderVersionDetails()
				+ "', for " + this);

		state = 0;

		devHash = new Hashtable<String, TSDevice>(10);
		trkHash = new Hashtable<String, TSTrunk>(10);
		agentHash = new Hashtable<TSAgentKey, TSAgent>(10);

		connHash = new Hashtable<CSTAConnectionID, TSConnection>(20);

		TtConnHash("ctor", "NO OBJECT", "NO CONNID");
		callHash = new Hashtable<Integer, TSCall>(10);
		nonCallHash = new Hashtable<Integer, TSCall>(10);
		xrefHash = new Hashtable<Integer, Object>(3);

		TtXrefHash("ctor", 0, "NO OBJECT");
		routeRegHash = new Hashtable<Integer, Object>(3);
		privXrefHash = new Hashtable<Integer, Object>(3);
		tsMonitorableDevices = new Vector<String>();
		tsRouteDevices = new Vector<String>();
		monitors = new Vector<TsapiProviderMonitor>();
		providerMonitorThreads = new Vector<TsapiProviderMonitor>();
		addressMonitorThreads = new Vector<TsapiAddressMonitor>();
		terminalMonitorThreads = new Vector<TsapiTerminalMonitor>();
		callMonitorThreads = new Vector<TsapiCallMonitor>();
		routeMonitorThreads = new Vector<TsapiRouteMonitor>();

		obsSync = new Object();
		nonCallIDArray = new int[100];
		callMonitoring = false;

		connectStringData = parseURL(_url);
		if (connectStringData.telephonyServers != null) {
			for (InetSocketAddress telephonyServer : connectStringData.telephonyServers) {
				Tsapi.addServer(telephonyServer);
			}
		}
		tsEHandler = new TSEventHandler(this);

		log.info("TSProvider: calling acsOpenStream serverID="
				+ connectStringData.serverId + " loginID="
				+ connectStringData.loginId + " passwd=******* for " + this);

		tsapi = TsapiFactory.getTsapi(connectStringData.serverId,
				connectStringData.loginId, connectStringData.password, vendors,
				tsEHandler);

		lucent = LucentPrivateData.isAvayaVendor(getVendor());

		auditor = new TSAuditThread(this);
		auditor.start();

		timerThread = new Timer("TsapiProReader", true);

		int timeInterval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
		timerThread.schedule(new TsapiProReaderTask(), timeInterval,
				timeInterval);

		if (enableTsapiHeartbeat) {
			tsapi.enableHeartbeat();
			tsapi.setHeartbeatTimeoutListener(this);
			enableTsapiHeartbeat = false;
		}

		setCapabilities(getCaps());
		setCallMonitor(getCallMonitor());
		if (tsCaps.sysStatStart != 0) {
			SysStatHandler handler = new SysStatHandler();
			try {
				tsapi.startSystemStatusMonitoring(null, handler);
			} catch (Exception e) {
				if (e instanceof ITsapiException) {
					throw new TsapiPlatformException(((ITsapiException) e)
							.getErrorType(), ((ITsapiException) e)
							.getErrorCode(),
							"startSystemStatusMonitoring() failure: "
									+ e.getMessage());
				}

				throw new TsapiPlatformException(4, 0,
						"startSystemStatusMonitoring() failure: "
								+ e.getMessage());
			}

		}

		JtapiEventThreadManager.initialize();

		initThread = new TSInitializationThread(this);
		initThread.start();

		initNewProvider();
	}

	public void addAddressMonitorThread(TsapiAddressMonitor obs) {
		if (addressMonitorThreads.contains(obs)) {
			return;
		}

		addressMonitorThreads.addElement(obs);
	}

	void addAgentToHash(TSAgent agent) {
		synchronized (agentHash) {
			TSAgentKey agentKey = agent.getAgentKey();
			if (agentKey != null) {
				Object oldObj = agentHash.put(agentKey, agent);
				if (oldObj != null) {
					log.info("NOTICE: agentHash.put() replaced " + oldObj
							+ " for " + this);
				}
			}
		}
	}

	void addAgentToSaveHash(TSAgent agent) {
		auditor.putAgent(agent);
	}

	public void addCallMonitorThread(TsapiCallMonitor obs) {
		if (callMonitorThreads.contains(obs)) {
			return;
		}

		callMonitorThreads.addElement(obs);
	}

	public IDomainDevice addCallToDomain(IDomainDevice d, IDomainCall c) {
		return m_providerTracker.addCallToDomain(d, c);
	}

	TSCall addCallToHash(TSCall call) {
		synchronized (callHash) {
			Object oldObj = callHash.put(new Integer(call.getCallID()), call);
			if (oldObj != null) {
				log.info("NOTICE: callHash.put() replaced " + oldObj + " for "
						+ this);
			}
			return (TSCall) oldObj;
		}
	}

	void addCallToSaveHash(TSCall call) {
		auditor.putCall(call);
	}

	TSConnection addConnectionToHash(TSConnection connection) {
		synchronized (connHash) {
			Object oldObj = null;
			CSTAConnectionID connID = connection.getConnID();
			if (connID != null) {
				oldObj = connHash.put(connID, connection);

				TtConnHash("addConn", connection, connID);

				log.info("NOTICE: connHash.put() replaced " + oldObj + " with "
						+ connection + " for " + this);
			}

			return (TSConnection) oldObj;
		}
	}

	void addConnectionToSaveHash(TSConnection connection) {
		auditor.putConn(connection);
	}

	private void addDeviceNameToPrintingBuffer(StringBuffer aBuffer,
			String aName) {
		if (aBuffer.length() > 0) {
			aBuffer.append(", ");
		}
		aBuffer.append(aName);
	}

	void addDeviceToHash(String deviceID, TSDevice device) {
		synchronized (devHash) {
			if (deviceID != null) {
				Object oldObj = devHash.put(deviceID, device);
				if (oldObj != null) {
					log.info("NOTICE: devHash.put() replaced " + oldObj
							+ " for " + this);
				}
			}
		}
	}

	void addDeviceToHash(TSDevice device) {
		addDeviceToHash(device.getName(), device);
	}

	void addMonitor(int monitorCrossRefID, Object observed) {
		synchronized (xrefHash) {
			Object oldObj = xrefHash.put(new Integer(monitorCrossRefID),
					observed);

			TtXrefHash("addMon", monitorCrossRefID, observed);
			if (oldObj != null) {
				log.info("NOTICE: xrefHash.put() replaced " + oldObj + " for "
						+ this);
			}
		}
	}

	public void addMonitor(TsapiProviderMonitor monitor)
			throws TsapiResourceUnavailableException {
		synchronized (obsSync) {
			if (monitors.contains(monitor)) {
				return;
			}

			monitors.addElement(monitor);

			monitor.addReference();
		}

		sendSnapshot(monitor);
	}

	void addNonCallToHash(TSCall call) {
		synchronized (nonCallHash) {
			Object oldObj = nonCallHash.put(new Integer(call.getNonCallID()),
					call);

			if (oldObj != null) {
				log.info("NOTICE: nonCallHash.put() replaced " + oldObj
						+ " for " + this);
			}
		}
	}

	void addPrivateXref(int xrefID, TSDevice tsDevice) {
		synchronized (privXrefHash) {
			Object oldObj = privXrefHash.put(new Integer(xrefID), tsDevice);
			if (oldObj != null) {
				log.info("NOTICE: privXrefHash.put() replaced " + oldObj
						+ " for " + this);
			}
		}
	}

	public void addProviderMonitorThread(TsapiProviderMonitor obs) {
		if (providerMonitorThreads.contains(obs)) {
			return;
		}

		providerMonitorThreads.addElement(obs);
	}

	void addRoute(int routeRegisterID, TSDevice tsDevice) {
		synchronized (routeRegHash) {
			Object oldObj = routeRegHash.put(new Integer(routeRegisterID),
					tsDevice);

			if (oldObj != null) {
				log.info("NOTICE: routeRegHash.put() replaced " + oldObj
						+ " for " + this);
			}
		}
	}

	public void addRouteMonitorThread(TsapiRouteMonitor obs) {
		if (routeMonitorThreads.contains(obs)) {
			return;
		}

		routeMonitorThreads.addElement(obs);
	}

	public void addTerminalMonitorThread(TsapiTerminalMonitor obs) {
		if (terminalMonitorThreads.contains(obs)) {
			return;
		}

		terminalMonitorThreads.addElement(obs);
	}

	void addTrunkToHash(String name, TSTrunk trunk) {
		synchronized (trkHash) {
			if (name != null) {
				Object oldObj = trkHash.put(name, trunk);
				if (oldObj != null) {
					log.info("NOTICE: trkHash.put() replaced " + oldObj
							+ " for " + this);
				}
			}
		}
	}

	boolean allowCallMonitoring() {
		return callMonitoring;
	}

	void callCleanup() {
		Enumeration<TSCall> callEnum = callHash.elements();

		while (callEnum.hasMoreElements()) {
			TSCall call;
			try {
				call = (TSCall) callEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			if (call == null) {
				log
						.error("callCleanup: handled AuditThread null call reference race condition for "
								+ this);
			}

			if (call.hasReceivedCallClearedTransfer()) {
				if (System.currentTimeMillis()
						- call.getCallClearedTransferReceiptTime() < 3000L) {
					continue;
				}
				Vector<TSEvent> eventList = new Vector<TSEvent>();

				call.setState(34, eventList);

				int jtapiCause = 212;

				tsEHandler.doCallMonitors(call, eventList, jtapiCause, null);
			}

			if (call.checkForMonitors()) {
				continue;
			}
			boolean is_confirmed_that_call_is_gone = false;

			boolean lucent_tactics_get_an_answer = false;

			if (isLucentV5()) {
				try {
					String old_ucid = call.getUCID();
					String new_ucid = call.queryUCID();

					if ((old_ucid != null) && (new_ucid != null)
							&& (old_ucid.compareTo(new_ucid) != 0)) {
						is_confirmed_that_call_is_gone = true;
					} else {
						is_confirmed_that_call_is_gone = false;
					}

					lucent_tactics_get_an_answer = true;
				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (TsapiPlatformException e) {
					if ((e.getErrorType() == 2)
							&& (((e.getErrorCode() == 24) || (e.getErrorCode() == 11)))) {
						is_confirmed_that_call_is_gone = true;

						lucent_tactics_get_an_answer = true;
					} else if ((e.getErrorType() == 2)
							&& (e.getErrorCode() == 15)) {
						log
								.info("Error: UCID not enabled on switch - interferes with JTAPI Call Auditing");
					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			}

			if (((lucent_tactics_get_an_answer) && (is_confirmed_that_call_is_gone))
					|| ((!lucent_tactics_get_an_answer) && (!call
							.updateObject()))) {
				if (call.getTSState() == 34) {
					if (callHash.get(new Integer(call.getCallID())) == null) {
						log.info("Benign race condition: call (callid "
								+ call.getCallID()
								+ ") went INVALID while being audited");
					} else {
						log
								.info("ERROR: removing call (callid "
										+ call.getCallID()
										+ ") from Provider's records - Audit indicates call had ended");

						call.delete();
					}

				}

				call.setState(34, null);
			}
		}
	}

	public void changeCallIDInDomain(int old_callid, int new_callid) {
		m_providerTracker.changeCallIDInDomain(old_callid, new_callid);
	}

	TSAgent createAgent(TSAgentKey agentKey, String agentID, String password) {
		return createAgent(agentKey, agentID, password,
				CREATEAGENT_ACCEPT_DELETED);
	}

	TSAgent createAgent(TSAgentKey agentKey, String agentID, String password,
			int deletedAgentSearchPolicy) {
		TSAgent agent = null;

		boolean newObject = false;
		boolean auditObject = false;

		synchronized (agentHash) {
			agent = (TSAgent) agentHash.get(agentKey);
			if (agent == null) {
				if (deletedAgentSearchPolicy == CREATEAGENT_ACCEPT_DELETED) {
					agent = auditor.getAgent(agentKey);
				}

				if (agent == null) {
					newObject = true;
					agent = new TSAgent(this, agentKey, password);
					addAgentToHash(agent);
				} else {
					auditObject = true;
				}
			}
		}

		if (newObject) {
			agent.finishConstruction();
		} else {
			agent.waitForConstruction();

			if ((agent.getACDDeviceID() == null)
					&& (agentKey.acdDeviceID != null) && (!auditObject)) {
				agent.addToSkillsVector(agentKey.acdDeviceID);
			}
		}
		return agent;
	}

	TSCall createCall(int callID) {
		synchronized (callHash) {
			TSCall call = null;

			if (callID != 0) {
				call = findCall(callID);
				if (call != null) {
					return call;
				}

				call = new TSCall(this, callID);
			} else {
				call = new TSCall(this, callID);
			}

			return call;
		}
	}

	TSCall createCall(int callID, Object privateData) {
		TSCall call = createCall(callID);
		return validateCall(privateData, call, callID);
	}

	TSConnection createConnection(CSTAConnectionID connID, TSDevice device,
			Vector<TSEvent> eventList) {
		TSConnection conn = null;

		boolean newObject = false;

		synchronized (connHash) {
			if (connID != null) {
				conn = (TSConnection) connHash.get(connID);
			}
			if (conn == null) {
				if (connID != null) {
					conn = auditor.getConn(connID);
				}
				if (conn == null) {
					newObject = true;
					conn = new TSConnection(this, connID, device, false);
					if (connID != null) {
						addConnectionToHash(conn);
					}
				}
			}
		}
		if (newObject) {
			conn.finishConstruction(null, eventList);
		} else {
			conn.waitForConstruction();
		}

		return conn.getTSConn();
	}

	public TSDevice createDevice(CSTAExtendedDeviceID deviceID) {
		if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0)
				|| (deviceID.getDeviceID() == null)) {
			return null;
		}

		TSDevice device = null;

		synchronized (devHash) {
			device = (TSDevice) devHash.get(deviceID.getDeviceID());

			if (device == null) {
				device = new TSDevice(this, deviceID);
				addDeviceToHash(device);
			}
		}

		return device;
	}

	public TSDevice createDevice(CSTAExtendedDeviceID deviceID,
			boolean checkValidity) throws TsapiInvalidArgumentException {
		if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0)
				|| (deviceID.getDeviceID() == null)) {
			return null;
		}
		if (checkValidity) {
			if (deviceID.getDeviceID().equals("AllRouteAddress")) {
				return createDevice(deviceID);
			}

			if ((state == 2) && (securityOn)
					&& (!tsMonitorableDevices.contains(deviceID.getDeviceID()))) {
				throw new TsapiInvalidArgumentException(0, 0,
						"not in provider's domain");
			}

		}

		return createDevice(deviceID);
	}

	TSDevice createDevice(CSTAExtendedDeviceID deviceID, CSTAConnectionID connID) {
		if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0)
				|| (deviceID.getDeviceID() == null)) {
			return null;
		}

		TSDevice device = null;

		device = (TSDevice) devHash.get(deviceID.getDeviceID());

		if (device == null) {
			if (connID != null) {
				TSConnection conn = (TSConnection) connHash.get(connID);
				if (conn == null) {
					conn = auditor.getConn(connID);
				}
				if (conn != null) {
					device = conn.getTSDevice();

					synchronized (device) {
						device.addName(deviceID);
						synchronized (devHash) {
							TSDevice tmpDev = (TSDevice) devHash.get(deviceID
									.getDeviceID());

							if (tmpDev == null) {
								addDeviceToHash(device);
							} else {
								device = tmpDev;
							}
						}
					}
				}
			}

			boolean notFound = false;
			synchronized (devHash) {
				if ((device == null)
						&& ((device = (TSDevice) devHash.get(deviceID
								.getDeviceID())) == null)) {
					notFound = true;
				}

			}

			if (notFound) {
				device = new TSDevice(this, deviceID);
				synchronized (device) {
					addDeviceToHash(device);
				}
			}

		}

		return device;
	}

	TSDevice createDevice(String deviceID) {
		return createDevice(deviceID, null);
	}

	public TSDevice createDevice(String name, boolean checkValidity)
			throws TsapiInvalidArgumentException {
		if (name == null) {
			return null;
		}

		return createDevice(
				new CSTAExtendedDeviceID(name, (short) 0, (short) 0),
				checkValidity);
	}

	TSDevice createDevice(String deviceID, CSTAConnectionID connID) {
		if (deviceID == null) {
			return null;
		}
		return createDevice(new CSTAExtendedDeviceID(deviceID, (short) 0,
				(short) 0), connID);
	}

	TSConnection createTerminalConnection(CSTAConnectionID connID,
			TSDevice termConnDevice, Vector<TSEvent> eventList,
			TSDevice connDevice) {
		TSConnection conn = null;

		boolean newObject = false;
		boolean auditObject = false;

		synchronized (connHash) {
			conn = (TSConnection) connHash.get(connID);

			if (conn == null) {
				conn = auditor.getConn(connID);

				if (conn == null) {
					newObject = true;
					conn = new TSConnection(this, connID, termConnDevice, true);
					addConnectionToHash(conn);
				} else {
					auditObject = true;
				}
			}
		}

		if (newObject) {
			conn.finishConstruction(connDevice, eventList);
		} else {
			conn.waitForConstruction();
		}

		if ((termConnDevice.isTerminal()) && (!conn.isTerminalConnection())) {
			if (isLucent()) {
				if (!auditObject) {
					deleteConnectionFromHash(connID);
					conn = createTerminalConnection(connID, termConnDevice,
							eventList, connDevice);
				}
			} else {
				conn.setTerminalConnection();
			}
		}

		return conn;
	}

	TSTrunk createTrunk(String trkName, int type) {
		if (trkName == null) {
			return null;
		}

		synchronized (trkHash) {
			TSTrunk trunk = null;

			trunk = (TSTrunk) trkHash.get(trkName);

			if (trunk != null) {
				return trunk;
			}

			trunk = new TSTrunk(this, trkName, type);

			return trunk;
		}
	}

	public TSCall createTSCall(int callID) {
		TSCall call = createCall(callID);
		call.updateObject();
		return call;
	}

	public TSConnection createTSConnection(CSTAConnectionID connID,
			TSDevice device) {
		return createConnection(connID, device, null);
	}

	public TSTrunk createTSTrunk(String trkName) {
		TSTrunk trunk = createTrunk(trkName, 1);
		return trunk;
	}

	void deleteAgentFromHash(TSAgentKey agentKey) {
		if (agentKey != null) {
			agentHash.remove(agentKey);
		}
	}

	void deleteCallFromHash(int callID) {
		callHash.remove(new Integer(callID));
	}

	void deleteConnectionFromHash(CSTAConnectionID connID) {
		if (connID != null) {
			connHash.remove(connID);

			TtConnHash("delConn", "NO OBJECT", connID);
		}
	}

	void deleteDeviceFromHash(String _deviceID) {
		synchronized (devHash) {
			Object removedObj = devHash.remove(_deviceID);
			log.info("NOTICE: devHash.remove() removed " + removedObj
					+ " by device name " + _deviceID);
		}
	}

	void deleteDeviceFromHash(TSDevice device) {
		synchronized (devHash) {
			Vector<CSTAExtendedDeviceID> keys = device.getKeys();
			for (int i = 0; i < keys.size(); ++i) {
				String key = ((CSTAExtendedDeviceID) keys.elementAt(i))
						.getDeviceID();
				Object removedObj = devHash.remove(key);
				log.info("NOTICE: devHash.remove() removed " + removedObj
						+ " for device name(" + i + ") " + key);
			}
		}
	}

	void deleteInstanceOfDeviceFromHash(TSDevice _soughtObj) {
		int keys_not_in_hash = 0;
		int keys_pointing_elsewhere = 0;
		Hashtable<String, Object> keys_pointing_at = new Hashtable<String, Object>();

		StringBuffer alias_names = new StringBuffer();

		StringBuffer elsewhere_names = new StringBuffer();

		StringBuffer not_in_hash_names = new StringBuffer();

		synchronized (devHash) {
			Vector<CSTAExtendedDeviceID> keys = _soughtObj.getKeys();
			for (int i = 0; i < keys.size(); ++i) {
				String key = ((CSTAExtendedDeviceID) keys.elementAt(i))
						.getDeviceID();
				Object foundObj = devHash.get(key);
				boolean foundAny = foundObj != null;
				boolean foundThatOne = foundObj == _soughtObj;

				if (foundThatOne) {
					devHash.remove(key);
				}

				if (foundThatOne) {
					addDeviceNameToPrintingBuffer(alias_names, key);
				} else if (foundAny) {
					++keys_pointing_elsewhere;
					keys_pointing_at.put(key, foundObj);
					addDeviceNameToPrintingBuffer(elsewhere_names, key);
				} else {
					++keys_not_in_hash;
					addDeviceNameToPrintingBuffer(not_in_hash_names, key);
				}

			}

		}

		if (keys_pointing_elsewhere + keys_not_in_hash == 0) {
			log.info("NOTICE: devHash.remove() expected, found and removed "
					+ _soughtObj + " by device name(s) [" + alias_names + "]");
		} else {
			if (keys_pointing_elsewhere > 0) {
				Iterator<String> key_iter = keys_pointing_at.keySet()
						.iterator();

				while (key_iter.hasNext()) {
					String d = (String) key_iter.next();
					TSDevice t = (TSDevice) keys_pointing_at.get(d);
					log
							.info("NOTICE: devHash.remove() expected "
									+ _soughtObj
									+ " but found "
									+ t
									+ " by device name "
									+ d
									+ " - race condition - left latter TSDevice in hash");
				}

			}

			if (keys_not_in_hash > 0) {
				log.info("NOTICE: attempted to devHash.remove() " + _soughtObj
						+ " by device name(s) [" + not_in_hash_names
						+ "] but no TSDevice there by those name(s)");
			}
		}
	}

	void deleteMonitor(int monitorCrossRefID) {
		xrefHash.remove(new Integer(monitorCrossRefID));

		TtXrefHash("delMon", monitorCrossRefID, "GONE");
	}

	void deleteNonCallFromHash(int nonCallId) {
		nonCallHash.remove(new Integer(nonCallId));
	}

	void deletePrivateXref(int xrefID) {
		synchronized (privXrefHash) {
			privXrefHash.remove(new Integer(xrefID));
		}
	}

	void deleteRoute(int routeRegisterID) {
		routeRegHash.remove(new Integer(routeRegisterID));
	}

	void deleteTrunkFromHash(String name) {
		synchronized (trkHash) {
			while (trkHash.remove(name) != null) {
				;
			}
		}
	}

	public void disableHeartbeat() {
		if (tsapi != null) {
			tsapi.disableHeartbeat();
		}
	}

	Vector<TSCall> doCallSnapshot(String device) {
		if (tsCaps.getSnapshotDeviceReq() == 0) {
			return null;
		}

		ProviderSnapshotDeviceConfHandler handler = new ProviderSnapshotDeviceConfHandler(
				this);
		try {
			tsapi.snapshotDevice(device, null, handler);
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}

		return handler.cv;
	}

	void dump(String indent) {
		log.trace(indent + "***** PROVIDER DUMP *****");
		log.trace(indent + "TSProvider: " + this);

		log.trace(indent + "TSProvider: " + connectStringData.serverId
				+ ";login=" + connectStringData.loginId + ";passwd=*******");

		log.trace(indent + "TSProvider state: " + state);
		log.trace(indent + "TSProvider version details: "
				+ getProviderVersionDetails());

		log.trace(indent + "TSProvider calls: ");
		Enumeration<TSCall> callEnum = callHash.elements();

		while (callEnum.hasMoreElements()) {
			TSCall call;
			try {
				call = (TSCall) callEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			call.dump(indent + " ");
		}
		log.trace(indent + "TSProvider non calls: ");
		Enumeration<TSCall> nonCallEnum = nonCallHash.elements();

		while (nonCallEnum.hasMoreElements()) {
			TSCall nonCall;
			try {
				nonCall = (TSCall) nonCallEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			nonCall.dump(indent + " ");
		}

		log.trace(indent + "TSProvider VDN Calls-to-VDN Domain Mapping: ");

		dumpDomainData(indent);

		log.trace(indent + "TSProvider devices: ");
		Enumeration<TSDevice> deviceEnum = devHash.elements();

		while (deviceEnum.hasMoreElements()) {
			TSDevice device;
			try {
				device = (TSDevice) deviceEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			device.dump(indent + " ");
		}
		log.trace(indent + "TSProvider conns: ");
		Enumeration<TSConnection> connEnum = connHash.elements();

		while (connEnum.hasMoreElements()) {
			TSConnection conn;
			try {
				conn = (TSConnection) connEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			conn.dump(indent + " ");
		}
		log.trace(indent + "TSProvider agents: ");
		Enumeration<TSAgent> agentEnum = agentHash.elements();

		while (agentEnum.hasMoreElements()) {
			TSAgent agent;
			try {
				agent = (TSAgent) agentEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			agent.dump(indent + " ");
		}
		log.trace(indent + "TSProvider trunks: ");
		Enumeration<TSTrunk> trkEnum = trkHash.elements();

		while (trkEnum.hasMoreElements()) {
			TSTrunk trk;
			try {
				trk = (TSTrunk) trkEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			trk.dump(indent + " ");
		}
		log.trace(indent + "TSProvider xrefs: ");
		Enumeration<Object> xrefEnum = xrefHash.elements();
		while (xrefEnum.hasMoreElements()) {
			try {
				log.trace(indent + "xref object: " + xrefEnum.nextElement());
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
			}
		}

		log.trace(indent + "TSProvider audits: ");
		auditor.dump(indent + " ");
		log.trace(indent + "***** PROVIDER DUMP END *****");
	}

	void dumpAgent(TSAgentKey agentKey) {
		auditor.dumpAgent(agentKey);
	}

	void dumpCall(int callID) {
		auditor.dumpCall(callID);
	}

	void dumpConn(CSTAConnectionID connID) {
		auditor.dumpConn(connID);
	}

	public void dumpDomainData(String indent) {
		m_providerTracker.dumpDomainData(indent);
	}

	public void enableHeartbeat() {
		if (tsapi != null) {
			tsapi.enableHeartbeat();
		} else {
			enableTsapiHeartbeat = true;
		}
	}

	public void finalizeOldProvider() {
		synchronized (provider_count_lock) {
			if (provider_count > 0) {
				provider_count -= 1;
				if (provider_count == 0) {
					JtapiEventThreadManager.drainThreads();
				}
			}
		}
	}

	TSDevice findACDDevice(int xrefID) {
		return (TSDevice) privXrefHash.get(new Integer(xrefID));
	}

	TSAgent findAgent(TSAgentKey agentKey) {
		return (TSAgent) agentHash.get(agentKey);
	}

	TSCall findCall(int callID) {
		synchronized (callHash) {
			TSCall call = null;

			if (callID != 0) {
				call = (TSCall) callHash.get(new Integer(callID));
				if (call != null) {
					return call;
				}

				call = auditor.getCall(callID);
				if (call != null) {
					return call;
				}

			}

			return null;
		}
	}

	TSDevice findDevice(String name) {
		synchronized (devHash) {
			return (TSDevice) devHash.get(name);
		}
	}

	public Vector<TsapiAddressMonitor> getAddressMonitorThreads() {
		return addressMonitorThreads;
	}

	public String getAdministeredSwitchSoftwareVersion() {
		return administeredSwitchSoftwareVersion;
	}

	public CallClassifierInfo getCallClassifierInfo()
			throws TsapiMethodNotSupportedException {
		if (!isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		try {
			LucentQueryCallClassifier qcc = new LucentQueryCallClassifier();
			Object result = sendPrivateData(qcc.makeTsapiPrivate());

			if (result instanceof LucentCallClassifierInfo) {
				return new CallClassifierInfo(
						((LucentCallClassifierInfo) result).numAvailPorts,
						((LucentCallClassifierInfo) result).numInUsePorts);
			}

			return null;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						" service failure");
			}

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	boolean getCallMonitor() {
		CSTAEvent event;
		try {
			event = tsapi.CSTAQueryCallMonitor();
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		CSTAQueryCallMonitorConfEvent qcmConf = (CSTAQueryCallMonitorConfEvent) event
				.getEvent();

		return qcmConf.isCallMonitor();
	}

	public Vector<TsapiCallMonitor> getCallMonitorThreads() {
		return callMonitorThreads;
	}

	TSCapabilities getCapabilities() {
		return tsCaps;
	}

	TSCapabilities getCaps() {
		TSCapabilities tsCaps = new TSCapabilities();

		if (isLucent()) {
			tsCaps.setLucent(getLucentPDV());
		}

		try {
			CSTAEvent event = tsapi.getApiCaps();
			if (event.getEvent() == null) {
				log
						.info("Init Capabilities: Conf event null, enable all Capabilities for "
								+ this);

				tsCaps.setAll();
				return tsCaps;
			}
			if (event.getEvent() instanceof CSTAGetAPICapsConfEvent) {
				CSTAGetAPICapsConfEvent getAPICapsConf = (CSTAGetAPICapsConfEvent) event
						.getEvent();

				if (isLucentV5()) {
					tsCaps.setAddParty(1);
				}
				tsCaps.setAlternateCall(getAPICapsConf.getAlternateCall());
				tsCaps.setAnswerCall(getAPICapsConf.getAnswerCall());
				tsCaps.setCallCompletion(getAPICapsConf.getCallCompletion());

				tsCaps.setClearCall(getAPICapsConf.getClearCall());
				tsCaps.setClearConnection(getAPICapsConf.getClearConnection());

				tsCaps.setConferenceCall(getAPICapsConf.getConferenceCall());

				tsCaps
						.setConsultationCall(getAPICapsConf
								.getConsultationCall());

				tsCaps.setDeflectCall(getAPICapsConf.getDeflectCall());
				tsCaps.setPickupCall(getAPICapsConf.getPickupCall());
				tsCaps.setGroupPickupCall(getAPICapsConf.getGroupPickupCall());

				tsCaps.setHoldCall(getAPICapsConf.getHoldCall());
				tsCaps.setMakeCall(getAPICapsConf.getMakeCall());
				tsCaps.setMakePredictiveCall(getAPICapsConf
						.getMakePredictiveCall());

				tsCaps.setQueryMwi(getAPICapsConf.getQueryMwi());
				tsCaps.setQueryDnd(getAPICapsConf.getQueryDnd());
				tsCaps.setQueryFwd(getAPICapsConf.getQueryFwd());
				tsCaps.setQueryAgentState(getAPICapsConf.getQueryAgentState());

				tsCaps.setQueryLastNumber(getAPICapsConf.getQueryLastNumber());

				tsCaps.setQueryDeviceInfo(getAPICapsConf.getQueryDeviceInfo());

				tsCaps.setReconnectCall(getAPICapsConf.getReconnectCall());
				tsCaps.setRetrieveCall(getAPICapsConf.getRetrieveCall());
				tsCaps.setSetMwi(getAPICapsConf.getSetMwi());
				tsCaps.setSetDnd(getAPICapsConf.getSetDnd());
				tsCaps.setSetFwd(getAPICapsConf.getSetFwd());
				tsCaps.setSetAgentState(getAPICapsConf.getSetAgentState());
				tsCaps.setTransferCall(getAPICapsConf.getTransferCall());
				tsCaps.setEventReport(getAPICapsConf.getEventReport());
				tsCaps
						.setCallClearedEvent(getAPICapsConf
								.getCallClearedEvent());

				tsCaps
						.setConferencedEvent(getAPICapsConf
								.getConferencedEvent());

				tsCaps.setConnectionClearedEvent(getAPICapsConf
						.getConnectionClearedEvent());

				tsCaps.setDeliveredEvent(getAPICapsConf.getDeliveredEvent());

				tsCaps.setDivertedEvent(getAPICapsConf.getDivertedEvent());
				tsCaps
						.setEstablishedEvent(getAPICapsConf
								.getEstablishedEvent());

				tsCaps.setFailedEvent(getAPICapsConf.getFailedEvent());
				tsCaps.setHeldEvent(getAPICapsConf.getHeldEvent());
				tsCaps.setNetworkReachedEvent(getAPICapsConf
						.getNetworkReachedEvent());

				tsCaps.setOriginatedEvent(getAPICapsConf.getOriginatedEvent());

				tsCaps.setQueuedEvent(getAPICapsConf.getQueuedEvent());
				tsCaps.setRetrievedEvent(getAPICapsConf.getRetrievedEvent());

				tsCaps.setServiceInitiatedEvent(getAPICapsConf
						.getServiceInitiatedEvent());

				tsCaps
						.setTransferredEvent(getAPICapsConf
								.getTransferredEvent());

				tsCaps.setCallInformationEvent(getAPICapsConf
						.getCallInformationEvent());

				tsCaps.setDoNotDisturbEvent(getAPICapsConf
						.getDoNotDisturbEvent());

				tsCaps.setForwardingEvent(getAPICapsConf.getForwardingEvent());

				tsCaps.setMessageWaitingEvent(getAPICapsConf
						.getMessageWaitingEvent());

				tsCaps.setLoggedOnEvent(getAPICapsConf.getLoggedOnEvent());
				tsCaps.setLoggedOffEvent(getAPICapsConf.getLoggedOffEvent());

				tsCaps.setNotReadyEvent(getAPICapsConf.getNotReadyEvent());
				tsCaps.setReadyEvent(getAPICapsConf.getReadyEvent());
				tsCaps.setWorkNotReadyEvent(getAPICapsConf
						.getWorkNotReadyEvent());

				tsCaps.setWorkReadyEvent(getAPICapsConf.getWorkReadyEvent());

				tsCaps.setBackInServiceEvent(getAPICapsConf
						.getBackInServiceEvent());

				tsCaps.setOutOfServiceEvent(getAPICapsConf
						.getOutOfServiceEvent());

				tsCaps.setPrivateEvent(getAPICapsConf.getPrivateEvent());
				tsCaps.setRouteRequestEvent(getAPICapsConf
						.getRouteRequestEvent());

				tsCaps.setReRoute(getAPICapsConf.getReRoute());
				tsCaps.setRouteSelect(getAPICapsConf.getRouteSelect());
				tsCaps.setRouteUsedEvent(getAPICapsConf.getRouteUsedEvent());

				tsCaps.setRouteEndEvent(getAPICapsConf.getRouteEndEvent());
				tsCaps.setMonitorDevice(getAPICapsConf.getMonitorDevice());
				tsCaps.setMonitorCall(getAPICapsConf.getMonitorCall());
				tsCaps.setMonitorCallsViaDevice(getAPICapsConf
						.getMonitorCallsViaDevice());

				tsCaps.setChangeMonitorFilter(getAPICapsConf
						.getChangeMonitorFilter());

				tsCaps.setMonitorStop(getAPICapsConf.getMonitorStop());
				tsCaps.setMonitorEnded(getAPICapsConf.getMonitorEnded());
				tsCaps.setSnapshotDeviceReq(getAPICapsConf
						.getSnapshotDeviceReq());

				tsCaps.setSnapshotCallReq(getAPICapsConf.getSnapshotCallReq());

				tsCaps.setEscapeService(getAPICapsConf.getEscapeService());
				tsCaps.setPrivateStatusEvent(getAPICapsConf
						.getPrivateStatusEvent());

				tsCaps.setEscapeServiceEvent(getAPICapsConf
						.getEscapeServiceEvent());

				tsCaps.setEscapeServiceConf(getAPICapsConf
						.getEscapeServiceConf());

				tsCaps
						.setSendPrivateEvent(getAPICapsConf
								.getSendPrivateEvent());

				tsCaps.setSysStatReq(getAPICapsConf.getSysStatReq());
				tsCaps.setSysStatStart(getAPICapsConf.getSysStatStart());
				tsCaps.setSysStatStop(getAPICapsConf.getSysStatStop());
				tsCaps.setChangeSysStatFilter(getAPICapsConf
						.getChangeSysStatFilter());

				tsCaps.setSysStatReqEvent(getAPICapsConf.getSysStatReqEvent());

				tsCaps.setSysStatReqConf(getAPICapsConf.getSysStatReqConf());

				tsCaps.setSysStatEvent(getAPICapsConf.getSysStatEvent());

				Object replyPriv = event.getPrivData();
				if ((replyPriv instanceof LucentGetAPICapsConfEvent)
						&& (replyPriv instanceof LucentV5GetAPICapsConfEvent)
						&& (replyPriv instanceof LucentV7GetAPICapsConfEvent)) {
					LucentV7GetAPICapsConfEvent cf = (LucentV7GetAPICapsConfEvent) replyPriv;

					setAdministeredSwitchSoftwareVersion(cf
							.getAdministeredSwitchSoftwareVersion());

					setSwitchSoftwareVersion(cf.getSwitchSoftwareVersion());

					setOfferType(cf.getOfferType());
					setServerType(cf.getServerType());
					setMonitorCallsViaDevice(cf.getMonitorCallsViaDevice());
				}

			} else {
				log
						.info("Init Capabilities: expected conf event with pduType 125,received conf event with pduType "
								+ event.getEvent().getPDU()
								+ ", enable all Capabilities" + " for " + this);

				tsCaps.setAll();
				return tsCaps;
			}
		} catch (Exception e) {
			log
					.error("Init Capabilities: Exception, enable all Capabilities - Exception: "
							+ e + " for " + this);

			tsCaps.setAll();
		}

		return tsCaps;
	}

	public TSConnection getConnection(CSTAConnectionID connID) {
		TSConnection conn = null;

		synchronized (connHash) {
			if (connID != null) {
				conn = (TSConnection) connHash.get(connID);
			}
			if ((conn == null) && (connID != null)) {
				conn = auditor.getConn(connID);
			}
		}

		if (conn == null) {
			return null;
		}

		conn.waitForConstruction();

		return conn.getTSConn();
	}

	public int getCurrentStateOfCallFromTelephonyServer(int callId) {
		TSCall currentCall = null;

		if (callId < 1) {
			throw new TsapiPlatformException(3, 0,
					"Please pass a Call ID value that is greater than 0.");
		}

		currentCall = createTSCall(callId);

		return getCurrentStateOfCallFromTelephonyServer(currentCall);
	}

	public int getCurrentStateOfCallFromTelephonyServer(TSCall call) {
		if (call == null) {
			throw new TsapiPlatformException(3, 0,
					"Call object passed in is null.");
		}

		log
				.info("Forcing a query on telephony server to check state of call - "
						+ call);
		return call.getStateFromServer();
	}

	short getDeviceExt(String deviceID) {
		if (tsCaps.getQueryDeviceInfo() == 0) {
			return 0;
		}
		CSTAEvent event;
		try {
			event = tsapi.queryDeviceInfo(deviceID, null);
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return 0;
		}

		Object replyPriv = event.getPrivData();
		if (replyPriv instanceof LucentQueryDeviceInfoConfEvent) {
			if (((LucentQueryDeviceInfoConfEvent) replyPriv)
					.getExtensionClass() == 0) {
				return 1;
			}
			if (((LucentQueryDeviceInfoConfEvent) replyPriv)
					.getExtensionClass() == 1) {
				return 2;
			}

			if (!(replyPriv instanceof LucentV5QueryDeviceInfoConfEvent)) {
				;
			}
		}

		return 0;
	}

	public IDomainCall getDomainCall(int callid) {
		return findCall(callid);
	}

	public IDomainDevice getDomainCallIsIn(IDomainCall c) {
		return m_providerTracker.getDomainCallIsIn(c);
	}

	public IDomainDevice getDomainDevice(String name) {
		return findDevice(name);
	}

	public int getInstanceNumber() {
		return m_instanceNumber;
	}

	public int getLucentPDV() {
		if (lucent) {
			if (lucentPDV == -1) {
				byte[] version = tsapi.getVendorVersion();

				if ((version.length == 0) || (version[0] != 0)
						|| (version[(version.length - 1)] != 0)) {
					log
							.info("Version bytes with no data, or missing discriminator byte or trailing NULL byte, found while decoding TSAPI private version string");

					lucentPDV = 0;
				} else {
					try {
						lucentPDV = Integer.parseInt(new String(version, 1,
								version.length - 2, "US-ASCII"));
					} catch (Exception e) {
						log
								.info("Exception occurred decoding TSAPI private version string: "
										+ e);

						lucentPDV = 0;
					}
				}
			}
			return lucentPDV;
		}
		return 0;
	}

	List<String> getMonitorableDevices() {
		short[] level = { 1, 2, 3 };

		List<String> listOfMonitorableDevices = new ArrayList<String>();
		for (int i = 0; i < level.length; ++i) {
			int index = GET_DEVICE_INITIAL_INDEX;
			do {
				CSTAEvent event;
				try {
					event = tsapi.getDeviceList(index, level[i]);
				} catch (Exception e) {
					// break label164:
					break;
				}
				if (event != null) {
					CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent) event
							.getEvent();

					if ((getDeviceListConf.getDriverSdbLevel() == 1)
							|| (getDeviceListConf.getDriverSdbLevel() == -1)) {
						setSecurity(false);
						return listOfMonitorableDevices;
					}
					for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
						String device = getDeviceListConf.getDevList()[j];

						if (!listOfMonitorableDevices.contains(device)) {
							listOfMonitorableDevices.add(device);
						}
					}
					// label164:
					index = getDeviceListConf.getIndex();
				}
			} while (index != GET_DEVICE_NO_MORE_INDEX);
		}

		return listOfMonitorableDevices;
	}

	public boolean getMonitorCallsViaDevice() {
		return monitorCallsViaDevice;
	}

	Object getMonitoredObject(int xrefID) {
		return xrefHash.get(new Integer(xrefID));
	}

	@SuppressWarnings("unchecked")
	public String getMonitoredObjects() {
		StringBuffer buffer = new StringBuffer();
		for (Map.Entry entry : xrefHash.entrySet()) {
			buffer.append(entry.getKey() + ":" + entry.getValue() + "\n");
		}
		return buffer.toString();
	}

	public Vector<TsapiProviderMonitor> getMonitors() {
		return new Vector<TsapiProviderMonitor>(monitors);
	}

	public String getName() {
		return connectStringData.url;
	}

	synchronized int getNonCallID() {
		int[] start = { nonCallID, 0 };
		for (int j = 0; j < 1; ++j) {
			for (int i = start[j]; i < 100; ++i) {
				if (nonCallIDArray[i] == NOT_IN_USE) {
					nonCallID = i;
					nonCallIDArray[i] = IN_USE;
					return nonCallID;
				}
			}
		}
		return -1;
	}

	public String getOfferType() {
		return offerType;
	}

	public Object getPrivateData() {
		if (replyPriv instanceof CSTAPrivate) {
			return replyPriv;
		}
		return null;
	}

	public Vector<TsapiProviderMonitor> getProviderMonitorThreads() {
		return providerMonitorThreads;
	}

	String getProviderVersionDetails() {
		// String std_string = "production build";

		String stdver = "5.2.0.483";

		String customver = "production build";

		return stdver + " [" + customver + "]";
	}

	public Vector<TsapiRouteMonitor> getRouteMonitorThreads() {
		return routeMonitorThreads;
	}

	public String getServerID() {
		return tsapi.getServerID();
	}

	public String getServerType() {
		return serverType;
	}

	public int getState() {
		switch (state) {
		case 0:
		case 1:
		default:
			return 17;
		case 2:
			int jtapiState = 16;

			if (tsCaps.sysStatReq != 0) {
				Vector<TSEvent> eventList = new Vector<TSEvent>();
				SysStatHandler handler = new SysStatHandler();
				try {
					tsapi.requestSystemStatus(null, handler);
				} catch (Exception e) {
					log
							.warn("Failed to get system status. Returning OUT_OF_SERVICE to be safe");
					setState(0, eventList, true);
					jtapiState = 17;
				}
				if ((handler.getSystemStatus() != 1)
						&& (handler.getSystemStatus() != 2)) {
					setState(0, eventList, true);
					jtapiState = 17;
				}
				if (eventList.size() > 0) {
					Vector<TsapiProviderMonitor> observers = getMonitors();
					for (int j = 0; j < observers.size(); ++j) {
						TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
								.elementAt(j);

						callback.deliverEvents(eventList, false);
					}
				}
			}
			return jtapiState;
		case 3:
		}
		return 18;
	}

	public Date getSwitchDateAndTime() throws TsapiMethodNotSupportedException {
		if (!isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		try {
			LucentQueryTod qtod = new LucentQueryTod();
			Object result = sendPrivateData(qtod.makeTsapiPrivate(), null, true);

			if (result instanceof LucentQueryTodConfEvent) {
				LucentQueryTodConfEvent tod = (LucentQueryTodConfEvent) result;
				if (tod.getYear() < 97) {
					tod.setYear(tod.getYear() + 100);
				}
				Calendar cal = Calendar.getInstance();
				cal.set(tod.getYear(), tod.getMonth() - 1, tod.getDay(), tod
						.getHour(), tod.getMinute(), tod.getSecond());

				return cal.getTime();
			}
			return null;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						" service failure");
			}

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	public String getSwitchSoftwareVersion() {
		return switchSoftwareVersion;
	}

	public Vector<TsapiTerminalMonitor> getTerminalMonitorThreads() {
		return terminalMonitorThreads;
	}

	public LucentTrunkGroupInfo getTrunkGroupInfo(String trunkAccessCode)
			throws TsapiMethodNotSupportedException {
		if (!isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		try {
			LucentQueryTg qtg = new LucentQueryTg(trunkAccessCode);
			Object result = sendPrivateData(qtg.makeTsapiPrivate());

			if (result instanceof LucentTrunkGroupInfo) {
				return (LucentTrunkGroupInfo) result;
			}
			return null;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						" service failure");
			}

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	public Vector<TSDevice> getTSACDDevices()
			throws TsapiMethodNotSupportedException {
		if (!isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		if (!securityOn) {
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD addresses can be accessed.");
		}

		Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			if (getDeviceExt((String) tsMonitorableDevices.elementAt(i)) == 2) {
				TSDevice device = createDevice((String) tsMonitorableDevices
						.elementAt(i));

				if (device != null) {
					tsDeviceVector.addElement(device);
				}
			}
		}
		return tsDeviceVector;
	}

	public Vector<TSDevice> getTSACDManagerDevices()
			throws TsapiMethodNotSupportedException {
		if (!isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		if (!securityOn) {
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD Manager addresses can be accessed.");
		}

		Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			if (getDeviceExt((String) tsMonitorableDevices.elementAt(i)) == 1) {
				TSDevice device = createDevice((String) tsMonitorableDevices
						.elementAt(i));

				if (device != null) {
					tsDeviceVector.addElement(device);
				}
			}
		}
		return tsDeviceVector;
	}

	public Vector<TSDevice> getTSAddressDevices() {
		if (!securityOn) {
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Addesses can be accessed.");
		}

		Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			TSDevice device = createDevice((String) tsMonitorableDevices
					.elementAt(i));
			if (device != null) {
				tsDeviceVector.addElement(device);
			}
		}
		TSDevice device = createDevice("AllRouteAddress");
		if (device != null) {
			tsDeviceVector.addElement(device);
		}
		return tsDeviceVector;
	}

	public TsapiAddressCapabilities getTsapiAddressCapabilities() {
		return new TsapiAddressCapabilities(tsCaps);
	}

	public TsapiCallCapabilities getTsapiCallCapabilities() {
		return new TsapiCallCapabilities(tsCaps);
	}

	public TsapiConnCapabilities getTsapiConnCapabilities() {
		return new TsapiConnCapabilities(tsCaps);
	}

	public TsapiProviderCapabilities getTsapiProviderCapabilities() {
		return new TsapiProviderCapabilities(tsCaps);
	}

	public int getTsapiState() {
		return state;
	}

	public TsapiTermConnCapabilities getTsapiTermConnCapabilities() {
		return new TsapiTermConnCapabilities(tsCaps);
	}

	public TsapiTerminalCapabilities getTsapiTerminalCapabilities() {
		return new TsapiTerminalCapabilities(tsCaps);
	}

	@SuppressWarnings("unchecked")
	public Vector<TSCall> getTSCalls() {
		Vector tsCallVector = new Vector();
		Vector tsDevCallVector = null;

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			tsDevCallVector = doCallSnapshot((String) tsMonitorableDevices
					.elementAt(i));

			if (tsDevCallVector != null) {
				for (int j = 0; j < tsDevCallVector.size(); ++j) {
					if (!tsCallVector.contains(tsDevCallVector.elementAt(j))) {
						tsCallVector.addElement(tsDevCallVector.elementAt(j));
					}
				}
			}
		}
		Enumeration<TSCall> callEnum;
		synchronized (nonCallHash) {
			callEnum = nonCallHash.elements();
			while (callEnum.hasMoreElements()) {
				try {
					tsCallVector.addElement(callEnum.nextElement());
				} catch (NoSuchElementException e) {
					log.error(e.getMessage(), e);
				}

			}

		}

		synchronized (callHash) {
			callEnum = callHash.elements();
			while (callEnum.hasMoreElements()) {
				TSCall callVar;
				try {
					callVar = (TSCall) callEnum.nextElement();
				} catch (NoSuchElementException e) {
					log.error(e.getMessage(), e);
					continue;
				}

				if (!tsCallVector.contains(callVar)) {
					;
				}
				tsCallVector.addElement(callVar);
			}

		}

		return tsCallVector;
	}

	public TSEventHandler getTsEHandler() {
		return tsEHandler;
	}

	public Vector<TSDevice> getTSRouteDevices() {
		if (!securityOn) {
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Route addresses can be accessed.");
		}

		Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsRouteDevices.size(); ++i) {
			TSDevice device = createDevice((String) tsRouteDevices.elementAt(i));
			if (device != null) {
				tsDeviceVector.addElement(device);
			}
		}
		TSDevice device = createDevice("AllRouteAddress");
		if (device != null) {
			tsDeviceVector.addElement(device);
		}
		return tsDeviceVector;
	}

	public Vector<TSDevice> getTSTerminalDevices() {
		if (!securityOn) {
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Terminals can be accessed.");
		}

		Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			String devName = (String) tsMonitorableDevices.elementAt(i);
			TSDevice device = createDevice(devName);
			if ((device != null) && (device.isTerminal())) {
				tsDeviceVector.addElement(device);
			}
		}
		return tsDeviceVector;
	}

	public String getVendor() {
		return tsapi.getVendor();
	}

	public byte[] getVendorVersion() {
		return tsapi.getVendorVersion();
	}

	public boolean heartbeatIsEnabled() {
		if (tsapi != null) {
			return tsapi.heartbeatIsEnabled();
		}

		return false;
	}

	public void heartbeatTimeout() {
		log.info("*** Heartbeat timer expired.  Shutting down Provider. ***");

		shutdown();
	}

	public void initNewProvider() {
		synchronized (provider_count_lock) {
			provider_count += 1;
		}
	}

	public boolean isCallInAnyDomain(IDomainCall c) {
		return m_providerTracker.isCallInAnyDomain(c);
	}

	boolean isConnInActiveHash(CSTAConnectionID connID) {
		return connHash.get(connID) != null;
	}

	boolean isConnInAnyHash(CSTAConnectionID connID) {
		return (isConnInActiveHash(connID)) || (isConnInSaveHash(connID));
	}

	boolean isConnInDisconnectedHash(CSTAConnectionID connID) {
		return auditor.getConn(connID) != null;
	}

	boolean isConnInSaveHash(CSTAConnectionID connID) {
		return auditor.getConn(connID) != null;
	}

	boolean isDeviceMonitorable(String name) {
		if ((state == 2) && (securityOn)) {
			if (name == null) {
				return false;
			}
			return tsMonitorableDevices.contains(name);
		}

		return true;
	}

	public boolean isLucent() {
		return lucent;
	}

	public boolean isLucentV5() {
		return getLucentPDV() >= 5;
	}

	public boolean isLucentV6() {
		return getLucentPDV() >= 6;
	}

	public boolean isLucentV7() {
		return getLucentPDV() >= 7;
	}

	public boolean isLucentV8() {
		return getLucentPDV() >= 8;
	}

	public void logln(String s) {
		if (log.isInfoEnabled()) {
			log.info(s);
		}
	}

	private ConnectStringData parseURL(String _url) {
		String serverID = _url;
		String loginID = "";
		String passwd = "";
		Collection<InetSocketAddress> telephonyServers = new LinkedHashSet<InetSocketAddress>();
		int firstSemiColon_index = _url.indexOf(';');
		serverID = _url.substring(0, firstSemiColon_index);
		if (firstSemiColon_index >= 0) {
			StringTokenizer params = new StringTokenizer(_url
					.substring(firstSemiColon_index + 1), ";");

			while (params.hasMoreTokens()) {
				StringTokenizer param = new StringTokenizer(params.nextToken(),
						"=");

				if (!param.hasMoreTokens()) {
					continue;
				}
				String key = param.nextToken();
				if (!param.hasMoreTokens()) {
					continue;
				}
				String value = param.nextToken();

				if ((key.equals("login")) || (key.equals("loginID"))) {
					loginID = value;
				} else if (key.equals("passwd")) {
					passwd = value;
				} else if (key.equals("servers")) {
					telephonyServers = JtapiUtils.parseTelephonyServerEntry(
							value, 450);
				}
			}
		}

		if (loginID.length() > 48) {
			throw new TsapiPlatformException(4, 0,
					"Username provided is more than 48 characters in length. Login ID="
							+ loginID);
		}

		if (passwd.length() > 47) {
			throw new TsapiPlatformException(4, 0,
					"Password provided is more than 47 characters in length. Password length="
							+ passwd.length());
		}

		return new ConnectStringData(serverID, loginID, passwd,
				telephonyServers, _url);
	}

	synchronized void releaseNonCallID(int nonCallId) {
		nonCallIDArray[nonCallId] = NOT_IN_USE;
	}

	public void removeAddressMonitorThread(TsapiAddressMonitor obs) {
		addressMonitorThreads.removeElement(obs);
	}

	public void removeAllCallsForDomain(IDomainDevice d) {
		m_providerTracker.removeAllCallsForDomain(d);
	}

	public void removeCallFromDomain(IDomainCall c) {
		m_providerTracker.removeCallFromDomain(c);
	}

	public void removeCallMonitorThread(TsapiCallMonitor obs) {
		callMonitorThreads.removeElement(obs);
	}

	public void removeMonitor(TsapiProviderMonitor monitor) {
		removeMonitor(monitor, 100, null);
	}

	void removeMonitor(TsapiProviderMonitor monitor, int cause,
			Object privateData) {
		if (monitors.removeElement(monitor)) {
			monitor.deleteReference(cause, privateData);
		}
	}

	void removeMonitors(int cause, Object privateData) {
		Vector<TsapiProviderMonitor> obs = new Vector<TsapiProviderMonitor>(
				monitors);
		for (int i = 0; i < obs.size(); ++i) {
			removeMonitor((TsapiProviderMonitor) obs.elementAt(i), cause,
					privateData);
		}
	}

	public void removeProviderMonitorThread(TsapiProviderMonitor obs) {
		providerMonitorThreads.removeElement(obs);
	}

	public void removeRouteMonitorThread(TsapiRouteMonitor obs) {
		routeMonitorThreads.removeElement(obs);
	}

	public void removeTerminalMonitorThread(TsapiTerminalMonitor obs) {
		terminalMonitorThreads.removeElement(obs);
	}

	public String requestPrivileges() throws TsapiInvalidArgumentException {
		RequestPrivilegesConfHandler handler = new RequestPrivilegesConfHandler(
				this);

		boolean request_failed = true;
		try {
			tsapi.requestPrivileges(null, handler);
			request_failed = false;
			String str = handler.get_nonce();

			return str;
		} catch (TsapiPlatformException e) {
			switch (e.getErrorType()) {
			case 120:
			case 126:
			case 127:
			case 128:
			case 121:
			case 122:
			case 123:
			case 124:
			case 125:
			}

			throw new TsapiPlatformException(e.getErrorType(),
					e.getErrorCode(),
					"Unexpected requestPrivileges TSAPI failure: " + e);
		} finally {
			if (request_failed) {
				shutdown();
			}
		}
	}

	public Object sendPrivateData(CSTAPrivate data)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		return sendPrivateData(data, null, false);
	}

	Object sendPrivateData(CSTAPrivate data, ConfHandler extraHandler)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		return sendPrivateData(data, extraHandler, false);
	}

	Object sendPrivateData(CSTAPrivate data, ConfHandler extraHandler,
			boolean priority) throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		if (data.tsType == 89) {
			ConfHandler handler;
			if (priority) {
				handler = new PriorityEscapeConfHandler(this, extraHandler);
			} else {
				handler = new EscapeConfHandler(this, extraHandler);
			}
			tsapi.CSTAEscapeService(data, handler);
			return ((EscapeConfHandler) handler).getPrivateData();
		}
		if (data.tsType == 95) {
			tsapi.CSTASendPrivateEvent(data);
			return null;
		}
		throw new TsapiPlatformException(3, 0, "unknown  data type ["
				+ data.tsType + "]");
	}

	void sendSnapshot(TsapiProviderMonitor callback) {
		if (callback == null) {
			return;
		}

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		switch (state) {
		case 2:
			eventList.addElement(new TSEvent(1, this));

			eventList.addElement(new TSEvent(9999, this,
					new TsapiProviderTsapiInServiceEvent()));

			break;
		case 1:
			eventList.addElement(new TSEvent(2, this));

			eventList.addElement(new TSEvent(9999, this,
					new TsapiProviderTsapiInitializingEvent()));

			break;
		case 0:
			eventList.addElement(new TSEvent(2, this));

			eventList.addElement(new TSEvent(9999, this,
					new TsapiProviderTsapiOutOfServiceEvent()));

			break;
		case 3:
			eventList.addElement(new TSEvent(3, this));

			eventList.addElement(new TSEvent(9999, this,
					new TsapiProviderTsapiShutdownEvent()));
		}

		if (eventList.size() <= 0) {
			return;
		}
		callback.deliverEvents(eventList, true);
	}

	void setAdministeredSwitchSoftwareVersion(
			String administeredSwitchSoftwareVersion) {
		this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
	}

	public void setAdviceOfCharge(boolean flag)
			throws TsapiMethodNotSupportedException {
		if (!isLucentV5()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		try {
			LucentSetAdviceOfCharge aoc = new LucentSetAdviceOfCharge(flag);
			sendPrivateData(aoc.makeTsapiPrivate());
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						" service failure");
			}

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	void setCallMonitor(boolean _callMonitoring) {
		callMonitoring = _callMonitoring;
	}

	void setCapabilities(TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
	}

	void setClientHeartbeatInterval(short heartbeatInterval) {
		tsapi.setClientHeartbeatInterval(heartbeatInterval);
	}

	public void setDebugPrinting(boolean enable) {
		boolean traceLoggingEnabled = JTAPILoggingAdapter
				.isTraceLoggingEnabled();
		boolean errorLoggingEnabled = Logger.getLogger("com.avaya.jtapi.tsapi")
				.isEnabledFor(Level.ERROR);
		boolean isLog4jLoggingEnabled = JtapiUtils.isLog4jConfigured();

		if ((!traceLoggingEnabled) && (isLog4jLoggingEnabled)) {
			traceLoggingEnabled = true;
		}

		if (enable) {
			if (traceLoggingEnabled) {
				Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.TRACE);
			} else {
				JTAPILoggingAdapter.setTraceLoggerLevel("7");
				JTAPILoggingAdapter.initializeLogging();
			}

		} else {
			if (!traceLoggingEnabled) {
				return;
			}
			if (errorLoggingEnabled) {
				Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.ERROR);
			} else {
				Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.OFF);
			}
		}
	}

	public void setHeartbeatInterval(short heartbeatInterval)
			throws TsapiInvalidArgumentException {
		ConfHandler handler = new SetHeartbeatIntervalConfHandler(this);
		try {
			tsapi.setHeartbeatInterval(heartbeatInterval, null, handler);
		} catch (TsapiInvalidArgumentException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setHeartbeatInterval() failure: " + e.getMessage());
			}

			throw new TsapiPlatformException(4, 0,
					"setHeartbeatInterval() failure: " + e.getMessage());
		}
	}

	private void setInstanceNumber() {
		synchronized (g_lock) {
			m_instanceNumber = (++g_instanceNumber);
		}
	}

	void setMonitorCallsViaDevice(boolean monitorCallsViaDevice) {
		this.monitorCallsViaDevice = monitorCallsViaDevice;
	}

	void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public void setPrivateData(Object o) {
		if (o instanceof CSTAPrivate) {
			replyPriv = o;
		}
	}

	public void setPrivileges(String xmlData)
			throws TsapiInvalidArgumentException {
		ConfHandler handler = new SetPrivilegesConfHandler(this);
		try {
			tsapi.setPrivileges(xmlData, null, handler);
			return;
		} catch (TsapiInvalidArgumentException e) {
			shutdown();
			throw e;
		} catch (TsapiPlatformException e) {
			shutdown();
			throw new TsapiPlatformException(e.getErrorType(),
					e.getErrorCode(), "setPrivileges TSAPI failure: " + e);
		} catch (Exception e) {
			shutdown();
			throw new TsapiPlatformException(4, 0,
					"Unexpected setPrivileges TSAPI failure: " + e);
		}
	}

	void setRouteDevices() {
		int index = GET_DEVICE_INITIAL_INDEX;
		do {
			CSTAEvent event;
			try {
				event = tsapi.getDeviceList(index, (short) 6);
			} catch (Exception e) {
				return;
			}

			CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent) event
					.getEvent();

			for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
				String device = getDeviceListConf.getDevList()[j];

				if (!tsRouteDevices.contains(device)) {
					tsRouteDevices.addElement(device);
				}
			}
			index = getDeviceListConf.getIndex();
		} while (index != GET_DEVICE_NO_MORE_INDEX);
	}

	void setSecurity(boolean _securityOn) {
		securityOn = _securityOn;
	}

	void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public void setSessionTimeout(int timeout) {
		TsapiSession.setTimeout(timeout);
	}

	void setState(int tsapi_shutdown, Vector<TSEvent> eventList) {
		setState(tsapi_shutdown, eventList, false);
	}

	private void setState(int _state, Vector<TSEvent> eventList,
			boolean ignoreOldState) {
		int oldCoreState = 16;
		if (!ignoreOldState) {
			oldCoreState = getState();
		}
		synchronized (this) {
			if (state == _state) {
				return;
			}

			state = _state;
		}

		switch (state) {
		case 2:
			synchronized (eventList) {
				if (eventList != null) {
					if ((ignoreOldState) || (oldCoreState != 16)) {
						eventList.addElement(new TSEvent(1, this));
					}

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiInServiceEvent()));
				}
			}

			break;
		case 1:
			synchronized (eventList) {
				if (eventList != null) {
					if ((ignoreOldState) || (oldCoreState != 17)) {
						eventList.addElement(new TSEvent(2, this));
					}

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiInitializingEvent()));
				}
			}

			break;
		case 0:
			synchronized (eventList) {
				if (eventList != null) {
					if ((ignoreOldState) || (oldCoreState != 17)) {
						eventList.addElement(new TSEvent(2, this));
					}

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiOutOfServiceEvent()));
				}
			}

			break;
		case 3:
			synchronized (eventList) {
				if (eventList != null) {
					if ((ignoreOldState) || (oldCoreState != 18)) {
						eventList.addElement(new TSEvent(3, this));
					}

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiShutdownEvent()));
				}

			}

			Enumeration<Object> xrefEnum = xrefHash.elements();
			Object monitored = null;

			while (xrefEnum.hasMoreElements()) {
				try {
					monitored = xrefEnum.nextElement();
				} catch (NoSuchElementException e) {
					log.error(e.getMessage(), e);
					continue;
				}

				if (monitored == null) {
					continue;
				}

				if (monitored instanceof TSDevice) {
					((TSDevice) monitored).removeObservers(100, null, 0);
				}
				if (monitored instanceof TSCall) {
					;
				}
				((TSCall) monitored).removeObservers(100, null, 0);
			}

			if (tsapi != null) {
				tsapi.shutdown();
			}

			devHash.clear();
			trkHash.clear();
			agentHash.clear();
			connHash.clear();

			TtConnHash("dtor", "NO OBJECT", "NO CONNID");
			callHash.clear();
			xrefHash.clear();

			TtXrefHash("dtor", 0, "NO OBJECT");
			routeRegHash.clear();
			privXrefHash.clear();
			providerMonitorThreads.removeAllElements();
			addressMonitorThreads.removeAllElements();
			terminalMonitorThreads.removeAllElements();
			callMonitorThreads.removeAllElements();
			routeMonitorThreads.removeAllElements();

			if (auditor != null) {
				auditor.stopRunning();
			}

			disableHeartbeat();
		}
	}

	void setSwitchSoftwareVersion(String switchSoftwareVersion) {
		this.switchSoftwareVersion = switchSoftwareVersion;
	}

	public void shutdown() {
		shutdown(null);
	}

	public void shutdown(Object privateData) {
		log.info("TSProvider.shutdown - attempting shutdown");
		if (timerThread != null) {
			timerThread.cancel();
		}
		timerThread = null;

		synchronized (shutdown_single_thread_lock) {
			if (state == 3) {
				log
						.info("TSProvider.shutdown - already in shutdown, redundant call, returning.");
				return;
			}

			log.info("TSProvider.shutdown - Starting");
			if (tsCaps.sysStatStop != 0) {
				SysStatHandler handler = new SysStatHandler();
				try {
					tsapi.stopSystemStatusMonitoring(null, handler);
				} catch (Exception e) {
					log.error("stopSystemStatusMonitoring() failure: "
							+ e.getMessage());
				}
			}

			Vector<TSEvent> eventList = new Vector<TSEvent>();
			synchronized (eventList) {
				setState(3, eventList);

				if (privateData != null) {
					for (int i = 0; i < eventList.size(); ++i) {
						TSEvent ev = (TSEvent) eventList.elementAt(i);
						if (ev.getPrivateData() == null) {
							ev.setPrivateData(privateData);
						}
					}
					if (!isLucent()) {
						eventList.addElement(new TSEvent(9999, this,
								privateData));
					}

				}

				if (eventList.size() > 0) {
					Vector<TsapiProviderMonitor> observers = getMonitors();
					for (int j = 0; j < observers.size(); ++j) {
						TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
								.elementAt(j);

						callback.deliverEvents(eventList, false);
					}
				}
			}
			removeMonitors(100, null);

			finalizeOldProvider();

			log.info("TSProvider.shutdown - Done");
		}
	}

	public String toString() {
		return "TSProvider[#" + getInstanceNumber() + "]@"
				+ Integer.toHexString(super.hashCode());
	}

	void TtConnHash(String s, Object connection, Object connID) {
		Tt.println("#C=" + connHash.size() + " I=" + connID.toString() + " C="
				+ connection.toString() + " //" + s);
	}

	void TtXrefHash(String s, int monitorCrossRefID, Object observed) {
		Tt.println("#X=" + xrefHash.size() + " R=" + monitorCrossRefID + " O="
				+ observed + " //" + s);
	}

	public void updateAddresses() {
		List<String> monitorableDevices = getMonitorableDevices();
		if ((monitorableDevices != null) && (monitorableDevices.size() != 0)) {
			synchronized (tsMonitorableDevices) {
				for (Object element : monitorableDevices) {
					if (!tsMonitorableDevices.contains(element)) {
						tsMonitorableDevices.add((String) element);
					}
				}
				tsMonitorableDevices.retainAll(monitorableDevices);
			}
		}
		monitorableDevices = null;
	}

	TSCall validateCall(Object privateData, TSCall call, int callID) {
		if (call == null) {
			return call;
		}

		if ((privateData instanceof LucentTransferredEvent)
				|| (privateData instanceof LucentConferencedEvent)) {
			return call;
		}
		if (privateData instanceof HasUCID) {
			if (((HasUCID) privateData).getUcid() == null) {
				return call;
			}
			if (call.ucid == null) {
				return call;
			}
			if (((HasUCID) privateData).getUcid().compareTo(call.ucid) != 0) {
				log
						.info("Mismatched UCID for validateCall removing stale call obj "
								+ call);

				log.info("UCID for validateCall for the new call is "
						+ ((HasUCID) privateData).getUcid());

				call.setState(34, null);
				dumpCall(callID);
				TSCall newCall = createCall(callID);
				return newCall;
			}
			return call;
		}

		return call;
	}

	void waitToInitialize() {
		if (state == 2) {
			return;
		}
		try {
			synchronized (initThread) {
				initThread.wait(DEFAULT_TIMEOUT);
			}
		} catch (InterruptedException e) {
			throw new TsapiPlatformException(4, 0, "init time-out");
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSProviderImpl JD-Core Version: 0.5.4
 */