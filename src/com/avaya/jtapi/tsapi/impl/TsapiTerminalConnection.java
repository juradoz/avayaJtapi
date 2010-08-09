package com.avaya.jtapi.tsapi.impl;

import java.net.URL;

import javax.telephony.Address;
import javax.telephony.InvalidArgumentException;
import javax.telephony.PlatformException;
import javax.telephony.Terminal;
import javax.telephony.capabilities.TerminalConnectionCapabilities;
import javax.telephony.privatedata.PrivateData;

import com.avaya.jtapi.tsapi.ConnectionID;
import com.avaya.jtapi.tsapi.ITsapiConnIDPrivate;
import com.avaya.jtapi.tsapi.ITsapiTerminalConnection;
import com.avaya.jtapi.tsapi.LucentConnection;
import com.avaya.jtapi.tsapi.LucentTerminalConnection;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
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
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiTerminalConnection implements ITsapiTerminalConnection, PrivateData,
		ITsapiConnIDPrivate {
	TSConnection tsConnection;
	CSTAPrivate privData = null;

	TsapiTerminalConnection(TSConnection _tsConnection) {
		tsConnection = _tsConnection;
		TsapiTrace.traceConstruction(this, TsapiTerminalConnection.class);
	}

	// 0: ldc 14
	// 2: aload_0
	// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 6: aload_0
	// 7: getfield 3
	// com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:tsConnection
	// Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
	// 10: invokevirtual 15
	// com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSConnection
	// ()Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
	// 13: astore_1
	// 14: aload_1
	// 15: ifnull +25 -> 40
	// 18: aload_1
	// 19: iconst_1
	// 20: invokestatic 9
	// com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject
	// (Ljava/lang/Object;Z)Ljava/lang/Object;
	// 23: checkcast 16 javax/telephony/Connection
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
	// 64: putfield 6
	// com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData
	// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
	// 67: ret 5
	//
	// Exception table:
	// from to target type
	// 6 38 52 finally
	// 40 57 52 finally }
	public final void answer() throws TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException, TsapiInvalidStateException {
		TsapiTrace.traceEntry("answer[]", this);
		try {
			tsConnection.answer(privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("answer[]", this);
	}

	private LucentClearConnection createLucentClearConnection(short dropRes,
			UserToUserInfo uui) {
		TsapiTrace
				.traceEntry(
						"createLucentClearConnection[short dropRes, UserToUserInfo uui]",
						this);
		TSProviderImpl provider = tsConnection.getTSProviderImpl();
		LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(uui);

		LucentClearConnection conn = null;
		if (provider != null) {
			if (provider.isLucentV6()) {
				conn = new LucentV6ClearConnection(dropRes, asn_uui);
			} else {
				conn = new LucentClearConnection(dropRes, asn_uui);
			}
		}
		TsapiTrace
				.traceExit(
						"createLucentClearConnection[short dropRes, UserToUserInfo uui]",
						this);
		return conn;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TsapiTerminalConnection) {
			return tsConnection
					.equals(((TsapiTerminalConnection) obj).tsConnection);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiTerminalConnection.class);
	}

	public final void generateDtmf(String digits)
			throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiInvalidStateException {
		TsapiTrace.traceEntry("generateDtmf[String digits]", this);
		try {
			tsConnection.generateDtmf(digits);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("generateDtmf[String digits]", this);
	}

	public final void generateDtmf(String digits, int toneDuration,
			int pauseDuration) throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiInvalidStateException {
		TsapiTrace
				.traceEntry(
						"generateDtmf[String digits, int toneDuration, int pauseDuration]",
						this);
		try {
			tsConnection.generateDtmf(digits, toneDuration, pauseDuration);
		} finally {
			privData = null;
		}
		TsapiTrace
				.traceExit(
						"generateDtmf[String digits, int toneDuration, int pauseDuration]",
						this);
	}

	public final int getCallControlState() {
		TsapiTrace.traceEntry("getCallControlState[]", this);
		try {
			int state = tsConnection.getCallControlTerminalConnectionState();
			TsapiTrace.traceExit("getCallControlState[]", this);
			return state;
		} finally {
			privData = null;
		}
	}

	public final TerminalConnectionCapabilities getCapabilities() {
		TsapiTrace.traceEntry("getCapabilities[]", this);
		try {
			TerminalConnectionCapabilities caps = tsConnection
					.getTsapiTermConnCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			privData = null;
		}
	}

	// 0: ldc 7
	// 2: aload_0
	// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 6: aload_0
	// 7: getfield 3
	// com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:tsConnection
	// Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
	// 10: invokevirtual 8
	// com/avaya/jtapi/tsapi/impl/core/TSConnection:getTSDevice
	// ()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
	// 13: astore_1
	// 14: aload_1
	// 15: ifnull +25 -> 40
	// 18: aload_1
	// 19: iconst_0
	// 20: invokestatic 9
	// com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject
	// (Ljava/lang/Object;Z)Ljava/lang/Object;
	// 23: checkcast 10 javax/telephony/Terminal
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
	// 64: putfield 6
	// com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData
	// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
	// 67: ret 5
	//
	// Exception table:
	// from to target type
	// 6 38 52 finally
	// 40 57 52 finally }
	// ERROR //
	public final javax.telephony.Connection getConnection() {
		return null;
	}// Byte code:

	public final int getMediaAvailability() {
		TsapiTrace.traceEntry("getMediaAvailability[]", this);
		try {
			TsapiTrace.traceExit("getMediaAvailability[]", this);
			return 129;
		} finally {
			privData = null;
		}
	}

	public final int getMediaState() {
		TsapiTrace.traceEntry("getMediaState[]", this);
		try {
			TsapiTrace.traceExit("getMediaState[]", this);
			return 0;
		} finally {
			privData = null;
		}
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		Object obj = TsapiPromoter
				.promoteTsapiPrivate((CSTAPrivate) tsConnection
						.getTermConnPrivateData());
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			int state = tsConnection.getTerminalConnectionState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Terminal getTerminal() {
		return null;
	}// Byte code:

	public final TerminalConnectionCapabilities getTerminalConnectionCapabilities(
			Terminal terminal, Address address)
			throws InvalidArgumentException, PlatformException {
		TsapiTrace
				.traceEntry(
						"getTerminalConnectionCapabilities[Terminal terminal, Address address]",
						this);
		TerminalConnectionCapabilities caps = getCapabilities();
		TsapiTrace
				.traceExit(
						"getTerminalConnectionCapabilities[Terminal terminal, Address address]",
						this);
		return caps;
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

	final TSConnection getTSConnection() {
		TsapiTrace.traceEntry("getTSConnection[]", this);
		TsapiTrace.traceExit("getTSConnection[]", this);
		return tsConnection;
	}

	@Override
	public final int hashCode() {
		return tsConnection.hashCode();
	}

	public final void hold() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("hold[]", this);
		try {
			tsConnection.hold(privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("hold[]", this);
	}

	public final void join() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("join[]", this);
		try {
			tsConnection.join(privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("join[]", this);
	}

	public final void leave() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("leave[]", this);
		try {
			tsConnection.leave(privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("leave[]", this);
	}

	public final void leave(short dropResource, UserToUserInfo userInfo)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"leave[short dropResource, UserToUserInfo userInfo]", this);
		LucentClearConnection lcc = createLucentClearConnection(dropResource,
				userInfo);
		privData = lcc.makeTsapiPrivate();
		leave();
		TsapiTrace.traceExit(
				"leave[short dropResource, UserToUserInfo userInfo]", this);
	}

	public final void listenHold(LucentConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenHold[LucentTerminalConnection partyToHold]", this);
		try {
			TSConnection party = (partyToHold == null) ? null
					: ((TsapiConnection) partyToHold).tsConnection;

			tsConnection.listenHold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenHold[LucentTerminalConnection partyToHold]", this);
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
		TsapiTrace.traceEntry(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
		try {
			TSConnection party = (partyToUnhold == null) ? null
					: ((TsapiConnection) partyToUnhold).tsConnection;

			tsConnection.listenUnhold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
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

	// 0: ldc 69
	// 2: aload_0
	// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 6: jsr +12 -> 18
	// 9: goto +17 -> 26
	// 12: astore_1
	// 13: jsr +5 -> 18
	// 16: aload_1
	// 17: athrow
	// 18: astore_2
	// 19: aload_0
	// 20: aconst_null
	// 21: putfield 6
	// com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData
	// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
	// 24: ret 2
	// 26: ldc 69
	// 28: aload_0
	// 29: invokestatic 5 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 32: return
	//
	// Exception table:
	// from to target type
	// 6 9 12 finally
	// 12 16 12 finally }
	public final void setDtmfDetection(boolean enable)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setDtmfDetection[boolean enable]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
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

	public final void startPlaying() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("startPlaying[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	// 0: ldc 67
	// 2: aload_0
	// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 6: jsr +12 -> 18
	// 9: goto +17 -> 26
	// 12: astore_1
	// 13: jsr +5 -> 18
	// 16: aload_1
	// 17: athrow
	// 18: astore_2
	// 19: aload_0
	// 20: aconst_null
	// 21: putfield 6
	// com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:privData
	// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
	// 24: ret 2
	// 26: ldc 67
	// 28: aload_0
	// 29: invokestatic 5 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
	// (Ljava/lang/String;Ljava/lang/Object;)V
	// 32: return
	//
	// Exception table:
	// from to target type
	// 6 9 12 finally
	// 12 16 12 finally }
	public final void startRecording() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("startRecording[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final void stopPlaying() {
	}// Byte code:

	// ERROR //
	public final void stopRecording() {
	}// Byte code:

	public final void unhold() throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("unhold[]", this);
		try {
			tsConnection.unhold(privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("unhold[]", this);
	}

	public final void useDefaultMicrophone()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("useDefaultMicrophone[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void useDefaultSpeaker()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("useDefaultSpeaker[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void usePlayURL(URL url)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("usePlayURL[URL url]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void useRecordURL(URL url)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("useRecordURL[URL url]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiTerminalConnection JD-Core Version: 0.5.4
 */