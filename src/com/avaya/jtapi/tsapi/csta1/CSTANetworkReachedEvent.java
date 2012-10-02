package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTANetworkReachedEvent extends CSTAUnsolicited {
	CSTAConnectionID connection;
	CSTAExtendedDeviceID trunkUsed;
	CSTAExtendedDeviceID calledDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 62;

	public static CSTANetworkReachedEvent decode(InputStream in) {
		CSTANetworkReachedEvent _this = new CSTANetworkReachedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.connection = CSTAConnectionID.decode(memberStream);
		this.trunkUsed = CSTAExtendedDeviceID.decode(memberStream);
		this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.localConnectionInfo = LocalConnectionState.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.connection, memberStream);
		CSTAExtendedDeviceID.encode(this.trunkUsed, memberStream);
		CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
		LocalConnectionState.encode(this.localConnectionInfo, memberStream);
		CSTAEventCause.encode(this.cause, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTANetworkReachedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(this.connection, "connection",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.trunkUsed, "trunkUsed",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice,
				"calledDevice", indent));
		lines.addAll(LocalConnectionState.print(this.localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 62;
	}

	public CSTAExtendedDeviceID getCalledDevice() {
		return this.calledDevice;
	}

	public short getCause() {
		return this.cause;
	}

	public CSTAConnectionID getConnection() {
		return this.connection;
	}

	public short getLocalConnectionInfo() {
		return this.localConnectionInfo;
	}

	public CSTAExtendedDeviceID getTrunkUsed() {
		return this.trunkUsed;
	}

	public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
		this.calledDevice = calledDevice;
	}

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setConnection(CSTAConnectionID connection) {
		this.connection = connection;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setTrunkUsed(CSTAExtendedDeviceID trunkUsed) {
		this.trunkUsed = trunkUsed;
	}
}