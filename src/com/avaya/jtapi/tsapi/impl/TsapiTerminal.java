package com.avaya.jtapi.tsapi.impl;

import java.util.ArrayList;
import java.util.Vector;

import javax.telephony.Address;
import javax.telephony.CallListener;
import javax.telephony.CallObserver;
import javax.telephony.InvalidArgumentException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PlatformException;
import javax.telephony.Provider;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;
import javax.telephony.TerminalListener;
import javax.telephony.TerminalObserver;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.Agent;
import javax.telephony.capabilities.TerminalCapabilities;
import javax.telephony.privatedata.PrivateData;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.ITsapiAgent;
import com.avaya.jtapi.tsapi.ITsapiConnection;
import com.avaya.jtapi.tsapi.ITsapiTerminal;
import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentV5TerminalEx;
import com.avaya.jtapi.tsapi.TSProvider;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

@SuppressWarnings("deprecation")
class TsapiTerminal implements ITsapiTerminal, PrivateData, LucentV5TerminalEx {
	Logger logger = Logger.getLogger(TsapiTerminal.class);
	TSDevice tsDevice;
	CSTAPrivate privData = null;
	String name;

	TsapiTerminal(TsapiProvider _provider, String _name)
			throws TsapiInvalidArgumentException {
		this(_provider, _name, false);
	}

	TsapiTerminal(TsapiProvider _provider, String _name, boolean checkValidity)
			throws TsapiInvalidArgumentException {
		TSProviderImpl tsProv = _provider.getTSProviderImpl();
		if (tsProv != null) {
			tsDevice = tsProv.createDevice(_name, checkValidity);
			if ((tsDevice == null) || (!tsDevice.isTerminal())) {
				String info = "";

				if (tsDevice == null) {
					info = "; device is null";
				} else if (!tsDevice.isTerminal()) {
					info = "; device is not a terminal";
				}
				throw new TsapiPlatformException(4, 0,
						"could not create terminal: " + _name + info);
			}
		} else {
			throw new TsapiPlatformException(4, 0, "could not locate provider");
		}
		name = tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	TsapiTerminal(TSDevice _tsDevice) {
		tsDevice = _tsDevice;

		name = tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	TsapiTerminal(TSProviderImpl _provider, String _name)
			throws TsapiInvalidArgumentException {
		tsDevice = _provider.createDevice(_name, false);

		if ((tsDevice == null) || (!tsDevice.isTerminal())) {
			String info = "";

			if (tsDevice == null) {
				info = "; device is null";
			} else if (!tsDevice.isTerminal()) {
				info = "; device is not a terminal";
			}
			throw new TsapiPlatformException(4, 0,
					"could not create terminal: " + _name + info);
		}

		name = tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	// ERROR //
	public final Agent addAgent(Address agentAddress, ACDAddress acdAddress,
			int initialState, String agentID, String password)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		try {
			if (!(agentAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"agent address is not an instanceof ITsapiAddress");
			}

			if (tsDevice != ((TsapiAddress) agentAddress).getTSDevice()) {
				throw new TsapiInvalidArgumentException(3, 0,
						"agent address name must be the same as this terminal's name");
			}

			TSDevice tsACDDevice = null;
			if (acdAddress != null) {
				if (!(acdAddress instanceof ITsapiAddress)) {
					throw new TsapiInvalidArgumentException(3, 0,
							"acd address is not an instanceof ITsapiAddress");
				}

				tsACDDevice = ((TsapiAddress) acdAddress).getTSDevice();
				if (tsACDDevice == null) {
					throw new TsapiPlatformException(4, 0,
							"could not locate address");
				}
			} else if (agentID == null) {
				throw new TsapiInvalidArgumentException(3, 0,
						"both acd address and agentID were null");
			}

			TSAgent tsAgent = tsDevice.addTSAgent(tsACDDevice, initialState, 0,
					0, agentID, password, privData);
			Agent localAgent;
			if (tsAgent != null) {
				localAgent = (Agent) TsapiCreateObject.getTsapiObject(tsAgent,
						false);

				privData = null;
				return localAgent;
			}
			throw new TsapiPlatformException(4, 0,
					"could not locate agent to return");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Agent addAgent(LucentAddress agentAddress,
			ACDAddress acdAddress, int initialState, int workMode,
			int reasonCode, String agentID, String password)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		if (agentAddress == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"agent Address is null");
		}

		return addAgent(agentAddress, acdAddress, initialState, workMode, 0,
				agentID, password);
	}

	public final Agent addAgent(LucentAddress agentAddress,
			ACDAddress acdAddress, int initialState, int workMode,
			String agentID, String password)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		TsapiTrace
				.traceEntry(
						"addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, String agentID, String password]",
						this);
		if (agentAddress == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"agent Address is null");
		}

		Agent agent = addAgent(agentAddress, acdAddress, initialState,
				workMode, 0, agentID, password);

		TsapiTrace
				.traceExit(
						"addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, String agentID, String password]",
						this);
		return agent;
	}

