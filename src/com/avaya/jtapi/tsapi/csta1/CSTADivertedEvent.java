package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTADivertedEvent extends CSTAUnsolicited {
	CSTAConnectionID connection;
	CSTAExtendedDeviceID divertingDevice;
	CSTAExtendedDeviceID newDestination;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 58;

	public static CSTADivertedEvent decode(final InputStream in) {
		final CSTADivertedEvent _this = new CSTADivertedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		connection = CSTAConnectionID.decode(memberStream);
		divertingDevice = CSTAExtendedDeviceID.decode(memberStream);
		newDestination = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(connection, memberStream);
		ASNSequence.encode(divertingDevice, memberStream);
		ASNSequence.encode(newDestination, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	public CSTAConnectionID getConnection() {
		return connection;
	}

	public CSTAExtendedDeviceID getDivertingDevice() {
		return divertingDevice;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	public CSTAExtendedDeviceID getNewDestination() {
		return newDestination;
	}

	@Override
	public int getPDU() {
		return 58;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADivertedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(connection, "connection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(divertingDevice,
				"divertingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(newDestination,
				"newDestination", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(final short _cause) {
		cause = _cause;
	}

	public void setConnection(final CSTAConnectionID _connection) {
		connection = _connection;
	}

	public void setDivertingDevice(final CSTAExtendedDeviceID _divertingDevice) {
		divertingDevice = _divertingDevice;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setNewDestination(final CSTAExtendedDeviceID _newDestination) {
		newDestination = _newDestination;
	}
}
