package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAConferenceCallConfEvent extends CSTAConfirmation {
	public static CSTAConferenceCallConfEvent decode(final InputStream in) {
		final CSTAConferenceCallConfEvent _this = new CSTAConferenceCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID newCall;
	CSTAConnection[] connList;

	public static final int PDU = 12;

	public CSTAConferenceCallConfEvent() {
	}

	public CSTAConferenceCallConfEvent(final CSTAConnectionID _newCall,
			final CSTAConnection[] _connList) {
		newCall = _newCall;
		connList = _connList;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		newCall = CSTAConnectionID.decode(memberStream);
		connList = ConnectionList.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConferenceCallConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(newCall, "newCall", indent));
		lines.addAll(ConnectionList.print(connList, "connList", indent));

		lines.add("}");
		return lines;
	}
}
