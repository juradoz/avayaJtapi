package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.ITsapiAgent;
import com.avaya.jtapi.tsapi.ITsapiConnection;
import com.avaya.jtapi.tsapi.ITsapiTerminal;
import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentV5TerminalEx;
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
import java.util.ArrayList;
import java.util.Vector;
import javax.telephony.Address;
import javax.telephony.CallListener;
import javax.telephony.CallObserver;
import javax.telephony.Connection;
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

@SuppressWarnings("deprecation")
class TsapiTerminal implements ITsapiTerminal, PrivateData, LucentV5TerminalEx {
	Logger logger = Logger.getLogger(TsapiTerminal.class);
	TSDevice tsDevice;
	CSTAPrivate privData = null;
	String name;

	public final String getName() {
		TsapiTrace.traceEntry("getName[]", this);
		try {
			TsapiTrace.traceExit("getName[]", this);
			return this.name;
		} finally {
			this.privData = null;
		}
	}

	public final String getDirectoryName() {
		TsapiTrace.traceEntry("getDirectoryName[]", this);
		String name = this.tsDevice.getDirectoryName();
		TsapiTrace.traceExit("getDirectoryName[]", this);
		return name;
	}

	public final Provider getProvider() {
		TsapiTrace.traceEntry("getProvider[]", this);
		try {
			TSProviderImpl TSProviderImpl = this.tsDevice.getTSProviderImpl();
			if (TSProviderImpl != null) {
				Provider provider = (Provider) TsapiCreateObject
						.getTsapiObject(TSProviderImpl, false);
				TsapiTrace.traceExit("getProvider[]", this);
				return provider;
			}

			throw new TsapiPlatformException(4, 0, "could not locate provider");
		} finally {
			this.privData = null;
		}
	}

	public final Address[] getAddresses() {
		TsapiTrace.traceEntry("getAddresses[]", this);
		try {
			Vector<?> tsAddrDevices = this.tsDevice.getTSAddressDevices();
			if ((tsAddrDevices == null) || (tsAddrDevices.size() == 0)) {
				TsapiTrace.traceExit("getAddresses[]", this);
				return null;
			}

			Address[] tsapiAddr = new Address[tsAddrDevices.size()];
			for (int i = 0; i < tsAddrDevices.size(); i++) {
				tsapiAddr[i] = ((Address) TsapiCreateObject.getTsapiObject(
						(TSDevice) tsAddrDevices.elementAt(i), true));
			}

			TsapiTrace.traceExit("getAddresses[]", this);
			return tsapiAddr;
		} finally {
			this.privData = null;
		}
	}

