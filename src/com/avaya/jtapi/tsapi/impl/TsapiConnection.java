package com.avaya.jtapi.tsapi.impl;

import javax.telephony.Address;
import javax.telephony.Connection;
import javax.telephony.InvalidArgumentException;
import javax.telephony.PlatformException;
import javax.telephony.Terminal;
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
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
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

	// 0: ldc 18
	// 2: aload_0
	// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 6: aload_0
	// 7: getfield 3 com/avaya/jtapi/tsapi/impl/TsapiConnection:tsConnection
	// Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
	// 10: invokevirtual 19
	// com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSTermConns
	// ()Ljava/util/Vector;
	// 13: astore_1
	// 14: aload_1
	// 15: ifnonnull +16 -> 31
	// 18: ldc 18
	// 20: aload_0
	// 21: invokestatic 5 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 24: aconst_null
	// 25: astore_2
	// 26: jsr +109 -> 135
	// 29: aload_2
	// 30: areturn
	// 31: aload_1
	// 32: dup
	// 33: astore_2
	// 34: monitorenter
	// 35: aload_1
	// 36: invokevirtual 20 java/util/Vector:size ()I
	// 39: ifne +18 -> 57
	// 42: ldc 18
	// 44: aload_0
	// 45: invokestatic 5 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 48: aconst_null
	// 49: astore_3
	// 50: aload_2
	// 51: monitorexit
	// 52: jsr +83 -> 135
	// 55: aload_3
	// 56: areturn
	// 57: aload_1
	// 58: invokevirtual 20 java/util/Vector:size ()I
	// 61: anewarray 21 javax/telephony/TerminalConnection
	// 64: astore_3
	// 65: iconst_0
	// 66: istore 4
	// 68: iload 4
	// 70: aload_1
	// 71: invokevirtual 20 java/util/Vector:size ()I
	// 74: if_icmpge +29 -> 103
	// 77: aload_3
	// 78: iload 4
	// 80: aload_1
	// 81: iload 4
	// 83: invokevirtual 22 java/util/Vector:elementAt (I)Ljava/lang/Object;
	// 86: checkcast 23 com/avaya/jtapi/tsapi/impl/core/TSConnection
	// 89: iconst_0
	// 90: invokestatic 9
	// com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject
	// (Ljava/lang/Object;Z)Ljava/lang/Object;
	// 93: checkcast 21 javax/telephony/TerminalConnection
	// 96: aastore
	// 97: iinc 4 1
	// 100: goto -32 -> 68
	// 103: ldc 18
	// 105: aload_0
	// 106: invokestatic 5 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 109: aload_3
	// 110: astore 4
	// 112: aload_2
	// 113: monitorexit
	// 114: jsr +21 -> 135
	// 117: aload 4
	// 119: areturn
	// 120: astore 5
	// 122: aload_2
	// 123: monitorexit
	// 124: aload 5
	// 126: athrow
	// 127: astore 6
	// 129: jsr +6 -> 135
	// 132: aload 6
	// 134: athrow
	// 135: astore 7
	// 137: aload_0
	// 138: aconst_null
	// 139: putfield 6 com/avaya/jtapi/tsapi/impl/TsapiConnection:privData
	// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
	// 142: ret 7
	//
	// Exception table:
	// from to target type
	// 35 52 120 finally
	// 57 114 120 finally
	// 120 124 120 finally
	// 6 29 127 finally
	// 31 55 127 finally
	// 57 117 127 finally
	// 120 132 127 finally }
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

	// 0: ldc 7
	// 2: aload_0
	// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 6: aload_0
	// 7: getfield 3 com/avaya/jtapi/tsapi/impl/TsapiConnection:tsConnection
	// Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
	// 10: invokevirtual 8
	// com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSCall
	// ()Lcom/avaya/jtapi/tsapi/impl/core/TSCall;
	// 13: astore_1
	// 14: aload_1
	// 15: ifnull +25 -> 40
	// 18: aload_1
	// 19: iconst_0
	// 20: invokestatic 9
	// com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject
	// (Ljava/lang/Object;Z)Ljava/lang/Object;
	// 23: checkcast 10 javax/telephony/Call
	// 26: astore_2
	// 27: ldc 7
	// 29: aload_0
	// 30: invokestatic 5 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 33: aload_2
	// 34: astore_3
	// 35: jsr +25 -> 60
	// 38: aload_3
	// 39: areturn
	// 40: new 11 com/avaya/jtapi/tsapi/TsapiPlatformException
	// 43: dup
	// 44: iconst_4
	// 45: iconst_0
	// 46: ldc 12
	// 48: invokespecial 13 com/avaya/jtapi/tsapi/TsapiPlatformException:<init>
	// (IILjava/lang/String;)V
	// 51: athrow
	// 52: astore 4
	// 54: jsr +6 -> 60
	// 57: aload 4
	// 59: athrow
	// 60: astore 5
	// 62: aload_0
	// 63: aconst_null
	// 64: putfield 6 com/avaya/jtapi/tsapi/impl/TsapiConnection:privData
	// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
	// 67: ret 5
	//
	// Exception table:
	// from to target type
	// 6 38 52 finally
	// 40 57 52 finally }
	// ERROR //
	public final Address getAddress() {
		return null;
	}// Byte code:

	// ERROR //
	public final javax.telephony.Call getCall() {
		return null;
	}// Byte code:

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

	// 0: ldc 14
	// 2: aload_0
	// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 6: aload_0
	// 7: getfield 3 com/avaya/jtapi/tsapi/impl/TsapiConnection:tsConnection
	// Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
	// 10: invokevirtual 15
	// com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSDevice
	// ()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
	// 13: astore_1
	// 14: aload_1
	// 15: ifnull +25 -> 40
	// 18: aload_1
	// 19: iconst_1
	// 20: invokestatic 9
	// com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject
	// (Ljava/lang/Object;Z)Ljava/lang/Object;
	// 23: checkcast 16 javax/telephony/Address
	// 26: astore_2
	// 27: ldc 14
	// 29: aload_0
	// 30: invokestatic 5 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 33: aload_2
	// 34: astore_3
	// 35: jsr +25 -> 60
	// 38: aload_3
	// 39: areturn
	// 40: new 11 com/avaya/jtapi/tsapi/TsapiPlatformException
	// 43: dup
	// 44: iconst_4
	// 45: iconst_0
	// 46: ldc 17
	// 48: invokespecial 13 com/avaya/jtapi/tsapi/TsapiPlatformException:<init>
	// (IILjava/lang/String;)V
	// 51: athrow
	// 52: astore 4
	// 54: jsr +6 -> 60
	// 57: aload 4
	// 59: athrow
	// 60: astore 5
	// 62: aload_0
	// 63: aconst_null
	// 64: putfield 6 com/avaya/jtapi/tsapi/impl/TsapiConnection:privData
	// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
	// 67: ret 5
	//
	// Exception table:
	// from to target type
	// 6 38 52 finally
	// 40 57 52 finally }
	// ERROR //
	public final javax.telephony.TerminalConnection[] getTerminalConnections() {
		return null;
	}// Byte code:

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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiConnection JD-Core Version: 0.5.4
 */