package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

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

	public void decodeMembers(InputStream memberStream) {
		this.initiatedConnection = CSTAConnectionID.decode(memberStream);
		this.localConnectionInfo = LocalConnectionState.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.initiatedConnection, memberStream);
		LocalConnectionState.encode(this.localConnectionInfo, memberStream);
		CSTAEventCause.encode(this.cause, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAServiceInitiatedEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(this.initiatedConnection,
				"initiatedConnection", indent));
		lines.addAll(LocalConnectionState.print(this.localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 66;
	}

	public short getCause() {
		return this.cause;
	}

	public CSTAConnectionID getInitiatedConnection() {
		return this.initiatedConnection;
	}

	public short getLocalConnectionInfo() {
		return this.localConnectionInfo;
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