package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAOriginatedEvent extends CSTAUnsolicited {
	CSTAConnectionID originatedConnection;
	CSTAExtendedDeviceID callingDevice;
	CSTAExtendedDeviceID calledDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 63;

	public static CSTAOriginatedEvent decode(final InputStream in) {
		final CSTAOriginatedEvent _this = new CSTAOriginatedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		originatedConnection = CSTAConnectionID.decode(memberStream);
		callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(originatedConnection, memberStream);
		ASNSequence.encode(callingDevice, memberStream);
		ASNSequence.encode(calledDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public CSTAExtendedDeviceID getCalledDevice() {
		return calledDevice;
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return callingDevice;
	}

	public short getCause() {
		return cause;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	public CSTAConnectionID getOriginatedConnection() {
		return originatedConnection;
	}

	@Override
	public int getPDU() {
		return 63;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAOriginatedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(originatedConnection,
				"originatedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(callingDevice, "callingDevice",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(calledDevice, "calledDevice",
				indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCalledDevice(final CSTAExtendedDeviceID deviceId) {
		calledDevice = deviceId;
	}

	public void setCallingDevice(final CSTAExtendedDeviceID deviceId) {
		callingDevice = deviceId;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setOriginatedConnection(final CSTAConnectionID connectionId) {
		originatedConnection = connectionId;
	}
}
