package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAEstablishedEvent extends CSTAUnsolicited {
	CSTAConnectionID establishedConnection;
	CSTAExtendedDeviceID answeringDevice;
	CSTAExtendedDeviceID callingDevice;
	CSTAExtendedDeviceID calledDevice;
	CSTAExtendedDeviceID lastRedirectionDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 59;

	public static CSTAEstablishedEvent decode(final InputStream in) {
		final CSTAEstablishedEvent _this = new CSTAEstablishedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		establishedConnection = CSTAConnectionID.decode(memberStream);
		answeringDevice = CSTAExtendedDeviceID.decode(memberStream);
		callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(establishedConnection, memberStream);
		ASNSequence.encode(answeringDevice, memberStream);
		ASNSequence.encode(callingDevice, memberStream);
		ASNSequence.encode(calledDevice, memberStream);
		ASNSequence.encode(lastRedirectionDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public CSTAExtendedDeviceID getAnsweringDevice() {
		return answeringDevice;
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

	public CSTAConnectionID getEstablishedConnection() {
		return establishedConnection;
	}

	public CSTAExtendedDeviceID getLastRedirectionDevice() {
		return lastRedirectionDevice;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 59;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAEstablishedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(establishedConnection,
				"establishedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(answeringDevice,
				"answeringDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(callingDevice, "callingDevice",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(calledDevice, "calledDevice",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(lastRedirectionDevice,
				"lastRedirectionDevice", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setAnsweringDevice(final CSTAExtendedDeviceID answeringDevice) {
		this.answeringDevice = answeringDevice;
	}

	public void setCalledDevice(final CSTAExtendedDeviceID calledDevice) {
		this.calledDevice = calledDevice;
	}

	public void setCallingDevice(final CSTAExtendedDeviceID callingDevice) {
		this.callingDevice = callingDevice;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setEstablishedConnection(
			final CSTAConnectionID establishedConnection) {
		this.establishedConnection = establishedConnection;
	}

	public void setLastRedirectionDevice(
			final CSTAExtendedDeviceID lastRedirectionDevice) {
		this.lastRedirectionDevice = lastRedirectionDevice;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}
