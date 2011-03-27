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

	TsapiTerminal(final TsapiProvider _provider, final String _name)
			throws TsapiInvalidArgumentException {
		this(_provider, _name, false);
	}

	TsapiTerminal(final TsapiProvider _provider, final String _name,
			final boolean checkValidity) throws TsapiInvalidArgumentException {
		final TSProviderImpl tsProv = _provider.getTSProviderImpl();
		if (tsProv != null) {
			tsDevice = tsProv.createDevice(_name, checkValidity);
			if (tsDevice == null || !tsDevice.isTerminal()) {
				String info = "";

				if (tsDevice == null)
					info = "; device is null";
				else if (!tsDevice.isTerminal())
					info = "; device is not a terminal";
				throw new TsapiPlatformException(4, 0,
						"could not create terminal: " + _name + info);
			}
		} else
			throw new TsapiPlatformException(4, 0, "could not locate provider");
		name = tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	TsapiTerminal(final TSDevice _tsDevice) {
		tsDevice = _tsDevice;

		name = tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	TsapiTerminal(final TSProviderImpl _provider, final String _name)
			throws TsapiInvalidArgumentException {
		tsDevice = _provider.createDevice(_name, false);

		if (tsDevice == null || !tsDevice.isTerminal()) {
			String info = "";

			if (tsDevice == null)
				info = "; device is null";
			else if (!tsDevice.isTerminal())
				info = "; device is not a terminal";
			throw new TsapiPlatformException(4, 0,
					"could not create terminal: " + _name + info);
		}

		name = tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	// ERROR //
	public final Agent addAgent(final Address agentAddress,
			final ACDAddress acdAddress, final int initialState,
			final String agentID, final String password)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		try {
			if (!(agentAddress instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"agent address is not an instanceof ITsapiAddress");

			if (tsDevice != ((TsapiAddress) agentAddress).getTSDevice())
				throw new TsapiInvalidArgumentException(3, 0,
						"agent address name must be the same as this terminal's name");

			TSDevice tsACDDevice = null;
			if (acdAddress != null) {
				if (!(acdAddress instanceof ITsapiAddress))
					throw new TsapiInvalidArgumentException(3, 0,
							"acd address is not an instanceof ITsapiAddress");

				tsACDDevice = ((TsapiAddress) acdAddress).getTSDevice();
				if (tsACDDevice == null)
					throw new TsapiPlatformException(4, 0,
							"could not locate address");
			} else if (agentID == null)
				throw new TsapiInvalidArgumentException(3, 0,
						"both acd address and agentID were null");

			final TSAgent tsAgent = tsDevice.addTSAgent(tsACDDevice,
					initialState, 0, 0, agentID, password, privData);
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
	public final Agent addAgent(final LucentAddress agentAddress,
			final ACDAddress acdAddress, final int initialState,
			final int workMode, final int reasonCode, final String agentID,
			final String password) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException {
		if (agentAddress == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"agent Address is null");

		return addAgent(agentAddress, acdAddress, initialState, workMode, 0,
				agentID, password);
	}

	public final Agent addAgent(final LucentAddress agentAddress,
			final ACDAddress acdAddress, final int initialState,
			final int workMode, final String agentID, final String password)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		TsapiTrace
				.traceEntry(
						"addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, String agentID, String password]",
						this);
		if (agentAddress == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"agent Address is null");

		final Agent agent = addAgent(agentAddress, acdAddress, initialState,
				workMode, 0, agentID, password);

		TsapiTrace
				.traceExit(
						"addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, String agentID, String password]",
						this);
		return agent;
	}

	public void addCallListener(final CallListener listener)
			throws ResourceUnavailableException {
		TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
		addTsapiCallEventMonitor(null, listener);
		TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
	}

	public void addCallObserver(final CallObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addCallObserver[CallObserver observer]", this);
		addTsapiCallEventMonitor(observer, null);
		TsapiTrace.traceExit("addCallObserver[CallObserver observer]", this);
	}

	public void addObserver(final TerminalObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addObserver[TerminalObserver observer]", this);
		try {
			TSProviderImpl prov = null;
			prov = tsDevice.getTSProviderImpl();

			if (prov == null)
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");

			final Vector<TsapiTerminalMonitor> observers = prov
					.getTerminalMonitorThreads();

			TsapiTerminalMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); ++i) {
					obs = (TsapiTerminalMonitor) observers.elementAt(i);
					if (obs.getObserver() != observer)
						continue;
					found = true;
					break;
				}

				if (!found)
					obs = new TsapiTerminalMonitor(prov, observer);
			}

			try {
				tsDevice.addTerminalMonitor(obs);
			} catch (final TsapiResourceUnavailableException e) {
				if (!found && obs != null)
					prov.removeTerminalMonitorThread(obs);
				throw e;
			}

		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("addObserver[TerminalObserver observer]", this);
	}

	public void addTerminalListener(final TerminalListener listener)
			throws ResourceUnavailableException, MethodNotSupportedException {
		TsapiTrace.traceEntry("addTerminalListener[TerminalListener listener]",
				this);
		try {
			TSProviderImpl prov = null;
			prov = tsDevice.getTSProviderImpl();

			if (prov == null)
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");

			final Vector<TsapiTerminalMonitor> observers = prov
					.getTerminalMonitorThreads();

			TsapiTerminalMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); ++i) {
					obs = (TsapiTerminalMonitor) observers.elementAt(i);
					if (obs.getListener() != listener)
						continue;
					found = true;
					break;
				}

				if (!found)
					obs = new TsapiTerminalMonitor(prov, listener);
			}

			try {
				tsDevice.addTerminalMonitor(obs);
			} catch (final TsapiResourceUnavailableException e) {
				if (!found && obs != null)
					prov.removeTerminalMonitorThread(obs);
				throw e;
			}

		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("addTerminalListener[TerminalListener listener]",
				this);
	}

	private void addTsapiCallEventMonitor(final CallObserver observer,
			final CallListener listener)
			throws TsapiResourceUnavailableException {
		try {
			final TSProviderImpl prov = tsDevice.getTSProviderImpl();

			if (prov == null)
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");

			final Vector<TsapiCallMonitor> observers = prov
					.getCallMonitorThreads();

			TsapiCallMonitor obs = null;
			TsapiCallMonitor obsToUse = null;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); ++i) {
					obs = (TsapiCallMonitor) observers.elementAt(i);
					if (observer != null) {
						if (obs.getObserver() != observer)
							continue;
						obsToUse = obs;

						break;
					}
					if (listener == null || obs.getListener() != listener)
						continue;
					obsToUse = obs;
					break;
				}

				if (obsToUse == null) {
					if (observer != null)
						obsToUse = new TsapiCallMonitor(prov, observer);
					else if (listener != null)
						obsToUse = new TsapiCallMonitor(prov, listener);
					if (obsToUse == null)
						throw new TsapiPlatformException(4, 0,
								"could not allocate Monitor wrapper");

				}

			}

			tsDevice.addTerminalCallMonitor(obsToUse);
		} finally {
			privData = null;
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TsapiTerminal)
			return tsDevice.equals(((TsapiTerminal) obj).tsDevice);

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
			final Vector<TSDevice> tsAddrDevices = tsDevice
					.getTSAddressDevices();
			if (tsAddrDevices == null || tsAddrDevices.size() == 0) {
				TsapiTrace.traceExit("getAddresses[]", this);
				return null;
			}

			final Address[] tsapiAddr = new Address[tsAddrDevices.size()];
			for (int i = 0; i < tsAddrDevices.size(); ++i)
				tsapiAddr[i] = (Address) TsapiCreateObject.getTsapiObject(
						tsAddrDevices.elementAt(i), true);

			TsapiTrace.traceExit("getAddresses[]", this);
			return tsapiAddr;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Agent[] getAgents() {
		try {
			final Vector<TSAgent> tsAgents = tsDevice.getTSAgentsForAgentTerm();
			if (tsAgents == null) {
				privData = null;
				return null;
			}
			synchronized (tsAgents) {
				if (tsAgents.size() == 0) {
					privData = null;
					return null;
				}
				final Agent[] agents = new Agent[tsAgents.size()];
				for (int i = 0; i < tsAgents.size(); ++i)
					agents[i] = (Agent) TsapiCreateObject.getTsapiObject(
							(TSAgent) tsAgents.elementAt(i), false);
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
			final Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if (tsapiTerminalCallObservers == null
					|| tsapiTerminalCallObservers.size() == 0) {
				TsapiTrace.traceExit("getCallListeners[]", this);
				return null;
			}

			CallListener[] listeners = null;
			final ArrayList<CallListener> callListenerList = new ArrayList<CallListener>();

			synchronized (tsapiTerminalCallObservers) {
				for (final Object obs : tsapiTerminalCallObservers) {
					final CallListener listener = ((TsapiCallMonitor) obs)
							.getListener();
					if (listener != null)
						callListenerList.add(((TsapiCallMonitor) obs)
								.getListener());
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
			final Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if (tsapiTerminalCallObservers == null
					|| tsapiTerminalCallObservers.size() == 0) {
				TsapiTrace.traceExit("getCallObservers[]", this);
				return null;
			}

			final ArrayList<CallObserver> callObserverList = new ArrayList<CallObserver>();
			CallObserver[] observers = null;

			for (final Object obs : tsapiTerminalCallObservers) {
				final CallObserver callObserver = ((TsapiCallMonitor) obs)
						.getObserver();
				if (callObserver != null)
					callObserverList.add(callObserver);
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
			final TerminalCapabilities caps = tsDevice
					.getTsapiTerminalCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			privData = null;
		}
	}

	public final String getDirectoryName() {
		TsapiTrace.traceEntry("getDirectoryName[]", this);
		final String name = tsDevice.getDirectoryName();
		TsapiTrace.traceExit("getDirectoryName[]", this);
		return name;
	}

	public final boolean getDoNotDisturb()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getDoNotDisturb[]", this);
		try {
			final boolean dnd = tsDevice.getDoNotDisturb();
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
			final Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if (tsapiTerminalObservers == null
					|| tsapiTerminalObservers.size() == 0) {
				TsapiTrace.traceExit("getObservers[]", this);
				return null;
			}

			final Vector<TerminalObserver> observers = new Vector<TerminalObserver>();

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				final TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getObserver() != null)
					observers.add(obs.getObserver());
			}
			TsapiTrace.traceExit("getObservers[]", this);
			if (observers.size() > 0)
				return (TerminalObserver[]) observers
						.toArray(new TerminalObserver[1]);
			return null;
		} finally {
			privData = null;
		}
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		final Object obj = TsapiPromoter
				.promoteTsapiPrivate((CSTAPrivate) tsDevice
						.getAddrPrivateData());
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	// ERROR //
	public final javax.telephony.Provider getProvider() {
		try {
			final TSProvider tsProvider = tsDevice.getTSProviderImpl();
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
			final Terminal terminal, final Address address)
			throws InvalidArgumentException, PlatformException {
		TsapiTrace.traceEntry(
				"getTerminalCapabilities[Terminal terminal, Address address]",
				this);
		final TerminalCapabilities caps = getCapabilities();
		TsapiTrace.traceExit(
				"getTerminalCapabilities[Terminal terminal, Address address]",
				this);
		return caps;
	}

	// ERROR //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final TerminalConnection[] getTerminalConnections() {
		try {
			Vector<TSConnection> tsconn = null;
			final Vector<TSConnection> vec = tsDevice
					.getTSTerminalConnections();
			if (vec != null)
				tsconn = (Vector) vec.clone();
			else {
				privData = null;
				return null;
			}
			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					privData = null;
					return null;
				}
				final TerminalConnection[] tsapiTermConn = new TerminalConnection[tsconn
						.size()];
				for (int i = 0; i < tsconn.size(); ++i)
					tsapiTermConn[i] = (TerminalConnection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsconn.elementAt(i),
									false);
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
			final Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if (tsapiTerminalObservers == null
					|| tsapiTerminalObservers.size() == 0) {
				TsapiTrace.traceExit("getTerminalListeners[]", this);
				return null;
			}

			final Vector<TerminalListener> listeners = new Vector<TerminalListener>();

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				final TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getListener() != null)
					listeners.add(obs.getListener());
			}
			TsapiTrace.traceExit("getTerminalListeners[]", this);
			if (listeners.size() > 0)
				return (TerminalListener[]) listeners
						.toArray(new TerminalListener[1]);

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
	public final TerminalConnection pickup(final Address pickAddress,
			final Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		try {
			if (!(pickAddress instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"pick Address is not an instanceof ITsapiAddress");

			if (!(terminalAddress instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");

			final TSDevice tsDevice1 = ((TsapiAddress) pickAddress)
					.getTSDevice();
			final TSDevice tsDevice2 = ((TsapiAddress) terminalAddress)
					.getTSDevice();
			if (tsDevice1 != null && tsDevice2 != null) {
				final TSConnection conn = tsDevice.pickup(tsDevice1, tsDevice2,
						privData);
				if (conn != null) {
					final TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsDevice1 == null)
				;
			throw new TsapiPlatformException(4, 0,
					"could not locate terminal address");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final TerminalConnection pickup(
			final javax.telephony.Connection pickConnection,
			final Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		try {
			if (!(pickConnection instanceof ITsapiConnection))
				throw new TsapiInvalidArgumentException(3, 0,
						"pick Connection is not an instanceof ITsapiConnection");

			if (!(terminalAddress instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");

			final TSConnection tsConn = ((TsapiConnection) pickConnection)
					.getTSConnection();
			final TSDevice tsDevice = ((TsapiAddress) terminalAddress)
					.getTSDevice();
			if (tsConn != null && tsDevice != null) {
				final TSConnection conn = tsDevice.pickup(tsConn, tsDevice,
						privData);
				if (conn != null) {
					final TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsConn == null)
				;
			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final TerminalConnection pickup(
			final TerminalConnection pickTermConn, final Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		try {
			if (!(pickTermConn instanceof ITsapiTerminalConnection))
				throw new TsapiInvalidArgumentException(3, 0,
						"pick TerminalConnection is not an instanceof ITsapiTerminalConnection");

			if (!(terminalAddress instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");

			final TSConnection tsConn = ((TsapiTerminalConnection) pickTermConn)
					.getTSConnection();
			final TSDevice tsDevice = ((TsapiAddress) terminalAddress)
					.getTSDevice();
			if (tsConn != null && tsDevice != null) {
				final TSConnection conn = tsDevice.pickup(tsConn, tsDevice,
						privData);
				if (conn != null) {
					final TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsConn == null)
				;
			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final TerminalConnection pickupFromGroup(
			final Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		try {
			if (!(terminalAddress instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");

			final TSDevice tsDevice = ((TsapiAddress) terminalAddress)
					.getTSDevice();
			if (tsDevice != null) {
				final TSConnection conn = tsDevice.groupPickup(tsDevice,
						privData);
				if (conn != null) {
					final TerminalConnection localTerminalConnection = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					return localTerminalConnection;
				}
			}
			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			privData = null;
		}
	}

	public final TerminalConnection pickupFromGroup(final String pickupGroup,
			final Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
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

	public final void removeAgent(final Agent agent)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException {
		TsapiTrace.traceEntry("removeAgent[Agent agent]", this);
		removeAgent(agent, 0);
		TsapiTrace.traceExit("removeAgent[Agent agent]", this);
	}

	public final void removeAgent(final Agent agent, final int reasonCode)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException {
		TsapiTrace.traceEntry("removeAgent[Agent agent, int reasonCode]", this);
		try {
			if (agent == null)
				tsDevice.removeTSAgent(null, reasonCode);
			else {
				if (!(agent instanceof ITsapiAgent))
					throw new TsapiInvalidArgumentException(3, 0,
							"The given Agent is not an instanceof ITsapiAgent");

				final TSAgent tsAgent = ((TsapiAgent) agent).getTSAgent();
				if (tsAgent != null)
					tsDevice.removeTSAgent(tsAgent, reasonCode);
				else
					throw new TsapiPlatformException(4, 0,
							"could not locate agent");
			}
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("removeAgent[Agent agent, int reasonCode]", this);
	}

	public void removeCallListener(final CallListener listener) {
		TsapiTrace
				.traceEntry("removeCallListener[CallListener listener]", this);
		try {
			final Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if (tsapiTerminalCallObservers == null
					|| tsapiTerminalCallObservers.size() == 0) {
				TsapiTrace.traceExit(
						"removeCallListener[CallListener listener]", this);

				return;
			}

			for (int i = 0; i < tsapiTerminalCallObservers.size(); ++i) {
				final TsapiCallMonitor obs = (TsapiCallMonitor) tsapiTerminalCallObservers
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

	public void removeCallObserver(final CallObserver observer) {
		TsapiTrace
				.traceEntry("removeCallObserver[CallObserver observer]", this);
		try {
			final Vector<TsapiCallMonitor> tsapiTerminalCallObservers = tsDevice
					.getTerminalCallObservers();

			if (tsapiTerminalCallObservers == null
					|| tsapiTerminalCallObservers.size() == 0) {
				TsapiTrace.traceExit(
						"removeCallObserver[CallObserver observer]", this);
				return;
			}

			for (int i = 0; i < tsapiTerminalCallObservers.size(); ++i) {
				final TsapiCallMonitor obs = (TsapiCallMonitor) tsapiTerminalCallObservers
						.elementAt(i);
				if (obs.getObserver() != observer)
					continue;
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

	public void removeObserver(final TerminalObserver observer) {
		TsapiTrace
				.traceEntry("removeObserver[TerminalObserver observer]", this);
		try {
			final Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if (tsapiTerminalObservers == null
					|| tsapiTerminalObservers.size() == 0) {
				TsapiTrace.traceExit(
						"removeObserver[TerminalObserver observer]", this);
				return;
			}

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				final TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getObserver() != observer)
					continue;
				tsDevice.removeTerminalMonitor(obs);
				TsapiTrace.traceExit(
						"removeObserver[TerminalObserver observer]", this);
				return;
			}

		} finally {
			privData = null;
		}
	}

	public void removeTerminalListener(final TerminalListener listener) {
		TsapiTrace.traceEntry(
				"removeTerminalListener[TerminalListener listener]", this);
		try {
			final Vector<TsapiTerminalMonitor> tsapiTerminalObservers = tsDevice
					.getTerminalObservers();

			if (tsapiTerminalObservers == null
					|| tsapiTerminalObservers.size() == 0) {
				TsapiTrace.traceExit(
						"removeTerminalListener[TerminalListener listener]",
						this);
				return;
			}

			for (int i = 0; i < tsapiTerminalObservers.size(); ++i) {
				final TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getListener() != listener)
					continue;
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

	public final Object sendPrivateData(final Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			final Object obj = tsDevice.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));
			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (final ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}
	}

	public final void setAgents(final Agent[] agents)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setAgents[Agent[] agents]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"deprecated method, not supported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void setDoNotDisturb(final boolean enable)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setDoNotDisturb[boolean enable]", this);
		try {
			tsDevice.setDoNotDisturb(enable, privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("setDoNotDisturb[boolean enable]", this);
	}

	public final void setPrivateData(final Object data) {
		TsapiTrace.traceEntry("setPrivateData[Object data]", this);
		try {
			privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate) data);
		} catch (final ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}

		TsapiTrace.traceExit("setPrivateData[Object data]", this);
	}
}
