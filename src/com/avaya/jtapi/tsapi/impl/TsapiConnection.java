package com.avaya.jtapi.tsapi.impl;

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

class TsapiConnection implements ITsapiConnection, PrivateData,
		ITsapiConnIDPrivate, LucentV6Connection {
	TSConnection tsConnection;
	CSTAPrivate privData = null;

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			int state = this.tsConnection.getConnectionState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			this.privData = null;
		}
	}

	public final Call getCall() {
		TsapiTrace.traceEntry("getCall[]", this);
		try {
			TSCall tsCall = this.tsConnection.getTSCall();
			if (tsCall != null) {
				Call call = (Call) TsapiCreateObject.getTsapiObject(tsCall,
						false);
				TsapiTrace.traceExit("getCall[]", this);
				return call;
			}

			throw new TsapiPlatformException(4, 0, "could not locate call");
		} finally {
			this.privData = null;
		}
	}

	public final Address getAddress() {
		TsapiTrace.traceEntry("getAddress[]", this);
		try {
			TSDevice tsDevice = this.tsConnection.getTSDevice();
			if (tsDevice != null) {
				Address addr = (Address) TsapiCreateObject.getTsapiObject(
						tsDevice, true);
				TsapiTrace.traceExit("getAddress[]", this);
				return addr;
			}

			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			this.privData = null;
		}
	}

	public final TerminalConnection[] getTerminalConnections() {
		TsapiTrace.traceEntry("getTerminalConnections[]", this);
		try {
			Vector<?> tsTermConns = this.tsConnection.getTSTermConns();

			if (tsTermConns == null) {
				TsapiTrace.traceExit("getTerminalConnections[]", this);
				return null;
			}

			synchronized (tsTermConns) {
				if (tsTermConns.size() == 0) {
					TsapiTrace.traceExit("getTerminalConnections[]", this);
					return null;
				}

				TerminalConnection[] tsapiTermConn = new TerminalConnection[tsTermConns
						.size()];
				for (int i = 0; i < tsTermConns.size(); i++) {
					tsapiTermConn[i] = ((TerminalConnection) TsapiCreateObject
							.getTsapiObject(
									(TSConnection) tsTermConns.elementAt(i),
									false));
				}
				TsapiTrace.traceExit("getTerminalConnections[]", this);
				return tsapiTermConn;
			}
		} finally {
			this.privData = null;
		}
	}

	public final void disconnect() throws TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException, TsapiInvalidStateException {
		TsapiTrace.traceEntry("disconnect[]", this);
		try {
			this.tsConnection.disconnect(this.privData);
		} finally {
			this.privData = null;
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

		if (this.tsConnection.getTSProviderImpl().isLucentV6()) {
			lcc = new LucentV6ClearConnection(dropResource, asn_uui);
		} else {
			lcc = new LucentClearConnection(dropResource, asn_uui);
		}
		this.privData = lcc.makeTsapiPrivate();
		disconnect();
		TsapiTrace
				.traceExit(
						"disconnect[short dropResource, UserToUserInfo userInfo]",
						this);
	}

	public final ConnectionCapabilities getCapabilities() {
		TsapiTrace.traceEntry("getCapabilities[]", this);
		try {
			ConnectionCapabilities caps = this.tsConnection
					.getTsapiConnCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			this.privData = null;
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

	public final int getCallControlState() {
		TsapiTrace.traceEntry("getCallControlState[]", this);
		try {
			int i = this.tsConnection.getCallControlConnectionState();
			TsapiTrace.traceExit("getCallControlState[]", this);
			return i;
		} finally {
			this.privData = null;
		}
	}

	public final void accept() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("accept[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			this.privData = null;
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
			this.privData = null;
		}
	}

	public final Connection redirect(String destinationAddress)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("redirect[String destinationAddress]", this);
		try {
			TSConnection conn = this.tsConnection.redirect(destinationAddress,
					this.privData);
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
			this.privData = null;
		}
	}

	public final void listenHold(LucentTerminalConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenHold[LucentTerminalConnection partyToHold]", this);
		try {
			TSConnection party = partyToHold == null ? null
					: ((TsapiTerminalConnection) partyToHold).tsConnection;

			this.tsConnection.listenHold(party);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"listenHold[LucentTerminalConnection partyToHold]", this);
	}

	public final void listenUnhold(LucentTerminalConnection partyToUnhold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
		try {
			TSConnection party = partyToUnhold == null ? null
					: ((TsapiTerminalConnection) partyToUnhold).tsConnection;

			this.tsConnection.listenUnhold(party);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
	}

	public final void listenHold(LucentConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("listenHold[LucentConnection partyToHold]", this);
		try {
			TSConnection party = partyToHold == null ? null
					: ((TsapiConnection) partyToHold).tsConnection;

			this.tsConnection.listenHold(party);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("listenHold[LucentConnection partyToHold]", this);
	}

	public final void listenUnhold(LucentConnection partyToUnhold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("listenUnhold[LucentConnection partyToUnhold]",
				this);
		try {
			TSConnection party = partyToUnhold == null ? null
					: ((TsapiConnection) partyToUnhold).tsConnection;

			this.tsConnection.listenUnhold(party);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("listenUnhold[LucentConnection partyToUnhold]",
				this);
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
			this.privData = null;
		}
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
			this.privData = null;
		}
	}

	public final CallCenterTrunk getTrunk() {
		TsapiTrace.traceEntry("getTrunk[]", this);
		try {
			TSTrunk tsTrunk = this.tsConnection.getTSTrunk();
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
		Object obj = null;
		Object tempObj = this.tsConnection.getConnPrivateData();
		if (tempObj != null) {
			obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate) tempObj);
		}
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			Object obj = this.tsConnection.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));

			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (ClassCastException e) {
		}
		throw new TsapiPlatformException(3, 0,
				"data is not a TsapiPrivate object");
	}

	public final ConnectionID getTsapiConnectionID() {
		TsapiTrace.traceEntry("getTsapiConnectionID[]", this);
		try {
			CSTAConnectionID cstaConnectionID = this.tsConnection.getConnID();
			ConnectionID id = new ConnectionID(cstaConnectionID.getCallID(),
					cstaConnectionID.getDeviceID(),
					(short) cstaConnectionID.getDevIDType());

			TsapiTrace.traceExit("getTsapiConnectionID[]", this);
			return id;
		} finally {
			this.privData = null;
		}
	}

	public int hashCode() {
		return this.tsConnection.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiConnection)) {
			return this.tsConnection
					.equals(((TsapiConnection) obj).tsConnection);
		}

		return false;
	}

	TsapiConnection(TSConnection _tsConnection) {
		this.tsConnection = _tsConnection;
		TsapiTrace.traceConstruction(this, TsapiConnection.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiConnection.class);
	}

	TSConnection getTSConnection() {
		TsapiTrace.traceEntry("getTSConnection[]", this);
		TsapiTrace.traceExit("getTSConnection[]", this);
		return this.tsConnection;
	}
}