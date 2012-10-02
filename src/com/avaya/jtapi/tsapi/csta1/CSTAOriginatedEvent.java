package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAOriginatedEvent extends CSTAUnsolicited {
	CSTAConnectionID originatedConnection;
	CSTAExtendedDeviceID callingDevice;
	CSTAExtendedDeviceID calledDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 63;

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.originatedConnection, memberStream);
		CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
		LocalConnectionState.encode(this.localConnectionInfo, memberStream);
		CSTAEventCause.encode(this.cause, memberStream);
	}

	public static CSTAOriginatedEvent decode(InputStream in) {
		CSTAOriginatedEvent _this = new CSTAOriginatedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.originatedConnection = CSTAConnectionID.decode(memberStream);
		this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.localConnectionInfo = LocalConnectionState.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAOriginatedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(this.originatedConnection,
				"originatedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice,
				"callingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice,
				"calledDevice", indent));
		lines.addAll(LocalConnectionState.print(this.localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 63;
	}

	public CSTAExtendedDeviceID getCalledDevice() {
		return this.calledDevice;
	}

	public void setCalledDevice(CSTAExtendedDeviceID deviceId) {
		this.calledDevice = deviceId;
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return this.callingDevice;
	}

	public void setCallingDevice(CSTAExtendedDeviceID deviceId) {
		this.callingDevice = deviceId;
	}

	public short getCause() {
		return this.cause;
	}

	public void setCause(short cause) {
		this.cause = cause;
	}

	public short getLocalConnectionInfo() {
		return this.localConnectionInfo;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public CSTAConnectionID getOriginatedConnection() {
		return this.originatedConnection;
	}

	public void setOriginatedConnection(CSTAConnectionID connectionId) {
		this.originatedConnection = connectionId;
	}
}