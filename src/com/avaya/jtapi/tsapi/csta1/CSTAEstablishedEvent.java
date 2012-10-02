package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAEstablishedEvent extends CSTAUnsolicited {
	CSTAConnectionID establishedConnection;
	CSTAExtendedDeviceID answeringDevice;
	CSTAExtendedDeviceID callingDevice;
	CSTAExtendedDeviceID calledDevice;
	CSTAExtendedDeviceID lastRedirectionDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 59;

	public static CSTAEstablishedEvent decode(InputStream in) {
		CSTAEstablishedEvent _this = new CSTAEstablishedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.establishedConnection, memberStream);
		CSTAExtendedDeviceID.encode(this.answeringDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.lastRedirectionDevice, memberStream);
		LocalConnectionState.encode(this.localConnectionInfo, memberStream);
		CSTAEventCause.encode(this.cause, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.establishedConnection = CSTAConnectionID.decode(memberStream);
		this.answeringDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.localConnectionInfo = LocalConnectionState.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAEstablishedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(this.establishedConnection,
				"establishedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.answeringDevice,
				"answeringDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice,
				"callingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice,
				"calledDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.lastRedirectionDevice,
				"lastRedirectionDevice", indent));
		lines.addAll(LocalConnectionState.print(this.localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 59;
	}

	public CSTAExtendedDeviceID getAnsweringDevice() {
		return this.answeringDevice;
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

	public CSTAConnectionID getEstablishedConnection() {
		return this.establishedConnection;
	}

	public CSTAExtendedDeviceID getLastRedirectionDevice() {
		return this.lastRedirectionDevice;
	}

	public short getLocalConnectionInfo() {
		return this.localConnectionInfo;
	}

	public void setAnsweringDevice(CSTAExtendedDeviceID answeringDevice) {
		this.answeringDevice = answeringDevice;
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

	public void setEstablishedConnection(CSTAConnectionID establishedConnection) {
		this.establishedConnection = establishedConnection;
	}

	public void setLastRedirectionDevice(
			CSTAExtendedDeviceID lastRedirectionDevice) {
		this.lastRedirectionDevice = lastRedirectionDevice;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}