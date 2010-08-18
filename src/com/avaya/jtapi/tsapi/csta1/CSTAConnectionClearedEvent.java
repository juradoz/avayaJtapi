package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAConnectionClearedEvent extends CSTAUnsolicited {
	CSTAConnectionID droppedConnection;
	CSTAExtendedDeviceID releasingDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 56;

	public static CSTAConnectionClearedEvent decode(final InputStream in) {
		final CSTAConnectionClearedEvent _this = new CSTAConnectionClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		droppedConnection = CSTAConnectionID.decode(memberStream);
		releasingDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(droppedConnection, memberStream);
		ASNSequence.encode(releasingDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	public CSTAConnectionID getDroppedConnection() {
		return droppedConnection;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 56;
	}

	public CSTAExtendedDeviceID getReleasingDevice() {
		return releasingDevice;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConnectionClearedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(droppedConnection,
				"droppedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(releasingDevice,
				"releasingDevice", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setDroppedConnection(final CSTAConnectionID droppedConnection) {
		this.droppedConnection = droppedConnection;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setReleasingDevice(final CSTAExtendedDeviceID releasingDevice) {
		this.releasingDevice = releasingDevice;
	}
}
