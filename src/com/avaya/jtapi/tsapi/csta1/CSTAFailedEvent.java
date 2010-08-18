package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAFailedEvent extends CSTAUnsolicited {
	CSTAConnectionID failedConnection;
	CSTAExtendedDeviceID failingDevice;
	CSTAExtendedDeviceID calledDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 60;

	public static CSTAFailedEvent decode(final InputStream in) {
		final CSTAFailedEvent _this = new CSTAFailedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		failedConnection = CSTAConnectionID.decode(memberStream);
		failingDevice = CSTAExtendedDeviceID.decode(memberStream);
		calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(failedConnection, memberStream);
		ASNSequence.encode(failingDevice, memberStream);
		ASNSequence.encode(calledDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public CSTAExtendedDeviceID getCalledDevice() {
		return calledDevice;
	}

	public short getCause() {
		return cause;
	}

	public CSTAConnectionID getFailedConnection() {
		return failedConnection;
	}

	public CSTAExtendedDeviceID getFailingDevice() {
		return failingDevice;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 60;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAFailedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(failedConnection,
				"failedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(failingDevice, "failingDevice",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(calledDevice, "calledDevice",
				indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCalledDevice(final CSTAExtendedDeviceID calledDevice) {
		this.calledDevice = calledDevice;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setFailedConnection(final CSTAConnectionID connection) {
		failedConnection = connection;
	}

	public void setFailingDevice(final CSTAExtendedDeviceID alertingDevice) {
		failingDevice = alertingDevice;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}
