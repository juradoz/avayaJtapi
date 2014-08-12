package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

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

	public static CSTAQueuedEvent decode(InputStream in) {
		CSTAQueuedEvent _this = new CSTAQueuedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.queuedConnection = CSTAConnectionID.decode(memberStream);
		this.queueingDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.numberQueued = ASNInteger.decode(memberStream);
		this.localConnectionInfo = LocalConnectionState.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.queuedConnection, memberStream);
		CSTAExtendedDeviceID.encode(this.queueingDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.lastRedirectionDevice, memberStream);
		ASNInteger.encode(this.numberQueued, memberStream);
		LocalConnectionState.encode(this.localConnectionInfo, memberStream);
		CSTAEventCause.encode(this.cause, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueuedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(this.queuedConnection,
				"queuedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.queueingDevice,
				"queueingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice,
				"callingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice,
				"calledDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.lastRedirectionDevice,
				"lastRedirectionDevice", indent));
		lines.addAll(ASNInteger
				.print(this.numberQueued, "numberQueued", indent));
		lines.addAll(LocalConnectionState.print(this.localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 64;
	}

	public CSTAExtendedDeviceID getCalledDevice() {
		return this.calledDevice;
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return this.callingDevice;
	}

	public short getCause() {
		return this.cause;
	}

	public CSTAExtendedDeviceID getLastRedirectionDevice() {
		return this.lastRedirectionDevice;
	}

	public short getLocalConnectionInfo() {
		return this.localConnectionInfo;
	}

	public int getNumberQueued() {
		return this.numberQueued;
	}

	public CSTAConnectionID getQueuedConnection() {
		return this.queuedConnection;
	}

	public CSTAExtendedDeviceID getQueueingDevice() {
		return this.queueingDevice;
	}

	public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
		this.calledDevice = calledDevice;
	}

	public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
		this.callingDevice = callingDevice;
	}

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setLastRedirectionDevice(
			CSTAExtendedDeviceID lastRedirectionDevice) {
		this.lastRedirectionDevice = lastRedirectionDevice;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setNumberQueued(int numberQueued) {
		this.numberQueued = numberQueued;
	}

	public void setQueuedConnection(CSTAConnectionID queuedConnection) {
		this.queuedConnection = queuedConnection;
	}

	public void setQueueingDevice(CSTAExtendedDeviceID queueingDevice) {
		this.queueingDevice = queueingDevice;
	}
}