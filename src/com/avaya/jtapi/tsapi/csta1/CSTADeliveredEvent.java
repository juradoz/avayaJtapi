package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTADeliveredEvent extends CSTAUnsolicited {
	CSTAConnectionID connection;
	CSTAExtendedDeviceID alertingDevice;
	CSTAExtendedDeviceID callingDevice;
	CSTAExtendedDeviceID calledDevice;
	CSTAExtendedDeviceID lastRedirectionDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 57;

	public static CSTADeliveredEvent decode(InputStream in) {
		CSTADeliveredEvent _this = new CSTADeliveredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		connection = CSTAConnectionID.decode(memberStream);
		alertingDevice = CSTAExtendedDeviceID.decode(memberStream);
		callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(connection, memberStream);
		ASNSequence.encode(alertingDevice, memberStream);
		ASNSequence.encode(callingDevice, memberStream);
		ASNSequence.encode(calledDevice, memberStream);
		ASNSequence.encode(lastRedirectionDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public CSTAExtendedDeviceID getAlertingDevice() {
		return alertingDevice;
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

	public CSTAConnectionID getConnection() {
		return connection;
	}

	public CSTAExtendedDeviceID getLastRedirectionDevice() {
		return lastRedirectionDevice;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 57;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADeliveredEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(connection, "connection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(alertingDevice,
				"alertingDevice", indent));
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

	public void setAlertingDevice(CSTAExtendedDeviceID alertingDevice) {
		this.alertingDevice = alertingDevice;
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

	public void setConnection(CSTAConnectionID connection) {
		this.connection = connection;
	}

	public void setLastRedirectionDevice(
			CSTAExtendedDeviceID lastRedirectionDevice) {
		this.lastRedirectionDevice = lastRedirectionDevice;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}

