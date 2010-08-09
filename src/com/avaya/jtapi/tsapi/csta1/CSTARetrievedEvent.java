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

	public static CSTARetrievedEvent decode(InputStream in) {
		CSTARetrievedEvent _this = new CSTARetrievedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		retrievedConnection = CSTAConnectionID.decode(memberStream);
		retrievingDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("CSTARetrievedEvent ::=");
		lines.add("{");

		String indent = "  ";
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

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setRetrievedConnection(CSTAConnectionID retrievedConnection) {
		this.retrievedConnection = retrievedConnection;
	}

	public void setRetrievingDevice(CSTAExtendedDeviceID retrievingDevice) {
		this.retrievingDevice = retrievingDevice;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARetrievedEvent JD-Core Version: 0.5.4
 */