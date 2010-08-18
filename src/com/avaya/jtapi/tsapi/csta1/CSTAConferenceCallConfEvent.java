package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAConferenceCallConfEvent extends CSTAConfirmation {
	public static CSTAConferenceCallConfEvent decode(InputStream in) {
		CSTAConferenceCallConfEvent _this = new CSTAConferenceCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID newCall;
	CSTAConnection[] connList;

	public static final int PDU = 12;

	public CSTAConferenceCallConfEvent() {
	}

	public CSTAConferenceCallConfEvent(CSTAConnectionID _newCall,
			CSTAConnection[] _connList) {
		newCall = _newCall;
		connList = _connList;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		newCall = CSTAConnectionID.decode(memberStream);
		connList = ConnectionList.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(newCall, memberStream);
		ConnectionList.encode(connList, memberStream);
	}

	public CSTAConnection[] getConnList() {
		return connList;
	}

	public CSTAConnectionID getNewCall() {
		return newCall;
	}

	@Override
	public int getPDU() {
		return 12;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConferenceCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(newCall, "newCall", indent));
		lines.addAll(ConnectionList.print(connList, "connList", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAConferenceCallConfEvent JD-Core Version:
 * 0.5.4
 */