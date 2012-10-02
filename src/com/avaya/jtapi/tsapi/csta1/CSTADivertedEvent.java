package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTADivertedEvent extends CSTAUnsolicited {
	CSTAConnectionID connection;
	CSTAExtendedDeviceID divertingDevice;
	CSTAExtendedDeviceID newDestination;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 58;

	public static CSTADivertedEvent decode(InputStream in) {
		CSTADivertedEvent _this = new CSTADivertedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.connection, memberStream);
		CSTAExtendedDeviceID.encode(this.divertingDevice, memberStream);
		CSTAExtendedDeviceID.encode(this.newDestination, memberStream);
		LocalConnectionState.encode(this.localConnectionInfo, memberStream);
		CSTAEventCause.encode(this.cause, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.connection = CSTAConnectionID.decode(memberStream);
		this.divertingDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.newDestination = CSTAExtendedDeviceID.decode(memberStream);
		this.localConnectionInfo = LocalConnectionState.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADivertedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(this.connection, "connection",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.divertingDevice,
				"divertingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.newDestination,
				"newDestination", indent));
		lines.addAll(LocalConnectionState.print(this.localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 58;
	}

	public short getCause() {
		return this.cause;
	}

	public CSTAConnectionID getConnection() {
		return this.connection;
	}

	public CSTAExtendedDeviceID getDivertingDevice() {
		return this.divertingDevice;
	}

	public short getLocalConnectionInfo() {
		return this.localConnectionInfo;
	}

	public CSTAExtendedDeviceID getNewDestination() {
		return this.newDestination;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setCause(short _cause) {
		this.cause = _cause;
	}

	public void setConnection(CSTAConnectionID _connection) {
		this.connection = _connection;
	}

	public void setDivertingDevice(CSTAExtendedDeviceID _divertingDevice) {
		this.divertingDevice = _divertingDevice;
	}

	public void setNewDestination(CSTAExtendedDeviceID _newDestination) {
		this.newDestination = _newDestination;
	}
}