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

	public static CSTAConnectionClearedEvent decode(InputStream in) {
		CSTAConnectionClearedEvent _this = new CSTAConnectionClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		droppedConnection = CSTAConnectionID.decode(memberStream);
		releasingDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConnectionClearedEvent ::=");
		lines.add("{");

		String indent = "  ";
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

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setDroppedConnection(CSTAConnectionID droppedConnection) {
		this.droppedConnection = droppedConnection;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setReleasingDevice(CSTAExtendedDeviceID releasingDevice) {
		this.releasingDevice = releasingDevice;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAConnectionClearedEvent JD-Core Version: 0.5.4
 */