package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTARetrievedEvent extends CSTAUnsolicited {
	CSTAConnectionID retrievedConnection;
	CSTAExtendedDeviceID retrievingDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 65;

	public static CSTARetrievedEvent decode(final InputStream in) {
		final CSTARetrievedEvent _this = new CSTARetrievedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		retrievedConnection = CSTAConnectionID.decode(memberStream);
		retrievingDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(retrievedConnection, memberStream);
		ASNSequence.encode(retrievingDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 65;
	}

	public CSTAConnectionID getRetrievedConnection() {
		return retrievedConnection;
	}

	public CSTAExtendedDeviceID getRetrievingDevice() {
		return retrievingDevice;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARetrievedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(retrievedConnection,
				"retrievedConnection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(retrievingDevice,
				"retrievingDevice", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setRetrievedConnection(
			final CSTAConnectionID retrievedConnection) {
		this.retrievedConnection = retrievedConnection;
	}

	public void setRetrievingDevice(final CSTAExtendedDeviceID retrievingDevice) {
		this.retrievingDevice = retrievingDevice;
	}
}
