package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAQueuedEvent extends CSTAUnsolicited {
	CSTAConnectionID queuedConnection;
	CSTAExtendedDeviceID queueingDevice;
	CSTAExtendedDeviceID callingDevice;
	CSTAExtendedDeviceID calledDevice;
	CSTAExtendedDeviceID lastRedirectionDevice;
	int numberQueued;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 64;

	public static CSTAQueuedEvent decode(final InputStream in) {
		final CSTAQueuedEvent _this = new CSTAQueuedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		queuedConnection = CSTAConnectionID.decode(memberStream);
		queueingDevice = CSTAExtendedDeviceID.decode(memberStream);
		callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
		numberQueued = ASNInteger.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(queuedConnection, memberStream);
		ASNSequence.encode(queueingDevice, memberStream);
		ASNSequence.encode(callingDevice, memberStream);
		ASNSequence.encode(calledDevice, memberStream);
		ASNSequence.encode(lastRedirectionDevice, memberStream);
		ASNInteger.encode(numberQueued, memberStream);
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

	public CSTAExtendedDeviceID getLastRedirectionDevice() {
		return lastRedirectionDevice;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	public int getNumberQueued() {
		return numberQueued;
	}

	@Override
	public int getPDU() {
		return 64;
	}

	public CSTAConnectionID getQueuedConnection() {
		return queuedConnection;
	}

	public CSTAExtendedDeviceID getQueueingDevice() {
		return queueingDevice;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueuedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(queuedConnection,
				"queuedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(queueingDevice,
				"queueingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(callingDevice, "callingDevice",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(calledDevice, "calledDevice",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(lastRedirectionDevice,
				"lastRedirectionDevice", indent));
		lines.addAll(ASNInteger.print(numberQueued, "numberQueued", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
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

	public void setLastRedirectionDevice(
			final CSTAExtendedDeviceID lastRedirectionDevice) {
		this.lastRedirectionDevice = lastRedirectionDevice;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setNumberQueued(final int numberQueued) {
		this.numberQueued = numberQueued;
	}

	public void setQueuedConnection(final CSTAConnectionID queuedConnection) {
		this.queuedConnection = queuedConnection;
	}

	public void setQueueingDevice(final CSTAExtendedDeviceID queueingDevice) {
		this.queueingDevice = queueingDevice;
	}
}
