package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.ITsapiCall;
import com.avaya.jtapi.tsapi.ITsapiCallIDPrivate;
import com.avaya.jtapi.tsapi.ITsapiTerminal;
import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentAgent;
import com.avaya.jtapi.tsapi.LucentConsultOptions;
import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.LucentTerminalConnection;
import com.avaya.jtapi.tsapi.LucentV10Call;
import com.avaya.jtapi.tsapi.LucentV7Call;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.LucentConsultationCall;
import com.avaya.jtapi.tsapi.csta1.LucentDirectAgentCall;
import com.avaya.jtapi.tsapi.csta1.LucentMakeCall;
import com.avaya.jtapi.tsapi.csta1.LucentSupervisorAssistCall;
import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV10ConsultationCall;
import com.avaya.jtapi.tsapi.csta1.LucentV6ConsultationCall;
import com.avaya.jtapi.tsapi.csta1.LucentV6DirectAgentCall;
import com.avaya.jtapi.tsapi.csta1.LucentV6MakeCall;
import com.avaya.jtapi.tsapi.csta1.LucentV6SupervisorAssistCall;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.CallListener;
import javax.telephony.CallObserver;
import javax.telephony.Connection;
import javax.telephony.InvalidArgumentException;
import javax.telephony.PlatformException;
import javax.telephony.Provider;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.capabilities.CallCapabilities;
import javax.telephony.privatedata.PrivateData;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public class TsapiCall implements ITsapiCall, PrivateData, ITsapiCallIDPrivate,
		LucentV7Call, LucentV10Call {
	private static Logger log = Logger.getLogger(TsapiCall.class);
	TSCall tsCall;
	CSTAPrivate privData = null;

	public final Connection[] getConnections() {
		TsapiTrace.traceEntry("getConnections[]", this);
		try {
			Vector<?> tsconn = null;
			this.tsCall = this.tsCall.getHandOff();
			tsconn = this.tsCall.getTSConnections();
			if (tsconn == null) {
				TsapiTrace.traceExit("getConnections[]", this);
				return null;
			}

			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					TsapiTrace.traceExit("getConnections[]", this);
					return null;
				}

				Connection[] tsapiConn = new Connection[tsconn.size()];
				for (int i = 0; i < tsconn.size(); i++) {
					tsapiConn[i] = ((Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsconn.elementAt(i),
									true));
				}
				TsapiTrace.traceExit("getConnections[]", this);
				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}

	public final Provider getProvider() {
		TsapiTrace.traceEntry("getProvider[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
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

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			int state = this.tsCall.getState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			this.privData = null;
		}
	}

	public final Connection[] connect(Terminal origterm, Address origaddr,
			String dialedDigits) throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connect[Terminal origterm, Address origaddr, String dialedDigits]",
						this);
		try {
			if (!(origterm instanceof ITsapiTerminal)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Terminal is not an instanceof ITsapiTerminal");
			}

			if (!(origaddr instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Address is not an instanceof ITsapiAddress");
			}

			Vector<?> tsConn = null;
			TSDevice tsDevice = ((TsapiAddress) origaddr).getTSDevice();
			TSDevice tsDevice1 = ((TsapiTerminal) origterm).getTSDevice();
			if ((tsDevice != null) && (tsDevice1 != null)) {
				if (tsDevice.equals(tsDevice1)) {
					this.tsCall = this.tsCall.getHandOff();
					tsConn = this.tsCall.connect(tsDevice, dialedDigits,
							this.privData);
				} else {
					throw new TsapiInvalidArgumentException(3, 0,
							"orig Terminal not associated with orig Address");
				}

			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");
			}

			if (tsConn == null) {
				TsapiTrace
						.traceExit(
								"connect[Terminal origterm, Address origaddr, String dialedDigits]",
								this);
				return null;
			}

			synchronized (tsConn) {
				if (tsConn.size() == 0) {
					TsapiTrace
							.traceExit(
									"connect[Terminal origterm, Address origaddr, String dialedDigits]",
									this);
					return null;
				}

				Connection[] tsapiConn = new Connection[tsConn.size()];
				for (int i = 0; i < tsConn.size(); i++) {
					tsapiConn[i] = ((Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsConn.elementAt(i),
									true));
				}
				TsapiTrace
						.traceExit(
								"connect[Terminal origterm, Address origaddr, String dialedDigits]",
								this);
				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}

	private LucentMakeCall createLucentMakeCall(String destRoute,
			boolean priorityCall, UserToUserInfo userInfo) {
		TsapiTrace
				.traceEntry(
						"createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
		LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (TSProviderImpl != null) {
			if (TSProviderImpl.isLucentV6()) {
				LucentV6MakeCall call = new LucentV6MakeCall(destRoute,
						priorityCall, asn_uui);
				TsapiTrace
						.traceExit(
								"createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
								this);
				return call;
			}

			LucentMakeCall call = new LucentMakeCall(destRoute, priorityCall,
					asn_uui);
			TsapiTrace
					.traceExit(
							"createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return call;
		}

		TsapiTrace
				.traceExit(
						"createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return null;
	}

	public final Connection[] connect(LucentTerminal origterm,
			LucentAddress origaddr, String dialedDigits, boolean priorityCall,
			UserToUserInfo userInfo) throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (origterm == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"orig Terminal is null");
		}

		if (origaddr == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"orig Address is null");
		}

		LucentMakeCall lmc = createLucentMakeCall(null, priorityCall, userInfo);
		this.privData = lmc.makeTsapiPrivate();
		Connection[] conns = connect(origterm, origaddr, dialedDigits);
		TsapiTrace
				.traceExit(
						"connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection[] connectDirectAgent(LucentTerminal origterm,
			LucentAddress origaddr, LucentAgent calledAgent,
			boolean priorityCall, UserToUserInfo userInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (calledAgent == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");
		}

		if (calledAgent.getACDAddress() != null) {
			LucentDirectAgentCall lda = createLucentDirectAgentCall(calledAgent
					.getACDAddress().getName(), priorityCall, userInfo);
			this.privData = lda.makeTsapiPrivate();
			Connection[] conns = connect(origterm, origaddr, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return conns;
		}

		log.info("*****connectDirectAgent: ACDAddress is NULL, using default Skill (ACD)");
		Connection[] conns = connect(origterm, origaddr,
				calledAgent.getAgentID());
		TsapiTrace
				.traceExit(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection[] connectDirectAgent(LucentTerminal origterm,
			LucentAddress origaddr, LucentAgent calledAgent,
			boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (calledAgent == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");
		}

		if (acdaddress != null) {
			LucentDirectAgentCall lda = createLucentDirectAgentCall(
					acdaddress.getName(), priorityCall, userInfo);
			this.privData = lda.makeTsapiPrivate();
			Connection[] conns = connect(origterm, origaddr, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return conns;
		}

		Connection[] conns = connectDirectAgent(origterm, origaddr,
				calledAgent, priorityCall, userInfo);
		TsapiTrace
				.traceExit(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	private LucentSupervisorAssistCall createLucentSupervisorAssistCall(
			String split, UserToUserInfo userInfo) {
		TsapiTrace
				.traceEntry(
						"createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]",
						this);
		TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
		LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (TSProviderImpl != null) {
			if (TSProviderImpl.isLucentV6()) {
				LucentV6SupervisorAssistCall call = new LucentV6SupervisorAssistCall(
						split, asn_uui);
				TsapiTrace
						.traceExit(
								"createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]",
								this);
				return call;
			}

			LucentSupervisorAssistCall call = new LucentSupervisorAssistCall(
					split, asn_uui);
			TsapiTrace
					.traceExit(
							"createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]",
							this);
			return call;
		}

		TsapiTrace
				.traceExit(
						"createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]",
						this);
		return null;
	}

	public final Connection[] connectSupervisorAssist(LucentAgent callingAgent,
			String dialedDigits, UserToUserInfo userInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]",
						this);
		if (callingAgent == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"calling Agent is null");
		}

		LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(
				callingAgent.getACDAddress().getName(), userInfo);
		this.privData = lsa.makeTsapiPrivate();
		Connection[] conns = connect(callingAgent.getAgentTerminal(),
				callingAgent.getAgentAddress(), dialedDigits);
		TsapiTrace
				.traceExit(
						"connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public void addObserver(CallObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addObserver[CallObserver observer]", this);
		try {
			addTsapiCallEventMonitor(observer, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		TsapiTrace.traceExit("addObserver[CallObserver observer]", this);
	}

	private void addTsapiCallEventMonitor(CallObserver observer,
			CallListener listener) throws Exception {
		TsapiTrace
				.traceEntry(
						"addTsapiCallEventMonitor(CallObserver observer, CallListener listener)",
						this);
		if ((observer != null) && (listener != null))
			throw new Exception(
					"Invalid call to add event monitor. At a time either a listener or an observer can be added");
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSProviderImpl prov = this.tsCall.getTSProviderImpl();

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
					if (observer != null)
						obsToUse = new TsapiCallMonitor(prov, observer);
					else if (listener != null)
						obsToUse = new TsapiCallMonitor(prov, listener);
					if (obsToUse == null) {
						throw new TsapiPlatformException(4, 0,
								"could not allocate Monitor wrapper");
					}

				}

			}

			this.tsCall.addCallMonitor(obsToUse);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"addTsapiCallEventMonitor(CallObserver observer, CallListener listener)",
						this);
	}

	public CallObserver[] getObservers() {
		TsapiTrace.traceEntry("getObservers[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			Vector<?> tsapiCallObservers = this.tsCall.getCallObservers();

			if ((tsapiCallObservers == null)
					|| (tsapiCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getObservers[]", this);
				return null;
			}

			ArrayList<CallObserver> observers = new ArrayList<CallObserver>();

			for (Iterator<?> i$ = tsapiCallObservers.iterator(); i$.hasNext();) {
				TsapiCallMonitor obs = (TsapiCallMonitor) i$.next();
				if (obs.getObserver() != null)
					observers.add(obs.getObserver());
			}
			TsapiTrace.traceExit("getObservers[]", this);
			CallObserver[] observerArray = new CallObserver[observers.size()];
			return (CallObserver[]) observers.toArray(observerArray);
		} finally {
			this.privData = null;
		}
	}

	public void removeObserver(CallObserver observer) {
		TsapiTrace.traceEntry("removeObserver[CallObserver observer]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			Vector<TsapiCallMonitor> tsapiCallObservers = this.tsCall.getCallObservers();

			if ((tsapiCallObservers == null)
					|| (tsapiCallObservers.size() == 0)) {
				TsapiTrace.traceExit("removeObserver[CallObserver observer]",
						this);
			} else {
				for (int i = 0; i < tsapiCallObservers.size();) {
					TsapiCallMonitor obs = (TsapiCallMonitor) tsapiCallObservers
							.elementAt(i);
					if (obs.getObserver() == observer) {
						this.tsCall = this.tsCall.getHandOff();
						this.tsCall.removeCallMonitor(obs);
						TsapiTrace.traceExit(
								"removeObserver[CallObserver observer]", this);
						return;
					}
					i++;
				}
			}
		} finally {
			this.privData = null;
		}
	}

	public final CallCapabilities getCapabilities(Terminal terminal,
			Address address) {
		TsapiTrace.traceEntry(
				"getCapabilities[Terminal terminal, Address address]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			CallCapabilities caps = this.tsCall.getTsapiCallCapabilities();
			TsapiTrace
					.traceExit(
							"getCapabilities[Terminal terminal, Address address]",
							this);
			return caps;
		} finally {
			this.privData = null;
		}
	}

	public final CallCapabilities getCallCapabilities(Terminal term,
			Address addr) throws InvalidArgumentException, PlatformException {
		TsapiTrace.traceEntry(
				"getCallCapabilities[Terminal term, Address addr]", this);
		CallCapabilities caps = getCapabilities(null, null);
		TsapiTrace.traceExit(
				"getCallCapabilities[Terminal term, Address addr]", this);
		return caps;
	}

	public final Address getCallingAddress() {
		TsapiTrace.traceEntry("getCallingAddress[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSDevice tsDevice = this.tsCall.getCallingAddress();
			Address addr;
			if (tsDevice != null) {
				addr = (Address) TsapiCreateObject.getTsapiObject(tsDevice,
						true);
				TsapiTrace.traceExit("getCallingAddress[]", this);
				return addr;
			}

			TsapiTrace.traceExit("getCallingAddress[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final Terminal getCallingTerminal() {
		TsapiTrace.traceEntry("getCallingTerminal[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSDevice tsDevice = this.tsCall.getCallingTerminal();
			Terminal term;
			if (tsDevice != null) {
				term = (Terminal) TsapiCreateObject.getTsapiObject(tsDevice,
						false);
				TsapiTrace.traceExit("getCallingTerminal[]", this);
				return term;
			}

			TsapiTrace.traceExit("getCallingTerminal[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final Address getCalledAddress() {
		TsapiTrace.traceEntry("getCalledAddress[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSDevice tsDevice = this.tsCall.getCalledDevice();
			Address addr;
			if (tsDevice != null) {
				addr = (Address) TsapiCreateObject.getTsapiObject(tsDevice,
						true);
				TsapiTrace.traceExit("getCalledAddress[]", this);
				return addr;
			}

			TsapiTrace.traceExit("getCalledAddress[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final Address getLastRedirectedAddress() {
		TsapiTrace.traceEntry("getLastRedirectedAddress[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSDevice tsDevice = this.tsCall.getLastRedirectionDevice();
			Address addr;
			if (tsDevice != null) {
				addr = (Address) TsapiCreateObject.getTsapiObject(tsDevice,
						true);
				TsapiTrace.traceExit("getLastRedirectedAddress[]", this);
				return addr;
			}

			TsapiTrace.traceExit("getLastRedirectedAddress[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final Connection offHook(Address origaddress, Terminal origterminal)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"offHook[Address origaddress, Terminal origterminal]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			this.privData = null;
		}
	}

	public final Connection addParty(String newParty, boolean active)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace
				.traceEntry("addParty[String newParty, boolean active]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSConnection tsConn = this.tsCall.addParty(newParty, active);
			Connection conn;
			if (tsConn != null) {
				conn = (Connection) TsapiCreateObject.getTsapiObject(tsConn,
						true);
				TsapiTrace.traceExit(
						"addParty[String newParty, boolean active]", this);
				return conn;
			}

			TsapiTrace.traceExit("addParty[String newParty, boolean active]",
					this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final Connection addParty(String newParty)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addParty[String newParty]", this);
		Connection conn = addParty(newParty, true);
		TsapiTrace.traceExit("addParty[String newParty]", this);
		return conn;
	}

	public final void drop() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("drop[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			this.tsCall.drop(this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("drop[]", this);
	}

	public final void conference(Call otherCall)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("conference[Call otherCall]", this);
		try {
			if (!(otherCall instanceof LucentV10Call)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"other Call is not an instanceof ITsapiCall");
			}

			TSCall oCall = ((TsapiCall) otherCall).getTSCall();
			if (oCall != null) {
				this.tsCall = this.tsCall.getHandOff();
				this.tsCall.conference(oCall, this.privData);
			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate other call");
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("conference[Call otherCall]", this);
	}

	public final void transfer(Call otherCall)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("transfer[Call otherCall]", this);
		try {
			if (!(otherCall instanceof LucentV10Call)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"other Call is not an instanceof ITsapiCall");
			}

			TSCall oCall = ((TsapiCall) otherCall).getTSCall();
			if (oCall != null) {
				this.tsCall = this.tsCall.getHandOff();
				this.tsCall.transfer(oCall, this.privData);
			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate other call");
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("transfer[Call otherCall]", this);
	}

	public final Connection transfer(String address)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiInvalidPartyException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("transfer[String address]", this);
		try {
			if ((address == null) || (address.equals(""))) {
				throw new TsapiInvalidArgumentException(3, 0,
						"address null or an empty string");
			}

			this.tsCall = this.tsCall.getHandOff();
			TSConnection tsConn = this.tsCall.transfer(address, this.privData);
			Connection conn;
			if (tsConn != null) {
				conn = (Connection) TsapiCreateObject.getTsapiObject(tsConn,
						true);
				TsapiTrace.traceExit("transfer[String address]", this);
				return conn;
			}

			TsapiTrace.traceExit("transfer[String address]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final void setConferenceController(TerminalConnection termconn)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException {
		try {
			TsapiTrace.traceEntry(
					"setConferenceController[TerminalConnection termconn]",
					this);
			if (!(termconn instanceof ITsapiTerminalConnection)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"The given TerminalConnection is not an instanceof ITsapiTerminalConnection");
			}

			TSConnection tsConn = ((TsapiTerminalConnection) termconn)
					.getTSConnection();
			if (tsConn != null) {
				this.tsCall = this.tsCall.getHandOff();
				this.tsCall.setConfController(tsConn);
			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection");
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"setConferenceController[TerminalConnection termconn]", this);
	}

	public final TerminalConnection getConferenceController() {
		TsapiTrace.traceEntry("getConferenceController[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSConnection tsConn = this.tsCall.getConfController();
			TerminalConnection conn;
			if (tsConn != null) {
				conn = (TerminalConnection) TsapiCreateObject.getTsapiObject(
						tsConn, false);
				TsapiTrace.traceExit("getConferenceController[]", this);
				return conn;
			}

			TsapiTrace.traceExit("getConferenceController[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final void setTransferController(TerminalConnection termconn)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"setTransferController[TerminalConnection termconn]", this);
		try {
			if (!(termconn instanceof ITsapiTerminalConnection)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"The given TerminalConnection is not an instanceof ITsapiTerminalConnection");
			}

			TSConnection tsConn = ((TsapiTerminalConnection) termconn)
					.getTSConnection();
			if (tsConn != null) {
				this.tsCall = this.tsCall.getHandOff();
				this.tsCall.setXferController(tsConn);
			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection");
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"setTransferController[TerminalConnection termconn]", this);
	}

	public final TerminalConnection getTransferController() {
		TsapiTrace.traceEntry("getTransferController[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSConnection tsConn = this.tsCall.getXferController();
			TerminalConnection obj;
			if (tsConn != null) {
				obj = (TerminalConnection) TsapiCreateObject.getTsapiObject(
						tsConn, false);
				TsapiTrace.traceExit("getTransferController[]", this);
				return obj;
			}

			TsapiTrace.traceExit("getTransferController[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final void setConferenceEnable(boolean enable)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException {
		TsapiTrace.traceEntry("setConferenceEnable[boolean enable]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			this.tsCall.setConfEnable(enable);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("setConferenceEnable[boolean enable]", this);
	}

	public final boolean getConferenceEnable() {
		TsapiTrace.traceEntry("getConferenceEnable[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			boolean enable = this.tsCall.getConfEnable();
			TsapiTrace.traceExit("getConferenceEnable[]", this);
			return enable;
		} finally {
			this.privData = null;
		}
	}

	public final void setTransferEnable(boolean enable)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException {
		TsapiTrace.traceEntry("setTransferEnable[boolean enable]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			this.tsCall.setXferEnable(enable);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("setTransferEnable[boolean enable]", this);
	}

	public final boolean getTransferEnable() {
		TsapiTrace.traceEntry("getTransferEnable[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			boolean enabled = this.tsCall.getXferEnable();
			TsapiTrace.traceExit("getTransferEnable[]", this);
			return enabled;
		} finally {
			this.privData = null;
		}
	}

	public final Connection[] consult(TerminalConnection termconn,
			String address) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace.traceEntry(
				"consult[TerminalConnection termconn, String address]", this);
		try {
			if (!(termconn instanceof ITsapiTerminalConnection)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"The given TerminalConnection is not an instanceof ITsapiTerminalConnection");
			}

			Vector<?> tsConnVector = null;
			TSConnection tsConn = ((TsapiTerminalConnection) termconn)
					.getTSConnection();
			if (tsConn != null) {
				this.tsCall = this.tsCall.getHandOff();
				tsConnVector = this.tsCall.consult(tsConn, address,
						this.privData);
			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection");
			}

			if (tsConnVector == null) {
				TsapiTrace.traceExit(
						"consult[TerminalConnection termconn, String address]",
						this);
				return null;
			}

			synchronized (tsConnVector) {
				if (tsConnVector.size() == 0) {
					TsapiTrace
							.traceExit(
									"consult[TerminalConnection termconn, String address]",
									this);
					return null;
				}

				Connection[] tsapiConn = new Connection[tsConnVector.size()];
				for (int i = 0; i < tsConnVector.size(); i++) {
					tsapiConn[i] = ((Connection) TsapiCreateObject
							.getTsapiObject(
									(TSConnection) tsConnVector.elementAt(i),
									true));
				}
				TsapiTrace.traceExit(
						"consult[TerminalConnection termconn, String address]",
						this);
				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}

	public final Connection consult(TerminalConnection termconn)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace.traceEntry("consult[TerminalConnection termconn]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			this.privData = null;
		}
	}

	private LucentConsultationCall createLucentConsultationCall(
			String destRoute, boolean priorityCall, UserToUserInfo userInfo,
			short consultOptions) {
		TsapiTrace
				.traceEntry(
						"createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo, short consultOptions]",
						this);
		TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
		LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		LucentConsultationCall call = null;
		if (TSProviderImpl != null) {
			if (TSProviderImpl.isLucentV10()) {
				call = new LucentV10ConsultationCall(destRoute, priorityCall,
						asn_uui, consultOptions);
			} else if (TSProviderImpl.isLucentV6()) {
				call = new LucentV6ConsultationCall(destRoute, priorityCall,
						asn_uui);
			} else {
				call = new LucentConsultationCall(destRoute, priorityCall,
						asn_uui);
			}
		}
		TsapiTrace
				.traceExit(
						"createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo, short consultOptions]",
						this);
		return call;
	}

	public final Connection[] consult(LucentTerminalConnection termconn,
			String address, boolean priorityCall, UserToUserInfo userInfo)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (termconn == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");
		}

		LucentConsultationCall lcc = createLucentConsultationCall(null,
				priorityCall, userInfo, (short) 0);
		this.privData = lcc.makeTsapiPrivate();
		Connection[] conns = consult(termconn, address);
		TsapiTrace
				.traceExit(
						"consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public Connection[] consult(LucentTerminalConnection termconn,
			String address, boolean priorityCall, UserToUserInfo userInfo,
			short consultOptions) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo, short consultOptions]",
						this);
		if (termconn == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");
		}

		if (!this.tsCall.getTSProviderImpl().isLucentV10()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		}

		if (!LucentConsultOptions.validate(consultOptions)) {
			throw new TsapiInvalidArgumentException(
					3,
					0,
					"The consultOptions value: "
							+ consultOptions
							+ " should be from one of the available LucentConsultOptions");
		}

		LucentConsultationCall lcc = createLucentConsultationCall(null,
				priorityCall, userInfo, consultOptions);
		this.privData = lcc.makeTsapiPrivate();
		Connection[] conns = consult(termconn, address);
		TsapiTrace
				.traceExit(
						"consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo, LucentConsultOptions consultOptions]",
						this);
		return conns;
	}

	private LucentDirectAgentCall createLucentDirectAgentCall(String split,
			boolean priorityCall, UserToUserInfo userInfo) {
		TsapiTrace
				.traceEntry(
						"createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
		LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (TSProviderImpl != null) {
			if (TSProviderImpl.isLucentV6()) {
				LucentV6DirectAgentCall call = new LucentV6DirectAgentCall(
						split, priorityCall, asn_uui);
				TsapiTrace
						.traceExit(
								"createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]",
								this);
				return call;
			}

			LucentDirectAgentCall call = new LucentDirectAgentCall(split,
					priorityCall, asn_uui);
			TsapiTrace
					.traceExit(
							"createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return call;
		}

		TsapiTrace
				.traceExit(
						"createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return null;
	}

	public final Connection[] consultDirectAgent(
			LucentTerminalConnection termconn, LucentAgent calledAgent,
			boolean priorityCall, UserToUserInfo userInfo)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (termconn == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");
		}

		if (calledAgent == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");
		}

		if (calledAgent.getACDAddress() != null) {
			LucentDirectAgentCall lda = createLucentDirectAgentCall(calledAgent
					.getACDAddress().getName(), priorityCall, userInfo);
			this.privData = lda.makeTsapiPrivate();
			Connection[] conns = consult(termconn, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return conns;
		}

		log.info("*****consultDirectAgent: ACDAddress is Null, using default skill(ACD)");
		Connection[] conns = consult(termconn, calledAgent.getAgentID());
		TsapiTrace
				.traceExit(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection[] consultDirectAgent(
			LucentTerminalConnection termconn, LucentAgent calledAgent,
			boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]",
						this);
		if (termconn == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");
		}

		if (calledAgent == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");
		}

		if (acdaddress != null) {
			LucentDirectAgentCall lda = createLucentDirectAgentCall(
					acdaddress.getName(), priorityCall, userInfo);
			this.privData = lda.makeTsapiPrivate();
			Connection[] conns = consult(termconn, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]",
							this);
			return conns;
		}

		Connection[] conns = consultDirectAgent(termconn, calledAgent,
				priorityCall, userInfo);
		TsapiTrace
				.traceExit(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]",
						this);
		return conns;
	}

	public final Connection[] consultSupervisorAssist(
			LucentTerminalConnection termconn, ACDAddress split,
			String address, UserToUserInfo userInfo)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]",
						this);
		if (termconn == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");
		}

		if (!(split instanceof LucentAddress)) {
			throw new TsapiInvalidArgumentException(3, 0,
					"The given ACD Address is not an instanceof LucentAddress");
		}

		LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(
				split.getName(), userInfo);
		this.privData = lsa.makeTsapiPrivate();
		Connection[] conns = consult(termconn, address);
		TsapiTrace
				.traceExit(
						"consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection[] connectPredictive(Terminal originatorTerminal,
			Address origAddress, String dialedDigits, int connectionState,
			int maxRings, int answeringTreatment, int answeringEndpointType)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectPredictive[Terminal originatorTerminal, Address origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType]",
						this);
		try {
			if ((originatorTerminal != null)
					&& (!(originatorTerminal instanceof ITsapiTerminal))) {
				throw new TsapiInvalidArgumentException(3, 0,
						"originator Terminal is not an instanceof ITsapiTerminal");
			}

			if (!(origAddress instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"originator Address is not an instanceof ITsapiAddress");
			}

			Vector<?> tsConn = null;

			TSDevice tsDevice = ((TsapiAddress) origAddress).getTSDevice();
			TSDevice tsDevice1 = null;
			if (originatorTerminal != null) {
				tsDevice1 = ((TsapiTerminal) originatorTerminal).getTSDevice();
			}
			if (tsDevice != null) {
				if ((tsDevice1 != null) && (!tsDevice.equals(tsDevice1))) {
					throw new TsapiInvalidArgumentException(3, 0,
							"originator Terminal not associated with originator Address");
				}

				this.tsCall = this.tsCall.getHandOff();
				tsConn = this.tsCall
						.connectPredictive(tsDevice, dialedDigits,
								connectionState, maxRings, answeringTreatment,
								answeringEndpointType, null, false, null,
								this.privData);
			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");
			}

			if (tsConn == null) {
				TsapiTrace
						.traceExit(
								"connectPredictive[Terminal originatorTerminal, Address origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType]",
								this);
				return null;
			}

			synchronized (tsConn) {
				if (tsConn.size() == 0) {
					TsapiTrace
							.traceExit(
									"connectPredictive[Terminal originatorTerminal, Address origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType]",
									this);
					return null;
				}

				Connection[] tsapiConn = new Connection[tsConn.size()];
				for (int i = 0; i < tsConn.size(); i++) {
					tsapiConn[i] = ((Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsConn.elementAt(i),
									true));
				}
				TsapiTrace
						.traceExit(
								"connectPredictive[Terminal originatorTerminal, Address origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType]",
								this);
				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}

	public final Connection[] connectPredictive(
			LucentTerminal originatorTerminal, LucentAddress origAddress,
			String dialedDigits, int connectionState, int maxRings,
			int answeringTreatment, int answeringEndpointType,
			boolean priorityCall, UserToUserInfo userInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectPredictive[LucentTerminal originatorTerminal, LucentAddress origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		try {
			if (origAddress == null) {
				throw new TsapiInvalidArgumentException(3, 0,
						"originator Address is null");
			}

			Vector<?> tsConn = null;
			TSDevice tsDevice = ((TsapiAddress) origAddress).getTSDevice();
			TSDevice tsDevice1 = null;
			if (originatorTerminal != null) {
				tsDevice1 = ((TsapiTerminal) originatorTerminal).getTSDevice();
			}
			if (tsDevice != null) {
				if ((tsDevice1 != null) && (!tsDevice.equals(tsDevice1))) {
					throw new TsapiInvalidArgumentException(3, 0,
							"originator Terminal not associated with orig Address");
				}

				this.tsCall = this.tsCall.getHandOff();
				tsConn = this.tsCall.connectPredictive(tsDevice, dialedDigits,
						connectionState, maxRings, answeringTreatment,
						answeringEndpointType, null, priorityCall, userInfo,
						this.privData);
			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");
			}

			if (tsConn == null) {
				TsapiTrace
						.traceExit(
								"connectPredictive[LucentTerminal originatorTerminal, LucentAddress origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, boolean priorityCall, UserToUserInfo userInfo]",
								this);
				return null;
			}

			synchronized (tsConn) {
				if (tsConn.size() == 0) {
					TsapiTrace
							.traceExit(
									"connectPredictive[LucentTerminal originatorTerminal, LucentAddress origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, boolean priorityCall, UserToUserInfo userInfo]",
									this);
					return null;
				}

				Connection[] tsapiConn = new Connection[tsConn.size()];
				for (int i = 0; i < tsConn.size(); i++) {
					tsapiConn[i] = ((Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsConn.elementAt(i),
									true));
				}
				TsapiTrace
						.traceExit(
								"connectPredictive[LucentTerminal originatorTerminal, LucentAddress origAddress, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, boolean priorityCall, UserToUserInfo userInfo]",
								this);
				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}

	public final void setApplicationData(Object data)
			throws TsapiInvalidStateException {
		TsapiTrace.traceEntry("setApplicationData[Object data]", this);

		if (getProvider().getState() != 16) {
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 0,
					getProvider().getState(), "provider is not in service");
		}

		if (getState() == 34) {
			throw new TsapiInvalidStateException(3, 0,
					TsapiCreateObject.getTsapiObject(this, false), 1,
					getProvider().getState(), "call is in invalid state");
		}

		try {
			this.tsCall.setApplicationData(data);
		} finally {
			this.privData = null;
			TsapiTrace.traceExit("setApplicationData[Object data]", this);
		}
	}

	public final Object getApplicationData() {
		TsapiTrace.traceEntry("getApplicationData[]", this);
		try {
			return this.tsCall.getApplicationData();
		} finally {
			this.privData = null;
			TsapiTrace.traceExit("getApplicationData[]", this);
		}
	}

	public final CallCenterTrunk[] getTrunks() {
		TsapiTrace.traceEntry("getTrunks[]", this);
		try {
			Vector<?> tstrunk = null;
			this.tsCall = this.tsCall.getHandOff();
			tstrunk = this.tsCall.getTSTrunks();
			if (tstrunk == null) {
				TsapiTrace.traceExit("getTrunks[]", this);
				return null;
			}

			synchronized (tstrunk) {
				if (tstrunk.size() == 0) {
					TsapiTrace.traceExit("getTrunks[]", this);
					return null;
				}

				TsapiTrunk[] tsapiTrunk = new TsapiTrunk[tstrunk.size()];
				for (int i = 0; i < tstrunk.size(); i++) {
					tsapiTrunk[i] = ((TsapiTrunk) TsapiCreateObject
							.getTsapiObject(tstrunk.elementAt(i), false));
				}
				TsapiTrace.traceExit("getTrunks[]", this);
				return tsapiTrunk;
			}
		} finally {
			this.privData = null;
		}
	}

	public final CallCenterTrunk getTrunk() {
		TsapiTrace.traceEntry("getTrunk[]", this);
		try {
			CallCenterTrunk[] trks = getTrunks();
			if ((trks == null) || (trks.length == 0)) {
				TsapiTrace.traceExit("getTrunk[]", this);
				return null;
			}
			CallCenterTrunk trunk = trks[0];
			TsapiTrace.traceExit("getTrunk[]", this);
			return trunk;
		} finally {
			this.privData = null;
		}
	}

	public final CallCenterAddress getDistributingAddress() {
		TsapiTrace.traceEntry("getDistributingAddress[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSDevice tsDevice = this.tsCall.getDistributingDevice();
			if (tsDevice == null) {
				TsapiTrace.traceExit("getDistributingAddress[]", this);
				return null;
			}
			CallCenterAddress addr = (CallCenterAddress) TsapiCreateObject
					.getTsapiObject(tsDevice, true);
			TsapiTrace.traceExit("getDistributingAddress[]", this);
			return addr;
		} finally {
			this.privData = null;
		}
	}

	public final CallCenterAddress getDistributingVDNAddress() {
		TsapiTrace.traceEntry("getDistributingVDNAddress[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSDevice tsDevice = this.tsCall.getDistributingVDN();
			if (tsDevice == null) {
				TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
				return null;
			}
			CallCenterAddress addr = (CallCenterAddress) TsapiCreateObject
					.getTsapiObject(tsDevice, true);
			TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
			return addr;
		} finally {
			this.privData = null;
		}
	}

	public final ACDAddress getDeliveringACDAddress() {
		TsapiTrace.traceEntry("getDeliveringACDAddress[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			TSDevice tsDevice = this.tsCall.getDeliveringACDDevice();
			if (tsDevice == null) {
				TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
				return null;
			}
			ACDAddress addr = (ACDAddress) TsapiCreateObject.getTsapiObject(
					tsDevice, true);
			TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
			return addr;
		} finally {
			this.privData = null;
		}
	}

	public final UserToUserInfo getUserToUserInfo() {
		TsapiTrace.traceEntry("getUserToUserInfo[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			UserToUserInfo uui = this.tsCall.getUUI();
			TsapiTrace.traceExit("getUserToUserInfo[]", this);
			return uui;
		} finally {
			this.privData = null;
		}
	}

	public final LookaheadInfo getLookaheadInfo() {
		TsapiTrace.traceEntry("getLookaheadInfo[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			LookaheadInfo lai = this.tsCall.getLAI();
			TsapiTrace.traceExit("getLookaheadInfo[]", this);
			return lai;
		} finally {
			this.privData = null;
		}
	}

	public final UserEnteredCode getUserEnteredCode() {
		TsapiTrace.traceEntry("getUserEnteredCode[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			UserEnteredCode uec = this.tsCall.getUEC();
			TsapiTrace.traceExit("getUserEnteredCode[]", this);
			return uec;
		} finally {
			this.privData = null;
		}
	}

	public final OriginalCallInfo getOriginalCallInfo() {
		TsapiTrace.traceEntry("getOriginalCallInfo[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			OriginalCallInfo oci = this.tsCall.getOCI();
			TsapiTrace.traceExit("getOriginalCallInfo[]", this);
			return oci;
		} finally {
			this.privData = null;
		}
	}

	public final short getReason() {
		TsapiTrace.traceEntry("getReason[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			short reason = this.tsCall.getReason();
			TsapiTrace.traceExit("getReason[]", this);
			return reason;
		} finally {
			this.privData = null;
		}
	}

	public final String getUCID() {
		TsapiTrace.traceEntry("getUCID[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			String ucid = this.tsCall.getUCID();
			TsapiTrace.traceExit("getUCID[]", this);
			return ucid;
		} finally {
			this.privData = null;
		}
	}

	public final int getCallOriginatorType() {
		TsapiTrace.traceEntry("getCallOriginatorType[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			int type = this.tsCall.getCallOriginatorType();
			TsapiTrace.traceExit("getCallOriginatorType[]", this);
			return type;
		} finally {
			this.privData = null;
		}
	}

	public final boolean hasCallOriginatorType() {
		TsapiTrace.traceEntry("hasCallOriginatorType[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			boolean has = this.tsCall.hasCallOriginatorType();
			TsapiTrace.traceExit("hasCallOriginatorType[]", this);
			return has;
		} finally {
			this.privData = null;
		}
	}

	public final boolean canSetBillRate() {
		TsapiTrace.traceEntry("canSetBillRate[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			boolean can = this.tsCall.canSetBillRate();
			TsapiTrace.traceExit("canSetBillRate[]", this);
			return can;
		} finally {
			this.privData = null;
		}
	}

	public final void setBillRate(short billType, float billRate)
			throws TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("setBillRate[short billType, float billRate]",
				this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			this.tsCall.setBillRate(billType, billRate);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("setBillRate[short billType, float billRate]",
				this);
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
		this.tsCall = this.tsCall.getHandOff();
		Object obj = null;
		Object tempObj = this.tsCall.getPrivateData();
		if (tempObj != null) {
			obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate) tempObj);
		}
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			Object obj = this.tsCall.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));
			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (ClassCastException e) {
		}
		throw new TsapiPlatformException(3, 0,
				"data is not a TsapiPrivate object");
	}

	public final int getTsapiCallID() {
		TsapiTrace.traceEntry("getTsapiCallID[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			int id = this.tsCall.getCallID();
			TsapiTrace.traceExit("getTsapiCallID[]", this);
			return id;
		} finally {
			this.privData = null;
		}
	}

	public void addCallListener(CallListener listener)
			throws ResourceUnavailableException {
		TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
		try {
			addTsapiCallEventMonitor(null, listener);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
	}

	public CallListener[] getCallListeners() {
		TsapiTrace.traceEntry("getCallListeners[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			Vector<TsapiCallMonitor> tsapiCallObservers = this.tsCall.getCallObservers();

			if ((tsapiCallObservers == null)
					|| (tsapiCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getCallListeners[]", this);
				return null;
			}
			ArrayList<CallListener> callListeners = new ArrayList<CallListener>();

			synchronized (tsapiCallObservers) {
				for (TsapiCallMonitor obs : tsapiCallObservers) {
					CallListener listener = obs.getListener();
					if (listener != null)
						callListeners.add(listener);
				}
			}
			CallListener[] callListener = new CallListener[callListeners.size()];
			TsapiTrace.traceExit("getCallListeners[]", this);
			return (CallListener[]) callListeners.toArray(callListener);
		} finally {
			this.privData = null;
		}
	}

	public void removeCallListener(CallListener listener) {
		TsapiTrace
				.traceEntry("removeCallListener[CallListener listener]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			Vector<TsapiCallMonitor> tsapiCallObservers = this.tsCall.getCallObservers();

			if ((tsapiCallObservers == null)
					|| (tsapiCallObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeCallListener[CallListener listener]", this);
			} else {
				for (TsapiCallMonitor obs : tsapiCallObservers)
					if (obs.getListener() == listener) {
						this.tsCall = this.tsCall.getHandOff();
						this.tsCall.removeCallMonitor(obs);
						TsapiTrace.traceExit(
								"removeCallListener[CallListener listener]",
								this);
						return;
					}
			}
		} finally {
			this.privData = null;
		}
	}

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		TsapiTrace.traceEntry("getDeviceHistory[]", this);
		try {
			this.tsCall = this.tsCall.getHandOff();
			V7DeviceHistoryEntry[] history = this.tsCall.getDeviceHistory();
			TsapiTrace.traceExit("getDeviceHistory[]", this);
			return history;
		} finally {
			this.privData = null;
		}
	}

	public final int hashCode() {
		this.tsCall = this.tsCall.getHandOff();

		TSProviderImpl TSProviderImpl = this.tsCall.getTSProviderImpl();
		return TSProviderImpl.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiCall)) {
			this.tsCall = this.tsCall.getHandOff();
			return this.tsCall.equals(((TsapiCall) obj).getTSCall());
		}

		return false;
	}

	TsapiCall(TsapiProvider _provider) {
		this(_provider, 0);
	}

	TsapiCall(TsapiProvider _provider, CSTAConnectionID connID) {
		this(_provider, connID.getCallID());
	}

	TsapiCall(TsapiProvider _provider, int callID) {
		TSProviderImpl tsProv = _provider.getTSProviderImpl();
		if (tsProv != null) {
			this.tsCall = tsProv.createTSCall(callID);
			if (this.tsCall == null) {
				throw new TsapiPlatformException(4, 0, "could not create call");
			}
		} else {
			throw new TsapiPlatformException(4, 0, "could not locate provider");
		}
		this.tsCall.referenced();
		TsapiTrace.traceConstruction(this, TsapiCall.class);
	}

	TsapiCall(TSProviderImpl _provider, CSTAConnectionID connID) {
		this.tsCall = _provider.createTSCall(connID.getCallID());
		if (this.tsCall == null) {
			throw new TsapiPlatformException(4, 0, "could not create call");
		}

		this.tsCall.referenced();
		TsapiTrace.traceConstruction(this, TsapiCall.class);
	}

	TsapiCall(TSCall _tscall) {
		_tscall = _tscall.getHandOff();
		this.tsCall = _tscall;
		this.tsCall.referenced();
		TsapiTrace.traceConstruction(this, TsapiCall.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (this.tsCall != null) {
			this.tsCall.unreferenced();
			this.tsCall = null;
		}
		TsapiTrace.traceDestruction(this, TsapiCall.class);
	}

	public final TSCall getTSCall() {
		TsapiTrace.traceEntry("getTSCall[]", this);
		this.tsCall = this.tsCall.getHandOff();
		TsapiTrace.traceExit("getTSCall[]", this);
		return this.tsCall;
	}

	public final Connection fastConnect(Terminal origterm, Address origaddr,
			String dialedDigits, boolean priorityCall, UserToUserInfo userInfo,
			String destRoute) throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"fastConnect[Terminal origterm, Address origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo, String destRoute]",
						this);
		try {
			if (!(origterm instanceof ITsapiTerminal)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Terminal is not an instanceof ITsapiTerminal");
			}

			if (!(origaddr instanceof ITsapiAddress)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Address is not an instanceof ITsapiAddress");
			}

			Vector<?> tsConn = null;
			TSDevice tsDevice = ((TsapiAddress) origaddr).getTSDevice();
			TSDevice tsDevice1 = ((TsapiTerminal) origterm).getTSDevice();
			LucentMakeCall lmc;
			if ((tsDevice != null) && (tsDevice1 != null)) {
				if (tsDevice.equals(tsDevice1)) {
					if ((destRoute == null) && (!priorityCall)
							&& (userInfo == null)) {
						this.privData = null;
					} else {
						lmc = createLucentMakeCall(destRoute, priorityCall,
								userInfo);
						this.privData = lmc.makeTsapiPrivate();
					}

					this.tsCall = this.tsCall.getHandOff();
					tsConn = this.tsCall.fastConnect(tsDevice, dialedDigits,
							this.privData);
				} else {
					throw new TsapiInvalidArgumentException(3, 0,
							"orig Terminal not associated with orig Address");
				}

			} else {
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");
			}

			if (tsConn == null) {
				TsapiTrace
						.traceExit(
								"fastConnect[Terminal origterm, Address origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo, String destRoute]",
								this);
				return null;
			}

			synchronized (tsConn) {
				if (tsConn.size() == 0) {
					TsapiTrace
							.traceExit(
									"fastConnect[Terminal origterm, Address origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo, String destRoute]",
									this);
					return null;
				}

				Connection tsapiConn = (Connection) TsapiCreateObject
						.getTsapiObject((TSConnection) tsConn.elementAt(0),
								true);

				TsapiTrace
						.traceExit(
								"fastConnect[Terminal origterm, Address origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo, String destRoute]",
								this);

				return tsapiConn;
			}
		} finally {
			this.privData = null;
		}
	}
}