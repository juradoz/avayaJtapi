package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTAServiceInitiatedEvent extends CSTAUnsolicited {
	CSTAConnectionID initiatedConnection;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 66;

	public static CSTAServiceInitiatedEvent decode(InputStream in) {
		CSTAServiceInitiatedEvent _this = new CSTAServiceInitiatedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		initiatedConnection = CSTAConnectionID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(initiatedConnection, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	public CSTAConnectionID getInitiatedConnection() {
		return initiatedConnection;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 66;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAServiceInitiatedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(initiatedConnection,
				"initiatedConnection", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setInitiatedConnection(CSTAConnectionID initiatedConnection) {
		this.initiatedConnection = initiatedConnection;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}

