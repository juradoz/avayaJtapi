package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTATransferCallConfEvent extends CSTAConfirmation {
	public static CSTATransferCallConfEvent decode(InputStream in) {
		CSTATransferCallConfEvent _this = new CSTATransferCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID newCall;
	CSTAConnection[] connList;

	public static final int PDU = 52;

	public CSTATransferCallConfEvent() {
	}

	public CSTATransferCallConfEvent(CSTAConnectionID _newCall,
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
		return 52;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTATransferCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(newCall, "newCall", indent));
		lines.addAll(ConnectionList.print(connList, "connList", indent));

		lines.add("}");
		return lines;
	}
}

