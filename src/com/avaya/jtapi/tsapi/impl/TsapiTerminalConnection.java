package com.avaya.jtapi.tsapi.impl;

import java.net.URL;

import javax.telephony.Address;
import javax.telephony.Connection;
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
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiTerminalConnection implements ITsapiTerminalConnection, PrivateData,
		ITsapiConnIDPrivate {
	TSConnection tsConnection;
	CSTAPrivate privData = null;

	TsapiTerminalConnection(final TSConnection _tsConnection) {
		tsConnection = _tsConnection;
		TsapiTrace.traceConstruction(this, TsapiTerminalConnection.class);
	}

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

	private LucentClearConnection createLucentClearConnection(
			final short dropRes, final UserToUserInfo uui) {
		TsapiTrace
				.traceEntry(
						"createLucentClearConnection[short dropRes, UserToUserInfo uui]",
						this);
		final TSProviderImpl provider = tsConnection.getTSProviderImpl();
		final LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(uui);

		LucentClearConnection conn = null;
		if (provider != null)
			if (provider.isLucentV6())
				conn = new LucentV6ClearConnection(dropRes, asn_uui);
			else
				conn = new LucentClearConnection(dropRes, asn_uui);
		TsapiTrace
				.traceExit(
						"createLucentClearConnection[short dropRes, UserToUserInfo uui]",
						this);
		return conn;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TsapiTerminalConnection)
			return tsConnection
					.equals(((TsapiTerminalConnection) obj).tsConnection);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiTerminalConnection.class);
	}

	public final void generateDtmf(final String digits)
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

	public final void generateDtmf(final String digits, final int toneDuration,
			final int pauseDuration) throws TsapiMethodNotSupportedException,
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
			final int state = tsConnection
					.getCallControlTerminalConnectionState();
			TsapiTrace.traceExit("getCallControlState[]", this);
			return state;
		} finally {
			privData = null;
		}
	}

	public final TerminalConnectionCapabilities getCapabilities() {
		TsapiTrace.traceEntry("getCapabilities[]", this);
		try {
			final TerminalConnectionCapabilities caps = tsConnection
					.getTsapiTermConnCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final javax.telephony.Connection getConnection() {
		try {
			final TSConnection tsConn = tsConnection.getTSConnection();
			Connection localConnection;
			if (tsConn != null) {
				localConnection = (Connection) TsapiCreateObject
						.getTsapiObject(tsConn, true);

				privData = null;

				return localConnection;
			}
			throw new TsapiPlatformException(4, 0,
					"could not locate connection");
		} finally {
			privData = null;
		}
	}

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
		final Object obj = TsapiPromoter
				.promoteTsapiPrivate((CSTAPrivate) tsConnection
						.getTermConnPrivateData());
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			final int state = tsConnection.getTerminalConnectionState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final Terminal getTerminal() {
		try {
			final TSDevice tsDevice = tsConnection.getTSDevice();
			Terminal localTerminal;
			if (tsDevice != null) {
				localTerminal = (Terminal) TsapiCreateObject.getTsapiObject(
						tsDevice, false);

				privData = null;
				return localTerminal;
			}
			throw new TsapiPlatformException(4, 0, "could not locate terminal");
		} finally {
			privData = null;
		}
	}

	public final TerminalConnectionCapabilities getTerminalConnectionCapabilities(
			final Terminal terminal, final Address address)
			throws InvalidArgumentException, PlatformException {
		TsapiTrace
				.traceEntry(
						"getTerminalConnectionCapabilities[Terminal terminal, Address address]",
						this);
		final TerminalConnectionCapabilities caps = getCapabilities();
		TsapiTrace
				.traceExit(
						"getTerminalConnectionCapabilities[Terminal terminal, Address address]",
						this);
		return caps;
	}

	public final ConnectionID getTsapiConnectionID() {
		TsapiTrace.traceEntry("getTsapiConnectionID[]", this);
		try {
			final CSTAConnectionID cstaConnectionID = tsConnection.getConnID();
			final ConnectionID id = new ConnectionID(cstaConnectionID
					.getCallID(), cstaConnectionID.getDeviceID(),
					(short) cstaConnectionID.getDevIDType());

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

	public final void leave(final short dropResource,
			final UserToUserInfo userInfo) throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"leave[short dropResource, UserToUserInfo userInfo]", this);
		final LucentClearConnection lcc = createLucentClearConnection(
				dropResource, userInfo);
		privData = lcc.makeTsapiPrivate();
		leave();
		TsapiTrace.traceExit(
				"leave[short dropResource, UserToUserInfo userInfo]", this);
	}

	public final void listenHold(final LucentConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenHold[LucentTerminalConnection partyToHold]", this);
		try {
			final TSConnection party = partyToHold == null ? null
					: ((TsapiConnection) partyToHold).tsConnection;

			tsConnection.listenHold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenHold[LucentTerminalConnection partyToHold]", this);
	}

	public final void listenHold(final LucentTerminalConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenHold[LucentTerminalConnection partyToHold]", this);
		try {
			final TSConnection party = partyToHold == null ? null
					: ((TsapiTerminalConnection) partyToHold).tsConnection;

			tsConnection.listenHold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenHold[LucentTerminalConnection partyToHold]", this);
	}

	public final void listenUnhold(final LucentConnection partyToUnhold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
		try {
			final TSConnection party = partyToUnhold == null ? null
					: ((TsapiConnection) partyToUnhold).tsConnection;

			tsConnection.listenUnhold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
	}

	public final void listenUnhold(final LucentTerminalConnection partyToUnhold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
		try {
			final TSConnection party = partyToUnhold == null ? null
					: ((TsapiTerminalConnection) partyToUnhold).tsConnection;

			tsConnection.listenUnhold(party);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"listenUnhold[LucentTerminalConnection partyToUnhold]", this);
	}

	public final Object sendPrivateData(final Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			final Object obj = tsConnection.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));
			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (final ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}
	}

	public final void setDtmfDetection(final boolean enable)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("setDtmfDetection[boolean enable]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
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

	public final void startPlaying() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("startPlaying[]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

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

	public final void usePlayURL(final URL url)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("usePlayURL[URL url]", this);
		try {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by implementation");
		} finally {
			privData = null;
		}
	}

	public final void useRecordURL(final URL url)
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
