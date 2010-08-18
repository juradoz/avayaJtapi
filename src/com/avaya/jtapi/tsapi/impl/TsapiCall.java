package com.avaya.jtapi.tsapi.impl;

import java.util.ArrayList;
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

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.ITsapiCall;
import com.avaya.jtapi.tsapi.ITsapiCallIDPrivate;
import com.avaya.jtapi.tsapi.ITsapiTerminal;
import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentAgent;
import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.LucentTerminalConnection;
import com.avaya.jtapi.tsapi.LucentV7Call;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.TSProvider;
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
import com.avaya.jtapi.tsapi.csta1.LucentV6ConsultationCall;
import com.avaya.jtapi.tsapi.csta1.LucentV6DirectAgentCall;
import com.avaya.jtapi.tsapi.csta1.LucentV6MakeCall;
import com.avaya.jtapi.tsapi.csta1.LucentV6SupervisorAssistCall;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

@SuppressWarnings("deprecation")
public class TsapiCall implements ITsapiCall, PrivateData, ITsapiCallIDPrivate,
		LucentV7Call {
	private static Logger log = Logger.getLogger(TsapiCall.class);
	TSCall tsCall;
	CSTAPrivate privData = null;

	TsapiCall(final TsapiProvider _provider) {
		this(_provider, 0);
	}

	TsapiCall(final TsapiProvider _provider, final CSTAConnectionID connID) {
		this(_provider, connID.getCallID());
	}

	TsapiCall(final TsapiProvider _provider, final int callID) {
		final TSProviderImpl tsProv = _provider.getTSProviderImpl();
		if (tsProv != null) {
			tsCall = tsProv.createTSCall(callID);
			if (tsCall == null)
				throw new TsapiPlatformException(4, 0, "could not create call");
		} else
			throw new TsapiPlatformException(4, 0, "could not locate provider");
		tsCall.referenced();
		TsapiTrace.traceConstruction(this, TsapiCall.class);
	}

	TsapiCall(TSCall _tscall) {
		_tscall = _tscall.getHandOff();
		tsCall = _tscall;
		tsCall.referenced();
		TsapiTrace.traceConstruction(this, TsapiCall.class);
	}

	TsapiCall(final TSProviderImpl _provider, final CSTAConnectionID connID) {
		tsCall = _provider.createTSCall(connID.getCallID());
		if (tsCall == null)
			throw new TsapiPlatformException(4, 0, "could not create call");

		tsCall.referenced();
		TsapiTrace.traceConstruction(this, TsapiCall.class);
	}

	public void addCallListener(final CallListener listener)
			throws ResourceUnavailableException {
		TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
		try {
			addTsapiCallEventMonitor(null, listener);
		} catch (final Exception e) {
			TsapiCall.log.error(e.getMessage(), e);
		}
		TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
	}

	public void addObserver(final CallObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addObserver[CallObserver observer]", this);
		try {
			addTsapiCallEventMonitor(observer, null);
		} catch (final Exception e) {
			TsapiCall.log.error(e.getMessage(), e);
		}
		TsapiTrace.traceExit("addObserver[CallObserver observer]", this);
	}

	public final Connection addParty(final String newParty)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addParty[String newParty]", this);
		final Connection conn = addParty(newParty, true);
		TsapiTrace.traceExit("addParty[String newParty]", this);
		return conn;
	}

	public final Connection addParty(final String newParty, final boolean active)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace
				.traceEntry("addParty[String newParty, boolean active]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSConnection tsConn = tsCall.addParty(newParty, active);
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
			privData = null;
		}
	}

	private void addTsapiCallEventMonitor(final CallObserver observer,
			final CallListener listener) throws Exception {
		TsapiTrace
				.traceEntry(
						"addTsapiCallEventMonitor(CallObserver observer, CallListener listener)",
						this);
		if (observer != null && listener != null)
			throw new Exception(
					"Invalid call to add event monitor. At a time either a listener or an observer can be added");
		try {
			tsCall = tsCall.getHandOff();
			final TSProviderImpl prov = tsCall.getTSProviderImpl();

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

			tsCall.addCallMonitor(obsToUse);
		} finally {
			privData = null;
		}
		TsapiTrace
				.traceExit(
						"addTsapiCallEventMonitor(CallObserver observer, CallListener listener)",
						this);
	}

	public final boolean canSetBillRate() {
		TsapiTrace.traceEntry("canSetBillRate[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final boolean can = tsCall.canSetBillRate();
			TsapiTrace.traceExit("canSetBillRate[]", this);
			return can;
		} finally {
			privData = null;
		}
	}

	public final void conference(final Call otherCall)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("conference[Call otherCall]", this);
		try {
			if (!(otherCall instanceof LucentV7Call))
				throw new TsapiInvalidArgumentException(3, 0,
						"other Call is not an instanceof ITsapiCall");

			final TSCall oCall = ((TsapiCall) otherCall).getTSCall();
			if (oCall != null) {
				tsCall = tsCall.getHandOff();
				tsCall.conference(oCall, privData);
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate other call");
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("conference[Call otherCall]", this);
	}

	public final Connection[] connect(final LucentTerminal origterm,
			final LucentAddress origaddr, final String dialedDigits,
			final boolean priorityCall, final UserToUserInfo userInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (origterm == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"orig Terminal is null");

		if (origaddr == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"orig Address is null");

		final LucentMakeCall lmc = createLucentMakeCall(null, priorityCall,
				userInfo);
		privData = lmc.makeTsapiPrivate();
		final Connection[] conns = connect(origterm, origaddr, dialedDigits);
		TsapiTrace
				.traceExit(
						"connect[LucentTerminal origterm, LucentAddress origaddr, String dialedDigits, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	// ERROR //
	public final Connection[] connect(final Terminal origterm,
			final Address origaddr, final String dialedDigits)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		try {
			if (!(origterm instanceof ITsapiTerminal))
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Terminal is not an instanceof ITsapiTerminal");
			if (!(origaddr instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Address is not an instanceof ITsapiAddress");

			Vector<TSConnection> tsConn = null;
			final TSDevice tsDevice = ((TsapiAddress) origaddr).getTSDevice();
			final TSDevice tsDevice1 = ((TsapiTerminal) origterm).getTSDevice();
			if (tsDevice != null && tsDevice1 != null) {
				if (tsDevice.equals(tsDevice1)) {
					tsCall = tsCall.getHandOff();
					tsConn = tsCall.connect(tsDevice, dialedDigits, privData);
				} else
					throw new TsapiInvalidArgumentException(3, 0,
							"orig Terminal not associated with orig Address");
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");
			if (tsConn == null) {

				privData = null;
				return null;
			}
			synchronized (tsConn) {
				if (tsConn.size() == 0) {
					privData = null;
					return null;
				}
				final Connection[] tsapiConn = new Connection[tsConn.size()];
				for (int i = 0; i < tsConn.size(); ++i)
					tsapiConn[i] = (Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsConn.elementAt(i),
									true);
				privData = null;
				return tsapiConn;
			}
		} finally {
			privData = null;
		}
	}

	public final Connection[] connectDirectAgent(final LucentTerminal origterm,
			final LucentAddress origaddr, final LucentAgent calledAgent,
			final boolean priorityCall, final UserToUserInfo userInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (calledAgent == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");

		if (calledAgent.getACDAddress() != null) {
			final LucentDirectAgentCall lda = createLucentDirectAgentCall(
					calledAgent.getACDAddress().getName(), priorityCall,
					userInfo);
			privData = lda.makeTsapiPrivate();
			final Connection[] conns = connect(origterm, origaddr, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return conns;
		}

		TsapiCall.log
				.info("*****connectDirectAgent: ACDAddress is NULL, using default Skill (ACD)");
		final Connection[] conns = connect(origterm, origaddr, calledAgent
				.getAgentID());
		TsapiTrace
				.traceExit(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection[] connectDirectAgent(final LucentTerminal origterm,
			final LucentAddress origaddr, final LucentAgent calledAgent,
			final boolean priorityCall, final UserToUserInfo userInfo,
			final ACDAddress acdaddress)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (calledAgent == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");

		if (acdaddress != null) {
			final LucentDirectAgentCall lda = createLucentDirectAgentCall(
					acdaddress.getName(), priorityCall, userInfo);
			privData = lda.makeTsapiPrivate();
			final Connection[] conns = connect(origterm, origaddr, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return conns;
		}

		final Connection[] conns = connectDirectAgent(origterm, origaddr,
				calledAgent, priorityCall, userInfo);
		TsapiTrace
				.traceExit(
						"connectDirectAgent[LucentTerminal origterm, LucentAddress origaddr, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	// ERROR //
	public final Connection[] connectPredictive(
			final LucentTerminal originatorTerminal,
			final LucentAddress origAddress, final String dialedDigits,
			final int connectionState, final int maxRings,
			final int answeringTreatment, final int answeringEndpointType,
			final boolean priorityCall, final UserToUserInfo userInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		try {
			if (origAddress == null)
				throw new TsapiInvalidArgumentException(3, 0,
						"originator Address is null");

			Vector<TSConnection> tsConn = null;
			final TSDevice tsDevice = ((TsapiAddress) origAddress)
					.getTSDevice();
			TSDevice tsDevice1 = null;
			if (originatorTerminal != null)
				tsDevice1 = ((TsapiTerminal) originatorTerminal).getTSDevice();
			if (tsDevice != null) {
				if (tsDevice1 != null && !tsDevice.equals(tsDevice1))
					throw new TsapiInvalidArgumentException(3, 0,
							"originator Terminal not associated with orig Address");

				tsCall = tsCall.getHandOff();
				tsConn = tsCall.connectPredictive(tsDevice, dialedDigits,
						connectionState, maxRings, answeringTreatment,
						answeringEndpointType, null, priorityCall, userInfo,
						privData);
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");
			if (tsConn == null) {
				privData = null;
				return null;
			}
			synchronized (tsConn) {
				if (tsConn.size() == 0) {
					privData = null;
					return null;
				}
				final Connection[] tsapiConn = new Connection[tsConn.size()];
				for (int i = 0; i < tsConn.size(); ++i)
					tsapiConn[i] = (Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsConn.elementAt(i),
									true);

				privData = null;
				return tsapiConn;
			}
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Connection[] connectPredictive(
			final Terminal originatorTerminal, final Address origAddress,
			final String dialedDigits, final int connectionState,
			final int maxRings, final int answeringTreatment,
			final int answeringEndpointType)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		try {
			if (originatorTerminal != null
					&& !(originatorTerminal instanceof ITsapiTerminal))
				throw new TsapiInvalidArgumentException(3, 0,
						"originator Terminal is not an instanceof ITsapiTerminal");

			if (!(origAddress instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"originator Address is not an instanceof ITsapiAddress");

			Vector<TSConnection> tsConn = null;

			final TSDevice tsDevice = ((TsapiAddress) origAddress)
					.getTSDevice();
			TSDevice tsDevice1 = null;
			if (originatorTerminal != null)
				tsDevice1 = ((TsapiTerminal) originatorTerminal).getTSDevice();
			if (tsDevice != null) {
				if (tsDevice1 != null && !tsDevice.equals(tsDevice1))
					throw new TsapiInvalidArgumentException(3, 0,
							"originator Terminal not associated with originator Address");

				tsCall = tsCall.getHandOff();
				tsConn = tsCall.connectPredictive(tsDevice, dialedDigits,
						connectionState, maxRings, answeringTreatment,
						answeringEndpointType, null, false, null, privData);
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");
			if (tsConn == null) {
				privData = null;
				return null;
			}
			synchronized (tsConn) {
				if (tsConn.size() == 0) {
					privData = null;
					return null;
				}
				final Connection[] tsapiConn = new Connection[tsConn.size()];
				for (int i = 0; i < tsConn.size(); ++i)
					tsapiConn[i] = (Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsConn.elementAt(i),
									true);
				privData = null;
				return tsapiConn;
			}
		} finally {
			privData = null;
		}
	}

	public final Connection[] connectSupervisorAssist(
			final LucentAgent callingAgent, final String dialedDigits,
			final UserToUserInfo userInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]",
						this);
		if (callingAgent == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"calling Agent is null");

		final LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(
				callingAgent.getACDAddress().getName(), userInfo);
		privData = lsa.makeTsapiPrivate();
		final Connection[] conns = connect(callingAgent.getAgentTerminal(),
				callingAgent.getAgentAddress(), dialedDigits);
		TsapiTrace
				.traceExit(
						"connectSupervisorAssist[LucentAgent callingAgent, String dialedDigits, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection[] consult(final LucentTerminalConnection termconn,
			final String address, final boolean priorityCall,
			final UserToUserInfo userInfo) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (termconn == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");

		final LucentConsultationCall lcc = createLucentConsultationCall(null,
				priorityCall, userInfo);
		privData = lcc.makeTsapiPrivate();
		final Connection[] conns = consult(termconn, address);
		TsapiTrace
				.traceExit(
						"consult[LucentTerminalConnection termconn, String address, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection consult(final TerminalConnection termconn)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace.traceEntry("consult[TerminalConnection termconn]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Connection[] consult(final TerminalConnection termconn,
			final String address) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		try {
			if (!(termconn instanceof ITsapiTerminalConnection))
				throw new TsapiInvalidArgumentException(3, 0,
						"The given TerminalConnection is not an instanceof ITsapiTerminalConnection");

			Vector<TSConnection> tsConnVector = null;
			final TSConnection tsConn = ((TsapiTerminalConnection) termconn)
					.getTSConnection();
			if (tsConn != null) {
				tsCall = tsCall.getHandOff();
				tsConnVector = tsCall.consult(tsConn, address, privData);
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection");
			if (tsConnVector == null) {
				privData = null;
				return null;
			}
			synchronized (tsConnVector) {
				if (tsConnVector.size() == 0) {

					privData = null;
					return null;
				}
				final Connection[] tsapiConn = new Connection[tsConnVector
						.size()];
				for (int i = 0; i < tsConnVector.size(); ++i)
					tsapiConn[i] = (Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsConnVector
									.elementAt(i), true);

				privData = null;
				return tsapiConn;
			}
		} finally {
			privData = null;
		}
	}

	public final Connection[] consultDirectAgent(
			final LucentTerminalConnection termconn,
			final LucentAgent calledAgent, final boolean priorityCall,
			final UserToUserInfo userInfo) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		if (termconn == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");

		if (calledAgent == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");

		if (calledAgent.getACDAddress() != null) {
			final LucentDirectAgentCall lda = createLucentDirectAgentCall(
					calledAgent.getACDAddress().getName(), priorityCall,
					userInfo);
			privData = lda.makeTsapiPrivate();
			final Connection[] conns = consult(termconn, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
							this);
			return conns;
		}

		TsapiCall.log
				.info("*****consultDirectAgent: ACDAddress is Null, using default skill(ACD)");
		final Connection[] conns = consult(termconn, calledAgent.getAgentID());
		TsapiTrace
				.traceExit(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	public final Connection[] consultDirectAgent(
			final LucentTerminalConnection termconn,
			final LucentAgent calledAgent, final boolean priorityCall,
			final UserToUserInfo userInfo, final ACDAddress acdaddress)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]",
						this);
		if (termconn == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");

		if (calledAgent == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"called Agent is null");

		if (acdaddress != null) {
			final LucentDirectAgentCall lda = createLucentDirectAgentCall(
					acdaddress.getName(), priorityCall, userInfo);
			privData = lda.makeTsapiPrivate();
			final Connection[] conns = consult(termconn, calledAgent
					.getAgentAddress().getName());
			TsapiTrace
					.traceExit(
							"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]",
							this);
			return conns;
		}

		final Connection[] conns = consultDirectAgent(termconn, calledAgent,
				priorityCall, userInfo);
		TsapiTrace
				.traceExit(
						"consultDirectAgent[LucentTerminalConnection termconn, LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo, ACDAddress acdaddress]",
						this);
		return conns;
	}

	public final Connection[] consultSupervisorAssist(
			final LucentTerminalConnection termconn, final ACDAddress split,
			final String address, final UserToUserInfo userInfo)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException {
		TsapiTrace
				.traceEntry(
						"consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]",
						this);
		if (termconn == null)
			throw new TsapiInvalidArgumentException(3, 0,
					"The given TerminalConnection is null");

		if (!(split instanceof LucentAddress))
			throw new TsapiInvalidArgumentException(3, 0,
					"The given ACD Address is not an instanceof LucentAddress");

		final LucentSupervisorAssistCall lsa = createLucentSupervisorAssistCall(
				split.getName(), userInfo);
		privData = lsa.makeTsapiPrivate();
		final Connection[] conns = consult(termconn, address);
		TsapiTrace
				.traceExit(
						"consultSupervisorAssist[LucentTerminalConnection termconn, ACDAddress split, String address, UserToUserInfo userInfo]",
						this);
		return conns;
	}

	private LucentConsultationCall createLucentConsultationCall(
			final String destRoute, final boolean priorityCall,
			final UserToUserInfo userInfo) {
		TsapiTrace
				.traceEntry(
						"createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		final TSProviderImpl TSProviderImpl = tsCall.getTSProviderImpl();
		final LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		LucentConsultationCall call = null;
		if (TSProviderImpl != null)
			if (TSProviderImpl.isLucentV6())
				call = new LucentV6ConsultationCall(destRoute, priorityCall,
						asn_uui);
			else
				call = new LucentConsultationCall(destRoute, priorityCall,
						asn_uui);
		TsapiTrace
				.traceExit(
						"createLucentConsultationCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		return call;
	}

	private LucentDirectAgentCall createLucentDirectAgentCall(
			final String split, final boolean priorityCall,
			final UserToUserInfo userInfo) {
		TsapiTrace
				.traceEntry(
						"createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		final TSProviderImpl TSProviderImpl = tsCall.getTSProviderImpl();
		final LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (TSProviderImpl != null) {
			if (TSProviderImpl.isLucentV6()) {
				final LucentV6DirectAgentCall call = new LucentV6DirectAgentCall(
						split, priorityCall, asn_uui);
				TsapiTrace
						.traceExit(
								"createLucentDirectAgentCall[String split, boolean priorityCall, UserToUserInfo userInfo]",
								this);
				return call;
			}

			final LucentDirectAgentCall call = new LucentDirectAgentCall(split,
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

	private LucentMakeCall createLucentMakeCall(final String destRoute,
			final boolean priorityCall, final UserToUserInfo userInfo) {
		TsapiTrace
				.traceEntry(
						"createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		final TSProviderImpl TSProviderImpl = tsCall.getTSProviderImpl();
		final LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (TSProviderImpl != null) {
			if (TSProviderImpl.isLucentV6()) {
				final LucentV6MakeCall call = new LucentV6MakeCall(destRoute,
						priorityCall, asn_uui);
				TsapiTrace
						.traceExit(
								"createLucentMakeCall[String destRoute, boolean priorityCall, UserToUserInfo userInfo]",
								this);
				return call;
			}

			final LucentMakeCall call = new LucentMakeCall(destRoute,
					priorityCall, asn_uui);
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

	private LucentSupervisorAssistCall createLucentSupervisorAssistCall(
			final String split, final UserToUserInfo userInfo) {
		TsapiTrace
				.traceEntry(
						"createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]",
						this);
		final TSProviderImpl TSProviderImpl = tsCall.getTSProviderImpl();
		final LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (TSProviderImpl != null) {
			if (TSProviderImpl.isLucentV6()) {
				final LucentV6SupervisorAssistCall call = new LucentV6SupervisorAssistCall(
						split, asn_uui);
				TsapiTrace
						.traceExit(
								"createLucentSupervisorAssistCall[String split, UserToUserInfo userInfo]",
								this);
				return call;
			}

			final LucentSupervisorAssistCall call = new LucentSupervisorAssistCall(
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

	public final void drop() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("drop[]", this);
		try {
			tsCall = tsCall.getHandOff();
			tsCall.drop(privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("drop[]", this);
	}

	public boolean equals(final Object obj) {
		if (obj instanceof TsapiCall) {
			tsCall = tsCall.getHandOff();
			return tsCall.equals(((TsapiCall) obj).getTSCall());
		}

		return false;
	}

	// ERROR //
	public final Connection fastConnect(final Terminal origterm,
			final Address origaddr, final String dialedDigits,
			final boolean priorityCall, final UserToUserInfo userInfo,
			final String destRoute) throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException {
		try {
			if (!(origterm instanceof ITsapiTerminal))
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Terminal is not an instanceof ITsapiTerminal");

			if (!(origaddr instanceof ITsapiAddress))
				throw new TsapiInvalidArgumentException(3, 0,
						"orig Address is not an instanceof ITsapiAddress");

			Vector<TSConnection> tsConn = null;
			final TSDevice tsDevice = ((TsapiAddress) origaddr).getTSDevice();
			final TSDevice tsDevice1 = ((TsapiTerminal) origterm).getTSDevice();
			LucentMakeCall lmc;
			if (tsDevice != null && tsDevice1 != null) {
				if (tsDevice.equals(tsDevice1)) {
					if (destRoute == null && !priorityCall && userInfo == null)
						privData = null;
					else {
						lmc = createLucentMakeCall(destRoute, priorityCall,
								userInfo);
						privData = lmc.makeTsapiPrivate();
					}

					tsCall = tsCall.getHandOff();
					tsConn = tsCall.fastConnect(tsDevice, dialedDigits,
							privData);
				} else
					throw new TsapiInvalidArgumentException(3, 0,
							"orig Terminal not associated with orig Address");
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate orig address");

			if (tsConn == null) {
				lmc = null;

				privData = null;
				return null;
			}
			synchronized (tsConn) {
				if (tsConn.size() == 0) {

					privData = null;
					return null;
				}
				final Connection tsapiConn = (Connection) TsapiCreateObject
						.getTsapiObject((TSConnection) tsConn.elementAt(0),
								true);

				final Connection localConnection1 = tsapiConn;

				privData = null;
				return localConnection1;
			}
		} finally {
			privData = null;
		}
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (tsCall != null) {
			tsCall.unreferenced();
			tsCall = null;
		}
		TsapiTrace.traceDestruction(this, TsapiCall.class);
	}

	public final Object getApplicationData()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getApplicationData[]", this);
		try {
			throw new TsapiMethodNotSupportedException(0, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final CallCapabilities getCallCapabilities(final Terminal term,
			final Address addr) throws InvalidArgumentException,
			PlatformException {
		TsapiTrace.traceEntry(
				"getCallCapabilities[Terminal term, Address addr]", this);
		final CallCapabilities caps = getCapabilities(null, null);
		TsapiTrace.traceExit(
				"getCallCapabilities[Terminal term, Address addr]", this);
		return caps;
	}

	public final Address getCalledAddress() {
		TsapiTrace.traceEntry("getCalledAddress[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSDevice tsDevice = tsCall.getCalledDevice();
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
			privData = null;
		}
	}

	public final Address getCallingAddress() {
		TsapiTrace.traceEntry("getCallingAddress[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSDevice tsDevice = tsCall.getCallingAddress();
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
			privData = null;
		}
	}

	public final Terminal getCallingTerminal() {
		TsapiTrace.traceEntry("getCallingTerminal[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSDevice tsDevice = tsCall.getCallingTerminal();
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
			privData = null;
		}
	}

	public CallListener[] getCallListeners() {
		TsapiTrace.traceEntry("getCallListeners[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final Vector<TsapiCallMonitor> tsapiCallObservers = tsCall
					.getCallObservers();

			if (tsapiCallObservers == null || tsapiCallObservers.size() == 0) {
				TsapiTrace.traceExit("getCallListeners[]", this);
				return null;
			}
			final ArrayList<CallListener> callListeners = new ArrayList<CallListener>();

			synchronized (tsapiCallObservers) {
				for (final Object obs : tsapiCallObservers) {
					final CallListener listener = ((TsapiCallMonitor) obs)
							.getListener();
					if (listener != null)
						callListeners.add(listener);
				}
			}
			final CallListener[] callListener = new CallListener[callListeners
					.size()];
			TsapiTrace.traceExit("getCallListeners[]", this);
			return (CallListener[]) callListeners.toArray(callListener);
		} finally {
			privData = null;
		}
	}

	public final int getCallOriginatorType() {
		TsapiTrace.traceEntry("getCallOriginatorType[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final int type = tsCall.getCallOriginatorType();
			TsapiTrace.traceExit("getCallOriginatorType[]", this);
			return type;
		} finally {
			privData = null;
		}
	}

	public final CallCapabilities getCapabilities(final Terminal terminal,
			final Address address) {
		TsapiTrace.traceEntry(
				"getCapabilities[Terminal terminal, Address address]", this);
		try {
			tsCall = tsCall.getHandOff();
			final CallCapabilities caps = tsCall.getTsapiCallCapabilities();
			TsapiTrace
					.traceExit(
							"getCapabilities[Terminal terminal, Address address]",
							this);
			return caps;
		} finally {
			privData = null;
		}
	}

	public final TerminalConnection getConferenceController() {
		TsapiTrace.traceEntry("getConferenceController[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSConnection tsConn = tsCall.getConfController();
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
			privData = null;
		}
	}

	public final boolean getConferenceEnable() {
		TsapiTrace.traceEntry("getConferenceEnable[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final boolean enable = tsCall.getConfEnable();
			TsapiTrace.traceExit("getConferenceEnable[]", this);
			return enable;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Connection[] getConnections() {
		try {
			Vector<TSConnection> tsconn = null;
			tsCall = tsCall.getHandOff();
			tsconn = tsCall.getTSConnections();
			if (tsconn == null) {
				privData = null;
				return null;
			}
			synchronized (tsconn) {
				if (tsconn.size() == 0) {
					privData = null;
					return null;
				}
				final Connection[] tsapiConn = new Connection[tsconn.size()];
				for (int i = 0; i < tsconn.size(); ++i)
					tsapiConn[i] = (Connection) TsapiCreateObject
							.getTsapiObject((TSConnection) tsconn.elementAt(i),
									true);

				privData = null;
				return tsapiConn;
			}
		} finally {
			privData = null;
		}
	}

	public final ACDAddress getDeliveringACDAddress() {
		TsapiTrace.traceEntry("getDeliveringACDAddress[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSDevice tsDevice = tsCall.getDeliveringACDDevice();
			if (tsDevice == null) {
				TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
				return null;
			}
			final ACDAddress addr = (ACDAddress) TsapiCreateObject
					.getTsapiObject(tsDevice, true);
			TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
			return addr;
		} finally {
			privData = null;
		}
	}

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		TsapiTrace.traceEntry("getDeviceHistory[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final V7DeviceHistoryEntry[] history = tsCall.getDeviceHistory();
			TsapiTrace.traceExit("getDeviceHistory[]", this);
			return history;
		} finally {
			privData = null;
		}
	}

	public final CallCenterAddress getDistributingAddress() {
		TsapiTrace.traceEntry("getDistributingAddress[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSDevice tsDevice = tsCall.getDistributingDevice();
			if (tsDevice == null) {
				TsapiTrace.traceExit("getDistributingAddress[]", this);
				return null;
			}
			final CallCenterAddress addr = (CallCenterAddress) TsapiCreateObject
					.getTsapiObject(tsDevice, true);
			TsapiTrace.traceExit("getDistributingAddress[]", this);
			return addr;
		} finally {
			privData = null;
		}
	}

	public final CallCenterAddress getDistributingVDNAddress() {
		TsapiTrace.traceEntry("getDistributingVDNAddress[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSDevice tsDevice = tsCall.getDistributingVDN();
			if (tsDevice == null) {
				TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
				return null;
			}
			final CallCenterAddress addr = (CallCenterAddress) TsapiCreateObject
					.getTsapiObject(tsDevice, true);
			TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
			return addr;
		} finally {
			privData = null;
		}
	}

	public final Address getLastRedirectedAddress() {
		TsapiTrace.traceEntry("getLastRedirectedAddress[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSDevice tsDevice = tsCall.getLastRedirectionDevice();
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
			privData = null;
		}
	}

	public final LookaheadInfo getLookaheadInfo() {
		TsapiTrace.traceEntry("getLookaheadInfo[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final LookaheadInfo lai = tsCall.getLAI();
			TsapiTrace.traceExit("getLookaheadInfo[]", this);
			return lai;
		} finally {
			privData = null;
		}
	}

	public CallObserver[] getObservers() {
		TsapiTrace.traceEntry("getObservers[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final Vector<TsapiCallMonitor> tsapiCallObservers = tsCall
					.getCallObservers();

			if (tsapiCallObservers == null || tsapiCallObservers.size() == 0) {
				TsapiTrace.traceExit("getObservers[]", this);
				return null;
			}

			final ArrayList<CallObserver> observers = new ArrayList<CallObserver>();

			for (final TsapiCallMonitor tsapiCallMonitor : tsapiCallObservers) {
				final TsapiCallMonitor obs = (TsapiCallMonitor) tsapiCallMonitor;
				if (obs.getObserver() != null)
					observers.add(obs.getObserver());
			}

			TsapiTrace.traceExit("getObservers[]", this);
			final CallObserver[] observerArray = new CallObserver[observers
					.size()];
			return (CallObserver[]) observers.toArray(observerArray);
		} finally {
			privData = null;
		}
	}

	public final OriginalCallInfo getOriginalCallInfo() {
		TsapiTrace.traceEntry("getOriginalCallInfo[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final OriginalCallInfo oci = tsCall.getOCI();
			TsapiTrace.traceExit("getOriginalCallInfo[]", this);
			return oci;
		} finally {
			privData = null;
		}
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		tsCall = tsCall.getHandOff();
		final Object obj = TsapiPromoter
				.promoteTsapiPrivate((CSTAPrivate) tsCall.getPrivateData());
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	// ERROR //
	public final javax.telephony.Provider getProvider() {
		try {
			tsCall = tsCall.getHandOff();
			final TSProvider tsProvider = tsCall.getTSProviderImpl();
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

	public final short getReason() {
		TsapiTrace.traceEntry("getReason[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final short reason = tsCall.getReason();
			TsapiTrace.traceExit("getReason[]", this);
			return reason;
		} finally {
			privData = null;
		}
	}

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final int state = tsCall.getState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			privData = null;
		}
	}

	public final TerminalConnection getTransferController() {
		TsapiTrace.traceEntry("getTransferController[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final TSConnection tsConn = tsCall.getXferController();
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
			privData = null;
		}
	}

	public final boolean getTransferEnable() {
		TsapiTrace.traceEntry("getTransferEnable[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final boolean enabled = tsCall.getXferEnable();
			TsapiTrace.traceExit("getTransferEnable[]", this);
			return enabled;
		} finally {
			privData = null;
		}
	}

	public final CallCenterTrunk getTrunk() {
		TsapiTrace.traceEntry("getTrunk[]", this);
		try {
			final CallCenterTrunk[] trks = getTrunks();
			if (trks == null || trks.length == 0) {
				TsapiTrace.traceExit("getTrunk[]", this);
				return null;
			}
			final CallCenterTrunk trunk = trks[0];
			TsapiTrace.traceExit("getTrunk[]", this);
			return trunk;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final CallCenterTrunk[] getTrunks() {
		try {
			Vector<TSTrunk> tstrunk = null;
			tsCall = tsCall.getHandOff();
			tstrunk = tsCall.getTSTrunks();
			if (tstrunk == null) {
				privData = null;
				return null;
			}
			synchronized (tstrunk) {
				if (tstrunk.size() == 0) {

					privData = null;
					return null;
				}
				final TsapiTrunk[] tsapiTrunk = new TsapiTrunk[tstrunk.size()];
				for (int i = 0; i < tstrunk.size(); ++i)
					tsapiTrunk[i] = (TsapiTrunk) TsapiCreateObject
							.getTsapiObject(tstrunk.elementAt(i), false);

				privData = null;
				return tsapiTrunk;
			}
		} finally {
			privData = null;
		}
	}

	public final int getTsapiCallID() {
		TsapiTrace.traceEntry("getTsapiCallID[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final int id = tsCall.getCallID();
			TsapiTrace.traceExit("getTsapiCallID[]", this);
			return id;
		} finally {
			privData = null;
		}
	}

	public final TSCall getTSCall() {
		TsapiTrace.traceEntry("getTSCall[]", this);
		tsCall = tsCall.getHandOff();
		TsapiTrace.traceExit("getTSCall[]", this);
		return tsCall;
	}

	public final String getUCID() {
		TsapiTrace.traceEntry("getUCID[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final String ucid = tsCall.getUCID();
			TsapiTrace.traceExit("getUCID[]", this);
			return ucid;
		} finally {
			privData = null;
		}
	}

	public final UserEnteredCode getUserEnteredCode() {
		TsapiTrace.traceEntry("getUserEnteredCode[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final UserEnteredCode uec = tsCall.getUEC();
			TsapiTrace.traceExit("getUserEnteredCode[]", this);
			return uec;
		} finally {
			privData = null;
		}
	}

	public final UserToUserInfo getUserToUserInfo() {
		TsapiTrace.traceEntry("getUserToUserInfo[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final UserToUserInfo uui = tsCall.getUUI();
			TsapiTrace.traceExit("getUserToUserInfo[]", this);
			return uui;
		} finally {
			privData = null;
		}
	}

	public final boolean hasCallOriginatorType() {
		TsapiTrace.traceEntry("hasCallOriginatorType[]", this);
		try {
			tsCall = tsCall.getHandOff();
			final boolean has = tsCall.hasCallOriginatorType();
			TsapiTrace.traceExit("hasCallOriginatorType[]", this);
			return has;
		} finally {
			privData = null;
		}
	}

	public final int hashCode() {
		tsCall = tsCall.getHandOff();

		final TSProviderImpl TSProviderImpl = tsCall.getTSProviderImpl();
		return TSProviderImpl.hashCode();
	}

	public final Connection offHook(final Address origaddress,
			final Terminal origterminal) throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"offHook[Address origaddress, Terminal origterminal]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public void removeCallListener(final CallListener listener) {
		TsapiTrace
				.traceEntry("removeCallListener[CallListener listener]", this);
		try {
			tsCall = tsCall.getHandOff();
			final Vector<TsapiCallMonitor> tsapiCallObservers = tsCall
					.getCallObservers();

			if (tsapiCallObservers == null || tsapiCallObservers.size() == 0) {
				TsapiTrace.traceExit(
						"removeCallListener[CallListener listener]", this);

				return;
			}

			for (final Object obs : tsapiCallObservers)
				if (((TsapiCallMonitor) obs).getListener() == listener) {
					tsCall = tsCall.getHandOff();
					tsCall.removeCallMonitor((TsapiCallMonitor) obs);
					TsapiTrace.traceExit(
							"removeCallListener[CallListener listener]", this);

					return;
				}
		} finally {
			privData = null;
		}
	}

	public void removeObserver(final CallObserver observer) {
		TsapiTrace.traceEntry("removeObserver[CallObserver observer]", this);
		try {
			tsCall = tsCall.getHandOff();
			final Vector<TsapiCallMonitor> tsapiCallObservers = tsCall
					.getCallObservers();

			if (tsapiCallObservers == null || tsapiCallObservers.size() == 0) {
				TsapiTrace.traceExit("removeObserver[CallObserver observer]",
						this);
				return;
			}

			for (int i = 0; i < tsapiCallObservers.size(); ++i) {
				final TsapiCallMonitor obs = (TsapiCallMonitor) tsapiCallObservers
						.elementAt(i);
				if (obs.getObserver() != observer)
					continue;
				tsCall = tsCall.getHandOff();
				tsCall.removeCallMonitor(obs);
				TsapiTrace.traceExit("removeObserver[CallObserver observer]",
						this);
				return;
			}

		} finally {
			privData = null;
		}
	}

	public final Object sendPrivateData(final Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			tsCall = tsCall.getHandOff();
			final Object obj = tsCall.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));
			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (final ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}
	}

	public final void setApplicationData(final Object data)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setApplicationData[Object data]", this);
		try {
			throw new TsapiMethodNotSupportedException(0, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void setBillRate(final short billType, final float billRate)
			throws TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("setBillRate[short billType, float billRate]",
				this);
		try {
			tsCall = tsCall.getHandOff();
			tsCall.setBillRate(billType, billRate);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("setBillRate[short billType, float billRate]",
				this);
	}

	public final void setConferenceController(final TerminalConnection termconn)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException {
		try {
			TsapiTrace.traceEntry(
					"setConferenceController[TerminalConnection termconn]",
					this);
			if (!(termconn instanceof ITsapiTerminalConnection))
				throw new TsapiInvalidArgumentException(3, 0,
						"The given TerminalConnection is not an instanceof ITsapiTerminalConnection");

			final TSConnection tsConn = ((TsapiTerminalConnection) termconn)
					.getTSConnection();
			if (tsConn != null) {
				tsCall = tsCall.getHandOff();
				tsCall.setConfController(tsConn);
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection");
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"setConferenceController[TerminalConnection termconn]", this);
	}

	public final void setConferenceEnable(final boolean enable)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException {
		TsapiTrace.traceEntry("setConferenceEnable[boolean enable]", this);
		try {
			tsCall = tsCall.getHandOff();
			tsCall.setConfEnable(enable);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("setConferenceEnable[boolean enable]", this);
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

	public final void setTransferController(final TerminalConnection termconn)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"setTransferController[TerminalConnection termconn]", this);
		try {
			if (!(termconn instanceof ITsapiTerminalConnection))
				throw new TsapiInvalidArgumentException(3, 0,
						"The given TerminalConnection is not an instanceof ITsapiTerminalConnection");

			final TSConnection tsConn = ((TsapiTerminalConnection) termconn)
					.getTSConnection();
			if (tsConn != null) {
				tsCall = tsCall.getHandOff();
				tsCall.setXferController(tsConn);
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate terminal connection");
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"setTransferController[TerminalConnection termconn]", this);
	}

	public final void setTransferEnable(final boolean enable)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException {
		TsapiTrace.traceEntry("setTransferEnable[boolean enable]", this);
		try {
			tsCall = tsCall.getHandOff();
			tsCall.setXferEnable(enable);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("setTransferEnable[boolean enable]", this);
	}

	public final void transfer(final Call otherCall)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("transfer[Call otherCall]", this);
		try {
			if (!(otherCall instanceof LucentV7Call))
				throw new TsapiInvalidArgumentException(3, 0,
						"other Call is not an instanceof ITsapiCall");

			final TSCall oCall = ((TsapiCall) otherCall).getTSCall();
			if (oCall != null) {
				tsCall = tsCall.getHandOff();
				tsCall.transfer(oCall, privData);
			} else
				throw new TsapiPlatformException(4, 0,
						"could not locate other call");
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("transfer[Call otherCall]", this);
	}

	public final Connection transfer(final String address)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiInvalidPartyException, TsapiMethodNotSupportedException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("transfer[String address]", this);
		try {
			if (address == null || address.equals(""))
				throw new TsapiInvalidArgumentException(3, 0,
						"address null or an empty string");

			tsCall = tsCall.getHandOff();
			final TSConnection tsConn = tsCall.transfer(address, privData);
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
			privData = null;
		}
	}
}
