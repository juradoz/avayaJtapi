package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAConferenceCallConfEvent extends CSTAConfirmation {
	CSTAConnectionID newCall;
	CSTAConnection[] connList;
	public static final int PDU = 12;

	public CSTAConferenceCallConfEvent() {
	}

	public CSTAConferenceCallConfEvent(CSTAConnectionID _newCall,
			CSTAConnection[] _connList) {
		this.newCall = _newCall;
		this.connList = _connList;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.newCall, memberStream);
		ConnectionList.encode(this.connList, memberStream);
	}

	public static CSTAConferenceCallConfEvent decode(InputStream in) {
		CSTAConferenceCallConfEvent _this = new CSTAConferenceCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.newCall = CSTAConnectionID.decode(memberStream);
		this.connList = ConnectionList.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConferenceCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
		lines.addAll(ConnectionList.print(this.connList, "connList", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 12;
	}

	public CSTAConnection[] getConnList() {
		return this.connList;
	}

	public CSTAConnectionID getNewCall() {
		return this.newCall;
	}
}