	public final TerminalConnection[] getTerminalConnections() {
		TsapiTrace.traceEntry("getTerminalConnections[]", this);
		try {
			Vector<?> tsconn = this.tsDevice.getTSTerminalConnections();

			if (tsconn == null) {
				TsapiTrace.traceExit("getTerminalConnections[]", this);
				return null;
			}

			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					TsapiTrace.traceExit("getTerminalConnections[]", this);
					return null;
				}

				TerminalConnection[] tsapiTermConn = new TerminalConnection[tsconn
						.size()];
				for (int i = 0; i < tsconn.size(); i++) {
					tsapiTermConn[i] = ((TerminalConnection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsconn.elementAt(i),
									false));
				}
				TsapiTrace.traceExit("getTerminalConnections[]", this);
				return tsapiTermConn;
			}
		} finally {
			this.privData = null;
		}
	}

	public void addObserver(TerminalObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addObserver[TerminalObserver observer]", this);
		try {
			TSProviderImpl prov = null;
			prov = this.tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<?> observers = prov.getTerminalMonitorThreads();

			TsapiTerminalMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); i++) {
					obs = (TsapiTerminalMonitor) observers.elementAt(i);
					if (obs.getObserver() == observer) {
						found = true;
						break;
					}
				}
				if (!found) {
					obs = new TsapiTerminalMonitor(prov, observer);
				}
			}

			try {
				this.tsDevice.addTerminalMonitor(obs);
			} catch (TsapiResourceUnavailableException e) {
				if ((!found) && (obs != null)) {
					prov.removeTerminalMonitorThread(obs);
				}
				throw e;
			}

		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("addObserver[TerminalObserver observer]", this);
	}

	public TerminalObserver[] getObservers() {
		TsapiTrace.traceEntry("getObservers[]", this);
		try {
			Vector<?> tsapiTerminalObservers = this.tsDevice
					.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit("getObservers[]", this);
				return null;
			}

			Vector<TerminalObserver> observers = new Vector<TerminalObserver>();

			for (int i = 0; i < tsapiTerminalObservers.size(); i++) {
				TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getObserver() != null)
					observers.add(obs.getObserver());
			}
			TsapiTrace.traceExit("getObservers[]", this);
			if (observers.size() > 0) {
				return (TerminalObserver[]) observers
						.toArray(new TerminalObserver[1]);
			}
			return null;
		} finally {
			this.privData = null;
		}
	}

	public void removeObserver(TerminalObserver observer) {
		TsapiTrace
				.traceEntry("removeObserver[TerminalObserver observer]", this);
		try {
			Vector<TsapiTerminalMonitor> tsapiTerminalObservers = this.tsDevice.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeObserver[TerminalObserver observer]", this);
			} else {
				for (int i = 0; i < tsapiTerminalObservers.size();) {
					TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
							.elementAt(i);
					if (obs.getObserver() == observer) {
						this.tsDevice.removeTerminalMonitor(obs);
						TsapiTrace.traceExit(
								"removeObserver[TerminalObserver observer]",
								this);
						return;
					}
					i++;
				}
			}
		} finally {
			this.privData = null;
		}
	}

	public void addCallObserver(CallObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addCallObserver[CallObserver observer]", this);
		addTsapiCallEventMonitor(observer, null);
		TsapiTrace.traceExit("addCallObserver[CallObserver observer]", this);
	}

	private void addTsapiCallEventMonitor(CallObserver observer,
			CallListener listener) throws TsapiResourceUnavailableException {
		try {
			TSProviderImpl prov = this.tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<?> observers = prov.getCallMonitorThreads();

			TsapiCallMonitor obs = null;
			TsapiCallMonitor obsToUse = null;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); i++) {
					obs = (TsapiCallMonitor) observers.elementAt(i);
					if (observer != null) {
						if (obs.getObserver() == observer) {
							obsToUse = obs;

							break;
						}
					} else if ((listener != null)
							&& (obs.getListener() == listener)) {
						obsToUse = obs;
						break;
					}

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

			this.tsDevice.addTerminalCallMonitor(obsToUse);
		} finally {
			this.privData = null;
		}
	}

	public CallObserver[] getCallObservers() {
		TsapiTrace.traceEntry("getCallObservers[]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = this.tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getCallObservers[]", this);
				return null;
			}

			ArrayList<CallObserver> callObserverList = new ArrayList<CallObserver>();
			CallObserver[] observers = null;

			for (TsapiCallMonitor obs : tsapiTerminalCallObservers) {
				CallObserver callObserver = obs.getObserver();
				if (callObserver != null)
					callObserverList.add(callObserver);
			}
			observers = new CallObserver[callObserverList.size()];
			return (CallObserver[]) callObserverList.toArray(observers);
		} finally {
			this.privData = null;
			TsapiTrace.traceExit("getCallObservers[]", this);
		}
	}

	public void removeCallObserver(CallObserver observer) {
		TsapiTrace
				.traceEntry("removeCallObserver[CallObserver observer]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = this.tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeCallObserver[CallObserver observer]", this);
				return;
			}
			for (int i = 0; i < tsapiTerminalCallObservers.size();) {
				TsapiCallMonitor obs = (TsapiCallMonitor) tsapiTerminalCallObservers
						.elementAt(i);
				if (obs.getObserver() == observer) {
					this.tsDevice.removeTerminalCallMonitor(obs);
					TsapiTrace.traceExit(
							"removeCallObserver[CallObserver observer]", this);
					return;
				}
				i++;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
	}

	public final TerminalCapabilities getCapabilities() {
		TsapiTrace.traceEntry("getCapabilities[]", this);
		try {
			TerminalCapabilities caps = this.tsDevice
					.getTsapiTerminalCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			this.privData = null;
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

	public final boolean getDoNotDisturb()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getDoNotDisturb[]", this);
		try {
			boolean dnd = this.tsDevice.getDoNotDisturb();
			TsapiTrace.traceExit("getDoNotDisturb[]", this);
			return dnd;
		} finally {
			this.privData = null;
		}
	}

	public final void setDoNotDisturb(boolean enable)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setDoNotDisturb[boolean enable]", this);
		try {
			this.tsDevice.setDoNotDisturb(enable, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("setDoNotDisturb[boolean enable]", this);
	}

	public final TerminalConnection pickup(Connection pickConnection,
			Address terminalAddress) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"pickup[Connection pickConnection, Address terminalAddress]",
				this);
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
				TSConnection conn = tsDevice.pickup(tsConn, tsDevice,
						this.privData);
				if (conn != null) {
					TerminalConnection tc = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					TsapiTrace
							.traceExit(
									"pickup[Connection pickConnection, Address terminalAddress]",
									this);
					return tc;
				}

				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsConn == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate connection");
			}

			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			this.privData = null;
		}
	}

	public final TerminalConnection pickup(TerminalConnection pickTermConn,
			Address terminalAddress) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace
				.traceEntry(
						"pickup[TerminalConnection pickTermConn, Address terminalAddress]",
						this);
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
				TSConnection conn = tsDevice.pickup(tsConn, tsDevice,
						this.privData);
				if (conn != null) {
					TerminalConnection tc = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					TsapiTrace
							.traceExit(
									"pickup[TerminalConnection pickTermConn, Address terminalAddress]",
									this);
					return tc;
				}

				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsConn == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection");
			}

			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			this.privData = null;
		}
	}

	public final TerminalConnection pickup(Address pickAddress,
			Address terminalAddress) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"pickup[Address pickAddress, Address terminalAddress]", this);
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
				TSConnection conn = this.tsDevice.pickup(tsDevice1, tsDevice2,
						this.privData);
				if (conn != null) {
					TerminalConnection tc = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					TsapiTrace
							.traceExit(
									"pickup[Address pickAddress, Address terminalAddress]",
									this);
					return tc;
				}

				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			if (tsDevice1 == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate pick address");
			}

			throw new TsapiPlatformException(4, 0,
					"could not locate terminal address");
		} finally {
			this.privData = null;
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
			this.privData = null;
		}
	}

	public final TerminalConnection pickupFromGroup(Address terminalAddress)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("pickupFromGroup[Address terminalAddress]", this);
		try {
			if (!(terminalAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"terminal Address is not an instanceof ITsapiAddress");
			}

			TSDevice tsDevice = ((TsapiAddress) terminalAddress).getTSDevice();
			if (tsDevice != null) {
				TSConnection conn = tsDevice.groupPickup(tsDevice,
						this.privData);
				if (conn != null) {
					TerminalConnection tc = (TerminalConnection) TsapiCreateObject
							.getTsapiObject(conn, false);
					TsapiTrace.traceExit(
							"pickupFromGroup[Address terminalAddress]", this);
					return tc;
				}

				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection to return");
			}

			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			this.privData = null;
		}
	}

	public final void setAgents(Agent[] agents)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setAgents[Agent[] agents]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"deprecated method, not supported by implementation");
		} finally {
			this.privData = null;
		}
	}

	public final Agent addAgent(Address agentAddress, ACDAddress acdAddress,
			int initialState, String agentID, String password)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		TsapiTrace
				.traceEntry(
						"addAgent[Address agentAddress, ACDAddress acdAddress, int initialState, String agentID, String password]",
						this);
		try {
			if (!(agentAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"agent address is not an instanceof ITsapiAddress");
			}

			if (this.tsDevice != ((TsapiAddress) agentAddress).getTSDevice()) {
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

			TSAgent tsAgent = this.tsDevice.addTSAgent(tsACDDevice,
					initialState, 0, 0, agentID, password, this.privData);

			if (tsAgent != null) {
				Agent agent = (Agent) TsapiCreateObject.getTsapiObject(tsAgent,
						false);
				TsapiTrace
						.traceExit(
								"addAgent[Address agentAddress, ACDAddress acdAddress, int initialState, String agentID, String password]",
								this);
				return agent;
			}

			throw new TsapiPlatformException(4, 0,
					"could not locate agent to return");
		} finally {
			this.privData = null;
		}
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

	public final Agent addAgent(LucentAddress agentAddress,
			ACDAddress acdAddress, int initialState, int workMode,
			int reasonCode, String agentID, String password)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		TsapiTrace
				.traceEntry(
						"addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, int reasonCode, String agentID, String password]",
						this);
		try {
			if (agentAddress == null) {
				throw new TsapiInvalidArgumentException(3, 0,
						"agent Address is null");
			}

			if (this.tsDevice != ((TsapiAddress) agentAddress).getTSDevice()) {
				throw new TsapiInvalidArgumentException(3, 0,
						"agent address name must be the same as this terminal's name");
			}

			TSDevice tsACDDevice = null;
			if (acdAddress != null) {
				if (!(acdAddress instanceof LucentAddress)) {
					throw new TsapiInvalidArgumentException(3, 0,
							"acd address is not an instanceof LucentAddress");
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

			TSAgent tsAgent = this.tsDevice.addTSAgent(tsACDDevice,
					initialState, workMode, reasonCode, agentID, password,
					this.privData);

			if (tsAgent != null) {
				Agent agent = (Agent) TsapiCreateObject.getTsapiObject(tsAgent,
						false);
				TsapiTrace
						.traceExit(
								"addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, int reasonCode, String agentID, String password]",
								this);
				return agent;
			}

			throw new TsapiPlatformException(4, 0,
					"could not locate agent to return");
		} finally {
			this.privData = null;
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
				this.tsDevice.removeTSAgent(null, reasonCode);
			} else {
				if (!(agent instanceof ITsapiAgent)) {
					throw new TsapiInvalidArgumentException(3, 0,
							"The given Agent is not an instanceof ITsapiAgent");
				}

				TSAgent tsAgent = ((TsapiAgent) agent).getTSAgent();
				if (tsAgent != null) {
					this.tsDevice.removeTSAgent(tsAgent, reasonCode);
				} else {
					throw new TsapiPlatformException(4, 0,
							"could not locate agent");
				}
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("removeAgent[Agent agent, int reasonCode]", this);
	}

	public final Agent[] getAgents() {
		TsapiTrace.traceEntry("getAgents[]", this);
		try {
			Vector<?> tsAgents = this.tsDevice.getTSAgentsForAgentTerm();
			if (tsAgents == null) {
				TsapiTrace.traceExit("getAgents[]", this);
				return null;
			}
			synchronized (tsAgents) {
				if (tsAgents.size() == 0) {
					TsapiTrace.traceExit("getAgents[]", this);
					return null;
				}
				Agent[] agents = new Agent[tsAgents.size()];
				for (int i = 0; i < tsAgents.size(); i++) {
					agents[i] = ((Agent) TsapiCreateObject.getTsapiObject(
							(TSAgent) tsAgents.elementAt(i), false));
				}
				TsapiTrace.traceExit("getAgents[]", this);
				return agents;
			}
		} finally {
			this.privData = null;
		}
	}

	public final void setPrivateData(Object data) {
		TsapiTrace.traceEntry("setPrivateData[Object data]", this);
		try {
			this.privData = TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data);
		} catch (ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}

		TsapiTrace.traceExit("setPrivateData[Object data]", this);
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		Object priv = this.tsDevice.getAddrPrivateData();
		Object obj = null;
		if (priv != null)
			obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate) priv);
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			Object obj = this.tsDevice.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));
			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (ClassCastException e) {
		}
		throw new TsapiPlatformException(3, 0,
				"data is not a TsapiPrivate object");
	}

	public final int hashCode() {
		return this.tsDevice.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiTerminal)) {
			return this.tsDevice.equals(((TsapiTerminal) obj).tsDevice);
		}

		return false;
	}

	TsapiTerminal(TsapiProvider _provider, String _name, boolean checkValidity)
			throws TsapiInvalidArgumentException {
		TSProviderImpl tsProv = _provider.getTSProviderImpl();
		if (tsProv != null) {
			this.tsDevice = tsProv.createDevice(_name, checkValidity);
			if ((this.tsDevice == null) || (!this.tsDevice.isTerminal())) {
				String info = "";

				if (this.tsDevice == null) {
					info = "; device is null";
				} else if (!this.tsDevice.isTerminal()) {
					info = "; device is not a terminal";
				}
				throw new TsapiPlatformException(4, 0,
						"could not create terminal: " + _name + info);
			}
		} else {
			throw new TsapiPlatformException(4, 0, "could not locate provider");
		}
		this.name = this.tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	TsapiTerminal(TSProviderImpl _provider, String _name)
			throws TsapiInvalidArgumentException {
		this.tsDevice = _provider.createDevice(_name, false);

		if ((this.tsDevice == null) || (!this.tsDevice.isTerminal())) {
			String info = "";

			if (this.tsDevice == null) {
				info = "; device is null";
			} else if (!this.tsDevice.isTerminal()) {
				info = "; device is not a terminal";
			}
			throw new TsapiPlatformException(4, 0,
					"could not create terminal: " + _name + info);
		}

		this.name = this.tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	TsapiTerminal(TsapiProvider _provider, String _name)
			throws TsapiInvalidArgumentException {
		this(_provider, _name, false);
	}

	TsapiTerminal(TSDevice _tsDevice) {
		this.tsDevice = _tsDevice;

		this.name = this.tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiTerminal.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (this.tsDevice != null) {
			this.tsDevice.unreferenced();
			this.tsDevice = null;
		}
		TsapiTrace.traceDestruction(this, TsapiTerminal.class);
	}

	public final TSDevice getTSDevice() {
		TsapiTrace.traceEntry("getTSDevice[]", this);
		TsapiTrace.traceExit("getTSDevice[]", this);
		return this.tsDevice;
	}

	public void addTerminalListener(TerminalListener listener)
			throws ResourceUnavailableException, MethodNotSupportedException {
		TsapiTrace.traceEntry("addTerminalListener[TerminalListener listener]",
				this);
		try {
			TSProviderImpl prov = null;
			prov = this.tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<?> observers = prov.getTerminalMonitorThreads();

			TsapiTerminalMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); i++) {
					obs = (TsapiTerminalMonitor) observers.elementAt(i);
					if (obs.getListener() == listener) {
						found = true;
						break;
					}
				}
				if (!found) {
					obs = new TsapiTerminalMonitor(prov, listener);
				}
			}

			try {
				this.tsDevice.addTerminalMonitor(obs);
			} catch (TsapiResourceUnavailableException e) {
				if ((!found) && (obs != null)) {
					prov.removeTerminalMonitorThread(obs);
				}
				throw e;
			}

		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("addTerminalListener[TerminalListener listener]",
				this);
	}

	public TerminalListener[] getTerminalListeners() {
		TsapiTrace.traceEntry("getTerminalListeners[]", this);
		try {
			Vector<?> tsapiTerminalObservers = this.tsDevice
					.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit("getTerminalListeners[]", this);
				return null;
			}

			Vector<TerminalListener> listeners = new Vector<TerminalListener>();

			for (int i = 0; i < tsapiTerminalObservers.size(); i++) {
				TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getListener() != null)
					listeners.add(obs.getListener());
			}
			TsapiTrace.traceExit("getTerminalListeners[]", this);
			if (listeners.size() > 0) {
				return (TerminalListener[]) listeners
						.toArray(new TerminalListener[1]);
			}

			return null;
		} finally {
			this.privData = null;
		}
	}

	public void removeTerminalListener(TerminalListener listener) {
		TsapiTrace.traceEntry(
				"removeTerminalListener[TerminalListener listener]", this);
		try {
			Vector<TsapiTerminalMonitor> tsapiTerminalObservers = this.tsDevice.getTerminalObservers();

			if ((tsapiTerminalObservers == null)
					|| (tsapiTerminalObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeTerminalListener[TerminalListener listener]",
						this);
				return;
			}
			for (int i = 0; i < tsapiTerminalObservers.size();) {
				TsapiTerminalMonitor obs = (TsapiTerminalMonitor) tsapiTerminalObservers
						.elementAt(i);
				if (obs.getListener() == listener) {
					this.tsDevice.removeTerminalMonitor(obs);
					TsapiTrace
							.traceExit(
									"removeTerminalListener[TerminalListener listener]",
									this);
					return;
				}
				i++;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"removeTerminalListener[TerminalListener listener]", this);
	}

	public void addCallListener(CallListener listener)
			throws ResourceUnavailableException {
		TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
		addTsapiCallEventMonitor(null, listener);
		TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
	}

	public CallListener[] getCallListeners() {
		TsapiTrace.traceEntry("getCallListeners[]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = this.tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getCallListeners[]", this);
				return null;
			}

			CallListener[] listeners = null;
			ArrayList<CallListener> callListenerList = new ArrayList<CallListener>();

			synchronized (tsapiTerminalCallObservers) {
				for (TsapiCallMonitor obs : tsapiTerminalCallObservers) {
					CallListener listener = obs.getListener();
					if (listener != null)
						callListenerList.add(obs.getListener());
				}
			}
			listeners = new CallListener[callListenerList.size()];
			TsapiTrace.traceExit("getCallListeners[]", this);
			return (CallListener[]) callListenerList.toArray(listeners);
		} finally {
			this.privData = null;
		}
	}

	public void removeCallListener(CallListener listener) {
		TsapiTrace
				.traceEntry("removeCallListener[CallListener listener]", this);
		try {
			Vector<TsapiCallMonitor> tsapiTerminalCallObservers = this.tsDevice
					.getTerminalCallObservers();

			if ((tsapiTerminalCallObservers == null)
					|| (tsapiTerminalCallObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeCallListener[CallListener listener]", this);
				return;
			}

			for (int i = 0; i < tsapiTerminalCallObservers.size();) {
				TsapiCallMonitor obs = (TsapiCallMonitor) tsapiTerminalCallObservers
						.elementAt(i);
				if (obs.getListener() == listener) {
					this.tsDevice.removeTerminalCallMonitor(obs);
					TsapiTrace.traceExit(
							"removeCallListener[CallListener listener]", this);
					return;
				}
				i++;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
	}

	public boolean isOnSwitch() {
		TsapiTrace.traceEntry("isOnSwitch[]", this);
		TsapiTrace.traceExit("isOnSwitch[]", this);
		return true;
	}
}