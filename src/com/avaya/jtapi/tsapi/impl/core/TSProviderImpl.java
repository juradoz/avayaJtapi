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
			if (!Tsapi.isRefreshPeriodChanged())
				return;
			timerThread.cancel();
			final int interval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
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

	public TSProviderImpl(final String _url, final Vector<TsapiVendor> vendors) {
		setInstanceNumber();

		TSProviderImpl.log.info("TSProvider: version '"
				+ getProviderVersionDetails() + "', for " + this);

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
		if (connectStringData.telephonyServers != null)
			for (final InetSocketAddress telephonyServer : connectStringData.telephonyServers)
				Tsapi.addServer(telephonyServer);
		tsEHandler = new TSEventHandler(this);

		TSProviderImpl.log.info("TSProvider: calling acsOpenStream serverID="
				+ connectStringData.serverId + " loginID="
				+ connectStringData.loginId + " passwd=******* for " + this);

		tsapi = TsapiFactory.getTsapi(connectStringData.serverId,
				connectStringData.loginId, connectStringData.password, vendors,
				tsEHandler);

		lucent = LucentPrivateData.isAvayaVendor(getVendor());

		auditor = new TSAuditThread(this);
		auditor.start();

		timerThread = new Timer("TsapiProReader", true);

		final int timeInterval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
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
			final SysStatHandler handler = new SysStatHandler();
			try {
				tsapi.startSystemStatusMonitoring(null, handler);
			} catch (final Exception e) {
				if (e instanceof ITsapiException)
					throw new TsapiPlatformException(
							((ITsapiException) e).getErrorType(),
							((ITsapiException) e).getErrorCode(),
							"startSystemStatusMonitoring() failure: "
									+ e.getMessage());

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

	public void addAddressMonitorThread(final TsapiAddressMonitor obs) {
		if (addressMonitorThreads.contains(obs))
			return;

		addressMonitorThreads.addElement(obs);
	}

	void addAgentToHash(final TSAgent agent) {
		synchronized (agentHash) {
			final TSAgentKey agentKey = agent.getAgentKey();
			if (agentKey != null) {
				final Object oldObj = agentHash.put(agentKey, agent);
				if (oldObj != null)
					TSProviderImpl.log.info("NOTICE: agentHash.put() replaced "
							+ oldObj + " for " + this);
			}
		}
	}

	void addAgentToSaveHash(final TSAgent agent) {
		auditor.putAgent(agent);
	}

	public void addCallMonitorThread(final TsapiCallMonitor obs) {
		if (callMonitorThreads.contains(obs))
			return;

		callMonitorThreads.addElement(obs);
	}

	public IDomainDevice addCallToDomain(final IDomainDevice d,
			final IDomainCall c) {
		return m_providerTracker.addCallToDomain(d, c);
	}

	TSCall addCallToHash(final TSCall call) {
		synchronized (callHash) {
			final Object oldObj = callHash.put(new Integer(call.getCallID()),
					call);
			if (oldObj != null)
				TSProviderImpl.log.info("NOTICE: callHash.put() replaced "
						+ oldObj + " for " + this);
			return (TSCall) oldObj;
		}
	}

	void addCallToSaveHash(final TSCall call) {
		auditor.putCall(call);
	}

	TSConnection addConnectionToHash(final TSConnection connection) {
		synchronized (connHash) {
			Object oldObj = null;
			final CSTAConnectionID connID = connection.getConnID();
			if (connID != null) {
				oldObj = connHash.put(connID, connection);

				TtConnHash("addConn", connection, connID);

				TSProviderImpl.log.info("NOTICE: connHash.put() replaced "
						+ oldObj + " with " + connection + " for " + this);
			}

			return (TSConnection) oldObj;
		}
	}

	void addConnectionToSaveHash(final TSConnection connection) {
		auditor.putConn(connection);
	}

	private void addDeviceNameToPrintingBuffer(final StringBuffer aBuffer,
			final String aName) {
		if (aBuffer.length() > 0)
			aBuffer.append(", ");
		aBuffer.append(aName);
	}

	void addDeviceToHash(final String deviceID, final TSDevice device) {
		synchronized (devHash) {
			if (deviceID != null) {
				final Object oldObj = devHash.put(deviceID, device);
				if (oldObj != null)
					TSProviderImpl.log.info("NOTICE: devHash.put() replaced "
							+ oldObj + " for " + this);
			}
		}
	}

	void addDeviceToHash(final TSDevice device) {
		addDeviceToHash(device.getName(), device);
	}

	void addMonitor(final int monitorCrossRefID, final Object observed) {
		synchronized (xrefHash) {
			final Object oldObj = xrefHash.put(new Integer(monitorCrossRefID),
					observed);

			TtXrefHash("addMon", monitorCrossRefID, observed);
			if (oldObj != null)
				TSProviderImpl.log.info("NOTICE: xrefHash.put() replaced "
						+ oldObj + " for " + this);
		}
	}

	public void addMonitor(final TsapiProviderMonitor monitor)
			throws TsapiResourceUnavailableException {
		synchronized (obsSync) {
			if (monitors.contains(monitor))
				return;

			monitors.addElement(monitor);

			monitor.addReference();
		}

		sendSnapshot(monitor);
	}

	void addNonCallToHash(final TSCall call) {
		synchronized (nonCallHash) {
			final Object oldObj = nonCallHash.put(
					new Integer(call.getNonCallID()), call);

			if (oldObj != null)
				TSProviderImpl.log.info("NOTICE: nonCallHash.put() replaced "
						+ oldObj + " for " + this);
		}
	}

	void addPrivateXref(final int xrefID, final TSDevice tsDevice) {
		synchronized (privXrefHash) {
			final Object oldObj = privXrefHash.put(new Integer(xrefID),
					tsDevice);
			if (oldObj != null)
				TSProviderImpl.log.info("NOTICE: privXrefHash.put() replaced "
						+ oldObj + " for " + this);
		}
	}

	public void addProviderMonitorThread(final TsapiProviderMonitor obs) {
		if (providerMonitorThreads.contains(obs))
			return;

		providerMonitorThreads.addElement(obs);
	}

	void addRoute(final int routeRegisterID, final TSDevice tsDevice) {
		synchronized (routeRegHash) {
			final Object oldObj = routeRegHash.put(
					new Integer(routeRegisterID), tsDevice);

			if (oldObj != null)
				TSProviderImpl.log.info("NOTICE: routeRegHash.put() replaced "
						+ oldObj + " for " + this);
		}
	}

	public void addRouteMonitorThread(final TsapiRouteMonitor obs) {
		if (routeMonitorThreads.contains(obs))
			return;

		routeMonitorThreads.addElement(obs);
	}

	public void addTerminalMonitorThread(final TsapiTerminalMonitor obs) {
		if (terminalMonitorThreads.contains(obs))
			return;

		terminalMonitorThreads.addElement(obs);
	}

	void addTrunkToHash(final String name, final TSTrunk trunk) {
		synchronized (trkHash) {
			if (name != null) {
				final Object oldObj = trkHash.put(name, trunk);
				if (oldObj != null)
					TSProviderImpl.log.info("NOTICE: trkHash.put() replaced "
							+ oldObj + " for " + this);
			}
		}
	}

	boolean allowCallMonitoring() {
		return callMonitoring;
	}

	void callCleanup() {
		final Enumeration<TSCall> callEnum = callHash.elements();

		while (callEnum.hasMoreElements()) {
			TSCall call;
			try {
				call = (TSCall) callEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
				continue;
			}

			if (call == null)
				TSProviderImpl.log
						.error("callCleanup: handled AuditThread null call reference race condition for "
								+ this);

			if (call.hasReceivedCallClearedTransfer()) {
				if (System.currentTimeMillis()
						- call.getCallClearedTransferReceiptTime() < 3000L)
					continue;
				final Vector<TSEvent> eventList = new Vector<TSEvent>();

				call.setState(34, eventList);

				final int jtapiCause = 212;

				tsEHandler.doCallMonitors(call, eventList, jtapiCause, null);
			}

			if (call.checkForMonitors())
				continue;
			boolean is_confirmed_that_call_is_gone = false;

			boolean lucent_tactics_get_an_answer = false;

			if (isLucentV5())
				try {
					final String old_ucid = call.getUCID();
					final String new_ucid = call.queryUCID();

					if (old_ucid != null && new_ucid != null
							&& old_ucid.compareTo(new_ucid) != 0)
						is_confirmed_that_call_is_gone = true;
					else
						is_confirmed_that_call_is_gone = false;

					lucent_tactics_get_an_answer = true;
				} catch (final TsapiUnableToSendException tue) {
					throw tue;
				} catch (final TsapiPlatformException e) {
					if (e.getErrorType() == 2
							&& (e.getErrorCode() == 24 || e.getErrorCode() == 11)) {
						is_confirmed_that_call_is_gone = true;

						lucent_tactics_get_an_answer = true;
					} else if (e.getErrorType() == 2 && e.getErrorCode() == 15)
						TSProviderImpl.log
								.info("Error: UCID not enabled on switch - interferes with JTAPI Call Auditing");

				} catch (final Exception e) {
					TSProviderImpl.log.error(e.getMessage(), e);
				}

			if (lucent_tactics_get_an_answer && is_confirmed_that_call_is_gone
					|| !lucent_tactics_get_an_answer && !call.updateObject()) {
				if (call.getTSState() == 34)
					if (callHash.get(new Integer(call.getCallID())) == null)
						TSProviderImpl.log
								.info("Benign race condition: call (callid "
										+ call.getCallID()
										+ ") went INVALID while being audited");
					else {
						TSProviderImpl.log
								.info("ERROR: removing call (callid "
										+ call.getCallID()
										+ ") from Provider's records - Audit indicates call had ended");

						call.delete();
					}

				call.setState(34, null);
			}
		}
	}

	public void changeCallIDInDomain(final int old_callid, final int new_callid) {
		m_providerTracker.changeCallIDInDomain(old_callid, new_callid);
	}

	TSAgent createAgent(final TSAgentKey agentKey, final String agentID,
			final String password) {
		return createAgent(agentKey, agentID, password,
				TSProviderImpl.CREATEAGENT_ACCEPT_DELETED);
	}

	TSAgent createAgent(final TSAgentKey agentKey, final String agentID,
			final String password, final int deletedAgentSearchPolicy) {
		TSAgent agent = null;

		boolean newObject = false;
		boolean auditObject = false;

		synchronized (agentHash) {
			agent = (TSAgent) agentHash.get(agentKey);
			if (agent == null) {
				if (deletedAgentSearchPolicy == TSProviderImpl.CREATEAGENT_ACCEPT_DELETED)
					agent = auditor.getAgent(agentKey);

				if (agent == null) {
					newObject = true;
					agent = new TSAgent(this, agentKey, password);
					addAgentToHash(agent);
				} else
					auditObject = true;
			}
		}

		if (newObject)
			agent.finishConstruction();
		else {
			agent.waitForConstruction();

			if (agent.getACDDeviceID() == null && agentKey.acdDeviceID != null
					&& !auditObject)
				agent.addToSkillsVector(agentKey.acdDeviceID);
		}
		return agent;
	}

	TSCall createCall(final int callID) {
		synchronized (callHash) {
			TSCall call = null;

			if (callID != 0) {
				call = findCall(callID);
				if (call != null)
					return call;

				call = new TSCall(this, callID);
			} else
				call = new TSCall(this, callID);

			return call;
		}
	}

	TSCall createCall(final int callID, final Object privateData) {
		final TSCall call = createCall(callID);
		return validateCall(privateData, call, callID);
	}

	TSConnection createConnection(final CSTAConnectionID connID,
			final TSDevice device, final Vector<TSEvent> eventList) {
		TSConnection conn = null;

		boolean newObject = false;

		synchronized (connHash) {
			if (connID != null)
				conn = (TSConnection) connHash.get(connID);
			if (conn == null) {
				if (connID != null)
					conn = auditor.getConn(connID);
				if (conn == null) {
					newObject = true;
					conn = new TSConnection(this, connID, device, false);
					if (connID != null)
						addConnectionToHash(conn);
				}
			}
		}
		if (newObject)
			conn.finishConstruction(null, eventList);
		else
			conn.waitForConstruction();

		return conn.getTSConn();
	}

	public TSDevice createDevice(final CSTAExtendedDeviceID deviceID) {
		if (deviceID == null || deviceID.getDeviceIDStatus() != 0
				|| deviceID.getDeviceID() == null)
			return null;

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

	public TSDevice createDevice(final CSTAExtendedDeviceID deviceID,
			final boolean checkValidity) throws TsapiInvalidArgumentException {
		if (deviceID == null || deviceID.getDeviceIDStatus() != 0
				|| deviceID.getDeviceID() == null)
			return null;
		if (checkValidity) {
			if (deviceID.getDeviceID().equals("AllRouteAddress"))
				return createDevice(deviceID);

			if (state == 2 && securityOn
					&& !tsMonitorableDevices.contains(deviceID.getDeviceID()))
				throw new TsapiInvalidArgumentException(0, 0,
						"not in provider's domain");

		}

		return createDevice(deviceID);
	}

	TSDevice createDevice(final CSTAExtendedDeviceID deviceID,
			final CSTAConnectionID connID) {
		if (deviceID == null || deviceID.getDeviceIDStatus() != 0
				|| deviceID.getDeviceID() == null)
			return null;

		TSDevice device = null;

		device = (TSDevice) devHash.get(deviceID.getDeviceID());

		if (device == null) {
			if (connID != null) {
				TSConnection conn = (TSConnection) connHash.get(connID);
				if (conn == null)
					conn = auditor.getConn(connID);
				if (conn != null) {
					device = conn.getTSDevice();

					synchronized (device) {
						device.addName(deviceID);
						synchronized (devHash) {
							final TSDevice tmpDev = (TSDevice) devHash
									.get(deviceID.getDeviceID());

							if (tmpDev == null)
								addDeviceToHash(device);
							else
								device = tmpDev;
						}
					}
				}
			}

			boolean notFound = false;
			synchronized (devHash) {
				if (device == null
						&& (device = (TSDevice) devHash.get(deviceID
								.getDeviceID())) == null)
					notFound = true;

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

	TSDevice createDevice(final String deviceID) {
		return createDevice(deviceID, null);
	}

	public TSDevice createDevice(final String name, final boolean checkValidity)
			throws TsapiInvalidArgumentException {
		if (name == null)
			return null;

		return createDevice(
				new CSTAExtendedDeviceID(name, (short) 0, (short) 0),
				checkValidity);
	}

	TSDevice createDevice(final String deviceID, final CSTAConnectionID connID) {
		if (deviceID == null)
			return null;
		return createDevice(new CSTAExtendedDeviceID(deviceID, (short) 0,
				(short) 0), connID);
	}

	TSConnection createTerminalConnection(final CSTAConnectionID connID,
			final TSDevice termConnDevice, final Vector<TSEvent> eventList,
			final TSDevice connDevice) {
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
				} else
					auditObject = true;
			}
		}

		if (newObject)
			conn.finishConstruction(connDevice, eventList);
		else
			conn.waitForConstruction();

		if (termConnDevice.isTerminal() && !conn.isTerminalConnection())
			if (isLucent()) {
				if (!auditObject) {
					deleteConnectionFromHash(connID);
					conn = createTerminalConnection(connID, termConnDevice,
							eventList, connDevice);
				}
			} else
				conn.setTerminalConnection();

		return conn;
	}

	TSTrunk createTrunk(final String trkName, final int type) {
		if (trkName == null)
			return null;

		synchronized (trkHash) {
			TSTrunk trunk = null;

			trunk = (TSTrunk) trkHash.get(trkName);

			if (trunk != null)
				return trunk;

			trunk = new TSTrunk(this, trkName, type);

			return trunk;
		}
	}

	public TSCall createTSCall(final int callID) {
		final TSCall call = createCall(callID);
		call.updateObject();
		return call;
	}

	public TSConnection createTSConnection(final CSTAConnectionID connID,
			final TSDevice device) {
		return createConnection(connID, device, null);
	}

	public TSTrunk createTSTrunk(final String trkName) {
		final TSTrunk trunk = createTrunk(trkName, 1);
		return trunk;
	}

	void deleteAgentFromHash(final TSAgentKey agentKey) {
		if (agentKey != null)
			agentHash.remove(agentKey);
	}

	void deleteCallFromHash(final int callID) {
		callHash.remove(new Integer(callID));
	}

	void deleteConnectionFromHash(final CSTAConnectionID connID) {
		if (connID != null) {
			connHash.remove(connID);

			TtConnHash("delConn", "NO OBJECT", connID);
		}
	}

	void deleteDeviceFromHash(final String _deviceID) {
		synchronized (devHash) {
			final Object removedObj = devHash.remove(_deviceID);
			TSProviderImpl.log.info("NOTICE: devHash.remove() removed "
					+ removedObj + " by device name " + _deviceID);
		}
	}

	void deleteDeviceFromHash(final TSDevice device) {
		synchronized (devHash) {
			final Vector<CSTAExtendedDeviceID> keys = device.getKeys();
			for (int i = 0; i < keys.size(); ++i) {
				final String key = ((CSTAExtendedDeviceID) keys.elementAt(i))
						.getDeviceID();
				final Object removedObj = devHash.remove(key);
				TSProviderImpl.log.info("NOTICE: devHash.remove() removed "
						+ removedObj + " for device name(" + i + ") " + key);
			}
		}
	}

	void deleteInstanceOfDeviceFromHash(final TSDevice _soughtObj) {
		int keys_not_in_hash = 0;
		int keys_pointing_elsewhere = 0;
		final Hashtable<String, Object> keys_pointing_at = new Hashtable<String, Object>();

		final StringBuffer alias_names = new StringBuffer();

		final StringBuffer elsewhere_names = new StringBuffer();

		final StringBuffer not_in_hash_names = new StringBuffer();

		synchronized (devHash) {
			final Vector<CSTAExtendedDeviceID> keys = _soughtObj.getKeys();
			for (int i = 0; i < keys.size(); ++i) {
				final String key = ((CSTAExtendedDeviceID) keys.elementAt(i))
						.getDeviceID();
				final Object foundObj = devHash.get(key);
				final boolean foundAny = foundObj != null;
				final boolean foundThatOne = foundObj == _soughtObj;

				if (foundThatOne)
					devHash.remove(key);

				if (foundThatOne)
					addDeviceNameToPrintingBuffer(alias_names, key);
				else if (foundAny) {
					++keys_pointing_elsewhere;
					keys_pointing_at.put(key, foundObj);
					addDeviceNameToPrintingBuffer(elsewhere_names, key);
				} else {
					++keys_not_in_hash;
					addDeviceNameToPrintingBuffer(not_in_hash_names, key);
				}

			}

		}

		if (keys_pointing_elsewhere + keys_not_in_hash == 0)
			TSProviderImpl.log
					.info("NOTICE: devHash.remove() expected, found and removed "
							+ _soughtObj
							+ " by device name(s) ["
							+ alias_names
							+ "]");
		else {
			if (keys_pointing_elsewhere > 0) {
				final Iterator<String> key_iter = keys_pointing_at.keySet()
						.iterator();

				while (key_iter.hasNext()) {
					final String d = (String) key_iter.next();
					final TSDevice t = (TSDevice) keys_pointing_at.get(d);
					TSProviderImpl.log
							.info("NOTICE: devHash.remove() expected "
									+ _soughtObj
									+ " but found "
									+ t
									+ " by device name "
									+ d
									+ " - race condition - left latter TSDevice in hash");
				}

			}

			if (keys_not_in_hash > 0)
				TSProviderImpl.log
						.info("NOTICE: attempted to devHash.remove() "
								+ _soughtObj + " by device name(s) ["
								+ not_in_hash_names
								+ "] but no TSDevice there by those name(s)");
		}
	}

	void deleteMonitor(final int monitorCrossRefID) {
		xrefHash.remove(new Integer(monitorCrossRefID));

		TtXrefHash("delMon", monitorCrossRefID, "GONE");
	}

	void deleteNonCallFromHash(final int nonCallId) {
		nonCallHash.remove(new Integer(nonCallId));
	}

	void deletePrivateXref(final int xrefID) {
		synchronized (privXrefHash) {
			privXrefHash.remove(new Integer(xrefID));
		}
	}

	void deleteRoute(final int routeRegisterID) {
		routeRegHash.remove(new Integer(routeRegisterID));
	}

	void deleteTrunkFromHash(final String name) {
		synchronized (trkHash) {
			while (trkHash.remove(name) != null)
				;
		}
	}

	public void disableHeartbeat() {
		if (tsapi != null)
			tsapi.disableHeartbeat();
	}

	Vector<TSCall> doCallSnapshot(final String device) {
		if (tsCaps.getSnapshotDeviceReq() == 0)
			return null;

		final ProviderSnapshotDeviceConfHandler handler = new ProviderSnapshotDeviceConfHandler(
				this);
		try {
			tsapi.snapshotDevice(device, null, handler);
		} catch (final TsapiUnableToSendException tue) {
			throw tue;
		} catch (final Exception e) {
			TSProviderImpl.log.error(e.getMessage(), e);
			return null;
		}

		return handler.cv;
	}

	void dump(final String indent) {
		TSProviderImpl.log.trace(indent + "***** PROVIDER DUMP *****");
		TSProviderImpl.log.trace(indent + "TSProvider: " + this);

		TSProviderImpl.log.trace(indent + "TSProvider: "
				+ connectStringData.serverId + ";login="
				+ connectStringData.loginId + ";passwd=*******");

		TSProviderImpl.log.trace(indent + "TSProvider state: " + state);
		TSProviderImpl.log.trace(indent + "TSProvider version details: "
				+ getProviderVersionDetails());

		TSProviderImpl.log.trace(indent + "TSProvider calls: ");
		final Enumeration<TSCall> callEnum = callHash.elements();

		while (callEnum.hasMoreElements()) {
			TSCall call;
			try {
				call = (TSCall) callEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
				continue;
			}

			call.dump(indent + " ");
		}
		TSProviderImpl.log.trace(indent + "TSProvider non calls: ");
		final Enumeration<TSCall> nonCallEnum = nonCallHash.elements();

		while (nonCallEnum.hasMoreElements()) {
			TSCall nonCall;
			try {
				nonCall = (TSCall) nonCallEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
				continue;
			}

			nonCall.dump(indent + " ");
		}

		TSProviderImpl.log.trace(indent
				+ "TSProvider VDN Calls-to-VDN Domain Mapping: ");

		dumpDomainData(indent);

		TSProviderImpl.log.trace(indent + "TSProvider devices: ");
		final Enumeration<TSDevice> deviceEnum = devHash.elements();

		while (deviceEnum.hasMoreElements()) {
			TSDevice device;
			try {
				device = (TSDevice) deviceEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
				continue;
			}

			device.dump(indent + " ");
		}
		TSProviderImpl.log.trace(indent + "TSProvider conns: ");
		final Enumeration<TSConnection> connEnum = connHash.elements();

		while (connEnum.hasMoreElements()) {
			TSConnection conn;
			try {
				conn = (TSConnection) connEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
				continue;
			}

			conn.dump(indent + " ");
		}
		TSProviderImpl.log.trace(indent + "TSProvider agents: ");
		final Enumeration<TSAgent> agentEnum = agentHash.elements();

		while (agentEnum.hasMoreElements()) {
			TSAgent agent;
			try {
				agent = (TSAgent) agentEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
				continue;
			}

			agent.dump(indent + " ");
		}
		TSProviderImpl.log.trace(indent + "TSProvider trunks: ");
		final Enumeration<TSTrunk> trkEnum = trkHash.elements();

		while (trkEnum.hasMoreElements()) {
			TSTrunk trk;
			try {
				trk = (TSTrunk) trkEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
				continue;
			}

			trk.dump(indent + " ");
		}
		TSProviderImpl.log.trace(indent + "TSProvider xrefs: ");
		final Enumeration<Object> xrefEnum = xrefHash.elements();
		while (xrefEnum.hasMoreElements())
			try {
				TSProviderImpl.log.trace(indent + "xref object: "
						+ xrefEnum.nextElement());
			} catch (final NoSuchElementException e) {
				TSProviderImpl.log.error(e.getMessage(), e);
			}

		TSProviderImpl.log.trace(indent + "TSProvider audits: ");
		auditor.dump(indent + " ");
		TSProviderImpl.log.trace(indent + "***** PROVIDER DUMP END *****");
	}

	void dumpAgent(final TSAgentKey agentKey) {
		auditor.dumpAgent(agentKey);
	}

	void dumpCall(final int callID) {
		auditor.dumpCall(callID);
	}

	void dumpConn(final CSTAConnectionID connID) {
		auditor.dumpConn(connID);
	}

	public void dumpDomainData(final String indent) {
		m_providerTracker.dumpDomainData(indent);
	}

	public void enableHeartbeat() {
		if (tsapi != null)
			tsapi.enableHeartbeat();
		else
			enableTsapiHeartbeat = true;
	}

	public void finalizeOldProvider() {
		synchronized (TSProviderImpl.provider_count_lock) {
			if (TSProviderImpl.provider_count > 0) {
				TSProviderImpl.provider_count -= 1;
				if (TSProviderImpl.provider_count == 0)
					JtapiEventThreadManager.drainThreads();
			}
		}
	}

	TSDevice findACDDevice(final int xrefID) {
		return (TSDevice) privXrefHash.get(new Integer(xrefID));
	}

	TSAgent findAgent(final TSAgentKey agentKey) {
		return (TSAgent) agentHash.get(agentKey);
	}

	TSCall findCall(final int callID) {
		synchronized (callHash) {
			TSCall call = null;

			if (callID != 0) {
				call = (TSCall) callHash.get(new Integer(callID));
				if (call != null)
					return call;

				call = auditor.getCall(callID);
				if (call != null)
					return call;

			}

			return null;
		}
	}

	TSDevice findDevice(final String name) {
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
		if (!isLucent())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		try {
			final LucentQueryCallClassifier qcc = new LucentQueryCallClassifier();
			final Object result = sendPrivateData(qcc.makeTsapiPrivate());

			if (result instanceof LucentCallClassifierInfo)
				return new CallClassifierInfo(
						((LucentCallClassifierInfo) result).numAvailPorts,
						((LucentCallClassifierInfo) result).numInUsePorts);

			return null;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						" service failure");

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	boolean getCallMonitor() {
		CSTAEvent event;
		try {
			event = tsapi.CSTAQueryCallMonitor();
		} catch (final TsapiUnableToSendException tue) {
			throw tue;
		} catch (final Exception e) {
			TSProviderImpl.log.error(e.getMessage(), e);
			return false;
		}
		final CSTAQueryCallMonitorConfEvent qcmConf = (CSTAQueryCallMonitorConfEvent) event
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
		final TSCapabilities tsCaps = new TSCapabilities();

		if (isLucent())
			tsCaps.setLucent(getLucentPDV());

		try {
			final CSTAEvent event = tsapi.getApiCaps();
			if (event.getEvent() == null) {
				TSProviderImpl.log
						.info("Init Capabilities: Conf event null, enable all Capabilities for "
								+ this);

				tsCaps.setAll();
				return tsCaps;
			}
			if (event.getEvent() instanceof CSTAGetAPICapsConfEvent) {
				final CSTAGetAPICapsConfEvent getAPICapsConf = (CSTAGetAPICapsConfEvent) event
						.getEvent();

				if (isLucentV5())
					tsCaps.setAddParty(1);
				tsCaps.setAlternateCall(getAPICapsConf.getAlternateCall());
				tsCaps.setAnswerCall(getAPICapsConf.getAnswerCall());
				tsCaps.setCallCompletion(getAPICapsConf.getCallCompletion());

				tsCaps.setClearCall(getAPICapsConf.getClearCall());
				tsCaps.setClearConnection(getAPICapsConf.getClearConnection());

				tsCaps.setConferenceCall(getAPICapsConf.getConferenceCall());

				tsCaps.setConsultationCall(getAPICapsConf.getConsultationCall());

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
				tsCaps.setCallClearedEvent(getAPICapsConf.getCallClearedEvent());

				tsCaps.setConferencedEvent(getAPICapsConf.getConferencedEvent());

				tsCaps.setConnectionClearedEvent(getAPICapsConf
						.getConnectionClearedEvent());

				tsCaps.setDeliveredEvent(getAPICapsConf.getDeliveredEvent());

				tsCaps.setDivertedEvent(getAPICapsConf.getDivertedEvent());
				tsCaps.setEstablishedEvent(getAPICapsConf.getEstablishedEvent());

				tsCaps.setFailedEvent(getAPICapsConf.getFailedEvent());
				tsCaps.setHeldEvent(getAPICapsConf.getHeldEvent());
				tsCaps.setNetworkReachedEvent(getAPICapsConf
						.getNetworkReachedEvent());

				tsCaps.setOriginatedEvent(getAPICapsConf.getOriginatedEvent());

				tsCaps.setQueuedEvent(getAPICapsConf.getQueuedEvent());
				tsCaps.setRetrievedEvent(getAPICapsConf.getRetrievedEvent());

				tsCaps.setServiceInitiatedEvent(getAPICapsConf
						.getServiceInitiatedEvent());

				tsCaps.setTransferredEvent(getAPICapsConf.getTransferredEvent());

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

				tsCaps.setSendPrivateEvent(getAPICapsConf.getSendPrivateEvent());

				tsCaps.setSysStatReq(getAPICapsConf.getSysStatReq());
				tsCaps.setSysStatStart(getAPICapsConf.getSysStatStart());
				tsCaps.setSysStatStop(getAPICapsConf.getSysStatStop());
				tsCaps.setChangeSysStatFilter(getAPICapsConf
						.getChangeSysStatFilter());

				tsCaps.setSysStatReqEvent(getAPICapsConf.getSysStatReqEvent());

				tsCaps.setSysStatReqConf(getAPICapsConf.getSysStatReqConf());

				tsCaps.setSysStatEvent(getAPICapsConf.getSysStatEvent());

				final Object replyPriv = event.getPrivData();
				if (replyPriv instanceof LucentGetAPICapsConfEvent
						&& replyPriv instanceof LucentV5GetAPICapsConfEvent
						&& replyPriv instanceof LucentV7GetAPICapsConfEvent) {
					final LucentV7GetAPICapsConfEvent cf = (LucentV7GetAPICapsConfEvent) replyPriv;

					setAdministeredSwitchSoftwareVersion(cf
							.getAdministeredSwitchSoftwareVersion());

					setSwitchSoftwareVersion(cf.getSwitchSoftwareVersion());

					setOfferType(cf.getOfferType());
					setServerType(cf.getServerType());
					setMonitorCallsViaDevice(cf.getMonitorCallsViaDevice());
				}

			} else {
				TSProviderImpl.log
						.info("Init Capabilities: expected conf event with pduType 125,received conf event with pduType "
								+ event.getEvent().getPDU()
								+ ", enable all Capabilities" + " for " + this);

				tsCaps.setAll();
				return tsCaps;
			}
		} catch (final Exception e) {
			TSProviderImpl.log
					.error("Init Capabilities: Exception, enable all Capabilities - Exception: "
							+ e + " for " + this);

			tsCaps.setAll();
		}

		return tsCaps;
	}

	public TSConnection getConnection(final CSTAConnectionID connID) {
		TSConnection conn = null;

		synchronized (connHash) {
			if (connID != null)
				conn = (TSConnection) connHash.get(connID);
			if (conn == null && connID != null)
				conn = auditor.getConn(connID);
		}

		if (conn == null)
			return null;

		conn.waitForConstruction();

		return conn.getTSConn();
	}

	public int getCurrentStateOfCallFromTelephonyServer(final int callId) {
		TSCall currentCall = null;

		if (callId < 1)
			throw new TsapiPlatformException(3, 0,
					"Please pass a Call ID value that is greater than 0.");

		currentCall = createTSCall(callId);

		return getCurrentStateOfCallFromTelephonyServer(currentCall);
	}

	public int getCurrentStateOfCallFromTelephonyServer(final TSCall call) {
		if (call == null)
			throw new TsapiPlatformException(3, 0,
					"Call object passed in is null.");

		TSProviderImpl.log
				.info("Forcing a query on telephony server to check state of call - "
						+ call);
		return call.getStateFromServer();
	}

	short getDeviceExt(final String deviceID) {
		if (tsCaps.getQueryDeviceInfo() == 0)
			return 0;
		CSTAEvent event;
		try {
			event = tsapi.queryDeviceInfo(deviceID, null);
		} catch (final TsapiUnableToSendException tue) {
			throw tue;
		} catch (final Exception e) {
			TSProviderImpl.log.error(e.getMessage(), e);
			return 0;
		}

		final Object replyPriv = event.getPrivData();
		if (replyPriv instanceof LucentQueryDeviceInfoConfEvent) {
			if (((LucentQueryDeviceInfoConfEvent) replyPriv)
					.getExtensionClass() == 0)
				return 1;
			if (((LucentQueryDeviceInfoConfEvent) replyPriv)
					.getExtensionClass() == 1)
				return 2;

			if (!(replyPriv instanceof LucentV5QueryDeviceInfoConfEvent))
				;
		}

		return 0;
	}

	public IDomainCall getDomainCall(final int callid) {
		return findCall(callid);
	}

	public IDomainDevice getDomainCallIsIn(final IDomainCall c) {
		return m_providerTracker.getDomainCallIsIn(c);
	}

	public IDomainDevice getDomainDevice(final String name) {
		return findDevice(name);
	}

	public int getInstanceNumber() {
		return m_instanceNumber;
	}

	public int getLucentPDV() {
		if (lucent) {
			if (lucentPDV == -1) {
				final byte[] version = tsapi.getVendorVersion();

				if (version.length == 0 || version[0] != 0
						|| version[(version.length - 1)] != 0) {
					TSProviderImpl.log
							.info("Version bytes with no data, or missing discriminator byte or trailing NULL byte, found while decoding TSAPI private version string");

					lucentPDV = 0;
				} else
					try {
						lucentPDV = Integer.parseInt(new String(version, 1,
								version.length - 2, "US-ASCII"));
					} catch (final Exception e) {
						TSProviderImpl.log
								.info("Exception occurred decoding TSAPI private version string: "
										+ e);

						lucentPDV = 0;
					}
			}
			return lucentPDV;
		}
		return 0;
	}

	List<String> getMonitorableDevices() {
		final short[] level = { 1, 2, 3 };

		final List<String> listOfMonitorableDevices = new ArrayList<String>();
		for (int i = 0; i < level.length; ++i) {
			int index = TSProviderImpl.GET_DEVICE_INITIAL_INDEX;
			do {
				CSTAEvent event;
				try {
					event = tsapi.getDeviceList(index, level[i]);
				} catch (final Exception e) {
					// break label164:
					break;
				}
				if (event != null) {
					final CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent) event
							.getEvent();

					if (getDeviceListConf.getDriverSdbLevel() == 1
							|| getDeviceListConf.getDriverSdbLevel() == -1) {
						setSecurity(false);
						return listOfMonitorableDevices;
					}
					for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
						final String device = getDeviceListConf.getDevList()[j];

						if (!listOfMonitorableDevices.contains(device))
							listOfMonitorableDevices.add(device);
					}
					// label164:
					index = getDeviceListConf.getIndex();
				}
			} while (index != TSProviderImpl.GET_DEVICE_NO_MORE_INDEX);
		}

		return listOfMonitorableDevices;
	}

	public boolean getMonitorCallsViaDevice() {
		return monitorCallsViaDevice;
	}

	Object getMonitoredObject(final int xrefID) {
		return xrefHash.get(new Integer(xrefID));
	}

	@SuppressWarnings("rawtypes")
	public String getMonitoredObjects() {
		final StringBuffer buffer = new StringBuffer();
		for (final Map.Entry entry : xrefHash.entrySet())
			buffer.append(entry.getKey() + ":" + entry.getValue() + "\n");
		return buffer.toString();
	}

	public Vector<TsapiProviderMonitor> getMonitors() {
		return new Vector<TsapiProviderMonitor>(monitors);
	}

	public String getName() {
		return connectStringData.url;
	}

	synchronized int getNonCallID() {
		final int[] start = { nonCallID, 0 };
		for (int j = 0; j < 1; ++j)
			for (int i = start[j]; i < 100; ++i)
				if (nonCallIDArray[i] == TSProviderImpl.NOT_IN_USE) {
					nonCallID = i;
					nonCallIDArray[i] = TSProviderImpl.IN_USE;
					return nonCallID;
				}
		return -1;
	}

	public String getOfferType() {
		return offerType;
	}

	public Object getPrivateData() {
		if (replyPriv instanceof CSTAPrivate)
			return replyPriv;
		return null;
	}

	public Vector<TsapiProviderMonitor> getProviderMonitorThreads() {
		return providerMonitorThreads;
	}

	String getProviderVersionDetails() {
		// String std_string = "production build";

		final String stdver = "5.2.0.483";

		final String customver = "production build";

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
				final Vector<TSEvent> eventList = new Vector<TSEvent>();
				final SysStatHandler handler = new SysStatHandler();
				try {
					tsapi.requestSystemStatus(null, handler);
				} catch (final Exception e) {
					TSProviderImpl.log
							.warn("Failed to get system status. Returning OUT_OF_SERVICE to be safe");
					setState(0, eventList, true);
					jtapiState = 17;
				}
				if (handler.getSystemStatus() != 1
						&& handler.getSystemStatus() != 2) {
					setState(0, eventList, true);
					jtapiState = 17;
				}
				if (eventList.size() > 0) {
					final Vector<TsapiProviderMonitor> observers = getMonitors();
					for (int j = 0; j < observers.size(); ++j) {
						final TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
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
		if (!isLucent())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		try {
			final LucentQueryTod qtod = new LucentQueryTod();
			final Object result = sendPrivateData(qtod.makeTsapiPrivate(),
					null, true);

			if (result instanceof LucentQueryTodConfEvent) {
				final LucentQueryTodConfEvent tod = (LucentQueryTodConfEvent) result;
				if (tod.getYear() < 97)
					tod.setYear(tod.getYear() + 100);
				final Calendar cal = Calendar.getInstance();
				cal.set(tod.getYear(), tod.getMonth() - 1, tod.getDay(),
						tod.getHour(), tod.getMinute(), tod.getSecond());

				return cal.getTime();
			}
			return null;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						" service failure");

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	public String getSwitchSoftwareVersion() {
		return switchSoftwareVersion;
	}

	public Vector<TsapiTerminalMonitor> getTerminalMonitorThreads() {
		return terminalMonitorThreads;
	}

	public LucentTrunkGroupInfo getTrunkGroupInfo(final String trunkAccessCode)
			throws TsapiMethodNotSupportedException {
		if (!isLucent())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		try {
			final LucentQueryTg qtg = new LucentQueryTg(trunkAccessCode);
			final Object result = sendPrivateData(qtg.makeTsapiPrivate());

			if (result instanceof LucentTrunkGroupInfo)
				return (LucentTrunkGroupInfo) result;
			return null;
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						" service failure");

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	public Vector<TSDevice> getTSACDDevices()
			throws TsapiMethodNotSupportedException {
		if (!isLucent())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		if (!securityOn)
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD addresses can be accessed.");

		final Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i)
			if (getDeviceExt((String) tsMonitorableDevices.elementAt(i)) == 2) {
				final TSDevice device = createDevice((String) tsMonitorableDevices
						.elementAt(i));

				if (device != null)
					tsDeviceVector.addElement(device);
			}
		return tsDeviceVector;
	}

	public Vector<TSDevice> getTSACDManagerDevices()
			throws TsapiMethodNotSupportedException {
		if (!isLucent())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		if (!securityOn)
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD Manager addresses can be accessed.");

		final Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i)
			if (getDeviceExt((String) tsMonitorableDevices.elementAt(i)) == 1) {
				final TSDevice device = createDevice((String) tsMonitorableDevices
						.elementAt(i));

				if (device != null)
					tsDeviceVector.addElement(device);
			}
		return tsDeviceVector;
	}

	public Vector<TSDevice> getTSAddressDevices() {
		if (!securityOn)
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Addesses can be accessed.");

		final Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			final TSDevice device = createDevice((String) tsMonitorableDevices
					.elementAt(i));
			if (device != null)
				tsDeviceVector.addElement(device);
		}
		final TSDevice device = createDevice("AllRouteAddress");
		if (device != null)
			tsDeviceVector.addElement(device);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector<TSCall> getTSCalls() {
		final Vector tsCallVector = new Vector();
		Vector tsDevCallVector = null;

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			tsDevCallVector = doCallSnapshot((String) tsMonitorableDevices
					.elementAt(i));

			if (tsDevCallVector != null)
				for (int j = 0; j < tsDevCallVector.size(); ++j)
					if (!tsCallVector.contains(tsDevCallVector.elementAt(j)))
						tsCallVector.addElement(tsDevCallVector.elementAt(j));
		}
		Enumeration<TSCall> callEnum;
		synchronized (nonCallHash) {
			callEnum = nonCallHash.elements();
			while (callEnum.hasMoreElements())
				try {
					tsCallVector.addElement(callEnum.nextElement());
				} catch (final NoSuchElementException e) {
					TSProviderImpl.log.error(e.getMessage(), e);
				}

		}

		synchronized (callHash) {
			callEnum = callHash.elements();
			while (callEnum.hasMoreElements()) {
				TSCall callVar;
				try {
					callVar = (TSCall) callEnum.nextElement();
				} catch (final NoSuchElementException e) {
					TSProviderImpl.log.error(e.getMessage(), e);
					continue;
				}

				if (!tsCallVector.contains(callVar))
					;
				tsCallVector.addElement(callVar);
			}

		}

		return tsCallVector;
	}

	public TSEventHandler getTsEHandler() {
		return tsEHandler;
	}

	public Vector<TSDevice> getTSRouteDevices() {
		if (!securityOn)
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Route addresses can be accessed.");

		final Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsRouteDevices.size(); ++i) {
			final TSDevice device = createDevice((String) tsRouteDevices
					.elementAt(i));
			if (device != null)
				tsDeviceVector.addElement(device);
		}
		final TSDevice device = createDevice("AllRouteAddress");
		if (device != null)
			tsDeviceVector.addElement(device);
		return tsDeviceVector;
	}

	public Vector<TSDevice> getTSTerminalDevices() {
		if (!securityOn)
			throw new TsapiPlatformException(
					4,
					0,
					"Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Terminals can be accessed.");

		final Vector<TSDevice> tsDeviceVector = new Vector<TSDevice>();

		waitToInitialize();

		for (int i = 0; i < tsMonitorableDevices.size(); ++i) {
			final String devName = (String) tsMonitorableDevices.elementAt(i);
			final TSDevice device = createDevice(devName);
			if (device != null && device.isTerminal())
				tsDeviceVector.addElement(device);
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
		if (tsapi != null)
			return tsapi.heartbeatIsEnabled();

		return false;
	}

	public void heartbeatTimeout() {
		TSProviderImpl.log
				.info("*** Heartbeat timer expired.  Shutting down Provider. ***");

		shutdown();
	}

	public void initNewProvider() {
		synchronized (TSProviderImpl.provider_count_lock) {
			TSProviderImpl.provider_count += 1;
		}
	}

	public boolean isCallInAnyDomain(final IDomainCall c) {
		return m_providerTracker.isCallInAnyDomain(c);
	}

	boolean isConnInActiveHash(final CSTAConnectionID connID) {
		return connHash.get(connID) != null;
	}

	boolean isConnInAnyHash(final CSTAConnectionID connID) {
		return isConnInActiveHash(connID) || isConnInSaveHash(connID);
	}

	boolean isConnInDisconnectedHash(final CSTAConnectionID connID) {
		return auditor.getConn(connID) != null;
	}

	boolean isConnInSaveHash(final CSTAConnectionID connID) {
		return auditor.getConn(connID) != null;
	}

	boolean isDeviceMonitorable(final String name) {
		if (state == 2 && securityOn) {
			if (name == null)
				return false;
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

	public void logln(final String s) {
		if (TSProviderImpl.log.isInfoEnabled())
			TSProviderImpl.log.info(s);
	}

	private ConnectStringData parseURL(final String _url) {
		String serverID = _url;
		String loginID = "";
		String passwd = "";
		Collection<InetSocketAddress> telephonyServers = new LinkedHashSet<InetSocketAddress>();
		final int firstSemiColon_index = _url.indexOf(';');
		serverID = _url.substring(0, firstSemiColon_index);
		if (firstSemiColon_index >= 0) {
			final StringTokenizer params = new StringTokenizer(
					_url.substring(firstSemiColon_index + 1), ";");

			while (params.hasMoreTokens()) {
				final StringTokenizer param = new StringTokenizer(
						params.nextToken(), "=");

				if (!param.hasMoreTokens())
					continue;
				final String key = param.nextToken();
				if (!param.hasMoreTokens())
					continue;
				final String value = param.nextToken();

				if (key.equals("login") || key.equals("loginID"))
					loginID = value;
				else if (key.equals("passwd"))
					passwd = value;
				else if (key.equals("servers"))
					telephonyServers = JtapiUtils.parseTelephonyServerEntry(
							value, 450);
			}
		}

		if (loginID.length() > 48)
			throw new TsapiPlatformException(4, 0,
					"Username provided is more than 48 characters in length. Login ID="
							+ loginID);

		if (passwd.length() > 47)
			throw new TsapiPlatformException(4, 0,
					"Password provided is more than 47 characters in length. Password length="
							+ passwd.length());

		return new ConnectStringData(serverID, loginID, passwd,
				telephonyServers, _url);
	}

	synchronized void releaseNonCallID(final int nonCallId) {
		nonCallIDArray[nonCallId] = TSProviderImpl.NOT_IN_USE;
	}

	public void removeAddressMonitorThread(final TsapiAddressMonitor obs) {
		addressMonitorThreads.removeElement(obs);
	}

	public void removeAllCallsForDomain(final IDomainDevice d) {
		m_providerTracker.removeAllCallsForDomain(d);
	}

	public void removeCallFromDomain(final IDomainCall c) {
		m_providerTracker.removeCallFromDomain(c);
	}

	public void removeCallMonitorThread(final TsapiCallMonitor obs) {
		callMonitorThreads.removeElement(obs);
	}

	public void removeMonitor(final TsapiProviderMonitor monitor) {
		removeMonitor(monitor, 100, null);
	}

	void removeMonitor(final TsapiProviderMonitor monitor, final int cause,
			final Object privateData) {
		if (monitors.removeElement(monitor))
			monitor.deleteReference(cause, privateData);
	}

	void removeMonitors(final int cause, final Object privateData) {
		final Vector<TsapiProviderMonitor> obs = new Vector<TsapiProviderMonitor>(
				monitors);
		for (int i = 0; i < obs.size(); ++i)
			removeMonitor((TsapiProviderMonitor) obs.elementAt(i), cause,
					privateData);
	}

	public void removeProviderMonitorThread(final TsapiProviderMonitor obs) {
		providerMonitorThreads.removeElement(obs);
	}

	public void removeRouteMonitorThread(final TsapiRouteMonitor obs) {
		routeMonitorThreads.removeElement(obs);
	}

	public void removeTerminalMonitorThread(final TsapiTerminalMonitor obs) {
		terminalMonitorThreads.removeElement(obs);
	}

	public String requestPrivileges() throws TsapiInvalidArgumentException {
		final RequestPrivilegesConfHandler handler = new RequestPrivilegesConfHandler(
				this);

		boolean request_failed = true;
		try {
			tsapi.requestPrivileges(null, handler);
			request_failed = false;
			final String str = handler.get_nonce();

			return str;
		} catch (final TsapiPlatformException e) {
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
			if (request_failed)
				shutdown();
		}
	}

	public Object sendPrivateData(final CSTAPrivate data)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		return sendPrivateData(data, null, false);
	}

	Object sendPrivateData(final CSTAPrivate data,
			final ConfHandler extraHandler)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		return sendPrivateData(data, extraHandler, false);
	}

	Object sendPrivateData(final CSTAPrivate data,
			final ConfHandler extraHandler, final boolean priority)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		if (data.tsType == 89) {
			ConfHandler handler;
			if (priority)
				handler = new PriorityEscapeConfHandler(this, extraHandler);
			else
				handler = new EscapeConfHandler(this, extraHandler);
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

	void sendSnapshot(final TsapiProviderMonitor callback) {
		if (callback == null)
			return;

		final Vector<TSEvent> eventList = new Vector<TSEvent>();

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

		if (eventList.size() <= 0)
			return;
		callback.deliverEvents(eventList, true);
	}

	void setAdministeredSwitchSoftwareVersion(
			final String administeredSwitchSoftwareVersion) {
		this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
	}

	public void setAdviceOfCharge(final boolean flag)
			throws TsapiMethodNotSupportedException {
		if (!isLucentV5())
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");

		try {
			final LucentSetAdviceOfCharge aoc = new LucentSetAdviceOfCharge(
					flag);
			sendPrivateData(aoc.makeTsapiPrivate());
		} catch (final TsapiPlatformException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						" service failure");

			throw new TsapiPlatformException(4, 0, " service failure");
		}
	}

	void setCallMonitor(final boolean _callMonitoring) {
		callMonitoring = _callMonitoring;
	}

	void setCapabilities(final TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
	}

	void setClientHeartbeatInterval(final short heartbeatInterval) {
		tsapi.setClientHeartbeatInterval(heartbeatInterval);
	}

	public void setDebugPrinting(final boolean enable) {
		boolean traceLoggingEnabled = JTAPILoggingAdapter
				.isTraceLoggingEnabled();
		final boolean errorLoggingEnabled = Logger.getLogger(
				"com.avaya.jtapi.tsapi").isEnabledFor(Level.ERROR);
		final boolean isLog4jLoggingEnabled = JtapiUtils.isLog4jConfigured();

		if (!traceLoggingEnabled && isLog4jLoggingEnabled)
			traceLoggingEnabled = true;

		if (enable) {
			if (traceLoggingEnabled)
				Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.TRACE);
			else {
				JTAPILoggingAdapter.setTraceLoggerLevel("7");
				JTAPILoggingAdapter.initializeLogging();
			}

		} else {
			if (!traceLoggingEnabled)
				return;
			if (errorLoggingEnabled)
				Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.ERROR);
			else
				Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.OFF);
		}
	}

	public void setHeartbeatInterval(final short heartbeatInterval)
			throws TsapiInvalidArgumentException {
		final ConfHandler handler = new SetHeartbeatIntervalConfHandler(this);
		try {
			tsapi.setHeartbeatInterval(heartbeatInterval, null, handler);
		} catch (final TsapiInvalidArgumentException e) {
			throw e;
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"setHeartbeatInterval() failure: " + e.getMessage());

			throw new TsapiPlatformException(4, 0,
					"setHeartbeatInterval() failure: " + e.getMessage());
		}
	}

	private void setInstanceNumber() {
		synchronized (TSProviderImpl.g_lock) {
			m_instanceNumber = ++TSProviderImpl.g_instanceNumber;
		}
	}

	void setMonitorCallsViaDevice(final boolean monitorCallsViaDevice) {
		this.monitorCallsViaDevice = monitorCallsViaDevice;
	}

	void setOfferType(final String offerType) {
		this.offerType = offerType;
	}

	public void setPrivateData(final Object o) {
		if (o instanceof CSTAPrivate)
			replyPriv = o;
	}

	public void setPrivileges(final String xmlData)
			throws TsapiInvalidArgumentException {
		final ConfHandler handler = new SetPrivilegesConfHandler(this);
		try {
			tsapi.setPrivileges(xmlData, null, handler);
			return;
		} catch (final TsapiInvalidArgumentException e) {
			shutdown();
			throw e;
		} catch (final TsapiPlatformException e) {
			shutdown();
			throw new TsapiPlatformException(e.getErrorType(),
					e.getErrorCode(), "setPrivileges TSAPI failure: " + e);
		} catch (final Exception e) {
			shutdown();
			throw new TsapiPlatformException(4, 0,
					"Unexpected setPrivileges TSAPI failure: " + e);
		}
	}

	void setRouteDevices() {
		int index = TSProviderImpl.GET_DEVICE_INITIAL_INDEX;
		do {
			CSTAEvent event;
			try {
				event = tsapi.getDeviceList(index, (short) 6);
			} catch (final Exception e) {
				return;
			}

			final CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent) event
					.getEvent();

			for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
				final String device = getDeviceListConf.getDevList()[j];

				if (!tsRouteDevices.contains(device))
					tsRouteDevices.addElement(device);
			}
			index = getDeviceListConf.getIndex();
		} while (index != TSProviderImpl.GET_DEVICE_NO_MORE_INDEX);
	}

	void setSecurity(final boolean _securityOn) {
		securityOn = _securityOn;
	}

	void setServerType(final String serverType) {
		this.serverType = serverType;
	}

	public void setSessionTimeout(final int timeout) {
		TsapiSession.setTimeout(timeout);
	}

	void setState(final int tsapi_shutdown, final Vector<TSEvent> eventList) {
		setState(tsapi_shutdown, eventList, false);
	}

	private void setState(final int _state, final Vector<TSEvent> eventList,
			final boolean ignoreOldState) {
		int oldCoreState = 16;
		if (!ignoreOldState)
			oldCoreState = getState();
		synchronized (this) {
			if (state == _state)
				return;

			state = _state;
		}

		switch (state) {
		case 2:
			synchronized (eventList) {
				if (eventList != null) {
					if (ignoreOldState || oldCoreState != 16)
						eventList.addElement(new TSEvent(1, this));

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiInServiceEvent()));
				}
			}

			break;
		case 1:
			synchronized (eventList) {
				if (eventList != null) {
					if (ignoreOldState || oldCoreState != 17)
						eventList.addElement(new TSEvent(2, this));

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiInitializingEvent()));
				}
			}

			break;
		case 0:
			synchronized (eventList) {
				if (eventList != null) {
					if (ignoreOldState || oldCoreState != 17)
						eventList.addElement(new TSEvent(2, this));

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiOutOfServiceEvent()));
				}
			}

			break;
		case 3:
			synchronized (eventList) {
				if (eventList != null) {
					if (ignoreOldState || oldCoreState != 18)
						eventList.addElement(new TSEvent(3, this));

					eventList.addElement(new TSEvent(9999, this,
							new TsapiProviderTsapiShutdownEvent()));
				}

			}

			final Enumeration<Object> xrefEnum = xrefHash.elements();
			Object monitored = null;

			while (xrefEnum.hasMoreElements()) {
				try {
					monitored = xrefEnum.nextElement();
				} catch (final NoSuchElementException e) {
					TSProviderImpl.log.error(e.getMessage(), e);
					continue;
				}

				if (monitored == null)
					continue;

				if (monitored instanceof TSDevice)
					((TSDevice) monitored).removeObservers(100, null, 0);
				if (monitored instanceof TSCall)
					;
				((TSCall) monitored).removeObservers(100, null, 0);
			}

			if (tsapi != null)
				tsapi.shutdown();

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

			if (auditor != null)
				auditor.stopRunning();

			disableHeartbeat();
		}
	}

	void setSwitchSoftwareVersion(final String switchSoftwareVersion) {
		this.switchSoftwareVersion = switchSoftwareVersion;
	}

	public void shutdown() {
		shutdown(null);
	}

	public void shutdown(final Object privateData) {
		TSProviderImpl.log.info("TSProvider.shutdown - attempting shutdown");
		if (timerThread != null)
			timerThread.cancel();
		timerThread = null;

		synchronized (shutdown_single_thread_lock) {
			if (state == 3) {
				TSProviderImpl.log
						.info("TSProvider.shutdown - already in shutdown, redundant call, returning.");
				return;
			}

			TSProviderImpl.log.info("TSProvider.shutdown - Starting");
			if (tsCaps.sysStatStop != 0) {
				final SysStatHandler handler = new SysStatHandler();
				try {
					tsapi.stopSystemStatusMonitoring(null, handler);
				} catch (final Exception e) {
					TSProviderImpl.log
							.error("stopSystemStatusMonitoring() failure: "
									+ e.getMessage());
				}
			}

			final Vector<TSEvent> eventList = new Vector<TSEvent>();
			synchronized (eventList) {
				setState(3, eventList);

				if (privateData != null) {
					for (int i = 0; i < eventList.size(); ++i) {
						final TSEvent ev = (TSEvent) eventList.elementAt(i);
						if (ev.getPrivateData() == null)
							ev.setPrivateData(privateData);
					}
					if (!isLucent())
						eventList.addElement(new TSEvent(9999, this,
								privateData));

				}

				if (eventList.size() > 0) {
					final Vector<TsapiProviderMonitor> observers = getMonitors();
					for (int j = 0; j < observers.size(); ++j) {
						final TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
								.elementAt(j);

						callback.deliverEvents(eventList, false);
					}
				}
			}
			removeMonitors(100, null);

			finalizeOldProvider();

			TSProviderImpl.log.info("TSProvider.shutdown - Done");
		}
	}

	public String toString() {
		return "TSProvider[#" + getInstanceNumber() + "]@"
				+ Integer.toHexString(super.hashCode());
	}

	void TtConnHash(final String s, final Object connection, final Object connID) {
		Tt.println("#C=" + connHash.size() + " I=" + connID.toString() + " C="
				+ connection.toString() + " //" + s);
	}

	void TtXrefHash(final String s, final int monitorCrossRefID,
			final Object observed) {
		Tt.println("#X=" + xrefHash.size() + " R=" + monitorCrossRefID + " O="
				+ observed + " //" + s);
	}

	public void updateAddresses() {
		List<String> monitorableDevices = getMonitorableDevices();
		if (monitorableDevices != null && monitorableDevices.size() != 0)
			synchronized (tsMonitorableDevices) {
				for (final Object element : monitorableDevices)
					if (!tsMonitorableDevices.contains(element))
						tsMonitorableDevices.add((String) element);
				tsMonitorableDevices.retainAll(monitorableDevices);
			}
		monitorableDevices = null;
	}

	TSCall validateCall(final Object privateData, final TSCall call,
			final int callID) {
		if (call == null)
			return call;

		if (privateData instanceof LucentTransferredEvent
				|| privateData instanceof LucentConferencedEvent)
			return call;
		if (privateData instanceof HasUCID) {
			if (((HasUCID) privateData).getUcid() == null)
				return call;
			if (call.ucid == null)
				return call;
			if (((HasUCID) privateData).getUcid().compareTo(call.ucid) != 0) {
				TSProviderImpl.log
						.info("Mismatched UCID for validateCall removing stale call obj "
								+ call);

				TSProviderImpl.log
						.info("UCID for validateCall for the new call is "
								+ ((HasUCID) privateData).getUcid());

				call.setState(34, null);
				dumpCall(callID);
				final TSCall newCall = createCall(callID);
				return newCall;
			}
			return call;
		}

		return call;
	}

	void waitToInitialize() {
		if (state == 2)
			return;
		try {
			synchronized (initThread) {
				initThread.wait(TSProviderImpl.DEFAULT_TIMEOUT);
			}
		} catch (final InterruptedException e) {
			throw new TsapiPlatformException(4, 0, "init time-out");
		}
	}
}