	public void addCallListener(CallListener listener)
			throws ResourceUnavailableException {
		TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
		addTsapiCallEventMonitor(null, listener);
		TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
	}

	public void addCallObserver(CallObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addCallObserver[CallObserver observer]", this);
		addTsapiCallEventMonitor(observer, null);
		TsapiTrace.traceExit("addCallObserver[CallObserver observer]", this);
	}

	public void addObserver(TerminalObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addObserver[TerminalObserver observer]", this);
		try {
			TSProviderImpl prov = null;
			prov = tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<TsapiTerminalMonitor> observers = prov
					.getTerminalMonitorThreads();

			TsapiTerminalMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); ++i) {
					obs = (TsapiTerminalMonitor) observers.elementAt(i);
					if (obs.getObserver() != observer) {
						continue;
					}
					found = true;
					break;
				}

				if (!found) {
					obs = new TsapiTerminalMonitor(prov, observer);
				}
			}

			try {
				tsDevice.addTerminalMonitor(obs);
			} catch (TsapiResourceUnavailableException e) {
				if ((!found) && (obs != null)) {
					prov.removeTerminalMonitorThread(obs);
				}
				throw e;
			}

		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("addObserver[TerminalObserver observer]", this);
	}

	public void addTerminalListener(TerminalListener listener)
			throws ResourceUnavailableException, MethodNotSupportedException {
		TsapiTrace.traceEntry("addTerminalListener[TerminalListener listener]",
				this);
		try {
			TSProviderImpl prov = null;
			prov = tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<TsapiTerminalMonitor> observers = prov
					.getTerminalMonitorThreads();

			TsapiTerminalMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); ++i) {
					obs = (TsapiTerminalMonitor) observers.elementAt(i);
					if (obs.getListener() != listener) {
						continue;
					}
					found = true;
					break;
				}

				if (!found) {
					obs = new TsapiTerminalMonitor(prov, listener);
				}
			}

			try {
				tsDevice.addTerminalMonitor(obs);
			} catch (TsapiResourceUnavailableException e) {
				if ((!found) && (obs != null)) {
					prov.removeTerminalMonitorThread(obs);
				}
				throw e;
			}

		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("addTerminalListener[TerminalListener listener]",
				this);
	}

	private void addTsapiCallEventMonitor(CallObserver observer,
			CallListener listener) throws TsapiResourceUnavailableException {
		try {
			TSProviderImpl prov = tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<TsapiCallMonitor> observers = prov.getCallMonitorThreads();

			TsapiCallMonitor obs = null;
			TsapiCallMonitor obsToUse = null;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); ++i) {
					obs = (TsapiCallMonitor) observers.elementAt(i);
					if (observer != null) {
						if (obs.getObserver() != observer) {
							continue;
						}
						obsToUse = obs;

						break;
					}
					if ((listener == null) || (obs.getListener() != listener)) {
						continue;
					}
					obsToUse = obs;
					break;
				}

				if (obsToUse == null) {
					if (observer != null) {
						obsToUse = new TsapiCallMonitor(prov, observer);
					} else if (listener != null) {
						obsToUse = new TsapiCallMonitor(prov, listener);
					}
					if (obsToUse == null) {
						throw new TsapiPlatformException(4, 0,
								"could not allocate Monitor wrapper");
					}

				}

			}

			tsDevice.addTerminalCallMonitor(obsToUse);
		} finally {
			privData = null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TsapiTerminal) {
			return tsDevice.equals(((TsapiTerminal) obj).tsDevice);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (tsDevice != null) {
			tsDevice.unreferenced();
			tsDevice = null;
		}
		TsapiTrace.traceDestruction(this, TsapiTerminal.class);
	}

	public final Address[] getAddresses() {
		TsapiTrace.traceEntry("getAddresses[]", this);
		try {
			Vector<TSDevice> tsAddrDevices = tsDevice.getTSAddressDevices();
			if ((tsAddrDevices == null) || (tsAddrDevices.size() == 0)) {
				TsapiTrace.traceExit("getAddresses[]", this);
				return null;
			}

			Address[] tsapiAddr = new Address[tsAddrDevices.size()];
			for (int i = 0; i < tsAddrDevices.size(); ++i) {
				tsapiAddr[i] = ((Address) TsapiCreateObject.getTsapiObject(
						tsAddrDevices.elementAt(i), true));
			}

			TsapiTrace.traceExit("getAddresses[]", this);
			return tsapiAddr;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Agent[] getAgents() {
		try {
			Vector<TSAgent> tsAgents = tsDevice.getTSAgentsForAgentTerm();
			if (tsAgents == null) {
				privData = null;
				return null;
			}
			synchronized (tsAgents) {
				if (tsAgents.size() == 0) {
					privData = null;
					return null;
				}
				Agent[] agents = new Agent[tsAgents.size()];
				for (int i = 0; i < tsAgents.size(); ++i) {
					agents[i] = ((Agent) TsapiCreateObject.getTsapiObject(
							(TSAgent) tsAgents.elementAt(i), false));
				}
				privData = null;
				return agents;
			}
		} finally {
			privData = null;
		}
	}

	public CallListener[] getCallListeners() {
		TsapiTrace.traceEntry("getCallListeners[]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getCallListeners[]", this);
				return null;
			}

			CallListener[] listeners = null;
			ArrayList<CallListener> callListenerList = new ArrayList<CallListener>();

			synchronized (tsapiTerminalCallObservers) {
				for (Object obs : tsapiTerminalCallObservers) {
					CallListener listener = ((TsapiCallMonitor) obs)
							.getListener();
					if (listener != null) {
						callListenerList.add(((TsapiCallMonitor) obs)
								.getListener());
					}
				}
			}
			listeners = new CallListener[callListenerList.size()];
			TsapiTrace.traceExit("getCallListeners[]", this);
			return (CallListener[]) callListenerList.toArray(listeners);
		} finally {
			privData = null;
		}
	}

	public CallObserver[] getCallObservers() {
		TsapiTrace.traceEntry("getCallObservers[]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getCallObservers[]", this);
				return null;
			}

			ArrayList<CallObserver> callObserverList = new ArrayList<CallObserver>();
			CallObserver[] observers = null;

			for (Object obs : tsapiTerminalCallObservers) {
				CallObserver callObserver = ((TsapiCallMonitor) obs)
						.getObserver();
				if (callObserver != null) {
					callObserverList.add(callObserver);
				}
			}
			observers = new CallObserver[callObserverList.size()];
			return (CallObserver[]) callObserverList.toArray(observers);
		} finally {
			privData = null;
			TsapiTrace.traceExit("getCallObservers[]", this);
		}
	}

	public final TerminalCapabilities getCapabilities() {
		TsapiTrace.traceEntry("getCapabilities[]", this);
		try {
			TerminalCapabilities caps = tsDevice.getTsapiTerminalCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			privData = null;
		}
	}

	public final String getDirectoryName() {
		TsapiTrace.traceEntry("getDirectoryName[]", this);
		String name = tsDevice.getDirectoryName();
		TsapiTrace.traceExit("getDirectoryName[]", this);
		return name;
	}

	public final boolean getDoNotDisturb()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getDoNotDisturb[]", this);
		try {
			boolean dnd = tsDevice.getDoNotDisturb();
			TsapiTrace.traceExit("getDoNotDisturb[]", this);
			return dnd;
		} finally {
			privData = null;
		}
	}

	public final String getName() {
		TsapiTrace.traceEntry("getName[]", this);
		try {
			TsapiTrace.traceExit("getName[]", this);
			return name;
		} finally {
			privData = null;
		}
	}

	public TerminalObserver[] getObservers() {
		TsapiTrace.traceEntry("getObservers[]", this);
		try {
			Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit("getObservers[]", this);
				return null;
			}

			Vector<TerminalObserver> observers = new Vector<TerminalObserver>();

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getObserver() != null) {
					observers.add(obs.getObserver());
				}
			}
			TsapiTrace.traceExit("getObservers[]", this);
			if (observers.size() > 0) {
				return (TerminalObserver[]) observers
						.toArray(new TerminalObserver[1]);
			}
			return null;
		} finally {
			privData = null;
		}
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		Object obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate) tsDevice
				.getAddrPrivateData());
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	// ERROR //
	public final javax.telephony.Provider getProvider() {
		try {
			TSProvider tsProvider = tsDevice.getTSProviderImpl();
			Provider localProvider;
			if (tsProvider != null) {
				localProvider = (Provider) TsapiCreateObject.getTsapiObject(
						tsProvider, false);

				privData = null;

				return localProvider;
			}
			throw new TsapiPlatformException(4, 0, "could not locate provider");
		} finally {
			privData = null;
		}
	}

	public final TerminalCapabilities getTerminalCapabilities(
			Terminal terminal, Address address)
			throws InvalidArgumentException, PlatformException {
		TsapiTrace.traceEntry(
				"getTerminalCapabilities[Terminal terminal, Address address]",
				this);
		TerminalCapabilities caps = getCapabilities();
		TsapiTrace.traceExit(
				"getTerminalCapabilities[Terminal terminal, Address address]",
				this);
		return caps;
	}

	// ERROR //
	@SuppressWarnings("unchecked")
	public final TerminalConnection[] getTerminalConnections() {
		try {
			Vector<TSConnection> tsconn = null;
			Vector<TSConnection> vec = tsDevice.getTSTerminalConnections();
			if (vec != null) {
				tsconn = (Vector) vec.clone();
			} else {
				privData = null;
				return null;
			}
			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					privData = null;
					return null;
				}
				TerminalConnection[] tsapiTermConn = new TerminalConnection[tsconn
						.size()];
				for (int i = 0; i < tsconn.size(); ++i) {
					tsapiTermConn[i] = ((TerminalConnection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsconn.elementAt(i),
									false));
				}
				privData = null;
				return tsapiTermConn;
			}
		} finally {
			privData = null;
		}
	}

	public TerminalListener[] getTerminalListeners() {
		TsapiTrace.traceEntry("getTerminalListeners[]", this);
		try {
			Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit("getTerminalListeners[]", this);
				return null;
			}

			Vector<TerminalListener> listeners = new Vector<TerminalListener>();

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getListener() != null) {
					listeners.add(obs.getListener());
				}
			}
			TsapiTrace.traceExit("getTerminalListeners[]", this);
			if (listeners.size() > 0) {
				return (TerminalListener[]) listeners
						.toArray(new TerminalListener[1]);
			}

			return null;
		} finally {
			privData = null;
		}
	}

	public final TSDevice getTSDevice() {
		TsapiTrace.traceEntry("getTSDevice[]", this);
		TsapiTrace.traceExit("getTSDevice[]", this);
		return tsDevice;
	}

	@Override
	public final int hashCode() {
		return tsDevice.hashCode();
	}

	// ERROR //
	public final TerminalConnection pickup(Address pickAddress,
			Address terminalAddress) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		try {
			if (!(pickAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"pick Address is not an instanceof ITsapiAddress");
			}

			if (!(terminalAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");
			}

			TSDevice tsDevice1 = ((TsapiAddress) pickAddress).getTSDevice();
			TSDevice tsDevice2 = ((TsapiAddress) terminalAddress).getTSDevice();
			if ((tsDevice1 != null) && (tsDevice2 != null)) {
				TSConnection conn = tsDevice.pickup(tsDevice1, tsDevice2,
						privData);
				if (conn != null) {
					TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsDevice1 == null) {
				;
			}
			throw new TsapiPlatformException(4, 0,
					"could not locate terminal address");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final TerminalConnection pickup(
			javax.telephony.Connection pickConnection, Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		try {
			if (!(pickConnection instanceof ITsapiConnection)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"pick Connection is not an instanceof ITsapiConnection");
			}

			if (!(terminalAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");
			}

			TSConnection tsConn = ((TsapiConnection) pickConnection)
					.getTSConnection();
			TSDevice tsDevice = ((TsapiAddress) terminalAddress).getTSDevice();
			if ((tsConn != null) && (tsDevice != null)) {
				TSConnection conn = tsDevice.pickup(tsConn, tsDevice, privData);
				if (conn != null) {
					TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsConn == null) {
				;
			}
			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final TerminalConnection pickup(TerminalConnection pickTermConn,
			Address terminalAddress) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		try {
			if (!(pickTermConn instanceof ITsapiTerminalConnection)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"pick TerminalConnection is not an instanceof ITsapiTerminalConnection");
			}

			if (!(terminalAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");
			}

			TSConnection tsConn = ((TsapiTerminalConnection) pickTermConn)
					.getTSConnection();
			TSDevice tsDevice = ((TsapiAddress) terminalAddress).getTSDevice();
			if ((tsConn != null) && (tsDevice != null)) {
				TSConnection conn = tsDevice.pickup(tsConn, tsDevice, privData);
				if (conn != null) {
					TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsConn == null) {
				;
			}
			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final TerminalConnection pickupFromGroup(Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		try {
			if (!(terminalAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");
			}

			TSDevice tsDevice = ((TsapiAddress) terminalAddress).getTSDevice();
			if (tsDevice != null) {
				TSConnection conn = tsDevice.groupPickup(tsDevice, privData);
				if (conn != null) {
					TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
			}
			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			privData = null;
		}
	}

	public final TerminalConnection pickupFromGroup(String pickupGroup,
			Address terminalAddress) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"pickupFromGroup[String pickupGroup, Address terminalAddress]",
				this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void removeAgent(Agent agent)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException {
		TsapiTrace.traceEntry("removeAgent[Agent agent]", this);
		removeAgent(agent, 0);
		TsapiTrace.traceExit("removeAgent[Agent agent]", this);
	}

	public final void removeAgent(Agent agent, int reasonCode)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException {
		TsapiTrace.traceEntry("removeAgent[Agent agent, int reasonCode]", this);
		try {
			if (agent == null) {
				tsDevice.removeTSAgent(null, reasonCode);
			} else {
				if (!(agent instanceof ITsapiAgent)) {
					throw new TsapiInvalidArgumentException(3, 0,
							"The given Agent is not an instanceof ITsapiAgent");
				}

				TSAgent tsAgent = ((TsapiAgent) agent).getTSAgent();
				if (tsAgent != null) {
					tsDevice.removeTSAgent(tsAgent, reasonCode);
				} else {
					throw new TsapiPlatformException(4, 0,
							"could not locate agent");
				}
			}
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("removeAgent[Agent agent, int reasonCode]", this);
	}

	public void removeCallListener(CallListener listener) {
		TsapiTrace
				.traceEntry("removeCallListener[CallListener listener]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeCallListener[CallListener listener]", this);

				return;
			}

			for (int i = 0; i < tsapiTerminalCallObservers.size(); ++i) {
				TsapiCallMonitor obs = (TsapiCallMonitor) tsapiTerminalCallObservers
						.elementAt(i);
				if (obs.getListener() == listener) {
					tsDevice.removeTerminalCallMonitor(obs);
					TsapiTrace.traceExit(
							"removeCallListener[CallListener listener]", this);
					return;
				}
			}
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
	}

	public void removeCallObserver(CallObserver observer) {
		TsapiTrace
				.traceEntry("removeCallObserver[CallObserver observer]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeCallObserver[CallObserver observer]", this);
				return;
			}

			for (int i = 0; i < tsapiTerminalCallObservers.size(); ++i) {
				TsapiCallMonitor obs = (TsapiCallMonitor) tsapiTerminalCallObservers
						.elementAt(i);
				if (obs.getObserver() != observer) {
					continue;
				}
				tsDevice.removeTerminalCallMonitor(obs);
				TsapiTrace.traceExit(
						"removeCallObserver[CallObserver observer]", this);
				return;
			}

		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
	}

	public void removeObserver(TerminalObserver observer) {
		TsapiTrace
				.traceEntry("removeObserver[TerminalObserver observer]", this);
		try {
			Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeObserver[TerminalObserver observer]", this);
				return;
			}

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getObserver() != observer) {
					continue;
				}
				tsDevice.removeTerminalMonitor(obs);
				TsapiTrace.traceExit(
						"removeObserver[TerminalObserver observer]", this);
				return;
			}

		} finally {
			privData = null;
		}
	}

	public void removeTerminalListener(TerminalListener listener) {
		TsapiTrace.traceEntry(
				"removeTerminalListener[TerminalListener listener]", this);
		try {
			Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeTerminalListener[TerminalListener listener]",
						this);
				return;
			}

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getListener() != listener) {
					continue;
				}
				tsDevice.removeTerminalMonitor(obs);
				TsapiTrace.traceExit(
						"removeTerminalListener[TerminalListener listener]",
						this);
				return;
			}

		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"removeTerminalListener[TerminalListener listener]", this);
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			Object obj = tsDevice.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));
			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}
	}

	public final void setAgents(Agent[] agents)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setAgents[Agent[] agents]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"deprecated method, not supported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void setDoNotDisturb(boolean enable)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setDoNotDisturb[boolean enable]", this);
		try {
			tsDevice.setDoNotDisturb(enable, privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("setDoNotDisturb[boolean enable]", this);
	}

	public final void setPrivateData(Object data) {
		TsapiTrace.traceEntry("setPrivateData[Object data]", this);
		try {
			privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate) data);
		} catch (ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}

		TsapiTrace.traceExit("setPrivateData[Object data]", this);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiTerminal JD-Core Version: 0.5.4
 */