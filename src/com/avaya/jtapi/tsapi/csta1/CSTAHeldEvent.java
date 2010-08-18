package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAHeldEvent extends CSTAUnsolicited {
	CSTAConnectionID heldConnection;
	CSTAExtendedDeviceID holdingDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 61;

	public static CSTAHeldEvent decode(InputStream in) {
		CSTAHeldEvent _this = new CSTAHeldEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		heldConnection = CSTAConnectionID.decode(memberStream);
		holdingDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(heldConnection, memberStream);
		ASNSequence.encode(holdingDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	public CSTAConnectionID getHeldConnection() {
		return heldConnection;
	}

	public CSTAExtendedDeviceID getHoldingDevice() {
		return holdingDevice;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 61;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAHeldEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(heldConnection, "heldConnection",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(holdingDevice, "holdingDevice",
				indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setHeldConnection(CSTAConnectionID heldConnection) {
		this.heldConnection = heldConnection;
	}

	public void setHoldingDevice(CSTAExtendedDeviceID holdingDevice) {
		this.holdingDevice = holdingDevice;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}

