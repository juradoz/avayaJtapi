package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.Connection;
import javax.telephony.InvalidArgumentException;
import javax.telephony.PlatformException;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.capabilities.ConnectionCapabilities;
import javax.telephony.privatedata.PrivateData;

import com.avaya.jtapi.tsapi.ConnectionID;
import com.avaya.jtapi.tsapi.ITsapiConnIDPrivate;
import com.avaya.jtapi.tsapi.ITsapiConnection;
import com.avaya.jtapi.tsapi.LucentConnection;
import com.avaya.jtapi.tsapi.LucentTerminalConnection;
import com.avaya.jtapi.tsapi.LucentV6Connection;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.LucentClearConnection;
import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV6ClearConnection;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiConnection implements ITsapiConnection, PrivateData,
		ITsapiConnIDPrivate, LucentV6Connection {
	TSConnection tsConnection;
	CSTAPrivate privData = null;

	TsapiConnection(TSConnection _tsConnection) {
		tsConnection = _tsConnection;
		TsapiTrace.traceConstruction(this, TsapiConnection.class);
	}

	public final void accept() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("accept[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void addToAddress(String additionalAddress)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addToAddress[String additionalAddress]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void disconnect() throws TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException, TsapiInvalidStateException {
		TsapiTrace.traceEntry("disconnect[]", this);
		try {
			tsConnection.disconnect(privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("disconnect[]", this);
	}

	public final void disconnect(short dropResource, UserToUserInfo userInfo)
			throws TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException, TsapiInvalidStateException {
		TsapiTrace
				.traceEntry(
						"disconnect[short dropResource, UserToUserInfo userInfo]",
						this);
		LucentClearConnection lcc = null;
		LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		if (tsConnection.getTSProviderImpl().isLucentV6()) {
			lcc = new LucentV6ClearConnection(dropResource, asn_uui);
		} else {
			lcc = new LucentClearConnection(dropResource, asn_uui);
		}
		privData = lcc.makeTsapiPrivate();
		disconnect();
		TsapiTrace
				.traceExit(
						"disconnect[short dropResource, UserToUserInfo userInfo]",
						this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TsapiConnection) {
			return tsConnection.equals(((TsapiConnection) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiConnection.class);
	}

	// ERROR //
	public final Address getAddress() {
		try {
			TSDevice tsDevice = tsConnection.getTSDevice();
			Address localAddress;
			if (tsDevice != null) {
				localAddress = (Address) TsapiCreateObject.getTsapiObject(
						tsDevice, true);

				privData = null;
				return localAddress;
			}
			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final javax.telephony.Call getCall() {
		try {
			TSCall tsCall = tsConnection.getTSCall();
			Call localCall;
			if (tsCall != null) {
				localCall = (Call) TsapiCreateObject.getTsapiObject(tsCall,
						false);

				privData = null;
				return localCall;
			}
			throw new TsapiPlatformException(4, 0, "could not locate call");
		} finally {
			privData = null;
		}
	}

	public final int getCallControlState() {
		TsapiTrace.traceEntry("getCallControlState[]", this);
		try {
			int i = tsConnection.getCallControlConnectionState();
			TsapiTrace.traceExit("getCallControlState[]", this);
			return i;
		} finally {
			privData = null;
		}
	}

	public final ConnectionCapabilities getCapabilities() {
		TsapiTrace.traceEntry("getCapabilities[]", this);
		try {
			ConnectionCapabilities caps = tsConnection
					.getTsapiConnCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			privData = null;
		}
	}

	public final ConnectionCapabilities getConnectionCapabilities(
			Terminal terminal, Address address)
			throws InvalidArgumentException, PlatformException {
		TsapiTrace
				.traceEntry(
						"getConnectionCapabilities[Terminal terminal, Address address]",
						this);
		ConnectionCapabilities caps = getCapabilities();
		TsapiTrace
				.traceExit(
						"getConnectionCapabilities[Terminal terminal, Address address]",
						this);
		return caps;
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		Object obj = TsapiPromoter
				.promoteTsapiPrivate((CSTAPrivate) tsConnection
						.getConnPrivateData());
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			int state = tsConnection.getConnectionState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final javax.telephony.TerminalConnection[] getTerminalConnections() {
		try {
			Vector<TSConnection> tsTermConns = null;
			tsTermConns = tsConnection.getTSTermConns();
			if (tsTermConns == null) {
				privData = null;
				return null;
			}
			synchronized (tsTermConns) {
				if (tsTermConns.size() == 0) {
					privData = null;
					return null;
				}
				TerminalConnection[] tsapiTermConn = new TerminalConnection[tsTermConns
						.size()];
				for (int i = 0; i < tsTermConns.size(); ++i) {
					tsapiTermConn[i] = ((TerminalConnection) TsapiCreateObject
							.getTsapiObject(tsTermConns.elementAt(i), false));
				}

				privData = null;
				return tsapiTermConn;
			}
		} finally {
			privData = null;
		}
	}

	public final CallCenterTrunk getTrunk() {
		TsapiTrace.traceEntry("getTrunk[]", this);
		try {
			TSTrunk tsTrunk = tsConnection.getTSTrunk();
			CallCenterTrunk trunk;
			if (tsTrunk != null) {
				trunk = (CallCenterTrunk) TsapiCreateObject.getTsapiObject(
						tsTrunk, false);
				TsapiTrace.traceExit("getTrunk[]", this);
				return trunk;
			}

			TsapiTrace.traceExit("getTrunk[]", this);
			return null;
		} finally {
			privData = null;
		}
	}

	public final ConnectionID getTsapiConnectionID() {
		TsapiTrace.traceEntry("getTsapiConnectionID[]", this);
		try {
			CSTAConnectionID cstaConnectionID = tsConnection.getConnID();
			ConnectionID id = new ConnectionID(cstaConnectionID.getCallID(),
					cstaConnectionID.getDeviceID(), (short) cstaConnectionID
							.getDevIDType());

			TsapiTrace.traceExit("getTsapiConnectionID[]", this);
			return id;
		} finally {
			privData = null;
		}
	}

	TSConnection getTSConnection() {
		TsapiTrace.traceEntry("getTSConnection[]", this);
		TsapiTrace.traceExit("getTSConnection[]", this);
		return tsConnection;
	}

	@Override
	public int hashCode() {
		return tsConnection.hashCode();
	}

	public final void listenHold(LucentConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("listenHold[LucentConnection partyToHold]", this);
		try {
			TSConnection party = (partyToHold == null) ? null
					: ((TsapiConnection) partyToHold).tsConnection;

			tsConnection.listenHold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("listenHold[LucentConnection partyToHold]", this);
	}

	public final void listenHold(LucentTerminalConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenHold[LucentTerminalConnection partyToHold]", this);
		try {
			TSConnection party = (partyToHold == null) ? null
					: ((TsapiTerminalConnection) partyToHold).tsConnection;

			tsConnection.listenHold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenHold[LucentTerminalConnection partyToHold]", this);
	}

	public final void listenUnhold(LucentConnection partyToUnhold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("listenUnhold[LucentConnection partyToUnhold]",
				this);
		try {
			TSConnection party = (partyToUnhold == null) ? null
					: ((TsapiConnection) partyToUnhold).tsConnection;

			tsConnection.listenUnhold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("listenUnhold[LucentConnection partyToUnhold]",
				this);
	}

	public final void listenUnhold(LucentTerminalConnection partyToUnhold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
		try {
			TSConnection party = (partyToUnhold == null) ? null
					: ((TsapiTerminalConnection) partyToUnhold).tsConnection;

			tsConnection.listenUnhold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
	}

	public final Connection park(String destAddress)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiInvalidPartyException, TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("park[String destAddress]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final Connection redirect(String destinationAddress)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("redirect[String destinationAddress]", this);
		try {
			TSConnection conn = tsConnection.redirect(destinationAddress,
					privData);
			Connection connexion;
			if (conn != null) {
				connexion = (Connection) TsapiCreateObject.getTsapiObject(conn,
						true);
				TsapiTrace.traceExit("redirect[String destinationAddress]",
						this);
				return connexion;
			}

			TsapiTrace.traceExit("redirect[String destinationAddress]", this);
			return null;
		} finally {
			privData = null;
		}
	}

	public final void reject() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("reject[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			Object obj = tsConnection.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));

			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}
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

